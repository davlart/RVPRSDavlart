package PO52.davletkaliyev.wdad.data.managers;

/**
 * Created by ArthurArt on 29.10.2017.
 */
import PO52.davletkaliyev.wdad.utils.PreferencesConstantManager;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.transform.Transformer;
 import javax.xml.transform.TransformerConfigurationException;
 import javax.xml.transform.TransformerException;
 import javax.xml.transform.TransformerFactory;
 import javax.xml.transform.dom.DOMSource;
 import javax.xml.transform.stream.StreamResult;
 import java.io.File;
 import java.util.Enumeration;
 import java.util.Properties;

public class PreferencesManager {
     private static volatile PreferencesManager instance;
     private Document doc;
     private static final String FILE_PATH="D:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\resources\\configuration\\appconfig.xml";

    private PreferencesManager() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new File(FILE_PATH));
    }

    public String getName(String object) {
        StringBuilder nameInfo = new StringBuilder("rmi://");
        nameInfo.append(getProperty(PreferencesConstantManager.REGISTRYADDRESS)).append(":")
                .append(getProperty(PreferencesConstantManager.REGISTRYPORT)).append("/")
                .append(object);
        return nameInfo.toString();
    }

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
        }

        return instance;
    }

    public void setProperty(String key, String value) {
        getElement(key).setTextContent(value);
    }

    public String getProperty(String key) {
        return getElement(key).getTextContent();
    }

    public void setProperties(Properties prop) {
        Enumeration elements = prop.elements();
        Enumeration keys = prop.keys();
        while (elements.hasMoreElements()) {
            getElement((String) elements.nextElement()).setTextContent((String) keys.nextElement());
        }
    }

    public Properties getProperties() {
        Properties prop = new Properties();
        String[] keys = new String[]{
                PreferencesConstantManager.CREATEREGISTRY,
                PreferencesConstantManager.REGISTRYADDRESS,
                PreferencesConstantManager.REGISTRYPORT,
                PreferencesConstantManager.POLICYPATH,
                PreferencesConstantManager.USECODEBASEONLY,
                PreferencesConstantManager.CLASSPROVIDER
        };

        for (String key : keys) {
            setProperty(key, getElement(key).getTextContent());
        }

        return prop;
    }

    public void addBindedObject(String name, String className) {
        NodeList nodeList = doc.getElementsByTagName("bindedobject");
        Element element;
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            if ((element.getAttribute("class").equals(className)) & (element.getAttribute("name").equals(name)))
                return;
            }
        element = doc.createElement("bindedobject");
        element.setAttribute("class", className);
        element.setAttribute("name", name);
        getElement("rmi").appendChild(element);
        saveTransformXml();
    }

    public void removeBindedObject(String name) {
                NodeList nodeList = doc.getElementsByTagName("bindedobject");
                Element element;
                for (int i = 0; i < nodeList.getLength(); i++) {
                       element = (Element) nodeList.item(i);
                       if (element.getAttribute("name").equals(name))
                            {
                                        getElement("rmi").removeChild(element);
                       }
                   }
        saveTransformXml();
    }


    private Element getElement(String nameField) {
        NodeList nodeList = doc.getElementsByTagName(nameField);
        String[] field = nameField.split("\\.");
        nodeList = doc.getElementsByTagName(field[field.length - 1]);
        Node node = nodeList.item(0);
        return (Element) node;
    }

    //region Createregistry
    @Deprecated
    public String getCreateregistry() {
        return getElement("createregistry").getTextContent();
    }

    @Deprecated
    public void setCreateregistry(String value) {
        getElement("createregistry").setTextContent(value);
    }
     //endregion

    //region Registryaddress
    @Deprecated
    public String getRegistryaddress() {
        return getElement("registryaddress").getTextContent();
    }

    @Deprecated
    public void setRegistryaddress(String value) {
        getElement("registryaddress").setTextContent(value);
    }
     //endregion

    //region Registryport
    @Deprecated
    public int getRegistryport() {
        return Integer.parseInt(getElement("registryport").getTextContent());
    }

    @Deprecated
    public void setRegistryport(int value) {
        getElement("registryport").setTextContent(String.valueOf(value));
    }
    //endregion

    //region Policypath
    @Deprecated
    public String getPolicypath() {
        return getElement("policypath").getTextContent();
    }

    @Deprecated
    public void setPolicypath(String value) {
        getElement("policypath").setTextContent(value);
    }
     //endregion

    //region Usecodebaseonly
    @Deprecated
    public String getUsecodebaseonly() {
        return getElement("usecodebaseonly").getTextContent();
    }

    @Deprecated
    public void setUsecodebaseonly(String value) {
        getElement("usecodebaseonly").setTextContent(value);
    }
     //endregion

    //region Classprovider
    @Deprecated
    public String getClassprovider() {
        return getElement("classprovider").getTextContent();
    }

    @Deprecated
    public void setClassprovider(String value) {
        getElement("classprovider").setTextContent(value);
    }
     //endregion

    public void saveTransformXml()
     {
         try {
             Transformer transformer = TransformerFactory.newInstance()
                     .newTransformer();
             DOMSource source = new DOMSource(doc);
             StreamResult result = new StreamResult(new File(FILE_PATH));
             transformer.transform(source, result);
         } catch (TransformerConfigurationException ex) {
             System.out.println(ex.getMessage());
         } catch (TransformerException ex) {
             System.out.println(ex.getMessage());
         }
     }
 }