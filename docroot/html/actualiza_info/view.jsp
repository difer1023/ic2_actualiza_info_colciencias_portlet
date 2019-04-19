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
            	<p>En este modulo, podra actualizar la informacion que utilizará la aplicacion, es importante que conozca lo siguiente:</p>
              <ul>
                <li>Se le pedira las credenciales de acceso al aplicativo Gruplac, estas credenciales no serán almacenadas por este aplicativo.</li>
                <li>Luego, se realizara una extraccion de informacion referente al grupo de investigacion de la plataforma, dicha informacion se guardara en la base de datos.</li>
                <li>Luego se realizara el calculo de la clasificacion del grupo de investigacion para la siguiente convocatoria de medicion de grupos de investigacion.</li>
                <li>Finalmente, podra ver la informacion extraida en el apartado Inicio y podra hacer uso de las demas funcionalidades.</li>
              </ul>
				<form id="formularioExtraccion" action="<%=actionForm%>" method="POST">
					<input type="hidden" id="tipoExtraccion" name="tipoExtraccion">
					<input type="button" class="btn btn-block btn-primary btn-sm" id="automatico" name="automatico" value="Continuar" onclick="enviar();">
				</form>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
</div>

<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<script type="text/javascript">
function enviar(tipo){
	$("#formularioExtraccion").submit();	
}
</script>
