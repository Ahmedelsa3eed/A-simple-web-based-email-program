package com.example.email.Server.network;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*SingleTon DP*/
public class SingleTonServer {
    private static SingleTonServer server;
    private User user;
    public ArrayList<Email> sent;
    public ArrayList<Email> inbox;
    public ArrayList<Email> trash;
    public ArrayList<Email> draft;
    public List<MultipartFile> multipartFiles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private SingleTonServer(){
        sent = new ArrayList<>();
        inbox = new ArrayList<>();
        trash = new ArrayList<>();
        draft = new ArrayList<>();
    }

    public static SingleTonServer getInstance(){
        if (server == null){
            server = new SingleTonServer();
        }
        return server;
    }

    public void loadUser(User user){
        setUser(user);
        try{
            File data = new File("data\\"+user.getEmail());
            String[] folders = data.list();
            for (String folder: folders){
                if (!folder.equals("info.json") && !folder.equals("contacts")) {
                    String path = "data\\" + user.getEmail() + "\\" + folder;
                    File f = new File(path);
                    String[] files = f.list();
                    for (String file : files) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        File jsonFile = new File(path + "\\" + file);
                        String[] jsonfiles = jsonFile.list();
                        Email mail = objectMapper.readerFor(Email.class).readValue(new File(jsonFile.getPath() + "\\" + jsonfiles[0]));
                        arrayFactory(folder, mail);

                    }
                }
            }
        }
        catch (IOException e){
            System.out.println("Error while loading the json file");
        }
        catch (NullPointerException nullPointerException){
            System.out.println("wrong path name while loading");
        }

    }

    public void arrayFactory(String folder, Email email){
        switch (folder){
            case "inbox":
                inbox.add(email);
                break;
            case "sent":
                sent.add(email);
                break;
            case "trash":
                trash.add(email);
                break;
            case "draft":
                draft.add(email);
                break;
        }
    }

    public void addEmail(Email email){
        sent.add(email);
    }


}
