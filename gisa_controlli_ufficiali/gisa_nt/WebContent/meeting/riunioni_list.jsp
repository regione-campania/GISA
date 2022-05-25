<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Riunione"%>
<%@page import="org.aspcfs.modules.meeting.base.Rilascio"%>

<jsp:useBean id="RiunioneList" class="org.aspcfs.modules.meeting.base.RiunioneList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="listaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchRiunioniListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<%@ include file="../initPage.jsp" %>
<script>




function cambiaStato(nuovoStato,idRiunione,userIdReferenteApprovazione)
{
	
	
	if (nuovoStato==3) // respint
	{
		if (  document.getElementById("note").value=="" )
			alert('Indicare nel campo note una motivazione');
		else
			{
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		cache: false,
  		url: 'GestioneRiunioni.do?command=CambiaStatoReferente',
        data: { "id": idRiunione,"stato":nuovoStato, "userIdReferente" :userIdReferenteApprovazione} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		location.href="GestioneRiunioni.do?command=Search";
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
			}
}else
	{
	
	$.ajax({
    	type: 'POST',
   		dataType: "html",
   		cache: false,
  		url: 'GestioneRiunioni.do?command=CambiaStatoReferente',
        data: { "id": idRiunione,"stato":nuovoStato, "userIdReferente" :userIdReferenteApprovazione} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		location.href="GestioneRiunioni.do?command=Search";
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	}
	
}

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


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Risultati Ricerca
</td>
</tr>
</table>




<dhv:pagedListStatus  object="SearchRiunioniListInfo"/>

<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   <th nowrap> <strong>Contesto</strong> </th>
      <th nowrap> <strong>Oggetto</strong> </th>
      <th nowrap> <strong>Note</strong> </th>
      <th nowrap> <strong>Luogo</strong> </th>
      <th nowrap> <strong>Data</strong> </th>
      <th nowrap> <strong>Partecipanti</strong> </th>
      <th nowrap> <strong>Stato</strong> </th>
      <th nowrap> <strong>Rilasci</strong> </th>
      <th nowrap> <strong>Azione</strong> </th>
       
    
  </tr>
  
  <%
  for (Riunione riunione : (Vector<Riunione>)RiunioneList)
  {
	  
	  %>
	  <tr>
	  <td>
	  <%=toHtml(riunione.getContesto()) %>
	  </td>
	   <td> <%=riunione.getTitolo() %> </td>
      <td > <%=riunione.getDescrizioneBreve() %> </td>
      <td > <%=riunione.getLuogo() %> </td>
      <td > <%=toDateasString(riunione.getData()) %> </td>
      <td > 
      <%
      
      for (int i = 0 ; i < riunione.getListaPartecipanti().size(); i++)
      {
    	out.println(""+riunione.getListaPartecipanti().get(i)+"<br>");  
      }
      %>
      
      </td>
      
     
      <td><%=listaStati.getSelectedValue(riunione.getStato()) %></td>
      <td>
       <%
      for (int i = 0 ; i < riunione.getListaRilasci().size(); i++)
      {
    	Rilascio rilascio = (Rilascio) riunione.getListaRilasci().get(0);  %>
    	<a href="#" onClick="apriDettaglioRilascio('<%=rilascio.getId() %>'); return false;"><%=toDateasString(rilascio.getData()) %></a><br/>
    	
    	<%
      }
      %>
      
      </td>
      <td><a href="GestioneRiunioni.do?command=DettaglioRiunione&id=<%=riunione.getId() %>">Apri</a></td>
      
     
	  </tr>
	  
	  <%
	  
  }
  %>
  
  </table>
  
  <dhv:pagedListControl object="SearchRiunioniListInfo" tdClass="row1"/>
  
  <div id="dettaglioRiunione"></div>
  
  <div id = "dettaglioRilascio"></div>