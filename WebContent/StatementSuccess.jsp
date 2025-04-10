<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Success Page</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(to right, #d4fc79, #96e6a1);
        padding: 50px;
        text-align: center;
        color: #333;
    }

    .container {
        background: #fff;
        padding: 30px;
        margin: auto;
        border-radius: 10px;
        max-width: 400px;
        box-shadow: 0 8px 20px rgba(0,0,0,0.2);
    }

    h2 {
        color: #2e7d32;
        margin-bottom: 20px;
    }

    p {
        font-size: 18px;
        margin: 10px 0;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Transfer Details</h2>
        <%
            session = request.getSession();
            out.println("<p><strong>Sender Account:</strong> " + session.getAttribute("sal") + "</p>");
            out.println("<p><strong>Receiver Account:</strong> " + session.getAttribute("ral") + "</p>");
            out.println("<p><strong>Amount Transferred:</strong> $" + session.getAttribute("al") + "</p>");
        %>
    </div>
</body>
</html>
