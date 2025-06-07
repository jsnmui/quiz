<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Contact Us</title>
    <style>
        form {
            max-width: 400px;
            margin: auto;
            background: #f8f8f8;
            padding: 20px;
            border-radius: 8px;
        }
        input, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 8px;
        }
        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px;
            width: 100%;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h2 style="text-align: center;">Contact Us</h2>

<form method="post" action="/contact-us">
    <label>Subject</label>
    <input type="text" name="subject" required />

    <label>Email Address</label>
    <input type="email" name="email" required />

    <label>Message</label>
    <textarea name="message" rows="4" required></textarea>

    <button type="submit">Submit</button>

    <c:if test="${param.success != null}">
        <p style="color: green;">Message submitted successfully!</p>
    </c:if>
</form>
</body>
</html>
