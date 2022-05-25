<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="javascript">
function openPopupModules(orgId, ticketId, idCU, url,tipo){
	var res;
	var result;
		window.open('PrintModulesHTML.do?command=ViewModules&orgId='+orgId+'&ticketId='+ticketId+'&idCU='+idCU+'&url='+url+'&tipo='+tipo,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	
} 

</script>
<div align="right" style="padding-left: 210px; margin-bottom: 45px">

<a href="javascript:openPopupModules('<%= OrgDetails.getOrgId() %>', '<%= TicketDetails.getId() %>', '<%= TicketDetails.getIdControlloUfficiale() %>', '<%= TicketDetails.getURlDettaglio() %>','6');">
	<font size="3px" color="#006699" style="font-weight: bold;">
		Stampa Mod.6 Verbale Superficie Ambientale</font> 
</a>
<br>			
</div>

			

	


