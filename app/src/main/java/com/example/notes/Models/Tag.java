package com.example.notes.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tag {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    public long id;

    public String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
