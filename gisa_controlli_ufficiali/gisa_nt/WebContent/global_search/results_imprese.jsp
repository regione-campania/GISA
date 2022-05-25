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
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%>
<%@page import="org.aspcfs.modules.ricercaunica.base.RicercaOpu"%>
<%@page import="org.aspcfs.modules.ricercaunica.base.RicercaList"%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*,org.aspcfs.modules.global_search.base.* " %>



<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditList" class="org.aspcfs.modules.audit.base.AuditList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">

  <%-- Preload image rollovers for drop-down menu --%>
  
  <%! public static String fixString(String nome)
  {
	  String toRet = "";
	  if (nome == null)
		  return nome;
	  
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll("\"", "");
	  return toRet;
	  
  }%>
  

  function setDecrizioneImpresa(idimpresa,descrizioneimpresa,idTipologia)
  { 
  if (window.opener!=null)
  	{
	  var orgIdOriginale = -1;
	  if (window.opener.document.getElementsByName("orgId")!=null && window.opener.document.getElementsByName("orgId")[0]!=null)
		  orgIdOriginale = window.opener.document.getElementsByName("orgId")[0].value;
	  
	  if (window.opener.document.getElementsByName("idStabilimento")!=null && window.opener.document.getElementsByName("idStabilimento")[0]!=null)
		  orgIdOriginale = window.opener.document.getElementsByName("idStabilimento")[0].value;
	
	 if (idimpresa==orgIdOriginale){
		 alert("Impossibile selezionare lo stesso operatore per la non conf. a carico di terzi!");
	 }
	 else {
  		window.opener.document.getElementById("id_impresa_sanzionata").value=idimpresa;
  		window.opener.document.getElementById("id_tipologia_operatore").value=idTipologia;

	  	window.opener.document.getElementById("descrizione_impresa_sanzionata").value=descrizioneimpresa;
  		window.opener.document.getElementById("descrizione_impresa_sanzionata").readOnly="true";
  		window.opener.document.getElementById("descrizione_impresa_sanzionata").style.display="";
  		if(window.opener.document.getElementById("descrizione_impresa_sanzionata").onchange!=null)
  			window.opener.document.getElementById("descrizione_impresa_sanzionata").onchange();

  		if (window.opener.document.getElementById("ncterzi_prov_gravi")!=null)
  			window.opener.document.getElementById("ncterzi_prov_gravi").style.display="block";
	  	
	  	window.close();
  	}
  	}
  	
  }
  
  function setDecrizioneImpresaNonTrovata()
  {
  if (window.opener!=null)
  	{
	  
	  var idimpresa = 9999999;
	  var idTipologia = -1;
	  
	  var descrizioneimpresa = document.getElementById("nome");
	  if (descrizioneimpresa==null || descrizioneimpresa.value=='')
		  alert("Inserire un nome per l'impresa!");
	  else
		  {
	 window.opener.document.getElementById("id_impresa_sanzionata").value=idimpresa;
  	window.opener.document.getElementById("id_tipologia_operatore").value=idTipologia;

  	window.opener.document.getElementById("descrizione_impresa_sanzionata").value=descrizioneimpresa.value;
  	window.opener.document.getElementById("descrizione_impresa_sanzionata").readOnly="true";
  	window.opener.document.getElementById("descrizione_impresa_sanzionata").style.display="";
		  
  	window.close();
  	}
  	}
  }
  
  function openAggiungiOpFuoriRegione(){
		var res;
		var result;

		window.location.href='OpnonAltrove.do?command=Add&tipoD=Operatori&popUp=true';
		//window.location.href='OperatoriFuoriRegione.do?command=Add&tipoD=Operatori&popUp=true';
} 
		
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0" >
<tr>
<td>
<dhv:label name=" ">Risultati Ricerca Imprese</dhv:label>
</td>
</tr>
</table>

<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0" style="width: 100%">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOpuListInfo"/>
<% int columnCount = 0; %>

<link rel="stylesheet" type="text/css" href="css/ext-all.css" />

<script type="text/javascript" src="javascript/ext-base.js"></script>
<script type="text/javascript" src="javascript/ext-all.js"></script>
<script type="text/javascript" src="javascript/TableGrid.js"></script>
<script type="text/javascript" >
Ext.onReady(function(){
        
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tabella-lista-operatori", {stripeRows: true} );
        grid.render();
}
);
</script>

