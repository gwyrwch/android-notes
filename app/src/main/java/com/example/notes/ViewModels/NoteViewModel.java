package com.example.notes.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.Models.Note;
import com.example.notes.Repositories.NoteRepository;

import java.util.List;



public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotesByTitle;
    private LiveData<List<Note>> allNotesByDate;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotesByDate = noteRepository.getAllNotesByDate();
        allNotesByTitle = noteRepository.getAllNotesByTitle();
    }

    public LiveData<List<Note>> getAllNotesByTitle() {
        return allNotesByTitle;
    }

    public LiveData<List<Note>> getAllNotesByDate() {
        return allNotesByDate;
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }
}
