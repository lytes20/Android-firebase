package lytestech.com.myfirebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gideon on 08/12/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From : " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body : " + remoteMessage.getNotification().getBody());

        notifyUser(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
    }

    public void notifyUser(String from, String notification){
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        //myNotificationManager.showNotification(from, notification, new Intent(getApplicationContext(), MainActivity.class));
        myNotificationManager.addNotification(from, notification);
    }
}
