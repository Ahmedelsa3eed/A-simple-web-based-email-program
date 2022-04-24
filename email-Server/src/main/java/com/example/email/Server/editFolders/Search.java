package com.example.email.Server.editFolders;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.controller.SingleTonServer;

import java.util.ArrayList;

public class Search {
    SingleTonServer server = SingleTonServer.getInstance();

   public ArrayList<Email> search(String searchBar, String searchPosition, String searchBy){

       ArrayList<Email> searchedMails = new ArrayList<>();
       ArrayList<Email> whereToSearch = getSearchingPosition(searchPosition);

       for (Email email : whereToSearch) {
           if ( toBeSearched(email, searchBy).contains(searchBar) ) {
               searchedMails.add(email);
           }
       }
       return searchedMails;
   }

    //take the folder name then returns the arraylist of emails of that folder
    public ArrayList<Email> getSearchingPosition(String searchPosition){
       switch (searchPosition){
           case "draft":
               return server.draft;
           case "sent":
               return server.sent;
           case "trash":
               return server.trash;

           default:
               return server.inbox;
       }

   }

   //take the search_by then returns the string to be searched

   public String toBeSearched(Email mail, String searchBy){
       switch (searchBy){
           case "body":
               return mail.getBody();
           case "subject":
               return mail.getSubject();
           case "from":
               return mail.getFrom();
           case "date":
               return mail.getDate();
           case "priority":
               return mail.getPriority();
           default:
               return mail.getTo();
       }
   }
}
