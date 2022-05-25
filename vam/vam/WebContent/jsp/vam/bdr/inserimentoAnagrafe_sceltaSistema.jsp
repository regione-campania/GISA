<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<fieldset>
<legend>Animale "${param['identificativo'] }" non presente in banca dati. Selezionare il sistema in cui anagrafare l'animale</legend>
	
<%
	BUtente utente = (BUtente)session.getAttribute("utente");
%>	
	
	<ul>
		<us:can f="ACCESSO_BDU" sf="ADD" og="MAIN" r="w">
			<li>
				<input type="button" value="Cani/Gatti" onclick="attendere(),location.href='vam.bdr.canina.ToAnagrafe.us?identificativo=${param['identificativo'] }'" />
			</li>
		</us:can>
<%
		if(!utente.getRuoloByTalos().equals("IZSM") && !utente.getRuoloByTalos().equals("Universita") && !utente.getRuoloByTalos().equals("6") && !utente.getRuoloByTalos().equals("8"))
		{
%>		
		<li>
			<input type="button" value="Sinantropi" onclick="attendere(),location.href='vam.bdr.sinantropi.ToAnagrafe.us?identificativo=${param['identificativo'] }&sinantropo=sin'" />
		</li>
		<li>
			<input type="button" value="Animale Marino" onclick="attendere(),location.href='vam.bdr.sinantropi.ToAnagrafe.us?identificativo=${param['identificativo'] }&sinantropo=mar'" />
		</li>
		<li>
			<input type="button" value="Animale dello Zoo/Circo" onclick="attendere(),location.href='vam.bdr.sinantropi.ToAnagrafe.us?identificativo=${param['identificativo'] }&sinantropo=zoo'" />
		</li>
<%
		}
		else
		{
%>
			Attenzione: l'utente non ha i permessi per anagrafare animali nelle banche dati
<%
		}
%>	
	</ul>
</fieldset>

<input type="button" value="Annulla" onclick="location.href='vam.accettazione.Home.us';" />
