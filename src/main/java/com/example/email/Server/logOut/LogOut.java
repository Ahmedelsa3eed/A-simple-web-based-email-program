package com.example.email.Server.logOut;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.folders.FolderFactory;
import com.example.email.Server.folders.JsonFactory;
import com.example.email.Server.network.SingleTonServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.logging.java.JavaLoggingSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.Paths.get;

public class LogOut {
    SingleTonServer server = SingleTonServer.getInstance();

    public void save(){
        //this function will be used to save from server to hdd all the updates on user server.
        //we have arraylist of inbox each has in an email object json file
        //we update and replace all files
        //this we be edited by me
        try {
            String[] files={"sent","trash","draft"};
            for (String whatToSave:files) {
                String dirPath = "data\\" + server.getUser().getEmail() + "\\" + whatToSave;

                File dir = new File(dirPath);
                removeDirectory(dir);

                for (int i = 0; i < getListOf(whatToSave).size(); i++) {
                    String pathSent = "data\\" + server.getUser().getEmail() + "\\" + whatToSave + "\\" + i;

                    FolderFactory factory = new FolderFactory();
                    factory.createFolder(pathSent);
                    pathSent += "\\" + getListOf(whatToSave).get(i).getTo() + ".json";//Saed there will be update here as we modified server and mail

                    JsonFactory jsonFactory = new JsonFactory();
                    jsonFactory.createJsonFile(new File(pathSent), getListOf(whatToSave).get(i));

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        server.resetServer();
    }
    public static void removeDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    removeDirectory(aFile);
                }
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
    public ArrayList<Email> getListOf(String whatToSave){
        switch (whatToSave){
            case "sent":
                return server.sent;
            case "draft":
                return server.draft;
            default:
                return server.trash;

            // we shold add here if we want to save any other thing
        }


    }
}
