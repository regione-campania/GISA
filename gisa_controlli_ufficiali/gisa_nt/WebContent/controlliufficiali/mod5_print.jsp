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
	<%@ include file="/controlliufficiali/mod5_header_print.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 

<font size="1px">IdCU: <%=MotivoIspezione.getIdControllo()%></font><br/>

<div id="idbutn" style="display:block;"><input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/></div>
<input id="stampaId" type="button" class = "buttonClass" value ="Stampa" onclick="window.print();" style="display:none"/>
<input type="hidden" id = "bozza" name = "bozza" value="">
<input type="hidden" id = "orgId" name = "orgId" value="<%=OrgOperatore.getOrgId()%>">
<%-- onclick="this.form.bozza.value='false';" --%>

<%if (!request.getParameter("bozza").equals("true") ) {%>
<input type="button" id ="generaPDFInit" class="buttonClass"  value="Genera e Stampa PDF" 	
onClick="window.scrollTo(0,document.body.scrollHeight); document.getElementById('generaPDF').click()" />
<%} %>




<P class="main">L'anno <label class="layout"><%= fixValore(OrgOperatore.getAnnoReferto())%></label> addì <label class="layout"><%= fixValore(OrgOperatore.getGiornoReferto())%></label> del mese di <label class="layout"><%= fixValore(OrgOperatore.getMeseReferto())%></label> 
alle ore <label class="layout"><%= fixValore(OrgUtente.getOre())%></label> 
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
Comune di <label class="layout"><%= fixValore(OrgOperatore.getComune())%></label> 
alla via <label class="layout"><%= fixValore(OrgOperatore.getIndirizzo())%></label> 
n° <label class="layout"><%= fixValore("")%></label>
ric.CE n° <label class="layout"><%= fixValore(OrgOperatore.getApproval_number())%></label> 
regist./cod.az./targa/n.seriale <label class="layout"><%= fixValore(OrgOperatore.getN_reg())%></label> 
linea di attività ispezionata <label class="layout"><%= fixValore(OrgOperatore.getTipologia_att())%></label> .<br>
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U><label class="layout"><%= fixValore(OrgOperatore.getRagione_sociale().replace('"',' '))%></label> 
sede legale in <label class="layout"><%= fixValore(OrgOperatore.getSede_legale())%></label> 
alla via <label class="layout"><%= fixValore(OrgOperatore.getIndirizzo_legale())%></label> 
n° <label class="layout"><%= fixValore("")%></label> <br>
PI/CF <label class="layout"><%= fixValore(OrgOperatore.getCodice_fiscale())%></label> 
legale rappr. sig. <label class="layout"><%= fixValore(OrgOperatore.getLegale_rapp())%></label> 
nato a <label class="layout"><%= fixValore(OrgOperatore.getLuogo_nascita_rappresentante())%></label> 
il <label class="layout"><%= fixValore(OrgOperatore.getGiornoNascita())%></label> /
<label class="layout"><%= fixValore(OrgOperatore.getMeseNascita())%></label> /
<label class="layout"><%= fixValore(OrgOperatore.getAnnoNascita())%></label>  
e residente in <label class="layout"><%= fixValore(OrgUtente.getResidenza_legale())%></label> 
alla via <label class="layout"><%= fixValore(OrgUtente.getIndirizzo_legale())%></label> 
n° <label class="layout"><%= fixValore(OrgUtente.getNumero_legale())%></label> 
domicilio digitale <label class="layout"><%= fixValore(OrgOperatore.getDomicilioDigitale())%></label>  <br>
<U><b>Presente all'ispezione:</b></U> 
sig. <label class="layout"><%= fixValore(OrgUtente.getNome_presente_ispezione())%></label> 
nato a <label class="layout"><%= fixValore(OrgUtente.getLuogo_nascita_presente_ispezione())%></label> 
il <label class="layout"><%= fixValore(OrgUtente.getGiorno_presente_ispezione())%></label> /
<label class="layout"><%= fixValore(OrgUtente.getMese_presente_ispezione())%></label> /
<label class="layout"><%= fixValore(OrgUtente.getAnno_presente_ispezione())%></label> 
e residente in <label class="layout"><%= fixValore(OrgUtente.getLuogo_residenza_presente_ispezione())%></label> 
alla via <label class="layout"><%= fixValore(OrgUtente.getVia_ispezione())%></label> 
n° <label class="layout"><%= fixValore(OrgUtente.getNum_civico_presente_ispezione())%></label> 
doc.ident. <label class="layout"><%= fixValore(OrgUtente.getDoc_identita_presente_ispezione())%></label> . 
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
  [X] &nbsp;Condizionalità
  <label class="layout"><%=tipiCond%></label> <br>
