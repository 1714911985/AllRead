package com.example.allreader.utils.observer;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.RingtoneManager;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

import androidx.core.app.NotificationCompat;

import com.example.allreader.R;

/**
 * Author: Eccentric
 * Created on 2024/6/6 14:09.
 * Description: 截屏监听器
 */
public class ScreenshotObserver extends ContentObserver {

    private Context mContext;

    public ScreenshotObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        // 检测到用户截屏，发送通知
        sendNotification();
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "channel_id")
                .setContentTitle(mContext.getResources().getText(R.string.screenshotNotification))//Screenshot notification
                .setContentText(mContext.getResources().getText(R.string.screenshotNotificationText))//You have taken a screenshot
                .setSmallIcon(R.drawable.logo_app)
                .setAutoCancel(true);

        // 发送通知
        notificationManager.notify(2, builder.build());

        // 播放通知声音
        try {
            Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(mContext, notificationUri);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 在合适的地方注册截屏监听器
    public static void registerScreenshotObserver(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        ScreenshotObserver observer = new ScreenshotObserver(context, new Handler(Looper.getMainLooper()));
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true, observer);
    }

    // 在适当的地方取消注册截屏监听器
    public static void unregisterScreenshotObserver(Context context, ScreenshotObserver observer) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.unregisterContentObserver(observer);
    }
}