package lytestech.com.myfirebase;

import android.content.Intent;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.util.Random;

/**
 * Created by gideon on 08/12/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public String TAG = "MyFirebaseInstanceIDService";


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

        getApplicationContext().sendBroadcast(new Intent(Constants.TOKEN_BROADCAST));

        Prefs.putString(Constants.FIREBASE_TOKEN, refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {


        Random rand = new Random();
        int  userId = rand.nextInt(50) + 1;

            AQuery aq = new AQuery(this);
            //String url = Constants.UPDATE_PUSH_TOKEN_URL + "?user_id=" + userId + "&push_token=" + token;
            String url = "" + "?user_id=" + userId + "&push_token=" + token;
            aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    Log.d(TAG, "Push token update: " + object.toString());
                }
            });

    }


}
