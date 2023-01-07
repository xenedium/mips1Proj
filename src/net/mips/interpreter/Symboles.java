package net.mips.interpreter;

public class Symboles {
    private String nom;
    private Mnemonique token;
    public Symboles(Mnemonique token, String nom){
        this.nom = nom;
        this.token = token;
    }
    public Mnemonique getToken() {
        return token;
    }
    public String getNom() {
        return nom;
    }
    public void setToken(Mnemonique token) {
        this.token = token;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
}
