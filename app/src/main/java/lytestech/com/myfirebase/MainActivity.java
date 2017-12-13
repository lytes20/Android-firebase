package lytestech.com.myfirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    AQuery aq;
    private BroadcastReceiver broadcastReceiver;
    public final String TAG = "MainActivity";
    public final String DEFAULT_STRING = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq = new AQuery(this);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                aq.id(R.id.tokenTextView).text(Prefs.getString(Constants.FIREBASE_TOKEN, Constants.DEFAULT_STRING));

            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(Constants.TOKEN_BROADCAST));

        if(Prefs.getString(Constants.FIREBASE_TOKEN, Constants.DEFAULT_STRING) != Constants.DEFAULT_STRING){
            aq.id(R.id.tokenTextView).text(Prefs.getString(Constants.FIREBASE_TOKEN, Constants.DEFAULT_STRING));
            Log.d(TAG, "FCM Token: "+ Prefs.getString(Constants.FIREBASE_TOKEN, Constants.DEFAULT_STRING));
        }


        FirebaseMessaging.getInstance().subscribeToTopic("notifications");

        aq.id(R.id.signOutButton).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignOut();
            }
        });

        populateVC();


    }

    public void handleSignOut(){
        FirebaseAuth.getInstance().signOut();
        Prefs.clear();
        updateUI();
    }

    private void updateUI() {
        startActivity(new Intent(MainActivity.this, LoginActivty.class));
    }

    public void populateVC(){
        aq.id(R.id.userNameTextView).text(Prefs.getString("user_name", DEFAULT_STRING));
        aq.id(R.id.userEmailTextView).text(Prefs.getString("user_email", DEFAULT_STRING));
        String stringUrl = Prefs.getString("user_profile_pic", DEFAULT_STRING);
        if(stringUrl != DEFAULT_STRING){
            aq.id(R.id.userProfilePicImage).image(stringUrl);
        }

    }


}
