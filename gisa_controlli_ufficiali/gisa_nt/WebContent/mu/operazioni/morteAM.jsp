<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="include.jsp" %>

<script> function checkFormAM(){
	alert('ante macellazione');
	
	var ret = true;
	var message = '';
	var dataMorteAnteMacellazione = document.getElementById('mavam_data').value != '';
	var luogoMorteAnteMacellazione = document.getElementById('mavam_luogo').selectedIndex > 0;
	var causaMorteAnteMacellazione = trim( document.getElementById('mavam_motivo').value ) != '';

		if(!dataMorteAnteMacellazione || !luogoMorteAnteMacellazione || !causaMorteAnteMacellazione){
			message += label("","- [Morte ant. macellazione] :\r\n \t Valorizzare i seguenti campi: \r\n \t*Data \r\n \t*Luogo di verifica\r\n \t*Causa\r\n \t\r\n"  );
			ret = false;
	}

		if (ret==false)
			alert(message);
		return ret;
}</script>


 <table width="100%" border="0" cellpadding="2" cellspacing="2"  class="details" style="border:1px solid black">
 <tr>
              <th colspan="2"><strong>Morte antecedente macellazione</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              		<input readonly type="text" id="dataMorteAm" name="dataMorteAm" onfocus="riportaDataArrivoMacello(this);" size="10" value="" required="true" label="Data morte ante macellazione" />&nbsp;  
			        <font color="red" id="dataMorteAnteMacellazione" >*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].dataMorteAm,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataMorteAm);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            	
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel">Luogo di verifica</td>
                
                <td>
                	<%
                		LuoghiVerifica.setJsEvent("onChange=\"javascript:displayLuogoVerifica();\"");
                	LuoghiVerifica.setRequired(true);
                	LuoghiVerifica.setLabel("Luogo verifica decesso morte ante macellazione");
                	%>                               
                	<%=LuoghiVerifica.getHtmlSelect( "idLuogoVerificaMorteAm", -1 ) %>
                	
                	<input 
                		type="text" 
                		size="30" 
                		id="descrizioneLuogoVerificaMorteAm" 
                		name="descrizioneLuogoVerificaMorteAm"
                		style="display:none;" 
                		value="" required="true" label="descrizione luogo verifica visita ante mortem"/>
                	<font color="red" id="luogoMorteAnteMacellazione" >*</font>
                </td>
                                
            </tr>
            
          <tr class="containerBody">
                <td class="formLabel">Causa</td>
                <td>
                	<textarea rows="2" cols="40" id="causaMorteAm" name="causaMorteAm" onchange="" required="true" label="Descrizione causa visita ante mortem"></textarea>
                	<font color="red" id="causaMorteAnteMacellazione">*</font>
                </td>
				
           </tr>
           
           <tr class="containerBody">
                <td class="formLabel">Impianto di termodistruzione</td>
                <td>
                	<textarea 
                		rows="2" 
                		cols="40" 
                		name="impiantoTermodistruzioneMorteAm"
                		id="impiantoTermodistruzioneMorteAm"
                		></textarea>
                </td>
           </tr>
           
             <tr class="containerBody">
                <td class="formLabel">Destinazione della carcassa</td>
                <td>
                	<textarea 
                		rows="2" 
                		cols="40" 
                		name="destinazioneCarcassaMorteAm"
                		id="destinazioneCarcassaMorteAm"
                		></textarea>
                </td>
           </tr>
           
           
           <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAslOrigineMorteAm"
            			name="comunicazioneAslOrigineMorteAm" 
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioAnimaleMorteAm"
            			name="comunicazioneProprietarioAnimaleMorteAm" 
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAziendaOrigineMorteAm"
            			name="comunicazioneAziendaOrigineMorteAm" 
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioMacelloMorteAm"
            			name="comunicazioneProprietarioMacelloMorteAm" 
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazionePifMorteAm"
            			name="comunicazionePifMorteAm" 
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneUvacMorteAm"
            			name="comunicazioneUvacMorteAm" 
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneRegioneMorteAm"
            			name="comunicazioneRegioneMorteAm" 
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="comunicazioneAltroMorteAm" 
            			name="comunicazioneAltroMorteAm" 
            			onclick="visualizzaTextareaMavamToAltro();"
            			/> Altro <br/>
            		<textarea id="comunicazioneAltroTestoMorteAm" name="comunicazioneAltroTestoMorteAm" rows="2" cols="40" style="display: none;" ></textarea>
            			
            	</td>
            </tr>
           
           <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td>
                	<textarea rows="2" cols="40" id="noteMorteAm" name="noteMorteAm"></textarea>
                </td>
				
           </tr>
        </tbody>
     </table>
     </br></br>
          <div 
	        	style="display: none"  id="blocco_animale_div">
	                  <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
	          <tbody>
	            <tr>
	              <th colspan="2"><strong>Blocco Animale</strong></th>
	            </tr>
	
	            <tr class="containerBody">
	              <td class="formLabel" >Data blocco</td>
	              <td>
	              		<input readonly type="text" name="dataBloccoMorteAm" size="10" value="" />&nbsp;
				        <a href="#" onClick="cal19.select(document.forms[0].dataBloccoMorteAm,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataBloccoMorteAm);"><img src="images/delete.gif" align="absmiddle"/></a>
	              		
	              </td>
	            </tr>
	
	            <tr class="containerBody">
	              <td class="formLabel" >Data sblocco</td>
	              <td>
		              	<input readonly type="text" name="dataSbloccoMorteAm" size="10" value="" />&nbsp;
				        <a href="#" onClick="cal19.select(document.forms[0].dataSbloccoMorteAm,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataSbloccoMorteAm);"><img src="images/delete.gif" align="absmiddle"/></a>
	              </td>
	            </tr>
	            
	             <tr class="containerBody">
	                <td class="formLabel">Destinazione allo sblocco</td>
	                <td>
	                	<%=ProvvedimentiVAM.getHtmlSelect("idDestinazioneSbloccoMorteAm", -1) %>
	    			</td>                
	            </tr>
	              </tbody>
	           </table>
	         