package com.example.email.Server.logs;

import com.example.email.Server.DataBase;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SignIn {
    SingleTonServer server;
    DataBase dataBase;

    public ResponseEntity<User> signIn(User user){
        server = SingleTonServer.getInstance();
        //get the correct password from mongodb
        User currentUser = DataBase.getUser( user.getEmail());
        if (currentUser == null){
            return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
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