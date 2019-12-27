package com.darko.mynotesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;

    private static final Object SYNC_LOCK = new Object();

    abstract NoteDAO noteDataAccessObject();

    static AppDatabase getInstance(Context context) {
        //CHECK IF THE DATABASE INSTANCE IS NULL
        if (instance == null) {
            //IF IT IS NULL,WE SYNC ON THE SYNC LOCK OBJECT
            synchronized (SYNC_LOCK) {
                //ANOTHER CHECK TO SEE IF THE DATABASE IS STILL NULL
                if (instance == null) {
                    //CREATE THE ROOM DATABASE INSTANCE
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        //RETURN THE ROOM DATABASE INSTANCE
        return instance;
    }
}