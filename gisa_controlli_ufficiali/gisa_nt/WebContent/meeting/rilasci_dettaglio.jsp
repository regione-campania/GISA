<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Riunione"%>
<jsp:useBean id="Rilascio" class="org.aspcfs.modules.meeting.base.Rilascio" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="listaContesti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

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
  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>



<%@ include file="../initPage.jsp" %>


<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Dettaglio Rilascio
</td>
</tr>
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni Rilascio</strong>
    </th>
  </tr>
  
   
		
 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Oggetto
			</td>
			<td>
         		<%= toHtml(Rilascio.getOggetto()) %>&nbsp;
			</td>
		</tr>
			
	
			
 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Data
			</td>
			<td>
         		<%= toDateasString(Rilascio.getData()) %>&nbsp;
			</td>
		</tr>

	</table>
		<br/><br/>
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Note di rilascio</strong>
    </th>
  </tr>
  			
  			 				
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Contesto
			</td>
			<td>
         		<%=listaContesti.getSelectedValue(Rilascio.getNoteIdContesto())%> &nbsp;
			</td>
		</tr>
		
		 				
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Modulo
			</td>
			<td>
         		<%= toHtml(Rilascio.getNoteModulo()) %>&nbsp;
			</td>
		</tr>
		
		 				
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Funzione
			</td>
			<td>
         		<%= toHtml(Rilascio.getNoteFunzione()) %>&nbsp;
			</td>
		</tr>
			
		 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Note
			</td>
			<td>
         		<%= toHtml(Rilascio.getNoteNote()) %>&nbsp;
			</td>
		</tr>
		
		</table>
		
		<br/><br/>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
    <th colspan="8">
      <strong>Riunioni di riferimento</strong>
    </th>
  </tr>
  
  <%for (int i=0; i<Rilascio.getListaRiunioni().size(); i++) {
  Riunione riunione = (Riunione) Rilascio.getListaRiunioni().get(i);
  %>
  
		<tr>
		<td>
		<a href="GestioneRiunioni.do?command=DettaglioRiunione&id=<%=riunione.getId()%>"> <%=riunione.getTitolo() %> <%=riunione.getDescrizioneBreve() %></a>  
		</td>
		</tr>
<%} %>		
		</table>
		
		
		 
       
       
      
	