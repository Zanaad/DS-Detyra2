import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunningMain = true;
        while (keepRunningMain) {
            System.out.println("Choose cipher:");
            System.out.println("1. Scytale Cipher");
            System.out.println("2. Bifid Cipher");
            String cipherChoiceStr = scanner.nextLine();

            int cipherChoice;
            try {
                cipherChoice = Integer.parseInt(cipherChoiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            if (cipherChoice == 1) {
                boolean keepRunningScytale = true;
                while(keepRunningScytale) {
                    System.out.println("Enter plaintext:");
                    String plaintext = scanner.nextLine();
                    System.out.println("Enter key:");
                    int key = Integer.parseInt(scanner.nextLine());
                    String encrypted = ScytaleCipher.encrypt(plaintext, key);
                    System.out.println("Encrypted text: " + encrypted);
                    System.out.println("Do you want to continue on this cypher? (Y/N)");
                    String continueChoice = scanner.nextLine().toUpperCase();
                    if (!continueChoice.startsWith("Y")) {
                        keepRunningScytale = false;
                    }
                }
            } else if (cipherChoice == 2) {
                BifidCipher.main(new String[]{});
            } else {
                System.out.println("Invalid choice. Exiting...");
                break;
            }
            System.out.println("Do you want to continue the program? (Y/N)");

            String continueChoice = scanner.nextLine().toUpperCase();
            if (!continueChoice.startsWith("Y")) {
                keepRunningMain = false;
            }
        }
        scanner.close();
        System.out.println("Program has ended.");
    }
}