# Programming 5 Project - March 2023
### Dorothy Modrall Sperling ACS 102B

## Domain and Relationships
The 3 main entities in this project are:
- Species (a dinosaur species). Species objects have a name, a scientific name, a diet (an enum, Diet, which can be HERBIVORE or CARNIVORE), a Period object, a URL to an image, and a list of digsites that they were found in.
- DigSite (a place where a fossil of a dinosaur species has been found). Digsite objects have a name, a country, doubles indicating coordinates (latitude and longitude), date of first excavation, and a list of species found.
- Period (a geologic timer period, e.g. Jurassic, Cretaceous) during which a dinosaurs species lived. Period objects have a name, a double indicating when they start in millions of years, and a double indicating when they end in millions of years. 

Below is an explanation of the many-to-many and one-to-many relationships in the domain:

### Many-to-Many Relationship
Species and DigSite have a many-to-many relationship. A species' fossil specimen may have been found in 0 or many digsites, and a digsite may contain fossils of 0 or many different species. 

### One-to-Many Relationship
Period and Species have a one-to-many relationship. Multiple Species may have lived during a Period, but no Species lives during more than one Period. 

## Build/Run
Use the profile _springdatarepo_ to run this project. This profile uses an H2 database that is managed with Spring Data Repository interfaces.

This project does not use web and console profiles. This project can only be run as a web application and not in the console. 

The profile _test_ is the active profile when running tests. This profile ensures that data is NOT added to the repository layer via a .sql file. 

Run tests from the command line with in the directory of this project with `./gradlew test`. 
Below is an example of a successful output: 
```
> Task :test
2023-03-26 20:48:22,923 INFO  [SpringApplicationShutdownHook] org.springframework.orm.jpa.AbstractEntityManagerFactoryBean: Closing JPA EntityManagerFactory for persistence unit 'default'
2023-03-26 20:48:22,925 INFO  [SpringApplicationShutdownHook] com.zaxxer.hikari.HikariDataSource: HikariPool-1 - Shutdown initiated...
2023-03-26 20:48:22,928 INFO  [SpringApplicationShutdownHook] com.zaxxer.hikari.HikariDataSource: HikariPool-1 - Shutdown completed.
2023-03-26 20:48:22,931 INFO  [SpringApplicationShutdownHook] org.springframework.orm.jpa.AbstractEntityManagerFactoryBean: Closing JPA EntityManagerFactory for persistence unit 'default'
2023-03-26 20:48:22,931 INFO  [SpringApplicationShutdownHook] com.zaxxer.hikari.HikariDataSource: HikariPool-2 - Shutdown initiated...
2023-03-26 20:48:22,933 INFO  [SpringApplicationShutdownHook] com.zaxxer.hikari.HikariDataSource: HikariPool-2 - Shutdown completed.

BUILD SUCCESSFUL in 16s
5 actionable tasks: 3 executed, 2 up-to-date
```

## Database
_springdatarepo_ uses an H2 database that is created when the project runs the import.sql file. Log into the database via the H2 console with URL "jdbc:h2:mem:spring_dinosaursdb" with username "sa".


# User Accounts

## Users
Can call all GET, rest API methods, and can call POST methods to add a SpeciesUserFavorite object.
Can see "favorite" pages, the "favorite" button, "add" pages, the "add" button, and the viewing history page. 
1) Username "hans" - password "spring"
2) User "lars" - password "more_spring"
3) User "toni" - password "cisco"

## Moderators
Can call all GET, PATCH, and POST rest API methods, including the POST method to add a SpeciesUserFavorite object.
Can see "favorite" pages, the "favorite" button, "add" pages, the "add" button, "Save Changes" button, and the viewing history page. 
2) Username "piet" - password "bash"
2) User "jeroen" - password "python"
3) User "rony" - password "talend"

## Administrators
Can call all GET, PATCH, DELETE, and POST rest API methods, including the POST method to add a SpeciesUserFavorite object.
Can see "favorite" pages, the "favorite" button, "add" pages, "Save Changes" button, "Delete" button, and the viewing history page.
1) Username "jan" - password "gangof4"
2) User "wim" - password "statistics"

## Hidden Pages for Unauthorized Users
- Session viewing history page - http://localhost:8300/sessionhistory
- - Session viewing history page - http://localhost:8300/dinosaurs/favorites
- Pages for adding species or digsites - http://localhost:8300/digsites/add, http://localhost:8300/dinosaurs/add

