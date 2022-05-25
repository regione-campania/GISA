<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="IterList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="Address" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl"  class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />



<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<%@ include file="../initPage.jsp"%>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>

    
  



	 

<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script>
    
    function checkForm()
    {
    	document.forms[0].doContinue.value="true";
    	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;

    	controllaCampi = true ;
    	if (document.getElementById("tipo_attivita")!=null && document.getElementById("tipo_attivita").value=='3')
    	{
    		controllaCampi=false ;
    	}
    		
    	if($("#searchcodeIdprovincia").length > 0 && controllaCampi == true)
        {
        	if( document.forms[0].searchcodeIdprovincia.value=='-1')
    		{
    			document.forms[0].doContinue.value="false";
    			msg+= "- Campo provincia sede PRODUTTIVA richiesto \n" ;
    		}
    	}
    	if (parseInt(document.getElementById('id_operatore').value)<=0  )
    		{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Controllare di aver Selezionato L'impresa \n" ;
    		
    		}
    	if(document.forms[0].searchcodeIdComune.value=='-1' && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo comune  richiesto \n" ;
    	}
    	if((document.forms[0].viainput.value=='' || document.forms[0].viainput.value=='Seleziona Indirizzo') && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo indirizzo  richiesto \n" ;
    	}
    	
    	
    	 if (document.forms[0].latitudine && document.forms[0].latitudine.value!=""){
           	 //alert(!isNaN(form.address2latitude.value));

           			if (isNaN(document.forms[0].latitudine.value) ||  (document.forms[0].latitudine.value < 39.988475) || (document.forms[0].latitudine.value > 41.503754)){
			 msg += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
            			 document.forms[0].doContinue.value="false";
            				 

            			 msg += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
            				 formTest = false;
            			}		 

           		//}
        	 }   


       	 
       	 if (document.forms[0].longitudine && document.forms[0].longitudine.value!=""){

          			if (isNaN(document.forms[0].longitudine.value) ||  (document.forms[0].longitudine.value < 13.7563172) || (document.forms[0].longitudine.value > 15.8032837)){

           			 msg += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
           			document.forms[0].doContinue.value="false";
           				 

           			 msg += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
           				 formTest = false;
           			}		 

       	 }   


    	if($("#codFiscaleSoggetto").length =0 && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo codice fiscale soggetto richiesto \n" ;
    	}

    	if($("#nome").length > 0 && document.forms[0].nome.value=='' && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo nome soggetto fisico richiesto \n" ;
    	}

    	if($("#cognome").length > 0 &&document.forms[0].cognome.value=='' && controllaCampi == true)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo cognome soggetto fisico richiesto \n" ;
    	}
    	
    	if(document.forms[0].numLineeProduttive.value=="0")
    		{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Controllare di Aver Selezionato almeno una Linea Produttiva \n" ;
    		}

		
    
    	if(document.forms[0].doContinue.value=="false")
        {
    		$('#mask').hide();
			$('.window').hide();
    		alert(msg);
    		
    	}
    	else
    	{
    		document.forms[0].doContinue.value="true";
    		document.forms[0].submit();
    	}
    }    
    
    function popUp(url) 
    {
    	  title  = '_types';
    	  width  =  '500';
    	  height =  '600';
    	  resize =  'yes';
    	  bars   =  'yes';
    	  var posx = (screen.width - width)/2;
    	  var posy = (screen.height - height)/2;
    	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
    	  var newwin=window.open(url, title, windowParams);
    	  newwin.focus();
		  if (newwin != null) 
		  {
    	    	if (newwin.opener == null)
    	      		newwin.opener = self;
    	  }
    	}

    $(function() {
        $( "#searchcodeIdprovincia" ).combobox();
        $( "#searchcodeIdComune" ).combobox();
        $( "#via" ).combobox();
        $( "#addressLegaleCity" ).combobox();
        $( "#addressLegaleCountry" ).combobox();
        $( "#addressLegaleLine1" ).combobox();
       
    });
  </script>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<table class="trails" cellspacing="0">
		<tr>
			<td>Impresa -> Aggiungi Stabilimento </td>
		</tr>
	</table>
</dhv:evaluate>

