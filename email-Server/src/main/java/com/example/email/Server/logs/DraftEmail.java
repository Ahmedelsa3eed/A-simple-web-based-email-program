package com.example.email.Server.logs;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.model.Email;

public class DraftEmail {
    SingleTonServer server = SingleTonServer.getInstance();

    public void addToDraft(Email email){
        server.draft.add(email);
    }

    public void editDraft(Email oldEmail, Email newEmail){
        Delete delete=new Delete();
        delete.deleteEmail(oldEmail,"draft");
        addToDraft(newEmail);
        }
    }



