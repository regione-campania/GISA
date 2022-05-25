<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: mycfs.jsp 24345 2007-12-09 15:22:23Z srinivasar@cybage.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.NewsArticle,org.aspcfs.modules.mycfs.beans.*, org.aspcfs.modules.registrazioniAnimali.base.*,org.aspcfs.modules.opu.base.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<%@page import="org.aspcfs.modules.sync.utils.SyncUtils"%>
<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>

<jsp:useBean id="NewsList" class="org.aspcfs.modules.accounts.base.NewsArticleList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="IndSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="syncClient" class="org.aspcfs.modules.service.base.SyncClient" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="eventiCircuitoCommerciale" class="org.aspcfs.modules.registrazioniAnimali.base.EventoList" scope="request"/>
<jsp:useBean id="listaRegistrazioni" class="org.aspcfs.modules.opu.base.RegistrazioniOperatoreList" scope="request"/>
<jsp:useBean id="eventiCircuitoPrivato"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoList"
	scope="request" />
<jsp:useBean id="registrazioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<meta charset="utf-8" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<style type="text/css">
.postit {
  position: relative;
  background-color: #F4F39E;
  border-color: #DEE184;
  text-align: left;
  margin: 1em 10px;
  box-shadow: 0px 2px 3px rgba(0,0,0,0.25);
  width: 22em;
  height: 19em;
  padding: 0.5em 0.5em;

}
.postittext {
  font-family:  "Segoe Print",helvetica;
  font-weight: bold;
  color:#00F;
  line-height: 1.5;
  font-size: 120%;
  font-style: italic;

}
</style>

<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>	
<!-- POPUP VETERINARI PRIVATI TEMPORANEA PER RICHIESTA DATI -->	
<script language="javascript">

function checkFirefox(msg)
{

	if(msg!=null && msg!="")
		{
		if(confirm(msg)==false)
			{
			location.href="Login.do?command=Logout";
			}
		}
		
		}
		
$(document).ready(function() {
	var closeandlogout = true;
	$("#dialog").dialog({
        autoOpen: false,
        resizable: true,
        modal: true,
        title: 'Prima di proseguire, per favore aggiorna i tuoi dati',
        close: function () {
        	if (closeandlogout){
	//	if ($("#email").val() == null || $("#email").val() == "" || $("#telefono").val() == null || $("#telefono").val() == "" ){
<%
		String ambiente = (String) request.getSession().getAttribute("ambiente");
		if (ambiente != null && ambiente.equalsIgnoreCase("sirv")) 
		{
%>
			document.location.href = "/bdu/Login.do?command=LoginSirv";
<%	
		}
		else
		{%>

			DwrUtil.Logout(logout);

	<%	}%>

		}
	//	else
	//	{

		//	DwrUtil.Logout();	

	//	}
            },
        buttons: {
            'Salva i dati e prosegui': function() {
              //  alert($("#telefono").val());
             //   alert($("#email").val());
                if ($("#email").val() != null && $("#email").val() != "" && $("#telefono").val() != null && $("#telefono").val() != "" ){
                   // alert("Dati completi");
                    DwrUtil.aggiornaDatiLLP($("#email").val(), $("#telefono").val(), <%=User.getUserId()%>, salvaCallback);
                    closeandlogout = false;
                	$(this).dialog('close');   
                } else{
               //     alert("Dati incompleti");
                }
            },
            'Rinuncia ed esci dal sistema': function() {
            	//$("#idTipologiaEvento").val(-1);
            	//popolaCampi();
            	//DwrUtil.Logout(logout);
                $(this).dialog('close');
                
               // $('#opener').button('refresh')
            }
        }
    });

	function logout(value)
	{
		
		document.location.href = "/bdu/MyCFS.do?command=Home";
		
	}

	function salvaCallback(value)
	{
		if (value){
			document.location.href = "/bdu/MyCFS.do?command=Home";
		}else{
			DwrUtil.Logout(logout);
		}
		
	}

<%
System.out.println("EMAIL" +User.getUserRecord().getContact().getPrimaryEmailAddress());
System.out.println("TELEFONO" +User.getUserRecord().getContact().getPrimaryPhoneNumber());
if ( User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && 
		(User.getUserRecord().getContact().getPrimaryEmailAddress() == null || 
		("").equals(User.getUserRecord().getContact().getPrimaryEmailAddress()) || 
		User.getUserRecord().getContact().getPrimaryPhoneNumber() == null ||
				("").equals(User.getUserRecord().getContact().getPrimaryPhoneNumber()))){
//System.out.println("popuP");
//System.out.println(User.getUserRecord().getContact().getPrimaryEmailAddress());
//System.out.println(User.getUserRecord().getContact().getPrimaryEmailAddress());%>
$('#dialog').dialog('open');
<%}%>

});
</script>

