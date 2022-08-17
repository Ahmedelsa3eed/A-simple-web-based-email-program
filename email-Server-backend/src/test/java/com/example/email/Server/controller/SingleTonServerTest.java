package com.example.email.Server.controller;

import com.example.email.Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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