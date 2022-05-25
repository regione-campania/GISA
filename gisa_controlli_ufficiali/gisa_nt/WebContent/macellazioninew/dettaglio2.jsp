<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninew.base.CategoriaRischio"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.ReflectionUtil"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Esito"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.DestinatarioCarni"%>
<%@page import="com.darkhorseventures.database.ConnectionPool" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />
<jsp:useBean id="Capo"				class="org.aspcfs.modules.macellazioninew.base.Capo"	scope="request" />
<%
	ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute("ConnectionElement");
	ConnectionPool sqlDriver = (ConnectionPool) request.getServletContext().getAttribute("ConnectionPool");
    Connection db = sqlDriver.getConnection(ce,null);
    boolean stampatoArt17 = (Boolean)request.getAttribute("stampatoArt17");
%>

<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="javascript/ui.core.js"></script>

<!-- <script type="text/javascript" src="javascript/ui.tabs.js"></script> -->
<script type="text/javascript">
$(function() {
	$("#tabs").tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
	$("#tabs li").removeClass('ui-corner-top').addClass('ui-corner-left');
});

</script>

<SCRIPT LANGUAGE="JavaScript" SRC="macellazioninew/cambio_specie.js"></SCRIPT> 

<script language="javascript">

function openPopupErrataCorrigeArt17(idPartita, orgId, tipo){
        var res;
        var result;
          	  window.open('MacellazioniDocumentiNew.do?command=ViewModuleErrataCorrigeArt17&orgId='+orgId+'&idPartita='+idPartita+'&tipoMacello='+tipo,'popupSelect',
              'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
} 

</script>

<script language="javascript">
function openPopupListaErrataCorrigeArt17(idPartita, orgId, tipo){
        var res;
        var result;
      
        	  window.open('MacellazioniDocumentiNew.do?command=ViewListaModuleErrataCorrigeArt17&orgId='+orgId+'&idPartita='+idPartita+'&tipoMacello='+tipo,'popupSelect',
              'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
 } 

function openPopupCancellazionePartita(idPartita){
    var res;
    var result;
   	  window.open('MacellazioniNew.do?command=PrepareCancellazionePartita&idPartita='+idPartita,'popupSelect',
          'height=300px,width=380px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
} 


</script>

 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

<style type="text/css">
	
/* Vertical Tabs
----------------------------------*/
.ui-tabs-vertical { width: 65em; }
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-selected { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: left; width: 50em;}

</style>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> >
			<%
				if (request.getParameter("return") == null)
				{
			%>
					<a href="Stabilimenti.do?command=Search"><dhv:label
					name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
					<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>">Scheda Stabilimento</a> >
			<%
				}
				else if (request.getParameter("return").equals("dashboard"))
				{
			%>
					<a href="Stabilimenti.do?command=Dashboard"><dhv:label
					name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
				}
			%>
			<a href="MacellazioniNew.do?command=List&orgId=<%=OrgDetails.getOrgId() %>">Macellazioni</a> > Dettaglio Partita
		</td>
	</tr>
</table>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
String param2 = "capoId=" + Capo.getId();
%>

<%@page import="java.util.ArrayList"%>
<dhv:container 
	name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati") : ("stabilimenti") %>"
	selected="macellazioni" 
	object="OrgDetails" 
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">

<br/>

<%@page import="org.aspcfs.modules.macellazioninew.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Campione"%><%@page import="org.aspcfs.modules.macellazioninew.base.Organi"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.PatologiaRilevata"%>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.TipoRicerca"%><jsp:useBean id="Tampone"			class="org.aspcfs.modules.macellazioninew.base.Tampone"	scope="request" />

<jsp:useBean id="Partita"				class="org.aspcfs.modules.macellazioninew.base.Partita"	scope="request" />
<jsp:useBean id="Speditore" class="org.aspcfs.modules.speditori.base.Organization" 	scope="request" />
<jsp:useBean id="SpeditoreAddress" class="org.aspcfs.modules.speditori.base.OrganizationAddress" 	scope="request" />
<jsp:useBean id="NCVAM"				class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="OrganiList" 		class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="OrganiListNew" 	class="java.util.TreeMap"							scope="request" />
<jsp:useBean id="PatologieRilevate"	class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="Esiti"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="Categorie"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="EsitiId"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="casl_NCRilevate"	class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="MatriciTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AnalisiTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiMonitoraggio" class="org.aspcfs.utils.web.LookupList" scope = "request"/>

<jsp:useBean id="casl_Provvedimenti_effettuati"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="Nazioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Matrici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ASL"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LuoghiVerifica"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Regioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Razze"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Specie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBovine"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiRisanamento"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="BseList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiMacellazione"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiVpm"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Patologie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PatologieOrgani"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Azioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Stadi"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Organi"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiAnalisi"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Molecole"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviASL"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvvedimentiVAM"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="look_ProvvedimentiCASL"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiCampioni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviCampioni"	class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ include file="../initPage.jsp"%>

<%if(ApplicationProperties.getProperty("visibilita_link_macelli").equals("si") ){ %>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintInvioCampioniTBC&file=invio_campioni_TBC.xml&<%=param2 %>">Invio campioni TBC</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintModelloLBE&file=modello_LBE.xml&<%=param2 %>">Modello LBE</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintModello1033TBC&file=modello_10_33_TBC.xml&<%=param2 %>">Modello 10 33 TBC</a>
</dhv:permission>
<%} %>

<br/><br/>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
<%
	if(Partita.getStato_macellazione().equals("OK.") || Partita.getStato_macellazione().contains("Incompleto: Presenti campioni senza esito") )
	{
%>
		<%--<a href="MacellazioniDocumentiNew.do?command=EsercentiArt17&<%=param1 %>&id=<%=Partita.getId() %>&comboDateMacellazione=<%=toDateasString(Partita.getVpm_data()) %>" style="text-decoration:none"><input type="button" value="Articolo 17"/></a>--%>
		<%--<a href="GestioneDocumenti.do?command=GeneraPDFMacelli&<%=param1 %>&tipo=Macelli_17&idPartita=<%=Partita.getId() %>&data=<%=toDateasString(Partita.getVpm_data()) %>" style="text-decoration:none"><input type="button" value="Articolo 17"/></a>--%>
<%
	}
	else
	{
		//out.println("Non è possibile stampare l'Articolo 17 perchè la partita si trova nello stato " + Partita.getStato_macellazione() + "<br/>");
	}
%>
	
</dhv:permission>

<%if(!stampatoArt17 && ! Partita.isModello10() && !Partita.isChiusa()){ %>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-edit">
	<input type="button" value="Modifica" 
	onclick="location.href='MacellazioniNew.do?command=ModificaCapo&id=<%=Partita.getId() %>&orgId=<%=OrgDetails.getOrgId() %>'"
	 />
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-delete">
	<input type="button" value="Elimina" onclick="if(confirm('Sicuro di voler eliminare la registrazione?')){location.href='MacellazioniNew.do?command=Delete&id=<%=Partita.getId() %>&orgId=<%=OrgDetails.getOrgId() %>'}" />
</dhv:permission>
<%} else{ 
if (Partita.getStato_macellazione().equals("Incompleto: Presenti campioni senza esito."))
{
%>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-edit">
	<input type="button" value="Inserisci Esito Campioni" onclick="location.href='MacellazioniNew.do?command=ModificaCapo&id=<%=Partita.getId() %>&orgId=<%=OrgDetails.getOrgId() %>'" />
</dhv:permission>
<%	
}
else if(Partita.isChiusa())
{
%>
	Partita chiusa, non modificabile
<%
}
else
{
%>
Partita non modificabile: per essa è stata già stampato l'art. 17 o Modello 10

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-edit">
 <% if (Partita.getErrata_corrige_generati()<10){ %>
<input type="button" value="Errata corrige art.17" onclick="openPopupErrataCorrigeArt17('<%=Partita.getId() %>', '<%=OrgDetails.getOrgId() %>', '<%=request.getAttribute("tipo") %>')" />
<%} else { %>
; Raggiunto numero massimo moduli Errata Corrige generabili per questa partita.
<%} %>
<a href="javascript:openPopupListaErrataCorrigeArt17('<%=Partita.getId() %>', '<%=OrgDetails.getOrgId() %>', '<%=request.getAttribute("tipo") %>')"> Moduli EC generati</a> 
<br/><br/>
</dhv:permission>

<%}} %>

<dhv:permission name="macellazioni-gestione-cancellazioni-capipartite-delete">
<center>
<table border="1px solid black">
<tr><td>
<input type="button" style="background:red" value="ELIMINA" onClick="openPopupCancellazionePartita('<%=Partita.getId()%>')"/>
</td></tr>
</table>
</center>
</dhv:permission>

<div class="demo">

<%
	boolean visualizzaSezioneBSE= true;
%>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Controllo Documentale</a></li>
		<% if (false) { %> <li><a href="#tabs-14">Partita sottoposta a test TSE</a></li> <% } %> 
		<li><a href="#tabs-7">Comunicazioni Esterne</a></li>
        <%--<li><a href="#tabs-5">Respingimento</a></li> --%>
		<%-- li><a href="#tabs-6">Blocco Animale</a></li --%>
		<li><a href="#tabs-4">Morte Ant.Macellazione</a></li>
		<li><a href="#tabs-2">Evidenza Visita AM</a></li>
        <li><a href="#tabs-3">Evidenza Visita PM</a></li>
        <%--<li><a href="#tabs-9">Macellazione e Distruz. Carcassa</a></li> --%>
		<%--<li><a href="#tabs-15">Ricezione Esito Accertamenti</a></li> --%>
	</ul>
<div id="tabs-1">

<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody>
    <tr>
        <td valign="top" width="55%">
        
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
            <tbody>
            
            <tr>
                <th colspan="2"><strong>Provenienza animale</strong></th>
            </tr>
       
            <tr class="containerBody">
            	<td class="formLabel">Codice Azienda di provenienza</td>
            	<td>
            	<%=Partita.getCd_codice_azienda_provenienza() %>
            	<div id="rowInfoAzienda" align="right" style="padding-right: 20px;"><%= toHtml2( Partita.getCd_info_azienda_provenienza()) %></div>	
            	</td>
            </tr>
            
 <%--           
            <tr class="containerBody">
               <td class="formLabel">Stato</td>
               <td>
               		<%=Nazioni.getSelectedValue( Partita.getCd_provenienza_stato() ) %>

               </td>
            </tr>
            
			<tr class="containerBody">
                <td class="formLabel" >A.S.L.<br></td>
                <td>
                	<%=ASL.getSelectedValue( Partita.getCd_asl() ) %>&nbsp;
				</td>
            </tr>
      
<%-- 		PER IL MOMENTO DISABILITATO 7 OTT 2010 
            <tr class="containerBody">
               <td class="formLabel">Comunitario</td>
               <td>	
                   <%=Partita.isCd_prov_stato_comunitario() ? ("SI") : ("NO") %>
               </td>
            </tr>
           
            <tr class="containerBody">
            	<td class="formLabel">Regione</td>
            	<td>
            		<%=Regioni.getSelectedValue( Partita.getCd_provenienza_regione() ) %>&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody">
            	<td class="formLabel">Comune</td>
            	<td>
            		<%=toHtmlValue( Partita.getCd_provenienza_comune() ) %>&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Speditore</td>
                <td>
                	<%=toHtmlValue( Partita.getCd_speditore() ) %>&nbsp;
				</td>
            </tr>
            <tr class="containerBody" >
                <td class="formLabel" >Codice Azienda Speditore</td>
                <td>
                	<%=toHtmlValue( Partita.getCd_codice_speditore() ) %>&nbsp;
				</td>
            </tr>
--%>            
            <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >partita</td>
                <td>
                	<%=Partita.getCd_partita()%> 
                </td>
            </tr>
            
             <tr class="containerBody">
            	<td class="formLabel">Commerciante/Proprietario degli animali</td>
            	<td>
	            	<%=Partita.getProprietario() %>
            	</td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Codice Azienda di Nascita</td>
                <td>
                	<%=Partita.getCd_codice_azienda() %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
            
            <input type="hidden" id="Specie1" name="Specie1" value="<%=(Partita.isSpecie_suina()) ? "Cinghiali" : "Ovini" %>"/>
            <input type="hidden" id="Specie2" name="Specie2" value="<%=(Partita.isSpecie_suina()) ? "Suini" : "Caprini" %>"/>
                
                <td class="formLabel" >Specie<br></td>
                <td>
<%
                	if(Partita.getCd_num_capi_ovini()>0)
                		out.println("<label class=\"specie1\">Ovini</label> ");	
					if(Partita.getCd_num_capi_caprini()>0)
					{
						if(Partita.getCd_num_capi_ovini()>0)
							out.println(", ");
						out.println("<label class=\"specie2\">Caprini</label> ");
					}
%>
				</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie1">Ovini</label> </td>
                <td>
<%
                	String num_capi_ovini = "";
                	if(Partita.getCd_num_capi_ovini()>0)
                		num_capi_ovini = Partita.getCd_num_capi_ovini()+"";
                	out.println(num_capi_ovini);
%>
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie2">Caprini</label> </td>
                <td>
<%
                	String num_capi_caprini = "";
                	if(Partita.getCd_num_capi_caprini()>0)
                		num_capi_caprini = Partita.getCd_num_capi_caprini()+"";
                	out.println(num_capi_caprini);
%>
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Dettagli Partita</td>
                <td>
<%					
                	for( CategoriaRischio cat: (ArrayList<CategoriaRischio>)Categorie)
                	{
                		if(cat.getId_categoria()==0)
                			out.println("0 [Default]<br/>");
                		else if(cat.getId_categoria()==1){
                			out.println("1 [Presenza animali di et&agrave; &gt; 18 mesi]<br/>");%>
                			Numero capi <label class="specie1">Ovini</label> > 18 mesi: <%=Partita.getCd_num_capi_ovini_18() %> <br/> 
                			Numero capi <label class="specie2">Caprini</label> > 18 mesi: <%=Partita.getCd_num_capi_caprini_18() %><br/> 
                		<%
                		}
                		else if(cat.getId_categoria()==2)
                			out.println("2 [MAC. DIFFERITA IN CASO DI BRUC.]<br/>");
                		
					} 
%>
                	<%=(Categorie.size() > 0) ? ("") : ("&nbsp;") %>
                	
                	
                	
				</td> 
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Vincolo sanitario</td>

              <td valign="middle">
              	<%=Partita.isCd_vincolo_sanitario() ? "SI" : "NO" %>
                <%
                	String motivo = Partita.getCd_vincolo_sanitario_motivo();
                	if(motivo != null && !motivo.trim().equals("")){
                %>
                Motivo: <%= motivo %>
                <%		
                	}
                %>
              </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Numero Mod. 4/ Cert.Sanit.</td>
                <td>
                	<%=Partita.getCd_mod4() %>&nbsp;
				</td>
            </tr>
            
			<tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Data Mod. 4/ Cert.Sanit.</td>
              <td>
              		<%=toDateasString(Partita.getCd_data_mod4()) %>&nbsp;
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Macell. differita piani risanamento</td>
              <td>
              	<%=PianiRisanamento.getSelectedValue( Partita.getCd_macellazione_differita() ) %>&nbsp;
              	<%if( Partita.getCd_macellazione_differita() > 0 && ( Partita.getVpm_data() != null || Partita.getMavam_data() != null ) ){ %>
              		<br/>
              	<%} %>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Partita sottoposta a test TSE</td>
              <td>
              	<%=( Partita.getCd_bse() ) ? ("SI, " ) : ( "NO" ) %>
<%
                	num_capi_ovini = "";
					num_capi_caprini = "";
                	if(Partita.getCd_num_capi_ovini_da_testare()>0)
	                	out.println("<br/>numero capi 	<label class=\"specie1\">Ovini</label>  da testare: " + Partita.getCd_num_capi_ovini_da_testare());
                	if(Partita.getCd_num_capi_caprini_da_testare()>0)
	                	out.println("<br/>numero capi 	<label class=\"specie2\">Caprini</label>  da testare: " + Partita.getCd_num_capi_caprini_da_testare());
%>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel">Disponibili informazioni catena alimentare </td>
              <td><%=Partita.isCd_info_catena_alimentare() ? "SI" : "NO" %></td>
            </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Data arrivo al macello</td>
                <td><zeroio:tz timestamp="<%=Partita.getCd_data_arrivo_macello() %>" showTimeZone="false" dateOnly="true" /> </td>
            </tr>
            
             <tr class="containerBody">
              <td class="formLabel">Data di arrivo dichiarata dal Gestore </td>
              <td><%=Partita.isCd_data_arrivo_macello_flag_dichiarata() ? "SI" : "NO" %></td>
            </tr>
            
            <tr>
                <th colspan="2"><strong>Identificazione Mezzo di Trasporto</strong></th>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Tipo</td>
                <td>
                	<%=Partita.getCd_tipo_mezzo_trasporto() != null ? Partita.getCd_tipo_mezzo_trasporto() : "" %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Targa</td>
                <td>
                	<%=Partita.getCd_targa_mezzo_trasporto() != null ? Partita.getCd_targa_mezzo_trasporto() : ""%>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
              	<td class="formLabel">Trasporto superiore<br>a 8 ore</td>
              	<td><%=Partita.isCd_trasporto_superiore8ore() ? "SI" : "NO" %></td>
            </tr>
            
<!--            -->

	<% if (visualizzaSezioneBSE) { %>
		<tbody>
            <tr>

              <th colspan="2"><strong>Partita sottoposta a test TSE</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data Prelievo</td>
                <td><zeroio:tz timestamp="<%=Partita.getBse_data_prelievo() %>" showTimeZone="false" dateOnly="true" /> </td>
            </tr>
                
             <tr class="containerBody">
                <td class="formLabel">Data Ricezione Esito</td>
                                                          <td><%=toDateasString(Partita.getBse_data_ricezione_esito()) %>&nbsp; </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
<%
				if(Partita.getCd_num_capi_ovini_da_testare()>0 || Partita.getCd_num_capi_caprini_da_testare()>0)
				{
					if(Partita.getBse_esito()==null || Partita.getBse_esito().equals("-1"))
						out.println("In attesa di esito");
					else
						out.println(Partita.getBse_esito());
				}
%>                
                </td>
             </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Partita.getBse_note() %></td>
            </tr>
        </tbody>
	<% } %>			


<!--            -->
            
             <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo </strong>                </th>
            </tr>
              <tr class="containerBody">
                <td class="formLabel" rowspan="3">&nbsp; </td>
                <td>&nbsp;1. <%=Partita.getCd_veterinario_1() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Partita.getCd_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Partita.getCd_veterinario_3() %></td>
            </tr>
            
            <tr>
                <th colspan="2"><strong>Dettagli addizionali</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >Note</td>
                <td>
                	<%=Partita.getCd_note() != null ? Partita.getCd_note() : ""%>&nbsp;
                </td>
            </tr>
				
            
        </tbody></table>        </td>
          

   

	</tr>
	</tbody>
</table>	
	</div>
	
	<div id="tabs-2">
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">



				<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
          <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >partita</td>
                <td>
                	<%=Partita.getCd_partita()%>
                </td>
            </tr>
            
          
              <%
            if(!Partita.getCd_specie().equals(""))
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Partita.getCd_specie()%>
                </td>
            </tr>
            <%} 
                
            %>
            <tr>
              <th colspan="2"><strong>Visita Ante Mortem </strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Partita.getVam_data()) %>&nbsp; </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td><%=Partita.getVam_esito()%></td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie1">Ovini</label> </td>
                <td>
<%
                	num_capi_ovini = "";
                	if(Partita.getVam_num_capi_ovini()>0)
                		num_capi_ovini = Partita.getVam_num_capi_ovini()+"";
                	else
                		num_capi_ovini = "0";
                	out.println(num_capi_ovini);
%>
                </td>
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie2">Caprini</label> </td>
                <td>
<%
                	num_capi_caprini = "";
                	if(Partita.getVam_num_capi_caprini()>0)
                		num_capi_caprini = Partita.getVam_num_capi_caprini()+"";
                	else
                		num_capi_caprini = "0";
                	out.println(num_capi_caprini);
%>
                </td>
            </tr>

            <tr class="containerBody">
                <td class="formLabel">Provvedimento adottato</td>
           		 <td>           		
           		 <%=ProvvedimentiVAM.getSelectedValue( Partita.getVam_provvedimenti() ) %> &nbsp;
           		 </td>
    				
            </tr>
            
            
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<% if (Partita.isVam_to_asl_origine()) { %>
            			ASL origine <br/>
            		<% } %>
            		
            		<% if (Partita.isVam_to_proprietario_animale()) { %>
            			Proprietario animale <br/>
            		<% } %>
            		
            		<% if (Partita.isVam_to_azienda_origine()) { %>
	            		Azienda di origine <br/>
            		<% } %>
            			
            		<% if (Partita.isVam_to_proprietario_macello()) { %>
            			Proprietario macello <br/>
            		<% } %>
            		
            		<% if (Partita.isVam_to_pif()) { %>
            			P.I.F. <br/>
            		<% } %>
            		
            		<% if (Partita.isVam_to_uvac()) { %>
            			U.V.A.C. <br/>
            		<% } %>
            		
            		<% if (Partita.isVam_to_regione()) { %>
            			Regione <br/>
            		<% } %>
            		
            		<% if (Partita.isVam_to_altro()) { %>
            			Altro <%= Partita.getVam_to_altro_testo() != null && !Partita.getVam_to_altro_testo().equals("")? ": " + Partita.getVam_to_altro_testo() : "" %><br/>
            		<% } %>
            		&nbsp;
            	</td>
            </tr>
            
                 </table>
                </td>

    </tr>
    
    <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    			<tr>
    				<th colspan="5">Non Conformità</th>
    			</tr>
    			<%if( NCVAM.size() > 0 ){ %>
    			<tr>
    				<th>Tipo</th> <th>Note</th> 
    			</tr>
    			
    			<%
    				for( int i = 0; i < NCVAM.size(); i++ )
    				{
    					NonConformita temp = (NonConformita)NCVAM.get( i );
    			%>
    			
    			<tr>
    				<td><%=TipiNonConformita.getSelectedValue( temp.getId_tipo() ) %>&nbsp;</td>    				
    				<td><%=toHtmlValue( temp.getNote() ) %>&nbsp;</td>
    				
    			</tr>
    			
    			<%} }else{%>
    			<tr>
    				<td colspan="5" class="formLabel"> Nessuna Non Conformità</td>
    			</tr>
    			<%} %>
   			</table>
 		</td>
 	</tr>
    
    </tbody></table>
    
      </td>






<table width="100%" border="0" cellpadding="2" cellspacing="2" style="display: none">
    <tbody><tr>
        <td valign="top" width="55%">
		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>

        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Abbattimento</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              	<%=toDateasString(Partita.getAbb_data()) %>&nbsp;
             </td>

            </tr>
                     
                 <tr class="containerBody">
                <td class="formLabel">Motivo</td>
                <td><%=Partita.getAbb_motivo() %>&nbsp;</td>
                </tr>
                
<!--               <tr class="containerBody">-->
<!--               <td class="formLabel">distruzione carcassa</td>-->
<!--               <td>	-->
<!--                   <%=Partita.isAbb_dist_carcassa() ? ("SI") : ("NO") %>-->
<!--               </td>-->
<!--            </tr>-->
            <tr class="containerBody">
                <td class="formLabel" rowspan="4">Veterinari addetti al controllo</td>
                <td>&nbsp;1. <%=Partita.getAbb_veterinario() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Partita.getAbb_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Partita.getAbb_veterinario_3() %></td>
            </tr> 
          </tbody>
        </table>

			  </td>

        
        

        <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>
		
</td>

</tr></tbody></table>



</div>
	
	
	
	
	
<div id="tabs-3">
	<table width="100%" border="0" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">
		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
<tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >partita</td>
                <td>
                	<%=Partita.getCd_partita()%>
                </td>
            </tr>
            
          
              <%
            if(!Partita.getCd_specie().equals(""))
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
<%
                	if(Partita.getCd_num_capi_ovini()>0)
                		out.println("	<label class=\"specie1\">Ovini</label> ");	
					if(Partita.getCd_num_capi_caprini()>0)
					{
						if(Partita.getCd_num_capi_ovini()>0)
							out.println(", ");
						out.println("	<label class=\"specie2\">Caprini</label> ");
					}
%>
				</td>
            </tr>
            <%} %>
            <tr>
              <th colspan="2"><strong>Macellazione</strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Tipo</td>
              <td><%=TipiMacellazione.getSelectedValue( Partita.getMac_tipo() ) %>&nbsp;</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >	<label class="specie1">Ovini</label>  macellati</td>
              <td>
