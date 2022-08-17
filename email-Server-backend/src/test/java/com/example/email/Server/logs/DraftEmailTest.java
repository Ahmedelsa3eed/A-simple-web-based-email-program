package com.example.email.Server.logs;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.model.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DraftEmailTest {
    DraftEmail underTest;
    SingleTonServer server;
    Email email;
    Email editedMail;

    @BeforeEach
    void setUp() {
        underTest = new DraftEmail();
        server = SingleTonServer.getInstance();
        server.resetServer();

        email = new Email("a", "2021","ahmed@mail.com", "emary@mail.com",
                "sub2",
				"bod2");

        editedMail =new Email("b","2020","ahmed@mail.com","emary@mail.com",
                "eited sub","eited boy");
        underTest.addToDraft(email);

    }
    @Test
    void editDraft() {
        underTest.editDraft(email,editedMail);
        assertThat(server.draft.get(0).getSubject()).isEqualTo("eited sub");
    }
}