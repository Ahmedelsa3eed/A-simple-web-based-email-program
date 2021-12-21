package com.example.email.Server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SignIn {

    public boolean signIn(User user){
        User currentUser = new User();
        String path ="data\\%s\\info.json";
        path = String.format(path, user.getEmail());
        System.out.println(path);

        File data = new File("data");
        String []files = data.list();
        if (files.length==0){
            return false;
        }
        else {
            for (String file:files){
                if (file.equals(user.getEmail())){
                    try {
                        final ObjectMapper objectMapper = new ObjectMapper();
                         currentUser = objectMapper.readerFor(User.class).readValue(new File(path));
                         if(currentUser.getPassword().equals(user.getPassword())){
                             System.out.println("sign in successfully");
                             return true;
                         }
                         else{
                             System.out.println("password wrong");
                             return false;
                         }
                    } catch( IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("wrong email address");
            return false;
        }

    }
}
