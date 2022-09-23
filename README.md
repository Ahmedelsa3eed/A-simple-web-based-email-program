# Mail Server App
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-orange.svg)](https://sonarcloud.io/summary/new_code?id=Ahmedelsa3eed_A-simple-web-based-email-program)

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
