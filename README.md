# Spring boot + AngularJS CRUD

Simple CREATE, READ, UPDATE and DELETE record.
Just for fun, and for the love in learning.

### Tech

Uses:

* [Spring-boot] - Java spring framework
* [AngularJS] - Javascript Framework
* [SB Admin 2] - Bootstrap theme

### Run

```sh
$ git clone https://github.com/dmadrigalejos/springboot-angular-crud.git
$ cd springboot-angular-crud
$ mvn spring-boot:run
```

Open your browser and go to http://localhost:8080/note

Create package (production)

```sh
$ mvn package
$ java -jar target/springbootangularjscrud-0.0.1-SNAPSHOT.jar
```

## How to run in IDE
In order to run and play with the project you need to:
1. Clone the project from GitHub
2. Import the project in STS as `Existing Maven Project`
4. Run the Spring Boot application as normal Java Application
    - Locate SpringbootAngularjsCrudApplication.java file
    - Run As > Java Application
5. Open your browser and go to http://localhost:8080/note