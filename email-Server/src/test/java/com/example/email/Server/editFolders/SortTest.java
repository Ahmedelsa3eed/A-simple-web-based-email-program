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
        date = LocalDateTime.of(2020,12,10,16,10).toString();
        Email email = new Email("c", date, "ahmed@mail.com", "emary@mail.com",
                "csub1", "zbody1");

        date2 = LocalDateTime.of(2021,12,10,16,15).toString();
        Email email2 = new Email("b", date2, "ahmed@mail.com", "emary@mail.com",
                "Asub2", "bod2");

        date3 = LocalDateTime.now().toString();
        Email email3 = new Email("a", date3, "ahmed@mail.com", "emary@mail.com",
                "bsub3", "abody3");

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
        underTest.sort("sent","date");
        assertThat(server.sent.get(0).getDate()).isEqualTo(date3);
        assertThat(server.sent.get(1).getDate()).isEqualTo(date2);
        assertThat(server.sent.get(2).getDate()).isEqualTo(date);
    }
    @Test
    void sortByBody() {
        underTest.sort("sent","body");
        assertThat(server.sent.get(0).getBody()).isEqualTo("abody3");
        assertThat(server.sent.get(1).getBody()).isEqualTo("bod2");
        assertThat(server.sent.get(2).getBody()).isEqualTo("zbody1");
    }
    @Test
    void sortBySubject() {
        underTest.sort("sent","subject");
        assertThat(server.sent.get(0).getSubject()).isEqualTo("Asub2");
        assertThat(server.sent.get(1).getSubject()).isEqualTo("bsub3");
        assertThat(server.sent.get(2).getSubject()).isEqualTo("csub1");
    }
    @Test
    void sortByPriority() {
        underTest.sort("sent","priority");
        assertThat(server.sent.get(0).getPriority()).isEqualTo("a");
        assertThat(server.sent.get(1).getPriority()).isEqualTo("b");
        assertThat(server.sent.get(2).getPriority()).isEqualTo("c");
    }



}