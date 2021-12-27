package com.example.email.Server.controller;

import com.example.email.Server.Contact.AddContact;
import com.example.email.Server.Contact.ContactUser;
import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.emailContent.Email;
import com.example.email.Server.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.File;
import java.io.IOException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class EmailServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailServerApplication.class, args);

//		User user= new User();
//		//User user2=new User();
//
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			user = objectMapper.readerFor(User.class).readValue(new File("data\\ahmed@mail.com\\info.json"));
//			//user2= objectMapper.readerFor(User.class).readValue(new File("data\\pet@mail.com\\info.json"));
//		} catch( IOException e) {
//			e.printStackTrace();
//		}
//
//		ServerController c = new ServerController();
//		c.signIn(user);
//		ContactUser contactUser = new ContactUser();
//		contactUser.setName("My team");
//		contactUser.addEmail("emary@mail.com");
//		contactUser.addEmail("saeed@mail.com");
//		AddContact a = new AddContact();
//		a.addContact(contactUser);



//		Email mail = new Email();
//		Email mail2 =new Email();
//		mail.setFrom("pet@mail.com");
//		mail.setTo("emary@mail.com");
//		mail.setSubject("jj");
//		mail.setBody("ahmed");
//
//		mail2.setFrom("pet@mail.com");
//		mail2.setTo("emary@mail.com");
//		mail2.setSubject("jj");
//		mail2.setBody("elemary");

//		//s.search("am@mail","inbox","from");
//		c.signIn(user2);
//		Delete d=new Delete();
//		d.deleteEmail(mail,"sent");
//		l.save();

	}

}
