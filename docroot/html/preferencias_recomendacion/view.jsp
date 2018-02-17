<%@include file="../init.jsp" %>

<portlet:actionURL var="actionForm"></portlet:actionURL>

<form id="formularioRegistro" action="<%=actionForm%>" method="POST" onsubmit="validatePassword()">
	<select name="selectorCategoria">
  		<option value="value1">Categor&#237;a A1/option> 
  		<option value="value1">Categor&#237;a A/option> 
  		<option value="value1">Categor&#237;a B/option> 
  		<option value="value1">Categor&#237;a C/option> 
	</select>
</form>