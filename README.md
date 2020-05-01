# Songbook Manager

***current version: 0.9.5***

It is a server side application (REST API) that lets the users manage songs in a songbook, manage accounts, playlists, own small songbooks and singing events.

### The idea

The idea for this application came to me when I joined the [**Sekcja Turystyki Kwalifikowanej**](http://stk.ue.poznan.pl/) (a group of tourists, especially mountains' enthusiasts, at my university in Poznań, Poland) and saw the PDF file where all the songs were stored. It was huge, around 900 pages. It was inconvenient to use. I decided to create a web application where it will be easier to update and explore the songbook and everyone can have their own library of songs, playlists and share it. 

***So it is a real-life problem solution.***

### Web clients

There will be two web clients of this application in the near future. One created with **Angular** by me. And one created by my friend (a Frontend dev) with **React**.


### Mobile client

After this application I will create an Android app that will be connected to this server and allow to explore it from Android devices and provide more features like `group singing` where everyone connects to one person and they can display the same page on all phones.

## Features
* REST API
* Browse a huge database of guitar songs' lyrics and guitar tabs
* Add new songs even from a file
* Create your own library of your favorite songs
* Create a playlist for every occassion
* Create a printable songbook out of any playlist
* see all upcoming and past singing events and come to one!

## Getting Started
 * The API on Heroku: https://stk-songbook.herokuapp.com/

### How to use the API

Full documentation is available on the server (the main page redirects directly to the documentation).


## Built With
* [Java 13]()
  * [Spring Boot]()
  * [Spring Data JPA]()
  * [Spring Security]()
  * [Lombok]()
  * [MapStruct]()
* [PostgreSQL]()
* [Heroku]()
* [Swagger]()

## Author

* **Andrzej Przybysz** - [BrieflyClear](https://github.com/BirieflyClear)
