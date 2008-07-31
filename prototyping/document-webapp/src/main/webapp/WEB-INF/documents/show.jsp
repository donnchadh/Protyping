<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="section">
	<h2>View Document</h2>
</div>

<div class="section">
	<form:form id="document" modelAttribute="document" action="document" method="get">
	<fieldset>
		<div class="field">
			<div class="label">Title:</div>
			<div class="output">${document.title}</div>
		</div>
		<div class="field">
			<div class="label">Description:</div>
			<div class="output">${document.description}</div>
		</div>
<%--
		<div class="field">
			<div class="label">City:</div>
			<div class="output">${hotel.city}</div>
		</div>
		<div class="field">
			<div class="label">State:</div>
			<div class="output">${hotel.state}</div>
		</div>
		<div class="field">
			<div class="label">Zip:</div>
			<div class="output">${hotel.zip}</div>
		</div>
		<div class="field">
			<div class="label">Country:</div>
			<div class="output">${hotel.country}</div>
		</div>
	    <div class="field">
	        <div class="label">Nightly rate:</div>
	        <div class="output">
	        	<spring:bind path="price">${status.value}</spring:bind>
	        </div>
	    </div>
--%>
	    <input type="hidden" name="documentId" value="${document.id}" />
		<div class="buttonGroup">
			<input type="submit" value="Analyse Document"/>&#160;
		</div>
	</fieldset>
	</form:form>
</div>
