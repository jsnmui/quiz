<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>User Management</title>
    <style>
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .active { color: green; }
        .suspended { color: red; }
        .pagination {
            text-align: center;
            margin: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: blue;
        }
    </style>
</head>
<body>

<h2 style="text-align: center;">Admin - User Management</h2>

<table>
    <thead>
    <tr>
        <th>User ID</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.firstname} ${user.lastname}</td>
            <td>${user.email}</td>
            <td>
                <c:choose>
                    <c:when test="${user.active}">
                        <span class="active">Active</span>
                    </c:when>
                    <c:otherwise>
                        <span class="suspended">Suspended</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${user.active}">
                        <form action="/admin/user-management/suspend" method="post">
                            <input type="hidden" name="userId" value="${user.userId}" />
                            <button type="submit">Suspend</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="/admin/user-management/activate" method="post">
                            <input type="hidden" name="userId" value="${user.userId}" />
                            <button type="submit">Activate</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="/admin/user-management?page=${i}">${i}</a>
    </c:forEach>
</div>

</body>
</html>
