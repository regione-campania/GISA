<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunione"%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList"%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato"%>
<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Riunione"%>
<%@page import="org.aspcfs.modules.meeting.base.Rilascio"%> 

<jsp:useBean id="Riunione" class="org.aspcfs.modules.meeting.base.Riunione" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="listaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaStatiRef" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaContesti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null || timestring.equals("null"))
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto;
	  return toRet;
	  
  }%>
  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>

<script>
function checkFormApprovazione(form)
{
	testForm=true;
	msg='';
	
	if (form.stato.value=='5')
		{
		msg='Se si Seleziona lo stato Approvato con Revisione, occorre allegare la versione aggiornata del Verbale.\n';
		msg+='Controllare i seguenti campi :\n';
		if (form.subjectRevisione.value=='')
			{
			testForm=false;
			msg+='- Oggetto File Richiesto\n';
			}
		if (form.fileRevisione.value=='')
		{
		testForm=false;
		msg+='- File Richiesto\n';
		}
		
		
		}
	else
		{
		if (form.stato.value=='3')
		{
			msg+='Controllare i seguenti campi :\n';
			if (form.note.value=='')
				{
				testForm=false;
				msg+='- Note Richiesto\n';
				}
		}
		}
	
	if (testForm==false)
		alert(msg);
	
	
	return testForm;
	
	}

