import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

// Assuming this is your interface. Remove if already imported from your package.
interface LevelManager {
    // String applyRule(String input); 
}

public class ReflectionTest {

    public static void main(String[] args) {
        // HashMap to store XPath as Key and LevelManager instance as Value
        Map<String, LevelManager> levelManagerMap = new HashMap<>();

        try {
            // 1. Load and parse the XML file
            File xmlFile = new File("rules.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // 2. Fetch all <subtree> elements
            NodeList subtreeList = doc.getElementsByTagName("subtree");

            for (int i = 0; i < subtreeList.getLength(); i++) {
                Node node = subtreeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element subtreeElement = (Element) node;
                    String xpath = subtreeElement.getAttribute("xpath");

                    // 3. Check if this subtree has a <levelManager> child
                    NodeList managerList = subtreeElement.getElementsByTagName("levelManager");
                    
                    if (managerList.getLength() > 0) {
                        // Extract the class path string
                        String className = managerList.item(0).getTextContent().trim();

                        try {
                            // 4. Dynamically load the class using Reflection
                            Class<?> clazz = Class.forName(className);
                            
                            // 5. Instantiate the class (Requires a no-argument constructor)
                            Object instance = clazz.getDeclaredConstructor().newInstance();
                            
                            // 6. Cast to LevelManager interface and put in HashMap
                            if (instance instanceof LevelManager) {
                                levelManagerMap.put(xpath, (LevelManager) instance);
                                System.out.println("Successfully loaded: " + className + " for XPath: " + xpath);
                                String generalizedValue = ((AgeManager) instance).manage("28");
                                System.out.println("Generalized value for " + xpath + ": " + generalizedValue);
                            } else {
                                System.err.println("Class " + className + " does not implement LevelManager.");
                            }

                        } catch (ClassNotFoundException e) {
                            System.err.println("Class not found in classpath: " + className);
                        } catch (Exception e) {
                            System.err.println("Failed to instantiate class: " + className + ". Error: " + e.getMessage());
                        }
                    }
                }
            }

            System.out.println("\nTotal LevelManagers loaded: " + levelManagerMap.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}