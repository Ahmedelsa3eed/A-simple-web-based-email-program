package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.model.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaBody implements Criteria{

    @Override
    public List<Email> meetCriteria(List<Email> emails, String searchBar) {
        List<Email> bodyEmails = new ArrayList<>();

        for (Email email : emails){
            if(email.getBody().contains(searchBar)){
                bodyEmails.add(email);
            }
        }
        return bodyEmails;
    }
}
