package com.ahmednmahran.moviesapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed Nabil on 19/08/2016.
 */
public class ReviewResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private Review[] results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("total_results")
    private int total_results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Review[] getResults() {
        return results;
    }

    public void setResults(Review[] results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
