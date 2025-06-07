<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    com.example.quiz.domain.User user = (session != null) ? (com.example.quiz.domain.User) session.getAttribute("user") : null;
    request.setAttribute("user", user);
    Boolean hasOpenQuiz = (session != null) ? (Boolean) session.getAttribute("hasOpenQuiz") : false;
%>

<nav style="
    background: #333;
    color: white;
    padding: 12px 24px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 15px;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1000;
">
    <!-- Show "Home" and "Logout" if user is logged in -->
    <c:if test="${not empty user}">
        <a href="/home" style="color: white; margin-right: 15px;">Home</a>
        <a href="/logout" style="color: white;">Logout</a>
    </c:if>
    <a href="/contact-us" style="color:white; margin-right: 15px;">Contact Us</a>
    <!-- Show "Login" and "Register" if user is NOT logged in -->
    <c:if test="${empty user}">
        <a href="/login" style="color: white; margin-right: 15px;">Login</a>
        <a href="/register" style="color: white;">Register</a>
    </c:if>
</nav>