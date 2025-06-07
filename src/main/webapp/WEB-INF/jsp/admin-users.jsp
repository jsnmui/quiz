<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>User Management</title>
</head>
<body>
<div class="container">
    <h2>Manage Users</h2>

    <table border="1" cellpadding="10">
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.firstname} ${user.lastname}</td>
                <td>${user.email}</td>
                <td>${user.active ? "Active" : "Suspended"}</td>
                <td>
                    <form method="post" action="/admin/users/status">
                        <input type="hidden" name="userId" value="${user.userId}" />
                        <input type="hidden" name="status" value="${!user.active}" />
                        <button type="submit">
                                ${user.active ? "Suspend" : "Activate"}
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div style="margin-top: 20px;">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="/admin/users?page=${i}">${i}</a>
        </c:forEach>
    </div>
</div>
</body>
</html>
