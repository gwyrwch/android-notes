package com.example.notes.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;

import java.util.List;

@Dao
public interface TagDao {
    @Query("SELECT * FROM tags")
    List<Tag> getAllTags();

    @Insert
    void insert(Tag tag);

    @Delete
    void delete(Tag tag);
}
