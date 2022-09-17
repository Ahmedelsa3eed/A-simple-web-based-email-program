package com.example.email.Server.DataBaseServices;

import com.example.email.Server.model.Email;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class EmailsServices {

    public static String getUserIDFromDB(String email){
        MongoDatabase database = DataBase.connectToDB("MailServer");
        Document document = database.getCollection("Users").find(new Document("email", email)).first();
        System.out.println(document);
        if(document != null){
            return document.get("_id").toString();
        }
        return null;
    }

    public static void sendEmail(Email email) {
        System.out.println(email.getFrom());
        System.out.println(email.getTo());

        String senderID = getUserIDFromDB(email.getFrom());
        String receiverID = getUserIDFromDB(email.getTo());
        System.out.println("The sender Id is  "+senderID);
        System.out.println("The receiver Id is"+receiverID);
        if(senderID == null || receiverID == null){
            System.out.println("Error in sending email to " + email.getTo());
            return;
        }
        MongoDatabase senderDatabase = DataBase.connectToDB(senderID);
        MongoDatabase receiverDatabase = DataBase.connectToDB(receiverID);
        System.out.println("Email sent to " + email.getTo());
        Document document = new Document();
        document.append("sender", email.getFrom());
        document.append("receiver", email.getTo());
        document.append("subject", email.getSubject());
        document.append("content", email.getBody());
        document.append("date", email.getDate());
        document.append("priority", email.getPriority());
        System.out.println("Email sent to " + email.getTo());
        senderDatabase.getCollection("Sent").insertOne(document);
        receiverDatabase.getCollection("Inbox").insertOne(document);
    }

    public static ArrayList<Email> getRequestedEmails(String userEmail, String CollectionName){
        String userID = getUserIDFromDB(userEmail);
        MongoDatabase database = DataBase.connectToDB(userID);
        ArrayList<Email> emails = new ArrayList<>();
        for (Document document : database.getCollection(CollectionName).find()) {
            System.out.println(document);
            Email email = new Email();
            email.setFrom((String) document.get("sender"));
            email.setTo((String) document.get("receiver"));
            email.setSubject((String) document.get("subject"));
            email.setBody((String) document.get("content"));
            email.setDate((String) document.get("date"));
            email.setPriority((String) document.get("priority"));
            emails.add(email);
        }
        return emails;
    }
    public static void removeMailFromInbox(String userEmail, String emailId){
        String userID = getUserIDFromDB(userEmail);
        MongoDatabase database = DataBase.connectToDB(userID);
        MongoCollection<Document> collection = database.getCollection("Inbox");
        System.out.println("To be deleted " + collection.find(new Document("_id", new ObjectId(emailId) )).first());
        Bson query = eq("_id", new ObjectId(emailId));
        try {
            database.getCollection("Trash").insertOne(Objects.requireNonNull(collection.find(query).first()));
            DeleteResult result = collection.deleteOne(query);
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }

    }

}
