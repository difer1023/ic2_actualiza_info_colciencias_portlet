<%@include file="../../init.jsp" %>
<portlet:actionURL name="guardarClasificaciones" var="guardarClasificacionesURL" />
<% 
JSONArray productos=(JSONArray) request.getAttribute("productosNoClasificados"); 
String categorias=(String) request.getAttribute("categorias"); 
%>
<div class="box">
   <div class="box-header">
     <h3 class="box-title">Productos no clasificados</h3>
   </div>
   <!-- /.box-header -->
   <div class="box-body no-padding">
     <p>Los siguientes productos no han sido clasificados por Colciencias, sin embargo, para que el sistema tenga una mayor precisión en el cálculo de la clasificación actual del grupo de investigación, puede proporcionar una clasificación de forma manual para cada producto, comprobando de manera manual los requisitos de existencia y calidad para los mismos.</p>
     
     <div class="alert alert-warning alert-dismissible">
       <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
       <h4><i class="icon fa fa-warning"></i> Advertencia:</h4>
       Las clasificaciones que seleccione, serán reflejo de su criterio, y para uso de este aplicativo, no influyen sobre las clasificaciones de los productos en Gruplac, ni tampoco se asegura que la clasificación seleccionada para cada producto sea la misma que pondrá Colciencias en la publicación de resultados de la convocatoria.
     </div>
     
     <table id="tablaProductos" class="table table-striped">
     <thead>
       <tr>
         <th>Nombre producto</th>
         <th>Tipo producto</th>
         <th>Clasificación</th>
       </tr>
     </thead>
     <tbody>
      
       </tbody>
     </table>
   </div>
   <!-- /.box-body -->
   <div class="box-footer">
   	<form id="frmGuardarClasificaciones" action="<%=guardarClasificacionesURL%>" method="post">
   		<input type="hidden" id="clasificaciones" name='<portlet:namespace/>clasificaciones'>
	   	<button id="btnGuardar" class="btn btn-info" type="button">Guardar</button>
   	</form>
   </div>
 </div>
 <!-- /.box -->
 
 <script>
 var productos=JSON.parse('<%=productos.toString().replace("\\\"","\\\\\"")%>');
 var categorias=JSON.parse('<%=categorias%>');
 
 productos.forEach(function(producto){
	 var tiposProducto=getCategoriasPorTipoProducto(producto.c_tipo_producto);
	 var select=$('<select>',{id:producto.k_codigo, class:"select-clasificacion"});
	 select.append('<option value="">Seleccione</option>');
	 tiposProducto.forEach(function(tipoProducto){
		 select.append('<option value="'+tipoProducto.k_codigo+'">'+tipoProducto.n_nombre+'</option>');
	 });
	 var fila=$('<tr>');
	 fila.append('<td>'+producto.n_nombre+'</td>');
	 fila.append('<td><span class="badge bg-red">'+producto.c_tipo_producto+'</span></td>');
	 fila.append($('<td>').append(select));
	 $('#tablaProductos tbody').append(fila);
 });
 
 $('#btnGuardar').click(function(){
	 var productosClasificados=[];
	 $('.select-clasificacion').each(function(){
		if(this.value!==""){
			productosClasificados.push({k_codigo:this.id,
				c_categoria:$(this).children("option:selected").val()});
		}
	 });
	 $('#clasificaciones').val(JSON.stringify(productosClasificados));
	 $('#frmGuardarClasificaciones').submit();
 });
 
 function getCategoriasPorTipoProducto(tipoProducto){
	 return categorias.filter(categoria => categoria.c_tipo_producto===tipoProducto);
 }
 
 </script>

