package com.example.email.Server.logs;

import com.example.email.Server.DataBase;
import com.example.email.Server.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Register {
    DataBase dataBase;
    public ResponseEntity<String> signUp(User user) {
        dataBase = DataBase.getInstance();
        //check if the user already exists
        if (dataBase.getUser(user.getEmail()) != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.ACCEPTED);
        }else {
            dataBase.setUser(user);
            return new ResponseEntity<>(user.getFirstName(), HttpStatus.CREATED);
        }
    }
}