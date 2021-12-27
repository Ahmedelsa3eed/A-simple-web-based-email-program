package com.example.email.Server.SignIn;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SignIn {
    SingleTonServer server;

    public boolean signIn(User user){
        server = SingleTonServer.getInstance();
        String path ="data\\%s\\info.json";
        path = String.format(path, user.getEmail());
        File data = new File("data");
        String []files = data.list();
        try {
            if ( files == null || files.length == 0){
                System.out.println("Email doesn't exist");
                return false;
            }
            for (String file:files){
                if (file.equals(user.getEmail())){
                    final ObjectMapper objectMapper = new ObjectMapper();
                    User currentUser = objectMapper.readerFor(User.class).readValue(new File(path));
                    if(currentUser.getPassword().equals(user.getPassword())){
                        System.out.println("sign in successfully");
                        server.loadUser(currentUser);
                        return true;
                    }
                    else{
                        System.out.println("password wrong");
                        return false;
                    }
                }
            }
            System.out.println("wrong email address");
            return false;
        }
        catch (IOException e){
            return false;
        }
    }
}
