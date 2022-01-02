
package bossfight;

import java.util.Scanner;

    public class Printer {
     static  Scanner scanner;

    public static void init(Scanner scanner) {
        Printer.scanner = scanner;
    }

    public static void write(String message) {
        System.out.println(message);
    }

    public static String readString() {
        return scanner.nextLine();
    }

    public static int readInt() {
        return scanner.nextInt();
    }
}
