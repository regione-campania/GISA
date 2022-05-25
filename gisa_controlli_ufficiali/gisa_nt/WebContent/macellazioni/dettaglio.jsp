<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page import="org.aspcfs.modules.macellazioni.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioni.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioni.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioni.base.Campione"%><%@page import="org.aspcfs.modules.macellazioni.base.Organi"%>
<%@page import="org.aspcfs.modules.macellazioni.base.PatologiaRilevata"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="org.aspcfs.modules.macellazioni.base.TipoRicerca"%><jsp:useBean id="Tampone"			class="org.aspcfs.modules.macellazioni.base.Tampone"	scope="request" />

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" 	scope="request" />
<jsp:useBean id="Speditore" class="org.aspcfs.modules.speditori.base.Organization" 	scope="request" />
<jsp:useBean id="SpeditoreAddress" class="org.aspcfs.modules.speditori.base.OrganizationAddress" 	scope="request" />
<jsp:useBean id="Capo"				class="org.aspcfs.modules.macellazioni.base.Capo"	scope="request" />
<jsp:useBean id="NCVAM"				class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="OrganiList" 		class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="OrganiListNew" 	class="java.util.TreeMap"							scope="request" />
<jsp:useBean id="PatologieRilevate"	class="java.util.ArrayList"							scope="request" />
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
<jsp:useBean id="sessionePregressa"	class="java.lang.String" scope="request" />

<%@ include file="../initPage.jsp"%>

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

