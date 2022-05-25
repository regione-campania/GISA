<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %> 

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

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

<jsp:useBean id="Scorta" class="org.aspcfs.modules.scortefarmaci.base.ScortaAllevamento" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>
<jsp:useBean id="comunicazioneOK" class="java.lang.String" scope="request"/>

<script>
function giorni_differenza(data1,data2){
	
	anno1 = parseInt(data1.substr(6),10);
	mese1 = parseInt(data1.substr(3, 2),10);
	giorno1 = parseInt(data1.substr(0, 2),10);
	anno2 = parseInt(data2.substr(6),10);
	mese2 = parseInt(data2.substr(3, 2),10);
	giorno2 = parseInt(data2.substr(0, 2),10);

	var dataok1=new Date(anno1, mese1-1, giorno1);
	var dataok2=new Date(anno2, mese2-1, giorno2);

	differenza = dataok2-dataok1;    
	giorni_diff = new String(Math.ceil(differenza/86400000));
	//alert('diff');
	//alert(giorni_diff);
	return giorni_diff;
}


function salva(form){
	
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var yyyy = today.getFullYear();
	today = dd + '/' + mm + '/' + yyyy;

	if (form.scortaDataInizio.value == '') {
	    	alert('Attenzione! Indicare una data di registrazione!');
	    	return false;
	    }
	 
    if (giorni_differenza(today, form.scortaDataInizio.value) > 0) {
    	alert('Attenzione! Indicare una data di registrazione non successiva alla data odierna!');
    	return false;
    }
		
	if (confirm("Confermare l'inserimento? ATTENZIONE. Verra' eseguito l'invio dei dati.")){
		form.action = "GestioneScortaFarmaci.do?command=InsertScortaAllevamento&auto-populate=true";
		loadModalWindow();
		form.submit();
		}
	}
	
function aggiorna(form){
	
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var yyyy = today.getFullYear();
	today = dd + '/' + mm + '/' + yyyy;

	if (form.scortaDataInizio.value == '') {
    	alert('Attenzione! Indicare una data di registrazione!');
    	return false;
    }
	
    if (giorni_differenza(today, form.scortaDataInizio.value) > 0) {
    	alert('Attenzione! Indicare una data di registrazione non successiva alla data odierna!');
    	return false;
    }
    
    if (form.scortaDataFine.value!= '' && giorni_differenza(today, form.scortaDataFine.value) > 0) {
    	alert('Attenzione! Indicare una data di fine validità non successiva alla data odierna!');
    	return false;
    }
    
    if (form.scortaDataFine.value!= '' && giorni_differenza(form.scortaDataInizio.value, form.scortaDataFine.value) < 0) {
    	alert('Attenzione! Indicare una data di fine validità non antecedente alla data di registrazione!');
    	return false;
    }
    
	if (confirm("Confermare l'aggiornamento? ATTENZIONE. Verra' eseguito l'invio dei dati.")){
		form.action = "GestioneScortaFarmaci.do?command=UpdateScortaAllevamento&auto-populate=true";
		loadModalWindow();
		form.submit();
		}
	}


function annulla(orgId){
	if (confirm("Annullare l'operazione?")){
		loadModalWindow();
		window.location.href="GestioneScortaFarmaci.do?command=ViewScortaAllevamento&orgId="+orgId;
	}
}

</script>

<center><b><U>Gestione Scorta Farmaci su allevamento <%=OrgDetails.getName() %> </U></label></b></center><br/>

<%if (Response!=null && !Response.equals("")){ %>
<div align="center" style="border:thin dotted #000000; background-color: #ffccbb">Salvataggio annullato. Motivazione:</div>
<div align="center" style="border:thick solid #000000; background-color: yellow"><%=Response %></div><br/><br/>
<%} %>

<%if (comunicazioneOK!=null && comunicazioneOK.equals("false")){ %>
<div align="center" style="border:thick solid #000000; background-color: red">ERRORE NELLA COOPERAZIONE APPLICATIVA</div><br/><br/>
<%} %>

