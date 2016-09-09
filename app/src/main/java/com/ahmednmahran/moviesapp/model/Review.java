
package com.ahmednmahran.moviesapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name="Reviews")
public class Review extends Model{

    @Column(name="review_id")
    @SerializedName("id")
    private String id;

    @Column(name="review_author")
    @SerializedName("author")
    private String author;

    @Column(name="review_content")
    @SerializedName("content")
    private String content;

    @Column(name="review_url")
    @SerializedName("url")
    private String url;

    public Review(){
        super();
    }

    public String getReviewId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
git