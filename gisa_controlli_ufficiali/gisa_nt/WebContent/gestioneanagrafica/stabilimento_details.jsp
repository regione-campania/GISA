<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.taglib.PermissionHandler"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="Numero_cu" class="java.lang.String" scope="request"/>
<jsp:useBean id="Numero_pratiche" class="java.lang.String" scope="request"/>
<jsp:useBean id="numero_linee_aggiungibili" class="java.lang.String" scope="request"/>

<jsp:useBean id="Messaggio" class="java.lang.String" scope="request"/>
  
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>
  
<%@ include file="../initPage.jsp" %>

<style>
	
	.dropbtn {
	}

	.dropdown {
	  position: relative;
	  display: inline-block;
	}
	
	.dropdown-content {
	  display: none;
	  position: absolute;
	  border-style: solid;
  	  border-width: 1px;
	  background-color: #E8E8E8;
	  overflow: auto;
	  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
	  z-index: 1;
	}
	
	.dropdown-content a {
	  display: block;
	  padding: 6px 16px;
	  
	}
	
	
	.show {display: block;}
	
	/* Change color of dropdown links on hover */
	.dropdown-content a:hover {background-color: #D0D0D0}

</style>


<% if (StabilimentoDettaglio.getCodiceErroreSuap()!=null && !StabilimentoDettaglio.getCodiceErroreSuap().equals("")){%>
	<script>
	alert('<%=StabilimentoDettaglio.getCodiceErroreSuap()%>');
	</script>	
<% } %> 

<% if (Messaggio!=null && !Messaggio.equalsIgnoreCase("null") && !Messaggio.equals("")) {%>
<script>
alert('<%=Messaggio%>');
</script>
<% } %>

<script>
function refreshDimensioniIframe(){
	var iframe = document.getElementById("dettaglioTemplate");
	$(iframe).height($(iframe).contents().find('html').height());
}

function refreshDimensioniIframe2(){
	var iframe = document.getElementById("dettaglioTemplateInformazioneSpecifica");
	$(iframe).height($(iframe).contents().find('html').height());
}
</script>

<%
	boolean flagLineeScia = false;
	boolean flagLineeSospese = false;
	boolean flagLineeAttive = false;
	boolean hasLineePanificio = false;
	for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
		LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
		//verifica se stabilimento scia o noscia
		if(!l.getFlags().isNoScia())
		{
			flagLineeScia = true;
		}
		//verifica se stabilimento linee che possono essere sospese
		if(l.getStato() == 0){
			flagLineeAttive = true;
		}

		//verifica se stabilimento linee che possono essere riattivate in quanto sospese
		if(l.getStato() == 2){
			flagLineeSospese = true;
		}
		
		//verifica se lo stabilimento ha linee che possono subire la trasformazione
		if(l.getStato() == 0 && l.getCodice().equalsIgnoreCase("MS.020-MS.020.200-852IT3A101") ){
			hasLineePanificio = true;
		}
	}
%>

<dhv:evaluate if="<%=!isPopup(request)&& User.getRoleId()!=Role.RUOLO_TRASPORTATORI_DISTRIBUTORI && User.getRoleId()!=Role.RUOLO_GESTORE_PRODOTTI_SOA%>">
<%-- Trails --%>
	<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> >
			<a href="RicercaUnica.do?command=Search">RISULTATO RICERCA</a> >
			SCHEDA
		</td>
	</tr>
	</table>
<%-- Trails --%>

<%@ include file="../../controlliufficiali/anagrafica_diffida_list.jsp" %>
</dhv:evaluate>

<%

boolean lineePregresse = false;
lineePregresse = StabilimentoDettaglio.isLineePregresse();

String nomeContainer = "gestioneanagrafica";

if(!flagLineeScia) { 
	nomeContainer = "gestioneanagraficanoscia";
}

if (lineePregresse) 
	nomeContainer = "suapminimale";

StabilimentoDettaglio.getOperatore().setRagioneSociale(StabilimentoDettaglio.getOperatore().getRagioneSociale().toUpperCase() );
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento();
%> 



