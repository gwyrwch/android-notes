package com.example.notes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notes.Database.NoteDatabase;
import com.example.notes.Database.TagDao;
import com.example.notes.Models.Tag;

import java.io.Console;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TagRepository {
    private TagDao tagDao;
    private LiveData<List<Tag>> allTags;

    public TagRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        tagDao = db.tagDao();
        allTags = tagDao.getAllTags();
    }

    public LiveData<List<Tag>> getAllTags() {
        return allTags;
    }

    public void insert(Tag tag) {
        new TagRepository.insertAsyncTask(tagDao).execute(tag);
    }

    private static class insertAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao asyncTagDao;

        insertAsyncTask(TagDao dao) {
            asyncTagDao = dao;
        }

        @Override
        protected Void doInBackground(final Tag... params) {
            params[0].id = asyncTagDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Tag tag) {
        new TagRepository.deleteAsyncTask(tagDao).execute(tag);
    }

    private static class deleteAsyncTask extends AsyncTask<Tag, Void, Void> {
        private TagDao asyncTagDao;

        deleteAsyncTask(TagDao dao) {
            asyncTagDao = dao;
        }

        @Override
        protected Void doInBackground(final Tag... params) {
            asyncTagDao.delete(params[0]);
            return null;
        }
    }

    public Tag getTagByTitle(String title) {
        AsyncTask<String, Void, Tag> task = new getTagByTitleAsyncTask(tagDao).execute(title);
        Tag result = null;
        try {
            result = task.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class getTagByTitleAsyncTask extends AsyncTask<String, Void, Tag> {
        private TagDao asyncTagDao;

        getTagByTitleAsyncTask(TagDao dao) {
            asyncTagDao = dao;
        }

        @Override
        protected Tag doInBackground(final String... params) {
            return asyncTagDao.getTagByTitle(params[0]);
        }
    }

}
