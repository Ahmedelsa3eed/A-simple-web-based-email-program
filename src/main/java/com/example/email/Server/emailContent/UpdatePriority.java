package com.example.email.Server.emailContent;

import com.example.email.Server.controller.SingleTonServer;

public class UpdatePriority {
    SingleTonServer server = SingleTonServer.getInstance();

    public void edit(Email newEmail, Email oldEmail){
        for(int i=0; i<server.inbox.size(); i++){
            if (oldEmail.getDate().equals(newEmail.getDate())){
                server.inbox.set(i, newEmail);
            }
        }
    }
    
}
