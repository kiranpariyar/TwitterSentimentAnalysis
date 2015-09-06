package com.databaseconnection;

import com.dataretrievtion.Tweet;

import com.mongodb.Block;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;

import com.mongodb.MongoClient;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
/**
 * Created by kiran on 7/2/15.
 */
public class DatabaseConnection {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection dbCollection;
    private static final String DATABASE_NAME = "sentimentanalysis";


    public void init() throws IOException {

        //Connect to a MongoDB instance running on the localhost on the default port 27017:
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase(DATABASE_NAME);
        dbCollection = db.getCollection("tweetObject");
    }


    public ArrayList<Brand> getBrandList() throws IOException{
        MongoClient mongoClient1 = new MongoClient();
        MongoDatabase mongoDatabase = mongoClient1.getDatabase(DATABASE_NAME);
        MongoCollection mongoCollection = mongoDatabase.getCollection("brand");


        System.out.println("Collection tweet selected successfully");
        System.out.print("Listing database data");
        final ArrayList<Brand> brands = new ArrayList<Brand>();

        FindIterable<Document> iterable = mongoCollection.find();

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
             System.out.println(document);
                final Brand brand = new Brand();
                brand.setBrandName(document.getString("brandName"));
                brand.setSearchTerm(document.getString("searchTerm"));
                brands.add(brand);
                }
            });

        return brands;
    }

    public void insertTweetList(ArrayList<Tweet> tweetArrayList) throws IOException{

        List<Document> documents = new ArrayList<Document>();

        System.out.println("/**** Inserting data into mongodb *****/");
        for(int i=0; i<tweetArrayList.size(); i++){
            Document document = new Document();
            document.put("date", tweetArrayList.get(i).getDate());
            document.put("name", tweetArrayList.get(i).getName());
            document.put("tweet", tweetArrayList.get(i).getTweet());
            document.put("noOfFollower", tweetArrayList.get(i).getNoOfFollower());
            document.put("retweet", tweetArrayList.get(i).getRetweet());
            document.put("brandName", tweetArrayList.get(i).getBrandName());
            document.put("sentimentRank", tweetArrayList.get(i).getSentimentRank());
            document.put("sentiment", tweetArrayList.get(i).getSentiment());
            documents.add(i,document);
        }

        System.out.print(documents);
        dbCollection.insertMany(documents);
        System.out.println(tweetArrayList.size() + " Data insertion of mongodb successful ");

    }


    public ArrayList<Tweet> retrieveTweetList(){
        System.out.println("Collection tweet selected successfully");
        System.out.print("Listing database data");
        final ArrayList<Tweet> tweetArrayList = new ArrayList<Tweet>();

        FindIterable<Document> iterable = dbCollection.find();

        iterable.forEach((Block<Document>) document -> {
//              System.out.println(document);
            final Tweet tweet = new Tweet();
            tweet.setDate(document.getDate("date"));
            tweet.setName(document.getString("name"));
            tweet.setTweet(document.getString("tweet"));
            tweet.setNoOfFollower(document.getInteger("noOfFollower"));
            tweet.setRetweet(document.getInteger("retweet"));
            tweet.setSentimentRank(document.getInteger("sentimentRank"));
            tweet.setBrandName(document.getString("brandName"));
            tweet.setSentiment(document.getString("sentiment"));
            System.out.println(tweet.getTweet() + " : " + tweet.getSentimentRank());
            tweetArrayList.add(tweet);
        });
        return tweetArrayList;
    }

    public void countDistinctRank(){

        AggregateIterable<Document> iterable = dbCollection.aggregate(asList(
                new Document("$group", new Document("_id", "$sentimentRank").append("count",
                        new Document("$sum", 1)))));
        
        iterable.forEach((Block<Document>) document -> {

//             System.out.println(document.toJson());
           System.out.println(document.get("_id") + " : " + document.get("count"));
        });
    }

}
