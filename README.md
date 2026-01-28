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

👨‍💻 Author

Jeeva K
Backend Developer (Java)
Student at CIET | Java | JDBC | MySQL | Servlets | Maven


---

⭐ If you like this project

Give it a star ⭐ on GitHub and feel free to fork it!


---

> This project is built for learning full-stack Java backend architecture and demonstrates real-world development practices.




---

⬇️ Download & Run (Quick Start)

Want to try the project locally? Follow these professional, minimal steps:

1️⃣ Clone the Repository

git clone https://github.com/your-username/food-delivery-backend.git
cd food-delivery-backend

2️⃣ Import into IDE

Open Eclipse / IntelliJ

Choose Import → Existing Maven Project

Select the project folder


3️⃣ Configure Database (MySQL)

Start MySQL Server

Create database:


CREATE DATABASE food_delivery;

Update credentials in:


src/main/resources/application.properties

4️⃣ Build the Project

mvn clean install

5️⃣ Run on Apache Tomcat 9

Add project to Tomcat server

Start the server


6️⃣ Access the Application

Open in browser:

http://localhost:8080/Food-delivery-website/Login.jsp


---

💡 Developer Note

This project is designed to demonstrate real backend engineering concepts such as:

Layered architecture

DAO pattern

Connection pooling

Clean separation of concerns


Ideal for:

Backend practice

Portfolio projects

Interview discussions

Learning enterprise Java fundamentals
