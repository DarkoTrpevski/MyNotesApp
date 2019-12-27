package com.darko.mynotesapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darko.mynotesapp.R;
import com.darko.mynotesapp.database.Note;
import com.darko.mynotesapp.ui.adapter.NotesAdapter;
import com.darko.mynotesapp.ui.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    //BUTTERKNIFE BINDING
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @OnClick(R.id.addNote)
    void addNewNote() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<Note> notesList;
    private NotesAdapter notesAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.manage_notes);

        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        //INITIALIZE THE RECYCLER VIEW
        initRecyclerView();
        //INITIALIZE THE VIEW MODEL
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<Note>> notesObserver = new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesList.clear();
                notesList.addAll(notes);

                if (notesAdapter == null) {
                    notesAdapter = new NotesAdapter(notesList, MainActivity.this);
                    recyclerView.setAdapter(notesAdapter);
                } else {
                    notesAdapter.notifyDataSetChanged();
                }
            }
        };
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //Observe the LiveData notes
        mainViewModel.notes.observe(this, notesObserver);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_all) {
            deleteSampleData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteSampleData() {
        mainViewModel.deleteSampleData();
    }
}