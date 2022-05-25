<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>

<%@page import="org.aspcfs.modules.praticacontributi.base.Pratica"%>
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="pratica" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request"/>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/dateControl.js"></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<SCRIPT>

function compareDates(date1,dateformat1,date2,dateformat2){
	var d1=getDateFromFormat(date1,dateformat1);
	var d2=getDateFromFormat(date2,dateformat2);
	if (d1==0 || d2==0) {
		return -1;
		}
	else if (d1 > d2) {
		return 1;
		}
	return 0;
	}

function doCheck(form) {
    if (form.dosubmit.value == "false") {     
      return true;
    } else {
      return(checkForm(form));
    }
   }


 function checkForm(form) {
    formTest = true;
    message = "";

    
    if (form.dataProroga.value==""){
    	 message += label("", "-E' obbligatorio settare la data della proroga\r\n");
	     formTest = false;
    }
       
    if (compareDates( form.dataProroga.value,"d/MM/y",form.oldData.value,"d/MM/y")<1) { 
   	 message += label("", "-La nuova data deve essere successiva a quella attuale\r\n");
	     formTest = false;
   }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    else
    {
  	return true;
    }
  }

 function attivaProroga(){
  mostraBox('proroga');
	 }

 function confermaChiusura(){
	  mostraBox('chiusura');
		 }
	
 
 function mostraBox(id) {
	 $id(id).style.display = 'block';
}

 function $id(id) { 
		return (document.getElementById)? document.
		getElementById(id) : document.all[id];
	}
</SCRIPT>



<form name="" action="PraticaContributi.do" method="post">

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="PraticaContributi.do"><dhv:label name="praticacontributi"></dhv:label></a> Ricerca >
  <a href="PraticaContributi.do?command=SearchList"><dhv:label name="pratica.contributi.search">Risultati Ricerca</dhv:label></a> >
  <dhv:label name="pratica.contributi.details">Dettagli</dhv:label>
</td>
</tr>
</table>



<%-- End Trails --%>
</dhv:evaluate>

<br>
     <input type="hidden" name="dosubmit" value="true" />    

<%if (pratica.getData_chiusura_pratica()== null ){%>
	<input type="button" value="Proroga" onClick="javascript:attivaProroga();"> 
	<label><font color="red" >Progetto Aperto</font></label>&nbsp
<%} 
	else {%>
		<label><font color="red" >Progetto Chiuso</font></label>
	<%} %>
	
<%if ( ( pratica.getGattiRestantiPadronali() == pratica.getTotaleGattiPadronali() ) && 
	   (pratica.getGattiRestantiCatturati() == pratica.getTotaleGattiCatturati() ) && 
	   (pratica.getCaniRestantiCatturati() == pratica.getTotaleCaniCatturati() ) &&
	   (pratica.getCaniRestantiPadronali() == pratica.getTotaleCaniPadronali()) && (pratica.getData_chiusura_pratica())==null){

%>	

<input type="button" value="Chiudi" onclick="javascript:this.form.action='PraticaContributi.do?command=Chiudi&id=<%=pratica.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>';if (confirm('Si è sicuri di voler chiudere il progetto?')){ if(true){submit()}}; "/>
	<% } %>	
<br>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="proroga" style="display:none">
 <tr>
    <th colspan="2">
      <strong><dhv:label name="">Settaggio Proroga</dhv:label></strong>
    </th>     
  </tr>
<tr class="containerBody">
           			<td  class="formLabel">
           				<dhv:label name="">Nuova data fine sterilizzazione</dhv:label>
	           		</td>
    	       		<td>
    	       		<input type="text" name="dataProroga" size="10" readonly="readonly" />
    	       		<input type="hidden" name="oldData" size="10" value="<%=toHtmlValue(toDateasString(pratica.getDataFineSterilizzazione())) %>" />
     					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataProroga,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a><font color="red">*</font>
        	   		</td>
           		</tr>	
 <tr>
 <td>
<input type="button" value="Salva" onclick="javascript:this.form.action='PraticaContributi.do?command=Proroga&id=<%=pratica.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>';if (confirm('Si è sicuri di voler prorogare il progetto?')){ if(doCheck(this.form)){submit()}}; "/>
	
</td>
</tr>
</table>
<br><br>

<br/>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2"><strong><dhv:label
			name="praticacontributi.SpecificInformation">Dettagli</dhv:label></strong>
		</th>
	</tr>
<%
	if(pratica.getIdTipologiaPratica()!=Pratica.idPraticaLP)
	{
%>
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Asl di Riferimento</dhv:label></td>
		<td><%=pratica.getDescrizioneAslPratica()%></td>
	</tr>
<%
	}
%>
	
<!-- Pratica su comuni -->
<dhv:evaluate if="<%=(pratica.getIdTipologiaPratica() == Pratica.idPraticaComune || pratica.getIdTipologiaPratica() == Pratica.idPraticaLP)  %>">
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Elenco Comuni coinvolti</dhv:label></td>
		<td>
		
		<%
		int tot_comuni = pratica.getComuniElenco().size();
	//	System.out.println(tot_comuni);
		int i = 0;
		for (Integer  s : pratica.getComuniElenco() ){
		i++;
		%>
			<%=comuniList.getSelectedValue(s) +( (i != tot_comuni)?   ", " : " " )%>	
			
		<%} %>
		
		
		</td>
	</tr>
