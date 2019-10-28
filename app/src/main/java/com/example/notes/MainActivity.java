package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.AdaptedNote;
import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.Repositories.TagToNoteRepository;
import com.example.notes.ViewModels.MainViewModel;
import com.example.notes.ViewModels.NoteViewModel;
import com.example.notes.ViewModels.TagToNoteViewModel;
import com.example.notes.ViewModels.TagViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getAllTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(@Nullable final List<Tag> allTags) {
                // Update the cached copy of the notes in the adapter.
                if (allTags != null) {
                    adapter.getTags(allTags);
                }
            }
        });

        viewModel.getAllNotesByDate().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> adaptedNotes) {
                // Update the cached copy of the notes in the adapter.
                if (adaptedNotes != null) {
                    adapter.setNotes(adaptedNotes);
                }
            }
        });

        viewModel.getFullData().observe(this, new Observer<List<TagToNote>>() {
            @Override
            public void onChanged(@Nullable final List<TagToNote> tagToNotes) {
                // Update the cached copy of the notes in the adapter.
                if (tagToNotes != null) {

                    adapter.getFullData(tagToNotes);
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NoteActivity.NOTE_TITLE), data.getStringExtra(NoteActivity.NOTE_BODY));

            ArrayList<Tag> tags = new ArrayList<>();
            for (String title : Objects.requireNonNull(data.getStringArrayListExtra(NoteActivity.TAGS))) {
                Tag t = viewModel.getTagByTitle(title);
                tags.add(t);
            }

            viewModel.insert(note, tags);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.app_name,
                    Toast.LENGTH_LONG).show();
        }
    }

}