<% } else { 
%>
  [] &nbsp;Condizionalità 
   <label class="layout"><%= fixValore("")%></label> <br>
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
   [X] &nbsp; igiene degli alimenti
<% } else { %>
  [] &nbsp; igiene degli alimenti
 <% } %> 
<% if( OggettoControllo.isChecked(4)) { %>
   [X] &nbsp; tracciab./rintracciab.
<% } else { %>
  [] &nbsp;tracciab./rintracciab.
<% } %>
<% if( OggettoControllo.isChecked(8)) { %>
   [X] &nbsp;materiali a contatto
<% } else { %>
  [] &nbsp;materiali a contatto
 <% } %> 
<% if( OggettoControllo.isChecked(2)) { %>
   [X] &nbsp;requisiti igienici dei locali/attrezzature
<% } else { %>
  [] &nbsp;requisiti igienici dei locali/attrezzature
<% } %> 
<% if( OggettoControllo.isChecked(5)) { %>
   [X] &nbsp;etichettatura
<% } else { %>
  [] &nbsp;etichettatura
<% } %> 
<% if( OggettoControllo.isChecked(7)) { %>
   [X] &nbsp;igiene del personale
<% } else { %>
  [] &nbsp;igiene del personale
 <% } %>
 <% if( OggettoControllo.isChecked(23)) { %>
   [X] &nbsp;trasporto
<% } else { %>
  [] &nbsp;trasporto
<% } %>  
 <% if( OggettoControllo.isChecked(20)) { %>
   [X] &nbsp;autocontr.(BPI) 
<% } else { %>
  [] &nbsp;autocontr.(BPI) 
<% } %> 
 <% if( OggettoControllo.isChecked(21)) { %>
   [X] &nbsp;autocontr.(BPA)
<% } else { %>
  [] &nbsp;autocontr.(BPA)
<% } %> 
 <% if( OggettoControllo.isChecked(22)) { %>
   [X] &nbsp;autocontr.(HACCP) 
<% } else { %>
  [] &nbsp;autocontr.(HACCP) 
<% } %> 
 <% if( OggettoControllo.isChecked(25)) { %>
   [X] &nbsp;altro
<% } else { %>
  [] &nbsp;altro
<% } %> 
  <label class="layout"><%= fixValore(OggettoControllo.getAltro_settore_consumo_umano())%></label> 
  </TD>
</TR>
<TR >
  <TH style="text-align:center">Settori alimentazione animale</TH>
</TR>
<TR>
  <TD>
<% if( OggettoControllo.isChecked(12)) { %>
   [X] &nbsp;igiene alimenti (animali DPA)
<% } else { %>
  [] &nbsp;igiene alimenti (animali DPA)
<% } %> 
 <% if( OggettoControllo.isChecked(13)) { %>
   [X] &nbsp;igiene alimenti (animali NON DPA)
<% } else { %>
  [] &nbsp;igiene alimenti (animali NON DPA)
<% } %> 
 <% if( OggettoControllo.isChecked(28)) { %>
   [X] &nbsp;tracciab./rintracciab.
<% } else { %>
  [] &nbsp;tracciab./rintracciab.
<% } %> 
 <% if( OggettoControllo.isChecked(31)) { %>
   [X] &nbsp;etichettatura
<% } else { %>
  [] &nbsp;etichettatura
<% } %> 
 <% if( OggettoControllo.isChecked(29)) { %>
   [X] &nbsp;trasporto
<% } else { %>
  [] &nbsp;trasporto
<% } %>
<% if( OggettoControllo.isChecked(30)) { %>
   [X] &nbsp;requisiti igienici locali/attrezzature
<% } else { %>
  [] &nbsp;requisiti igienici locali/attrezzature
<% } %> 
<% if( OggettoControllo.isChecked(32)) { %>
   [X] &nbsp;autocontr. (HACCP) 
<% } else { %>
  [] &nbsp;autocontr. (HACCP) 
<% } %>
<% if( OggettoControllo.isChecked(33)) { %>
   [X] &nbsp; autocontrollo (B.P.I)
<% } else { %>
  [] &nbsp; autocontrollo (B.P.I)
<% } %>   
<% if( OggettoControllo.isChecked(34)) { %>
   [X] &nbsp; autocontrollo (B.P.A)
<% } else { %>
  [] &nbsp;autocontrollo (B.P.A) 
<% } %>   
<% if( OggettoControllo.isChecked(37)) { %>
   [X] &nbsp; altro
<% } else { %>
  [] &nbsp;altro 
<% } %>
<label class="layout"><%= fixValore(OggettoControllo.getAltro_settore_zootecnici())%></label> 
 </TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore S.O.A. negli stabilimenti di trasformazione e commercializzazione S.O.A.</TH>
