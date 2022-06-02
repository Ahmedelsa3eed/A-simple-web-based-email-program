package com.example.email.Server.logs;

import com.example.email.Server.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class SignInTest {
    private SignIn underTest;

    @BeforeEach
    void setUp() {
        underTest = new SignIn();
    }

    @Test
    void signInWithWrongEmail() {
        User user = new User("ahmed",
                "mahmoud",
                "am@mail.com",
                "0000");
        ResponseEntity<User> expected = underTest.signIn(user);
        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void signInWithWrongPassword() {
        User user = new User("ahmed",
                "mahmoud",
                "ahmed@mail.com",
                "88888");
        ResponseEntity<User> expected = underTest.signIn(user);
        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void canSignIn() {
        User user = new User("ahmed",
                "mahmoud",
                "saeed@mail.com",
                "1");
        ResponseEntity<User> expected = underTest.signIn(user);
        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}