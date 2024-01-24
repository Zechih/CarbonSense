<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<head>
	<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<link rel="stylesheet" href="./resources/css/header.css">
</head>

<body>
	<nav class="navbar bg-body-tertiary fixedpos">
		<div class="nav-container">
			<img src="https://www.mbip.gov.my/sites/default/files/styles/panopoly_image_original/public/jhr_mpj-header-bm-mbip_baru.png?itok=1CSnL0Sp"
				alt="header" class="header-image">
		</div>
		<div class="account">
			<div class="accountInfo">
				<p>Username</p>
				<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
					class="bi bi-person-circle" style="margin: 0 10px;" viewBox="0 0 16 16">
					<path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
					<path fill-rule="evenodd"
						d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
				</svg>
				<i class="bi bi-caret-down-fill" style="margin: 0 10px;"></i>
			</div>
			<ul class="accountDropdown">
				<li><a href="login.html"><i class="bi bi-box-arrow-left" style='margin: 0 10px 0 0;'></i>Log Out</a></li>
			</ul>
		</div>
	</nav>


	<div class="offcanvas-container">
		<div class="offcanvas show offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1"
			id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel" style="z-index: auto">
			<div class="offcanvas-header">
				<br>
			</div>
			<div class="offcanvas-body">
				<ul class="nav-menu" style="list-style: none;">
					<li class="nav-item"><a href="./dashboard.html" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="40"
								height="40" fill="currentColor" class="bi bi-grid-1x2-fill" viewBox="0 0 16 16">
								<path
									d="M0 1a1 1 0 0 1 1-1h5a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1zm9 0a1 1 0 0 1 1-1h5a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1h-5a1 1 0 0 1-1-1zm0 9a1 1 0 0 1 1-1h5a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1h-5a1 1 0 0 1-1-1z" />
							</svg>
							<h4>Dashboard</h4>
						</a></li>
					<li class="nav-item"><a class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="50"
								height="50" fill="currentColor" class="bi bi-qr-code-scan" viewBox="0 0 16 16">
								<path
									d="M0 .5A.5.5 0 0 1 .5 0h3a.5.5 0 0 1 0 1H1v2.5a.5.5 0 0 1-1 0zm12 0a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-1 0V1h-2.5a.5.5 0 0 1-.5-.5M.5 12a.5.5 0 0 1 .5.5V15h2.5a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5v-3a.5.5 0 0 1 .5-.5m15 0a.5.5 0 0 1 .5.5v3a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1 0-1H15v-2.5a.5.5 0 0 1 .5-.5M4 4h1v1H4z" />
								<path d="M7 2H2v5h5zM3 3h3v3H3zm2 8H4v1h1z" />
								<path d="M7 9H2v5h5zm-4 1h3v3H3zm8-6h1v1h-1z" />
								<path
									d="M9 2h5v5H9zm1 1v3h3V3zM8 8v2h1v1H8v1h2v-2h1v2h1v-1h2v-1h-3V8zm2 2H9V9h1zm4 2h-1v1h-2v1h3zm-4 2v-1H8v1z" />
								<path d="M12 9h2V8h-2z" />
							</svg>
							<h4>Submit</h4>
						</a>
						<nav class="nav nav-pills flex-column">
							<a class="nav-link ms-3 my-1" href="./waterconsumption.html" style="color: inherit;">
								<h5>Water Consumption</h5>
							</a> <a class="nav-link ms-3 my-1" href="./electricityconsumption.html"
								style="color: inherit;">
								<h5>Electricity Consumption</h5>
							</a> <a class="nav-link ms-3 my-1" href="./recycleactivity.html" style="color: inherit;">
								<h5>Recycle Activity</h5>
							</a>
						</nav>
					</li>
					<li class="nav-item"><a href="profile.html" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="40"
								height="40" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
								<path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
								<path fill-rule="evenodd"
									d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1" />
									<!-- d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225borderArial, Helvetica, sans-serif 5.468 2.37A7 7 0 0 0 8 1" /> -->
							</svg>
							<h4>My Account</h4>
						</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>