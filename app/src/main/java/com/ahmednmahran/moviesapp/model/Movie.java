package com.ahmednmahran.moviesapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed Nabil on 12/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 *
 */
@Table(name = "Movies")
public class Movie extends Model{

    @Column(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @Column(name = "adult")
    private boolean adult;

    @Column(name = "overview")
    private String overview;

    @Column(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @Column(name = "genre_ids")
    @SerializedName("genre_ids")
    private int[] genreIds;

    @Column(name = "movie_id")
    private int id;

    @Column(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;

    @Column(name="original_language")
    @SerializedName("original_language")
    private String originalLanguage;

    @Column(name = "title")
    private String title;


    @Column(name ="backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @Column(name ="popularity")
    private String popularity;

    @Column(name ="vote_count")
    private int vote_count;

    @Column(name ="video")
    private boolean video;

    @Column(name ="vote_average")
    private double vote_average;

    @Expose
    private boolean favourite;
    public Movie() {
        super();
    }

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, int[] genreIds, int id, String originalTitle, String originalLanguage, String title, String backdropPath, String popularity, int vote_count, boolean video, double vote_average) {
        super();
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public int getMovieId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
