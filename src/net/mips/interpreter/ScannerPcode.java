package net.mips.interpreter;

import java.io.IOException;
import java.io.InputStream;

public class ScannerPcode {
    private Symboles symbCour;
    private char carCour;
    private InputStream fluxSour;
    private final int EOF = 0;
    public ScannerPcode(InputStream in) {
        this.fluxSour = in;
    }

    public Mnemonique codageLex(String inst) {
        // Alternative option: No need for initMotCles, just resolve the Mne from the string
        // Note that EOF and NUM are not supposed to be valid PCODE instructions so we throw
        try {
            var mne = Mnemonique.valueOf(inst.toUpperCase());
            if (mne == Mnemonique.NUM) throw new IllegalArgumentException();
            return mne;
        }
        catch (IllegalArgumentException err) {
            throw new RuntimeException("Invalid PCODE !");
        }
    }
    public void lireCar() {
        try {
            var rChar = this.fluxSour.read();
            this.carCour = (char) (rChar == -1 ? EOF : rChar);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read from PCODE stream !");
        }

    }
    public void lireNombre() {
        StringBuilder input = new StringBuilder();
        while (Character.isDigit(this.carCour)) {
            input.append(this.carCour);
            this.lireCar();
        }
        this.symbCour = new Symboles(Mnemonique.NUM, input.toString());
    }
    public void lireInst() {
        StringBuilder input = new StringBuilder();
        while (Character.isLetter(this.carCour)) {
            input.append(this.carCour);
            this.lireCar();
        }
        this.symbCour = new Symboles(this.codageLex(input.toString()), input.toString());
    }
    public void symbSuiv() {
        while (Character.isWhitespace(this.carCour)) this.lireCar();
        if (Character.isLetter(this.carCour)) this.lireInst();
        else if (Character.isDigit(this.carCour)) this.lireNombre();
    }

    public char getCarCour() {
        return carCour;
    }
    public Symboles getSymbCour() {
        return symbCour;
    }
    public void setFluxSour(InputStream fluxSour) {
        this.fluxSour = fluxSour;
    }

}
