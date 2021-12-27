package com.example.email.Server;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.emailContent.Email;

public class DraftEmail {

    /**
     * We should save the mail not completed in the draft
     * The user can edit the draft emails and remove them
     * so the algorithm we be like sending but we will not sent to the other person
     * we will save it in draft
     * **/

    SingleTonServer server=SingleTonServer.getInstance();

    public void addToDraft(Email email){
        server.draft.add(email);

    }

    //we want to edit the mail can edit to ,from ,subject,b
    //We assume that we delete the old draft an write an other draft

    public void editDraft(Email oldEmail, Email newEmail){

        Delete delete=new Delete();
        delete.deleteEmail(oldEmail,"draft");
        addToDraft(newEmail);

        //here we assume that we deleted the old raft mail and write the edited draft

        //server.draft.add(email);

        }

    }
    //In class email we have handeled how to delete from draft


