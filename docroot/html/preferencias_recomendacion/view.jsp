<%@include file="../init.jsp" %>

<portlet:actionURL var="actionForm"></portlet:actionURL>
<%
User usuario=themeDisplay.getUser(); 
String clasificacionActual=(String) user.getExpandoBridge().getAttribute("clasificacionActual");
String clasificacionObjetivo=(String) user.getExpandoBridge().getAttribute("clasificacionObjetivo");
%>

<div class="row">
	<div class="col-md-4 col-centered">
		<div class="box box-solid">
			<div class="box-header with-border">
				<h3 class="box-title">Selecci�n de objetivo</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<p>En este modulo, podr� seleccionar la clasificaci�n que desea
					alcanzar para la pr�xima convocatoria de medici�n de grupos de
					investigaci�n</p>

				<form id="formularioSelecccion" action="<%=actionForm%>"
					method="POST">
					<label>Clasificaci�n objetivo</label> <select
						name="<portlet:namespace/>selectorCategoria" id="selectorCategoria">
						<option value="A1">Categor�a A1</option>
						<option value="A">Categor�a A</option>
						<option value="B">Categor�a B</option>
						<option value="C">Categor�a C</option>
					</select> 
					<input type="submit" class="btn btn-block btn-primary btn-sm" value="Continuar">
				</form>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
</div>

<script>
	function deshabilitarOpciones(opciones) {
		opciones.forEach(function(opcion) {
			$('#selectorCategoria option[value="' + opcion + '"]').attr(
					'disabled', 'disabled');
		});
	}
<%if(clasificacionActual.equals("A1")){ %>
	deshabilitarOpciones(['A','B','C']);
<%} %>
<%if(clasificacionActual.equals("A")){ %>
	deshabilitarOpciones(['B','C']);
<%} %>
<%if(clasificacionActual.equals("B")){ %>
	deshabilitarOpciones(['C']);
<%} %>

<%if(!clasificacionObjetivo.isEmpty()){ %>
	$('#selectorCategoria').val('<%=clasificacionObjetivo%>');
<%}else{ %>
	$('#selectorCategoria').val('<%=clasificacionActual%>');
<%} %>

</script>
