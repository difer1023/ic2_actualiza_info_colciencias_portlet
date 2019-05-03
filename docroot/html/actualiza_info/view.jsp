<%@include file="../init.jsp" %>
<portlet:actionURL name="seleccionMetodo" var="actionForm"></portlet:actionURL>
<div class="row">
<div class="col-md-4 col-centered">
          <div class="box box-solid">
            <div class="box-header with-border">
              <h3 class="box-title">Bienvenido</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            	<p>En este modulo, podr� actualizar la informaci�n que utilizar� la aplicaci�n, es importante que conozca lo siguiente:</p>
              <ul>
                <li>Se le pedir� las credenciales de acceso al aplicativo Gruplac, estas credenciales no ser�n almacenadas por este aplicativo.</li>
                <li>Luego, se realizara una extracci�n de informaci�n referente al grupo de investigaci�n de la plataforma, dicha informaci�n se guardara en la base de datos.</li>
                <li>Luego se realizara el calculo de la clasificaci�n del grupo de investigaci�n para la siguiente convocatoria de medici�n de grupos de investigaci�n.</li>
                <li>Finalmente, podr� ver la informaci�n extra�da en el apartado Inicio y podr� hacer uso de las dem�s funcionalidades.</li>
              </ul>
				<form id="formularioExtraccion" action="<%=actionForm%>" method="POST">
					<input type="hidden" id="tipoExtraccion" name="tipoExtraccion">
					<input type="button" class="btn btn-block btn-primary btn-sm" id="automatico" name="automatico" value="Continuar" onclick="enviar();">
				</form>
            </div>
            <!-- /.box-body -->
            <div class="overlay hide" id="loader">
              <i class="fa fa-spinner fa-spin"></i>
            </div>
          </div>
          <!-- /.box -->
        </div>
</div>

<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<script type="text/javascript">
function enviar(tipo){
	mostrarLoader();
	$("#formularioExtraccion").submit();	
}

function mostrarLoader(){
	document.getElementById("loader").classList.remove("hide");
}
</script>
