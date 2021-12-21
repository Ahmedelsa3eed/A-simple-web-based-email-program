package com.example.email.Server;

import com.example.email.Server.emailContent.Email;
import com.example.email.Server.emailContent.SendingEmail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class EmailServerApplication {

	public static void main(String[] args) {
			User user= new User();
			User user2=new User();
			User user3=new User();

		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			user = objectMapper.readerFor(User.class).readValue(new File("data\\am@mail\\info.json"));
			user2= objectMapper.readerFor(User.class).readValue(new File("data\\pet@m\\info.json"));

		} catch( IOException e) {
			e.printStackTrace();
		}

			Email mail=new Email();
			mail.setBody("nnnnnnnnnnnnn");
			mail.setSubject("greating");
			mail.setUser(user);
			mail.setTo(user2);
		SendingEmail s=new SendingEmail();
		s.send(mail);
		s.send(mail);


		//SpringApplication.run(EmailServerApplication.class, args);
	}

}
