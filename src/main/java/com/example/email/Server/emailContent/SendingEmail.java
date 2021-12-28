package com.example.email.Server.emailContent;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.user.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

public class SendingEmail {
    SingleTonServer server = SingleTonServer.getInstance();
    /*
     * first, update the userinfo file of the receiver
     * second, update the data at the singleton server
     * don't forgot to update the userinfo before logout
     * */
    public void send(Email email){
        try {
            email.setDate(LocalDateTime.now().toString());
            email.setAttachmentPath(server.attachmentId);
            email.setAttachmentsName();
            server.attachmentId = null;
            User receiver = getUserInfo(email.getTo());
            String pathReceiver = "data/"+receiver.getEmail()+"/inbox/"+receiver.getIdReceive();

            FolderFactory factory = new FolderFactory();
            factory.createFolder(pathReceiver);

            server.getUser().setIdSend(server.getUser().getIdSend() + 1);
            receiver.setIdReceive(receiver.getIdReceive() + 1);

            createMail(server.getUser(), pathReceiver, email);
            //createMail(receiver, pathSender, email);

            updateUserInfo(receiver);
            System.out.println(email.getAttachmentPath());

            server.sent.add(email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // create mail in receiver json
    private void createMail(User user, String path, Email email) throws IOException{
        path+="/"+user.getEmail()+".json";
        File mail = new File(path);
        mail.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(mail, email);
    }


//    public void uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
//        FileResource fileResource = new FileResource();
//        fileResource.uploadFiles(multipartFiles, pathReceiver);
////        fileResource.uploadFiles(multipartFiles, pathSender);
//        email.multipartFiles = multipartFiles;
//        server.sent.add(email);
//        createMail(server.getUser(), pathReceiver, email);
//    }

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
