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
 <div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table width="100%">
<col width="33%"><col width="53%">
<tr>
<td><img style="text-decoration: none;" height="60px" max-width="80px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(mod.getAslMacello()).toLowerCase() %>.jpg" /></td>
<td align="center"><div class="titolo">
REGIONE CAMPANIA<br/>
AZIENDA SANITARIA LOCALE <%= SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() %><br/>
SERVIZIO VETERINARIO <br/> ................ <br/>
Unità Operativa Veterinaria del Distretto ...................... <br/>
INDIRIZZO ...................................................<BR/>
TELEFONO .......................... FAX ..................... <BR/>
MAIL ..............................................................</div>
</td>
<td>&nbsp; </td>
</tr>
</table>
<br/>
<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo"> Prot.  <%=mod.getProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%>
	<% if(mod.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=mod.getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%> )
				<%} %> 
 </td>
<td> </td>
<td align="right">Al Dipartimento di Prevenzione<br/>
Servizi Veterinari<br/>
dell'A.S.L. (ASL di origine animale) ..................
</td>
</tr>
</table>
<br/>
<div class="testoMedio"><center><b>OGGETTO:</b> COMUNICAZIONE RISULTATI ISPEZIONE POST MORTEM</br>
(All. I Sez. II Capo I Punto 2.a Reg. 854/04)<br/>
IDATIDOSI - (O.M. 21/04/1964)
<br/><br/>
	
<div align="left">Si comunica che presso il macello <%=nomeMacello %> sito nel comune di <%=comuneMacello %> riconosciuto con n. <%=approvalNumber %> CE alla visita ispettiva post mortem i sottoelencati animali:
<br/><br/>
<% String specie = ""; String speditore = "..........................."; String cod_speditore = "..........................."; String comune = "...........................";
	String loc = "...........................";	String mod4 = "";	String dataMod4 = "";
	int i = 0;
		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;
		 ArrayList<Capo> lista = null;
		 lista = mod.getListaCapi();
		
		 
		 for(Capo c : lista){
			i++;
			specie = ""; speditore = "..........................."; cod_speditore = "..........................."; comune = "...........................";
			loc = "...........................";	mod4 = "";	dataMod4 = "";
			
			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");				
			if(c.getCd_id_speditore() > 0){
				speditoreOrg = PopolaCombo.buildSpeditore(c.getCd_id_speditore());
			}
			if(speditoreOrg != null){
				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}
				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}
				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}
			}
			if(speditoreAddress != null){
				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}
				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}
			}
			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");
			if(c.getCd_data_mod4() != null){
				dataMod4 = toDateasString(c.getCd_data_mod4());
			}
			else {
				dataMod4 = "..........................." ;
			}
			%>
			<div align="left">
			<%= i %>. specie <%=specie%>, con matr. <%=c.getCd_matricola() %> proveniente dall'azienda 
			cod. az. <%=(c.getCd_codice_azienda_provenienza()!=null) ? c.getCd_codice_azienda_provenienza() : ".........................................." %> 
			 <%=(c.getCd_info_azienda_provenienza()!=null) ? c.getCd_info_azienda_provenienza() : "............................................."%> 
					</div> <br/>
					
			<%} %>	
			

<br/><br/> macellati in data <%=toDateasString(mod.getData()) %> sono risultati infestati da IDATIDOSI.<br/>
Tanto si comunica per gli ulteriori accertamenti ed i seguiti di competenza.<br/>
La trasmissione del documento ha valore ufficiale non si provvederà ad inviare lo stesso a mezzo posta (L.n° 412 art.6 del 30/12/1991 e succ. integ.) 
<br/><br/>
</div>

<br/>
<div align="right">IL VETERINARIO</div>
	</div>	
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>	
</body>
</html>