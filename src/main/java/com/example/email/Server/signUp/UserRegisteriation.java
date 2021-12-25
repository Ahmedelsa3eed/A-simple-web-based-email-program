package com.example.email.Server.signUp;

import com.example.email.Server.user.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class UserRegisteriation {

    public Boolean signUp(User user) throws IOException {
        File file = new File("data" + "\\"+user.getEmail());
        if(checkMailAddress(user.getEmail())){
            System.out.println("Error there exist user with that name");
            return false;
        }

        file.mkdirs();
        File infoFile = createJsonFile(file.getPath());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(infoFile, user);
        createDirectories(file);
        return true;
    }

    public void createDirectories(File file){
        FolderFactory factory = new FolderFactory();
        String path = file.getPath()+"\\";
        factory.createFolder(path+"inbox");
        factory.createFolder(path+"trash");
        factory.createFolder(path+"contacts");
        factory.createFolder(path+"sent");
        factory.createFolder(path+"draft");
    }

    public File createJsonFile(String path) throws IOException {
        path+="\\"+"info.json";
        System.out.println(path);
        File infoFile = new File(path);
        infoFile.createNewFile();
        return infoFile;

    }

    private boolean checkMailAddress(String mailAddress) {

        File data = new File("data");
        String []files = data.list();
        if (files.length == 0)return false;
        else{
            for (String file:files){
                if (file.equals(mailAddress)){
                    return true;
                }
            }
        }
        return false;
    }





}