<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="include.jsp" %>		
<script> function checkFormComunicazioni(){
	
	alert('comunicazioni');
	var ret = true;
	var message = '';
	var comunicazioniEsterneA = document.getElementById('casl_to_asl_origine').checked || 
	document.getElementById('casl_to_proprietario_animale').checked ||
	document.getElementById('casl_to_azienda_origine').checked ||
	document.getElementById('casl_to_proprietario_macello').checked ||
	document.getElementById('casl_to_pif').checked ||
	document.getElementById('casl_to_uvac').checked ||
	document.getElementById('casl_to_regione').checked ||
	document.getElementById('casl_to_altro').checked ;
var dataComunicazioniEsterne = document.getElementById('casl_data').value != '';
var tipoNCComunicazioniEsterne = document.getElementById('casl_NC_rilevate').selectedIndex > 0;
var provvedimentiComunicazioniEsterne = document.getElementById('casl_provvedimenti_selezionati').selectedIndex > 0;
//se almeno uno di essi è valorizzato...
if(comunicazioniEsterneA || dataComunicazioniEsterne || tipoNCComunicazioniEsterne || provvedimentiComunicazioniEsterne){

//...allora vedi se ce n'è almeno uno NON valorizzato
if(!comunicazioniEsterneA || !dataComunicazioniEsterne || !tipoNCComunicazioniEsterne || !provvedimentiComunicazioniEsterne){
message += label("","- [Comunicazioni Esterne] :\r\n \tUno dei seguenti campi è valorizzato:\r\n \t*Comunicazioni a\r\n \t*Data\r\n \t*Tipo di non conformità\r\n \t*Provvedimenti Adottati\r\n \tValorizzare anche gli altri.\r\n"  );
ret = false;
}
}
	if (ret==false)
		alert(message);
	return ret;
}</script>


<table width="100%" border="0" cellpadding="2" cellspacing="2"  class="details" style="border:1px solid black">
    <tbody>
   <tr>
                <th colspan="2"><strong>Comunicazioni Esterne</strong>                </th>
            </tr>
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<p id="comunicazioneA" align="center" style="display: none;"><font color="red" >*</font></p>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAslOrigineComunicazioni"
            			name="comunicazioneAslOrigineComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioAnimaleComunicazioni"
            			name="comunicazioneProprietarioAnimaleComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAziendaOrigineComunicazioni"
            			name="comunicazioneAziendaOrigineComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioMacelloComunicazioni"
            			name="comunicazioneProprietarioMacelloComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazionePifComunicazioni"
            			name="comunicazionePifComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneUvacComunicazioni"
            			name="comunicazioneUvacComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneRegioneComunicazioni"
            			name="comunicazioneRegioneComunicazioni" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="comunicazioneAltroComunicazioni" 
            			name="comunicazioneAltroComunicazioni" 
            			onclick="visualizzaTextareaCAslToAltro();gestisciObbligatorietaComunicazioniEsterne();"
            			/> Altro <br/>
            		<textarea id="comunucazioneAltroTestoComunicazioni" name="comunucazioneAltroTestoComunicazioni" rows="2" cols="40" style="display: none" ></textarea>
            			
            	</td>
            </tr>   
    
     <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              		<input label="Data comunicazioni esterne" readonly type="text" 
              		id="dataComunicazioniEsterne" name="dataComunicazioniEsterne" onfocus="riportaDataArrivoMacello(this);gestisciObbligatorietaComunicazioniEsterne();" 
              		size="10" value="" />&nbsp;
              		<font color="red" id="dataComunicazioniEsterne" style="display: none;">*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].dataComunicazioniEsterne,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataComunicazioniEsterne);"><img src="images/delete.gif" align="absmiddle"/></a>
            <p id="dataComunicazioneA" align="center" style="display: none;"><font color="red" >*</font></p>  </td>
            </tr>
            
            <tr>
            
   			<td class="formLabel">Tipo di non conformità</td>
                <%
                	MotiviASL.setMultiple(true);
                	MotiviASL.setSelectSize(5);
                	MotiviASL.setJsEvent("onchange=\"javascript:gestisciObbligatorietaComunicazioniEsterne();\"");
                	//MotiviASL.setRequired(true);
                	MotiviASL.setLabel("Tipo non conformita");
					LookupList casl_NC_sel = new LookupList();
					for( Casl_Non_Conformita_Rilevata nc: (ArrayList<Casl_Non_Conformita_Rilevata>)casl_NCRilevate )
					{
						casl_NC_sel.addItem( nc.getId_casl_non_conformita(), "" );//add( Patologie.get( Patologie.getLevelFromId(pr.getId_patologia()) ) );
					}
                %>
			<td>
				<p id="tipoNonConformita" align="center" style="display: none;"><font color="red" >*</font></p>
				<%=MotiviASL.getHtmlSelect( "listaNonConformita", casl_NC_sel ) %>
			</td> 
			</tr>
			
			
           <tr class="containerBody">
	           <td class="formLabel">Descrizione non conformità</td>
	           <td><textarea rows="2" cols="40" name="descrizioneNonConformitaComunicazioni" id="descrizioneNonConformitaComunicazioni"></textarea></td>
           </tr>
           
           <tr>
           <td class="formLabel">Provvedimenti Adottati</td>
                <%
                	look_ProvvedimentiCASL.setMultiple(true);
                	look_ProvvedimentiCASL.setSelectSize(5);
					LookupList provvedimentiSelezionati = new LookupList();
					for( ProvvedimentiCASL pr: (ArrayList<ProvvedimentiCASL>)casl_Provvedimenti_effettuati )
					{
						provvedimentiSelezionati.addItem( pr.getId_provvedimento(), "" );
					}
					
					look_ProvvedimentiCASL.setJsEvent("onchange=\"javascript:gestisciBloccoAnimale();gestisciObbligatorietaComunicazioniEsterne();\"");
					
                %>
			<td>
				<p id="provvedimentiAdottati" align="center" style="display: none;"><font color="red" >*</font></p>
				<%look_ProvvedimentiCASL.setLabel("Provvedimento adottato"); %>
				<%=look_ProvvedimentiCASL.getHtmlSelect( "listaProvvedimenti", -1 ) %>
			</td>
           </tr>
           
            <tr class="containerBody" id="note_prevvedimento" >
	           <td class="formLabel">Note</td>
	           <td><textarea rows="2" cols="40" name="noteProvvedimentoAdottato"></textarea></td>
           </tr>
           
        </tbody>
   </table>
   
   <br/>
   
   <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0" style="border:1px solid black">
          <tbody>
            <tr>
              <th colspan="2"><strong>Ricezione Comunicazioni Esterne</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data</td>
                <td>
                	<input readonly type="text" id="d" name="dataRicezioneComunicazioniEsterne" onfocus="riportaDataArrivoMacello(this);" size="10" value="" />&nbsp;  
			        <a href="#" onClick="cal19.select(document.forms[0].dataRicezioneComunicazioniEsterne,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataRicezioneComunicazioniEsterne);"><img src="images/delete.gif" align="absmiddle"/></a>
                </td>
             </tr>
             
             <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><textarea rows="2" cols="40" name="noteRicezioneComunicazioniEsterne"></textarea></td>
             </tr>
          </tbody>
         </table>