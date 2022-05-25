<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/vam.tld" prefix="vam" %>

<script language="JavaScript" type="text/javascript" src="js/vam/accettazione/addDecedutoNonAnagrafe.js"></script>

	<form action="vam.accettazione.AddDecedutoNonAnagrafe.us" method="post">

	<fieldset>
		<legend>
			Dati animale giunto deceduto e non registrato in anagrafe regionale
		</legend>
		<table>	
			<tr>
				<td>
					Tipologia
				</td>
				<td>
					<select id="idSpecie" name="idSpecie" onchange="selezionaRazzeMantelli(this.value)">
						<c:forEach items="${specie}" var="temp" >
							<option value="${temp.id }">${temp.description }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>
					<label id="labSottotipologia" style="display:none">Sottotipologia</label>
				</td>
				<td>
					<select id="idSottotipologia" name="idSottotipologia" style="display:none" onchange="selezionaSpecieSottotipologia(this.value)">
							<option value="sin">Sinantropo</option>
							<option value="mar">Marino</option>
							<option value="zoo">Zoo</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td id="labelRazza">
					Razza
				</td>
				<td> 
					<div name="razze_cane" id="razze_cane">
						<select name="idRazza">
							<option value="-1">-- Seleziona --</option>
							<c:forEach items="${razzeCane}" var="temp" >
								<c:if test="${temp.cane==true}">
									<option value="${temp.id}">${temp.description }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div name="razze_gatto" id="razze_gatto" style="display: none;">
						<select name="idRazza">
							<option value="-1">-- Seleziona --</option>
							<c:forEach items="${razzeGatto}" var="temp" >
								<c:if test="${temp.gatto==true}">
									<option value="${temp.id}">${temp.description }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div name="razza_sinantropoS" id="razza_sinantropoS" style="display: none;" >
						<font color="red"> *</font>
						<select name="specieSinantropoS" id="specieSinantropoS" onchange="javascript:chooseSpecie(this.value);">
							<option value="0" selected>&lt;--- Selezionare Classe ---&gt;</option>
    						<option value="1" >  Uccello  </option>
            				<option value="2" >  Mammifero </option>
            				<option value="3" >  Rettile/Anfibio    </option>    
	    				</select>
	    				<select name="tipologiaSinantropoU" id="uccelli" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listUccelli}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        	<select name="tipologiaSinantropoM" id="mammiferi" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listMammiferi}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        	<select name="tipologiaSinantropoRA" id="rettiliAnfibi" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listRettiliAnfibi}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>
					</div>
					
					<div name="razza_sinantropoM" id="razza_sinantropoM" style="display: none;" >
						<font color="red"> *</font>
						<select name="specieSinantropoM" id="specieSinantropoM" onchange="javascript:chooseSpecieM(this.value);">
							<option value="0" selected>&lt;--- Selezionare ---&gt;</option>
    						<option value="1" >  Mammiferi/Cetacei  </option>
            				<option value="2" >  Rettile/Testuggini </option>
            				<option value="3" >  Selaci Chondrichthlyes    </option>    
	    				</select>
	    				<select name="tipologiaSinantropoS" id="selaci" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listSelaci}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        	<select name="tipologiaSinantropoMC" id="mammiferiCetacei" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listMammiferiCetacei}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        	<select name="tipologiaSinantropoRT" id="rettiliTestuggini" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listRettiliTestuggini}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>
					</div>
					
					<div name="razza_sinantropoZ" id="razza_sinantropoZ" style="display: none;" >
						<font color="red"> *</font>
						<select name="specieSinantropoZ" id="specieSinantropoZ" onchange="javascript:chooseSpecieZ(this.value);">
							<option value="0" selected>&lt;--- Selezionare ---&gt;</option>
    						<option value="1" >  Uccello  </option>
            				<option value="2" >  Mammifero </option>
            				<option value="3" >  Rettile/Anfibio    </option>    
	    				</select>
	    				<select name="tipologiaSinantropoUZ" id="uccelliZ" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listUccelliZ}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        	<select name="tipologiaSinantropoMZ" id="mammiferiZ" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listMammiferiZ}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        	<select name="tipologiaSinantropoRAZ" id="rettiliAnfibiZ" style="display:none;">
							<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
		    				<c:forEach var="ss" items="${listRettiliAnfibiZ}">
		            			<option value="<c:out value="${ss.id}"/>">            				
		            				${ss.description}
		    					</option>
		            		</c:forEach>
		    			</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<label id="mantello1">Mantello</label>
				</td>
				<td> 
					<div name="mantelli_cane" id="mantelli_cane">
						<select name="idMantello">
							<option value="-1">-- Seleziona --</option>
							<c:forEach items="${mantelliCane}" var="temp" >
								<c:if test="${temp.cane==true}">
									<option value="${temp.id }">${temp.description }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div name="mantelli_gatto" id="mantelli_gatto" style="display: none;">
						<select name="idMantello" disabled=disabled>
							<option value="-1">-- Seleziona --</option>
							<c:forEach items="${mantelliGatto}" var="temp" >
								<c:if test="${temp.gatto==true}">
									<option value="${temp.id}">${temp.description }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div name="mantelli_sinantropo" id="mantelli_sinantropo" style="display: none;">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<label id="taglia1">Taglia</label>
				</td>
				<td> 
					<select name="idTaglia" id="taglia2">
						<option value="-1">-- Seleziona --</option>
						<c:forEach items="${taglie}" var="temp" >
							<option value="${temp.id }">${temp.description }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			
			<tr>
				<td>
					<label id="dataNascitaLabel">Data di nascita</label>
					<label style="display:none;" id="etaLabel">Et&agrave;</label>
				</td>
				<td>
					<div id="dataNascitaDiv">
						<input type="text" id="dataNascita" name="dataNascita" maxlength="10" size="10" readonly="readonly" />
						<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
						<script type="text/javascript">
							Calendar.setup({
								inputField  :    "dataNascita",      // id of the input field
								ifFormat    :    "%d/%m/%Y",  // format of the input field
								button      :    "id_img_1",  // trigger for the calendar (button ID)
								// align    :    "rl,      // alignment (defaults to "Bl")
								singleClick :    true,
								timeFormat	:   "24",
								showsTime	:   false
							});					    
						</script>
						<input type="checkbox" name="dataNascitaPresunta" id="dataNascitaPresunta" /> <label for="dataNascitaPresunta">presunta</label>
					</div>
					<div id="etaDiv">
						<select name="idEta" id="idEta" style="display:none">
						<option value="-1">-- Seleziona --</option>
							<c:forEach items="${listEta}" var="temp" >
								<option value="${temp.id }"
								>${temp.description }</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			
			
			
			<tr>
				<td>
					Data del decesso<font color="red"> *</font>
				</td>
				<td>
					<input type="text" id="dataMorte" name="dataMorte" maxlength="10" size="10" readonly="readonly" />
					<img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
					<script type="text/javascript">
						Calendar.setup({
							inputField  :    "dataMorte",      // id of the input field
							ifFormat    :    "%d/%m/%Y",  // format of the input field
							button      :    "id_img_2",  // trigger for the calendar (button ID)
							// align    :    "rl,      // alignment (defaults to "Bl")
							singleClick :    true,
							timeFormat	:   "24",
							showsTime	:   false
						});					    
					</script>
					<input type="checkbox" name="dataMortePresunta" id="dataMortePresunta" /> <label for="dataMortePresunta">presunta</label>
				</td>
			</tr>
			
			<tr>
				<td>
					Probabile causa del decesso
				</td>
				<td>
		        <select name="causaMorteIniziale" >
		        	<option value="">&lt;--Selezionare un valore--&gt;</option>	
		        	 <c:forEach items="${listCMI}" var="temp" >	
		        	 	<option value="${temp.id }">${temp.description }</option>	        	 				
					</c:forEach>
		        </select>
	        	</td>
				<td>				
				</td>
			</tr>
			
			<tr>
				<td>
					Sesso
				</td>
				<td>
					<select name="sesso" id="sesso">
						<option value="M">Maschio</option>
						<option value="F">Femmina</option>
						<option value="ND">Non Definito</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label id="labSter">Sterilizzato</label>
				</td>
				<td id="trSter3">
					<input type="checkbox" id="sterilizzato" name="sterilizzato"/>
				</td>
			</tr>
			
			<tr>
				<td>
					<label id="labRand">Randagio</label>
				</td>
				<td id="trRand3">
					<input type="checkbox" checked="checked" id="randagio" name="randagio" onclick="formRandagio(this);"/>
					<!--i>Cliccare per inserire dati ritrovamento</i-->
				</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset id="datiProprietario">
		<legend>
			Dati proprietario animale
		</legend>
		<table>
			<tr id="trComuneSindaco" style="display:none;">
				<td>
					Sindaco del comune di 
				</td>
				<td> 
				    <font color="red"> *</font>
					<select name="comuneSindaco" id="comuneSindaco" disabled="disabled">
						<option value="0">&lt;--- Selezionare il comune  ---&gt;</option>
	    				<c:forEach var="c" items="${comuni}">
	            			<option value="<c:out value="${c.id}"/>">            				
	            				${c.description}
	    					</option>
	            		</c:forEach>
	    			</select>
				</td>
			</tr>
			
			<tr id="trProprietarioNome">
				<td>
					Nome
				</td>
				<td> 
					<input type="text" name="proprietarioNome" id="proprietarioNome" maxlength="64" />
					<font color="red"> *</font>
				</td>
			</tr>

			<tr id="trProprietarioCognome">
				<td>
					Cognome
				</td>
				<td> 
					<input type="text" name="proprietarioCognome" id="proprietarioCognome" maxlength="64" />
					<font color="red"> *</font>
				</td>
			</tr>

			<tr id="trProprietarioCodiceFiscale">
				<td>
					Codice Fiscale
				</td>
				<td> 
					<input type="text" name="proprietarioCodiceFiscale"  id="proprietarioCodiceFiscale"maxlength="16" />
				</td>
			</tr>
			
			<tr id="trProprietarioDocumento">
				<td>
					Documento
				</td>
				<td> 
					<input type="text" name="proprietarioDocumento" id="proprietarioDocumento" maxlength="64" />
				</td>
			</tr>
			
			<tr id="trProprietarioIndirizzo">
				<td>
					Indirizzo
				</td>
				<td> 
					<input type="text" name="proprietarioIndirizzo" id="proprietarioIndirizzo" maxlength="64" />
					<font color="red"> *</font>
				</td>
			</tr>
			
			<tr id="trProprietarioComune">
				<td>
					Comune
				</td>
				<td> 
					<input type="text" name="proprietarioComune" id="proprietarioComune" maxlength="64" />
					<font color="red"> *</font>
				</td>
			</tr>
			
			<tr id="trProprietarioProvincia">
				<td>
					Provincia
				</td>
				<td> 
					<input type="text" name="proprietarioProvincia" id="proprietarioProvincia" maxlength="2" />
				</td>
			</tr>	
			
			<tr id="trProprietarioCap">
				<td>
					CAP
				</td>
				<td> 
					<input type="text" name="proprietarioCap" id="proprietarioCap" maxlength="5" />
				</td>
			</tr>	
			
			<tr id="trProprietarioTelefono">
				<td>
					Telefono
				</td>
				<td> 
					<input type="text" name="proprietarioTelefono" id="proprietarioTelefono" maxlength="64" />
				</td>
			</tr>	
		</table>
	</fieldset>
	<!-- DATI SUL RITROVAMENTO DEL RANDAGIO   -->
	<fieldset id="datiRitrovamento">
		<legend>
			Dati inerenti il ritrovamento
		</legend>
		<table> 	
        	<tr>
	        <td>
	       		 Provincia e Comune Ritrovamento<font color="red"> *</font>
	        </td>
	        <td>
	        	<select id="provinciaRitrovamento"  name="provinciaRitrovamento" onChange="javascript: chooseProvinciaRitrovamento(this.value)">
					<option value=""  SELECTED>&lt;--- Selezionare la provincia ---&gt;</option>
    				<option value="AV" >  Avellino  	</option>
    				<option value="BN" >  Benevento  </option>
            		<option value="CE" >   Caserta 	</option>
            		<option value="NA" >  Napoli 	</option>  
            		<option value="SA" >  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>    
	        	<div id="comuneRitrovamentoBN" style="display:none;">
		        	<select name="comuneRitrovamentoBN" id="comuneRitrovamentoChooserBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneRitrovamentoNA" style="display:none;">
		        	<select name="comuneRitrovamentoNA" id="comuneRitrovamentoChooserNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneRitrovamentoSA" style="display:none;">
		        	<select name="comuneRitrovamentoSA" id="comuneRitrovamentoChooserSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneRitrovamentoCE" style="display:none;">
		        	<select name="comuneRitrovamentoCE" id="comuneRitrovamentoChooserCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneRitrovamentoAV" style="display:none;">
		        	<select name="comuneRitrovamentoAV" id="comuneRitrovamentoChooserAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.id}"/>">            				
	            				${av.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
	        </td>
        </tr>
        
        
       	<tr >
        	<td>
        		Indirizzo Ritrovamento<font color="red"> *</font>
        	</td>
        	<td>
        		<input type="text" name="indirizzoRitrovamento" id="indirizzoRitrovamento" maxlength="25" size="25"/>
        	</td>
        	<td>
        	</td>
       	</tr>	
       	
       	<tr>
        	<td>
        		Note Ritrovamento
        	</td>
        	<td>
        		<TEXTAREA NAME="noteRitrovamento" COLS=40 ROWS=3></TEXTAREA>         		
        	</td>
        	<td>
        	</td>
       	</tr>   			
		</table>
		
	</fieldset>
	
	
	
	
	
	
	<font color="red">* </font> Campi obbligatori
	<br/>
	<input type="submit" value="Salva e prosegui con l'accettazione" onclick="return checkForm() && myConfirm('Sicuro di voler procedere col salvataggio?');" />
	<input type="button" value="Annulla" onclick="if( myConfirm('Sicuro di voler annullare?') ){location.href='vam.accettazione.Home.us'};" />
</form>

<script type="text/javascript">
	var idSpeciePrecedente = 1;
	function selezionaRazzeMantelli( idSpecie )
	{
		if( 1 == idSpeciePrecedente || 1 == idSpecie )
		{
			toggleDiv( "razze_cane" );
			toggleDivMantello( "mantelli_cane" );
			document.getElementById('mantello1').style.display="block";
		}

		if( 2 == idSpeciePrecedente || 2 == idSpecie )
		{
			toggleDiv( "razze_gatto" );
			toggleDivMantello( "mantelli_gatto" );
			document.getElementById('mantello1').style.display="block";
		}

		if( 3 == idSpeciePrecedente || 3 == idSpecie )
		{
			toggleDiv( "razza_sinantropoS" );
			toggleDivMantello( "mantelli_sinantropo" );
		}
		if(1 == idSpecie)
		{
			document.getElementById('taglia1').style.display="block";
			document.getElementById('taglia2').style.display="block";
			document.getElementById('mantello1').style.display="block";
		}
		else if(2 == idSpecie)
		{
			document.getElementById('taglia1').style.display="none";
			document.getElementById('taglia2').style.display="none";
		}
		else if(3 == idSpecie)
		{
			document.getElementById('taglia1').style.display="none";
			document.getElementById('taglia2').style.display="none";
			document.getElementById('mantello1').style.display="none";
		}
		if(1 == idSpecie || 2 == idSpecie)
		{
			document.getElementById('dataNascitaDiv').style.display="block";
			document.getElementById('etaDiv').style.display="none";
			document.getElementById('idEta').style.display="block";
			document.getElementById('dataNascitaLabel').style.display="block";
			document.getElementById('etaLabel').style.display="none";
			document.getElementById('labelRazza').innerHTML='Razza';
			disabilitaCampi(false);
			document.getElementById('idSottotipologia').value = "sin";
			selezionaSpecieSottotipologia("sin");
			document.getElementById('idSottotipologia').style.display="none";
			document.getElementById('labSottotipologia').style.display="none";
			document.getElementById('razza_sinantropoS').style.display="none";
			document.getElementById('razza_sinantropoM').style.display="none";
			document.getElementById('razza_sinantropoZ').style.display="none";
			
		}
		if(3 == idSpecie)
		{
			document.getElementById('dataNascitaDiv').style.display="none";
			document.getElementById('etaDiv').style.display="block";
			document.getElementById('idEta').style.display="block";
			document.getElementById('dataNascitaLabel').style.display="none";
			document.getElementById('etaLabel').style.display="block";
			document.getElementById('labelRazza').innerHTML='Classe';
			disabilitaCampi(true);
			document.getElementById('idSottotipologia').style.display="block";
			document.getElementById('labSottotipologia').style.display="block";
		}

		idSpeciePrecedente = idSpecie;
	}
	
	formRandagio(document.getElementById('randagio'));
	
	
	
	function disabilitaCampi(bool)
	{
		var proprieta = "block";
		var disabled = "";
		if(bool)
		{
			proprieta = "none";
			disabled="disabled"
		}
		document.getElementById('labSter').style.display=proprieta;
		document.getElementById('randagio').style.display=proprieta;
		document.getElementById('labRand').style.display=proprieta;
		document.getElementById('sterilizzato').style.display=proprieta;
		document.getElementById('randagio').disabled=disabled;
		document.getElementById('sterilizzato').disabled=disabled;
		//document.getElementById('datiRitrovamento').style.display=proprieta;
		document.getElementById('datiProprietario').style.display=proprieta;
	}
	
	function selezionaSpecieSottotipologia(specie)
	{
		if(specie=="sin")
		{
			document.getElementById('razza_sinantropoS').style.display="block";
			document.getElementById('razza_sinantropoM').style.display="none";
			document.getElementById('razza_sinantropoZ').style.display="none";
		
		}
		else if(specie=="mar")
		{
			document.getElementById('razza_sinantropoS').style.display="none";
			document.getElementById('razza_sinantropoM').style.display="block";
			document.getElementById('razza_sinantropoZ').style.display="none";	
		}
		else if(specie=="zoo")
		{
			document.getElementById('razza_sinantropoS').style.display="none";
			document.getElementById('razza_sinantropoM').style.display="none";
			document.getElementById('razza_sinantropoZ').style.display="block";
		}
		
	}
</script>

	

