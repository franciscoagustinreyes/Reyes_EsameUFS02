package it.itsrizzoli;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PizzaService implements HttpHandler {

    private List<Pizza> listaPizza;

    public PizzaService() {
        //Inizializzare pizze
        this.listaPizza = new ArrayList<>();
        listaPizza.add(new Pizza("Margherita", new String[]{"Tomato", "Mozzarella"}, 8.50));
        listaPizza.add(new Pizza("Pepperoni", new String[]{"Tomato", "Pepperoni", "Cheese"}, 9.50));
        listaPizza.add(new Pizza("Vegetariana", new String[]{"Tomato", "Mushrooms", "Peppers"}, 7.50));
    }

    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("GET")) {
            String command = exchange.getRequestURI().getPath();

            if (command.equals("/with_tomato")) {
                response = getPizzaIngredienti("Tomato");
            } else if (command.equals("/with_cheese")) {
                response = getPizzaIngredienti("Cheese");
            } else if (command.equals("/sorted_by_price")) {
                response = sortPrice();
            } else {
                response = "Invalid command";
            }
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getPizzaIngredienti(String ingredienti) {
        StringBuilder result = new StringBuilder();
        for (Pizza pizza : listaPizza) {
            if (containsIngredient(pizza, ingredienti)) {
                result.append(pizza.getNome()).append("\n");
            }
        }
        return result.toString();
    }

    private boolean containsIngredient(Pizza pizza, String ingredient) {
        for (String ingredienti : pizza.getIngredienti()) {
            if (ingredienti.equalsIgnoreCase(ingredient)) {
                return true;
            }
        }
        return false;
    }

    private String sortPrice() {
        listaPizza.sort(Comparator.comparingDouble(Pizza::getPrezzo));

        StringBuilder result = new StringBuilder();
        for (Pizza pizza : listaPizza) {
            result.append(pizza.getNome()).append(" - ").append(pizza.getPrezzo()).append("\n");
        }
        return result.toString();
    }
}