<%
				int capiMacellatiOvini = Partita.getVam_num_capi_ovini();
				for( Esito esitoOvini: (ArrayList<Esito>)Esiti)
				{ 
					if(esitoOvini.getNum_capi_ovini()>0 && esitoOvini.getId_esito()==2)
					{ 
						capiMacellatiOvini -= esitoOvini.getNum_capi_ovini();
					}
				}
				out.println(capiMacellatiOvini);
%>
			</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >	<label class="specie2">Caprini</label>  macellati</td>
              <td>
<%
				int capiMacellatiCaprini = Partita.getVam_num_capi_caprini();
				for( Esito esitoCaprini: (ArrayList<Esito>)Esiti)
				{ 
					if(esitoCaprini.getNum_capi_caprini()>0 && esitoCaprini.getId_esito()==2)
					{ 
						capiMacellatiCaprini -= esitoCaprini.getNum_capi_caprini();
					}
				}
				out.println(capiMacellatiCaprini);
%>
			</td>
            </tr>
     
          </tbody>
        </table>
        
         
			  </td>
        
        

        <%-- %>td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td--%>
    </tr></tbody></table>
		
	</td>

</tr></tbody></table>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%"><table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Visita Post Mortem</strong></th>

            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data macellazione</td>
              <td><%=toDateasString(Partita.getVpm_data()) %>&nbsp; </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
                	<%for( Esito pr: (ArrayList<Esito>)Esiti){ %>
                		<%=EsitiVpm.getSelectedValue( pr.getId_esito() ) %><%if(pr.getNum_capi_ovini()>0){ out.println(", numero capi 	<label class=\"specie1\">Ovini</label> : " + pr.getNum_capi_ovini() );} if(pr.getNum_capi_caprini()>0){  out.println(", numero capi 	<label class=\"specie2\">Caprini</label> : " + pr.getNum_capi_caprini() );}%><br/>
                	<%} %>
                	<%=(Esiti.size() > 0) ? ("") : ("&nbsp;") %>
                </td>
            </tr>