</dhv:evaluate>	


<!-- Pratica su canili -->
<dhv:evaluate if="<%=(pratica.getIdTipologiaPratica() == Pratica.idPraticaCanile) %>">
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Elenco Canili coinvolti</dhv:label></td>
		<td>
		
		<%
		int tot_canili = pratica.getCaniliElencoNome().size();
	//	System.out.println(tot_comuni);
		int i = 0;
		for (String  s : pratica.getCaniliElencoNome() ){
		i++;
		%>
			<%=s +( (i != tot_canili)?   ", " : " " )%>	
			
		<%} %>
		
		
		</td>
	</tr>
</dhv:evaluate>	


	<dhv:evaluate if="<%=pratica.getOggettoPratica()!=null && !("").equals(pratica.getOggettoPratica())%>" >
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Oggetto Decreto</dhv:label></td>
		<td><%=pratica.getOggettoPratica() %></td>
	</tr>
	</dhv:evaluate>
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Numero Decreto</dhv:label></td>
		<td><%=pratica.getNumeroDecretoPratica() %></td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Data Decreto</dhv:label></td>
		<td><%=toHtmlValue(toDateasString(pratica.getDataDecreto()))%></td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Data Inizio Sterilizzazione</dhv:label></td>
		<td><%=toHtmlValue(toDateasString(pratica.getDataInizioSterilizzazione())) %></td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Data Fine Sterilizzazione</dhv:label></td>
		<td><%=toHtmlValue(toDateasString(pratica.getDataFineSterilizzazione())) %></td>
	</tr>
	<%if (pratica.getData_chiusura_pratica()!=null){ %>
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Data Chiusura Pratica</dhv:label></td>
		<td><%=toHtmlValue(toDateasString(pratica.getData_chiusura_pratica())) %></td>
	</tr>
	<%} %>
	
	<dhv:evaluate if="<%=(pratica.getIdTipologiaPratica() == Pratica.idPraticaLP)  %>">
	<tr class="containerBody">
		<td class="formLabel" valign="top"><dhv:label name="">Veterinari</dhv:label></td>
		<td>
		
		<%
		int tot_vet = pratica.getVeterinariElencoNome().size();
	//	System.out.println(tot_comuni);
		int i = 0;
		for (String  s : pratica.getVeterinariElencoNome() ){
		i++;
		%>
			<%=s +( (i != tot_vet)?   ", " : " " )%>	
			
		<%} %>
		
		
		</td>
	</tr>
</dhv:evaluate>	

</table>
<br/>
<table width="100%" border="2" bgcolor="#FF9933" bordercolor="#FF9933">
<%
if(pratica.getIdTipologiaPratica()!=Pratica.idPraticaLP)
{
%>
 <tr>
  <td width="25%" class="formLabel2" align="left" ><b> Numero Totale Cani Catturati</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniCatturati() %></td>
  <td width="25%" class="formLabel2"><b>Cani Catturati inseriti in BDR</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniCatturati()-pratica.getCaniRestantiCatturati() %></td>
 </tr>
 <tr>
  <td width="25%" class="formLabel2"><b>Numero Totale Cani Padronali</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniPadronali() %></td>
  <td width="25%" class="formLabel2"><b>Cani Padronali inseriti in BDR</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniPadronali()-pratica.getCaniRestantiPadronali() %></td>
 </tr>
 <tr>
  <td width="25%" class="formLabel2"><b>Numero Totale Gatti Catturati</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleGattiCatturati() %></td>
  <td width="25%" class="formLabel2"><b>Gatti Catturati inseriti in BDR</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleGattiCatturati() - pratica.getGattiRestantiCatturati() %></td>
 </tr>
 <tr>
  <td width="25%" class="formLabel2"><b>Numero Totale Gatti Padronali</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleGattiPadronali() %></td>
  <td width="25%" class="formLabel2"><b>Gatti Padronali inseriti in BDR</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleGattiPadronali()- pratica.getGattiRestantiPadronali() %></td>
 </tr>
<%
}
else
{
%>
	<tr>
  <td width="25%" class="formLabel2" align="left" ><b> Numero Totale Cani Maschi</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniMaschi() %></td>
  <td width="25%" class="formLabel2"><b>Cani Maschi inseriti in BDR</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniMaschi()-pratica.getCaniRestantiMaschi() %></td>
 </tr>
	<tr>
  <td width="25%" class="formLabel2" align="left" ><b> Numero Totale Cani Femmina</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniFemmina() %></td>
  <td width="25%" class="formLabel2"><b>Cani Femmina inseriti in BDR</b></td>
  <td width="25%" bgcolor="#BDCFFF"><%=pratica.getTotaleCaniFemmina()-pratica.getCaniRestantiFemmina() %></td>
 </tr>	
	
<%
}
%>
</table>

<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazione sul record</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Inserito
    </td>
    <td>
      <dhv:username id="<%= pratica. getEnteredBy() %>" />
      	 <%=toHtmlValue(toDateasString(pratica.getEntered()))%>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modificato
    </td>
    <td>
      <dhv:username id="<%= pratica.getModifiedBy() %>" />
     <%=toHtmlValue(toDateasString(pratica.getModified()))%>&nbsp;
    </td>
  </tr>
</table>
</form>



