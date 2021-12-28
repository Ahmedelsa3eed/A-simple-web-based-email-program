package com.example.email.Server.controller;

import com.example.email.Server.Contact.AddContact;
import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.Contact.EditContact;
import com.example.email.Server.draft.DraftEmail;
import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.editFolders.Search;
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
@Controller
/*Server controller is the facade of this system*/
public class ServerController {
    SingleTonServer server;

    @PostMapping("/register")
    @ResponseBody
    public boolean signUp(@RequestBody User user) {
        Register register = new Register();
        return register.signUp(user);
    }

    @PostMapping("/signIn")
    @ResponseBody
    public boolean signIn(@RequestBody User user){
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

    @GetMapping("/download/{filename},{attachment}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable String filename,
                                                  @PathVariable String attachment) throws Exception{
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
    public ResponseEntity<ArrayList<ContactUser>> addContact(@RequestBody ContactUser contactUser){
        AddContact c = new AddContact();
        return new ResponseEntity<>(c.addContact(contactUser), HttpStatus.OK);
    }

    @DeleteMapping( "/deleteContact/{oldContactName}")
    public ResponseEntity<ArrayList<ContactUser>> deleteContact(@PathVariable String oldContactName) {
        EditContact e = new EditContact();
        e.deleteContactUser(oldContactName);
        return new ResponseEntity<>(server.contacts, HttpStatus.OK);
    }

    @PostMapping("/editContact")
    @ResponseBody
    public ResponseEntity<ArrayList<ContactUser>> editContact(@RequestParam("contacts") List<ContactUser> contacts) {
        EditContact e = new EditContact();
        e.editContactUser(contacts.get(0), contacts.get(1));
        return new ResponseEntity<>(server.contacts, HttpStatus.OK);
    }

    @GetMapping("/logOut")
    public void logOut() {
        LogOut logOut = new LogOut();
        logOut.save();
    }
}
