package com.example.allreader;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.dao.FilesDao_Impl;
import com.example.allreader.room.database.AppDatabase;
import com.example.allreader.room.database.AppDatabase_Impl;
import com.example.allreader.room.entity.Files;
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.observer.ScreenshotObserver;
import com.example.allreader.utils.util.FileUtils;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "PermanentChannel";
    private static final String Notification_Title = "All Reader";
    private ScreenshotObserver screenshotObserver;
    private AlertDialog goSettingDialog;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 12;
    private FilesDao filesDao;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initData();

        createNotificationChannel();//创建通知渠道
        sendNotification();// 发送通知


        // 注册截屏监听器
        screenshotObserver = new ScreenshotObserver(this, new Handler(Looper.getMainLooper()));
        ScreenshotObserver.registerScreenshotObserver(this);

        // 创建通知渠道
        createScreenshotNotificationChannel();

        checkAndRequestPermissionThenScan();
    }

    private void initData() {
        appDatabase = AppDatabase.getInstance(this);
        filesDao = new FilesDao_Impl(appDatabase);
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
                .setOngoing(true)// 设置为持续通知，点击通知不会消失
                .build();

        // 发送通知
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, build);

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

    private void createScreenshotNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            String channelName = "截屏通知";//Screenshot notification

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void checkAndRequestPermissionThenScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkAndRequestAllFilePermission();
        } else {
            checkAndRequestStoragePermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkAndRequestAllFilePermission() {
        if (Environment.isExternalStorageManager()) {
            startScan();
        } else {
            setGoSettingDialog(this, getResources().getText(R.string.needAllFilePermission).toString(), Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }


    private void checkAndRequestStoragePermission() {
        try {
            if (checkStoragePermission()) {
                startScan();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScan();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(this, getResources().getText(R.string.hasNoPermission), Toast.LENGTH_SHORT).show();
                    } else {
                        setGoSettingDialog(this, getResources().getText(R.string.needStoragePermission).toString(), Settings.ACTION_APPLICATION_DETAILS_SETTINGS, requestCode);
                    }
                }
        }
    }

    private void setGoSettingDialog(Context context, String title, String action, int requestCode) {
        if (goSettingDialog == null) {
            goSettingDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setPositiveButton(getResources().getText(R.string.toAuthorize), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse("package:" + getPackageName());
                            Intent intent = new Intent(action, uri);
                            startActivityForResult(intent, requestCode);
                        }
                    }).create();
        }
        goSettingDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    Toast.makeText(this, getResources().getText(R.string.hasNoPermission), Toast.LENGTH_SHORT).show();
                } else {
                    startScan();
                }
            } else {
                checkAndRequestStoragePermission();
            }
        }
    }

    //文件扫描
    private void startScan() {
        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                filesDao.deleteAll();
                File externalFilesDirs = Environment.getExternalStorageDirectory();
                Log.e("Test_tag", "run: " + externalFilesDirs.getAbsolutePath() );
                ConcurrentLinkedQueue<File> fileQueue = new ConcurrentLinkedQueue<>();
                fileQueue.offer(externalFilesDirs);
                while (!fileQueue.isEmpty()) {
                    File file = fileQueue.poll();
                    if (file == null || !file.exists()) {
                        continue;
                    }
                    File[] files = file.listFiles();
                    if (files == null) {
                        Log.e("TAG", "getExternalStoragePublicDirectory 没子目录 " );
                        continue;
                    }
                    Log.e("TAG", "getExternalStoragePublicDirectory 有子目录 " );

                    for (File childFile : files) {
                        if (childFile == null) {
                            continue;
                        }

                        if (childFile.isDirectory()) {
                            //文件夹  加到队列里
                            fileQueue.offer(childFile);
                            Log.e("文件夹", childFile.getAbsolutePath() );
                        } else {
                            if (FileUtils.isDocument(childFile)) {
                                Log.e("文件", childFile.getAbsolutePath() );
                                //文档类型文件  直接加到数据库里面
                                filesDao.insert(FileUtils.fileToFileEntity(childFile));
                            }
                        }
                    }
                }
                sendBroadcast();
            }
        });



    }

    private void sendBroadcast() {
        Intent intent = new Intent("com.example.allreader.ACTION_SCAN_FINISHED");
        sendBroadcast(intent);
    }

}