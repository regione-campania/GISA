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

<% if( TicketDetails.getURlDettaglio() == "Allevamenti" && request.getAttribute("link_visibile").equals("si")) { %> 
	<% if(request.getAttribute("link_mod_a").equals("si")) { %> 
 		<div class="pulsanteStampa" id="stampaSchedaCampione">
			<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteA');"
				id="verbale" target="_self">Stampa Mod.A SIN Latte Ovicaprini</a>		
		</div>
	<% }
	
	if(request.getAttribute("link_mod_b").equals("si")) { %>
		<div class="pulsanteStampa" id="stampaSchedaCampione">
		<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','molluschi');"
			id="verbale" target="_self">Stampa Mod.B SIN Mitili </a>		
	</div>
	<% } 
	
	if(request.getAttribute("mod_sin").equals("NEW")){
		if(request.getAttribute("link_mod_c1").equals("si")) { %>
		<div class="pulsanteStampa" id="stampaSchedaCampione">
		<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteC1');"
			id="verbale" target="_self">Stampa Mod.C1 SIN Latte Ovicaprini-Bovini-Bufalini </a>		
	</div>
	<% }
	if(request.getAttribute("link_mod_c2").equals("si")) { %>
		<div class="pulsanteStampa" id="stampaSchedaCampione">
		<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteC2');"
			id="verbale" target="_self">Stampa Mod.C2 SIN Alimento Zootecnico Ovicaprini-Bovini-Bufalini </a>		
	</div>
	<% }
	if(request.getAttribute("link_mod_d").equals("si")) { %>
	<div class="pulsanteStampa" id="stampaSchedaCampione">
	<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','pesci');"
		id="verbale" target="_self">Stampa Mod.D SIN Pesci </a>		
	</div>
	<% } 
	
	} else {
		
		if(request.getAttribute("link_mod_c").equals("si")) { %>
 		<div class="pulsanteStampa" id="stampaSchedaCampione">
			<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteC');"
				id="verbale" target="_self">Stampa Mod.C SIN Latte Ovicaprini-Bovini-Bufalini </a>		
		</div>
		<% }
		
		if(request.getAttribute("link_mod_d").equals("si")) { %>
		<div class="pulsanteStampa" id="stampaSchedaCampione">
		<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','pesci');"
			id="verbale" target="_self">Stampa Mod.D SIN Pesci </a>		
		</div>
		<% } 
		
	}//fine piani_sin
	
	} else { //non si tratta di allevamenti...
		if(TicketDetails.getURlDettaglio() != "Allevamenti"){
			
			if(request.getAttribute("link_mod_a").equals("si")) { %> 
	 		<div class="pulsanteStampa" id="stampaSchedaCampione">
				<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteA');"
					id="verbale" target="_self">Stampa Mod.A SIN Latte Ovicaprini</a>		
			</div>
		<% }
			if(request.getAttribute("link_mod_b").equals("si")) { %>
	 		<div class="pulsanteStampa" id="stampaSchedaCampione">
				<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','molluschi');"
					id="verbale" target="_self">Stampa Mod.B SIN Mitili </a>		
			</div>
		<% }
		if(request.getAttribute("mod_sin").equals("NEW")){
				if(request.getAttribute("link_mod_c1").equals("si")) { %>
				<div class="pulsanteStampa" id="stampaSchedaCampione">
				<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteC1');"
					id="verbale" target="_self">Stampa Mod.C1 SIN Latte Ovicaprini-Bovini-Bufalini </a>		
			</div>
			<% }
			if(request.getAttribute("link_mod_c2").equals("si")) { %>
				<div class="pulsanteStampa" id="stampaSchedaCampione">
				<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','latteC2');"
					id="verbale" target="_self">Stampa Mod.C2 SIN Alimento Zootecnico Ovicaprini-Bovini-Bufalini </a>		
			</div>
			<% }	
		if(request.getAttribute("link_mod_d").equals("si")) { %>
	 		<div class="pulsanteStampa" id="stampaSchedaCampione">
				<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','pesci');"
					id="verbale" target="_self">Stampa Mod.D SIN Pesci </a>		
			</div>
		<% }
		
	} //fine piani sin 
	else { 
		if(request.getAttribute("link_mod_c").equals("si")) { %>
		<div class="pulsanteStampa" id="stampaSchedaCampione">
		<a href= "#" onclick="javascript:openModSin('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio()%>','pesci');"
			id="verbale" target="_self">Stampa Mod.D SIN Pesci </a>		
		</div>
		<% } 
		
		} 
	}
}%>
			
	
 
