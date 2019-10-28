package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitleItemView, noteBodyItemView;
        private final ChipGroup tagsGroup;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteTitleItemView = itemView.findViewById(R.id.titleTextView);
            noteBodyItemView = itemView.findViewById(R.id.bodyTextView);
            tagsGroup = itemView.findViewById(R.id.tagsGroup);
        }
    }

    private final LayoutInflater inflater;
    private List<Note> notes;
    private List<Tag> tags;
    private List<TagToNote> tagToNotes;
    private Context context;

    NoteListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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
            holder.noteTitleItemView.setText(current.title);
            holder.noteBodyItemView.setText(current.body);
            holder.tagsGroup.removeAllViews();

            if (this.tagToNotes == null) {
                return;
            }

            for (TagToNote tn : this.tagToNotes) {
                if (tn.noteId == current.id) {
                    for (Tag tag : this.tags) {
                        if (tag.id == tn.tagId) {
                            Chip c = new Chip(context);
                            c.setText(tag.tagName);
                            c.setCheckable(false);
                            holder.tagsGroup.addView(c);
                        }
                    }
                }

            }
        } else {
            // Covers the case of data not being ready yet.
            holder.noteTitleItemView.setText("No notes");
        }
    }

    @Override
    public int getItemCount() {
        if (notes != null)
            return notes.size();
        else return 0; //when it is first called,
                        // notes has not been updated (means initially, it's null, and we can't return null)
    }

    void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    void getTags(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }


    void getFullData(List<TagToNote> tagToNotes) {
        this.tagToNotes = tagToNotes;
        notifyDataSetChanged();
    }

}
