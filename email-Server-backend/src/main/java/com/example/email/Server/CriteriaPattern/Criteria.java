package com.example.email.Server.CriteriaPattern;

import com.example.email.Server.emailContent.Email;

import java.util.List;

public interface Criteria {
    List<Email> meetCriteria(List<Email> emails, String searchBar);
}
