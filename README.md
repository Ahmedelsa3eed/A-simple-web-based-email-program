# Mail Server App
![Code Analysis](https://github.com/Ahmedelsa3eed/A-simple-web-based-email-program/workflows/build/badge.svg)

## Accosiated with Alexandria University

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

## Sample Runs

![1](https://user-images.githubusercontent.com/73740339/164770403-7bb517db-3979-485e-9e7a-0015843cc774.png)

![22](https://user-images.githubusercontent.com/73740339/164770931-5cfc927e-db70-4757-9ae6-b958466735d6.png)

![3](https://user-images.githubusercontent.com/73740339/164771532-dd6071c0-faf9-475d-8570-3ffd23571a12.png)
