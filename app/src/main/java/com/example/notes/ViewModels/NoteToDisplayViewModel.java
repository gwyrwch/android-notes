package com.example.notes.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.notes.Models.Note;

public class NoteToDisplayViewModel extends ViewModel {
    LiveData<Note> note;
}