<!--            <%if( EsitiId.contains(2)){ %>-->
<!--             <tr class="containerBody">-->
<!--               <td class="formLabel">Distruzione Carcassa</td>-->
<!--               <td>	-->
<!--                   <%=Partita.isAbb_dist_carcassa() ? ("SI") : ("NO") %>-->
<!--               </td>-->
<!--            </tr>   -->
<!--            <%} %>        -->

			<tr class="containerBody">
                <td class="formLabel">Patologie Rilevate</td>
                <td>
                	<%for( PatologiaRilevata pr: (ArrayList<PatologiaRilevata>)PatologieRilevate ){ %>
                		<%=Patologie.getSelectedValue( pr.getId_patologia() ) %><br/>
                	<%} %>
                	<%=(PatologieRilevate.size() > 0) ? ("") : ("&nbsp;") %>
                </td>
            </tr> 
                
             <tr class="containerBody">
                <td class="formLabel">Causa Presunta o Accertata</td>
                <td><%=Partita.getVpm_causa_patologia() %>&nbsp; </td>
             </tr>
				<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Partita.getVpm_note() %>&nbsp;</td>

            </tr> 
             
              <tr class="containerBody">
                <td class="formLabel" rowspan="3">Veterinari addetti al controllo</td>
                <td>&nbsp;1. <%=Partita.getVpm_veterinario() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Partita.getVpm_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Partita.getVpm_veterinario_3() %></td>
            </tr> 
            <tr>
                <th colspan="2"><strong>Destinatario/Esercente</strong>                </th>
            </tr>
			
            <tr class="containerBody">
            	<td  class="formLabel">Destinatari/Esercenti</td>
		        <td>
		        
		        <% ArrayList<DestinatarioCarni> listaDestinatari = Partita.getListaDestinatariCarni(); 
		        for (DestinatarioCarni destinatario : listaDestinatari) { %>
		        
		           	<%=toHtml(destinatario.getNome()) %> (<%=(destinatario.isInRegione()) ? ("In regione") : ("Fuori regione") %>)
		        	<br/>
		        	Numero capi <label class="specie1">Ovini</label>: <%=toHtml(destinatario.getNumCapiOvini()+"") %>, <label class="specie2">Caprini</label>: <%=toHtml(destinatario.getNumCapiCaprini()+"") %>
		        	<br/>
		        	<%} %>
		        	
		        </td>
		    </tr>
		    
		    
             </table>
         
			  
			  </td>
          
    </tr>
  <%if(  EsitiId.contains(3) ||  EsitiId.contains(4) ){ %>
    <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
            <tr>
              <th colspan="2"><strong>Organi</strong></th>
            </tr>
<!--            <tr class="containerBody">-->
<!--              <td class="formLabel" >Data</td>-->
<!---->
<!--              <td><dhv:tz  dateOnly="true" timestamp="<%=Partita.getLcso_data() %>"/>&nbsp; </td>-->
<!--            </tr>-->
     <%if( OrganiListNew.size() > 0 ){ %>
     
    			<%
    				for( int key : (Set<Integer>)OrganiListNew.keySet() ){
    					Organi temp = (Organi)((ArrayList<Organi>)OrganiListNew.get(key)).get(0) ;
    			%>
    			<tr class="containerBody">
    				<td class="formLabel">Organo</td>
    				<td><%=Organi.getSelectedValue( temp.getLcso_organo() ) %>&nbsp;</td>
    			</tr>
    			<tr class="containerBody">
                		<td class="formLabel">Lesione Anatomopatologica</td>
                		<td>
                		<%for(Organi o : (ArrayList<Organi>)OrganiListNew.get(key)){ %>
                			<%=PatologieOrgani.getSelectedValue( o.getLcso_patologia() ) %>
                			<%= o.getLcso_patologia() == 5 ? o.getLcso_patologia_altro() != null && !o.getLcso_patologia_altro().equals("") ? ": " + o.getLcso_patologia_altro() : ": N.D." : "" %>
                			
                			<% String lesione = PatologieOrgani.getSelectedValue( o.getLcso_patologia() );
                			String organoNome = Organi.getSelectedValue( o.getLcso_organo() );
                			organoNome = organoNome.replaceAll(" ", "");
                			if (lesione.equalsIgnoreCase("echinococco") || lesione.equalsIgnoreCase("fasciola") || lesione.equalsIgnoreCase("dicrocoelium") ) {%>
                			<input type ="button" value ="Genera scheda segnalazione al macello" onClick = "javascript:openRichiestaPDF_Echinococco('<%=Partita.getId_macello() %>', '<%=Partita.getId() %>', '<%=organoNome%>',  'ModelloEchinococco')" />
                			<%} %>
                			
                			<br/>
                		<%} %>
                		</td>
            	</tr>
            	<%if( temp.getLcso_patologia() == 6 ){ %>
    			<tr class="containerBody">
    				<td class="formLabel">Stadio</td>
    				<td><%=Stadi.getSelectedValue( temp.getLcso_stadio() ) %>&nbsp;</td>
    			</tr>
    			<%} %>
    			
    			
    			
    			<tr class="containerBody">
    				<td class="formLabel" colspan="2">&nbsp;</td>
    			</tr>
    			
    			
    			<%} }
     			else{%>
    			<tr>
    				<td colspan="5" class="formLabel"> Nessun Organo Selezionato.</td>
    			</tr>
    			<%} %>
       
    </table>
    </td>
    </tr>
<%
	}
  	if( EsitiId.contains(4))
  	{ 
%>
    <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
           <tr>
              <th colspan="2"><strong>Libero Consumo Previo Risanamento</strong></th>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data prevista di liberalizzazione</td>
                            <td><%=toDateasString(Partita.getLcpr_data_prevista_liber()) %>&nbsp; </td>
              
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data effettiva di liberalizzazione</td>
                                          <td><%=toDateasString(Partita.getLcpr_data_effettiva_liber()) %>&nbsp; </td>
              
            </tr>
          
         </table>
         </td>
    </tr>
  <%} %>
  
 
   <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    			<tr>
    				<th colspan="4">
    					Tamponi
    			
    				</th>
    			</tr>
    			<%if( Tampone.getId()>0 ){ %>
    			<tr>
    				<th>Piano</th><th>Tipo Carcassa</th> <th>Tipo Ricerca</th><th>Metodo</th>
    			</tr>
    			
    			<tr>
    				
              		<td><%=PianiMonitoraggio.getSelectedValue(Tampone.getPiano_monitoraggio()) %></td>
             		<td><%=MatriciTamponi.getSelectedValue(Tampone.getId_tipo_carcassa()) %>&nbsp;</td>
           			<td><%
           				for ( TipoRicerca tiporicerca : Tampone.getTipo_ricerca())
           				{
           					out.println("- "+tiporicerca.getDescrizione()+ " <br>");
           				}
           			
           			%>&nbsp;</td>
           			<td><%=(Tampone.isDistruttivo() ? "DISTRUTTIVO" : "NON DISTRUTTIVO") %>&nbsp;</td>
    				
    			</tr>
    			
    			<%} else{%>
    			<tr>
    				<td colspan="3" class="formLabel"> Nessun Tampone</td>
    			</tr>
    			<%} %>
   			</table>
    		
         </td>
    </tr>
    <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    			<tr>
    				<th colspan="6">
    					Campioni
    					<%if( Campioni.size() > 0 ){ %>
	              			<br/>
	              		<%} %>
    				</th>
    			</tr>
    			<%if( Campioni.size() > 0 ){ %>
    			<tr>
    				<th>Matrice</th> <th>Tipo Analisi</th><th>Molecole/Agente Etiologico</th><th>Motivo</th><th>Esito</th><th>Data Ricezione Esito</th>
    			</tr>
    			
    			<%
    				for( int i = 0; i < Campioni.size(); i++ )
    				{
    					Campione temp = (Campione)Campioni.get( i );
    			%>
    			
    			<tr>
    				
              
             		<td><%=Matrici.getSelectedValue( temp.getMatrice() ) %>&nbsp;</td>
           			<td><%=TipiAnalisi.getSelectedValue( temp.getId_tipo_analisi() ) %>&nbsp;</td>
           			<td><%=Molecole.getSelectedValue( temp.getId_molecole()) + " " + temp.getNote()  %>&nbsp;</td>
    				<td><%=MotiviCampioni.getSelectedValue( temp.getId_motivo() ) %>&nbsp; </td>
    				<td><%=EsitiCampioni.getSelectedValue( temp.getId_esito() ) %>&nbsp;</td>
    				<td><zeroio:tz timestamp="<%=temp.getData_ricezione_esito()%>" showTimeZone="false" dateOnly="true" />&nbsp; </td>
    			</tr>
    			
    			<%} }else{%>
    			<tr>
    				<td colspan="5" class="formLabel"> Nessun Campione</td>
    			</tr>
    			<%} %>
   			</table>
 		</td>
    
    </tbody>
    </table></td>
	</tr>
	</tbody>
