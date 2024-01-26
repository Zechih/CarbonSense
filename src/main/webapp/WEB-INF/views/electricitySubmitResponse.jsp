<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="headerUser.jsp" />
	<div class="title">
		<h1>ELECTRICITY COMSUMPTION SUBMISSION</h1>
	</div>
	<div class="modal fade show" id="staticBackdropLive"
		data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLiveLabel" style="display: block;"
		aria-modal="true" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="staticBackdropLiveLabel">Message</h1>
				</div>
				<div class="modal-body">
					<p><c:out value="${message}"></c:out></p>
				</div>
				<div class="modal-footer">
					<a href="${pageContext.request.contextPath}/electricityConsumption"><button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button></a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>