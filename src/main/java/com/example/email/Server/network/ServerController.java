package com.example.email.Server.network;

import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.editFolders.Search;
import com.example.email.Server.emailContent.FileResource;
import com.example.email.Server.user.User;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.signUp.UserRegisteriation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@CrossOrigin
@Controller
/*Server controller is the facade of this system*/
public class ServerController {
//    SendingEmail s;
    @PostMapping("/register")
    @ResponseBody
    public boolean signUp(@RequestBody User user) throws IOException {
        UserRegisteriation userRegisteriation = new UserRegisteriation();
        return userRegisteriation.signUp(user);
    }

    /*
    * upon login_in load the folders of that user in the server
    */
    @PostMapping("/signIn")
    @ResponseBody
    public boolean signIn(@RequestBody User user){
        SignIn signIn = new SignIn();
        SingleTonServer server = SingleTonServer.getInstance();
        server.loadUser(user);
        return signIn.signIn(user);
    }

    @PostMapping("/send")
    @ResponseBody
    public void send(@RequestBody Email email){
        SendingEmail s = new SendingEmail();
        s.send(email);
    }

//    @PostMapping("/upload")
//    @ResponseBody
//    public void upload(@RequestParam("files")List<MultipartFile> multipartFiles){
//        SingleTonServer server = SingleTonServer.getInstance();
//        server.multipartFiles = multipartFiles;
//    }

    @GetMapping("/search")
    public void search(@RequestParam String searchBar, String searchPosition, String searchBy){
        Search s = new Search();
        s.search(searchBar, searchPosition, searchBy);
    }

    @PostMapping("/addContact")
    @ResponseBody
    public ContactUser addContact(@RequestBody User user, ContactUser contactUser){
        ContactUser c = new ContactUser();
        return c.addContact(user, contactUser);
    }

    @GetMapping("/logOut")
    public void logOut(){
        //put user values in info.json hello mi senti ?

    }
}
