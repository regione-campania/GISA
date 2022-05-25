<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ include file="../initPage.jsp" %>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="OrgOperatore" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="OrgUtente" class="org.aspcf.modules.controlliufficiali.base.OrganizationUtente" scope="request"/>
<jsp:useBean id="OggettoControllo" class="org.aspcf.modules.controlliufficiali.base.OggettoControllo" scope="request"/>
<jsp:useBean id="MotivoIspezione" class="org.aspcf.modules.controlliufficiali.base.MotivoIspezione" scope="request"/>


<%!
	
	public static String fixValore(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	else
		return code;
	
}
%>

	
<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<script>


	function checkSubmit() {
		myVar=confirm("Sei sicuro di voler salvare in modo definitivo i dati? Se sì, non sarà più possibile modificare i dati inseriti.");
		if(myVar=="1")
		{
			document.myform.submit();
			//window.opener.location.href ='AccountVigilanza.do?command=TicketDetails&id=214431&orgId=762051';
		 	//window.close();
		}
		
	}

	function closeAndRefresh(chiudi,url,bozza) {
		if(chiudi == "si")
		{
			self.close();
			window.opener.location.href = url;
		 	
		}

		if(bozza == 'false'){
			var f = document.forms['myform'];
			for(var i=0,fLen=f.length;i<fLen;i++){
			  f.elements[i].readOnly = true;
			  f.elements[i].className = 'layout';
			}
			var g = document.forms['myform'].getElementsByTagName("textarea");
			for(var j=0; j < g.length; j++)
				 g.item(j).className = 'textdim2';
				 
			document.getElementById('stampaId').className = 'buttonClass';
			document.getElementById('idbutn').style.display = 'none';
			document.getElementById('generaPDFInit').className = 'buttonClass';		
		}
		
	}
	

	
</script>

</HEAD>
<BODY  onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>','<%= request.getAttribute("bozza") %>')"> 


<form method="post" name="myform" action="PrintReportVigilanza.do?command=SaveUserFields&idControllo=<%=MotivoIspezione.getIdControllo()%>&orgId=<%=OrgOperatore.getOrgId()%>&url=<%=OrgOperatore.getOperatoreUrl()%>">

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/controlliufficiali/mod5_header.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 

<font size="1px">IdCU: <%=MotivoIspezione.getIdControllo()%></font><br/>

<div id="idbutn" style="display:block;"><input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/></div>
<input id="stampaId" type="button" class = "buttonClass" value ="Stampa" onclick="window.print();" style="display:none"/>
<input type="hidden" id = "bozza" name = "bozza" value="">
<input type="hidden" id = "orgId" name = "orgId" value="<%=OrgOperatore.getOrgId()%>">
<%-- onclick="this.form.bozza.value='false';" --%>

<%if (!request.getParameter("bozza").equals("true") ) {%>

<%-- <a style="text-decoration: none; color:black;" href="javascript:openRichiestaPDF_Modello5('<%=MotivoIspezione.getIdControllo()%>','<%= OrgOperatore.getOperatoreUrl() %>','<%= OrgOperatore.getOrgId() %>','5','modello5');"> --%>
<!--               <input type="button" value="Genera e Stampa PDF (nuovo bottone)"/>  -->
<!-- </a> -->
<!-- <br> -->

<input type="button" id ="generaPDFInit" class="buttonClass"  value="Genera e Stampa PDF" 	
onClick="javascript:openRichiestaPDF_Modello5('<%=MotivoIspezione.getIdControllo()%>','<%= OrgOperatore.getOperatoreUrl() %>','<%= OrgOperatore.getOrgId() %>','5','modello5');" />

<!-- <input type="button" id ="generaPDFInit" class="buttonClass"  value="Genera e Stampa PDF (vecchio bottone)" 	 -->
<!-- onClick="window.scrollTo(0,document.body.scrollHeight); document.getElementById('generaPDF').click()" /> -->
<%} %>




<P class="main">L'anno <input class="layout" type="text" readonly name="" id="" value="<%= OrgOperatore.getAnnoReferto()%>" size="4" maxlength="4"/> addì <input class="layout" type="text" readonly name="" id="" value="<%= OrgOperatore.getGiornoReferto()%>" size="2" maxlength="2" /> del mese di <input class="layout" type="text" readonly name="" value="<%= OrgOperatore.getMeseReferto()%>" id="" size="10" maxlength="10"/> 
alle ore <input class="editField" type="text" value="<%=OrgUtente.getOre()%>" name="ore" id="ore" size="5" maxlength="5"/> 
i sottoscritti <br/>

<% if (OrgOperatore.getComponente_nucleo()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_due()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_due()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_tre()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_tre()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_quattro()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_quattro()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_cinque()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_cinque()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_sei()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_sei()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_sette()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_sette()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_otto()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_otto()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_nove()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_nove()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_dieci()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_dieci()) %></label> <%} %> 

