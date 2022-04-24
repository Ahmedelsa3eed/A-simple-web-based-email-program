package com.example.email.Server.controller;

//import com.example.email.Server.folders.FileListFilter;
import com.example.email.Server.Contact.AddContact;
import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.draft.DraftEmail;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class EmailServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailServerApplication.class, args);
/*

        		User user= new User();
				User user2=new User();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			user = objectMapper.readerFor(User.class).readValue(new File("data\\ahmed@mail.com\\info.json"));
			//user2= objectMapper.readerFor(User.class).readValue(new File("data\\pet@mail.com\\info.json"));
		} catch( IOException e) {
			e.printStackTrace();
		}

		ServerController c = new ServerController();
		c.signIn(user);
		ContactUser contactUser = new ContactUser();
		contactUser.setName("My team");
		contactUser.addEmail("emary@mail.com");
		contactUser.addEmail("saeed@mail.com");

		AddContact a = new AddContact();
		a.addContact(contactUser);
		c.logOut();


		//expect in draft array that mail deleted and add edited mail

//		SendingEmail underTest;
//		SignIn signIn;
//		SingleTonServer server;
//		SendingEmail sendingEmail;
//		Sort sort;
//
//
//		underTest = new SendingEmail();
//		signIn = new SignIn();
//		server = SingleTonServer.getInstance();
//		sendingEmail = new SendingEmail();
//		sort = new Sort();
//
//
//		User user = new User("ahmed", "mahmoud", "ahmed@mail.com", "1");
//		signIn.signIn(user);
//		String date = LocalDateTime.of(2020, 12, 10, 16, 10).toString();
//		Email email = new Email("b", date, "ahmed@mail.com", "emary@mail.com",
//				"sub1", "body1");
//
//		String date2 = LocalDateTime.of(2021, 12, 10, 16, 15).toString();
//		Email email2 = new Email("a", date2, "ahmed@mail.com", "emary@mail.com",
//				"sub2", "bod2");
//
//		String date3 = LocalDateTime.now().toString();
//		Email email3 = new Email("c", date3, "ahmed@mail.com", "emary@mail.com",
//				"sub3", "body3");
//		sendingEmail.send(email);
//		sendingEmail.send(email2);
//		sendingEmail.send(email3);
//
//		sort.sort("sent", "date");

//
/*
		FileListFilter fileListFilter = new FileListFilter();
		fileListFilter.recursiveDelete(new File("data\\attachments\\5f8030cd-68f8-472e-b307-4313e0fec4a1"));
*/
	}
}