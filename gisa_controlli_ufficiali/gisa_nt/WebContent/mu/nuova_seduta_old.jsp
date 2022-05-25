<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table class="details" layout="fixed" width="50%">
<col width="180px">
<th colspan="2">Creazione nuova seduta</th> 
<tr><td class="formLabel">Data seduta</td> <td> <input readonly type="text" name="dataSeduta" id="dataSeduta=" size="10" value="" />&nbsp;  
    <a href="#" onClick="cal19.select(document.forms[0].dataSeduta,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataSeduta);return false;"><img src="images/delete.gif" align="absmiddle"/></a>    
		</td></tr>

</table>
<br/><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr> <th colspan="5"> <center>Lista partite</center> </th></tr>
  	<tr>
			<th><strong>Numero</strong></th>
			<th><strong>Codice azienda provenienza</strong></th>
			<th><strong>Mod. 4</strong></th>
			<th><strong>Data di arrivo al macello</strong></th>
			<th><strong>Capi</strong></th>
		</tr>	
			<%
	
	if (listaPartite.size()>0){
		for (int i=0;i<listaPartite.size(); i++){
			PartitaUnivoca partita = (PartitaUnivoca) listaPartite.get(i); %>
	
				
			<tr>
			<td><a href="javascript:popURL('MacellazioneUnica.do?command=DettaglioPartita&idPartita=<%=partita.getId()%>&popup=true', null, '800px', null, 'yes', 'yes')"><%=partita.getNumeroPartita() %></a></td>
			<td><%=partita.getCodiceAziendaProvenienza() %></td>
			<td><%=partita.getMod4() %></td>
			<td><%=partita.getDataArrivoMacello() %></td>
			<td><input type="button" onClick="mostraCapi('<%=partita.getId()%>')" value="MOSTRA/NASCONDI"/>
			</tr>

<tr><td colspan="4">
<table style="display:none" id="tabella_<%=partita.getId()%>">
<tr><th>SPECIE</th> <th>Matricola</th> <th>Aggiungi singolo capo</th> <th>Aggiungi tutti i capi di questa specie</th></tr>			
	<% for (int j = 0; j< partita.getListaCapi().size(); j++){
	CapoUnivoco capo = (CapoUnivoco) partita.getListaCapi().get(j);
	%>
<tr class="rowmu<%=capo.getSpecieCapo()%>"> <td><%=specieList.getSelectedValue(capo.getSpecieCapo()) %></td> 
<td><%=capo.getMatricola() %> &nbsp;</td>
<td><input type="checkbox" id="capo_<%=capo.getId() %>" name="capo_<%=capo.getIdPartita() %>_<%=capo.getSpecieCapo()%>"/></td>
<td>
<%if (j==0 || (int) partita.getListaCapi().get(j-1).getSpecieCapo() != capo.getSpecieCapo()){  %>
<input type="checkbox" id="specie_<%=capo.getSpecieCapo() %>" specie="<%=capo.getSpecieCapo() %>" name="specie_<%=capo.getSpecieCapo() %>" onClick="gestisciAggiuntaTotale(this, '<%=capo.getSpecieCapo()%>', '<%=capo.getIdPartita()%>')"/>
<% } %>
</td>
</tr>
<% 
}%>
</table></td></tr>
<tr><td></td></tr>
<% }
%>
	
	
	<% } else {%>
		<tr><td colspan="8"> Non sono state trovate partite. &nbsp; </td></tr>
		
		<% } %>
		
		</table>