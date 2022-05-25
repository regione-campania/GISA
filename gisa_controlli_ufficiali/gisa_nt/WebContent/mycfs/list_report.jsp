<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.sintesis.base.SintesisStabilimento"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.NewsArticle,org.aspcfs.modules.mycfs.beans.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*" %>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%><jsp:useBean id="NewsList" class="org.aspcfs.modules.accounts.base.NewsArticleList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="NewUserList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="IndSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CUAperti" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="CUSorveglianza" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="CUChiusiFU" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>

<jsp:useBean id="CUApertiASL" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="CUSorveglianzaASL" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="CUChiusiFUASL" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>

<jsp:useBean id="CuChiusuraUfficio" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="NCinScadenza" class="org.aspcfs.modules.followup.base.TicketList" scope="request"/>


 <link href="css/nonconformita.css" rel="stylesheet" type="text/css" />
 
 
<script>

function viewControlliChiusuraUfficio(){

	document.getElementById("chiusura_ufficio").style.display="" ;
}
function viewControlliASL(){

	document.getElementById("cuASLUO").style.display="" ;
}
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></script>
<%@ include file="../initPage.jsp" %>

<%
if (CuChiusuraUfficio.size()>0)
	{
	%>

	<center><strong>
	<a class="ovalbutton"   href = "#chiusura_ufficio" id="link" onclick="javascript : viewControlliChiusuraUfficio()" style="text-align: center; " > <span>Controlli che verranno chiusi d'ufficio </span></a>
	</strong>
	</center>

	<%
	}
%>
<dhv:permission name="myhomepage-scadenzario-cuasl-view"><a class="ovalbutton"   href = "#" id="link" onclick="javascript : viewControlliASL()" style="text-align: center; " > <span>Controlli Unità Operative </span></a></dhv:permission>