</table>	
</div>
	
	
    <div id="tabs-4">
	
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%"><table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>

        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
          <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >partita</td>
                <td>
                	<%=Partita.getCd_partita()%>
                </td>
            </tr>
            
          
              <%
            if(!Partita.getCd_specie().equals(""))
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
					<%=Partita.getCd_specie()%>
				</td>
            </tr>
            <%} %>
            <tr>
              <th colspan="2"><strong>Morte antecedente macellazione</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Partita.getMavam_data()) %>&nbsp; </td>
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel">Luogo di verifica</td>
                <td>
                	<%=LuoghiVerifica.getSelectedValue( Partita.getMavam_luogo() ) %>&nbsp;
                	<%=Partita.getMavam_descrizione_luogo_verifica() %>
                </td>
           	
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie1">Ovini</label> </td>
                <td>
<%
                	num_capi_ovini = "";
                	if(Partita.getMavam_num_capi_ovini()>0)
                		num_capi_ovini = Partita.getMavam_num_capi_ovini()+"";
                	out.println(num_capi_ovini);
%>
                </td>
            </tr>
               
           <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie2">Caprini</label> </td>
                <td>
<%
                	num_capi_caprini = "";
                	if(Partita.getMavam_num_capi_caprini()>0)
                		num_capi_caprini = Partita.getMavam_num_capi_caprini()+"";
                	out.println(num_capi_caprini);
