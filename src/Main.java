import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("input.txt")); //reads the input.txt file
            PrintWriter writer = new PrintWriter("output.txt");  //writes the result to output.txt file
            int numCases = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numCases; i++) {

                String line = scanner.nextLine().trim(); // Reads and trims the line

                String[] parts = line.split("\\s+"); //divides according to the spaces

                char operator = parts[0].charAt(0); //first line
                String polynomial1Str = parts[1];   //second line
                String polynomial2Str = parts[2];   //third line


                Polynomial polynomial1 = parsePolynomial(polynomial1Str);  //parses the first polynomial
                Polynomial polynomial2 = parsePolynomial(polynomial2Str);  //parses the first polynomial

                Polynomial result = performOperation(operator, polynomial1, polynomial2); //performs the operation that given at the beginning
                writer.println(formatPolynomial(result));

                //System.out.println(polynomial1.getHead().getTerm().getCoefficient());
                //System.out.println(polynomial1.getHead().getTerm().getExponentX());
                //System.out.println(polynomial1.getHead().getTerm().getExponentY());
                //System.out.println(polynomial1.getHead().getTerm().getExponentZ());

            }
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static Polynomial parsePolynomial(String polynomialStr) {
        Polynomial polynomial = new Polynomial();

        // Splits the polynomialString into terms based on '+' and '-'
        String[] termsStr = polynomialStr.split("(?=\\+)|(?=\\-)");

        for (String termStr : termsStr) {
            // Skips empty Strings
            if (termStr.isEmpty()) {
                continue;
            }

            int coefficient = 1; // Default coefficient should be 1 if not specified
            int exponentX = 0, exponentY = 0, exponentZ = 0;

            // Check if the term starts with a '-' sign, puts negative coefficient
            boolean negative = false;
            if (termStr.charAt(0) == '-') {
                negative = true;
                termStr = termStr.substring(1); // Remove  '-' sign
            }
            else if (termStr.charAt(0) == '+') {
                termStr = termStr.substring(1); // Remove  '+' sign
            }

            // Splits term into separate coefficient and variables
            String[] parts = termStr.split("(?=[xyz])");

            // Parses the coefficient
            if (parts.length > 0 && !parts[0].isEmpty()) {
                // Checks if the part contains a coefficient
                if (parts[0].matches("[-+]?\\d+")) {
                    coefficient = Integer.parseInt(parts[0]);
                }
            }

            // Parses the exponents for variables
            for (int i = 1; i < parts.length; i++) {
                if (parts[i].startsWith("x")) {
                    exponentX = parseExponent(parts[i]);
                }
                else if (parts[i].startsWith("y")) {
                    exponentY = parseExponent(parts[i]);
                }
                else if (parts[i].startsWith("z")) {
                    exponentZ = parseExponent(parts[i]);
                }
            }

            // put coefficient for negative terms
            if (negative) {
                coefficient *= -1;
            }

            // Creates and adds the term to the polynomial
            Term term = new Term(coefficient, exponentX, exponentY, exponentZ);
            polynomial.addTerm(term);
        }

        return polynomial;
    }

    private static int parseExponent(String term) {
        if (term.length() == 1) {
            // If there is no any exponent specified, return 1
            return 1;
        } else {
            // Parses the exponent from the string
            return Integer.parseInt(term.substring(1));
        }
    }

    //  Performs the arithmetic operation on two polynomials.
    private static Polynomial performOperation(char operator, Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();
        switch (operator) {
            case '+':
                //adds if the case is addition
                result = addPolynomials(polynomial1, polynomial2);
                break;
            case '-':
                //subtracts if the case is addition
                result = subtractPolynomials(polynomial1, polynomial2);
                break;
            case '*':
                //multiplies if the case is addition
                result = multiplyPolynomials(polynomial1, polynomial2);
                break;
            default:
                System.out.println("Invalid operator");
        }
        return result;
    }

    // Calculates the differences between exponents of each variable
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

    //adds two polinomials
    private static Polynomial addPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();

        Node node1 = polynomial1.getHead();
        Node node2 = polynomial2.getHead();

        while (node1 != null && node2 != null) {
            // Compares exponents of the terms both with node1 and node2
            int compareResult = compareExponents(node1.getTerm(), node2.getTerm());

            if (compareResult == 0) {
                // If exponents equal, add coefficients and create the new term
                int sumCoefficient = node1.getTerm().getCoefficient() + node2.getTerm().getCoefficient();
                if (sumCoefficient != 0) {
                    Term sumTerm = new Term(sumCoefficient, node1.getTerm().getExponentX(),
                            node1.getTerm().getExponentY(), node1.getTerm().getExponentZ());
                    result.addTerm(sumTerm);
                }
                node1 = node1.getNext();
                node2 = node2.getNext();
            } else if (compareResult < 0) {
                // If exponent of the term in node1 is lesser, add it to the result
                result.addTerm(node1.getTerm());
                node1 = node1.getNext();
            } else {
                // If the exponent of the term in node2 is lesser , add it to the result
                result.addTerm(node2.getTerm());
                node2 = node2.getNext();
            }
        }

        // Add remaining terms from polynomial1, if any exists
        while (node1 != null) {
            result.addTerm(node1.getTerm());
            node1 = node1.getNext();
        }

        // Add remaining terms from polynomial2, if any exists
        while (node2 != null) {
            result.addTerm(node2.getTerm());
            node2 = node2.getNext();
        }

        // Consolidate like terms in the result polynomial
        consolidateLikeTerms(result);

        return result;
    }

    //it consolidates like terms ,
    private static void consolidateLikeTerms(Polynomial polynomial) {
        Node current = polynomial.getHead();
        //checks whether current and current.next node exists
        while (current != null && current.getNext() != null) {
            Node nextNode = current.getNext();
            if (areLikeTerms(current.getTerm(), nextNode.getTerm())) {
                // If the current and next nodes have like terms, combine them
                int sumCoefficient = current.getTerm().getCoefficient() + nextNode.getTerm().getCoefficient();
                current.getTerm().setCoefficient(sumCoefficient);
                current.setNext(nextNode.getNext());
            } else {
                current = current.getNext();
            }
        }
    }

    //examines the like terms based on their exponents , if it is equal they are likely for addition and subtraction
    private static boolean areLikeTerms(Term term1, Term term2) {
        return term1.getExponentX() == term2.getExponentX() &&
                term1.getExponentY() == term2.getExponentY() &&
                term1.getExponentZ() == term2.getExponentZ();
    }

    private static Polynomial subtractPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
        // Initialize the result polynomial
        Polynomial result = new Polynomial();

        // Initialize iterators for polynomial1 and polynomial2
        Node current1 = polynomial1.getHead();
        Node current2 = polynomial2.getHead();

        // Iterate through polynomial terms until both iterators reach the end
        while (current1 != null || current2 != null) {
            // Check if iterator for polynomial1 has reached the end
            if (current1 == null) {
                // If polynomial1 has ended, add negated terms of polynomial2 to the result
                while (current2 != null) {
                    // Add negated term from polynomial2 to result
                    result.addTerm(new Term(-current2.getTerm().getCoefficient(),
                            current2.getTerm().getExponentX(),
                            current2.getTerm().getExponentY(),
                            current2.getTerm().getExponentZ()));
                    current2 = current2.getNext();
                }
                // Break the loop as polynomial2 terms are exhausted
                break;
            }
            // Check if iterator for polynomial2 has reached the end
            if (current2 == null) {
                // If polynomial2 has ended, add terms of polynomial1 to the result
                while (current1 != null) {
                    result.addTerm(new Term(current1.getTerm().getCoefficient(),
                            current1.getTerm().getExponentX(),
                            current1.getTerm().getExponentY(),
                            current1.getTerm().getExponentZ()));
                    current1 = current1.getNext();
                }
                break;
            }

            // Both iterators are valid, compare the exponents of the current terms
            Term term1 = current1.getTerm();
            Term term2 = current2.getTerm();
            int comparison = compareExponents(term1, term2);

            if (comparison == 0) {
                // If exponents are equal, subtract coefficients and add result to polynomial
                int coefficient = term1.getCoefficient() - term2.getCoefficient();
                // Add the resulting term to the result polynomial if the coefficient is non-zero
                if (coefficient != 0) {
                    result.addTerm(new Term(coefficient,
                            term1.getExponentX(), term1.getExponentY(), term1.getExponentZ()));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (comparison < 0) {
                // If exponent of term in polynomial1 is less, add it directly to the result
                result.addTerm(new Term(term1.getCoefficient(),
                        term1.getExponentX(), term1.getExponentY(), term1.getExponentZ()));
                current1 = current1.getNext();
            } else {
                // If exponent of term in polynomial2 is less, add its negation to the result
                result.addTerm(new Term(-term2.getCoefficient(),
                        term2.getExponentX(), term2.getExponentY(), term2.getExponentZ()));
                current2 = current2.getNext();
            }
        }

        return result;
    }

    private static Polynomial multiplyPolynomials(Polynomial polynomial1, Polynomial polynomial2) {
    Polynomial result = new Polynomial(); // Initialize the result polynomial

    Node current1 = polynomial1.getHead(); // Start iterating through polynomial1

    // Iterate through each term of polynomial1
    while (current1 != null) {
        Node current2 = polynomial2.getHead(); // Start iterating through polynomial2 for each term of polynomial1

        // Multiply each term of polynomial1 with each term of polynomial2
        while (current2 != null) {
            // Calculate the coefficient, exponentX, exponentY, and exponentZ for the product term
            int coefficient = current1.getTerm().getCoefficient() * current2.getTerm().getCoefficient();
            int exponentX = current1.getTerm().getExponentX() + current2.getTerm().getExponentX();
            int exponentY = current1.getTerm().getExponentY() + current2.getTerm().getExponentY();
            int exponentZ = current1.getTerm().getExponentZ() + current2.getTerm().getExponentZ();

            // Create the product term
            Term term = new Term(coefficient, exponentX, exponentY, exponentZ);

            // Add the product term to the result polynomial
            result.addTerm(term);

            // Move to the next term in polynomial2
            current2 = current2.getNext();
        }

        // Move to the next term in polynomial1
        current1 = current1.getNext();
    }

    return result; // Return the result polynomial
}

    private static String formatPolynomial(Polynomial polynomial) {
        if (polynomial.isEmpty()) {
            return "0"; // Return "0" if the polynomial is empty
        }
        StringBuilder result = new StringBuilder();
        Node current = polynomial.getHead();
        while (current != null) {
            Term term = current.getTerm();
            int coefficient = term.getCoefficient();
            int exponentX = term.getExponentX();
            int exponentY = term.getExponentY();
            int exponentZ = term.getExponentZ();

            // Append the coefficient
            if (result.length() > 0) {
                if (coefficient > 0) {
                    result.append("+"); // Add '+' sign for positive coefficients
                }
            } else {
                // Append the negative sign for the first term if the coefficient is negative
                if (coefficient < 0) {
                    result.append("-");
                }
            }
            if (coefficient != 1 && coefficient != -1) {
                result.append(coefficient); // Append the coefficient if not 1 or -1
            }

            // Append the variables and their exponents
            if (exponentX > 0) {
                result.append("x");
                if (exponentX > 1) {
                    result.append(exponentX);
                }
            }
            if (exponentY > 0) {
                result.append("y");
                if (exponentY > 1) {
                    result.append(exponentY);
                }
            }
            if (exponentZ > 0) {
                result.append("z");
                if (exponentZ > 1) {
                    result.append(exponentZ);
                }
            }
            current = current.getNext();
        }
        return result.toString();
    }


}
