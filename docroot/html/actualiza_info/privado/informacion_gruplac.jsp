<%@include file="../../init.jsp" %>

<portlet:actionURL name="obtenerInformacionPrivada" var="actionForm"></portlet:actionURL>

<form id="formularioRegistro" action="<%=actionForm%>" method="POST">
	<label for="nombre">Nombre</label>
	<input type="text" id="nombre" name="nombre"  required>
	<label for="nombre">N&#250;mero del documento</label>
	<input type="text" id="numeroDocumento" name="numeroDocumento"  required>
	<label for="nombre">Contrase&#243;a</label>
	<input type="password" id="contrasena" name="contrasena"  required>
	<input type="submit" value="Enviar">
</form>