package com.darko.mynotesapp.database;


import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private static AppRepository ourInstance;

    public LiveData<List<Note>> notes;

    private AppDatabase database;

    private Executor executor = Executors.newSingleThreadExecutor();

    //--------------------------------------------------------------------------------------------------
    //SINGLETON CONSTRUCTOR
    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    //CONSTRUCTOR
    private AppRepository(Context context) {
        database = AppDatabase.getInstance(context);
        notes = getAllNotes();
    }

    private LiveData<List<Note>> getAllNotes() {
        return database.noteDataAccessObject().getAll();
    }

    public void deleteSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.noteDataAccessObject().deleteAll();
            }
        });
    }

    public void insertNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.noteDataAccessObject().insertNote(note);
            }
        });
    }

    public void deleteNote(final Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.noteDataAccessObject().deleteNote(note);
            }
        });
    }

    public Note getNoteById(int noteId) {
        return database.noteDataAccessObject().getNoteById(noteId);
    }
}