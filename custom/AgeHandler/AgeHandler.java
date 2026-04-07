package custom.AgeHandler;

import interfaces.AttributeHandler;
import interfaces.LevelManager;

public class AgeHandler extends AttributeHandler 
{
    public AgeHandler(){
        this.maxLevel = 2;
    }
    public LevelManager getLevelManager(int level)
    {
        try
        {
            Class<?> clazz = Class.forName("custom.AgeHandler.AgeManager" + level);

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
}
