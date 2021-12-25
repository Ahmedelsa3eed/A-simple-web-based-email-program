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

    public void setBody(String body) {
        this.body = body;
    }
}
