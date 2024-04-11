package it.itsrizzoli;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        // Crea un server HTTP sulla porta 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Assegna il gestore delle richieste
        server.createContext("/", new PizzaService());

        // Avvia il server
        server.start();
        System.out.println("Server started on port 8080");
    }
}