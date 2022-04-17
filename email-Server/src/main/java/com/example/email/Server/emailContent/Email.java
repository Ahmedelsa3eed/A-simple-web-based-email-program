package com.example.email.Server.emailContent;

import java.io.File;
import java.util.ArrayList;

/*
 * immutable Design pattern
 * */
public class Email {
    private String priority;
    private String date;
    private String from;
    private String to;
    private String subject;
    private String body;
    private String attachmentPath;
    private String[] attachmentsName;
    
    public Email() {
        this.priority = "b";
        this.attachmentPath = "noAttachment";
    }
    public Email(String priority, String date, String from, String to, String subject, String body) {
        this.priority = priority;
        this.date = date;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
    public Email(String priority, String date, String from, String to, String subject, String body, String attachmentPath) {
        this.priority = priority;
        this.date = date;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachmentPath = attachmentPath;
    }
    
    public String[] getAttachmentsName() {
        return attachmentsName;
    }
    public void setAttachmentsName() {
        File file = new File("data/attachments/"+attachmentPath);
        attachmentsName = file.list();
    }
    public String getAttachmentPath() {
        return attachmentPath;
    }
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
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
    public void setFrom(String from) {
        this.from = from;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setBody(String body) {
        this.body = body;
    }
    

}
