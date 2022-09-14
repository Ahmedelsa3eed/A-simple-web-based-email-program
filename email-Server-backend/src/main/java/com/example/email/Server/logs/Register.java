package com.example.email.Server.logs;

import com.example.email.Server.DataBase;
import com.example.email.Server.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Register {
    public ResponseEntity<String> signUp(User user) {
        //check if the user already exists
        if (DataBase.getUser(user.getEmail()) != null) {
            return new ResponseEntity<>("There exist user with that email", HttpStatus.ACCEPTED);
        }else {
            DataBase.setUser(user);
            System.out.println("User added");
            return new ResponseEntity<>(user.getFirstName(), HttpStatus.CREATED);
        }
    }
}