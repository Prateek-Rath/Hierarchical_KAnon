package generalization.range_generalization;

import org.w3c.dom.Element;

import interfaces.LevelManager;

public class RangeLevelManager implements LevelManager
{
    private int bucketSize;
    private int startValue;

    public RangeLevelManager(int bucketSize, int startValue)
    {
        this.bucketSize = bucketSize;
        this.startValue = startValue;
    }

    public boolean isEqual(Element root1, Element root2)
    {
        String content1 = root1.getTextContent();
        String content2 = root2.getTextContent();

        return content1.equals(content2);
    }  
    
    public void generalize(Element root)
    {
        String content = root.getTextContent();
        int value = Integer.parseInt(content);

        int bucketIndex = Math.floorDiv(value - startValue, bucketSize);
        int lower = startValue + bucketIndex * bucketSize;
        int higher = lower + bucketSize - 1;

        root.setTextContent(lower + "-" + higher);
    }
}
