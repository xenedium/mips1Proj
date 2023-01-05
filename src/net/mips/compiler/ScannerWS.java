package net.mips.compiler;

import java.util.ArrayList;

public class ScannerWS extends Scanner {

    private ArrayList<Symboles> tableSymb;
    private int placeSymb;
    private int offset;

    public ScannerWS(String filePath) throws ErreurCompilation {
        super(filePath);
        this.tableSymb = new ArrayList<Symboles>();
        this.offset = -1;
    }
    public void initMotsCles() {
        super.initMotsCles();
    }
    public Tokens codageLex(String mot) {
        return super.codageLex(mot);
    }
    public void entrerSymb(ClasseIdf classe) {
        var newSymb = new Symboles(this.getSymbCour().getToken(), this.getSymbCour().getNom(), classe);
        this.tableSymb.add(newSymb);
        // Note: newSymb is a mutable ref, if the change occurs the change will be applied also in the array
        if (classe == ClasseIdf.CONSTANTE || classe == ClasseIdf.VARIABLE) newSymb.setAdresse(++offset);
    }
    public void chercherSymb() {
        for (var i=0;i < this.tableSymb.size(); i++) {
            if (this.tableSymb.get(i).getToken().equals(this.getSymbCour().getToken())
                    && this.tableSymb.get(i).getNom().equals(this.getSymbCour().getNom()))
            {
                this.placeSymb = i;
                return;
            }
        }
        this.placeSymb = -1;
    }
    public int getPlaceSymb() {
        return placeSymb;
    }
    public ArrayList<Symboles> getTableSymb() {
        return this.tableSymb;
    }
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
