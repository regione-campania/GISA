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
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        
        
   <script>
   function clearForm(){
	   var inp = document.getElementById("searchAccount").getElementsByTagName('input');
	   for(var i in inp){
	       if(inp[i].type == "text"){
	           inp[i].value='';
	       }
	   }
	   
	   mostraAttivitaProduttive('attprincipale',1,-1, true,-1);
   }
   
   function checkForm(form){
	   var linea1 = document.getElementById("searchattivita1").value;
	   var linea2 = document.getElementById("searchattivita2").value;
	   var linea3 = document.getElementById("searchattivita3").value;
	   var searchAttivita = document.getElementById("searchattivita");
	   
	   var linea = "";
	   
	   if (linea1!=''){
		   linea = linea1;
		   if (linea2!=''){
			   linea = linea + "->"+linea2;
			   if (linea3!='')
				   linea = linea + "->"+linea3;
		   }
			   
	   }
	   if (linea!='')
		   searchAttivita.value = linea;
	   loadModalWindow();
	   form.submit();
   }

   </script>     

<center>
<table style="border:1px solid black">
<tr><td>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="OpuStab.do?command=ToAddNonFissa"><dhv:label name="">Aggiungi</dhv:label></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</td></tr>
</table> 
</center> 
<br/>

<form name="searchAccount" id = "searchAccount" action="OpuStab.do?command=Search" method="post">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Anagrafica Impresa</dhv:label></a> > 
<dhv:label name="">Ricerca</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>


<table>
<col width="50%">
<tr><td valign="top">  


<!--  IMPRESA -->
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Anagrafica Impresa</dhv:label></strong>
          </th>
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
        	<td nowrap class="formLabel">
     		 Partita IVA
   			 </td> 
    		<td> 
				<input  type="text" maxlength="11" size="50" name="searchpartitaIva" value="">
			</td>
  		</tr>
        
<!--         <tr> -->
<!--         	<td nowrap class="formLabel" > -->
<%--      		 <dhv:label name="">Comune</dhv:label> --%>
<!--    			 </td>  -->
<!--     		<td>  -->
<!-- 				<input  type="text" maxlength="50" size="50" name="searchcomuneSedeLegale" value=""> -->
<!-- 			</td> -->
<!--   		</tr> -->
 </table>
 
 
 </td><td valign="top">  
 
 <!--  RAPPRESENTANTE LEGALE -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details"> 
    <tr>
          <th colspan="2">
            <strong><dhv:label name="">Legale rappresentante</dhv:label></strong>
          </th>
        </tr>
        
        <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Cognome </dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchcognomeSoggettoFisico" value="">
			</td>
  		</tr>
  		
  		   <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Nome </dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchnomeSoggettoFisico" value="">
			</td>
  		</tr>
  		
  			   <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Codice Fiscale </dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="16" size="50" name="searchcodiceFiscaleSoggettoFisico" value="">
			</td>
  		</tr> 
 </table>
  		
  		</td></tr>
  		<tr><td valign="top">  
  		
  
  
  
  </td><td valign="top">  
  	
    <!-- LINEA ATTIVITA -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">    
    <tr>
          <th colspan="2">
            <strong><dhv:label name="">Linea attività</dhv:label></strong>
          </th>
        </tr>
        
        <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Linea</dhv:label>
   			 </td> 
    		<td> 
    		<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/SuapDwr.js"> </script>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>

<input type ="hidden" value = "false" id = "validatelp" value = "false">
	<table id = "attprincipale" style="width: 100%;"></table>
<script> 	mostraAttivitaProduttive('attprincipale',1,-1, true,-1);</script>

<div style="display:none">
 <input type="text" name="searchattivita1" id="searchattivita1"/>
 <input type="text" name="searchattivita2" id="searchattivita2"/>
 <input type="text" name="searchattivita3" id="searchattivita3"/>
<input type="text" name="searchattivita" id="searchattivita" value=""/>
</div>
			</td>
  		</tr>
 </table>
 

      
      </td><tr> 	
 </table> 		


<input type="button" id="search" name="search" value="Ricerca" onClick="checkForm(this.form);"/>
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

</form>











