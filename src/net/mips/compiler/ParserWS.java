package net.mips.compiler;

public class ParserWS extends Parser {

    public ParserWS(String filePath) throws ErreurCompilation {
        super(new ScannerWS(filePath));
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
            ((ScannerWS)this.getScanner()).chercherSymb();
            // RULE 3
            if (((ScannerWS)this.getScanner()).getPlaceSymb() == -1) throw new ErreurSemantique(CodesErr.ID_NON_DEFINI);
            // RULE 5
            if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb())
                    .getClasse() == ClasseIdf.PROGRAMME) throw new ErreurSemantique(CodesErr.ID_PROGRAMME_ERR);
            this.getScanner().symbSuiv();
        }
        else throw new ErreurSemantique(err);
    }
    @Override
    public void program() throws ErreurCompilation {
        testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
        testeInsere(Tokens.ID_TOKEN, ClasseIdf.PROGRAMME, CodesErr.ID_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        this.block();
        testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }
    @Override
    public void consts() throws ErreurCompilation {
        if (this.getScanner().getSymbCour().getToken() == Tokens.CONST_TOKEN) {
            testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            do {
                testeInsere(Tokens.ID_TOKEN, ClasseIdf.CONSTANTE, CodesErr.ID_ERR);
                testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
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
        // RULE 4
        if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb())
                .getClasse() == ClasseIdf.CONSTANTE) throw new ErreurSemantique(CodesErr.ID_CONST_MODIFIED);

        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
        this.expr();
    }
    @Override
    public void lire() throws ErreurCompilation {
        testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        // RULE 4
        if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb())
                .getClasse() == ClasseIdf.CONSTANTE) throw new ErreurSemantique(CodesErr.ID_CONST_MODIFIED);

        while (this.getScanner().getSymbCour().getToken() == Tokens.VIR_TOKEN) {
            testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            // RULE 4
            if (((ScannerWS)this.getScanner()).getTableSymb().get(((ScannerWS)this.getScanner()).getPlaceSymb())
                    .getClasse() == ClasseIdf.CONSTANTE) throw new ErreurSemantique(CodesErr.ID_CONST_MODIFIED);
        }
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }
    @Override
    public void fact() throws ErreurCompilation {
        switch (this.getScanner().getSymbCour().getToken()) {
            case ID_TOKEN -> testeCherche(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            case NUM_TOKEN -> testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            case PARG_TOKEN -> {
                testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                this.expr();
                testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
            default -> throw new ErreurSyntaxique(CodesErr.CAR_INC_ERR);
        }
    }
}
