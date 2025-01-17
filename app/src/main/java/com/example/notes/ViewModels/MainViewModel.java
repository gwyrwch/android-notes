package com.example.notes.ViewModels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.Repositories.NoteRepository;
import com.example.notes.Repositories.TagRepository;
import com.example.notes.Repositories.TagToNoteRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private TagRepository tagRepository;
    private TagToNoteRepository tagToNoteRepository;

    private LiveData<List<Note>> allNotesByTitle;
    private LiveData<List<Note>> allNotesByDate;
    private final MutableLiveData<List<Note>> notesOnScreen;
    private LiveData<List<TagToNote>> fullData;
    private LiveData<List<Tag>> allTags;

    enum CurrentState {
        DATE,
        TITLE
    }
    private CurrentState currentState;
    private String lastFilteredTagTitle;


    public MainViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotesByDate = noteRepository.getAllNotesByDate();
        allNotesByTitle = noteRepository.getAllNotesByTitle();

        tagRepository = new TagRepository(application);
        tagToNoteRepository = new TagToNoteRepository(application);
        allTags = tagRepository.getAllTags();
        fullData = tagToNoteRepository.getFullData();

        notesOnScreen = new MutableLiveData<>();
        currentState = CurrentState.DATE;
        lastFilteredTagTitle = null;
    }

    public void setAllNotesByTitle() {
        currentState = CurrentState.TITLE;
        notesOnScreen.setValue(allNotesByTitle.getValue());

        if (lastFilteredTagTitle != null) {
            displayNotesByTag(lastFilteredTagTitle);
        }
    }

    public LiveData<List<Note>> getAllNotesByTitle() {
        return allNotesByTitle;
    }

    public void updateIfTitle() {
        if (currentState == CurrentState.TITLE) {
            setAllNotesByTitle();
        }
    }

    public void setAllNotesByDate() {
        currentState = CurrentState.DATE;
        notesOnScreen.setValue(allNotesByDate.getValue());

        if (lastFilteredTagTitle != null) {
            displayNotesByTag(lastFilteredTagTitle);
        }
    }

    public LiveData<List<Note>> getAllNotesByDate() {
        return allNotesByDate;
    }

    public void updateIfDate() {
        if (currentState == CurrentState.DATE) {
            setAllNotesByDate();
        }
    }

    public MutableLiveData<List<Note>> getNotesOnScreen() {
        return notesOnScreen;
    }

    private AsyncTask<Note, Void, Note> insertNote(Note note) {
        return noteRepository.insert(note);
    }

    private AsyncTask<Note, Void, Note> updateNote(Note note) {
        return noteRepository.update(note);
    }

    private void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    private void insertTagToNote(long tagId, AsyncTask<Note, Void, Note> noteInsertion) {
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

    public void dropCurrentState() {
        lastFilteredTagTitle = null;
        notesOnScreen.setValue(allNotesByDate.getValue());
        currentState = CurrentState.DATE;
    }

    public void insert(Note note, List<Tag> tags) {
        AsyncTask<Note, Void, Note> noteInsertion = insertNote(note);
        for (Tag t : tags) {
            if (t.id == 0) throw new AssertionError();
            insertTagToNote(t.id, noteInsertion);
        }

        dropCurrentState();
    }

    private void deleteAllTagsForNote(long nodeId) {
        tagToNoteRepository.deleteAllTagsForNote(nodeId);
    }

    public void update(Note note, List<Tag> tags) {
        AsyncTask<Note, Void, Note> noteUpdate = updateNote(note);
        deleteAllTagsForNote(note.id);
        for (Tag t : tags) {
            if (t.id == 0) throw new AssertionError();
            insertTagToNote(t.id, noteUpdate);
        }

        dropCurrentState();
    }

    public void delete(Note note) {
        deleteAllTagsForNote(note.id);
        deleteNote(note);

        dropCurrentState();
    }

    private List<Tag> getTagsFromNote(long nodeId) {
        return tagToNoteRepository.getTagsFromNote(nodeId);
    }

    public void displayNotesByTag(String tagTitle) {
        if (notesOnScreen.getValue() == null) {
            return;
        }

        this.lastFilteredTagTitle = tagTitle;

        List<Note> newNotesToDisplay = new ArrayList<>();

        for (Note n: notesOnScreen.getValue()) {
            boolean contains = false;
            for (Tag t: getTagsFromNote(n.id)) {
                if (t.tagName.equals(tagTitle)) {
                    contains = true;
                }
            }
            if (contains) {
                newNotesToDisplay.add(n);
            }
        }

        notesOnScreen.setValue(newNotesToDisplay);
    }
}
