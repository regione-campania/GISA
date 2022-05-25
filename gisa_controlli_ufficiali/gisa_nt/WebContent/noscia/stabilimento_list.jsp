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
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.ricercaunica.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="StabilimentiList" class="org.aspcfs.modules.ricercaunica.base.RicercaList" scope="request"/>
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoOperatore" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="AltriStabilimenti.do?command=Default"><dhv:label name="">Altri stabilimenti</dhv:label></a> >
<a href="GisaNoScia.do?command=SearchForm"><dhv:label name="">Gestione No-Scia Ricerca</dhv:label></a> >
<dhv:label name="">Risultato Ricerca</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOpuListInfo"/>
<% int columnCount = 0; %>

<table cellpadding="8" cellspacing="0" border="0" style="width: 100%" class="pagedList">
  <tr>
  
       
    <th nowrap <% ++columnCount; %>>
     <div align="center">
      <dhv:label name="">Nome/Ditta/Ragione Sociale</dhv:label>
    	</div>  
    </th>  
    
    <th   nowrap <% ++columnCount; %>>
        <div align="center">
      <dhv:label name="">PARTITA IVA</dhv:label>
     	</div>
    </th>

     <th   nowrap <% ++columnCount; %>>
        <div align="center">
      <dhv:label name="">ASL</dhv:label>
     	</div>
    </th>
    
     <th   nowrap <% ++columnCount; %>>
       <div align="center">
      <dhv:label name="">Sede produttiva</dhv:label>
      </div>
    </th>
    
        
         <th <% ++columnCount; %>>
   		  <div align="center">
          <strong><dhv:label name="">Norma di Riferimento / Tipologia</dhv:label></strong>
          </div>
        </th>  
        
      
     
         <th   <% ++columnCount; %>>
          <div align="center">
          <strong><dhv:label name="">Attivita</dhv:label></strong>
          </div>
        </th>  
        
    
         <th   nowrap <% ++columnCount; %>>
          <div align="center">
      <dhv:label name="">Stato</dhv:label>
      </div>
    </th>
    
  
  </tr>
