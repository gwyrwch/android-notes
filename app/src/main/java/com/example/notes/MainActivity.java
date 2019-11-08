package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.ViewModels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NoteBaseFragment.OnFragmentInteractionListener {
    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_date:
                viewModel.setAllNotesByDate();
                break;
            case R.id.action_sort_by_title:
                viewModel.setAllNotesByTitle();
                break;
            case R.id.action_drop:
                viewModel.dropCurrentState();
                break;
            default:
                break;
        }
        return true;
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
            System.out.println(requestCode);
            Toast.makeText(
                    getApplicationContext(),
                    R.string.note_edited,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFragmentInteraction() { }
}
