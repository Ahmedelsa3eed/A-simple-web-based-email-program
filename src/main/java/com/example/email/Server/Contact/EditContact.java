package com.example.email.Server.Contact;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.emailContent.Email;

import java.util.ArrayList;

public class EditContact {
    SingleTonServer server = SingleTonServer.getInstance();
    //handle rename
    //handle delete
    //sorting

    public void sort(String whatToSort,String sortBy){
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
    public String sortBy(ContactUser user ,String sortBy){
        switch (sortBy){
            case "name":
                return user.getName();
            default:
                return user.getName();
        }
    }
    //searcin
//    public ArrayList<Email> search(String searchBar, String searchPosition, String searchBy){
//
//        ArrayList<Email> searchedMails = new ArrayList<>();
//        ArrayList<Email> whereToSearch = getSearchingPosition(searchPosition);
//
//        for (Email email : whereToSearch) {
//            if (toBeSearched(email, searchBy).contains(searchBar)) {
//                searchedMails.add(email);
//            }
//        }
//        return searchedMails;
//    }


}


/**
 * 1.contact
 *   rename , delete , search , sort
 *
 * 2.handle the priority stars 0 or 1 or 2 or 3 or 4
 *  priority queue to view important emails first, you should support at least 4 priorities.
 *  Priority will be assigned when composing a new email manually
 *
 *  3.draft
 *  when sending you click discard, put this email in the draft of that user.
 *
 *  4.sea
 *aloooo
 *join meeting
 * swany m3l2
 * OK
 *
 * **/