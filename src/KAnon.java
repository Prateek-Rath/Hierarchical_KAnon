import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import interfaces.AttributeHandler;

public class KAnon {

    // Map XPath -> AttributeHandler
    private Map<String, AttributeHandler> handlerMap = new HashMap<>();
    private Map<String, Integer> currentGenLevel = new HashMap<>(); 
    private Queue<Map<String, Integer>> q = new ArrayDeque<Map<String,Integer>>();

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

    public void traverse(){
        q.add(currentGenLevel);
        while(!q.isEmpty()){
            Map<String, Integer> curr = q.poll();

            // if(my_check(curr)){
            //     //found the gen level
            //     break;
            // }

            if(check_Kanon(curr)){
                System.out.printf("Found it\n");
                break;
            }

            for(Map.Entry<String, Integer> attr_lvl : curr.entrySet()){
                Map<String, Integer> nextel = new HashMap<>(curr);
                //if(attr_lvl.getValue() < maxLevel)
                AttributeHandler handler = handlerMap.get(attr_lvl.getKey());
                if(attr_lvl.getValue() < handler.maxLevel){
                    nextel.put(attr_lvl.getKey(), attr_lvl.getValue() + 1);
                    q.add(nextel);
                }
            }
        }
    }


    public boolean check_Kanon(Map<String, Integer> curr){
        return true;
    }
}