%>
                </td>
            </tr>         
                
            <tr class="containerBody">
                <td class="formLabel">Causa</td>
                <td><%=Partita.getMavam_motivo() %>&nbsp;</td>

            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Impianto di termodistruzione</td>
                <td>
                	<%=Partita.getMavam_impianto_termodistruzione() %>&nbsp;
                	<%if( toHtmlValue( Partita.getMavam_impianto_termodistruzione() ).length() > 0 ){ %>
              			<br/>
              		<%} %>
                </td>

            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Destinazione della carcassa</td>
                <td>
                	
                		<%=toHtmlValue( Partita.getMvam_destinazione_carcassa() ) %>
                </td>
           </tr>
            
            
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<% if (Partita.isMavam_to_asl_origine()) { %>
            			ASL origine <br/>
            		<% } %>
            		
            		<% if (Partita.isMavam_to_proprietario_animale()) { %>
            			Proprietario animale <br/>
            		<% } %>
            		
            		<% if (Partita.isMavam_to_azienda_origine()) { %>
	            		Azienda di origine <br/>
            		<% } %>
            			
            		<% if (Partita.isMavam_to_proprietario_macello()) { %>
            			Proprietario macello <br/>
            		<% } %>
            		
            		<% if (Partita.isMavam_to_pif()) { %>
            			P.I.F. <br/>
            		<% } %>
            		
            		<% if (Partita.isMavam_to_uvac()) { %>
            			U.V.A.C. <br/>
            		<% } %>
            		
            		<% if (Partita.isMavam_to_regione()) { %>
            			Regione <br/>
            		<% } %>
            		
            		<% if (Partita.isMavam_to_altro()) { %>
            			Altro <%= Partita.getMavam_to_altro_testo() != null && !Partita.getMavam_to_altro_testo().equals("")? ": " + Partita.getMavam_to_altro_testo() : "" %><br/>
            		<% } %>
            		&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Partita.getMavam_note() %>&nbsp;</td>

            </tr>
            
          </tbody>
        </table>
          
			  
			  </td>
        
        
    </tr></tbody></table>&nbsp;</td>