## Hidden Items for Unauthorized Users
- Buttons that allow user to add, delete, or save updates to digsites and dinosaurs
- Buttons on homepage and navbar links to pages for adding species or digsites

## Users and Species
Users can be linked to Species through "favoriting". A User can "favorite" a Species only once and can "unfavorite" this species.
Species may be "favorited" by many users, and users may "favorite" many species. Users can see the species they favorited on their "My Favorite Species" page.

# GET and DELETE endpoints

## Fetching 1 Dinosaur Species - OK
### Request
```
GET http://localhost:8350/api/dinosaurs/1
Accept: application/json
```
### Response
```
{
  "id": 1,
  "name": "Iguanodon",
  "scientificName": "springosaurus booticus",
  "numberSpecimensFound": 100,
  "diet": "HERBIVORE",
  "period": {
    "name": "Cretaceous",
    "startMillionsYears": 145.0,
    "endMillionsYears": 100.5
  }
}
```


## Fetching 1 Dinosaur Species - Not Found
### Request
```
GET http://localhost:8350/api/dinosaurs/100
Accept: application/json
```
### Response 
```
{
  "timestamp": "2023-03-08T21:35:22.647+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/dinosaurs/100"
}
```

## Fetching All Dinosaur Species - OK
### Request
```
GET http://localhost:8350/api/dinosaurs/
Accept: application/json
```
### Response 
```
[
  {
    "id": 1,
    "name": "Iguanodon",
    "scientificName": "Iguanodon bernissartensis",
    "numberSpecimensFound": 38,
    "diet": "HERBIVORE",
    "period": {
      "name": "Cretaceous",
      "startMillionsYears": 145.0,
      "endMillionsYears": 100.5
    }
  },
  {
    "id": 2,
    "name": "Archeopteryx",
    "scientificName": "Archaeopteryx lithographica",
    "numberSpecimensFound": 12,
    "diet": "CARNIVORE",
    "period": {
      "name": "Jurassic",
      "startMillionsYears": 201.3,
      "endMillionsYears": 145.0
    }
  },
  {
    "id": 3,
    "name": "T. rex",
    "scientificName": "Tyrannosaurus rex",
    "numberSpecimensFound": 32,
    "diet": "CARNIVORE",
    "period": {
      "name": "Cretaceous",
      "startMillionsYears": 145.0,
      "endMillionsYears": 100.5
    }
  },
  {
    "id": 4,
    "name": "Triceratops",
    "scientificName": "Triceratops horridus",
    "numberSpecimensFound": 166,
    "diet": "HERBIVORE",
    "period": {
      "name": "Cretaceous",
      "startMillionsYears": 145.0,
      "endMillionsYears": 100.5
    }
  },
  {
    "id": 5,
    "name": "Ankylosaurus",
    "scientificName": "Ankylosaurus magniventris",
    "numberSpecimensFound": 3,
    "diet": "HERBIVORE",
    "period": {
      "name": "Cretaceous",
      "startMillionsYears": 145.0,
      "endMillionsYears": 100.5
    }
  },
  {
    "id": 6,
    "name": "Sauropod",
    "scientificName": "Patagotitan mayorum",
    "numberSpecimensFound": 150,
    "diet": "HERBIVORE",
    "period": {
      "name": "Cretaceous",
      "startMillionsYears": 145.0,
      "endMillionsYears": 100.5
    }
  },
  {
    "id": 7,
    "name": "Plesiosaur",
    "scientificName": "Plesiosaurus dolichodeirus",
    "numberSpecimensFound": 114,
    "diet": "CARNIVORE",
    "period": {
      "name": "Triassic",
      "startMillionsYears": 251.902,
      "endMillionsYears": 201.36
    }
  },
  {
    "id": 8,
    "name": "Stegosaur",
    "scientificName": "Stegosaurus stenops",
    "numberSpecimensFound": 80,
    "diet": "HERBIVORE",
    "period": {
      "name": "Jurassic",
      "startMillionsYears": 201.3,
      "endMillionsYears": 145.0
    }
  },
  {
    "id": 9,
    "name": "Imaginary Dinosaur",
    "scientificName": "Imaginary Dinosaur",
    "numberSpecimensFound": 1,
    "diet": "HERBIVORE",
    "period": {
      "name": "Triassic",
      "startMillionsYears": 251.902,
      "endMillionsYears": 201.36
    }
  }
]
```


