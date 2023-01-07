import net.mips.compiler.ParserWS;
import net.mips.interpreter.ParserPcode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var mips1Source = Path.of(System.getProperty("user.dir"), "prog.mips1");

        // Write to an output memory stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        var parser = new ParserWS(mips1Source.toString(), outputStream);
        parser.getScanner().initMotsCles();
        parser.getScanner().lireCar();
        parser.getScanner().symbSuiv();
        parser.program();
        parser.savePcode();

        // Pipe the output memory stream to an input memory stream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        // Explicitly run the JVM garbage collector to collect unused objects
        System.gc();
        var pcodeParser = new ParserPcode(inputStream);
        pcodeParser.getScanner().lireCar();
        pcodeParser.getScanner().symbSuiv();
        pcodeParser.pcode();
        pcodeParser.getInterpreterPcode().interPcode();
    }
}