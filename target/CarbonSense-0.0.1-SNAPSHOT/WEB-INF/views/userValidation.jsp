<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="ISO-8859-1">
			<title>User Account Validation</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
				integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
				crossorigin="anonymous">
			<link href="./resources/css/report.css" rel="stylesheet">
			<link rel="stylesheet"
				href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
				integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
				crossorigin="anonymous"></script>
		</head>

		<body>
			<jsp:include page="headerAdmin.jsp" />
			<div class="title">
				<h1>User Account Validation</h1>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">ID</th>
						<th scope="col">First Name</th>
						<th scope="col">Last Name</th>
						<th scope="col">IC/Passport Number</th>
						<th scope="col">Email</th>
						<th scope="col">Phone Number</th>
						<th scope="col">Region</th>
						<th scope="col">Proof</th>
						<th scope="col">Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${user}" var="user" varStatus="loop">
						<tr>
							<th scope="row">${loop.index + 1}</th>
							<td>${user.userId}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>${user.ic}</td>
							<td>${user.email}</td>
							<td>${user.phoneNumber}</td>
							<td>${user.region}</td>
							<td><button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal"
									data-bs-target="#Modal${user.userId}">
									Image
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
										class="bi bi-box-arrow-up-right" viewBox="0 0 16 16">
										<path fill-rule="evenodd"
											d="M8.636 3.5a.5.5 0 0 0-.5-.5H1.5A1.5 1.5 0 0 0 0 4.5v10A1.5 1.5 0 0 0 1.5 16h10a1.5 1.5 0 0 0 1.5-1.5V7.864a.5.5 0 0 0-1 0V14.5a.5.5 0 0 1-.5.5h-10a.5.5 0 0 1-.5-.5v-10a.5.5 0 0 1 .5-.5h6.636a.5.5 0 0 0 .5-.5" />
										<path fill-rule="evenodd"
											d="M16 .5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0 0 1h3.793L6.146 9.146a.5.5 0 1 0 .708.708L15 1.707V5.5a.5.5 0 0 0 1 0z" />
									</svg>
								</button></td>
							<td>${user.status}</td>
						</tr>

						<!-- Modal -->
						<div class="modal fade" id="Modal${user.userId}" tabindex="-1"
							aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<h1 class="modal-title fs-5" id="exampleModalLabel">ID#${user.userId}</h1>
										<button type="button" class="btn-close" data-bs-dismiss="modal"
											aria-label="Close"></button>
									</div>
									<div class="modal-body">
										<h5>Image</h5>
										<img width="500px"
											src="data:image/*;base64,${Base64.getEncoder().encodeToString(user.addressProof)}" />
										<h5>Name: ${user.firstName} ${user.lastName}</h5>
										<h5>Region: ${user.region}</h5>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Close</button>
										<a href="userValidationDelete?userId=${user.userId}"><button type="button"
												class="btn btn-danger">Delete</button></a>
										<a href="userValidationDisapprove?userId=${user.userId}"><button type="button"
												class="btn btn-primary">Disapprove</button></a>
										<a href="userValidationApprove?userId=${user.userId}"><button type="button"
												class="btn btn-success">Approve</button></a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
			</table>
		</body>

		</html>