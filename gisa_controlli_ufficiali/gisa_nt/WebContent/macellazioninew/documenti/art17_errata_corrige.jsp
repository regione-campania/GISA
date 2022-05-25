<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.net.InetAddress"%>
  <%@ page import="java.util.*"%>
  <%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
 <jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
 <jsp:useBean id="Partita" class="org.aspcfs.modules.macellazioninew.base.Partita" scope="request"/>
 <jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
 <jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="userIp" class="java.lang.String" scope="request"/>
   <jsp:useBean id="userName" class="java.lang.String" scope="request"/>
 <jsp:useBean id="timeNow" class="java.lang.String" scope="request"/>
<jsp:useBean id="art17" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>
   
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.DestinatarioCarni"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../initPage.jsp" %>
<link rel="stylesheet" documentale_url="" href="css/moduli_print.css" type="text/css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script src="javascript/geocodifica.js" type="text/javascript" language="JavaScript"></script>
<script src="dwr/interface/Geocodifica.js" type="text/javascript" language="JavaScript"></script>
<script src="dwr/engine.js" type="text/javascript" language="JavaScript"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>

<script>

function setVeterinarioNome(indice, nome){
	var vet = document.getElementById('veterinari_cd'+indice+'_nome');
	if (vet!=null)
		vet.value = nome;
}

function gestisciPartitaEsistente(partita){

	//var form = document.main;
	if( partita.esistente )
	{
		//form.capo_esistente.value = 'si';
		alert( "Numero partita "+partita.partita +" già esistente." );
		document.getElementById("numero").value="";
		
	}
	
}

function gestioneCb(scelta){
	
	var cb = document.getElementById('cb_'+scelta);
	var errati =  document.getElementById('div_'+scelta+'_errati');
	var corretti =  document.getElementById('div_'+scelta+'_corretti');
	if (cb.checked){
		errati.style.visibility='visible';
		corretti.style.visibility='visible';
	}
	else
		{
		errati.style.visibility='hidden';
		corretti.style.visibility='hidden';
		}

}

function checkForm(){
	var message = '';
	if (document.getElementById('sottoscritto').value=='')
		message = message+'\n Compilare il campo Sottoscritto';
	if (document.getElementById('motivo').value=='')
		message = message+'\n Compilare il campo Motivo';
	if (document.getElementById('cb_numero').checked && document.getElementById('numero').value =='')
		message = message+'\n Compilare il campo Numero';
	if (document.getElementById('cb_mod4').checked && document.getElementById('mod4').value =='')
		message = message+'\n Compilare il campo Riferimento Mod4';
	if (document.getElementById('cb_altro').checked && document.getElementById('altro_errato').value =='')
		message = message+'\n Compilare il campo Altro (errato)';
	if (document.getElementById('cb_altro').checked && document.getElementById('altro_corretto').value =='')
		message = message+'\n Compilare il campo Altro (corretto)';
	if (document.getElementById('cb_veterinario_1').checked && document.getElementById('veterinari_cd1').value ==-1)
		message = message+'\n Compilare il campo Veterinario 1';
	if (document.getElementById('cb_veterinario_2').checked && document.getElementById('veterinari_cd2').value ==-1)
		message = message+'\n Compilare il campo Veterinario 2';
	if (document.getElementById('cb_veterinario_3').checked && document.getElementById('veterinari_cd3').value ==-1)
		message = message+'\n Compilare il campo Veterinario 3';
	
	for (var i =1; i<=20; i++){
		if (document.getElementById('cb_destinatari_'+i)!=null)
			if (document.getElementById('cb_destinatari_'+i).checked && (document.getElementById('destinatario_'+i+'_id').value ==-1 || (document.getElementById('destinatario_'+i+'_nome').value=='' && document.getElementById('destinatario_'+i+'_id').value==-999)))
					message = message+'\n Compilare il campo Destinatario '+i;
	}
		
	if (message!='')
		{
		alert(message);
		return false;
		}
	return true;
}
</script>


