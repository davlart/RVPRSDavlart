package PO52.davletkaliyev.wdad.learn.xml;

import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Flat;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArthurArt on 29.10.2017.
 */
public class TestXmlTask {

    public static void main(String[] args) throws Exception {
        XmlTask xmlTask = new XmlTask("D:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\learn\\xml\\fersion2.xml");
        Housekeeper housekeeper = new Housekeeper("D:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\learn\\xml\\fersion2.xml");

        System.out.println(xmlTask.getBill("N.Ponova",64,43));
        System.out.println(housekeeper.getBill2("N.Ponova",64,43));
//        xmlTask.setTariff("coldwater",21);
//        xmlTask.addRegistration("N.Ponova",64,43,2017,4,120,500,1850,225);
//
        //testXmlParseInClass();
    }

    //Test parseXml in Class
    public static void testXmlParseInClass() throws ParseException {
        Housekeeper housekeeper = new Housekeeper("D:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\learn\\xml\\fersion2.xml");

        List<Building> buildings = housekeeper.getBuilding();
        List<Flat> flats;
        List<Registration> registrations;
        for (int i = 0; i < buildings.size(); i++) {
            System.out.println(buildings.get(i).getStreet()+" "+buildings.get(i).getNumber());
            flats = buildings.get(i).getFlatList();
            for (int j = 0; j < flats.size(); j++) {
                System.out.println("    "+flats.get(j).getNumber()+" "+flats.get(j).getArea()+" "
                        +flats.get(j).getPersonsQuantity());
                registrations = flats.get(j).getRegistrations();
                for (int k = 0; k < registrations.size(); k++) {
                    System.out.println("        "+
                            registrations.get(k).getDate() +" "+
                            registrations.get(k).getColdwater()+" "+
                            registrations.get(k).getHotwater()+" "+
                            registrations.get(k).getElectricity()+" "+
                            registrations.get(k).getGas());
                }
            }
        }
    }

}
