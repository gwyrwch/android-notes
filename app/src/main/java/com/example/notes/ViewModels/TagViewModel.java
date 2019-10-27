package com.example.notes.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.Models.Note;
import com.example.notes.Models.Tag;
import com.example.notes.Repositories.NoteRepository;
import com.example.notes.Repositories.TagRepository;

import java.util.ArrayList;
import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private TagRepository tagRepository;
    private LiveData<List<Tag>> allTags;

    public List<String> selectedTitles;

    public TagViewModel(Application application) {
        super(application);

        selectedTitles = new ArrayList<>();

        tagRepository = new TagRepository(application);
        allTags = tagRepository.getAllTags();
    }

    public LiveData<List<Tag>> getAllTags() {
        return allTags;
    }

    public void insert(Tag tag) {
        tagRepository.insert(tag);
    }

    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    public boolean addNewTitle(String title) {
        if (selectedTitles.contains(title)) {
            return false;
        }
        return selectedTitles.add(title);
    }

    public Tag getTagByTitle(String title) {
        return tagRepository.getTagByTitle(title);
    }
}
