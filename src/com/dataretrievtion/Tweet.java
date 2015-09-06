package com.dataretrievtion;

import java.util.Date;

/**
 * Created by kiran on 6/29/15.
 */
public class Tweet {

    private Date date;
    private String name;
    private String tweet;
    private int noOfFollower;
    private int retweet;
    private String brandName;
    private int sentimentRank;
    private String sentiment;

    public Tweet(){}

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setNoOfFollower(int noOfFollower) {
        this.noOfFollower = noOfFollower;
    }

    public void setRetweet(int retweet) {
        this.retweet = retweet;
    }

    public void setSentimentRank(int sentimentRank) {
        this.sentimentRank = sentimentRank;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTweet() {
        return tweet;
    }

    public int getNoOfFollower() {
        return noOfFollower;
    }

    public int getRetweet() {
        return retweet;
    }

    public int getSentimentRank() {
        return sentimentRank;
    }
}
