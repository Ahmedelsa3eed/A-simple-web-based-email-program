package com.example.email.Server.emailContent;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
* immutable Design pattern
* */
public class Email {
    private String from;
    private String to;
    private String subject;
    private String body;
    //public List<MultipartFile> multipartFiles;

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }
    public String getSubject() {
        return subject;
    }
    public String getBody() {
        return body;
    }

    public boolean isEqual(Email email1 ,Email email2){
        return email1.from.equals(email2.from) &&
                email1.to.equals(email2.to) &&
                email1.body.equals(email2.body) &&
                email1.subject.equals(email2.subject);
    }

}
