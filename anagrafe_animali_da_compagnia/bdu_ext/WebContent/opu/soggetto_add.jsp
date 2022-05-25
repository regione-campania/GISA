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
  - Version: $Id: accounts_add.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>


<jsp:useBean id="Soggetto" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request"/>
<jsp:useBean id="Titolo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>

<%@ include file="../initPage.jsp" %>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
  
<form id = "addSoggetto" name="addSoggetto" action="Soggetto.do?command=Insert&auto-populate=true" method="post">
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="<dhv:label name="global.button.insert">Insert</dhv:label>" name="Save" onClick="">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
</dhv:evaluate></br></br>
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Soggetto fisico</dhv:label></strong>
      
    </th>
  </tr>
  
      <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Titolo</dhv:label>
      </td>
      <td>
 	<%= Titolo.getHtmlSelect("idTitolo", Soggetto.getIdTitolo()) %>

      </td>
    </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Codice Fiscale</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="16" name="codFiscale" value=""><font color="red">*</font>
    </td>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Sesso</dhv:label>
    </td>
    <td>
      <input type="radio" name="sesso" id="sesso" value="M" checked="checked">M
       <input type="radio" name="sesso" id="sesso" value="F">F
    </td>
  </tr>
  
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Nome</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nome" value=""><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Cognome</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognome" value=""><font color="red">*</font>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataNascita" name="dataNascita" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
      
  
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="comuneNascita" value="">
    </td>
  </tr>
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Provincia Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="provinciaNascita" value="">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune residenza</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="addressLegaleCountry" value="">
    </td>
  </tr>
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Cap residenza</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="addressLegaleZip" value="">
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Provincia residenza</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="addressLegaleCity" value="">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="addressLegaleLine1" value="">
    </td>
  </tr>
  
  
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="email" value="">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefono1" value="">
    </td>
    
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono secondario</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefono2" value="">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="fax" value="">
    </td>
    
  </tr>
  

</table>
</br></br>
<input type="submit" value="<dhv:label name="global.button.insert">Insert</dhv:label>" name="Save" onClick="">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
</dhv:evaluate>
</form>
