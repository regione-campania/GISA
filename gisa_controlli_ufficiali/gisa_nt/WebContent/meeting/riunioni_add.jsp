<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Referente"%>
<jsp:useBean id="listaLuoghi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaReferenti" class="org.aspcfs.modules.meeting.base.ReferenteList" scope="request"/>
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

function mostraAltro(value)
{
	
	if (value=='ALTRO')
		{
		document.getElementById("altro").style.display="";
		}
	else
		{
		document.getElementById("altro").style.display="none";
		}
}
function mostraAltroContesto(valore)
{
	if (valore=='ALTRO')
		{
		document.getElementById("altroContesto").style.display="";
		}
	else
		{
		document.getElementById("altroContesto").style.display="none";
		}
		}

function rimuoviPartecipante(field)
{
	
	field.closest("tr").remove();
}


var indiceRiga = 1;
function nuovoPartecipante(){
	
	
	$('#tablePartecipanti tr:last').after('<tr id="rigaPartecipante'+indiceRiga+'"><td nowrap class="formLabel"></td><td><input type = "text" name = "partecipanti" style="width: 400px;" required="required" >  <a href="#" onclick="rimuoviPartecipante(this); return false;"><img src="images/delete.gif"></a></td></tr>');
	indiceRiga++;
}




$(function() {
	$('#data').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});
});



</script>


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Nuova Riunione 
</td>
</tr>
</table>
<br>


<form method="post" action = "GestioneRiunioni.do?command=Insert&auto-populate=true" enctype="multipart/form-data" onSubmit="loadModalWindow()" acceptcharset="UTF-8">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">DATI RIUNIONE</dhv:label></strong>
    </th>
  </tr>
 
 
 
  <tr>
      <td nowrap class="formLabel">
        Contesto
      </td>
      <td>
        <%
        listaContesti.setJsEvent("onchange='mostraAltroContesto(this.value);'");
        %>
        <%=listaContesti.getHtmlSelectText("contesto", "GISA") %>
        <input type="text" name = "altroContesto" id = "altroContesto" value="" style="display: none">
      </td>
    </tr>
    
    <tr>
      <td nowrap class="formLabel">
        Oggetto
      </td>
      <td>
        <input type = "text" name = "titolo" required="required" style="width: 400px;"/>
      </td>
    </tr>
    
    
     <tr>
      <td nowrap class="formLabel">
        Luogo
      </td>
      <td>
      
      Sede <input type = "radio" value ="1" name = "sede" onclick="document.getElementById('luogoFisico').style.display='';">
      
        A Distanza <input type = "radio" value ="2" name = "sede" onclick="document.getElementById('luogoFisico').style.display='none';">
        
      </td>
    </tr>
    
     <tr style="display: none" id ="luogoFisico">
      <td nowrap class="formLabel">
        &nbsp;
      </td>
      <td>
      
      <%
      listaLuoghi.setRequired(true);
      listaLuoghi.setJsEvent("onchange='mostraAltro(this.value);'");
      %>
      <%=listaLuoghi.getHtmlSelecText("luogo", "US", false) %>
      <input type="text" name  ="altro" id = "altro" style="display: none">
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
    
     <tr>
      <td nowrap class="formLabel">
        Note
      </td>
      <td>
        <textarea rows="6" cols="80" name="descrizioneBreve" required="required"></textarea>
      </td>
    </tr>
    
    
    </table>
    <br>
    
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="tablePartecipanti">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">PARTECIPANTI</dhv:label></strong>
    </th>
  </tr>
     <tr>
      <td nowrap class="formLabel">
        Partecipanti
      </td>
      <td>
       <a href="#" onclick="javascript:nuovoPartecipante(); return false;" >Nuovo Partecipante</a>
      </td>
    </tr>
    
    <tr id="rigaPartecipante"><td nowrap class="formLabel"></td><td><input type = "text" name = "partecipanti" style="width: 400px;" required="required" >  <a href="#" disabled onclick="rimuoviPartecipante(this)"></a></td></tr>
    <tr id="rigaPartecipante"><td nowrap class="formLabel"></td><td><input type = "text" name = "partecipanti" style="width: 400px;" required="required" >  <a href="#" disabled onclick="rimuoviPartecipante(this)"></a></td></tr>
    
    </table>
    
    <br>
    
   <%@ include file="allegaFileRiunione.jsp" %>
    
    
  <br>
    
       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>LISTA REFERENTI</strong>
    </th>
  </tr>
    
     <tr>
      <td nowrap class="formLabel">
        Referenti Validazione
      </td>
      <td>
      <select name="referenti" multiple="multiple" required="required" size="<%=ListaReferenti.size()%>">
      
      <%
      for(Referente referente : (Vector<Referente>)ListaReferenti)
      {
    	  %>
    	  <option value="<%=referente.getId()%>" selected="selected"><%=referente.getNominativo()%></option>
    	  <%
      }
      %>
      
      </select>
      
      
      </td>
    </tr>
    
    
    </table>
    <br><br><br>
    <table>
     <tr>
    <td><input type = "submit" value = "Salva"></td>
    <td><input type = "button" value = "Annulla" onClick="location.href='GestioneRiunioni.do?command=SearchForm'"></td>
    </tr>
    </table>
    
    </form>
    
    
   