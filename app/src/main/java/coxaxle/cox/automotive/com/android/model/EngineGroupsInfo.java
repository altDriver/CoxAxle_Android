package coxaxle.cox.automotive.com.android.model;

/**
 * Created by Kishore on 9/19/2016.
 */
public class EngineGroupsInfo {


    String code;
    String displayOrder;
    String name;
    String shortName;


    public EngineGroupsInfo(String code, String displayOrder, String name, String shortName) {
        this.code = code;
        this.displayOrder = displayOrder;
        this.name = name;
        this.shortName = shortName;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }





}
