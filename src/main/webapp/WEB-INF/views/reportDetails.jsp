<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<link href="./resources/css/report.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script type="text/javascript"
	src="https://html2canvas.hertzen.com/dist/html2canvas.js"></script>

<script type='text/javascript'>
	$(document).ready(function() {
		$("#header").load("headerAdmin.html");
	});
</script>
<script type="text/javascript">
    /* pie chart1 */
    google.charts.load('current', {
        packages: ['corechart']
    }).then(function () {
        var data = google.visualization.arrayToDataTable([
            ['Activity', 'CarbonFootprint'],
            ['Water Consumption', 1200],
            ['Electricity Consumption', 2000],
            ['Recycle Activity', 1950]
        ]);

        var options = {
            width: 700,
            height: 400,
            title: 'Carbon Footprint',
            pieHole: 0.4,
        };

        var chart = new google.visualization.PieChart(
            document.getElementById('donutchart'));
        chart.draw(data, options);
    });
</script>

<!-- table script -->
<script>
	function sortTable(columnIndex) {
		var table, rows, switching, i, x, y, shouldSwitch;
		table = document.getElementById("environmentTable");
		switching = true;

		while (switching) {
			switching = false;
			rows = table.rows;

			for (i = 1; i < (rows.length - 1); i++) {
				shouldSwitch = false;
				x = rows[i].getElementsByTagName("td")[columnIndex];
				y = rows[i + 1].getElementsByTagName("td")[columnIndex];

				if (columnIndex === 1 || columnIndex === 2 || columnIndex === 4) {
					shouldSwitch = parseFloat(x.innerHTML) < parseFloat(y.innerHTML);
				} else {
					shouldSwitch = x.innerHTML.toLowerCase() < y.innerHTML
							.toLowerCase();
				}

				if (shouldSwitch) {
					rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
					switching = true;
				}
			}
		}
	}
</script>
</head>

<body>
	<!-- <div id='header'></div> -->
	<jsp:include page="headerAdmin.jsp" />
	<div class="title">
		<h1>REPORT</h1>
	</div>

	<div>
		<script>
			//Create PDf from HTML...
			function CreatePDFfromHTML() {

				var HTML_Width = $("#target").width();
				var HTML_Height = $("#target").height();
				var top_left_margin = 15;
				var PDF_Width = HTML_Width + (top_left_margin * 2);
				var PDF_Height = (PDF_Width * 1.5) + (top_left_margin * 2);
				var canvas_image_width = HTML_Width;
				var canvas_image_height = HTML_Height;

				var totalPDFPages = Math.ceil(HTML_Height / PDF_Height) - 1;

				html2canvas($("#target")[0], {
					scale : 5,
					useCORS : true,
				})
						.then(
								function(canvas) {
									var imgData = canvas.toDataURL(
											"image/jpeg", 1.0);
									var pdf = new jsPDF('p', 'pt', [ PDF_Width,
											PDF_Height ]);
									pdf.addImage(imgData, 'JPG',
											top_left_margin, top_left_margin,
											canvas_image_width,
											canvas_image_height);
									for (var i = 1; i <= totalPDFPages; i++) {
										pdf.addPage(PDF_Width, PDF_Height);
										pdf
												.addImage(
														imgData,
														'JPG',
														top_left_margin,
														-(PDF_Height * i)
																+ (top_left_margin * 4),
														canvas_image_width,
														canvas_image_height);
									}
									pdf.save("Your_PDF_Name.pdf");

								});
			}
		</script>
		<div>
			<div id="target">
				<div id="donutchart"></div>
				<div>
					<table class="table" id="environmentTable">
						<thead>
							<tr>
								<th onclick="sortTable(0)">Name</th>
								<th onclick="sortTable(1)">Water Consumption</th>
								<th onclick="sortTable(2)">Electricity Consumption</th>
								<th onclick="sortTable(3)">Recycle Activity</th>
								<th onclick="sortTable(4)">Total Carbon Emission</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${applicationList}" var="application" varStatus="loop">
							<tr>
								<td>${application.name}</td>
								<td>${application.waterConsumption} liters</td>
								<td>${application.electricityConsumption} kWh</td>
								<td>${application.recycle} kg</td>
								<td>${application.carbonEmission} kgCO2</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div>
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Close</button>
				<button type="button" class="btn btn-danger"
					onclick="CreatePDFfromHTML()">
					<i class="bi bi-file-earmark-arrow-down"></i> PDF
				</button>
				<button type="button" class="btn btn-success">
					<i class="bi bi-file-earmark-arrow-down"></i> Excel
				</button>
			</div>
		</div>
	</div>
</body>
</html>