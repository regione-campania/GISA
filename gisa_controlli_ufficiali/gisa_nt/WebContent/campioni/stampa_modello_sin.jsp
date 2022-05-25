<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Piani2" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script type="text/javascript">
function openModSin(orgId,idControllo,url,mod){
	var res;
	var result;
	var mes = document.getElementById('messaggio_sin').value;
	if( mes != '' && mes != 'null'){
		alert(mes);
	}else{
		window.open('CampioniReport.do?command=ViewSchedaSIN&idControllo='+idControllo+'&orgId='+orgId+'&url='+url+'&tipo='+mod,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}		

	
		
}
</script>

<% String messaggio_sin = (String) request.getAttribute("messaggio_sin"); %>
<input type = "hidden" name="messaggio_sin" id="messaggio_sin" value="<%=messaggio_sin%>"/>
<dhv:evaluate if="<%= Piani2.getSelectedValue(TicketDetails.getMotivazione_piano_campione()).contains("57") %>">
<% if(request.getAttribute("link_visibile").equals("si")) { %> 
	<% if(OrgDetails.getSpecieAllev().contains("OVINI") || OrgDetails.getSpecieAllev().contains("CAPRINI")) { %>
	<% //if(OrgDetails.getOrientamentoProd().contains("LATTE") || OrgDetails.getOrientamentoProd().contains("MISTO")){ %>
		<div class="pulsanteStampa" id="stampaSchedaCampione">
			 <%-- <a href="CampioniReport.do?command=StampaSchedaSIN&idControllo=<%=TicketDetails.getId()%>&orgId=<%=TicketDetails.getOrgId()%>&url=<%=TicketDetails.getURlDettaglio() %>&tipo=latte"
				id="verbale" target="_blank">Stampa Modello SIN Latte</a>--%>
				
		   <a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteA');"
				id="verbale" target="_self">Stampa Mod.A SIN Latte</a>
				
	<% } %>
	<% if(OrgDetails.getSpecieAllev().contains("OVINI") || OrgDetails.getSpecieAllev().contains("CAPRINI") || 
			OrgDetails.getSpecieAllev().contains("BOVINI") || OrgDetails.getSpecieAllev().contains("BUFALINI") ) { %>
	
		   <a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteC');"
				id="verbale" target="_self">Stampa Mod.C SIN Latte</a>	
				
		</div>
	<% } %>
	<% } %>
	<%  
	if(request.getAttribute("link_mod_d").equals("si")) { %>
 		<div class="pulsanteStampa" id="stampaSchedaCampione">
			<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','pesci');"
				id="verbale" target="_self">Stampa Mod.D SIN </a>		
		</div>
	<% } %>
	<% if(request.getAttribute("link_mod_b").equals("si")) { %>
 		<div class="pulsanteStampa" id="stampaSchedaCampione">
			<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','molluschi');"
				id="verbale" target="_self">Stampa Mod.B SIN </a>		
		</div>
	<% } %>
 
<% if(OrgDetails.getSpecieAllev().contains("AVICOLI") || OrgDetails.getSpecieAllev().contains("VOLATILI")) { %>
	<% //if(OrgDetails.getOrientamentoProd().contains("UOVA")){ %>
	<div class="pulsanteStampa" id="stampaSchedaCampione" style="margin-right:10px;">
		<a href="CampioniReport.do?command=StampaSchedaSIN&idControllo=<%=TicketDetails.getId()%>&orgId=<%=TicketDetails.getOrgId()%>&url=<%=TicketDetails.getURlDettaglio() %>&tipo=uova"
			id="verbale" target="_blank">Stampa Modello SIN Uova</a>
	</div>
<% } %>

</dhv:evaluate>