import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import interfaces.AttributeHandler;

public class Main {

    // Map XPath -> AttributeHandler
    Map<String, AttributeHandler> handlerMap = new HashMap<>();
    Map<String, Integer> currentGenLevel = new HashMap<>(); 

    public void load(String ruleFilePath) {        
        try {
            File xmlFile = new File(ruleFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList quasiList = doc.getElementsByTagNameNS(
                "http://www.xpriv.com/rules", "quasi"
            );

            if (quasiList.getLength() == 0) {
                System.err.println("No <quasi> section found");
                return;
            }

            Element quasiElement = (Element) quasiList.item(0);

            NodeList subtreeList = quasiElement.getElementsByTagNameNS(
                "http://www.xpriv.com/rules", "subtree"
            );

            for (int i = 0; i < subtreeList.getLength(); i++) {

                Node node = subtreeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element subtreeElement = (Element) node;
                    String xpath = subtreeElement.getAttribute("xpath");
                    currentGenLevel.put(xpath, 0);
                    Node attributeHandler = subtreeElement.getElementsByTagNameNS("http://www.xpriv.com/rules", "AttributeHandler").item(0);

                    String className = attributeHandler.getTextContent().trim();

                    try {
                        Class<?> clazz = Class.forName(className);
                        Object instance = clazz.getDeclaredConstructor().newInstance();

                        // ✅ Use AttributeHandler instead of LevelManager
                        if (instance instanceof AttributeHandler) {

                            AttributeHandler handler = (AttributeHandler) instance;

                            handlerMap.put(xpath, handler);

                            System.out.println("Loaded: " + className + " for XPath: " + xpath);

                        } else {
                            System.err.println("Class " + className + " does not implement AttributeHandler.");
                        }

                    } catch (ClassNotFoundException e) {
                        System.err.println("Class not found: " + className);
                    } catch (Exception e) {
                        System.err.println("Instantiation failed for " + className + ": " + e.getMessage());
                    }
                    
                }
            }

            System.out.println("\nTotal handlers loaded: " + handlerMap.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

    }
}