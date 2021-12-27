package com.example.email.Server.signUp;

import com.example.email.Server.user.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class Register {

    public Boolean signUp(User user) {
        try {
            File file = new File("data" + "\\"+user.getEmail());
            if(checkMailAddress(user.getEmail())){
                //System.out.println("There exist user with that email");
                return false;
            }

            file.mkdirs();
            File infoFile = createJsonFile(file.getPath());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(infoFile, user);
            createDirectories(file);
            return true;
        }
        catch (IOException e){
            return false;
        }

    }

    private void createDirectories(File file){
        FolderFactory factory = new FolderFactory();
        String path = file.getPath()+"\\";
        factory.createFolder(path+"inbox");
        factory.createFolder(path+"trash");
        factory.createFolder(path+"contacts");
        factory.createFolder(path+"sent");
        factory.createFolder(path+"draft");
    }

    private File createJsonFile(String path) throws IOException {
        path+="\\"+"info.json";
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