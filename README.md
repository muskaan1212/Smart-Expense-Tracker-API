# 💸 Smart Expense Tracker API

> A lightweight REST API for tracking, filtering, and analyzing personal expenses — built with **Java + Spring Boot**.

No database. No unnecessary complexity. Just a clean API that does the job. ⚡

---

## ✨ What can it do?

| Feature           | Endpoint                            | Description                    |
| ----------------- | ----------------------------------- | ------------------------------ |
| ➕ Add expense     | `POST /expenses`                    | Create a new expense           |
| 📋 View expenses  | `GET /expenses`                     | Get all expenses               |
| 🔎 Filter         | `GET /expenses?category=Food`       | Filter by category             |
| 🧮 Total          | `GET /expenses/total`               | Calculate overall spending     |
| 📊 Category total | `GET /expenses/total?category=Food` | Calculate spending by category |
| 🗑️ Delete        | `DELETE /expenses/{id}`             | Remove an expense              |

The API also includes input validation, error handling, JSON persistence, automated tests, and interactive Swagger documentation.

---

## 🛠️ Built with

**Backend**

* ☕ Java 21
* 🌱 Spring Boot 3
* 📦 Maven

**Testing**

* 🧪 JUnit 5
* 🔬 Spring Boot Test

**Documentation**

* 📖 OpenAPI / Swagger

**Storage**

* 📄 Local JSON file

> A database wasn't necessary for this assignment, so the application keeps things intentionally lightweight with local JSON persistence.

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd expense-tracker-api
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Start the API

```bash
mvn spring-boot:run
```

The server will start at:

```text
http://localhost:8080
```

That's it. 🎉

---

## 🧪 Run the Tests

Run the complete test suite:

```bash
mvn test
```

Or perform a clean build and test:

```bash
mvn clean test
```

The test suite covers the core API behavior, validation, filtering, calculations, and error cases.

---

# 📡 API Reference

## ➕ Create an Expense

```http
POST /expenses
```

### Request

```json
{
  "title": "Pizza",
  "amount": 450.00,
  "category": "Food",
  "date": "2026-09-12"
}
```

### Response

```json
{
  "id": 1,
  "title": "Pizza",
  "amount": 450.00,
  "category": "Food",
  "date": "2026-09-12"
}
```

**Response:** `201 Created`

The API generates the expense ID automatically.

---

## 📋 Get All Expenses

```http
GET /expenses
```

### Response

```json
[
  {
    "id": 1,
    "title": "Pizza",
    "amount": 450.00,
    "category": "Food",
    "date": "2026-09-12"
  },
  {
    "id": 2,
    "title": "Metro",
    "amount": 80.00,
    "category": "Transport",
    "date": "2026-09-11"
  }
]
```

**Response:** `200 OK`

---

## 🔎 Filter by Category

```http
GET /expenses?category=Food
```

Category matching is **case-insensitive**.

For example:

```text
/expenses?category=Food
/expenses?category=food
/expenses?category=FOOD
```

all return the same category results.

---

## 🧮 Calculate Total Spending

### Overall

```http
GET /expenses/total
```

Response:

```json
{
  "total": 530.00
}
```

### By Category

```http
GET /expenses/total?category=Food
```

Response:

```json
{
  "total": 450.00
}
```

---

## 🗑️ Delete an Expense

```http
DELETE /expenses/1
```

Successful deletion returns:

```text
204 No Content
```

If the expense doesn't exist:

```text
404 Not Found
```

---

# 🛡️ Validation & Error Handling

The API doesn't blindly accept everything thrown at it.

### Expense validation

| Field      | Rule                     |
| ---------- | ------------------------ |
| `title`    | Cannot be blank          |
| `amount`   | Must be greater than `0` |
| `category` | Cannot be blank          |
| `date`     | Must be a valid date     |

Invalid input returns:

```text
400 Bad Request
```