<% 
	int tipoDettaglioScheda = 15;
	int tipoDettaglioAttestato = 23;
	System.out.println("tipo attivita"+StabilimentoDettaglio.getTipoAttivita() );
	
	if (StabilimentoDettaglio.getTipoAttivita()==1){
		tipoDettaglioScheda = 24;
		tipoDettaglioAttestato = 26;
	}
	else if (StabilimentoDettaglio.getTipoAttivita()==2){
		tipoDettaglioScheda = 25;
		tipoDettaglioAttestato = 27;
	}

%>

<%
	 List arrayLineeOperatoriCommerciali = Arrays.asList("349-93-ALCAT-ALTR", "349-93-ALCAT-PRIV", "349-93-ALPET-ALTR", "349-93-ALPET-PRIV", "IUV-COIAC-VEICG","IUV-CODAC-VEDCG", "IUV-CODAC-DET", "IUV-COIAC-INGINF", "IUV-COIAC-INGSUP");
	 List arrayLineeCanili = Arrays.asList("IUV-CAN-CAN", "IUV-CAN-ALLGAT", "IUV-CAN-ALLCAN", "IUV-ADCA-ADCA", "IUV-CAN-RIFRIC", "IUV-CAN-RIFSAN", "IUV-CAN-PEN", "IUV-CAN-AAF", "VET-AMBV-PU", "VET-CLIV-PU", "VET-OSPV-PU");
	 List arrayLineeCliniche = Arrays.asList("IUV-CAN-RIFSAN", "IUV-CAN-DEG", "VET-AMBV-PU", "VET-CLIV-PU", "VET-OSPV-PU");


	boolean viewSincronizzazione = false ;
	boolean isCanile = false;
	boolean isOsm = false;
	int idRelCanile = -1;
	
	int idLineaCanile = -1;
	int idLineaClinica = -1;
	
	for (LineaProduttiva lp : (Vector<LineaProduttiva>)StabilimentoDettaglio.getListaLineeProduttive())
	{
		if(arrayLineeCanili.contains(lp.getCodice()) || arrayLineeOperatoriCommerciali.contains(lp.getCodice()) || arrayLineeCliniche.contains(lp.getCodice()) )
		{
			viewSincronizzazione=true ;
		}

		//if(lp.getCodice().equals("IUV-CAN-CAN") ) // gestione valida solo per ML8
		if(arrayLineeCanili.contains(lp.getCodice())) //gestione valida per Ml8 e Ml10
		{
			isCanile=true ;
			idRelCanile = lp.getId_rel_stab_lp();
		}
		
		if(lp.getCodice().startsWith("MG") || lp.getCodice().startsWith("MR"))
		{
			isOsm=true ;
		}
		
		if(arrayLineeCanili.contains(lp.getCodice()))
			idLineaCanile=lp.getId_rel_stab_lp();

		if(arrayLineeCliniche.contains(lp.getCodice()))
			idLineaClinica=lp.getId_rel_stab_lp();
	}
	
	PermissionHandler p1 = new PermissionHandler();
	PermissionHandler p2 = new PermissionHandler();
	
	if (flagLineeScia)
	{ 
		p1.setName("sincronizza-bdu-view");
		p2.setName("sincronizza-vam-view");
		p1.setPageContext(pageContext);
		p2.setPageContext(pageContext);
	}
	
	PermissionHandler permesso_modifica_scheda = new PermissionHandler();
	permesso_modifica_scheda.setName("gestioneanagrafica-errata-corrige-scia-view");
	permesso_modifica_scheda.setPageContext(pageContext);
	
%>

  
<div align="right">
<% if (StabilimentoDettaglio.getStato()==4) { %>
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
	<input type="button" title="Scheda cessazione" value="Scheda cessazione" onClick="openRichiestaPDFOpuAnagrafica('<%= StabilimentoDettaglio.getIdStabilimento() %>', '29');">
	<br/>
<% } %>
<%if(flagLineeScia){ %>
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Attestato registrazione" value="Attestato registrazione" onClick="openRichiestaPDFOpuAnagrafica('<%=StabilimentoDettaglio.getIdStabilimento()%>', '26');">
<%} %>

<%
	boolean flagInformazioniAggiuntive = false;
	for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
		LineaProduttiva lverificace = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
		lverificace.existCampiEstesi();
		if(lverificace.existCampiEstesi())
		{
			flagInformazioniAggiuntive = true;
			break;
		}
	}
	%>

