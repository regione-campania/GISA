<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="gruppiUtente" type="java.util.ArrayList" scope="request" />
    
    <%@ include file="../../initPage.jsp"%>

  <% UserBean user = (UserBean) session.getAttribute("User");%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %> 

<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_screen.css" type="text/css" media="screen" />
<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_print.css" type="text/css" media="print" />


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

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
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%@ include file="js/registro_sanzioni_js.jsp"%>

<script>
function openRicercaGlobale(){
	var res;
	var result;

	
		window.open('GlobalSearch.do?command=SearchFormImprese',null,
		'height=500px,width=7000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
		
		
		} 
	
function gestisciEnteAccertato(form){
	form.trasgressore.value="-1";
	form.obbligato.value="-1";
	form.trasgressore_altro.value="";
	form.obbligato_altro.value="";
	form.trasgressore.onchange();
	form.obbligato.onchange();
	
	if (form.trasgressore[1].value != "ALTRO")
		form.trasgressore.remove(1);
	
	if (form.obbligato[1].value != "ALTRO")
		form.obbligato.remove(1);
	
	var option = document.createElement("option");
	option.text = form.descrizione_impresa_sanzionata.value;
	option.value = form.descrizione_impresa_sanzionata.value;
	
	var option2 = document.createElement("option");
	option2.text = form.descrizione_impresa_sanzionata.value;
	option2.value = form.descrizione_impresa_sanzionata.value;
	
	form.trasgressore.add(option, form.trasgressore[1]);
	form.obbligato.add(option2, form.obbligato[1]);
}

function gestisciTrasgressore(form){
	if (form.trasgressore.value=="ALTRO")
		form.trasgressore_altro.style.display="block";
	else{
		form.trasgressore_altro.style.display="none";
		form.trasgressore_altro.value="";
	}
		
}

function gestisciObbligato(form){
	if (form.obbligato.value=="ALTRO")
		form.obbligato_altro.style.display="block";
	else{
		form.obbligato_altro.style.display="none";
		form.obbligato_altro.value="";
	}
		
}

function annulla(){
	if (confirm("Annullare l'operazione?"))
		window.close();
}

function checkForm(form){
	var esito = true;
	var msg = "";
	
	if (form.id_impresa_sanzionata.value==""){
		esito = false;
		msg+= "Inserire Ente Accertato.\n"
	}
	if (form.asl_competenza.value==-1){
		esito = false;
		msg+= "Inserire ASL di competenza.\n"
	}
	if (form.trasgressore.value=="-1"){
		esito = false;
		msg+= "Inserire Trasgressore.\n"
	}
	if (form.obbligato.value=="-1"){
		esito = false;
		msg+= "Inserire Obbligato.\n"
	}
	if (form.trasgressore.value != form.descrizione_impresa_sanzionata.value && form.obbligato.value != form.descrizione_impresa_sanzionata.value ){
		esito = false;
		msg+= "Almeno uno tra Trasgressore ed Obbligato deve corrispondere con Ente Accertato.\n"
	}
	if (form.trasgressore.value == form.descrizione_impresa_sanzionata.value && form.obbligato.value == form.descrizione_impresa_sanzionata.value ){
		esito = false;
		msg+= "Solo uno tra Trasgressore ed Obbligato deve corrispondere con Ente Accertato.\n"
	}
	if (form.trasgressore.value == "ALTRO" && form.trasgressore_altro.value.trim() == "" ){
		esito = false;
		msg+= "Inserire la descrizione Trasgressore (Altro).\n"
	}
	if (form.obbligato.value == "ALTRO" && form.obbligato_altro.value.trim() == "" ){
		esito = false;
		msg+= "Inserire la descrizione Obbligato (Altro).\n"
	}
	if (form.pv.value.trim()==""){
		esito = false;
		msg+= "Inserire numero PV.\n"
	}
	if (form.data_accertamento.value==""){
		esito = false;
		msg+= "Inserire Data Accertamento.\n"
	}
	
	if (form.data_accertamento.value!=""){
		
		if(parseInt(form.data_accertamento.value.substr(form.data_accertamento.value.length - 4)) <= 2015){
			if (parseInt(form.data_accertamento.value.substr(3,2))<3){
				esito = false;
				msg+= "Inserire una data accertamento uguale o successiva al 1/3/2015."
			}
			
		}
	}
	
	
	
	if (!esito){
		alert(msg);
		return false;
	}
	
	if (confirm('Inserire questa sanzione nel registro?')){
		loadModalWindow();
		form.submit();
	}
}
</script>


<form name="form1" id="form1" action="RegistroTrasgressori.do?command=InsertSanzione" method="post">

<table class="details2" id = "tabletrasgressione" cellpadding="10" cellspacing="10">

<tr><th colspan="4">Inserisci sanzione nel Registro Trasgressori</th></tr>

<tr><th>Ente accertato </th>  
<td>
<input type="hidden" id="id_impresa_sanzionata" name="id_impresa_sanzionata"/><br/>
<input type="hidden" id="id_tipologia_operatore" name="id_tipologia_operatore"/><br/>
<input type="text" readonly id="descrizione_impresa_sanzionata" name="descrizione_impresa_sanzionata" size="40" onChange="gestisciEnteAccertato(this.form)"/><br/> 
<a href="#" onClick="openRicercaGlobale()">SELEZIONA DA GISA</a>
</td>

<th>ASL di competenza</th>  
<td><%= SiteList.getHtmlSelect("asl_competenza",-1) %></td></tr>

<tr><th>Trasgressore</th>  
<td>
<select id="trasgressore" name="trasgressore" onChange="gestisciTrasgressore(this.form)">
<option value="-1">-- SELEZIONARE --</option>
<option value="ALTRO">Altro</option>
</select>
<input type="text" id="trasgressore_altro" name="trasgressore_altro" style="display:none"/>
</td>

<th>Obbligato in solido</th>  
<td>
<select id="obbligato" name="obbligato" onChange="gestisciObbligato(this.form)">
<option value="-1">-- SELEZIONARE --</option>
<option value="ALTRO">Altro</option>
<option value="NESSUNO">Nessuno</option>
</select>
<input type="text" id="obbligato_altro" name="obbligato_altro" style="display:none"/>
</td></tr>


<th>PV N</th>  
<td><input type="text" id="pv" name="pv" maxlength="10" /></td>

<th>Data accertamento  </th>  
<td>
<input class="editField" type="text" id="data_accertamento" name="data_accertamento" readonly="readonly" size="10" value=""/> 
<a href="#" onClick="cal19.select(document.forms[0].data_accertamento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
</td></tr>


<tr>
<th colspan="2"><input type="button" onClick="annulla()" value="ANNULLA"/></th>
<th colspan="2"><input type="button" onClick="checkForm(this.form)" value="INSERISCI"/></th>
</tr>
</table>

		
</form>		
