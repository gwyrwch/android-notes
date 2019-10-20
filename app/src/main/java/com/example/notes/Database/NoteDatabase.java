package com.example.notes.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;
import com.example.notes.utilities.Converters;

@Database(entities = {Note.class, TagToNote.class, Tag.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    public abstract TagDao tagDao();
    public abstract TagToNoteDao tagToNoteDao();


    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "notes_database")
                            .addCallback(noteDatabaseCallback)
//                            .allowMainThreadQueries() //fixme: may not be in production
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback noteDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final NoteDao mDao;

    PopulateDbAsync(NoteDatabase db) {
        mDao = db.noteDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        // Start the app with a clean database every time.
        // Not needed if you only populate on creation.
        mDao.deleteAll();

        Note note = new Note("title1", "body1");
        mDao.insert(note);
        note = new Note("title2", "body2");
        mDao.insert(note);
        note = new Note("title3", "body3");
        mDao.insert(note);
        note = new Note("title4", "body4");
        mDao.insert(note);

        return null;
    }
}

}

