package com.example.email.Server.editFolders;

import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SortTest {

    @Mock private SignIn signIn;
    private AutoCloseable autoCloseable;
    @Mock private SendingEmail sendingEmail;
    SingleTonServer server = SingleTonServer.getInstance();
    private Sort underTest;
    private String date3;
    private String date2;
    private String date;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        signIn = new SignIn();
        server.resetServer();
        sendingEmail = new SendingEmail();
        underTest = new Sort();
        User user = new User("ahmed", "mahmoud", "ahmed@mail.com", "1");
        signIn.signIn(user);
        String date = LocalDateTime.of(2020,12,10,16,10).toString();
        Email email = new Email(0, date, "ahmed@mail.com", "emary@mail.com",
                "sub1", "body1");

        String date2 = LocalDateTime.of(2021,12,10,16,15).toString();
        Email email2 = new Email(0, date2, "ahmed@mail.com", "emary@mail.com",
                "sub2", "bod2");

        date3 = LocalDateTime.now().toString();
        Email email3 = new Email(0, date3, "ahmed@mail.com", "emary@mail.com",
                "sub3", "body3");
        sendingEmail.send(email);
        sendingEmail.send(email2);
        sendingEmail.send(email3);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    @Test
    void sortByDate() {
        sort.sort("sent","date");
        assertThat(server.sent.get(0).getDate()).isEqualTo(date3);
    }
    @Test
    void sortByBody() {
        sort.sort("sent","date");
        assertThat(server.sent.get(0).getDate()).isEqualTo(date3);
    }



}