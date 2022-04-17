package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.emailContent.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaFrom implements Criteria{
    @Override
    public List<Email> meetCriteria(List<Email> emails, String searchBar) {
        List<Email> FromEmails = new ArrayList<>();

        for (Email email : emails){
            if(email.getFrom().contains(searchBar)){
                FromEmails.add(email);
            }
        }
        return FromEmails;
    }
}
