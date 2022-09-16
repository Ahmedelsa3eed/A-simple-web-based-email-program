package com.example.email.Server.DataBaseServices;

import com.example.email.Server.model.User;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UsersServices {


    public static User getUserFromDB(String email){
        MongoDatabase database = DataBase.connectToDB();
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
    public static void creatNewUser(User user){
        MongoDatabase database = DataBase.connectToDB();
        Document document = new Document();
        document.append("firstName", user.getFirstName());
        document.append("secondName", user.getSecondName());
        document.append("email", user.getEmail());
        document.append("password", user.getPassword());
        database.getCollection("Users").insertOne(document);
    }
}
