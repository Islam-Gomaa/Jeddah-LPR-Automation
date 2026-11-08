package api;

import java.util.concurrent.ThreadLocalRandom;

public class SaudiPlateGenerator {

    /*
     * Saudi License Plate Letters
     */

    private static final char[] ENGLISH_PLATE_LETTERS = {
            'A', 'B', 'J', 'D', 'R', 'S', 'X', 'T', 'E',
            'G', 'K', 'L', 'Z', 'N', 'H', 'U', 'V'
    };

    private static final char[] ARABIC_PLATE_LETTERS = {
            'ا', 'ب', 'ح', 'د', 'ر', 'س', 'ص', 'ط', 'ع',
            'ق', 'ك', 'ل', 'م', 'ن', 'ه', 'و', 'ى'
    };

    /*
     * Generate complete Saudi plate
     */

    public static SaudiPlate generateRandomPlate() {

        int plateNumber = generatePlateNumber();

        String plateEn = generateEnglishPlate(plateNumber);

        String plateAr = generateArabicPlate(
                plateNumber,
                plateEn
        );

        return new SaudiPlate(
                plateNumber,
                plateEn,
                plateAr
        );
    }

    /*
     * Generate random 4-digit number
     */

    private static int generatePlateNumber() {

        return ThreadLocalRandom.current()
                .nextInt(1000, 10000);
    }

    /*
     * Generate English plate
     * Example: 1234 ABC
     */

    private static String generateEnglishPlate(int number) {

        StringBuilder letters = new StringBuilder();

        for (int i = 0; i < 3; i++) {

            char letter = ENGLISH_PLATE_LETTERS[
                    ThreadLocalRandom.current()
                            .nextInt(ENGLISH_PLATE_LETTERS.length)
                    ];

            letters.append(letter);
        }

        return number + " " + letters;
    }

    /*
     * Generate Arabic plate
     * Example: حبأ 1234
     */

    private static String generateArabicPlate(
            int number,
            String englishPlate) {

        String englishLetters = englishPlate.substring(
                englishPlate.indexOf(" ") + 1
        );

        StringBuilder arabicLetters = new StringBuilder();

        for (int i = englishLetters.length() - 1; i >= 0; i--) {

            char englishLetter = englishLetters.charAt(i);

            char arabicLetter = getArabicEquivalent(englishLetter);

            arabicLetters.append(arabicLetter);
        }

        return arabicLetters + " " + number;
    }

    /*
     * Convert English letter to Arabic equivalent
     */

    private static char getArabicEquivalent(char englishLetter) {

        for (int i = 0; i < ENGLISH_PLATE_LETTERS.length; i++) {

            if (ENGLISH_PLATE_LETTERS[i] == englishLetter) {

                return ARABIC_PLATE_LETTERS[i];
            }
        }

        throw new IllegalArgumentException(
                "Unsupported Saudi plate letter: " + englishLetter
        );
    }
}