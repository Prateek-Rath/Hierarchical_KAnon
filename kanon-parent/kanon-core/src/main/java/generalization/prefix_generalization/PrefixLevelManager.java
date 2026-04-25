package generalization.prefix_generalization;

import org.w3c.dom.Element;

import interfaces.LevelManager;

public class PrefixLevelManager implements LevelManager
{
    private int prefixLength;

    public PrefixLevelManager(int prefixLength)
    {
        this.prefixLength = prefixLength;
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

        if (content.length() <= prefixLength)
            return;

        String mask = "*".repeat(content.length() - prefixLength);
        String masked_content = content.substring(0, prefixLength) + mask;

        root.setTextContent(masked_content);
    }
}
