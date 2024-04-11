package it.itsrizzoli;

public class Pizza {
    private String nome;
    private String[] ingredienti = new String[5];
    private double prezzo;

    public Pizza(String nome, String[] ingredienti, double prezzo){
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.prezzo = prezzo;
    }

    //Getters
    public String getNome() {
        return nome;
    }

    public String[] getIngredienti() {
        return ingredienti;
    }

    public double getPrezzo() {
        return prezzo;
    }

    //Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIngredienti(String[] ingredienti) {
        this.ingredienti = ingredienti;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}