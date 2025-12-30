# University ERP System

## Introduction
This project is a **University ERP System** built using **Java Swing** and **MySQL**.  
It supports **Students**, **Instructors**, and **Admins**, each with separate roles and permissions.

For better security, the system uses **two databases**:
- **Auth DB** – stores usernames, roles, and bcrypt-hashed passwords
- **ERP DB** – stores academic data such as courses, enrollments, grades, and schedules

---

## System Architecture
The application follows a layered architecture:

- **UI Layer** – Java Swing JFrames and panels
- **Service Layer** – business logic (registration rules, grading, maintenance checks)
- **Data Layer** – JDBC interaction with Auth and ERP databases
- **Utility Layer** – bcrypt hashing, transcript export, validation, dialogs

---

## Database Design

### Auth Database
- `username_password` – usernames, bcrypt password hashes, roles, lockout info

### ERP Database
- `students` – student profiles  
- `instructor_name_username` – instructor username-to-name mapping  
- `admins` – admin accounts  
- `courses` – course details, credits, grading weights, seat limits  
- `sections` – course offerings (time, room, instructor, capacity)  
- `enrollments` – student–section mapping (no duplicates)  
- `grades` – quiz, assignment, midsem, endsem marks  
- `maintenance` – ON/OFF maintenance flag  
- `notifications` – system notifications  
- `registration_dates` – registration window  
- `relation1` – role mapping  
- `subjectsxname_instructor` – instructor-course mapping  
- `subjectsxname_students` – student-course mapping  

### Key Relationships
- Every ERP user exists in the Auth DB
- A course can have multiple sections
- Students can enroll only once per section
- Grades link students to a course and section

---

## Features

### Common
- Secure login using bcrypt
- Role-based dashboards
- Clear error and success messages
- Sortable tables

---

### Student Features
- Browse course catalog and sections
- Register for courses (seat, date, duplicate, maintenance checks)
- Drop courses within allowed window
- Auto-generated timetable
- View grades and final results
- Download transcript (CSV and PDF)

---

### Instructor Features
- View assigned sections only
- Enter marks (quiz, assignment, midsem, endsem, project)

**Restrictions**
- Cannot modify other instructors’ sections
- Grade entry disabled during maintenance mode

---

### Admin Features
- Add students and instructors (updates Auth & ERP DB)
- Create courses and sections
- Assign instructors and capacities
- Enable/disable maintenance mode
  - Students: cannot register/drop
  - Instructors: cannot enter grades
  - Admins: full access
- Maintenance banner visible to all users

---

## Role Enforcement & Maintenance Mode
Authorization is enforced in the **service layer**:
- Role verification
- Resource ownership checks
- Maintenance mode validation

Unauthorized actions are blocked with alerts.

---

## Extras
- CSV & PDF transcript export
- Login attempt tracking
- Notification subsystem
- Clean and consistent UI dialogs
## How to Run

### Requirements
- Java 17+
- MySQL

### Steps
1. Import the project
2. Load both SQL files (Auth DB and ERP DB)
3. Update database credentials
4. Run `Main.java`

### Terminal
```bash
javac -cp ".;../lib/*" Main/Main.java
java  -cp ".;../lib/*" Main.Main

