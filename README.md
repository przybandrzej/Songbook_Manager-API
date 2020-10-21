# Songbook Manager Service API  
***current version: 1.9.0***  
It is a server side application (RESTful API) that lets the users manage songs in a songbook, manage accounts, playlists, own small songbooks and singing events.

### The idea  
The idea for this application came to me when I joined the [**Sekcja Turystyki Kwalifikowanej**](http://stk.ue.poznan.pl/) (a group of tourists, especially mountains' enthusiasts, at my university in Poznań, Poland) and saw the PDF file where all the songs were stored. It was huge, around 900 pages. It was inconvenient to use. I decided to create a web application where it will be easier to update and explore the songbook and everyone can have their own library of songs, playlists and share it.   
***So it is a real-life problem solution.***

### Web clients  
There is one web client of this application created with **Angular** by me: [**Songbook Angular Client repo**](https://github.com/przybandrzej/songbook-angular-client).  

### Mobile client  
After this application is finished I will create an Android app that will connect to this API and allow to explore it from Android devices and provide more features like `group singing` where everyone connects to one person and they can display the same page on all phones.

## Features  
* REST API
* Browse a huge database of guitar songs' lyrics and guitar tabs
* Create your own library of your favorite songs
* Create a playlist for every occasion
* Create a printable songbook out of any playlist
* Add new songs even from a file (json file)
* see all upcoming and past singing events and come to one! **<Not implemented yet>**

## Getting Started  
 * The API on AWS EC2: https://stk-uep.pl/api-docs and [https://stk-uep.pl/api/*](https://stk-uep.pl/api/*)
 
### Build    
* **dev**:  
Build command `mvn clean package` or `mvn clean package -Pdev`  
* **prod**:  
Build command `mvn clean package -Pprod` (you need to provide your own `application-prod.yml` file)

### Run  
To build the app you need the following services:
  * RabbitMQ
  * PostgreSQL database
  * mailer service (preferably the [mailer-service](https://github.com/przybandrzej/mailer-service) from my repo)  

* **dev**  
Run command `mvn spring-boot:run` or `mvn spring-boot:run -Drun.profiles=dev`.
* **prod**  
Run command `mvn spring-boot:run -Drun.profiles=prod`.

### Init database  
There is **Flyway** for database migration. The DB will be initialized on application's first startup. The only thing to do is to create postgres database and give credentials in `application-dev.yml` and `application-prod.yml`.  
The init data consists of user roles (user, moderator, admin and superuser) and **Superuser** credentials. The roles' names and Superuser credentials can be changed in `application.yml`. Please change the Superuser's password after first startup which is set to `superuser`.

### Wiki  
Feel free to read [Wiki](https://github.com/przybandrzej/Songbook_Manager-API/wiki) articles. You can find more useful information there. 

## Built With
* [Java 13]()
  * [Spring Boot]()
  * [Spring Data JPA]()
  * [Spring Security]()
  * [Lombok]()
  * [MapStruct]()
  * [PDF Box]()
  * [Junit 5]()
  * [Mockito]()
  * [Thymeleaf]()
  * [Spring Boot AMPQ]()
* [Flyway]()
* [PostgreSQL]()
* [RabbitMQ]()
* [Heroku]()
* [Swagger]()

## Author  
* **Andrzej Przybysz** - [BrieflyClear](https://github.com/przybandrzej)