<jsp:useBean id="historyList" class="org.aspcfs.modules.contacts.base.ContactHistoryList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="contactHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<table width="100%">
<tr>
<td  width="50%">

<%  ArrayList<String> caniliOccupati = (ArrayList<String>)request.getAttribute("caniliOccupati");
	if(caniliOccupati!=null && !caniliOccupati.isEmpty()){ %>
		<font color="red"><b>Lista canili della tua asl che risultano in sovraffollamento alla data di oggi e che sono bloccati se non si ripristinano i valori di capienza:</b></font><%
		Iterator<String> caniliOccupatiIterator = caniliOccupati.iterator();  %>		
		<ol><%
			while(caniliOccupatiIterator.hasNext()){
				String temp = caniliOccupatiIterator.next(); %>		
				<li><font color="red"><b><%=temp%></b></font></li><%
			} %>	
		</ol> <%
	} %>
   

<%  ArrayList<String> caniliBloccatiManualmente = (ArrayList<String>)request.getAttribute("caniliBloccatiManualmente");
	if(caniliBloccatiManualmente!=null && !caniliBloccatiManualmente.isEmpty()){ %>
		<br><font color="red"><b>Lista canili della tua asl che risultano bloccati in ingresso:</b></font><!-- <b> [il valore tra parentesi indica la data di inizio blocco]:</b>--><%
		Iterator<String> caniliBloccatiManualmenteIterator = caniliBloccatiManualmente.iterator();  %>		
		<ol><%
			while(caniliBloccatiManualmenteIterator.hasNext()){
				String temp = caniliBloccatiManualmenteIterator.next(); %>		
				<li><font color="red"><b><%=temp.replace("[","</font><font color='black'>[")%></b></font></li><%
			} %>	
		</ol> <%
	} %>

</td>
<td align="center">
	<div class="postit">
		<pre class="postittext"><jsp:include page="../templates/postit.txt" flush="true" /></pre>
	</div>
</td>
</table>

<%@ include file="../initPage.jsp" %>
<%
  //returnPage specifies the source of the request ( Accounts/ Home Page ) 
  String returnPage = request.getParameter("return");
  
%>
<script type="text/javascript">
  function fillFrame(frameName,sourceUrl){
    window.frames[frameName].location.href=sourceUrl;
  }

  function reopen(){
    window.location.href='MyCFS.do?command=Home';
  }

  function reloadFrames(){
    window.frames['calendar'].location.href='MyCFS.do?command=MonthView&source=Calendar&inline=true&reloadCalendarDetails=true';
  }
</script>


<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <dhv:evaluate if="<%= SyncUtils.isOfflineMode(applicationPrefs) %>">
    <tr>
      <td>
        <table class="note" cellspacing="0"><tr>
          <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
          <td>
            <dhv:evaluate if="<%= SyncUtils.isSyncConflict(applicationPrefs) %>">
              <dhv:label name="login.offline.invalid.state">
                The Offline Concourse Suite Community Edition system is in an invalid state. Your prior sync was not successful
                and the database might be in an un-reliable state. You can continue to review your
                offline data, but will not be able to perform any future syncs and add, edit or delete
                data. You need to re-install the system by clicking on "Reload" on your Desktop Client.
	            </dhv:label>
            </dhv:evaluate>
            <dhv:evaluate if="<%= !SyncUtils.isSyncConflict(applicationPrefs) %>">
              <dhv:label name="login.offline.msg">
	              You are currently logged in using offline mode. Some of the features have been turned off intentionally and may
	              not be available during offline mode. When you regain connectivity, you can perform a sync with your Concourse Suite Community Edition
	              Server to be up-to-date.
	            </dhv:label><br /><br />
	          <dhv:label name="login.offline.lastSyncedOn">Last Synced on</dhv:label>: <%= syncClient.getAnchor() %>&nbsp;&nbsp;
	          <input type="button" value="<dhv:label name="global.button.Sync">Sync</dhv:label>" onClick="javascript:window.location.href='RequestSyncUpdates.do?command=Default'"/>
           </dhv:evaluate>
          </td>
        </tr></table>
      </td>
    </tr>
  </dhv:evaluate>
    <tr>
      <td>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <%-- User Selected Info --%>

  <%-- AdsJet users only --%>
  <dhv:permission name="products-view">
  <tr>
    <td colspan="2">
      <table class="pagedListHeader" cellspacing="0">
        <tr>
          <td align="center">
            <strong>Welcome to AdsJet.com.</strong>
          </td>
        </tr>
      </table>
      <table class="pagedListHeader2" cellspacing="0">
        <tr>
          <th>
            You are currently at the "My Home Page" tab in which you can 
            review the status of your pending ad requests and orders.
            From the "Products &amp; Services" tab you can review 
            publication information, as well as manage and place ads.
          </th>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:permission>
  <%-- Calendar and Details --%>
  <tr valign="top">
    <%-- Right cell --%>
    <td valign="top" height="50" width="100%"><%-- Change height to 100% once Safari works in all places --%>
