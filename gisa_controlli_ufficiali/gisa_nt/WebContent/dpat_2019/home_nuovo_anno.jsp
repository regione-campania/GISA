<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIstanza"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.Calendar, java.util.Iterator"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.utils.web.CustomLookupElement"%>


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="siteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="anniList" class="java.util.ArrayList" scope="request" />




<%
int mese = Calendar.getInstance().get(Calendar.MONTH);
if (Calendar.getInstance().get(Calendar.MONTH)>=11 || mese<=3)
{
	int anno =Calendar.getInstance().get(Calendar.YEAR) ;
	if (mese>=11)
		 anno = Calendar.getInstance().get(Calendar.YEAR)+1;
	

%>




<form action="DpatSDC2019Config.do?command=AddModify" method="post">
<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="DpatSDC2019Config.do">2015</a>  
			</td>
		</tr>
	</table>

	ASL
	<%

	if (User.getSiteId()>0)
	{
	%>
	<%=siteList.getSelectedValue(User.getSiteId())%>
	<input type ="hidden" name = "idAsl" id="idAsl" value="<%=User.getSiteId()%>">
	<%} else{
		
	
		siteList.setRequired(true);
	%>
	<%=siteList.getHtmlSelect("idAsl", User.getSiteId())%>
	<%} %>
	<br><br>
	SELEZIONARE L'ANNO
	<select id="ANNO" onchange="getAreaStruttureComplesse()">
		
					<option value="<%=anno%>"><%="CONFIGURA STRUMENTO CALCOLO "+anno +""%></option>
		
	</select>
	<br><br>
	<br><br>
	
	combo_area
	<input type="submit" style="background-color:#FF4D00; font-weight: bold;"  value="VAI"/>

	</form>
	<%}%>