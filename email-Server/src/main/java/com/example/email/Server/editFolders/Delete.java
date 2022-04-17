package com.example.email.Server.editFolders;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.logOut.LogOut;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Delete {
    //decrement id

    SingleTonServer server = SingleTonServer.getInstance();
    //we get the email that should be removed
    //we should remove it from the linked list of sent or inbox or .... let work for  inbox now
    //so we have array[] inbox of mail and tha mail to be canceled
    //after that i shloud bush the mail in array list of trash
    //it is so easy so i should remove the email from the array list
    //lets do it

    public void deleteEmail(Email email, String position){
        //here we refresh to load all data from the server so that we
        server.loadInbox();
        LogOut l=new LogOut();


        ArrayList<Email> list =  whereToDelete(position);

            for (int i =0 ; i < list.size();i++){
                if (email.getDate().equals(list.get(i).getDate())){

                    list.set(i,email);

                }
            }

        list.remove(email);
        server.trash.add(email);
        l.saveInbox();

    }

    private ArrayList<Email> whereToDelete(String position){
        switch (position){
            case "inbox":
                return server.inbox;
            case "sent":
                return server.sent;
            default:
                return server.draft;
        }
    }


    public void removeFromTrash(Email email){

        for (int i =0 ; i < server.trash.size();i++){
            if (email.getDate().equals(server.trash.get(i).getDate())){
                server.trash.set(i,email);
            }
        }

        server.trash.remove(email);
    }
    private void delete(User user, int id, String folderName) {

        Path trashFile= Paths.get("data\\"+user.getEmail()+"\\"+folderName+"\\"+ id);
        Path x=Paths.get("data\\"+user.getEmail()+"\\trash\\"+user.getIdTrash());
        user.setIdTrash(user.getIdTrash()+1);
        try {
            Files.move(trashFile,x);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
