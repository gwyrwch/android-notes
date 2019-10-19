package com.example.notes.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tag {
    @PrimaryKey
    public int id;

    public String tagName;

    public Tag(int id) {
        this.id = id;
    }
}
