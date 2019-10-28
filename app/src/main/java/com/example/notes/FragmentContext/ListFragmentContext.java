package com.example.notes.FragmentContext;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

public class ListFragmentContext implements FragmentContext {
    @Override
    public int fragmentId() {
        return R.layout.fragment_note_list;
    }

    @Override
    public int recyclerId() {
        return R.id.recyclerview;
    }

    @Override
    public RecyclerView.LayoutManager layoutManager(Context activity) {
        return new LinearLayoutManager(activity);
    }
}