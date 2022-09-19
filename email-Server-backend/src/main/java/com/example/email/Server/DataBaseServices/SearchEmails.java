package com.example.email.Server.DataBaseServices;

import com.example.email.Server.model.Email;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.TextSearchOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class SearchEmails {

    public static Email[] searchEmailsInDB(String userID, String searchKey, String searchPosition){
        System.out.println("Search key is "+searchKey);
        Email[] emails = EmailsServices.getRequestedEmails(userID, searchPosition);
        ArrayList<Email> searchedEmails = new ArrayList<>();
        for (Email email : emails) {
            if (email.getSubject().contains(searchKey) ||
                    email.getBody().contains(searchKey)||
                    email.getReceiver().contains(searchKey)) {
                searchedEmails.add(email);
            }
        }
        Email[] searchedEmailsArray = new Email[searchedEmails.size()];
        searchedEmails.toArray(searchedEmailsArray);
        return searchedEmailsArray;
    }

}
