# E-Learning Spring Boot API

A Spring Boot e-learning backend for managing students, courses, and enrollments, with email notifications, auditing, scheduling, and pagination support.

## Tech Stack

- Java 25
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- MySQL
- Spring Mail
- Spring AOP
- Spring Actuator
- Lombok
- Maven Wrapper (`mvnw`, `mvnw.cmd`)

## Project Structure

```text
src/main/java/com/saadoun/e_learning
  |- aspect/         # AOP audit logging
  |- configration/   # Web configuration
  |- controller/     # REST controllers
  |- dto/            # DTOs, requests, responses, projections
  |- event/          # Domain events, listener, publisher
  |- mapper/         # Entity/DTO mappers
  |- model/          # JPA entities and specs
  |- repositories/   # Spring Data repositories
  |- scheduler/      # Scheduled jobs (email retry)
  |- service/        # Business services
src/main/resources
  |- application.yaml
  |- application-local.yaml
  |- logback-spring.xml
```

## Prerequisites

- JDK 25 installed and available on PATH
- MySQL server running
- Maven is optional (wrapper is included)

## Configuration

Use `src/main/resources/application-local.yaml` for local development values.

Typical properties to configure:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto`
- `spring.mail.host`
- `spring.mail.port`
- `spring.mail.username`
- `spring.mail.password`
- `spring.mail.properties.mail.smtp.auth`
- `spring.mail.properties.mail.smtp.starttls.enable`

Run with the local profile:

```powershell
./mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

## Build and Run

Compile only:

```powershell
./mvnw.cmd -q -DskipTests compile
```

Run tests:

```powershell
./mvnw.cmd test
```

Start application:

```powershell
./mvnw.cmd spring-boot:run
```

Package jar:

```powershell
./mvnw.cmd clean package
```

## API Overview

Base path follows your controller mappings (commonly `/api/...`).

Main domains:

- Students
- Courses
- Enrollments

The codebase also includes:

- Paginated student listing via `/api/students/get-all` (`page`, `size`, `sort`)
- Enrollment-created events and listeners
- Async email sending in the background (`@Async` listener)
- Email queue with automatic retry scheduler for failed emails
- Audit logging via AOP aspect

## Email Delivery Behavior

- Enrollment emails are sent in the background to avoid blocking API requests.
- Each outgoing email is first saved as a pending queue record.
- If sending fails, the email is marked as failed and scheduled for retry.
- A scheduler periodically retries failed emails and sends them again to users.
- Successfully retried emails are marked as sent.

## Logging

Logging is configured via `src/main/resources/logback-spring.xml`.

- File logging is enabled.
- Console logging should also be enabled for easier local debugging.

## Notes

- Java package uses `com.saadoun.e_learning` (underscore), not `com.saadoun.e-learning`.
- `spring-boot-starter-aop` is pinned to `4.0.0-M2` in `pom.xml` while parent is `4.1.0`.
  If dependency resolution issues appear, run:

```powershell
./mvnw.cmd -q -U -DskipTests compile
```

## Useful Files

- `pom.xml`
- `HELP.md`
- `src/main/resources/application.yaml`
- `src/main/resources/application-local.yaml`
- `src/main/resources/logback-spring.xml`

## Future Improvements

- Add OpenAPI/Swagger documentation for endpoints.
- Add integration tests for student/course/enrollment flows.
- Add Docker Compose for app + MySQL local startup.
