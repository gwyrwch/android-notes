package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
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
    private View.OnClickListener onClickListener;

    NoteListAdapter(Context context, View.OnClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        itemView.setOnClickListener(onClickListener);
        itemView.setTag(this);
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

            for (String title: getTitlesForNote(current.id)) {
                Chip c = new Chip(context);
                c.setText(title);
                c.setCheckable(false);
                holder.tagsGroup.addView(c);
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

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }


    void setFullData(List<TagToNote> tagToNotes) {
        this.tagToNotes = tagToNotes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public ArrayList<String> getTitlesForNote(long noteId) {
        ArrayList<String> result = new ArrayList<>();
        for (TagToNote tn : this.tagToNotes) {
            if (tn.noteId == noteId) {
                for (Tag tag : this.tags) {
                    if (tag.id == tn.tagId) {
                        result.add(tag.tagName);
                    }
                }
            }
        }
        return result;
    }
}
