package custom.AgeHandler;

import org.w3c.dom.Element;
import interfaces.LevelManager;

public class AgeManager3 implements LevelManager 
{
    private static AgeManager3 ageManager3 = null;

    private AgeManager3()
    {

    }

    public static AgeManager3 getInstance()
    {
        if (ageManager3 == null)
            ageManager3 = new AgeManager3();

        return ageManager3;
    }

    public boolean isEqual(Element root1, Element root2)
    {
        String category1 = root1.getTextContent();
        String category2 = root2.getTextContent();

        return category1.equals(category2);
    }  
    
    public void generalize(Element root)
    {
        root.setTextContent("*****");
    }
}
