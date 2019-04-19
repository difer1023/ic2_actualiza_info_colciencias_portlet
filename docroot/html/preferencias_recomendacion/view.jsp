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
				<h3 class="box-title">Seleccion de objetivo</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<p>En este modulo, podra seleccionar la clasificacion que desea
					alcanzar para la proxima convocatoria de medicion de grupos de
					investigacion</p>

				<form id="formularioSelecccion" action="<%=actionForm%>"
					method="POST">
					<label>Clasificacion objetivo</label> <select
						name="<portlet:namespace/>selectorCategoria" id="selectorCategoria">
						<option value="A1">Categor&#237;a A1</option>
						<option value="A">Categor&#237;a A</option>
						<option value="B">Categor&#237;a B</option>
						<option value="C">Categor&#237;a C</option>
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
