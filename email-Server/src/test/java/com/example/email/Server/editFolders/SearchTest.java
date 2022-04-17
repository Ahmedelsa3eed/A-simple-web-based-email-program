package com.example.email.Server.editFolders;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    Search underTest;
    SingleTonServer server;

    @BeforeEach
    void setUp() {
        underTest = new Search();
        server = SingleTonServer.getInstance();
        server.resetServer();
        User user = new User("","","emary@mail.com","1");
        server.loadUser(user);
    }

    @Test
    void searchByDate() {
        assertThat(underTest.search("2020-12","inbox","date").size()).isEqualTo(2);
    }

    @Test
    void searchByBody() {
        for(Email email: underTest.search("so3da","inbox","body")){
            assertThat(email.getBody().contains("so3da")).isFalse();
        }
    }

    @Test
    void searchBySubject() {
        for(Email email: underTest.search("sub","inbox","subject")){
            assertThat(email.getSubject().contains("sub")).isTrue();
        }
    }

    @Test
    @Disabled
    void searchByPriority() {
        for(Email email: underTest.search("a","inbox","priority")){
            assertThat(email.getPriority().contains("a")).isTrue();
        }
    }

}