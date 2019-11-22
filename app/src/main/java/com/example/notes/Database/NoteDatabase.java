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
import com.example.notes.Utilities.Converters;

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
        private final NoteDao nDao;
        private final TagDao tDao;
        private final TagToNoteDao tnDao;

        PopulateDbAsync(NoteDatabase db) {
            nDao = db.noteDao();
            tDao = db.tagDao();
            tnDao = db.tagToNoteDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

//            tnDao.deleteAll();
//            nDao.deleteAll();
//            tDao.deleteAll();
    //
    //        Note note = new Note("title1", "body1");
    //        note.id = mDao.insert(note);
    //        note = new Note("title2", "body2");
    //        note.id = mDao.insert(note);
    //        note = new Note("title3", "body3");
    //        note.id = mDao.insert(note);
    //        note = new Note("title4", "body4");
    //        note.id = mDao.insert(note);
    //
    //        Tag tag = new Tag("tag1");
    //        tag.id = tDao.insert(tag);
    //        Tag tag2 = new Tag("tag2");
    //        tag2.id = tDao.insert(tag2);
    //
    //        if (note.id == 0 || tag.id == 0) throw new AssertionError();
    //
    //        tnDao.insert(new TagToNote(tag.id, note.id));
    //        tnDao.insert(new TagToNote(tag2.id, note.id));
            return null;
        }
    }

}

