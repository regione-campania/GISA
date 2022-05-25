<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.guc.Comune"%>
<%@page import="it.us.web.bean.guc.GestoreAcque"%>
<%@page import="it.us.web.dao.GestoreAcqueDAO"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<%@page import="java.util.*"%>
<%@page import="it.us.web.bean.guc.Ruolo"%>
<%@page import="it.us.web.bean.guc.Clinica"%>

<jsp:useBean id="UserRecord" class="it.us.web.bean.guc.Utente" scope="request"/>

<%@ include file="../guc/modalWindow.jsp"%>

<script>
function mostraAssociaClinicheVam(){ 
	loadModalWindow();
	$("#dialogAssociaClinicheVam").dialog({
	    resizable: true,
	    height: 'auto',
	    modal : true,
	    width: 'auto',
        overflow:'scroll'
        });
}
function nascondiAssociaClinicheVam(){
	loadModalWindowUnlock();
	$("#dialogAssociaClinicheVam").dialog("close");
}

</script>

<div id="content" align="center">

<div align="center">
	<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
	
	<c:if test="${UserRecord.dataScadenza==null}">
	<a href="guc.ToEditAnagrafica.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/edit-user.png" height="18px" width="18px" />Modifica Credenziali</a>
	<a href="guc.ToEditProfilo.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/edit_role.png" height="18px" width="18px" />Modifica Profilo</a>
	</c:if>
	
	<c:if test="${UserRecord.dataScadenza!=null}">
	<a href="guc.Rollback.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/undo.png" height="18px" width="18px" />Annulla Modifiche</a>


						</c:if>
						
	<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px; display: none"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
