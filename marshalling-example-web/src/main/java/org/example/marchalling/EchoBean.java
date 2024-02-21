package org.example.marchalling;

import javax.ejb.Singleton;

@Singleton
public class EchoBean {
    public String echo(String s) {
        return "echo: " + s;
    }
}
