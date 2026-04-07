package interfaces;

public abstract class AttributeHandler 
{
    public int maxLevel;
    public abstract LevelManager getLevelManager(int level);

}
