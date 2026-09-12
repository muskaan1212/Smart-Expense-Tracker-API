# Smart Expense Tracker API

A small Spring Boot 3 REST API for recording and reviewing expenses. Data is persisted to a local JSON file, making the project easy to run without a database.

## Requirements

- Java 21+
- Maven 3.9+

## Run

```bash
mvn clean test
mvn spring-boot:run
```

The API starts at `http://localhost:8080`. Set `EXPENSE_STORAGE_FILE` to change the JSON storage path.

## Endpoints

### Create

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H 'Content-Type: application/json' \
  -d '{"amount":12.50,"category":"Food","description":"Lunch","date":"2026-01-15"}'
```

Returns `201 Created` with the generated numeric `id`.

### List and filter

```bash
curl http://localhost:8080/api/expenses
curl 'http://localhost:8080/api/expenses?category=food'
```

Category filtering is case-insensitive.

### Totals

```bash
curl http://localhost:8080/api/expenses/total
curl 'http://localhost:8080/api/expenses/total?category=food'
```

### Delete

```bash
curl -i -X DELETE http://localhost:8080/api/expenses/1
```

Returns `204 No Content`, or `404 Not Found` when the ID does not exist.

## Validation and storage

`amount` must be at least `0.01`; `category`, `description`, and `date` are required; dates cannot be in the future. Amounts and totals use `BigDecimal`. The JSON file is loaded at startup and written after creates and deletes.

## API documentation

Swagger UI is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html), with the OpenAPI document at `/v3/api-docs`.

## Project layout

- `src/main/java`: application, controller, service, model, and JSON repository
- `tests`: JUnit/Spring MVC tests
- `AI_NOTES.md`: assisted-development notes and review record
