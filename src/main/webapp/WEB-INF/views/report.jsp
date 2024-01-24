<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="now" class="java.util.Date" />
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
</head>

<body>
	<!-- <div id='header'></div> -->
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
				<h2>8 Reports Generated</h2>
			</div>
		</div>
		<div class="report-card">
			<div>
				<svg xmlns="http://www.w3.org/2000/svg" width="60" height="60"
					fill="currentColor" class="bi bi-person-fill-check"
					viewBox="0 0 16 16">
                    <path
						d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m1.679-4.493-1.335 2.226a.75.75 0 0 1-1.174.144l-.774-.773a.5.5 0 0 1 .708-.708l.547.548 1.17-1.951a.5.5 0 1 1 .858.514ZM11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
                    <path
						d="M2 13c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4" />
                </svg>
			</div>
			<div>
				<h2>1290 Participant</h2>
			</div>
		</div>
		<div class="report-card">
			<div>
				<svg xmlns="http://www.w3.org/2000/svg" width="60" height="60"
					fill="currentColor" class="bi bi-globe-asia-australia"
					viewBox="0 0 16 16">
                    <path
						d="m10.495 6.92 1.278-.619a.483.483 0 0 0 .126-.782c-.252-.244-.682-.139-.932.107-.23.226-.513.373-.816.53l-.102.054c-.338.178-.264.626.1.736a.476.476 0 0 0 .346-.027ZM7.741 9.808V9.78a.413.413 0 1 1 .783.183l-.22.443a.602.602 0 0 1-.12.167l-.193.185a.36.36 0 1 1-.5-.516l.112-.108a.453.453 0 0 0 .138-.326ZM5.672 12.5l.482.233A.386.386 0 1 0 6.32 12h-.416a.702.702 0 0 1-.419-.139l-.277-.206a.302.302 0 1 0-.298.52l.761.325Z" />
                    <path
						d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0M1.612 10.867l.756-1.288a1 1 0 0 1 1.545-.225l1.074 1.005a.986.986 0 0 0 1.36-.011l.038-.037a.882.882 0 0 0 .26-.755c-.075-.548.37-1.033.92-1.099.728-.086 1.587-.324 1.728-.957.086-.386-.114-.83-.361-1.2-.207-.312 0-.8.374-.8.123 0 .24-.055.318-.15l.393-.474c.196-.237.491-.368.797-.403.554-.064 1.407-.277 1.583-.973.098-.391-.192-.634-.484-.88-.254-.212-.51-.426-.515-.741a6.998 6.998 0 0 1 3.425 7.692 1.015 1.015 0 0 0-.087-.063l-.316-.204a1 1 0 0 0-.977-.06l-.169.082a1 1 0 0 1-.741.051l-1.021-.329A1 1 0 0 0 11.205 9h-.165a1 1 0 0 0-.945.674l-.172.499a1 1 0 0 1-.404.514l-.802.518a1 1 0 0 0-.458.84v.455a1 1 0 0 0 1 1h.257a1 1 0 0 1 .542.16l.762.49a.998.998 0 0 0 .283.126 7.001 7.001 0 0 1-9.49-3.409Z" />
                </svg>
			</div>
			<div>
				<h2>20 CO2 Reduced</h2>
			</div>
		</div>
	</div>
	<div class="report-container">
		<div class="report-title">
			<h5>
				Reports <span class="badge text-bg-secondary">8</span>
			</h5>
		</div>
		<div class="report-table">
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">MONTH</th>
						<th scope="col">LAST UPDATE</th>
					</tr>
				</thead>
				<fmt:formatDate var="currentMonth" value="${now}" pattern="MM" />
				<fmt:formatDate var="currentYear" value="${now}" pattern="yyyy" />
				<tbody>
					<c:forEach var="i" begin="${smallest_year}" end="${Integer.parseInt(currentYear)}" step="1">
						<c:forEach var="j" begin="1" end="12" step="1">
							<tr>
								<th scope="row">1</th>
								<td><c:out value="${j}"></c:out> <c:out value="${i}"></c:out></td>
								<td><button type="button" class="btn btn-light"
										style="padding: 0; margin: 0;" data-bs-toggle="modal"
										data-bs-target="#exampleModal">
										<svg xmlns="http://www.w3.org/2000/svg" width="23" height="23"
											fill="currentColor" class="bi bi-file-earmark-ruled"
											viewBox="0 0 23 23">
                                    <path
												d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V9H3V2a1 1 0 0 1 1-1h5.5zM3 12v-2h2v2zm0 1h2v2H4a1 1 0 0 1-1-1zm3 2v-2h7v1a1 1 0 0 1-1 1zm7-3H6v-2h7z" />
                                </svg>
									</button> 30-11-2023 20:47:55</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div>
			<nav aria-label="Page navigation example">
				<ul class="pagination">
					<li class="page-item"><a class="page-link" href="#"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
					<li class="page-item"><a class="page-link" href="#">1</a></li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>