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
        // Inizializzare le pizze
        this.listaPizza = new ArrayList<>();
        listaPizza.add(new Pizza("Margherita            ", new String[]{"Tomato", "Cheese"}, 7.49));
        listaPizza.add(new Pizza("Bufalina              ", new String[]{"Rucola", "Cheese"}, 9.49));
        listaPizza.add(new Pizza("Diavola               ", new String[]{"Tomato", "Cheese", "Pepperoni"}, 9.99));
        listaPizza.add(new Pizza("Rustica               ", new String[]{"Cheese", "Bacon"}, 8.49));
        listaPizza.add(new Pizza("Prosciutto            ", new String[]{"Tomato", "Cheese", "Prosciutto"}, 8.99));
        listaPizza.add(new Pizza("Marinara              ", new String[]{"Tomato", "Olives", "Oregano"}, 5.99));
        listaPizza.add(new Pizza("Valdostana            ", new String[]{"Cheese", "Prosciutto"}, 7.99));
    }

    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String requestMethod = exchange.getRequestMethod();

        if (requestMethod.equalsIgnoreCase("GET")) {
            String command = exchange.getRequestURI().getPath();

            if (command.equals("/with_tomato")) {
                response = getPizzaConIngrediente("Tomato");
            } else if (command.equals("/with_cheese")) {
                response = getPizzaConIngrediente("Cheese");
            } else if (command.equals("/sorted_by_price")) {
                response = getPizzasSortedByPrice();
            } else {
                response = "Invalid command";
            }
        }
        //gestione risposta
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getPizzaConIngrediente(String ingredient) {
        StringBuilder result = new StringBuilder();
        for (Pizza pizza : listaPizza) {
            if (containsIngredient(pizza, ingredient)) {
                result.append(pizza.getNome()).append("€").append(pizza.getPrezzo()).append("\n");
                result.append("(");
                appendIngredients(result, pizza.getIngredienti());
                result.append(")");
                result.append("\n\n");
            }
        }
        return result.toString();
    }

    private boolean containsIngredient(Pizza pizza, String ingredient) {
        for (String ingred : pizza.getIngredienti()) {
            if (ingred.equalsIgnoreCase(ingredient)) {
                return true;
            }
        }
        return false;
    }

    private String getPizzasSortedByPrice() {
        listaPizza.sort(Comparator.comparingDouble(Pizza::getPrezzo));

        StringBuilder result = new StringBuilder();
        for (Pizza pizza : listaPizza) {
            result.append(pizza.getNome()).append("€").append(pizza.getPrezzo()).append("\n");
            result.append("(");
            appendIngredients(result, pizza.getIngredienti());
            result.append(")");
            result.append("\n\n");
        }
        return result.toString();
    }

    private void appendIngredients(StringBuilder builder, String[] ingredients) {
        for (int i = 0; i < ingredients.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(ingredients[i]);
        }
    }
}