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



<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<script>
	window.numRegTrovati = []; //USATA DAL BOTTONE PER SCARICARE GLI XML
	var timerControlloTokenModale;
	 


	function intercettaAction(originalAction)
	{
		
		originalAction = originalAction.replace(/&/g,'$');
		//alert(originalAction);
		var link = 'InterfRicercaUnica.do?command=Intercetta&action_originale='+originalAction;
		location.href = link;
	}

	
	function intercettaBtnScaricaArchivioXml(nomeform)
	{
		
		if(window.numRegTrovati.length == 0)
		{
			alert("LISTA RISULTATI VUOTA");
			return false;
		}
		
		//concateno i numeri reg e li passo alla servlet che genera l'archivio xml, separandoli da un -
		var stringaToSend = "";
		for(var i = 0; i<  window.numRegTrovati.length; i++)
		{
			if(numRegTrovati[i].numReg == undefined || numRegTrovati[i].numReg  == null || numRegTrovati[i].numReg == 'null')
				continue;
			//console.log(window.numRegTrovati[i].numReg)
			stringaToSend += numRegTrovati[i].numReg + "-";
		}
		//levo ultimo trattino separatore
		stringaToSend = stringaToSend.substring(0,stringaToSend.length-1);
		console.log(stringaToSend);
		//creo per il form un nuovo hidden input
		var hiddenInput = document.createElement("input");
		hiddenInput.setAttribute("type","hidden");
		hiddenInput.setAttribute("name","numRegStab");
		hiddenInput.setAttribute("value",stringaToSend);
		document.forms[nomeform].appendChild(hiddenInput);
		console.log("invio");
		
		
		/*-- il server riceve questo token, e quando ha terminato l'invio del file lo setta come cookie, in modo tale che
		lato client possiamo sapere quando è apparsa la finestra del download, e quindi togliamo la modal */
		var hiddenInputTokenModale = document.createElement("input");
		hiddenInputTokenModale.setAttribute("type","hidden");
		hiddenInputTokenModale.setAttribute("name","tokenPerUnlockModal");
		var token = Math.floor(Math.random()*1000);
		hiddenInputTokenModale.setAttribute("value",token);
		document.forms[nomeform].appendChild(hiddenInputTokenModale);
		 
		/*setto il polling per quando il client avrà settato il cookie che indica che abbiamo ricevuto il file in download
		elimino prima eventuali polling già registrati e faccio unlock di un'eventuale finestra già aperta di caricamento*/
		if(timerControlloTokenModale !== undefined)
		{
			clearInterval(timerControlloTokenModale); //pulisco vecchio timer
			loadModalWindowUnlock(); //chiudo eventuale finestra modale caricamento aperta
		}
		
		//registro il timer
		timerControlloTokenModale = setInterval(function()
		{
			//ogni tot tempo devo controllare se è stato settato come cookie il token inviato alla richiesta
			if(document.cookie.contains("tokenPerUnlockModal"))
			{
				//elimino il cookie
				delete_cookie("tokenPerUnlockModal");
				console.log("download arrivato");
				//elimino il timer
				clearInterval(timerControlloTokenModale);
				//sblocco la modale
				loadModalWindowUnlock();
				
				
			}
			else
			{
				console.log("download non arrivato");
			}
		},300);
		
		//prima di fare il submit avvio la modale
		loadModalWindow();
		
		return true; //parte il submit
	}
	
	function delete_cookie(name) {
	    document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	};
	

	
</script>

<style>

	.campoSchiarito
	{
		opacity : 0.5;
	}

</style>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td> 
<%if (SearchOpuListInfo.getLink().contains("Suap")) {%>
<a href="GisaSuapStab.do?command=SearchForm"><dhv:label name="">Pratiche SUAP</dhv:label></a> >
<a href="GisaSuapStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a>>
<%} else { %>
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a>>
<%} %>


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
	<center>
		<h3>ELENCO ATTIVITA' CORRISPONDENTI AI PARAMETRI DI RICERCA</h3>
	</center>
