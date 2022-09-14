package com.example.email.Server;

import com.example.email.Server.model.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;

public class DataBase {

    private static DataBase instance = null;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private DataBase(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://MailServer:Mailserver123@mailserver.5krhxva.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("MailServer");

    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;

    }

    public MongoDatabase getDatabase() {
        return database;
    }
    public User getUser(String email){
        Document document = database.getCollection("Users").find(new Document("email", email)).first();
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        user.setFirstName((String) document.get("firstName"));
        user.setSecondName((String) document.get("secondName"));
        user.setEmail((String) document.get("email"));
        user.setPassword((String) document.get("password"));

        return user;
    }
    public String getPassword(String email){
        try{
        Document doc = database.getCollection("Users").find(new Document("email", email)).first();
        return doc.getString("password");
        }catch(Exception e){
            return "Wrong Email";
        }

    }


    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        DataBase database = DataBase.getInstance();
        System.out.println(database.getPassword("ahmd@gmail.com"));

    }
}
