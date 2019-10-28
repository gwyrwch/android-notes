package com.example.notes.ViewModels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.notes.MainActivity;
import com.example.notes.Models.AdaptedNote;
import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.Repositories.NoteRepository;
import com.example.notes.Repositories.TagRepository;
import com.example.notes.Repositories.TagToNoteRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private TagRepository tagRepository;
    private TagToNoteRepository tagToNoteRepository;

    private LiveData<List<Note>> allNotesByTitle;
    private LiveData<List<Note>> allNotesByDate;
    private LiveData<List<TagToNote>> fullData;
    private LiveData<List<Tag>> allTags;

    public MainViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotesByDate = noteRepository.getAllNotesByDate();
        allNotesByTitle = noteRepository.getAllNotesByTitle();

        tagRepository = new TagRepository(application);
        tagToNoteRepository = new TagToNoteRepository(application);
        allTags = tagRepository.getAllTags();
        fullData = tagToNoteRepository.getFullData();
    }

    public LiveData<List<Note>> getAllNotesByTitle() {
        return allNotesByTitle;
    }

    public LiveData<List<Note>> getAllNotesByDate() {
        return allNotesByDate;
    }

    public AsyncTask<Note, Void, Note> insertNote(Note note) {
        return noteRepository.insert(note);
    }

    public void insertTagToNote(long tagId, AsyncTask<Note, Void, Note> noteInsertion) {
        tagToNoteRepository.insert(tagId, noteInsertion);
    }

    public Tag getTagByTitle(String title) {
        return tagRepository.getTagByTitle(title);
    }

    public LiveData<List<Tag>> getAllTags() {
        return allTags;
    }

    public LiveData<List<TagToNote>> getFullData() {
        return fullData;
    }

    public void insert(Note note, List<Tag> tags) {
        AsyncTask<Note, Void, Note> noteInsertion = insertNote(note);
        for (Tag t : tags) {
            if (t.id == 0) throw new AssertionError();
            insertTagToNote(t.id, noteInsertion);
        }
    }

    public LiveData<List<Tag>> getTagsFromNote(long nodeId) {
        return tagToNoteRepository.getTagsFromNote(nodeId);
    }
}
