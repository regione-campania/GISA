<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/DwrPreaccettazione.js"> </script>
<script type="text/javascript">
	function delete_campione_callback(returnValue){
		if (returnValue){
			window.location.href='Campioni.do?command=ViewElencoPrenotazioni&orgId='+<%=OrgDetails.getOrgId()%>;
		}
		else {
			alert('Impossibile cancellare campione.');
		}
	}

	function delete_campione(ticketid,idCU,altId){
		if (idCU!="-1"){
			popURL('<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=ConfirmDelete&id='+ticketid+'&altId='+altId+'&popup=true', 'Delete_ticket','320','200','yes','no');
		} else{
			PopolaCombo.delete_campione(ticketid,delete_campione_callback);
		}
	}

	</script>

<script>
function completaDati(){
	var nomeAction = '<%=TicketDetails.getURlDettaglio()%>'+'Campioni.do';
	document.details.action='Campioni.do?command=ViewCompletaCampione&id=<%=TicketDetails.getId()%>&altId=<%=OrgDetails.getAltId()%>&ck_mot=<%=request.getAttribute("ck_mot")%>&ck_nv=<%=request.getAttribute("ck_nv")%>&ck_dp=<%=request.getAttribute("ck_dp")%>&ck_mat=<%=request.getAttribute("ck_mat")%>&ck_an=<%=request.getAttribute("ck_an")%>&input='+nomeAction;
	document.details.submit();
}
</script>


<dhv:permission name="impresa-linkprintmodules-view">
			<%	if (numero_include.equals("1")) { 
					
					if (TicketDetails.getURlDettaglio()!= null &&  !TicketDetails.getURlDettaglio().startsWith("Acque")){%>
						
						<%@ include file="/campioni/campioni_stampa_moduli_precompilati.jsp"%>
					<%}	
			}%>
</dhv:permission>


<% if(TicketDetails.isflagBloccoCu()==false){
	
	if (TicketDetails.isTrashed()) {%>
	
		<dhv:permission name="<%=perm_op_delete%>">
		<input type="button" value="Ripristina" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=Restore&id=<%=TicketDetails.getId()%>;this.form.submit();'">
		</dhv:permission>
		
<% } 
	else if (TicketDetails.getClosed() != null) { %>
 	
 		<dhv:permission name="reopen-reopen-view">
		<input type="button" value="Riapri" onClick="loadModalWindow();javascript:location.href='<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>&altId=<%=TicketDetails.getAltId() %>'">
		</dhv:permission>
 				
	<%	} else { %>

		<dhv:permission name="<%=perm_op_delete%>">
		<input type="button" value="Elimina" onClick="javascript:delete_campione('<%=TicketDetails.getId()%>','<%=TicketDetails.getIdControlloUfficiale()%>','<%=OrgDetails.getAltId()%>');">
		</dhv:permission>

		<dhv:permission name="<%=perm_op_edit %>">
			<% if (((String) request.getAttribute("flag"))!=null && ((String) request.getAttribute("flag")).equals("1")) { %>
		
				<input type="button" value="Chiudi" onClick="javascript:alert('Attenzione! La pratica non può essere chiusa perchè i dati del campione non sono completi');">
		
		<%} else { 
				
			if(TicketDetails.isEsitoCampioneChiuso()){%>
				
				<input type="button" value="Chiudi" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=Chiudi&altId=<%=OrgDetails.getAltId()%>&id=<%=TicketDetails.getId()%>';if( confirm('Sei sicuro di voler chiudere il campione? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){loadModalWindow();submit()};">
			<% }
			}
			
			if (((String) request.getAttribute("flag")).equals("1")) {%>
			
				<input type="button" value="completa campione" id="completa" onClick="javascript:completaDati();" onmouseover="javascript:ddrivetip('Completa il campione con le informazioni non inserite in fase di prenotazione',150,'lightyellow');" onmouseout="javascript:hideddrivetip();">
			
		<% }%>
		</dhv:permission>

	<%}
	} else { %>
				
			<font color = "red" ><%=TicketDetails.getNoteBlocco() %></font><br><br>
				
			<% if(TicketDetails.getClosed() == null) { %>
		
				<dhv:permission name="operazioni-hd-view">
				<% if (((String) request.getAttribute("flag")) != null && ((String) request.getAttribute("flag")).equals("1")) { //devi completare il campione...	%>
		
					<input type="button" value="Chisura per HD" onClick="javascript:alert('Attenzione! La pratica non può essere chiusa perchè i dati del campione non sono completi');">
				
				<% } else { %>
		
					<input type="button" value="Chiusura per HD" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Campioni.do?command=Chiudi&altId=<%=OrgDetails.getAltId()%>&id=<%=TicketDetails.getId()%>';if( confirm('Sei sicuro di voler chiudere il campione? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
		
				<% }%>
				</dhv:permission>
		
			<% if (((String) request.getAttribute("flag"))!=null && ((String) request.getAttribute("flag")).equals("1")) { %>
		
			<input type="button" value="completa campione" id="completa" onClick="javascript:completaDati();" onmouseover="javascript:ddrivetip('Completa il campione con le informazioni non inserite in fase di prenotazione',150,'lightyellow');" onmouseout="javascript:hideddrivetip();">
		
		<% }
		}
	}%>



		<!--  GESTIONE ESITI CAMPIONE -->

		<dhv:permission name="<%=perm_op_edit %>">
		<% if (!TicketDetails.isEsitoCampioneChiuso() || !TicketDetails.isInformazioniLaboratorioChiuso()){ 
			
		%>
		<input type="button" style="background-color:#CC3300" value="Salva"
		onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio()%>Campioni.do?command=UpdateTicketEsiti&id=<%=TicketDetails.getId()%>&altId=<%=TicketDetails.getAltId()%>';if( confirm('Sei sicuro di voler salvare gli esiti? \n Attenzione! La modifica degli esiti degli analiti verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){ if (checkFormEsiti()){submit()}};">
			
		<% } %>
		</dhv:permission>

		<dhv:permission name="riapertura_esiti_campioni-add">
		<% if (TicketDetails.isInformazioniLaboratorioChiuso()){ 
		
		%>
		<input type="button" style="background-color:#CC3300" value="Riapri informazioni laboratorio"
		onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio()%>Campioni.do?command=RiapriTicketEsiti&id=<%=TicketDetails.getId()%>&altId=<%=TicketDetails.getAltId()%>';if( confirm('Sei sicuro di voler proseguire? \n La sezione Esito Laboratorio sarà nuovamente modificabile.') ){submit()};">
		
	<%		}%>
		</dhv:permission>


