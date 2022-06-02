package com.example.email.Server.logs;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public class SignIn {
    SingleTonServer server;

    public ResponseEntity<User> signIn(User user){
        server = SingleTonServer.getInstance();
        String path ="data\\%s\\info.json";
        path = String.format(path, user.getEmail());
        File data = new File("data");
        String []files = data.list();
        try {
            /* Email doesn't exist */
            if ( files == null || files.length == 0){
                return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
            }
            for (String file:files){
                if (file.equals(user.getEmail())){
                    ObjectMapper objectMapper = new ObjectMapper();
                    User currentUser = objectMapper.readerFor(User.class).readValue(new File(path));
                    /* sign in successfully */
                    if(currentUser.getPassword().equals(user.getPassword())){
                        server.loadUser(currentUser);
                        return new ResponseEntity<>(currentUser, HttpStatus.OK);
                    }
                    /* Wrong Password! */
                    else{
                        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
                    }
                }
            }
            /* wrong email address */
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
        catch (IOException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}