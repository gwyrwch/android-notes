package com.example.notes.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;

import java.util.List;

@Dao
public interface TagToNoteDao {
    @Insert
    void insert(TagToNote tagToNote);

    @Query("SELECT * FROM notes " +
            "INNER JOIN tag_to_note " +
            "ON notes.note_id=tag_to_note.note_id " +
            "WHERE tag_to_note.tag_id=:tagId"
    )
    LiveData<List<Note>> getNotesByTag(final long tagId);

    @Query("SELECT * FROM tags " +
            "INNER JOIN tag_to_note " +
            "ON tags.tag_id=tag_to_note.tag_id " +
            "WHERE tag_to_note.note_id=:noteId"
    )
    LiveData<List<Tag>> getTagsFromNote(final long noteId);

    @Query("DELETE FROM tag_to_note")
    void deleteAll();
}
