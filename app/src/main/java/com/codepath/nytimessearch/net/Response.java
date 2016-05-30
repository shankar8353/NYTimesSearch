
package com.codepath.nytimessearch.net;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class Response {

    @SerializedName("docs")
    public List<Doc> docs = new ArrayList<Doc>();

}
