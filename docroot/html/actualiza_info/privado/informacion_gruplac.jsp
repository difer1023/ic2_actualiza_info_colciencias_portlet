<%@include file="../../init.jsp"%>

<portlet:actionURL name="obtenerInformacionPrivada" var="actionForm"></portlet:actionURL>
<div class="row">
	<div class="col-md-6 col-centered">
		<div class="box box-solid">

			<div class="box-body">
				<form id="formularioRegistro" action="<%=actionForm%>" method="POST" class="form-horizontal">
					<div class="form-group">
						<label for="nacionalidad" class="col-sm-2 control-label">Nacionalidad</label>
						<div class="col-sm-10">
							<select name="nacionalidad" id="nacionalidad"
								class="form-control">
								<option value="C">Colombiana</option>
								<option value="N">Paname&#243;a</option>
								<option value="B">Brasile&#243;a</option>
								<option value="H">Chilena</option>
								<option value="U">Cubana</option>
								<option value="Q">Ecuatoriana</option>
								<option value="M">Mexicana</option>
								<option value="R">Peruana</option>
								<option value="P">Portugesa</option>
								<option value="V">Venezolana</option>
								<option value="E">Extranjero - otra</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="paisNacimiento" class="col-sm-2 control-label">Pa&#237;s
							de nacimiento</label>
						<div class="col-sm-10">
							<select name="paisNacimiento" id="paisNacimiento"
								class="form-control">
								<option value="COL">Colombia</option>
								<option value="AFG">Afganistán</option>
								<option value="ALB">Albania</option>
								<option value="RFA">Alemania</option>
								<option value="AND">Andorra</option>
								<option value="ANG">Angola</option>
								<option value="ATC">Antártica</option>
								<option value="ANB">Antigua y Barbuda</option>
								<option value="AHL">Antillas Neerlandesas</option>
								<option value="ARA">Arabia Saudita</option>
								<option value="ARL">Argelia</option>
								<option value="ARG">Argentina</option>
								<option value="ARM">Armenia</option>
								<option value="AUS">Australia</option>
								<option value="AUT">Austria</option>
								<option value="AZE">Azerbaiyán</option>
								<option value="BHS">Bahamas</option>
								<option value="BGD">Bangladés</option>
								<option value="BRB">Barbados</option>
								<option value="BAR">Baréin</option>
								<option value="BEL">Bélgica</option>
								<option value="BLZ">Belice</option>
								<option value="BEN">Benín</option>
								<option value="BER">Bermudas</option>
								<option value="BEA">Bielorrusia</option>
								<option value="BIR">Birmania</option>
								<option value="BOL">Bolivia</option>
								<option value="BOS">Bosnia-Herzegovina</option>
								<option value="BOT">Botsuana</option>
								<option value="BRA">Brasil</option>
								<option value="IOT">Britsh Indian Ocean</option>
								<option value="BRN">Brunéi</option>
								<option value="BUL">Bulgaria</option>
								<option value="BKF">Burkina Faso</option>
								<option value="BUR">Burundi</option>
								<option value="BUT">Bután</option>
								<option value="CBV">Cabo Verde</option>
								<option value="CBJ">Camboya</option>
								<option value="CAM">Camerún</option>
								<option value="CAN">Canadá</option>
								<option value="CAT">Catar</option>
								<option value="CHA">Chad</option>
								<option value="CHL">Chile</option>
								<option value="CHN">China</option>
								<option value="CHP">Chipre</option>
								<option value="COL">Colombia</option>
								<option value="COM">Comoras</option>
								<option value="CRN">Corea del Norte</option>
								<option value="CRS">Corea del Sur</option>
								<option value="CMF">Costa de Marfil</option>
								<option value="CRC">Costa Rica</option>
								<option value="CRO">Croacia</option>
								<option value="CUB">Cuba</option>
								<option value="DIN">Dinamarca</option>
								<option value="DON">Dominica</option>
								<option value="EQU">Ecuador</option>
								<option value="EGI">Egipto</option>
								<option value="ELS">El Salvador</option>
								<option value="EAU">Emiratos Árabes Unidos</option>
								<option value="ERI">Eritrea</option>
								<option value="ESC">Escocia</option>
								<option value="SVK">Eslovaquia</option>
								<option value="SVN">Eslovenia</option>
								<option value="ESP">España</option>
								<option value="EUA">Estados Unidos</option>
								<option value="EST">Estonia</option>
								<option value="ETP">Etiopía</option>
								<option value="FIL">Filipinas</option>
								<option value="FIN">Finlandia</option>
								<option value="FJI">Fiyi</option>
								<option value="FRA">Francia</option>
								<option value="GAB">Gabón</option>
								<option value="GAL">Gales</option>
								<option value="GAM">Gambia</option>
								<option value="GEO">Georgia</option>
								<option value="GAN">Ghana</option>
								<option value="GIB">Gibraltar</option>
								<option value="GRD">Granada</option>
								<option value="GRE">Grecia</option>
								<option value="GRL">Groenlandia</option>
								<option value="GUM">Guam</option>
								<option value="GUA">Guatemala</option>
								<option value="GFR">Guayana Francesa</option>
								<option value="GDL">Guayana Francesa</option>
								<option value="GNE">Guinea</option>
								<option value="GNQ">Guinea Ecuatorial</option>
								<option value="GNB">Guinea-Bisáu</option>
								<option value="GUI">Guyana</option>
								<option value="HTI">Haití</option>
								<option value="HAW">Hawái</option>
								<option value="HON">Honduras</option>
								<option value="HKG">Hong Kong</option>
								<option value="HUN">Hungría</option>
								<option value="IND">India</option>
								<option value="IDN">Indonesia</option>
								<option value="ING">Inglaterra</option>
								<option value="IRQ">Irak</option>
								<option value="IRA">Irán</option>
								<option value="IRL">Irlanda</option>
								<option value="IRN">Irlanda del Norte</option>
								<option value="CXR">Isla de Navidad</option>
								<option value="PAS">Isla de Pascua</option>
								<option value="FOR">Isla de Taiwán</option>
								<option value="WAK">Isla Wake</option>
								<option value="ISL">Islandia</option>
								<option value="CYM">Islas Caimán</option>
								<option value="CCK">Islas Cocos</option>
								<option value="COK">Islas Cook</option>
								<option value="IFA">Islas Feroe</option>
								<option value="FLK">Islas Malvinas</option>
								<option value="MNP">Islas Marianas del Norte</option>
								<option value="MHL">Islas Marshall</option>
								<option value="MID">Islas Midway</option>
								<option value="PCI">Islas Pacífico</option>
								<option value="PCN">Islas Pitcairn</option>
								<option value="SLB">Islas Salomón</option>
								<option value="SHN">Islas Santa Helena</option>
								<option value="TCA">Islas Turcas y Caicos</option>
								<option value="ULM">Islas ultramarinas de Estados
									Unidos</option>
								<option value="VGB">Islas Vírgenes</option>
								<option value="IVA">Islas Vírgenes de los Estados
									Unidos</option>
								<option value="ISR">Israel</option>
								<option value="ITA">Italia</option>
								<option value="JAM">Jamaica</option>
								<option value="JAP">Japón</option>
								<option value="JOR">Jordania</option>
								<option value="CAZ">Kazajistán</option>
								<option value="QUE">Kenia</option>
								<option value="QUI">Kirguistán</option>
								<option value="KIR">Kiribati</option>
								<option value="KWT">Kuwait</option>
								<option value="LAO">Laos</option>
								<option value="LES">Lesoto</option>
								<option value="LET">Letonia</option>
								<option value="LBN">Líbano</option>
								<option value="LBR">Liberia</option>
								<option value="LIB">Libia</option>
								<option value="LIE">Liechtenstein</option>
								<option value="LIT">Lituania</option>
								<option value="LUX">Luxemburgo</option>
								<option value="MAC">Macao</option>
								<option value="MAD">Madagascar</option>
								<option value="MAL">Malasia</option>
								<option value="MLV">Malaui</option>
								<option value="MDV">Maldivas</option>
								<option value="MLI">Malí</option>
								<option value="MLT">Malta</option>
								<option value="MAR">Marruecos</option>
								<option value="MRT">Martinica</option>
								<option value="MAU">Mauricio</option>
								<option value="MTN">Mauritania</option>
								<option value="MEX">México</option>
								<option value="FSM">Micronesia</option>
								<option value="MOL">Moldavia</option>
								<option value="MON">Mónaco</option>
								<option value="MGL">Mongolia</option>
								<option value="MNE">Montenegro</option>
								<option value="MSR">Montserrat</option>
								<option value="MBQ">Mozambique</option>
								<option value="MMR">Myanmar</option>
								<option value="NAM">Namibia</option>
								<option value="NRU">Nauru</option>
								<option value="NPL">Nepal</option>
								<option value="NIC">Nicaragua</option>
								<option value="NIG">Níger</option>
								<option value="NGA">Nigeria</option>
								<option value="NIU">Niue</option>
								<option value="N/A">No Aplica</option>
								<option value="NFK">Norfolk</option>
								<option value="NOR">Noruega</option>
								<option value="NCL">Nueva Caledonia</option>
								<option value="NZL">Nueva Zelanda</option>
								<option value="OMA">Omán</option>
								<option value="HOL">Países Bajos</option>
								<option value="PAQ">Pakistán</option>
								<option value="PLW">Palaos</option>
								<option value="PAN">Panamá</option>
								<option value="PNG">Papúa Nueva Guinea</option>
								<option value="PRG">Paraguay</option>
								<option value="PER">Perú</option>
								<option value="PLF">Polinesia Francesa</option>
								<option value="POL">Polonia</option>
								<option value="POR">Portugal</option>
								<option value="PTR">Puerto Rico</option>
								<option value="GBR">Reino Unido</option>
								<option value="RCA">República Centroafricana</option>
								<option value="TCH">República Checa</option>
								<option value="TWD">República de China</option>
								<option value="MKD">República de Macedonia</option>
								<option value="CON">República del Congo</option>
								<option value="COD">República Democrática del Congo</option>
								<option value="DOM">República Dominicana</option>
								<option value="REU">Reunión</option>
								<option value="RUA">Ruanda</option>
								<option value="ROM">Rumania</option>
								<option value="RSS">Rusia</option>
								<option value="ESH">Sahara Occidental</option>
								<option value="SAM">Samoa</option>
								<option value="ASM">Samoa Americana</option>
								<option value="KNA">San Cristóbal y Nieves</option>
								<option value="SMR">San Marino</option>
								<option value="SPM">San Pierre Miquelon</option>
								<option value="VCT">San Vicente y las Granadinas</option>
								<option value="LCA">Santa Lucía</option>
								<option value="STP">Santo Tomé y Príncipe</option>
								<option value="SEN">Senegal</option>
								<option value="SRB">Serbia</option>
								<option value="SYC">Seychelles</option>
								<option value="SRL">Sierra Leona</option>
								<option value="CIN">Singapur</option>
								<option value="SIR">Siria</option>
								<option value="SOM">Somalia</option>
								<option value="SRI">Sri Lanka</option>
								<option value="SUA">Suazilandia</option>
								<option value="AFS">Sudáfrica</option>
								<option value="SUD">Sudán</option>
								<option value="SSD">Sudán del Sur</option>
								<option value="SUE">Suecia</option>
								<option value="SUI">Suiza</option>
								<option value="SUR">Surinam</option>
								<option value="TAI">Tailandia</option>
								<option value="TAN">Tanzania</option>
								<option value="TAD">Tayikistán</option>
								<option value="TMP">Timor Oriental</option>
								<option value="TGO">Togo</option>
								<option value="TKL">Tokelau</option>
								<option value="TON">Tonga</option>
								<option value="TRT">Trinidad y Tobago</option>
								<option value="TUN">Túnez</option>
								<option value="TUC">Turkmenistán</option>
								<option value="TUR">Turquía</option>
								<option value="TUV">Tuvalu</option>
								<option value="UCR">Ucrania</option>
								<option value="UGA">Uganda</option>
								<option value="URU">Uruguay</option>
								<option value="UZB">Uzbekistán</option>
								<option value="VUT">Vanuatu</option>
								<option value="VAT">Vaticano</option>
								<option value="VEN">Venezuela</option>
								<option value="VTN">Vietnam</option>
								<option value="WLF">Wallis y Futuna</option>
								<option value="IEM">Yemen</option>
								<option value="IMS">Yemen del Sur</option>
								<option value="DJI">Yibuti</option>
								<option value="IUG">Yugoslavia</option>
								<option value="ZAR">Zaire</option>
								<option value="ZAN">Zambia</option>
								<option value="ZIN">Zimbabue</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="nombre" class="col-sm-2 control-label">Nombre</label>
						<div class="col-sm-10">
							<input type="text" id="nombre" class="form-control" name="nombre"
								required>
						</div>
					</div>
					<div class="form-group">
						<label for="numeroDocumento" class="col-sm-2 control-label">N&#250;mero
							de documento</label>
						<div class="col-sm-10">
							<input type="text" id="numeroDocumento" class="form-control"
								name="numeroDocumento" required>
						</div>
					</div>
					<div class="form-group">
						<label for="contrasena" class="col-sm-2 control-label">Contrase&#241;a</label>
						<div class="col-sm-10">
							<input type="password" id="contrasena" name="contrasena"
								class="form-control" required>
						</div>
					</div>
					<div class="box-footer">
						<input type="submit" value="Enviar" class="btn btn-info pull-right" onclick="mostrarLoader()">
					</div>
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
<script type="text/javascript">
function mostrarLoader(){
	document.getElementById("loader").classList.remove("hide");
}
</script>
