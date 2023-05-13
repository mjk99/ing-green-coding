package org.example;

import org.example.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //System.setProperty("sun.net.httpserver.nodelay", "true");
        new Server().start();
    }
}