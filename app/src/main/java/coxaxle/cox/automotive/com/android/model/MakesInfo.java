package coxaxle.cox.automotive.com.android.model;

import java.util.ArrayList;

/**
 * Created by Kishore on 9/8/2016.
 */
public class MakesInfo {

    String makeCode;
    String makeName;
    ArrayList<ModelsInfo> modelsInfoArrayList;

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }


    public String getMakeCode() {
        return makeCode;
    }

    public void setMakeCode(String makeCode) {
        this.makeCode = makeCode;
    }

    public ArrayList<ModelsInfo> getModelsInfoArrayList() {
        return modelsInfoArrayList;
    }

    public void setModelsInfoArrayList(ArrayList<ModelsInfo> modelsInfoArrayList) {
        this.modelsInfoArrayList = modelsInfoArrayList;
    }



    public MakesInfo(String makeCode, String makeName, ArrayList<ModelsInfo> modelsInfoArrayList) {
        this.makeCode = makeCode;
        this.makeName = makeName;
        this.modelsInfoArrayList = modelsInfoArrayList;
    }


}
