# Songbook_Service
A Spring web application connected to a PostgreSQL database and deployed to Heroku.

It is an app that lets the users manage songs in a songbook (add, remove, update and read, create playlists), manage accounts and singing events. It has REST API that other applications can use.

For example my Android app named "Songbook" is connected to the API. It will display the songs on mobile devices and implement even more fetures that this web app (eg. connect with other Android devices near you and display the same song).

## Features
* Browse a huge database of guitar songs' lyrics and guitar tabs
* Create your own library of your favorite songs
* Create a playlist for each occassion
* Create a printable song book out of any playlist
* Use the API to use the database for your own purposes
* see all upcoming and past singing events and come to one!

## Getting Started
 * The deployed Heroku app: https://stk-songbook.herokuapp.com/ (currently only the API works)
 * The Android app's repo: https://github.com/BrieflyClear/Songbook-Android

### How to use the API
Refer to the following endpoints:
* Authors:
   * all : /api/authors/get
   * one by ID: /api/authors/get/id/{id}
   * all matching the name (usually one): /api/authors/get/name/{name}
 * Songs:
   * all : /api/songs/get
   * one by ID: /api/songs/get/id/{id}
   * all matching the title: /api/songs/get/title/{title}
   * all matching the author: /api/songs/get/author/{authorId}
   * all matching the category: /api/songs/get/category/{categoryId}
 * Tags:
   * all : /api/tags/get
   * one by ID: /api/tags/get/id/{id}
   * all matching the name (usually one): /api/tags/get/name/{name}
 * Categories:
   * all : /api/categories/get
   * one by ID: /api/categories/get/id/{id}
   * all matching the name (usually one): /api/categories/get/name/{name}
 * Playlists (only the ones with 'public' marker):
   * all : /api/playlists/get
   * one by ID: /api/playlists/get/id/{id}
   * all matching the name: /api/playlists/get/name/{name}
   * all matching the owner: /api/playlists/get/owner/{ownerId}
 * UserRoles:
   * all : /api/user_roles/get
   * one by ID: /api/user_roles/get/id/{id}
   * all matching the name: /api/user_roles/get/name/{name}

## Built With
* [Java 8]()
  * [Spring Boot]()
  * [Spring Data]()
  * [Lombok]()
* [PostgreSQL]()
* [Heroku]()

## Author

* **Andrzej Przybysz** - [BrieflyClear](https://github.com/BirieflyClear)

Still developing.