<script>
function selectDestinazione( index )
{
	try {
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario --";
		
		document.getElementById( 'destinatario_' + index + "_id" ).value	= "-1";
		document.getElementById( 'destinatario_' + index + "_nome" ).value	= "";
		document.getElementById('esercenteFuoriRegione' + index).style.display = 'none';
		document.getElementById('esercenteFuoriRegione' + index).value = '';
		document.getElementById('esercenteNoGisa' + index).style.display = 'none';
		document.getElementById('esercenteNoGisa' + index).value = '';
		
		
		
		inReg = document.getElementById( "inRegione_" + index );
		fuoriReg = document.getElementById( "outRegione_" + index );
	
		if( inReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "block";
			document.getElementById( 'esercenti_' + index ).style.display	= "none";
			
		}
		else if( fuoriReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "none";
			document.getElementById( 'esercenti_' + index ).style.display	= "block";
			
		}
	}
	catch(err)
	{
		alert(err.description);
	}
}

function gestisciObbligatorietaVisitaPostMortem(){
	
}
function gestioneObbligNumCapiDestCarni (){
	
}
function mostraTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).style.display = '';
}

function nascondiTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).value = '';
	document.getElementById(idTextarea).style.display = 'none';
}

function impostaDestinatarioMacelloCorrente(index){
	document.getElementById('destinatario_' + index + '_id').value = "<%=OrgDetails.getOrgId()%>";
	document.getElementById('destinatario_' + index + '_nome').value = "<%=OrgDetails.getName().replaceAll("\"","'")%>";
	document.getElementById('destinatario_label_' + index).innerHTML = "<%=OrgDetails.getName().replaceAll("\"","'")%>";
		
	document.getElementById('esercenteNoGisa' + index).style.display = 'none';
}

function valorizzaDestinatario(campoTextarea,idDestinatario){
	document.getElementById(idDestinatario + '_nome').value = campoTextarea.value;
	document.getElementById(idDestinatario + '_id').value = -999;
	gestisciObbligatorietaVisitaPostMortem();
}

function selectDestinazioneFromLinkTextarea( index )
{
	try {
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario --";
		if(document.getElementById( 'destinatario_' + index + "_id" ).value != "-999"){
			document.getElementById( 'destinatario_' + index + "_id" ).value	= "-1";
			document.getElementById( 'destinatario_' + index + "_nome" ).value	= "";
			document.getElementById('esercenteNoGisa' + index).value = '';
			document.getElementById('esercenteFuoriRegione' + index).value = '';
		}
		
		
		
		
		inReg = document.getElementById( "inRegione_" + index );
		fuoriReg = document.getElementById( "outRegione_" + index );
	
		if( inReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "block";
			document.getElementById( 'esercenti_' + index ).style.display	= "none";
			document.getElementById('esercenteFuoriRegione' + index).style.display = 'none';
			document.getElementById('esercenteFuoriRegione' + index).value = '';
		}
		else if( fuoriReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "none";
			document.getElementById( 'esercenti_' + index ).style.display	= "block";
			document.getElementById('esercenteNoGisa' + index).style.display = 'none';
			document.getElementById('esercenteNoGisa' + index).value = '';
		}
	}
	catch(err)
	{
		alert(err.description);
	}
}
</script>

<html>
<body>

<script>
<%if (messaggio!=null && !messaggio.equals("null") && !messaggio.equals("")){%>
alert('<%=messaggio%>');
<%}%>
</script>
<table width="100%"">
<col width="33%"> <col width="33%">
<tr>
<td>
<div class="boxIdDocumento"></div><br/>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
<i><%=userIp %> - <%=userName %></i>
</td>
<td></td>
<td><div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=aslList.getSelectedValue(OrgDetails.getSiteId()).toLowerCase() %>.jpg" /></div></td>
</tr>

</table>

<br/>

<br/>

<center><b>Regione Campania<br/>
Azienda Sanitaria Locale <%=aslList.getSelectedValue(OrgDetails.getSiteId())%><br/>
Servizio Veterinario<br/><br/>
ISPEZIONE DELLE CARNI</br/><br/>
<font size="5px"><U>ERRATA CORRIGE PER ART.17 (R.D. 20/12/1928, N. 3298) *</U></font></b></center>
<br/><br/>