<table cellpadding="8" id="tabella-lista-operatori" cellspacing="0" width="100%" border="1" >
	<thead>
  	<tr>
    	<th  <% ++columnCount; %>>
      		<strong><dhv:label name="">Ragione Sociale</dhv:label></strong>
    	</th>
    
    	<th <% ++columnCount; %>>
        	<strong><dhv:label name="">ASL</dhv:label></strong>
    	</th>
    
    	
      	
      	 
       
		<th  <% ++columnCount; %>>
          	<strong>C.F Titolare</strong>
		</th>
		

		<th  <% ++columnCount; %>>
          	<strong>P.Iva</strong>
		</th>
		
        <th  <% ++columnCount; %>>
          	<strong>Stato</strong>
		</th>
		
		<th  <% ++columnCount; %>>
          	<strong>Targa</strong>
		</th>
	
		
		<th  <% ++columnCount; %>>
      		<strong>Comune</strong>
    	</th>
   
        <th  <% ++columnCount; %>>
          <strong>Provincia</strong>
		</th>
  </tr>
  </thead>
  <tbody>
<%
 RicercaList OrgList = (RicercaList)request.getAttribute("OrgList");
	Iterator j = OrgList.iterator();
	
	ArrayList<Integer> listaRiferimentoId = new ArrayList<Integer>();
	
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    RicercaOpu thisOrg = (RicercaOpu)j.next();
    if (!listaRiferimentoId.contains(thisOrg.getRiferimentoId())) {
    	listaRiferimentoId.add(thisOrg.getRiferimentoId());
%>
 
    <tr>
    <!-- Switch sulla tipologia per il link??? -->
	<td>
	
	<%
	Ticket temp = new Ticket();
	
	temp.setTipologia_operatore(thisOrg.getTipologia());
	%>

    	<a href="javascript:setDecrizioneImpresa(<%=thisOrg.getRiferimentoId() %>,'<%=fixString(thisOrg.getRagioneSociale()) %>',<%=thisOrg.getTipologia()%>)">	<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a>
		
	</td>
		
    <td >
       <%= toHtml(thisOrg.getAsl()) %>
    </td>
    
   
    
  
    
	<td >
		 <% if(thisOrg.getCodiceFiscaleSoggettoFisico() ==null || thisOrg.getCodiceFiscaleSoggettoFisico().equals("") || thisOrg.getCodiceFiscaleSoggettoFisico().contains("null") ) { %> 
       		<%= toHtml("N.D") %>
       	 <% } else { %>	
       		<%= toHtml(thisOrg.getCodiceFiscaleSoggettoFisico()) %>
       	 <% } %>
    </td>
    
  
  
	
	
	<td >
       <%= toHtml(thisOrg.getPartitaIva()) %>
    </td>
	
	<td >
    	   <%= toHtml(thisOrg.getStato()) %>
	</td>
	
	<td >
       <%= toHtml(thisOrg.getTarga()) %>
    </td>

	
	<td>
	<%= (thisOrg.getComune()) %>
	</td>
    <td >
      <%= (thisOrg.getProvSedeProduttiva()) %>
	</td>
  </tr>
  <% }
	}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOpuListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="">Nessun operatore trovato con i parametri di ricerca specificati.</dhv:label>
      
<br/>  <br />
    <a href="javascript:openAggiungiOpFuoriRegione()">Inserisci operatore</a>
    
      <br />  <br />
      <a href="GlobalSearch.do?command=SearchFormImprese"><dhv:label name="">Modifica la Ricerca</dhv:label></a>. 
    </td>
 </tr>
 
<!--   <tr> -->
<!--     <td> -->
<!-- 		<a href="javascript:setDecrizioneImpresaNonTrovata()">SCEGLI</a> -->
<!-- 	</td> -->
		
<!--     <td >Ragione sociale: <input type="text" id="nome" name="nome" value=""></td> -->
<!--     <td ></td> -->
<!-- 	<td ></td> -->
<!-- 	<td > </td> -->
<!--   <td ></td> -->
<!-- 	<td ></td> -->
<!-- 	<td ></td> -->
<!-- <td ></td> -->
<!--     <td ></td> -->
<!--   </tr> -->
 
 
<%}%>
</tbody>
</table>
<br />
<dhv:pagedListControl object="SearchOpuListInfo" tdClass="row1"/>

