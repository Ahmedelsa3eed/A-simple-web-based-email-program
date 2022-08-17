package com.example.email.Server.logs;

import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.Email;
import com.example.email.Server.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SendingEmailTest {

    @Test
    void send() {
        SendingEmail underTest = new SendingEmail();
        SignIn sign = new SignIn();
        User user = new User("ahmed", "mahmoud", "ahmed@mail.com", "1");
        sign.signIn(user);
        SingleTonServer server = SingleTonServer.getInstance();
        String date = LocalDateTime.now().toString();
        Email email = new Email("a", date, "ahmed@mail.com", "emary@mail.com",
                "sub", "body");
        System.out.println(date);
        underTest.send(email);
        assertThat(server.sent.contains(email)).isTrue();
    }
}