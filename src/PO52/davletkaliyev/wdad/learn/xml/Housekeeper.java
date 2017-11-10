package PO52.davletkaliyev.wdad.learn.xml;

import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Flat;
import PO52.davletkaliyev.wdad.utils.date.Registration;
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
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ArthurArt on 02.11.2017.
 */
public class Housekeeper implements Serializable{
    final File xmlFile;
    private String filePath;
    private Document doc;
    private NodeList buildingNodes;
    private Tariffs tariffs;
    List<Building> buildings;

    
    public Housekeeper(String fileName) throws ParseException {
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
        this.buildings = getBuildingsList();
    }

    public List<Building> getBuilding(){
        return this.buildings;
    }

    private ArrayList<Building> getBuildingsList() throws ParseException {
        ArrayList<Building> buildingsList = new ArrayList<Building>();
        Element building;
        Building bufBulding;

        for (int i = 0; i < buildingNodes.getLength(); i++) {
            building = (Element) buildingNodes.item(i);
            bufBulding = new Building();
            bufBulding.setNumber(Integer.parseInt(building.getAttribute("number")));
            bufBulding.setStreet(building.getAttribute("street"));

            bufBulding.setFlatList(getFlatList(building));
            buildingsList.add(bufBulding);
        }
        return buildingsList;
    }


    private ArrayList<Flat> getFlatList(Element building) throws ParseException {
        ArrayList<Flat> flatsArrayList = new ArrayList<Flat>();
        Element flatElem;
        NodeList flatNodeList = building.getElementsByTagName("flat");
        Flat bufFlat;
        for (int i = 0; i < flatNodeList.getLength(); i++) {
            flatElem = (Element) flatNodeList.item(i);
            bufFlat = new Flat();

            bufFlat.setPersonsQuantity(Integer.parseInt(flatElem.getAttribute("personsquantity")));
            bufFlat.setNumber(Integer.parseInt(flatElem.getAttribute("number")));
            bufFlat.setArea(Double.parseDouble(flatElem.getAttribute("area")));
            bufFlat.setRegistrations(getRegistrationList(flatElem));

            flatsArrayList.add(bufFlat);
        }
        if(!flatsArrayList.isEmpty()) return flatsArrayList;
        return null;
    }

    private ArrayList<Registration> getRegistrationList(Element flat) throws ParseException {
        ArrayList<Registration> registrations = new ArrayList<Registration>();
        Registration bufRegistration;
        NodeList registrationNodeList = flat.getElementsByTagName("registration");
        Element registrationElm;

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM.yyyy");
        String coldwater,hotwater,electricity,gas,mounth,year;

        for (int i = 0; i < registrationNodeList.getLength(); i++) {
            registrationElm = (Element) registrationNodeList.item(i);
            bufRegistration = new Registration();
            coldwater =  registrationElm.getElementsByTagName("coldwater").item(0).getTextContent();
            if(coldwater!=null)
                bufRegistration.setColdwater(Double.parseDouble(coldwater));

            hotwater =  registrationElm.getElementsByTagName("hotwater").item(0).getTextContent();
            if(hotwater != null)
                bufRegistration.setHotwater(Double.parseDouble(hotwater));

            electricity =  registrationElm.getElementsByTagName("electricity").item(0).getTextContent();
            if(electricity != null)
                bufRegistration.setElectricity(Double.parseDouble(electricity));

            gas =  registrationElm.getElementsByTagName("gas").item(0).getTextContent();
            if(gas != null)
                bufRegistration.setGas(Double.parseDouble(gas));

            mounth =  registrationElm.getAttribute("mounth");
            year = registrationElm.getAttribute("year");
            if((mounth != null)&&(year != null))
                bufRegistration.setDate(format.parse((mounth+1)+"."+year));

            registrations.add(bufRegistration);
        }
        if(!registrations.isEmpty()) return registrations;

        return null;
    }

