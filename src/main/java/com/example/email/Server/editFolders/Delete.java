package com.example.email.Server.editFolders;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Delete {
    //decrement id

    SingleTonServer server=SingleTonServer.getInstance();
    //we get the email that should be removed
    //we should remove it from the linked list of sent or inbox or .... let work for  inbox now
    //so we have array[] inbox of mail and tha mail to be canceled
    //after that i shloud bush the mail in array list of trash
    //it is so easy so i should remove the email from the array list
    //lets do it

    public void deleteEmail(Email email, String position){
    ArrayList<Email> list =  whereToDelete(position);
        for (int i =0 ;i<list.size();i++) {
            if (email.isEqual(list.get(i), email))  {
                 list.set(i, email);
            }
        }
        list.remove(email);
        server.trash.add(email);
        whereToDelete(position).equals(list);
    }

    public ArrayList<Email> whereToDelete(String position){
        switch (position){
            case "inbox":
                return server.inbox;
            case "sent":
                return server.sent;

            default:
                return server.draft;
        }
    }

    public void delete(User user, int id, String folderName) {
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
