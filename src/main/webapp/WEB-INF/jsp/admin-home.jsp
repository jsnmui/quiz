<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Admin Home</title>
    <style>
        .admin-section {
            margin: 50px auto;
            text-align: center;
        }
        .admin-section button {
            margin: 10px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="admin-section">
    <h2>Welcome, Admin</h2>
    <button onclick="location.href='/admin/user-management'">User Management</button>
    <button onclick="location.href='/admin/results'">Quiz Result Management</button>
    <button onclick="location.href='/admin/contacts'">Contact Us Management</button>
    <button onclick="location.href='/admin/questions'">Question Management</button>
</div>
</body>
</html>
