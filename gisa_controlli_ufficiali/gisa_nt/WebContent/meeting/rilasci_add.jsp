<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Vector"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>

<jsp:useBean id="Riunione" class="org.aspcfs.modules.meeting.base.Riunione" scope="request"/>
<jsp:useBean id="listaContesti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>


<%@ include file="../initPage.jsp" %>

<script>
function checkForm(form)
{
	if (form.idRiunione0.value==""){
			alert('Selezionare una riunione di riferimento.');
			return false;
	}
	loadModalWindow();
	return true;
}

function cercaRiunione(indice){
	
	  window.open('GestioneRiunioni.do?command=SearchFormDaRilasci&indiceRiunione='+indice,'popupSelect',
      'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

	
}

$(function() {
	$('#data').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});
});

function cancellaRiunione(indice){
	document.getElementById("idRiunione"+indice).value="";
	document.getElementById("titoloRiunione"+indice).innerHTML="";
	document.getElementById("cancellaRiunione"+indice).style.display="none";
}
</script>


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Nuovo rilascio
</td>
</tr>
</table>
<br>


<form method="post" action = "GestioneRiunioni.do?command=InsertRilascio&auto-populate=true" onSubmit="return checkForm(this)" acceptcharset="UTF-8">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">DATI RILASCIO</dhv:label></strong>
    </th>
  </tr>
 
  
    <tr>
      <td nowrap class="formLabel">
        Oggetto
      </td>
      <td>
        <input type = "text" name = "oggetto" required="required" style="width: 400px;"/>
      </td>
    </tr>
    
  <tr>
      <td nowrap class="formLabel">
        Data
      </td>
      <td>
        <input type = "text" name = "data" id = "data" required="required"/>
         <div style="display: none;"> 
            &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
        </div>
      </td>
    </tr>
   </table>
   
   <br/><br/>
   
   <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">NOTE DI RILASCIO</dhv:label></strong>
    </th>
  </tr>
     
    <tr>
      <td nowrap class="formLabel">
        Contesto
      </td>
      <td>
     
        <%=listaContesti.getHtmlSelect("noteIdContesto", 1) %>
       
      </td>
    </tr>
    
     <tr>
      <td nowrap class="formLabel">
        Modulo
      </td>
      <td>
        <input type = "text" name = "noteModulo" required="required" style="width: 400px;"/>
      </td>
    </tr>
    
     <tr>
      <td nowrap class="formLabel">
        Funzione/descrizione
      </td>
      <td>
         <input type = "text" name = "noteFunzione" required="required" style="width: 400px;"/>
      </td>
    </tr> 
    
 <tr>
      <td nowrap class="formLabel">
        Note
      </td>
      <td>
        <textarea name = "noteNote" required="required" style="width: 400px;"></textarea>
      </td>
    </tr>
    
    
    
    
    </table>
    <br>
    
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="tableRiunione">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">RIUNIONE DI RIFERIMENTO</dhv:label></strong>
    </th>
  </tr>
     <tr>
      <td nowrap class="formLabel">
        Riunione 1
      </td>
      <td>
      <input type="text" readonly name="idRiunione0" id="idRiunione0" required="required" size="3" style="display:none" value="<%=(Riunione.getId()>0)  ? Riunione.getId() : "" %>"/>
      <label id="titoloRiunione0" name="titoloRiunione0"><%=(Riunione.getId()>0)  ? StringEscapeUtils.escapeJavaScript(Riunione.getTitolo())+" "+StringEscapeUtils.escapeJavaScript(Riunione.getDescrizioneBreve()) : "" %></label>
        <a href="#" onclick="javascript:cercaRiunione('0'); return false;" >Cerca Riunione</a>
        
      </td>
    </tr>
    <th colspan="2">
      <strong><dhv:label name="">ALTRE RIUNIONI DI RIFERIMENTO</dhv:label></strong>
    </th>  
    
    <% for (int i =1; i<10; i++) { %>
    <tr>
      <td nowrap class="formLabel">
        Riunione <%=i+1 %>
      </td>
      <td>
      <input type="text" readonly name="idRiunione<%=i %>" id="idRiunione<%=i %>" required="required" size="3" style="display:none"/>
      <label id="titoloRiunione<%=i %>" name="titoloRiunione<%=i %>"></label>
       <a href="#" onclick="javascript:cancellaRiunione('<%=i %>'); return false;" style="display:none" id="cancellaRiunione<%=i%>">(Rimuovi)</a>
       <a href="#" onclick="javascript:cercaRiunione('<%=i %>'); return false;" >Cerca Riunione</a>
      </td>
    </tr>
    
    <%} %>
    
    
   
    </table>
    
    <br><br><br>
    <table>
     <tr>
    <td><input type = "submit" value = "Salva"></td>
    <td><input type = "button" value = "Annulla" onClick="location.href='GestioneRiunioni.do?command=SearchForm'"></td>
    </tr>
    </table>
    
    </form>
    
    
   