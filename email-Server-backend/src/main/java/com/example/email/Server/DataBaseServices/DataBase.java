package com.example.email.Server.DataBaseServices;

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




    public static MongoDatabase connectToDB(){
         ConnectionString connectionString = new ConnectionString("mongodb+srv://MailServer:Mailserver123@mailserver.5krhxva.mongodb.net/?retryWrites=true&w=majority");
         MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
         MongoClient mongoClient = MongoClients.create(settings);

        return mongoClient.getDatabase("MailServer");

    }

}
