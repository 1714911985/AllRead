package com.example.allreader.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.allreader.room.dao.FilesDao;

import com.example.allreader.room.entity.Files;

/**
 * Author: Eccentric
 * Created on 2024/6/7 11:04.
 * Description: com.example.allreader.room.database.AppDatabase
 */
@Database(entities = {Files.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilesDao fileDao();

    private volatile static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