Trying to access or delete an expense that doesn't exist returns:

```text
404 Not Found
```

---

# 📖 Interactive API Documentation

Swagger is included so the API can be explored without manually constructing requests.

Start the application and open:

```text
http://localhost:8080/swagger-ui/index.html
```

From there you can:

* View every endpoint
* Inspect request/response models
* Send API requests
* Test validation
* Explore error responses

---

# 🏗️ Architecture

The project follows a simple layered architecture:

```text
             HTTP Request
                  │
                  ▼
        ┌──────────────────┐
        │    Controller    │
        └────────┬─────────┘
                 │
                 ▼
        ┌──────────────────┐
        │     Service      │
        └────────┬─────────┘
                 │
                 ▼
        ┌──────────────────┐
        │    Repository    │
        └────────┬─────────┘
                 │
                 ▼
        ┌──────────────────┐
        │   JSON Storage   │
        └──────────────────┘
```

Each layer has a focused responsibility:

**Controller** → handles HTTP requests and responses

**Service** → contains business logic

**Repository** → handles data persistence

**Model / DTO** → represents API data

**Exception Handler** → provides consistent error responses

---

# 📁 Project Structure

```text
expense-tracker-api/
│
├── README.md
├── AI_NOTES.md
├── pom.xml
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/muskaan/expensetracker/
│       │       ├── controller/
│       │       ├── service/
│       │       ├── repository/
│       │       ├── model/
│       │       ├── dto/
│       │       ├── exception/
│       │       └── ExpenseTrackerApplication.java
│       │
│       └── resources/
│           └── application.properties
│
└── tests/
```

---

# 💾 Persistence

The API uses a local JSON file instead of an external database.

When the application starts, existing expenses are loaded from the file.

When an expense is added or deleted, the stored data is updated.

This keeps the project:

* Lightweight
* Easy to run
* Easy to inspect
* Free from database setup

No MySQL. No PostgreSQL. No database credentials. Just run the application. 🚀

---

# 🧪 Testing Strategy

The test suite focuses on both the **happy path** and common failure cases.

Tests cover:

* Creating an expense
* Retrieving expenses
* Filtering by category
* Calculating overall totals
* Calculating category totals
* Deleting an expense
* Handling missing expense IDs
* Rejecting invalid amounts
* Rejecting missing required fields

Run everything with:

```bash
mvn clean test
```

---

# 🤖 AI Usage

AI tools were used as a development assistant during this project.

The complete details of:

* What was AI-generated
* What was written manually
* What was reviewed and changed
* What was tested
* Which AI suggestions were rejected

are documented separately in [`AI_NOTES.md`](AI_NOTES.md).

The goal was to use AI for productivity while still understanding, reviewing, testing, and taking responsibility for the final implementation.

---

# 🎯 Design Decisions

### Why Spring Boot?

Spring Boot provides a clean structure for building REST APIs while keeping the project easy to test and maintain.

### Why JSON instead of a database?

The assignment explicitly allows in-memory or local JSON storage and does not require a database. JSON persistence provides data durability without introducing unnecessary infrastructure.

### Why layered architecture?

Separating controllers, services, and repositories keeps business logic independent from HTTP handling and persistence, making the application easier to test and extend.

### Why Swagger?

Swagger provides an immediately usable interface for reviewers to explore and test the API.

---

# 🔮 Possible Extensions

If this API were developed beyond the assignment, some natural next steps would be:

* 🔐 Authentication and user accounts
* 🗄️ Database persistence
* 🔎 Full-text expense search
* 📅 Monthly spending summaries
* 📈 Spending analytics
* 📤 CSV export
* ☁️ Cloud deployment

These are intentionally outside the current scope to keep the implementation focused on the assignment requirements.

---

## 👩‍💻 Built for the Software Engineering Apprenticeship Assignment — 2026

**Smart Expense Tracker API**

Simple API. Clean architecture. Tested behavior.
