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
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<%@ include file="../initPage.jsp" %>


<script>
			function popolaComboComuni(idAsl)
			{
				idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				
					PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback) ;
				
			}

	          function setComuniComboCallback(returnValue)
	          {
	        	  var select = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
	              

	              //Azzero il contenuto della seconda select
	              for (var i = select.length - 1; i >= 0; i--)
	            	  select.remove(i);

	              indici = returnValue [0];
	              valori = returnValue [1];
	              //Popolo la seconda Select
	              for(j =0 ; j<indici.length; j++){
	              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
	              var NewOpt = document.createElement('option');
	              NewOpt.value = indici[j]; // Imposto il valore
	              NewOpt.text = valori[j]; // Imposto il testo

	              //Aggiungo l'elemento option
	              try
	              {
	            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	              }catch(e){
	            	  select.add(NewOpt); // Funziona solo con IE
	              }
	              }
	          }

</script>
<form name="searchAccount" action="Concessionari.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MolluschiBivalvi.do">Concssionari</a> > 
Ricerca Concessionari
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            Ricerca Rapida Concessionari
          </th>
        </tr>
       <tr>
          <td class="formLabel">
           ASL
          </td>
          <td>
          <%if(User.getSiteId()>0)
        	  {
        	  %>
        	  <%=SiteIdList.getSelectedValue(User.getSiteId()) %>
        	    <input type="hidden" name = "searchcodesiteId" value = "<%=User.getSiteId() %>">
        	  <%
        	  
        	  }
          else
          {
        	  %>
        	  <%=SiteIdList.getHtmlSelect("searchcodesiteId",-1) %>
        	  <%
          }
        	  %>
          
          </td>
        </tr>
      
        <tr>
          <td class="formLabel">
           Impresa
          </td>
          <td>
        <input type = "text" name = "searchname">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
           Legale Rappresentante
          </td>
          <td>
        <input type = "text" name = "searchtitolare_nome">
          </td>
        </tr>
       
       
       
       
        
         
      </table>
    </td>
  </tr>
</table>
<input type="submit" onclick='loadModalWindow();' value="<dhv:label name="button.search">Search</dhv:label>">
</form>
</body>

