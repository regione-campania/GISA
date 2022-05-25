<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.suap.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@page import="java.util.Map.Entry"%>

<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>

<%@page import="java.sql.Timestamp"%>

<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="LineeCampiEstesi" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.suap.base.Operatore" scope="request"/>


<%@ include file="../initPage.jsp"%>


<script>
var refPopup = null ;
  		function intercettaValidaBtn(idRichiesta,pIvaImpresa,codiceFiscaleImpresa,idTipoRichiesta)
  		{
  			if(refPopup!=null)
  				refPopup.close();
  			
  			refPopup= window.open('InterfValidazioneRichieste.do?command=PaginaPerRichiesta&idRichiesta='+idRichiesta+'&pIvaImpresa='+pIvaImpresa+'&codiceFiscaleImpresa='+codiceFiscaleImpresa+'&idTipoRichiesta='+idTipoRichiesta+'&popup=true','popupSelect',
	         'height=800px,width=800px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
  		
 
</script>
<dhv:evaluate if="<%=!isPopup(request)%>">

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<%-- 
<a href="GisaSuapStab.do?command=Default"><dhv:label name="">Pratiche SUAP</dhv:label></a> >
<a href="GisaSuapStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnicaSuap.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
--%>

<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
<%-- <a href="InterfValidazioneRichieste.do?command=MostraRichiesteDaValutare">PRATICHE APICOLTURA</a> > --%>
<a href="GestionePraticheAction.do?command=ListaPraticheApicoltura">PRATICHE APICOLTURA</a> >
Scheda Pratica
</td>
</tr>
</table>
</dhv:evaluate>


<%
String param = "altId="+StabilimentoDettaglio.getAltId()+"&opId=" + StabilimentoDettaglio.getIdOperatore();
%>

<%
String nomeContainer = "suaprichieste";
int idOperazione = -1;
if (StabilimentoDettaglio.getOperatore()!=null)
	idOperazione = StabilimentoDettaglio.getOperatore().getIdOperazione();


if (StabilimentoDettaglio.getOperatore().isValidato() || (idOperazione!= Operatore.OP_NUOVO_STAB && idOperazione!= Operatore.OP_VARIAZIONE) || (StabilimentoDettaglio.getTipoAttivita()==StabilimentoDettaglio.TIPO_SCIA_APICOLTURA))
	nomeContainer = "suaprichiesteminimale";
%>

<% 
int tipoDettaglioScheda = 28;

%>


 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<div align="right">
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Stampa Scheda" value="Stampa Scheda" onClick="openRichiestaPDFOpuRichiestaAnagrafica('<%= StabilimentoDettaglio.getAltId() %>', '<%=tipoDettaglioScheda%>');">
</div>
 	

<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">

<%if (StabilimentoDettaglio.getOperatore().isValidato())
  {%>
	<font color="red">La pratica è già stata validata.</font>
<%} 
  else 
	{ %>
	  <%--
	  <% if(StabilimentoDettaglio.isFlagProsegubilita()==true) {  %>
			<center><input class="greenBigButton" type="button" value="PROSEGUI" onClick="intercettaValidaBtn('<%=StabilimentoDettaglio.getOperatore().getIdOperatore() %>','<%=StabilimentoDettaglio.getOperatore().getPartitaIva() %>','<%=StabilimentoDettaglio.getOperatore().getCodFiscale() %>','<%=StabilimentoDettaglio.getOperatore().getIdOperazione() %>')"/></center>
			<br/><br/>
	  <% } else { %>
	  		<center>
				<font color="red">Richiesta da validare in gestione richieste del cavaliere apicoltura.</font>
		    </center>
	  <% } %>
	 --%>
	 	
	<center>
	<br>			
		<font color="red" size="3px">
			Richiesta da validare: controllare se l'apicoltore ha inserito il primo apiario 
			oppure se la richiesta risulta da validare nella sezione "gestione richieste" del cavaliere "apicoltura"
		</font>
	</center>
	 	 
 <% } %>

<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=StabilimentoDettaglio.getAltId() %>" />
       <jsp:param name="objectIdName" value="alt_id" />
     <jsp:param name="tipo_dettaglio" value="<%=tipoDettaglioScheda %>" />
     </jsp:include>
     

</dhv:container>

