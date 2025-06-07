<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Contact Us Messages</title>
    <style>
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #f4f4f4;
        }

        .view-button {
            padding: 5px 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .view-button:hover {
            background-color: #0056b3;
        }

        h2 {
            text-align: center;
            margin-top: 30px;
        }
    </style>
</head>
<body>

<h2>Admin - Contact Us Messages</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Subject</th>
        <th>Email</th>
        <th>Time</th>
        <th>Message Preview</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.id}</td>
            <td>${contact.subject}</td>
            <td>${contact.email}</td>
            <td>${contact.time}</td>
            <td>
                <c:choose>
                    <c:when test="${fn:length(contact.message) > 30}">
                        ${fn:substring(contact.message, 0, 30)}...
                    </c:when>
                    <c:otherwise>
                        ${contact.message}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <a href="/admin/contacts/${contact.id}">
                    <button class="view-button">View</button>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
