package com.example.email.Server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ContactUser {
    private String email;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactUser addContact(User user, ContactUser contactUser) {
        String path ="data" + "\\"+user.getEmail() + "\\contacts\\" + contactUser.getName()+".json";
        if (!checkEmail(contactUser.getEmail())){
            System.out.println("there is not account with that mailAddress");
            return contactUser;
        }
        if (checkContact(user,contactUser.email)){
            System.out.println("there is already a contact with that email");
            return contactUser;
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            File infoFile = createJsonFile(path);
            mapper.writeValue(infoFile , contactUser);
        }
        catch (IOException e){
            System.out.println("error at creating json file");
        }
        return contactUser;
    }
    //find if there exist usere with that mail
    public Boolean checkContact(User user ,String email){
        File contact = new File("data\\"+user.getEmail()+"\\contacts");
        String [] contacts = contact.list();
        if ( contacts.length == 0){
            return false;
        }
        try {
            for (String c : contacts){
                ObjectMapper objectMapper = new ObjectMapper();
                ContactUser contactUser = objectMapper.readerFor(ContactUser.class).readValue(new File(contact.getPath()+"\\"+c));
                System.out.println(c);
                if(contactUser.getEmail().equals(email)){
                    return true;
                }
            }

        }
        catch (IOException e){
            System.out.println("error at loading the contact user");
        }
        return false;
    }
    public Boolean checkEmail(String email){
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

    public File createJsonFile(String path) throws IOException {
        System.out.println(path);
        File infoFile = new File(path);
        infoFile.createNewFile();
        return infoFile;
    }

}
