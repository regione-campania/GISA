<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="java.util.Iterator"%>
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="StabilimentoOpu" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<script type="text/javascript" src="suap/javascriptsuap/suap_linee.js"></script>


<input type="hidden" value="false" id="validatelp">

<fieldset id="operazione">
	<h1>
		<center>SOSPENSIONE STABILIMENTO O DI UNA O PIU' LINEE DI ATTIVITA'</center>
		<center> Inserire la data di Sospensione
		<input type="text" size="15" name="dataInizioSospensione" required="required" id="dataInizioSospensione" placeholder="dd/MM/YYYY"> 
	<script> $(function() {	$('#dataInizioSospensione').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0,  showOnFocus: false, showTrigger: '#calImg',  onClose: controlloDataCessazione }); }); </script>
	<div style="display: none;"> 
    &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger">
    </div> 
	</h1>
</fieldset>

<br>
<br>
<fieldset id="secondarie">
	<legend>SOSPENSIONE ATTIVITA'</legend>
	
	<table style="width: 100%;">
	<tr><td>SI INTENDE SOSPENDERE L'INTERO STABILIMENTO? &nbsp; &nbsp; &nbsp; &nbsp;<input type="checkbox" id="sospensioneStabilimento" name="sospensioneStabilimento" onClick="gestisciSospensione(this)"/></td>
	<td>
	<div id="divSospensione" style="display:none">
	</div>
	
	</td>
	</tr>
	</table>
	<br>
	<br>
	<br>
	
	<%
	if(StabilimentoOpu.getIdStabilimento() <=0){
	%>
	<table style="width: 100%;" id="tableQualeLinea">
		<tr>
			<td width="50%" align="left">INDICARE LE LINEE DI ATTIVITA CHE SI INTENDE SOSPENDERE</td>
			<td align="left"><input type="button" value="Seleziona"
				onclick="aggiungiRiferimentoTabella(<%=newStabilimento.getTipoInserimentoScia()%>)"></td>
		</tr>
	</table>
	
	<%}
	else
	{
		%>
		<table style="width: 100%;" id="tableQualeLinea">
		<tr>
			<td width="50%" align="left" colspan="2">INDICARE LE LINEE DI ATTIVITA CHE SI INTENDE SOSPENDERE</td>
			
		</tr>
	</table>
	<br><br>
	
	<%
	Iterator<LineaProduttiva> itLp = StabilimentoOpu.getListaLineeProduttive().iterator();
	int indice = 1 ;
	while (itLp.hasNext())
	{
		LineaProduttiva lp = itLp.next();
		if(lp.getStato()!=Stabilimento.STATO_CESSATO && lp.getStato()!=Stabilimento.STATO_SOSPESO)
		{
	%>
	
	<table id="attsecondarie<%=indice %>"  style="width: 100%;">
	<tbody>
	<tr>
	<td  align="left" colspan="2">
<!-- 	<div style=""> -->

<%
String[] livelliLinea = lp.getDescrizione_linea_attivita().split("->");
for(String descrioneLiello : livelliLinea)
{
%>
	<p style="color:null"><%=descrioneLiello %></p>
<%	
}
%>
<!-- 	</div> -->
	</td>
	<td id="validaattsecondarie<%=indice %>" aligh="right">
<img width="50px" height="50px" src="css/suap/images/ok3.png" style="display: none" id="img<%=indice%>">
</td>
<td><input type="checkbox" name="idLineaProduttiva" value="<%=lp.getId()%>" onclick="checkLinea(<%=indice%>,this);"></td>
	</tr>
	</tbody>
	</table>
	
	<%
	indice ++ ;
	} 
		}%>
	
		
		<%
	}
	
	%>
	<br> <br>
</fieldset>