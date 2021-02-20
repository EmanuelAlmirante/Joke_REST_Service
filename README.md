# Joke REST Service

For this solution it was used a H2 in-memory DB to simplify things. This is a SQL DB, which fulfills the requirement to demonstrate ability to work with relational databases. 

The requirement regarding the text search query is not perfect. It will work with multiple words in the query, but they must be i the same order that they appear in the joke. For example, if we have a joke with the text "This is a test joke" and we try to do a text search query of "joke test" the result will be empty. On the other hand, if we do a text search query of "test joke" it will retrieve the correct result. To perfect this we should use Elasticsearch, for example.

### Tech Stack:

- Java 11
- Spring Boot
- Hibernate
- Swagger
- H2 Database (in-memory)
- JUnit
- Hamcrest
- Mockito


### Setup without Docker:

- Clone/extract project to a folder
- Run the application with:
  - _mvn clean install_
  - _mvn spring-boot:run_
- Test the application with:
  - _mvn test_ -> run all tests
  - _mvn -Dtest=TestClass test_ -> run a single test class
  - _mvn -Dtest=TestClass1,TestClass2 test_ -> run multiple test classes
- Package the application with _mvn package_
- Test using Postman and the file in the folder _postman_collections_


### Setup with Docker:

- Install Docker on your machine
- Launch Docker
- Run the command _sudo systemctl status docker_ to confirm Docker is running
- Open terminal in the project folder
- Run the command _sudo docker-compose build_
- Run the command _sudo docker-compose up -d_
- The container will be up and the application will be running inside
- Test using Postman and the file in the folder _postman_collections_


### Endpoints:

The documentation of this API can be found at _http://localhost:8080/swagger-ui.html_ (**Note: you need to initialize the application to access this link**).

For testing the API use Postman and the file in the _postman_collections_ folder. 
