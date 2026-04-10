package interfaces;

import org.w3c.dom.Element;

public abstract class AttributeHandler 
{
    public int maxLevel;
    public abstract LevelManager getLevelManager(int level);
    public abstract Element getGeneralized(Element root, int level);
}