<jsp:include page="../gestione_documenti/boxDocumentaleIframeGestioneAnagrafica.jsp">
	<jsp:param name="iframeId" value="dettaglioTemplate" />
	<jsp:param name="altId" value="<%=StabilimentoDettaglio.getAltId() %>" />
	<jsp:param name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
	<jsp:param name="tipo" value="SchedaAnagrafica" />
	<jsp:param name="tipoOperatore" value="<%=tipoDettaglioAttestato%>" />
	<jsp:param name="flagLineeScia" value="<%=flagLineeScia%>" /> 
</jsp:include>


<% if (StabilimentoDettaglio.getDatiMobile().size()>0) { %>
<form name="gestionePdf" action="GestioneDocumenti.do?command=GeneraPDFdaHtml" method="POST">
  	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>  
  	<select id="idCampoEsteso" name="idCampoEsteso">
	 	<% for (int i = 0; i<StabilimentoDettaglio.getDatiMobile().size(); i++) { %>
			 <option value="<%=StabilimentoDettaglio.getDatiMobile().get(i).getId()%>"><%=StabilimentoDettaglio.getDatiMobile().get(i).getLabel().toUpperCase() %>: <%=StabilimentoDettaglio.getDatiMobile().get(i).getTarga() %></option> 
		<% } %>
	</select>
	
	<input type="hidden" name="orgId" id="orgId" value=""></input>
	<input type="hidden" name="ticketId" id="ticketId" value=""></input>
	<input type="hidden" name="tipo" id="tipo" value="SchedaAnagrafica"></input>
	<input type="hidden" name="idCU" id="idCU" value=""></input>
	<input type="hidden" name="url" id="url" value=""></input>
	<input type="hidden" name="extra" id="extra" value=""></input>
	<input type="hidden" name="altId" id="altId" value="<%=StabilimentoDettaglio.getAltId() %>"></input>
	<input type="hidden" name="stabId" id="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>"></input>
	<input type="hidden" name="iframeId" id="iframeId" value="dettaglioTemplateInformazioneSpecifica"></input>
	<input type="hidden" name="htmlcode" id="htmlcode" value=""></input>
	
	<input type="button" id ="generaPDFstampaSchedaSpecifica" title="Stampa Scheda Specifica" value="Stampa Scheda Specifica" 
		onClick="stampaSchedaSpecifica('<%=StabilimentoDettaglio.getAltId() %>', this.form);">
	<!-- 
    <input type="button" title="Stampa Scheda Specifica" value="Stampa Scheda Specifica" onClick="openRichiestaPDFOpuAnagraficaCampoEsteso('<%= StabilimentoDettaglio.getIdStabilimento() %>', document.getElementById('idCampoEsteso').value, '41');">
	-->
</form>
<% } %> 

</div>

<dhv:permission name="note_hd-view">
	<jsp:include page="../note_hd/link_note_hd.jsp">
	<jsp:param name="riferimentoId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
	<jsp:param name="riferimentoIdNomeTab" value="opu_stabilimento" />
	<jsp:param name="typeView" value="button" />
	</jsp:include>
</dhv:permission>

<dhv:permission name="giava-creazione-utente-view"> 
	<jsp:include page="../utils_giava/creazione_utente_giava.jsp">
	<jsp:param name="riferimentoIdGiava" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
	<jsp:param name="riferimentoIdNomeTabGiava" value="opu_stabilimento" />
	</jsp:include>
</dhv:permission>

<dhv:permission name="gestione-schede-supplementari-view"> 
	<jsp:include page="../schedesupplementari/bottone.jsp">
	<jsp:param name="riferimentoId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
	<jsp:param name="riferimentoIdNomeTab" value="opu_stabilimento" />
	</jsp:include>
</dhv:permission>
	
<dhv:permission name="osm-invio-view"> 
<% if(isOsm) { %>
	<input style="width:250px" type="button" value="GESTIONE INVIO OSM" onclick="openPopupLarge('GestioneOSM.do?command=Details&riferimentoId=<%=StabilimentoDettaglio.getIdStabilimento()%>&riferimentoIdNomeTab=opu_stabilimento')"/>
<% }  %>	
</dhv:permission>

