Description
------------
Task: Design and implement a REST API using Spring Boot without frontend.

Stack
------------

* Java 11
* Maven
* Spring Boot 2.7.0
* Spring Data JPA
* H2 Database
* JUnit 5
* Lombok
* Log4J

Installation
------------
Clone a repository:

```sh
$ git clone https://github.com/AnatolyRytik/safetynet-system.git
```

Run program in IDE or just execute it with Maven:

```sh
$ mvn spring-boot:run
```

##### H2 database

* JDBC URL: jdbc:h2:mem:testdb
* user name: sa
* without password

## Endpoints

### GET

**http://localhost:8080/firestation?stationNumber={station_number}**

> This url should return a list of people covered by the corresponding fire station.
> So if the station number = 1, it must return the inhabitants covered by station number 1. The list
> must include the following specific information: first name, last name, address, telephone number. Furthermore,
> it must provide a count of the number of adults and the number of children (any individual aged 18 or over
> less) in the service area.

**http://localhost:8080/childAlert?address={address}**

> This url should return a list of children (any individual aged 18 or younger) living at this address.
> The list should include the first and last name of each child, their age and a list of others
> household members. If there is no child, this url may return an empty string

**http://localhost:8080/phoneAlert?firestation={firestation_number}**

> This url must return a list of the telephone numbers of the residents served by the fire station.
> firefighters. We will use it to send emergency text messages to specific households.

**http://localhost:8080/fire?address={address}**

> This url must return the list of inhabitants living at the given address as well as the number of the barracks
> of firefighters serving it. The list should include name, phone number, age and background
> medical (drugs, dosage and allergies) of each person

**http://localhost:8080/flood/stations?stations={list_of_station_numbers}**

> This url should return a list of all homes served by the barracks. This list should include the
> people by address. It must also include the name, telephone number and age of the inhabitants, and
> include their medical history (medications, dosage and allergies) next to each name.

**http://localhost:8080/personInfo?firstName={firstName(not_required)}&lastName={lastName}**

> This url must return the name, address, age, email address and medical history (drugs,
> dosage, allergies) of each inhabitant. If more than one person has the same name, they must
> all appear.

**http://localhost:8080/communityEmail?city={city}**

> This url must return the email addresses of all the inhabitants of the city

### POST / PUT / DELETE

To add and update you need to send a Json body :

http://localhost:8080/person

http://localhost:8080/medicalRecord

http://localhost:8080/firestation

