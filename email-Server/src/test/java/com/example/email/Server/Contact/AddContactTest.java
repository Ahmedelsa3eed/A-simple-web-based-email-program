package com.example.email.Server.Contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddContactTest {
    AddContact underTest;

    @BeforeEach
    void setUp() {
        underTest = new AddContact();
    }

    @Test
    void shouldAddContact() {
        //given
        ArrayList<String> emails = new ArrayList<>();
        emails.add("ahmed@mail.com");
        emails.add("emary@mail.com");
        ContactUser contactUser = new ContactUser("gang 4", emails);
        //when
        boolean expected =  underTest.addContact(contactUser).contains(contactUser);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void shouldNotAddContact() {
        ArrayList<String> emails = new ArrayList<>();
        emails.add("a@mail.com");
        emails.add("e@mail.com");
        ContactUser contactUser = new ContactUser("gang 4", emails);

        assertThat(underTest.addContact(contactUser).contains(contactUser)).isFalse();
    }
}