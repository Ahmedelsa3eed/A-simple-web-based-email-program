package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.emailContent.Email;

import java.util.ArrayList;
import java.util.List;

public class OrCriteria implements Criteria{

    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria){
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Email> meetCriteria(List<Email> emails, String searchBar) {
        List<Email> firstCriteriaItems = criteria.meetCriteria(emails, searchBar);
        List<Email> otherCriteriaItems = otherCriteria.meetCriteria(emails, searchBar);

        for (Email email : otherCriteriaItems){
            if(!firstCriteriaItems.contains(email)){
                firstCriteriaItems.add(email);
            }
        }
        return firstCriteriaItems;
    }
}
