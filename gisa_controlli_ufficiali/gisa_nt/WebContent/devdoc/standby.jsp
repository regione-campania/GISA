<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.devdoc.base.Flusso"%>


<jsp:useBean id="Flusso" class="org.aspcfs.modules.devdoc.base.Flusso" scope="request"/>
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
  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }
  
  public static String zeroPad(int id)
  {
	  String toRet = String.valueOf(id);
	  while (toRet.length()<3)
	  	toRet = "0"+toRet;
	  return toRet;
  
  }
  %>



<%@ include file="../initPage.jsp" %>


  

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni Richiesta</strong>
    </th>
  </tr>
  
 	 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Richiesta
			</td>
			<td>
         		<%= zeroPad(Flusso.getIdFlusso()) %>&nbsp;
			</td>
		</tr>  
		

	
	 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Descrizione
			</td>
			<td>
         		<%= Flusso.getDescrizione() %>&nbsp;
			</td>
		</tr>  
		
			 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Tags
			</td>
			<td>
         		<%= toHtml(Flusso.getTags()) %>&nbsp;
			</td>
		</tr> 
	
			 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Data ultima modifica
			</td>
			<td>
         		<%= toDateWithTimeasString(Flusso.getData()) %>&nbsp;
			</td>
		</tr> 
		<% if (Flusso.getDataConsegna()!=null){ %>
			 <tr class="containerBody">
			<td nowrap class="formLabel">
      			Data consegna
			</td>
			<td>
         		<%= toDateWithTimeasString(Flusso.getDataConsegna()) %>&nbsp;
			</td>
		</tr> 
		<%} %>
		
		</table>
		
		<br/><br/>
		
		<form method="post" action = "" onSubmit="loadModalWindow()">
		
		<table cellpadding="4" cellspacing="0" border="0" class="details">
  <tr>
    <th colspan="2">
      <strong>Standby</strong>
    </th>
  </tr>
  
  <input type="hidden" id="idFlusso" name="idFlusso" value="<%=Flusso.getIdFlusso()%>"/>
   
 <%if (Flusso.getDataStandby()==null) {%>
  <tr><td>Data standby</td> <td> <input type="text" readonly id="dataStandby" name="dataStandby" size="10" value=""/>
<a href="#" onClick="cal19.select(document.getElementById('dataStandby'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
</td></tr>
 <tr><td>Note Standby</td> <td> <textarea id="noteStandby" name="noteStandby" cols="120" rows="3"></textarea></td></tr>
 <%} %>
 <tr><td colspan="2">
 
 <%if (Flusso.getDataStandby()==null) {%>
 <input type="button" id="bottoneStandby" value="STANDBY" onClick="checkFormStandby(this.form,1)"/>
 <%} else { %>
  <input type="button" id="bottoneStandby" value="RIATTIVA" onClick="checkFormStandby(this.form,0)"/>
 
 <%} %>
 
 </td></tr>
</table>
	
	</form>
		
		