package com.example.email.Server.emailContent;

import com.example.email.Server.network.SingleTonServer;
import com.example.email.Server.user.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SendingEmail {
    SingleTonServer server;
    String pathSender;
    String pathReceiver;
    Email email;
    /*
     * first, update the userinfo file of the receiver
     * second, update the data at the singleton server
     * don't forgot to update the userinfo before logout
     * */
    public void send(Email email){
        this.email = email;
        try {
            server = SingleTonServer.getInstance();
            User receiver = getUserInfo(email.getTo());
           // pathSender = "data\\"+server.getUser().getEmail()+"\\sent\\"+server.getUser().getIdSend();
            pathReceiver = "data/"+receiver.getEmail()+"/inbox/"+receiver.getIdReceive();

            FolderFactory factory = new FolderFactory();
            //factory.createFolder(pathSender);
            factory.createFolder(pathReceiver);

            server.getUser().setIdSend(server.getUser().getIdSend() + 1);
            receiver.setIdReceive(receiver.getIdReceive() + 1);

            createMail(server.getUser(), pathReceiver, email);
            //createMail(receiver, pathSender, email);

            updateUserInfo(receiver);
            server.sent.add(email);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // create mail in receiver json
    public void createMail(User user, String path, Email email) throws IOException{
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

    public User getUserInfo(String mail) throws IOException{
        String path = "data\\"+mail+"\\info.json";
        ObjectMapper objectMapper = new ObjectMapper();
        return (objectMapper.readerFor(User.class).readValue(new File(path)));
    }

    public void updateUserInfo( User user) throws IOException {
        String path = "data\\"+user.getEmail()+"\\info.json";
        File infoFile = new File(path);
        infoFile.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(infoFile, user);
    }

}
