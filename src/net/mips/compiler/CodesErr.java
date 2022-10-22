package net.mips.compiler;

public enum CodesErr {
    CAR_INC_ERR("Symbole inconnu"), FIC_VID_ERR("Erreur d'ouverture de fichier");

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
