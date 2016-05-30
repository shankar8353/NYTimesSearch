
package com.codepath.nytimessearch.net;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class Doc {

    @SerializedName("web_url")
    public String webUrl;

    @SerializedName("multimedia")
    public List<Multimedia> multimedia = new ArrayList<>();

    @SerializedName("headline")
    public Headline headline;

    @SerializedName("_id")
    public String id;

}
