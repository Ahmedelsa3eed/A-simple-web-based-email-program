package com.example.email.Server;

import com.example.email.Server.registeriation.UserRegisteriation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@CrossOrigin
@Controller
public class ServerController {
    @PostMapping("/register")
    @ResponseBody
    public boolean signUp(@RequestBody User user) throws IOException {
        UserRegisteriation userRegisteriation = new UserRegisteriation();
        return userRegisteriation.signUp(user);
    }

    @PostMapping("/signIn")
    @ResponseBody
    public boolean signIn(@RequestBody User user){
        SignIn signIn = new SignIn();
        return signIn.signIn(user);
    }

    @PostMapping("/addContact")
    @ResponseBody
    public ContactUser addContact(@RequestBody User user, ContactUser contactUser){
        ContactUser c = new ContactUser();
        return c.addContact(user, contactUser);
    }

}
