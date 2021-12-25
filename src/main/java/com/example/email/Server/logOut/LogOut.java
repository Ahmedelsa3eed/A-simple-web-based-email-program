package com.example.email.Server.logOut;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.folders.FolderFactory;
import com.example.email.Server.folders.JsonFactory;
import com.example.email.Server.network.SingleTonServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.logging.java.JavaLoggingSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LogOut {
    SingleTonServer server = SingleTonServer.getInstance();

    public void saveSent(String whatToSave){
        //this function will be used to save from server to hdd all the updates on user server.
        //we have arraylist of inbox each has in an email object json file
        //we update and replace all files

        //this we be edited by me
        for (int i =0; i < server.sent.size(); i++){
            String pathSent = "data\\"+server.getUser().getEmail()+"\\"+whatToSave+"\\"+i;
            FolderFactory factory = new FolderFactory();
            factory.createFolder(pathSent);
            //.getFirstName()
            pathSent+="\\"+getListOf(whatToSave).get(i).getTo()+".json";//Saed there will be update here as we modified server and mail

            try {
                JsonFactory jsonFactory = new JsonFactory();
                jsonFactory.createJsonFile(new File(pathSent), getListOf(whatToSave).get(i));
            } catch (IOException e) {
                System.out.println(e);;
            }

        }

    }

    public ArrayList<Email> getListOf(String whatToSave){
        switch (whatToSave){
            case "sent":
                return server.sent;
            default:
                return server.trash;

            // we shold add here if we want to save any other thing
        }


    }
}
