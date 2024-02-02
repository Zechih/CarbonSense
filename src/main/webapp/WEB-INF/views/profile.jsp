<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
                crossorigin="anonymous">
            <title>Dashboard</title>
            <link rel="stylesheet" href="./resources/css/profile.css">
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <title>Dashboard</title>

            <style>
                body {
                    padding: 130px;
                }

                .infobar {
                    width: 95%;
                    height: 100%;
                    background: rgba(217, 217, 217, 0);
                    border: 3px #002B7F solid;
                    padding: 20px;
                    font-weight: bold;
                    font-size: 20px;
                }

                .regionbox {
                    left: 575px;
                    top: 380px;
                    position: absolute;
                    color: black;
                    font-family: Arial, Helvetica, sans-serif;
                    font-weight: 700;
                    font-size: 30px;
                    word-wrap: break-word
                }

                .region {
                    width: 513px;
                    height: 55px;
                    left: 575px;
                    top: 425px;
                    position: absolute;
                    background: #D9D9D9;
                    border-radius: 33px;
                    border: 1px black solid
                }

                .region-text {
                    position: absolute;
                    top: 50%;
                    left: 25%;
                    transform: translate(-50%, -50%);
                    color: black;
                    font-size: 20px;
                    font-family: Arial, Helvetica, sans-serif;
                    font-weight: 100;
                    margin-left: 5px;
                }
            </style>

        </head>

        <body>
            <jsp:include page="headerUser.jsp" />
            <div class="title">
                <h1>PROFILE</h1>
            </div>

            <div style="width: 80%; height: 80%; position: relative; margin: 10px">
                <div
                    style="width: 550px; height: 0px; left: 550px; top: 12px; position: absolute; transform: rotate(90deg); transform-origin: 0 0; border: 1px black solid">
                </div>
                <div style="width: 800px; height: 350px; left: 0px; top: 0px; position: absolute">
                    <div class="icbox">
                        <div class="ic-text">
                            ${user.ic}
                        </div>
                    </div> <!--IC No Box Heres-->
                    <div class="ic">IC / Passport No.</div>
                    <div class="contactbox">
                        <div class="contact-text">
                            ${user.phoneNumber}
                        </div>
                    </div> <!--Contact No Box Here-->
                    <div class="contact">
                        Contact No.</div>
                    <div class="occupationbox">
                        <div class="occupation-text">
                            ${user.occupation}
                        </div>
                    </div>
                    <div class="occupation">
                        Occupation</div>
                    <div class="emailbox">
                        <div class="email-text">
                            ${user.email}
                        </div>
                    </div>
                    <div class="email">
                        Email Address</div>
                    <div class="fnamebox">
                        <div class="fname-text">
                            ${user.firstName}
                        </div>
                    </div>
                    <div class="fname">
                        First Name</div>
                    <div class="lnamebox">
                        <div class="lname-text">
                            ${user.lastName}
                        </div>
                    </div>
                    <div class="lname">
                        Last Name</div>
                    <div class="addressbox">
                        <div class="address-text">
                            ${user.address}
                        </div>
                    </div>
                    <div class="address">
                        Full Address</div>
                    <div class="categorybox">
                        Category
                    </div>
                    <div class="category">
                        <c:choose>
                            <c:when test="${user.category eq 'A1'}">
                                <div class="category-text">
                                    ${user.category}: Housing (High Rise)
                                </div>
                            </c:when>
                            <c:when test="${user.category eq 'A2'}">
                                <div class="category-text">
                                    ${user.category}: Housing (Landed)
                                </div>
                            </c:when>
                            <c:when test="${user.category eq 'A3'}">
                                <div class="category-text">
                                    ${user.category}: Institution
                                </div>
                            </c:when>
                            <c:when test="${user.category eq 'A4'}">
                                <div class="category-text">
                                    ${user.category}: MBIP Staff and Divisions
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="regionbox">
                        Region
                    </div>
                    <div class="region">
                        <div class="region-text">
                            ${user.region}
                        </div>
                    </div>
                </div>
            </div>

            <a href="${pageContext.request.contextPath}/update" button class="btn btn-primary mt-3"
                data-bs-toggle="modal" data-bs-target="#editProfileModal">Edit Profile</button>
        </body>

        </html>