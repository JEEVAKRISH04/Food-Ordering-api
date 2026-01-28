🍔 Food Delivery Backend API

> A clean, modular Java backend system for a food delivery platform built with JSP, Servlets, JDBC, MySQL, Maven, and Apache Tomcat, following proper DAO architecture and backend design principles.




---

🚀 Project Overview

This project is a backend-driven food delivery system that handles core features like:

User registration & authentication

Restaurant and menu management

Cart & order processing

Order tracking & history

Payment handling

Role-based features (Admin, Customer, Delivery Boy)


Designed with a strong focus on:

Maintainable code

Layered architecture

Database-driven logic

Industry-style backend practices



---

🧠 Architecture Design

This project follows a layered architecture pattern similar to real-world backend systems:

Client (Browser)
    ↓
JSP (View Layer)
    ↓
Servlets (Controller Layer)
    ↓
DAO Classes (Business & Data Access Layer)
    ↓
Utility (Connection Pooling via DBCP)
    ↓
MySQL Database

📦 Package Structure

com.food
 ┣ dao        → Database logic (UsersDAO, OrdersDAO, etc.)
 ┣ model      → POJO models (Users, Orders, Menu, Cart...)
 ┣ servlet    → Controllers (LoginServlet, SignUpServlet, etc.)
 ┣ utility    → DB Connection Pool (UtilityClass)
 ┣ listener   → Lifecycle cleanup (MySQLCleanupListener)

This separation ensures:

Clean responsibilities

Easy debugging

Scalable design



---

⚙️ Tech Stack

Layer	Technology

Backend	Java (Servlets & JSP)
Database	MySQL
Build Tool	Maven
Server	Apache Tomcat 9
Connection Pool	Apache DBCP2
Frontend	HTML, CSS, JavaScript
IDE	Eclipse
Version Control	Git & GitHub



---
