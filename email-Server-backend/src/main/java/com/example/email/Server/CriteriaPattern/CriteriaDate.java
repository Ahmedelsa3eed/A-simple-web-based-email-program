package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.model.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaDate implements Criteria{
    @Override
    public List<Email> meetCriteria(List<Email> emails, String searchBar) {
        List<Email> dateEmails = new ArrayList<>();

        for (Email email : emails){
            if(email.getDate().contains(searchBar)){
                dateEmails.add(email);
            }
        }
        return dateEmails;
    }
}
