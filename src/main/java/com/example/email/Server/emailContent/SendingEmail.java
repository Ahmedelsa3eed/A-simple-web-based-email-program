package com.example.email.Server.emailContent;

import com.example.email.Server.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SendingEmail {

    public void send(Email email){
        //put in sent folder at sender user
        try {
            String pathSender = "data\\"+email.getUser().getEmail()+"\\sent\\"+email.getUser().getIdSend();
            String pathReceiver = "data\\"+email.getTo().getEmail()+"\\inbox\\"+email.getTo().getIdReceive();

            FolderFactory factory = new FolderFactory();
            factory.createFolder(pathSender);
            factory.createFolder(pathReceiver);

            email.getUser().setIdSend(email.getUser().getIdSend() + 1);
            email.getTo().setIdReceive(email.getTo().getIdReceive() + 1);

            createMail(email.getUser(), pathReceiver, email);
            createMail(email.getTo(), pathSender, email);

            updateUserInfo(email.getTo());
            updateUserInfo(email.getUser());
        }
        catch (IOException e){
            System.out.println("error at loading the contact user");
        }
    }
    public void createMail(User user, String path, Email email) throws IOException{
        path+="\\"+user.getFirstName()+".json";
        File mail=new File(path);
        mail.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(mail, email);


    }
    public void updateUserInfo( User user) throws IOException {
        String path = "data\\"+user.getEmail()+"\\info.json";
        File infoFile = new File(path);
        infoFile.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(infoFile, user);
    }

}
