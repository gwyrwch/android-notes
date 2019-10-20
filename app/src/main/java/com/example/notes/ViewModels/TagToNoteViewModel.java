package com.example.notes.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.Repositories.TagToNoteRepository;

import java.util.List;

public class TagToNoteViewModel extends AndroidViewModel {
    private TagToNoteRepository tagToNoteRepository;

    private LiveData<List<Tag>> tagsFromNote;
    private LiveData<List<Note>> notesByTag;

    public TagToNoteViewModel(@NonNull Application application, int tagId, int noteId) {
        super(application);

        tagToNoteRepository = new TagToNoteRepository(application, tagId, noteId);
        tagsFromNote = tagToNoteRepository.getTagsFromNote();
        notesByTag = tagToNoteRepository.getNotesByTag();
    }

    public LiveData<List<Tag>> getTagsFromNote() {
        return tagsFromNote;
    }

    public LiveData<List<Note>> getNotesByTag(){
        return notesByTag;
    }

    public void insert(TagToNote tagToNote) {
        tagToNoteRepository.insert(tagToNote);
    }
}
