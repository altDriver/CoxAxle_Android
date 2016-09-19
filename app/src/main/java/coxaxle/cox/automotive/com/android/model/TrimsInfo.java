package coxaxle.cox.automotive.com.android.model;

/**
 * Created by Kishore on 9/8/2016.
 */


public class TrimsInfo {



    String trimCode;
    String trimName;

    public TrimsInfo(String strTrimCode, String strTrimName){

        trimCode = strTrimCode;
        trimName = strTrimName;
    }

    public String getTrimCode() {
        return trimCode;
    }

    public void setTrimCode(String trimCode) {
        this.trimCode = trimCode;
    }

    public String getTrimName() {
        return trimName;
    }

    public void setTrimName(String trimName) {
        this.trimName = trimName;
    }
}
