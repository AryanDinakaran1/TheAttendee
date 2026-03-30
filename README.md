# theAttendee

A JavaFX-based school attendance management application with MySQL integration.

## 🚀 Project Overview

theAttendee is a desktop GUI tool built in Java (JavaFX) for managing student registration, daily attendance, and full attendance history. It was originally developed as a school project and includes tables for:

- `schoolRegister` (master student list)
- `schoolAtt` (current attendance state per student)
- `fullRecords` (attendance history log)
- `fb` (feedback entries)

## 🧩 Features

- Student registration with fields:
  - ID, first name, last name, roll, class, division, email, phone, date
- Attendance recording:
  - Class, division, subject, status (present/absent), review notes
- Edit-in-place for table rows and in-app updates to DB
- Search, sort, and class filtered views
- Delete student + cascade in `schoolRegister` and `schoolAtt`
- Feedback form with rating and message storage
- Multi-scene UI: Welcome, Dashboard, Register, Attendance, Smart Attendance, About, Feedback

## ⚙️ Tech Stack

- Java 11+ (JavaFX 11+)
- JavaFX (UI)
- JDBC + MySQL

## 📁 Repository Structure

- `src/` - source code
  - `Main.java` - primary JavaFX app entrypoint and logic
  - `credentials.java` - DB connection config
  - `tableViewManager.java` - model for student registry rows
  - `attendance.java` - model for attendance rows
  - `finalRecTableViewManager.java` - model for full record view rows
  - `AlertBox.java`, `ErrorBox.java` - helper dialogs
- `README.md` - this file
- `changeForAlert.css`, `changes.css` - UI theming

## 🔌 Database Setup

1. Install MySQL.
2. Create schema and tables (approximate definitions):

```sql
CREATE DATABASE studentsdb;
USE studentsdb;

CREATE TABLE schoolRegister (
  id INT PRIMARY KEY,
  fname VARCHAR(100),
  lname VARCHAR(100),
  roll VARCHAR(50),
  class VARCHAR(20),
  division VARCHAR(10),
  email VARCHAR(150),
  phoneNum BIGINT,
  date VARCHAR(50)
);

CREATE TABLE schoolAtt (
  id INT PRIMARY KEY,
  fname VARCHAR(100),
  lname VARCHAR(100),
  roll VARCHAR(50),
  class VARCHAR(20),
  division VARCHAR(10),
  subject VARCHAR(100),
  attendance VARCHAR(20),
  review TEXT
);

CREATE TABLE fullRecords (
  id INT AUTO_INCREMENT PRIMARY KEY,
  studID INT,
  fname VARCHAR(100),
  lname VARCHAR(100),
  roll VARCHAR(50),
  class VARCHAR(20),
  div VARCHAR(10),
  subject VARCHAR(100),
  attendance VARCHAR(20),
  date VARCHAR(50),
  review TEXT
);

CREATE TABLE fb (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  feedback TEXT,
  rating INT,
  date VARCHAR(50)
);
```

3. Update connection details in `src/credentials.java`:

```java
String url = "jdbc:mysql://<host>:3306/studentsdb";
String user = "attendee";
String pass = "<your_password>"; // set DBPASSWD accordingly
```

## 🛠️ Build and Run

### Option 1: Using command line

1. From project root:

```bash
javac -d out --module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml src/*.java
java --module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml -cp out Main
```

2. Set `DBPASSWD` via environment or source constant.

### Option 2: Using an IDE (recommended)

- Import as Java project
- Add JavaFX SDK library to classpath
- Set VM options (if needed):
  `--module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml`
- Run `Main`.

## ⚠️ Known limitations / TODO

- Password is currently hard-coded / requires manual setup.
- SQL uses raw concatenation (vulnerable to SQL injection) and no prepared statements.
- Database operations assume localhost network and no connection pooling.
- Some tooltips and UI flows are dated (9th-grade-era project style).
- Could improve by adding Docker, server side API, auth, and responsive UI.

## 📝 Contribution

This is a personal project; contributions are welcome as PRs. Focus areas:

- Switch JDBC strings to `PreparedStatement`
- Add migrations and SQL DDL scripts
- Make class and division selectors dynamic
- Add test coverage for DB helper methods

## 📬 Contact

Author: Aryan Dinakaran
Project: theAttendee (School attendance manager)
