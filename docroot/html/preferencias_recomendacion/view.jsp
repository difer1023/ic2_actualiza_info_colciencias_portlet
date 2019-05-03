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
				<h3 class="box-title">Selección de objetivo</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body">
				<p>En este modulo, podrá seleccionar la clasificación que desea
					alcanzar para la próxima convocatoria de medición de grupos de
					investigación</p>

				<form id="formularioSelecccion" action="<%=actionForm%>"
					method="POST">
					<label>Clasificación objetivo</label> <select
						name="<portlet:namespace/>selectorCategoria" id="selectorCategoria">
						<option value="A1">Categoría A1</option>
						<option value="A">Categoría A</option>
						<option value="B">Categoría B</option>
						<option value="C">Categoría C</option>
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
