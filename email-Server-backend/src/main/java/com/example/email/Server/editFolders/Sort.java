package com.example.email.Server.editFolders;

import com.example.email.Server.model.Email;
import com.example.email.Server.controller.SingleTonServer;

import java.util.ArrayList;

public class Sort {

    SingleTonServer server=SingleTonServer.getInstance();

    public void sort(String whatToSort, String sortBy){
        ArrayList<Email> listOfMails=sortFile(whatToSort);
        for (int i=0 ; i<listOfMails.size()-1 ; i++){

            for (int j=i+1 ; j < listOfMails.size() ; j++){

                if ( sortBy(listOfMails.get(i),sortBy).toLowerCase().compareTo(sortBy(listOfMails.get(j),sortBy).toLowerCase()) > 0
                        && !sortBy.equals("date")
                        || (sortBy.equals("date")
                        && sortBy(listOfMails.get(i),sortBy).compareTo(sortBy(listOfMails.get(j),sortBy)) < 0 ) ){

                    Email temp = listOfMails.get(i);
                    listOfMails.set(i, listOfMails.get(j)) ;
                    listOfMails.set(j, temp) ;
                }
            }
        }
    }

    public ArrayList<Email> sortFile(String whatToSort){

        switch (whatToSort){
            case "inbox":
                return server.inbox;
            case "sent":
                return server.sent;
            case "trash":
                return server.trash;
            default:
                return server.draft;
        }

    }

    public String sortBy(Email mail ,String sortBy){
        switch (sortBy){
            case "subject":
                return mail.getSubject();
            case "to":
                return mail.getReceiver();
            case "body":
                return mail.getBody();
            case "date":
                return mail.getDate();
            case "priority":
                return mail.getPriority();
            default:
                return mail.getSender();
        }
    }
}
