package custom.AddressHandler.helper;

import org.w3c.dom.Element;

public class XMLHelper
{    
    public Element getFirst(Element parent, String tag) 
    {
        return (Element) parent.getElementsByTagName(tag).item(0);
    }

    public void set(Element parent, String tag, String value) 
    {
        Element el = getFirst(parent, tag);
        if (el != null) el.setTextContent(value);
    }

    public String get(Element parent, String tag) 
    {
        Element el = getFirst(parent, tag);
        return (el != null) ? el.getTextContent() : "";
    }

    public String maskPincode(String pin, int stars) 
    {
        if (pin == null || pin.length() <= stars) return "*";
        return pin.substring(0, pin.length() - stars) + "*".repeat(stars);
    }
}