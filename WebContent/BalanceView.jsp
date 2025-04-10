<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="UTF-8">

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Balance</title>
<style>
    body {
        margin: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(to right, #74ebd5, #ACB6E5);
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .card {
        background-color: white;
        padding: 40px 60px;
        border-radius: 16px;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
        text-align: center;
    }

    h2 {
        color: #2c3e50;
        margin-bottom: 20px;
    }

    .balance {
        font-size: 24px;
        color: #27ae60;
        font-weight: bold;
    }
</style>
</head>
<body>
    <div class="card">
        <h2>Your Current Account Balance:</h2>
        <div class="balance">
            <%
                session = request.getSession(false);
                if (session != null && session.getAttribute("bal") != null) {
                    out.println("₹ " + session.getAttribute("bal"));
                } else {
                    out.println("Balance information is not available.");
                }
            %>
        </div>
    </div>
</body>
</html>
