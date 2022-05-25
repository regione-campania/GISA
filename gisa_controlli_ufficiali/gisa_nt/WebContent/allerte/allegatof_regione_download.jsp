<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%><jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="id_allerta" class="java.lang.String" scope="request"/>
<jsp:useBean id="Allerta" class="org.aspcfs.modules.allerte.base.Ticket" scope="request"/>
<%SiteIdList.setSelectSize(5); %>

<script> function getAsl(){
		var sel = document.getElementById('asl_regione_corrente');
		var idAsl = sel.value;
		return idAsl;}

	 function getTipoDownload(){
		 var rates = document.getElementsByName('tipo_download');
		 var rate_value;
		 for(var i = 0; i < rates.length; i++){
		     if(rates[i].checked){
		         rate_value = rates[i].value;
		     }
		 }
		return rate_value;}
		</script>

<script>
function bottoneGestore(input){
	
	
	if (input.checked == true && input.value=='pdf') { 
	    document.getElementById("WordButton").style.display = "none";
	    document.getElementById("PdfButton").style.display = "block";
	  } else if (input.checked == true && input.value=='doc') {
	    document.getElementById("WordButton").style.display = "block";
	    document.getElementById("PdfButton").style.display = "none";
	  }
}
</script>


<form name = "details" method="post" action="" onsubmit="window.close()">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr >
			<th colspan="2"><strong>Scarica Allegato F</strong></th>
		</tr>
		<tr class="containerBody">
		<td nowrap class="formLabel">Tipologia Allegato</td>
		<td >Globale <input type = "radio" name = "tipo_download" id = "tipo_download" value = "1" checked="checked" onclick="document.getElementById('asl').style.display='none'"> Per ASL <input type = "radio" name = "tipo_download" id = "tipo_download" value = "2" onclick="document.getElementById('asl').style.display=''"></td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Tipologia Allegato</td>
		<td >Pdf <input type = "radio" name = "tipo_file" value = "pdf" checked="checked" onChange="bottoneGestore(this)">  Word <input type = "radio" name = "tipo_file" value = "doc" onChange="bottoneGestore(this)"></td>
		</tr>
		
		
	
		<tr class="containerBody" style="display: none;" id = "asl">
		<td nowrap class="formLabel">Seleziona Asl</td>
		<td >
		<%Iterator<String> itAslAllerta = Allerta.getAsl_coinvolte().keySet().iterator();
		%>
		<select name = "asl_regione_corrente"  id = "asl_regione_corrente" size="5">
		<%while(itAslAllerta.hasNext())
			{
			String asl = itAslAllerta.next();
			int id = SiteIdList.getIdFromValue(asl);
			%>
			<option value = "<%=id %>" selected="selected"><%=asl %></option>
			<%
			}%>
		</select>
		</td>
		</tr>
		<tr class="containerBody" >
		<td nowrap class="formLabel"></td>
		<td >
		
		
		 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
		<input type = "button" id="WordButton" name="WordButton" style="display:none" value = "Scarica Allegato F (WORD) " onclick="javascript : document.details.action ='TroubleTicketsAllerte.do?command=DownloadAllegatoF&ticketid=<%=id_allerta %>'; document.details.submit(); ">
		
         <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
		
		<!-- input type = "button" value = "Scarica Allegato F " onclick="javascript : document.details.action ='TroubleTicketsAllerte.do?command=StampaAllegatoF&ticketid=<%=id_allerta %>'; document.details.submit(); "-->
		
		
		   <input type="button" id="PdfButton" name="PdfButton"  title="Scarica Allegato F " value="Scarica Allegato F (PDF)" onClick="openRichiestaPDF('<%= id_allerta %>', getAsl(), getTipoDownload(), '-1', '-1', 'AllegatoF');">
 	
						
		
</td>
		</tr>
		
	</table>
<input type = "hidden" name = "tipoAlimenti" value = "<%=request.getAttribute("tipoAlimenti") %>">
<input type = "hidden" name = "specie_alimenti" value = "<%=request.getAttribute("specie_alimenti") %>">

</form>
