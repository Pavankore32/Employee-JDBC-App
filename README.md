# JDBC Employee App

This Java project is a simple Employee Management Application using **JDBC** to connect with a **MySQL** database. It allows users to **Add, View, Update, and Delete** employee records from the database via a command-line interface.

## Features
- Connects to MySQL using the JDBC driver.
- CRUD operations on the `employee` table:
  - **Add**: Insert new employee records.
  - **View**: List all employees with their details.
  - **Update**: Modify employee information by ID.
  - **Delete**: Remove employee records by ID.
- Handles database connection securely with `allowPublicKeyRetrieval` and `useSSL=false`.

## Requirements
- Java JDK 8 or higher
- MySQL Database
- MySQL Connector/J (JAR file in `lib` folder)

## Database Setup
1. Create a database named `employee_db` in MySQL.
2. Create a table `employee` with the following structure:
```sql
CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    position VARCHAR(50),
    salary DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
To Compile and Run 
javac -cp "lib/mysql-connector-j-9.4.0.jar" src/EmployeeApp.java
java -cp "lib/mysql-connector-j-9.4.0.jar;src" EmployeeApp

Author
Pavan kore 
