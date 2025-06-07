# QuizApp

QuizApp is a Java-based web application that allows users to register, take quizzes, and view their results. Administrators can manage users, quiz questions, and view submitted messages. Built using Spring Boot, JSP, JDBC, and MySQL, it demonstrates a complete full-stack Java web project.

## Features

### User Features

* Register and log in
* Take random quizzes (3 questions per quiz)
* View quiz results
* Submit messages via the Contact Us page (even without logging in)

### Admin Features

* Admin-only dashboard (`/admin-home`)
* User Management:

  * View users with pagination
  * Activate/Suspend user accounts
* Question Management:

  * View all questions
  * Add, edit, activate/suspend questions
* Quiz Result Management:

  * View quiz results (with filters by user/category)
  * See individual quiz result details
* Contact Us Management:

  * View user messages
  * Open full view of message content

## Technologies Used

* Java 17+
* Spring Boot
* JSP + JSTL
* MySQL
* JDBC Template
* Embedded Tomcat

## Getting Started

### Prerequisites

* Java 17+
* Maven
* MySQL

### Database Setup

1. Create a database called `quizdb`
2. Import the schema and any seed data you have

### Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quizdb
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

### Run the Application

```bash
./mvnw spring-boot:run
```

Visit [http://localhost:8080](http://localhost:8080) in your browser.

## Admin Credentials (Sample)

```sql
INSERT INTO user (email, password, firstname, lastname, is_active, is_admin)
VALUES ('admin@example.com', 'admin123', 'Admin', 'User', 1, 1);
```

## Folder Structure

```
src/
 └── main/
     ├── java/com/example/quiz/
     │   ├── controller/
     │   ├── service/
     │   ├── dao/
     │   └── domain/
     └── resources/
         ├── application.properties
         └── templates (JSP files)
```

## Contributions

Pull requests and issues are welcome. Please fork the repository and submit a PR.

## License

This project is open-source. Add a license if required.

---

For more details, contact the project maintainer.
