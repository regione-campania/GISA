<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="org.aspcfs.modules.soa.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.soa.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page  import="java.util.Date"%>



<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request" />
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="statiStabilimenti" class="org.aspcfs.utils.web.LookupList" 	scope="request" />

<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="tipoAutorizzazzione" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="impreseAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="stabilimentiAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="ControlloDocumentale" class="org.aspcfs.modules.stabilimenti.base.ControlloDocumentale" scope = "request"></jsp:useBean>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script>
function openPopupModulesPdf(orgId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
}
</script>
<script>
var campoLat;
var campoLong;
var field_id_impianto ;
function setComboImpianti(idCategoria,idField)
{
		field_id_impianto = idField;
		
	   	PopolaCombo.getValoriComboImpiantiSoa(idCategoria,setValoriComboCallBack);
	   
}

 function setValoriComboCallBack(returnValue)
 {
    	var select = document.getElementById(field_id_impianto); //Recupero la SELECT
     

     //Azzero il contenuto della seconda select
     for (var i = select.length - 1; i >= 0; i--)
   	  select.remove(i);

     indici = returnValue [0];
     valori = returnValue [1];
     //Popolo la seconda Select
     if (indici.length==0)
     {
    	 var NewOpt = document.createElement('option');
         NewOpt.value = -1; // Imposto il valore
    	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
         	
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

 var field_id_prodotti ;
 function setComboProdotti(idCategoria,idField)
	{
	 	field_id_prodotti = idField;
			
		 PopolaCombo.getValoriComboProdottiSoa(idCategoria,setComboProdottiCallBack);
		   
	}

	 function setComboProdottiCallBack(returnValue)
     {
	    	var select = document.getElementById(field_id_prodotti); //Recupero la SELECT
         

         //Azzero il contenuto della seconda select
         for (var i = select.length - 1; i >= 0; i--)
       	  select.remove(i);

         indici = returnValue [0];
         valori = returnValue [1];
         //Popolo la seconda Select
         if (indici.length==0)
         {
        	 var NewOpt = document.createElement('option');
             NewOpt.value = -1; // Imposto il valore
        	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
             	
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
        	 var NewOpt = document.createElement('option');
             NewOpt.value = -1; // Imposto il valore
        	 	NewOpt.text = 'Seleziona uno o piu prodotti'; // Imposto il testo
             
             	NewOpt.selected = true ;
             //Aggiungo l'elemento option
             try
             {
           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
             }catch(e){
           	  select.add(NewOpt); // Funziona solo con IE
             }
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

	 function setComboImpiantiProdotti(idCategoria,idField1,idField2)
	 {
		setComboImpianti(idCategoria,idField1);
		setComboProdotti(idCategoria,idField2);
	 }
	     

	
  	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
  	{
	   campoLat = campo_lat;
	   campoLong = campo_long;
	   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
	   
	   
	}
	function setGeocodedLatLonCoordinate(value)
	{
		campoLat.value = value[1];;
		campoLong.value =value[0];
		
	}

	 function doCheck(form){
		 
			  if(checkForm(form)) {
				  form.submit();
				  return true;
			  }
			  else
				  return false;
			  
		  }
	  
	function checkForm(form,sottoattivita)
	{
		formTest=true ;
		msg='';
	
		numSottoAttivita = form.size.value ;
		


		if (parseInt(numSottoAttivita)==0)
		{
			msg +=' - Controllare di aver Inserito almeno una sottoattivita\n';
			formTest = false ;
		}
		else
		{
		for(i=(sottoattivita+1);i<=parseInt(numSottoAttivita);i++)
		{
			if(document.getElementById('impianto_'+i).value=='-1')
			{
				msg +=' - Controllare di aver Selezionato un impianto per la sottoattivita '+i+'\n';
				formTest = false ;
			}
			if(document.getElementById('categoria_'+i).value=='-1')
			{
				msg +=' - Controllare di aver Selezionato una categoria per la sottoattivita '+i+'\n';
				formTest = false ;
			}
			
			if(document.getElementById('dateI_'+i).value=='')
			{
				msg +=' - Controllare di aver Selezionato la data inizio per la sottoattivita '+i+'\n';
				formTest = false ;
			}
	
		}
	}	

		if(formTest==false)
		{
			alert(msg);
		}
		return formTest;
		
	
	}
	function azzeraElementi()
	{
		document.getElementById("elementi").value = "0" ;
		document.getElementById("size").value = "0" ;
	}
</script>

<%@ include file="../initPage.jsp"%>




<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="Soa.do">Soa</a> > <%
			if (request.getParameter("return") == null) {
			%>
			<a href="Soa.do?command=Search"><dhv:label
				name="stabilimenti.SearchResults">Search Results</dhv:label></a> > <%
			} else if (request.getParameter("return").equals("dashboard")) {
			%>
			<a href="Soa.do?command=Dashboard">Cruscotto</a> > 
			<%
			}
			%>
			Richiesta di Istruttoria Per Stabilimento Esistente </td>
		</tr>
	</table>
	
</dhv:evaluate>


<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>



<%
				boolean  isMercatoIttico=false;
				Iterator iElencoAttivita_ = elencoSottoAttivita.iterator();
				if (iElencoAttivita_.hasNext()) {
					while (iElencoAttivita_.hasNext()) {
						SottoAttivita thisAttivita_ = (SottoAttivita) iElencoAttivita_.next();
						
						if ( (thisAttivita_.getCodice_impianto() == 15) && ( thisAttivita_.getCodice_sezione() == 8) ) {
							isMercatoIttico=true;
						}
						else
						{
							
						}
					}
				}
%>



		
		
	
	<form name = "addAccount" method = "post" action = "Soa.do?command=UpdateIstruttoria" onsubmit="return checkForm(document.addAccount,<%=elencoSottoAttivita.size() %>);">
	<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getOrgId() %>">
	<%-- STABILIMENTO IN ISTRUTTORIA PRELIMINARE 	PERMESSI DA ATTIVARE SOLO ALL'ASL --%>
	
		
	
		
	
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
			</th>
		</tr>

		
			<dhv:evaluate if="<%= SiteList.size() > 1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="stabilimenti.site">Site</dhv:label></td>
					<td><%=SiteList.getSelectedValue(OrgDetails.getSiteId())%> <input
						type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
					</td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%= SiteList.size() <= 1 %>">
				<input type="hidden" name="siteId" id="siteId" value="-1" />
			</dhv:evaluate>
		

			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getName())%>&nbsp;</td>
			</tr>
	

		<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="organization.accountNumber">Account Number</dhv:label></td>
				<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Approval Number</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getNumAut())%></td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Categoria</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getCategoria())%></td>
			</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA
				</td>
				<td><%=toHtml(OrgDetails.getPartitaIva())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><%=toHtml(OrgDetails.getCodiceFiscale())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		
		<dhv:include name="organization.alert" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getAlertText()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.AlertDescription">Alert Description</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getAlertText())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
	
		<dhv:include name="organization.directBill" none="true">
			<dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">Direct Bill</td>
					<td><input type="checkbox" name="directBill" CHECKED DISABLED /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= OrgDetails.getStatoLab() !=  -1 %>">
			<tr class="containerBody">
				<td name="statoLab1" id="statoLab1" nowrap class="formLabel"><dhv:label
					name="">Stato Soa</dhv:label></td>
				<td><%=statoLab.getSelectedValue(OrgDetails.getStatoLab())%>
				<input type="hidden" name="statoLab"
					value="<%=OrgDetails.getStatoLab()%>"></td>
			</tr>
		</dhv:evaluate>
 
 <% if(Audit!=null){ %>
  
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= (((OrgDetails.getCategoriaRischio()>0))?(OrgDetails.getCategoriaRischio()):("3"))%>
      </td>
    </tr>
  
 
    
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
      </td>
      <td>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
    
      
       %>
       <%= (((OrgDetails.getProssimoControllo()!=null))?(dataPC.format(OrgDetails.getProssimoControllo())):(dataPC.format(d)))%>

      </td>
    </tr>
  <%}%>

		<dhv:include name="organization.date1" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.date1">Date1</dhv:label></td>
					<td><zeroio:tz timestamp="<%= OrgDetails.getDate1() %>"
						dateOnly="true" showTimeZone="false" default="&nbsp;" /> <%=showAttribute(request, "contractEndDateError")%>
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="organization.rating" none="true">
			<dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="sales.rating">Rating</dhv:label>
					</td>
					<td><%=RatingList.getSelectedValue(OrgDetails.getRating())%>
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
		
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data presentazione istanza</dhv:label>
				</td>
				<td><zeroio:tz timestamp="<%= OrgDetails.getDate2() %>"
					dateOnly="true" showTimeZone="false" default="&nbsp;" /></td>
			</tr>
		</dhv:evaluate>
		
		
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Stato Pratica</dhv:label>
				</td>
				<td><%=statiStabilimenti.getSelectedValue(OrgDetails.getStatoIstruttoria()) %>
				<%
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				if (OrgDetails.getStatoIstruttoria()==7)
				{
					Timestamp data_assegnazione = OrgDetails.getData_assegnazione_approval_number();
					data_assegnazione.setMonth(data_assegnazione.getMonth()+3);
					out.println("Scadenza Pratica : "+sdf.format(new Date(data_assegnazione.getTime())));
				}
				if (OrgDetails.getStatoIstruttoria()==9)
				{
					Timestamp data_assegnazione = OrgDetails.getData_assegnazione_approval_number();
					data_assegnazione.setMonth(data_assegnazione.getMonth()+6);
					out.print("Scadenza Pratica : "+sdf.format(new Date(data_assegnazione.getTime())));
				}
				%>
				
				</td>
			</tr>
		

		
		<dhv:include name="organization.contractEndDate" none="true">
			<dhv:evaluate
				if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.ContractEndDate">Contract End Date</dhv:label>
					</td>
					<td><zeroio:tz
						timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true"
						timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>"
						showTimeZone="false" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
			
			
		</dhv:include>
		
		</table>
		<br>
		<br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="2"><strong><dhv:label name="">Indirizzo</dhv:label></strong></th>
  			</tr>
			<%
				Iterator iaddress = OrgDetails.getAddressList().iterator();
				if (iaddress.hasNext()) {
					while (iaddress.hasNext()) {
						OrganizationAddress thisAddress = (OrganizationAddress) iaddress
						.next();
			%>    
    		<tr class="containerBody">
      			<td nowrap class="formLabel" valign="top"><%=(thisAddress.getType()==1)?"Sede Legale-Domicio Fiscale" : toHtml(thisAddress.getTypeName())%></td>
      			<td>
        			<%=toHtml(thisAddress.toString())%>&nbsp;<br/><%=thisAddress.getGmapLink() %>
        			<dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          				<dhv:label name="account.primary.brackets">(Primary)</dhv:label>
        			</dhv:evaluate>
      			</td>
    		</tr>
			<%
				}
				} else {
			%>
    		<tr class="containerBody">
      			<td colspan="2">
        			<font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      			</td>
    		</tr>	
			<%
				}
				%>
		</table>
		</div>
		<br/><br/>
	
		
		<%
			if(OrgDetails.getNomeRappresentante()!=null && ! "".equals(OrgDetails.getNomeRappresentante())) 
			{
				%>
					<div id="tab2" class="tab">
		
				<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>

  <dhv:evaluate if="<%= (hasText(OrgDetails.getCodiceFiscaleRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= toHtml((OrgDetails.getCodiceFiscaleRappresentante()) )%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= (hasText(OrgDetails.getNomeRappresentante())) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= toHtml((OrgDetails.getNomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= (hasText(OrgDetails.getCognomeRappresentante())) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= toHtml((OrgDetails.getCognomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null)  %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
    
        <%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
         </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di nascita</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getCity_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di Residenza</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getCity_legale_rapp())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getProv_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Provincia</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getProv_legale_rapp())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getAddress_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Indirizzo</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getAddress_legale_rapp())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= (hasText(OrgDetails.getEmailRappresentante())&& (!OrgDetails.getEmailRappresentante().equals("-1"))) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getEmailRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= (hasText(OrgDetails.getTelefonoRappresentante()) && (!OrgDetails.getTelefonoRappresentante().equals("-1"))) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getTelefonoRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getFax())&& (!OrgDetails.getFax().equals("-1"))) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getFax()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<!--  fine delle modifiche -->
		
