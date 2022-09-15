package com.example.email.Server.DataBaseServices;

import com.example.email.Server.model.Email;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class EmailsServices {


    public static void sendEmail(Email email) {
        MongoDatabase database = DataBase.connectToDB();
        System.out.println("Email sent to " + email.getTo());

        if (database.getCollection("Users").find(new Document("email", email.getTo())).first() != null) {
            System.out.println("User found");
            Document document = new Document();
            document.append("sender", email.getFrom());
            document.append("receiver", email.getTo());
            document.append("subject", email.getSubject());
            document.append("content", email.getBody());
            document.append("date", email.getDate());
            document.append("priorit", email.getPriority());
            database.getCollection("Emails").insertOne(document);
        }
    }
    public static ArrayList<Email> getMailsFromDB(String userEmail, String type){
        MongoDatabase database = DataBase.connectToDB();
        ArrayList<Email> emails = new ArrayList<>();
        for (Document document : database.getCollection("Emails").find(new Document(type, userEmail))) {
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
    public static void removeMailFromInbox(){
        MongoDatabase database = DataBase.connectToDB();
        MongoCollection<Document> collection = database.getCollection("Emails");
        System.out.println("To be deleted " + collection.find(new Document("_id", new ObjectId("6322dad9bef0a65ddf2f499b") )).first());
        Bson query = eq("_id", new ObjectId("6322dad9bef0a65ddf2f499b"));
        try {
            database.getCollection("Trashs").insertOne(collection.find(query).first());
            DeleteResult result = collection.deleteOne(query);
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }

    }

}
