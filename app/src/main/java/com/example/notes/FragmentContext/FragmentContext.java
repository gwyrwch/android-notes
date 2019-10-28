package com.example.notes.FragmentContext;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface FragmentContext {
    int fragmentId();
    int recyclerId();
    RecyclerView.LayoutManager layoutManager(Context activity);
}
