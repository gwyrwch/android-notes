package com.example.notes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notes.Database.NoteDatabase;
import com.example.notes.Database.TagToNoteDao;
import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;

import java.util.List;

public class TagToNoteRepository {
    private TagToNoteDao tagToNoteDao;
    private LiveData<List<Tag>> tagsFromNote;
    private LiveData<List<Note>> notesByTag;

    public TagToNoteRepository(Application application, int tagId, int noteId) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        tagToNoteDao = db.tagToNoteDao();
        tagsFromNote = tagToNoteDao.getTagsFromNote(noteId);
        notesByTag = tagToNoteDao.getNotesByTag(tagId);

    }

    public LiveData<List<Tag>> getTagsFromNote() {
        return tagsFromNote;
    }

    public LiveData<List<Note>> getNotesByTag() {
        return notesByTag;
    }


    public void insert(TagToNote tagToNote) {
        new TagToNoteRepository.insertAsyncTask(tagToNoteDao).execute(tagToNote);
    }


    private static class insertAsyncTask extends AsyncTask<TagToNote, Void, Void> {
        private TagToNoteDao asyncTagToNoteDao;

        insertAsyncTask(TagToNoteDao dao) {
            asyncTagToNoteDao = dao;
        }

        @Override
        protected Void doInBackground(final TagToNote... params) {
            asyncTagToNoteDao.insert(params[0]);
            return null;
        }
    }
}
