package Password_Strength_Tester_and_Cracker;

public class PasswordStrengthChecker {

    public static String checkStrength(String password) {
        int score = 0;

        if (password.length() >= 8)
            score++;
        if (password.matches(".*[A-Z].*"))
            score++;
        if (password.matches(".*[a-z].*"))
            score++;
        if (password.matches(".*\\d.*"))
            score++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))
            score++;

        if (score <= 2)
            return "Weak";
        else if (score == 3 || score == 4)
            return "Moderate";
        else
            return "Strong";
    }
}
