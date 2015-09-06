package com.processing;

import com.databaseconnection.Brand;
import com.databaseconnection.DatabaseConnection;
import com.dataretrievtion.RetrieveData;
import com.dataretrievtion.Tweet;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public void diplayPreprocessedTweet(ArrayList<String> tweets){
        for (String tweet : tweets){
            System.out.println(tweet);
        }
    }

    public void diplaySentimentAnalysedTweet(ArrayList<Tweet> tweetObjectList){

        for(Tweet tweetObject : tweetObjectList){
            System.out.println(tweetObject.getTweet()+" : "+
                    tweetObject.getSentimentRank());
        }
    }

    public void displayBrandList(ArrayList<Brand> brands){
        for ( Brand brand : brands){
            System.out.println(brand.getBrandName());
            System.out.println(brand.getSearchTerm());
            System.out.println();

        }
    }


    public static void main(String[] args) throws IOException {

        Main mainObject = new Main();
        DatabaseConnection databaseConnection = new DatabaseConnection();

        ArrayList<Tweet> tweetObjectList;
        //for storing tweets and rank
        ArrayList<String> tweets = new ArrayList<String>();
        ArrayList<Integer> tweetRank;

        //Getting brand list
        ArrayList<Brand> brands;
        brands = databaseConnection.getBrandList();


        //Retrieving Tweets from twitter
        System.out.println("Retrieving Tweets from twitter :\n");
        tweetObjectList = RetrieveData.getTweetObjectList(brands);
        int sizeOfTweet = tweetObjectList.size();

        for (Tweet aTweetObjectList : tweetObjectList) {
            tweets.add(aTweetObjectList.getTweet());
        }

        //Preprocessing the tweets
        System.out.print("\nPreprocessing the tweets :\n");
        TweetPreproces tweetPreproces = new TweetPreproces();
        tweets = tweetPreproces.getPreprocessedTweet(tweets);

        //sentiment analysis of tweets
        System.out.println("\nProcessing Sentiment Analysis of Tweets:");
        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        tweetRank = sentimentAnalysis.findSentiment(tweets);

        for(int i=0; i<sizeOfTweet; i++){
            tweetObjectList.get(i).setSentimentRank(tweetRank.get(i));
        }

//        mainObject.diplaySentimentAnalysedTweet(tweetObjectList);

        //saving data to mongodb


        databaseConnection.init();
        databaseConnection.insertTweetList(tweetObjectList);
//      databaseConnection.countDistinctRank();

        ArrayList<Tweet> tweetArrayList1;
        tweetArrayList1 = databaseConnection.retrieveTweetList();

        for (Tweet aTweetArrayList1 : tweetArrayList1) {
            System.out.println(aTweetArrayList1.getTweet() + " : " + aTweetArrayList1.getSentimentRank() + " : " + aTweetArrayList1.getBrandName());
        }
    }
}
