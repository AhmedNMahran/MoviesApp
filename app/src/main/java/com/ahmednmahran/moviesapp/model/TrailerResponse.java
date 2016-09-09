
package com.ahmednmahran.moviesapp.model;

import com.google.gson.annotations.SerializedName;

public class TrailerResponse {

    @SerializedName("id")
    private String mId;
    @SerializedName("results")
    private Trailer[] mTrailers;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Trailer[] getResults() {
        return mTrailers;
    }

    public void setResults(Trailer[] trailers) {
        mTrailers = trailers;
    }

}
