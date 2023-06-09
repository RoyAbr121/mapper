# Wheat Fields Mapper 

An application written using Spring Boot that implements a REST API in order to search for the geographic data of wheat fields worldwide - through the use of web scraping - and  present them on a map using a third-party API.


<img width="1257" alt="Screenshot 2023-05-02 094522" src="https://user-images.githubusercontent.com/42959429/235598695-f8255830-61b1-4dee-9e28-2833c6fe5d5d.png">

## Technologies

**Backend**: Spring Boot

**Database**: PostgreSQL

**Containerization**: Docker

**Miscellaneous**: Hibernate, Swagger


## How to Run Locally 

Clone the project:
```
got clone git@https://github.com/RoyAbr121/mapper.git
```

Enter the project folder. Start the PostgresSQL container using Docker Compose:
```
docker compose up -d
```
Run the project on your IDE. Use the Swagger UI in order to run the REST API calls(use **fields-controller**):

[http://localhost:8080/swagger-ui.html#](http://localhost:8080/swagger-ui.html#) 
