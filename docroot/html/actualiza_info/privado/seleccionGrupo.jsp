<%@include file="../../init.jsp"%>

<portlet:actionURL name="seleccionarGrupoInvestigacion" var="actionForm"></portlet:actionURL>

<%
	ArrayList<String> grupos = (ArrayList) request
			.getAttribute("gruposInvestigacion");
%>

<form id="formularioRegistro" action="<%=actionForm%>" method="POST">


	<label for="seleccionGrupo">Seleccione grupo de investigacion</label> <select
		name="seleccionGrupo" id="seleccionGrupo">
		<%for (int i=0;i<grupos.size();i++){%>
		<option value="<%=i+1%>"><%=grupos.get(i) %></option>
		<%} %>
	</select> <input type="submit" value="Enviar">
</form>