package com.example.notes.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(tableName = "tag_to_note",
        primaryKeys = { "tagId", "noteId" },
        foreignKeys = {
                @ForeignKey(entity = Note.class,
                        parentColumns = "note_id",
                        childColumns = "noteId"),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tagId")
        })
public class TagToNote {
    public int tagId;
    @ColumnInfo(index = true)
    public int noteId;
}