</TR>
<TR>
  <TD>
<% if( OggettoControllo.isChecked(45)) { %>
   [X] &nbsp; strutture, attrezzature, condizioni di pulizia
<% } else { %>
  [] &nbsp;strutture, attrezzature, condizioni di pulizia
<% } %>
<% if( OggettoControllo.isChecked(46)) { %>
   [X] &nbsp;SOA in ingresso
<% } else { %>
  [] &nbsp;SOA in ingresso
<% } %>
<% if( OggettoControllo.isChecked(47)) { %>
   [X] &nbsp;SOA finiti/depositati
<% } else { %>
  [] &nbsp;SOA finiti/depositati
<% } %>
<% if( OggettoControllo.isChecked(50)) { %>
   [X] &nbsp;gestione residui fine lavorazione
<% } else { %>
  [] &nbsp;gestione residui fine lavorazione
<% } %>
<% if( OggettoControllo.isChecked(48)) { %>
   [X] &nbsp;parametri di processo
<% } else { %>
  [] &nbsp;parametri di processo 
<% } %>   
<% if( OggettoControllo.isChecked(49)) { %>
   [X] &nbsp; autocontrollo (HACCP)
<% } else { %>
  [] &nbsp;autocontrollo (HACCP) 
<% } %> 
<% if( OggettoControllo.isChecked(51)) { %>
   [X] &nbsp;registri
<% } else { %>
  [] &nbsp;registri
<% } %> 
<% if( OggettoControllo.isChecked(52)) { %>
   [X] &nbsp; altro
<% } else { %>
  [] &nbsp;altro 
<% } %> 

<label class="layout"><%= fixValore(OggettoControllo.getAltro_settore_impianti())%></label> ;
</TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore Rifiuti e S.O.A. negli altri stabilimenti</TH>
</TR>
<TR>
  <TD> 
<% if( OggettoControllo.isChecked(54)) { %>
   [X] &nbsp;gestione rifiuti
<% } else { %>
  [] &nbsp;gestione rifiuti
<% } %>  
<% if( OggettoControllo.isChecked(55)) { %>
   [X] &nbsp;gestione olii esausti
<% } else { %>
  [] &nbsp;gestione olii esausti
<% } %> 
<% if( OggettoControllo.isChecked(56)) { %>
   [X] &nbsp;gestione S.O.A.
<% } else { %>
  [] &nbsp;gestione S.O.A.
<% } %> 
<% if( OggettoControllo.isChecked(57)) { %>
   [X] &nbsp;gestione M.S.R.
<% } else { %>
  [] &nbsp; gestione M.S.R.
<% } %>   
<% if( OggettoControllo.isChecked(58)) { %>
   [X] &nbsp;trasporto S.O.A.
<% } else { %>
  [] &nbsp;trasporto S.O.A.
<% } %> 
<% if( OggettoControllo.isChecked(59)) { %>
   [X] &nbsp; altro
<% } else { %>
  [] &nbsp;altro 
<% } %> 
<label class="layout"><%= fixValore(OggettoControllo.getAltro_settore_rifiuti())%></label> ;     
  </TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore benessere animale (<U>non</U> durante il trasporto)</TH>