<table cellpadding="4" cellspacing="0" border="0" width="100%">  <tr><td>
<div class = "scroll">
<br><br>
<table class="details" cellpadding="4" cellspacing="0" border="0" width="100%">
	<thead>
	<tr><th colspan="7">Controlli In Sorveglianza prossimi alla Scadenza e Controlli Aperti effettuati dall'utente</th></tr>

  	<tr>
    	<th nowrap >
      		<strong><dhv:label name="">Tipologia Operatore</dhv:label></strong>
    	</th>
    
    	<th nowrap >
        	<strong><dhv:label name="">Ragione Sociale</dhv:label></strong>
    	</th>
    
    	<th nowrap >
          	<strong>Controllo del</strong>
		</th>
      	 
      	<th nowrap >
          	<strong>Tipo Controllo</strong>
		</th>
      
		<th nowrap >
          	<strong>Punteggio</strong>
		</th>
		<th nowrap >
          	<strong>Stato</strong>
		</th>
		
		<th nowrap >
          	<strong>Id Controllo</strong>
		</th>
		
		</tr>
		</thead>
	
		<tbody>
		
		 <%
    Iterator jj = CUAperti.iterator();
	Iterator yy = CUSorveglianza.iterator();
	
	Iterator kk = CUChiusiFU.iterator();
	Iterator ncIterator = NCinScadenza.iterator();

    if ( jj.hasNext() || yy.hasNext() || kk.hasNext() ) {
      int rowid = 0;
      int i =0;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      
    while (yy.hasNext()) {
          i++;
          rowid = (rowid != 1?1:2);
          org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)yy.next();
          
    %>
      <tr class="row<%= rowid %>">
      
  		<td width="10%" valign="top" nowrap>
  			<%=thisTic.getTipologiaOperatoreDescr()%>
  		</td>
  		<td class="row<%= rowid %>">
  	    <%=thisTic.getRagioneSociale() %>
  	    </td>
  	    <td width="10%" valign="top" nowrap>
  	    <%if (thisTic.getAssignedDate()!=null) {%>
  	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
  	    	<%}
  	    else
  	    {%>
  			&nbsp;
      	<%
  	    	}
  	    %>
  			
  		</td>
      	
  		<%if(thisTic.getTipoCampione() > -1) {%>
  		
  		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
  		
  
  		</td>
  		<%}else{%>
  		<td>-
  		</td>
  		<%} %>
  		
  		<%if(thisTic.getPunteggio() >= 3) {%>
  		<td valign="top"><%= thisTic.getPunteggio() %></td>	
  		<%}else{%>
  		<td><%=thisTic.getPunteggio()%> - Esito Controllo Ufficiale Favorevole
  		</td>
  		<%} %>
  		<td>
  		<%if ( thisTic.getDataProssimoControllo()!= null && thisTic.getDataProssimoControllo().before((new Timestamp(System.currentTimeMillis())) ) ) 
  		{%>
  			<%="<font color = 'red'>Controllo Scaduto.Prossimo Controllo entro il "+sdf.format(new java.util.Date(thisTic.getDataProssimoControllo().getTime()))+"</font>" %>
  		<%} 
  		else
  		{if ( thisTic.getDataProssimoControllo()!= null  ) 
  	  		{%>
  				<%="<font color = 'red'>Prossimo Controllo entro il "+sdf.format(new java.util.Date(thisTic.getDataProssimoControllo().getTime()))+"</font>" %>
  		
  			<%
  	  		}
  		}
  		
  		%>
  		</td>
  		
  		<td><%
  		if( (thisTic.getTipologiaOperatore() == 1) && thisTic.getIdMacchinetta()>0) 
  			{
				%> <a id="<%=thisTic.getId()%>"
								href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a> <%
  			}
  		else if( thisTic.getTipologiaOperatore() == 999) 
			{
			%><a id="<%=thisTic.getId()%>"
							href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
			}
  		else if( thisTic.getTipologiaOperatore() == 2000) 
		{
		%> <a id="<%=thisTic.getId()%>"
						href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
		}
  			else
  			{
  			%> <a id="<%= toHtml(thisTic.getIdControlloUfficiale()) %>"
								href="<%=thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>&assetId=<%=thisTic.getAssetId() %>"><%= toHtml(thisTic.getIdControlloUfficiale()) %></a> <%} %>

							</td>

  	</tr>
        

    <%}
      
      while (jj.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)jj.next();
        
  %>
    <tr class="row<%= rowid %>">
    
		<td width="10%" valign="top" nowrap>
			<%=thisTic.getTipologiaOperatoreDescr()  %>
		</td>
		<td class="row<%= rowid %>">
	    <%=thisTic.getRagioneSociale() %>
	    </td>
	    <td width="10%" valign="top" nowrap>
	    <%if (thisTic.getAssignedDate()!=null) {%>
	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
	    	<%}
	    else
	    {%>
			&nbsp;
    	<%
	    	}
	    %>
			
		</td>
    	
		<%if(thisTic.getTipoCampione() > -1) {%>
		
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
		
	
		</td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		
		<%if(thisTic.getPunteggio() >= 3) {%>
		<td valign="top"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td><%=thisTic.getPunteggio()%> - Esito Controllo Ufficiale Favorevole
		</td>
		<%} %>
		<td>
			<%if (thisTic.haveSottoAttivita())
				{
					out.print("<font color = 'orange'>Controllo Aperto. Sottoattività Si</font>");
					
				}
			else
			{
				out.print("<font color = 'orange'>Controllo Aperto. SottoAttività No</font>");
			}
			 %>
  		
		</td>
		
		 <td>
	  		<% if( (thisTic.getTipologiaOperatore() == 1)) {
	  			if(thisTic.getIdMacchinetta()>0)
	  			{
					%>
					<a id="<%=thisTic.getId()%>" href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a>
					<%
	  				
	  			}
	  			else
	  			{
	  			%> 
  				<a id="<%=thisTic.getId()%>" href="AccountVigilanza.do?command=TicketDetails&id=<%=thisTic.getId()%>&orgId=<%=thisTic.getOrgId()%>"><%=thisTic.getId()%></a>
  			<%} } 
	  		else if( thisTic.getTipologiaOperatore() == 999) 
			{
			%> <a id="<%=thisTic.getId()%>"
							href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
			}
	  		else if( thisTic.getTipologiaOperatore() == 2000) 
			{
			%> <a id="<%=thisTic.getId()%>"
							href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
			}
	  		else
  			{%>
  				<a id="<%= toHtml(thisTic.getIdControlloUfficiale()) %>"
								href="<%=thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>&assetId=<%=thisTic.getAssetId() %>"><%= toHtml(thisTic.getIdControlloUfficiale()) %></a>
  				
  			<%}%>
  		</td>
		

	</tr>
  <%}
      
      
      //Blocco aggiunto
      while (kk.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)kk.next();
        
  %>
    <tr class="row<%= rowid %>">
    
		<td width="10%" valign="top" nowrap>
			<%=thisTic.getTipologiaOperatoreDescr()  %>
		</td>
		<td class="row<%= rowid %>">
	    <%=thisTic.getRagioneSociale() %>
	    </td>
	    <td width="10%" valign="top" nowrap>
	    <%if (thisTic.getAssignedDate()!=null) {%>
	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
	    	<%}
	    else
	    {%>
			&nbsp;
    	<%
	    	}
	    %>
			
		</td>
    	
		<%if(thisTic.getTipoCampione() > -1) {%>
		
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
		
		<%if(thisTic.getTipoCampione()==3){%>
		
		<%= " - " + AuditTipo.getSelectedValue(thisTic.getAuditTipo()) %>
		
	
		
		<%} %>
		
		</td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		
		<%if(thisTic.getPunteggio() >= 3) {%>
		<td valign="top"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td><%=thisTic.getPunteggio()%> - Esito Controllo Ufficiale Favorevole
		</td>
		<%} %>
		<td>
		<%
			out.print("<font color = 'green'>Controllo Chiuso con Follow Up</font>");
		%>
		</td>
		
		 <td>
	  		<% if( (thisTic.getTipologiaOperatore() == 1)) {
	  			if(thisTic.getIdMacchinetta()>0)
	  			{
					%>
					<a id="<%=thisTic.getId()%>" href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a>
					<%
	  				
	  			}
	  			else
	  			{
	  			%> 
  				<a id="<%=thisTic.getId()%>" href="AccountVigilanza.do?command=TicketDetails&id=<%=thisTic.getId()%>&orgId=<%=thisTic.getOrgId()%>"><%=thisTic.getId()%></a>
  			<%} } 
	  		else if( thisTic.getTipologiaOperatore() == 999) 
			{
			%> <a id="<%=thisTic.getId()%>"
							href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
			}
	  		else if( thisTic.getTipologiaOperatore() == 2000) 
			{
			%> <a id="<%=thisTic.getId()%>"
							href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
			}
	  		else {%>
  			
  			<a id="<%= toHtml(thisTic.getIdControlloUfficiale()) %>"
				href="<%=thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>&assetId=<%=thisTic.getAssetId() %>"><%= toHtml(thisTic.getIdControlloUfficiale()) %></a>
  			<% }%>
  		</td>
		

	</tr>
       
    <%}
    }
    else {
    	  %>
    	  
    	    <tr class="containerBody">
    	      <td colspan="7">
    	        <dhv:label name="">Nessun Controllo Ufficiale Risulta aperto da piu di 20 giorni dalla data di apertura.</dhv:label>
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

<br><br>
<div id = "cuASLUO" class = "scroll1" style="display:none">
<table cellpadding="4" cellspacing="0" border="0" width="100%"  >  <tr><td>
<div class = "scroll">
<br><br>
<table class="details" cellpadding="4" cellspacing="0" border="0" width="100%">
	<thead>
	<tr><th colspan="8">Controlli In Sorveglianza prossimi alla Scadenza e Controlli Aperti effettuati dall'intera ASL</th></tr>

  	<tr>
    	<th nowrap >
      		<strong><dhv:label name="">Tipologia Operatore</dhv:label></strong>
    	</th>
    
    	<th nowrap >
        	<strong><dhv:label name="">Ragione Sociale</dhv:label></strong>
    	</th>
    
    	<th nowrap >
          	<strong>Controllo del</strong>
		</th>
      	 
      	<th nowrap >
          	<strong>Tipo Controllo</strong>
		</th>
      
		<th nowrap >
          	<strong>Punteggio</strong>
		</th>
		<th nowrap >
          	<strong>Stato</strong>
		</th>
		
		<th nowrap >
          	<strong>Id Controllo</strong>
		</th>
		
		<th nowrap >
          	<strong>Unità Operativa</strong>
		</th>
		</tr>
		</thead>
	
		<tbody>
		
		 <%
    Iterator CUApertiASLIterator = CUApertiASL.iterator();
	Iterator CUSorveglianzaASLIterator = CUSorveglianzaASL.iterator();
	
	Iterator CUChiusiFUASLIterator = CUChiusiFUASL.iterator();
	//Iterator ncIterator = NCinScadenza.iterator();

    if ( CUApertiASLIterator.hasNext() || CUSorveglianzaASLIterator.hasNext() || CUChiusiFUASLIterator.hasNext() ) {
      int rowid = 0;
      int i =0;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      
    while (CUSorveglianzaASLIterator.hasNext()) {
          i++;
          rowid = (rowid != 1?1:2);
          org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)CUSorveglianzaASLIterator.next();
          
    %>
      <tr class="row<%= rowid %>">
      
  		<td width="10%" valign="top" nowrap>
  			<%=thisTic.getTipologiaOperatoreDescr()%>
  		</td>
  		<td class="row<%= rowid %>">
  	    <%=thisTic.getRagioneSociale() %>
  	    </td>
  	    <td width="10%" valign="top" nowrap>
  	    <%if (thisTic.getAssignedDate()!=null) {%>
  	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
  	    	<%}
  	    else
  	    {%>
  			&nbsp;
      	<%
  	    	}
  	    %>
  			
  		</td>
      	
  		<%if(thisTic.getTipoCampione() > -1) {%>
  		
  		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
  		
  
  		</td>
  		<%}else{%>
  		<td>-
  		</td>
  		<%} %>
  		
  		<%if(thisTic.getPunteggio() >= 3) {%>
  		<td valign="top"><%= thisTic.getPunteggio() %></td>	
  		<%}else{%>
  		<td><%=thisTic.getPunteggio()%> - Esito Controllo Ufficiale Favorevole
  		</td>
  		<%} %>
  		<td>
  		<%if ( thisTic.getDataProssimoControllo()!= null && thisTic.getDataProssimoControllo().before((new Timestamp(System.currentTimeMillis())) ) ) 
  		{%>
  			<%="<font color = 'red'>Controllo Scaduto.Prossimo Controllo entro il "+sdf.format(new java.util.Date(thisTic.getDataProssimoControllo().getTime()))+"</font>" %>
  		<%} 
  		else
  		{if ( thisTic.getDataProssimoControllo()!= null  ) 
  	  		{%>
  				<%="<font color = 'red'>Prossimo Controllo entro il "+sdf.format(new java.util.Date(thisTic.getDataProssimoControllo().getTime()))+"</font>" %>
  		
  			<%
  	  		}
  		}
  		
  		%>
  		</td>
  		
  		<td><%
  		if( (thisTic.getTipologiaOperatore() == 1) && thisTic.getIdMacchinetta()>0) 
  			{
				%> <a id="<%=thisTic.getId()%>"
								href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a> <%
  			}
  		else if( thisTic.getTipologiaOperatore() == 999) 
		{
		%> <a id="<%=thisTic.getId()%>"
						href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
		}
  		else if( thisTic.getTipologiaOperatore() == 2000) 
		{
		%> <a id="<%=thisTic.getId()%>"
						href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
		}
  			else
  			{
  			%> <a id="<%= toHtml(thisTic.getIdControlloUfficiale()) %>"
								href="<%=thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>&assetId=<%=thisTic.getAssetId() %>"><%= thisTic.getId() %></a> 
			<%} %>

		</td>
		<td>
		<%
			//System.out.println("Tipo campione ticket : "+thisTic.getTipoCampione());
			if (thisTic.getTipoCampione()==4) {
				ArrayList<Piano> pianiUo = thisTic.getPianoMonitoraggio();
				if (!pianiUo.isEmpty()) {
					Iterator<Piano> iteratorPiani = pianiUo.iterator();
					while (iteratorPiani.hasNext()){
						Piano piano = iteratorPiani.next();
						out.println(piano.getDescrizione()+"  <br/> <b>Per conto di:</b> "+piano.getDesc_uo());
					}
				}
				else {
					HashMap<Integer,OiaNodo> listaUoIspezioni = thisTic.getLista_uo_ispezione();
					Iterator<Integer> iteratorIspezioni = listaUoIspezioni.keySet().iterator();
					while (iteratorIspezioni.hasNext())
					{
						Integer tipoIspezione = iteratorIspezioni.next();
						out.println(TipoIspezione.getSelectedValue(tipoIspezione)+"  <br/> <b>Per conto di:</b> "+listaUoIspezioni.get(tipoIspezione).getDescrizione_lunga());
					}	
				}
			}else 
				if((thisTic.getLista_unita_operative()!=null  && ! thisTic.getLista_unita_operative().isEmpty() && thisTic.getLista_unita_operative().size()>0))
				{
				for(OiaNodo uo : thisTic.getLista_unita_operative() ){
					out.println(uo.getDescrizione_lunga());					
				}
				}
				%>&nbsp;
			</td>
        
</tr>
    <%}
      
      while (CUApertiASLIterator.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)CUApertiASLIterator.next();
        
  %>
    <tr class="row<%= rowid %>">
    
		<td width="10%" valign="top" nowrap>
			<%=thisTic.getTipologiaOperatoreDescr()  %>
		</td>
		<td class="row<%= rowid %>">
	    <%=thisTic.getRagioneSociale() %>
	    </td>
	    <td width="10%" valign="top" nowrap>
	    <%if (thisTic.getAssignedDate()!=null) {%>
	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
	    	<%}
	    else
	    {%>
			&nbsp;
    	<%
	    	}
	    %>
			
		</td>
    	
		<%if(thisTic.getTipoCampione() > -1) {%>
		
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
		
	
		</td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		
		<%if(thisTic.getPunteggio() >= 3) {%>
		<td valign="top"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td><%=thisTic.getPunteggio()%> - Esito Controllo Ufficiale Favorevole
		</td>
		<%} %>
		<td>
			<%if (thisTic.haveSottoAttivita())
				{
					out.print("<font color = 'orange'>Controllo Aperto. Sottoattività Si</font>");
					
				}
			else
			{
				out.print("<font color = 'orange'>Controllo Aperto. SottoAttività No</font>");
			}
			 %>
  		
		</td>
		
		 <td>
	  		<%	
	  			if( (thisTic.getTipologiaOperatore() == 1)) {
	  			if(thisTic.getIdMacchinetta()>0)
	  			{
					%>
					<a id="<%=thisTic.getId()%>" href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a>
					<%
	  				
	  			}
	  			else
	  			{
	  			%> 
  				<a id="<%=thisTic.getId()%>" href="AccountVigilanza.do?command=TicketDetails&id=<%=thisTic.getId()%>&orgId=<%=thisTic.getOrgId()%>"><%=thisTic.getId()%></a>
  			<%} }
	  			else if( thisTic.getTipologiaOperatore() == 999) 
				{
				%> <a id="<%=thisTic.getId()%>"
								href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
				}
	  	  		else if( thisTic.getTipologiaOperatore() == 2000) 
	  			{
	  			%> <a id="<%=thisTic.getId()%>"
	  							href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
	  			}
	  			else
  				{
  				%>
  				<a id="<%= toHtml(thisTic.getIdControlloUfficiale()) %>"
								href="<%=thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>&assetId=<%=thisTic.getAssetId() %>"><%= toHtml(thisTic.getIdControlloUfficiale()) %></a>
  				<%
  				}%>
  		</td>
  		<td>
		<%			
			if (thisTic.getTipoCampione()==4) {
				ArrayList<Piano> pianiUo = thisTic.getPianoMonitoraggio();
				if (!pianiUo.isEmpty()){
					Iterator<Piano> iteratorPiani = pianiUo.iterator();
					while (iteratorPiani.hasNext()){
							Piano piano = iteratorPiani.next();
							out.println(piano.getDescrizione()+"  <br/> <b>Per conto di:</b> "+piano.getDesc_uo());
						}
					}else {
						HashMap<Integer,OiaNodo> listaUoIspezioni = thisTic.getLista_uo_ispezione();
						Iterator<Integer> iteratorIspezioni = listaUoIspezioni.keySet().iterator();
						while (iteratorIspezioni.hasNext())
						{
							Integer tipoIspezione = iteratorIspezioni.next();
							out.println(TipoIspezione.getSelectedValue(tipoIspezione)+"  <br/> <b>Per conto di:</b> "+listaUoIspezioni.get(tipoIspezione).getDescrizione_lunga());
						}
					}
			} else if((thisTic.getLista_unita_operative()!=null  && ! thisTic.getLista_unita_operative().isEmpty() && thisTic.getLista_unita_operative().size()>0)) {
				for(OiaNodo uo : thisTic.getLista_unita_operative() ) {
					out.println(uo.getDescrizione_lunga());					
				}
			}
		%>&nbsp;
		</td>

	</tr>
  <%}
      
      
      //Blocco aggiunto
      while (CUChiusiFUASLIterator.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)CUChiusiFUASLIterator.next();
        
  %>
    <tr class="row<%= rowid %>">
    
		<td width="10%" valign="top" nowrap>
			<%=thisTic.getTipologiaOperatoreDescr()  %>
		</td>
		<td class="row<%= rowid %>">
	    <%=thisTic.getRagioneSociale() %>
	    </td>
	    <td width="10%" valign="top" nowrap>
	    <%if (thisTic.getAssignedDate()!=null) {%>
	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
	    	<%}
	    else
	    {%>
			&nbsp;
    	<%
	    	}
	    %>
			
		</td>
    	
		<%if(thisTic.getTipoCampione() > -1) {%>
		
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
		
		<%if(thisTic.getTipoCampione()==3){%>
		
		<%= " - " + AuditTipo.getSelectedValue(thisTic.getAuditTipo()) %>
		
	
		
		<%} %>
		
		</td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		
		<%if(thisTic.getPunteggio() >= 3) {%>
		<td valign="top"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td><%=thisTic.getPunteggio()%> - Esito Controllo Ufficiale Favorevole
		</td>
		<%} %>
		<td>
		<%
			out.print("<font color = 'green'>Controllo Chiuso con Follow Up</font>");
		%>
		</td>
		
		 <td>
	  		<% 
	  			
	  			if( (thisTic.getTipologiaOperatore() == 1)) {
	  			if(thisTic.getIdMacchinetta()>0)
	  			{
					%>
					<a id="<%=thisTic.getId()%>" href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a>
					<%
	  				
	  			}
	  			else
	  			{
	  			%> 
  				<a id="<%=thisTic.getId()%>" href="AccountVigilanza.do?command=TicketDetails&id=<%=thisTic.getId()%>&orgId=<%=thisTic.getOrgId()%>"><%=thisTic.getId()%></a>
  			<%} } 
	  			else if( thisTic.getTipologiaOperatore() == 999) 
				{
				%> <a id="<%=thisTic.getId()%>"
								href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
				}
	  	  		else if( thisTic.getTipologiaOperatore() == 2000) 
	  			{
	  			%> <a id="<%=thisTic.getId()%>"
	  							href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
	  			}
	  			else  {%>

  			 	<a id="<%=thisTic.getId()%>" href="<%=thisTic.getURlDettaglio() %>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getId()%>&orgId=<%=thisTic.getOrgId()%>"><%=thisTic.getId()%></a>

  			<% } %>
  		</td>
  		<td>
		<%
			//System.out.println("Tipo campione ticket : "+thisTic.getTipoCampione());
			if (thisTic.getTipoCampione()==4) {
				ArrayList<Piano> pianiUo = thisTic.getPianoMonitoraggio();
				if (!pianiUo.isEmpty()){
					Iterator<Piano> iteratorPiani = pianiUo.iterator();
					while (iteratorPiani.hasNext()){
							Piano piano = iteratorPiani.next();
							out.println(piano.getDescrizione()+"  <br/> <b>Per conto di:</b> "+piano.getDesc_uo());
						}
				}else {
					HashMap<Integer,OiaNodo> listaUoIspezioni = thisTic.getLista_uo_ispezione();
					Iterator<Integer> iteratorIspezioni = listaUoIspezioni.keySet().iterator();
					while (iteratorIspezioni.hasNext())
					{
						Integer tipoIspezione = iteratorIspezioni.next();
						out.println(TipoIspezione.getSelectedValue(tipoIspezione)+" <br/> <b>Per conto di:</b> "+listaUoIspezioni.get(tipoIspezione).getDescrizione_lunga());
					}
				}
				}else 
				if((thisTic.getLista_unita_operative()!=null  && ! thisTic.getLista_unita_operative().isEmpty() && thisTic.getLista_unita_operative().size()>0))
				{
				for(OiaNodo uo : thisTic.getLista_unita_operative() )
				{
					out.println(uo.getDescrizione_lunga());
					
				}
				}
				%>&nbsp;
			</td>

	</tr>
       
    <%}
    }
    else {
    	  %>
    	  
    	    <tr class="containerBody">
    	      <td colspan="7">
    	        <dhv:label name="">Nessun Controllo Ufficiale Risulta aperto da piu di 20 giorni dalla data di apertura.</dhv:label>
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
</div>

<br>
<br>

<a name = "chiusura_ufficio"></a>
<div id = "chiusura_ufficio" class = "scroll1" style="display: none">
<table class="details" cellpadding="4" cellspacing="0" border="0" width="100%">
	<thead>
	<tr><th colspan="7">Lista Controlli Ufficiali che verranno Chiusi d'Ufficio</th></tr>
  	<tr>
    	<th nowrap >
      		<strong><dhv:label name="">Tipologia Operatore</dhv:label></strong>
    	</th>
    
    	<th nowrap >
        	<strong><dhv:label name="">Ragione Sociale</dhv:label></strong>
    	</th>
    
    	<th nowrap >
          	<strong>Controllo del</strong>
		</th>
      	 <th nowrap >
          	<strong>data Inserimento</strong>
		</th>
		<th nowrap >
          	<strong>Data Chiusura Ufficio</strong>
		</th>
      	<th nowrap >
          	<strong>Tipo Controllo</strong>
		</th>
      
		
		
		
		<th nowrap >
          	<strong>Id Controllo</strong>
		</th>
		
		</tr>
		</thead>
	
		<tbody>
		<%
		   int rowid = 0;
      int i =0;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      yy = CuChiusuraUfficio.iterator();
    while (CUChiusiFUASLIterator.hasNext()) {
          i++;
          rowid = (rowid != 1?1:2);
          org.aspcfs.modules.vigilanza.base.Ticket thisTic = (org.aspcfs.modules.vigilanza.base.Ticket)yy.next();
          
    %>
      <tr class="row<%= rowid %>">
      
  		<td width="10%" valign="top" nowrap>
  			<%=thisTic.getTipologiaOperatoreDescr()%>
  		</td>
  		<td class="row<%= rowid %>">
  	    <%=thisTic.getRagioneSociale() %>
  	    </td>
  	    <td width="10%" valign="top" nowrap>
  	    <%if (thisTic.getAssignedDate()!=null) {%>
  	    	<%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%>
  	    	<%}
  	    else
  	    {%>
  			&nbsp;
      	<%
  	    	}
  	    %>
  			
  		</td>
      	  		<td valign="top"><%=sdf.format(new java.sql.Date (thisTic.getEntered().getTime()))%></td>	
      	<td valign="top"><b><%=sdf.format(new java.sql.Date (thisTic.getData_chiusura_ufficio_prevista().getTime()))%></b></td>	
      	
      	
  		<%if(thisTic.getTipoCampione() > -1) {%>
  		
  		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
  		
  
  		</td>
  		<%}else{%>
  		<td>-
  		</td>
  		<%} %>
  		
  		
  	
  		<td><%
  		if( (thisTic.getTipologiaOperatore() == 1) && thisTic.getIdMacchinetta()>0) 
  			{
				%> <a id="<%=thisTic.getId()%>"
								href="DistributoriVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&orgId=<%=thisTic.getOrgId() %>&idmacchinetta=<%=thisTic.getIdMacchinetta() %>"><%=thisTic.getId()%></a> <%
  			}
  		else if( thisTic.getTipologiaOperatore() == 999) 
		{
		%> <a id="<%=thisTic.getId()%>"
						href="OpuStabVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&stabId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>&idStabilimentoopu=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getIdStabilimento()%>"><%=thisTic.getId()%></a> <%
		}
  		else if( thisTic.getTipologiaOperatore() == 2000) 
		{
		%> <a id="<%=thisTic.getId()%>"
						href="StabilimentoSintesisActionVigilanza.do?command=TicketDetails&id=<%=thisTic.getId() %>&altId=<%=thisTic.getOrgId()>0 ? thisTic.getOrgId():thisTic.getAltId()%>"><%=thisTic.getId()%></a> <%
		}
  			else
  			{
  			%> <a id="<%= toHtml(thisTic.getIdControlloUfficiale()) %>"
								href="<%=thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>&assetId=<%=thisTic.getAssetId() %>"><%= toHtml(thisTic.getIdControlloUfficiale()) %></a> 
								
								<%} %>
		</td>

  	</tr>
        

    <%}%>
    
    </tbody>
    </table>
    <br>
<br>	 
		 </div>
		 
	<font color="red" style="font-size:10pt;">Sono visualizzati solo i follow up:<br> 
	- registrati nell'ambito di Non conformità di tipo "Significativa" o "Grave" <br>
	- con stato APERTO <br>
	- inseriti dall'utente loggato<br>
	- con data termine risoluzione NC che ricade nei 10 giorni a partire da oggi.<br>
	</font>

	<table class="details" cellpadding="4" cellspacing="0" border="0" width="100%">
	<thead>
	<tr><th colspan="7">Follow UP con data termine risoluzione NC prossima alla scadenza</th></tr>

  	<tr>

		
      	 <th nowrap >
          	<strong>ID Controllo</strong>
		</th>
		
      	<th nowrap >
          	<strong>ID Non Conformità</strong>
		</th>
				
		<th nowrap >
          	<strong>Tipo Non Conformità</strong>
		</th>
			
		<th nowrap >
          	<strong>ID Followup</strong>
		</th>		
		
		<th nowrap >
          	<strong>Data termine per la risoluzione</strong>
		</th>		
		
		<th nowrap >
          	<strong>Provvedimenti Adottati</strong>
		</th>
		<th nowrap >
          	<strong>Note</strong>
		</th>
		
		</tr>
		</thead>
	
		<tbody>
		<%
		
		while (ncIterator.hasNext()) {
          i++;
          rowid = (rowid != 1?1:2);
          
          org.aspcfs.modules.followup.base.Ticket thisTic = (org.aspcfs.modules.followup.base.Ticket)ncIterator.next();
          
    	%>
      <tr class="row<%= rowid %>">
      
  		<td width="10%" valign="top" nowrap>  			
  			<a id="<%=thisTic.getIdControlloUfficiale()%>" href="<%= thisTic.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=thisTic.getIdControlloUfficiale()%>&orgId=<%=thisTic.getOrgId()%>"><%= thisTic.getIdControlloUfficiale()%></a>	  		  		  		
  		</td>
  		
  		<td  width="10%" valign="top" nowrap>
  		<%=thisTic.getId_nonconformita()%>
	  		<!--  <a id="<%=thisTic.getId_nonconformita()%>" href="<%= thisTic.getURlDettaglio()%>NonConformita.do?command=TicketDetails&id=<%=thisTic.getId_nonconformita()%>&orgId=<%=thisTic.getOrgId()%>"><%= thisTic.getId_nonconformita()%></a> -->
  	    </td>
  	    <td  width="10%" valign="top" nowrap>
  		<% if (thisTic.getTipo_nc()==2) {%>
  			Significativa
  		<%} else if (thisTic.getTipo_nc()==3) {%>
			Grave
			<%} %>
  	    </td>
  	    <td  width="10%" valign="top" nowrap>
  	    	<a id="<%=thisTic.getId()%>" href="<%= thisTic.getURlDettaglio()%>Followup.do?command=TicketDetails&id=<%=thisTic.getId()%>&idC=<%=thisTic.getIdControlloUfficiale()%>&idNC=<%=thisTic.getId_nonconformita() %>&orgId=<%=thisTic.getOrgId()%>"><%= thisTic.getId()%></a>
  	    </td>
  	    
  	    <td width="10%" valign="top" nowrap>
  	    <%=sdf.format(new java.sql.Date (thisTic.getAssignedDate().getTime()))%> 			
  		</td>
      	
		<td >
		<%
		HashMap<Integer,String> listaProvvedimenti = thisTic.getListaLimitazioniFollowup();
		Iterator<Integer> it=listaProvvedimenti.keySet().iterator();
		while(it.hasNext()){
			int k=it.next();
			out.print(listaProvvedimenti.get(k)+",");
			
		}
		
		
		%>&nbsp;
		</td>
		
		<td>
		<%= thisTic.getNoteFollowup() %>
		
  		</td>
  	</tr>
  	<%} %>
		</tbody>
		
		</table>
		 <center><a href="#top"><h1>Torna su</h1></a></center>
