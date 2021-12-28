package com.example.email.Server.controller;

import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*SingleTon DP*/
public class SingleTonServer {
    private static SingleTonServer server;
    private User user;
    public ArrayList<Email> sent;
    public ArrayList<Email> inbox;
    public ArrayList<Email> trash;
    public ArrayList<Email> draft;
    public ArrayList<ContactUser> contacts;
    public String attachmentId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private SingleTonServer(){
        sent = new ArrayList<>();
        inbox = new ArrayList<>();
        trash = new ArrayList<>();
        draft = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    public static SingleTonServer getInstance(){
        if (server == null){
            server = new SingleTonServer();
        }
        return server;
    }

    public void loadUser(User user){
        setUser(user);
        try{
            File data = new File("data\\"+user.getEmail());
            String[] folders = data.list();
            for (String folder: folders){
                if (!folder.equals("info.json") && !folder.equals("attachments")) {
                    String path = "data\\" + user.getEmail() + "\\" + folder;
                    File f = new File(path);
                    String[] files = f.list();
                    for (String file : files) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        File jsonFile = new File(path + "\\" + file);
                        String[] jsonFiles = jsonFile.list();
                        if (!folder.equals("contacts")){
                            Email mail = objectMapper.readerFor(Email.class)
                                    .readValue(new File(jsonFile.getPath() + "\\" + jsonFiles[0]));
                            arrayFactory(folder, mail);
                        }
                        else {
                            ContactUser contactUser =  objectMapper.readerFor(ContactUser.class)
                                    .readValue(new File(jsonFile.getPath()));
                            server.contacts.add(contactUser);
                        }

                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            //return false;
        }

    }

    private void arrayFactory(String folder, Email email){
        switch (folder){
            case "inbox":
                inbox.add(email);
                break;
            case "sent":
                sent.add(email);
                break;
            case "trash":
                trash.add(email);
                break;
            case "draft":
                draft.add(email);
                break;
        }
    }

    public void resetServer(){
        user = new User();
        sent = new ArrayList<>();
        inbox = new ArrayList<>();
        trash = new ArrayList<>();
        draft = new ArrayList<>();
    }

}
