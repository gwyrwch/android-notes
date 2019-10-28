package com.example.notes.FragmentContext;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

public class GridFragmentContext implements FragmentContext {
    @Override
    public int fragmentId() {
        return R.layout.fragment_note_grid;
    }

    @Override
    public int recyclerId() {
        return R.id.recyclerviewGrid;
    }

    @Override
    public RecyclerView.LayoutManager layoutManager(Context activity) {
        return new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
    }
}
