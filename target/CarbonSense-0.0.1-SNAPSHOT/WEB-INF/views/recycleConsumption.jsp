<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
    <title>Recycle Activity Submission</title>

<style>
    body {
        padding: 130px 0 0 310px;
    }
    
    .navbar {
        height: 55px;
        width: 100%;
        background-color: white;
        width: 100%;
    }
    
    .fixedpos {
        position: fixed;
        top: 0;
        left: 0;
        z-index: 10;
        width: 100%;
    }
    
    .nav-container {
        display: flex;
        flex-wrap: inherit;
        align-items: center;
        justify-content: space-between;
        margin: 0 20px;
    }
    
    .header-image {
        max-height: 40px;
        max-width: 100%;
        height: auto;
        vertical-align: bottom;
    }
    
    .account {
        position: relative;
    }
    
    .accountInfo {
        display: flex;
        align-items: center;
    }
    
    .account:hover {
        cursor: pointer;
    }
    
    .accountInfo p {
        margin: 0;
    }
    
    .accountDropdown {
        list-style: none;
        padding: 0;
        margin: 0;
        position: absolute;
        top: 40px;
        right: 0;
        background-color: white;
        min-width: 150px;
        width: 100%;
        height: 0;
        transition: height 0.3s linear;
        transition-delay: 0.1s;
        overflow: hidden;
        border-radius: 10px;
        box-shadow: rgba(50, 50, 93, 0.25) 0px 6px 12px -2px, rgba(0, 0, 0, 0.3)
            0px 3px 7px -3px;
    }
    
    .account:hover .accountDropdown {
        height: 44px;
        transition: height 0.3s linear;
        transition-delay: 0.1s;
    }
    
    .accountDropdown li {
        height: fit-content;
        display: flex;
    }
    
    .accountDropdown li a {
        text-decoration: none;
        color: black;
        padding: 10px 15px;
    }
    
    .accountDropdown li:hover {
        background-color: whitesmoke;
    }
    
    .offcanvas-container {
        width: 300px;
        height: 100%;
        display: inline-block;
    }
    
    .offcanvas {
        background-color: #AFA8FC;
        --bs-offcanvas-width: 300px;
    }
    
    .nav-link {
        display: inline-flex;
    }
    
    .nav-link {
        margin: 5px 0 5px 0;
    }
    
    .nav-link h4 {
        margin-top: 15px;
    }
    
    .nav-link svg {
        display: flex;
        margin: 10px 10px 10px 0;
        align-items: center;
        justify-content: center;
    }
    
    .title {
        position: fixed;
        max-height: 100 px;
        width: 100%;
        top: 55px;
        left: 300px;
        background-color: #AD6CDF;
    }
    
    .title h1 {
        padding: 15px 0 10px 20px;
    }
</style>
</head>

<body>
    <jsp:include page="headerAdmin.jsp" />
    <div class title = "title">
        <h1>RECYCLE CONSUMPTION SUBMISSION</h1>
    </div>

    <div class ="calendar-image">
        <div class="calendar-container">
			<div class="calendar-header">
				<button id="prevBtn" onclick="prevMonth()">&lt;</button>
				<h2 id="monthYear"></h2>
				<button id="nextBtn" onclick="nextMonth()">&gt;</button>
			</div>
			<div class="calendar-body" id="calendarBody"></div>
			<div class="calendar-date">
				<label for="startDate">Start Date:</label> <input type="text"
					id="startDate" readonly> <label for="endDate">End
					Date:</label> <input type="text" id="endDate" readonly>
			</div>
            <div class="semak-Image">
                <img
                    src="https://p.sda1.dev/14/5112b1c0a8e3cd002b85d059ff45ed2c/image.png"
                    alt="semak bill" width="400" height="270">
            </div>
    </div>

    <form action = "recycleSubmit" method="post" enctype="multipart/form-data">
    <div class="form-container">
		<div class="form-input-value mb-3">
			<div>
				<label for="AccumulatedKg" class="form-label">Accumulated
                total in KG</label> <input type="text" class="form-control"
					id="AccumulatedKg">
			</div>
			<div>
				<label for="recycleRM" class="form-label">Accumulated Total (in RM)
                </label> <input type="text" class="form-control"
					id="recycleRM" required >
			</div>
		</div>
		<div class="form-upload">
			<div>
				<label for="billImage" class="form-label">Upload a picture/
					prove of your collection result</label> <input type="file"
					class="form-control" id="billImage" required>
			</div>
			<div class="submit-btn">
				<button type="button" class="btn btn-secondary">Cancel</button>
				<input type="submit" class="btn btn-success" value="Submit" >
			</div>
		</div>
	</div>
</form>

<script>
    let currentDate = new Date();
 // Get the first day of the month and the total number of days
    const firstDay = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
    const lastDay = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);
    let selectedStartDate = null;
    let selectedEndDate = null;

    function renderCalendar() {
        const calendarBody = document.getElementById('calendarBody');
        const monthYear = document.getElementById('monthYear');
        console.log(monthYear);
        const startDateInput = document.getElementById('startDate');
        const endDateInput = document.getElementById('endDate');
        
        document.getElementById('days').value = lastDay.getDate();

        // Clear previous content
        calendarBody.innerHTML = '';

        // Set the month and year in the header
       	monthYear.textContent = formatMonthYear(currentDate);

        // Create day elements for each day in the month
        for (let i = 1; i <= lastDay.getDate(); i++) {
            const day = document.createElement('div');
            day.classList.add('day');
            day.textContent = i;

            // Highlight selected range
            if (selectedStartDate && selectedEndDate) {
                const currentDay = new Date(currentDate.getFullYear(), currentDate.getMonth(), i);
                if (currentDay >= selectedStartDate && currentDay <= selectedEndDate) {
                    day.classList.add('selected-range');
                }
            }

            // Attach click event to select start and end dates
            day.addEventListener('click', () => handleDateSelection(i));

            calendarBody.appendChild(day);
        }

        // Update the input fields with selected dates
        startDateInput.value = selectedStartDate ? selectedStartDate.toDateString() : '';
        endDateInput.value = selectedEndDate ? selectedEndDate.toDateString() : '';
    }

    function handleDateSelection(day) {
        const selectedDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);

        // If both start and end dates are selected, reset the selection
        if (selectedStartDate && selectedEndDate) {
            selectedStartDate = selectedDate;
            selectedEndDate = null;
        } else if (!selectedStartDate) {
            // If start date is not selected, set it
            selectedStartDate = selectedDate;
        } else {
            // If start date is selected but end date is not, set the end date
            selectedEndDate = selectedDate;
        }

        // If end date is before start date, swap them
        if (selectedStartDate && selectedEndDate && selectedStartDate > selectedEndDate) {
            [selectedStartDate, selectedEndDate] = [selectedEndDate, selectedStartDate];
        }

        // Re-render the calendar
        renderCalendar();
    }

    function prevMonth() {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar();
    }

    function nextMonth() {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar();
    }
    
    function formatMonthYear(date) {
        const options = { month: 'long', year: 'numeric' };
        return new Intl.DateTimeFormat('default', options).format(date);
    }

    // Initial render
    renderCalendar();
</script>
</body>
</html>
