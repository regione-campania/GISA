<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<script language="javascript">

function openChk_bns(idStabilimento,idControllo,url,specie){
	var res;
	var result;
		window.open('PrintModulesHTML.do?command=AddSchedaAllegato&idControllo='+idControllo+'&idStabilimento='+idStabilimento+'&url='+url+'&specie='+specie,'popupSelect',
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

<input type="hidden" id="dataCU" name="dataCU" value="<%=TicketDetails.getAssignedDate()%>"/>

<% 
String specie = (String) request.getAttribute("specieAllev");
int specieAllev = Integer.parseInt(specie);


String[] codiciInterniBenessere = {"982", "983"} ;
String codiciInterniBenessereCondizionalita = "1483" ;
	String codiceInterno = "";
	boolean flagCondiz = false ;
	boolean trovato = false;
	
	if (!TicketDetails.isClosed_nolista())
		
	for(Piano p :TicketDetails.getPianoMonitoraggio()) {
		codiceInterno= p.getCodice_interno();
		flagCondiz = p.isFlagCondizionalita();
		System.out.println("cod I "+codiceInterno);
		System.out.println("flag C "+flagCondiz);
		System.out.println("specie A "+specieAllev);
		if (Arrays.asList(codiciInterniBenessere).contains(codiceInterno)) {	
	trovato = true;
		if(specieAllev == 131){
%>
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getIdStabilimento() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','131');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Galline Ovaiole"/></a>
		
</div>
<% } else if(specieAllev == 122) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getIdStabilimento() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','122');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Suini"/></a>
		</div>	

<% } else if(specieAllev == 121) { %>
	
	<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getIdStabilimento() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','121');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Bovini"/></a>
		
		
	
	<a href="javascript:openChk_bns('<%= TicketDetails.getIdStabilimento() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','1211');"> 
		<input type="button" value="Compila/Visualizza Lista di riscontro per Vitelli"/></a>
		
		
	</div> 
	<%} else { %>

<div align="right" style="padding-left: 210px; margin-top: 35px">
		<a href="javascript:openChk_bns('<%= TicketDetails.getIdStabilimento() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','-2');"> 
		
		<input type="button" value="Compila/Visualizza Lista di riscontro per Altre Specie"/></a>
		</div>	

<% } %> 


<%@ include file="opu_controlli_ufficiali_stampa_chk_bns_frontespizio.jsp" %>

<%}
    if (trovato)
		   break;  
 
	}

%>


<% for(Piano p :TicketDetails.getPianoMonitoraggio()) {
		codiceInterno= p.getCodice_interno();
		flagCondiz = p.isFlagCondizionalita();
		System.out.println("cod I2 "+codiceInterno);
		System.out.println("flag C2 "+flagCondiz);
		System.out.println("specie A2 "+specieAllev);
   if (codiciInterniBenessereCondizionalita.contains(codiceInterno) && !TicketDetails.isClosed_nolista() && flagCondiz==true && TicketDetails.getTipo_ispezione_condizionalita().keySet().contains(Ticket.TIPO_CONDIZIONALISTA_ATTO_B11))
{
%>
<div align="rigth" style="padding-left: 210px; margin-bottom: 45px">
<a href="javascript:openModB11('-1', '<%= TicketDetails.getIdStabilimento() %>','<%=TicketDetails.getId()%>');"> 
		<font size="3px" color="#006699"><b>Check-list controllo condizionalita'<br>
	Atto B11-Sicurezza Alimentare</font><b></a>
		</div>
<%	
} }%>


<jsp:useBean id="ErroreAccountVigilanza" class="java.lang.String" scope="request"/>
<script>
<% if (ErroreAccountVigilanza!=null && !ErroreAccountVigilanza.equals("")){ %>
	alert("<%=ErroreAccountVigilanza%>");
	<% } %>
</script>