    private NodeList getBuildingList(){
        return doc.getElementsByTagName("building");
    }
    private NodeList getFlatNodeList(Element building) { return building.getElementsByTagName("flat");}
    private NodeList getRegistrations(Element flat){
        return flat.getElementsByTagName("registration");
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

    public Flat getFlat(Building building,int flatNumber) throws ParseException {
        Flat resultFlat = new Flat();

        Element buildingElm = getBuilding(building.getStreet(),building.getNumber());
        if(building == null) return null;
        Element flatElm = getFlat(buildingElm, flatNumber);
        if(flatElm == null) return null;

        resultFlat.setNumber(Integer.parseInt(flatElm.getAttribute("number")));
        resultFlat.setArea(Double.parseDouble(flatElm.getAttribute("area")));
        resultFlat.setPersonsQuantity(Integer.parseInt(flatElm.getAttribute("personsquantity")));

        ArrayList<Registration> registrations = new ArrayList<Registration>();
        Registration registrationBuf;
        NodeList registrationNodeList = flatElm.getElementsByTagName("registration");
        Element registrationElm;

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM.yyyy");
        String coldwater,hotwater,electricity,gas,mounth,year;


        for (int i = 0; i < registrationNodeList.getLength() ; i++) {
            registrationBuf = new Registration();
            registrationElm = (Element) registrationNodeList.item(i);

            coldwater =  registrationElm.getElementsByTagName("coldwater").item(0).getTextContent();
            if(coldwater!=null)
                registrationBuf.setColdwater(Double.parseDouble(coldwater));

            hotwater =  registrationElm.getElementsByTagName("hotwater").item(0).getTextContent();
            if(hotwater != null)
                registrationBuf.setHotwater(Double.parseDouble(hotwater));

            electricity =  registrationElm.getElementsByTagName("electricity").item(0).getTextContent();
            if(electricity != null)
                registrationBuf.setElectricity(Double.parseDouble(electricity));

            gas =  registrationElm.getElementsByTagName("gas").item(0).getTextContent();
            if(gas != null)
                registrationBuf.setGas(Double.parseDouble(gas));

            mounth =  registrationElm.getAttribute("mounth");
            year = registrationElm.getAttribute("year");
            if((mounth != null)&&(year != null))
            registrationBuf.setDate(format.parse(mounth+"."+year));
            registrations.add(registrationBuf);
        }
        if(registrations.size() != 0)
            resultFlat.setRegistrations(registrations);
        return resultFlat;
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

//    Calendar calendar = new GregorianCalendar();
//        calendar.setTime(registration.getDate());
//    addRegistration(building.getStreet(),building.getNumber(),
//    flatNumber,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,
//            registration.getColdwater(), registration.getHotwater(), registration.getElectricity(),registration.getGas());

    private Registration getRegistrationCurrentDay(int month,int year, Flat flat){
        List<Registration> registrations = flat.getRegistrations();
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < registrations.size() ; i++) {
            calendar.setTime(registrations.get(i).getDate());
            if((month == calendar.get(Calendar.MONTH) + 1)
                    &&(year == calendar.get(Calendar.YEAR)))
                return registrations.get(i);
        }
        return null;
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

    private Registration getRegistrationBeforeData(int currentMonth,int currentYear, Flat flat){
        List<Registration> registrations = flat.getRegistrations();
        if(registrations.size()<2) return null;
        Registration registration;
        Registration beforeData = registrations.get(0);
        Calendar calendarRegistration = new GregorianCalendar();
        Calendar calendarBeforeData = new GregorianCalendar();
        for (int i = 0; i < registrations.size(); i++) {
            registration = registrations.get(i);
            calendarRegistration.setTime(registration.getDate());
            calendarBeforeData.setTime(beforeData.getDate());
            if(((calendarRegistration.get(Calendar.MONTH) + 1) < currentMonth)
                    &&(calendarRegistration.get(Calendar.YEAR) <= currentYear)
                    &&((calendarRegistration.get(Calendar.YEAR)) >= calendarBeforeData.get(Calendar.YEAR))
                    &&((calendarRegistration.get(Calendar.MONTH) + 1) >= (calendarBeforeData.get(Calendar.MONTH) + 1)))
                beforeData = registration;
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
    private double getBill(Registration toData){
        double result = 0.0;
        result +=  toData.getColdwater()*tariffs.coldwater;
        result +=  toData.getHotwater()*tariffs.hotwater;
        result +=  toData.getElectricity()*tariffs.electricity;
        result +=  toData.getGas()*tariffs.gas;

        return result;
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

    private double getBill(Registration toData, Registration beforeData){
        double result = 0.0;
        result +=  (toData.getColdwater()-beforeData.getColdwater())*tariffs.coldwater;
        result +=  (toData.getHotwater()-beforeData.getHotwater())*tariffs.hotwater;
        result +=  (toData.getElectricity()-beforeData.getElectricity())*tariffs.electricity;
        result +=  (toData.getGas()-beforeData.getGas())*tariffs.gas;

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

    public double getBill2(String street, int buildingNumber, int flatNumber){
        Building building = null;

        for (int i = 0; i < buildings.size(); i++) {
            if(buildings.get(i).getStreet().equals(street)&&(buildings.get(i).getNumber() == buildingNumber))
                building = buildings.get(i);
        }
        if(building == null) return 0.0;

        List<Flat> flatList = building.getFlatList();
        Flat flat = null;
        for (int i = 0; i < flatList.size(); i++) {
            if(flatList.get(i).getNumber()==flatNumber) flat = flatList.get(i);
        }
        if(flat == null) return 0.0;
        Calendar toData = GregorianCalendar.getInstance();
        int currentMonth = toData.get(Calendar.MONTH)+1;
        int currentYear = toData.get(Calendar.YEAR);
        Registration registrationCurrentDay = getRegistrationCurrentDay(currentMonth,currentYear,flat);
        if(registrationCurrentDay == null) return 0.0;
        Registration registrationBeforeData = getRegistrationBeforeData(currentMonth,currentYear,flat);
        if(registrationBeforeData == null) return getBill(registrationCurrentDay);
        else return getBill(registrationCurrentDay,registrationBeforeData);
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

    public double getBill(Building building, int flatNumber){
        return getBill(building.getStreet(),building.getNumber(),flatNumber);
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

    public void addRegistration(Building building, int flatNumber, Registration registration) throws TransformException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(registration.getDate());
        addRegistration(building.getStreet(),building.getNumber(),
                flatNumber,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,
                registration.getColdwater(), registration.getHotwater(), registration.getElectricity(),registration.getGas());
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
