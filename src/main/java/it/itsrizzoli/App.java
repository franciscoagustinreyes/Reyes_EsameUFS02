package it.itsrizzoli;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App
{
    public static void main( String[] args ) {
        //start server
        HTTPServer server = new HTTPServer();
        server.startServer(8080);
    }
}