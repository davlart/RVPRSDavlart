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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.sun.xml.internal.ws.util.xml.XmlUtil.newTransformer;

/**
 * Created by ArthurArt on 09.10.2017.
 */
public class XmlTask {


    Housekeeper housekeeper;

    public XmlTask(String fileName) throws Exception{
         housekeeper = new Housekeeper(fileName);
    }
    public double getBill(String street, int buildingNumber, int flatNumber){
        return housekeeper.getBill(street,buildingNumber,flatNumber);
    }
//    изменяющий стоимость заданной  единицы показания счетчика (ХВС, ГВС, электроэнергия, газ).
    public void setTariff (String tariffName, double newValue) throws TransformException {
         housekeeper.setTariff(tariffName,newValue);
    }

    public void addRegistration (String street, int buildingNumber, int flatNumber, int year, int month, double coldWater,
                                double hotWater, double electricity, double gas) throws TransformException {
        housekeeper.addRegistration( street,  buildingNumber,  flatNumber,  year,  month,  coldWater,
         hotWater,  electricity,  gas);
    }
}
