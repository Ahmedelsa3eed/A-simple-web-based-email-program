package com.example.email.Server.Contact;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.ContactUser;
import com.example.email.Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EditContactTest {
    EditContact underTest;
    SingleTonServer server;

    @BeforeEach
    void setUp() {
        underTest = new EditContact();
        server = SingleTonServer.getInstance();
        server.resetServer();
        User user = new User("","","ahmed@mail.com","1");
        server.loadUser(user);
    }

    @Test
    void shouldDeleteContactUser() {
        assertThat(underTest.deleteContactUser("team2")).isTrue();
        assertThat(underTest.deleteContactUser("team2")).isFalse();
    }

    @Test
    void shouldEditContactUser() {
        ContactUser newContactUser = new ContactUser();
        newContactUser.setName("team3");
        newContactUser.addEmail("saeed22@mail.com");
        newContactUser.addEmail("saeed@mail.com");
        ContactUser oldContactUser = new ContactUser();
        oldContactUser.setName("team4");
        oldContactUser.addEmail("emary@mail.com");
        oldContactUser.addEmail("saeed@mail.com");

        assertThat(underTest.editContactUser(newContactUser, oldContactUser).contains(newContactUser)).isTrue();
    }

    @Test
    void sort() {
        underTest.sort();
        assertThat(server.contacts.get(0).getName()).isEqualTo("a");
        assertThat(server.contacts.get(2).getName()).isEqualTo("team2");
    }

    @Test
    void search() {
        assertThat(underTest.search("team", "name").size()).isEqualTo(2);
        assertThat(underTest.search("saeed", "emails").size()).isEqualTo(2);
    }
}