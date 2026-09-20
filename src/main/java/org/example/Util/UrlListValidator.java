package org.example.Util;

public class UrlListValidator {
    
    public boolean isListLinkValid(String link) {
        if (link == null) return false;

        String[] linkSplitted = link.split("/");
        if (linkSplitted.length < 5) return false;
        return linkSplitted[4].equals("list");
    }

}