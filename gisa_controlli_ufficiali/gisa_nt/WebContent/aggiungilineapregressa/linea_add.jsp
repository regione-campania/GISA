<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">

<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script type="text/javascript">
var jQuery_1_7_2 = $.noConflict(true);
</script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal99 = new CalendarPopup();
	cal99.showYearNavigation();
	cal99.showYearNavigationInput();
	cal99.showNavigationDropdowns();
</SCRIPT>
<script src="javascript/noscia/addNoScia.js"></script>
<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<%@page import="org.aspcfs.modules.opu.base.*"%>

<%@ include file="../initPage.jsp" %>
  
  <script> function checkForm(form){
	  var idLinea = form.idLineaProduttiva.value;
	  var dataInizio = form.dataInizio.value;
	  var dataInizioLinea = form.dataInizioLinea.value;
	  var dataFineLinea = form.dataFineLinea.value;
	  var lineaEsistente = document.getElementById(idLinea);
	  var esito = true;
	  var msg = "Impossibile proseguire. Controllare i seguenti errori:\n\n";
	  if (idLinea != parseInt(idLinea, 10)){
		  esito = false;
	  	  msg+="Selezionare una linea!\n";
	  }
	  if (dataInizioLinea == ''){
		  esito = false;
		  msg+="Selezionare una data di inizio della linea!\n";
	  }
	  /*
	  if (confrontoDate(dataInizioLinea, dataInizio)==2){
		  esito = false;
		  msg+="La data inizio della linea deve essere maggiore o uguale alla data inizio attività dello stabilimento.\n";
	  }
	  */
	  if (dataFineLinea!= '' && confrontoDate(dataFineLinea, dataInizioLinea)==2){
		  esito = false;
		  msg+="La data inizio della linea deve essere maggiore o uguale alla data fine della linea.\n";
	  }
	  if (lineaEsistente!=null){
		  esito = false;
		  msg+="La linea selezionata è già presente sullo stabilimento!";
	  }
	  
	  if (!esito){
		  alert (msg);
		  return false;
	  }
	  
       //inizio verifica esistenza ed eventuale obbligatorieta cun
		var messagioerrore = '';
		var esitocun = false;
		var cun_popup_inserito = document.getElementById('codice_nazionale').value;
	  	var url = "CunRichiesto.do?command=Search&codiceLinea=" + idLinea;
		
		var request = $.ajax({
			url : url,
			async: false,
			dataType : "json"
		});
		
		request.done(function(result) {
			
			if(result.cun_obbligatorio == '1'){
				if(cun_popup_inserito.trim() != ''){
					esitocun = true;
				}else{
					messagioerrore = 'Attenzione! La linea selezionata richiede obbligatoriamente il cun';
					esitocun = false;
				}
			}else{
				esitocun = true;
			}
		});
		request.fail(function(jqXHR, textStatus) {
			console.log('Error');
		});

		if (esitocun){
			if(!verificaEsistenzaCun(document.getElementById('codice_nazionale'))){
				return false;
			}	
		}else{
			alert(messagioerrore);
			return false;
		}
		//fine verifica esistenza ed eventuale obbligatorieta cun
		
	  
	  if (confirm('Confermare la linea con questi dati?')){
		 form.idLinea.value = idLinea;
		 loadModalWindow();
		 form.submit();
	  }
  }
  
  function annulla(form){
		if (confirm('ATTENZIONE! Annullare le modifiche?')){
			loadModalWindow();
			location.href="OpuStab.do?command=Details&stabId="+<%=StabilimentoDettaglio.getIdStabilimento()%>;
			 }
		
	}
  </script>
  
  
<%
String nomeContainer = StabilimentoDettaglio.getContainer();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
nomeContainer = "suap";
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"&altId="+StabilimentoDettaglio.getAltId();
%>
<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">
    		
