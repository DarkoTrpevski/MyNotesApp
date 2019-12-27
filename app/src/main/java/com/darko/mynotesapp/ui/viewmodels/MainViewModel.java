package com.darko.mynotesapp.ui.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.darko.mynotesapp.database.AppRepository;
import com.darko.mynotesapp.database.Note;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Note>> notes;
    private AppRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application.getApplicationContext());
        notes = repository.notes;
    }

    public void deleteSampleData() {
        repository.deleteSampleData();
    }
}