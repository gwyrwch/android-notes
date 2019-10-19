package com.example.notes.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "notes", indices = {@Index("note_id")})
public class Note {
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    public int id;

    public String title;
    public String body;

    public Date addedDate;

    public Note(int id, String title, String body) {
        this.id = id;

        Date currentTime = Calendar.getInstance().getTime();
        this.addedDate = currentTime;

        if (title == null) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            this.title = df.format(currentTime);
        }
        else {
            this.title = title;
        }

        this.body = body;
    }
}
