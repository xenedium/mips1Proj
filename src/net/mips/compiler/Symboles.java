package net.mips.compiler;

public class Symboles {
    private Tokens token;
    private String nom;
    private ClasseIdf classe;

    public Symboles(Tokens token, String nom) {
        this(token, nom, null);
    }
    public Symboles(Tokens token, String nom, ClasseIdf classe) {
        this.token = token;
        this.nom = nom;
        this.classe = classe;
    }


    public String getNom() {
        return nom;
    }
    public Tokens getToken() {
        return token;
    }
    public ClasseIdf getClasse() {
        return classe;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setToken(Tokens token) {
        this.token = token;
    }
    public void setClasse(ClasseIdf classe) {
        this.classe = classe;
    }
}
