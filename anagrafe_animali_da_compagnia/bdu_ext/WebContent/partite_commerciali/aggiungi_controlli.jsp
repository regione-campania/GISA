<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="registrazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="animaleList" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList" scope="request" />
<jsp:useBean id="partita" class="org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale" scope="request"/>
<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>


<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
 <SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script>

function popolaCampi(){
		 $.ajax({
		        type: "GET",
		        url: "<%=request.getContextPath() %>/ServletForm?&idTipoRegistrazione=26",
		        async: false,
		        dataType: "json",
		        error: function(XMLHttpRequest, status, errorThrown) {
		            alert("oh no!");
		           // alert(status);
		        },
		        success: function (data, status) {
		        	var  toinsert = '';
		        	var html = '';
		        	var proprietario = "";
		        	for (var i = 0; i < data.length; i++) {
		        	    var row = data[i];
		        	    		
		        	            var label = row['label'];
		        	   			var type = row['type'];
		        	   			var name = row['name'];
		        	   			var lookupname = row['lookup'];
		        	   			var value = row['value'];
		        	        	//alert(type);
		        	  if (type == 'data'){
		            	//  alert ('in if');
		        		  toinsert = toinsert + '<tr>  <td class="formLabel"> <dhv:label name="">'+label+'</dhv:label></td> <td>';
		                  toinsert = toinsert + '<input  readonly type="text" id="'+name+'" name="'+name+'" size="10" value="" nomecampo="'+name+'" tipocontrollo="T2,T6,T7" labelcampo="'+name+'" />&nbsp;'
		                  toinsert = toinsert + '<a href="#" onClick="cal19.select(document.forms[0].'+name+',\'anchor19\',\'dd/MM/yyyy\'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td></tr>'; 
							if (name == 'dataEsitoControlloLaboratorio'){
				        		 // alert(toinsert);
							} 
		        	  }else if (type == 'select'){

							 html = row['html'];
		            	  
		        		//  var toinsert = toinsert + '<tr><td><a href="javascript:popUp(\'Soggetto.do?command=Search&tipologiaSoggetto=1&popup=true\');">Ricerca soggetto</a></td></tr>'
		        		   toinsert = toinsert + '<tr><td class="formLabel"> <dhv:label name="">'+label+'</dhv:label></td><td>'+html+'</td></tr>'
		            	  }else if (type == 'textarea'){
							toinsert = toinsert + '<tr><td class="formLabel" nowrap>'+label+'</td><td><textarea rows="10" cols="10" name="'+name+'" id="'+name+'"></textarea></td></tr>';
			            	  
		            	  } else if (type == 'hidden'){

						
		          	  
		        		    toinsert = toinsert + '<input type = "'+type+'" name="'+name+'" id="'+name+'" value ="'+value+'"/>';
		          	  }
		        	  else{
		            	 
		        	  	var  toinsert = toinsert + '<tr><td class="formLabel" nowrap>'+label+'</td><td><input type = "'+type+'" name="'+name+'" id="'+name+'" value="'+value+'" /></td></tr>';
		        	  }
		        	}

		            
		            $("#datireg").html("");
		            $("#datireg").append(toinsert);

		           
		           
		          
		        },
		        complete: function() {
		        //	hideById();
		        	$("#idDecisioneFinale").val( "1" ).attr('selected',true);
		        	$("#idDecisioneFinale").prop('disabled', 'disabled');
		        }
		    });
	}



$(document).ready(function() {

	   popolaCampi();
	  // alert( $("#idDecisioneFinale").val());

		
	});


</script>

<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0"> 
<tr>
<td width="100%">
  <a href="AnimaleAction.do"><dhv:label name="anagrafica.animale">Animale</dhv:label></a> >
  <dhv:label name="anagrafica.animale.aggiungi">Aggiungi registrazione</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<body>
<form name="inserisciControlli" action="PartiteCommerciali.do?command=InserisciControlliLiberi&auto-populate=true" method="post">
 <input type="hidden" name="doContinue" id="doContinue" value="">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Informazioni</dhv:label></strong>
	  </th>
  </tr>
  
  	<tr>
  		<td class="formLabel" nowrap>
		Asl di riferimento partita
		</td>
		<td><%=aslList.getSelectedValue(partita.getIdAslRiferimento()) %></td>
		<input type = "hidden" name = "idAslRiferimento" id = "idAslRiferimento" value= "<%=partita.getIdAslRiferimento() %>" />
		<input type= "hidden" name= "idPartita" id= "idPartita" value= "<%=partita.getIdPartitaCommerciale() %>" />
	</tr>  
	<tr>
		<td class="formLabel" nowrap>
		Microchips
		</td>
		<td>
		<%for (int k = 0; k < animaleList.size(); k++){
			Animale thisAnimale = (Animale) animaleList.get(k);
			if (k != 0){
			%>
		
		,<%}%>
		<%=thisAnimale.getMicrochip() %>
		<%} %>
		</td>
	</tr>  
<tr><td class="formLabel" nowrap>
Proprietario
</td><td>
<%if (partita.getOperatoreCommerciale() != null && partita.getOperatoreCommerciale().getRappLegale() != null ) { %>
<%= toHtml(partita.getOperatoreCommerciale().getRappLegale().getNome() + " " + partita.getOperatoreCommerciale().getRappLegale().getCognome()) %>
<%} %></td>

</tr> 
</table>
</br></br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Tipologia registrazione</dhv:label></strong>
	  </th>
  </tr>
<tr><td class="formLabel" nowrap>
Registrazione
</td><td><%= registrazioniList.getHtmlSelect("idTipologiaEvento", evento.getIdTipologiaEvento()) %></td></tr>  </table>

</br></br>
<table id="" cellpadding="2" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Dati da inserire</dhv:label></strong>
	  </th>
  </tr>
<tr id="datireg"></tr>

</table>
<!-- input type="submit" value="invia"/-->
<input type="button" value="invia" onclick="if (this.form.dataInserimentoControlli.value!=''){this.form.submit()} else {alert('Data di registrazione controlli obbligatoria.')};" />


</form>
