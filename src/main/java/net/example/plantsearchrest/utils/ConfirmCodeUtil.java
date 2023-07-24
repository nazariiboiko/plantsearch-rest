package net.example.plantsearchrest.utils;

public class ConfirmCodeUtil {
    private static final int min = 100000;
    private static final int max = 999999;
    public static int generate6DigitCode() {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }
}