</div>

	<h4 class="titolopagina">Dettaglio Utente</h4>
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

    <tr>
      <th class="title" colspan="2">
        <strong>Contatto</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Nome</td>
      <td>${UserRecord.nome}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Cognome</td>
      <td>${UserRecord.cognome}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Codice Fiscale</td>
      <td>${UserRecord.codiceFiscale}
      
      <c:if test="${UserRecord.dataScadenza==null}">
	<a href="guc.ToEditCf.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/edit-cf.png" height="18px" width="18px" />Modifica Codice Fiscale</a>
	</c:if>
      
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">E-Mail</td>
      <td>${UserRecord.email}
      <c:if test="${UserRecord.dataScadenza==null}">
	<a href="guc.ToEditEmail.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/edit-cf.png" height="18px" width="18px" />Modifica Mail</a>
	</c:if>
	
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Telefono</td>
      <td>${UserRecord.telefono}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Note</td>
      <td>${UserRecord.note}</td>
    </tr>
    <tr>
      <th class="title" colspan="2">
        <strong>Credenziali</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Username</td>
      <td>${UserRecord.username}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Data scadenza Profilo/Inizio Validità Nuovo Profilo</td>
      <td><fmt:formatDate value="${UserRecord.dataScadenza}" pattern="dd/MM/yyyy"/> </td>
    </tr>
    <tr>
      <th class="title" colspan="2">
        <strong>A.S.L.</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Asl</td>
    <td><%= UserRecord.getAsl().getId() == -1 ? "TUTTE LE ASL" : UserRecord.getAsl().getNome() != null && !UserRecord.getAsl().getNome().equals("") ? UserRecord.getAsl().getNome() : "Nessuna" %></td>
    </tr>
    
    <tr>
      <th class="title" colspan="2">
        <strong>Cliniche</strong>&nbsp; <input type="button" value="ASSOCIA TUTTE LE CLINICHE DI VAM" onClick="mostraAssociaClinicheVam()"/>
      </th>
    </tr>
   <%for(Clinica c : UserRecord.getClinicheVam()){ %>
    <tr class="containerBody">
      <td class="formLabel">Clinica </td>
      <td><%=c.getDescrizioneClinica() %></td>
    </tr>
    <%} %>
    
    <tr>
      <th class="title" colspan="2">
        <strong>Canili</strong>&nbsp;
      </th>
    </tr>
    <% if(UserRecord.getCanileDescription() != null && !UserRecord.getCanileDescription().equals("")){ %>
    <tr class="containerBody">
      <td class="formLabel">Canile Canina</td>
      <td>${UserRecord.canileDescription}</td>
    </tr>
    <%} %>
    
     <tr>
      <th class="title" colspan="2">
        <strong>Canili di BDU</strong>&nbsp;
      </th>
    </tr>
    <% if(UserRecord.getCaniliBdu()!=null && UserRecord.getCaniliBdu().size()>0 && UserRecord.getCaniliBdu().get(0)!=null && UserRecord.getCaniliBdu().get(0).getDescrizioneCanile()!=null && !UserRecord.getCaniliBdu().get(0).getDescrizioneCanile().equals("")){ %>
    <tr class="containerBody">
      <td class="formLabel">Canile bdu</td>
      <td><%=UserRecord.getCaniliBdu().get(0).getDescrizioneCanile()%></td>
    </tr>
    <%} %>
    
    <tr>
      <th class="title" colspan="2">
        <strong>Importatori di BDU</strong>&nbsp;
      </th>
    </tr>
    <% if(UserRecord.getImportatori()!=null && UserRecord.getImportatori().size()>0 && UserRecord.getImportatori().get(0)!=null && UserRecord.getImportatori().get(0).getRagioneSociale()!=null && !UserRecord.getImportatori().get(0).getRagioneSociale().equals("")){ %>
	    <tr class="containerBody">
	      <td class="formLabel">Importatori bdu</td>
	      <td><%=UserRecord.getImportatori().get(0).getRagioneSociale()%></td>
	    </tr>
    <%} %>
    
    
    <tr>
      <th class="title" colspan="2">
        <strong>Ruoli</strong>&nbsp;
      </th>
    </tr>
    <%
    HashMap<String,HashMap<String,String>> extOpt = UserRecord.getExtOption();
    for(String endpoint : UserRecord.getHashRuoli().keySet()){ %>
    <tr class="containerBody">
      <td class="formLabel">Ruolo <%= endpoint %></td>
      
      <td>
      <table>
      <tr>
      	<td><%= UserRecord.getHashRuoli().get(endpoint)!=null && UserRecord.getHashRuoli().get(endpoint).getRuoloInteger() > 0 ? UserRecord.getHashRuoli().get(endpoint).getRuoloString() : "N.D." %></td>
  		<%  HashMap<String, String> ext = new HashMap<String,String>();
  			ext = extOpt.get(endpoint);
  			if (ext!=null && ext.size()>0) {
 			Iterator it = ext.entrySet().iterator();
 			while (it.hasNext()) {
 				Map.Entry e = (Map.Entry)it.next();
 				if (UserRecord.getHashRuoli().get(endpoint).getRuoloInteger() > 0){%>
 					<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td><b><%=e.getKey().toString().toUpperCase().replaceAll(endpoint.toUpperCase(),"").replaceAll("_"," ")%></b> : <%=e.getValue().toString().toUpperCase()%></td> 
 		<%  	}
 			} } %> 
	 </tr>
	 </table>
      </td>
    </tr>
    
    <%
    if (UserRecord.getHashRuoli().get(endpoint)!=null && endpoint.equalsIgnoreCase("gisa_ext") && UserRecord.getHashRuoli().get(endpoint).getRuoloString()!=null && UserRecord.getHashRuoli().get(endpoint).getRuoloString().equalsIgnoreCase("suap"))
    {
    	%>
    	
     <tr class="containerBody">
	      <td class="formLabel">Livello accreditamento</td>
	      <td><%=UserRecord.getLivelloAccreditamentoSuap()%></td>
	    </tr>
	    
     <tr class="containerBody">
	      <td class="formLabel">Comune</td>
	      <td><%=UserRecord.getComuneSuap()%></td>
	    </tr>
<!-- 	    <tr class="containerBody"> -->
<!-- 	      <td class="formLabel">Telefono</td> -->
<%-- 	      <td><%=UserRecord.getTelefonoSuap()%></td> --%>
<!-- 	    </tr> -->
	    
	      <tr class="containerBody">
	      <td class="formLabel">Ip</td>
	      <td><%=UserRecord.getIpSuap()%></td>
	    </tr>
	    
	      <tr class="containerBody">
	      <td class="formLabel">Pec</td>
	      <td><%=UserRecord.getPecSuap()%></td>
	    </tr>
	    
	      <tr class="containerBody">
	      <td class="formLabel">Callback Url</td>
	      <td><%=UserRecord.getCallbackSuap()%></td>
	    </tr>
	    
	     <tr class="containerBody">
	      <td class="formLabel">Callback Url (Opzionale)</td>
	      <td><%=UserRecord.getCallbackSuap_ko()%></td>
	    </tr>
	    
	      <tr class="containerBody">
	      <td class="formLabel">Shared Key (Size <%=UserRecord.getSharedKeySuap().length() %>)</td>
	      <td><%=UserRecord.getSharedKeySuap()%></td>
	    </tr>
    	<%
    }
    
    if (UserRecord.getHashRuoli().get(endpoint)!=null && endpoint.equalsIgnoreCase("gisa_ext"))
    {
    	%>
    	
    
     	<tr class="containerBody"><td class="formLabel">Numero Registrazione Stabilimento</td><td><%=UserRecord.getNumRegStab()%></td></tr>
	    
	<% ArrayList<GestoreAcque> gestoriList = (ArrayList<GestoreAcque>) request.getAttribute("gestoriList");
		if (UserRecord.getGestore()>0)
		{
		    for(GestoreAcque gestore : gestoriList)
		    {
		    	if(gestore.getId()==UserRecord.getGestore())
		    	{%>
					<tr class="containerBody"><td class="formLabel">Gestore Acque</td><td><%=gestore.getNome()%></td></tr>		    					  			
		<%} } } %>
		
		<% ArrayList<Comune> comuni = (ArrayList<Comune>) request.getAttribute("comuni");
	    	if (UserRecord.getComuneGestore()>0)
			{
		    	for(Comune comune : comuni)
		    			{
			    		if(comune.getId()==UserRecord.getComuneGestore())
			    			{
						%>
						<tr class="containerBody"><td class="formLabel">Comune Gestore Acque</td><td><%=comune.getNome()%></td></tr>		    					  			
		<% } } } %>
		
		<% if (UserRecord.getTipoAttivitaApicoltore()!=null) { %>
			<tr class="containerBody"><td class="formLabel">Tipo attivita' apicoltore</td><td><%=UserRecord.getTipoAttivitaApicoltore().equalsIgnoreCase("A") ? "Per Autoconsumo" : (UserRecord.getTipoAttivitaApicoltore().equalsIgnoreCase("C")) ? "Per commercio" : "" %></td></tr>		    					  			
		<% } %>
		
		<% if (UserRecord.getPiva()!=null) { %>
			<tr class="containerBody"><td class="formLabel">Partita iva associazione/attivita' apicoltura</td><td><%=UserRecord.getPiva()%></td></tr>		    					  			
		<% } %>

		<% if (UserRecord.getComuneApicoltore()>0) { 
		    for(Comune comune : comuni)
		    {
		    	if(comune.getId()==UserRecord.getComuneApicoltore())
		    	{ %>
					<tr class="containerBody"><td class="formLabel">Comune Apicoltore</td><td><%=comune.getNome()%></td></tr>		    					  			
		<% } } } %>
		
		<% if (UserRecord.getIndirizzoApicoltore()!=null && !UserRecord.getIndirizzoApicoltore().equals("")) { %>
			<tr class="containerBody"><td class="formLabel">Indirizzo apicoltore</td><td><%=UserRecord.getIndirizzoApicoltore()%></td></tr>		    					  			
		<% } %>
		
		<% if (UserRecord.getCapIndirizzoApicoltore()!=null && !UserRecord.getCapIndirizzoApicoltore().equals("")) { %>
			<tr class="containerBody"><td class="formLabel">CAP apicoltore</td><td><%=UserRecord.getCapIndirizzoApicoltore()%></td></tr>		    					  			
		<% } %>
		
		<% if (UserRecord.getComuneTrasportatore()>0) { 
		    for(Comune comune : comuni)
		    {
		    	if(comune.getId()==UserRecord.getComuneTrasportatore())
		    	{ %>
					<tr class="containerBody"><td class="formLabel">Comune Trasportatore/Distributore</td><td><%=comune.getNome()%></td></tr>		    					  			
		<% } } } %>
		
		<% if (UserRecord.getIndirizzoTrasportatore()!=null && !UserRecord.getIndirizzoTrasportatore().equals("")) { %>
			<tr class="containerBody"><td class="formLabel">Indirizzo Trasportatore/Distributore</td><td><%=UserRecord.getIndirizzoTrasportatore()%></td></tr>		    					  			
		<% } %>
		
		<% if (UserRecord.getCapIndirizzoTrasportatore()!=null && !UserRecord.getCapIndirizzoTrasportatore().equals("")) { %>
			<tr class="containerBody"><td class="formLabel">CAP Trasportatore/Distributore</td><td><%=UserRecord.getCapIndirizzoTrasportatore()%></td></tr>		    					  			
		<% } %>
		
	<%		
    	} 
    }
	%>
    
  	<tr>
      <th class="title" colspan="2">
        <strong>Informazioni</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Inserito da</td>
      <td>${UserRecord.enteredByUsername}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Inserito il</td>
      <td>${UserRecord.entered}</td>
    </tr>
    
      
    
  </table>
 	
</div>


<div id="dialogAssociaClinicheVam" style="display: none;" title="ASSOCIA TUTTE LE CLINICHE DI VAM">
<div align="center">
Attenzione. <br/><br/>
Con questa operazione, a questo utente saranno associate TUTTE le cliniche presenti in VAM.<br/><br/>
(Operazione consentita solo su utenti HD/Amministratori VAM)

<br/><br/>

<div id="bottoniAssociaClinicheVam">
<input type="button" value="ANNULLA" style="width:100px; height: 50px; font-size: 15px" onClick="nascondiAssociaClinicheVam()"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="CONFERMA" style="width:400px; height: 50px; font-size: 15px" onClick="window.location.href='allineaclinichevam.AllineaTutteClinicheVam.us?id=${UserRecord.id}'; document.getElementById('bottoniAssociaClinicheVam').style.display='none'"/>
</div>

</div>
</div>

