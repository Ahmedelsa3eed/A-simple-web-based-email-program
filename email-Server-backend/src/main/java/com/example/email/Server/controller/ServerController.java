package com.example.email.Server.controller;

import com.example.email.Server.DataBaseServices.*;
import com.example.email.Server.logs.LogOut;
import com.example.email.Server.logs.Register;
import com.example.email.Server.logs.SignIn;
import com.example.email.Server.model.ContactUser;
import com.example.email.Server.model.User;
import com.example.email.Server.logs.DraftEmail;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.editFolders.Search;
import com.example.email.Server.editFolders.Sort;
import com.example.email.Server.emailContent.FileResource;
import com.example.email.Server.model.Email;
import com.mongodb.client.model.Filters;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@Controller
public class ServerController {
    SingleTonServer server=SingleTonServer.getInstance();

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<User> signUp(@RequestBody User user) {

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
    public ResponseEntity<String> upload(@RequestParam("files") List<MultipartFile> multipartFiles, @RequestParam("userID") String userID) {
        //upload data to the server
        System.out.println("uploading files" + multipartFiles.size());
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                System.out.println("uploading file................" + multipartFile.getOriginalFilename());
                UploadFileToDB.uploadFile(multipartFile.getBytes(),userID,multipartFile.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileResource fileResource = new FileResource();
        server = SingleTonServer.getInstance();
        String id = fileResource.uploadFiles(multipartFiles);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void send(@RequestBody Email email,@RequestParam String userID){
        System.out.println("sending email");
        System.out.println("userID: " + userID);
        ArrayList<MultipartFile> multipartFiles = (ArrayList<MultipartFile>) email.getAttachments();;
        System.out.println("uploading files" + multipartFiles.size());
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                System.out.println("uploading file................" + multipartFile.getOriginalFilename());
                UploadFileToDB.uploadFile(multipartFile.getBytes(),userID,multipartFile.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        EmailsServices.sendEmail(email);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFiles(@RequestParam String filename,
                                                  String attachment) throws Exception{
        System.out.println(attachment);
        FileResource fileResource = new FileResource();
        return fileResource.downloadFiles(filename, attachment);
    }

    @GetMapping("/inbox")
    public ResponseEntity<Email[]> getInbox(@RequestParam String userID){
        System.out.println("inbox of "+ userID);
        return new ResponseEntity<>(EmailsServices.getRequestedEmails(userID, "Inbox"), HttpStatus.OK);
    }

    @GetMapping("/sent")
    public ResponseEntity<Email[]> getSent(@RequestParam String userID){
        //TODO : make the front end send the current user email
        return new ResponseEntity<>(EmailsServices.getRequestedEmails(userID, "Sent"), HttpStatus.OK);
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

    @GetMapping("/trash")
    public ResponseEntity<Email[]> getTrash(@RequestParam String userID){
        return new ResponseEntity<>(EmailsServices.getRequestedEmails(userID, "Trash"), HttpStatus.OK);
    }

    @PostMapping("/addToDraft")
    @ResponseBody
    public ResponseEntity<ArrayList<Email>>  addToDraft(@RequestBody Email email){
        DraftEmail s = new DraftEmail();
        s.addToDraft(email);
        return new ResponseEntity<>(server.draft, HttpStatus.OK);
    }

//    @PostMapping("/sendDraft")
//    @ResponseBody
//    public void sendDraft(@RequestBody Email email){
//        Delete d = new Delete();
//        d.deleteEmail(email,"draft");
//        send(email);
//    }

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
    public ResponseEntity<Email[]> search(@RequestParam String userID, String searchString, String searchPosition){
        System.out.println("searching for "+ searchString + " in " + searchPosition+ "user "+ userID);
        return new ResponseEntity<>(SearchEmails.searchEmailsInDB(userID,searchString,searchPosition), HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<Email[]> sort(@RequestParam String userID, String sortBy, String position){
        System.out.println("sorting for "+ sortBy + " in " + position+ "user "+ userID);
         return new ResponseEntity<>(SortService.sortEmailsBy(userID,sortBy,position), HttpStatus.OK);
    }

    @PostMapping("/priority")
    @ResponseBody
    public ResponseEntity<ArrayList<Email>> priority(@RequestBody ArrayList<Email> emails) {
        server.inbox=emails;
        return new ResponseEntity<>(server.inbox, HttpStatus.OK);
    }

    @PostMapping( "/deleteFromInbox")
    @ResponseBody
    public ResponseEntity<Boolean> deleteEmail(@RequestBody Email email) {
        System.out.println("delete "+ email.get_id());
        return new ResponseEntity<>(EmailsServices.removeMailFromInbox(email), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public void refresh(){
        LogOut logOut = new LogOut();
        logOut.refresh();
    }

    @DeleteMapping( "/delete")
    public ResponseEntity<Boolean> deleteEmailFromDB(@RequestParam(value="userID")String userID,@RequestParam(value="emailID") String emailID, @RequestParam(value="position")String position) {
        System.out.println("We should delete this email from "+position);
        return new ResponseEntity<>(EmailsServices.removeMailFromDB(userID,emailID, position), HttpStatus.OK);

    }

    @DeleteMapping( "/markAsSeen")
    public  ResponseEntity<Boolean> markAsSeen(@RequestParam(value="userID") String userID,@RequestParam(value="emailID") String emailID ) {
        System.out.println("mark as seen "+ emailID + " user email is " + userID);
        return new ResponseEntity<>(EmailsServices.markAsSeen(userID,emailID), HttpStatus.OK);
    }

    @GetMapping("/logOut")
    public void logOut() {
        LogOut logOut = new LogOut();
        logOut.save();
    }
}
