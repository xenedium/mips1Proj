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
            var rChar = this.fluxSour.read();
            this.carCour = (char) (rChar == -1 ? EOF : rChar);
        }
        catch (Exception e) {
            this.carCour = (char) EOF;
        }
    }
    public void lireNombre() {
        StringBuilder input = new StringBuilder();

        while (Character.isDigit(this.carCour)) {
            input.append(this.carCour);
            this.lireCar();
        }

        this.symbCour = new Symboles(Tokens.NUM_TOKEN, input.toString());

    }
    public void lireMot() {
        StringBuilder input = new StringBuilder();

        while (Character.isLetter(this.carCour) || Character.isDigit(this.carCour)) {
            input.append(this.carCour);
            this.lireCar();
        }

        this.symbCour = new Symboles(this.codageLex(input.toString()), input.toString());
    }
    public void symbSuiv() throws ErreurLexicale {
        boolean read_next = true;
        while (this.carCour == ' ' || this.carCour == '\t' || this.carCour == '\n') this.lireCar();


        if (Character.isLetter(this.carCour)) {
            this.lireMot();
            read_next = false;
        }
        else if (Character.isDigit(this.carCour)) {
            this.lireNombre();
            read_next = false;
        }
        else switch (this.carCour) {
                case '+' -> this.symbCour = new Symboles(Tokens.PLUS_TOKEN, "+");
                case '-' -> this.symbCour = new Symboles(Tokens.MOINS_TOKEN, "-");
                case '*' -> this.symbCour = new Symboles(Tokens.MUL_TOKEN, "*");
                case '/' -> this.symbCour = new Symboles(Tokens.DIV_TOKEN, "/");
                case '<' -> {
                    this.lireCar();
                    if (this.carCour == '=') this.symbCour = new Symboles(Tokens.INFEG_TOKEN, "<=");
                    else if (this.carCour == '>') this.symbCour = new Symboles(Tokens.DIFF_TOKEN, "<>");
                    else {
                        this.symbCour = new Symboles(Tokens.INF_TOKEN, "<");
                        read_next = false;
                    }
                }
                case '>' -> {
                    this.lireCar();
                    if (this.carCour == '=') this.symbCour = new Symboles(Tokens.SUPEG_TOKEN, ">=");
                    else {
                        this.symbCour = new Symboles(Tokens.SUP_TOKEN, ">");
                        read_next = false;
                    }
                }
                case '=' -> this.symbCour = new Symboles(Tokens.EG_TOKEN, "=");
                case ';' -> this.symbCour = new Symboles(Tokens.PVIR_TOKEN, ";");
                case ',' -> this.symbCour = new Symboles(Tokens.VIR_TOKEN, ",");
                case '(' -> this.symbCour = new Symboles(Tokens.PARG_TOKEN, "(");
                case ')' -> this.symbCour = new Symboles(Tokens.PARD_TOKEN, ")");
                case '.' -> this.symbCour = new Symboles(Tokens.PNT_TOKEN, ".");
                case ':' -> {
                    this.lireCar();
                    if (this.carCour == '=') this.symbCour = new Symboles(Tokens.AFFEC_TOKEN, ":=");
                    else this.symbCour = new Symboles(Tokens.ERR_TOKEN, this.carCour + "");
                }
                default -> this.symbCour = new Symboles(Tokens.ERR_TOKEN, this.carCour + "");
            }
        if (this.symbCour.getToken() == Tokens.ERR_TOKEN) throw new ErreurLexicale(CodesErr.CAR_INC_ERR);
        if (read_next) this.lireCar();
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
