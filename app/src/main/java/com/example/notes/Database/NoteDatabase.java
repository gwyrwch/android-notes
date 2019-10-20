package com.example.notes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.utilities.Converters;

@Database(entities = {Note.class, TagToNote.class, Tag.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {
    public abstract TagToNoteDao tagToNoteDao();
    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();

    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "notes_database")
//                            .allowMainThreadQueries() //fixme: may not be in production
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
