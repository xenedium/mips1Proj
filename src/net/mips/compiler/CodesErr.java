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
    PARD_ERR("Symbole ) attendu");


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
