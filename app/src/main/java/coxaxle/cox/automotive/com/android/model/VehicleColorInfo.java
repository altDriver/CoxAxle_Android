package coxaxle.cox.automotive.com.android.model;

/**
 * Created by Kishore on 9/19/2016.
 */
public class VehicleColorInfo {

    String code;
    String name;
    String rgbHex;
    String shortName;

    public VehicleColorInfo(String name, String rgbHex) {
        this.name = name;
        this.rgbHex = rgbHex;
    }

    public String getName() {
        return name;
    }

    public String getRgbHex() {
        return rgbHex;
    }
}
