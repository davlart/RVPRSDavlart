package PO52.davletkaliyev.wdad.learn.xml;

/**
 * Created by ArthurArt on 29.10.2017.
 */
public class TestXmlTask {
    public static void main(String[] args) throws Exception {
        XmlTask xmlTask = new XmlTask("D:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\learn\\xml\\fersion2.xml");
        System.out.println(xmlTask.getBill("N.Ponova","64","43"));
        xmlTask.setTariff("coldwater",21);
        xmlTask.addRegistration("N.Ponova","64","43",2017,4,120,500,1850,225);
    }
}
