package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.model.Email;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSubject implements Criteria{
    @Override
    public List<Email> meetCriteria(List<Email> emails, String searchBar) {
        List<Email> subjectEmails = new ArrayList<>();

        for (Email email : emails){
            if(email.getSubject().contains(searchBar)){
                subjectEmails.add(email);
            }
        }
        return subjectEmails;
    }
}
