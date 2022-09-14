package com.example.email.Server;

import com.example.email.Server.model.Email;
import com.example.email.Server.model.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class DataBase {


    static ConnectionString connectionString = new ConnectionString("mongodb+srv://MailServer:Mailserver123@mailserver.5krhxva.mongodb.net/?retryWrites=true&w=majority");
    static MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
    static MongoClient mongoClient = MongoClients.create(settings);
    private static final MongoDatabase database = mongoClient.getDatabase("MailServer");

    public DataBase(){

    }

    public static User getUser(String email){
        Document document = database.getCollection("Users").find(new Document("email", email)).first();
        if(document != null){
            User user = new User();
            user.setFirstName((String) document.get("firstName"));
            user.setSecondName((String) document.get("secondName"));
            user.setEmail((String) document.get("email"));
            user.setPassword((String) document.get("password"));
            return user;
        }
        return null;
    }
    public static void setUser(User user){
        Document document = new Document();
        document.append("firstName", user.getFirstName());
        document.append("secondName", user.getSecondName());
        document.append("email", user.getEmail());
        document.append("password", user.getPassword());
        database.getCollection("Users").insertOne(document);
    }

    public static void sendEmail(Email email){
        System.out.println("Email sent to "+ email.getTo());

        if(database.getCollection("Users").find(new Document("email", email.getTo())).first() != null){
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
    public static ArrayList<Email> getSent(){
        return null;

    }

}
