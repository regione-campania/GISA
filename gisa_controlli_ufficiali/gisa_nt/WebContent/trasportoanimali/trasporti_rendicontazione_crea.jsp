<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,org.aspcfs.modules.trasportoanimali.base.*, org.aspcfs.modules.base.*" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.trasportoanimali.base.Organization" scope="request"/>
<jsp:useBean id="Anno" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ASL" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<script language="JavaScript">
  function checkFileForm(form) {
    var formTest = true;
    var messageText = "";

    
    /*if ( document.inputForm.ASL.value == "-1"){
        messageText += label("", "ASL è richiesto.\r\n");
        formTest = false;
      }*/

    if ( document.inputForm.Anno.value == "-1"){
        messageText += label("", "Anno è richiesto.\r\n");
        formTest = false;
      }
         
    if (formTest == false) {
      alert(messageText);
      return false;
    }
  }
</script>
<%

UserBean user = (UserBean)session.getAttribute("User");


if(user.getSiteId()!=-1)
{
	%>
	
	<form  name="inputForm" method="post" action="TrasportoAnimali.do?command=PrintReportRendicontazione&file=rendicontazione_annuale_asl.xml" onSubmit="return checkFileForm(this);">
	
	<%	
}else
{
	%>
	
	<form  name="inputForm" method="post" action="TrasportoAnimali.do?command=PrintReportRendicontazione&file=Rendicontazione.pdf" onSubmit="return checkFileForm(this);">
	
	<%
}
%>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="TrasportoAnimali.do"><dhv:label name="a">Trasporti Animali</dhv:label></a> >
  <dhv:label name="a">Rendicontazione Annuale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  
</table>
  <dhv:formMessage />
  <table>
   <tr>
  	 	<td>
  	 		<dhv:label name="">Anno</dhv:label>
  	 	</td>
  	 	<td>
  	 	        <%= Anno.getHtmlSelect("anno",OrgDetails.getAnno())%>
  	 	</td>
  	 </tr>
  	 <%--<tr>
  	 	<td>
  	 		<dhv:label name="">ASL</dhv:label>
  	 	</td>
  	 	<td>
  	 	        <%= ASL.getHtmlSelect("ASL",OrgDetails.getSpecieA())%>       	 	        
  	 	</td>
  	 </tr>--%>
  </table>
  <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
  <br>
<img height="16" border="0" align="absmiddle" width="16" src="images/icons/stock_print-16.gif"/>
  <input type="submit" value=" <dhv:label name="global.button.sav">Procedi</dhv:label> " name="save" />
<input type="button" value="<dhv:label name="global.button.cancel">Chiudi</dhv:label>" onClick="location.href='TrasportoAnimali.do?command=Dashboard'" /><br />
  <dhv:formMessage />
  <br />
</form>