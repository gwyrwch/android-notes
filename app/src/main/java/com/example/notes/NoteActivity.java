package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.notes.Algorithm.TagRecommender;
import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.ViewModels.NoteViewModel;
import com.example.notes.ViewModels.TagViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_BODY = "body";
    public static final String NOTE_ID = "note_id";
    public static final String NOTE_DATE = "added_date";
    public static final String TAGS = "tags";

    private EditText editNoteTitleView, editNoteBodyView;
    private PopupMenu popup;

    long currentNoteId;

    private TagRecommender tagRecommender;
    private TagViewModel tagViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tagViewModel = ViewModelProviders.of(this).get(TagViewModel.class);

        tagRecommender = new TagRecommender();
        tagViewModel.getAllTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                tagRecommender.setAllTags(tags);
            }
        });

        editNoteTitleView = findViewById(R.id.edit_note_title);
        editNoteBodyView = findViewById(R.id.edit_note_body);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // save button OnClickListener
                Intent replyIntent = new Intent();

                String title = editNoteTitleView.getText().toString();
                replyIntent.putExtra(NOTE_TITLE, title);

                String body = editNoteBodyView.getText().toString();
                replyIntent.putExtra(NOTE_BODY, body);

                if (currentNoteId != -1) {
                    replyIntent.putExtra(NOTE_ID, currentNoteId);
                }

                replyIntent.putStringArrayListExtra(TAGS, (ArrayList<String>) tagViewModel.selectedTitles);

                setResult(RESULT_OK, replyIntent);
                finish(); // exit NoteActivity
            }
        });

        popup = new PopupMenu(NoteActivity.this, editNoteBodyView);
        popup.getMenuInflater().inflate(R.menu.tag_menu, popup.getMenu());

        final TextInputLayout textLayout = findViewById(R.id.textInputLayout2);
        textLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNoteBodyView.requestFocus();
                System.out.println("OPen clava");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    // shows keyboard
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });

        Intent intent = getIntent();
        currentNoteId = intent.getLongExtra(NOTE_ID, -1);

        if (currentNoteId != -1) {
            editNoteBodyView.setText(intent.getStringExtra(NOTE_BODY));
            editNoteTitleView.setText(intent.getStringExtra(NOTE_TITLE));
            ArrayList<String> titles = intent.getStringArrayListExtra(TAGS);
            for (String title: titles) {
                onTagSelected(title);
            }
        }

        editNoteBodyView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count - after == 1) {
                    TagRecommender tr = NoteActivity.this.tagRecommender;
                    tr.drop();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count - before == 1) {
                    start += before;
                    if (start >= s.length()) {
                        System.out.println("WAAAT");
                        return;
                    }
                    TagRecommender tr = NoteActivity.this.tagRecommender;

                    boolean need2Display = false;
                    if (s.charAt(start) == '#') {
                        tr.startInputting(start);
                        need2Display = true;
                    } else if (tr.isInputting) {
                        tr.addChar(s.charAt(start), start);
                        need2Display = true;
                    }

                    if (need2Display) {
                        popup.getMenu().clear();
                        for (Tag t: tr.recommend()) {
                            popup.getMenu().add(t.tagName);
                        }

                        if (tr.currentTag().length() > 1 && NoteActivity.this.tagViewModel.getTagByTitle(tr.currentTag()) == null) {
                            popup.getMenu().add("Create new tag " + tr.currentTag());
                        }

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // System.out.println(item.getTitle());
                                NoteActivity.this.tagRecommender.finishInputting();

                                String title = item.getTitle().toString();
                                if (title.startsWith("Create new tag ")) {
                                    title = title.substring("Create new tag ".length());
                                    NoteActivity.this.tagViewModel.insert(new Tag(title));
                                    NoteActivity.this.onTagSelected(title);
                                } else {
                                    NoteActivity.this.onTagSelected(title);
                                }
                                return true;
                            }
                        });

                        popup.show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onTagSelected(String title) {
        if (!tagViewModel.addNewTitle(title)) {
            return;
        }
        if (tagRecommender.lastPos != -1) {
            editNoteBodyView.getText().insert(tagRecommender.lastPos + 1, title.substring(tagRecommender.currentTag().length()));
        }
        createChip(title);
    }

    private void createChip(final String title) {
        ChipGroup cg = findViewById(R.id.chip_group);
        Chip c = new Chip(this);
        c.setText(title);
        c.setCheckable(false);
        cg.addView(c);

//        c.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tag tagToDelete = tagViewModel.getTagByTitle(title);
//                tagViewModel.delete(tagToDelete);
//            }
//        });
    }
}
