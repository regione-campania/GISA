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
  - Version: $Id: contactlist_include.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>

<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%><jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="session"/>
<jsp:useBean id="ProjectListSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="Filters" class="org.aspcfs.modules.base.FilterList" scope="request"/>
<jsp:useBean id="viewUserId" class="java.lang.String" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/pageListInfo.js"></script>

<%


%>

<script>
function addItem(size)
{

	var select = window.opener.document.getElementById("listViewId");
	 for (var i = select.length - 1; i >= 0; i--)
  	  select.remove(i);
	for (i=1 ; i<=size;i++)
	{
		
		if(document.getElementById('checkcontact'+i)!=null && document.getElementById('checkcontact'+i).checked == true)
		{
			cId = document.getElementById('hiddencontactid'+i).value ;
			cMail = document.getElementById('hiddenmail'+i).value ;
			cName = document.getElementById('hiddenname'+i).value ;

			

			 var NewOpt = document.createElement('option');
             NewOpt.value = cId+';'+cMail ; // Imposto il valore
             NewOpt.text = cName; // Imposto il testo
             NewOpt.selected=true ;
             //Aggiungo l'elemento option
             try
             {
           	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
             }catch(e){
                 alert(e);
           	  select.add(NewOpt); // Funziona solo con IE
             }
			
			
		}

	}
	window.close();
	
}

function removeItem(idUtente)
{
	

}
function stampa()
{
	alert('stampa');

}

</script>

<table width="100%" border="0">

</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center" width="8">&nbsp;</th>
    <th>Nome</th>
    <th>Asl</th>
	<th>Ruolo
    </th>
  </tr>
<%
	Iterator j = ContactList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int count = 0;
    while (j.hasNext()) {
			count++;
      rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
      int thisContactId = thisContact.getId();
      
%>
  <tr class="row<%= rowid %>">
    <td align="center" nowrap width="8">
      <input type="checkbox" name="checkcontact<%= count %>" id = "checkcontact<%= count %>" value=<%= thisContactId %><%=thisContact.getValidName() %> >
      <input type="hidden" id="hiddencontactid<%= count %>" name="hiddencontactid<%= count %>" value="<%= thisContactId %>">
      <input type="hidden" id="hiddenname<%= count %>"  name="hiddenname<%= count %>" value="<%= toHtml(thisContact.getValidName()) %>">
      <input type="hidden" id="hiddenmail<%= count %>"  name="hiddenname<%= count %>" value="<%= toHtml(thisContact.getEmailAddress(0)) %>">
      
    </td>
    <td nowrap>
		
      <%= ((thisContact.getNameFirst() != null) ? toHtml( thisContact.getNameFirst().toUpperCase()) : "" ) %>
       <%= ((thisContact.getNameLast() != null) ? toHtml( thisContact.getNameLast().toUpperCase()) : "" ) %>
    </td>
    <td nowrap>
    <%if(thisContact.getSiteId() ==-1)
    	{
    	%>
    	Non Assegnata
    	<%}
    else
    {
    %>
      <%= SiteIdList.getSelectedValue(thisContact.getSiteId()) %>
    <%} %>
    </td>

	<td>
		<%=((thisContact.getRole()!=null) ?  toHtml(thisContact.getRole().toUpperCase()) : "" ) %>
	</td>

  </tr>
<%
    }
  } else {
%>
  <tr>
    <td class="containerBody" colspan="<%= !ContactList.getExclusiveToSite() || ContactList.getIncludeAllSites()?"6":"5" %>">
      <dhv:label name="campaign.noContactsMatchedQuery">No contacts matched query.</dhv:label>
    </td>
  </tr>
<%}%>

</table>
