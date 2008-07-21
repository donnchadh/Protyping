package org.donnchadh.gaelbot.crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public abstract class AbstractBot implements Runnable {

    public static final String UTF_8 = "UTF-8";

    public static String buildGoogleUrl(String... focail) throws UnsupportedEncodingException {
        String newLink = "http://www.google.ie/search?q=";
        for (int i = 0; i < focail.length; i++) {
            if (i != 0) {
                newLink += "+";
            }
            newLink += URLEncoder.encode(focail[i], UTF_8);
        }
        newLink += "&num=100";
        return newLink;
    }

}
