package com.example.notes.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class AdaptedNote {
    private Note note;
    private List<Tag> tags;

    public AdaptedNote(Note note, List<Tag> tags) {
        this.note = note;
        this.tags = tags;
    }

    public Note getNote() {
        return note;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
