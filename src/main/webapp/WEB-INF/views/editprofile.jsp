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
            <link rel="stylesheet" href="./resources/css/edit-profile.css">
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

                .categorybox {
                    left: 575px;
                    top: 259px;
                    position: absolute;
                    color: black;
                    font-size: 30px;
                    font-family: Arial, Helvetica, sans-serif;
                    font-weight: 700;
                    word-wrap: break-word
                }

                .category {
                    width: 513px;
                    height: 55px;
                    left: 575px;
                    top: 308px;
                    position: absolute;
                    background: #D9D9D9;
                    border-radius: 33px;
                    border: 1px black solid
                }

                .category-text {
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
                    <div class="occupationbox">
                    </div>
                    <div class="occupation">
                        Occupation</div>
                    <div class="emailbox">
                    </div>
                    <div class="email">
                        Email Address</div>
                    <div class="fname">
                        First Name</div>
                    <div class="lname">
                        Last Name</div>
                    <div class="address">
                        Full Address</div>
                    <div class="categorybox">
                        Category
                    </div>
                </div>
                <form style="width: 800px; height: 350px; left: 0px; top: 0px; position: absolute"
                    action="${pageContext.request.contextPath}/updateProfileProcess" method="post">
                    <div class="ic">IC / Passport No.</div>
                    <div class="input-wrapper">
                        <div class="icbox">
                            <div class="ic-text">
                                <input type="text" id="ic" name="ic" value="${user.ic}" />
                            </div>
                        </div>
                    </div>
                    <div class="contact">Contact No.</div>
                    <div class="input-wrapper">
                        <div class="contactbox">
                            <input type="text" id="phoneNumber" name="phoneNumber" class="contact-text"
                                value="${user.phoneNumber}" />
                        </div>
                    </div>
                    <div class="occupationbox">
                        <select name="occupation" id="occupation">
                            <option value="Occupation">Occupation</option>
                            <option value="Private" ${user.occupation eq 'Private' ? 'selected' : '' }>Private
                            </option>
                            <option value="Government" ${user.occupation eq 'Government' ? 'selected' : '' }>
                                Government
                            </option>
                            <option value="Unemployed" ${user.occupation eq 'Unemployed' ? 'selected' : '' }>
                                Unemployed
                            </option>
                            <option value="Retired" ${user.occupation eq 'Retired' ? 'selected' : '' }>Retired
                            </option>
                        </select>
                    </div>
                    <div class="input-wrapper">
                        <div class="emailbox">
                            <input type="email" id="email" name="email" class="email-text" value="${user.email}" />
                        </div>
                    </div>
                    <div class="input-wrapper-name">
                        <div class="fnamebox">
                            <input type="text" id="firstName" name="firstName" class="fname-text"
                                value="${user.firstName}" />
                        </div>
                        <div class="lnamebox">
                            <input type="text" id="lastName" name="lastName" class="lname-text"
                                value="${user.lastName}" />
                        </div>
                    </div>
                    <div class="addressbox">
                        <textarea id="full_address" name="address" class="address-text"
                            style="height: 160px;width: 450px;">${user.address}</textarea>
                    </div>
                    <div class="category">
                        <select name="category" id="category">
                            <option value="Category">Category</option>
                            <option value="A1" ${user.category eq 'A1' ? 'selected' : '' }>A1: Housing (High Rise)
                            </option>
                            <option value="A2" ${user.category eq 'A2' ? 'selected' : '' }>A2: Housing (Landed)
                            </option>
                            <option value="A3" ${user.category eq 'A3' ? 'selected' : '' }>A3: Institution</option>
                            <option value="A4" ${user.category eq 'A4' ? 'selected' : '' }>A4: MBIP Staff and
                                Divisions</option>
                        </select>
                    </div>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Update Profile</button>
            </form>
            </div>

        </body>

        </html>