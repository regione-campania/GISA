<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*"%>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<jsp:useBean id="thisEvento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="deleteOk" class="java.lang.String"
	scope="request" />
	<jsp:useBean id="animale" 
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
	<jsp:useBean id="registrazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

	<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/PraticaList.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/LineaProduttiva.js"> </script>
	
<script type="text/javascript">
function checkForm(form) {

	if (document.deleteRegistrazione.noteCancellazione.value!=""){
		loadModalWindow(); //ATTENDERE PREGO     
		document.deleteRegistrazione.submit();
		return true;
	}	
	else{
		alert("Note di cancellazione obbligatorie.");
		return false;
	 }
	}

</script>
	<%@ include file="../initPage.jsp"%>
<%

String noteCancellazione=thisEvento.getNoteCancellazione();
if (noteCancellazione==null)
	noteCancellazione="";


java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); %>	
	
	
	<form name="deleteRegistrazione" action="RegistrazioniAnimale.do?command=DeleteRegistrazione" method="post" >
	
	<table cellpadding="4" cellspacing="4" border="1" width="100%">
		<tr>
			<th colspan="2" bgcolor="#FFCC99"><strong>Info cancellazione</strong>
			</th>
		</tr>

		<tr id="eventoId">
			<td class="formLabel" nowrap>Identificativo
			</td>
			<td><label><%=thisEvento.getIdEvento()%></label>
		</td>
		</tr>
		<tr id="eventoName">
			<td class="formLabel" nowrap>Categoria
			</td>
			<td><label>	<%= registrazioniList.getSelectedValue(thisEvento.getIdTipologiaEvento()) %>
	</label>
		</td>
		</tr>
		<tr id="dataId">
			<td class="formLabel" nowrap>Data cancellazione
			</td>
			<td><label><%=dataToString(timeNow)%></label>
		</td>
		</tr>
		
		
		<tr id="utenteId">
			<td class="formLabel" nowrap>Utente cancellazione
			</td>
			<td><label>
			<dhv:username id="<%=User.getUserId() %>"></dhv:username>
			
		</td>
		</tr>
		
		
		<tr id="noteId">
			<td class="formLabel" nowrap>Note
			</td>
			<dhv:evaluate if="<%=(!deleteOk.equals("OK"))%>">
			<td><textarea name="noteCancellazione" id="noteCancellazione" cols="30" rows="10" placeholder="Note di cancellazione obbligatorie."><%= noteCancellazione%></textarea></td>
 			</dhv:evaluate>
 			<dhv:evaluate if="<%=(deleteOk.equals("OK"))%>">
			<td><label><%=noteCancellazione %></label></td>
 			</dhv:evaluate>
 		</tr>
		
		<tr id="utenteId">
			<td class="formLabel" nowrap>Stato animale ripristinato
			</td>
			<td><label><%=statoList.getSelectedValue(thisEvento.getIdStatoOriginale()) %></label>
<%
			if(request.getAttribute("statoInconsistente")!=null)
				out.println("<br/><font color=\"red\">Attenzione! Lo stato precedente è inconsistente con la registrazione che si sta cancellando. Si prega di aggiustarlo tramite funzioni hd prima di procedere</font>");
%>
			
		</td>
		</tr>
		
		</table>
		<input type="hidden" id="idRegistrazione" name="idRegistrazione" value="<%=thisEvento.getIdEvento()%>">
		<input type="hidden" id="idTipologiaRegistrazione" name="idTipologiaRegistrazione" value="<%=thisEvento.getIdTipologiaEvento()%>">
		<input type="hidden" id="dataCancellazione" name="dataCancellazione" value="<%=timeNow%>">
		<input type="hidden" id="utenteCancellazione" name="utenteCancellazione" value="<%=User.getUserId()%>">
		<input type="hidden" id="idAnimale" name="idAnimale" value="<%=animale.getIdAnimale()%>">
		<input type="hidden" id="tipoEvento" name="tipoEvento" value="<%= registrazioniList.getSelectedValue(thisEvento.getIdTipologiaEvento()) %>">
	
	
	
	<!--  SE L'EVENTO NON E' GIA' STATO CANCELLATO, MOSTRA IL BOTTONE -->
	<dhv:evaluate if="<%=(!deleteOk.equals("OK") && thisEvento.getDataCancellazione()==null)%>">
	<input type="button" value="CANCELLA REGISTRAZIONE" onclick="javascript:return checkForm(this);"></dhv:evaluate>
	
	<dhv:evaluate if="<%=(deleteOk.equals("OK") || thisEvento.getDataCancellazione()!=null )%>">
	<center><p></p>
	<font color="red" size="2"><b>Registrazione cancellata!</b></font>

	<p></p>
	<a href="RegistrazioniAnimale.do?command=ReloadListaRegistrazioni">Esci e ricarica</a></center>
	</dhv:evaluate>

	
	</form>
	
	
	