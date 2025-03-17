# PoisePMS

## Overview
**PoisePMS** is a project management system designed to streamline project workflows by managing records, personnel, and interactions with databases. The system offers robust functionality for creating, reading, updating, and deleting records, along with utilities for input validation and formatting.

## Features
### Package: `database`
- **Create**: Manages the creation of records in the database.
- **DatabaseManager**: Facilitates all interactions with the database.
- **Delete**: Handles deletion of records from the database.
- **Read**: Manages reading records from the database.

### Package: `menu`
- **Menu**: Manages the main menu of the program.

### Package: `records`
- **Architect, Contractor, Customer, Engineer, Manager**: Represent individuals with basic contact details relevant to projects.
- **Person**: A base class representing a person with basic contact details.
- **Project**: Represents a project with detailed financial, timeline, and personnel information.

### Package: `utils`
- **Utils**: Contains helper methods for input validation and formatting.

## Setup Instructions

### Setting Up MySQL Database
1. **Download and Install MySQL**:
   - Download the MySQL Community Server from [here](https://dev.mysql.com/downloads/mysql/).
   - Follow the installation instructions for your operating system.

2. **Start the MySQL Server**:
   - On Windows: Use the MySQL Command Line Client or MySQL Workbench to start the server.
   - On macOS/Linux: Use the terminal to start the MySQL server (e.g., `sudo service mysql start`).

3. **Initialize the Database Using Provided SQL Files**:
   - Locate the `init_database` folder in the project directory.
   - Open your MySQL CLI and ensure you are connected to the `PoisePMS` database:
     ```bash
     mysql -u your_username -p
     USE PoisePMS;
     ```
   - Execute the SQL scripts using the `source` command:
     ```bash
     source path/to/init_database/createTables.sql;
     source path/to/init_database/insertData.sql;
     ```
   - Replace `path/to/` with the actual path to the `init_database` folder.

4. **Verify Database Initialization**:
   - Check that the necessary tables and initial data have been created by running:
     ```sql
     SHOW TABLES;
     SELECT * FROM your_table_name;
     ```

5. **Configure Database Connection in Your Project**:
   - Locate the configuration file (or the `DatabaseManager` class in your project).
   - Update the database connection details:
     ```java
     String url = "jdbc:mysql://localhost:3306/poise_pms";
     String username = "your_username";
     String password = "your_password";
     ```

### Configuring MySQL Connector (`mysql-connector-j-x.y.z.jar`)
To ensure the project connects to MySQL successfully, you need to add the MySQL Connector JAR (`mysql-connector-j-x.y.z.jar`) to your project's classpath. Below are setup instructions for different IDEs:

#### **IntelliJ IDEA**
1. Download the MySQL Connector JAR from [here](https://dev.mysql.com/downloads/connector/j/).
2. Go to **File** → **Project Structure** → **Libraries**.
3. Click **+ (Add)** → **Java** and select the downloaded JAR file.
4. Apply changes and restart IntelliJ if needed.

#### **Eclipse**
1. Right-click your project in the **Package Explorer**.
2. Select **Build Path** → **Configure Build Path**.
3. Under the **Libraries** tab, click **Add External JARs...**.
4. Choose the MySQL Connector JAR and click **Apply and Close**.

#### **VS Code**
1. Ensure you have the **Java Extension Pack** installed.
2. Place the `mysql-connector-j-x.y.z.jar` inside a `lib/` folder within your project.
3. Update your **launch.json** or use the following command to run your project with the JAR:
   ```bash
   java -cp "lib/mysql-connector-j-x.y.z.jar:bin" Main
   ```

## General Project Setup
1. Clone the repository:
   ```
   git clone https://github.com/Kader-the-Coder/PoisePMS.git
   ```
2. Navigate to the project directory:
   ```
   cd PoisePMS
   ```
3. Ensure you have [JDK](https://www.oracle.com/java/technologies/javase-downloads.html) installed.
4. Build the project using your preferred IDE or the command line:
   ```
   javac -d bin src/**/*.java
   ```
5. Run the program:
   ```
   java -cp bin Main
   ```

## Technologies Used
- **Java**: Core programming language.
- **MySQL**: Database management system.
- **Javadoc**: Used to generate documentation.
- **Git**: Version control system.

## Usage
The main menu (`Menu` class) allows users to:
- Add, view, update, and delete records.
- Manage details of personnel and projects.
- Access utility methods for input validation and formatting.

## Contribution
Contributions are welcome! Please fork the repository, make your changes, and submit a pull request.

## Author
Abdul Kader, [GitHub Profile](https://github.com/Kader-the-Coder).
