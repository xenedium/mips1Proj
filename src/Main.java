import net.mips.compiler.Scanner;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {

        var mips1Source = Path.of(System.getProperty("user.dir"), "prog.mips1");

        var scanner = new Scanner(mips1Source.toString());

        scanner.initMotsCles();

        scanner.lireCar();

        while (scanner.getCarCour() != '\0') {
            scanner.symbSuiv();
            System.out.println(scanner.getSymbCour().getToken().toString() + " <===> " + scanner.getSymbCour().getNom());
        }
    }
}