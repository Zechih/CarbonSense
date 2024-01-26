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
<link rel="stylesheet" href="./resources/css/headerAdmin.css">
</head>

<body>
	<nav class="navbar bg-body-tertiary fixedpos">
		<div class="nav-container">
			<img
				src="https://www.mbip.gov.my/sites/default/files/styles/panopoly_image_original/public/jhr_mpj-header-bm-mbip_baru.png?itok=1CSnL0Sp"
				alt="header" class="header-image">
		</div>
		<div class="account">
			<div class="accountInfo">
				<p>Username</p>
				<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30"
					fill="currentColor" class="bi bi-person-circle"
					style="margin: 0 10px;" viewBox="0 0 16 16">
					<path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
					<path fill-rule="evenodd"
						d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
				</svg>
				<i class="bi bi-caret-down-fill" style="margin: 0 10px;"></i>
			</div>
			<ul class="accountDropdown">
				<li><a href="login.html"><i class="bi bi-box-arrow-left"
						style='margin: 0 10px 0 0;'></i>Log Out</a></li>
			</ul>
		</div>
	</nav>


	<div class="offcanvas-container">
		<div class="offcanvas show offcanvas-start" data-bs-scroll="true"
			data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling"
			aria-labelledby="offcanvasScrollingLabel" style="z-index: auto">
			<div class="offcanvas-header">
				<br>
			</div>
			<div class="offcanvas-body">
				<ul class="nav-menu" style="list-style: none;">
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/dashboardAdmin"
						class="nav-link"><svg xmlns="http://www.w3.org/2000/svg"
								width="40" height="40" fill="currentColor"
								class="bi bi-grid-1x2-fill" viewBox="0 0 16 16">
								<path
									d="M0 1a1 1 0 0 1 1-1h5a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1zm9 0a1 1 0 0 1 1-1h5a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1h-5a1 1 0 0 1-1-1zm0 9a1 1 0 0 1 1-1h5a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1h-5a1 1 0 0 1-1-1z" />
							</svg>
							<h4>Dashboard</h4> </a></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/validation.html"
						class="nav-link"><svg xmlns="http://www.w3.org/2000/svg"
								width="50" height="50" fill="currentColor"
								class="bi bi-check-circle" viewBox="0 0 16 16"
								style="margin-top: 20px;">
								<path
									d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16" />
								<path
									d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05" />
							</svg>
							<h4>Validate Information</h4> </a>
						<nav class="nav nav-pills flex-column">
							<a class="nav-link ms-3 my-1" href=""
								style="color: inherit;">
								<h5>User Validation</h5>
							</a> <a class="nav-link ms-3 my-1" href=""
								style="color: inherit;">
								<h5>Water Consumption Validation</h5>
							</a> <a class="nav-link ms-3 my-1" href="electricityValidation"
								style="color: inherit;">
								<h5>Electricity Consumption Validation</h5>
							</a> <a class="nav-link ms-3 my-1" href=""
								style="color: inherit;">
								<h5>Recycle Activity Validation</h5>
							</a>
						</nav></li>
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/report" class="nav-link"><svg
								xmlns="http://www.w3.org/2000/svg" width="40" height="40"
								fill="currentColor" class="bi bi-file-earmark-text"
								viewBox="0 0 16 16">
								<path
									d="M5.5 7a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1zM5 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5m0 2a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1-.5-.5" />
								<path
									d="M9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.5zm0 1v2A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z" />
							</svg>
							<h4>Reports</h4> </a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>