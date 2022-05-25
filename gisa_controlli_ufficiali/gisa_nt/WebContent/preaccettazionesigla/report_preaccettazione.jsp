<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="report_preac" class="org.json.JSONArray" scope="request"/>
  <%@ include file="../initPage.jsp" %>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ReportPreaccettazione.do?command=SearchForm">CERCA PREACCETTAZIONE</a> > REPORT GENERALE PREACCETTAZIONE
		</td>
	</tr>
</table>

<center>
<br><br>
<h3>REPORT GENERALE PREACCETTAZIONE</h3>

<table class="table details" style="border-collapse: collapse" border="1" width="80%" cellpadding="2">
	<tr>
		<th style="text-align:center; width:20%"><p>Asl</p></th>
		<th style="text-align:center"><p>Associati/non associati</th>
		<th style="text-align:center; width:25%"><p>Codici preaccettazione generati</p></th>
	</tr>
<%for(int i=0; i<report_preac.length(); i++){ %>
<tr class="row<%=i%2%>">
	<td align="center">
		<p>
		<%if(!report_preac.getJSONObject(i).getString("desc_asl").equalsIgnoreCase("null")){ %>
			<%=report_preac.getJSONObject(i).get("desc_asl") %>
		<%}else{ %>
			0
		<%} %>
		</p>
	</td>
	
	<td align="center">
		<table class="table details" style="border-collapse: collapse" width="100%" cellpadding="1">
			<tr>
				<td align="center" width="25%">ASSOCIATI</td>
				<td align="center" width="10%">
					<%=(Integer.parseInt(report_preac.getJSONObject(i).get("letti_da_laboratorio_campione").toString()) 
							+ Integer.parseInt(report_preac.getJSONObject(i).get("associati_non_letti").toString())) %>
				</td>
				<td>
					<table class="table details" style="border-collapse: collapse" border="1" width="100%" cellpadding="5">
						<tr>
							<td align="center">Letti</td>
							<td align="center" width="20%"><%=report_preac.getJSONObject(i).get("letti_da_laboratorio_campione") %></td>
						</tr>
						<tr>
							<td align="center">Non letti</td>
							<td align="center" width="20%"><%=report_preac.getJSONObject(i).get("associati_non_letti") %></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" width="25%">NON ASSOCIATI</td>
				<td  align="center" width="10%">
					<%=(Integer.parseInt(report_preac.getJSONObject(i).get("letti_da_laboratorio_solo_osa").toString()) 
							+ Integer.parseInt(report_preac.getJSONObject(i).get("non_associati_non_letti").toString())) %>
				</td>
				<td>
					<table class="table details" style="border-collapse: collapse" border="1" width="100%" cellpadding="5">
						<tr>
							<td align="center">Letti </td>
							<td align="center" width="20%"><%=report_preac.getJSONObject(i).get("letti_da_laboratorio_solo_osa") %></td>
						</tr>
						<tr>
							<td align="center">Non letti</td>
							<td align="center" width="20%"><%=report_preac.getJSONObject(i).get("non_associati_non_letti") %></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>		
	</td>
	
	<td align="center">
		<p><b><%=report_preac.getJSONObject(i).get("associati_a_osa") %></b></p>
	</td>
</tr>
<%} %>
</table>
<br><br>
</center> 