</tr></tbody></table>
	</div>
    

	<div id="tabs-6" class="ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
	
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">

		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%">
         </td>

              <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>
		
		</td>

</tr></tbody></table>
	</div>


		<div id="tabs-7">
		

		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
          
          <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >partita</td>
                <td>
                	<%=Partita.getCd_partita()%>
                </td>
            </tr>
            
          
              <%
            if(!Partita.getCd_specie().equals(""))
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Partita.getCd_specie()%>
                </td>
            </tr>
            <%} %>
            <tr>

              <th colspan="2">
              	<strong>Comunicazioni Esterne</strong>
              </th>
            </tr>
            
			<tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<% if (Partita.isCasl_to_asl_origine()) { %>
            			ASL origine <br/>
            		<% } %>
            		
            		<% if (Partita.isCasl_to_proprietario_animale()) { %>
            			Proprietario animale <br/>
            		<% } %>
            		
            		<% if (Partita.isCasl_to_azienda_origine()) { %>
	            		Azienda di origine <br/>
            		<% } %>
            			
            		<% if (Partita.isCasl_to_proprietario_macello()) { %>
            			Proprietario macello <br/>
            		<% } %>
            		
            		<% if (Partita.isCasl_to_pif()) { %>
            			P.I.F. <br/>
            		<% } %>
            		
            		<% if (Partita.isCasl_to_uvac()) { %>
            			U.V.A.C. <br/>
            		<% } %>
            		
            		<% if (Partita.isCasl_to_regione()) { %>
            			Regione <br/>
            		<% } %>
            		
            		<% if (Partita.isCasl_to_altro()) { %>
            			Altro <%= Partita.getCasl_to_altro_testo() != null && !Partita.getCasl_to_altro_testo().equals("")? ": " + Partita.getCasl_to_altro_testo() : "" %><br/>
            		<% } %>
            		&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Partita.getCasl_data()) %>&nbsp; </td>
            </tr>
            
