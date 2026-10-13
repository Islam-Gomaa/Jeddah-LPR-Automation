package utils;

public final class TextUtils {

    private TextUtils() {
    }

    public static String normalizeArabicNumbers(String text) {
        if (text == null) {
            return null;
        }

        return text
                // Arabic-Indic digits → English digits
                .replace('٠', '0')
                .replace('١', '1')
                .replace('٢', '2')
                .replace('٣', '3')
                .replace('٤', '4')
                .replace('٥', '5')
                .replace('٦', '6')
                .replace('٧', '7')
                .replace('٨', '8')
                .replace('٩', '9')

                // Normalize question mark
                .replace('؟', '?')

                // Normalize directional marks
                .replace("\u200E", "")
                .replace("\u200F", "")
                .replace("\u202A", "")
                .replace("\u202B", "")
                .replace("\u202C", "")
                .replace("\u202D", "")
                .replace("\u202E", "")

                // Normalize whitespace
                .replaceAll("\\s+", " ")

                .trim();
    }
}