<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<html>
<head><title>Add New Question</title></head>
<body>
<h2>Add New Question</h2>
<form action="/admin/questions/add" method="post">
    <label>Category:</label>
    <select name="categoryId">
        <c:forEach var="cat" items="${categories}">
            <option value="${cat.categoryId}">${cat.name}</option>
        </c:forEach>
    </select><br><br>

    <label>Description:</label>
    <input type="text" name="description" required/><br><br>

    <label>Choices:</label><br>
    <c:forEach begin="0" end="2" var="i">
        <input type="text" name="choice" required/><br>
    </c:forEach>
    <label>Correct Choice Index (0–2):</label>
    <input type="number" name="correctChoice" min="0" max="2" required/><br><br>

    <button type="submit">Submit</button>
</form>
</body>
</html>