</TR>
<TR>
<TD>
<% if( OggettoControllo.isChecked(38)) { %>
   [X] &nbsp;ben. animali da compagnia
<% } else { %>
  [] &nbsp;ben. animali da compagnia
<% } %> 
<% if( OggettoControllo.isChecked(39)) { %>
   [X] &nbsp;ben. animali da reddito 
<% } else { %>
  [] &nbsp;ben. animali da reddito 
<% } %> 
<% if( OggettoControllo.isChecked(65)) { %>
   [X] &nbsp;ben. durante la macellazione 
<% } else { %>
  [] &nbsp; ben. durante la macellazione 
<% } %> 
<% if( OggettoControllo.isChecked(66)) { %>
   [X] &nbsp; altro
<% } else { %>
  [] &nbsp;altro 
<% } %> 
<label class="layout"><%= fixValore(OggettoControllo.getAltro_settore_benessere_animale())%></label> .
</TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore benessere animale durante il trasporto</TH>
</TR>
<TR>
  <TD> 
<% if( OggettoControllo.isChecked(84)) { %>
   [X] &nbsp;c/o luogo partenza
<% } else { %>
  [] &nbsp;c/o luogo partenza
<% } %> 
<% if( OggettoControllo.isChecked(85)) { %>
   [X] &nbsp;durante trasporto 
<% } else { %>
  [] &nbsp;durante trasporto 
<% } %> 
<% if( OggettoControllo.isChecked(86)) { %>
   [X] &nbsp;c/o posto di controllo
<% } else { %>
  [] &nbsp;c/o posto di controllo
<% } %> 
<% if( OggettoControllo.isChecked(87)) { %>
   [X] &nbsp; c/o macello di destinazione
<% } else { %>
  [] &nbsp; c/o macello di destinazione
<% } %>  
<% if( OggettoControllo.isChecked(88)) { %>
   [X] &nbsp;c/o altra destinazione
<% } else { %>
  [] &nbsp;c/o altra destinazione
<% } %>  
</TD>
</TR>    
<TR>   
  	<TD>N.capi 
     <label class="layout"><%=(MotivoIspezione.getNCapi()>=0) ? MotivoIspezione.getNCapi() :  fixValore("") %></label>  della specie: 
	<% if( MotivoIspezione.isSpecieChecked(11)) { %>
   	[X] &nbsp;pesci d'acqua dolce
	<% } else { %>
  	[] &nbsp;pesci d'acqua dolce 
	<% } %>   
	<% if( MotivoIspezione.isSpecieChecked(12)) { %>
   	[X] &nbsp;pesci ornamentali
	<% } else { %>
  	[] &nbsp;pesci ornamentali
	<% } %>   
	<% if( MotivoIspezione.isSpecieChecked(13)) { %>
   	[X] &nbsp;oche
	<% } else { %>
  	[] &nbsp;oche
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(1)) { %>
   	[X] &nbsp;bovini
	<% } else { %>
  	[] &nbsp;bovini
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(10)) { %>
   	[X] &nbsp;bufali
	<% } else { %>
  	[] &nbsp;bufali
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(14)) { %>
   	[X] &nbsp;conigli
	<% } else { %>
  	[] &nbsp;conigli
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(4)) { %>
   	[X] &nbsp;equidi
	<% } else { %>
  	[] &nbsp;equidi
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(15)) { %>
   	[X] &nbsp;ovaiole
	<% } else { %>
  	[] &nbsp;ovaiole
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(16)) { %>
   	[X] &nbsp;broiler
	<% } else { %>
  	[] &nbsp;broiler
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(21)) { %>
   	[X] &nbsp;ovicaprini
	<% } else { %>
  	[] &nbsp;ovicaprini
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(2)) { %>
   	[X] &nbsp;suini
	<% } else { %>
  	[] &nbsp;suini
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(18)) { %>
   	[X] &nbsp;vitelli
	<% } else { %>
  	[] &nbsp;vitelli
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(19)) { %>
   	[X] &nbsp;struzzi 
	<% } else { %>
  	[] &nbsp;struzzi 
	<% } %>
	<% if( MotivoIspezione.isSpecieChecked(20)) { %>
   	[X] &nbsp;cani
	<% } else { %>
  	[] &nbsp;cani
	<% } %>
    <% if( MotivoIspezione.isSpecieChecked(6)) { %>
   	[X] &nbsp;altro 
	<% } else { %>
  	[] &nbsp;altro 
	<% } %>
    <label class="layout"><%= fixValore("")%></label>; <br>
    Luogo di partenza <label class="layout"><%= fixValore(OrgUtente.getLuogo_partenza_trasporto())%></label> 
    Nazione <label class="layout"><%= fixValore(OrgUtente.getNazione_partenza_trasporto())%></label> 
    data partenza <label class="layout"><%= fixValore(OrgUtente.getData_partenza_trasporto())%></label> 
    ora <label class="layout"><%= fixValore(OrgUtente.getOra_partenza_trasporto())%></label>  <br>
     Destinazione <label class="layout"><%= fixValore(OrgUtente.getDestinazione_trasporto())%></label> 
     Nazione <label class="layout"><%= fixValore(OrgUtente.getNazione_destinazione_trasporto())%></label> 
     data presumibile di arrivo <label class="layout"><%= fixValore(OrgUtente.getData_arrivo_trasporto())%></label> 
     ora <label class="layout"><%= fixValore(OrgUtente.getOra_arrivo_trasporto())%></label>  
     Certificato sanitario n. <label class="layout"><%= fixValore(OrgUtente.getCertificato_trasporto())%></label>  del 
     <label class="layout"><%= fixValore(OrgUtente.getData_certificato_trasporto())%></label> 
     luogo di rilascio <label class="layout"><%= fixValore(OrgUtente.getLuogo_rilascio_trasporto())%></label> .<br>
  </TD>
