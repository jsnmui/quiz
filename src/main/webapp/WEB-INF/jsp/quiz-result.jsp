<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quiz Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f9f9f9;
        }

        .container {
            background: white;
            padding: 30px;
            max-width: 800px;
            margin: 0 auto;
            border-radius: 8px;
            box-shadow: 0 0 12px rgba(0,0,0,0.1);
        }

        .passed {
            color: green;
            font-weight: bold;
        }

        .failed {
            color: red;
            font-weight: bold;
        }

        .question-box {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ccc;
            border-left: 5px solid #007bff;
        }

        .correct-choice {
            color: green;
            font-weight: bold;
        }

        .wrong-choice {
            color: red;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Quiz Result</h2>

    <p><strong>Quiz:</strong> ${quiz.name}</p>
    <p><strong>User:</strong> ${user.firstname} ${user.lastname}</p>
    <p><strong>Started at:</strong> ${startTime}</p>
    <p><strong>Ended at:</strong> ${endTime}</p>

    <c:choose>
        <c:when test="${score >= 2}">
            <p class="passed">You passed the quiz. Score: ${score} / ${total}</p>
        </c:when>
        <c:otherwise>
            <p class="failed">You failed the quiz. Score: ${score} / ${total}</p>
        </c:otherwise>
    </c:choose>

    <hr/>

    <c:forEach var="result" items="${questionResults}">
        <div class="question-box">
            <p><strong>Q:</strong> ${result.question.description}</p>
            <ul>
                <c:forEach var="choice" items="${result.question.choices}">
                    <li class="
                        ${choice.choiceId == result.correctChoiceId ? 'correct-choice' : ''}
                        ${choice.choiceId == result.selectedChoiceId && choice.choiceId != result.correctChoiceId ? 'wrong-choice' : ''}
                    ">
                            ${choice.description}
                        <c:if test="${choice.choiceId == result.selectedChoiceId}"> (Your Answer)</c:if>
                        <c:if test="${choice.choiceId == result.correctChoiceId}"> (Correct Answer)</c:if>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>

    <br/>
    <a href="/home">← Take Another Quiz</a>
</div>

</body>
</html>
