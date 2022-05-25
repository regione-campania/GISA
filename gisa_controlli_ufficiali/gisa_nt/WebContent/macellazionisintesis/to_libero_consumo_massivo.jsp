<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request" />
<%@page import="org.aspcfs.modules.macellazionisintesis.base.Capo"%>

<%@ include file="../initPage.jsp"%>



<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<script>

function checkForm(form){
	var ret = true;
	var msg ="";
	var okay = false;
	
	var checkboxs=document.getElementsByTagName("input");
	
	for( i=0,l=checkboxs.length;i<l;i++)
    {
        if(checkboxs[i].type=='checkbox' && checkboxs[i].checked)
        {
        	okay = true;
            break;
        }
    }
	
	if (okay==false){
		ret = false;
		msg+="Selezionare almeno una matricola.\r\n";
	}
		
	if( trim(form.destinatario_1_nome.value).length == 0 && trim(form.destinatario_1_nome.value).length == 0 ){
		ret = false;
		msg+="Inserire almeno un Destinatario delle Carni.\r\n";
	}
	
	if (ret == true){
		loadModalWindow();
		form.submit();
	}
	else {
		alert(msg);
		return false;
	}
	
	
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

function valorizzaDestinatario(campoTextarea,idDestinatario){
	document.getElementById(idDestinatario + '_nome').value = campoTextarea.value;
	document.getElementById(idDestinatario + '_id').value = -999;
}

function impostaDestinatarioMacelloCorrente(index){
	document.getElementById('destinatario_' + index + '_id').value = document.getElementById('id_macello').value;
	document.getElementById('destinatario_' + index + '_nome').value = "<%=OrgDetails.getOperatore().getRagioneSociale().replaceAll("\"","'")%>";
	document.getElementById('destinatario_label_' + index).innerHTML = "<%=OrgDetails.getOperatore().getRagioneSociale().replaceAll("\"","'")%>";
	document.getElementById('esercenteNoGisa' + index).style.display = 'none';
};



function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 


function mostraTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).style.display = '';
}

function nascondiTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).value = '';
	document.getElementById(idTextarea).style.display = 'none';
}

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

</script>


<table class="trails" cellspacing="0">
	<tr>
		<td> <%
			if (request.getParameter("return") == null)
				{
		%>  <a
			href="StabilimentoSintesisAction.do?command=DettaglioStabilimento&stabId=<%=OrgDetails.getIdStabilimento()%>">Scheda
				Stabilimento</a> > <%
 	}
 		else if (request.getParameter("return").equals("dashboard"))
 		{}
 %> Libero consumo massivo</td>
	</tr>
</table>




<% ArrayList<Capo> listaCapi = (ArrayList<Capo>) request.getAttribute("listaCapi"); %>

<%String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());%>

<dhv:container
	name="sintesismacelli"
	selected="macellazioni" object="Operatore" param="<%=param1%>"
	appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'
	>



<form name="main" action="MacellazioniSintesis.do?command=LiberoConsumoMassivo" method="post">


<table class="details" width="100%" cellpadding="9">
<tr><th>Lista matricole</th><th> Destinatario carni</th></tr>
<col width="10%"><col width="50%">

<tr>
<td valign="top">
<table class="details" cellpadding="9" width="100%" id="listaCapi">

<tr> <th>Data macellazione</th> <th>Matricola</th> <th>Seleziona</th></tr>


<% for (int i = 0; i<listaCapi.size(); i++){
	Capo c = (Capo) listaCapi.get(i);%>
	
<tr> 
<td><%= toDateasString(c.getVpm_data()) %></td>
<td><%= c.getCd_matricola() %> <input type="hidden" id="id_<%=i %>" name="id_<%=i %>" value="<%=c.getId() %>"/> </td>
<td><input type="checkbox" id="cb_<%=i %>" name="cb_<%=i %>" <%=c.getVpm_data()==null ? "disabled" : "" %>/> </td>
</tr>
	
	<% }%>

</table>


</td>

<td valign="top">



