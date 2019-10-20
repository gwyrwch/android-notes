package com.example.notes.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY notes.title")
    LiveData<List<Note>> sortByTitle();

    @Query("SELECT * FROM notes ORDER BY notes.addedDate")
    LiveData<List<Note>> sortByDate();

    // fixme: maybe delete this code
    @Query("DELETE FROM notes")
    void deleteAll();
}
