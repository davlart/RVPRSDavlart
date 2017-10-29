package PO52.davletkaliyev.wdad.learn.xml;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.*;

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

import static com.sun.xml.internal.ws.util.xml.XmlUtil.newTransformer;

/**
 * Created by ArthurArt on 09.10.2017.
 */
public class XmlTask {

    final File xmlFile;
    private String filePath;
    private Document doc;


    public XmlTask(String fileName) throws Exception{
        this.filePath = fileName;
        this.xmlFile = new File(filePath);
        try {

            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
             doc = documentBuilder.parse(xmlFile);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (org.xml.sax.SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }


    }
//    возвращающий сумму, которую необходимо оплатить владельцам заданной квартиры.
//    Счет определяется разницей показаний счетчиков за текущий месяц и предыдущий. Тарифы определены так же в xml-документе
    public double getBill (String street, String buildingNumber, String flatNumber)
    {
        double result = 0.0;
        double bufValue = 0.0;
        NodeList nodeList;
        NodeList bufNodeList;
        Element element;
        Element bufElement;
        Element elementPreviousMonth;
        Element elementCurrentMonth;
        nodeList = doc.getElementsByTagName("tariffs");
        element = (Element) nodeList.item(0);
        double coldwater = Double.parseDouble( element.getAttribute("coldwater"));
        double hotwater = Double.parseDouble(element.getAttribute("hotwater"));
        double electricity = Double.parseDouble(element.getAttribute("electricity"));
        double gas = Double.parseDouble(element.getAttribute("gas"));

        nodeList = doc.getElementsByTagName("building");

        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if((element.getAttribute("street").equals(street))
                    &&(element.getAttribute("number").equals(buildingNumber)))
            {
                for (int j = 0; j < element.getElementsByTagName("flat").getLength(); j++) {
                    bufElement = (Element) element.getElementsByTagName("flat").item(j);
                    if(bufElement.getAttribute("number").equals(flatNumber)) {
                        elementCurrentMonth = getCurrentMonth(bufElement);
                        elementPreviousMonth = getPreviousMonth(bufElement);

                            if((elementCurrentMonth.getElementsByTagName("coldwater")!=null)
                               &&(elementPreviousMonth.getElementsByTagName("coldwater")!=null))
                                        result += (Double.parseDouble(elementCurrentMonth.getElementsByTagName("coldwater").item(0).getTextContent())
                                            -Double.parseDouble(elementPreviousMonth.getElementsByTagName("coldwater").item(0).getTextContent()))*coldwater;;

                            if((elementCurrentMonth.getElementsByTagName("hotwater")!=null)
                                        &&(elementPreviousMonth.getElementsByTagName("hotwater")!=null))
                                        result += (Double.parseDouble(elementCurrentMonth.getElementsByTagName("hotwater").item(0).getTextContent())
                                            -Double.parseDouble(elementPreviousMonth.getElementsByTagName("hotwater").item(0).getTextContent()))*hotwater;

                            if((elementCurrentMonth.getElementsByTagName("electricity")!=null)
                                        &&(elementPreviousMonth.getElementsByTagName("electricity")!=null))
                                        result += (Double.parseDouble(elementCurrentMonth.getElementsByTagName("electricity").item(0).getTextContent())
                                            -Double.parseDouble(elementPreviousMonth.getElementsByTagName("electricity").item(0).getTextContent()))*electricity;

                            if((elementCurrentMonth.getElementsByTagName("gas")!=null)
                                    &&(elementPreviousMonth.getElementsByTagName("gas")!=null))
                                    result+=(Double.parseDouble(elementCurrentMonth.getElementsByTagName("gas").item(0).getTextContent())
                                        -Double.parseDouble(elementPreviousMonth.getElementsByTagName("gas").item(0).getTextContent()))*gas;

                    }

                }
                return result;
            }
        }

        return 1;
    }

//    изменяющий стоимость заданной  единицы показания счетчика (ХВС, ГВС, электроэнергия, газ).
    public void setTariff (String tariffName, double newValue) throws TransformException {
        NodeList nodeList = doc.getElementsByTagName("tariffs");
        Element element = (Element) nodeList.item(0);
        element.setAttribute(tariffName, String.valueOf(newValue));
        saveTransformXML();
    }

//    добавляющий показания счетчиков к заданной квартире в заданный период.
    public void addRegistration (String street, String buildingNumber, String flatNumber, int year, int month, double coldWater,
                                 double hotWater, double electricity, double gas) throws TransformException {

        NodeList nodeList = doc.getElementsByTagName("building");
        NodeList bufNodeList;
        Element bufElement;
        Element element;
        for (int i = 0; i < nodeList.getLength() ; i++) {
            element = (Element) nodeList.item(i);
            if(element.getAttribute("street").equals(street)&&element.getAttribute("number").equals(buildingNumber)){
                bufNodeList = element.getElementsByTagName("flat");
                for (int j = 0; j < bufNodeList.getLength(); j++) {
                    bufElement = (Element) bufNodeList.item(j);
                    if(bufElement.getAttribute("number").equals(flatNumber)){
                        Element elem  = (Element) doc.createElement("registration");
                        elem.setAttribute("month", String.valueOf(month));
                        elem.setAttribute("year", String.valueOf(year));
                        bufElement.appendChild(elem);
                        Element elem1 = (Element) doc.createElement("coldwater") ;
                        elem1.setTextContent(String.valueOf(coldWater));
                        elem.appendChild(elem1);
                        elem1 = (Element) doc.createElement("hotwater") ;
                        elem1.setTextContent(String.valueOf(coldWater));
                        elem.appendChild(elem1);
                        elem1 = (Element) doc.createElement("electricity") ;
                        elem1.setTextContent(String.valueOf(coldWater));
                        elem.appendChild(elem1);
                        elem1 = (Element) doc.createElement("gas") ;
                        elem1.setTextContent(String.valueOf(coldWater));
                        elem.appendChild(elem1);



                        saveTransformXML();
                    }

                }
            }
        }
    }

    private Element getCurrentMonth(Element flatValue){
        NodeList nodeList = flatValue.getElementsByTagName("registration");
        return  (Element) nodeList.item(nodeList.getLength()-1);
    }

    private Element getPreviousMonth (Element flatValue){
        NodeList nodeList = flatValue.getElementsByTagName("registration");
        return  (Element) nodeList.item(nodeList.getLength()-2);
    }
    private void saveTransformXML() throws TransformException {
                try {
                        Transformer transformer = TransformerFactory.newInstance()
                                        .newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(filePath));
                        transformer.transform(source, result);
                   } catch (TransformerConfigurationException ex) {
                   } catch (TransformerException ex) {
                   }
    }
}
