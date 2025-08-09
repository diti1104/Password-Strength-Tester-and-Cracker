package Password_Strength_Tester_and_Cracker;

import java.util.Scanner;

public class PasswordTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n Password Strength Tester and Cracker");
            System.out.println("1. Check Password Strength");
            System.out.println("2. Crack Password ");
            System.out.println("3. Generate Strong Password");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter a password to check: ");
                    String password = scanner.nextLine();
                    String strength = PasswordStrengthChecker.checkStrength(password);
                    System.out.println("Password Strength: " + strength);
                    break;

                case 2:
                    System.out.print("Enter a simple password to crack (max 5 characters): ");
                    String target = scanner.nextLine();
                    String cracked = PasswordCracker.crackPassword(target);
                    if (cracked != null) {
                        System.out.println(" Password cracked: " + cracked);
                    } else {
                        System.out.println(" Could not crack the password.");
                    }
                    break;

                case 3:
                    System.out.print("Enter desired length of password: ");
                    int length = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    String generated = PasswordGenerator.generatePassword(length);
                    System.out.println("Generated Password: " + generated);
                    break;

                case 4:
                    System.out.println("Exiting. Stay safe!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);

        scanner.close();
    }
}
