package net.mips.interpreter;

import java.io.InputStream;
import java.util.Arrays;

public class ParserPcode {
    private ScannerPcode scanner;
    private InterpreterPcode interpreterPcode;
    public ParserPcode(InputStream in) {
        this.scanner = new ScannerPcode(in);
        this.interpreterPcode = new InterpreterPcode();
    }
    public void testeAccept(Mnemonique mne) {
        if (this.scanner.getSymbCour().getToken() == mne) {
            if (this.scanner.getCarCour() != 0) this.scanner.symbSuiv();
        }
        else throw new RuntimeException("Unexpected token: Found "
                + this.scanner.getSymbCour().getToken()
                + "Instead of "
                + mne
        );
    }
    public void pcode() {
        Mnemonique[] ign = {Mnemonique.INT, Mnemonique.HLT};
        testeAccept(Mnemonique.INT);
        var unparsedNum = this.scanner.getSymbCour().getNom();
        testeAccept(Mnemonique.NUM);
        interpreterPcode.loadMnemonic(Mnemonique.INT, Integer.parseInt(unparsedNum));
        while (!Arrays.asList(ign).contains(this.scanner.getSymbCour().getToken()))
            this.instPcode();
        testeAccept(Mnemonique.HLT);
    }
    public void instPcode() {
        switch (this.scanner.getSymbCour().getToken()){
            case ADD -> {
                testeAccept(Mnemonique.ADD);
                interpreterPcode.loadMnemonic(Mnemonique.ADD);
            }
            case SUB -> {
                testeAccept(Mnemonique.SUB);
                interpreterPcode.loadMnemonic(Mnemonique.SUB);
            }
            case MUL -> {
                testeAccept(Mnemonique.MUL);
                interpreterPcode.loadMnemonic(Mnemonique.MUL);
            }
            case DIV -> {
                testeAccept(Mnemonique.DIV);
                interpreterPcode.loadMnemonic(Mnemonique.DIV);
            }
            case EQL -> {
                testeAccept(Mnemonique.EQL);
                interpreterPcode.loadMnemonic(Mnemonique.EQL);
            }
            case NEQ -> {
                testeAccept(Mnemonique.NEQ);
                interpreterPcode.loadMnemonic(Mnemonique.NEQ);
            }
            case GTR -> {
                testeAccept(Mnemonique.GTR);
                interpreterPcode.loadMnemonic(Mnemonique.GTR);
            }
            case LSS -> {
                testeAccept(Mnemonique.LSS);
                interpreterPcode.loadMnemonic(Mnemonique.LSS);
            }
            case GEQ -> {
                testeAccept(Mnemonique.GEQ);
                interpreterPcode.loadMnemonic(Mnemonique.GEQ);
            }
            case LEQ -> {
                testeAccept(Mnemonique.LEQ);
                interpreterPcode.loadMnemonic(Mnemonique.LEQ);
            }
            case PRN -> {
                testeAccept(Mnemonique.PRN);
                interpreterPcode.loadMnemonic(Mnemonique.PRN);
            }
            case INN -> {
                testeAccept(Mnemonique.INN);
                interpreterPcode.loadMnemonic(Mnemonique.INN);
            }
            case LDV -> {
                testeAccept(Mnemonique.LDV);
                interpreterPcode.loadMnemonic(Mnemonique.LDV);
            }
            case STO -> {
                testeAccept(Mnemonique.STO);
                interpreterPcode.loadMnemonic(Mnemonique.STO);
            }
            case LDI -> {
                testeAccept(Mnemonique.LDI);
                var unparsedNum = this.scanner.getSymbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.LDI, Integer.parseInt(unparsedNum));
            }
            case LDA -> {
                testeAccept(Mnemonique.LDA);
                var unparsedNum = this.scanner.getSymbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.LDA, Integer.parseInt(unparsedNum));
            }
            case BRN -> {
                testeAccept(Mnemonique.BRN);
                var unparsedNum = this.scanner.getSymbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.BRN, Integer.parseInt(unparsedNum));
            }
            case BZE -> {
                testeAccept(Mnemonique.BZE);
                var unparsedNum = this.scanner.getSymbCour().getNom();
                testeAccept(Mnemonique.NUM);
                interpreterPcode.loadMnemonic(Mnemonique.BZE, Integer.parseInt(unparsedNum));
            }
        }
    }

    public ScannerPcode getScanner() {
        return scanner;
    }
    public InterpreterPcode getInterpreterPcode() {
        return interpreterPcode;
    }
}
