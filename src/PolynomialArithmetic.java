import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PolynomialArithmetic {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File(
                    "C:/Users/muzca/Desktop/muzo-genel/COURSES/CS/cs201/cs-201-15-tatil/first-lab/src/input.txt"));
            PrintWriter writer = new PrintWriter(new File(
                    "C:/Users/muzca/Desktop/muzo-genel/COURSES/CS/cs201/cs-201-15-tatil/first-lab/src/output.txt"));
            int numCases = scanner.nextInt();
            scanner.nextLine(); // consume newline
            for (int i = 0; i < numCases; i++) {

                String line = scanner.nextLine().trim(); // Read and trim the line
                // Split the line based on spaces
                String[] parts = line.split("\\s+"); // ben ekledim

                char operator = parts[0].charAt(0); // Extract the operator
                String polynomial1Str = parts[1]; // Extract the first polynomial
                String polynomial2Str = parts[2]; // Extract the second polynomial

                // Parse and perform the operation on polynomials

                Polynomial polynomial1 = parsePolynomial(polynomial1Str);
                Polynomial polynomial2 = parsePolynomial(polynomial2Str);
                Polynomial result = performOperation(operator, polynomial1, polynomial2);
                writer.println(formatPolynomial(result));

            }
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static Polynomial parsePolynomial(String polynomialStr) {
        Polynomial polynomial = new Polynomial();
        String[] termsStr = polynomialStr.split("\\s+");
        for (int i = 1; i < termsStr.length; i++) { // Start from index 1 to skip the operator
            String termStr = termsStr[i];
            int coefficient = 1; // Default coefficient if not specified
            int exponentX = 0, exponentY = 0, exponentZ = 0;
            int lastIndex = 0;
            for (int j = 0; j < termStr.length(); j++) {
                char ch = termStr.charAt(j);
                if (ch == 'x' || ch == 'y' || ch == 'z') {
                    String factor = termStr.substring(lastIndex, j);
                    if (!factor.isEmpty()) {
                        int value = Integer.parseInt(factor);
                        if (ch == 'x')
                            exponentX = value;
                        else if (ch == 'y')
                            exponentY = value;
                        else if (ch == 'z')
                            exponentZ = value;
                    }
                    lastIndex = j + 1;
                } else if (ch == '+' || ch == '-') {
                    String factor = termStr.substring(lastIndex, j);
                    if (!factor.isEmpty()) {
                        coefficient = Integer.parseInt(factor);
                    }
                    lastIndex = j;
                    break;
                }
            }
            String factor = termStr.substring(lastIndex);
            if (!factor.isEmpty()) {
                coefficient = Integer.parseInt(factor);
            }
            Term term = new Term(coefficient, exponentX, exponentY, exponentZ);
            polynomial.addTerm(term);
        }
        return polynomial;
    }

    // private static int extractExponent(String factor) {
    // return Integer.parseInt(factor.substring(1));
    // }

    private static Polynomial performOperation(char operator, Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();
        switch (operator) {
            case '+':
                result = addPolynomials(polynomial1, polynomial2);
                break;
            case '-':
                result = subtractPolynomials(polynomial1, polynomial2);
                break;
            case '*':
                result = multiplyPolynomials(polynomial1, polynomial2);
                break;
            default:
                System.out.println("Invalid operator");
        }
        return result;
    }

    private static int compareExponents(Term term1, Term term2) {
        int diffX = term1.getExponentX() - term2.getExponentX();
        int diffY = term1.getExponentY() - term2.getExponentY();
        int diffZ = term1.getExponentZ() - term2.getExponentZ();
        if (diffX != 0)
            return diffX;
        if (diffY != 0)
            return diffY;
        return diffZ;
    }

    private static Polynomial addPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();

        Node current1 = polynomial1.getHead();
        Node current2 = polynomial2.getHead();

        while (current1 != null || current2 != null) {
            if (current1 == null) {
                while (current2 != null) {
                    result.addTerm(new Term(current2.getTerm().getCoefficient(),
                            current2.getTerm().getExponentX(),
                            current2.getTerm().getExponentY(),
                            current2.getTerm().getExponentZ()));
                    current2 = current2.getNext();
                }
                break;
            }
            if (current2 == null) {
                while (current1 != null) {
                    result.addTerm(new Term(current1.getTerm().getCoefficient(),
                            current1.getTerm().getExponentX(),
                            current1.getTerm().getExponentY(),
                            current1.getTerm().getExponentZ()));
                    current1 = current1.getNext();
                }
                break;
            }

            Term term1 = current1.getTerm();
            Term term2 = current2.getTerm();

            int comparison = compareExponents(term1, term2);

            if (comparison == 0) {
                int coefficient = term1.getCoefficient() + term2.getCoefficient();
                if (coefficient != 0) {
                    result.addTerm(new Term(coefficient,
                            term1.getExponentX(), term1.getExponentY(), term1.getExponentZ()));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (comparison < 0) {
                result.addTerm(new Term(term1.getCoefficient(),
                        term1.getExponentX(), term1.getExponentY(), term1.getExponentZ()));
                current1 = current1.getNext();
            } else {
                result.addTerm(new Term(term2.getCoefficient(),
                        term2.getExponentX(), term2.getExponentY(), term2.getExponentZ()));
                current2 = current2.getNext();
            }
        }
        return result;
    }

    private static Polynomial subtractPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();

        Node current1 = polynomial1.getHead();
        Node current2 = polynomial2.getHead();

        while (current1 != null || current2 != null) {
            if (current1 == null) {
                while (current2 != null) {
                    result.addTerm(new Term(-current2.getTerm().getCoefficient(),
                            current2.getTerm().getExponentX(),
                            current2.getTerm().getExponentY(),
                            current2.getTerm().getExponentZ()));
                    current2 = current2.getNext();
                }
                break;
            }
            if (current2 == null) {
                while (current1 != null) {
                    result.addTerm(new Term(current1.getTerm().getCoefficient(),
                            current1.getTerm().getExponentX(),
                            current1.getTerm().getExponentY(),
                            current1.getTerm().getExponentZ()));
                    current1 = current1.getNext();
                }
                break;
            }

            Term term1 = current1.getTerm();
            Term term2 = current2.getTerm();
            int comparison = compareExponents(term1, term2);

            if (comparison == 0) {
                int coefficient = term1.getCoefficient() - term2.getCoefficient();
                if (coefficient != 0) {
                    result.addTerm(new Term(coefficient,
                            term1.getExponentX(), term1.getExponentY(), term1.getExponentZ()));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (comparison < 0) {
                result.addTerm(new Term(term1.getCoefficient(),
                        term1.getExponentX(), term1.getExponentY(), term1.getExponentZ()));
                current1 = current1.getNext();
            } else {
                result.addTerm(new Term(-term2.getCoefficient(),
                        term2.getExponentX(), term2.getExponentY(), term2.getExponentZ()));
                current2 = current2.getNext();
            }
        }

        return result;
    }

    private static Polynomial multiplyPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();

        Node current1 = polynomial1.getHead();

        while (current1 != null) {
            Node current2 = polynomial2.getHead();
            while (current2 != null) {
                int coefficient = current1.getTerm().getCoefficient() * current2.getTerm().getCoefficient();
                int exponentX = current1.getTerm().getExponentX() + current2.getTerm().getExponentX();
                int exponentY = current1.getTerm().getExponentY() + current2.getTerm().getExponentY();
                int exponentZ = current1.getTerm().getExponentZ() + current2.getTerm().getExponentZ();

                Term term = new Term(coefficient, exponentX, exponentY, exponentZ);
                result.addTerm(term);

                current2 = current2.getNext();
            }
            current1 = current1.getNext();
        }

        return result;
    }

    private static String formatPolynomial(Polynomial polynomial) {
        if (polynomial.isEmpty()) {
            return "muzo";
        }
        StringBuilder result = new StringBuilder();
        Node current = polynomial.getHead();
        while (current != null) {
            Term term = current.getTerm();
            if (result.length() > 0) {
                result.append("+");
            }
            result.append(term.getCoefficient());
            if (term.getExponentX() > 0) {
                result.append("x");
                if (term.getExponentX() > 1) {
                    result.append(term.getExponentX());
                }
            }
            if (term.getExponentY() > 0) {
                result.append("y");
                if (term.getExponentY() > 1) {
                    result.append(term.getExponentY());
                }
            }
            if (term.getExponentZ() > 0) {
                result.append("z");
                if (term.getExponentZ() > 1) {
                    result.append(term.getExponentZ());
                }
            }
            current = current.getNext();
        }
        return result.toString();
    }
}