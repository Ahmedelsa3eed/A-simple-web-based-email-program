package com.example.email.Server.controller;

import com.example.email.Server.Contact.AddContact;
import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.Contact.EditContact;
import com.example.email.Server.draft.DraftEmail;
import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.editFolders.Search;
import com.example.email.Server.editFolders.Sort;
import com.example.email.Server.emailContent.FileResource;
import com.example.email.Server.emailContent.UpdatePriority;
import com.example.email.Server.logOut.LogOut;
import com.example.email.Server.user.User;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.signUp.Register;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;

@CrossOrigin
@RestController
/*Server controller is the facade of this system*/
public class ServerController {
    SingleTonServer server;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody User user) {
        Register register = new Register();
        return register.signUp(user);
    }

    @PostMapping("/signIn")
    @ResponseBody
    public ResponseEntity<User> signIn(@RequestBody User user){
        SignIn signIn = new SignIn();
        return signIn.signIn(user);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam("files") List<MultipartFile> multipartFiles){
        FileResource fileResource = new FileResource();
        server = SingleTonServer.getInstance();
        String id = fileResource.uploadFiles(multipartFiles);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/send")
    @ResponseBody
    public void send(@RequestBody Email email){
        SendingEmail s = new SendingEmail();
        s.send(email);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFiles(@RequestParam String filename,
                                                  String attachment) throws Exception{
        System.out.println(attachment);
        FileResource fileResource = new FileResource();
        return fileResource.downloadFiles(filename, attachment);
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Email>> getInbox(){
        server  = SingleTonServer.getInstance();
        return new ResponseEntity<>(server.inbox, HttpStatus.OK);
    }

    @RequestMapping(value = "/sent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Email>> getSent(){
        server  = SingleTonServer.getInstance();
        return new ResponseEntity<>(server.sent, HttpStatus.OK);
    }

    @RequestMapping(value = "/draft", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Email>> getDraft(){
        server  = SingleTonServer.getInstance();
        return new ResponseEntity<>(server.draft, HttpStatus.OK);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ContactUser>> getContacts(){
        server  = SingleTonServer.getInstance();
        return new ResponseEntity<>(server.contacts, HttpStatus.OK);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Email>> getTrash(){
        server  = SingleTonServer.getInstance();
        return new ResponseEntity<>(server.trash, HttpStatus.OK);
    }

    @PostMapping("/addToDraft")
    @ResponseBody
    public ResponseEntity<ArrayList<Email>>  addToDraft(@RequestBody Email email){
        DraftEmail s = new DraftEmail();
        s.addToDraft(email);
        return new ResponseEntity<>(server.draft, HttpStatus.OK);
    }

    @PostMapping("/sendDraft")
    @ResponseBody
    public void sendDraft(@RequestBody Email email){
        Delete d = new Delete();
        d.deleteEmail(email,"draft");
        send(email);
    }

    @PostMapping("/editDraft")
    @ResponseBody
    public ResponseEntity<ArrayList<Email>> editDraft(@RequestParam("twoDrafts") List<Email> twoDrafts) {
        DraftEmail draftEmail = new DraftEmail();
        draftEmail.editDraft(twoDrafts.get(0), twoDrafts.get(1));
        return new ResponseEntity<>(server.draft, HttpStatus.OK);
    }

    @PostMapping("/addContact")
    @ResponseBody
    public void addContact(@RequestBody ArrayList<ContactUser> newContact){
        server.contacts = newContact;
    }

    @PostMapping( "/deleteContact")
    public void deleteContact(@RequestBody ArrayList<ContactUser> newContact) {
        server.contacts = newContact;
    }

    @PostMapping("/editContact")
    @ResponseBody
    public void editContact(@RequestBody ArrayList<ContactUser> newContacts) {
        server.contacts = newContacts;
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<Email>> search(@RequestParam String searchBar, String searchPosition, String searchBy){
        Search s = new Search();
        return new ResponseEntity<>(s.search(searchBar, searchPosition, searchBy), HttpStatus.OK);
    }

    @GetMapping("/sort")
    public void sort(@RequestParam String sortPosition, String sortBy){
        Sort s = new Sort();
        s.sort(sortPosition, sortBy);
    }

    @PostMapping("/priority")
    @ResponseBody
    public ResponseEntity<ArrayList<Email>> priority(@RequestParam("TwoEmails") List<Email> TwoEmails) {
        UpdatePriority updatePriority = new UpdatePriority();
        updatePriority.edit(TwoEmails.get(0), TwoEmails.get(1));
        return new ResponseEntity<>(server.inbox, HttpStatus.OK);
    }

    @PostMapping( "/deleteFromInbox")
    @ResponseBody
    public void deleteEmail(@RequestBody Email oldEmail) {
        Delete d = new Delete();
        d.deleteEmail(oldEmail, "inbox");
    }

    @GetMapping("/refresh")
    public void refresh(){
        LogOut logOut = new LogOut();
        logOut.refresh();
    }


    @PostMapping( "/deleteFromDraft")
    @ResponseBody
    public void deleteFromDraft(@RequestBody ArrayList<Email> newEmails){
        server.draft = newEmails;

    }

    @PostMapping( "/deleteFromSent")
    @ResponseBody
    public void deleteFromSent(@RequestBody ArrayList<Email> newEmails){

        server.sent = newEmails;

    }


    @PostMapping( "/deleteFromTrash")
    @ResponseBody
    public void deleteFromTrash(@RequestBody Email oldEmail){
        Delete d=new Delete();
        d.removeFromTrash(oldEmail);
    }

    @GetMapping("/logOut")
    public void logOut() {
        LogOut logOut = new LogOut();
        logOut.save();
    }
}
/**
 * Now we should show the attachments names in th front end so we can add them in mail as array of
 * strings so that it will be related to that mail there will be a method that get the attachments
 * names to show them in the E-mail it's easy we will only loop on the file in the folder and add
 * attachment name to the array of string .
 * */