</table></div>
<br>
				<%
				
			}
		%>
		
		
		
		
		
		
		<br>
			<div id="tab3" class="tab">
		<a href = "javascript:clonaNelPadre()" > Aggiungi Attività</a>
		<font color = "red">Da qui è possibile aggiungere nuove linee produttive per lo stabilimento (sezione/attivita)</font>
	<br/>
	<br/>
	<input type = "hidden" id = "size" name = "size" value = "<%=elencoSottoAttivita.size() %>">
	<input type = "hidden" id = "elementi" name = "elementi" value = "<%=elencoSottoAttivita.size() %>">
	
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="9"><strong><dhv:label name="">Linee Produttive</dhv:label></strong></th>
  			</tr>
			<%
				Iterator iElencoAttivita = elencoSottoAttivita.iterator();
				if (iElencoAttivita.hasNext()) {
			%>
			<tr class="formLabel">
				<td  align="left">Categoria/sezione</td>
				<td  align="left">Impianto/Attivit&agrave;</td>
				<td  align="left">Prodotti</td>
				<td  align="left">Descrizione Stato Attivita'</td>
				<td  align="left" >Produzione con Riti Religiosi</td>	
				<td  align="left" >Prodotti Imballati</td>	
				<td  align="left" >Prodotti non Imballati</td>
				
					</tr>
					
					<tr class="containerBody" id = "row" style="display: none">
    		
    		<td>
	  		<select name = "categoria_0" id = "categoria_0" onchange="setComboImpiantiProdotti(this.value,'impianto_0','prodotti_0')">
					<%
					Iterator	it = categoriaList.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
				</td>
      			
				<td>
				<select name = "impianto_0"  id = "impianto_0" >
					<%
					 it = impianto.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
				</td>
		   		
				<td>
				
	  			<select name = "prodotti_0"  id = "impianto_0" multiple="multiple" >
					<%
					it = LookupProdotti.iterator();
					while(it.hasNext())
					{
						LookupElement el = (LookupElement)it.next();
						%>
						
						<option value = "<%=el.getCode() %>" <%if(el.getCode()==-1){ %>selected="selected"<%} %> title="<%=el.getDescription().toUpperCase() %>"><%=el.getDescription() %></option>
						<%
					}
					%></select> &nbsp;
				</td>
				
				<td>
				<%
				int statoInDomanda = 3 ;
			%>
				<%=statoLab.getSelectedValue(statoInDomanda)%>
					<input type = "hidden" name = "statoLab_0" value = "<%=statoInDomanda %>"	>
	  			
				</td>

				<td >
	  				<input type="checkbox" name = "riti_religiosi_0" value = "SI">
				</td>
				<td >
	  				<input type="checkbox" name = "imballata_0" value = "SI">
				</td>
				<td >
	  				<input type="checkbox" name = "non_imballata_0" value = "SI">
				</td>
					
			
    		</tr>
			<%
					while (iElencoAttivita.hasNext()) {
						SottoAttivita thisAttivita = (SottoAttivita) iElencoAttivita.next();
			%>    
    		<tr class="containerBody">
      			<td>
      				<%= categoriaList.getSelectedValue( thisAttivita.getCodice_sezione() ) %>&nbsp;
      			</td>
      			<td>
      			<%=toHtml(thisAttivita.getAttivita())%>&nbsp;
      				
      			</td>
      			<td>
      				<%
      					if (thisAttivita.getListaProdotti().isEmpty())
      					{
      						out.println("-");
      					}
      					else
      					for(Integer idProdotto : thisAttivita.getListaProdotti())
      					{
      						out.println(" - "+	LookupProdotti.getSelectedValue( idProdotto)+"<br/>");
      					%>
      					
      					<%	
      					
      					}
      					%>
      					&nbsp;
      				
      			</td>
      		
      			
      			<td>
        			<%=toHtml(thisAttivita.getDescrizione_stato_attivita())%>&nbsp;
      			</td>
      			
      			<td>
      				<input type = "checkbox" <%if(thisAttivita.isRiti_religiosi()) {%>checked="checked"<% }%> disabled="disabled"> &nbsp;
      			</td>
      			
      			<td>
      				<input type = "checkbox" <%if(thisAttivita.getImballata()==1) {%>checked="checked"<% }%> disabled="disabled"> &nbsp;
      			</td>
      			
      				<td>
      				<input type = "checkbox" <%if(thisAttivita.getNon_imballata()==1) {%>checked="checked"<% }%> disabled="disabled"> &nbsp;
      			</td>
      			
      		
      			
      			
    		</tr>
			<%
					}
				} else {
			%>
    		<tr class="containerBody">
      			<td colspan="2">
        			<font color="#9E9E9E"><dhv:label name="">Attività non inserite.</dhv:label></font>
      			</td>
    		</tr>	
			<%
				}
			%>
		</table>
		</div>
		<br>
		
		
  		<input type = "hidden" name = "tipo_istruttoria" value = "2">
<input type = "submit" value = "Salva Richiesta">
		</form>
