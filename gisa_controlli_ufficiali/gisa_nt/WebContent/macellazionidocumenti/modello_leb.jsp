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
 TreeMap<Integer, Capo> listaLEB = null;
 listaLEB = mod.getHashCapoLEB();
 TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> listaSpeditori = null;
 listaSpeditori = mod.getHashSpeditoreLEB();
 String speditore = "..........................."; 
 String cod_speditore = "..........................."; 
 String comune = "...........................";
 String loc ="............";
 String asl="............................";
 org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
 OrganizationAddress speditoreAddress = null;
 
	int i = 0;
	int sizeLista = listaLEB.size();
	
	 for(Map.Entry<Integer,Capo> elemento : listaLEB.entrySet()){
		 Capo capo = (Capo) elemento.getValue(); 
		 ModelloGenerico listaModelliElemento = (ModelloGenerico) listaModelli.get(i);
		 %> 
		 <!--  QUA DENTRO VA MESSO IL SINGOLO DOCUMENTO  -->
		 <!--  capo CONTIENE IL CAPO ATTUALE -->
		 <!--  listaModelliElemento CONTIENE IL MODELLO ATTUALE (PER PROGRESSIVO ETC) -->
		<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 
		 
<div align="right"><b>ALLEGATO F</b></div>		 
<center><div class="titolo">LEUCOSI BOVINA /BUFALINA<br/>
SCHEDA DI RILEVAMENTO DATI AL MACELLO</div></center>
		 
<table width="100%">
<col width="33%"><col width="33%"><col width="20%">
<tr>
<td class="piccolo"> Prot.  <%=listaModelliElemento.getProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%>
	<% if(listaModelliElemento.getOldProgressivo() > 0){%>
				<br/> (sostituisce il doc. con prot.  <%=listaModelliElemento.getOldProgressivo() + "/" + SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() + "/" + mod.getAnnoDataModulo()%> )
				<%} %>
 </td>
<td> e p.c.</td>
<td align="right">All'I.Z.S. di Portici <br/>
Sezione di ................................ <br/>
Al dipartimento di prevenzione<br/> Servizi Veterinari<br/>
dell'ASL ............................<br/>
Al Centro di Referenza Nazionale per lo Studio dei Retrovirus<br/>
correlati alle patologie infettive dei ruminanti (IZS Perugia)<br/>
Competente per l'allevamento di provenienza dei capi.<br/>
</tr>
</table>		 


<table>
<col width="50%">
<tr><td><b>DR.</b> .......................................... </td><td> <b>Recapito telefonico</b> .......................................... </td></tr>
<tr><td><b>ASL:</b> ..........................................  </td><td> <b>Data:</b> .......................................... </td></tr>
<tr><td><b>MACELLO:</b> <%=nomeMacello %> </td><td> <b>Tel.</b> .......................................... ; <b>Fax</b> ........................................... </td></tr>
<tr><td><b>VIA:</b> ..........................................  </td><td> <b>Comune</b> <%=comuneMacello %></td></tr>
<tr><td> <b>Specie</b> [ <%if (capo.getCd_specie()==1){ %>X<%} %> ] Bovina [ <%if (capo.getCd_specie()==2){ %>X<%} %> ] Bufalina </td> <td></td></tr>
</table>
		 
	
<table>
<col width="50%">
<tr><td> [ ] Macellazione regolare </td> <td><b>n. identificazione</b> <%=capo.getCd_matricola() %></td></tr>
<tr><td> [ ] Macellazione capo dubbio/positivo </td> <td><b>Sesso</b> [ <%if(capo.isCd_maschio()){ %>X<%} %> ] M  [ <%if(!capo.isCd_maschio()){ %>X<%} %> ] F</td></tr>
<tr><td> [ ] Macellazione capo infetto </td> <td><b>Data di nascita</b> <%=toDateasString(capo.getCd_data_nascita()) %></td></tr>
<tr><td> [ ] Macellazione capo non infetto da allevamento inf. (a mano) </td> <td><b>Razza meticcio /incrocio</b> <%=listaRazze.getSelectedValue(capo.getCd_id_razza()) %></td></tr>
</table>
<% speditore = "..........................."; 
 cod_speditore = "..........................."; 
 comune = "...........................";
  loc ="............";
 asl="............................";
speditoreOrg = null;
 speditoreAddress = null;
if(capo.getCd_id_speditore() > 0){
				speditoreOrg = PopolaCombo.buildSpeditore(capo.getCd_id_speditore());
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
				if(speditoreOrg.getSiteId() > 0){
					asl = SiteList.getSelectedValue(speditoreOrg.getSiteId());
				}
			}
			if(speditoreAddress != null){
				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}
				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}
			}%>
			<br/><br/>
			
<b>Codice allevamento di provenienza</b> <%=cod_speditore %><br/>
<b>Proprietario</b> <%=speditore %><br/>
<b>Indirizzo</b> <%=(speditoreAddress!=null) ? speditoreAddress : "............................." %><br/>
<b>Comune</b> <%=comune %> <b>Prov</b> <%=loc %><br/>
<b>A.S.L. di provenienza</b> <%=asl %> <b>distretto</b> ...................................<br/>
<br/><br/>

DESCRIZIONE MATERIALE INVIATO PER L'ESAME ISTOLOGICO<br/>

<table>
<tr><td>[ ] Milza </td> <td> </td> <td>Altro</td></tr>
<tr><td><i>Linfonodi</i></td> <td>[ ] Perirenali</td> <td>..........................................</td></tr>
<tr><td></td> <td>[ ] Sopramammari</td> <td>..........................................</td></tr>
<tr><td></td> <td>[ ] Mediastinici</td> <td>..........................................</td></tr>
<tr><td></td> <td>[ ] Perirenali</td> <td>..........................................</td></tr>
<tr><td></td> <td>[ ] Peribronchiali</td> <td>..........................................</td></tr>

</table>
<br/><br/>

DESCRIZIONE MATERIALE INVIATO PER ULTERIORI INDAGINI DIAGNOSTICHE <br/>
Prelievo di sangue<br/>
[ ] 2 provette vacutainer 10 ml tappo rosso (AGID/ ELISA)<br/>
[ ] 2 provette vacutainer 10 ml tappo viola ( PCR )<br/>
Totale campioni inviati n° ................ <br/><br/>
<br/><br/>

Firma.............................................................................<br/><br/>
<br/><br/>
I campioni prelevati devono essere mantenuti separati per organo, in contenitori a tenuta identificati con etichetta riportante il numero di matricola 
dell'animale e la tipologia dell'organo. I campioni vanno conservati a temperatura di refrigerazione e inviati nel più breve tempo possibile alla 
sezione dell'IZS competente per territorio.

		 
<%i++; 
			if (i!=sizeLista) { %>
			<div style="page-break-before:always">&nbsp; </div>  
		<% }  }%>
	
	
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>		
</body>
</html>