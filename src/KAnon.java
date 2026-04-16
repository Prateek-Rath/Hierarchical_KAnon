import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

import interfaces.AttributeHandler;
import interfaces.LevelManager;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class KAnon 
{
    // Map XPath -> AttributeHandler
    private Map<String, AttributeHandler> handlerMap;
    // represents the current gen lvl as (xpath1 -> genlvl1, xpath2 -> genlvl2, ...)
    private Map<String, Integer> currentGenLevel; 
    // helps to travel the generalization lattice level-wise using BFS
    private Queue<Map<String, Integer>> q;
    // The dataset loaded from the XML file, represented as a list of records (Elements)
    private List<Element> dataset;
    // k value
    private int kAnonymity;

    public KAnon()
    {
        this.handlerMap = new HashMap<>();
        this.currentGenLevel = new HashMap<>();
        this.dataset = new ArrayList<>();
        this.q = new ArrayDeque<Map<String, Integer>>();
    }

    public void loadDataset(String dataFilePath, String entityXPath) {
        try {
            File xmlFile = new File(dataFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            
            // Match the namespace settings of your rules
            dbFactory.setNamespaceAware(true); 
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            XPath xpathProcessor = XPathFactory.newInstance().newXPath();
            
            // Get all records (e.g., all <person> elements)
            NodeList nodes = (NodeList) xpathProcessor.evaluate(
                entityXPath, 
                doc, 
                XPathConstants.NODESET
            );

            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    dataset.add((Element) nodes.item(i));
                }
            }

            System.out.println("Dataset loaded successfully. Total records: " + dataset.size());

        } catch (Exception e) {
            System.err.println("Error loading dataset: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void load(String ruleFilePath, String datasetFilePath) {        
        try {
            File xmlFile = new File(ruleFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            /* Extract the value of k in k-anonymity */
            NodeList kList = doc.getElementsByTagNameNS(
                "http://www.xpriv.com/rules", "k_anonymity"
            );

            if (kList.getLength() > 0) 
            {
                Element kElement = (Element) kList.item(0);
                String kValueStr = kElement.getTextContent().trim();

                int kValue = Integer.parseInt(kValueStr);

                System.out.println("k-anonymity value: " + kValue);

                this.kAnonymity = kValue;
            } 
            
            else 
            {
                System.err.println("No <k_anonymity> tag found");
                System.exit(1);
            }
            
            /* Get all quasi attributes(nodes here) and load their handlers */ 
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
            
            /* Get the entity level i.e. the level at which k-anon is required */
            NodeList entityList = doc.getElementsByTagNameNS("http://www.xpriv.com/rules", "entity_level");
            
            if (entityList.getLength() > 0) {
                Element entityElement = (Element) entityList.item(0);
                String entityXPath = entityElement.getAttribute("xpath");
                
                // Now call the loader (assuming your data file is named data.xml)
                loadDataset(datasetFilePath, entityXPath);
            }

            System.out.println("\nTotal handlers loaded: " + handlerMap.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* K-anonyize the data by doing bfs on the queue */
    public void k_anonymize()
    {  
        q.add(currentGenLevel);
        int x = 0;
        while(!q.isEmpty()){
            Map<String, Integer> curr = q.poll();

            if(check_Kanon(curr)){
                writeAnonymizedDataset("./data/kanon_output.xml", curr);
                break;
            }

            for(Map.Entry<String, Integer> attr_lvl : curr.entrySet()){
                Map<String, Integer> nextel = new HashMap<>(curr);
                //if(attr_lvl.getValue() < maxLevel)
                // AttributeHandler handler = handlerMap.get(attr_lvl.getKey()); not required as of now
                
                if(attr_lvl.getValue() < this.kAnonymity){
                    System.out.println(attr_lvl.getKey());
                    nextel.put(attr_lvl.getKey(), attr_lvl.getValue() + 1);
                    q.add(nextel);
                }
            }
            ++x;
        }
    }

    public boolean check_Kanon(Map<String, Integer> curr) 
    {
        // System.out.println("check_Kanon called.");

        // List to hold our groups (Equivalence Classes)
        // Each inner list contains records that are indistinguishable
        List<List<Element>> equivalenceClasses = new ArrayList<>();

        for (Element record : dataset) {
            boolean foundGroup = false;

            for (java.util.List<Element> group : equivalenceClasses) {
                // Compare the current record with the first member of an existing group
                Element representative = group.get(0);
                
                if (isRecordEquivalent(record, representative, curr)) {
                    group.add(record);
                    foundGroup = true;
                    break;
                }
            }

            // If no matching group is found, create a new equivalence class
            if (!foundGroup) {
                java.util.List<Element> newGroup = new java.util.ArrayList<>();
                newGroup.add(record);
                equivalenceClasses.add(newGroup);
            }
        }

        // Check if every equivalence class meets the k-threshold
        for (java.util.List<Element> group : equivalenceClasses) {

            // System.out.println("Group size = " + group.size());

            if (group.size() < kAnonymity) {
                return false; // Privacy violation: a group is smaller than k
            }
        }

        return true; // Success: All groups are at least size k
    }

    /**
     * Helper to check if two records are equivalent across all quasi-identifiers
     * based on the current generalization levels.
     */
    private boolean isRecordEquivalent(Element r1, Element r2, Map<String, Integer> levels) {
        for (Map.Entry<String, Integer> entry : levels.entrySet()) {
            String xpath = entry.getKey();
            int level = entry.getValue();

            AttributeHandler handler = handlerMap.get(xpath);
            LevelManager manager = handler.getLevelManager(level);

            // We assume your XML structure allows finding the specific node via XPath
            // For this example, we use a simplified node lookup
            Element node1 = findNodeByXPath(r1, xpath);
            Element node2 = findNodeByXPath(r2, xpath);

            Element generalizedNode1 = handler.getGeneralized(node1, level);
            Element generalizedNode2 = handler.getGeneralized(node2, level);

            if (!manager.isEqual(generalizedNode1, generalizedNode2)) {
                return false; // If even one attribute differs, they aren't equivalent
            }
        }
        return true;
    }

    /**
     * Helper to find a specific sub-element within a record based on an XPath.
     * This evaluates the XPath relative to the provided record element.
     */
    private Element findNodeByXPath(Element record, String xpathExpression) {
        try {
            XPath xpathProcessor = XPathFactory.newInstance().newXPath();
            
            // Evaluate the expression relative to the 'record' node
            Node result = (Node) xpathProcessor.evaluate(
                xpathExpression, 
                record, 
                XPathConstants.NODE
            );

            if (result != null && result.getNodeType() == Node.ELEMENT_NODE) {
                return (Element) result;
            }
        } catch (Exception e) {
            System.err.println("Error evaluating XPath '" + xpathExpression + "': " + e.getMessage());
        }
        return null;
    }

    /* anonymize the dataset as per the current levels and write it to outputPath */
    public void writeAnonymizedDataset(String outputPath, Map<String, Integer> levels) {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Create a new empty document
        Document newDoc = builder.newDocument();

        // Root element
        Element root = newDoc.createElement("dataset");
        newDoc.appendChild(root);

        for (Element record : dataset) {
            // Deep copy original record into new document
            Node importedRecord = newDoc.importNode(record, true);
            Element newRecord = (Element) importedRecord;

            // Apply generalization for each quasi-identifier
            for (Map.Entry<String, Integer> entry : levels.entrySet()) {
                String xpath = entry.getKey();
                int level = entry.getValue();

                AttributeHandler handler = handlerMap.get(xpath);

                Element node = findNodeByXPath(newRecord, xpath);
                if (node == null) continue;

                Element generalizedNode = handler.getGeneralized(node, level);

                // Replace node content (simple approach)
                if (generalizedNode != null) {
                    node.setTextContent(generalizedNode.getTextContent());
                }
            }

            root.appendChild(newRecord);
        }

        // Write to file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File(outputPath));

        transformer.transform(source, result);

        System.out.println("Anonymized dataset written to: " + outputPath);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}