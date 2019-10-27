package com.QQzzyy.bookreader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Book extends DataSupport implements Serializable {
    @Expose
    @SerializedName("_id")
    private String bookId;
    private String title;
    private String author;
    private String shortIntro;
    private String cover;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String get_id() {
        return bookId;
    }

    public void set_id(String _id) {
        this.bookId = _id;
    }
}
