package com.example.deepanshub.weddingplanner.todoListnew;

/**
 * Created by deepanshub on 23/4/18.
 */

public class TodoModel {
    private String id;
    private String title;
    private String description;

    public TodoModel(String id, String title, String descripton) {
        this.id = id;
        this.title = title;
        this.description = descripton;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