<%
if (request.getAttribute("Exist") != null && !("").equals(request.getAttribute("Exist"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
}
if (request.getAttribute("Error") != null && !("").equals(request.getAttribute("Error"))) 
{
%> 
<font color="red"><%=(String) request.getAttribute("Error")%></font>
<%
}

%> 

<form id="addStabilimento" name="addStabilimento" action="<%=newStabilimento.getAction() %>.do?command=Insert&auto-populate=true" method="post" onsubmit="javascript:loadModalWindow();">
<%
	boolean popUp = false;
  	if(request.getParameter("popup")!=null)
  	{
    	popUp = true;
  	}
%> 
<input type="button" value="Salva" name="Save" onClick="javascript:verificaSoggetto(document.getElementById('codFiscaleSoggetto'))"> <dhv:formMessage showSpace="false" />



<dhv:evaluate if="<%= popUp %>">
	<input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate> 

<br/>

<input type = "hidden" id = "tipoInserimento" name = "tipoInserimento" value = "-1"/>
<input type = "hidden" id = "sovrascrivi" name = "sovrascrivi" value = "si"/>
<input type = "hidden" id = "idAsl" name = idAsl value = ""/>
<input type = "hidden" id = "descrAsl" name = "descrAsl" value = ""/>
<input type = "hidden" name = "tipologiaSoggetto" value = "<%=(request.getAttribute("tipologiaSoggetto")!=null)? (String)request.getAttribute("tipologiaSoggetto"):"" %>">

<br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
			<th colspan="2">
				<dhv:label name="<%="opu.operatore_gisa" %>"></dhv:label>
			</th>
	</tr>

	<dhv:evaluate if="<%= hasText(Operatore.getRagioneSociale()) %>">
		<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1">
						<dhv:label name="opu.operatore.intestazione"></dhv:label>
				</td>
				<td>
					<%= toHtmlValue(Operatore.getRagioneSociale()) %>
				</td>
		</tr>
	</dhv:evaluate>
	<input type="hidden" name="idOperatore" id="idOperatore" value="<%=Operatore.getIdOperatore() %>" /> 

	<dhv:evaluate if="<%= hasText(Operatore.getPartitaIva()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
					<dhv:label name="opu.operatore.piva"></dhv:label>
			</td>
				<td>
					<%= toHtmlValue(Operatore.getPartitaIva()) %>
				</td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%= hasText(Operatore.getCodFiscale()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
				<dhv:label name="opu.operatore.cf"></dhv:label>
			</td>
			<td>
				<%= toHtmlValue(Operatore.getCodFiscale()) %>
			</td>
		</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%= hasText(Operatore.getNote()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
				<dhv:label name="opu.operatore.note"></dhv:label>
			</td>
			<td>
				<%= toHtmlValue(Operatore.getNote()) %>
			</td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%= (Operatore.getRappLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="<%="opu.soggetto_fisico_gisa"%>"></dhv:label>
			</td>
			<td>
				<%= Operatore.getRappLegale().getNome()+ " "+Operatore.getRappLegale().getCognome()+ " ("+Operatore.getRappLegale().getCodFiscale()+")" %>
			<%= (!Operatore.getRappLegale().getIndirizzo().toString().equals("")) ? "<br>Residente in : "+ Operatore.getRappLegale().getIndirizzo().toString() :"" %>
			</td>
		</tr>
	</dhv:evaluate>
	<dhv:evaluate if="<%= (Operatore.getSedeLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
			</td>
			<td>
				<%
					String comune =  ComuniList.getSelectedValue(Operatore.getSedeLegale().getComune());
    				String via = (Operatore.getSedeLegale().getVia()!= null)? Operatore.getSedeLegale().getVia():"";  %>
				<%= Operatore.getSedeLegale().toString() %></td>
		</tr>
	</dhv:evaluate>
	
	<tr>
	<td class="formLabel" nowrap>
			Seleziona Impresa
			</td>
	<td>
	
	
						 [<a onclick="window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true','','width=800,height=900,scrollbars=yes');return false;"
							href="OperatoreAction.do?command=SearchForm&popup=true">Ricerca</a>]
							&nbsp;
							[<a onclick="window.open('OperatoreAction.do?command=Add&popup=true','','scrollbars=yes,width=1000,height=400');return false;"
							href="OperatoreAction.do?command=Add&popup=true">Aggiungi</a>]
							&nbsp;&nbsp;
							<%
					if (Operatore.getIdOperatore()<=0)
					{
					%>
							[ - Selezionare un'impresa tramiteil link Ricerca se esistente o aggiungerla tramite il link aggiungi- ]
								<%} %>
							
</td>
					</tr>
					
					
	
					
				
					
					
</table>


<sc:context id="opu;gisa_nt">
<%@ include file="stabilimento_generic_add.jsp" %>
<sc:namecontext></sc:namecontext>
</sc:context>

<input type="hidden" name="doContinue" id="doContinue" value="">


<input type = "hidden" id = "id_operatore" value = "<%=Operatore.getIdOperatore() %>"/>
</form>


<!-- POP UP MODALE  -->
<div id="boxes">
	<div id="dialog4" class="window" >
   		<a href="#" class="close"/><b>Chiudi</b></a>
  		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<tr>
				<th colspan="2"><strong><div id = "intestazione"></div></strong></th>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Nome</td>
				<td><div id = "nomeSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Cognome</td>
				<td><div id = "cognomeSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Sesso</td>
				<td><div id = "sessoSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Data Nascita</td>
				<td><div id = "dataNascitaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Comune Nascita</td>
				<td><div id = "comuneNascitaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Provincia Nascita</td>
				<td><div id = "provinciaNascitaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Comune Residenza</td>
				<td><div id = "comuneResidenzaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Provincia Residenza</td>
				<td><div id = "provinciaResidenzaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Indirizzo Residenza</td>
				<td><div id = "indirizzoResidenzaSoggetto"></div></td>
			</tr>
			<tr>
				<td class="formLabel" nowrap>Documento</td>
				<td><div id = "documentoSoggetto"></div></td>
			</tr>
		
			<tr id="azione">
				<td><input type = "button" value = "Sovrascrivi" onclick="document.getElementById('sovrascrivi').value='si';checkForm();"/></td>
				<td><input type = "button" value = "Non Sovrascrivere" onclick="document.getElementById('sovrascrivi').value='no';checkForm();" /></td>
			</tr>
		</table>
</div>



<div id="dialog" title="Stabilimento Esistente! vuoi andare al dettaglio ?" style="height: 80px;">
</div>

<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>

