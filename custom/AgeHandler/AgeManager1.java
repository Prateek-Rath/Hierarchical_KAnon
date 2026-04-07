package custom.AgeHandler;

import org.w3c.dom.Element;
import interfaces.LevelManager;

public class AgeManager1 implements LevelManager 
{
    private static AgeManager1 ageManager1 = null;

    private AgeManager1()
    {

    }

    public static AgeManager1 getInstance()
    {
        if (ageManager1 == null)
            ageManager1 = new AgeManager1();

        return ageManager1;
    }

    public boolean isEqual(Element root1, Element root2)
    {
        String bin1 = root1.getTextContent();
        String bin2 = root2.getTextContent();

        return bin1.equals(bin2);
    }  
    
    public void generalize(Element root)
    {
        String content = root.getTextContent();
        String[] splitted = content.split("-");
        int higher = Integer.parseInt(splitted[1]);

        if (higher <= 19)
            root.setTextContent("Young");

        else if (higher <= 59)
            root.setTextContent("Adult");

        else
            root.setTextContent("Senior");
    }
}
