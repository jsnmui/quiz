<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Edit Question</title>
</head>
<body>
<h2>Edit Question</h2>

<form method="post" action="/admin/questions/edit">
    <input type="hidden" name="questionId" value="${question.questionId}"/>

    <label>Description:</label><br/>
    <textarea name="description" rows="3" cols="60">${question.description}</textarea><br/><br/>

    <c:forEach var="choice" items="${choices}" varStatus="status">
        <label>Choice ${status.index + 1}:</label>
        <input type="text" name="choice" value="${choice.description}"/>
        <input type="radio" name="correctChoice" value="${choice.choiceId}"
               <c:if test="${choice.correct}">checked</c:if> /> Correct<br/><br/>
    </c:forEach>

    <button type="submit">Update Question</button>
</form>

<br/>
<a href="/admin/questions">← Back to Question Management</a>
</body>
</html>