<form method="post" name="form2" action="MacellazioniDocumentiNew.do?command=SalvaModuleErrataCorrigeArt17">
<input type="hidden" id="idMacello" name="idMacello" value="<%=Partita.getId_macello()%>"/>
<input type="hidden" id="idPartita" name="idPartita" value="<%=Partita.getId()%>"/>
<input type="hidden" id="dataMacellazione" name="dataMacellazione" value="<%=toDateasString(Partita.getDataSessioneMacellazione()) %>"/>
<center>


Il sottoscritto <input class="editField" type="text" id="sottoscritto" name="sottoscritto" size="30"/>, relativamente agli art.17 
<br/>
<input type="hidden" id="riferimentoArt17" name="riferimentoArt17" value="<%=art17 %>"/>
<label class="layout"><%=art17 %></label> 
<br/>

elaborati e rilasciati presso il macello <%=OrgDetails.getName() %> del comune di <%=OrgDetails.getCity().toUpperCase() %> (<%=OrgDetails.getState().toUpperCase()%>), per la data di macellazione <%=toDateasString(Partita.getDataSessioneMacellazione()) %> 
<br/><br/>
<b>DICHIARA CHE</b></center><br/><br/>
È stata erroneamente digitata l'informazione riguardante i campi della partita con numero <b><u><%=Partita.getCd_partita() %></u></b>:<br/><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<col width="33%"><col width="33%">
<tr><th style="border:1px solid black;">Dati</th>
<th style="border:1px solid black;">Dati errati</th>
<th style="border:1px solid black;">Dati corretti</th></tr>

<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_numero" name="cb_numero" onClick="gestioneCb('numero')"> Il numero della partita</td>
<td><div id="div_numero_errati" style="visibility:hidden"><input class="layout" readonly type="text" id="numero_errato" name="numero_errato" value="<%=Partita.getCd_partita() %>"/></div>&nbsp;</td>
<td style="border:1px solid black;"><div id="div_numero_corretti" style="visibility:hidden"><input class="editField" type="text" id="numero" name="numero" size="20" onchange="Geocodifica.isCapoEsistenteUpdate('<%=Partita.getId() %>', this.value, '<%=Partita.getCd_codice_azienda_provenienza()%>', '<%=Partita.getCd_num_capi_ovini()%>', '<%=Partita.getCd_num_capi_caprini()%>', gestisciPartitaEsistente);" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/></div></td>
</tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_mod4" name="cb_mod4" onClick="gestioneCb('mod4')"> Il riferimento al Mod. 4 è</td>
<td style="border:1px solid black;"><div id="div_mod4_errati" style="visibility:hidden"><input class="layout" readonly type="text" id="mod4_errato" name="mod4_errato" value="<%=Partita.getCd_mod4() %>"/></div> &nbsp;</td>
<td style="border:1px solid black;"><div id="div_mod4_corretti" style="visibility:hidden"><input class="editField" type="text" id="mod4" name="mod4" size="20"/></div> &nbsp; </td>
</tr>
<% HashMap<String,ArrayList<Contact>> listaVeterinari = (HashMap<String,ArrayList<Contact>>)request.getAttribute("listaVeterinari"); %>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_veterinario_1" name="cb_veterinario_1" onClick="gestioneCb('veterinario_1')"> Veterinari addetti al controllo 1</td>
<td style="border:1px solid black;"><div id="div_veterinario_1_errati" style="visibility:hidden">
 <%=Partita.getCd_veterinario_1() %><br/>
 <input type="hidden" id="veterinari_cd1_errato" name="veterinari_cd1_errato" value="<%=Partita.getCd_veterinario_1()%>"/>
 </div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_veterinario_1_corretti" style="visibility:hidden">

<input type="hidden" id="veterinari_cd1_nome" name="veterinari_cd1_nome" value="<%=Partita.getCd_veterinario_1()%>"/>