<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">    
    		<jsp:param name="riferimentoIdPreaccettazione" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
    		<jsp:param name="riferimentoIdNomePreaccettazione" value="stabId" />
    		<jsp:param name="riferimentoIdNomeTabPreaccettazione" value="opu_stabilimento" />
   	 		<jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>

<% if(!flagLineeScia) { %>
	<% if(permesso_modifica_scheda.doStartTag() == 1 || StabilimentoDettaglio.getStato() != 4){ %>
	<dhv:permission name="gestioneanagrafica-modifica-scheda-view">
	<div class="dropdown">
		<input type="button" onclick="mostraListaOperazioni('dropdownModifica')" value="Modifica dati scheda" class="dropbtn" style="width:250px"/>
			<div id="dropdownModifica" class="dropdown-content" style="width:250px">
			<br>
			&nbsp;&nbsp;<label><b><i>modifiche dati</i></b></label>
			<%if(StabilimentoDettaglio.getStato() != 4) { %>
				<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=cessazione">CESSAZIONE/REVOCA</a>
			<%} %>
							
			<%if (!StabilimentoDettaglio.isLineePregresse() && StabilimentoDettaglio.getStato() != 4) {%>  
				<dhv:permission name="variazione_stato_stabilimento-view"> 
					<%if(flagLineeSospese){ %>
						<a href="GestioneAnagraficaAction.do?command=AddGestioneRiattivaLinee&altId=<%=StabilimentoDettaglio.getAltId() %>">RIATTIVA LINEE SOSPESE</a>
					<% } %>
					<%if(flagLineeAttive){ %>
						<a href="GestioneAnagraficaAction.do?command=AddGestioneSospendiLinee&altId=<%=StabilimentoDettaglio.getAltId() %>">SOSPENDI LINEE</a>
					<% } %>
				</dhv:permission>
			<%} %>
			<%if(StabilimentoDettaglio.getTipoAttivita() == 1){ %>
				<% boolean compatibilita_ampliamento_noscia = false;
					for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
						LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
						if(l.getFlags().getCompatibilita_noscia() > 0)
						{
							compatibilita_ampliamento_noscia = true;
							break;
						}
					}
					%>
				<%if(compatibilita_ampliamento_noscia && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=ampliamento">AGGIUNGI LINEA</a>
			 	<%} %>
			<%} %>
			
			<%if( Numero_cu.equalsIgnoreCase("0") ){ %>
				<a href="#"
				onclick="if(confirm('Eliminare scheda?')==true)
						{
								loadModalWindowCustom('Attendere Prego...');
								window.location.href='GestioneAnagraficaAction.do?command=EliminaScheda&altId=<%=StabilimentoDettaglio.getAltId() %>&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>';		
						}">ELIMINA SCHEDA</a>
			<%} %>
			
			<dhv:permission name="gestioneanagrafica-errata-corrige-view">
				<br><br>
				&nbsp;&nbsp;<label><b><i>errata corrige</i></b></label>
				<a href="GestioneAnagraficaAction.do?command=Modify&altId=<%=StabilimentoDettaglio.getAltId() %>">ERRATA CORRIGE MODIFICA ANAGRAFICA</a>
				
				<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=modifylineanoscia">ERRATA CORRIGE MODIFICA LINEA</a>	
				
			</dhv:permission>
			
			
			<br>
			</div>
	</div>
	</dhv:permission>
	<% } %>	
<% } %>

