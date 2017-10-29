package PO52.davletkaliyev.wdad.data.managers;

/**
 * Created by ArthurArt on 29.10.2017.
 */
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

public class PreferencesManager {
     private static volatile PreferencesManager instance;
     private Document doc;
     private static final String FILE_PATH="src\\PO52\\Pecherin\\wdad\\resources\\configuration\\appconfig.xml";

    private PreferencesManager() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new File(FILE_PATH));
    }

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
        }

        return instance;
    }

    private Element getElement(String nameField) {
        NodeList nodeList = doc.getElementsByTagName(nameField);
        Node node = nodeList.item(0);
        return (Element) node;
    }

    //region Createregistry
    public String getCreateregistry() {
        return getElement("createregistry").getTextContent();
    }

    public void setCreateregistry(String value) {
        getElement("createregistry").setTextContent(value);
    }
     //endregion

    //region Registryaddress
    public String getRegistryaddress() {
        return getElement("registryaddress").getTextContent();
    }

    public void setRegistryaddress(String value) {
        getElement("registryaddress").setTextContent(value);
    }
     //endregion

    //region Registryport
    public int getRegistryport() {
        return Integer.parseInt(getElement("registryport").getTextContent());
    }

    public void setRegistryport(int value) {
        getElement("registryport").setTextContent(String.valueOf(value));
    }
    //endregion

    //region Policypath
    public String getPolicypath() {
        return getElement("policypath").getTextContent();
    }

    public void setPolicypath(String value) {
        getElement("policypath").setTextContent(value);
    }
     //endregion

    //region Usecodebaseonly
    public String getUsecodebaseonly() {
        return getElement("usecodebaseonly").getTextContent();
    }

    public void setUsecodebaseonly(String value) {
        getElement("usecodebaseonly").setTextContent(value);
    }
     //endregion

    //region Classprovider
    public String getClassprovider() {
        return getElement("classprovider").getTextContent();
    }

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