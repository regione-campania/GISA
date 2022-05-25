<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.NewsArticle,org.aspcfs.modules.mycfs.beans.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="NewsList" class="org.aspcfs.modules.accounts.base.NewsArticleList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<jsp:useBean id="LitaConcessioni" class="org.aspcfs.modules.molluschibivalvi.base.ConcessioniList" scope="request"/>
 <link href="css/nonconformita.css" rel="stylesheet" type="text/css" />
 
<script>

function viewControlliChiusuraUfficio(){

	document.getElementById("chiusura_ufficio").style.display="" ;
}

</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>


<table cellpadding="4" cellspacing="0" border="0" width="100%">  <tr><td>
<div class = "scroll">
<br><br>
<table class="details" cellpadding="4" cellspacing="0" border="0" width="100%">
	<thead>
	<tr><th colspan="6">Concessioni In Scadenza</th></tr>

  	<tr>
    	<th nowrap >
      		<strong><dhv:label name="">Num Concessione</dhv:label></strong>
    	</th>
    
    	<th nowrap >
        	<strong><dhv:label name="">data Concessione</dhv:label></strong>
    	</th>
    
    	<th nowrap >
          	<strong>Data Scadenza</strong>
		</th>
      	 
      	<th nowrap >
          	<strong>Zona di Produzione</strong>
		</th>
      
		<th nowrap >
          	<strong>Concessionario</strong>
		</th>
		<th nowrap >
          	<strong>Stato</strong>
		</th>
		</tr>
		</thead>
	
		<tbody>
		
		 <%
    Iterator jj = LitaConcessioni.iterator();

    if ( jj.hasNext() ) {
      int rowid = 0;
      int i =0;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      
  
      
      while (jj.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.molluschibivalvi.base.Concessione thisTic = (org.aspcfs.modules.molluschibivalvi.base.Concessione)jj.next();
        
  %>
    <tr class="row<%= rowid %>">
    
		<td width="10%" valign="top" nowrap>
			<%=thisTic.getNumConcessione()  %>
		</td>
		<td class="row<%= rowid %>">
	    <%=thisTic.getDataConcessioneasString() %>
	    </td>
	   <td class="row<%= rowid %>">
	    <%=thisTic.getDataScadenzaasString() %>
	    </td>
	<td class="row<%= rowid %>">
	    <a href = "MolluschiBivalvi.do?command=Details&orgId=<%=thisTic.getZona().getId() %>" ><%=thisTic.getZona().getName() %></a>
	    </td>
	  		<td class="row<%= rowid %>">
	   <a href = "Concessionari.do?command=Details&orgId=<%=thisTic.getConcessionario().getId() %>" > <%=thisTic.getConcessionario().getName() %></a>
	    </td>	
		<td class="row<%= rowid %>">
	    <%
	    Timestamp time_curr = new Timestamp(System.currentTimeMillis());
	    %>
	    
	    <%=(time_curr.after(thisTic.getDataScadenza())?"<font color = 'red'>SCADUTA</font>" :"<font color = 'orange'>IN SCADENZA</font>") %>
	    </td>

	</tr>
  <%}
      
   
    }
    else {
    	  %>
    	  
    	    <tr class="containerBody">
    	      <td colspan="6">
    	        <dhv:label name="">Nessuna concessione scade nei prossimi 3 mesi .</dhv:label>
    	      </td>
    	    </tr>
    	  <%
    	  }
    	  %>
    	 
		</tbody>
		
		</table>
		 </div>
		 
		 </td>
		 </tr>
		 
		
		
</table>


<br>
<br>


