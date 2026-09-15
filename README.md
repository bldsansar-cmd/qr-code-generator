# Qrazy

A Spring Boot application for generating customizable QR codes — pixel/finder styles, logos, colors, static or dynamic (redirecting) QR codes, PNG/SVG export, and bulk generation.

## Requirements

- Java 17+
- Docker (for the database)

## Setup

1. Start the database:

   ```bash
   docker compose up -d
   ```

   This launches a PostgreSQL container with a `qrazy` database (user/password: `postgres`/`postgres`). The schema and seed data are created automatically on first application startup.

2. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

3. Open http://localhost:8080/qrazy/

## Configuration

Database connection settings can be overridden with environment variables (defaults match `docker-compose.yml`):

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Generated QR code images and customization assets are stored under `src/main/resources/static/images/`.
