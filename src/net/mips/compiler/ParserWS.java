package net.mips.compiler;

import net.mips.interpreter.Instruction;
import net.mips.interpreter.Mnemonique;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ParserWS extends Parser {

    private ArrayList<Instruction> pcode;
    private PrintWriter fluxCible;
    public ParserWS(String filePath, OutputStream out) throws ErreurCompilation {
        super(new ScannerWS(filePath));
        this.pcode = new ArrayList<>();
        this.fluxCible = new PrintWriter(out, true);
    }
    public void testeInsere(Tokens token, ClasseIdf classe, CodesErr err) throws ErreurCompilation {
        if (token == this.getScanner().getSymbCour().getToken()) {
            ((ScannerWS)this.getScanner()).chercherSymb();
            if (((ScannerWS)this.getScanner()).getPlaceSymb() != -1) throw new ErreurSemantique(CodesErr.ID_DEFINED_ERR);
            ((ScannerWS)this.getScanner()).entrerSymb(classe);
            this.getScanner().symbSuiv();
        }
        else throw new ErreurSemantique(err);
    }
    public void testeCherche(Tokens token, CodesErr err) throws ErreurCompilation {
        if (token == this.getScanner().getSymbCour().getToken()) {
            this.getScanner().chercherSymb();
            // RULE 3
            if (this.getScanner().getPlaceSymb() == -1) throw new ErreurSemantique(CodesErr.ID_NON_DEFINI);
            // RULE 5
            if (this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb()).getClasse() == ClasseIdf.PROGRAMME) throw new ErreurSemantique(CodesErr.ID_PROGRAMME_ERR);
            this.getScanner().symbSuiv();
        }
        else throw new ErreurSemantique(err);
    }
    public void generer1(Mnemonique mnemonique) {
        this.pcode.add(new Instruction(mnemonique));
    }
    public void generer2(Mnemonique mnemonique, int operande) {
        this.pcode.add(new Instruction(mnemonique, operande));
    }
    public void savePcode() {
        for (var inst : pcode) fluxCible.println(inst.getMne().toString() + (inst.getSuite() != Integer.MIN_VALUE ? " " + inst.getSuite() : ""));
    }
    public ArrayList<Instruction> getPcode() {
        return pcode;
    }
    public void setPcode(ArrayList<Instruction> pcode) {
        this.pcode = pcode;
    }
    public void setFluxCible(PrintWriter fluxCible) {
        this.fluxCible = fluxCible;
    }
    public PrintWriter getFluxCible() {
        return fluxCible;
    }
    public ScannerWS getScanner() {
        return (ScannerWS) super.getScanner();
    }

    @Override
    public void block() throws ErreurCompilation {
        consts();
        vars();
        pcode.add(0, new Instruction(Mnemonique.INT, this.getScanner().getOffset()));
        insts();
    }
    @Override
    public void program() throws ErreurCompilation {
        testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
        testeInsere(Tokens.ID_TOKEN, ClasseIdf.PROGRAMME, CodesErr.ID_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        this.block();
        generer1(Mnemonique.HLT);
        testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }
    @Override
    public void consts() throws ErreurCompilation {
        if (this.getScanner().getSymbCour().getToken() == Tokens.CONST_TOKEN) {
            testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            do {
                testeInsere(Tokens.ID_TOKEN, ClasseIdf.CONSTANTE, CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, this.getScanner().getOffset());
                testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                // Get the number but don't parse it because we don't know if it's an actual number
                var num = this.getScanner().getSymbCour().getNom();
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                // After the testeAccepte we are sure that it's a number
                generer2(Mnemonique.LDI, Integer.parseInt(num));
                generer1(Mnemonique.STO);
                testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            } while (this.getScanner().getSymbCour().getToken() == Tokens.ID_TOKEN);
        }
    }
    @Override
    public void vars() throws ErreurCompilation {
        if (this.getScanner().getSymbCour().getToken() == Tokens.VAR_TOKEN) {
            testeAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
            do {
                testeInsere(Tokens.ID_TOKEN, ClasseIdf.VARIABLE, CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, this.getScanner().getOffset());
                if (this.getScanner().getSymbCour().getToken() == Tokens.VIR_TOKEN) {
                    testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                }
            } while (this.getScanner().getSymbCour().getToken() == Tokens.ID_TOKEN);
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        }
    }
    @Override
    public void affec() throws ErreurCompilation {
        testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        generer2(Mnemonique.LDA, this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb()).getAdresse());
        // RULE 4
        if (this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb())
                .getClasse() == ClasseIdf.CONSTANTE) throw new ErreurSemantique(CodesErr.ID_CONST_MODIFIED);

        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
        this.expr();
        generer1(Mnemonique.STO);
    }
    @Override
    public void lire() throws ErreurCompilation {
        testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        // RULE 4
        if (this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb())
                .getClasse() == ClasseIdf.CONSTANTE) throw new ErreurSemantique(CodesErr.ID_CONST_MODIFIED);
        generer2(Mnemonique.LDA, this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb()).getAdresse());
        generer1(Mnemonique.INN);

        while (this.getScanner().getSymbCour().getToken() == Tokens.VIR_TOKEN) {
            testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            // RULE 4
            if (this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb()).getClasse() == ClasseIdf.CONSTANTE) throw new ErreurSemantique(CodesErr.ID_CONST_MODIFIED);
            generer2(Mnemonique.LDA, this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb()).getAdresse());
            generer1(Mnemonique.STO);
        }
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }
    @Override
    public void ecrire() throws ErreurCompilation {
        testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        this.expr();
        generer1(Mnemonique.PRN);
        while (this.getScanner().getSymbCour().getToken() == Tokens.VIR_TOKEN) {
            testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            this.expr();
            generer1(Mnemonique.PRN);
        }
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }
    @Override
    public void expr() throws ErreurCompilation {
        Tokens[] tokens = {Tokens.PLUS_TOKEN, Tokens.MOINS_TOKEN};
        this.term();
        while (Arrays.asList(tokens).contains(this.getScanner().getSymbCour().getToken())) {
            var op = this.getScanner().getSymbCour().getToken();
            this.addop();
            this.term();
            generer1(op == Tokens.PLUS_TOKEN ? Mnemonique.ADD : Mnemonique.SUB);
        }
    }
    @Override
    public void term() throws ErreurCompilation {
        Tokens[] tokens = {Tokens.MUL_TOKEN, Tokens.DIV_TOKEN};
        this.fact();
        while (Arrays.asList(tokens).contains(this.getScanner().getSymbCour().getToken())) {
            var op = this.getScanner().getSymbCour().getToken();
            this.mulop();
            this.fact();
            generer1(op == Tokens.MUL_TOKEN ? Mnemonique.MUL : Mnemonique.DIV);
        }
    }
    @Override
    public void fact() throws ErreurCompilation {
        switch (this.getScanner().getSymbCour().getToken()) {
            case ID_TOKEN -> {
                testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                generer2(Mnemonique.LDA, this.getScanner().getTableSymb().get(this.getScanner().getPlaceSymb()).getAdresse());
                generer1(Mnemonique.LDV);
            }
            case NUM_TOKEN -> {
                var unverifiedNum = this.getScanner().getSymbCour().getNom();
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                generer2(Mnemonique.LDI, Integer.parseInt(unverifiedNum));
            }
            case PARG_TOKEN -> {
                testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                this.expr();
                testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
            default -> throw new ErreurSyntaxique(CodesErr.CAR_INC_ERR);
        }
    }
}
