<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" /> 
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
	
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AnagraficaPartenza" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope = "request"></jsp:useBean>
	
	   
   <script>
   function clearForm(){
	   var inp = document.getElementById("searchAccount").getElementsByTagName('input');
	   for(var i in inp){
	       if(inp[i].type == "text"){
	           inp[i].value='';
	       }
	   }
	   
	   mostraAttivitaProduttive('attprincipale',1,-1, true,-1);
   }
   
   
   
   
   function checkForm(form){
	   var linea1 = document.getElementById("searchattivita1").value;
	   var linea2 = document.getElementById("searchattivita2").value;
	   var linea3 = document.getElementById("searchattivita3").value;
	   var searchAttivita = document.getElementById("searchattivita");
	   
	   var linea = "";
	   
	   if (linea1!=''){
		   linea = linea1;
		   if (linea2!=''){
			   linea = linea + "->"+linea2;
			   if (linea3!='')
				   linea = linea + "->"+linea3;
		   }
			   
	   }
	   if (linea!='')
		   searchAttivita.value = linea;
	   loadModalWindow();
	   form.submit();
   }

   </script>     


<form name="searchAccount" id = "searchAccount" action="RicercaUnica.do?command=Search" method="post">
<%-- Trails --%>


<%-- End Trails --%>

<%

for(Integer tipologiaDestinazione : AnagraficaPartenza.getTipologiaAnagraficheVersoCuiConvergere())
{
	%>
	<input type = "hidden" name = "searchmultiplecodetipologiaAnagraficheVersoCuiConvergere" value = "<%=tipologiaDestinazione%>">
	<%
}
%>


