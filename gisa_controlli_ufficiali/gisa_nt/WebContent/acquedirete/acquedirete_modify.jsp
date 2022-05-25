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
  - Version: $Id: accounts_modify.jsp 19046 2007-02-07 18:53:43Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.acquedirete.base.Organization,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.acquedirete.base.OrganizationAddress"%><jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAcque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipologiaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.acquedirete.base.Organization" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script language="JavaScript">
  indSelected = 0;
  orgSelected = 1; 
  
function doCheck(form) {
	  
      if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
function checkForm(form) {
    var formTest = true;
    var formTest2 = true; 
    message = "";
    alertMessage = "";
    	

    if((form.siteId.value =='-1' || form.siteId.value =='') )
    {
    	message += '- ASL richiesta\n' ;
    	formTest = false ;
    }
    
   if (form.name.value == "" ){
  	 //alert(!isNaN(form.address2longitude.value));
  		 message += "- Si Prega di Inserire la Denominazione\r\n";
  		 formTest = false;
  }
      
   if (form.address1city.value == "-1" || form.address1city.value == "" ){
  	 //alert(!isNaN(form.address2longitude.value));
  		 message += "- Si Prega di selezionare  il comune di appartenenza\r\n";
  		 formTest = false;
  }
   
   if (form.address1line1.value == "" ){
	  	 //alert(!isNaN(form.address2longitude.value));
	  		 message += "- Si Prega di inserire un indirizzo\r\n";
	  		 formTest = false;
	  }
   
   if (form.address1latitude.value == '' || isNaN(form.address1latitude.value)){
   	message += "- Inserire la Latitudine, con un valore compreso tra 39.988475 e 41.503754 \r\n";
		formTest = false;
   } else {
		 if ((form.address1latitude.value < 39.988475) || (form.address1latitude.value > 41.503754)){
		       	message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 \r\n";
		        formTest = false;
		  }		
	}	    
 	 
 	 if (form.address1longitude.value == '' || isNaN(form.address1longitude.value)){
 		message += "- Inserire la Longitudine, con un valore compreso tra 13.7563172 e 15.8032837 \r\n";
		formTest = false;
 	 } else {
 		 if ((form.address1longitude.value < 13.7563172) || (form.address1longitude.value > 15.8032837)){
 	      	message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 \r\n";
 	        formTest = false;		      		
 	  	 }   
 	 }
   
   
   	 if (formTest && formTest2){
   		 var test = document.addAccount.selectedList;
	     if (test != null) {
	       selectAllOptions(document.addAccount.selectedList);
	     }
	     if(alertMessage != "") {
	       confirmAction(alertMessage);
	     }
	     form.submit();
	     return true;
   	   	
  }else {
   	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
   	    return false;
   	 }     	 
  }

</script>





<form name="addAccount" action="AcqueRete.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0" class="details">
<tr>
<td>
<a href="AcqueRete.do?command=SearchForm"><dhv:label name="">Acque Di Rete</dhv:label></a> > 
<a href="AcqueRete.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Acque Di Rete</dhv:label></a> >
<%--}--%>
<dhv:label name="accountsc.modify">Modifica Informazioni Acque Di Rete</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="acquedirete" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">

<input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';return checkForm(this.form);" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='AcqueRete.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='AcqueRete.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_modify.ModifyPrimaryInformation">Modify Primary Information</dhv:label></strong>
    </th>     
  </tr>
  
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">ASL</dhv:label>
      </td>
      <td>
        <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
        </dhv:evaluate>
      </td>
  </tr>
 
 <dhv:include name="name" none="true">
  <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="250" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
    </td>
  </tr>
  </dhv:include>
  <tr>
    <td nowrap class="formLabel" name="tipo_struttura" id="tipo_struttura">
      <dhv:label name="">Tipologia</dhv:label>
    </td>
    <td>
    	<%= TipoAcque.getHtmlSelect("tipo_struttura",OrgDetails.getTipo_struttura()) %>
    	 <input type="hidden" name="tipo_struttura" value="<%=OrgDetails.getTipo_struttura()%>" >
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Ente Gestore</dhv:label>
    </td>
    <td>
      <input type="text" size="50"  name="ente_gestore" value="<%=OrgDetails.getEnte_gestore()%>"><font color="red">*</font>
    </td>
  </tr>
  
     <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Stato</dhv:label>
    </td>
    <td>
			<select name = "stato">
				<option value = "0" <%=(OrgDetails.getStato()==0) ? "selected" :"" %>>Attivo</option>
				<option value = "1" <%=(OrgDetails.getStato()==1) ? "selected" :"" %>>Non Attivo</option>
			</select>
    </td>
  </tr>
  </table>
 
