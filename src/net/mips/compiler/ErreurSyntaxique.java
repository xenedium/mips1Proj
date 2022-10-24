package net.mips.compiler;

public class ErreurSyntaxique extends ErreurCompilation {
    public ErreurSyntaxique(CodesErr codesErr) {
        super(codesErr.getMessage());
    }
}
