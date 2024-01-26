<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<title>Electricity Consumption</title>
<link rel="stylesheet" href="./resources/css/consumption.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="headerUser.jsp" />
	<div class="title">
		<h1>ELECTRICITY COMSUMPTION SUBMISSION</h1>
	</div>
	<div class="calendar-image">
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
		</div>
		<div class="semak-Image">
			<img
				src="https://p.sda1.dev/14/5112b1c0a8e3cd002b85d059ff45ed2c/image.png"
				alt="semak bill" width="400" height="270">
		</div>
	</div>
	<form action="electricitySubmit" method="post" enctype="multipart/form-data">
	<div class="form-container">
		<div class="form-input-value mb-3">
			<div>
				<label for="days" class="form-label">The number of days for
					each month</label> <input type="text"
					class="form-control" id="days" value="" readonly>
			</div>
			<div>
				<label for="proportionalFactor" class="form-label">Proportional
					Factor</label> <input type="text" class="form-control"
					id="proportionalFactor" name="proportionalFactor" required>
			</div>
			<div>
				<label for="waterUsageRM" class="form-label">The current
					electric usage value (in RM)</label> <input type="text"
					class="form-control" id="electricityUsageRM" name="electricityUsageRM" required>
			</div>
			<div>
				<label for="waterUsageM3" class="form-label">The current
					electric usage value (kWh)</label> <input type="text" class="form-control"
					id="electricityUsageM3" name="electricityUsageM3" required>
			</div>
		</div>
		<div class="form-upload">
			<div>
				<label for="billImage" class="form-label">Upload your bill
					according to the selected month</label> <input type="file"
					class="form-control" id="billImage" name="billImage" required>
			</div>
			<input type="hidden" id="userID" name="userID" value="22">
			<div class="submit-btn">
				<button type="button" class="btn btn-secondary">Cancel</button>
				<input type="submit" class="btn btn-success" value="Submit">
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