$(function () {
	$( "#approvazione" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	   	title:"APPROVAZIONE",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
	
$(function () {
	$( "#nuovaRevisioneVerbale" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	   	title:"APPROVAZIONE",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
	
	

	
	
$(function () {
	$( "#listaRevisioniVerbale" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	   	title:"LISTA REVISIONI VERBALE",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
	
$(function () {
	$( "#dettaglioRilascio" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"DETTAGLIO RILASCIO <input type=\"button\" value=\"CHIUDI\" onclick=\"javascript:$('#dettaglioRilascio').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#dettaglioRilascio").dialog("close"); } );	
	

function allegaRevisione()
{
var statoSelezionato = document.getElementById("stato").value;	

if (statoSelezionato=='5')
	{
		document.getElementById("tabAllegati").style.display="";
	}
else
	{
	document.getElementById("tabAllegati").style.display="none";
	}
}






function nuovaRevisione(idRiunione)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneRiunioni.do?command=ToAllegaNuovaRevisione',
	        data: { "id": idRiunione} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('nuovaRevisioneVerbale').innerHTML=msg ; 
	       		$('#nuovaRevisioneVerbale').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}

function listaRevisioniVerbale(headerVerbale)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneRiunioni.do?command=ListaRevisioniVerbale',
	        data: { "headerVerbale": headerVerbale} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('listaRevisioniVerbale').innerHTML=msg ; 
	       		$('#listaRevisioniVerbale').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}
function apriApprovazioneEdit(userIdReferente,idRiunione)
{
loadModalWindow();
	
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		cache: false,
  		url: 'GestioneRiunioni.do?command=EditApprovazione',
        data: { "id": idRiunione,"userId":userIdReferente} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		document.getElementById('approvazione').innerHTML=msg ; 
       		$('#approvazione').dialog('open');
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	
}


function apriDettaglioRilascio(idRilascio)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneRiunioni.do?command=DettaglioRilascio&popup=true',
	        data: { "id": idRilascio} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('dettaglioRilascio').innerHTML=msg ; 
	       		$('#dettaglioRilascio').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}
</script>


<%@ include file="../initPage.jsp" %>


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Dettaglio
</td>
</tr>
</table>

<dhv:container name="riunioni" selected="Invia" object="" param="<%="id="+Riunione.getId() %>">
<dhv:permission name="meeting-edit">
<% if(Riunione.getStato()!=Riunione.STATO_CHIUSO_NON_MODIFICABILE){ %>
<input type = "button" onclick="location.href='GestioneRiunioni.do?command=ChiudiRiunione&id=<%=Riunione.getId() %>'" value="CHIUSURA D'UFFICIO"/>
<%} %>
</dhv:permission>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni Riunione</strong>
    </th>
  </tr>
  
   <tr class="containerBody">
			<td nowrap class="formLabel">
      			Contesto
			</td>
			<td>
         		<%= toHtml(Riunione.getContesto()) %>&nbsp;
			</td>
		</tr>
		
 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Oggetto
			</td>
			<td>
         		<%= toHtml(Riunione.getTitolo()) %>&nbsp;
			</td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Luogo
			</td>
			<td>
         		<%= toHtml(Riunione.getLuogo()) %>&nbsp;
			</td>
		</tr>
		
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Note
			</td>
			<td>
         		<%= toHtml(Riunione.getDescrizioneBreve()) %>&nbsp;
			</td>
		</tr>
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Data
			</td>
			<td>
         		<%= toDateasString(Riunione.getData()) %>&nbsp;
			</td>
		</tr>
		
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      		 Partecipanti
			</td>
			<td>
         		 <%
      
      for (int i = 0 ; i < Riunione.getListaPartecipanti().size(); i++)
      {
    	out.println(""+Riunione.getListaPartecipanti().get(i)+"<br>");  
      }
         		%>
         		
			</td>
		</tr>
		</table>
		<br>
		
		<%
		boolean altriAllegati = false ;
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
    <th colspan="8">
      <strong>Verbale Riunione</strong>
    </th>
  </tr>
  

		<tr>
			<th><strong>Codice/ID</strong></th>
			<th><strong>Revisione</strong></th>
			<th><strong>Oggetto</strong></th>
			<th><strong>Verbale Principale</strong></th>
			<th><strong>Tipo</strong></th>
			<th><strong>Data caricamento</strong></th>
			<th><strong>Caricato/creato da</strong></th>
			<dhv:permission name="meeting-revisiona-view">
			<th><strong>Admin</strong></th>
			</dhv:permission>
		</tr>
	
			
			<%
	
	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleAllegatoRiunione doc = (DocumentaleAllegatoRiunione) listaAllegati.get(i);
				
			%>
			
			
			<tr  <%=(doc.isRevisione()) ? "style=\"background-color: yellow;\"" :"" %>  >
			<td><%=doc.getIdHeader() %> </td> 
			<td><a href="#" onclick="listaRevisioniVerbale('<%=doc.getIdHeader()%>')"><%="Rev."+doc.getNumeroRevisione() %></a> </td>
			<td>
			<a href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
			<% if (doc.getEstensione().equalsIgnoreCase("pdf")) {%>
			<img src="gestione_documenti/images/pdf_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("csv")) { %>
			<img src="gestione_documenti/images/csv_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("png") || doc.getEstensione().equals("gif") || doc.getEstensione().equals("jpg") || doc.getEstensione().equals("ico")) { %>
			<img src="gestione_documenti/images/img_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("rar") || doc.getEstensione().equals("zip")) { %>
			<img src="gestione_documenti/images/rar_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("xls")) { %>
			<img src="gestione_documenti/images/xls_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("doc")) { %>
			<img src="gestione_documenti/images/doc_icon.png" width="20"/>
			<%} else { %>
			<img src="gestione_documenti/images/file_icon.png" width="20"/>
			<%} %> 
			<%= doc.getOggetto() %> 
			</a>
			</td> 
			<td>
			<input type = "checkbox" disabled="disabled" <%=(doc.isPrincipale())? "checked" :"" %>/>
			</td>
			<td><%=(doc.getEstensione()!=null && !doc.getEstensione().equals("null")) ? doc.getEstensione() : "&nbsp;"%></td>
			<td><%=fixData( doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUserId() %>" /> </td> 
			<dhv:permission name="meeting-revisiona-view">
			<td>
			<%
			if(doc.isPrincipale() && Riunione.getStato()!=Riunione.STATO_CHIUSO_NON_MODIFICABILE)
			{
			%>
			<a href= "#" onclick="nuovaRevisione(<%=Riunione.getId()%>)">Revisiona</a>
			<%} %>
			</td>
			</dhv:permission>
			</tr>
		
			
		<%
				
		} else {%>
					<tr>
			<td colspan="8">Non sono presenti file in questa cartella.</td> 
		</tr>
		<%}%>
		
		</table>
		<br>
		
		
		<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
		<col width="10%">
		<tr><th colspan="7">Rilasci collegati</th></tr>
		
		<dhv:permission name="meeting-rilasci-add">
		<tr><td colspan="7"> <a href="GestioneRiunioni.do?command=AddRilascio&idRiunione=<%=Riunione.getId() %>">AGGIUNGI RILASCIO</a></td></tr>
		</dhv:permission>
		
		<tr><th>Data Rilascio</th>
		<th>Oggetto</th>
		<th>Contesto</th>
		<th>Modulo</th>
		<th>Funzione</th>
		<th>Note</th>
		<th>Dettaglio</th>
		</tr>
		
		<%for (int i = 0; i< Riunione.getListaRilasci().size(); i++) {
		Rilascio ril = (Rilascio) Riunione.getListaRilasci().get(i); %>
		<tr>
		<td><%=toDateasString(ril.getData()) %></td>
		<td><%=ril.getOggetto() %></td>
		<td><%=listaContesti.getSelectedValue(ril.getNoteIdContesto())%></td>
		<td><%=ril.getNoteModulo() %></td>
		<td><%=ril.getNoteFunzione() %></td>
		<td><%=ril.getNoteNote() %></td>
		<td><a href="#" onClick="apriDettaglioRilascio('<%=ril.getId() %>'); return false;">Apri</a></td>
		</tr>
		<%} %>
		</table>
		
		<br/>
		
		
		
		<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
		<tr><th colspan="6">Lista Referenti</th></tr>
  <tr>	
      <th > <strong>Nominativo</strong> </th>
      <th>Data Approvazione</th>
      <th > <strong>Commento</strong> </th>
      <th > <strong>Verbale</strong> </th>
      <th > <strong>Stato</strong> </th>
       <th > <strong>#</strong> </th>
		</tr>
		
		<%
		
		 for (int i = 0 ; i < Riunione.getListaReferenti().size(); i++)
	      {
	    	
			 %>
			 <tr>
			 <td><%=Riunione.getListaReferenti().get(i).getReferente().getNominativo() %></td>
			 <td><%=toDateasString(Riunione.getListaReferenti().get(i).getDataApprovazione()) %></td>
			 <td><%=Riunione.getListaReferenti().get(i).getNote() %></td>
			 <td>
			
			<%=toHtml(Riunione.getListaReferenti().get(i).getRevVerbale())%>
			
			 
			 
			 </td>
			 <td><%=listaStatiRef.getSelectedValue(Riunione.getListaReferenti().get(i).getStato()) %></td>
			 <td><%if(Riunione.getListaReferenti().get(i).getReferente().getUserId()==User.getUserId() && Riunione.getStato()!=Riunione.STATO_CHIUSO_NON_MODIFICABILE){ %>
			 <input type = "button" value = "<%=Riunione.getListaReferenti().get(i).getStato()==Riunione.STATO_BOZZA ? "APPROVAZIONE" : "MODIFICA" %> " onclick="javascript:apriApprovazioneEdit(<%=User.getUserId()%>,<%=Riunione.getId()%>)">
			 <%} %>
			 </td>
			 </tr>
			 <%
	    	
	      }
		%>
		
		</table>
		
		
		
		
		<div id = "listaRevisioniVerbale">
		
		</div>
		
		<div id = "approvazione">
		
		</div>
		
		<div id = "nuovaRevisioneVerbale">
		
		</div>
		
		<div id = "dettaglioRilascio">
		
		</div>
		</dhv:container>
		
		 
       
       
      
	