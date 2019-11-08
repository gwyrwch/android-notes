package com.example.notes.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.Models.Tag;
import com.example.notes.Repositories.TagRepository;

import java.util.ArrayList;
import java.util.List;


//todo: rename this class
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

    public boolean deleteTitle(String title) {
        if (selectedTitles.contains(title)) {
            return selectedTitles.remove(title);
        }
        return false;
    }

}