<%--        <tr class="containerBody">
                <td class="formLabel">Tipo di non conformità</td>
                <td><%=MotiviASL.getSelectedValue( Partita.getCasl_motivo() ) %>&nbsp;</td>
    			
            </tr>
--%>
            
			<tr class="containerBody">
                <td class="formLabel">Tipo di non conformità</td>
                <td>
                	<%for( Casl_Non_Conformita_Rilevata nc: (ArrayList<Casl_Non_Conformita_Rilevata>)casl_NCRilevate ){ %>
                		<%=MotiviASL.getSelectedValue( nc.getId_casl_non_conformita() ) %><br/>
                	<%} %>
                	<%=(casl_NCRilevate.size() > 0) ? ("") : ("&nbsp;") %>
                </td>
            </tr> 
            
            <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie1">Ovini</label> </td>
                <td>
<%
                	num_capi_ovini = "";
                	if(Partita.getCasl_num_capi_ovini()>0)
                		num_capi_ovini = Partita.getCasl_num_capi_ovini()+"";
                	out.println(num_capi_ovini);
%>
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >numero capi 	<label class="specie2">Caprini</label> </td>
                <td>
<%
                	num_capi_caprini = "";
                	if(Partita.getCasl_num_capi_caprini()>0)
                		num_capi_caprini = Partita.getCasl_num_capi_caprini()+"";
                	out.println(num_capi_caprini);
