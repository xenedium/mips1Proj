import net.mips.compiler.Parser;
import net.mips.compiler.ParserWS;
import net.mips.compiler.Scanner;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {

        var mips1Source = Path.of(System.getProperty("user.dir"), "prog.mips1");

        var parser = new ParserWS(mips1Source.toString(), System.out);

        parser.getScanner().initMotsCles();

        parser.getScanner().lireCar();
        parser.getScanner().symbSuiv();

        parser.program();
        parser.savePcode();
    }
}