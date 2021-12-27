package com.example.email.Server.signUp;

import com.example.email.Server.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RegisterTest {
    private Register underTest;

    @BeforeEach
    void setUp() {
        underTest = new Register();
    }

    @Test
    void canNotSignUp() {
        //given
        User user = new User("ahmed",
                "mahmoud",
                "ahmed@mail.com",
                "0000");

        //when
        boolean expected = underTest.signUp(user);
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void canSignUp(){
        User user = new User("ahmed",
                "mahmoud",
                "saeed22@mail.com",
                "0000");
        boolean expected = underTest.signUp(user);
        assertThat(expected).isTrue();
    }

}