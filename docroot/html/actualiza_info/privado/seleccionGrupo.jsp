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
				<h3 class="box-title">Selección de grupo</h3>
			</div>
			<!-- /.box-header -->
			<form id="formularioRegistro" action="<%=actionForm%>" method="POST">
				<div class="box-body">

					<div class="form-group">
						<label for="seleccionGrupo" class="control-label">Seleccione grupo de
							investigación</label> <select name="seleccionGrupo" id="seleccionGrupo" class="form-control">
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
					<input class="btn btn-info pull-right" type="submit" value="Enviar" onclick="mostrarLoader()">
				</div>
			</form>
			<div class="overlay hide" id="loader">
              <i class="fa fa-spinner fa-spin"></i>
            </div>
		</div>
		<!-- /.box -->
	</div>
</div>
<script>
function mostrarLoader(){
	document.getElementById("loader").classList.remove("hide");
}
</script>