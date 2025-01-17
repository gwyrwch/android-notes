package com.example.notes.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.Models.Tag;

import java.util.List;

@Dao
public interface TagDao {
    @Query("SELECT * FROM tags")
    LiveData<List<Tag>> getAllTags();

    @Insert
    long insert(Tag tag);

    @Delete
    void delete(Tag tag);


    @Query("SELECT * FROM tags WHERE tagName=:title")
    Tag getTagByTitle(String title);

    @Query("DELETE FROM tags")
    void deleteAll();
}
