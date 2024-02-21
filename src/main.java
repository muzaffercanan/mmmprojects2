import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class main {

    public static void main(String[] args) {

        public void readPolynomialsFromFile(String filename) {
            try {
                Scanner scanner = new Scanner(new File(filename));
                int numCases = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                for (int i = 0; i < numCases; i++) {
                    char operator = scanner.next().charAt(0);
                    String polynomial1 = scanner.next();
                    String polynomial2 = scanner.nextLine().trim();
                    // Process operator and polynomials accordingly
                    // You can parse the strings and create Term objects
                    // Then insert them into the linked list
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
