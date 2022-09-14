package com.example.email.Server.logs;

import com.example.email.Server.DataBase;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public class SignIn {
    SingleTonServer server;
    DataBase dataBase;

    public ResponseEntity<User> signIn(User user){
        server = SingleTonServer.getInstance();
        dataBase = DataBase.getInstance();
        //get the correct password from mongodb
        User currentUser = dataBase.getUser( user.getEmail());
        if (currentUser == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else if(currentUser.getPassword().equals(user.getPassword())){
            server.loadUser(currentUser);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
    }
}