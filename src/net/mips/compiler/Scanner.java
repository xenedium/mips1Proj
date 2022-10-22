package net.mips.compiler;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;


public class Scanner {
    private ArrayList<Symboles> motsCles;
    private Symboles symbCour;
    private char carCour;
    private FileReader fluxSour;

    private final int EOF = 0;


    public Scanner(String filePath) throws ErreurLexicale {
        try {
            File file = new File(filePath);
            this.fluxSour = new FileReader(file);
        }
        catch (Exception e) {
            throw new ErreurLexicale(CodesErr.FIC_VID_ERR);
        }
        this.motsCles = new ArrayList<>();
    }

    public void initMotsCles() {
        this.motsCles.add(
                new Symboles(Tokens.PROGRAM_TOKEN, "program")
        );
        this.motsCles.add(
                new Symboles(Tokens.CONST_TOKEN, "const")
        );
        this.motsCles.add(
                new Symboles(Tokens.VAR_TOKEN, "var")
        );
        this.motsCles.add(
                new Symboles(Tokens.BEGIN_TOKEN, "begin")
        );
        this.motsCles.add(
                new Symboles(Tokens.END_TOKEN, "end")
        );
        this.motsCles.add(
                new Symboles(Tokens.IF_TOKEN, "if")
        );
        this.motsCles.add(
                new Symboles(Tokens.THEN_TOKEN, "then")
        );
        this.motsCles.add(
                new Symboles(Tokens.WHILE_TOKEN, "while")
        );
        this.motsCles.add(
                new Symboles(Tokens.DO_TOKEN, "do")
        );
        this.motsCles.add(
                new Symboles(Tokens.WRITE_TOKEN, "write")
        );
        this.motsCles.add(
                new Symboles(Tokens.READ_TOKEN, "read")
        );
    }

    public Tokens codageLex(String mot) {
        for (var motCles : this.motsCles) {
            if (Objects.equals(motCles.getNom(), mot)) return motCles.getToken();
        }
        return Tokens.ID_TOKEN;
    }

    public void lireCar() {
        try {
            var rchar = this.fluxSour.read();
            this.carCour = (char) (rchar == -1 ? EOF : rchar);
        }
        catch (Exception e) {
            this.carCour = (char) EOF;
        }
    }
    public void lireNombre() {
    }
    public void lireMot() {

    }
    public void symbSuiv() {

    }


    public char getCarCour() {
        return carCour;
    }
    public void setCarCour(char carCour) {
        this.carCour = carCour;
    }

    public FileReader getFluxSour() {
        return fluxSour;
    }
    public void setFluxSour(FileReader fluxSour) {
        this.fluxSour = fluxSour;
    }

    public ArrayList<Symboles> getMotsCles() {
        return motsCles;
    }
    public void setMotsCles(ArrayList<Symboles> motsCles) {
        this.motsCles = motsCles;
    }

    public Symboles getSymbCour() {
        return symbCour;
    }
    public void setSymbCour(Symboles symbCour) {
        this.symbCour = symbCour;
    }
}
