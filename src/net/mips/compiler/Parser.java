package net.mips.compiler;

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
        testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
    }

    public void vars() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
    }

    public void insts() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
        testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void inst() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
        testeAccept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
        testeAccept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }

    public void affec() throws ErreurSyntaxique, ErreurLexicale {       //    <?>
        testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);                  // \_(*_*)_/
        testeAccept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);            //    | |
                                                                        //   /   \
        testeAccept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
    }


    public void si() throws ErreurSyntaxique, ErreurLexicale {
    }

    public void tantque() throws ErreurSyntaxique, ErreurLexicale {
    }



    public void ecrire() throws ErreurSyntaxique, ErreurLexicale {
        testeAccept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
        testeAccept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
        //testeAccept(Tokens.ID_TOKEN, CodesErr.ID_ERR);          // Same issue here can be an LVALUE or RVALUE
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

    public void cond() throws ErreurSyntaxique, ErreurLexicale {        //very variant....
    }



    public Scanner getScanner() {
        return scanner;
    }
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