<table>
		            	<tr class="containerBody" style="display: block;">
		                	<td>
								<select id="veterinari_cd1" name="veterinari_cd1" onchange="setVeterinarioNome('1', this.options[this.selectedIndex].text)">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Partita.getCd_veterinario_1() != null && Partita.getCd_veterinario_1().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %>  value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
								</select>
		                	</td>
		                  </tr>
		                  </table></div></td> </tr>
		                  
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_veterinario_2" name="cb_veterinario_2" onClick="gestioneCb('veterinario_2')"> Veterinari addetti al controllo 2</td>
<td style="border:1px solid black;"><div id="div_veterinario_2_errati" style="visibility:hidden">
 <%=Partita.getCd_veterinario_2() %><br/>
  <input type="hidden" id="veterinari_cd2_errato" name="veterinari_cd2_errato" value="<%=Partita.getCd_veterinario_2()%>"/>
 </div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_veterinario_2_corretti" style="visibility:hidden">
<input type="hidden" id="veterinari_cd2_nome" name="veterinari_cd2_nome" value="<%=Partita.getCd_veterinario_2()%>"/>
<table>
		            	<tr class="containerBody" style="display: block;">
			                <td>
				             	<select id="veterinari_cd2" name="veterinari_cd2"  onchange="setVeterinarioNome('2', this.options[this.selectedIndex].text)">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Partita.getCd_veterinario_2() != null && Partita.getCd_veterinario_2().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
								</select>
							</td>
			            </tr>
			               </table></div></td> </tr>
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_veterinario_3" name="cb_veterinario_3" onClick="gestioneCb('veterinario_3')"> Veterinari addetti al controllo 3</td>
<td style="border:1px solid black;"><div id="div_veterinario_3_errati" style="visibility:hidden">
 <%=Partita.getCd_veterinario_3() %><br/>
  <input type="hidden" id="veterinari_cd3_errato" name="veterinari_cd3_errato" value="<%=Partita.getCd_veterinario_3()%>"/>
 </div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_veterinario_3_corretti" style="visibility:hidden">
<input type="hidden" id="veterinari_cd3_nome" name="veterinari_cd3_nome" value="<%=Partita.getCd_veterinario_3()%>"/>
<table>		                
			            <tr 
			            	class="containerBody" 
			            	id="cd_veterinario_2_toggle">
			                <td>
				                <select id="veterinari_cd3" name="veterinari_cd3" onchange="setVeterinarioNome('3', this.options[this.selectedIndex].text)">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Partita.getCd_veterinario_3() != null && Partita.getCd_veterinario_3().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
								</select>
			                </td>
			            </tr>
		            </table>



</div> &nbsp; </td>
</tr>


