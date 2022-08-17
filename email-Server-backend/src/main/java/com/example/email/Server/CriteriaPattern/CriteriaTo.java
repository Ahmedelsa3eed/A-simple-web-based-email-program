package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.emailContent.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaTo implements Criteria{
    @Override
    public List<Email> meetCriteria(List<Email> emails, String searchBar) {
        List<Email> toEmails = new ArrayList<>();

        for (Email email : emails){
            if(email.getTo().contains(searchBar)){
                toEmails.add(email);
            }
        }
        return toEmails;
    }
}
