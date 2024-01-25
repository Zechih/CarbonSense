<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	<div class="modal fade show" id="staticBackdropLive"
		data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLiveLabel" style="display: block;"
		aria-modal="true" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="staticBackdropLiveLabel">Error</h1>
				</div>
				<div class="modal-body">
					<p>No submission in this month.</p>
				</div>
				<div class="modal-footer">
					<a href="${pageContext.request.contextPath}/report"><button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button></a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
