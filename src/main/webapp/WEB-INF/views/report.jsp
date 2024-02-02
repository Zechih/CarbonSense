<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="now" class="java.util.Date" />
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
</head>

<body>
	<jsp:include page="headerAdmin.jsp" />
	<div class="title">
		<h1>REPORT</h1>
	</div>
	<div class="report-collection">
		<div class="report-card">
			<div>
				<svg xmlns="http://www.w3.org/2000/svg" width="60" height="60"
					fill="currentColor" class="bi bi-graph-up-arrow"
					viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
						d="M0 0h1v15h15v1H0zm10 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V4.9l-3.613 4.417a.5.5 0 0 1-.74.037L7.06 6.767l-3.656 5.027a.5.5 0 0 1-.808-.588l4-5.5a.5.5 0 0 1 .758-.06l2.609 2.61L13.445 4H10.5a.5.5 0 0 1-.5-.5" />
                </svg>
			</div>
			<div>
				<% int counter = 1; %>
				<h2><b id="counter"></b> Reports Generated</h2>
			</div>
		</div>
	</div>
	<div class="report-container">
		<div class="report-title">
			<h5>
				Reports <span class="badge text-bg-secondary"><b id="counter1"></b></span>
			</h5>
		</div>
		<div class="report-table">
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">MONTH-YEAR</th>
						<th scope="col">REPORT</th>
					</tr>
				</thead>
				<fmt:formatDate var="currentMonth" value="${now}" pattern="MM" />
				<fmt:formatDate var="currentYear" value="${now}" pattern="yyyy" />
				<c:set var="smallest_year" value="${smallest_year }" />
				<c:set var="smallest_month" value="${smallest_month }" />
				<tbody>
					<%
					int smallestYear = (Integer) request.getAttribute("smallest_year");
					int smallestMonth = (Integer) request.getAttribute("smallest_month");
					int currentYear = Integer.parseInt((String) pageContext.getAttribute("currentYear"));
					int currentMonth = Integer.parseInt((String) pageContext.getAttribute("currentMonth"));

					for (int i = currentYear; i >= smallestYear; i--) {
						if (i != currentYear) {
							currentMonth = 12;
						}
						for (int j = currentMonth; j > 0; j--) {
					%>
					<tr>
						<th scope="row"><%=counter%></th>
						<td><%=j%>-<%=i%></td>
						<td><a href="reportDetails?month=<%=j%>&year=<%=i%>">
								<svg xmlns="http://www.w3.org/2000/svg" width="23" height="23"
									fill="currentColor" class="bi bi-file-earmark-ruled"
									viewBox="0 0 23 23">
                            	<path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V9H3V2a1 1 0 0 1 1-1h5.5zM3 12v-2h2v2zm0 1h2v2H4a1 1 0 0 1-1-1zm3 2v-2h7v1a1 1 0 0 1-1 1zm7-3H6v-2h7z" />
	                        </svg>
						</a></td>
					</tr>
					<%
							counter++;
							if (i == smallestYear && j == smallestMonth) {break;}
							}
						}
					%>

				</tbody>
			</table>
		</div>
	</div>
</body>
<script>
	var counterElement = document.getElementById('counter');
	var counterElement1 = document.getElementById('counter1');
	function updateCounter() {
        counterElement.textContent = <%= counter - 1 %>;
        counterElement1.textContent = <%= counter - 1 %>;
    }
    updateCounter();

</script>
</html>