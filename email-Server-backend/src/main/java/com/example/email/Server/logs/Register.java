package com.example.email.Server.logs;

import com.example.email.Server.model.User;
import com.example.email.Server.folders.FolderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;


public class Register {

    public ResponseEntity<String> signUp(User user) {
        try {
            File file = new File("data" + "\\"+user.getEmail());
            if(checkMailAddress(user.getEmail())){
                return new ResponseEntity<>("There exist user with that email", HttpStatus.ACCEPTED);
            }

            file.mkdirs();
            File infoFile = createJsonFile(file.getPath());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(infoFile, user);
            createDirectories(file);
            return new ResponseEntity<>(user.getFirstName(), HttpStatus.CREATED);
        }
        catch (IOException e){
            return new ResponseEntity<>("Exception occurred", HttpStatus.BAD_REQUEST);
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