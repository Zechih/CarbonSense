<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.model.CarbonRegion"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Report</title>
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
	<%
	String[] categorys = {"Housing (High Rise)", "Housing (Landed)", "Institution", "MBIP staff and divisions"};
	pageContext.setAttribute("category", categorys);
	String[] regions = {"Pulai Indah", "Kangkar Pulai", "Pulai Utama", "Sri Pulai", "Taman Universiti", "Mutiara Rini",
			"Lima Kedai", "Nusa Bayu", "Gelang Patah", "Leisure Farm", "Tanjung Kupang", "Medini Iskandar", "Kota Iskandar",
			"Bukit Horizon", "Impian Emas", "Sri Skudai", "Skudai", "Skudai Baru", "Selesa Jaya", "Tun Aminah",
			"Nusa Bestari", "Bukit Indah", "Sutera Utama", "Perling"};

	pageContext.setAttribute("region", regions);
	ArrayList<CarbonRegion> carbonRegionList = (ArrayList<CarbonRegion>) request.getAttribute("carbonRegionList");
	%>
	<div>
		<script type="text/javascript">
			/* pie chart1 */
			google.charts.load('current', {
				packages : [ 'corechart' ]
			}).then(
					function() {
						var data = google.visualization.arrayToDataTable([
								[ 'Activity', 'CarbonFootprint' ],
								[ 'Water Consumption', ${carbonReportAnalysis.totalWaterCarbon} ],
								[ 'Electricity Consumption', ${carbonReportAnalysis.totalElectricityCarbon} ],
								[ 'Recycle Activity', ${carbonReportAnalysis.totalRecycleCarbon} ] ]);

						var options = {
							width : 500,
							height : 300,
							title : 'Carbon Footprint',
							pieHole : 0.4,
							chartArea: {top: 40, bottom: 1},
						};

						var chart = new google.visualization.PieChart(document
								.getElementById('donutchart'));
						chart.draw(data, options);
					});
			
			/* bar chart */
			google.charts.load('current', {packages: ['corechart', 'bar']});
			google.charts.setOnLoadCallback(drawBarColors);
			
			function drawBarColors() {
				var data = google.visualization.arrayToDataTable([
			        ['Region', 'Water', 'Electricity', 'Recycle', { role: 'annotation' } ],
			        <%for (String region : regions) {
	boolean isInList = false;
	for (CarbonRegion carbonRegion : carbonRegionList) {
		if (region.equals(carbonRegion.getRegion())) {
			isInList = true;
			out.println("['" + carbonRegion.getRegion() + "', " + carbonRegion.getWater_Carbon() + ", "
					+ carbonRegion.getElectricity_Carbon() + ", " + carbonRegion.getRecycle_Carbon() + ", ''],");
			break;
		}
	}
	if (!isInList) {
		out.println("['" + region + "', 0, 0, 0, ''], ");
	}
}%>
			      ]);

			      var options = {
			        width: 1000,
			        height: 600,
			        legend: { position: 'top', maxLines: 3 },
			        bar: { groupWidth: '75%' },
			        chartArea: {top: 20, bottom: 70},
			        isStacked: true
			      };
			      
			      var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
			      chart.draw(data, options);
			    }
			
		</script>
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
		<div class="report-details">
			<div id="target">
				<div id="donutchart"></div>
				<div id="chart_div"></div>
				<div>
					<c:forEach items="${category}" var="category" varStatus="loop">
						<h4>
							<c:out value="${category}"></c:out>
						</h4>
						<table class="table" id="environmentTable">
							<thead>
								<tr>
									<th>#</th>
									<th onclick="sortTable(0)">Name</th>
									<th onclick="sortTable(1)">Water Consumption (liters)</th>
									<th onclick="sortTable(2)">Electricity Consumption (kWh)</th>
									<th onclick="sortTable(3)">Recycle Activity (kg)</th>
									<th onclick="sortTable(4)">Total Carbon Emission (kgCO2)</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="count" value="1" />
								<c:set var="inCategory" value="false" />
								<c:forEach items="${applicationList}" var="application"
									varStatus="loop">
									<c:if test="${application.category == category}">
										<tr>
											<td>${count}</td>
											<c:set var="count" value="${count + 1}" />
											<td>${application.name}</td>
											<td>${application.waterConsumption}</td>
											<td>${application.electricityConsumption}</td>
											<td>${application.recycle}</td>
											<td>${application.carbonEmission}</td>
											<c:set var="inCategory" value="true" />
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${inCategory == false}">
							<c:out value="No Submission"></c:out>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div style="padding-top: 40px">
				<button type="button" class="btn btn-danger"
					onclick="CreatePDFfromHTML()">
					<i class="bi bi-file-earmark-arrow-down"></i> PDF
				</button>
			</div>
		</div>
	</div>
</body>
</html>
