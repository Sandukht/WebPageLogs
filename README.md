# Web Page Word Statistics Analyzer

A full-stack web application that analyzes the most frequent words on any given URL.
Built with **Spring Boot** (Java) for the backend and **React** for the frontend. The application also stores results in a **MySQL database**.

---

## Project Structure

```
WebPageLogs/ <-- Root project folder
├── build/ <-- Gradle build output (auto-generated)
├── gradle/ <-- Gradle wrapper files
├── .gitignore
├── build.gradle
├── settings.gradle
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/webpagelogs/
│   │   │       ├── WebPageLogsApplication.java
│   │   │       ├── controller/
│   │   │       │   └── WebPageController.java
│   │   │       ├── db/
│   │   │       │   ├── WordStatistic.java
│   │   │       │   └── WordStatisticService.java
│   │   │       └── nlp/
│   │   │           ├── LanguageModelTrainer.java
│   │   │           └── LanguageDetector.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── lang-model.bin
│   │       ├── lang-train.txt
│   │       ├── stopwords/
│   │       │   ├── stopwords_en.txt
│   │       │   ├── stopwords_ru.txt
│   │       │   └── stopwords_hy.txt
│   │       └── static/
│   └── test/java/com/example/webpagelogs/ <-- Unit tests
├── frontend/
│   ├── package.json
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── App.js
│       ├── index.js
│       └── styles.css
```

---

## Project Overview

This application allows users to input a URL and analyze the words appearing on that web page. The backend performs **NLP-based word frequency analysis** and stores the results in a relational database. The frontend provides a responsive interface to display results in a table format.

---

## Features

* Analyze word frequency of any public web page.
* Supports multiple languages detected from words.
* Stores and updates results in a MySQL database.
* Full-stack architecture with React + Spring Boot + JPA.
* Cross-origin support for smooth frontend-backend communication.
* Handles errors gracefully and informs the user.

---

## Tech Stack

**Backend**

* Java 17
* Spring Boot 3
* Spring Data JPA
* Hibernate ORM
* MySQL
* OpenNLP for language processing

**Frontend**

* React 18
* Fetch API for AJAX requests
* HTML5 / CSS3

**Other**

* Gradle build system
* CORS configuration for frontend-backend communication

---

## Architecture

```
React Frontend <----> Spring Boot Backend <----> MySQL Database
       |                     |                       |
       |  HTTP Requests      |  JPA / Hibernate      |  Stores word counts & languages
       v                     v                       v
User enters URL  ->  NLP analysis  ->  Save/Update DB
```

---

## NLP Pretraining & Word Analysis

The backend leverages **Natural Language Processing (NLP)** to analyze word frequency and detect language. It uses **pre-trained models** and custom stopwords lists to improve accuracy.

### Pre-trained Language Model

* A **pre-trained language detection model** (`lang-model.bin`) is included and loaded by `LanguageDetector` and `LanguageModelTrainer`.
* Supports multiple languages simultaneously (English, German, French, Dutch, Turkish, Portuguese, Armenian, Russian, etc.).
* Detects language for each word individually, enabling accurate multilingual analysis.

### Training & Stopwords

* A **training dataset** (`lang-train.txt`) fine-tunes language detection for words commonly found on web pages.
* **Stopwords lists** are provided under `resources/stopwords/` to remove common non-meaningful words:

  * `stopwords_en.txt` – English stopwords
  * `stopwords_ru.txt` – Russian stopwords
  * `stopwords_hy.txt` – Armenian stopwords
* Stopwords removal ensures that analysis focuses on meaningful keywords.

### NLP Workflow

1. **Fetch Webpage Content** – Download URL content and parse HTML into plain text.
2. **Tokenization** – Split text into individual words/tokens.
3. **Stopwords Filtering** – Remove common filler words.
4. **Language Detection** – Detect language of each word using the pre-trained model.
5. **Frequency Counting** – Count occurrences of each unique word.
6. **Database Update** – Save words, counts, and languages in MySQL for later retrieval.

### Advantages

* **Faster Analysis** – Pre-trained model ready for inference.
* **Multilingual Support** – Handles mixed-language pages.
* **Accurate Keyword Extraction** – Stopwords and language detection reduce noise.
* **Scalable** – New languages can be added via training datasets.

**Example:**

```java
LanguageModelTrainer trainer = new LanguageModelTrainer();
List<Map<String, Object>> topWords = trainer.processUrl("https://example.com");
for (Map<String, Object> row : topWords) {
    System.out.println(row.get("word") + " - " + row.get("count") + " (" + row.get("lang") + ")");
}
```

---

## Setup Instructions

### Backend

1. Clone the repository:

```bash
git clone <repo-url>
cd WebPageLogs
```

2. Build and run Spring Boot:

```bash
./gradlew bootRun
```

**Backend URL:** `http://localhost:8080`

---

### Frontend

1. Navigate to frontend:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start development server:

```bash
npm start
```

**Frontend URL:** `http://localhost:3000`

> Ensure backend is running before submitting URLs.

---

### Database

1. Install MySQL and create database:

```sql
CREATE DATABASE webpagestats;
```

2. Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/webpagestats
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.jpa.hibernate.ddl-auto=update
```

---

## API Endpoints

| Method | URL            | Params | Description                    |
| ------ | -------------- | ------ | ------------------------------ |
| POST   | `/api/analyse` | url    | Analyze a URL and return JSON. |

**Example Request:**

```bash
curl -X POST "http://localhost:8080/api/analyse?url=https://example.com"
```

**Example Response:**

```json
[
  {"word": "in", "count": 3, "lang": "de"},
  {"word": "domain", "count": 3, "lang": "nl"},
  {"word": "use", "count": 2, "lang": "fr"}
]
```

---

## Usage

1. Open `http://localhost:3000`.
2. Enter a URL.
3. Click **Analyse**.
4. View word statistics in the table.

---

## Project Flow

1. User enters URL in React form.
2. Frontend sends a `POST` request to Spring Boot backend.
3. Backend uses `LanguageModelTrainer` to process the URL.
4. Words, counts, and languages are saved in MySQL via `WordStatisticService`.
5. Backend returns JSON with top words.
6. Frontend renders results in a table.

---

## Contributing

* Fork the repository.
* Create a feature branch: `git checkout -b feature-name`.
* Commit your changes: `git commit -m "Add new feature"`.
* Push to branch: `git push origin feature-name`.
* Create a pull request.

---

---
## Diagram showing NLP pretraining interaction

                +----------------+
                |  Pre-trained   |
                |  Language      |
                |  Model (bin)   |
                +--------+-------+
                         |
                         v
                 +-------+--------+
                 | LanguageModel  |
                 | Trainer /      |
                 | LanguageDetector|
                 +-------+--------+
                         |
                         v
   +---------------------+--------------------+
   |  Backend Processing (Spring Boot)       |
   |  - Fetch Webpage Content                |
   |  - Tokenize & Filter Stopwords         |
   |  - Detect Language                      |
   |  - Count Word Frequencies               |
   +---------------------+--------------------+
                         |
                         v
                +--------+--------+
                |   MySQL Database |
                |  word_statistics|
                +-----------------+
                         ^
                         |
               Frontend JSON Response
                   (React Table)

---

---
## How it works step by step:

Pre-trained Model is loaded once at backend startup.

LanguageModelTrainer uses it to detect word languages.

Backend fetches URL content, tokenizes it, filters stopwords, and counts words.

Word statistics are saved/updated in MySQL.

Frontend fetches JSON results and displays them.

---
