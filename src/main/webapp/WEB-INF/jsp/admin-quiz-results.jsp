<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Quiz Result Management</title>
    <style>
        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }
        th {
            background-color: #f0f0f0;
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: blue;
        }
    </style>
</head>
<body>
<h2 style="text-align:center">Quiz Results</h2>

<!-- Filter Form -->
<form action="/admin/results" method="get" style="text-align: center; margin-bottom: 20px;">
    Category:
    <select name="categoryName">
        <option value="">All</option>
        <c:forEach var="cat" items="${categoryList}">
            <option value="${cat.name}" <c:if test="${categoryName == cat.name}">selected</c:if>>${cat.name}</option>
        </c:forEach>
    </select>

    User:
    <select name="userName">
        <option value="">All</option>
        <c:forEach var="name" items="${userNameList}">
            <option value="${name}" <c:if test="${userName == name}">selected</c:if>>
                    ${name}
            </option>
        </c:forEach>
    </select>

    <button type="submit">Filter</button>
    <a href="/admin/results?reset=true" style="margin-left: 10px;">Reset</a>
</form>

<!-- Results Table -->
<table>
    <thead>
    <tr>
        <th>Taken Time</th>
        <th>Category</th>
        <th>User</th>
        <th>Questions</th>
        <th>Score</th>
        <th>Details</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="res" items="${results}">
        <tr>
            <td>${res.endTime}</td>
            <td>${res.categoryName}</td>
            <td>${res.userFullName}</td>
            <td>${res.numQuestions}</td>
            <td>${res.score}</td>
            <td><a href="/results/${res.quizId}">View</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Pagination -->
<div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="/admin/results?page=${i}&categoryName=${categoryName}&userName=${userName}">${i}</a>
    </c:forEach>
</div>

</body>
</html>
