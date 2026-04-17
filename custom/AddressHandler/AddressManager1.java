package custom.AddressHandler;

import org.w3c.dom.Element;

import custom.AddressHandler.helper.XMLHelper;
import interfaces.LevelManager;

public class AddressManager1 implements LevelManager 
{
    private static AddressManager1 instance;
    private XMLHelper xmlHelper;

    private AddressManager1() 
    {
        this.xmlHelper = new XMLHelper();
    }

    public static AddressManager1 getInstance() 
    {
        if (instance == null) 
            instance = new AddressManager1();
        
        return instance;
    }

    public boolean isEqual(Element root1, Element root2) 
    {
        return xmlHelper.get(root1, "houseNumber").equals(xmlHelper.get(root2, "houseNumber")) &&
           xmlHelper.get(root1, "street").equals(xmlHelper.get(root2, "street")) &&
           xmlHelper.get(root1, "city").equals(xmlHelper.get(root2, "city")) &&
           xmlHelper.get(root1, "pincode").equals(xmlHelper.get(root2, "pincode"));
    }

    public void generalize(Element root) 
    {
        xmlHelper.set(root, "houseNumber", "*****");
    }

    public Element getGeneralized(Element root) 
    {
        generalize(root);
        return root;
    }
}
