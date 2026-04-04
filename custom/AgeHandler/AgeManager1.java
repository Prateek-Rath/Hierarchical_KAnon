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
        return true;
    }  
    
    public void generalize(Element root)
    {
        
    }
}
