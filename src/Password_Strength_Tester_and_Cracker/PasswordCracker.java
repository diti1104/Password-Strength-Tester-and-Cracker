package Password_Strength_Tester_and_Cracker;

public class PasswordCracker {

    public static String crackPassword(String target) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int maxLength = 5;

        for (int length = 1; length <= maxLength; length++) {
            String result = crackRecursive("", chars, target, length);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    private static String crackRecursive(String prefix, String chars, String target, int maxLength) {
        if (prefix.length() == maxLength) {
            if (prefix.equals(target)) {
                return prefix;
            }
            return null;
        }

        for (int i = 0; i < chars.length(); i++) {
            String attempt = crackRecursive(prefix + chars.charAt(i), chars, target, maxLength);
            if (attempt != null) {
                return attempt;
            }
        }

        return null;
    }
}
