package it.itsrizzoli;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        //Server HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        //Gestore delle richieste
        server.createContext("/", new PizzaService());

        //Avvia il server
        server.start();
        System.out.println("Server started on port 8081");
    }
}