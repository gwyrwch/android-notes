package com.example.notes.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.utilities.Converters;

@Database(entities = {Note.class, TagToNote.class, Tag.class}, version = 1)
@TypeConverters({Converters.class})
// todo : what is version?  in @database
public abstract class NoteDatabase extends RoomDatabase {
    public abstract TagToNoteDao tagToNoteDao();
    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();
}