</TR>
<TR>
  <TH style="text-align:center;"> Settore Sanità animale</TH>
</TR>
<TR>
  <TD> 
  <% if( OggettoControllo.isChecked(42)) { %>
   	[X] &nbsp;controllo malattie infettive
	<% } else { %>
  	[] &nbsp;controllo malattie infettive
	<% } %>
	<label class="layout"><%= fixValore("")%></label>
	<% if(OggettoControllo.isChecked(43)) { %>
   	[X] &nbsp;anagrafe
	<% } else { %>
  	[] &nbsp;anagrafe
	<% } %>
	<% if( OggettoControllo.isChecked(78)) { %>
   	[X] &nbsp;riproduzione animale
	<% } else { %>
  	[] &nbsp;riproduzione animale
	<% } %>
	<% if(OggettoControllo.isChecked(77)) { %>
   	[X] &nbsp;biosicurezza
	<% } else { %>
  	[] &nbsp;biosicurezza
	<% } %>
	<% if( OggettoControllo.isChecked(68)) { %>
   	[X] &nbsp;altro
	<% } else { %>
  	[] &nbsp;altro
	<% } %>
    <label class="layout"><%= fixValore(OggettoControllo.getAltro_settore_sanita_animale())%></label> ;     
  </TD>
</TR>
<TR>
  <TH style="text-align:center;">Settore Altro</TH>
</TR>
<TR>
  <TD> 
  <% if(OggettoControllo.isChecked(60)) { %>
   	[X] &nbsp;farmacosorveglianza
	<% } else { %>
  	[] &nbsp;farmacosorveglianza
	<% } %>
	 <% if(OggettoControllo.isChecked(61)) { %>
   	[X] &nbsp; farmacovigilanza 
	<% } else { %>
  	[] &nbsp; farmacovigilanza 
	<% } %>
	<% if(OggettoControllo.isChecked(62)) { %>
   	[X] &nbsp;sanità dei vegetali
	<% } else { %>
  	[] &nbsp;sanità dei vegetali
	<% } %>
  <% if(OggettoControllo.isChecked(63)) { %>
   	[X] &nbsp;altro
	<% } else { %>
  	[] &nbsp;altro
	<% } %>
	<label class="layout"><%= fixValore(OggettoControllo.getAltro_altro())%></label> ;     
  </TD>
</TR></P>
</table>

<br>
  
Strumenti e/o tecniche utilizzate per l'ispezione: 
<label class="layout"><%= fixValore(OrgUtente.getStrumenti_ispezione())%></label> .<br>
<table>
<TR>
  <TD> 
  <P class="main">
    Nel caso l'ispezione sia stata effettuata per la verifica della risoluzione di n.c. rilevate in un precedente controllo ufficiale, le azioni correttive messe in atto risultano essere adeguate ed efficaci? <br>
    <% if(MotivoIspezione.isSiAltro()) { %>
   	[X] &nbsp;SI
	<% } else { %>
  	[] &nbsp;SI
	<% } %>
    <% if(MotivoIspezione.isNo()) { %>
   	[X] &nbsp;NO(applicare sanzione)
	<% } else { %>
  	[] &nbsp;NO(applicare sanzione)
	<% } %>
    Descrivere brevemente le modalità di risoluzione:<br>  
    <label class="layout"><%= fixValore(MotivoIspezione.getDescrizione())%></label> 
    </P>
