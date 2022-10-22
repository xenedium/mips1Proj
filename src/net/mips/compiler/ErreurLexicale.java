package net.mips.compiler;

public class ErreurLexicale extends ErreurCompilation{
    public ErreurLexicale(CodesErr codesErr) {
        super(codesErr.getMessage());
    }
}
