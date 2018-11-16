package com.ndimension.smartlibrary.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.activity.MainActivity;
import com.ndimension.smartlibrary.utility.Pref;


import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private Pref pref;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().toString());
        pref = new Pref(this);
        String msg  = remoteMessage.getData().get("body");
        String title  = remoteMessage.getData().get("title");
        if (TextUtils.isEmpty(pref.getBlockNotification())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotificationAboveOreo(this, title, msg);
            } else {
                sendNotification(msg, title);
            }
        }else {
            if (pref.getBlockNotification().equals("true")){

            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    showNotificationAboveOreo(this, title, msg);
                } else {
                    sendNotification(msg, title);
                }
            }
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotificationAboveOreo(this, title, msg);
        } else {
            sendNotification(msg, title);
        }*/
    }
    private void sendNotification(String messageBody,String title) {
        Random rn = new Random();
        int range = 99 - 1 + 1;
        int randomNum =  rn.nextInt(range) + 0;

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true);

        if (TextUtils.isEmpty(pref.getSound())){
            notificationBuilder.setSound(defaultSoundUri);
        }else {
            if (pref.getSound().equals("true")){
                notificationBuilder.setSound(defaultSoundUri);
            }else {

            }
        }

        if (TextUtils.isEmpty(pref.getVibrate())){
            notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }else {
            if (pref.getVibrate().equals("true")){
                notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            }else {

            }
        }



        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(randomNum, notificationBuilder.build());
    }

    public void showNotificationAboveOreo(Context context, String title, String body) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        Random rn = new Random();
        int range = 99 - 1 + 1;
        int randomNum =  rn.nextInt(range) + 0;


        int notificationId = randomNum;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(body);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        //    mBuilder.setColor(getResources().getColor(R.color.black));
        } else {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        if (TextUtils.isEmpty(pref.getSound())){
            mBuilder.setSound(defaultSoundUri);
        }else {
            if (pref.getSound().equals("true")){
                mBuilder.setSound(defaultSoundUri);
            }else {

            }
        }

        if (TextUtils.isEmpty(pref.getVibrate())){
            mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }else {
            if (pref.getVibrate().equals("true")){
                mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            }else {

            }
        }


        notificationManager.notify(notificationId, mBuilder.build());
    }
}

