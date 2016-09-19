package coxaxle.cox.automotive.com.android.model;

import java.util.ArrayList;

/**
 * Created by Kishore on 9/8/2016.
 */
public class ModelsInfo {

    String modelCode;
    String modelName;
    ArrayList<TrimsInfo> trimsInfosArrayList;

   public ModelsInfo(String strModelCode, String strModelName, ArrayList<TrimsInfo> trimsInfosArrayList){

        this.modelCode = strModelCode;
        this.modelName = strModelName;
        this.trimsInfosArrayList = trimsInfosArrayList;

    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public ArrayList<TrimsInfo> getTrimsInfosArrayList() {
        return trimsInfosArrayList;
    }

    public void setTrimsInfosArrayList(ArrayList<TrimsInfo> trimsInfosArrayList) {
        this.trimsInfosArrayList = trimsInfosArrayList;
    }
}