<% ArrayList<DestinatarioCarni> listaDestinatari = Partita.getListaDestinatariCarniConIndice();
for (int k=0; k<listaDestinatari.size();k++){
	DestinatarioCarni dest = listaDestinatari.get(k);
	int i = dest.getIndice();%>
	
<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_destinatari_<%=i %>" name="cb_destinatari_<%=i %>" onClick="gestioneCb('destinatari_<%=i%>')"> Destinatario delle carni <%=i %></td>
<td style="border:1px solid black;"><div id="div_destinatari_<%=i%>_errati" style="visibility:hidden">

<input type="hidden"	name="destinatario_<%=i %>_in_regione_errato" value="true" 
id="inRegione_<%=i %>_errato" <%=(!dest.isInRegione()) ? ("") : ("checked=\"checked\"") %> /> 
<input type="hidden"	name="destinatario_<%=i %>_in_regione_errato" value="false" 
id="outRegione_<%=i %>_errato" <%=(!dest.isInRegione()) ? ("checked=\"checked\"") : ("") %> />

<input type="hidden" name="destinatario_<%=i %>_id_errato" id="destinatario_<%=i %>_id_errato" 
value="<%=(dest.getId() != -1) ? (dest.getId()) : ("-1") %>" />
<input type="hidden" name="destinatario_<%=i %>_nome_errato" id="destinatario_<%=i %>_nome_errato" 
value="<%=toHtmlValue( dest.getNome() ) %>" />
	
<%=dest.getNome() %> (<%=dest.getNumCapiOvini() %> ovini <%=dest.getNumCapiCaprini() %> caprini)
</div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_destinatari_<%=i %>_corretti" style="visibility:hidden">
<table>
<tr>
            	<td colspan="2">
		            <table width="100%" border="0" cellpadding="2" cellspacing="0" align="left">
			            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_<%=i %>_in_regione" 
							        	value="true" 
							        	onclick="selectDestinazione(<%=i %>)" 
							        	id="inRegione_<%=i %>" 
							        	<%=(!dest.isInRegione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_<%=i %>_in_regione" 
						        		value="false" 
						        		onclick="selectDestinazione(<%=i %>)" 
						        		id="outRegione_<%=i %>"
						        		<%=(!dest.isInRegione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatari/Esercenti</td>
					        <td>
						        <div 
						        	style="<%=(!dest.isInRegione()) ? ("display:none") : ("") %>" 
						        	id="imprese_<%=i%>">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', <%= (i==1) ? 1 : (i==2) ? 2 : (i+2) %>, 'impresa' );" onclick="selectDestinazione(<%=i %>);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', <%= (i==1) ? 3 : (i==2) ? 4 : (i+2) %>, 'stab');" onclick="selectDestinazione(<%=i %>);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa<%=i %>');" onclick="selectDestinazioneFromLinkTextarea(<%=i %>);" >[Inserisci Esercente non in G.I.S.A.]</a><br/> 
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(<%=i %>);" onclick="" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa<%=i %>" name="esercenteNoGisa<%=i %>" onchange="valorizzaDestinatario(this,'destinatario_<%=i %>');" ><%=toHtmlValue( dest.getNome() ) %></textarea>
						        </div>
						        <div  
						        	style="<%=(!dest.isInRegione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_<%=i%>">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione<%=i %>');" onclick="selectDestinazioneFromLinkTextarea(<%=i %>);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione<%=i %>" name="esercenteFuoriRegione<%=i %>" onchange="valorizzaDestinatario(this,'destinatario_<%=i %>');" ><%=toHtmlValue( dest.getNome() ) %></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_<%=i %>" align="center">
						        	<%=(dest.getId() != -1) ? (toHtmlValue( dest.getNome() )) : ("-- Seleziona Destinatario --") %>
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_<%=i %>_id" 
					        		id="destinatario_<%=i %>_id" 
					        		value="<%=(dest.getId() != -1) ? (dest.getId()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_<%=i %>_nome" 
					        		id="destinatario_<%=i %>_nome" 
					        		onchange=""
					        		value="<%=toHtmlValue( dest.getNome() ) %>" />
					        		<p id="destinatarioCarni<%=i %>" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
						    
						</tr>
					</table>
		  		</td>
			 </tr>
</table>

</div> &nbsp; </td>
</tr>

<%}%>


<tr>
<td style="border:1px solid black;"><input type="checkbox" id="cb_altro" name="cb_altro" onClick="gestioneCb('altro')"> Altro</td>
<td style="border:1px solid black;"><div id="div_altro_errati" style="visibility:hidden"><input class="editField" type="text" id="altro_errato" name="altro_errato" size="20"/></div> &nbsp; </td>
<td style="border:1px solid black;"><div id="div_altro_corretti" style="visibility:hidden"><input class="editField" type="text" id="altro_corretto" name="altro_corretto" size="20"/></div> &nbsp; </td>
</tr>
</table>
<br/>
Motivo della correzione: <input class="editField" type="text" id="motivo" name="motivo" size="70" value=""/> <br/><br/>

<table width="100%">
<col width="50%">
<tr>
<td>Data <br/>
<%=timeNow %></td>
<td><div align="right">Il veterinario ufficiale dell'ASL <%=aslList.getSelectedValue(OrgDetails.getSiteId())%></div></td>
</table>
<br/><br/><br/>
* <i>Il presente modulo rappresenta una notifica documentale. Eventuali correzioni sui dati dovranno essere effettuate separatamente.</i>
<br/><br/>
<input id="salvaId" type="button" value ="Salva e stampa" onclick="if (checkForm(this.form)){if (confirm('ATTENZIONE! La partita sarà aggiornata sulla base dell\'Errata Corrige generata. L\'utente si assume la responsabilità delle modifiche richieste.')){this.form.submit()}}" />
</form>
</body>