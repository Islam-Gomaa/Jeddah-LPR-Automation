package utils;

import java.security.SecureRandom;

public final class TestDataGenerator {

    private static final String ENGLISH_LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final String ARABIC_LETTERS =
            "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";

    private static final int DEFAULT_SUFFIX_LENGTH = 6;

    private static final SecureRandom RANDOM = new SecureRandom();

    private TestDataGenerator() {
        // Utility class
    }

    public static String generateEnglish(String baseValue) {

        validate(baseValue);

        return baseValue + randomLetters(
                ENGLISH_LETTERS,
                DEFAULT_SUFFIX_LENGTH
        );
    }

    public static String generateArabic(String baseValue) {

        validate(baseValue);

        return baseValue + randomLetters(
                ARABIC_LETTERS,
                DEFAULT_SUFFIX_LENGTH
        );
    }

    private static String randomLetters(
            String characters,
            int length) {

        StringBuilder result =
                new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            result.append(
                    characters.charAt(
                            RANDOM.nextInt(characters.length())
                    )
            );
        }

        return result.toString();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {

            throw new IllegalArgumentException(
                    "Test data value cannot be null or empty"
            );
        }
    }
}