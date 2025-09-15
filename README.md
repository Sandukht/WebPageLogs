# WebPageLogs

## Overview

**WebPageLogs** is a Java Spring Boot application that fetches the content of a given web page URL, analyzes the text, and stores the **Top 10 most frequent words** in a database, along with their language. The application also filters out **stopwords** (common words like "the", "и", "և") and numbers to provide meaningful statistics.

### Features

* Fetches text content from any web page URL.
* Detects the primary language of the text (`en` for English, `ru` for Russian, `hy` for Armenian).
* Ignores stopwords and numeric values for accurate word frequency analysis.
* Stores the word statistics in a database (using **Spring Data JPA / Hibernate**).
* Prints the **Top 10 words** in a clean and readable format at the end.

---

## Technologies Used

* **Java 17+**: Main programming language.
* **Spring Boot 3**: Provides application framework and dependency injection.
* **Spring Data JPA**: Used to interact with the database.
* **Hibernate ORM**: Handles database persistence automatically.
* **JSoup**: Fetches and parses HTML content from web pages.
* **Gradle 9**: Build and dependency management.
* **H2/MySQL (or any relational DB)**: Stores the word statistics.
* **Bash scripts**: Automates Gradle build and cleanup.

---

## How It Works

1. **User Input**: The application asks for a URL.
2. **HTML Fetching**: JSoup fetches the web page and removes scripts, styles, and other non-text content.
3. **Text Processing**:

   * Splits the text into words.
   * Converts words to lowercase.
   * Removes stopwords and numeric values.
   * Counts frequency of each word.
4. **Language Detection**: Based on character ranges:

   * English (`a-zA-Z`) → `en`
   * Russian (`а-яА-Я`) → `ru`
   * Armenian (`\u0531-\u0587`) → `hy`
5. **Database Storage**: Top 10 words are stored via Spring Data JPA/Hibernate.
6. **Output**: Prints a clean Top 10 list in the console.

---

## Running the Project

### Prerequisites

* Java 17+ installed.
* Gradle installed (optional, because wrapper is included).

### Steps

#### 1. Build the project

If you want a **clean build**, use your custom script `./grad`:

```bash
#!/usr/bin/env bash
set -e
./gradlew --stop
rm -rf .gradle build
./gradlew clean build
```

Or manually with Gradle:

```bash
./gradlew clean build
```

#### 2. Run the project

After building, you can run the project using:

```bash
java -jar build/libs/WebPageLogs-0.0.1-SNAPSHOT.jar
```

Or, if you have a wrapper command `./up`:

```bash
#!/usr/bin/env bash
set -e
#./gradlew bootRun
java -jar build/libs/WebPageLogs-0.0.1-SNAPSHOT.jar
```

#### 3. Input URL

The program will prompt:

```
Enter a URL (or 'exit' to quit):
```

Example:

```
https://www.linkedin.com/feed/
```

#### 4. Output

After processing, you will see something like:

```
=== Top 10 words for URL: https://www.linkedin.com/feed/ ===
Word: policy, Count: 5, Lang: en
Word: sign, Count: 5, Lang: en
Word: linkedin, Count: 5, Lang: en
Word: email, Count: 4, Lang: en
...
=== Word statistics updated ===
```

---

## Project Structure

```
src/main/java/com/example/webpagelogs/
├── fetcher/WebFetcher.java          # Fetches and cleans HTML text
├── input/Input.java                 # Reads user input
├── db/WordStatistic.java            # Entity representing word statistics
├── db/WordStatisticRepository.java  # Spring Data JPA repository interface
├── db/WordStatisticService.java     # Processes and saves word statistics
└── WebPageLogsApplication.java      # Main Spring Boot application
```

```
src/main/resources/stopwords/
├── stopwords_en.txt                 # English stopwords
├── stopwords_ru.txt                 # Russian stopwords
└── stopwords_hy.txt                 # Armenian stopwords
```

---

## Notes

* Stopwords are loaded from `src/main/resources/stopwords/`.
* Numbers are ignored in word counting.
* Hibernate debug logs can be disabled by adding this to `application.properties`:

```properties
logging.level.org.hibernate.SQL=OFF
```

