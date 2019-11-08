package com.example.notes.Algorithm;

import com.example.notes.Models.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagRecommender {
    public boolean isInputting = false;
    private StringBuilder input;
    private List<Tag> tags;

    public void setAllTags(List<Tag> tags) {
        this.tags = tags;
    }

    public TagRecommender() {
        input = new StringBuilder();
        tags = null;
        lastPos = -1;
    }

    public List<Tag> recommend() {
        List<Tag> result = new ArrayList<>();
        if (tags == null) {
            return result;
        }

        for (Tag t: tags) {
            if (t.tagName.startsWith(input.toString())) {
                result.add(t);
            }
        }
        return result;
    }

    public int lastPos;

    public void addChar(char c, int position) {
        input.append(c);
        lastPos = position;
    }

    public void drop() {
        input = new StringBuilder();
        isInputting = false;
    }

    public void startInputting(int start) {
        isInputting = true;
        input = new StringBuilder();
        lastPos = start;
    }

    public void finishInputting() {
        isInputting = false;
    }

    public String currentTag() {
        return input.toString();
    }
}