<form id = "addAccount" name="addAccount" action="" method="post" >

<input type="hidden" id="orgId" name="orgId" value="<%=OrgDetails.getOrgId()%>"/>
<input type="hidden" id="id" name="id" value="<%=Scorta.getId()%>"/>

<table cellpadding="5" style="border-collapse: collapse" width="100%" class="details">
<col width="30%">

<tr><th colspan="2">Scorta farmaci</th></tr>

<tr>
<td class="formLabel"> Tipologia della scorta </td>
<td> 
<input type="radio" id="tipoScortaCodice1" name="tipoScortaCodice" value="ALLEVAMENTO" <%= "ALLEVAMENTO".equals(Scorta.getTipoScortaCodice()) || Scorta.getTipoScortaCodice()==null ? "checked=\"checked\"" : ""%>"/> ALLEVAMENTO 
</td>
</tr>

<tr>
<td class="formLabel">Numero di registrazione in GISA alla detenzione della scorta </td>
<td>
<input type="text" readonly="readonly" id="scortaNumAutorizzazione" name="scortaNumAutorizzazione" value="<%=toHtml(Scorta.getScortaNumAutorizzazione())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">Data di registrazione della scorta<br/> (La data e' quella di cui alla comunicazione/SCIA pervenuta dal  SUAP)</td>
<td>
<input type="text" id="scortaDataInizio" name="scortaDataInizio" readonly="readonly" size="10" value="<%=toHtml(Scorta.getScortaDataInizio()) %>"/> 
<a href="#" onClick="cal19.select(document.forms[0].scortaDataInizio,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"> <img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<a href="#" onClick="document.forms[0].scortaDataInizio.value=''; return false;" NAME="anchor19" ID="anchor19"> <img src="images/icons/stock_calc-cancel-16.gif" border="0" align="absmiddle" width="10px"></a> &nbsp;
</td>
</tr>

<% if (Scorta.getLovscortaId()!=null) { %>
<tr>
<td class="formLabel">Data di fine validita' della scorta</td>
<td>
<input type="text" id="scortaDataFine" name="scortaDataFine" readonly="readonly" size="10" value="<%=toHtml(Scorta.getScortaDataFine()) %>"/> 
<a href="#" onClick="cal19.select(document.forms[0].scortaDataFine,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<a href="#" onClick="document.forms[0].scortaDataFine.value=''; return false;" NAME="anchor19" ID="anchor19"> <img src="images/icons/stock_calc-cancel-16.gif" border="0" align="absmiddle" width="10px"></a> &nbsp;
</td>
</tr>
<% } %>

<tr>
<td class="formLabel">Asl Allevamento</td>
<td> <%=AslList.getSelectedValue(Scorta.getAslCodice())  %>
<input type="hidden" id="aslCodice" name="aslCodice" value="<%=toHtml(Scorta.getAslCodice())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">Codice Azienda</td>
<td>
<input type="text" readonly="readonly" id="codAzienda" name="codAzienda" value="<%=toHtml(Scorta.getCodAzienda())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">ID Fiscale Allevamento </td>
<td>
<input type="text" readonly="readonly" id="idFiscaleAllevamento" name="idFiscaleAllevamento" value="<%=toHtml(Scorta.getIdFiscaleAllevamento())%>"/>
</td>
</tr>

<tr>
<td align= "center" colspan="2"><input type="button" value="ANNULLA" onClick="annulla('<%=OrgDetails.getOrgId()%>')"/> 

<%if (Scorta.getLovscortaId()==null) { %>
<input type="button" value="SALVA E INVIA" onClick="salva(this.form)"/>
<% } else { %>
<input type="button" value="AGGIORNA E SINCRONIZZA" onClick="aggiorna(this.form)"/>
<%} %>
</td>
</tr>
</table>

</form>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
