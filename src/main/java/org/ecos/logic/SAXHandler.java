package org.ecos.logic;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
    boolean isCountry = false;
    boolean isLong = false;
    private boolean hasCode;
    private boolean isConstructor;
    private boolean isName;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("country")){
            isCountry = true;
        }
        if (qName.equals("long")){
            isLong = true;
        }

        if (qName.equals("Circuit")){
            System.out.println("code: " + attributes.getValue("code"));
        }
        if (qName.equals("Constructor")){
            this.isConstructor = true;
        }
        if (qName.equals("name")){
            this.isName = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isCountry){
            String name = new String(ch, start, length);
            System.out.println("Country: " +name);
            isCountry = false;
        }
        if (isLong){
            String name = new String(ch, start, length);
            System.out.println("Long: " +name);
            isLong = false;
        }


        if (this.isName && this.isConstructor) {
            String name = new String(ch, start, length);
            System.out.println("Escudería: " + name);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("country")){
            isCountry = false;
        }
        if (qName.equals("long")){
            isLong = false;
        }

        if (qName.equals("Constructor")){
            this.isConstructor = false;
        }
        if (qName.equals("name")){
            this.isName = false;
        }

    }
}
