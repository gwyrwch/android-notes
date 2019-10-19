package com.example.notes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Note {
    public String title;
    public List<String> tags;
    public String body;

    public Note(String title, List<String> tags, String body) {
        if (title == null) {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            this.title = df.format(currentTime);
        }
        else {
            this.title = title;
        }

        this.tags = tags;
        this.body = body;
    }
}
