package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.Note;

import java.util.List;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textView);

        }
    }

    private final LayoutInflater inflater;
    private List<Note> notes;

    NoteListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (notes != null) {
            Note current = notes.get(position);
            holder.noteItemView.setText(current.title);
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (notes != null)
            return notes.size();
        else return 0; //when it is first called,
                        // notes has not been updated (means initially, it's null, and we can't return null)
    }


    void setNotes(List<Note> nts) {
        notes = nts;
        notifyDataSetChanged();
    }
}
