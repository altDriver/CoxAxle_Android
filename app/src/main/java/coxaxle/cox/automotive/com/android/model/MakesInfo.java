package coxaxle.cox.automotive.com.android.model;

import java.util.ArrayList;

/**
 * Created by Kishore on 9/8/2016.
 */
public class MakesInfo {

     String makeCode;
     ArrayList<ModelsInfo> modelsInfoArrayList;

    public MakesInfo(String makeCode, ArrayList<ModelsInfo> modelsInfoArrayList){

        this.makeCode = makeCode;
        this.modelsInfoArrayList = modelsInfoArrayList;
    }


}