<!--  IMPRESA -->
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name=""> Dati operatore</dhv:label></strong>
          </th>
        </tr>
        
        
        
        <tr>
                <td class="formLabel">
                  <dhv:label name="">ASL</dhv:label>
                </td>
                <td>
                  <%
                 
           
           SiteList.setJsEvent("onChange=popolaComboComuni()");
           %>
           
           <%
           if(AnagraficaPartenza.getIdAsl()>0)
           {
        	   %>
        	   <input type = "hidden" name = "searchcodeidAsl" value = "<%=AnagraficaPartenza.getIdAsl() %>">
        	   <%=SiteList.getSelectedValue(AnagraficaPartenza.getIdAsl()) %>
        	   <%
           }else{
           
           %>
           
          	  <%= SiteList.getHtmlSelect("searchcodeidAsl", AnagraficaPartenza.getIdAsl()) %>
          	  <%} %>
                </td>
              </tr> 
             
        
        <tr>
          <td class="formLabel">
            <dhv:label name="lab.denom">Nome/Ditta/Ragione sociale</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchragioneSociale" id="searchRagioneSociale" value="">
          </td>
        </tr>
        
         <tr>
        	<td nowrap class="formLabel">
     		 Partita IVA
   			 </td> 
    		<td> 
				<input  type="text" maxlength="11" size="50" <%if(AnagraficaPartenza.getPartitaIva()!=null && !AnagraficaPartenza.getPartitaIva().equals("")) {%>readonly="readonly"<%} %> name="searchpartitaIva" value="<%=((AnagraficaPartenza.getPartitaIva()!=null && !AnagraficaPartenza.getPartitaIva().equals("")  ) ? AnagraficaPartenza.getPartitaIva().trim() : "")%>">
			</td>
  		</tr>
  		
  		<tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Numero di registrazione</dhv:label>
   			 </td> 
    		<td> 
				<input type="text" maxlength="50" size="50" name="searchnumeroRegistrazione" value="">
			</td>
  		</tr>
  		
        
  		 <tr>
                <td class="formLabel">
                  <dhv:label name="">Comune</dhv:label>
                </td>
                <td>
                 	<input type = "hidden" name = "searchcodetipoAttivita" value = "<%=AnagraficaPartenza.getTipoAttivita() %>">
                 
                 	<%
                 	if(AnagraficaPartenza.getTipoAttivita()!=null && !AnagraficaPartenza.getTipoAttivita().toLowerCase().contains("mobile") && AnagraficaPartenza.getComuneSedeProduttiva()!=null && !"".equals( AnagraficaPartenza.getComuneSedeProduttiva()))
                 	{%>
                 		<input type="hidden" name="searchcomuneSedeProduttiva" value="<%=AnagraficaPartenza.getComuneSedeProduttiva()%>">
                 		<%=AnagraficaPartenza.getComuneSedeProduttiva() %>
                 		
                 		<%
                 	}
                 	else
                 	{
                 		
                 		if(AnagraficaPartenza.getTipoAttivita()!=null && AnagraficaPartenza.getTipoAttivita().toLowerCase().contains("mobile") && AnagraficaPartenza.getComuneSedeLegale()!=null && !"".equals( AnagraficaPartenza.getComuneSedeLegale()))
                     	{%>
                     		<input type="hidden" name="searchcomuneSedeProduttiva" value="<%=AnagraficaPartenza.getComuneSedeLegale()%>">
                     		<%=AnagraficaPartenza.getComuneSedeLegale() %>
                     		
                     		<%
                     	}
                     	else
                     	{
                     		if(AnagraficaPartenza.getTipoAttivita()!=null && AnagraficaPartenza.getTipoAttivita().toLowerCase().contains("mobile") && AnagraficaPartenza.getComune()!=null && !"".equals( AnagraficaPartenza.getComune()))
                         	{%>
                         		<input type="hidden" name="searchcomuneSedeProduttiva" value="<%=AnagraficaPartenza.getComune()%>">
                         		<%=AnagraficaPartenza.getComune() %>
                         		
                         		<%
                         	}
                     		else{
                     		
                 	%>
                 	
                 	<%= ComuniList.getHtmlSelectText("searchcomuneSedeOperativa",SearchOrgListInfo.getSearchOptionValue("searchcomuneSedeOperativa")) %>
             	<%}}} %>
                </td>
              </tr> 
        
  		
  		 <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Indirizzo</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchindirizzoSedeOperativa" value="">
			</td>
  		</tr>
  		
  		
         <tr>
          <th colspan="2">
            <strong><dhv:label name="">Legale rappresentante</dhv:label></strong>
          </th>
        </tr>
  
  		<tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Codice Fiscale </dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="16" size="50" name="searchcodiceFiscaleSoggettoFisico" value="">
			</td>
  		</tr> 
  		
  	  
 </table>
 
 <%
 if(AnagraficaPartenza!=null && AnagraficaPartenza.getTipologia()>0)
 {
	 %>
	 <input type="hidden" name="searchcodetipologiaOperatore" value="<%=AnagraficaPartenza.getTipologia() %>">
	 <%
 }
 %>
 <%
	if(request.getAttribute("Popup")!=null)
	{
	%>
	<input type = "hidden" name = "Popup" value="true"/>
	<%} 
 
 if(request.getAttribute("tipoOperazione")!=null)
	{
	%>
	<input type = "hidden" name = "tipoOperazione" value="<%=request.getAttribute("tipoOperazione")%>"/>
	<%} 
	%>
<input type="button" id="search" name="search" value="Ricerca" onclick="apriRisultatiRicercaOperatore(<%=AnagraficaPartenza.getRiferimentoId() %>,'<%=AnagraficaPartenza.getRiferimentoIdNomeCol()%>');"/>


 <%
	if(request.getAttribute("Popup")!=null)
	{
	%>
<input type = "button" value="ESCI" onclick="$('#dialogRICERCA').dialog('close');">
<%} else{%>

<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="clearForm();">
<%} 
 
 
	%>
	
	<div id="LoadingImage" style="display: none">
	Ricerca in corso Attendere ..
<img src="images/ajax-loader.gif" />
</div>
	
	<input type = "hidden" name = "rifId" value="<%=AnagraficaPartenza.getRiferimentoId() %>">
	<input type = "hidden" name = "rifIdNome" value="<%=AnagraficaPartenza.getRiferimentoIdNomeCol() %>">
		<input type = "hidden" name = "tipoRicerca" value="<%=AnagraficaPartenza.getTipoRicerca() %>">
	
	
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

</form>



<script>

$( "#dialogRICERCA" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"SPOSTAMENTO CONTROLLI  - RICERCA OPERATORE",
    width:1250,
    height:1000,
    draggable: false,
    modal: true
   
}).prev(".ui-dialog-titlebar");




</script>