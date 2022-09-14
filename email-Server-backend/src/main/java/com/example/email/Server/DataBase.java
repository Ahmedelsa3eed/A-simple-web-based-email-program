package com.example.email.Server;

import com.example.email.Server.model.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DataBase {

    private static DataBase instance = null;
    private final MongoDatabase database;
    private DataBase(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://MailServer:Mailserver123@mailserver.5krhxva.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("MailServer");

    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;

    }
    public User getUser(String email){
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
    public void setUser(User user){
        Document document = new Document();
        document.append("firstName", user.getFirstName());
        document.append("secondName", user.getSecondName());
        document.append("email", user.getEmail());
        document.append("password", user.getPassword());
        database.getCollection("Users").insertOne(document);
    }
    
}
