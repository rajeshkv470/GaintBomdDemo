package com.example.gaintbomddemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GaintBombResponse {

    @SerializedName("status_code")
    public int statusCode;


    @SerializedName("number_of_total_results")
    public int numberOfTotalResults;

    @SerializedName("number_of_page_results")
    public int numberOfPageResults;

    @SerializedName("offset")
    public int offset;

    @SerializedName("limit")
    public int limit;

    @SerializedName("results")
    public List<Result> results;

    public static class Result {

        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;

        @SerializedName("deck")
        public String deck;

        @SerializedName("date_last_updated")
        public String dateLastUpdated;

        @SerializedName("image")
        public Image image;
    }

    public static class Image {
        @SerializedName("medium_url")
        public String mediumUrl;
    }

}
