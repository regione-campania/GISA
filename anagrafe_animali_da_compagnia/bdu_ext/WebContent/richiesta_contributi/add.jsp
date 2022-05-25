<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.assets.base.*,java.text.DateFormat"%>
	

<%@page import="org.aspcfs.modules.praticacontributi.base.PraticaDWR"%>
<jsp:useBean id="RC" class="org.aspcfs.modules.richiestecontributi.base.RichiestaContributi" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="elencoPratiche" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>



<SCRIPT LANGUAGE="JavaScript" >
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

		<p>
			<font color="red">
				<%=toHtmlValue( (String)request.getAttribute( "Error" ) ) %>
			</font>
		</p>
		
		
<script type="text/javascript">

		function init() {
		    <% if (request.getAttribute("UserProvincia")!=null) { 
		  		UserBean user = (UserBean) request.getAttribute("UserProvincia"); 
		  	%>
			<% } %>
		  } 
				
			function verifica()
			{
				var form = document.addRichiesta;
				var ok = true;
				var messaggio = '';

				if ((form.elencoPratiche.value==null)||(form.elencoPratiche.value=="")){
					messaggio += 'Non ci sono progetti di Sterilizzazione per questa asl.\r\n';
					ok = false;
				} 
					
				if (form.elencoPratiche.value== -1) 
				{
					messaggio += 'Selezionare un progetto di Sterilizzazione.\r\n';
					ok = false;
				}

				if( !ok )
				{
					alert( messaggio );
				}

				return ok;
			}


						
		</script>
<body onload="javascript:init();">
		<form method="post" name="addRichiesta" action="ContributiSterilizzazioni.do?command=AvviaRichiesta" onSubmit="return verifica();">
<%
				if(elencoPratiche!=null && !elencoPratiche.isEmpty())
				{
%>
					<input type="submit" value="Avvia la richiesta" />
<%			
				}
%>						

			<input type="button" value="Annulla" onclick="location.href='ContributiSterilizzazioni.do'" />
			
			<table class="details"  cellspacing="0" cellpadding="4" border="0" width="100%">
				<tr>
<%
				if(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE")))
				{
%>
       	  			<td nowrap class="formLabel">
           		   	<dhv:label name="">Asl di Riferimento</dhv:label>
           			</td>
<%
				}
				else
				{
%>
					<td nowrap class="formLabel">
           		   		<dhv:label name="">Comune di Riferimento</dhv:label>
           			</td>
<%
					
				}
%>
           		<td>	
           		<%
				if(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE")))
				{
%>
       	  			<dhv:evaluate if="<%=(User.getSiteId()> 0) %>">
           				<%= aslRifList.getSelectedValue(User.getSiteId()) %>
           			</dhv:evaluate>
<%
				}
				else
				{
%>
           				<%=comuniList.getSelectedValue(User.getUserRecord().getIdComune())%>
<%
				}
%>


           		<dhv:evaluate >
           		
           		</dhv:evaluate>
           		
  				</td>
            </tr>
  						
	
	<tr id="pratiche">
		<td class="formLabel">
	   				<dhv:label name="">Elenco Pratiche</dhv:label>
		</td>
		<td>
<%
				if(elencoPratiche==null || elencoPratiche.isEmpty())
				{
					out.println("Non sono presenti pratiche disponibili");
				}
				else
				{
%>		

	    		<select id="elencoPratiche" name="elencoPratiche" >
	    		
	    		<% 		
	    		for (PraticaDWR p : (ArrayList<PraticaDWR>) elencoPratiche){%>
					   <option value="<%=p.getId()%>">
				
				
				
				
				<%
				if(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE")))
				{

       	  			  if(p.getElencoCanili() != null || p.getElenco_comuni() != null) { %>
							  <% if(p.getElenco_comuni() != null){ %>				   
						  		 <%="Decreto n° "+p.getNumero_decreto_pratica()+" del "+p.getData_decreto_stringa() + " - N° totale cani catturati "+p.getTotale_cani_catturati() +" -N° totale cani padronali "+p.getTotale_cani_padronali() + " -N° totale gatti padronali "+ p.getTotale_gatti_padronali() +"  -N° totale gatti catturati "+p.getTotale_gatti_catturati() + " - "+p.getElenco_comuni()%>
							<% } else { %>   
					 		<%="Decreto n° "+p.getNumero_decreto_pratica()+" del "+p.getData_decreto_stringa() + " - N° totale cani catturati "+p.getTotale_cani_catturati() +" -N° totale cani padronali "+p.getTotale_cani_padronali()+ " - "+p.getElencoCanili()%>
					   	<% } }%>
<%
				}
				else
				{

					  if(p.getElenco_comuni() != null) { %>
						  		 <%="Decreto n° "+p.getNumero_decreto_pratica()+" del "+p.getData_decreto_stringa() + " - N° totale cani maschi "+p.getTotale_cani_maschi() +" -N° totale cani femmina "+p.getTotale_cani_maschi() + " - "+p.getElenco_comuni()%>
							<%  }%>
<%
					
				}
%>


				
				
				
				
				
				
				
				
					   
					   
 
 					   </option>		
					   
	    			
	    				    			
	    		<%} %>
	    		
	    		
	    		</select>
<%
				}    		
%>
	    </td>	
	</tr>
</table>
 </form>
	</body>    
	   