<form id = "addAccount" name="addAccount" action="AggiungiLineaPregressa.do?command=Insert&auto-populate=true" method="post">
<input type="hidden" id="idStab" name="idStab" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>	
<input type="hidden" id="idLinea" name="idLinea" value=""/>	

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Riepilogo</th></tr>


<tr>
<td class="formLabel">Numero registrazione</td>
<td><%=StabilimentoDettaglio.getNumero_registrazione() %></td>
</tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td><%=StabilimentoDettaglio.getOperatore().getRagioneSociale() %></td>
</tr>

<tr>
<td class="formLabel">Data inizio attivita'</td>
<td><%=toDateasString(StabilimentoDettaglio.getDataInizioAttivita()) %>
<input type="hidden" id="dataInizio" name="dataInizio" value="<%=toDateasString(StabilimentoDettaglio.getDataInizioAttivita())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">Linee produttive</td>
<td>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<% for (int i = 0; i<StabilimentoDettaglio.getListaLineeProduttive().size(); i++){
	LineaProduttiva linea = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
	%>
<tr>
<td><%=linea.getNumeroRegistrazione() %></td>
<td><%=linea.getDescrizione_linea_attivita().replace("->", "-><br/>") %>
<input type="hidden" id="<%=linea.getIdAttivita()%>" name="<%=linea.getIdAttivita()%>" value="<%=linea.getIdAttivita()%>"/></td>
</tr>
<% } %>
</table>

</td>
</tr>
	
	
<tr>
<td class="formLabel">Aggiungi linea pregressa</td>
<td>

<jsp:include page="../gestioneml/navigaml.jsp">
<jsp:param name="rev" value="<%=org.aspcfs.modules.suap.utils.SuapDwr.getMaxRevMl() %>" />
<jsp:param name="flagFisso" value="true" />
<jsp:param name="flagMobile" value="false" />
<jsp:param name="flagApicoltura" value="false" />
<jsp:param name="flagRegistrabili" value="true" />
<jsp:param name="flagRiconoscibili" value="false" />
<jsp:param name="flagSintesis" value="false" />
<jsp:param name="flagBdu" value="false" />
<jsp:param name="flagVam" value="false" />
<jsp:param name="flagNoScia" value="false" />
</jsp:include>

</td>
</tr>

<tr><td class="formLabel">DATA INIZIO</td> <td><input type="text" size="15" name="dataInizioLinea" id="dataInizioLinea" value="" class="required"  placeholder="dd/MM/YYYY" ></td></tr>
<tr><td class="formLabel">DATA FINE</td> <td><input type="text" size="15" name="dataFineLinea"  id="dataFineLinea" value="" placeholder="dd/MM/YYYY"></td></tr>
<tr><td class="formLabel">CUN</td><td><input type = "text" id = "codice_nazionale" name = "codice_nazionale"></td></tr>

<tr><td align="center"><input type="button" value="ANNULLA" onClick="annulla(this.form)"/></td>
<td align="center"><input type="button" value="CONFERMA" onClick="checkForm(this.form)"/></td></tr>

	
</table>
</form>

</dhv:container>


</body>
</html>


<div style="display: none;"> 
    &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
	</div>
	 
<script>
$(function() {
	$('#dataInizioLinea').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: document.getElementById('dataInizio').value, showOnFocus: false, showTrigger: '#calImg'}); 
	$('#dataFineLinea').datepick({dateFormat: 'dd/mm/yyyy', maxDate: '', showOnFocus: false, showTrigger: '#calImg',  onClose: controlloDate}); 
});

function controlloDate(){
	if(document.getElementById(this.id.replace("Fine","Inizio")).value==""){
		alert("ATTENZIONE! Inserire prima la data inizio linea.".toUpperCase());	
		this.value="";
	}
}

function confrontoDate(data_iniziale ,data_finale){
	if ($.datepicker.parseDate('dd/mm/yy', data_finale) > $.datepicker.parseDate('dd/mm/yy', data_iniziale))
		return 2;
	else
		return 1;
	}
	</script>
	
	