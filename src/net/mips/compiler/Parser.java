package net.mips.compiler;
// Not fully implemented
//   /?\
//   \ /
// (°) (°)
//  / ^ \
//  /\/\/\
// tantque | si | block | program | consts | vars | insts

public class Parser {
    private Scanner scanner;

    public Parser(String fileName) throws ErreurCompilation {
        this.scanner = new Scanner(fileName);
    }

    public void testeAccept(Tokens token, CodesErr codeErr) throws ErreurSyntaxique, ErreurLexicale {
        if (this.scanner.getSymbCour().getToken() == token) {
            this.scanner.symbSuiv();
        }
        else throw new ErreurSyntaxique(codeErr);
    }

    public void block() throws ErreurSyntaxique, ErreurLexicale {
        consts();
        vars();
        insts();
    }

    public void program() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        block();
        testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }

    public void consts() throws ErreurSyntaxique, ErreurLexicale {

    }

    public void vars() throws ErreurSyntaxique, ErreurLexicale {

    }

    public void insts() throws ErreurSyntaxique, ErreurLexicale {

    }

    public void inst() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
        testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void affec() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
        if (this.scanner.getSymbCour().getToken() == Tokens.ID_TOKEN)
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        else
            testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void si() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.IF_TOKEN, CodesErr.IF_ERR);
        this.cond();
        testeAccept(Tokens.THEN_TOKEN, CodesErr.THEN_ERR);
        testeAccept(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
        // Block
        testeAccept(Tokens.END_TOKEN, CodesErr.END_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void tantque() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.WHILE_TOKEN, CodesErr.WHILE_ERR);
        this.cond();
        testeAccept(Tokens.DO_TOKEN, CodesErr.DO_ERR);
        testeAccept(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
        // block
        testeAccept(Tokens.END_TOKEN, CodesErr.END_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void ecrire() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        if (this.scanner.getSymbCour().getToken() == Tokens.ID_TOKEN)
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        else
            testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void lire() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void cond() throws ErreurSyntaxique, ErreurLexicale {
        if (this.scanner.getSymbCour().getToken() == Tokens.ID_TOKEN)
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        else
            testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);

        switch (this.scanner.getSymbCour().getToken()) {
            case INF_TOKEN -> testeAccept(Tokens.INF_TOKEN, CodesErr.INF_ERR);
            case SUP_TOKEN -> testeAccept(Tokens.SUP_TOKEN, CodesErr.SUP_ERR);
            case INFEG_TOKEN -> testeAccept(Tokens.INFEG_TOKEN, CodesErr.INFEG_ERR);
            case SUPEG_TOKEN -> testeAccept(Tokens.SUPEG_TOKEN, CodesErr.SUPEG_ERR);
            case DIFF_TOKEN -> testeAccept(Tokens.DIFF_TOKEN, CodesErr.DIFF_ERR);
            default -> testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
        }

        if (this.scanner.getSymbCour().getToken() == Tokens.ID_TOKEN)
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        else
            testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
    }



    public Scanner getScanner() {
        return scanner;
    }
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