<%
  boolean noneSelected = false;
  int numeroLocaiFunz=0; 
  ArrayList<OrganizationAddress> locali_funz_collegati = new ArrayList<OrganizationAddress>();
  
	int s=1;	
	boolean primo=false;
	int flag=0;
	
	  int acount = 0;
	  int locali=0;
	
	  Iterator anumber = OrgDetails.getAddressList().iterator();
	  while (anumber.hasNext()) {
	     OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
	     
	 	if (thisAddress.getType()==6)
	  	{
	  		numeroLocaiFunz ++ ;
	  		locali_funz_collegati.add(thisAddress);
	  	}
	  	else
	  	{
	  		 ++acount;
	%> 

	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
		    <strong>Ubicazione</strong>
		 
	      <input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
	      
	    </th>
	    </tr>
	    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
	   
	 <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
	    </td>
	    <td>
	    
	    <select  name="address1city" id="address1city">
		<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
	            
		 <%
	                Vector v = OrgDetails.getComuni2();
		 			Enumeration e=v.elements();
	                while (e.hasMoreElements()) {
	                	String prov=e.nextElement().toString();
	                  
	        %>
	                <option <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( prov ) ){%>selected="selected"<%} %>  value="<%=prov%>"><%= prov %></option>	
	              <%}%>
			
		</select>
	    
		<font color = "red">*</font>
	
	    </td>
	  </tr>
	 
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
	    </td>
	    <td>
	   
	      <input type="text" size="40" name="address<%= acount %>line1" id="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
	      <font color="red">*</font>
	    </td>
	  </tr>

	 
	  <dhv:evaluate if="<%=thisAddress.getType() == 1 %>">
	      <tr class="containerBody">
	       <td nowrap class="formLabel">
	          <dhv:label name="">C/O</dhv:label>
	       </td>
	       <td>
	        <input type="text" size="40" name="address1line2" maxlength="80" value="<%= ((thisAddress.getStreetAddressLine2()!=null) ? (thisAddress.getStreetAddressLine2()) : ("")) %>">
	      </td>
	  </tr>
	  </dhv:evaluate>
	  
  <tr class="containerBody"> 
  <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td> 
   <td> 
    	<input type="text" id="address<%= acount %>latitude" name="address<%= acount %>latitude" size="30" value="<%= (thisAddress.getLatitude() != 0.0 ) ? thisAddress.getLatitude() : "" %>" id="">
    	 <font color="red">*</font>
     </td>
  </tr> 
  <tr class="containerBody"> 
   <td class="formLabel" nowrap id="longitude1"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td> 
    <td><input type="text" id="address<%= acount %>longitude" name="address<%= acount %>longitude" size="30" value="<%= (thisAddress.getLongitude() != 0.0 ) ?  thisAddress.getLongitude() : "" %>" id="longitude12">
     <font color="red">*</font><a href="#" onClick="window.open('docs/GuidaCoordinate.pdf','width=600,height=300'); return false;"> Guida al calcolo delle coordinate da Smartphone</a>
     
   </tr> 
	      <!--tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
	    </td>
	    
	    <td>
	      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
	    </td>
	 
	  
	  </tr-->  
	  
	   <%-- tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
	    </td>
	    <td>   
	    
	    <%
		    if(thisAddress.getType() == 1)
		    {
			    if(thisAddress.getState()==null) 
			    {
	    %>
	            <input type="text" size="10" name="address1state" maxlength="80" value=""> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
	   <%
	   			}
			    else
			    {
			    	
		%>
	        <input type="text" size="10" name="address1state" maxlength="80" value="<%=thisAddress.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
	   <%
	   			}
			}
		    else if(thisAddress.getType() == 5)
		    {
		    	if(thisAddress.getState() == null || thisAddress.getState().equals("null")) {
		    		
		    %>
			    <input type="text"  size="28" name="address1state"  value="" maxlength="80">
			     <%
	   			}
			    else
			    {
			    	
				%>
		      		<input type="text"  size="28" name="address1state"  value="<%=thisAddress.getState()%>" maxlength="80">
		    <%} } 
		    else{ %>
		      <input type="text"  size="28" name="address<%= acount %>state"  value="<%=thisAddress.getState()%>" maxlength="80">
		     
		      <%} %>
	    </td>
	  </tr--%> 
	 
	  <% } }%>

	 
	</table>
	  
<br>
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';return checkForm(this.form);" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='AcqueRete.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='AcqueRete.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
<input type="hidden" name="action1" id="action1" value="modify">
</dhv:container>
</form>