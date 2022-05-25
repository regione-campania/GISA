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
<jsp:useBean id="SearchApiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ComuniToAsl" class="java.util.HashMap" scope="request" />
<%@ include file="../initPage.jsp" %>


<form name="searchAccount" action="ApicolturaAttivita.do?command=Search" method="post">
<input type="hidden" name="searchisNuovaRicercaApicoltura" value="true"/> <!-- per evitare che le nuove modifiche backend non siano retrocompatibili nei bean centralizzati -->
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ApicolturaAttivita.do?command=SearchForm"><dhv:label name="">Apicoltura </dhv:label></a> > 
<dhv:label name="">Ricerca Rapida</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>


		<%=showError(request, "ErrorValidazioneError")%>
		<%=showError(request, "EsitoImport")%>
<table id="tab0" cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Rapida APICOLTORE-APIARI<br><font style="color:red; font-size:8px;"><b>La ricerca mostra gli apicoltori con i relativi apiari associati, indipendentemente dal tipo di ricerca selezionato. </b></font></dhv:label></strong>
          </th>
        </tr>
        
         <tr>
          <td class="formLabel" >
            <dhv:label name="">Ricerca per</dhv:label>
          </td>
          <td title="Selezionare se applicare le restrizioni sugli apicoltori o sugli apiari associati">
            <input type="radio" name="searchricercaPer" value="apicoltore"  />&nbsp;Apicoltore</br>
            <input type="radio" name="searchricercaPer" value="apiario"  />&nbsp;Apiario
          </td>
         </tr>
         
         
         <tr>
          <td class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td id="tdComune">
            <!-- <input type="text" name="comune" /> -->
            <%=ComuniList.getHtmlSelect("searchidComune", -1) %>
          </td>
         </tr>
         
         <tr>
         
         <tr>
          <td class="formLabel">
            <dhv:label name="">Asl di Competenza</dhv:label>
          </td>
          <td id="tdAsl" title="A seconda del soggetto su cui si applicano le condizioni di ricerca (apicoltore/apiario), verranno visualizzati i risultati per la Campania/Fuori regione/Asl specificata/Tutti">
           <%if(User.getSiteId()>0){ %>
           <%=SiteList.getSelectedValue(User.getSiteId()) %>
           <input type = "hidden" name = "searchidAslString" value = "<%=User.getSiteId() %>" /> <!-- uso search e non searchcode perche' a quanto pare la ListPageInfo non funziona bene con gli int ma con le string si -->
           <%}else{ %>
           <%=SiteList.getHtmlSelect("searchidAslString", -1) %>
           <%} %>
          
          </td>
        </tr>
        
           <%-- <tr>
          <td class="formLabel">
            <dhv:label name="">IN REGIONE</dhv:label>
          </td>
          <td id="tdSearchcodeinRegione" >
           SI <input type = "radio" name="searchcodeinRegione" value="1" checked="checked">
           <b><font color = "red">NO</font></b><input type = "radio" name="searchcodeinRegione" value="2">
          </td>
        </tr> --%>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Ragione Sociale</dhv:label>
          </td>
          <td id="tdDenominazione">
            <input type="text" maxlength="70" size="50" name="searchragioneSociale" >
          </td>
        </tr>
       
       <tr>
          <td class="formLabel">
            <dhv:label name="">Codice Fiscale Proprietario</dhv:label>
          </td>
          <td id="tdCodiceFiscale">
            <input type="text" maxlength="70" size="50" name="searchcfProprietario" >
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="">CUN</dhv:label>
          </td>
          <td id="tdCun">
            <input type="text" maxlength="70" size="50" name="searchCun" >
          </td>
        </tr>
      
      
      </table>
    </td>

  </tr>
</table>

<input type="submit"  onclick='loadModalWindow();' value="<dhv:label name="button.search">Search</dhv:label>">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

</form>


<script>








var comuniToAsl;
var previousComuneScelto;

