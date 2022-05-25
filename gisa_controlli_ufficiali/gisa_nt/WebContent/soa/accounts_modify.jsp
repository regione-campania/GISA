<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="statoLabImpianti" class="org.aspcfs.utils.web.LookupList"	scope="request" />

<%@page import="org.aspcfs.modules.soa.base.OrganizationAddress"%>
<%@page import="org.aspcfs.modules.soa.base.SottoAttivita"%><jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="categoria" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoAutorizzazzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script>
var campoLat;
	var campoLong;
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

	 var controllo_approval_number = true ;


	function controllo_esistenza_approval_number()
	{
		  
		  if(document.getElementById('numAut')!= null && document.getElementById('numAut').value!='')
		  	PopolaCombo.controlloEsuistenzaApprovalNumber(document.getElementById('numAut').value,controllo_esistenza_approval_number_callback);
	}
	function controllo_esistenza_approval_number_callback(val)
	{
		
		if(val==true)
		{
			alert('Attenzione! Il numero di riconoscimento inserito è assegnato a un altro stabilimento');
			controllo_approval_number = false;
		}
		else
		{
			controllo_approval_number = true ;
		}

	}
	  
	
function viewBodyModify(statoAttuale)
{
	/*if (statoAttuale == 2)
	{
		document.getElementById('esito_controlli').style.display="" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="none" ;
	}*/
	if (statoAttuale == 6 || statoAttuale == 5)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
	if (statoAttuale == 1)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
	if (statoAttuale == 2)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
	if (statoAttuale == 14)
	{
		document.getElementById('esito_controlli').style.display="none" ;
		document.getElementById('informazioni_principali').style.display="none" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	else
		if (statoAttuale == 0 || statoAttuale=='null')
		{
			
			document.getElementById('esito_controlli').style.display="none" ;
			document.getElementById('informazioni_principali').style.display="none" ;
			document.getElementById('lista_impianti').style.display="" ;
		}
	else
	{
		
		document.getElementById('esito_controlli').style.display="" ;
		document.getElementById('informazioni_principali').style.display="" ;
		document.getElementById('lista_impianti').style.display="" ;
	}
	
	
	
}

function checkForm()
{

	formTest = true ;
	msg = "Controllare di aver selezionato le seguenti informazioni : \n";
	if (document.getElementById('informazioni_principali').style.display=="")
	{
		if (document.addAccount.numAut.value == '')
		{
			msg += " - approval number" ;
			formTest = false ;
		}
		if (document.addAccount.data_assegnazione_approval_number.value == '')
		{
			msg += " - Data Assegnazione approval number in sintesi \n" ;
			formTest = false ;
		}

	}
	if(controllo_approval_number==false)
	{
		msg += "Attenzione il numero di riconoscimento inserito è stato assegnato a un altro stabilimento" ;
		formTest = false ;
	}
	

	if (document.getElementById('lista_impianti').style.display=='')
	{
	
		size = parseInt(document.addAccount.size.value)	;

		/*if(document.getElementById("n_s").value=="0")
		{*/
		for (i=1;i<=size;i++)
		{


			if(document.getElementById('statoLab_'+i).value == -'1' )
			{

				
					msg += " - Descrizione Stato Attivita" ;
					formTest = false ;
					break ;
				
			
				
			}
			
			if(document.getElementById('statoLab_'+i).value == '2' )
			{

				if (document.getElementById('dateIS_'+i).value == '')
				{
					msg += " - data inizio sospensione per linee produttive" ;
					formTest = false ;
					break ;
				}
			
				
			}

			/*if(document.getElementById('statoLab_'+i).value == '1' )
			{

				if (document.getElementById('dateF_'+i).value == '')
				{
					msg += " - data Fine per linee produttive" ;
					formTest = false ;
					break ;
				}
			
				
			}*/

			
			
			
			if (document.getElementById('dateI_'+i).value == '')
			{
				msg += " - data inizio sottoattivita per linee produttive" ;
				formTest = false ;
				break ;
			}
			/*if (document.getElementById('tipoAutorizzazzione_'+i)!=null && document.getElementById('tipoAutorizzazzione_'+i).value == '-1')
			{
				msg += " - Tipo autorizzazzione per linee produttive" ;
				formTest = false ;
				break ;
			}*/
		}
		//}
	}

	if (formTest==false)
	{
		alert(msg);
		return false ;
	}
	document.addAccount.submit();
}

	
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>

<%
Integer tipoModifica = (Integer)request.getAttribute("tipoModifica"); %>

<body onload="viewBodyModify(<%=OrgDetails.getStatoIstruttoria() %>)">

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Soa.do">Stabilimenti</a> >Aggiungi Stabilimento 
</td>
</tr>
</table>
<br/>

<form name = "addAccount" method="post" action="Soa.do?command=Update">

<input type = "text" name = "tipoSoa" value = "<%=OrgDetails.getTipoSoa() %>"/>

<input type = "hidden" name = "condizionato" value = "<%=request.getAttribute("Condizionato") %>"/>
<input type = "hidden" name = "condizionato" value = "<%=request.getAttribute("Revoca") %>"/>
<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getOrgId() %>">
<input type = "button" value="Salva" onclick="return checkForm()">
<input type = "hidden" name = "tipoModifica" value = "<%=tipoModifica %>">
<div id = "informazioni_principali" style="display: none">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Informazioni Principali</strong> </th>
		</tr>

				<tr class="containerBody">
					<td nowrap class="formLabel">ASL</td>
					<td><%=SiteList.getSelectedValue(OrgDetails.getSiteId())%> 
					<input type="hidden" name="siteId" value="<%=User.getSiteId()%>">
					</td>
				</tr>
				<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Ragione Sociale
				</td>
				<td>
				<%=OrgDetails.getName() %>
				<input type = "hidden" name = "name" readonly="readonly" value="<%=OrgDetails.getName() %>" id = "name" maxlength="85" size="50" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";>
				</td>
			</tr>
		
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA
				</td>
				
				<td><%=OrgDetails.getPartitaIva() %><input type = "hidden" name = "piva"  readonly="readonly" value="<%=OrgDetails.getPartitaIva() %>" id = "piva" maxlength="11" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";></td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><input type = "hidden" name = "cf_stabilimento" id = "cf_stabilimento" style="background: none repeat scroll 0% 0% rgb(255, 255, 255)";></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data presentazione istanza</dhv:label>
				</td>
				<td>
						<%=OrgDetails.getDate2String() %>
						<input type="hidden" name="dateI" size="10" value="<%=OrgDetails.getDate2String() %>" />&nbsp;
	  			</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Stato Stabilimento</dhv:label>
				</td>
				<td>
				<input type = "hidden" name = "statoLab" value ="<%=OrgDetails.getStatoLab() %>"> 
					<%=statoLab.getSelectedValue(OrgDetails.getStatoLab())%>&nbsp;	  		
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Data Assegnazione in Sintesi</td>
				<% %>
				<td><input type = "text" id = "data_assegnazione_approval_number" name = "data_assegnazione_approval_number" <%if (OrgDetails.getData_assegnazione_approval_number()!=null ) {%>readonly="readonly" <%} %>value = "<%=toHtml2(OrgDetails.getData_assegnazione_approval_number_asString()) %>" />
				 <a href = "javascript:popCalendar('addAccount','data_assegnazione_approval_number','it','IT','Europe/Berlin');">
      			    <img align="absmiddle" border="0" src="images/icons/stock_form-date-field-16.gif"></a>
				<font color = "red">*</font></td>
			
			</tr> 
			
			<tr class="containerBody">
				<td nowrap class="formLabel">Approval Number</td>
				
				<td><input type = "text" id = "numAut" name = "numAut" <%if (OrgDetails.getNumAut()!=null && ! "".equals(OrgDetails.getNumAut())) {%>readonly="readonly" <%} %>value = "<%=toHtml2(OrgDetails.getNumAut()) %>" onchange="javascript : controllo_esistenza_approval_number()"/><font color = "red"</td>
			</tr> 
			
		</table>
		
	<br/>
	<br/>
	<br>
		<br>
		<%
			if(OrgDetails.getNomeRappresentante()!=null && ! "".equals(OrgDetails.getNomeRappresentante())) 
			{
				%>
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
      			<dhv:label name="">Comune di residenza</dhv:label>
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
      			<dhv:label name="">Domicilio digitale</dhv:label>
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
		
</table>
<br>
				<%
				
			}
		%>
		<br><br>
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
      			<td nowrap class="formLabel" valign="top"><%=toHtml(thisAddress.getTypeName())%></td>
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
	
	<br/>
	<br/>
	</div>
	
	<br/>
	<br/>
	<div id = "lista_impianti" style="display: none">
	<input type = "hidden" id = "size" name = "size" value = "<%=elencoSottoAttivita.size() %>">
	<input type = "hidden" id = "elementi" name = "elementi" value = "<%=elencoSottoAttivita.size() %>">
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id = "attivita">
  			<tr>
    			<th colspan="11"><strong>Linee Produttive</strong></th>
  			</tr>
			
			<tr class="formLabel">
					<td width ="15%" align="left">Impianto/attivit&agrave;</td>
				<td width ="15%" align="left">Categoria/Sezione</td>
				<td width ="10%" align="left">Prodotti</td>
				<td width ="10%" align="left">Descrizione Stato Attività</td>
				<%if(OrgDetails.getStatoIstruttoria()==0)
      			{ 
      			%>
      			
      					<td width ="10%" align="left">Data Inizio Sospensione</td>
			
      			
				<td width ="10%" align="left">Data Fine</td>
				
				
				<%}
				else
				{
					%>
					<td width ="10%" align="left">Data Inizio</td>
					<%
					
				}
				%>
				<td width ="10%" align="left"  >Autorizzazione</td>	
<!--				<td width ="3%" align="left" >Produzione con Riti Religiosi</td>	-->
<!--				<td width ="3%" align="left" >Prodotti Imballati</td>	-->
<!--				<td width ="3%" align="left" >Prodotti non Imballati</td>	-->
			</tr>
			
				<%
				int i = 1 ;
				Iterator iElencoAttivita = elencoSottoAttivita.iterator();
				
					while (iElencoAttivita.hasNext()) {
						SottoAttivita thisAttivita = (SottoAttivita) iElencoAttivita.next();
			%>    
			<input type = "hidden" name = "id_<%=i %>" value = "<%=thisAttivita.getId() %>">
    		<tr class="containerBody">
      			<td>
      				<%=toHtml(thisAttivita.getAttivita())%>&nbsp;
      				<input type = "hidden" name = "impianto_<%=i %>" value = "<%=thisAttivita.getCodice_impianto() %>" />
      			</td>
      			<td>
      				<%= categoriaList.getSelectedValue( thisAttivita.getCodice_sezione() ) %>&nbsp;
      				<input type = "hidden" name = "categoria_<%=i %>" value = "<%=thisAttivita.getCodice_sezione() %>" />
      			</td>
      			<td>
      				<%
      					for(Integer idProdotto : thisAttivita.getListaProdotti())
      					{
      						out.println(" - "+	LookupProdotti.getSelectedValue( idProdotto));
      					%>
      					<input type = "hidden" name = "prodotti_<%=i %>" value = "<%=idProdotto %>" />
      					<%	
      					
      					}
      					%>
      					&nbsp;
      				
      			</td>
      			
      			<td>
      				<%
      					if(OrgDetails.getStatoIstruttoria()==2)
      					{
      						out.print(statoLabImpianti.getSelectedValue(thisAttivita.getStato_attivita()));
      						%>
      						<input type = "hidden" name = "statoLab_<%=i %>" value = "<%=thisAttivita.getStato_attivita() %>">
      						<%
      					}
      					else
      					{  
      				%>
        				
        				<%if (OrgDetails.getTipoSoa()==1)
        					{%>
        				<%=(thisAttivita.getStato_attivita() ==3 ) ? statoLabImpianti.getHtmlSelect("statoLab_"+i,5) : statoLabImpianti.getHtmlSelect("statoLab_"+i,thisAttivita.getStato_attivita())%>&nbsp;
        				<%}
        				else
        				{
        					%>
        					<%=statoLabImpianti.getHtmlSelect("statoLab_"+i,0) %>
        					<%
        				}
        				%>
        				
        				<%} %>
      			</td>
      			
      			<%if(OrgDetails.getStatoIstruttoria()==0)
      			{ 
      				%>
      				
      				<td>
      				<input type="text"  size="10" id="dateIS_<%=i %>" name="dateIS_<%=i %>" value = "<%=thisAttivita.getData_inizio_sospensioneAsString() %>">
      			    <a href = "javascript:popCalendar('addAccount','dateIS_<%=i %>','it','IT','Europe/Berlin');">
      			    <img align="absmiddle" border="0" src="images/icons/stock_form-date-field-16.gif"></a>
      			     <font color = 'red'>*</font>
      				</td>
      			
      				
      				
      				<td>
      				<input type="hidden"  size="10" id="dateI_<%=i %>" name="dateI_<%=i %>" value = "<%=thisAttivita.getData_inizio_attivitaAsString() %>">
      				<input type="text" value="" size="10"  id="dateF_<%=i %>" name="dateF_<%=i %>"  value = "<%=thisAttivita.getData_fine_attivitaAsString() %>">
      			    <a href = "javascript:popCalendar('addAccount','dateF_<%=i %>','it','IT','Europe/Berlin');">
      			    <img align="absmiddle" border="0" src="images/icons/stock_form-date-field-16.gif"></a>
      				</td>
      				<%
      				
      			}
      			else
      			{ 
      			%>
      			
      			
      			<td>
      			<input type="text"  size="10"  id="dateI_<%=i %>" name="dateI_<%=i %>"  value = "<%=thisAttivita.getData_inizio_attivitaAsString() %>">
      			    <a href = "javascript:popCalendar('addAccount','dateI_<%=i %>','it','IT','Europe/Berlin');">
      			    <img align="absmiddle" border="0" src="images/icons/stock_form-date-field-16.gif"></a>
      			    
      			    <input type="hidden" value="" size="10"  id="dateF_<%=i %>" name="dateF_<%=i %>"  value = "<%=thisAttivita.getData_fine_attivitaAsString() %>">
				</td>
		
			<%} %>
      			
      			<td>
      				
      				<%if(OrgDetails.getStatoIstruttoria()==2 )
  					{
      					%>
      					<%= tipoAutorizzazioneList.getHtmlSelect("tipoAutorizzazzione_"+i , 2 )%>&nbsp;
      					<%
  					}
      				else
      				{
      					%>
  					
      				<input type = "hidden"  id = "tipoAutorizzazzione_<%=i %>"  name = "tipoAutorizzazzione_<%=i %>" value = "<%=thisAttivita.getTipo_autorizzazione() %>" />
					<input type = "hidden" name = "riti_religiosi_<%=i %>" <%if(thisAttivita.isRiti_religiosi()) {%>value = "on"<% }else {%>value = "off"<%}%> > &nbsp;
      				<input type = "hidden" name = "imballata_<%=i %>" <%if(thisAttivita.getImballata()==1) {%>value = "on"<% }else {%>value = "off"<%}%> >
      				<input type = "hidden" name = "non_imballata_<%=i %>" <%if(thisAttivita.getNon_imballata()==1) {%>value = "on"<% }else {%>value = "off"<%}%> >
      				<%=(thisAttivita.getTipo_autorizzazione() >0) ? tipoAutorizzazioneList.getSelectedValue( thisAttivita.getTipo_autorizzazione() ) : ""%>&nbsp;
      			<%} %>

      			</td>
      			

      			
    		</tr>
			<%
			i++;
					}
				
			%>
			
		
		</table>
		</div>
		<br><br>
		
		<div id = "esito_controlli" style="display: none">
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Completamento Istruttoria</dhv:label></strong>
	  </th>
  </tr>
  <tr >
      <td nowrap class="formLabel">
        Data Completamento
      </td>
      <td>
              	<zeroio:dateSelect form="addAccount" field="data1" timestamp="<%= "" %>" showTimeZone="false" /><font color="red">*</font>


      </td>
    </tr>
  
  <tr style="display: none">
	<td nowrap class="formLabel" name="nameMiddle" id="nameMiddle">
      <dhv:label name="requestor.requestor_add.Classificatio">Esito</dhv:label>
    </td> 
    <td> 
	<select name="nameMiddle">
			<option value=" "><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            <option value="Favorevole">Favorevole</option>
			<option value="Non favorevole">Non favorevole</option>
			<option value="Favorevole con lievi non conformita'">Favorevole con lievi non conformita'</option>
    </select> 
	</td>
  	</tr>
  	 	<tr>
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Note</dhv:label>
      </td>
      <td>
      	<textarea rows="6" cols="40" name = "note"></textarea>
      </td>
    </tr>
						
	
    
  	<tr style="display: none">
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Data Esito</dhv:label>
      </td>
      <td>
      	<div id="data3">
        	<zeroio:dateSelect form="addAccount" field="data3" timestamp="<%= "" %>" showTimeZone="false" /><font color="red">*</font>
        </div>
      </td>
    </tr>
    <input type = "hidden" id = "statoCorrente" value = "<%=OrgDetails.getStatoIstruttoria() %>">
  <%
						int statoIstruttoria = OrgDetails.getStatoIstruttoria();
						
						if(statoIstruttoria==6)
						{
							%>
							<input type ="hidden" name = "nuovoStato" id = "n_s" value = "7"/>
							<%
							
						}
						else
						{
							if(statoIstruttoria==3)
							{
							%>
							<input type ="hidden" name = "nuovoStato" id = "n_s" value = "0"/>
							<%
							}
							else
								if(statoIstruttoria==8)
								{
								%>
								<input type ="hidden" name = "nuovoStato" id = "n_s" value = "0"/>
								<%
								}else
									if(statoIstruttoria==2)
									{
									%>
									<input type ="hidden" name = "nuovoStato" id = "n_s" value = "4"/>
									<%
									}
									else
									{%>
									<input type ="hidden" name = "nuovoStato" id = "n_s" value = "0"/>
									<%}
						}
					
					
					%>  	
  
  </table>
  </div>
</form></body>