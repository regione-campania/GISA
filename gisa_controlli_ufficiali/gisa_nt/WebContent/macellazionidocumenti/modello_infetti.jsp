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

<jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>

<%@page import="org.aspcfs.modules.speditori.base.OrganizationAddress"%>
<%@page import="org.aspcfs.modules.macellazioni.base.Capo"%>

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
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td><img style="text-decoration: none;" height="60px" max-width="80px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(mod.getAslMacello()).toLowerCase() %>.jpg" /></td>
<td align="center"><div class="titolo">
REGIONE CAMPANIA<br/>
AZIENDA SANITARIA LOCALE <%= SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() %><br/>
SERVIZIO VETERINARIO ................ <br/>
DISTRETTO SANITARIO DI ............................... <br/>
.......................................................</div>
</td>
<td>&nbsp; </td>
</tr>
</table>

<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo"> Prot.  <%=mod.getProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%>
	<% if(mod.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=mod.getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%> )
				<%} %> 
 </td>
<td> </td>
<td> Al Dipartimento di Prevenzione<br/>
Servizio Veterinario<br/>
la ASL ..................<br/>
Fax: ........................<br/>
Alla Regione .................<br/>
Assessorato Sanità<br/>
Servizio Veterinario<br/>
Fax: ........................<br/>
Alla Regione CAMPANIA<br/>
Assessorato Sanità<br/>
Servizio Veterinario (da inviare x email)<br/>
Al Ministero della Salute<br/>
Direzione Generale della Sanità<br/>
Animale e del Farmaco Veterinario<br/>
Ufficio II<br/>
Via Ribotta<br/>
ROMA (da inviare x email)</td>
</tr>
</table>

<div class="testoMedio"><center><b>OGGETTO: Certificazione del ricevimento degli animali infetti e dell'avvenuta macellazione</b>
<br/><br/>
Si attesta che in data <%=toDateasString(mod.getData()) %> i sottoelencati capi della specie:</center>
<br/><br/>
<% String specie = ""; String speditore = "..........................."; String cod_speditore = "..........................."; String comune = "...........................";
	String loc = "...........................";	String mod4 = "";	String dataMod4 = "";
	int i = 0;
		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;
		 ArrayList<Capo> lista = null;
		 ArrayList<Capo> lista2 = null;
		 
		 if (mod.getMalattiaCapo()!=null && mod.getMalattiaCapo().equalsIgnoreCase("TUBERCOLOSI")){
			 lista = mod.getListaCapiTubercolosi();
			 lista2 = mod.getListaCapiBrucellosi();
		 }
		 else if (mod.getMalattiaCapo()!=null && mod.getMalattiaCapo().equalsIgnoreCase("BRUCELLOSI")){
			 lista = mod.getListaCapiBrucellosi();
			 lista2 = mod.getListaCapiTubercolosi();
		 }
		 
		 if (lista!=null)
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
			
			<%= i %>. specie <%=specie%> proveniente dall'azienda 
			cod. az. <%=(c.getCd_codice_azienda_provenienza()!=null) ? c.getCd_codice_azienda_provenienza() : ".........................................." %> 
			 <%=(c.getCd_info_azienda_provenienza()!=null) ? c.getCd_info_azienda_provenienza() : "............................................."%> 
			 scortato dal mod. 4 n° <%=mod4%> datato <%= dataMod4%> <br/>
			<%} %>			

<br/><br/>
infetti da <%=mod.getMalattiaCapo() %> sono giunti in vincolo sanitario presso questo stabilimento di macellazione.<br/>
I suddetti animali sono stati sottoposti a macellazione in data <%=toDateasString(mod.getData()) %> <br/>

Elenco degli animali abbattuti: <br/>

<%	 
i=1;
if (lista!=null)
	for(Capo c : lista){
%>
<%=i++ %> Matricola  <%=c.getCd_matricola() %><br/>
<%} %>
</div>
	<% if (mod.getMod2()!=null && lista2.size()>0) { %>
	<div style="page-break-before:always">&nbsp; </div>  
	<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td><img style="text-decoration: none;" height="60px" max-width="80px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(mod.getMod2().getAslMacello()).toLowerCase() %>.jpg" /></td>
<td><div class="titolo">
REGIONE CAMPANIA<br/>
AZIENDA SANITARIA LOCALE <%= SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() %><br/>
SERVIZIO VETERINARIO ................ <br/>
DISTRETTO SANITARIO DI ............................... <br/>
..............................................................</div>
</td>
<td>&nbsp; </td>
</tr>
</table>

<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo">Prot.  <%=mod.getMod2().getProgressivo() + "/" + SiteList.getSelectedValue(mod.getMod2().getAslMacello()).toUpperCase() + "/" + mod.getMod2().getAnnoDataModulo()%>
	<% if(mod.getMod2().getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=mod.getMod2().getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getMod2().getAslMacello()).toUpperCase() + "/" + mod.getMod2().getAnnoDataModulo()%> )
				<%} %> 
 </td>
