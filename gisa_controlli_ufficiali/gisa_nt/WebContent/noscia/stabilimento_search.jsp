<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%> 
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
  <link href="css/tooltip.css" rel="stylesheet" type="text/css" />
<script src="javascript/tooltip.js" type="text/javascript"></script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        
<!--     <script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/SuapDwr.js"> </script> -->
<!-- <SCRIPT src="javascript/suapUtil.js"></SCRIPT>     -->
<!-- <script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script> -->
<!-- <script type="text/javascript" src="javascript/tabber.js"></script> -->
<!-- <script src="javascript/aggiuntaCampiEstesiScia.js"></script> -->
<%-- 	<jsp:useBean id="normeList" class="org.aspcfs.utils.web.LookupList" --%>
<%-- 	scope="request" />  --%>
<!-- <link href="css/nonconformita.css" rel="stylesheet" type="text/css" /> -->

<script>
 function clearForm(form){
	   var inp = form.getElementsByTagName('input');
	   for(var i in inp){
	       if(inp[i].type == "text"){
	           inp[i].value='';
	       }
	   }
	   var asl = form.searchcodeidAsl;
	   asl.value="-1";
	   
	   var stato = form.searchcodeidStato;
	   stato.value="-1";
	   
	   var sel = form.getElementsByTagName('select');
	   for(var i in sel){
	           sel[i].value='';
	   }
   }
   
   function checkForm(form){
	   
	 	loadModalWindow();
	   	form.submit();
   }
   </script>


<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="AltriStabilimenti.do?command=Default"><dhv:label name="">Altri stabilimenti</dhv:label></a> >
Gestione No Scia > Ricerca 
</td>
</tr>
</table>
<%-- End Trails --%>




	<form name="searchAccount1" id = "searchAccount1" action="GisaNoScia.do?command=Search" method="post">
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Rapida</dhv:label></strong>
          </th>
        </tr>
        
   <tr id="asl">
        	<td nowrap class="formLabel">
     		 <dhv:label name="">ASL</dhv:label>
   			 </td> 
    		<td> 
				
	    <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("searchcodeidAsl",-1) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="searchcodeidAsl" id="searchcodeidAsl" value="<%=User.getSiteId()%>" >
          
        </dhv:evaluate>
    
			</td>
  		</tr> 
		
 	 <tr>
          <td class="formLabel">
            <dhv:label name="lab.denom">Nome/Ditta/Ragione sociale</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchragioneSociale" id="searchRagioneSociale" value="">
          </td>
        </tr>
        <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Numero <br/>Registrazione stabilimento</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchnumeroRegistrazione" value="">
			</td>
  		</tr>
  		<tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Partita IVA</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="11" size="50" name="searchpartitaIva" value="">
			</td>
  		</tr>
  
        	
  <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Linea di attività</dhv:label>
   			 </td> 
    		<td> 
<!--     		<input type="text" name="searchAttivita" style="width: 35%"> -->
    		<select id="searchAttivita" name="searchAttivita" style="max-width: 60%;">
				<option value=""></option>
				<c:forEach items="${listLinee}" var="linee">
				<c:if test= "${!fn:contains(linee.codice_univoco_ml, 'OPR-OPR-X')}">
				<option value="${linee.codice_univoco_ml}">${linee.desc_linea}
				</option>
			</c:if>
		</c:forEach>
	</select>
<%--     		<img title="<%="INDICARE LA DESCRIZIONE DELLA LINEA DI ATTIVITA. ESEMPIO : (COMMERCIO CARNE)".toUpperCase() %>" class="masterTooltip" src="images/questionmark.png" width="20"/> --%>
 		</td>
 </tr>
 </table>
 <input type="button" id="search" name="search" value="Ricerca" onClick="checkForm(document.forms['searchAccount1']);"/>
 <%
if (User.getRoleId()!=Role.RUOLO_COMUNE)
{
%> 
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="clearForm(document.forms['searchAccount1']);">

<%} %>
<input type="hidden" name="source" value="searchForm">
        </form>
	


<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>







 