<script language="javascript">
function openPopupErrataCorrigeArt17(idCapo, orgId, tipo){
        var res;
        var result;
        
       // if (document.all) {
        	  window.open('MacellazioniDocumenti.do?command=ViewModuleErrataCorrigeArt17&orgId='+orgId+'&idCapo='+idCapo+'&tipoMacello='+tipo,'popupSelect',
              'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
    		
    		
    	/*	} else {
    		
    			res = window.showModalDialog('MacellazioniDocumenti.do?command=ViewModuleErrataCorrigeArt17&orgId='+orgId+'&idCapo='+idCapo+'&tipoMacello='+tipo,'popupSelect',
    			'dialogWidth:1280px;dialogHeight:800px;center: 1; scroll: 0; help: 1; status: 0');
    		
    		}
        */
               
        
} 

</script>

<script language="javascript">
function openPopupListaErrataCorrigeArt17(idCapo, orgId, tipo){
        var res;
        var result;
        
      //  if (document.all) {
        	  window.open('MacellazioniDocumenti.do?command=ViewListaModuleErrataCorrigeArt17&orgId='+orgId+'&idCapo='+idCapo+'&tipoMacello='+tipo,'popupSelect',
              'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
    		
    		
    	/*	} else {
    		
    			res = window.showModalDialog('MacellazioniDocumenti.do?command=ViewListaModuleErrataCorrigeArt17&orgId='+orgId+'&idCapo='+idCapo+'&tipoMacello='+tipo,'popupSelect',
    			'dialogWidth:1280px;dialogHeight:800px;center: 1; scroll: 0; help: 1; status: 0');
    		
    		} */
        
               
        
} 

function openPopupCancellazioneCapo(idCapo){
    var res;
    var result;
   	  window.open('Macellazioni.do?command=PrepareCancellazioneCapo&idCapo='+idCapo,'popupSelect',
          'height=300px,width=380px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
} 

</script>

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
			<a href="Macellazioni.do?command=List&orgId=<%=OrgDetails.getOrgId() %>">Macellazioni</a> > Dettaglio
		</td>
	</tr>
</table>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
String param2 = "capoId=" + Capo.getId();
%>
<dhv:container 
	name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati") : ("stabilimenti") %>"
	selected="macellazioni" 
	object="OrgDetails" 
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">

<br/>

<%if(ApplicationProperties.getProperty("visibilita_link_macelli").equals("si") ){ %>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="Macellazioni.do?command=PrintInvioCampioniTBC&file=invio_campioni_TBC.xml&<%=param2 %>">Invio campioni TBC</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="Macellazioni.do?command=PrintModelloLBE&file=modello_LBE.xml&<%=param2 %>">Modello LBE</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="Macellazioni.do?command=PrintModello1033TBC&file=modello_10_33_TBC.xml&<%=param2 %>">Modello 10 33 TBC</a>
</dhv:permission>
<%} %>

<br/><br/>

<%
if(Capo.getVpm_data()!=null)
{
%>
	SESSIONE DI APPARTENENZA: <%=toDateasString(Capo.getVpm_data())+"-"+Capo.getCd_seduta_macellazione() %> <%=(sessionePregressa!=null) ? sessionePregressa : "" %><br/><br/>
<%
}
else
{
%>
	SESSIONE DI APPARTENENZA: <%=toDateasString(Capo.getMavam_data())+"-"+Capo.getCd_seduta_macellazione() %> <%=(sessionePregressa!=null) ? sessionePregressa : "" %><br/><br/>
<%
}
if(!Capo.isArticolo17() && ! Capo.isModello10()){ %>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-edit">
	<input type="button" value="Modifica" onclick="location.href='Macellazioni.do?command=ModificaCapo&cdSedutaMacellazione=<%=Capo.getCd_seduta_macellazione()%>&vpmData=<%=(Capo.getVpm_data()==null ? ("") : (toDateasString(Capo.getVpm_data())))%>&mavamData=<%=(Capo.getMavam_data()==null ? ("") : (toDateasString(Capo.getMavam_data())))%>&id=<%=Capo.getId() %>&orgId=<%=OrgDetails.getOrgId() %>'" />
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-delete">
	<input type="button" value="Elimina" onclick="if(confirm('Sicuro di voler eliminare la registrazione?')){location.href='Macellazioni.do?command=Delete&id=<%=Capo.getId() %>&orgId=<%=OrgDetails.getOrgId() %>'}" />
</dhv:permission>
<%} else{ 
if (Capo.getStato_macellazione().equals("Incompleto: Presenti campioni senza esito."))
{
%>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-edit">
	<input type="button" value="Inserisci Esito Campioni" onclick="location.href='Macellazioni.do?command=ModificaCapo&id=<%=Capo.getId() %>&orgId=<%=OrgDetails.getOrgId() %>'" />
</dhv:permission>
<%	
}else
{
%>
Capo non modificabile: per esso è stato già stampato l'art. 17 o Modello 10
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-edit">

 <% if (Capo.getErrata_corrige_generati()<10){ %>
<input type="button" value="Errata corrige art.17" onclick="openPopupErrataCorrigeArt17('<%=Capo.getId() %>', '<%=OrgDetails.getOrgId() %>', '<%=request.getAttribute("tipo") %>')" />
<%} else { %>
; Raggiunto numero massimo moduli Errata Corrige generabili per questo capo.
<%} %>
<a href="javascript:openPopupListaErrataCorrigeArt17('<%=Capo.getId() %>', '<%=OrgDetails.getOrgId() %>', '<%=request.getAttribute("tipo") %>')"> Moduli EC generati</a> 
<br/><br/>
</dhv:permission>
<%}} %>

<dhv:permission name="macellazioni-gestione-cancellazioni-capipartite-delete">
<center>
<table border="1px solid black">
<tr><td>
<input type="button" style="background:red" value="ELIMINA" onClick="openPopupCancellazioneCapo('<%=Capo.getId()%>')"/>
</td></tr>
</table>
</center>
</dhv:permission>

<div class="demo">

<%
	boolean visualizzaSezioneBSE= false;
	visualizzaSezioneBSE= (Capo.isBovino() || Capo.isBufalino() ) && (Capo.getCd_bse()!=-1);
	
%>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">Controllo Documentale</a></li>
		<% if (false) { %> <li><a href="#tabs-14">Test BSE</a></li> <% } %> 
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
            	<%=Capo.getCd_codice_azienda_provenienza() %>
            	<div id="rowInfoAzienda" align="right" style="padding-right: 20px;"><%=toHtml2( Capo.getCd_info_azienda_provenienza()) %></div>	
            	</td>
            		
            </tr>
            
 <%--           
            <tr class="containerBody">
               <td class="formLabel">Stato</td>
               <td>
               		<%=Nazioni.getSelectedValue( Capo.getCd_provenienza_stato() ) %>

               </td>
            </tr>
            
			<tr class="containerBody">
                <td class="formLabel" >A.S.L.<br></td>
                <td>
                	<%=ASL.getSelectedValue( Capo.getCd_asl() ) %>&nbsp;
				</td>
            </tr>
      
<%-- 		PER IL MOMENTO DISABILITATO 7 OTT 2010 
            <tr class="containerBody">
               <td class="formLabel">Comunitario</td>
               <td>	
                   <%=Capo.isCd_prov_stato_comunitario() ? ("SI") : ("NO") %>
               </td>
            </tr>
           
            <tr class="containerBody">
            	<td class="formLabel">Regione</td>
            	<td>
            		<%=Regioni.getSelectedValue( Capo.getCd_provenienza_regione() ) %>&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody">
            	<td class="formLabel">Comune</td>
            	<td>
            		<%=toHtmlValue( Capo.getCd_provenienza_comune() ) %>&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Speditore</td>
                <td>
                	<%=toHtmlValue( Capo.getCd_speditore() ) %>&nbsp;
				</td>
            </tr>
            <tr class="containerBody" >
                <td class="formLabel" >Codice Azienda Speditore</td>
                <td>
                	<%=toHtmlValue( Capo.getCd_codice_speditore() ) %>&nbsp;
				</td>
            </tr>
--%>            
            <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >Matricola</td>
                <td>
                	<%=Capo.getCd_matricola()%>
                </td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Codice Azienda di Nascita</td>
                <td>
                	<%=Capo.getCd_codice_azienda() %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Sesso</td>
              <td>
                 <%=Capo.isCd_maschio() ? ("M") : ("F") %>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Categoria</td>

              	<td>
              		<%=CategorieBovine.getSelectedValue( Capo.getCd_categoria_bovina() ) + CategorieBufaline.getSelectedValue( Capo.getCd_categoria_bufalina() ) %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Razza</td>

              	<td>
              		<%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;
              		<%if(Capo.getCd_id_razza() == 999){ %>
              		<%= Capo.getCd_razza_altro() != null && !Capo.getCd_razza_altro().equals("") ? ": " + Capo.getCd_razza_altro() : ": N.D." %>
					<%} %>
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data di nascita</td>
              <td>
              		<%=toDateasString(Capo.getCd_data_nascita())%>&nbsp;
              </td>
            </tr>	
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Categoria di Rischio</td>
                <td>
                	<%=Capo.getCd_categoria_rischio() %>
				</td> 
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Vincolo sanitario</td>

              <td valign="middle">
              	<%=Capo.isCd_vincolo_sanitario() ? "SI" : "NO" %>
                <%
                	String motivo = Capo.getCd_vincolo_sanitario_motivo();
                	if(motivo != null && !motivo.trim().equals("")){
                %>
                Motivo: <%= motivo %>
                <%		
                	}
                %>
              </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Mod. 4</td>
                <td>
                	<%=Capo.getCd_mod4()!=null ? Capo.getCd_mod4() : "" %>&nbsp;
				</td>
            </tr>
            
			<tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Data Mod. 4</td>
              <td>
              		<%=toDateasString(Capo.getCd_data_mod4())%>&nbsp;
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Macell. differita piani risanamento</td>
              <td>
              	<%=PianiRisanamento.getSelectedValue( Capo.getCd_macellazione_differita() ) %>&nbsp;
              	<%if( Capo.getCd_macellazione_differita() > 0 && ( Capo.getVpm_data() != null || Capo.getMavam_data() != null ) ){ %>
              		<br/>
              	<%} %>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Test BSE</td>
              <td>
              	<%=( Capo.getCd_bse() >= 0 ) ? ( BseList.getSelectedValue( Capo.getCd_bse() ) ) : ( "NO" ) %>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel">Disponibili informazioni catena alimentare </td>
              <td><%=Capo.isCd_info_catena_alimentare() ? "SI" : "NO" %></td>
            </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Data arrivo al macello</td>
                <td><%=toDateasString(Capo.getCd_data_arrivo_macello())%> </td>
            </tr>
            
             <tr class="containerBody">
              <td class="formLabel">Data di arrivo dichiarata dal Gestore </td>
              <td><%=Capo.isCd_data_arrivo_macello_flag_dichiarata() ? "SI" : "NO" %></td>
            </tr>
            
            <tr>
                <th colspan="2"><strong>Identificazione Mezzo di Trasporto</strong></th>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Tipo</td>
                <td>
                	<%=Capo.getCd_tipo_mezzo_trasporto() != null ? Capo.getCd_tipo_mezzo_trasporto() : "" %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Targa</td>
                <td>
                	<%=Capo.getCd_targa_mezzo_trasporto() != null ? Capo.getCd_targa_mezzo_trasporto() : ""%>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
              	<td class="formLabel">Trasporto superiore<br>a 8 ore</td>
              	<td><%=Capo.isCd_trasporto_superiore8ore() ? "SI" : "NO" %></td>
            </tr>
            
<!--            -->

	<% if (visualizzaSezioneBSE) { %>
		<tbody>
            <tr>

              <th colspan="2"><strong>Test BSE</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data Prelievo</td>
                <td><%=toDateasString(Capo.getBse_data_prelievo())%> </td>
            </tr>
                
             <tr class="containerBody">
                <td class="formLabel">Data Ricezione Esito</td>
                                                          <td><%=toDateasString(Capo.getBse_data_ricezione_esito()) %>&nbsp; </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td><%=( Capo.getBse_esito().equals("-1") ) ? ( "In attesa di esito" ) : ( Capo.getBse_esito() ) %></td>
             </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Capo.getBse_note() %></td>
            </tr>
        </tbody>
	<% } %>			


<!--            -->
            
             <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo </strong>                </th>
            </tr>
              <tr class="containerBody">
                <td class="formLabel" rowspan="3">&nbsp; </td>
                <td>&nbsp;1. <%=Capo.getCd_veterinario_1() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Capo.getCd_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Capo.getCd_veterinario_3() %></td>
            </tr>
            
            <tr>
                <th colspan="2"><strong>Dettagli addizionali</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >Note</td>
                <td>
                	<%=Capo.getCd_note() != null ? Capo.getCd_note() : ""%>&nbsp;
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
                <td class="formLabel" >Matricola</td>
                <td>
                	<%=Capo.getCd_matricola()%>
                </td>
            </tr>
            
          
              <%
            if(Capo.getCd_specie()>0)
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <tr class="containerBody">
              <td class="formLabel" >Sesso</td>
              <td>
                 <%=Capo.isCd_maschio() ? ("M") : ("F") %>
              </td>
            </tr>
                <%
            if(Capo.getCd_categoria_bovina()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Categoria</td>

              	<td>
              		<%=CategorieBovine.getSelectedValue( Capo.getCd_categoria_bovina() ) + CategorieBufaline.getSelectedValue( Capo.getCd_categoria_bufalina() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <%
            if(Capo.getCd_id_razza()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Razza</td>

              	<td>
              		<%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;
              		<%if(Capo.getCd_id_razza() == 999){ %>
              		<%= Capo.getCd_razza_altro() != null && !Capo.getCd_razza_altro().equals("") ? ": " + Capo.getCd_razza_altro() : ": N.D." %>
					<%} %>
				</td>
            </tr>
            <%} %>
          
            <tr>
              <th colspan="2"><strong>Visita Ante Mortem </strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Capo.getVam_data()) %>&nbsp; </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td><%=Capo.getVam_esito()%></td>
            </tr>

            <tr class="containerBody">
                <td class="formLabel">Provvedimento adottato</td>
           		 <td>           		
           		 <%=ProvvedimentiVAM.getSelectedValue( Capo.getVam_provvedimenti() ) %> &nbsp;
           		 </td>
    				
            </tr>
            
            
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<% if (Capo.isVam_to_asl_origine()) { %>
            			ASL origine <br/>
            		<% } %>
            		
            		<% if (Capo.isVam_to_proprietario_animale()) { %>
            			Proprietario animale <br/>
            		<% } %>
            		
            		<% if (Capo.isVam_to_azienda_origine()) { %>
	            		Azienda di origine <br/>
            		<% } %>
            			
            		<% if (Capo.isVam_to_proprietario_macello()) { %>
            			Proprietario macello <br/>
            		<% } %>
            		
            		<% if (Capo.isVam_to_pif()) { %>
            			P.I.F. <br/>
            		<% } %>
            		
            		<% if (Capo.isVam_to_uvac()) { %>
            			U.V.A.C. <br/>
            		<% } %>
            		
            		<% if (Capo.isVam_to_regione()) { %>
            			Regione <br/>
            		<% } %>
            		
            		<% if (Capo.isVam_to_altro()) { %>
            			Altro <%= Capo.getVam_to_altro_testo() != null && !Capo.getVam_to_altro_testo().equals("")? ": " + Capo.getVam_to_altro_testo() : "" %><br/>
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






<table width="100%" border="0" cellpadding="2" cellspacing="2" <%if(Capo.getVam_provvedimenti()!=4){ out.println("style=\"display: none\""); } %> >
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
              	<%=toDateasString(Capo.getAbb_data()) %>&nbsp;
             </td>

            </tr>
                     
                 <tr class="containerBody">
                <td class="formLabel">Motivo</td>
                <td><%=Capo.getAbb_motivo() %>&nbsp;</td>
                </tr>
                
<!--               <tr class="containerBody">-->
<!--               <td class="formLabel">distruzione carcassa</td>-->
<!--               <td>	-->
<!--                   <%=Capo.isAbb_dist_carcassa() ? ("SI") : ("NO") %>-->
<!--               </td>-->
<!--            </tr>-->
            <tr class="containerBody">
                <td class="formLabel" rowspan="4">Veterinari addetti al controllo</td>
                <td>&nbsp;1. <%=Capo.getAbb_veterinario() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Capo.getAbb_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Capo.getAbb_veterinario_3() %></td>
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
                <td class="formLabel" >Matricola</td>
                <td>
                	<%=Capo.getCd_matricola()%>
                </td>
            </tr>
            
          
              <%
            if(Capo.getCd_specie()>0)
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <tr class="containerBody">
              <td class="formLabel" >Sesso</td>
              <td>
                 <%=Capo.isCd_maschio() ? ("M") : ("F") %>
              </td>
            </tr>
                <%
            if(Capo.getCd_categoria_bovina()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Categoria</td>

              	<td>
              		<%=CategorieBovine.getSelectedValue( Capo.getCd_categoria_bovina() ) + CategorieBufaline.getSelectedValue( Capo.getCd_categoria_bufalina() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <%
            if(Capo.getCd_id_razza()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Razza</td>

              	<td>
              		<%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;
              		<%if(Capo.getCd_id_razza() == 999){ %>
              		<%= Capo.getCd_razza_altro() != null && !Capo.getCd_razza_altro().equals("") ? ": " + Capo.getCd_razza_altro() : ": N.D." %>
					<%} %>
				</td>
            </tr>
            <%} %>
            <tr>
              <th colspan="2"><strong>Macellazione</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Progressivo</td>
              <td >
              	<%--  <%= (Capo.getMac_progressivo()==-1 ? (" "):(Capo.getMac_progressivo())) %>&nbsp; --%>
              	<%= Capo.getProgressivo_macellazione() > 0 ? Capo.getProgressivo_macellazione() : ""  %>
              </td>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Tipo</td>
              <td><%=TipiMacellazione.getSelectedValue( Capo.getMac_tipo() ) %>&nbsp;</td>
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
              <td>
<%
				String stato	= Capo.getStato_macellazione();
				if(stato==null || !stato.equals("Incompleto: Inseriti solo i dati sul controllo documentale"))
	            	out.println(toDateasString(Capo.getVpm_data()));
%> &nbsp; 
			</td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
                	<%=EsitiVpm.getSelectedValue( Capo.getVpm_esito() ) %>&nbsp;
   					<%if( Capo.getVpm_esito() == 2 || Capo.getVpm_esito() == 5 ){ %>
              			<br/>
              		<%} %>
                </td>

            </tr>
<!--            <%if(Capo.getVpm_esito()==2){ %>-->
<!--             <tr class="containerBody">-->
<!--               <td class="formLabel">Distruzione Carcassa</td>-->
<!--               <td>	-->
<!--                   <%=Capo.isAbb_dist_carcassa() ? ("SI") : ("NO") %>-->
<!--               </td>-->
<!--            </tr>   -->
<!--            <%} %>        -->

			<tr class="containerBody">
              <td class="formLabel" >Data Ricezione Esito</td>
              <td><%=toDateasString(Capo.getVpm_data_esito()) %>&nbsp; </td>
            </tr>

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
                <td><%=Capo.getVpm_causa_patologia() != null ? Capo.getVpm_causa_patologia() : "" %>&nbsp; </td>
             </tr>
				<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Capo.getVpm_note() != null ? Capo.getVpm_note() : "" %>&nbsp;</td>

            </tr> 
             
              <tr class="containerBody">
                <td class="formLabel" rowspan="3">Veterinari addetti al controllo</td>
                <td>&nbsp;1. <%=Capo.getVpm_veterinario() !=null ? Capo.getVpm_veterinario() : "" %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Capo.getVpm_veterinario_2() !=null ? Capo.getVpm_veterinario_2() : "" %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Capo.getVpm_veterinario_3() !=null ? Capo.getVpm_veterinario_3()  : "" %></td>
            </tr> 
            <tr>
                <th colspan="2"><strong>Destinatario delle Carni</strong>                </th>
            </tr>
			
            <tr class="containerBody">
            	<td  class="formLabel">Destinatari</td>
		        <td>
		        	<%if( Capo.getDestinatario_1_id() != -1 ){ %>
		        	<%=toHtml(Capo.getDestinatario_1_nome()) %> (<%=(Capo.isDestinatario_1_in_regione()) ? ("In regione") : ("Fuori regione") %>)
		        	<br/>
		        	<%}
		        	if( Capo.getDestinatario_2_id() != -1 ){
		        	%>
		        	<%=toHtml(Capo.getDestinatario_2_nome()) %> (<%=(Capo.isDestinatario_2_in_regione()) ? ("In regione") : ("Fuori regione") %>)
		        	<br/>
		        	<%} %>
		        	<%if( Capo.getDestinatario_3_id() != -1 ){ %>
		        	<%=toHtml(Capo.getDestinatario_3_nome()) %> (<%=(Capo.isDestinatario_3_in_regione()) ? ("In regione") : ("Fuori regione") %>)
		        	<br/>
		        	<%}
		        	if( Capo.getDestinatario_4_id() != -1 ){
		        	%>
		        	<%=toHtml(Capo.getDestinatario_4_nome()) %> (<%=(Capo.isDestinatario_4_in_regione()) ? ("In regione") : ("Fuori regione") %>)
		        	<%} %>
		        	&nbsp;
		        	
		        </td>
		    </tr>
		    
		    
             </table>
         
			  
			  </td>
          
    </tr>
  <%if( Capo.getVpm_esito() == 3 || Capo.getVpm_esito() == 4 ){ %>
    <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
            <tr>
              <th colspan="2"><strong>Organi</strong></th>
            </tr>
<!--            <tr class="containerBody">-->
<!--              <td class="formLabel" >Data</td>-->
<!---->
<!--              <td><dhv:tz  dateOnly="true" timestamp="<%=Capo.getLcso_data() %>"/>&nbsp; </td>-->
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
    <%}
  if(Capo.getVpm_esito() == 4){ %>
    <tr>
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
           <tr>
              <th colspan="2"><strong>Libero Consumo Previo Risanamento</strong></th>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data prevista di liberalizzazione</td>
                            <td><%=toDateasString(Capo.getLcpr_data_prevista_liber()) %>&nbsp; </td>
              
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data effettiva di liberalizzazione</td>
                                          <td><%=toDateasString(Capo.getLcpr_data_effettiva_liber()) %>&nbsp; </td>
              
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
    				<td><%=toDateasString(temp.getData_ricezione_esito())%>&nbsp; </td>
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
                <td class="formLabel" >Matricola</td>
                <td>
                	<%=Capo.getCd_matricola()%>
                </td>
            </tr>
            
          
              <%
            if(Capo.getCd_specie()>0)
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <tr class="containerBody">
              <td class="formLabel" >Sesso</td>
              <td>
                 <%=Capo.isCd_maschio() ? ("M") : ("F") %>
              </td>
            </tr>
                <%
            if(Capo.getCd_categoria_bovina()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Categoria</td>

              	<td>
              		<%=CategorieBovine.getSelectedValue( Capo.getCd_categoria_bovina() ) + CategorieBufaline.getSelectedValue( Capo.getCd_categoria_bufalina() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <%
            if(Capo.getCd_id_razza()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Razza</td>

              	<td>
              		<%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;
              		<%if(Capo.getCd_id_razza() == 999){ %>
              		<%= Capo.getCd_razza_altro() != null && !Capo.getCd_razza_altro().equals("") ? ": " + Capo.getCd_razza_altro() : ": N.D." %>
					<%} %>
				</td>
            </tr>
            <%} %>
            <tr>
              <th colspan="2"><strong>Morte antecedente macellazione</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Capo.getMavam_data())%>&nbsp; </td>
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel">Luogo di verifica</td>
                <td>
                	<%=LuoghiVerifica.getSelectedValue( Capo.getMavam_luogo() ) %>&nbsp;
                	<%=Capo.getMavam_descrizione_luogo_verifica() != null ? Capo.getMavam_descrizione_luogo_verifica() : "" %>
                </td>
           	
            </tr>
                    
                
            <tr class="containerBody">
                <td class="formLabel">Causa</td>
                <td><%=Capo.getMavam_motivo() != null ? Capo.getMavam_motivo() : "" %>&nbsp;</td>

            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Impianto di termodistruzione</td>
                <td>
                	<%=Capo.getMavam_impianto_termodistruzione() != null ? Capo.getMavam_impianto_termodistruzione() : ""  %>&nbsp;
                	<%if( toHtmlValue( Capo.getMavam_impianto_termodistruzione() ).length() > 0 ){ %>
              			<br/>
              		<%} %>
                </td>

            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Destinazione della carcassa</td>
                <td>
                	
                		<%=toHtmlValue( Capo.getMvam_destinazione_carcassa() ) %>
                </td>
           </tr>
            
            
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<% if (Capo.isMavam_to_asl_origine()) { %>
            			ASL origine <br/>
            		<% } %>
            		
            		<% if (Capo.isMavam_to_proprietario_animale()) { %>
            			Proprietario animale <br/>
            		<% } %>
            		
            		<% if (Capo.isMavam_to_azienda_origine()) { %>
	            		Azienda di origine <br/>
            		<% } %>
            			
            		<% if (Capo.isMavam_to_proprietario_macello()) { %>
            			Proprietario macello <br/>
            		<% } %>
            		
            		<% if (Capo.isMavam_to_pif()) { %>
            			P.I.F. <br/>
            		<% } %>
            		
            		<% if (Capo.isMavam_to_uvac()) { %>
            			U.V.A.C. <br/>
            		<% } %>
            		
            		<% if (Capo.isMavam_to_regione()) { %>
            			Regione <br/>
            		<% } %>
            		
            		<% if (Capo.isMavam_to_altro()) { %>
            			Altro <%= Capo.getMavam_to_altro_testo() != null && !Capo.getMavam_to_altro_testo().equals("")? ": " + Capo.getMavam_to_altro_testo() : "" %><br/>
            		<% } %>
            		&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Capo.getMavam_note() != null ? Capo.getMavam_note() : "" %>&nbsp;</td>

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
                <td class="formLabel" >Matricola</td>
                <td>
                	<%=Capo.getCd_matricola()%>
                </td>
            </tr>
            
          
              <%
            if(Capo.getCd_specie()>0)
            {
            %>
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <tr class="containerBody">
              <td class="formLabel" >Sesso</td>
              <td>
                 <%=Capo.isCd_maschio() ? ("M") : ("F") %>
              </td>
            </tr>
                <%
            if(Capo.getCd_categoria_bovina()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Categoria</td>

              	<td>
              		<%=CategorieBovine.getSelectedValue( Capo.getCd_categoria_bovina() ) + CategorieBufaline.getSelectedValue( Capo.getCd_categoria_bufalina() ) %>&nbsp;
				</td>
            </tr>
            <%} %>
            <%
            if(Capo.getCd_id_razza()>0)
            {
            %>
            <tr class="containerBody">
              <td class="formLabel" >Razza</td>

              	<td>
              		<%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;
              		<%if(Capo.getCd_id_razza() == 999){ %>
              		<%= Capo.getCd_razza_altro() != null && !Capo.getCd_razza_altro().equals("") ? ": " + Capo.getCd_razza_altro() : ": N.D." %>
					<%} %>
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
            		<% if (Capo.isCasl_to_asl_origine()) { %>
            			ASL origine <br/>
            		<% } %>
            		
            		<% if (Capo.isCasl_to_proprietario_animale()) { %>
            			Proprietario animale <br/>
            		<% } %>
            		
            		<% if (Capo.isCasl_to_azienda_origine()) { %>
	            		Azienda di origine <br/>
            		<% } %>
            			
            		<% if (Capo.isCasl_to_proprietario_macello()) { %>
            			Proprietario macello <br/>
            		<% } %>
            		
            		<% if (Capo.isCasl_to_pif()) { %>
            			P.I.F. <br/>
            		<% } %>
            		
            		<% if (Capo.isCasl_to_uvac()) { %>
            			U.V.A.C. <br/>
            		<% } %>
            		
            		<% if (Capo.isCasl_to_regione()) { %>
            			Regione <br/>
            		<% } %>
            		
            		<% if (Capo.isCasl_to_altro()) { %>
            			Altro <%= Capo.getCasl_to_altro_testo() != null && !Capo.getCasl_to_altro_testo().equals("")? ": " + Capo.getCasl_to_altro_testo() : "" %><br/>
            		<% } %>
            		&nbsp;
            	</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td><%=toDateasString(Capo.getCasl_data())%>&nbsp; </td>
            </tr>
            
<%--        <tr class="containerBody">
                <td class="formLabel">Tipo di non conformità</td>
                <td><%=MotiviASL.getSelectedValue( Capo.getCasl_motivo() ) %>&nbsp;</td>
    			
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
                <td class="formLabel">Descrizione non Conformità</td>
                <td><%=Capo.getCasl_info_richiesta() != null ? Capo.getCasl_info_richiesta() : "" %>&nbsp;</td>
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
                <td><%=Capo.getCasl_note_prevvedimento() !=null ? Capo.getCasl_note_prevvedimento() : ""%>&nbsp;</td>
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
                <td><%=toDateasString(Capo.getRca_data())%>&nbsp; </td>
             </tr>
             
             <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Capo.getRca_note() != null ? Capo.getRca_note() : "" %>&nbsp; </td>
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
              <td><%=toDateasString(Capo.getSeqa_data())%>&nbsp; </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Sbloccato il</td>
              <td><%=toDateasString(Capo.getSeqa_data_sblocco())%>&nbsp; </td>
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel">Destinazione allo sblocco</td>
                                
                <td><%=ProvvedimentiVAM.getSelectedValue( Capo.getSeqa_destinazione_allo_sblocco() ) %> &nbsp;</td>
    			
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
              	<dhv:tz dateOnly="true" timestamp="<%=Capo.getAbb_data() %>"/>&nbsp;
             </td>

            </tr>
                     
                 <tr class="containerBody">
                <td class="formLabel">Motivo</td>
                <td><%=Capo.getAbb_motivo() %>&nbsp;</td>
                </tr>
                
<!--               <tr class="containerBody">-->
<!--               <td class="formLabel">distruzione carcassa</td>-->
<!--               <td>	-->
<!--                   <%=Capo.isAbb_dist_carcassa() ? ("SI") : ("NO") %>-->
<!--               </td>-->
<!--            </tr>-->
            <tr class="containerBody">
                <td class="formLabel" rowspan="4">Veterinari addetti al controllo</td>
                <td>&nbsp;1. <%=Capo.getAbb_veterinario() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Capo.getAbb_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Capo.getAbb_veterinario_3() %></td>
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
    
    <% if (false) { %>
    <tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>

              <th colspan="2"><strong>Test BSE</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data Prelievo</td>
                <td><%=toDateasString(Capo.getBse_data_prelievo())%> </td>
            </tr>
                
             <tr class="containerBody">
                <td class="formLabel">Data Ricezione Esito</td>
                <td><%=toDateasString(Capo.getBse_data_ricezione_esito())%></td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td><%=Capo.getBse_esito() %></td>
             </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=Capo.getBse_note() %></td>
            </tr>

          </tbody>
        </table>
         
	</td>
    <% } %>
        

    </tr></tbody></table>
		
	</td>

</tr></tbody></table>

</div>
	
		
</div>

</div><!-- End demo -->

</dhv:container>

