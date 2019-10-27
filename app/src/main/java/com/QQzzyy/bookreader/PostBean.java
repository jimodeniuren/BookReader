package com.QQzzyy.bookreader;

import java.util.List;

public class PostBean {
    private List<post> posts;
    private List<post> helps;
    private List<post> reviews;

    public List<post> getPosts() {
        return posts;
    }

    public void setPosts(List<post> posts) {
        this.posts = posts;
    }

    public List<post> getHelps() {
        return helps;
    }

    public void setHelps(List<post> helps) {
        this.helps = helps;
    }

    public List<post> getReviews() {
        return reviews;
    }

    public void setReviews(List<post> reviews) {
        this.reviews = reviews;
    }
}
