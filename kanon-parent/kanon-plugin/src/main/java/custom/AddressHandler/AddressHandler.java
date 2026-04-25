package custom.AddressHandler;

import org.w3c.dom.Element;

import interfaces.AttributeHandler;
import interfaces.LevelManager;

public class AddressHandler extends AttributeHandler 
{
    public AddressHandler()
    {
        this.maxLevel = 5;
    }

    public LevelManager getLevelManager(int level)
    {
        try
        {
            Class<?> clazz = Class.forName("custom.AddressHandler.AddressManager" + level);

            java.lang.reflect.Method method = clazz.getMethod("getInstance");
            Object instance = method.invoke(null);

            return (LevelManager)instance;
        }

        catch (Exception e)
        {
            System.out.println("Exception occurred: " + e.getMessage());
            return null;
        }
    }
    
    public Element getGeneralized(Element root, int level)
    {
        if (level > maxLevel)
        {
            System.err.println("Cannot generalize address attribute to level " + level);
            return null;
        }

        Element generalized = (Element) root.cloneNode(true);

        for (int i = 0; i <= level; ++i)
        {
            LevelManager levelManager = getLevelManager(i);
            levelManager.generalize(generalized);
        }

        return generalized;
    }
}
