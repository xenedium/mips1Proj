package net.mips.interpreter;

import java.util.ArrayList;
import java.util.Scanner;

public class InterpreterPcode {
    private Instruction[] pcode;
    private int[] pile;
    private int pc;
    private int offset;
    private int sp;

    public InterpreterPcode() {
        this.pcode = new Instruction[50];
        this.pile = new int[50];
        this.pc = this.offset = this.sp = -1;
    }

    public void loadMnemonic(Mnemonique mne) {
        this.pcode[++pc] = new Instruction(mne);
    }
    public void loadMnemonic(Mnemonique mne, int suite) {
        this.pcode[++pc] = new Instruction(mne, suite);
    }

    public void interInst(Instruction instruction) {
        int val1, val2, adr;
        switch (instruction.getMne()) {
            case INT -> {
                this.offset = this.sp = instruction.getSuite();
                this.pc++;
            }
            case LDI, LDA -> {
                this.pile[++sp] =  instruction.getSuite();
                this.pc++;
            }
            case STO -> {
                val1 = this.pile[this.sp--];
                adr = this.pile[this.sp--];
                this.pile[adr] = val1;
                this.pc++;
            }
            case LDV -> {
                adr = this.pile[this.sp--];
                this.pile[++sp] = this.pile[adr];
                this.pc++;
            }
            case EQL -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 == val2 ? 1 : 0);
                this.pc++;
            }
            case NEQ -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 != val2 ? 1 : 0);
                this.pc++;
            }
            case GTR -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 > val2 ? 1 : 0);
                this.pc++;
            }
            case LSS -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 < val2 ? 1 : 0);
                this.pc++;
            }
            case GEQ -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 >= val2 ? 1 : 0);
                this.pc++;
            }
            case LEQ -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 <= val2 ? 1 : 0);
                this.pc++;
            }
            case BRN -> this.pc = instruction.getSuite();
            case BZE -> {
                if (pile[sp--] == 0) pc = instruction.getSuite();
                else pc++;
            }
            case ADD -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 + val2);
                this.pc++;
            }
            case SUB -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 - val2);
                this.pc++;
            }
            case MUL -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 * val2);
                this.pc++;
            }
            case DIV -> {
                val1 = this.pile[this.sp--];
                val2 = this.pile[this.sp--];
                this.pile[++sp] = (val1 / val2);
                this.pc++;
            }
            case PRN -> {
                val1 = this.pile[this.sp--];
                System.out.println(val1);
                this.pc++;
            }
            case INN -> {
                var sc = new Scanner(System.in);
                adr = this.pile[sp--];
                pile[adr] = sc.nextInt();
                this.pc++;
            }
        }
    }
    public void interPcode() {
        pc = 0;
        while (pcode[pc] != null && pcode[pc].getMne() != Mnemonique.HLT) interInst(pcode[pc]);
    }
}
