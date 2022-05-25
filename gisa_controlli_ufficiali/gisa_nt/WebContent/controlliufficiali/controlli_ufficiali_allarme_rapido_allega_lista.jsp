<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<script>

function chiudiPopUp(fileCaricato,idFile,errore,tipoAllegato)
{

	
	if(fileCaricato=='yes' && errore=='null')
	{

		if (tipoAllegato=='2')
		{	
		opener.document.getElementById('idFile').value = idFile ;
		opener.document.getElementById('fileAllegare').value = idFile ;
		opener.document.getElementById('isAllegato').value = 'true' ;
		alert(opener.document.getElementById('isAllegato').value);
		opener.document.getElementById('msg_file').innerHTML="<font color = 'green'>File allegato correttamente</font>"
		}
		else
		{
	
			opener.document.getElementById('idFileSupervisione').value = idFile ;
	
			opener.document.getElementById('fileAllegareSupervisione').value = idFile ;
	
			opener.document.getElementById('isAllegatoSupervisione').value = 'true' ;
			opener.document.getElementById('msg_file_supervisione').innerHTML="<font color = 'green'>File allegato correttamente</font>"
				
			}
		
		alert(opener.document.getElementById('isAllegato').value);
			
		window.close();
	}

	
	
}
</script>

<%
String tipoAllegato = "" ;
if ("1".equalsIgnoreCase((String)request.getAttribute("TipoAllegato")))
{
	tipoAllegato = "1" ;

}
else
{
	tipoAllegato = "2" ;
}

%>
<body onload="chiudiPopUp('<%=request.getAttribute("fileCaricato") %>',<%=request.getAttribute("idFile") %>,'<%=request.getAttribute("file_sizeError") %>',<%=tipoAllegato %>)">

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
  - Version: $Id: accounts_documents_upload.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="nomeFile" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if (form.id<%= OrgDetails.getOrgId() %>.value.length < 5) {
      messageText += label("file.required", "- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != label("button.pleasewait","Please Wait...")) {
        form.upload.value=label("button.pleasewait","Please Wait...");
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<form method="post" name="inputForm" action="PrintReportVigilanza.do?command=Upload<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="TipoAllegato" value="<%=tipoAllegato %>" />

<input type="hidden" name="id" value="<%=request.getAttribute("orgId")  %>" />
<input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>" />
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
Allega <%=(request.getAttribute("TipoAllegato")!=null && "1".equalsIgnoreCase((String)request.getAttribute("TipoAllegato")))? "Verbale" : "Lista di Distribuzione"%>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>Allega<%=request.getAttribute("nomeFile")%> </b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
      </td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="VERBALE"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    
   
 		
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
      </td>
      <td>
        <input type="file" name="id<%= request.getAttribute("orgId") %>" size="45">
      </td>
    </tr>
  </table>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" value="<dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload" />

</form>
</body>
