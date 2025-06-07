<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }

        h1 {
            color: #d9534f;
        }

        p {
            margin-top: 20px;
            color: #555;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>403 - Access Denied</h1>
    <p>You do not have permission to view this page.</p>
    <a href="/quiz">Return to Home</a>
</div>
</body>
</html>
