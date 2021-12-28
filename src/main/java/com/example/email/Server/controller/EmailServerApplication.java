package com.example.email.Server.controller;

import com.example.email.Server.Contact.AddContact;
import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.DraftEmail;
import com.example.email.Server.SignIn.SignIn;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.editFolders.Sort;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

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
			user2= objectMapper.readerFor(User.class).readValue(new File("data\\pet@mail.com\\info.json"));
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



		Email mail = new Email();
		Email mail2 =new Email();
		mail.setFrom("pet@mail.com");
		mail.setTo("emary@mail.com");
		mail.setSubject("jj");
		mail.setBody("ahmed");

	 	mail2.setFrom("pet@mail.com");
		mail2.setTo("emary@mail.com");
		mail2.setSubject("jj");
		mail2.setBody("elemary");

		DraftEmail draft =new DraftEmail();
		draft.addToDraft(mail);
		draft.addToDraft(mail2);

		Email editedMail=new Email();
		editedMail.setSubject("jj hello");
		editedMail.setBody("ahmed elemary");
		editedMail.setFrom("pet@mail.com");
		editedMail.setTo("emary@mail.com");

		draft.editDraft(mail,editedMail);
*/
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
	}
}