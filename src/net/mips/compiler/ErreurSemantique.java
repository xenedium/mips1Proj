package net.mips.compiler;

public class ErreurSemantique extends ErreurCompilation{
    public ErreurSemantique(CodesErr err) {
        super(err.getMessage());
    }
}