<br><br>qualificandosi, si sono presentati presso:
</P>
<P class="main">
<%-- <% System.out.println("Nome: "+OrgOperatore.getRagione_sociale()); %> --%>
<U><b>Stabilimento/azienda/altro</b>(luogo dell'ispezione):</U> 
Comune di <input class="layout" type="text"  readonly  name="" id="" size="88" maxlength="" value="<%= (OrgOperatore.getComune() != null ) ? OrgOperatore.getComune() : "" %>" /> 
alla via <input class="layout" type="text"  readonly  name="" id="" maxlenght="70" size="70" value="<%= (OrgOperatore.getIndirizzo() != null ) ? OrgOperatore.getIndirizzo() : "" %>" /> 
n° <input class="layout" type="text" readonly  name="" id="" size="6" maxlength ="6"/> 
ric.CE n° <input class="layout" type="text" readonly  size="40" name="" id="" value="<%= (OrgOperatore.getApproval_number() != null) ? OrgOperatore.getApproval_number() : ""%>"/> 
regist./cod.az./targa/n.seriale <input class="layout" type="text" readonly name="" id="" size= "30" maxlenght="30" value="<%= (OrgOperatore.getN_reg() != null) ? OrgOperatore.getN_reg() : "" %>" /> 
linea di attività ispezionata <input class="layout" type="text" readonly  name="" id="" size="121" maxlength="" value="<%= (OrgOperatore.getTipologia_att() != null ) ? OrgOperatore.getTipologia_att() : ""%>" />.<br>
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U><input class="layout" readonly type="text" name="ragione_sociale" id="ragione_sociale" value="<%= (OrgOperatore.getRagione_sociale() != null) ?  OrgOperatore.getRagione_sociale().replace('"',' ') : ""%>" size="102" maxlength=""/>
sede legale in <input class="layout" type="text" readonly  name="" id="" size="50" maxlength="" value="<%= ((String )OrgOperatore.getSede_legale()) != null ? OrgOperatore.getSede_legale() : ""%>" /> 
alla via <input class="layout" type="text" readonly  name="" id="" size="50" maxlength="" value="<%= ((String )OrgOperatore.getIndirizzo_legale()) != null ? OrgOperatore.getIndirizzo_legale() : "" %>" /> 
n° <input class="layout" type="text" readonly  name="" id="" size="6" maxlength="6"/> <br>
PI/CF <input class="layout" type="text" readonly  name="" id="" size="40" maxlength="16" value="<%= (String )OrgOperatore.getCodice_fiscale()%>" /> 
legale rappr. sig. <input class="layout" type="text" readonly  name="" id="" size="80" maxlength=""/ value="<%= ((String )OrgOperatore.getLegale_rapp()) !=null ? OrgOperatore.getLegale_rapp(): "" %>" > 
nato a <input class="layout" type="text" readonly  name="" id="" size="20" maxlength="" value="<%= ((String )OrgOperatore.getLuogo_nascita_rappresentante()) != null ? (String)OrgOperatore.getLuogo_nascita_rappresentante() :  "" %>"/> 
il <input class="layout" type="text" readonly  name="" id="" size="2" maxlength="2" value="<%= OrgOperatore.getGiornoNascita()%>"/>/
<input class="layout" type="text" readonly  name="" id="" size="2" maxlength="2" value="<%= OrgOperatore.getMeseNascita()%>"/>/
<input class="layout" type="text" readonly  name="" id="" size="4" maxlength="4" value="<%= OrgOperatore.getAnnoNascita()%>"/> 
e residente in <input class="layout" type="text" name="luogo_residenza" id="luogo_residenza" value="<%=OrgUtente.getResidenza_legale()%>" size="30" maxlength="" /> 
alla via <input class="layout" type="text" name="indirizzo_legale_rappresentante" id="indirizzo_legale_rappresentante" size="50" maxlength="" value="<%= OrgUtente.getIndirizzo_legale()%>" /> 
n° <input class="editField" type="text" name="num_civico_rappresentante" id="num_civico_rappresentante" value="<%=OrgUtente.getNumero_legale() %>" size="6" maxlength="6"/> 
domicilio digitale <input class="editField" type="text" name="domicilio_digitale" id="domicilio_digitale" size="50" maxlength="" value="<%= (OrgOperatore.getDomicilioDigitale() != null && !OrgOperatore.getDomicilioDigitale().equals("null")) ? OrgOperatore.getDomicilioDigitale() : ""%>" /> <br>
<U><b>Presente all'ispezione:</b></U> 
sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione" value="<%=OrgUtente.getNome_presente_ispezione()%>" size="120" maxlength=""/>
nato a <input class="editField" type="text" name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione" value="<%=OrgUtente.getLuogo_nascita_presente_ispezione()%>" size="90" maxlength=""/> 
il <input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione" value="<%=OrgUtente.getGiorno_presente_ispezione()%>" size="2" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione" value="<%=OrgUtente.getMese_presente_ispezione()%>" size="2" maxlength="2"/>/
<input class="editField" type="text" name="anno_presente_ispezione" id="anno_presente_ispezione" value="<%=OrgUtente.getAnno_presente_ispezione()%>" size="4" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione" value="<%=OrgUtente.getLuogo_residenza_presente_ispezione()%>" size="60" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione"  value="<%=OrgUtente.getVia_ispezione()%>" size="70" maxlength=""/> 
n° <input class="editField" type="text" name="num_civico_presente_ispezione" id="num_civico_presente_ispezione" value="<%=OrgUtente.getNum_civico_presente_ispezione()%>" size="6" maxlength="6"/> 
doc.ident. <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione" value="<%=OrgUtente.getDoc_identita_presente_ispezione()%>" size="20" maxlength="" />. 
Questi si è dichiarato quale incaricato della ricezione di atti ed è stato avvisato della facoltà di farsi assistere da un legale di fiducia.<br><br>
</P> 
<table width="100%">
<tr>
<td>
<U><b>MOTIVO DELL'ISPEZIONE</b></U>:

<% String attivita = "";
if(MotivoIspezione.isSorveglianza())
   attivita+= "sorveglianza <br/>";
if(MotivoIspezione.isChecked(11))
   attivita+="programmata in base alla categ. di rischio <br/>";
if(MotivoIspezione.isChecked(8)) 
   attivita+="verifica risoluzione n.c. <br/>";
if(MotivoIspezione.isChecked(9))
   attivita+="sospetto di presenza n.c <br/>";
if(MotivoIspezione.isChecked(14))
   attivita+="macellazione privata <br/>";
if(MotivoIspezione.isChecked(28))
   attivita+="macellazione d'urgenza <br/>";
if(MotivoIspezione.isChecked(12))
   attivita+="richiesta forze dell'ordine/altre autorità <br/>";
if(MotivoIspezione.isChecked(44))
   attivita+="richiesta forze dell'ordine o altre autorita' - supervisione del direttore / responsabile (1.1) <br/>";
 if(MotivoIspezione.isChecked(6))
   attivita+="reclamo <br/>";
 if(MotivoIspezione.isChecked(39))
   attivita+="qualif. indenne Aujeszky <br/>";
if(MotivoIspezione.isChecked(1))
   attivita+="a seguito di campione/tampone non conforme <br/>";
if(MotivoIspezione.isChecked(29))
   attivita+="dissequestro/distruzione <br/>";
if(MotivoIspezione.isChecked(36))
   attivita+="indagini epidemiologiche <br/>";
if(MotivoIspezione.isChecked(31))
   attivita+="controllo focolai malattie inf.ve anim. <br/>";
if(MotivoIspezione.isChecked(40))
   attivita+="spostamento e/o compravendita anim. <br/>";
if(MotivoIspezione.isChecked(33))
   attivita+="diagnostica cadaverica grandi anim. <br/>";
if(MotivoIspezione.isChecked(20)) 
   attivita+="svincolo sanitario <br/>";
if(MotivoIspezione.isChecked(15))
   attivita+="SCIA <br/>";
if(MotivoIspezione.isChecked(17))
   attivita+="riconoscimento <br/>";
if(MotivoIspezione.isChecked(37))
   attivita+="vaccinaz. carbonchio ematico <br/>";
if(MotivoIspezione.isChecked(38))
   attivita+="vaccinaz. vitelle con RB51 <br/>";
if(MotivoIspezione.isChecked(34))
   attivita+="identif. elettron. bufal./bov./ovicapr. (casi previsti da <br> O.M.9/8/12) <br/>";
if(MotivoIspezione.isChecked(32))
   attivita+="identif. elettron. bufal./bov./ovicapr. (escluso casi previsti da O.M.9/8/12) <br/>";
if(MotivoIspezione.isChecked(35))
   attivita+="identif. elettron. bufal./bov./ovicapr. (escluso casi previsti da O.M.9/8/12) <br/>";
if(MotivoIspezione.isChecked(30) || MotivoIspezione.isChecked(5))
   attivita+="certificaz. a favore dell'impresa <br/>";
if(MotivoIspezione.isChecked(26))
   attivita+="avvelen. animali <br/>";
if(MotivoIspezione.isChecked(7))
   attivita+="sistema allarme rapido: "+ MotivoIspezione.getAllarmeRapido();
if(MotivoIspezione.isChecked("4123a"))
	   attivita+="Ispezioni per attivita' a favore di imprese o privati (ad escusione dei certificati di esportazionedi cui all'attivita' B8 <br/>";
String vuoto = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
%>
<br/>
Attività: <label class="layout"><%=(!attivita.equals("")) ? attivita : vuoto %></label><br/>
<% String piani = "";
if(MotivoIspezione.isChecked(2)){
// 	piani+= (MotivoIspezione.getPiano1() != null ) ?  MotivoIspezione.getPiano1() : "";
// 	piani = piani.replaceAll("-", "<br/> -");
piani +=(MotivoIspezione.getPianoHtml() != null ) ?  MotivoIspezione.getPianoHtml() : "";
}%>
Piano di monitoraggio: <label class="layout"><%=(!piani.equals("")) ? piani : vuoto %></label><br/>
	
<% if(MotivoIspezione.isChecked(47)) { 
	HashMap<Integer,String> tipoCondizionalita =  OrgOperatore.getTipoCondizionalita();
	String tipiCond = "" ;
	Iterator<Integer> itcond = tipoCondizionalita.keySet().iterator();
	while (itcond.hasNext())
	{
		tipiCond += tipoCondizionalita.get(itcond.next())+";";	
	}
	%>
   <span class="checkedItem"> &nbsp;Condizionalità</span> 
  <label class="layout"><%=tipiCond%></label> <br>
<% } else { 
%>
  <span class="NocheckedItem"> &nbsp;Condizionalità</span> 
   <input class="layout" type="text" readonly  name="" id="" size="80" maxlength="" value=""/> <br>
 <% } %>
</td>
</tr>
</table>

<h3>OGGETTO DEL CONTROLLO</h3> 
<table>
<P class="main">
<TR>
  <TH style="text-align:center"> Settori: Sicurezza alimenti per uso umano, Etichettatura alimenti uso umano </TH>
</TR>
<TR>
  <TD>
<% if( OggettoControllo.isChecked(19)) { %>
   <span class="checkedItem"> &nbsp; igiene degli alimenti</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp; igiene degli alimenti</span>
 <% } %> 
<% if( OggettoControllo.isChecked(4)) { %>
   <span class="checkedItem"> &nbsp; tracciab./rintracciab.</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;tracciab./rintracciab.</span>
<% } %>
<% if( OggettoControllo.isChecked(8)) { %>
   <span class="checkedItem"> &nbsp;materiali a contatto</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;materiali a contatto</span>
 <% } %> 
<% if( OggettoControllo.isChecked(2)) { %>
   <span class="checkedItem"> &nbsp;requisiti igienici dei locali/attrezzature</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;requisiti igienici dei locali/attrezzature</span>
<% } %> 
<% if( OggettoControllo.isChecked(5)) { %>
   <span class="checkedItem"> &nbsp;etichettatura</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;etichettatura</span>
<% } %> 
<% if( OggettoControllo.isChecked(7)) { %>
   <span class="checkedItem"> &nbsp;igiene del personale</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;igiene del personale</span>
 <% } %>
 <% if( OggettoControllo.isChecked(23)) { %>
   <span class="checkedItem"> &nbsp;trasporto</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;trasporto</span>
<% } %>  
 <% if( OggettoControllo.isChecked(20)) { %>
   <span class="checkedItem"> &nbsp;autocontr.(BPI) </span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;autocontr.(BPI) </span>
<% } %> 
 <% if( OggettoControllo.isChecked(21)) { %>
   <span class="checkedItem"> &nbsp;autocontr.(BPA)</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;autocontr.(BPA)</span>
<% } %> 
 <% if( OggettoControllo.isChecked(22)) { %>
   <span class="checkedItem"> &nbsp;autocontr.(HACCP) </span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;autocontr.(HACCP) </span>
<% } %> 
 <% if( OggettoControllo.isChecked(25)) { %>
   <span class="checkedItem"> &nbsp;altro</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;altro</span>
<% } %> 
  <input class="layout" type="text" readonly  name="" id="" size="40" maxlength="" value="<%=OggettoControllo.getAltro_settore_consumo_umano() %>"/>
  </TD>
</TR>
<TR >
  <TH style="text-align:center">Settori alimentazione animale</TH>
</TR>
<TR>
  <TD>
<% if( OggettoControllo.isChecked(12)) { %>
   <span class="checkedItem"> &nbsp;igiene alimenti (animali DPA)</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;igiene alimenti (animali DPA)</span>
<% } %> 
 <% if( OggettoControllo.isChecked(13)) { %>
   <span class="checkedItem"> &nbsp;igiene alimenti (animali NON DPA)</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;igiene alimenti (animali NON DPA)</span>
<% } %> 
 <% if( OggettoControllo.isChecked(28)) { %>
   <span class="checkedItem"> &nbsp;tracciab./rintracciab.</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;tracciab./rintracciab.</span>
<% } %> 
 <% if( OggettoControllo.isChecked(31)) { %>
   <span class="checkedItem"> &nbsp;etichettatura</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;etichettatura</span>
<% } %> 
 <% if( OggettoControllo.isChecked(29)) { %>
   <span class="checkedItem"> &nbsp;trasporto</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;trasporto</span>
<% } %>
<% if( OggettoControllo.isChecked(30)) { %>
   <span class="checkedItem"> &nbsp;requisiti igienici locali/attrezzature</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;requisiti igienici locali/attrezzature</span>
<% } %> 
<% if( OggettoControllo.isChecked(32)) { %>
   <span class="checkedItem"> &nbsp;autocontr. (HACCP) </span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;autocontr. (HACCP) </span>
<% } %>
<% if( OggettoControllo.isChecked(33)) { %>
   <span class="checkedItem"> &nbsp; autocontrollo (B.P.I)</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp; autocontrollo (B.P.I)</span>
<% } %>   
<% if( OggettoControllo.isChecked(34)) { %>
   <span class="checkedItem"> &nbsp; autocontrollo (B.P.A)</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;autocontrollo (B.P.A) </span>
<% } %>   
<% if( OggettoControllo.isChecked(37)) { %>
   <span class="checkedItem"> &nbsp; altro</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;altro </span>
<% } %>
 <input class="layout" type="text" readonly  name="" id="" size="25" maxlength="" value="<%=OggettoControllo.getAltro_settore_zootecnici()%>"/> 
 </TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore S.O.A. negli stabilimenti di trasformazione e commercializzazione S.O.A.</TH>
</TR>
<TR>
  <TD>
<% if( OggettoControllo.isChecked(45)) { %>
   <span class="checkedItem"> &nbsp; strutture, attrezzature, condizioni di pulizia</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;strutture, attrezzature, condizioni di pulizia</span>
<% } %>
<% if( OggettoControllo.isChecked(46)) { %>
   <span class="checkedItem"> &nbsp;SOA in ingresso</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;SOA in ingresso</span>
<% } %>
<% if( OggettoControllo.isChecked(47)) { %>
   <span class="checkedItem"> &nbsp;SOA finiti/depositati</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;SOA finiti/depositati</span>
<% } %>
<% if( OggettoControllo.isChecked(50)) { %>
   <span class="checkedItem"> &nbsp;gestione residui fine lavorazione</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;gestione residui fine lavorazione</span>
<% } %>
<% if( OggettoControllo.isChecked(48)) { %>
   <span class="checkedItem"> &nbsp;parametri di processo</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;parametri di processo </span>
<% } %>   
<% if( OggettoControllo.isChecked(49)) { %>
   <span class="checkedItem"> &nbsp; autocontrollo (HACCP)</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;autocontrollo (HACCP) </span>
<% } %> 
<% if( OggettoControllo.isChecked(51)) { %>
   <span class="checkedItem"> &nbsp;registri</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;registri</span>
<% } %> 
<% if( OggettoControllo.isChecked(52)) { %>
   <span class="checkedItem"> &nbsp; altro</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;altro </span>
<% } %> 

<input class="layout" type="text" readonly name="" value="<%= OggettoControllo.getAltro_settore_impianti() %>" id="" size="35" maxlength=""/>;
</TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore Rifiuti e S.O.A. negli altri stabilimenti</TH>
</TR>
<TR>
  <TD> 
<% if( OggettoControllo.isChecked(54)) { %>
   <span class="checkedItem"> &nbsp;gestione rifiuti</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;gestione rifiuti</span>
<% } %>  
<% if( OggettoControllo.isChecked(55)) { %>
   <span class="checkedItem"> &nbsp;gestione olii esausti</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;gestione olii esausti</span>
<% } %> 
<% if( OggettoControllo.isChecked(56)) { %>
   <span class="checkedItem"> &nbsp;gestione S.O.A.</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;gestione S.O.A.</span>
<% } %> 
<% if( OggettoControllo.isChecked(57)) { %>
   <span class="checkedItem"> &nbsp;gestione M.S.R.</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp; gestione M.S.R.</span>
<% } %>   
<% if( OggettoControllo.isChecked(58)) { %>
   <span class="checkedItem"> &nbsp;trasporto S.O.A.</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;trasporto S.O.A.</span>
<% } %> 
<% if( OggettoControllo.isChecked(59)) { %>
   <span class="checkedItem"> &nbsp; altro</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;altro </span>
<% } %> 
<input class="layout" type="text" readonly name="" value="<%= OggettoControllo.getAltro_settore_rifiuti() %>" id="" size="35" maxlength=""/>;     
  </TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore benessere animale (<U>non</U> durante il trasporto)</TH>
</TR>
<TR>
<TD>
<% if( OggettoControllo.isChecked(38)) { %>
   <span class="checkedItem"> &nbsp;ben. animali da compagnia</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;ben. animali da compagnia</span>
<% } %> 
<% if( OggettoControllo.isChecked(39)) { %>
   <span class="checkedItem"> &nbsp;ben. animali da reddito </span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;ben. animali da reddito </span>
<% } %> 
<% if( OggettoControllo.isChecked(65)) { %>
   <span class="checkedItem"> &nbsp;ben. durante la macellazione </span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp; ben. durante la macellazione </span>
<% } %> 
<% if( OggettoControllo.isChecked(66)) { %>
   <span class="checkedItem"> &nbsp; altro</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;altro </span>
<% } %> 
<input class="layout" type="text" readonly  name="" id="" size="50" maxlength="" value="<%=OggettoControllo.getAltro_settore_benessere_animale() %>"/>.
</TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore benessere animale durante il trasporto</TH>
</TR>
<TR>
  <TD> 
<% if( OggettoControllo.isChecked(84)) { %>
   <span class="checkedItem"> &nbsp;c/o luogo partenza</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;c/o luogo partenza</span>
<% } %> 
<% if( OggettoControllo.isChecked(85)) { %>
   <span class="checkedItem"> &nbsp;durante trasporto </span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;durante trasporto </span>
<% } %> 
<% if( OggettoControllo.isChecked(86)) { %>
   <span class="checkedItem"> &nbsp;c/o posto di controllo</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;c/o posto di controllo</span>
<% } %> 
<% if( OggettoControllo.isChecked(87)) { %>
   <span class="checkedItem"> &nbsp; c/o macello di destinazione</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp; c/o macello di destinazione</span>
<% } %>  
<% if( OggettoControllo.isChecked(88)) { %>
   <span class="checkedItem"> &nbsp;c/o altra destinazione</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;c/o altra destinazione</span>
<% } %>  
</TD>
</TR>    
<TR>   
  	<TD>N.capi 
     <input class="layout" type="text" readonly  name="" id="" size="6" maxlength="6" value="<%=(MotivoIspezione.getNCapi()>=0) ? MotivoIspezione.getNCapi() : "" %>"/> della specie: 
	<% if( MotivoIspezione.isSpecieChecked(11)) { %>
   	<span class="checkedItem"> &nbsp;pesci d'acqua dolce</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;pesci d'acqua dolce </span>
	<% } %>   
	<% if( MotivoIspezione.isSpecieChecked(12)) { %>
   	<span class="checkedItem"> &nbsp;pesci ornamentali</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;pesci ornamentali</span>
	<% } %>   
	<% if( MotivoIspezione.isSpecieChecked(13)) { %>
   	<span class="checkedItem"> &nbsp;oche</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;oche</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(1)) { %>
   	<span class="checkedItem"> &nbsp;bovini</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;bovini</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(10)) { %>
   	<span class="checkedItem"> &nbsp;bufali</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;bufali</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(14)) { %>
   	<span class="checkedItem"> &nbsp;conigli</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;conigli</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(4)) { %>
   	<span class="checkedItem"> &nbsp;equidi</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;equidi</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(15)) { %>
   	<span class="checkedItem"> &nbsp;ovaiole</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;ovaiole</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(16)) { %>
   	<span class="checkedItem"> &nbsp;broiler</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;broiler</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(21)) { %>
   	<span class="checkedItem"> &nbsp;ovicaprini</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;ovicaprini</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(2)) { %>
   	<span class="checkedItem"> &nbsp;suini</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;suini</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(18)) { %>
   	<span class="checkedItem"> &nbsp;vitelli</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;vitelli</span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(19)) { %>
   	<span class="checkedItem"> &nbsp;struzzi </span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;struzzi </span>
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(20)) { %>
   	<span class="checkedItem"> &nbsp;cani</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;cani</span>
	<% } %>
    <% if( MotivoIspezione.isSpecieChecked(6)) { %>
   	<span class="checkedItem"> &nbsp;altro </span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;altro </span>
	<% } %>
    <input class="layout" type="text" readonly  name="" id="" size="60" maxlength=""/>; <br>
    Luogo di partenza <input class="editField" type="text" name="luogo_partenza_trasporto" id="luogo_partenza_trasporto" value="<%=OrgUtente.getLuogo_partenza_trasporto() %>" size="35" maxlength=""/> 
    Nazione <input class="editField" type="text" name="nazione_partenza_trasporto" id="nazione_partenza_trasporto" value="<%=OrgUtente.getNazione_partenza_trasporto()%>" size="35" maxlength=""/> 
    data partenza <input class="editField" type="text" name="data_partenza" id="data_partenza" value="<%=OrgUtente.getData_partenza_trasporto()%>" size="10" maxlength="10"/> 
    ora <input class="editField" type="text" name="ora_partenza" id="ora_partenza" value="<%=OrgUtente.getOra_partenza_trasporto()%>" size="4" maxlength="4"/> <br>
     Destinazione <input class="editField" type="text" name="destinazione_trasporto" id="destinazione_trasporto" value="<%=OrgUtente.getDestinazione_trasporto() %>" size="35" maxlength=""/> 
     Nazione <input class="editField" type="text" name="nazione_destinazione_trasporto" id="nazione_destinazione_trasporto" value="<%=OrgUtente.getNazione_destinazione_trasporto()%>" size="35" maxlength=""/> 
     data presumibile di arrivo <input class="editField" type="text" name="data_arrivo_trasporto" id="data_arrivo_trasporto" value="<%=OrgUtente.getData_arrivo_trasporto()%>" size="10" maxlength="10"/> 
     ora <input class="editField" type="text" name="ora_arrivo_trasporto" id="ora_arrivo_trasporto" value="<%=OrgUtente.getOra_arrivo_trasporto()%>" size="5" maxlength="5"/> 
     Certificato sanitario n. <input class="editField" type="text" name="certificato_trasporto" id="certificato_trasporto" value="<%=OrgUtente.getCertificato_trasporto()%>" size="6" maxlength="6"/> del 
     <input class="editField" type="text" id="data_certificato_trasporto" name="data_certificato_trasporto" value="<%=OrgUtente.getData_certificato_trasporto() %>" size="10" maxlength="10"/> 
     luogo di rilascio <input class="editField" type="text" name="luogo_rilascio_trasporto" id="luogo_rilascio_trasporto" value="<%=OrgUtente.getLuogo_rilascio_trasporto()%>" size="20" maxlength=""/>.<br>
  </TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore Sanità animale</TH>
</TR>
<TR>
  <TD> 
  <% if( OggettoControllo.isChecked(42)) { %>
   	<span class="checkedItem"> &nbsp;controllo malattie infettive</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;controllo malattie infettive</span>
	<% } %>
	<input class="layout" type="text" readonly name="" id="" size="105" maxlength=""/> 
	<% if(OggettoControllo.isChecked(43)) { %>
   	<span class="checkedItem"> &nbsp;anagrafe</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;anagrafe</span>
	<% } %>
	<% if( OggettoControllo.isChecked(78)) { %>
   	<span class="checkedItem"> &nbsp;riproduzione animale</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;riproduzione animale</span>
	<% } %>
	<% if(OggettoControllo.isChecked(77)) { %>
   	<span class="checkedItem"> &nbsp;biosicurezza</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;biosicurezza</span>
	<% } %>
	<% if( OggettoControllo.isChecked(68)) { %>
   	<span class="checkedItem"> &nbsp;altro</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;altro</span>
	<% } %>
    <input class="layout" type="text" readonly name="" value="<%= OggettoControllo.getAltro_settore_sanita_animale() %>" id="" size="90" maxlength=""/>;     
  </TD>
</TR>
<TR>
  <TH style="text-align:center;">Settore Altro</TH>
</TR>
<TR>
  <TD> 
  <% if(OggettoControllo.isChecked(60)) { %>
   	<span class="checkedItem"> &nbsp;farmacosorveglianza</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;farmacosorveglianza</span>
	<% } %>
	 <% if(OggettoControllo.isChecked(61)) { %>
   	<span class="checkedItem"> &nbsp; farmacovigilanza </span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp; farmacovigilanza </span>
	<% } %>
	<% if(OggettoControllo.isChecked(62)) { %>
   	<span class="checkedItem"> &nbsp;sanità dei vegetali</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;sanità dei vegetali</span>
	<% } %>
  <% if(OggettoControllo.isChecked(63)) { %>
   	<span class="checkedItem"> &nbsp;altro</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;altro</span>
	<% } %>
	<input class="layout" type="text"readonly id="" value="<%= OggettoControllo.getAltro_altro() %>" maxlength="" size="70">;     
  </TD>
</TR></P>
</table>

<br>
  
Strumenti e/o tecniche utilizzate per l'ispezione: 
<input class="editField" type="text" name="strumenti_ispezione" id="strumenti_ispezione" value="<%=OrgUtente.getStrumenti_ispezione() != null ? OrgUtente.getStrumenti_ispezione(): ""%>" size="60" maxlength="150"/>.<br>
<table>
<TR>
  <TD> 
  <P class="main">
    Nel caso l'ispezione sia stata effettuata per la verifica della risoluzione di n.c. rilevate in un precedente controllo ufficiale, le azioni correttive messe in atto risultano essere adeguate ed efficaci? <br>
    <% if(MotivoIspezione.isSiAltro()) { %>
   	<span class="checkedItem"> &nbsp;SI</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;SI</span>
	<% } %>
    <% if(MotivoIspezione.isNo()) { %>
   	<span class="checkedItem"> &nbsp;NO(applicare sanzione)</span>
	<% } else { %>
  	<span class="NocheckedItem"> &nbsp;NO(applicare sanzione)</span>
	<% } %>
    Descrivere brevemente le modalità di risoluzione:<br>  
    <input class="layout" type="text" readonly name="descrizione" id="descrizione" value="<%= (MotivoIspezione.getDescrizione() != null) ? MotivoIspezione.getDescrizione() : "" %>" size="150" maxlength=""/>
    </P>
</TD>
</TR>
</TABLE>

<P class="main">
L'Unità organizzat. reponsabile del procedimento è quella sopraindicata, presso la cui sede la parte può prendere visione degli atti e presentare memorie scritte e documenti. Il responsabile del procedimento è  <input class="editField" type="text" name="responsabile_procedimento" size="50" id="responsabile_procedimento" value="<%=OrgUtente.getResponsabile_procedimento()%>" maxlength="" />.<br>
Descrizione dei provvedimenti <U>non</U> causati da n.c. a carico del soggetto ispezionato:<br>
<textarea rows="4" cols="50" class="textDim" readonly><%= OrgOperatore.getListProvNC() %>
</textarea><br>
</P>

<P class="main"> <b>Non conformità <U>formali</U> rilevate:</b><br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getDescrizione_ncf()%></textarea><br>
Punteggio *: <input class="layout" type="text" readonly  name="" value="<%=OrgOperatore.getPunteggio_formale()%>" id="" size="5" maxlength="5"/><br>
Valutazione del rischio caratterizzato dalla presenza delle n.c. formali (motivazioni di fatto):<br>
<textarea rows="4" cols="50"  class="textDim" readonly>
<%=OrgOperatore.getValutazione_formale()%>"
</textarea><br>

Follow up delle n.c. formali (dispositivo del provvedim. amministr.): visto Reg. CE 882/04, L.241/90, DPR 320/54 e DGRC 377/11 si dispone che <br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getFollowUpFormale()%></textarea><br>(ex art.54 Reg. CE 882/04, avverso il provvedim. ammin. si può ricorrere al T.A.R. entro 60 giorni dalla data di notifica)<br><br>
<b>Non conformità <U>significative</U> rilevate:</b><br>
<textarea rows="4" cols="50" class="textDim" readonly>
<%=OrgOperatore.getDescrizione_ncs()%>
</textarea><br>
Punteggio *: <input class="layout" type="text" readonly  name="" id="" value="<%=OrgOperatore.getPunteggio_significativo()%>" size="5" maxlength="5"/><br>
Valutazione del rischio caratterizzato dalla presenza delle n.c. significative (motivazioni di fatto)<br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getValutazione_significativa()%></textarea><br>
Follow up delle n.c. significative (dispositivo del provvedim. amministr.): visto Reg. CE 882/04, L.241/90, DPR 320/54, DGRC n. 377/11 si dispone che <br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getFollowUpSign()%></textarea><br>
(ex art.54 Reg. CE 882/04, avverso il provvedim. ammin. si può ricorrere al T.A.R. entro 60 giorni dalla data di notifica)<br><br>
<b>Non conformità <U>gravi</U> rilevate:</b><br> 
<textarea rows="4" cols="50"  class="textDim" readonly><%=OrgOperatore.getDescrizione_ncg()%></textarea><br>
Punteggio *: <input class="layout" type="text" readonly  name="" id="" value="<%=OrgOperatore.getPunteggio_grave()%>" size="5" maxlength="5" /><br><br>

<b>Follow up delle n.c. gravi</b>:
<ol>
<% 
    int i = 0;
	ArrayList<String> key = new ArrayList<String> ();
	ArrayList<String> value = new ArrayList<String> ();
   if(OrgUtente.getGravi() != null){
	   StringTokenizer st = new StringTokenizer(OrgUtente.getGravi(),"=;");
	   while(st.hasMoreTokens()){
		  ++i;
		  key.add(st.nextToken());
		  value.add(st.nextToken());		
		}   
   }
   	for (int j=i; j<=16; j++){
   		value.add("");
   	}
%>	
<li> 
<% if(OrgOperatore.isNcgCheck1()) { %>
   	<span class="checkedItem"> &nbsp;si è proceduto alla contestazione di illecit<input class="editField" type="text" name="gravi_1" id="gravi_1" value="<%= value.get(0) %>" maxlength="1" size="1"/> 
amministrativ<input class="editField" type="text" name="gravi_2" id="gravi_2" value="<%= value.get(1)%>" maxlength="1" size="1"/> 
con verbal<input class="editField" type="text"  name="gravi_3" id="gravi_3" value="<%= value.get(2) %>" size="1" maxlength="1"/> 
n.<input class="layout" type="text"  name="proc_verb" id="proc_verb" value="<%= OrgOperatore.getProcessiVerbali() %>" size="25" maxlength="25"/>
</span>
<% } else { %>
	<span class="NocheckedItem"> &nbsp;si è proceduto alla contestazione di illecit<input class="editField" type="text"  name="gravi_1" id="gravi_1" value="<%= value.get(0) %>" maxlength="1" size="1"/> 
amministrativ<input class="editField" type="text" name="gravi_2" id="gravi_2" value="<%= value.get(1)%>" maxlength="1" size="1"/> 
con verbal<input class="editField" type="text" name="gravi_3" id="gravi_3" value="<%= value.get(2) %>" size="1" maxlength="1"/> 
n.<input class="layout" type="text" name="proc_verb" id="proc_verb" value="<%= OrgOperatore.getProcessiVerbali() %>" size="25" maxlength="25"/>
</span>
<% } %>
<li>
<% if(OrgOperatore.isNcgCheck2()) { %>
   	<span class="checkedItem"> &nbsp;si procederà, se del caso e/o previo ulteriori accertamenti, alla notifica di illecit
<input class="editField" type="text" name="gravi_4" id="gravi_4" value="<%= value.get(3) %>" maxlength="1" size="1" /> 
amministrativ<input class="editField" type="text" name="gravi_5" id="gravi_5" value="<%= value.get(4) %>" size="1" maxlength="1"/> 
con att<input class="editField" type="text" name="gravi_6" id="gravi_6" value="<%= value.get(5) %>" maxlength="1" size="1"/> a parte
</span>
<% } else { %>
<span class="NocheckedItem"> &nbsp;si procederà, se del caso e/o previo ulteriori accertamenti, alla notifica di illecit
<input class="editField" type="text" name="gravi_4" id="gravi_4" value="<%= value.get(3) %>" maxlength="1" size="1" /> 
amministrativ<input class="editField" type="text" name="gravi_5" id="gravi_5" value="<%= value.get(4) %>" size="1" maxlength="1"/> 
con att<input class="editField" type="text" name="gravi_6" id="gravi_6" value="<%= value.get(5) %>" maxlength="1" size="1"/> a parte
</span>
<% } %>
<li> 
<% if(OrgOperatore.isNcgCheck3()) { %>
   	<span class="checkedItem"> &nbsp;visto il Reg. CE 882/04, la L. 689/81 e la DGRC n. 377/11, in seguito alla rilevazione di illecito amministrativo, si è proceduto al sequestro amministrativo delle cose confiscabili 
elencate ne<input class="editField" type="text" name="gravi_7" id="gravi_7" value="<%= value.get(6)%>" size="1" maxlength="1"/> 
verbal<input class="editField" type="text" name="gravi_8" id="gravi_8" value="<%= value.get(7) %>" size="1" maxlength="1"/>n.
<input class="layout" type="text" name="" id="" value="" size="4" maxlength="4"/>. 
</span>
<% } else { %>
<span class="NocheckedItem"> &nbsp;visto il Reg. CE 882/04, la L. 689/81 e la DGRC n. 377/11, in seguito alla rilevazione di illecito amministrativo, si è proceduto al sequestro amministrativo delle cose confiscabili 
elencate ne<input class="editField" type="text" name="gravi_7" id="gravi_7" value="<%= value.get(6)%>" size="1" maxlength="1"/> 
verbal<input class="editField" type="text" name="gravi_8" id="gravi_8" value="<%= value.get(7) %>" size="1" maxlength="1"/>n.
<input class="layout" type="text" name="" id="" value="" size="4" maxlength="4"/>. 
</span>
<% } %>
<li>
<% if(OrgOperatore.isNcgCheck4()) { %>
   	<span class="checkedItem"> &nbsp;si è proceduto al sequestro penale delle cose elencate ne
<input class="editField" type="text" name="gravi_9" id="gravi_9" value="<%= value.get(8) %>" size="1" maxlength="1"/> verbal
<input class="editField" type="text" name="gravi_10" id="gravi_10" value="<%=value.get(9) %>" size="1" maxlength="1"/> n.
<input class="layout" type="text" name="" id="" value="" size="4" maxlength="4"/> 
</span>
<% } else { %>
<span class="NocheckedItem"> &nbsp;si è proceduto al sequestro penale delle cose elencate ne
<input class="editField" type="text" name="gravi_9" id="gravi_9" value="<%= value.get(8) %>" size="1" maxlength="1"/> verbal
<input class="editField" type="text" name="gravi_10" id="gravi_10" value="<%=value.get(9) %>" size="1" maxlength="1"/> n.
<input class="layout" type="text" name="" id="" value="" size="4" maxlength="4"/> 
</span>
<% } %>
<li>
<% if(OrgOperatore.isNcgCheck5()) { %>
   	<span class="checkedItem"> &nbsp;visto il Reg. CE 882/04,
 L. 241/90, L 283/62 e DGRC n. 377/11 si è proceduto al blocco/sequestro sanitario delle cose elencate ne
 <input class="editField" type="text" name="gravi_11" id="gravi_11" value="<%=value.get(10) %>" size="1" maxlength="1"/> 
 verbal<input class="editField" type="text" name="gravi_12" id="gravi_12" value="<%= value.get(11) %>" size="1" maxlength="1"/> 
 n.<input class="layout" type="text" name="" id="" value="" size="4" maxlength="4"/> 
 che forma parte integrante del presente provvedimento amministrativo  
</span>
<% } else { %>
<span class="NocheckedItem"> &nbsp;visto il Reg. CE 882/04,
 L. 241/90, L 283/62 e DGRC n. 377/11 si è proceduto al blocco/sequestro sanitario delle cose elencate ne
 <input class="editField" type="text" name="gravi_11" id="gravi_11" value="<%=value.get(10) %>" size="1" maxlength="1"/> 
 verbal<input class="editField" type="text" name="gravi_12" id="gravi_12" value="<%= value.get(11) %>" size="1" maxlength="1"/> 
 n.<input class="layout" type="text" name="" id="" value="" size="4" maxlength="4"/> 
 che forma parte integrante del presente provvedimento amministrativo  
</span>
<% } %>
<input type="hidden" name="gravi_13" id="gravi_13" value=""/>

<li>
<% if(OrgOperatore.isNcgCheck6()) { %>
   	<span class="checkedItem"> &nbsp; visto Reg. CE 882/04, L. 241/90, DPR 320/54, DGRC n. 377/11 si dispone che (dispositivo del provved. amministr.):<br>   
	<textarea rows="4" cols="50"  class="textDim"><%=OrgOperatore.getFollowUpGravi()%></textarea><br>		
</span>
<% } else { %>
<span class="NocheckedItem"> &nbsp; visto Reg. CE 882/04, L. 241/90, DPR 320/54, DGRC n. 377/11 si dispone che (dispositivo del provved. amministr.): <br>  
	<textarea rows="4" cols="50"  class="textDim"><%=OrgOperatore.getFollowUpGravi()%></textarea><br>		  
</span>
<% } %>
</ol>
Valutazione del rischio caratterizzato dalla presenza delle n.c. gravi (motivazioni di fatto da redigere solo nei sopraelencati casi <b>5)</b> e <b>6)</b>):<br>	
<textarea rows="4" cols="50"  class="textDim"><%=(OrgOperatore.getValutazione_grave()) != null ? OrgOperatore.getValutazione_grave() : "" %></textarea><br>
Il presente all'ispezione spontaneamente dichiara che:<br>
<textarea rows="4" cols="50" class="textDim" >
</textarea><br>
Ai sensi degli art. 21 bis e quater L. 241/90 il presente provvedimento ha efficacia immediata in quanto cautelare e urgente. Essendo susseguente ad ispezione, al presente provvedimento si applica la deroga ex art.7 p. 1 L. 241/90 in materia di
comunicazione di avvio del procedimento. Si avvisa che alla scadenza del termine concesso per la risoluzione delle n.c., si procederà a nuova ispezione con spese a carico del soggetto.  In materia di sicurezza alimentare, la mancata risoluzione 
delle n.c. comporterà la contestazione di illecito amministrativo. Ai sensi dell'art. 13 D.L.vo 196/03 si informa che i dati  
personali potranno essere inviati ad uffici interni o P.A. esterne con finalità che riguardano la definizione della presente 
procedura e degli atti conseguenti. Consci delle sanzioni previste per le dichiarazioni mendaci, gli ispettori dichiarano che 
per nessuno di loro esistono conflitti d'interesse nello svolgimento della presente ispezione.<br> 
Note:<br>
<textarea rows="4" cols="50" class="textDim" id="note" name="note"><%=(OrgUtente.getNote()!=null && !OrgUtente.getNote().equals("")) ? OrgUtente.getNote() : OrgOperatore.getProblem() %>
</textarea><br>
Data chiusura ispez. <input class="layout" type="text" readonly  name="" id="" size="2" maxlength="2" value="<%=OrgOperatore.getGiorno_chiusura()%>"/>/<input class="layout" type="text" readonly  name="" id="" size="2" maxlength="2" value="<%=OrgOperatore.getMese_chiusura()%>"/>/<input class="layout" type="text" readonly  name="" id="" size="4" maxlength="4" value="<%=OrgOperatore.getAnno_chiusura()%>"/> Punteggio tot. delle n.c. <input class="layout" type="text" readonly name="" id="" size="5" maxlength="5" value="<%=OrgOperatore.getPunteggio_ispezione()%>"/>.
Fatto in n.<input class="editField" type="text" id="" value="<%=OrgUtente.getNumero_copie()%>" id="numero_copie" name="numero_copie" maxlength="4" size="4"> copie originali, letto, confermato, sottoscritto e consegnato. (*) n. 1 punto per ogni n.c. formale, n. 7 punti per ogni n.c. significativa, n. 25 punti per ogni n.c. grave. Nelle ispezioni effettuate nella sorveglianza, i punteggi delle n.c. sono già contabilizzati nelle check list. N.C. = Non Conformità
<br><br>
</P>
<P> IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IL LEGALE DI FIDUCIA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 
</DIV>
</BODY>   
</FORM>

<dhv:permission name="server_documentale-view">
<%if (!request.getParameter("bozza").equals("true") ) {%>
<!--  BOX DOCUMENTALE -->
	 <jsp:include page="../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
     <jsp:param name="ticketId" value="0" />
      <jsp:param name="tipo" value="5" />
       <jsp:param name="idCU" value="<%=request.getParameter("idControllo") %>" />
        <jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
<!--  BOX DOCUMENTALE -->
<% } %>
</dhv:permission>
