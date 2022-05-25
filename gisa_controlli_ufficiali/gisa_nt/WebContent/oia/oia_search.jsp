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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*"%>
<jsp:useBean id="SearchOrgListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

	
	
	<jsp:useBean id="OggettoAudit" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="lookupTipologia"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookupASL" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	<jsp:useBean id="lookupAudit" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<%@ include file="../initPage.jsp"%>
<script>


function mostraOggettoAudit(value)
{
	if (value=='22')
		{
		document.getElementById("tr_oggetto").style.display="none";
		
		document.getElementById("searchcodeauditTipo").value="-1";
		
		}
	else
		{
		document.getElementById("tr_oggetto").style.display="";
		}
	}
function doSubmit()
{
	formTest=true;
	array =$( "#searchgrouptipologia_struttura option:selected" );
	if (array.length>1)
		{
		for (i=0;i<array.length;i++)
			{
			if (array[i].value=="-1")
				{
				alert('Assicurarsi di non aver scelto la voce TUTTE insieme a una struttura');
				formTest = false;
				}
			}
		}
	
// 	if(document.searchAccount.searchcodeid_asl.value=='-1')
// 	{
// 		alert('Selezionare la struttura soggetta al controllo');
// 		formTest=false;
// 	}
	
	if (formTest==true){
		loadModalWindow();
		document.searchAccount.submit();
	}

}

function doSubmitInserimentoCU(){
	
	var orgId = document.getElementById('searchcodeid_asl').value 
	if (orgId==-1){
		alert('Selezionare ASL');
		return false;
	}
	document.searchAccount.action='OiaVigilanza.do?command=Add&orgId='+orgId+'&idNodo='+orgId;
	loadModalWindow();
	document.searchAccount.submit();
}

function setComboNodi()
{
		

		idasl = document.getElementById('searchcodeid_asl').value ;
		anno = document.getElementById('anno').value ;
		PopolaCombo.getValoriComboStruttureOia(idasl,anno,setComboNodiCallBack);
	   
}

 function setComboNodiCallBack(returnValue)
 {
	 //alert ("returnValue: "+returnValue);
    	var select = document.getElementById("searchgrouptipologia_struttura"); //Recupero la SELECT
     

     //Azzero il contenuto della seconda select
     for (var i = select.length - 1; i >= 0; i--)
   	  select.remove(i);
    	
     
     /* Aggiunta TUTTE*/
     idasl = document.getElementById('searchcodeid_asl').value ;
     
     var NewOpt = document.createElement('option');
     NewOpt.value = -1; // Imposto il valore
     NewOpt.text ='Tutte'; // Imposto il testo
     NewOpt.title = 'Tutte';
     try
     {
   	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
     }catch(e){
   	  select.add(NewOpt); // Funziona solo con IE
     
     }

     indici = returnValue [0];
     valori = returnValue [1];
     //Popolo la seconda Select
     if (indici.length==0)
     {
    	 var NewOpt = document.createElement('option');
         NewOpt.value = -1; // Imposto il valore
    	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
         	NewOpt.title = valori[i];
         //Aggiungo l'elemento option
         try
         {
       	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
         }catch(e){
       	  select.add(NewOpt); // Funziona solo con IE
         }
      }
     else
     {
     for(j =0 ; j<indici.length; j++){
     //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
     var NewOpt = document.createElement('option');
     NewOpt.value = indici[j]; // Imposto il valore
     if(valori[j] != null)
     	NewOpt.text = valori[j]; // Imposto il testo
     	NewOpt.title = valori[j];
     //Aggiungo l'elemento option
     try
     {
   	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
     }catch(e){
   	  select.add(NewOpt); // Funziona solo con IE
     }
     }

     }


 }


</script>
<form name="searchAccount" action="Oia.do?command=ViewVigilanza" method="post">
<%-- Trails --%>

<table class="trails" cellspacing="0">
	<tr>
		<td><a href="Oia.do?command=SearchForm">Autorità Competenti</a>
		> Ricerca</td>
	</tr>
</table>
<%-- End Trails --%>

  
<font color="red" size="3px">&nbsp;&nbsp;&nbsp; Per l'inserimento di un AUDIT / SUPERVISIONE MEDIANTE SIMULAZIONE non va selezionato un tipo audit / simulazione come filtro di ricerca <br/>
&nbsp;&nbsp;&nbsp;  ma occorre ricercare solo la struttura a cui associare il controllo. </font>
 
 
<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2">Informazioni Primarie del soggetto controllato</th>
			</tr>
			
			<tr> <td class="formLabel">Anno</td> 
		<td>
		<select name = "searchcodeanno" id = "anno">
		<option value="2021" selected="selected">2021</option>
		<option value="2020">2020</option>
		<option value="2019">2019</option>
		<option value="2018">2018</option>
		<option value="2017">2017</option>
		<option value="2016">2016</option>
		<option value="2015">2015</option>
		<option value="2014">2014</option>
		<option value="2013">2013</option>
		</select>
		</td>
		</tr>
			
			<tr >
				<td  class="formLabel">Asl</td>
				<td>
				<%
				
				lookupASL.setJsEvent("onChange='setComboNodi()'");
				
				if (User.getSiteId()>0)
				{
				%>
					<%=lookupASL.getHtmlSelect("searchcodeid_asl",-1) %>
				<%}else
					{%>
				<%=lookupASL.getHtmlSelect("searchcodeid_asl",-1) %>
				<%} %>
					<%lookupTipologia.removeAll(lookupTipologia);
					lookupTipologia.setMultiple(true);
					lookupTipologia.setSelectSize(7);
					lookupTipologia.addItem(-1,"Lista Unita Complesse");
					%>
			    <input type="button" onclick='doSubmitInserimentoCU()'	value="Inserisci Controllo Ufficiale">		
				</td>
				
			</tr>
			<tr> <td class="formLabel">Strutture Controllate</td> 
		<td><%=lookupTipologia.getHtmlSelect("searchgrouptipologia_struttura",-1) %>
		&nbsp;	
<font color="red">Attenzione! Di seguito sono riportate tutte le strutture presenti nello strumento di calcolo per cui è stato eseguito il "Salva e Chiudi".<br>
Qualora non fossero presenti le strutture desiderate, controllare che figurino correttamente nello strumento di calcolo e che quest'ultimo sia stato Salvato/Chiuso.
</font>
		
	
		
		</td>
		</tr>
			
		
		<tr> <td class="formLabel">Tecnica di controllo</td> 
		<td>
		<%lookupAudit.setJsEvent("onchange='mostraOggettoAudit(this.value);'"); %>
		<%=lookupAudit.getHtmlSelect("searchcodeaudit",-1) %></td>
		</tr>
		
		<tr id="tr_oggetto"> <td class="formLabel">Motivo Audit</td> 
		<td><%=OggettoAudit.getHtmlSelect("searchcodeauditTipo",-1) %></td>
		</tr>
		
			

		</table>
		</td>
	</tr>

</table>

<input type="button" onclick='doSubmit()'
	value="Ricerca">
	
	
	</form>


