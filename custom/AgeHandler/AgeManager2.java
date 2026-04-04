package custom.AgeHandler;

import org.w3c.dom.Element;
import interfaces.LevelManager;

public class AgeManager2 implements LevelManager 
{
    private static AgeManager2 ageManager2 = null;

    private AgeManager2()
    {

    }

    public static AgeManager2 getInstance()
    {
        if (ageManager2 == null)
            ageManager2 = new AgeManager2();

        return ageManager2;
    }

    public boolean isEqual(Element root1, Element root2)
    {
        return true;
    }  
    
    public void generalize(Element root)
    {
        
    }
}
