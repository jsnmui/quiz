<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .register-container {
            background: white;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
        }

        .register-container h2 {
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
        input[type="password"],
        input[type="email"] {
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

        .login-link {
            margin-top: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Create Account</h2>
    <form action="/register" method="post">
        <label for="email">Email</label>
        <input type="email" name="email" required />

        <label for="firstname">First Name</label>
        <input type="text" name="firstname" required />

        <label for="lastname">Last Name</label>
        <input type="text" name="lastname" required />

        <label for="password">Password</label>
        <input type="password" name="password" required />

        <button type="submit" class="btn">Register</button>
    </form>
    <div class="login-link">
        <p>Already have an account? <a href="/login">Login here</a></p>
    </div>
</div>
</body>
</html>
