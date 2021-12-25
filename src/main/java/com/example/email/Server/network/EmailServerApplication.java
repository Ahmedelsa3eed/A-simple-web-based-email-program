package com.example.email.Server.network;

import com.example.email.Server.editFolders.Delete;
import com.example.email.Server.editFolders.Search;
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
//		User user2=new User();
//
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			user = objectMapper.readerFor(User.class).readValue(new File("data\\am@mail\\info.json"));
//			user2= objectMapper.readerFor(User.class).readValue(new File("data\\pet@m\\info.json"));
//
//		} catch( IOException e) {
//			e.printStackTrace();
//		}
//
//		ServerController c = new ServerController();
//		c.signIn(user2);
//		Email mail = new Email();
//		mail.setFrom("am@mail");
//		mail.setTo("pet@m");
// 		mail.setSubject("jj");
//		mail.setBody("alllloooooooooooo");
//		//Search s=new Search();
//		//s.search("am@mail","inbox","from");
//		Delete d =new Delete();
//		c.send(mail);
//		d.deleteEmail(mail,"ee");
//		d.deleteEmail(mail,"ww");



	}

}
