package interfaces;

import org.w3c.dom.Element;

public interface LevelManager
{
    public boolean isEqual(Element root1, Element root2);
    public void generalize(Element root);
    public Element getGeneralized(Element root);
}