<%

	
	HashMap<String,ArrayList<RicercaOpu>> ordPerRifId = new HashMap<String,ArrayList<RicercaOpu>>();
	
	Iterator j0 = StabilimentiList.iterator();
	if ( j0.hasNext()  ) {
		
		
		
    //ordino i risultati per numero getRiferimentoId
    while (j0.hasNext()) 
    {
    	
    	RicercaOpu org0 = (RicercaOpu)j0.next();
    	String rifId = org0.getRiferimentoId()+""+org0.getRiferimentoIdNomeCol();
    	
    	System.out.print("\n rag:"+org0.getRagioneSociale()+" rifid:"+rifId);
    	
    	if(!ordPerRifId.containsKey(rifId))
    	{
    		ordPerRifId.put(rifId,new ArrayList<RicercaOpu>());
    		System.out.println("E' IL PRIMO");
    	}
    	
    	ordPerRifId.get(rifId).add(org0);
    }
    
    
  
    	
    
    
    int rowid = 0;
    int i = 0;	
    Iterator j = ordPerRifId.keySet().iterator();
    String lastRifId = "";
    
    boolean campoToShow = true;
    
    
    while(j.hasNext())
    {
    	 i++;
    	String key = (String)j.next();
    	ArrayList<RicercaOpu> lista =(ArrayList<RicercaOpu>) ordPerRifId.get(key);
    	
    	rowid = (rowid != 1 ? 1 : 2);
    int numIterazioni = 1;	
   for(RicercaOpu thisOrg :lista)
   {
   
    	
%>

<%Ticket temp = new Ticket();
temp.setTipologia_operatore(thisOrg.getTipologia());
	%>
	

  <tr class="row<%= rowid %>" >
      
    <%if(numIterazioni==1){ %>
	<td  valign="center" align="center" style="width: 15%;" rowspan="<%=lista.size()%>">
	<div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
<%-- 	<a onclick="intercettaAction('<%=temp.getURlDettaglioanagrafica()+".do?command=Details&"+thisOrg.getRiferimentoIdNome()+"="+thisOrg.getRiferimentoId() %>')" id="<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %>" href="#"><%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a> --%>
		<% if(temp.getURlDettaglioanagrafica().equalsIgnoreCase("OpuStab")) {
			//if (1==0){%>
			<a  id="<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %>" href="GestioneAnagraficaAction.do?command=Details&stabId=<%=thisOrg.getRiferimentoId()%>"><%=  toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a>
		<%} else { %>
		<a  id="<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %>" href="<%=temp.getURlDettaglioanagrafica() %>.do?command=Details&idFarmacia=<%=thisOrg.getRiferimentoId() %>&opId=<%=thisOrg.getRiferimentoId() %>&<%=thisOrg.getRiferimentoIdNome()%>=<%=thisOrg.getRiferimentoId()%><%=(thisOrg.getTipologia()== 1999) ? "&container=archiviati" : "" %>"><%=  toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a>	
	 	<%} %>
	
	</div>
	</td>
	<%}
    
    %>
	
	 <%if(numIterazioni==1){ %>
	<td valign="center" align="center"  nowrap style="width: 10%;" rowspan="<%=lista.size()%>">
	<div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
	<%=  toHtml2(thisOrg.getPartitaIva())   %>
	</div>
	</td>
	<%} %>
	
	 <%if(numIterazioni==1){ %>
	<td valign="center" align="center"  nowrap style="width: 5%;" rowspan="<%=lista.size()%>">
	<div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
	<%=  toHtml2(thisOrg.getAsl())   %>
	</div>
	</td>
	<%} %>
	
	 <%if(numIterazioni==1){ %>
	<td valign="center" align="center"  nowrap style="width: 10%;" rowspan="<%=lista.size()%>">
	<div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
	<%= (thisOrg.getIndirizzoSedeProduttiva()!=null)? ( toHtml4(thisOrg.getIndirizzoSedeProduttiva().replaceAll(",", "<br>"))   ) :"N.D."  %>
	</div>
	</td>
	<%} %>
	
	
    <td valign="center" align="center" nowrap style="width: 5%;">
    <div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
   <%= toHtml2(thisOrg.getNorma()) %>
  
   </div>
    </td>
    
        <td valign="left" align="left"  nowrap style="width: 15px;" title="<%=  (thisOrg.getAttivita()!=null ) ? ( toHtml2(thisOrg.getAttivita()) ) : "" %>">
        <%
        if(thisOrg.getAttivita()!=null && thisOrg.getAttivita().contains("->")  )
        {
        String[] lineaA = thisOrg.getAttivita().split("->");
        for(int indice=0;indice<lineaA.length;indice++)
        {
        	
        	if(lineaA[indice].length()>50)
        	{
        	out.print(lineaA[indice].substring(0,50)+" ..." +"<br>");
        	}
        	else
        		out.print(lineaA[indice] +"<br>");
        }
        }
        else
        {
        	if(thisOrg.getAttivita()!=null && thisOrg.getAttivita().length()>50)
        	{
        	out.print(thisOrg.getAttivita().substring(0,40)+" ..." +"<br>");
        	}
        	else
        		if(thisOrg.getAttivita()!=null )
        			out.print(thisOrg.getAttivita() +"<br>");
        		else
        			out.print("LINEA NON SPECIFICATA");
        }
        if(thisOrg.getAttivita()!=null )
        {
       %>
        
        <img  style="width:15px" src="images/questionmark.png"></img>
        <%} %>
        </td>
      
      	<td valign="center" align="center"  nowrap style="width: 15%;"><%= toHtml2(thisOrg.getStato())  %> <%= toHtml2(thisOrg.getStatoImpresa())  %> </td>
      	
  
	
  </tr>
       	
   
    <script>
    //VALORIZZO LA STRUTTURA DATI CHE MANTIENE TUTTI NUM REG STABILIMENTI TROVATI
    
    	
    	window.numRegTrovati.push(
    			{
    				
    				
    				numReg : '<%=thisOrg.getNumeroRegistrazione() %>' 
    				
    			}
    	);
    
    </script>  	 
<%
numIterazioni++;
   }}%>

<%} else {%>

  <tr class="containerBody">
    <td colspan="<%= SearchOpuListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
     Nessun risultato trovato nell'anagrafica selezionata.<br />
      <a href="GisaNoScia.do?command=SearchForm">Torna alla ricerca</a>.
    </td>
  </tr>
<%}%>
</table>
 
 <BR>
 
 
<dhv:pagedListControl object="SearchOpuListInfo" tdClass="row1"/>


<!-- <div id = "dialogGestioneScia"> -->

<!-- </div> -->