package com.QQzzyy.bookreader;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class LikedBook extends DataSupport implements Serializable {
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

    public void init(Book book){
        this.bookId=book.get_id();
        this.author=book.getAuthor();
        this.cover=book.getCover();
        this.shortIntro=book.getShortIntro();
        this.title=book.getTitle();
    }
    public Book backToBook(){
        Book book = new Book();
        book.set_id(this.bookId);
        book.setShortIntro(this.shortIntro);
        book.setAuthor(this.author);
        book.setTitle(this.title);
        book.setCover(this.cover);
        return book;
    }
}
