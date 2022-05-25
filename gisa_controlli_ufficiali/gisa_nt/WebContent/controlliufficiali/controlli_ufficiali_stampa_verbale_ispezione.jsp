<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="javascript">

function openMod5(orgId,idControllo,url){
	var res;
	var result;
	window.open('PrintReportVigilanza.do?command=ViewSchedaIspezione&idControllo='+idControllo+'&stabId='+orgId+'&orgId='+orgId+'&url='+url,'popupSelect',
			'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openMod5New(idControllo, tipoMod){
	var res;
	var result;
	window.open('Modello5.do?command=View&idControllo='+idControllo+'&tipoMod='+tipoMod,'popupSelect',
			'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openModB11(orgId,stabId, idControllo){
	var res;
	var result;
	
	/*Aggiornamento provvisorio legato alla nuova gestione della checklist B11*/
	//alert('La funzione risulta momentaneamente disabilitata per motivi di aggiornamento della chechlist B11(B26).');
		
		 window.open('PrintModulesHTML.do?command=AddSchedaAllegato&idControllo='+idControllo+'&idStabilimento='+stabId+'&orgId='+orgId+'&specie=-1','popupSelect',
			'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		
}

function openMod5(orgId, stabId, altId, idControllo,url,bozza){
	var res;
	var result;
	var param = "";
	
	if (altId>0)
		param+="&altId="+altId;
	if (orgId>0)
		param+="&orgId="+orgId;
	if (stabId>0)
		param+="&stabId="+stabId;
		
			 window.open('PrintReportVigilanza.do?command=ViewSchedaIspezione&idControllo='+idControllo+'&bozza='+bozza+'&url='+url+param,'popupSelect',
				'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
					
		
}
</script>


<%
String desc = User.getUserRecord().getDescrizione_gruppo_ruolo();
boolean isGisa = false;
if (desc==null || desc.contains("GISA"))
	isGisa = true;

int orgId = 1;
int stabId = -1;
int altId = -1;
boolean bozza = false;

if(request.getAttribute("bozza")== null || Boolean.parseBoolean((String)request.getAttribute("bozza")) ) 
	bozza = true;

orgId=TicketDetails.getOrgId();
if(TicketDetails.getIdStabilimento()>0)
	stabId=TicketDetails.getIdStabilimento();
if(TicketDetails.getAltId()>0)
	altId=TicketDetails.getAltId();
if(TicketDetails.getIdApiario()>0)
	orgId=TicketDetails.getIdApiario();
	

	int tipoControllo = TicketDetails.getTipoCampione();
	if( tipoControllo != 3 && (TicketDetails.getClosed()!= null || TicketDetails.isChiusura_attesa_esito()==true ) && isGisa){ 
%>

<div align="right" style="padding-left: 210px; margin-bottom: 45px">

<% 

Timestamp dataRev8 = Timestamp.valueOf("2020-02-03 00:00:00.00000000");

if (TicketDetails.getAssignedDate().after(dataRev8)) {%>
<a href="javascript:openMod5New('<%=TicketDetails.getId()%>', 'A');"><font size="3px" color="#006699"><b>Visualizza Modello 5/A (rev8) automatico e stampabile</font><b></a><br/>
<a href="javascript:openMod5New('<%=TicketDetails.getId()%>', 'B');"><font size="3px" color="#006699"><b>Visualizza Modello 5/B (rev8) automatico e stampabile</font><b></a>
<% } else { %>
<br/><br/>
<a href="javascript:openMod5('<%= orgId %>', '<%= stabId %>', '<%=altId %>', '<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=bozza%>');"><font size="3px" color="#006699"><b>Visualizza Modello 5 automatico e stampabile</font><b></a>
<% } %>
 
</div> 

<% } %>

