# Songbook_Service
A Spring web application connected to a PostgreSQL database and deployed to Heroku.

It is an app that lets the users manage songs in a songbook (add, remove, update and read, create playlists), manage accounts and singing events.

For example my Android app named "Songbook" is connected to the API. It will display the songs on mobile devices and implement even more fetures that this web app (eg. connect with other Android devices near you and display the same song).

## Features
* HATEOAS driven RESTful API
* Browse a huge database of guitar songs' lyrics and guitar tabs
* Create your own library of your favorite songs
* Create a playlist for each occassion
* Create a printable song book out of any playlist
* see all upcoming and past singing events and come to one!

## Getting Started
 * The deployed Heroku app: https://stk-songbook.herokuapp.com/ (currently only the API works)
 * The Android app's repo: https://github.com/BrieflyClear/Songbook-Android

### How to use the API
Refer to the following endpoints:
* Authors:
   * all : /api/authors
   * one by ID: /api/authors/id/{id}
   * all matching the name (usually one): /api/authors/name/{name}
 * Songs:
   * all : /api/songs
   * one by ID: /api/songs/id/{id}
   * all matching the title: /api/songs/title/{title}
   * all matching the author: /api/songs/author/{authorId}
   * all matching the category: /api/songs/category/{categoryId}
 * Tags:
   * all : /api/tags
   * one by ID: /api/tags/id/{id}
   * all matching the name (usually one): /api/tags/name/{name}
 * Categories:
   * all : /api/categories
   * one by ID: /api/categories/id/{id}
   * all matching the name (usually one): /api/categories/name/{name}
 * Playlists (only the ones with 'public' marker):
   * all : /api/playlists
   * one by ID: /api/playlists/id/{id}
   * all matching the name: /api/playlists/name/{name}
   * all matching the owner: /api/playlists/owner/{ownerId}
 * UserRoles:
   * all : /api/user_roles
   * one by ID: /api/user_roles/id/{id}
   * all matching the name: /api/user_roles/name/{name}

## Built With
* [Java 8]()
  * [Spring Boot]()
  * [Spring Data]()
  * [Spring MVC]()
  * [Spring Security]()
  * [JUnit]()
  * [Mockito]()
  * [Lombok]()
  * [MapStruct]()
  * [Log4J]()
* [Angular 8]()
  * [Bootstrap]()
* [PostgreSQL]()
* [Heroku]()

## Author

* **Andrzej Przybysz** - [BrieflyClear](https://github.com/BirieflyClear)

Still developing.