<table>

  <tr>
   <td class="formLabel" colspan="3" align="left"><strong>Destinatario Carni</strong></td>
            	</tr>
            	
	            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_1_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(1)" 
							        	id="inRegione_1" 
							        	 /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_1_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(1)" 
						        		id="outRegione_1"
						        	 />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(2)" 
						        		id="inRegione_2" 
						        		 /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(2)" 
						        		id="outRegione_2"
						        		/>
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="" 
						        	id="imprese_1">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 1, 'impresa' );" onclick="selectDestinazione(1);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 3, 'stab');" onclick="selectDestinazione(1);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa1');" onclick="selectDestinazioneFromLinkTextarea(1);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(1);" onclick="" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa1" name="esercenteNoGisa1" onchange="valorizzaDestinatario(this,'destinatario_1');" ></textarea>
						        </div>
						        <div  
						        	 style="display:none"
						        	id="esercenti_1">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 1 );" >[Seleziona Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione1');" onclick="selectDestinazioneFromLinkTextarea(1);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione1" name="esercenteFuoriRegione1" onchange="valorizzaDestinatario(this,'destinatario_1');" ></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_1" align="center">
						        	-- Seleziona Destinatario --
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_id" 
					        		id="destinatario_1_id" 
					        		value="-1" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_nome" 
					        		id="destinatario_1_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni1" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
						    <td>
						    	<div style="" id="imprese_2">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 2, 'impresa' );" onclick="selectDestinazione(2);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 4, 'stab');" onclick="selectDestinazione(2);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa2');" onclick="selectDestinazioneFromLinkTextarea(2);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(2);" onclick="" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa2" name="esercenteNoGisa2" onchange="valorizzaDestinatario(this,'destinatario_2');" ></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_2">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 2 );" >[Seleziona Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione2');" onclick="selectDestinazioneFromLinkTextarea(2);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione2" name="esercenteFuoriRegione2" onchange="valorizzaDestinatario(this,'destinatario_2');" ></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_2" align="center">
						        	-- Seleziona Destinatario --
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_id" 
					        		id="destinatario_2_id" 
					        		value="-1" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_nome" 
					        		id="destinatario_2_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni2" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
				
			            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td width="43%"> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_3_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(3)" 
							        	id="inRegione_3" 
							        	 /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_3_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(3)" 
						        		id="outRegione_3"
						        		 />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(4)" 
						        		id="inRegione_4" 
						        		 /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(4)" 
						        		id="outRegione_4"
						        		 />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="" 
						        	id="imprese_3">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'impresa' );" onclick="selectDestinazione(3);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'stab');" onclick="selectDestinazione(3);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa3');" onclick="selectDestinazioneFromLinkTextarea(3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(3);" onclick="" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa3" name="esercenteNoGisa3" onchange="valorizzaDestinatario(this,'destinatario_3');" ></textarea>
						        </div>
						        <div  
						        	 style="display:none"
						        	id="esercenti_3">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione3');" onclick="selectDestinazioneFromLinkTextarea(3);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione3" name="esercenteFuoriRegione3" onchange="valorizzaDestinatario(this,'destinatario_3');" ></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_3" align="center">
						        	-- Seleziona Destinatario --
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_id" 
					        		id="destinatario_3_id" 
					        		value="-1" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_nome" 
					        		id="destinatario_3_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni3" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
							
							
						    <td>
						    	<div style="" id="imprese_4">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'impresa' );" onclick="selectDestinazione(4);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'stab');" onclick="selectDestinazione(4);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa4');" onclick="selectDestinazioneFromLinkTextarea(4);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(4);" onclick="" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa4" name="esercenteNoGisa4" onchange="valorizzaDestinatario(this,'destinatario_4');" ></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_4">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione4');" onclick="selectDestinazioneFromLinkTextarea(4);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione4" name="esercenteFuoriRegione4" onchange="valorizzaDestinatario(this,'destinatario_4');" ></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_4" align="center">
						        	-- Seleziona Destinatario --	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_id" 
					        		id="destinatario_4_id" 
					        		value="-1" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_nome" 
					        		id="destinatario_4_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni4" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>

<tr><td colspan="4" align="center">
<input type="button" value="PROCESSA LIBERO CONSUMO MASSIVO" onClick="checkForm(this.form)"/>
</td></tr>

</table>



 </td> </tr>

</table>


<input type="hidden" name="id_macello" id="id_macello" value="<%=OrgDetails.getAltId() %>" />
<input type="hidden" name="sizeLista" id="sizeLista" value="<%=listaCapi.size() %>" />


</form>
</dhv:container>