## Fetching All Dig Sites of A Dinosaur Species - No Content
### Request
```
GET http://localhost:8350/api/dinosaurs/9/digsites
Accept: application/json
```
### Response
```
<Response body is empty>

Response code: 204; Time: 8ms (8 ms); Content length: 0 bytes (0 B)
```

## Fetching All Dig Sites of A Dinosaur Species - Not Found 
### Request
```
GET http://localhost:8350/api/dinosaurs/100/digsites
Accept: application/json
```
### Response 
```
<Response body is empty>

Response code: 404; Time: 6ms (6 ms); Content length: 0 bytes (0 B)
```

## Deleting A Dinosaur Species - No Content 
### Request
```
DELETE http://localhost:8350/api/dinosaurs/5
```

### Response 
```
<Response body is empty>

Response code: 204; Time: 15ms (15 ms); Content length: 0 bytes (0 B)
```

## Deleting A Dinosaur Species - Not Found
### Request
```
DELETE http://localhost:8350/api/dinosaurs/100
```

### Response 
```
<Response body is empty>

Response code: 404; Time: 6ms (6 ms); Content length: 0 bytes (0 B)
```


# POST and PATCH Endpoints

## Creating a dinosaur - OK
### Request
```
POST http://localhost:8350/api/dinosaurs/
Accept: application/json
Content-Type: application/json

{
  "name": "Springosaur",
  "scientificName": "Springosaurus examplus",
  "numberOfSpecimensFound": 10,
  "image":"https://i.natgeofe.com/n/9b87dda3-9946-4a1c-a97f-c21f73ced888/Meraxes-CREDIT-Carlos-Papolio_4x3.jpg",
  "dietName":"Carnivore",
  "periodName":"Cretaceous",
  "foundInDigSites": [2,3,4]
}
```
### Response
```
{
  "id": 16,
  "name": "Springosaur",
  "scientificName": "Springosaurus examplus",
  "numberSpecimensFound": 10,
  "diet": "CARNIVORE",
  "period": {
    "name": "Cretaceous",
    "startMillionsYears": 145.0,
    "endMillionsYears": 100.5
  }
}
```

## Creating a dinosaur - Bad Request
### Request
```
POST http://localhost:8350/api/dinosaurs/
Accept: application/json
Content-Type: application/json

{
  "name": "Springosaur Bad Example!",
  "scientificName": "Springosaurus",
  "numberOfSpecimensFound": -1,
  "image":"https://i.natgeofe.com/n/9b87dda3-9946-4a1c-a97f-c21f73ced888/Meraxes-CREDIT-Carlos-Papolio_4x3.jpg",
  "dietName":"Vegetarian!",
  "periodName":"right now",
  "foundInDigSites": [2,3,4]
}
```
### Response
```
{
  "timestamp": "2023-03-08T21:51:15.621+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/dinosaurs/"
}
```

## Updating a dinosaur - No Content
### Request
```
PATCH http://localhost:8350/api/dinosaurs/1
Content-Type: application/json

{
"scientificName" : "springosaurus booticus",
"numberOfSpecimensFound" : 100,
"periodName" : "cretaceous",
"dietName" : "herbivore"
}
```
### Response
```
<Response body is empty>

Response code: 204; Time: 8ms (8 ms); Content length: 0 bytes (0 B)
```

## Updating a dinosaur - Not Found
### Request
```
PATCH http://localhost:8350/api/dinosaurs/100
Content-Type: application/json

{
"scientificName" : "springosaurus booticus",
"numberOfSpecimensFound" : 100,
"periodName" : "cretaceous",
"dietName" : "herbivore"
}
```
### Response
```
<Response body is empty>

Response code: 404; Time: 4ms (4 ms); Content length: 0 bytes (0 B)
```

## Fetching 1 Dinosaur Species in XML Format
## Request
```
GET http://localhost:8350/api/dinosaurs/1
Accept: application/xml

```
## Response
```
<SpeciesDto>
    <id>1</id>
    <name>Iguanodon</name>
    <scientificName>springosaurus booticus</scientificName>
    <numberSpecimensFound>100</numberSpecimensFound>
    <diet>HERBIVORE</diet>
    <period>
        <name>Cretaceous</name>
        <startMillionsYears>145.0</startMillionsYears>
        <endMillionsYears>100.5</endMillionsYears>
    </period>
</SpeciesDto>
```
