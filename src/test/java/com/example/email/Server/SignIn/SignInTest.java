package com.example.email.Server.SignIn;

import com.example.email.Server.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        boolean expected = underTest.signIn(user);
        assertThat(expected).isFalse();
    }

    @Test
    void signInWithWrongPassword() {
        User user = new User("ahmed",
                "mahmoud",
                "ahmed@mail.com",
                "88888");
        boolean expected = underTest.signIn(user);
        assertThat(expected).isFalse();
    }

    @Test
    void canSignIn() {
        User user = new User("ahmed",
                "mahmoud",
                "saeed@mail.com",
                "1");
        boolean expected = underTest.signIn(user);
        assertThat(expected).isTrue();
    }

}