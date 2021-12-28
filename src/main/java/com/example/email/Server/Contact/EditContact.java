package com.example.email.Server.Contact;

import com.example.email.Server.controller.SingleTonServer;

import java.util.ArrayList;

public class EditContact {
    SingleTonServer server = SingleTonServer.getInstance();

    public void deleteContactUser(String oldContactName){
        server.contacts.removeIf(user -> user.getName().equals(oldContactName));
    }

    public void editContactUser(ContactUser newContactUser, ContactUser oldContactUser){
        int index = server.contacts.indexOf(oldContactUser);
        server.contacts.set(index, newContactUser);
    }

    //sorting
    public void sort(String whatToSort, String sortBy){
        for (int i=0 ; i<server.contacts.size()-1 ; i++){

            for (int j=i+1 ; j < server.contacts.size() ; j++){

                if ( sortBy(server.contacts.get(i), sortBy).compareTo(sortBy(server.contacts.get(j), sortBy)) > 0 ){

                    ContactUser temp = server.contacts.get(i);
                    server.contacts.set(i, server.contacts.get(j));
                    server.contacts.set(j, temp);
                }
            }
        }


    }
    private String sortBy(ContactUser user ,String sortBy){
        switch (sortBy){
            case "name":
                return user.getName();
            default:
                return user.getName();
        }
    }

    //search by name till now
    public ArrayList<ContactUser> search(String searchBar, String searchBy){

        ArrayList<ContactUser> searchedContacts = new ArrayList<>();

        for (ContactUser user : server.contacts) {
            if (toBeSearched(user, searchBy).contains(searchBar)) {
                searchedContacts.add(user);
            }
        }
        return searchedContacts;
    }
    //take the search_by then returns the string to be searched
    private String toBeSearched(ContactUser user, String searchBy){
        switch (searchBy){
            case "name":
                return user.getName();
            default:
                return user.getName();
        }
    }
}


/**
 * Search and sort by Date
 *
 * 1.contact
 *   rename , delete , search , sort
 *
 * 2.handle the priority stars 0 or 1 or 2 or 3 or 4
 *  priority queue to view important emails first, you should support at least 4 priorities.
 *  Priority will be assigned when composing a new email manually
 *
 *  3.draft when sending you click discard, put this email in the draft of that user.
 *  4.attachment
 *  5.auto delete emails after 30 days.
 *  6.You should enable selecting multiple emails to be able to bulk move, delete, ...etc.
 *  7.You should implement a refresh button to check for any new email.
 * **/