package custom.AgeHandler;

import org.w3c.dom.Node;
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

    public boolean isEqual(Node root1, Node root2)
    {
        return true;
    }  
    
    public Node generalize(Node root)
    {
        return null;
    }
}
