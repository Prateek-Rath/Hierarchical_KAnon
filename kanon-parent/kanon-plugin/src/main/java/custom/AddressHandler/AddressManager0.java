package custom.AddressHandler;

import org.w3c.dom.Element;

import custom.AddressHandler.helper.XMLHelper;
import interfaces.LevelManager;

public class AddressManager0 implements LevelManager 
{
    private static AddressManager0 addressManager0 = null;
    private XMLHelper xmlHelper;

    private AddressManager0()
    {
        this.xmlHelper = new XMLHelper();
    }

    public static AddressManager0 getInstance()
    {
        if (addressManager0 == null)
            addressManager0 = new AddressManager0();

        return addressManager0;
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
        return;
    }
}
