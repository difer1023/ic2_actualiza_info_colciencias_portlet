<%@include file="../../init.jsp" %>

<portlet:actionURL name="obtenerInformacionPublica" var="actionForm"></portlet:actionURL>

<form id="formularioRegistro" action="<%=actionForm%>" method="POST">
	<label for="nombreGrupo">Nombre del grupo</label>
	<input type="text" id="nombreGrupo" name="nombreGrupo"  required>
	<label for="enlaceGrupo">Direcci&#243;n URL del grupo</label>
	<input type="text" id="enlaceGrupo" name="enlaceGrupo"  required>
	<input type="submit" value="Enviar">
</form>