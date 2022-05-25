<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="it.us.web.bean.BUtente"%>
<%
	String sinantropo = (String)request.getAttribute("sinantropo");
	String sinantropoDesc = "";
	String action = "ToAdd";
	if(sinantropo.equals("sin"))
		sinantropoDesc = "Sinantropo";
	else if(sinantropo.equals("mar"))
	{
		sinantropoDesc = "Animale Marino";
		action = "ToAddMarini";
	}
	else if(sinantropo.equals("zoo"))
	{
		sinantropoDesc = "Animale dello Zoo/Circo";
		action = "ToAddZoo";
	}
%>

Inserimento Registrazioni <%=sinantropoDesc%>: <b>${param['identificativo']}</b>
<br/>
Operazioni da registrare: Iscrizione Anagrafe

<input type="button" value="Operazione Completata" onclick="javascript:attendere(),anagrafeCompletata();" />
<input type="button" value="Annulla" onclick="javascript:if(myConfirm('Sicuro di voler annullare l\'operazione?')){ annulla(); };" />

<br/>&nbsp;<br/>

<iframe 
	id="myFrame" name="myFrame"  frameborder="0"  vspace="0"  
	hspace="0" marginwidth="0" marginheight="0"
	width="95%"  scrolling=yes  height="900"
	style="BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; Z-INDEX: 999; BORDER-LEFT: black 1px solid; BORDER-BOTTOM: black 1px solid;"
	src="sinantropi.<%=action%>.us?interactiveMode=y&identificativo=${param['identificativo']}">
</iframe>

<script type="text/javascript">
function annulla()
{
	location.href='vam.accettazione.FindAnimale.us?identificativo=${param['identificativo'] }';
};

function anagrafeCompletata()
{
	location.href='vam.bdr.sinantropi.AnagrafeCompletata.us?identificativo=${param['identificativo'] }';
};
</script>
