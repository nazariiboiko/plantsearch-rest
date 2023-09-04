package net.example.plantsearchrest.utils;

/**
 * The ConfirmCodeUtil class provides utility methods for generating 6-digit confirmation codes.
 */
public class ConfirmCodeUtil {
    private static final int MIN_CODE = 100000;
    private static final int MAX_CODE = 999999;

    /**
     * Generates a random 6-digit confirmation code.
     *
     * @return A randomly generated 6-digit code.
     */
    public static int generate6DigitCode() {
        return (int) (Math.floor(Math.random() * (MAX_CODE - MIN_CODE + 1)) + MAX_CODE);
    }
}
