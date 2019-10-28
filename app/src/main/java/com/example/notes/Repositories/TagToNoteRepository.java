package com.example.notes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.Database.NoteDatabase;
import com.example.notes.Database.TagToNoteDao;
import com.example.notes.Models.AdaptedNote;
import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Models.TagToNote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TagToNoteRepository {
    private TagToNoteDao tagToNoteDao;

    public TagToNoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        tagToNoteDao = db.tagToNoteDao();
    }

    public LiveData<List<Tag>> getTagsFromNote(long noteId) {
        return tagToNoteDao.getTagsFromNote(noteId);
    }

    public LiveData<List<Note>> getNotesByTag(long tagId) {
        return tagToNoteDao.getNotesByTag(tagId);
    }

    public LiveData<List<TagToNote>> getFullData() {
        return tagToNoteDao.getFullData();
    }

    public void insert(final long tagId, final long nodeId) {
        new TagToNoteRepository.insertAsyncTask(tagToNoteDao, null).execute(
            new ArrayList<Long>() {
                {
                    add(tagId);
                    add(nodeId);
                }
            }
        );
    }

    public void insert(final long tagId, AsyncTask<Note, Void, Note> noteInsertion) {
        new TagToNoteRepository.insertAsyncTask(tagToNoteDao, noteInsertion).execute(new ArrayList<Long>() {
            {
                add(tagId);
            }
        });
    }

    private static class insertAsyncTask extends AsyncTask<List<Long>, Void, Void> {
        private TagToNoteDao asyncTagToNoteDao;
        private AsyncTask<Note, Void, Note> noteInsertion;

        insertAsyncTask(TagToNoteDao dao, AsyncTask<Note, Void, Note> noteInsertion) {
            asyncTagToNoteDao = dao;
            this.noteInsertion = noteInsertion;
        }

        @Override
        protected Void doInBackground(final List<Long>... params) {
            if (noteInsertion != null) {
                try {
                    asyncTagToNoteDao.insert(new TagToNote(params[0].get(0), noteInsertion.get().id));
                } catch (InterruptedException ignored) {
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                asyncTagToNoteDao.insert(new TagToNote(params[0].get(0), params[0].get(1)));
            }
            return null;
        }
    }

}
