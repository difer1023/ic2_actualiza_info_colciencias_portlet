<%@include file="../init.jsp" %>

<portlet:actionURL name="seleccionMetodo" var="actionForm"></portlet:actionURL>

<form id="formularioExtraccion" action="<%=actionForm%>" method="POST">
	<input type="hidden" id="tipoExtraccion" name="tipoExtraccion">
	<input type="button" id="automatico" name="automatico" value="Extracción automática" onclick="enviar('automatico');">
	<input type="button" id="manual" name="manual" value="Extracción manual" onclick="enviar('manual');">
</form>

<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<script type="text/javascript">
function enviar(tipo){
	//saco el valor accediendo a un input de tipo text y name = nombre2 y lo asigno a uno con name = nombre3 
	$("#tipoExtraccion").val(tipo);
	$("#formularioExtraccion").submit();	
}
</script>
