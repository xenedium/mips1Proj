package net.mips.compiler;

import java.util.ArrayList;

public class ScannerWS extends Scanner {

    private ArrayList<Symboles> tableSymb;
    private int placeSymb;

    public ScannerWS(String filePath) throws ErreurCompilation {
        super(filePath);
        this.tableSymb = new ArrayList<Symboles>();
    }
    public void initMotsCles() {
        super.initMotsCles();
    }
    public Tokens codageLex(String mot) {
        return super.codageLex(mot);
    }
    public void entrerSymb(ClasseIdf classe) {
        this.tableSymb.add(new Symboles(this.getSymbCour().getToken(), this.getSymbCour().getNom(), classe));
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
}
