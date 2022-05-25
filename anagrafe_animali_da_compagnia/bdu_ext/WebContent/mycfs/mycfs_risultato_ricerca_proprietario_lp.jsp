<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- Trails --%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></script>
        

<%@ include file="../initPage.jsp"%>
        
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
			<a href="MyCFS.do?command=ToRicercaProprietarioLP">Ricerca Proprietario</a> >
			<dhv:label name="">Risultati Ricerca Proprietario</dhv:label>
		</td>
	</tr>
</table>

<jsp:useBean id="thisAnimale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />

<table class="details" cellspacing="4" cellpadding="4" border="0" width="40%">
<col width="20%">
	<tr>
		<th colspan="2" >
			<strong>Informazioni Proprietario</strong>
		</th>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			Proprietario
		</td>
		<td>
			<%=((thisAnimale.getProprietario()!=null)?(thisAnimale.getProprietario().getRagioneSociale()):(" N.D."))%>
		</td>
	</tr>
	
	<tr>
		<th colspan="2" >
			<strong>Informazioni Detentore</strong>
		</th>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			Detentore
		</td>
		<td>
			<%=((thisAnimale.getDetentore()!=null)?(thisAnimale.getDetentore().getRagioneSociale()):(" N.D."))%>
		</td>
	</tr>
	
	<tr>
		<th colspan="2" >
			<strong>Informazioni Animale</strong>
		</th>
	</tr>
	
	<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Specie</dhv:label></td>
				<td><%=specieList.getSelectedValue(thisAnimale.getIdSpecie())%></td>
	</tr>
	
	<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Registrazione</dhv:label>
				</td>
	<td><%=toDateString(thisAnimale.getDataRegistrazione())%></td>
	</tr>
		
<%
	if (thisAnimale.getIdAslRiferimento() > 0) 
	{
%>
		<tr class="containerBody">
			<td class="formLabel" valign="top"><dhv:label name="">Asl di Riferimento</dhv:label></td>
			<td><%=SiteList.getSelectedValue(thisAnimale.getIdAslRiferimento())%></td>
		</tr>
<%
	}
%>


<%
	if (thisAnimale.getIdRazza() > 0) 
	{
%>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Razza</dhv:label></td>
					<td><%=toHtml(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%>
						&nbsp;</td>
				</tr>
<%
	}
%>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Sesso</dhv:label></td>
				<td><%=(thisAnimale.getSesso() != null && !("").equals(thisAnimale.getSesso())) ? toHtml(thisAnimale.getSesso()) : "--"%></td>
			</tr>

<%
	if (thisAnimale.getIdTaglia() > 0) 
	{
%>

				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
			
					<td><%=toHtml(tagliaList.getSelectedValue(thisAnimale.getIdTaglia()))%>
						&nbsp;</td>
										</tr>
					<%	
					}
					%>

				
				<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data di nascita</dhv:label>
				</td>
				<td><%=toHtmlValue(toDateasString(thisAnimale.getDataNascita()))%>&nbsp;</td>
			</tr>
			
			<tr class="containerBody">

				<td class="formLabel"><dhv:label name="">Mantello</dhv:label></td>
				<td><%=mantelloList.getSelectedValue(thisAnimale.getIdTipoMantello())%>&nbsp;</td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
				<td><%=toHtml(thisAnimale.getMicrochip())%></td>
			</tr>

	<tr class="containerBody">
		<td class="formLabel">
			Stato
		</td>
		<td>
			<%=statoList.getSelectedValue(thisAnimale.getStato())%>
		</td>
	</tr>
	
	
</table>

