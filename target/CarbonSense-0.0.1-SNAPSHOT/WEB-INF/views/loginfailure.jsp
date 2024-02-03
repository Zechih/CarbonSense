<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
            <meta charset="ISO-8859-1">
            <title></title>

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
                    background-image: url('${pageContext.request.contextPath}/resources/images/maxresdefault.png');
                    /* Replace with the actual path to your image */
                    background-size: cover;
                    background-position: center;
                    background-repeat: no-repeat;
                }
            </style>

        </head>

        <body id="background">
            <div class="container">
                <div class="modal fade show" id="staticBackdropLive" data-bs-backdrop="static" data-bs-keyboard="false"
                    tabindex="-1" aria-labelledby="staticBackdropLiveLabel" style="display: block;" aria-modal="true"
                    role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="staticBackdropLiveLabel">Message</h1>
                            </div>
                            <div class="modal-body">
                                <p>
                                    <c:out value="${message}"></c:out>
                                </p>
                            </div>
                            <div class="modal-footer">
                                <a href="${pageContext.request.contextPath}/login"><button type="button"
                                        class="btn btn-secondary" data-bs-dismiss="modal">Close</button></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>