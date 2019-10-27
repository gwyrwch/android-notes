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

    public TagToNoteViewModel(@NonNull Application application) {
        super(application);

        tagToNoteRepository = new TagToNoteRepository(application);
    }

    public LiveData<List<Tag>> getTagsFromNote(long nodeId) {
        return tagToNoteRepository.getTagsFromNote(nodeId);
    }

    public LiveData<List<Note>> getNotesByTag(long tagId){
        return tagToNoteRepository.getNotesByTag(tagId);
    }
}
