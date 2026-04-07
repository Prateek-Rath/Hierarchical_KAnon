package custom.AgeHandler;

import org.w3c.dom.Element;
import interfaces.LevelManager;

public class AgeManager0 implements LevelManager 
{
    private static AgeManager0 ageManager0 = null;

    private AgeManager0()
    {

    }

    public static AgeManager0 getInstance()
    {
        if (ageManager0 == null)
            ageManager0 = new AgeManager0();

        return ageManager0;
    }

    public boolean isEqual(Element root1, Element root2)
    {
        String age1 = root1.getTextContent();
        String age2 = root2.getTextContent();

        return age1.equals(age2);
    }  
    
    public void generalize(Element root)
    {
        int age = Integer.parseInt(root.getTextContent());
        int lower = (age / 10) * 10;
        int higher = (age / 10 + 1) * 10 - 1;

        root.setTextContent(lower + "-" + higher);
    }
}