<%--     <dhv:evaluate if="<%=((User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE_ASL")) || 
    		User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_REFERENTE_ASL") ) || 
    		User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA"))) && 
    		(eventiCircuitoCommerciale.size() > 0 || eventiCircuitoPrivato.size() > 0 || listaRegistrazioni.size() > 0)) %>">
    
      Calendar details
      <table height="380" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>

         <td> <%@ include
							file="../cessioni_include.jsp"%></td>
							
        </tr>
      </table>
    
     </dhv:evaluate> --%>
     </td>
  </tr>
</table>

    </td>

  </tr>
  
 
</table>
<%-- Next section --%>

<dhv:permission name="myhomepage-miner-view">
<br>


<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <td>
      <input type="hidden" name="command" value="Home">
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <form name="miner_select" type="get" action="MyCFS.do">
          <tr>
            <th width="60%" valign="center">
              <strong><dhv:label name="calendar.personalizedNewsAndEvents" param="amp=&amp;">Personalized Industry News &amp; Events</dhv:label></strong>
            </th>
            <td width="40%" style="text-align: right;" valign="center">
    	<% if (request.getParameter("industry") == null || request.getParameter("industry").equals("")) { %>
              <%=IndSelect.getHtmlSelect("industry",1)%>
      <% } else { %>
              <%=IndSelect.getHtmlSelect("industry",Integer.parseInt(request.getParameter("industry")))%>
       <%}%>
            </td>
          </tr>
        </form>
      </table>
    </td>
  </tr>
<%
	Iterator j = NewsList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
		while (j.hasNext()) {
      rowid = (rowid == 1?2:1);
      NewsArticle thisNews = (NewsArticle)j.next();
		%>      
  <tr class="row<%= rowid %>">
    <td width="11%" valign="center">
      <%= thisNews.getDateEntered() %>
    </td>
    <td width="100%" valign="center">
      <a href="<%= thisNews.getUrl() %>" target="_new"><%= thisNews.getHeadline() %></a>
    </td>
  </tr>
		<%}
	} else {%>
  <tr class="containerBody"><td><dhv:label name="calendar.noNewsFound">No news found.</dhv:label></td></tr>
	<%}%>
</table>
</dhv:permission>

<table>
<tr>
<td><%String context = pageContext.getRequest().getServletContext().getContextPath();
context = context.substring(context.indexOf("/")+1);
String note_rilascio="../note_rilascio_"+context+".txt";%>
<td><jsp:include page="<%= note_rilascio %>" flush="true" /></td>
</td>
</tr>
</table>
<div id="dialog" title="Dialog Title" style="display: none; height: 900px; width: 900px;" >
<table>
<tr><td>Nominativo: </td> <td> <%= User.getContactName() %></td></tr>
<tr><td>Recapito telefonico: </td> <td> <input type="text" name="telefono" id="telefono" value="<%=(User.getUserRecord().getContact().getPrimaryPhoneNumber()  != null) ? User.getUserRecord().getContact().getPrimaryPhoneNumber()  : "" %>"> </td></tr>
<tr><td>Recapito email: </td> <td> <input type="text" name="email" id="email" value="<%=(User.getUserRecord().getContact().getPrimaryEmailAddress() != null) ? User.getUserRecord().getContact().getPrimaryEmailAddress()  : "" %>"> </td></tr>
</table>
</div>
  <script>
  checkFirefox('<%=LoginBean.getMessage()%>');
  </script>
  
  
  