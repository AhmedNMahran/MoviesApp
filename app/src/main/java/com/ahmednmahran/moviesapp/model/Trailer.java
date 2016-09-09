
package com.ahmednmahran.moviesapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

@Table(name="trailers")
public class Trailer extends Model{

    @Column(name = "trailer_id")
    @SerializedName("id")
    private String mId;

    @Column(name = "trailer_language")
    @SerializedName("iso_639_1")
    private String mLanguage;
    @Column(name = "trailer_country")
    @SerializedName("iso_3166_1")
    private String mCountry;

    @Column(name = "trailer_key")
    @SerializedName("key")
    private String mKey;

    @Column(name = "trailer_name")
    @SerializedName("name")
    private String mName;

    @Column(name = "trailer_site")
    @SerializedName("site")
    private String mSite;

    @Column(name = "trailer_size")
    @SerializedName("size")
    private Long mSize;

    @Column(name = "trailer_type")
    @SerializedName("type")
    private String mType;

    public Trailer(){
        super();
    }

    public String getTrailerId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String iso_639_1) {
        mLanguage = iso_639_1;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public Long getSize() {
        return mSize;
    }

    public void setSize(Long size) {
        mSize = size;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }
}
