# Insurance Claims Microservice

A Spring Boot microservice for processing insurance claims with customer management capabilities.

## Overview

This service provides REST APIs to manage insurance claims and customer data, built with modern Java and Spring Boot technologies.

- Database Schema is in the root directory  of the folder.

## Tech Stack

- Java 21
- Spring Boot
- PostgreSQL
- Docker

## Getting Started

### Installation

1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```

2. Build the project:
   ```bash
   cd insurance-claims-service
   mvn clean package
   ```

## Running the Application

### Local Development

Start PostgreSQL (optional):
```bash
docker-compose up -d
```

Run the application:
```bash
mvn spring-boot:run
```

## Access

The application runs at `http://localhost:8080`

---
