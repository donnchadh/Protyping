package org.donnchadh.gaelbot;

public class OireachtoBotGa extends OireachtoBot {
    public OireachtoBotGa() {
        super("http://achtanna.oireachtas.ie/ga.toc.decade.html", "ga", 18000);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new OireachtoBotGa().run();
    }

}
