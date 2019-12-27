package com.darko.mynotesapp.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.darko.mynotesapp.R;
import com.darko.mynotesapp.database.Note;
import com.darko.mynotesapp.ui.viewmodels.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.darko.mynotesapp.utils.Constants.EDITING_KEY;
import static com.darko.mynotesapp.utils.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    public static final String TAG = "EditorActivity";

    @BindView(R.id.note_edit_text)
    EditText editText;

    private EditorViewModel editorViewModel;
    private boolean isNewNote, isEditNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            isEditNote = savedInstanceState.getBoolean(EDITING_KEY);
        }
        //INITIALIZE THE VIEW MODEL
        initViewModel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {
        editorViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        editorViewModel.mutableLiveNote.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                if (note != null && !isEditNote) {
                    editText.setText(note.getText());
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //IF THERE ARE NO EXTRAS, IT IS A NEW NOTE
            setTitle(getString(R.string.new_note));
            isNewNote = true;
        } else {
            // IF THERE ARE EXTRAS, GET ITS ID AND LOAD THE DATA FROM THAT ID
            setTitle(getString(R.string.edit_note));
            int noteId = extras.getInt(NOTE_ID_KEY);
            editorViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isNewNote) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            editorViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        editorViewModel.saveNote(editText.getText().toString());
        finish();
    }
}