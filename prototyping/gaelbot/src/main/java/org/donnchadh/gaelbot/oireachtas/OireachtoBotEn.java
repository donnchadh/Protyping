package org.donnchadh.gaelbot.oireachtas;

public class OireachtoBotEn extends OireachtoBot {
    public OireachtoBotEn() {
        super("http://acts.oireachtas.ie/en.toc.decade.html", "en", 10000);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new OireachtoBotEn().run();
    }

}
