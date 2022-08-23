# Recipe API

This is a standalone java application which allows users to manage their favourite recipes.
Users are able to filter available recipes based on one or more of the following criteria:

1. Whether all ingredients are vegetarian
2. The number of servings
3. Include ingredients list
4. Exclude ingredients list
5. Text search for a specific word in an instruction.

The application is implemented on Java 11 according to REST architecture and uses Spring Boot framework version 2.7.3.
The REST API has all generated documentation available on the local url: http://localhost:8080/swagger-ui/index.html

## How to run with local DB

Import project as Gradle project.
To run application with local in-memory H2 DB using IDE the **"local"** profile must be explicitly set as environment 
variable spring.profiles.active=local
For tests, the application can be run using a local in-memory H2 DB as datasource:

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

For production the Postgres DB is required.
The profile **"prod"** contains configuration for local Postgres DB.
This profile is default.
Setting URL and credentials are in the file **application-prod.yml**
To use this profile run:

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## Docker configuration

Docker must be preinstalled to use this configuration.
To run application with Postgres DB in Docker:

```bash
docker-compose up -d
```

## Tests

Integration and unit tests implemented for testing.
