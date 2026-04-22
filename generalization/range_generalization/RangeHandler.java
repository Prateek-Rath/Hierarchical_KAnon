package generalization.range_generalization;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import interfaces.AttributeHandler;
import interfaces.LevelManager;

public class RangeHandler extends AttributeHandler
{
    private List<LevelManager> levelManagers;

    public RangeHandler(int maxLevel)
    {
        this.maxLevel = maxLevel;
        this.levelManagers = new ArrayList<LevelManager>();
    }

    public void addLevelManager(int level, LevelManager levelManager)
    {
        levelManagers.add(level, levelManager);
    }

    public LevelManager getLevelManager(int level)
    {
        if (level >= levelManagers.size())
        {
            System.err.println("Cannot get level manager for level " + level);
            return null;
        }

        return levelManagers.get(level);
    }

    public Element getGeneralized(Element root, int level)
    {
        if (level > maxLevel || level >= levelManagers.size())
        {
            System.err.println("Cannot generalize attribute to level " + level);
            return null;
        }

        Element generalized = root;
        
        LevelManager levelManager = getLevelManager(level);
        generalized = levelManager.getGeneralized(generalized);

        return generalized;
    }
}
