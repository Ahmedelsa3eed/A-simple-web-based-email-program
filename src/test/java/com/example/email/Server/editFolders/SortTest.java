package com.example.email.Server.editFolders;

import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SortTest {
    SendingEmail underTest;
    SignIn signIn;
    SingleTonServer server;
    SendingEmail sendingEmail;
    Sort sort;

    @BeforeEach
    void setUp() {
        underTest = new SendingEmail();
        signIn = new SignIn();
        server = SingleTonServer.getInstance();
        sendingEmail = new SendingEmail();
        sort = new Sort();
    }

    @Test
    void sort() {
        User user = new User("ahmed", "mahmoud", "ahmed@mail.com", "1");
        signIn.signIn(user);
        String date = LocalDateTime.of(2020,12,10,16,10).toString();
        Email email = new Email(0, date, "ahmed@mail.com", "emary@mail.com",
                "sub1", "body1");

        String date2 = LocalDateTime.of(2021,12,10,16,15).toString();
        Email email2 = new Email(0, date2, "ahmed@mail.com", "emary@mail.com",
                "sub2", "bod2");

        String date3 = LocalDateTime.now().toString();
        Email email3 = new Email(0, date3, "ahmed@mail.com", "emary@mail.com",
                "sub3", "body3");
        sendingEmail.send(email);
        sendingEmail.send(email2);
        sendingEmail.send(email3);

        sort.sort("sent","date");

        assertThat(server.sent.get(1).getDate()).isEqualTo(date);
    }
}