<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="macello" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="mod" class="org.aspcfs.modules.macellazionidocumenti.base.ModelloGenerico" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaSpecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvvedimentiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaLuoghiVerifica" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaRazze" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>

<%@page import="org.aspcfs.modules.speditori.base.OrganizationAddress"%>
<%@page import="org.aspcfs.modules.macellazioni.base.Capo"%>
<%@page import="org.aspcfs.modules.macellazioni.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioni.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazionidocumenti.base.ModelloGenerico"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="macellazionidocumenti/css/macelli_moduli_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="macellazionidocumenti/css/macelli_moduli_print.css" />

<body>
 <% 
 ArrayList<ModelloGenerico> listaModelli = null;
 listaModelli = mod.getListaModelli();
 ArrayList<Capo> lista = null;
	lista = mod.getListaCapiMortiAnte();
	int i = 0;
	int sizeLista = lista.size();
	String specie ="";
	String razza="";
	String sesso = "";
	String luogo ="";
	
	for(Capo c : lista){
	 ModelloGenerico listaModelliElemento = (ModelloGenerico) listaModelli.get(i);%>
	
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table width="100%">
<col width="33%"><col width="53%">
<tr>
<td><img style="text-decoration: none;" height="60px" max-width="80px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(mod.getAslMacello()).toLowerCase() %>.jpg" /></td>
<td align="center"><div class="titolo">
REGIONE CAMPANIA<br/>
AZIENDA SANITARIA LOCALE <%= SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() %><br/>
SERVIZIO VETERINARIO ................ <br/>
Unità Operativa Veterinaria del Distretto ...................... <br/>
INDIRIZZO ...................................................<BR/>
TELEFONO .......................... FAX ..................... <BR/>
MAIL ..............................................................</div>
</td>
<td>&nbsp; </td>
</tr>
</table>

<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo"> Prot.  <%=listaModelliElemento.getProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%>
	<% if(listaModelliElemento.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=listaModelliElemento.getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%> )
				<%} %>
 </td>
<td> </td>
<td align="right">Allo stabilimento della ditta ......................................<br/>
Al Sig ................................ proprietario dell'animale ..........................<br/>
Al Responsabile dello stabilimento ..............................<br/>
All'ASL ............................<br/>
Al PIF e UVAC ....................................<br/>
..................................................<br/>
..................................................<br/>
..................................................</td>
</tr>
</table>
<br/>
<div class="testoMedio"><center><b>OGGETTO: Rilevazione di animale morto in fase antecedente la macellazione (STABILIMENTO  <%=nomeMacello%> con numero CE  <%=approvalNumber %>)</b>
<br/><br/>

<%specie = listaSpecie.getValueFromId(c.getCd_specie());
specie = (specie != null && !specie.equals("") ? specie : "...........................");
razza = listaRazze.getValueFromId(c.getCd_id_razza());
razza = (razza != null && !razza.equals("") ? razza : "...........................");
sesso = c.isCd_maschio() ? "M" : "F";
luogo = listaLuoghiVerifica.getSelectedValue(c.getMavam_luogo());
if(c.getMavam_luogo() == 3){
	String desc_luogo = c.getMavam_descrizione_luogo_verifica();
	desc_luogo = (desc_luogo != null && !desc_luogo.equals("") ? desc_luogo : "...........................");
	luogo = luogo + ": " + desc_luogo;
}
	%>	
	
<div align="left">Si comunica che in data <%=toDateasString(mod.getData()) %>il Servizio Veterinario operante presso lo stabilimento in oggetto da 
controlli effettuati ha rilevato nel luogo "<%=luogo %>" che l'animale di seguito descritto è morto antecedentemente alla macellazione: <br/><br/>

	
specie <%=specie %> , razza <%=razza %> , sesso <%=sesso %> con matr. <%=c.getCd_matricola() %>

<br/><br/>
L'animale sopra indicato è inviato allo stabilimento in indirizzo per la distruzione.<br/><br/>
NOTE<br/>
............................................................................................................................<br/><br/>
............................................................................................................................<br/><br/>
............................................................................................................................<br/><br/>
<br/><br/>
</div>

<br/>
<div align="left">DATA</div>
<div align="right">IL VETERINARIO</div>
	</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
<% 
i++;
	if (i!=sizeLista) { %>
	<div style="page-break-before:always">&nbsp; </div>  
	<% } %> 

<% }%>
	
	
</body>
</html>