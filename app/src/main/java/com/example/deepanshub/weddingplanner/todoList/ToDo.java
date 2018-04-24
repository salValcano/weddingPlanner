package com.example.deepanshub.weddingplanner.todoList;

/**
 * Created by deepanshub on 23/4/18.
 */

public class ToDo {
    private String id, title, description;

    public ToDo() {
    }

    public ToDo(String id, String title, String description) {
        this.id = id;
        this.description = description;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

