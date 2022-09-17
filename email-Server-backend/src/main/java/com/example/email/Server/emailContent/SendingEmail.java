package com.example.email.Server.emailContent;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.*;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SendingEmail {
    SingleTonServer server = SingleTonServer.getInstance();

    public void send(Email email){
        try {
            email.setDate(LocalDateTime.now().toString());
            email.setAttachmentPath(server.attachmentId);
            email.setAttachmentsName();
            server.attachmentId = null;
            User receiver = getUserInfo(email.getReceiver());
            String pathReceiver = "data/"+receiver.getEmail()+"/inbox/"+receiver.getIdReceive();

            FolderFactory factory = new FolderFactory();
            factory.createFolder(pathReceiver);

            server.getUser().setIdSend(server.getUser().getIdSend() + 1);
            receiver.setIdReceive(receiver.getIdReceive() + 1);

            createMail(server.getUser(), pathReceiver, email);

            updateUserInfo(receiver);
            System.out.println(email.getAttachmentPath());

            server.sent.add(email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createMail(User user, String path, Email email) throws IOException{
        path+="/"+user.getEmail()+".json";
        File mail = new File(path);
        mail.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(mail, email);
    }

    private User getUserInfo(String mail) throws IOException{
        String path = "data\\"+mail+"\\info.json";
        ObjectMapper objectMapper = new ObjectMapper();
        return (objectMapper.readerFor(User.class).readValue(new File(path)));
    }

    private void updateUserInfo( User user) throws IOException {
        String path = "data\\"+user.getEmail()+"\\info.json";
        File infoFile = new File(path);
        infoFile.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(infoFile, user);
    }

}
