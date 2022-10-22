package net.mips.compiler;

public class Symboles {
    private Tokens token;
    private String nom;

    public Symboles(Tokens token, String nom) {
        this.token = token;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
    public Tokens getToken() {
        return token;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setToken(Tokens token) {
        this.token = token;
    }
}
