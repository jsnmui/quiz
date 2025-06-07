<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Question Management</title>
</head>
<body>
<h2 style="text-align: center;">Admin - Question Management</h2>
<div style="text-align: center; margin-bottom: 15px;">
    <a href="/admin/questions/add"><button>Add New Question</button></a>
</div>
<table border="1" style="width: 90%; margin: auto;">
    <thead>
    <tr>
        <th>ID</th>
        <th>Category</th>
        <th>Description</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="q" items="${questions}">
        <tr>
            <td>${q.questionId}</td>
            <td>${q.categoryName}</td>
            <td>${q.description}</td>
            <td>
                <c:choose>
                    <c:when test="${q.active}">Active</c:when>
                    <c:otherwise>Suspended</c:otherwise>
                </c:choose>
            </td>
            <td>
                <form action="/admin/questions/toggle-status" method="post" style="display:inline;">
                    <input type="hidden" name="questionId" value="${q.questionId}" />
                    <button type="submit">
                        <c:choose>
                            <c:when test="${q.active}">Suspend</c:when>
                            <c:otherwise>Activate</c:otherwise>
                        </c:choose>
                    </button>
                </form>
                <a href="/admin/questions/edit?questionId=${q.questionId}">
                    <button>Edit</button>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