<% if(flagLineeScia){ %>
	<% if(permesso_modifica_scheda.doStartTag() == 1 || StabilimentoDettaglio.getStato() != 4){ %>
	<dhv:permission name="gestioneanagrafica-modifica-scheda-view">
	<div class="dropdown">
		<input type="button" onclick="mostraListaOperazioni('dropdownModifica')" value="Modifica dati scheda" class="dropbtn" style="width:250px"/>
			<div id="dropdownModifica" class="dropdown-content" style="width:250px">
			<br>
			&nbsp;&nbsp;<label><b><i>modifiche dati</i></b></label>
				<%if(StabilimentoDettaglio.getTipoAttivita() == 1 && Integer.parseInt(numero_linee_aggiungibili) > 0 && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=ampliamento">AGGIUNZIONE LINEA D'ATTIVITA'</a>
				<%} %>
				
				<%if(StabilimentoDettaglio.getStato() != 4) { %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=cessazione">CESSAZIONE</a>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=cessazioneufficio&causalePratica=5">CESSAZIONE D'UFFICIO</a>
				<%} %>
				
				<%if(StabilimentoDettaglio.getTipoAttivita() == 1 && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=TemplateVariazione&altId=<%=StabilimentoDettaglio.getAltId() %>">SUBINGRESSO</a>
				<%} %>
				
				<%if(StabilimentoDettaglio.getTipoAttivita() == 2 && StabilimentoDettaglio.getOperatore().getTipo_societa() != 11 && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=TemplateVariazione&altId=<%=StabilimentoDettaglio.getAltId() %>">SUBINGRESSO</a>
				<%} %>
				
				<%if(StabilimentoDettaglio.getTipoAttivita() == 1 && StabilimentoDettaglio.getOperatore().getTipo_societa() != 11 && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=variazioneSedeLegale">VARIAZIONE SEDE LEGALE</a>
				<%} %>
				
				<%if(StabilimentoDettaglio.getTipoAttivita() == 1 && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=trasferimentoSede">TRASFERIMENTO SEDE</a>
				<%} %>
				
				<%if(StabilimentoDettaglio.getTipoAttivita() == 1 && StabilimentoDettaglio.getStato() != 4){ %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=ampliamentoFisico">AMPLIAMENTO FISICO DEL LOCALE</a>
				<%} %>
				
				<%if (!StabilimentoDettaglio.isLineePregresse() && StabilimentoDettaglio.getStato() != 4) {%>  
					<dhv:permission name="variazione_stato_stabilimento-view"> 					
						<%if(flagLineeSospese){ %>
							<a href="GestioneAnagraficaAction.do?command=AddGestioneRiattivaLinee&altId=<%=StabilimentoDettaglio.getAltId() %>">RIATTIVA LINEE SOSPESE</a>
						<% } %>
						<%if(flagLineeAttive){ %>
							<a href="GestioneAnagraficaAction.do?command=AddGestioneSospendiLinee&altId=<%=StabilimentoDettaglio.getAltId() %>">SOSPENDI LINEE</a>
						<% } %>					
					</dhv:permission>
				<%} %>
				
				<%if(hasLineePanificio) { %>
					<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=trasformazione">TRASFORMAZIONE (SOLO PANIFICI)</a>
				<% } %>
				
				<!-- 
				<dhv:permission name="opu-edit">
					<a href="#" onClick="openPopupLarge('OpuStab.do?command=ListaLivelliAggiuntivi&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>')">Livelli aggiuntivi masterlist</a>  
					<a href="#" onclick="alert('nella nuova gestione non saranno specificati');">Livelli aggiuntivi masterlist</a>
				</dhv:permission>
				-->
				
				<%if (lineePregresse){ %>
					<dhv:permission name="opu-edit">
						<a href="#" onClick="openPopupLarge('OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>')">AGGIORNA LINEE DI ATTIVITA'</a>
					</dhv:permission>
				<%} %>
				<%if( Numero_cu.equalsIgnoreCase("0") ){ %>
					<a href="#"
					   onclick="if(confirm('Eliminare scheda?')==true)
						{
								loadModalWindowCustom('Attendere Prego...');
								window.location.href='GestioneAnagraficaAction.do?command=EliminaScheda&altId=<%=StabilimentoDettaglio.getAltId() %>&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>';		
						}">ELIMINA SCHEDA</a>
				<%} %>
				
				<%if(permesso_modifica_scheda.doStartTag() != 1 && isOsm){ %>
					<a href="#" onClick="verificaPropagazionBdu('GestioneAnagraficaAction.do?command=Modify&altId=<%=StabilimentoDettaglio.getAltId() %>')">ERRATA CORRIGE MODIFICA ANAGRAFICA</a>
				<% } %>
				
				<dhv:permission name="gestioneanagrafica-errata-corrige-scia-view">
					<br><br>
					&nbsp;&nbsp;<label><b><i>funzioni hd</i></b></label>
					<a href="#" onClick="verificaPropagazionBdu('GestioneAnagraficaAction.do?command=Modify&altId=<%=StabilimentoDettaglio.getAltId() %>')">ERRATA CORRIGE MODIFICA ANAGRAFICA</a>				
					<a href="#" onClick="verificaPropagazionBdu('GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=modifylinee')">ERRATA CORRIGE MODIFICA LINEA</a>
	
					<%if(StabilimentoDettaglio.getTipoAttivita() == 1){ %>
					<dhv:permission name="gestioneanagrafica-aggiungi-linea-pregressa-view">
						<a href="GestioneAnagraficaAction.do?command=ModifyGeneric&altId=<%=StabilimentoDettaglio.getAltId() %>&operazione=lineapregressa">AGGIUNGI LINEA PREGRESSA</a>
					</dhv:permission>
					<%} %>
				
					<%if((viewSincronizzazione==true || isCanile || idLineaClinica>0)&&(p1.doStartTag()==1 || p2.doStartTag()==1)) { %>
						<dhv:permission name = "sincronizza-bdu-view">
						<% if(viewSincronizzazione==true) { %>
							<a href="#" onclick="loadModalWindow(); location.href='GestioneAnagraficaAction.do?command=Details&sincronizzaBdu=true&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>'">SINCRONIZZA BDU (ANAGRAFICA)</a>
						<% } %>
						<% if(isCanile) { %>
							<a href="#" onclick="loadModalWindow(); location.href='GestioneAnagraficaAction.do?command=Details&sincronizzaBduMq=true&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>&idRelCanile=<%=idRelCanile%>'">SINCRONIZZA BDU (SUPERFICIE MQ)</a>												
							<a href="#" onclick="mostraStatoBdu(<%=StabilimentoDettaglio.getIdStabilimento()%>)">VISUALIZZA STATO IN BDU</a>										
						<% } %>
						</dhv:permission>
						
						<% if (idLineaClinica>0) { %>
							<dhv:permission name = "sincronizza-vam-view">
								<a href="#" onclick="openPopup('OpuStab.do?command=PrepareSincronizzaVam&id=<%=StabilimentoDettaglio.getIdStabilimento()%>')">SINCRONIZZA IN VAM</a>												
							</dhv:permission>
						<% } %>
						
					<%} %>
	
					<br><br>
				</dhv:permission>
				<dhv:permission name="gestioneanagrafica-associa-pratica-add">
					&nbsp;&nbsp;<label><b><i>associazione pratiche</i></b></label>					
						<a href="#" onclick="gestione_associa_pratica(<%=StabilimentoDettaglio.getSedeOperativa().getComune() %>, 
																  <%=StabilimentoDettaglio.getIdStabilimento() %>,
																  <%=User.getUserId() %>)"
						title="operazione che consente di associare a questo stabilimento una pratica suap già inserita nel sistema">associa pratica suap</a>
						<div>
							<jsp:include page="../javascript/gestioneanagrafica/associa_pratica.jsp" />
						</div>
					
					<!-- 
					<a href="#" onclick="alert('Work in progres');">associa pratica errata corrige</a>
					<a href="#" onclick="alert('Work in progres');">associa pratica forze dell'ordine</a>
					-->
					<br><br>
				</dhv:permission>
			</div>
	</div>
	</dhv:permission>
	<% } %>
	
	
	<%if(StabilimentoDettaglio.getTipoAttivita() == 1 && !StabilimentoDettaglio.isLineePregresse()){ %>
		<dhv:permission name = "opu-sposta-controlli-view">
			<br><br>
			<div>
			<jsp:include page="../dialog_convergenza_cu_elimina_anagrafica.jsp"/>
			</div>
		</dhv:permission>
	<%} %>

