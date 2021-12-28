package com.example.email.Server.controller;

import com.example.email.Server.Contact.AddContact;
import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.Contact.EditContact;
import com.example.email.Server.DraftEmail;
import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.editFolders.Search;
import com.example.email.Server.emailContent.FileResource;
import com.example.email.Server.user.User;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.signUp.Register;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;

@CrossOrigin
@Controller
/*Server controller is the facade of this system*/
public class ServerController {
    SingleTonServer server;
    //    SendingEmail s;
    @PostMapping("/register")
    @ResponseBody
    public boolean signUp(@RequestBody User user) {
        Register register = new Register();
        return register.signUp(user);
    }

    /*
    * upon login_in load the folders of that user in the server
    */
    @PostMapping("/signIn")
    @ResponseBody
    public boolean signIn(@RequestBody User user){
        SignIn signIn = new SignIn();
        return signIn.signIn(user);
    }

    @PostMapping("/send")
    @ResponseBody
    public void send(@RequestBody Email email){
        SendingEmail s = new SendingEmail();
        s.send(email);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<List<String>> upload(@RequestParam("files") List<MultipartFile> multipartFiles){
        FileResource fileResource = new FileResource();
        server = SingleTonServer.getInstance();
        List<String> list = new ArrayList<>();
        list.add( fileResource.uploadFiles(multipartFiles, server.getUser().getEmail()));
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Email>> inbox(){
        server  = SingleTonServer.getInstance();
        return new ResponseEntity<>(server.inbox, HttpStatus.OK);
    }

    @PostMapping("/draft")
    @ResponseBody
    public void draft(@RequestBody Email email){
        DraftEmail s = new DraftEmail();
        s.addToDraft(email);
    }
    @PostMapping("/sendDraft")
    @ResponseBody
    public void sendDraft(@RequestBody Email email){
        Delete d=new Delete();
        d.deleteEmail(email,"draft");
        send(email);
    }

    //we should add here to edit draft we should get old mail and new mail

    @GetMapping("/search")
    public void search(@RequestParam String searchBar, String searchPosition, String searchBy){
        Search s = new Search();
        s.search(searchBar, searchPosition, searchBy);
    }

    @PostMapping("/addContact")
    @ResponseBody
    public ArrayList<ContactUser> addContact(@RequestBody ContactUser contactUser){
        AddContact c = new AddContact();
        return c.addContact(contactUser);
    }

    @RequestMapping(value = "/deleteContact/{oldContactName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ContactUser>> deleteContact(@PathVariable("oldContactName") String oldContactName) {
        EditContact e = new EditContact();
        e.deleteContactUser(oldContactName);
        return new ResponseEntity<>(server.contacts, HttpStatus.OK);
    }

    @RequestMapping(value = "/editContact/{oldContactUser}", method = RequestMethod.POST)
    public ResponseEntity<ArrayList<ContactUser>> EditContact(@RequestBody ContactUser newContactUser,
            @PathVariable("oldContactUser") ContactUser oldContactUser) {
        EditContact e = new EditContact();
        e.editContactUser(newContactUser, oldContactUser);
        return new ResponseEntity<>(server.contacts, HttpStatus.OK);
    }

    @GetMapping("/logOut")
    public void logOut() {
        //put user values in info.json hello mi senti ?
    }
}