%>
                </td>
            </tr>
            
                
            <tr class="containerBody">
                <td class="formLabel">Descrizione non Conformità</td>
                <td><%=Partita.getCasl_info_richiesta() %>&nbsp;</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Provvedimenti Adottati</td>
                <td>
                	<%for( ProvvedimentiCASL pr: (ArrayList<ProvvedimentiCASL>)casl_Provvedimenti_effettuati ){ %>
                		<%=look_ProvvedimentiCASL.getSelectedValue( pr.getId_provvedimento()) %><br/>
                	<%} %>
                	<%=(casl_Provvedimenti_effettuati.size() > 0) ? ("") : ("&nbsp;") %>
                </td>
            </tr> 
            
			<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Partita.getCasl_note_prevvedimento() %>&nbsp;</td>
            </tr>
            
          </tbody>
        </table>

			  </td>
        
        

        <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>


		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Ricezione Comunicazioni Esterne</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data</td>
                <td><%=toDateasString(Partita.getRca_data()) %>&nbsp; </td>
             </tr>
             
             <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Partita.getRca_note() %>&nbsp; </td>
             </tr>
                 
          </tbody>
        </table>
        
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2">
              	<strong>Blocco Animale</strong>
              </th>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Partita.getSeqa_data()) %>&nbsp; </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Sbloccato il</td>
              <td><%=toDateasString(Partita.getSeqa_data_sblocco()) %>&nbsp; </td>
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel">Destinazione allo sblocco</td>
                                
                <td><%=ProvvedimentiVAM.getSelectedValue( Partita.getSeqa_destinazione_allo_sblocco() ) %> &nbsp;</td>
    			
            </tr>
          </tbody>
        </table>
        
         </td>
        
        

        <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>
	</div>


<%--
<div id="tabs-9">

<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">
		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>

        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Abbattimento</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              	<dhv:tz dateOnly="true" timestamp="<%=Partita.getAbb_data() %>"/>&nbsp;
             </td>

            </tr>
                     
                 <tr class="containerBody">
                <td class="formLabel">Motivo</td>
                <td><%=Partita.getAbb_motivo() %>&nbsp;</td>
                </tr>
                
<!--               <tr class="containerBody">-->
<!--               <td class="formLabel">distruzione carcassa</td>-->
<!--               <td>	-->
<!--                   <%=Partita.isAbb_dist_carcassa() ? ("SI") : ("NO") %>-->
<!--               </td>-->
<!--            </tr>-->
            <tr class="containerBody">
                <td class="formLabel" rowspan="4">Veterinari addetti al controllo</td>
                <td>&nbsp;1. <%=Partita.getAbb_veterinario() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Partita.getAbb_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Partita.getAbb_veterinario_3() %></td>
            </tr> 
          </tbody>
        </table>

			  </td>

        
        

        <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>
		
</td>

</tr></tbody></table>
	</div --%>

<div id="tabs-14">

	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody>
    
    </tr></tbody></table>
		
	</td>

</tr></tbody></table>

</div>
	
		
</div>

</div><!-- End demo -->

</dhv:container>

<% 
	    if (db != null) 
	      sqlDriver.free(db);
	    db = null;
%>  

<script>lanciaModifiche()</script>