<% } %>


<br>

<jsp:include page="../gestionecodicesinvsa/gestione_codice_sinvsa.jsp">
	<jsp:param name="action" value="GestioneAnagraficaAction" />
	<jsp:param name="riferimentoId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
	<jsp:param name="riferimentoIdNomeTab" value="opu_stabilimento" />
	<jsp:param name="idRuoloUtente" value="<%= User.getRoleId() %>" />
</jsp:include>

<br>

<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>" hideContainer="false">

<%if (lineePregresse){ %>
	<center>
		<b>LO STABILIMENTO HA LINEE NON AGGIORNATE <br>
		   (PER AGGIORNARLE ANDARE IN "MODIFICA DATI SCHEDA" -> "AGGIORNA LINEE DI ATTIVITA' ")
		 </b>
	</center>
	<br><br>
<%} %>
<b>ASL: <%=StabilimentoDettaglio.getAsl().toUpperCase() %></b>
<iframe scrolling="no" src="GestioneAnagraficaAction.do?command=TemplateDetails&altId=<%=StabilimentoDettaglio.getAltId() %>" id="dettaglioTemplate" style="top:0;left: 0;width:100%;height: 200%; border: none; " onload="refreshDimensioniIframe()"></iframe>

</dhv:container>

<% if (StabilimentoDettaglio.getDatiMobile().size()>0) { %>
<div style="display : none;">
	<iframe scrolling="no" 
	src="" id="dettaglioTemplateInformazioneSpecifica" style="top:0;left: 0;width:100%;height: 200%; border: none; " onload="refreshDimensioniIframe2()"></iframe>
</div>
<% } %>

