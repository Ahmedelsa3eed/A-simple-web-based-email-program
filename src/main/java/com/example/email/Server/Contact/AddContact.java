package com.example.email.Server.Contact;

import com.example.email.Server.controller.SingleTonServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddContact {
    SingleTonServer server = SingleTonServer.getInstance();

    public ArrayList<ContactUser> addContact(ContactUser contactUser) {
        for (String email: contactUser.getEmails()){
            if (!checkEmail(email)){
                System.out.println("there is no account with that mailAddress");
                return server.contacts;
            }
        }
        server.contacts.add(contactUser);
        return server.contacts;
    }

    //find if there exist usere with that mail
//    public Boolean checkContact(User user , String email){
//        File contact = new File("data\\"+user.getEmail()+"\\contacts");
//        String [] contacts = contact.list();
//        if ( contacts.length == 0){
//            return false;
//        }
//        try {
//            for (String c : contacts){
//                ObjectMapper objectMapper = new ObjectMapper();
//                ContactUser contactUser = objectMapper.readerFor(ContactUser.class).readValue(new File(contact.getPath()+"\\"+c));
//                System.out.println(c);
//                if(contactUser.getEmail().equals(email)){
//                    return true;
//                }
//            }
//
//        }
//        catch (IOException e){
//            System.out.println("error at loading the contact user");
//        }
//        return false;
//    }

    private boolean checkEmail(String email){
        File data = new File("data");
        String []files = data.list();
        if (files.length==0){
            return false;
        }
        else {
            for (String file : files){
                if (email.equals(file)){
                    return true;
                }
            }
        }
        return false;
    }

    private File createJsonFile(String path) throws IOException {
        System.out.println(path);
        File infoFile = new File(path);
        infoFile.createNewFile();
        return infoFile;
    }

}
