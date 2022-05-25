<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import ="org.aspcfs.modules.suap.utils.CodiciRisultatiRichiesta" %>
<%@page import ="org.aspcfs.modules.opu.base.OperatorePerDuplicati" %>
<%@page import ="java.util.ArrayList, java.util.HashMap,org.aspcfs.modules.suap.base.CodiciRisultatoFrontEnd,org.aspcfs.modules.suap.base.Stabilimento" %>
<%@page import ="org.json.JSONObject" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:useBean id="Richiesta" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="descrizione_errore" class="java.lang.String" scope="request"/>

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
	
<%@ include file="../initPage.jsp"%>
	
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr><th colspan="4"><strong>MODIFICA STATO DEI LUOGHI STABILIMENTO</strong></th></tr>
	<tr>
		<th colspan="3">MODIFICA STATO DEI LUOGHI STABILIMENTO</th>
		<th>Operazione</th>
	</tr>

	<!-- ELIMINO SCARICA PLANIMETRIA POICHE' NON E' PIU' POSSIBILE INSERIRE ALLEGATI IN INSERIMENTO SCIA 
	<tr>
	
		<td colspan="3">PLANIMETRIA ALLEGATA</td>
		<td style="background-color:lightgray;">
			<input type="button" onclick ="intercettaScaricaPlanimetriaBut('InterfValidazioneRichieste.do?command=DownloadAllegatoSingoloPerRichiesta&idRichiesta=<%=Richiesta.getOperatore().getIdOperatore() %>&indiceAllegato=0')" value="DOWNLOAD" />
		</td>
	</tr>
	 -->
	
	<tr>
	<td colspan="3"></td>
	<td><%if(Richiesta.getOperatore().isValidato()==false){ %>
		<input id="btnCessazioneGlobale" type="submit"
			value="trasferisci in anagrafica stabilimenti"
			onClick="intercettaBottoneValidaGlobale(1);" />
		<input id="btnCessazioneGlobale" type="submit"
			value="Respingi"
			onClick="intercettaBottoneValidaGlobale(2);" />
			<%} %>
		</td>
		
		
		
	</tr>
   
    </table>
    