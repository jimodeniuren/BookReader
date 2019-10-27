package com.QQzzyy.bookreader;

import java.io.Serializable;

public class post implements Serializable {
    private String _id;
    private author author;
    private String title;
    private String content = "";

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public com.QQzzyy.bookreader.author getAuthor() {
        return author;
    }

    public void setAuthor(com.QQzzyy.bookreader.author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
