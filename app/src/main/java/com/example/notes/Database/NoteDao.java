package com.example.notes.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.Models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY notes.title")
    List<Note> sortByTitle();

    @Query("SELECT * FROM notes ORDER BY notes.addedDate")
    List<Note> sortByDate();
}
