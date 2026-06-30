# Document Management System (DMS)

## Overview

This project is a simplified Document Management System (DMS) implementing a regulated document workflow.

## Tech Stack

- Java 25
- Spring Boot 4.1
- Spring Data JPA
- H2 Database
- Maven
- Lombok

## Features

- Create Document
- Submit for Review
- Approve Review
- Approve Document
- Mark Document as Obsolete
- Immutable Audit Log
- Tenant Isolation
- DTO-based API Responses
- Global Exception Handling

## Workflow

```
DRAFT
   |
   v
IN_REVIEW
   |
   v
PENDING_APPROVAL
   |
   v
RELEASED
   |
   v
OBSOLETE
```

## Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

Application runs on:

```
http://localhost:8080
```

## H2 Console

URL

```
http://localhost:8080/h2-console
```

Database Details

```
JDBC URL : jdbc:h2:mem:dmsdb
Username : sa
Password :
```

## API Endpoints

### Create Document

```
POST /api/{tenantId}/documents
```

### Get Document

```
GET /api/{tenantId}/documents/{id}
```

### Submit Document

```
PATCH /api/{tenantId}/documents/{id}/submit
```

### Approve Review

```
PATCH /api/{tenantId}/documents/{id}/approve-review
```

### Reject Review

```
PATCH /api/{tenantId}/documents/{id}/reject
```

### Approve Document

```
PATCH /api/{tenantId}/documents/{id}/approve
```

### Mark Obsolete

```
PATCH /api/{tenantId}/documents/{id}/obsolete
```

### Audit Log

```
GET /api/{tenantId}/documents/{id}/audit-log
```

## Sample Create Request

```json
{
  "title": "Java Spring Boot",
  "type": "SOP",
  "authorId": "22222222-2222-2222-2222-222222222222"
}
```

## Sample Submit Request

```json
{
  "reviewerId": "33333333-3333-3333-3333-333333333333",
  "actorId": "33333333-3333-3333-3333-333333333333"
}
```

## Testing

The APIs were tested using Postman.

## Project Structure

```
controller/
service/
repository/
entity/
dto/
mapper/
exception/
config/
enums/
```

## Author

Indrajit Dhage