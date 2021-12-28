package com.example.email.Server.controller;

import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SingleTonServerTest {
    SingleTonServer underTest;

    @BeforeEach
    void setUp() {
        underTest = SingleTonServer.getInstance();
    }

    @Test
    void serverShouldLoadContacts() {
        User user = new User("","", "ahmed@mail.com","1");
        underTest.loadUser(user);
        assertThat(underTest.contacts.get(0).getName()).isEqualTo("My team");
    }
}