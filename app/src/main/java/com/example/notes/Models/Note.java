package com.example.notes.Models;

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    public long id;

    public String title;
    public String body;

    public Date addedDate;

    public Note(String title, String body) {

        Date currentTime = Calendar.getInstance().getTime();
        this.addedDate = currentTime;

        if (title == null || title.isEmpty()) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            this.title = df.format(currentTime);
        }
        else {
            this.title = title;
        }

        this.body = body;
    }
}
