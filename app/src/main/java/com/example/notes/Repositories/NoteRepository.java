package com.example.notes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notes.Database.NoteDao;
import com.example.notes.Database.NoteDatabase;
import com.example.notes.Models.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotesByTitle;
    private LiveData<List<Note>> allNotesByDate;


    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotesByTitle = noteDao.sortByTitle();
        allNotesByDate = noteDao.sortByDate();
    }

    public LiveData<List<Note>> getAllNotesByTitle() {
        return allNotesByTitle;
    }

    public LiveData<List<Note>> getAllNotesByDate() {
        return allNotesByDate;
    }

    public void insert(Note note) {
        new insertAsyncTask(noteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncNoteDao;

        insertAsyncTask(NoteDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncNoteDao.insert(params[0]);
            return null;
        }
    }

    public void update(Note note) {
        new updateAsyncTask(noteDao).execute(note);
    }

    private static class updateAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncNoteDao;

        updateAsyncTask(NoteDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncNoteDao.update(params[0]);
            return null;
        }
    }


    public void delete(Note note) {
        new deleteAsyncTask(noteDao).execute(note);
    }

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao asyncNoteDao;

        deleteAsyncTask(NoteDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            asyncNoteDao.delete(params[0]);
            return null;
        }
    }
}
