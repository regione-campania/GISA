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
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%> 
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        
        
   <script>
   function clearForm(){
	   var inp = document.getElementById("searchAccount").getElementsByTagName('input');
	   for(var i in inp){
	       if(inp[i].type == "text"){
	           inp[i].value='';
	       }
	   }
	   var asl = document.getElementById("searchcodeidAsl");
	   asl.value="-1";
	   
	   var stato = document.getElementById("searchcodeidStato");
	   stato.value="-1";
	   
	   mostraAttivitaProduttiveRicerca('attprincipale',1,-1, true,-1);
	   
	   document.getElementById("nuova").click();
	   mostraTipoAttivita(1);
	   
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
	   
	  
	   
	   var valorizzati = 0;
	   var inp = document.getElementsByTagName('input');
	   for(var i in inp){
	       if(inp[i].type == "text"){
	    	   if (inp[i].value !='' && inp[i].value.length>2)
	    		   valorizzati++;
	       }
	   }
	   
	   var asl = document.getElementById("searchcodeidAsl");
	   if (asl.value!='-1')
			valorizzati++;	   
	   
	   var stato = document.getElementById("searchcodeidStato");
	   if (stato.value!='-1')
			valorizzati++;	  
	   
	   if(valorizzati>0)
	   {
	 	loadModalWindow();
	   	form.submit();
	   }
	   else{
		   alert("Attenzione! Selezionare almeno un campo di ricerca valorizzandolo con più di due caratteri.");
		   return false;
	   }
	  
   }
   
   
   function gestisciTipoAttivita(val){
	   if (val=="2")
		   document.getElementById("asl").style.display="none";
	   else
		   document.getElementById("asl").style.display="";
	   
	   if (val=="1" || val=="3")
		   document.getElementById("targa_tr").style.display="none";
	   else
		   document.getElementById("targa_tr").style.display="";
   }
   
   function mostraTipoAttivita(val){
	   
	   if (val =='1'){ // CERCA IN NUOVA ANAGRAFICA
		   	document.getElementById("tipoattivita").style.display="";
		   	document.getElementById("tipoAttivita_tutte").checked="checked";
			document.getElementById("tipoAttivita_tutte").click();
			document.getElementById("searchcodeidStato").disabled="";
			document.getElementById("searchcodeidStato").value="-1";
			document.getElementById("linee").style.display="";
			document.getElementById("targa_tr").style.display="";
			document.getElementById("tr_allerta").style.display="";
			document.getElementById("tr_tipolinea").style.display="";
			document.getElementById("tr_stato").style.display="";
			document.getElementById("tr_riconoscimento").style.display="";
	   }
	   else	if (val =='0'){ // CERCA IN VECCHIA ANAGRAFICA
		   	document.getElementById("tipoattivita").style.display="none";
			document.getElementById("tipoAttivita_tutte").checked="checked";
			document.getElementById("tipoAttivita_tutte").click();
			document.getElementById("searchcodeidStato").value="-1";
			document.getElementById("searchcodeidStato").disabled="disabled";
			document.getElementById("linee").style.display="none";
			mostraAttivitaProduttiveRicerca('attprincipale',1,-1, true,-1);
			document.getElementById("targa_tr").style.display="";
			document.getElementById("tr_allerta").style.display="none";
			if(document.searchAccount.flagAllerte.checked){
				document.searchAccount.flagAllerte.checked=false;
				abilitaRicercaAllerte();
				}
			document.getElementById("tr_tipolinea").style.display="none";
			document.getElementById("tr_stato").style.display="";
			document.getElementById("tr_riconoscimento").style.display="";
	   }
	  else	if (val =='2'){ // CERCA IN RICHIESTE
			document.getElementById("tipoattivita").style.display="";
		   	document.getElementById("tipoAttivita_tutte").checked="checked";
			document.getElementById("tipoAttivita_tutte").click();
			document.getElementById("searchcodeidStato").disabled="";
			document.getElementById("searchcodeidStato").value="7";
			document.getElementById("linee").style.display="";
			document.getElementById("targa_tr").style.display="none";
			document.getElementById("tr_allerta").style.display="none";
			if(document.searchAccount.flagAllerte.checked){
			   document.searchAccount.flagAllerte.checked=false;
				abilitaRicercaAllerte();
				}
			document.getElementById("tr_tipolinea").style.display="none";
			document.getElementById("tr_stato").style.display="";
			document.getElementById("tr_riconoscimento").style.display="";
	   }
	  else	if (val =='3'){ // CERCA IN STABILIMENTI ARCHIVIATI
			document.getElementById("tipoattivita").style.display="none";
			document.getElementById("tipoAttivita_tutte").checked="checked";
			document.getElementById("tipoAttivita_tutte").click();
			document.getElementById("searchcodeidStato").value="-1";
			document.getElementById("searchcodeidStato").disabled="disabled";
			document.getElementById("linee").style.display="none";
			mostraAttivitaProduttiveRicerca('attprincipale',1,-1, true,-1);
			document.getElementById("targa_tr").style.display="none";
			document.getElementById("tr_allerta").style.display="none";
			if(document.searchAccount.flagAllerte.checked){
			  	document.searchAccount.flagAllerte.checked=false;
			  	abilitaRicercaAllerte();
			}
			document.getElementById("tr_tipolinea").style.display="none";
			document.getElementById("tr_stato").style.display="none";
			document.getElementById("tr_riconoscimento").style.display="none";
	   }
   }

   function setStatoCu(stato)
   {
       document.searchAccount.searchstatoCu.value = stato;
   }
  function abilitaRicercaAllerte()
  {
	  
	  if (document.searchAccount.flagAllerte.checked)
		{
			document.getElementById("bloccoAllerte").style.display="block";
			document.searchAccount.searchcodiceAllerta.value = "Tutte";
			document.getElementById("nuova").click();
			mostraTipoAttivita(1);
		}
		else
		{
			document.getElementById("bloccoAllerte").style.display="none";
			document.searchAccount.searchcodiceAllerta.value = "";
		}
      
  }

  function popLookupSelectorAllertaRicerca(displayFieldId, displayFieldId2, table, params) {
	  title  = '_types';
	  width  =  '600';
	  height =  '550';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('LookupSelector.do?command=PopupSelectorAllertaRicercaImprese&displayFieldId=' + displayFieldId + '&displayFieldId2=' + displayFieldId2 + '&table=' + table + params, title, windowParams+'&filtroDesc=700');
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
  
   </script>     


<form name="searchAccount" id = "searchAccount" action="RicercaUnica.do?command=Search" method="post">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
Ricerca
</td>
</tr>
</table>
<%-- End Trails --%>



<center>

<marquee>
				<font color="blue"><b>NEWS : A BREVE SARA' RILASCIATA LA NUOVA MASCHERA DI RICERCA IN ANAGRAFICA STABILIMENTI. E' POSSIBILE VISUALIZZARLA IN AMBIENTE DI COLLAUDO ( col.gisacampania.it) PER ESEGUIRE DELLE PROVE </b></font>
			</marquee>

</center>
<!--  IMPRESA -->
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Per Impresa</dhv:label></strong>
          </th>
        </tr>
        
         <tr>
           <td class="formLabel"> Tipo ricerca</td>
           <td>
         <input type="radio" id="nuova" name="searchcodetipoRicerca" value="-1" checked onClick="" checked /> Tutte
         <input type="radio" id="nuova" name="searchcodetipoRicerca" value="1" onClick="mostraTipoAttivita(this.value)"/> Cerca in nuova anagrafica
         <input type="radio" id="vecchia" name="searchcodetipoRicerca" value="0" onClick="mostraTipoAttivita(this.value)"/> Cerca in vecchia anagrafica/Operatori non soggetti a SCIA<br/>
         <input type="radio" id="richieste" name="searchcodetipoRicerca" value="2" onClick="mostraTipoAttivita(this.value)"/> Cerca nelle richieste di registrazione in itinere
          <input type="radio" id="archiviati" name="searchcodetipoRicerca" value="3" onClick="mostraTipoAttivita(this.value)"/> Cerca negli stabilimenti archiviati<br/>
          
         </td>
         </tr>
         
          <tr id="tr_tipolinea">
           <td class="formLabel"> Tipo Linea Produttiva</td>
           <td>
         <select name="searchcodeidTipoLineaProduttiva">
         <option value="-1">TUTTE</option>
           <option value="1">REGISTRABILI</option>
             <option value="2">RICONOSCIBILI</option>
         </select>
         
         </td>
         </tr>
         
         
         
         <tr id="tipoattivita">
           <td class="formLabel"> Stabilimento</td>
           <td>
         <input type="radio" id="tipoAttivita_tutte" name="searchcodetipoAttivita" value="-1" checked onClick="gestisciTipoAttivita(this.value)"/> Tutti
         <input type="radio" id="tipoAttivita_fissa" name="searchcodetipoAttivita" value="1"  onClick="gestisciTipoAttivita(this.value)"/> Con sede fissa
         <input type="radio" id="tipoAttivita_mobile" name="searchcodetipoAttivita" value="2" onClick="gestisciTipoAttivita(this.value)"/> Senza sede fissa
		<input type="radio" id="tipoAttivita_mobile" name="searchcodetipoAttivita" value="3" onClick="gestisciTipoAttivita(this.value)"/> Con attività apicoltura
        
         
         
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
				<input  type="text" maxlength="11" size="50" name="searchpartitaIva" value="">
			</td>
  		</tr>
  		
  		 <tr id='targa_tr'>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Targa</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="15" size="50" name="targa" value="">
			</td>
  		</tr>
  		
  	
 </table>
 
 <!--  STABILIMENTO -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">    
    <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Per Stabilimento (Sede Operativa)</dhv:label></strong>
          </th>
        </tr>
        
         <tr id="asl" >
        	<td nowrap class="formLabel">
     		 <dhv:label name="">ASL</dhv:label>
   			 </td> 
    		<td> 
				
	    <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("searchcodeidAsl",-1) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="searchcodeidAsl" id="searchcodeidAsl" value="<%=User.getSiteId()%>" >
        </dhv:evaluate>
    
			</td>
  		</tr> 
  		
        
           <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Comune</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchcomuneSedeProduttiva">
			</td>
  		</tr> 
  		
  		   <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Via/Piazza</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchindirizzoSedeProduttiva" value="">
			</td>
  		</tr> 
  		
  		
        <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Numero <br/>Registrazione</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchnumeroRegistrazione" value="">
			</td>
  		</tr>
  		
  	<tr id="tr_riconoscimento">
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Numero<br/>riconoscimento/<br>CUN/<br>Codice azienda</dhv:label>
   			 </td> 
    		<td> 
				<input  type="text"  maxlength="50" size="50" name="searchnumeroRiconoscimento" placeholder="">
			</td>
  		</tr> 

 <tr id="tr_stato">
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Stato</dhv:label>
   			 </td> 
    		<td> 
				   <%= ListaStati.getHtmlSelect("searchcodeidStato",-1) %>
			</td>
  		</tr>
  	
  	
  	 <tr id="tr_allerta">
          <td nowrap class="formLabel">
            <dhv:label name="">Controlli per Allerta</dhv:label>
          </td>
          <td>
          <input type = "checkbox" name = "flagAllerte" onclick="abilitaRicercaAllerte()">
          
          <div id = "bloccoAllerte" style = "display: none">
          
          		<input type="hidden" id="ticketid" value="-1" name="ticketidd">
           		<input style="background-color: lightgray" readonly="readonly" type="text" size="20"  id="id_allerta" name="searchcodiceAllerta"  value="" >
      			&nbsp;[<a href="#" onClick="popLookupSelectorAllertaRicerca('id_allerta','name','ticket','');return false;">Seleziona</a>]
     			<br>
     			Controlli Aperti <input type = "radio" name = "statoCu" onclick="setStatoCu('aperti')" checked="checked"> Controlli chiusi <input type = "radio" name = "statoCu" onclick="setStatoCu('chiusi')"> 
     			<input type = "hidden" name = "searchstatoCu" value = "aperti" >
     	</div>
       </td>
        </tr>
  	
  	
  		  				
 </table>
 
  		
   <!-- LINEA ATTIVITA -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="linee">    
    <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Per Linea di attività</dhv:label></strong>
          </th>
        </tr>
 
        <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Linea</dhv:label>
   			 </td> 
    		<td> 
    		<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/SuapDwr.js"> </script>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>

<input type ="hidden" value = "false" id = "validatelp" value = "false">
	<table id = "attprincipale" style="width: 100%;"></table>
<script> 	mostraAttivitaProduttiveRicerca('attprincipale',1,-1, true,-1);</script>

<div style="display:none">
 <input type="text" name="searchattivita1" id="searchattivita1"/>
 <input type="text" name="searchattivita2" id="searchattivita2"/>
 <input type="text" name="searchattivita3" id="searchattivita3"/>
<input type="text" name="searchattivita" id="searchattivita" value=""/>
</div>
			</td>
  		</tr>
 </table>
  
  	
   		
   
 <!--  RAPPRESENTANTE LEGALE -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details"> 
    <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Per Legale rappresentante</dhv:label></strong>
          </th>
        </tr>
        
     
  		   <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Nominativo </dhv:label>
   			 </td> 
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchnomeSoggettoFisico" value="">
			</td>
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
  		
  		

<input type="button" id="search" name="search" value="Ricerca" onClick="checkForm(this.form);"/>
<%
if (User.getRoleId()!=Role.RUOLO_COMUNE)
{
%>
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="clearForm();">
<%} %>
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

</form>



 