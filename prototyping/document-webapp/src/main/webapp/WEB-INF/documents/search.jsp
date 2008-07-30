<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="heading" class="section">
	<h2>Document Results</h2>
</div>
<p>
<a id="changeSearchLink" href="index?searchString=${searchCriteria.searchString}&pageSize=${searchCriteria.pageSize}">Change Search</a>
<script type="text/javascript">
	Spring.addDecoration(new Spring.AjaxEventDecoration({
		elementId: "changeSearchLink",
		event: "onclick",
		popup: true,
		params: {fragments: "documentSearchForm"}		
	}));
</script>
</p>
<div id="documentResults" class="section">
<c:if test="${not empty documents}">
	<table class="summary">
		<thead>
			<tr>
				<th>Title</th>
				<th>Description</th>
<%--
				<th>City, State</th>
				<th>Zip</th>
--%>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="hotel" items="${hotels}">
				<tr>
					<td>${document.title}</td>
					<td>${document.description}</td>
<%--
					<td>${hotel.city}, ${hotel.state}, ${hotel.country}</td>
					<td>${hotel.zip}</td>
--%>
					<td><a href="show?id=${document.id}">View Document</a></td>
				</tr>
			</c:forEach>
			<c:if test="${empty documents}">
				<tr>
					<td colspan="3">No documents found</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<div class="buttonGroup">
		<c:if test="${searchCriteria.page > 0}">
			<a id="prevResultsLink" href="search?searchString=${searchCriteria.searchString}&pageSize=${searchCriteria.pageSize}&page=${searchCriteria.page - 1}">Previous Results</a>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.AjaxEventDecoration({
					elementId: "prevResultsLink",
					event: "onclick",
					params: {fragments: "body"}
				}));
			</script>
		</c:if>
		<c:if test="${not empty hotels && fn:length(hotels) == searchCriteria.pageSize}">
			<a id="moreResultsLink" href="search?searchString=${searchCriteria.searchString}&pageSize=${searchCriteria.pageSize}&page=${searchCriteria.page + 1}">More Results</a>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.AjaxEventDecoration({
					elementId: "moreResultsLink",
					event: "onclick",
					params: {fragments: "body"}		
				}));
			</script>
		</c:if>		
	</div>
</c:if>
</div>	

