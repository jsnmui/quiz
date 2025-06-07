<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .login-container {
            background: white;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
        }

        .login-container h2 {
            margin-bottom: 20px;
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-top: 10px;
            color: #444;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .btn {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            background: #4CAF50;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn:hover {
            background: #45a049;
        }

        .register-link {
            margin-top: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <!-- Error message -->
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    <form method="post" action="/login">
        <label for="username">Email</label>
        <input type="text" name="username" required />

        <label for="password">Password</label>
        <input type="password" name="password" required />

        <button type="submit" class="btn">Login</button>
    </form>
    <div class="register-link">
        <p>Don't have an account? <a href="/register">Register here</a></p>
    </div>
</div>
</body>
</html>