</TD>
</TR>
</TABLE>

<P class="main">
L'Unità organizzat. reponsabile del procedimento è quella sopraindicata, presso la cui sede la parte può prendere visione degli atti e presentare memorie scritte e documenti. Il responsabile del procedimento è  <label class="layout"><%= fixValore(OrgUtente.getResponsabile_procedimento())%></label> .<br>
Descrizione dei provvedimenti <U>non</U> causati da n.c. a carico del soggetto ispezionato:<br>
<textarea rows="4" cols="50" class="textDim" readonly><%= OrgOperatore.getListProvNC() %>
</textarea><br>
</P>

<P class="main"> <b>Non conformità <U>formali</U> rilevate:</b><br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getDescrizione_ncf()%></textarea><br>
Punteggio *: <label class="layout"><%= fixValore(OrgOperatore.getPunteggio_formale())%></label> <br>
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
Punteggio *: <label class="layout"><%= fixValore(OrgOperatore.getPunteggio_significativo())%></label> <br>
Valutazione del rischio caratterizzato dalla presenza delle n.c. significative (motivazioni di fatto)<br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getValutazione_significativa()%></textarea><br>
Follow up delle n.c. significative (dispositivo del provvedim. amministr.): visto Reg. CE 882/04, L.241/90, DPR 320/54, DGRC n. 377/11 si dispone che <br>
<textarea rows="4" cols="50" class="textDim" readonly><%=OrgOperatore.getFollowUpSign()%></textarea><br>
(ex art.54 Reg. CE 882/04, avverso il provvedim. ammin. si può ricorrere al T.A.R. entro 60 giorni dalla data di notifica)<br><br>
<b>Non conformità <U>gravi</U> rilevate:</b><br> 
<textarea rows="4" cols="50"  class="textDim" readonly><%=OrgOperatore.getDescrizione_ncg()%></textarea><br>
Punteggio *: <label class="layout"><%= fixValore(OrgOperatore.getPunteggio_grave())%></label> <br><br>

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
   	[X] &nbsp;si è proceduto alla contestazione di illecit<label class="layout"><%= fixValore(value.get(0))%></label> 
amministrativ<label class="layout"><%= fixValore(value.get(1))%></label> 
con verbal<label class="layout"><%= fixValore(value.get(2))%></label> 
n.<label class="layout"><%= fixValore(OrgOperatore.getProcessiVerbali())%></label> 

