# PHForeclosures

A Spring Boot application for managing PH Foreclosures data.

## Technical Stack

- **Java Version**: 17
- **Spring Boot Version**: 3.4.2
- **Build Tool**: Maven
- **Packaging**: JAR
- **Database**: SQLite
- **Testing**: JUnit, Mockito, Cucumber, http-client5

## Dependencies

- Spring Web - for building RESTful web services
- Spring Data JDBC - for database access
- SQLite JDBC Driver (xerial) - for SQLite connectivity

## Building the Application

```bash
mvn clean package
```

## Running the Application

```bash
mvn spring-boot:run
```

Or run the jar directly:

```bash
java -jar target/phforeclosures-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080 by default.

## Running Tests

```bash
mvn test
```

## Database

The application uses SQLite as the database. The database file (`phforeclosures.db`) will be created automatically in the project root directory when the application starts.