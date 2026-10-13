package data;

public final class VehicleTestData {

    private static String plateEn;
    private static String plateAr;
    private static String plateNumber;
    private static String plateLettersEn;
    private static String plateLettersAr;



    private VehicleTestData() {
    }

    public static void setVehicle(
            String englishPlate,
            String arabicPlate) {

        plateEn = englishPlate;
        plateAr = arabicPlate;
        plateNumber = englishPlate.replaceAll("[^0-9]", "");
        plateLettersEn = plateEn.replaceAll("[^A-Za-z]", "");
        plateLettersAr = plateAr.replaceAll("[^\\u0600-\\u06FF]", "");

    }

    public static String getPlateEn() {
        return plateEn;
    }

    public static String getPlateAr() {
        return plateAr;
    }

    public static String getPlateNumber() {
        return plateNumber;
    }

    public static String getPlateLettersEn() {
        return plateLettersEn;
    }

    public static String getPlateLettersAr() {
        return plateLettersAr;
    }
}