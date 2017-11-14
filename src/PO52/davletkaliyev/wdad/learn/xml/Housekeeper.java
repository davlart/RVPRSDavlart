package PO52.davletkaliyev.wdad.learn.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.crypto.dsig.TransformException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ArthurArt on 02.11.2017.
 */
public class Housekeeper {
    final File xmlFile;
    private String filePath;
    private Document doc;
    private NodeList buildingNodes;
    private Tariffs tariffs;

    
    public Housekeeper(String fileName){
        this.filePath = fileName;
        this.xmlFile = new File(filePath);
        try {

            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            this.doc = documentBuilder.parse(xmlFile);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (org.xml.sax.SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        this.buildingNodes = getBuildingList();
        this.tariffs = setTariffs();
    }

    private NodeList getBuildingList(){
        return doc.getElementsByTagName("building");
    }
    private Element getBuilding(String street, int number){
        Element building;
        for (int i = 0; i < buildingNodes.getLength(); i++) {
            building = (Element) buildingNodes.item(i);
            if(building.getAttribute("street").equals(street)
                    &&(Integer.parseInt(building.getAttribute("number")) == number))
                return building;
        }
        return null;
    }

    private Element getFlat(Element building, int number){
        Element flat;
        NodeList flatNode = building.getElementsByTagName("flat");
        for (int i = 0; i < flatNode.getLength(); i++) {
            flat = (Element) flatNode.item(i);
            if(Integer.parseInt(flat.getAttribute("number")) == number)
                return flat;
        }
        return null;
    }
    
    private NodeList getTariffs(){
        return doc.getElementsByTagName("tariffs");
    }

    private Tariffs setTariffs(){
        Element tariffs = (Element) doc.getElementsByTagName("tariffs").item(0);
        return new Tariffs(
         Double.parseDouble( tariffs.getAttribute("coldwater")),
         Double.parseDouble( tariffs.getAttribute("hotwater")),
         Double.parseDouble( tariffs.getAttribute("electricity")),
         Double.parseDouble( tariffs.getAttribute("gas"))
        );
    }
    
    private NodeList getRegistrations(Element flat){
        return flat.getElementsByTagName("registration");        
    }

    private Element getRegistrationCurrentDay(int month,int year, Element flat){
        NodeList registrations = getRegistrations(flat);
        Element registration;
        for (int i = 0; i < registrations.getLength() ; i++) {
            registration = (Element) registrations.item(i);
            if((month == Integer.parseInt(registration.getAttribute("month")))
                &&(Integer.parseInt(registration.getAttribute("year")) == year))
                return registration;
        }
        return null;
    }

    private Element getRegistrationBeforeData(int currentMonth,int currentYear, Element flat){
        NodeList registrations = getRegistrations(flat);
        if(registrations.getLength()<2) return null;
        Element registration;
        Element beforeData = (Element) registrations.item(0);
        for (int i = 0; i < registrations.getLength(); i++) {
            registration = (Element) registrations.item(i);
            if((Integer.parseInt(registration.getAttribute("month")) < currentMonth)
               &&(Integer.parseInt(registration.getAttribute("year")) <= currentYear)
               &&(Integer.parseInt(registration.getAttribute("year")) >= Integer.parseInt(beforeData.getAttribute("year")))
               &&(Integer.parseInt(registration.getAttribute("month")) >= Integer.parseInt(beforeData.getAttribute("month")))
                )
                beforeData = registration;
        }

        return beforeData;
    }

    private double getBill(Element toData){
     double result = 0.0;
        if((toData.getElementsByTagName("coldwater")!=null))
            result += Double.parseDouble(toData.getElementsByTagName("coldwater").item(0).getTextContent())*tariffs.coldwater;
        if((toData.getElementsByTagName("hotwater")!=null))
            result += Double.parseDouble(toData.getElementsByTagName("hotwater").item(0).getTextContent())*tariffs.hotwater;;
        if((toData.getElementsByTagName("electricity")!=null))
            result += Double.parseDouble(toData.getElementsByTagName("electricity").item(0).getTextContent())*tariffs.electricity;;
        if((toData.getElementsByTagName("gas")!=null))
            result += Double.parseDouble(toData.getElementsByTagName("gas").item(0).getTextContent())*tariffs.gas;;

        return result;
    }
    private double getBill(Element toData,Element beforeData){
        double result = 0.0;

        if((toData.getElementsByTagName("coldwater")!=null)
                &&(beforeData.getElementsByTagName("coldwater")!=null))
            result += (Double.parseDouble(toData.getElementsByTagName("coldwater").item(0).getTextContent())
                    -Double.parseDouble(beforeData.getElementsByTagName("coldwater").item(0).getTextContent()))*tariffs.coldwater;

        if((toData.getElementsByTagName("hotwater")!=null)
                &&(beforeData.getElementsByTagName("hotwater")!=null))
            result += (Double.parseDouble(toData.getElementsByTagName("hotwater").item(0).getTextContent())
                    -Double.parseDouble(beforeData.getElementsByTagName("hotwater").item(0).getTextContent()))*tariffs.hotwater;

        if((toData.getElementsByTagName("electricity")!=null)
                &&(beforeData.getElementsByTagName("electricity")!=null))
            result += (Double.parseDouble(toData.getElementsByTagName("electricity").item(0).getTextContent())
                    -Double.parseDouble(beforeData.getElementsByTagName("electricity").item(0).getTextContent()))*tariffs.electricity;

        if((toData.getElementsByTagName("gas")!=null)
                &&(beforeData.getElementsByTagName("gas")!=null))
            result += (Double.parseDouble(toData.getElementsByTagName("gas").item(0).getTextContent())
                    -Double.parseDouble(beforeData.getElementsByTagName("gas").item(0).getTextContent()))*tariffs.gas;

        return result;
    }

    public double getBill(String street, int buildingNumber, int flatNumber){

        Element building = getBuilding(street,buildingNumber);
        if(building == null) return 0.0;
        Element flat = getFlat(building,flatNumber);
        if(flat == null) return 0.0;
        Calendar toData = GregorianCalendar.getInstance();
        int currentMonth = toData.get(Calendar.MONTH)+1;
        int currentYear = toData.get(Calendar.YEAR);
        Element registrationCurrentDay = getRegistrationCurrentDay(currentMonth,currentYear,flat);
        if(registrationCurrentDay == null) return 0.0;
        Element registrationBeforeData = getRegistrationBeforeData(currentMonth,currentYear,flat);
        if(registrationBeforeData == null) return getBill(registrationCurrentDay);
        else return getBill(registrationCurrentDay,registrationBeforeData);
        
    }
////    изменяющий стоимость заданной  единицы показания счетчика (ХВС, ГВС, электроэнергия, газ).
    public void setTariff (String tariffName, double newValue) throws TransformException {
        NodeList tariffs = doc.getElementsByTagName("tariffs");
        Element tariff = (Element) tariffs.item(0);
        tariff.setAttribute(tariffName, String.valueOf(newValue));
        saveTransformXML();
    }

    //    добавляющий показания счетчиков к заданной квартире в заданный период.
    public void addRegistration (String street, int buildingNumber, int flatNumber, int year, int month, double coldWater,
                                 double hotWater, double electricity, double gas) throws TransformException {

        Element flat = getFlat(getBuilding(street,buildingNumber),flatNumber);
        Element registration  = (Element) doc.createElement("registration");
        registration.setAttribute("month", String.valueOf(month));
        registration.setAttribute("year", String.valueOf(year));
        flat.appendChild(registration);

        Element bufElementTrafic = (Element) doc.createElement("coldwater") ;
        bufElementTrafic.setTextContent(String.valueOf(coldWater));
        registration.appendChild(bufElementTrafic);
        bufElementTrafic = (Element) doc.createElement("hotwater") ;
        bufElementTrafic.setTextContent(String.valueOf(coldWater));
        registration.appendChild(bufElementTrafic);
        bufElementTrafic = (Element) doc.createElement("electricity") ;
        bufElementTrafic.setTextContent(String.valueOf(coldWater));
        registration.appendChild(bufElementTrafic);
        bufElementTrafic = (Element) doc.createElement("gas") ;
        bufElementTrafic.setTextContent(String.valueOf(coldWater));
        registration.appendChild(bufElementTrafic);

        saveTransformXML();
    }

    private void saveTransformXML() throws TransformException {
                try {
                        Transformer transformer = TransformerFactory.newInstance()
                                        .newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(filePath));
                        transformer.transform(source, result);
                }
                catch (TransformerConfigurationException ex) {}
                catch (TransformerException ex) {}
    }

}
