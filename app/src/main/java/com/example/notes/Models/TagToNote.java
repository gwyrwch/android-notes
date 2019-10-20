package com.example.notes.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(tableName = "tag_to_note",
        primaryKeys = { "tag_id", "note_id" },
        foreignKeys = {
                @ForeignKey(entity = Note.class,
                        parentColumns = "note_id",
                        childColumns = "note_id"),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "tag_id",
                        childColumns = "tag_id")
        })
public class TagToNote {
    @ColumnInfo(index = true, name = "tag_id")
    public int tagId;
    @ColumnInfo(index = true, name = "note_id")
    public int noteId;
}

