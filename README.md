# LGVB Digital Banking System

This document provides instructions for setting up and running the LGVB Digital Banking System, a Java-based digital banking application.

## Prerequisites

Ensure the following software is installed:

* **Java Development Kit (JDK):** Version 11 or higher
* **Maven:** For building and running the project
* **MySQL Server:** Database for the application
* **IDE (optional):** IntelliJ IDEA, Eclipse, or NetBeans

## Setup Instructions

1. **Open a terminal in the project root folder:**

   * **Windows:** Right-click the project folder → **Open in Terminal**
   * **Mac/Linux:** Open Terminal → navigate to the project folder:

     ```bash
     cd /path/to/lgvb-project
     ```

2. **Log in to MySQL as root:**

   ```bash
   mysql -u root -p
   ```

   Enter your MySQL root password when prompted.

3. **Run the setup script:**
   Once logged in, run the SQL script included in the project to create the database, tables, and default user:

   ```sql
   SOURCE src/main/resources/sql/setup.sql;
   ```

   > Make sure you are in the project root folder when running this command.

4. **Verify the database:**

   ```sql
   SHOW DATABASES;
   USE lgvb_banking;
   SHOW TABLES;
   ```

## Configuration

Check the database connection in `src/main/resources/app.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/lgvb_banking
db.username=lgvb_app
db.password=lgvb_password123
```

> Update the password here if you changed it during setup.

## Running the Application

**Using Maven:**

```bash
mvn clean compile exec:java -Dexec.mainClass="com.leshka_and_friends.lgvb.Main"
```

**Using an IDE:**

1. Import the project as a Maven project.
2. Locate the `Main` class in `src/main/java/com/leshka_and_friends/lgvb/`.
3. Run the `Main` class.

The application will start and connect to the database automatically.

## Troubleshooting

* **Cannot connect to MySQL:** Make sure MySQL server is running and credentials are correct.
* **Database already exists error:** The setup script drops the old database, so rerunning it will overwrite existing data.

## Project Structure

```
lgvb-digital-banking/
│
├─ src/main/java/             # Java source code
├─ src/main/resources/        # SQL scripts, properties, and assets
├─ pom.xml                    # Maven project file
└─ README.md                  # This file
```
