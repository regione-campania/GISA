<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<script language="javascript">

function openChk_bns(orgId,idControllo,url,specie){
	var res;
	var result;
		window.open('PrintModulesHTML.do?command=AddSchedaAllegato&idControllo='+idControllo+'&orgId='+orgId+'&url='+url+'&specie='+specie,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}


</script>

<script language="javascript">

function openModificaChk_bns(idControllo, specie){
	//var mes = document.getElementById('messaggio_pnaa').value;
	var mes = '';
	if( mes != '' && mes != 'null'){
		alert(mes);
	}else{
		  window.open('CheckListAllevamenti.do?command=ModificaListaRiscontro&idControllo='+idControllo+'&specie='+specie,'popupSelect',
				'height=600px,width=800px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
  }				
}

</script>

<script>

function openInvioModB11(idControllo){
	var res;
	var result;
		window.open('Allevamenti.do?command=SendCUB11&tipo=puntuale&idControllo='+idControllo,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openGuidaB11(){
	var res;
	var result;
		window.open('man/#inviob26','popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<input type="hidden" id="dataCU" name="dataCU" value="<%=TicketDetails.getAssignedDate()%>"/>

<% 


String[] codiciInterniBenessere = {"982", "983"} ;
String codiciInterniBenessereCondizionalita = "1483" ;
	String codiceInterno = "";
	int specieAllev = OrgDetails.getSpecieA();
	boolean flagCondiz = false ;
	boolean trovato = false;
	
	System.out.println("AAAAAAAAAA "+specieAllev);
	if (!TicketDetails.isClosed_nolista())
		
	for(Piano p :TicketDetails.getPianoMonitoraggio()) {
		codiceInterno= p.getCodice_interno();
		flagCondiz = p.isFlagCondizionalita();
		
		System.out.println("AAAAAAAAABBBBBBBBBBBBBBBBA "+codiceInterno);

	if (Arrays.asList(codiciInterniBenessere).contains(codiceInterno)) {	
		
    	trovato = true;
		if(specieAllev == 131){
%>
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','131');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Galline Ovaiole"/></a>
		
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','1461');"> 	
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Polli da carne"/></a></a>
		
</div>
<% } else if(specieAllev == 122) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','122');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Suini"/></a>
		</div>	
		
<% // }  else if(specieAllev == 121 && OrgDetails.getOrientamentoProd().contains("LATTE")) { %>
<%-- <div align="right" style="padding-left: 210px; margin-top: 35px">
	<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','122');"> 
	<font size="2px" color="#FF9933"> 
	<b>Compila/Visualizza Lista di riscontro per  3 Vitelli</font><b></a>
</div>	
 --%>
<% } else if(specieAllev == 139) { %>

<div align="right" style="padding-left: 210px; margin-top: 35px">
	<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','139');"> 
	
	<input type="button" value="Compila/Visualizza Lista di riscontro per Fagiani"/></a>
	</div>
<% } else if(specieAllev == 125) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','125');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Caprini"/></a>
		
		
	</div>	
<% } else if(specieAllev == 146) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','146');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Avicoli Misti"/></a>
		
		
		
		
<%-- 			<a name="modificaLista" href="javascript:openModificaChk_bns('<%=TicketDetails.getId()%>','-2');">  --%>
<!-- 		<font size="1px" color="#FF0000">  -->
<!-- 		<b>Completa Lista di riscontro per Polli da carne</font><b></a><br> -->
					
	</div>
		
	</div>	
<% } else if(specieAllev == 128) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','128');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Conigli"/></a>
		
	</div>
<% } else if(specieAllev == 129) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','121');"> 
	
		<input type="button" value="Compila/Visualizza Lista di riscontro per Bovini/Bufalini"/></a>
			
	</div>	
<% } else if(specieAllev == 121) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','121');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Bovini/Bufalini"/></a>
	
	<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','1211');"> 
		<input type="button" value="Compila/Visualizza Lista di riscontro per Vitelli"/></a>
		
		
	</div>
<% } else if(specieAllev == 126) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','126');"> 
		<input type="button" value="Compila/Visualizza Lista di riscontro per Cavalli"/></a>	
			
	</div>	
<% } else if(specieAllev == 124) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','124');"> 
		<input type="button" value="Compila/Visualizza Lista di riscontro per Ovini"/></a>	
			
	</div>	
<% } else if(specieAllev == 134) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','134');"> 
		<input type="button" value="Compila/Visualizza Lista di riscontro per Quaglie"/></a>
			
	</div>	
	<% } else if(specieAllev == 149) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','149');"> 
		<input type="button" value="Compila/Visualizza Lista di riscontro per Asini"/></a>
			
	</div>	
<% } else if(specieAllev == 160) { %>

<div align="right" style="padding-left: 210px; margin-top: 35px">
	<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','160');"> 
	<input type="button" value="Compila/Visualizza Lista di riscontro per Pesci"/></a>	
	</div>
<% } %> 


<%-- <%@ include file="controlli_ufficiali_stampa_chk_bns_frontespizio.jsp" %> --%>

<%}
   if (trovato)
		   break;
 
	}

%>

<% for(Piano p :TicketDetails.getPianoMonitoraggio()) {
		codiceInterno= p.getCodice_interno();
		flagCondiz = p.isFlagCondizionalita();

   if (codiceInterno!=null && codiciInterniBenessereCondizionalita.contains(codiceInterno) && !TicketDetails.isClosed_nolista() && flagCondiz==true && TicketDetails.getTipo_ispezione_condizionalita().keySet().contains(Ticket.TIPO_CONDIZIONALISTA_ATTO_B11))
{
%>
<div align="rigth" style="padding-left: 210px; margin-bottom: 45px">
<a href="javascript:openModB11('<%= TicketDetails.getOrgId() %>', '-1', '<%=TicketDetails.getId()%>');"> 
		<font size="3px" color="#006699"><b>Check-list controllo condizionalita'<br>
	Atto B11-Sicurezza Alimentare</font><b></a>
		</div>
	
	<div align="rigth" style="padding-left: 210px; margin-bottom: 45px">
	<a href="#" onClick="openGuidaB11()">Guida compilazione Atto B11</a>
	</div>
	
	<dhv:permission name="allevamenti-allevamenti-ba-view">
	<% if (TicketDetails.getClosed()!=null && (TicketDetails.getEsito_import_b11()==null || !"OK".equalsIgnoreCase(TicketDetails.getEsito_import_b11()))) { %>
	<div align="center">
	<%if (org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente")!=null && org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale")){ %>
	<input type="button" value="INVIA AL MINISTERO (CONDIZIONALITA' ATTO B11)" onClick="openInvioModB11('<%=TicketDetails.getId()%>')"/><br/><br/>
	<%} else {%>
	<font color="red">Invio non possibile in ambiente non di esercizio</font>
	<%} %>
	</div>
	<%} %>
	</dhv:permission>
		
		
<%	
} }%>








<jsp:useBean id="ErroreAccountVigilanza" class="java.lang.String" scope="request"/>
<script>
<% if (ErroreAccountVigilanza!=null && !ErroreAccountVigilanza.equals("")){ %>
	alert("<%=ErroreAccountVigilanza%>");
	<% } %>
</script>
