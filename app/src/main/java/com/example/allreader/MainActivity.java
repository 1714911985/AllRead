package com.example.allreader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "PermanentChannel";
    private static final String Notification_Title = "All Reader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createNotificationChannel();//创建通知渠道
        sendNotification();// 发送通知
    }

    private void sendNotification() {
        // 设置点击通知后的操作
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification build = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_app)
                .setContentTitle(Notification_Title)
                .setContentText(getResources().getText(R.string.notificationText))
                .setAutoCancel(false)  // 设置点击后不自动取消
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();// 设置为持续通知，点击通知不会消失

        // 发送通知
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID,build);

    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "PermanentChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);

            // 注册通知渠道
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancelAll();
    }
}