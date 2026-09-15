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

Generated QR code images and customization assets are stored under `src/main/resources/static/images/` by default; override with `IMAGE_FOLDER_QR_CODE`, `IMAGE_FOLDER_CUSTOMIZE`, `IMAGE_FOLDER_CUSTOMIZE_LOGO`, `IMAGE_FOLDER_COMMON` (the `Dockerfile` points these at `/app/data/images/...`).

## Deploying (e.g. Render free tier)

A `Dockerfile` is included for container-based hosts.

1. Create a free PostgreSQL instance on the host and note its connection details.
2. Create a Web Service from this repo's `Dockerfile`.
3. Set environment variables on the web service:
   - `SPRING_DATASOURCE_URL=jdbc:postgresql://<db-host>:<db-port>/<db-name>`
   - `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
   - `PORT` is provided automatically by most hosts; the app already binds to it.
4. Deploy. The app creates its schema and seed data automatically on first boot.
