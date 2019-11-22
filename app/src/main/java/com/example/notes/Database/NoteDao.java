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
    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY notes.title")
    LiveData<List<Note>> sortByTitle();

    @Query("SELECT * FROM notes ORDER BY notes.addedDate DESC")
    LiveData<List<Note>> sortByDate();

    @Query("DELETE FROM notes")
    void deleteAll();
}
