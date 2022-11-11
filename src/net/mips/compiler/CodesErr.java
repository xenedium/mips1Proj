package net.mips.compiler;

public enum CodesErr {
    CAR_INC_ERR("Symbole inconnu"),
    FIC_VID_ERR("Erreur d'ouverture de fichier"),
    PROGRAM_ERR("Mot cle program attendu !"),
    ID_ERR("Identificateur attendu !"),
    PVIR_ERR("Symbole ; attendu"),
    PNT_ERR("Symbol . attendu"),
    CONST_ERR("Const attendu"),
    EG_ERR("Symbole = attendu"),
    NUM_ERR("Numero attendu"),
    AFFEC_ERR("Symboles := attendu"),
    WHILE_ERR("Mot while attendu"),
    WRITE_ERR("Mot write attendu"),

    READ_ERR("Mot read attendu"),
    PARG_ERR("Symbole ( attendu"),
    PARD_ERR("Symbole ) attendu"),
    IF_ERR("Mot if attendu"),
    INF_ERR("Symbole < attendu"),
    SUP_ERR("Symbole > attendu"),
    INFEG_ERR("Symbole <= attendu"),
    SUPEG_ERR("Symbole >= attendu"),
    DO_ERR("Mot do attendu"),
    THEN_ERR("Mot then attendu"),
    BEGIN_ERR("Mot begin attendu"),
    END_ERR("Mot end attendu"),
    DIFF_ERR("Symbole != attendu"),
    VAR_ERR("Mot var attendu"),
    COND_ERR("Condition attendue"),
    EXPR_ERR("Expression attendue"),
    PLUS_ERR("Symbole + attendu"),
    MOINS_ERR("Symbole - attendu"),
    MUL_ERR("Symbole * attendu"),
    DIV_ERR("Symbole / attendu"),
    VIR_ERR("Symbole , attendu"),
    ID_NON_DEFINI("ID non defini"),
    ID_PROGRAMME_ERR("ID du programme utilise"),
    ID_CONST_MODIFIED("cant edit a const variable"),
    ID_DEFINED_ERR("ID deja defini");


    private String message;

    private CodesErr(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
