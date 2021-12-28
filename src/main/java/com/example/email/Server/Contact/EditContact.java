package com.example.email.Server.Contact;

import com.example.email.Server.controller.SingleTonServer;

import java.util.ArrayList;

public class EditContact {
    SingleTonServer server = SingleTonServer.getInstance();

    public boolean deleteContactUser(String oldContactName){
       return server.contacts.removeIf(user -> user.getName().equals(oldContactName));
    }

    public ArrayList<ContactUser> editContactUser(ContactUser newContactUser, ContactUser oldContactUser){
        for(int i=0; i<server.contacts.size(); i++){
            if (oldContactUser.getName().equals(server.contacts.get(i).getName())){
                server.contacts.set(i, newContactUser);
            }
        }
        return server.contacts;
    }

    //sorting
    public void sort(){
        for (int i=0 ; i < server.contacts.size()-1 ; i++){

            for (int j=i+1 ; j < server.contacts.size() ; j++){

                if ( server.contacts.get(i).getName().toLowerCase().compareTo(server.contacts.get(j).getName().toLowerCase()) > 0 ){

                    ContactUser temp = server.contacts.get(i);
                    server.contacts.set(i, server.contacts.get(j));
                    server.contacts.set(j, temp);
                }
            }
        }


    }


    //search by name till now
    public ArrayList<ContactUser> search(String searchBar, String searchBy){

        ArrayList<ContactUser> searchedContacts = new ArrayList<>();

        for (ContactUser user : server.contacts) {
            for (String searched : toBeSearched(user, searchBy)){
                if (searched.contains(searchBar))
                    searchedContacts.add(user);
            }
        }
        return searchedContacts;
    }
    //take the search_by then returns the string to be searched
    private String toBeSearched(ContactUser user, String searchBy){
        switch (searchBy){
            case "name":
                ArrayList<String> name = new ArrayList<>();
                name.add(user.getName());
                return name;
            default:
                return user.getEmails();
        }
    }
}


/**
 *
 *  3.draft when sending you click discard, put this email in the draft of that user.
 *  5.Auto delete emails after 30 days.
 *
 *  6.You should enable selecting multiple emails to be able to bulk move, delete, ...etc.
 *  7.You should implement a refresh button to check for any new email.
 * **/