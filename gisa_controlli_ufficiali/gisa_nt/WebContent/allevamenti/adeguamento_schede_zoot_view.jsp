<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="AzDetails" class="org.aspcf.modules.controlliufficiali.base.AziendeZootFields" scope="request"/>
 
<!--  Allegato 1 -->	
<% if(OrgDetails.getCodice_specie() == 131 ) { %>
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Adeguamento Schede zootecniche: Galline Ovaiole</dhv:label></strong>
		</th>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">N. ovaiole: capacità massima</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getCap_max_animali()%></td><td>/<%=AzDetails.getNum_animali_isp()%> ispezionati</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale capannoni</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_capannoni()%></td><td>/<%=AzDetails.getNum_tot_capannoni_isp()%> ispezionati</td>
			</tr>
			</tr>
		</table>
		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Metodo di allevamento
		<td>
		<table class="noborder">
			<tr>
			
				<td><%=(AzDetails.getMetodo_allevamento() != null && (!AzDetails.getMetodo_allevamento().equals("null")) && (!AzDetails.getMetodo_allevamento().equals(""))) ? AzDetails.getMetodo_allevamento() : "N.D"%></td><br> 
				<td>N.capannoni con gabbie modificate: <%=AzDetails.getNum_capannoni_con_gabbie()%>
				<br>N.capannoni con gabbie NON modificate: <%=AzDetails.getNum_capannoni_non_gabbie()%></td>
			</tr>
		</table>
		</td>
	</tr>
	
	</table>
<br>
<br>	
<% } else if(OrgDetails.getCodice_specie() == 122) { %>	

<!--  Allegato 2 -->
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Adeguamento Schede zootecniche: Suini</dhv:label></strong>
		</th>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale capannoni</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_capannoni()%></td><td>/<%=AzDetails.getNum_tot_capannoni_isp()%> ispezionati</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale box
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_box()%></td><td>/<%=AzDetails.getNum_tot_box_isp()%> ispezionati</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<%-- <tr class="containerBody">
		<td nowrap class="formLabel">N. totale box attivi<br> all'atto dell'ispezione
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_box_isp()%></td>
			</tr>
		</table>
		</td>
	</tr>--%>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">Specie
		<td>
		<table class="noborder">
			<tr>
				<td>Verri:</td> <td>capacità massima: <%=AzDetails.getCap_max_verri()%></td><td>/<%=AzDetails.getNum_verri_isp()%> presenti all'atto dell'ispezione </td>
			</tr>
			<tr>	
				<td>Scrofe e scrofette: </td> <td>capacità massima: <%=AzDetails.getCap_max_scrofe_scrofette()%></td><td>/<%=AzDetails.getNum_scrofe_scrofette_isp()%> presenti all'atto dell'ispezione </td>
			</tr>
			<tr>
				<td>Lattonzoli: </td> <td>capacità massima: <%=AzDetails.getCap_max_lattonzoli()%></td><td>/<%=AzDetails.getNum_lattonzoli_isp()%> presenti all'atto dell'ispezione </td>
			</tr>
			<tr>
				<td>Suinetti:</td> <td>capacità massima: <%=AzDetails.getCap_max_suinetti()%></td><td>/<%=AzDetails.getNum_suinetti_isp()%> presenti all'atto dell'ispezione </td>
			</tr>
			<tr>
				<td>Suini al grasso </td> <td>capacità massima: <%=AzDetails.getCap_max_suini_al_grasso()%></td><td>/<%=AzDetails.getNum_suini_al_grasso_isp()%> presenti all'atto dell'ispezione </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<% } else if(OrgDetails.getCodice_specie() == 1211 || OrgDetails.getCodice_specie() == 121) { %>
<br>
<br>
<!--  Allegato 3 -->
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Adeguamento Schede zootecniche: Vitelli </dhv:label></strong>
		</th>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale dei vitelli presenti</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_animali_presenti()%></td>
			</tr>
			
		</table>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale dei vitelli di età inferiore a 8 settimane</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_vitelli_inf_8_settimane()%></td>
			</tr>
			
		</table>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Capacità massima vitelli</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getCap_max_animali()%></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br>
<br>
<% } else if(OrgDetails.getCodice_specie() == 1461 || OrgDetails.getCodice_specie()==146 ) {%>
<!--  Allegato 5 -->
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Adeguamento Schede zootecniche: Polli da carne</dhv:label></strong>
		</th>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale capannoni</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_capannoni()%></td><td>/<%=AzDetails.getNum_tot_capannoni_isp()%> ispezionati</td> 
			</tr>
		</table>
		</td>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">N. totale animali presenti</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getNum_tot_animali_presenti()%></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Capacità massima di allevamento autorizzata<br>
		dalla ASL competente per il territorio</td>
		<td>
		<table class="noborder">
			<tr>
				<td><%=AzDetails.getCap_max_animali()%> kg/mq</td>	
			</tr>
		</table>
		</td>
	</tr>
	
</table>
<% } %>