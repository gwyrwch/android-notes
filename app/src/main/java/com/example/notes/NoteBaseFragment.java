package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notes.FragmentContext.FragmentContext;
import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class NoteBaseFragment<MyFragmentContext extends FragmentContext> extends Fragment {
    public static final int EDIT_NOTE_ACTIVITY_REQUEST_CODE = 2;

    private MainViewModel viewModel;
    private View v;
    private OnFragmentInteractionListener mListener;
    protected MyFragmentContext fragmentContext;
    private RecyclerView recyclerView;

    public NoteBaseFragment(MyFragmentContext fragmentContext) {
        this.fragmentContext = fragmentContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(fragmentContext.fragmentId(), container, false);
        recyclerView = v.findViewById(fragmentContext.recyclerId());
        recyclerView.setLayoutManager(fragmentContext.layoutManager(getActivity()));


        final NoteListAdapter adapter = new NoteListAdapter(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                Note noteClicked = ((NoteListAdapter)v.getTag()).getNotes().get(itemPosition);

                Intent intent = new Intent(getActivity(), NoteActivity.class);

                intent.putExtra(NoteActivity.NOTE_ID, noteClicked.id);
                intent.putExtra(NoteActivity.NOTE_DATE, noteClicked.addedDate);
                intent.putExtra(NoteActivity.NOTE_BODY, noteClicked.body);
                intent.putExtra(NoteActivity.NOTE_TITLE, noteClicked.title);
                intent.putStringArrayListExtra(NoteActivity.TAGS, ((NoteListAdapter)v.getTag()).getTitlesForNote(noteClicked.id));

                startActivityForResult(intent, EDIT_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        recyclerView.setAdapter(adapter);



//        viewModel = ViewModelProviders.of(this).get(MainViewModel.class); //fixme: both variants work why?)))
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

        viewModel.getAllTags().observe(this, new Observer<List<Tag>>() {
            @Override
            public void onChanged(@Nullable final List<Tag> allTags) {
                // Update the cached copy of the notes in the adapter.
                if (allTags != null) {
                    adapter.setTags(allTags);
                }
            }
        });

        viewModel.getAllNotesByTitle().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                viewModel.updateIfTitle();
            }
        });

        viewModel.getAllNotesByDate().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                viewModel.updateIfDate();
            }
        });

        viewModel.getNotesOnScreen().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                // Update the cached copy of the notes in the adapter.
                if (notes != null) {
                    adapter.setNotes(notes);
                }
            }
        });

        viewModel.getFullData().observe(this, new Observer<List<TagToNote>>() {
            @Override
            public void onChanged(@Nullable final List<TagToNote> tagToNotes) {
                // Update the cached copy of the notes in the adapter.
                if (tagToNotes != null) {

                    adapter.setFullData(tagToNotes);
                }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(NoteActivity.NOTE_TITLE), data.getStringExtra(NoteActivity.NOTE_BODY));
            note.id = data.getLongExtra(NoteActivity.NOTE_ID, -1);

            ArrayList<Tag> tags = new ArrayList<>();
            for (String title : Objects.requireNonNull(data.getStringArrayListExtra(NoteActivity.TAGS))) {
                Tag t = viewModel.getTagByTitle(title);
                tags.add(t);
            }

            viewModel.update(note, tags);
        }
    }
}
