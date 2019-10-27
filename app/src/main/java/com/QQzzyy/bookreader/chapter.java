package com.QQzzyy.bookreader;

import java.io.Serializable;

public class chapter implements Serializable {
    private String link;
    private String title;
    private String cpContent;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCpContent() {
        return cpContent;
    }

    public void setCpContent(String cpContent) {
        this.cpContent = cpContent;
    }
}
