package api;

public class SaudiPlate {

    private final int plateNumber;
    private final String plateEn;
    private final String plateAr;

    public SaudiPlate(
            int plateNumber,
            String plateEn,
            String plateAr) {

        this.plateNumber = plateNumber;
        this.plateEn = plateEn;
        this.plateAr = plateAr;
    }

    public int getPlateNumber() {
        return plateNumber;
    }

    public String getPlateEn() {
        return plateEn;
    }

    public String getPlateAr() {
        return plateAr;
    }

    @Override
    public String toString() {

        return "SaudiPlate{" +
                "plateNumber=" + plateNumber +
                ", plateEn='" + plateEn + '\'' +
                ", plateAr='" + plateAr + '\'' +
                '}';
    }
}