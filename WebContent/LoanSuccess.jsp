<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Loan Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #fceabb, #f8b500);
            padding: 50px;
            text-align: center;
            color: #333;
        }

        .container {
            background: #fff;
            padding: 30px;
            margin: auto;
            border-radius: 10px;
            max-width: 500px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }

        h1 {
            color: #27ae60;
        }

        p {
            font-size: 18px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Loan Application Submitted!</h1>
        <%
            session = request.getSession(false); // avoid creating session if none exists
            String name = (session != null && session.getAttribute("name") != null) ? (String)session.getAttribute("name") : "Customer";
            String email = (session != null && session.getAttribute("email") != null) ? (String)session.getAttribute("email") : "your registered email";

        %>
        <p>Dear <strong><%= name %></strong>, thank you for showing interest in our loans at <strong>ABCBank</strong>.</p>
        <p>Our executive will contact you soon on your email: <strong><%= email %></strong>.</p>
    </div>
</body>
</html>
