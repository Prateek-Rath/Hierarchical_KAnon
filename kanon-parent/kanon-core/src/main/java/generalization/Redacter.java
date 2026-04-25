package generalization;

import org.w3c.dom.Element;

import interfaces.LevelManager;

public class Redacter implements LevelManager
{
    public boolean isEqual(Element root1, Element root2)
    {
        String content1 = root1.getTextContent();
        String content2 = root2.getTextContent();

        return content1.equals(content2);
    }  
    
    public void generalize(Element root)
    {
        root.setTextContent("*****");
    }
}
