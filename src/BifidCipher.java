import java.awt.Point;
import java.util.*;

public final class BifidCipher {

    public static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    public static final String ALPHABETNUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Enter your message:");
            String message = scanner.nextLine().toUpperCase().replace(" ", "");

            System.out.println("Choose Polybius square size (5 or 6):");
            int size = scanner.nextInt();
            scanner.nextLine(); // Consume the remaining newline

            // Adjusting the input processing based on the size of the square
            if (size == 5) {
                message = message.replace("J", "I");
            }

            System.out.println("Enter your secret key (press Enter for standard alphabet):");
            String keyInput = scanner.nextLine().toUpperCase().replace(" ", "");
            if (size == 5) {
                keyInput = keyInput.replace("J", "I");
            }

            String alphabet = (size == 5) ? ALPHABET : ALPHABETNUMBER;
            Bifid bifid;
            if (keyInput.isEmpty()) {
                bifid = new Bifid(size, alphabet); // Standard alphabet or extended with digits
            } else {
                bifid = new Bifid(size, keyInput); // Custom table creation using secret key
            }

            System.out.println("Using Polybius square:");
            bifid.display();

            System.out.println("Do you want to (E)ncrypt or (D)ecrypt?");
            char edChoice = scanner.nextLine().toUpperCase().charAt(0);

            try {
                if (edChoice == 'E') {
                    String encrypted = bifid.encrypt(message);
                    System.out.println("Encrypted: " + encrypted);
                } else if (edChoice == 'D') {
                    String decrypted = bifid.decrypt(message);
                    System.out.println("Decrypted: " + decrypted);
                } else {
                    System.out.println("Invalid option. Please choose 'E' for encrypt or 'D' for decrypt.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Do you want to continue? (Y/N)");
            String continueChoice = scanner.nextLine().toUpperCase();
            if (!continueChoice.startsWith("Y")) {
                keepRunning = false;
            }
        }
        scanner.close();
        System.out.println("Program has ended.");
    }
}

final class Bifid {

    private char[][] grid;
    private Map<Character, Point> coordinates = new HashMap<>();

    public Bifid(int size, String aText) {
        String uniqueCharacters = getUniqueCharacters(aText, size * size);
        setUpGrid(size, uniqueCharacters);
    }

    private String getUniqueCharacters(String text, int maxLength) {
        Set<Character> seen = new LinkedHashSet<>();
        for (char c : text.toCharArray()) {
            if ((Character.isLetter(c) || Character.isDigit(c)) && !seen.contains(c) && seen.size() < maxLength) {
                seen.add(Character.toUpperCase(c));
            }
        }

        String additionalChars = (maxLength == 36) ? BifidCipher.ALPHABETNUMBER : BifidCipher.ALPHABET;
        for (char c : additionalChars.toCharArray()) {
            if (!seen.contains(c) && seen.size() < maxLength) {
                seen.add(c);
            }
        }

        StringBuilder builder = new StringBuilder();
        seen.forEach(builder::append);
        return builder.toString();
    }

    private void setUpGrid(int size, String chars) {
        grid = new char[size][size];
        int index = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char currentChar = chars.charAt(index);
                grid[i][j] = currentChar;
                coordinates.put(currentChar, new Point(i, j));
                if (currentChar == 'I') coordinates.put('J', new Point(i, j)); // Handle I/J substitution
                if (++index >= chars.length()) return; // Exit once all chars are placed
            }
        }
    }

    public String encrypt(String aText) {
        List<Integer> rowOne = new ArrayList<>();
        List<Integer> rowTwo = new ArrayList<>();
        for (char ch : aText.toCharArray()) {
            Point coordinate = coordinates.get(Character.toUpperCase(ch));
            if (coordinate == null) {
                throw new IllegalArgumentException("Character " + ch + " not in Polybius square");
            }
            rowOne.add(coordinate.x);
            rowTwo.add(coordinate.y);
        }

        rowOne.addAll(rowTwo);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rowOne.size() - 1; i += 2) {
            result.append(grid[rowOne.get(i)][rowOne.get(i + 1)]);
        }
        return result.toString();
    }

    public String decrypt(String aText) {
        return "decrypt";
    }

    public void display() {
        for (char[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }
}
