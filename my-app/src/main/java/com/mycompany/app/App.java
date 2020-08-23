package com.mycompany.app;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import com.mongodb.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.*;

class Room {
    void add_room(String type, int price) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection col = db.getCollection("Rooms");
        Document document = new Document();
        document.put("type", type);
        document.put("price", price);
        col.insertOne(document);
        mongoClient.close();
    }
}
class Booking {
    void create_new_booking(String type, String name, Date in, Date out) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        Random rand = new Random();
        int selected = rand.nextInt(100000);
        MongoCollection col = db.getCollection("Bookings");
        Document document = new Document();
        document.put("Booking_id", selected);
        document.put("type", type);
        Document documentDetail = new Document();
        documentDetail.put("customer_name", name);
        documentDetail.put("check_in_date", in.toString());
        documentDetail.put("check_out_date", out.toString());
        document.put("Booking_details", documentDetail);
        col.insertOne(document);
        mongoClient.close();
    }
    void view_booking() {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection col = db.getCollection("Bookings");
        List<Document> documents = (List<Document>) col.find().into(new ArrayList<Document>());
        for(Document document : documents) {
            System.out.println(document);
        }
        mongoClient.close();
    }
    void remove(int id) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection col = db.getCollection("Bookings");
        Document d = new Document();
        d.put("Booking_id", id);
        col.deleteOne(d);
        mongoClient.close();
    }
    void edit(int id) {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017);
        MongoDatabase db = mongoClient.getDatabase("mydb");
        MongoCollection col = db.getCollection("Bookings");
        UpdateResult result = null;
        result = col.updateOne(Filters.eq("Booking_id", id), new Document("$set", new Document("type", "Double")));
        mongoClient.close();
    }
}

public class App {
    public static void main( String[] args ) {

        for (int i = 0; i < 10; ++i) {
            Room r = new Room();
            r.add_room("Single", 4211);
        }
        for (int i = 0; i < 10; ++i) {
            Date d = new Date();
            Booking b = new Booking();
            b.create_new_booking("Single", "Monk", d, d);
        }
//        Booking b = new Booking();
//        b.remove(56287);
    }
}
/*
 */