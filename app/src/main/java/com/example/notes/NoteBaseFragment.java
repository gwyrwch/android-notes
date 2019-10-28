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

import java.util.List;
import java.util.Objects;


public class NoteBaseFragment<MyFragmentContext extends FragmentContext> extends Fragment {
    private MainViewModel viewModel;
    private View v;
    private OnFragmentInteractionListener mListener;
    protected MyFragmentContext fragmentContext;

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
        RecyclerView recyclerView = v.findViewById(fragmentContext.recyclerId());
        recyclerView.setLayoutManager(fragmentContext.layoutManager(getActivity()));

        final NoteListAdapter adapter = new NoteListAdapter(getActivity());
        recyclerView.setAdapter(adapter);



//        viewModel = ViewModelProviders.of(this).get(MainViewModel.class); //fixme: both variants work why?)))
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);

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

    }
}
