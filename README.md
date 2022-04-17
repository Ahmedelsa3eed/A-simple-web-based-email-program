# Accosiated with Alexandria University

## Objective
The aim of the project is to implement the basic functionalities of a mail server, including manipulation of mails, attachments and contacts.

## UML Diagram
![fasad-Page-1 drawio](https://user-images.githubusercontent.com/73740339/163716486-1710a2bf-ebaa-48ce-9acc-1e97e02c7eae.png)

## Used Design Patterns

### Singleton DP:
- We have applied the singleton design pattern on the “SingletonServer” class, it is unique for the user that is login in the server at that time. It has arrays of emails for (inbox, sent, trash, draft, contacts). We save to the database “to our hdd” only when refresh or logout happen, we reset the server after each logout.

### Criteria DP:
- We used the criteria -filter- design pattern to filter the emails according to subject, sender, body, date, or receiver and direct them to a specific mail folder.

### Façade DP:
- We applied the façade design pattern on the “ServerController” class, it
simplifies a-ccess to a related set of objects by providing one object that all objects outside the set use to communicate with the set.

## User Guide

### Sign Up:
- Click on create new account to sign Up in our email server web app.
- Choose any first name and second name.
- Your email address should end with @mail.com, @gmail.com, or @yahoo.com. It should be unique “not like another person's email address”.
- The password should be more than 8 characters.
- Click on submit and welcome to our web app. Enjoy it :)

### Sign In:
- Enter the correct mail address and the password then you will be able to use the app.
- The email address you enter should exist in our server so that you can sign in to it.

### Log out:
- It was in all the pages except the page of sign in and sign up.
- When you click on it you will return to the sign in page again.

### Refresh Button:
- It was in all the pages except the page of sign in and sign up.
- When you click on the refresh button you will update all the data you have.

### Inbox:
- Here you can see all the mail that you received from other persons.
- You can sort by priority, subject, body, date.
- You can search by priority, subject, body, date.
- When you click on the details button you will see the body of the email.
- You can also see and download the attachments if there are any.
- When you click on the delete button you will remove the email from the inbox you will find it in the trash page.

### Sent Folder:
- In this page you can see all the emails that you sent.
- You can sort by priority, subject, body, date.
- You can search by priority, subject, body, date.
- When you click on the details button you will see the body of the email.
- You can also see and download the attachments if there are any.

### Contact Page:
- In this page you can see all the contacts that you save.
- When you click on the new contact button you can create a new name in your contact and if you want to add his emails click on edit button.
- When you click on the details button you will see the name of the contact and his emails if he has.
- When you click on the Edit button you will see the name of the contact and six places to the emails you can fill or edit any one of them and after finishing click on the Save button.
- When you click on the delete button you will remove the contact and his emails from the contacts.

### Send Mail:
- Here you are going to send an email to another person.
- Required to that is the address mail of the person that you want to send that email to, subject, body.
- If you want to add an attachment or more you should click on choose file and select the file that you want to send.
- Add to the draft button it will add the email to the draft page and you can send it later or edit it then send.
- When you click on the Submit button you will send the email.
### Draft page:
- In this page you can see all the emails that you wrote but didn't finish and you save it to the draft.
- You can sort by priority, subject, body, date.
- You can search by priority, subject, body , date.
- When you click on the details button you will see the body of the email that you wrote.
- When you click on the delete button you will remove the email from the draft.
- When you click on the Edit button you can edit anything in the email then click on the Save button.
- When you click on the Submit button you will send the email and remove it from the draft.
### Trash page:
- In this page you can see all the emails that you delete from the inbox.
- You can sort by priority, subject, body, date.
- You can search by priority, subject, body, date.
- When you click on the details button you will see the body of the email that you delete.
- When you click on the delete button you will remove the email from the trash.
- When you click on the Restore button you can restore the email to the inbox and delete it from the trash.
