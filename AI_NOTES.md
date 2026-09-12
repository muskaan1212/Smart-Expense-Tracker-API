# AI Notes

## Assisted work

The project was implemented with AI assistance from the approved plan. The initial frontend starter was replaced with a focused Java 21/Spring Boot API at the repository root.

## Manual review

Reviewed the package structure, validation rules, HTTP status codes, persistence behavior, case-insensitive category filtering, `BigDecimal` totals, and the test isolation strategy. The repository writes only when data changes and creates the configured parent directory when necessary.

## Rejected suggestions

No database, authentication layer, client UI, or local browser storage was added because the requested API is intentionally self-contained and file-backed.

## Validation performed

The intended validation sequence is `mvn clean test`, followed by `mvn spring-boot:run` and manual checks of create, list, filter, total, delete, validation, not-found, persistence, and Swagger endpoints.

## Edge cases

Invalid or missing request fields return HTTP 400. Future dates are rejected. Missing delete IDs return HTTP 404. Empty stores report a zero total. Category matching is normalized with the root locale.

## Final deviations

The implementation uses a single JSON file and generated IDs based on the largest persisted ID, matching the lightweight storage requirement without introducing a database dependency.
