<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.base.StabilimentoList"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="fissa" class="java.lang.String" scope="request" />
<jsp:useBean id="StabilimentoOpu" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="operazioneScelta" class="java.lang.String" scope="request" />

<script type="text/javascript" src="suap/javascriptsuap/suap_linee.js"></script>
<input type="hidden"  id="validatelp" <% if(StabilimentoOpu.getIdStabilimento()>0 && !"new".equalsIgnoreCase(operazioneScelta)) { %>value="true" <%}else{%> value="false" <%} %>>
<input type="hidden"  id="validatelpcheck"  value="false">
<fieldset id="operazione">
	<h1>
		<center>ATTIVITA' PRODUTTIVE</center>

		<div id="divDataInizioStabilimento" style="display:none">
		<center>
		<%="Lo stabilimento e' a carattere TEMPORANEO. Tutte le linee di attività compilate in questa sezione saranno completate con questi dati:<br/>".toUpperCase() %>
		<b>Data Inizio:</b> <input type="text" readonly id="dataInizioLinee" name="dataInizioLinee"/>
		<b>Data Fine:</b>   <input type="text" readonly id="dataFineLinee" name="dataFineLinee"/>
		</center>
		</div>
		
	</h1>
</fieldset>



<%=(operazioneScelta.equalsIgnoreCase("cambiotitolarita")) ? "<h3>ATTIVITA' PRODUTTIVE NON NECESSARIE</h3>" : "" %>

<%
if(StabilimentoOpu.getIdStabilimento()<=0)
{
%>		
<fieldset id="setAttivitaPrincipale" <%=(operazioneScelta.equalsIgnoreCase("cambiotitolarita")) ? "style='display:none'" : "" %>>
	<legend ><b>INDICARE ALMENO UNA LINEA DI ATTIVITA'</b></legend>
	<table id="attprincipale" style="width: 100%;">
	</table>

</fieldset>
<br>
<br>

<%
if (fissa.equals("true") || fissa == null)
{
%>
<fieldset id="secondarie" <%=(operazioneScelta.equalsIgnoreCase("cambiotitolarita")) ? "style='display:none'" : "" %>>
	<legend><b>INDICARE EVENTUALI LINEE DI ATTIVITA' AGGIUNTIVE</b></legend>
	<table style="width: 100%;">
		<tr>
			
			<td align="left"><input type="button" value="Aggiungi ALTRE ATTIVITA' PRODUTTIVE"
				onclick="if(document.getElementById('tipo_impresa')!=null && document.getElementById('tipo_impresa').value=='12') {alert('Impossibile aggiungere ulteriori linee per questo tipo impresa.'); return false; };  aggiungiRiferimentoTabella(<%=newStabilimento.getTipoInserimentoScia()%>)"></td>
		</tr>
	</table>
	<!-- <br> <br> -->
</fieldset>
<%}%>
<%}
else
{
%>
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
	<td width="100%" align="left" colspan="2">
	<div style="width:100%;">

<%
String[] livelliLinea = lp.getDescrizione_linea_attivita().split("->");
for(String descrioneLiello : livelliLinea)
{
%>
	<p style="color:null"><%=descrioneLiello %></p>
<%	
}
%>
	</div>
	</td>
	<td id="validaattsecondarie<%=indice %>" aligh="right">
<img width="50px" height="50px" src="css/suap/images/ok3.png"  id="img<%=indice%>">
</td>
<td><input type="hidden" name="idLineaProduttiva" value="<%=lp.getId()%>"></td>
	</tr>
	</tbody>
	</table>
	
	<%
	indice ++ ;
	} 
		}%>
		
		<script>


</script>	

<%	

}
%>
