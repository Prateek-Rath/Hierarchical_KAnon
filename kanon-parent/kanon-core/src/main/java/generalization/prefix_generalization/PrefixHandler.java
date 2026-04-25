package generalization.prefix_generalization;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import interfaces.AttributeHandler;
import interfaces.LevelManager;

public class PrefixHandler extends AttributeHandler
{
    private List<LevelManager> levelManagers;

    public PrefixHandler(int maxLevel)
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

        Element generalized = (Element) root.cloneNode(true);
        
        LevelManager levelManager = getLevelManager(level);
        levelManager.generalize(generalized);

        return generalized;
    }
}
