package com.example.email.Server.emailContent;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.user.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SendingEmail {
    SingleTonServer server;
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
           // email.setDate(LocalDateTime.now().toString());
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
    private boolean move(File sourceFile, File destFile)
    {
        if (sourceFile.isDirectory())
        {
            for (File file : sourceFile.listFiles())
            {
                move(file, new File(file.getPath().substring("temp".length()+1)));
            }
        }
        else
        {
            try {
                Files.move(Paths.get(sourceFile.getPath()), Paths.get(destFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

}
