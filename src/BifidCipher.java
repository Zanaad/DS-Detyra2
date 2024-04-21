import java.awt.Point;
import java.util.*;

public final class BifidCipher {

    public static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";  // Default alphabet as key
    public static final String ALPHABETNUMBER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true; // Var reponsible for keeping the program running

        while (keepRunning) {
            System.out.println("Enter your message:");
            String message = scanner.nextLine().toUpperCase(); 

            System.out.println("Choose Polybius square size (5 or 6):"); 
            int size = scanner.nextInt();
            scanner.nextLine(); // Consume the remaining newline

            // Adjusting the input processing based on the size of the square
            if (size == 5) {
                message = message.replace("J", "I"); // J is excluded from the 5x5 polyb. square
            }

            System.out.println("Enter your secret key (press Enter for standard alphabet):");
            String keyInput = scanner.nextLine().toUpperCase().replace(" ", ""); // Polybian square doesn't include whitespaces
            if (size == 5) {
                keyInput = keyInput.replace("J", "I"); // Ref line 23
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
            } // takes e or d for input and executes encryption or decryption sequentially, else prints an error message

            System.out.println("Do you want to continue? (Y/N)");
            String continueChoice = scanner.nextLine().toUpperCase(); // prompts the user to start or end the program, if the word does not start with y, the variable for keeping the response running is set to false and so ending the program
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
    private Map<Character, Point> coordinates = new HashMap<>(); // Initalizes the coordinates for the character in a hashmap

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
        } // Linked hash set that loops for each character of the function string input, digits or letters are accepted, excludes duplicates and handles errors on max size exceeding

        String additionalChars = (maxLength == 36) ? BifidCipher.ALPHABETNUMBER : BifidCipher.ALPHABET;
        for (char c : additionalChars.toCharArray()) {
            if (!seen.contains(c) && seen.size() < maxLength) {
                seen.add(c);
            }
        } // checks if max length is 36 and assigns it to alphabetnumber if so and alphabet otherwise, and adds them to the seen

        StringBuilder builder = new StringBuilder();
        seen.forEach(builder::append);
        return builder.toString(); //seen characters appended to the string builder
    }

    private void setUpGrid(int size, String chars) {
        grid = new char[size][size];
        int index = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char currentChar = chars.charAt(index);
                grid[i][j] = currentChar;   //indexes through each character of the key and assigns a grid position as well as a coordinate afterwards
                coordinates.put(currentChar, new Point(i, j));
                if (currentChar == 'I') coordinates.put('J', new Point(i, j)); // Handle I/J substitution
                if (++index >= chars.length()) return; // Exit once all chars are placed
            }
        }
    }

    public String encrypt(String aText) {
        String aText1 = aText.replace(" ", ""); 

        List<Integer> rowOne = new ArrayList<>();
        List<Integer> rowTwo = new ArrayList<>();

        for (char ch : aText1.toCharArray()) {
            Point coordinate = coordinates.get(Character.toUpperCase(ch));
            if (coordinate == null) {
                throw new IllegalArgumentException("Character " + ch + " not in Polybius square!!!");
            }
            rowOne.add(coordinate.x);
            rowTwo.add(coordinate.y);   
        } // declares 2 array lists, iterates over each character of the text, and adds x and y coordinate to 1st and 2nd arrlist respectively, throws error of grid non-existent characters

        rowOne.addAll(rowTwo);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rowOne.size() - 1; i += 2) {
            result.append(grid[rowOne.get(i)][rowOne.get(i + 1)]);
        }
        // merges the coordinate rows, and assigns coordinates based on 2 sequential numbers

        for (int i = 0; i < aText.length(); i++){
            char ch = aText.charAt(i);
            if(Character.isWhitespace(ch)) result.insert(i, ch); //addition of whitespaces, reused on decryption
        }

        return result.toString();
    }

    public String decrypt(String aText) {
        String aText1 = aText.replace(" ", "");

        List <Integer> rowOne = new ArrayList<>();
        List <Integer> rowTwo = new ArrayList<>();

        for (char ch : aText1.toCharArray()) {
            Point coordinate = coordinates.get(Character.toUpperCase(ch));
            if (coordinate == null) {
                throw new IllegalArgumentException("Character " + ch + " is not found in the Polybius Square!!!");
            }
            rowOne.add(coordinate.x);
            rowTwo.add(coordinate.y);
        }

        StringBuilder result2 = new StringBuilder();
        for(int i = 0; i < rowOne.size(); i++) {
            int size = rowOne.size();
            int half = (int)(Math.floor(size/2.0));
            int index = (int) (Math.ceil(i/2.0));

            if(size % 2 == 0) { // If nr of char is even coordinate assignment is dealt between the same row, paralel line way
                if (i % 2 == 0) {
                    result2.append(grid[rowOne.get(index)] [rowOne.get(index + half)]);
                }
                else result2.append(grid[rowTwo.get(index-1)] [rowTwo.get(index + half - 1)]);
            }
            else { // If nr of char is odd, coordinate assignment is dealt between the 2 different rows, zig-zag way
                if (i % 2 == 0) {
                    result2.append(grid[rowOne.get(index)] [rowTwo.get(index + half)]);//+1//
                }
                else result2.append(grid[rowTwo.get(index-1)] [rowOne.get(index + half)]);
            }
        }
        for(int i = 0; i < aText.length(); i++){
            char ch = aText.charAt(i);
            if(Character.isWhitespace(ch)) result2.insert(i, ch);
        }
        return result2.toString();
    }

    public void display() {
        for (char[] row : grid) {
            System.out.println(Arrays.toString(row));
        } 
         //Iterates between rows of the grid, prints the grid 
    }
}