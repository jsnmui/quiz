<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Home - Quiz App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 80px 40px 40px;
            background-color: #f4f4f4;
        }

        h2 {
            color: #333;
            margin-top: 40px;
        }

        .category-list {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }

        .category-card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            width: 200px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
            text-align: center;
        }

        .category-card button {
            margin-top: 10px;
            padding: 8px 16px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .category-card button:hover {
            background: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        a.view-link {
            color: #007bff;
            text-decoration: none;
        }

        a.view-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Available Quiz Categories</h2>
<div class="category-list">
    <c:forEach var="category" items="${categories}">
        <div class="category-card">
            <strong>${category.name}</strong>
            <form method="post" action="/quiz/start">
                <input type="hidden" name="categoryId" value="${category.categoryId}" />
                <button type="submit">Start Quiz</button>
            </form>
        </div>
    </c:forEach>
    <c:if test="${hasOngoingQuiz}">
        <a href="/quiz/take" class="btn">Resume Quiz</a>
    </c:if>
</div>

<hr/>

<h2>Your Past Quizzes</h2>
<table border="1" cellpadding="8">
    <tr>
        <th>Quiz Name</th>
        <th>Start Time</th>
        <th>View</th>
    </tr>
    <c:forEach var="quiz" items="${quizHistory}">
        <tr>
            <td>${quiz.name}</td>
            <td>${quiz.timeStart}</td>
            <td><a href="/results/${quiz.quizId}">View</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
