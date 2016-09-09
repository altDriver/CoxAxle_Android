package coxaxle.cox.automotive.com.android.model;

import java.util.ArrayList;

/**
 * Created by Kishore on 9/8/2016.
 */
public class ModelsInfo {

    String modelCode;
    String modelName;
    ArrayList<TrimsInfo> trimsInfosArrayList;

    ModelsInfo(String strModelCode, String strMaodelName, ArrayList<TrimsInfo> trimsInfosArrayList){

        this.modelCode = strModelCode;
        this.modelName = strMaodelName;
        this.trimsInfosArrayList = trimsInfosArrayList;

    }
}
