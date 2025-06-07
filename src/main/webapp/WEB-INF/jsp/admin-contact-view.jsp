<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>View Contact Message</title>
    <style>
        .container {
            width: 60%;
            margin: 50px auto;
            border: 1px solid #ccc;
            padding: 30px;
            border-radius: 10px;
            background-color: #fdfdfd;
        }

        .field {
            margin-bottom: 20px;
        }

        .label {
            font-weight: bold;
        }

        .value {
            margin-left: 10px;
        }

        .back-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .back-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Contact Message Details</h2>

    <div class="field">
        <span class="label">Subject:</span>
        <span class="value">${contact.subject}</span>
    </div>

    <div class="field">
        <span class="label">Email:</span>
        <span class="value">${contact.email}</span>
    </div>

    <div class="field">
        <span class="label">Time:</span>
        <span class="value">${contact.time}</span>
    </div>

    <div class="field">
        <span class="label">Message:</span>
        <div class="value" style="margin-top: 10px; white-space: pre-wrap;">${contact.message}</div>
    </div>

    <a href="/admin/contacts" class="back-btn">Back to Messages</a>
</div>

</body>
</html>