<td> </td>
<td> Al Dipartimento di Prevenzione<br/>
Servizio Veterinario<br/>
la ASL ..................<br/>
Fax: ........................<br/>
Alla Regione .................<br/>
Assessorato Sanità<br/>
Servizio Veterinario<br/>
Fax: ........................<br/>
Alla Regione CAMPANIA<br/>
Assessorato Sanità<br/>
Servizio Veterinario (da inviare x email)<br/>
Al Ministero della Salute<br/>
Direzione Generale della Sanità<br/>
Animale e del Farmaco Veterinario<br/>
Ufficio II<br/>
Via Ribotta<br/>
ROMA (da inviare x email)</td>
</tr>
</table>

<div class="testoMedio">
<center><b>OGGETTO: Certificazione del ricevimento degli animali infetti e dell'avvenuta macellazione</b>
<br/><br/>
Si attesta che in data <%=toDateasString(mod.getData()) %> i sottoelencati capi della specie:</center>
<br/><br/>
<% String specie2 = ""; String speditore2 = "..........................."; String cod_speditore2 = "..........................."; String comune2 = "...........................";
	String loc2 = "...........................";	String mod42 = "";	String dataMod42 = "";
	int i2 = 0;
		org.aspcfs.modules.speditori.base.Organization speditoreOrg2 = null;
		OrganizationAddress speditoreAddress2 = null;
		 if (lista2!=null)
		for(Capo c : lista2){
			i2++;
			specie2 = ""; speditore = "..........................."; cod_speditore2 = "..........................."; comune2 = "...........................";
			loc2 = "...........................";	mod42 = "";	dataMod42 = "";
			specie2 = listaSpecie.getValueFromId(c.getCd_specie());
			specie2 = (specie2 != null && !specie2.equals("") ? specie2 : "...........................");				
			if(c.getCd_id_speditore() > 0){
				speditoreOrg2 = PopolaCombo.buildSpeditore(c.getCd_id_speditore());
			}
			if(speditoreOrg2 != null){
				if( speditoreOrg2.getName() != null && !speditoreOrg2.getName().equals("") ){
					speditore2 = speditoreOrg2.getName();
				}
				if( speditoreOrg2.getAccountNumber() != null && !speditoreOrg2.getAccountNumber().equals("") ){
					cod_speditore2 = speditoreOrg2.getAccountNumber();
				}
				if(speditoreOrg2.getAddressList().size() > 0){
					speditoreAddress2 = (OrganizationAddress) speditoreOrg2.getAddressList().get(0);
				}
			}
			if(speditoreAddress2 != null){
				if( speditoreAddress2.getCity() != null && !speditoreAddress2.getCity().equals("") ){
					comune2 = speditoreAddress2.getCity();
				}
				if( speditoreAddress2.getState() != null && !speditoreAddress2.getState().equals("") ){
					loc2 = speditoreAddress2.getState();
				}
			}
			mod42 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");
			if(c.getCd_data_mod4() != null){
				dataMod42 = toDateasString(c.getCd_data_mod4());
			}
			else {
				dataMod42 = "..........................." ;
			}
			%>
			
			<%= i2 %>. specie <%=specie2%> proveniente dall'azienda <%= speditore2%> cod. az. <%=cod_speditore2%> 
					sita nel comune di <%=comune2%> in loc. <%=loc2%> scortato dal mod. 4 n° <%=mod42%> datato <%= dataMod42%> <br/>
			<%} %>			

<br/><br/>
infetti da <%=mod.getMod2().getMalattiaCapo() %> sono giunti in vincolo sanitario presso questo stabilimento di macellazione.<br/>
I suddetti animali sono stati sottoposti a macellazione in data <%=toDateasString(mod.getMod2().getData()) %> <br/>

Elenco degli animali abbattuti: <br/>
<%	 i2 = 1;
if (lista2!=null)
	for(Capo c : lista2){
%>
<%=i2++ %> Matricola  <%=c.getCd_matricola() %><br/>
<%} %>
	</div>
	<%} %>

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>

</body>
</html>