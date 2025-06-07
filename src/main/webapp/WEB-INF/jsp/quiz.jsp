<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Take Quiz</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        .question-block {
            border: 1px solid #ccc;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
        }

        .question-block h4 {
            margin-top: 0;
        }

        .choice-label {
            display: block;
            margin-left: 10px;
            margin-bottom: 5px;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
        }
    </style>
</head>

<body>
<h2>Quiz</h2>

<form method="post" action="/quiz/submit">
    <c:forEach var="question" items="${questions}">
        <div class="question-block">
            <h4>Q: ${question.description}</h4>
            <c:forEach var="choice" items="${question.choices}">
                <label class="choice-label">
                    <input
                            type="radio"
                            name="question_${question.questionId}"
                            value="${choice.choiceId}" />
                        ${choice.description}
                </label>
            </c:forEach>
        </div>
    </c:forEach>

    <button type="submit">Submit Quiz</button>
</form>
</body>
</html>
