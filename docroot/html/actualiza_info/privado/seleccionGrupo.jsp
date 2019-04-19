<%@include file="../../init.jsp"%>

<portlet:actionURL name="seleccionarGrupoInvestigacion" var="actionForm"></portlet:actionURL>

<%
	ArrayList<String> grupos = (ArrayList) request
			.getAttribute("gruposInvestigacion");
%>
<div class="row">
	<div class="col-md-4 col-centered">
		<div class="box box-solid">
			<div class="box-header with-border">
				<h3 class="box-title">Seleccion de grupo</h3>
			</div>
			<!-- /.box-header -->
			<form id="formularioRegistro" action="<%=actionForm%>" method="POST">
				<div class="box-body">

					<div class="form-group">
						<label for="seleccionGrupo">Seleccione grupo de
							investigacion</label> <select name="seleccionGrupo" id="seleccionGrupo">
							<%
								for (int i = 0; i < grupos.size(); i++) {
							%>
							<option value="<%=i + 1%>"><%=grupos.get(i)%></option>
							<%
								}
							%>
						</select>
					</div>
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<input type="submit" value="Enviar">
				</div>
			</form>
		</div>
		<!-- /.box -->
	</div>
</div>
