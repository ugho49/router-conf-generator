package fr.nantes.stephan.routerconfgenerator.app;

import java.util.HashMap;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class RouterModel {

    private HashMap<String, String> attributes = new HashMap<String, String>();

    public RouterModel() {
    }

    public RouterModel(final HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(final String key, final String value) {
        attributes.put(key, value);
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public String getAttribute(final String key) {
        String str = "";

        if (attributes.containsKey(key)) {
            str = attributes.get(key);
        }

        return str;
    }

    public void setAttributes(final HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
