package net.mips.interpreter;

public class Instruction {
    private Mnemonique mne;
    private int suite;
    // INT_MIN for NO OPERAND OP CODES
    public Instruction() {
        this(null, Integer.MIN_VALUE);
    }
    public Instruction(Mnemonique mne) {
        this(mne, Integer.MIN_VALUE);
    }
    public Instruction(Mnemonique mne, int suite) {
        this.mne = mne;
        this.suite = suite;
    }
    public int getSuite() {
        return suite;
    }
    public Mnemonique getMne() {
        return mne;
    }
    public void setSuite(int suite) {
        this.suite = suite;
    }
    public void setMne(Mnemonique mne) {
        this.mne = mne;
    }
}
