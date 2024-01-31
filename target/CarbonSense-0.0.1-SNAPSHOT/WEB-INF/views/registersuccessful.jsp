<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Registration Successful</title>
            <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
            <style>
                body {
                    font-family: Arial, Helvetica, sans-serif;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    height: 100vh;
                    margin: 0;
                }

                #background {
                    background-image: url('${pageContext.request.contextPath}/resources/images/maxresdefault.png');
                    background-size: cover;
                    background-position: center;
                    background-repeat: no-repeat;
                }
            </style>
        </head>

        <body id="background">
            <div class="container">
                <h2>Registration Successful!</h2>
                <p>Name: ${user.firstName} ${user.lastName}</p><br>
                <p>Email: ${user.email}</p><br>
                <p>Address: ${user.address}</p>
                <div class="register-link">
                    <a href="${pageContext.request.contextPath}/login">Click me for Login</a>
                </div>
            </div>
        </body>


        </html>