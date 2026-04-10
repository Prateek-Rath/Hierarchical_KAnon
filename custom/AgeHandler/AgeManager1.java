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
        String range1 = root1.getTextContent();
        String range2 = root2.getTextContent();

        return range1.equals(range2);
    }  
    
    public void generalize(Element root)
    {
        int age = Integer.parseInt(root.getTextContent());
        int lower = (age / 10) * 10;
        int higher = (age / 10 + 1) * 10 - 1;

        root.setTextContent(lower + "-" + higher);
    }

    public Element getGeneralized(Element root)
    {
        String content = root.getTextContent();
        int age = Integer.parseInt(content);
        int lower = (age / 10) * 10;
        int higher = (age / 10 + 1) * 10 - 1;

        root.setTextContent(lower + "-" + higher);

        return root;
    }
}