<div id = "dialogStatoBdu"></div>

<script>

function stampaSchedaSpecifica(alt_id, form_in){
	openPopupStampa();
	document.getElementById('dettaglioTemplateInformazioneSpecifica').src='GestioneAnagraficaAction.do?command=TemplateDetails&altId=' + alt_id + '&id_info_specifica=' + document.getElementById('idCampoEsteso').value;
	document.getElementById('dettaglioTemplateInformazioneSpecifica').onload = function(){
		catturaHtml(form_in);		 
		form_in.submit(); 
		return closePopupStampaSchedaSpecifica();
	};
	
}

function mostraListaOperazioni(listadamostrare){
	
	var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
	
	document.getElementById(listadamostrare).classList.toggle("show");
}

//Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

function openPopup(url){
	
	  var res;
      var result;
      	  window.open(url,'popupSelect',
            'height=300px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}

function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
	
var toUrl = "" ;
function verificaPropagazionBdu(url)
{
	loadModalWindowCustom('Attendere Prego...');
	toUrl = url;
	PopolaCombo.propagazioneInBdu(<%=StabilimentoDettaglio.getIdStabilimento()%>,verificaPropagazionBduCallback);
}
	
function verificaPropagazionBduCallback(val)
{	
	<% if (isOsm) {%>
	
		if (val==true)
			alert("Attenzione! Per gli stabilimenti con linee OSM e linee soggette a propagazione in altri sistemi è possibile eseguire una richiesta di errata corrige. L'invio a SINVSA va richiesto all'HD.")
		val = false;
	<% } %>

	if(val==false)
	{
		location.href=toUrl;
	}
	else
	{
		alert("Attenzione ! per gli stabilimenti con linee soggette a propagazione in altri sistemi non è possibile eseguire una richiesta di errata corrige.")
		loadModalWindowUnlock();
	}
	
}

$( "#dialogStatoBdu" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"Stato Canile in Bdu",
    width:850,
    height:500,
    draggable: false,
    modal: true,
    buttons:{
    	 
    	 "ESCI" : function() { $(this).dialog("close");}
    	
    },
    show: {
        effect: "blind",
        duration: 1000
    },
    hide: {
        effect: "explode",
        duration: 1000
    }
   
}).prev(".ui-dialog-titlebar").css("background","#bdcfff");

function mostraStatoBdu(idStabilimento)
{

	
	loadModalWindow();
	$.ajax({
    	type: 'POST',
    	async: false,
   		dataType: "html",
   		cache: false,
  		url: 'SuapStab.do?command=StatoBdu',
        data: { "idStabilimento": idStabilimento} , 
    	success: function(msg) {
    		loadModalWindowUnlock();
       		document.getElementById('dialogStatoBdu').innerHTML=msg ; 
	
   		},
   		error: function (err, errore) {
   			alert('ko '+errore);
        }
		});
	$('#dialogStatoBdu').dialog('open');
	
	
}

</script>
 

