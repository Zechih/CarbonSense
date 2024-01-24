<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>Registration Page</title>
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
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
        <div class="left-section">
            <h2><img src="${pageContext.request.contextPath}/resources/images/mbiplogo.png" alt="MBIP Logo" width="380px"></h2>
            <h3>PERSONAL DETAILS</h3>
            <div class="input-wrapper">
                <input type="text" name="icno" placeholder="IC/Passport No." required>
            </div>
            <div class="input-wrapper">
                <div class="name-group">
                    <input type="text" name="fname" placeholder="First Name" required>
                    <input type="text" name="lname" placeholder="Last Name" required>
                </div>
            </div>
            <div class="input-wrapper">
                <div class="radio-group">
                    <label>
                        <input type="radio" name="gender" value="male" required> Male
                    </label>
                    <label>
                        <input type="radio" name="gender" value="female" required> Female
                    </label>
                </div>
            </div>
            <div class="input-wrapper">
                <input type="email" name="email" placeholder="Email Address" required>
            </div>
            <div class="input-wrapper">
                <div class="select"></div>
                <select name="occupation" id="occupation">
                    <option value="Occupation">Occupation</option>
                    <option value="Private">Private</option>
                    <option value="Government">Government</option>
                    <option value="Unemployed">Unemployed</option>
                    <option value="Retired">Retired</option>
                </select>
            </div>
            <div class="input-wrapper">
                <input type="password" name="password" value="password" placeholder="Password" required>
            </div>
            <div class="input-wrapper">
                <input type="password" name="cfmpassword" value="cfmpassword" placeholder="Confirmed Password" required>
            </div>
            <div class="input-wrapper">
                <div class="radio-group">
                    <label>
                        MBIP Staff <input type="radio" name="staff" value="yes" onclick="toggleMbipStaffId()" required> Yes
                    </label>
                    <label>
                        <input type="radio" name="staff" value="no" onclick="toggleMbipStaffId()" required checked> No
                    </label>
                </div>
            </div>
        
            <div class="input-wrapper mbip-staff-id">
                <input type="text" name="mbipStaffId" id="mbip-staff-id" placeholder="MBIP Staff ID">
            </div>
        </div>        
        <div class="right-section">
            <h3>LOCATION DETAILS</h3>
            <div class="input-wrapper">
                <textarea id="address" name="address" rows="4" cols="50" placeholder="Address in full"></textarea>
            </div>
            <div class="input-wrapper">
                <div class="select"></div>
                <select name="category" id="category">
                    <option value="Category">Category</option>
                    <option value="A1">A1: Housing (High Rise)</option>
                    <option value="A2">A2: Housing (Landed)</option>
                    <option value="A3">A3: Institution</option>
                    <option value="A4">A4: MBIP Staff and Divisions</option>
                </select>
            </div>
            <br>
            <div class="proof-document-outer">
                <div class="proof-document-inner">
                    <label for="proof-document" id="proof-document-box">
                        <div id="proof-document-icon"><img src="${pageContext.request.contextPath}resources/images/upload2.png" width="50px" height="50px" alt="upload">
                        </div>
                        <div id="proof-document-text">You can drop or drag your files here</div>
                    </label>
                    <input type="file" name="proofDocument" id="proof-document" accept=".pdf">
                </div>
            </div>
        
            <form id="registrationForm" action="${pageContext.request.contextPath}/register/create" method="post">
                <div>
                    <br>
                    <input type="submit" value="Submit">
                    <br>
                </div>
            </form>
        
            <script>
                function toggleMbipStaffId() {
                    var mbipStaffInput = document.getElementById('mbip-staff-id');
                    var mbipStaffRadio = document.querySelector('input[name="staff"]:checked');
                    var categorySelect = document.getElementById('category');
        
                    if (mbipStaffInput && mbipStaffRadio && categorySelect) {
                        mbipStaffInput.style.display = mbipStaffRadio.value === 'yes' ? 'block' : 'none';
        
                        // Set default category to "A4" if MBIP Staff is selected
                        categorySelect.value = mbipStaffRadio.value === 'yes' ? 'A4' : 'Category';
                    }
                }
        
                function showModal() {
                    document.getElementById("overlay").style.display = "block";
                    document.getElementById("modal").style.display = "block";
                }
        
                function showConfirmationPopup() {
                    showModal();
                }
        
                // Attach an event listener to the forms
                document.getElementById("registrationForm").addEventListener("submit", function (event) {
                    // Prevent the default form submission
                    event.preventDefault();
        
                    // Additional logic for form submission, if needed
        
                    // Show the confirmation popup
                    showConfirmationPopup();
                });
        
                // Initial call to toggleMbipStaffId
                toggleMbipStaffId();
            </script>

        </div>
    </div>
</body>

</html>
