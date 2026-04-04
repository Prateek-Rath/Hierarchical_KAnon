import custom.AgeHandler.AgeHandler;
import interfaces.LevelManager;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class PrivacyEngine 
{
    public static void main(String args[])
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            AgeHandler ageHandler = new AgeHandler();

            Element age1 = doc.createElement("age");
            age1.setTextContent("18");
            
            Element age2 = doc.createElement("age");
            age2.setTextContent("65");

            LevelManager ageManager0 = ageHandler.getLevelManager(0);
            System.out.println(ageManager0.isEqual(age1, age2));

            ageManager0.generalize(age1);
            ageManager0.generalize(age2);

            System.out.println("Modified age1: " + age1.getTextContent());
            System.out.println("Modified age2: " + age2.getTextContent());
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }    
}
