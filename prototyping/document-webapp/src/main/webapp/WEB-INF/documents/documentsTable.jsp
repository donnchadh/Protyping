<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="bookings" "class="section">
<security:authorize ifAllGranted="ROLE_USER">
	<h2>Current Documents</h2>

	<c:if test="${empty documents}">
	<tr>
		<td colspan="7">No documents found</td>
	</tr>
	</c:if>

	<c:if test="${!empty documents}">
	<table class="summary">
		<thead>
			<tr>
				<th>Title</th>
				<th>Description</th>
<%--
				<th>City, State</th>
				<th>Check in Date</th>
				<th>Check out Date</th>
--%>
				<th>Document ID</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="document" items="${documents}">
			<tr>
				<td>${document.title}</td>
				<td>${document.description}</td>
<%--
				<td>${booking.hotel.city}, ${booking.hotel.state}</td>
				<td>${booking.checkinDate}</td>
				<td>${booking.checkoutDate}</td>
--%>
				<td>${document.id}</td>
				<td>
					<a id="cancelLink_${booking.id}" href="deleteBooking?id=${booking.id}">Cancel</a>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.AjaxEventDecoration({
							elementId:"cancelLink_${booking.id}",
							event:"onclick",
							params: {fragments:"bookingsTable"}
						}));
					</script>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
</security:authorize>

</div>