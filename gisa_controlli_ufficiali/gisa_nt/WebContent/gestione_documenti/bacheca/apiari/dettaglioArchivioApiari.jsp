<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../../initPage.jsp"%>
<jsp:useBean id="ArchivioDetail" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="storeId" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>



  <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null || timestring.equals("null"))
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto;
	  return toRet;
	  
  }%>
  

<table class="trails" cellspacing="0">
<tbody><tr>
<td>
<a href="GestioneBacheca.do?command=ListaApiari">Bacheca</a> &gt;
<a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>">Contenuto archivio</a> &gt;
Scheda archivio documenti
</td>
</tr>
</tbody></table>

<!-- BOX MESSAGGIO -->
<%if (messaggioPost!=null && !messaggioPost.equals("null")) {
	String color="green";
	if (messaggioPost.startsWith("Errore"))
		color="red";
%>

<p style="text-align: center;"><span style="font-size: large; font-family: trebuchet ms,geneva; font-weight: bold; color: <%=color %>; background-color:#ff8">
<%=messaggioPost %>
</span></p>
<%} %>


<%
	String[] split;
	split = ArchivioDetail.get(0).toString().split(";;");
			%>

<% if (split[7]==null || split[7].equals("null")) {%>
<dhv:permission name="documentale_bacheca_apiari-edit">
<a href="GestioneBacheca.do?command=GestisciArchivioApiari&storeId=<%=storeId%>&descrizioneArchivio=<%=split[1] %>&nomeArchivio=<%=split[0] %>&dataInizio=<%=split[2] %>&statoArchivio=<%=split[3] %>&operazione=modificaArchivio">
Modifica</a>
</dhv:permission>
&nbsp;
 <%--dhv:permission name="documentale_bacheca-delete">
	<a href="GestioneBacheca.do?command=GestisciArchivio&storeId=<%=storeId%>&operazione=cancellaArchivio" onClick="return confirm('ATTENZIONE! Stai per cancellare l\'archivio e tutto il suo contenuto. Sei sicuro di continuare?')">
Cancella</a>
 </dhv:permission--%>
 <% } %>		
 <br/><br/>


  <table cellpadding="4" cellspacing="0" border="0" width="70%" class="details">
  <col width="10%">
  
  

		<tr>
			<th colspan="2"><strong>Informazioni generali</strong></th>
			</tr>
	
		<tr><td><b>Stato</b></td>	<td>
			<% if (split[7]!=null && !split[7].equals("null")) {%>
			<font color="blue">QUESTO ARCHIVIO DOCUMENTI E' STATO ARCHIVIATO IL <%=fixData(split[7]) %></font>
			<%} else  if (split[3]!=null && !split[3].equals("null")) {%>
			<font color="green">QUESTO ARCHIVIO DOCUMENTI E' STATO APPROVATO IL <%=fixData(split[3]) %></font>
			<%} else { %>
			<font color="red"> Questo archivio documenti è attualmente sotto revisione e non è stato approvato</font>
			<%} %>
			</td></tr>
			<tr class="row1"><td><b>Titolo</b></td>	<td><%=split[0] %></td></tr>
			<tr><td><b>Descrizione</b></td>	<td><%=split[1] %></td></tr>
			<tr class="row1"><td><b>Data inizio</b></td>	<td><%=split[2] %></td></tr>
			<tr><td><b>Inserito</b></td>	<td> <dhv:username id="<%=split[4] %>"></dhv:username> <%=fixData(split[5]) %></td></tr>
			<tr class="row1"><td><b>Modificato</b></td>	<td><dhv:username id="<%=split[8] %>"></dhv:username> <%=fixData(split[6]) %></td></tr>
			
			
			
			
			
			
		</table>
	