<% } else { %>
	[] &nbsp;si è proceduto alla contestazione di illecit<label class="layout"><%= fixValore(value.get(0))%></label> 
amministrativ<label class="layout"><%= fixValore(value.get(1))%></label> 
con verbal<label class="layout"><%= fixValore(value.get(2))%></label> 
n.<label class="layout"><%= fixValore(OrgOperatore.getProcessiVerbali())%></label> 

<% } %>
<li>
<% if(OrgOperatore.isNcgCheck2()) { %>
   	[X] &nbsp;si procederà, se del caso e/o previo ulteriori accertamenti, alla notifica di illecit
<label class="layout"><%= fixValore(value.get(3))%></label> 
amministrativ<label class="layout"><%= fixValore(value.get(4))%></label> 
con att<label class="layout"><%= fixValore(value.get(5))%></label> a parte

<% } else { %>
[] &nbsp;si procederà, se del caso e/o previo ulteriori accertamenti, alla notifica di illecit
<label class="layout"><%= fixValore(value.get(3))%></label> 
amministrativ<label class="layout"><%= fixValore(value.get(4))%></label> 
con att<label class="layout"><%= fixValore(value.get(5))%></label> a parte

<% } %>
<li> 
<% if(OrgOperatore.isNcgCheck3()) { %>
   	[X] &nbsp;visto il Reg. CE 882/04, la L. 689/81 e la DGRC n. 377/11, in seguito alla rilevazione di illecito amministrativo, si è proceduto al sequestro amministrativo delle cose confiscabili 
elencate ne<label class="layout"><%= fixValore(value.get(6))%></label> 
verbal<label class="layout"><%= fixValore(value.get(7))%></label> n.
<label class="layout"><%= fixValore("")%></label>. 

<% } else { %>
[] &nbsp;visto il Reg. CE 882/04, la L. 689/81 e la DGRC n. 377/11, in seguito alla rilevazione di illecito amministrativo, si è proceduto al sequestro amministrativo delle cose confiscabili 
elencate ne<label class="layout"><%= fixValore(value.get(6))%></label> 
verbal<label class="layout"><%= fixValore(value.get(7))%></label> n.
<label class="layout"><%= fixValore("")%></label>. 

<% } %>
<li>
<% if(OrgOperatore.isNcgCheck4()) { %>
   	[X] &nbsp;si è proceduto al sequestro penale delle cose elencate ne
<label class="layout"><%= fixValore(value.get(8))%></label> verbal
<label class="layout"><%= fixValore(value.get(9))%></label> n.
<label class="layout"><%= fixValore("")%></label> 

<% } else { %>
[] &nbsp;si è proceduto al sequestro penale delle cose elencate ne
<label class="layout"><%= fixValore(value.get(8))%></label> verbal
<label class="layout"><%= fixValore(value.get(9))%></label> n.
<label class="layout"><%= fixValore("")%></label> 

<% } %>
<li>
<% if(OrgOperatore.isNcgCheck5()) { %>
   	[X] &nbsp;visto il Reg. CE 882/04,
 L. 241/90, L 283/62 e DGRC n. 377/11 si è proceduto al blocco/sequestro sanitario delle cose elencate ne
 <label class="layout"><%= fixValore(value.get(10))%></label> 
 verbal<label class="layout"><%= fixValore(value.get(11))%></label> 
 n.<label class="layout"><%= fixValore("")%></label>
 che forma parte integrante del presente provvedimento amministrativo  

<% } else { %>
[] &nbsp;visto il Reg. CE 882/04,
 L. 241/90, L 283/62 e DGRC n. 377/11 si è proceduto al blocco/sequestro sanitario delle cose elencate ne
 <label class="layout"><%= fixValore(value.get(10))%></label> 
 verbal<label class="layout"><%= fixValore(value.get(11))%></label> 
 n.<label class="layout"><%= fixValore("")%></label> 
 che forma parte integrante del presente provvedimento amministrativo  

<% } %>
<input type="hidden" name="gravi_13" id="gravi_13" value=""/>

<li>
<% if(OrgOperatore.isNcgCheck6()) { %>
   	[X] &nbsp; visto Reg. CE 882/04, L. 241/90, DPR 320/54, DGRC n. 377/11 si dispone che (dispositivo del provved. amministr.):<br>   
	<textarea rows="4" cols="50"  class="textDim"><%=OrgOperatore.getFollowUpGravi()%></textarea><br>		

<% } else { %>
[] &nbsp; visto Reg. CE 882/04, L. 241/90, DPR 320/54, DGRC n. 377/11 si dispone che (dispositivo del provved. amministr.): <br>  
	<textarea rows="4" cols="50"  class="textDim"><%=OrgOperatore.getFollowUpGravi()%></textarea><br>		  

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
Data chiusura ispez. <label class="layout"><%= fixValore(OrgOperatore.getGiorno_chiusura())%></label> /<label class="layout"><%= fixValore(OrgOperatore.getMese_chiusura())%></label> /<label class="layout"><%= fixValore(OrgOperatore.getAnno_chiusura())%></label>  Punteggio tot. delle n.c. <label class="layout"><%= fixValore(OrgOperatore.getPunteggio_ispezione())%></label> .
Fatto in n.<label class="layout"><%= fixValore(OrgUtente.getNumero_copie())%></label> copie originali, letto, confermato, sottoscritto e consegnato. (*) n. 1 punto per ogni n.c. formale, n. 7 punti per ogni n.c. significativa, n. 25 punti per ogni n.c. grave. Nelle ispezioni effettuate nella sorveglianza, i punteggi delle n.c. sono già contabilizzati nelle check list. N.C. = Non Conformità
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
