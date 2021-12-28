package com.example.email.Server.emailContent;

import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SendingEmailTest {

//    @Mock
    @Test
    void send() {
        SendingEmail underTest = new SendingEmail();
        SignIn sign = new SignIn();
        User user = new User("ahmed", "mahmoud", "ahmed@mail.com", "1");
        sign.signIn(user);
        SingleTonServer server = SingleTonServer.getInstance();
        //given
        String date = LocalDateTime.now().toString();
        Email email = new Email("a", date, "ahmed@mail.com", "emary@mail.com",
                "sub", "body");
        System.out.println(date);
        underTest.send(email);
        assertThat(server.sent.contains(email)).isTrue();
    }
}