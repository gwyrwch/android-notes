package com.example.notes;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        private final ImageButton deleteNoteButton;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteTitleItemView = itemView.findViewById(R.id.titleTextView);
            noteBodyItemView = itemView.findViewById(R.id.bodyTextView);
            tagsGroup = itemView.findViewById(R.id.tagsGroup);
            deleteNoteButton = itemView.findViewById(R.id.delete_note);
        }
    }

    private final LayoutInflater inflater;
    private List<Note> notes;
    private List<Tag> tags;
    private List<TagToNote> tagToNotes;
    private Context context;
    private View.OnClickListener onClickListener, onTagClickListener, onDeleteNoteClickListener;

    NoteListAdapter(Context context, View.OnClickListener onClickListener,
                    View.OnClickListener onTagClickListener, View.OnClickListener onDeleteNoteClickListener) {
        /**
         @param onClickListener is then set to recyclerview_item
         @param onTagClickListener is then set to each chip in tagsGroup
         @param onDeleteNoteClickListener is then set to deleteNote button in recyclerview_item
         * they are set in {@link NoteBaseFragment}
         *
         */
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
        this.onTagClickListener = onTagClickListener;
        this.onDeleteNoteClickListener = onDeleteNoteClickListener;
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
            Note currentNote = notes.get(position);
            holder.noteTitleItemView.setText(currentNote.title);
            holder.noteBodyItemView.setText(currentNote.body);
            holder.tagsGroup.removeAllViews();

            if (this.tagToNotes == null) {
                return;
            }

            for (String title: getTagsForNote(currentNote.id)) {
                Chip c = new Chip(context);
                c.setText(title);
                c.setCheckable(false);
                c.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#3CFF2D55")));
                holder.tagsGroup.addView(c);
                c.setTag(title);
                c.setOnClickListener(onTagClickListener);
            }

            holder.deleteNoteButton.setOnClickListener(onDeleteNoteClickListener);
            holder.deleteNoteButton.setTag(currentNote);

        } else {
            holder.noteTitleItemView.setText(R.string.no_notes);
        }
    }

    @Override
    public int getItemCount() {
        if (notes != null)
            return notes.size();
        else return 0;
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

    ArrayList<String> getTagsForNote(long noteId) {
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
