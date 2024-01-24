<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <title>Login Page</title>
    <link href="resources/login.css" rel="stylesheet">

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            /* Updated to center horizontally */
            height: 100vh;
            margin: 0;
        }

        #background {
            background-image: url('resources/image/maxresdefault.png');
            /* Replace with the actual path to your image */
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
    </style>
</head>

<body id="background">
    <div class="container">
        <h2><img src="resources/image/mbiplogo.png" alt="MBIP Logo" width="450px"></h2>
        <br>
        <div class="input-wrapper">
            <form action="YourLoginServlet" method="post"> 
                <input type="text" name="utmid" placeholder="UTM ID" required>
                <br>
                <input type="password" name="password" placeholder="Password" required>
                <br>
                <input type="submit" value="Login">
            </form>
        </div>
        <br>
        <div class="register-link">
            <a href="registration.jsp">Click me for Register</a>
        </div>
        <br>
    </div>
</body>

</html>
