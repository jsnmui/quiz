DROP DATABASE IF EXISTS quizdb;
CREATE DATABASE quizdb;
USE quizdb;

-- User table
CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    is_admin BOOLEAN DEFAULT FALSE
);

-- Category table
CREATE TABLE Category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Question table
CREATE TABLE Question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- Choice table
CREATE TABLE Choice (
    choice_id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    description VARCHAR(255),
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES Question(question_id)
);

-- Quiz table
CREATE TABLE Quiz (
    quiz_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category_id INT,
    name VARCHAR(100),
    time_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    time_end TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- QuizQuestion table
CREATE TABLE QuizQuestion (
    qq_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT,
    question_id INT,
    user_choice_id INT,
    FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id),
    FOREIGN KEY (question_id) REFERENCES Question(question_id),
    FOREIGN KEY (user_choice_id) REFERENCES Choice(choice_id)
);

-- Contact table
CREATE TABLE Contact (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(255),
    message TEXT,
    email VARCHAR(100),
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Quiz_Answer (
    answer_id INT AUTO_INCREMENT PRIMARY KEY,
    quiz_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_choice_id INT NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES Quiz(quiz_id),
    FOREIGN KEY (question_id) REFERENCES Question(question_id),
    FOREIGN KEY (selected_choice_id) REFERENCES Choice(choice_id)
);


-- Insert Categories
INSERT INTO Category (name) VALUES
('Math'), ('Literature'), ('History');

-- MATH Questions and 3 Choices Each
INSERT INTO Question (category_id, description) VALUES
(1, 'What is 2 + 2?'),
(1, 'What is the square root of 16?'),
(1, 'Solve: 5 * 6'),
(1, 'What is 10 divided by 2?'),
(1, 'What is the value of π (approx)?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(1, '3', FALSE), (1, '4', TRUE), (1, '5', FALSE),
(2, '2', FALSE), (2, '4', TRUE), (2, '6', FALSE),
(3, '25', FALSE), (3, '30', TRUE), (3, '35', FALSE),
(4, '5', TRUE), (4, '10', FALSE), (4, '15', FALSE),
(5, '3.14', TRUE), (5, '2.71', FALSE), (5, '1.41', FALSE);

-- LITERATURE Questions and 3 Choices Each
INSERT INTO Question (category_id, description) VALUES
(2, 'Who wrote "Romeo and Juliet"?'),
(2, 'What is the main theme of "1984"?'),
(2, 'Who is the author of "Pride and Prejudice"?'),
(2, 'What is a haiku?'),
(2, 'What genre is "Moby Dick"?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(6, 'Shakespeare', TRUE), (6, 'Orwell', FALSE), (6, 'Dickens', FALSE),
(7, 'Freedom', FALSE), (7, 'Surveillance', TRUE), (7, 'Romance', FALSE),
(8, 'Jane Austen', TRUE), (8, 'George Eliot', FALSE), (8, 'Charlotte Brontë', FALSE),
(9, '5-7-5 Syllable Poem', TRUE), (9, 'Epic', FALSE), (9, 'Sonnet', FALSE),
(10, 'Whaling Novel / Classic', TRUE), (10, 'Science Fiction', FALSE), (10, 'Biography', FALSE);

-- HISTORY Questions and 3 Choices Each
INSERT INTO Question (category_id, description) VALUES
(3, 'Who was the first President of the USA?'),
(3, 'In which year did WWII end?'),
(3, 'Which empire built the Colosseum?'),
(3, 'Who discovered America?'),
(3, 'What was the Cold War?');

INSERT INTO Choice (question_id, description, is_correct) VALUES
(11, 'George Washington', TRUE), (11, 'Abraham Lincoln', FALSE), (11, 'John Adams', FALSE),
(12, '1945', TRUE), (12, '1939', FALSE), (12, '1950', FALSE),
(13, 'Roman Empire', TRUE), (13, 'Greek Empire', FALSE), (13, 'Ottoman Empire', FALSE),
(14, 'Christopher Columbus', TRUE), (14, 'Marco Polo', FALSE), (14, 'Magellan', FALSE),
(15, 'US–USSR political tension', TRUE), (15, 'World War III', FALSE), (15, 'A hot war', FALSE);

-- Insert Users
INSERT INTO User (email, password, firstname, lastname, is_active, is_admin) VALUES
('admin@example.com', 'admin123', 'Admin', 'User', TRUE, TRUE),
('user1@example.com', 'user123', 'John', 'Doe', TRUE, FALSE),
('suspended@example.com', 'suspend123', 'Jane', 'Smith', FALSE, FALSE);

-- Insert a Quiz for user1 (Math Quiz)
INSERT INTO Quiz (user_id, category_id, name, time_start, time_end) VALUES
(2, 1, 'Math Quiz', NOW(), NOW());

-- Link 3 Math Questions (IDs 1–3) with selected answers
INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES
(1, 1, 2),  -- Correct: 4
(1, 2, 5),  -- Correct: 4
(1, 3, 8);  -- Correct: 30