<dhv:permission name="opu-generazione-xml-anagrafiche-view">
 <table name="block_Featured_Article"  width="100%" border="0" cellpadding="5" cellspacing="0">
 <tbody>
	<tr>
	<td align="right">
		<form name="hiddenFormGenerazioneXml" method="post" action="generazioneXmlAnagrafica" 
			onsubmit=" return intercettaBtnScaricaArchivioXml('hiddenFormGenerazioneXml'); ">
		<input type="submit" value="SCARICA IN FORMATO XML" />
		
		</form>
	</td>
	</tr>
 	
 </tbody>
 </table>
</dhv:permission>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOpuListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" style="width: 100%" class="pagedList">
  <tr>
  
   <th <% ++columnCount; %>>
   		  <div align="center">
          <strong><dhv:label name="">Norma di Riferimento / Tipologia</dhv:label></strong>
          </div>
        </th>  
       
        
    <th nowrap <% ++columnCount; %>>
     <div align="center">
      <dhv:label name="">Nome/Ditta/Ragione Sociale</dhv:label>
    	</div>  
    </th>  
    
    
     <th   <% ++columnCount; %>>
     	    <div align="center">
          <strong>Partita IVA</strong>
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
    
         
        

          <th  <% ++columnCount; %>>
           <div align="center">
          <strong><dhv:label name="">Num. Registrazione</dhv:label></strong>
          </div>
        </th>  
          <th   <% ++columnCount; %>>
           <div align="center">
          <strong><dhv:label name="">Num.<br/>riconoscimento/<br>CUN/<br>Codice azienda</dhv:label></strong>
          </div>
        </th> 
      
      
       <th   <% ++columnCount; %>>
       	 <div align="center">
          <strong><dhv:label name="">Tiologia Attività</dhv:label></strong>
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

	//ottengo i possibili colori per numero di reg-------------------------------------------------
	/*
	HashMap<String,Double[]> colori = new HashMap<String,Double[]>();
	Iterator j0 = StabilimentiList.iterator();
	ArrayList<String> coda = new ArrayList<String>();
	while(j0.hasNext())
	{
		RicercaOpu org0 = (RicercaOpu)j0.next();
		String numReg = org0.getNumeroRegistrazione();
		if(colori.containsKey(numReg))
		{
			continue;
		}
		Double[] rgb = new Double[]{0.0,0.0,0.0};
		coda.add(numReg);
		colori.put(numReg,rgb);
	}
	int numColDist = coda.size();
	double startGray = (double)Integer.parseInt("111111",16);
	double endGray = (double)Integer.parseInt("ffffff",16);
	
	double porz = (endGray - startGray) / numColDist;
	
	for(int p=0;p<coda.size(); p++)
	{
		String numr = coda.get(p);
		double r = startGray + p * porz;
		double g = r;
		double b = r;
		
		colori.put(numr, new Double[]{r,g,b});
	}
	*/
	//----------------fine calcolo colori distinti per num reg
	

	
	
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
    
    <td valign="center" align="center" nowrap style="width: 5%;">
    <div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
   <%= toHtml2(thisOrg.getNorma()) %>
   <%
   if(thisOrg.getRiferimentoIdNomeTab().equalsIgnoreCase("suap_ric_scia_stabilimento"))
   {
	   %>
	   <br>(PRATICA IN ITINERE)
	   <%
   }
   %>
   </div>
    </td>
    
    
      
    <%if(numIterazioni==1){ %>
	<td  valign="center" align="center" style="width: 15%;" rowspan="<%=lista.size()%>">
	<div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
<%-- 	<a onclick="intercettaAction('<%=temp.getURlDettaglioanagrafica()+".do?command=Details&"+thisOrg.getRiferimentoIdNome()+"="+thisOrg.getRiferimentoId() %>')" id="<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %>" href="#"><%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a> --%>
	
	<a  id="<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %>" href="<%=temp.getURlDettaglioanagrafica() %>.do?command=Details&idFarmacia=<%=thisOrg.getRiferimentoId() %>&opId=<%=thisOrg.getRiferimentoId() %>&<%=thisOrg.getRiferimentoIdNome()%>=<%=thisOrg.getRiferimentoId()%><%=(thisOrg.getTipologia()== 1999) ? "&container=archiviati" : "" %>"><%=  toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a>
	</div>
	</td>
	<%}
    
    %>
	
	 <%if(numIterazioni==1){ %>
	  <td valign="center"  align="center"  nowrap style="width: 10%;" rowspan="<%=lista.size()%>">
	  <div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
	  <%=   toHtml2(thisOrg.getPartitaIva())   %>
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
	<%= (thisOrg.getIndirizzoSedeProduttiva()!=null)? ( toHtml2(thisOrg.getIndirizzoSedeProduttiva().replaceAll(",", "<br>"))   ) :"N.D."  %>
	</div>
	</td>
	<%} %>
	
	
	
    
      <%if(numIterazioni==1){ %>
      <td valign="center" align="center"  nowrap style="width: 10%;" rowspan="<%=lista.size()%>">
      <div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
      <%=  toHtml(thisOrg.getNumeroRegistrazione())  %>
      </div>
      </td>
      <%} %>
      
  <%if(numIterazioni==1){ %>
        <td valign="center" align="center"  nowrap style="width: 10%;" rowspan="<%=lista.size()%>">
      <div class="<%= campoToShow ? "" : "campoSchiarito" %>" >
      <%=(thisOrg.getNumAut()!=null) ? (  toHtml(thisOrg.getNumAut())  ) : "N.D." %>
      </div>
      </td>
      <%} %>
      
        <%if(numIterazioni==1 && thisOrg.getTipoAttivita().toUpperCase().contains("FISSA")){ %>
      
       	<td valign="center" align="center"  nowrap style="width: 15%;" rowspan="<%=lista.size()%>">
       	 
       	<%= toHtml2(thisOrg.getTipoAttivita())  %>
       	<%=( thisOrg.getTipoAttivita() != null && thisOrg.getTipoAttivita().toLowerCase().contains("mobile") &&  thisOrg.getTarga()!=null && !"".equals(thisOrg.getTarga()) ) ?  ("<br/>TARGA:<b>"+toHtml2(thisOrg.getTarga()) )+"</b>" : ( thisOrg.getTipoAttivita().toLowerCase().contains("mobile") ? "<br/><b>TARGA</b>: N.D." : "" )  %>
       	 
       	</td>
       	<%
       		System.out.println(thisOrg.getTipoAttivita().toLowerCase().contains("mobile"));
       		System.out.println(thisOrg.getTipoAttivita());
       		System.out.println(thisOrg.getTarga());
        
        }
        else
        {
        	%>
        	  <%if( ! thisOrg.getTipoAttivita().toUpperCase().contains("FISSA")){ %>
      
       	<td valign="center" align="center"  nowrap style="width: 15%;">
       	 
       	<%= toHtml2(thisOrg.getTipoAttivita())  %>
       	<%=( thisOrg.getTipoAttivita() != null && thisOrg.getTipoAttivita().toLowerCase().contains("mobile") &&  thisOrg.getTarga()!=null && !"".equals(thisOrg.getTarga()) ) ?  ("<br/>TARGA:<b>"+toHtml2(thisOrg.getTarga()) )+"</b>" : ( thisOrg.getTipoAttivita().toLowerCase().contains("mobile") ? "<br/><b>TARGA</b>: N.D." : "" )  %>
       	 
       	</td>
       	<%}
        }
        %>
       	
       	
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
      <a href="OpuStab.do?command=SearchForm">Torna alla ricerca</a>.
    </td>
  </tr>
<%}%>
</table>
 
 <BR>
 
 
<dhv:pagedListControl object="SearchOpuListInfo" tdClass="row1"/>


<div id = "dialogGestioneScia">

</div>





    

