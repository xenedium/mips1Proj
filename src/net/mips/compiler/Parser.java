package net.mips.compiler;

import java.util.Arrays;

public class Parser {
    private Scanner scanner;

    public Parser(String fileName) throws ErreurCompilation {
        this.scanner = new Scanner(fileName);
    }

    public void testeAccept(Tokens token, CodesErr codeErr) throws ErreurSyntaxique, ErreurLexicale {
        if (this.scanner.getSymbCour().getToken() == token) {
            System.out.println("Accept " + token + " " + this.scanner.getSymbCour().getNom());
            if (this.scanner.getCarCour() != 0) this.scanner.symbSuiv();
        }
        else throw new ErreurSyntaxique(codeErr);
    }

    public void block() throws ErreurSyntaxique, Exception {
        this.consts();
        this.vars();
        this.insts();
    }

    public void program() throws ErreurSyntaxique, Exception {
        testeAccept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        this.block();
        testeAccept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
    }

    public void consts() throws ErreurSyntaxique, Exception {
        if (this.scanner.getSymbCour().getToken() == Tokens.CONST_TOKEN) {
            testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
            do {
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
                testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
                testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            } while (this.scanner.getSymbCour().getToken() == Tokens.ID_TOKEN);
        }

    }

    public void vars() throws ErreurSyntaxique, Exception {
        if (this.scanner.getSymbCour().getToken() == Tokens.VAR_TOKEN) {
            testeAccept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
            do {
                testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
                if (this.scanner.getSymbCour().getToken() == Tokens.VIR_TOKEN) {
                    testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
                }
            } while (this.scanner.getSymbCour().getToken() == Tokens.ID_TOKEN);
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
        }
    }

    public void insts() throws ErreurSyntaxique, Exception {
        Tokens[] tokens = {Tokens.ID_TOKEN, Tokens.IF_TOKEN, Tokens.WHILE_TOKEN, Tokens.WRITE_TOKEN, Tokens.READ_TOKEN};
        testeAccept(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
        this.inst();
        while (this.scanner.getSymbCour().getToken() == Tokens.PVIR_TOKEN){
            testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
            this.inst();
        }
        testeAccept(Tokens.END_TOKEN, CodesErr.END_ERR);
    }

    public void inst() throws ErreurSyntaxique, Exception {
        switch (this.scanner.getSymbCour().getToken()) {
            case BEGIN_TOKEN -> this.insts();
            case ID_TOKEN -> this.affec();
            case READ_TOKEN -> this.lire();
            case WRITE_TOKEN -> this.ecrire();
            case IF_TOKEN -> this.si();
            case WHILE_TOKEN -> this.tantque();
        }
    }

    public void affec() throws ErreurSyntaxique, Exception {
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
        this.expr();
    }

    public void si() throws ErreurSyntaxique, Exception {
        testeAccept(Tokens.IF_TOKEN, CodesErr.IF_ERR);
        this.cond();
        testeAccept(Tokens.THEN_TOKEN, CodesErr.THEN_ERR);
        this.inst();
    }

    public void tantque() throws ErreurSyntaxique, Exception {
        testeAccept(Tokens.WHILE_TOKEN, CodesErr.WHILE_ERR);
        this.cond();
        testeAccept(Tokens.DO_TOKEN, CodesErr.DO_ERR);
        this.inst();
    }

    public void ecrire() throws ErreurSyntaxique, Exception {
        testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        this.expr();
        while (this.scanner.getSymbCour().getToken() == Tokens.VIR_TOKEN) {
            testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            this.expr();
        }
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }

    public void lire() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        while (this.scanner.getSymbCour().getToken() == Tokens.VIR_TOKEN) {
            testeAccept(Tokens.VIR_TOKEN, CodesErr.VIR_ERR);
            testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        }
        testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
    }

    public void cond() throws ErreurSyntaxique, Exception {
        this.expr();
        this.relop();
        this.expr();
    }

    public void expr() throws ErreurSyntaxique, Exception {
        Tokens[] tokens = {Tokens.PLUS_TOKEN, Tokens.MOINS_TOKEN};
        this.term();
        while (Arrays.asList(tokens).contains(this.scanner.getSymbCour().getToken())) {
            this.addop();
            this.term();
        }

    }

    public void term() throws ErreurSyntaxique, Exception {
        Tokens[] tokens = {Tokens.MUL_TOKEN, Tokens.DIV_TOKEN};
        this.fact();
        while (Arrays.asList(tokens).contains(this.scanner.getSymbCour().getToken())) {
            this.mulop();
            this.fact();
        }
    }

    public void fact() throws ErreurSyntaxique, Exception {
        switch (this.scanner.getSymbCour().getToken()) {
            case ID_TOKEN -> testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
            case NUM_TOKEN -> testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
            case PARG_TOKEN -> {
                testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
                this.expr();
                testeAccept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
            }
            default -> throw new ErreurSyntaxique(CodesErr.CAR_INC_ERR);
        }
    }

    public void relop() throws ErreurSyntaxique, Exception {
        switch (this.scanner.getSymbCour().getToken()) {
            case INF_TOKEN -> testeAccept(Tokens.INF_TOKEN, CodesErr.INF_ERR);
            case SUP_TOKEN -> testeAccept(Tokens.SUP_TOKEN, CodesErr.SUP_ERR);
            case INFEG_TOKEN -> testeAccept(Tokens.INFEG_TOKEN, CodesErr.INFEG_ERR);
            case SUPEG_TOKEN -> testeAccept(Tokens.SUPEG_TOKEN, CodesErr.SUPEG_ERR);
            case DIFF_TOKEN -> testeAccept(Tokens.DIFF_TOKEN, CodesErr.DIFF_ERR);
            case EG_TOKEN -> testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
            default -> throw new ErreurSyntaxique(CodesErr.COND_ERR);
        }
    }

    public void addop() throws ErreurSyntaxique, Exception {
        if (this.scanner.getSymbCour().getToken() == Tokens.PLUS_TOKEN)
            testeAccept(Tokens.PLUS_TOKEN, CodesErr.PLUS_ERR);
        else if (this.scanner.getSymbCour().getToken() == Tokens.MOINS_TOKEN)
            testeAccept(Tokens.MOINS_TOKEN, CodesErr.MOINS_ERR);
    }

    public void mulop() throws ErreurSyntaxique, Exception {
        if (this.scanner.getSymbCour().getToken() == Tokens.MUL_TOKEN)
            testeAccept(Tokens.MUL_TOKEN, CodesErr.MUL_ERR);
        else if (this.scanner.getSymbCour().getToken() == Tokens.DIV_TOKEN)
            testeAccept(Tokens.DIV_TOKEN, CodesErr.DIV_ERR);
    }

    public Scanner getScanner() {
        return scanner;
    }
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
