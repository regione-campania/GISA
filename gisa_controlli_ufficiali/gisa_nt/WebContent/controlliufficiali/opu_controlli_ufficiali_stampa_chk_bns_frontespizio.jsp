<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="hashSchede" class="java.util.HashMap" scope="request"/>  

<script language="javascript"> function gestisciLinkModifica(){
	
	var dataCU = document.getElementById("dataCU").value;
	var anno = dataCU.substring(0,4);
	var mese = dataCU.substring(5,7);
	var giorno = dataCU.substring(8, 10);
		
	var d1 = new Date(anno, mese, giorno);
	var d2 = new Date(2014, 7, 1);
	 if ((d1 < d2) || (<%=TicketDetails.getClosed()!=null%>) ) 
		{
	   chiudiTutto();
		}
	}
	
function chiudiTutto(){
	   var element = document.getElementById('bottoneModifica');
	        element.style.display = 'none';
		}
</script>


<% int specieNew = -2;

if (specieAllev==131 || specieAllev == 122 || specieAllev == 121)
	specieNew = specieAllev;
else
	specieNew = -2;
System.out.println("AAAAA sp"+specieAllev+ " "+specieNew);
%>



<% if (specieAllev!=121 && specieAllev!=146) {%>

<div id="bottoneModifica" align="right" style="padding-left: 210px; margin-top: 10px;" >
<a href="javascript:openModificaChk_bns('<%=TicketDetails.getId()%>','<%=specieNew%>');" <%if (hashSchede.get(specieAllev)!=null && Boolean.FALSE.equals(hashSchede.get(specieAllev))) { %> style="display:none" <%} %>> 
		<font size="2px" color="#FF0000"> 
		<b>Compila Frontespizio</b></font></a>
</div>

<%} else  if (specieAllev!=146) { %>

<div id="bottoneModifica" align="right" style="padding-left: 210px; margin-top: 10px;">
<a href="javascript:openModificaChk_bns('<%=TicketDetails.getId()%>','-2');" <%if (hashSchede.get(-2)!=null && Boolean.FALSE.equals(hashSchede.get(-2))) { %> style="visibility:hidden" <%} %>> 
		<font size="2px" color="#FF0000"> 
		<b>Compila Frontespizio per Bovini</b></font></a>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
<a href="javascript:openModificaChk_bns('<%=TicketDetails.getId()%>','1211');" <%if (hashSchede.get(1211)!=null && Boolean.FALSE.equals(hashSchede.get(1211))) { %> style="visibility:hidden" <%} %>> 
		<font size="2px" color="#FF0000"> 
		<b>Compila Frontespizio per Vitelli</b></font></a>
</div>

<%} else  { %>

<div id="bottoneModifica" align="right" style="padding-left: 210px; margin-top: 10px;">
<a href="javascript:openModificaChk_bns('<%=TicketDetails.getId()%>','-2');" <%if (hashSchede.get(-2)!=null && Boolean.FALSE.equals(hashSchede.get(-2))) { %> style="visibility:hidden" <%} %>> 
		<font size="2px" color="#FF0000"> 
		<b>Compila Frontespizio per Avicoli Misti</b></font></a>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
<a href="javascript:openModificaChk_bns('<%=TicketDetails.getId()%>','1461');" <%if (hashSchede.get(1461)!=null && Boolean.FALSE.equals(hashSchede.get(1461))) { %> style="visibility:hidden" <%} %>> 
		<font size="2px" color="#FF0000"> 
		<b>Compila Frontespizio per Polli da carne</b></font></a>
</div>


<%} %>


<script>gestisciLinkModifica()</script>