$(function(){ 
		/*inizializzo i comuni to asl */
		initComuniToAsl();
		
		/*metto handler sui radio botton del tipo di ricerca */
		$("input[type='radio'][name='searchricercaPer'][value='apicoltore']").on("change",function(evt){
			changeRicercaPer('apicoltore');
		});
		
		$("input[type='radio'][name='searchricercaPer'][value='apiario']").on("change",function(evt){
			changeRicercaPer('apiario');
		});
		
		
		
		 /*metto un handler sulla select dell'asl in maniera tale che quando selezioniamo fuori regione, si disabiliti la select dei comuni poiche' ovviamente non ha senso
		 cercare per un comune campano quando cerchiamo per i fuori regione */
		 $("#searchidAslString").on("change",function(evt){
			 
			 var value = $(this).val();
			 if(value == 16) /*fuori regione */
			 {
				 /*disabilito la combo del comune */
				 /*SE E' ABILITATO E NON STIAMO SU APICOLTORE */
				if($("#tdComune select").prop("disabled") == false && $("input[name='searchricercaPer']:checked").val() != "apicoltore" )
				{
					$("#tdComune select").val("-1"); 
					$("#tdComune select").prop("disabled",true); /*per una select non si puo' usare readonly (che fa comunque viaggiare il parametro per non far schiattare server side)
	 				quindi per disabilitare la select del comune metto disabled, e creo un input type hidden che sostituisce la select, con lo stesso nome */
	 				$("#tdComune").append($("input",{type : 'hidden', name : 'searchcodecomune', value : '-1'}));
	 				$("#tdComune select").css({"background-color": "rgba(125,125,125,0.4)"});
				}
				
			 }
			 else
			 {
				 /*attivo la combo del comune */
				 /*A PATTO CHE SIA DISABILITATO E CHE NON SIAMO SU APICOLTORE */
				 if($("#tdComune select").prop("disabled") == true && $("input[name='searchricercaPer']:checked").val() != "apicoltore" )
				{
	 				$("#tdComune select").prop("disabled",false);
	 				$("#tdComune input[type='hidden']").remove(); /*levo il dummy che eventualmente stava a sostituire la select disabilitata */
	 				$("#tdComune select").css({"background-color": "white"});
				}
			 }
			 /*in ogni caso , abilitata o meno la combo, gli resettiamo il valore per evitare di aver lasciato un comune non valido per la nuova asl */
			 $("#tdComune select").val("-1");
		 });
		 
		 
		 
		 
		/*metto handler sull'onfocus per salvare valore precedente del comune */
		$("#searchidComune").on("focus",function(evt)
		{
			previousComuneScelto = $(this).val();
		});
		/*metto handler sul cambio del comune che avvisa se il comune selezionato non corrisponde con l'asl scelta */
		$("#searchidComune").on("change",function(evt){
			var idcomune = +($(this).val());
			if(idcomune == -1)
			{ /*ho scelto nessun comune */
				return ; 
			}
			var idAslPerComune = comuniToAsl[idcomune]; /*questa e' l'asl che dovrebbe essere associata al comune */
			/*ho scelto comune, quindi lo paragono con l'asl selezionata A PATTO DI
			AVER SELEZIONATO UN VALORE SPECIFICO PER ASL (QUINDI NON TUTTE LE ASL, NON TUTTE LE ASL CAMPANE, NON SOLO FUORI REGIONE )
			  */
			var idAslSelezionato =  +($("#searchidAslString").val());
			if(idAslSelezionato > 0 && idAslSelezionato != 16 && idAslSelezionato != 98) /*16 e' fuori regione , 98 e' tutte asl campane */
			{
				if(idAslPerComune != idAslSelezionato)
				{
					alert("Attenzione, il comune selezionato non appartiene all'asl scelta!");
					/*RIMETTO IL VECCHIO VALORE */
					$("#searchidComune").val(previousComuneScelto); 
				}
			}
						
		});
		
		/*simulo click sul radio  button all'avvio */
		$("input[type='radio'][name='searchricercaPer'][value='apicoltore']").trigger("click");
		
	});







 	
	
	
 	function initComuniToAsl()
 	{
 		comuniToAsl = {};
 		<% if(ComuniToAsl != null) {
			
 				for(Object idComune : ComuniToAsl.keySet())
 				{%>
 					/*costruisco l'oggetto javascript*/
 					comuniToAsl[<%=(Integer)idComune%>] = <%=(Integer)ComuniToAsl.get(idComune) %>;
 				
 				<%}
 		
 			}
 		 %>
 		 
 	}
 	
 	
 	
 	
  
	
	function changeRicercaPer(valore) /*a seconda del tipo di ricerca , blocco i campi e rendo dummy gli input (non li disabilito per retrocompatibilita server side ) */
	{
		/*per semplicita' li resetto tutti e li abilito, e poi disabilito solo quelli che vanno disabilitati */
		
		//$("#tdComune input[type='text']").prop("readonly",false);
		//$("#tdComune input[type='text']").val("");
		//$("#tdComune input[type='text']").css({"background-color":"white"});
		if( $("#searchidAslString").val() != 16) /*NB non riattivo il comune se risulta essere asl fuori regione */
		{
			$("#tdComune select").prop("disabled",false);
			$("#tdComune input[type='hidden']").remove(); /*levo il dummy che eventualmente stava a sostituire la select disabilitata */
			$("#tdComune select").css({"background-color": "white"});
		}
		
		
		$("#tdDenominazione input[type='text']").prop("readonly",false);
		$("#tdDenominazione input[type='text']").val("");
		$("#tdDenominazione input[type='text']").css({"background-color":"white"});
		
		$("#tdCodiceFiscale input[type='text']").prop("readonly",false);
		$("#tdCodiceFiscale input[type='text']").val("");
		$("#tdCodiceFiscale input[type='text']").css({"background-color":"white"});
		
		$("#tdCun input[type='text']").prop("readonly",false);
		$("#tdCun input[type='text']").val("");
		$("#tdCun input[type='text']").css({"background-color":"white"});
		
		if(valore == 'apicoltore'  ) /*disabilito */
		{
			
			//$("#tdComune input[type='text']").prop("readonly","true");
			//$("#tdComune input[type='text']").css({"background-color":"rgba(125,125,125,0.4)"});
			$("#tdComune select").prop("disabled",true); /*per una select non si puo' usare readonly (che fa comunque viaggiare il parametro per non far schiattare server side)
			quindi per disabilitare la select del comune metto disabled, e creo un input type hidden che sostituisce la select, con lo stesso nome */
			$("#tdComune").append($("input",{type : 'hidden', name : 'searchcodecomune', value : '-1'}));
			$("#tdComune select").css({"background-color": "rgba(125,125,125,0.4)"});
		}
		else
		{
			
			$("#tdDenominazione input[type='text']").prop("readonly",true);
			$("#tdDenominazione input[type='text']").css({"background-color":"rgba(125,125,125,0.4)"});
			
			$("#tdCodiceFiscale input[type='text']").prop("readonly",true);
			$("#tdCodiceFiscale input[type='text']").css({"background-color":"rgba(125,125,125,0.4)"});
			
			$("#tdCun input[type='text']").prop("readonly",true);
			$("#tdCun input[type='text']").css({"background-color":"rgba(125,125,125,0.4)"});
			
		}
		
		 
	}
	
	


</script>



</body>

