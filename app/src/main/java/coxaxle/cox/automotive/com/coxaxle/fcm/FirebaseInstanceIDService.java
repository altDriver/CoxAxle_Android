package coxaxle.cox.automotive.com.coxaxle.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import coxaxle.cox.automotive.com.coxaxle.common.UserSessionManager;

/**
 * Created by kishore on 19-08-2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService{

    UserSessionManager mUserSessionManager;//StoreFCMTokenId

    @Override
    public void onTokenRefresh() {

        //mUserSessionManager = new UserSessionManager();


        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);

        Log.d("Firebasetoken=======",token);
    }

    private void registerToken(final String token) {









       /* OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.71/fcm/register.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}