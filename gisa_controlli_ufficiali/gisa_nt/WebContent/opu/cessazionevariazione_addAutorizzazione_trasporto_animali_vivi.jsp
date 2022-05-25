<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page
	import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>

<%@page import="org.aspcfs.modules.opu.base.StabilimentoList"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%><jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="contactList"
	class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="ContactAddressTypeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="Operatore"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="TicketDetails"
	class="org.aspcfs.modules.cessazionevariazione.base.Ticket"
	scope="request" />
<jsp:useBean id="ContactPhoneTypeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />

<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/setSalutation.js"></script>

<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestoreCodiceFiscale.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script>


  
</script>



<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/opu.js"></SCRIPT>




<script>
    function checkForm(form){
        formTest = true;
        message = "";
        var curr_date = new Date();
        
    	if(form.assignedDate.value.length!=10)
    	{
    		 message += label("", "- Data Errata\r\n");
    	      formTest = false;
    	}
    	else
    	{
    		data_inserita = form.assignedDate.value ;
    		anno = parseInt(data_inserita.substr(6),10);
    		mese = parseInt(data_inserita.substr(3, 2),10);
    		giorno = parseInt(data_inserita.substr(0, 2),10);
    		
    		var data=new Date(anno, mese-1, giorno);
    	
    		if(data>curr_date)
    		{
    			 message += label("", "- Non è possibile inserire una data maggiore di quella di oggi\r\n");
    		      formTest = false;
    		}
    	}
        if (checkNullString(form.name.value)){
            message += "- Impresa richiesta\r\n";
            formTest = false;
          }
 
        if (checkNullString(form.codFiscaleSoggetto.value) && form.codFiscaleSoggetto.value.length>16){
            message += "- Codice Fiscale del rappresentante Impresa richiesto o non corretto\r\n";
            formTest = false;
          }

        if (checkNullString(form.codFiscaleSoggettoStab.value) && form.codFiscaleSoggettoStab.value.length>16){
            message += "- Codice Fiscale del rappresentante Stabilimento richiesto o non corretto\r\n";
            formTest = false;
          }

        
       
      		    
        if (form.assignedDate.value == "") {
          message += label("check.campioni.data_richiesta.selezionato","- Data Voltura è richiesto\r\n");
          formTest = false;
        }
        if (formTest == false) {
          alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
          return false;
        } else {
        	 if (form.flagAslDiverse.value=='false')
             {
             	 message = "- Attenzione Esistono altri stabilimenti della stessa asl.La voltura comportera' cambiamenti sull'impresa\r\n";
					alert(message);
          	}
             else
             {
     			
             	if (form.flagAslDiverse.value=='true')
                 {
                 	 message = "- Attenzione Esistono altri stabilimenti di ASL diverse.I cambiamenti sull'impresa saranno effettuati se tutte le asl confermano la voltura!\r\n";
                 	alert(message);
              		}
         		
              } 
            form.submit();
          return true;
        }
      }
    


  
   
      
    $(function() {
        //$( "#searchcodeIdComune" ).combobox();
        
    
        $( "#addressLegaleCity" ).combobox();
        $( "#addressLegaleCountry" ).combobox();
        $( "#addressLegaleLine1" ).combobox();


        $( "#addressLegaleCityStab" ).combobox();
        $( "#addressLegaleCountryStab" ).combobox();
        $( "#addressLegaleLine1Stab" ).combobox();
       
    });
    </script>



<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.cessazionevariazione.base.*"%>
<%@ include file="../initPage.jsp"%>

<form name="addticket"
	action="<%=OrgDetails.getAction() %>Cessazionevariazione.do?command=InsertTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>"
	method="post"><%-- Trails --%>
		<input type = "hidden" id = "opId" name = "opId" value = "<%=OrgDetails.getIdOperatore() %>"/>
		<input type = "hidden" id = "stabId" name = "stabId" value = "<%=OrgDetails.getIdStabilimento() %>"/>
		<input type = "hidden" id = "sovrascrivi" name = "sovrascrivi" value = "si"/>
		<input type = "hidden" id = "sovrascriviStab" name = "sovrascriviStab" value = "si"/>
	<%
	Iterator<Stabilimento> itStab = Operatore.getListaStabilimenti().iterator();
	boolean flagAslDiverse = false ;
	int idAsl = -1 ;
	while (itStab.hasNext())
	{
		Stabilimento st = itStab.next() ;
	
		
		if (idAsl != st.getIdAsl() && idAsl!=-1)
		{
			flagAslDiverse = true ;
			break ;
		}
		else
		{
			idAsl = st.getIdAsl();
		}
	}
	
	
	%>

<input type="hidden" name="flagAslDiverse" value = "<%=flagAslDiverse %>"> 
	
	
<table class="trails" cellspacing="0">
	<tr>
		<td>Volture Operatore Unico</td>
	</tr>
</table>
<%-- End Trails --%> <dhv:container name="opu"
	selected="cessazionevariazione" object="OrgDetails"
	param='<%= "stabId=" + OrgDetails.getIdStabilimento()+"&opId="+OrgDetails.getIdOperatore() %>'
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
	<input type="button"
		value="Inserisci"
		name="Save" onClick="verificaSoggetto(document.getElementById('codFiscaleSoggetto'))">
	<br>
	<dhv:formMessage />

	<%-- include basic troubleticket add form --%>

	<%@ page
		import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>

	<script language="JavaScript" TYPE="text/javascript"
		SRC="javascript/popAccounts.js"></script>
	<script language="JavaScript" TYPE="text/javascript"
		SRC="javascript/popServiceContracts.js"></script>
	<script language="JavaScript" TYPE="text/javascript"
		SRC="javascript/popAssets.js"></script>
	<script language="JavaScript" TYPE="text/javascript"
		SRC="javascript/popProducts.js"></script>
	<script language="JavaScript" TYPE="text/javascript"
		SRC="javascript/popURL.js"></script>
	<script language="JavaScript" TYPE="text/javascript"
		SRC="javascript/popCalendar.js"></script>
	<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
		SRC="javascript/popLookupSelect.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
		SRC="javascript/popContacts.js"></SCRIPT>

<input type="hidden" id="idAsl" name=idAsl value="<%=User.getSiteId() %>" /> 


	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="">Voltura</dhv:label></strong>
			</th>
		</tr>

		<dhv:include name="stabilimenti-sites" none="true">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="stabilimenti.site">Site</dhv:label></td>
				<td><%=SiteIdList.getSelectedValue(OrgDetails.getIdAsl())%> <input
					type="hidden" name="siteId" value="<%=OrgDetails.getIdAsl()%>">

				</td>
			</tr>
			<%--</dhv:evaluate>  --%>
			<dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
				<input type="hidden" name="siteId" id="siteId" value="-1" />
			</dhv:evaluate>
		</dhv:include>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="sanzionia.data_richiesta">Data Voltura</dhv:label></td>
			<td><input readonly type="text" id="assignedDate"
				name="assignedDate" size="10" /> <a href="#"
				onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <font color="red">* <%= request.getAttribute("dataVolturaError") != null ? request.getAttribute("dataVolturaError") : "" %></font>
			<%= showAttribute(request, "assignedDateError") %></td>
		</tr>
		<input type="hidden" name="tipo_richiesta"
			value="autorizzazione_trasporto_animali_vivi" />
		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Impresa</dhv:label></td>


			<td><input type="text" size="50" maxlength="80" name="name"
				value="<%= toHtmlValue(Operatore.getRagioneSociale()) %>"><font
				color="#00FF00"><b>* Inserire la variazione</b></font> <input
				type="hidden" name="stabId"
				value="<%=OrgDetails.getIdStabilimento()%>"> <input
				type="hidden" name="orgSiteId" id="orgSiteId"
				value="<%=  OrgDetails.getIdAsl() %>" /></td>

		</tr>
		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Denominazione</dhv:label>
			</td>



			<input type="hidden" size="50" maxlength="80" name="banca" value="">

		</tr>
		<input type="hidden" name="tipo_richiesta"
			value="attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" />

		
		<tr class="containerBody">
			<td class="formLabel" nowrap>Partita IVA
			</td>
			<td><input type="text" size="50" maxlength="80"
				name="partitaIva"
				value="<%= toHtmlValue(Operatore.getPartitaIva()) %>"><font
				color="#00FF00"><b>* Inserire la variazione</b></font></td>
		</tr>
		<tr class="containerBody" style="display: none">
			<td class="formLabel" nowrap>Codice Fiscale
			</td>
			<td><input type="text" size="20" maxlength="16"
				name="codiceFiscale"
				value="<%= toHtmlValue(Operatore.getCodFiscale()) %>"></td>
		</tr>




	</table>
	<br/>
	
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">Rappresentante Sede legale</th>
		</tr>
		<tr>
		<th>&nbsp;</th>
		<th>
			<input type = "text" name="cfSearch"  value = "Ricerca codice Fiscale" onclick="this.value=''" id = "cfSearch" style="background-color: blue;color: white;">
			<a href="#" onclick="ricercaSoggettoFisico(document.getElementById('cfSearch').value)">
				<img src="images/filter.gif">
			</a>
			<a href= "#" onclick="document.getElementById('cfSearch').value=''">
				<img src="images/clear.gif">
			</a>
		</th>
	</tr>
		<tr>
			<td class="formLabel"><dhv:label
				name="opu.sede_legale.inregione"></dhv:label></td>
			<td><select class="todisable" name="inregioneRappOperativo"
				id="inregione">
				<option value="si">SI</option>
				<option value="no">NO</option>
			</select></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.nome"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="nome" name="nome"
				value="<%=Operatore.getRappLegale().getNome() %>"><font
				color="red">*</font></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cognome"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="cognome" name="cognome"
				value="<%=Operatore.getRappLegale().getCognome() %>"><font
				color="red">*</font></td>
		</tr>


		<tr id="">
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.sesso"></dhv:label></td>
			<td><input class="todisable" type="radio" name="sesso"
				id="sesso1" value="M" checked="checked">M <input
				type="radio" name="sesso" id="sesso2" value="F">F</td>
		</tr>



		<tr>
			<td nowrap class="formLabel"><dhv:label
				name="opu.soggetto_fisico.data_nascita"></dhv:label></td>
			<td><input class="todisable" readonly type="text"
				id="dataNascita" name="dataNascita" size="10"
				value="<%=toDateasString(Operatore.getRappLegale().getDataNascita()) %>" />
			<a href="#"
				onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_nascita"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="comuneNascita" name="comuneNascita"
				value="<%=Operatore.getRappLegale().getComuneNascita() %>"></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_nascita"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="provinciaNascita" name="provinciaNascita"
				value="<%=Operatore.getRappLegale().getProvinciaNascita() %>"></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_residenza"></dhv:label></td>

			<td><select class="todisable" name="addressLegaleCountry"
				id="addressLegaleCountry">
				<option
					value="<%=Operatore.getRappLegale().getIndirizzo().getIdProvincia()%>"><%=(Operatore.getRappLegale().getIndirizzo().getIdProvincia()>0)?Operatore.getRappLegale().getIndirizzo().getDescrizione_provincia() : "Inserire le prime 4 lettere".trim()%></option>

			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato
			le prime 5 lettere apparirà in automatico la lista delle province che
			iniziano con le lettere digitate) <font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
			<input type="hidden" name="addressLegaleCountryTesto"
				id="addressLegaleCountryTesto" /> <!--      <input type="text" size="30" maxlength="50" id = "" name="addressLegaleCountry" value="">-->
			</td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_residenza"></dhv:label></td>
			<td><select class="todisable" name="addressLegaleCity"
				id="addressLegaleCity">
				<option
					value="<%=Operatore.getRappLegale().getIndirizzo().getComune()%>"><%=(Operatore.getRappLegale().getIndirizzo().getComune()>0)? Operatore.getRappLegale().getIndirizzo().getDescrizioneComune() : "Inserire le prime 4 lettere".trim()%></option>

			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato
			le prime 5 lettere apparirà in automatico la lista dei comuni che
			iniziano con le lettere digitate) <font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
			<input type="hidden" name="addressLegaleCityTesto"
				id="addressLegaleCityTesto" /></td>
		</tr>





		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.indirizzo"></dhv:label></td>
			<td><select class="todisable" name="addressLegaleLine1"
				id="addressLegaleLine1">

				<option
					value="<%=Operatore.getRappLegale().getIndirizzo().getIdIndirizzo()%>"
					selected="selected"><%=Operatore.getRappLegale().getIndirizzo().getVia()%></option>

			

			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato
			le prime 5 lettere apparirà in automatico la lista degli indirizzi
			che iniziano con le lettere digitate) <font color="red">(*)</font> <input
				type="hidden" name="addressLegaleLine1Testo"
				id="addressLegaleLine1Testo" /> <!--      <input type="text" size="30" maxlength="50" id = "addressLegaleLine1" name="addressLegaleLine1" value="">-->
			</td>
		</tr>


		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cf"></dhv:label></td>
			<td><input type="text" name="codFiscaleSoggetto"
				id="codFiscaleSoggetto"
				value="<%=Operatore.getRappLegale().getCodFiscale()%>" /> <font
				color="red">(*)</font> <%if(Operatore.isFlagFuoriRegione()){ %> <input
				type="checkbox" name="estero" id="estero"
				onclick="if(this.checked){document.getElementById('calcoloCF').style.visibility='hidden';} else {document.getElementById('calcoloCF').style.visibility='visible';}">Provenienza
			Estera <%} %> <input type="button" id="calcoloCF"
				value="Calcola Codice Fiscale" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascita,document.forms[0].dataNascita,'codFiscaleSoggetto')"></input></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.didentita"></dhv:label></td>
			<td><input class="todisable" type="text"
				name="documentoIdentita" id="documentoIdentita"
				value="<%=Operatore.getRappLegale().getDocumentoIdentita()%>" /></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.mail"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="email" name="email" value=""></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="telefono1" name="telefono1"
				value="<%=Operatore.getRappLegale().getTelefono1()%>"></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono2"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="telefono2" name="telefono2" value=""></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.fax"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="fax" name="fax" value=""></td>

		</tr>

	</table>
	<br>
	
	
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">Rappresentante Sede Operativa</th>
		</tr>
		<tr>
		<th>&nbsp;</th>
		<th>
			<input type = "text" name="cfSearchStab"  value = "Ricerca codice Fiscale" onclick="this.value=''" id = "cfSearchStab" style="background-color: blue;color: white;">
			<a href="#" onclick="ricercaSoggettoFisicoStab(document.getElementById('cfSearchStab').value)">
				<img src="images/filter.gif">
			</a>
			<a href= "#" onclick="document.getElementById('cfSearchStab').value=''">
				<img src="images/clear.gif">
			</a>
		</th>
	</tr>
		<tr>
			<td class="formLabel"><dhv:label
				name="opu.sede_legale.inregione"></dhv:label></td>
			<td><select class="todisable" name="inregioneRappOperativoStab"
				id="inregioneRappOperativoStab">
				<option value="si">SI</option>
				<option value="no">NO</option>
			</select></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.nome"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="nomeStab" name="nomeStab"
				value="<%=OrgDetails.getRappLegale().getNome() %>"><font
				color="red">*</font></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cognome"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="cognomeStab" name="cognomeStab"
				value="<%=OrgDetails.getRappLegale().getCognome() %>"><font
				color="red">*</font></td>
		</tr>


		<tr id="">
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.sesso"></dhv:label></td>
			<td><input class="todisable" type="radio" name="sessoStab"
				id="sesso1Stab" value="M" checked="checked">M 
				<input
				type="radio" name="sessoStab" id="sesso2Stab" value="F">F</td>
		</tr>



		<tr>
			<td nowrap class="formLabel"><dhv:label
				name="opu.soggetto_fisico.data_nascita"></dhv:label></td>
			<td><input class="todisable" readonly type="text"
				id="dataNascitaStab" name="dataNascitaStab" size="10"
				value="<%=toDateasString(OrgDetails.getRappLegale().getDataNascita()) %>" />
			<a href="#"
				onClick="cal19.select(document.forms[0].dataNascitaStab,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_nascita"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="comuneNascitaStab" name="comuneNascitaStab"
				value="<%=OrgDetails.getRappLegale().getComuneNascita() %>"></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_nascita"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="provinciaNascitaStab" name="provinciaNascitaStab"
				value="<%=OrgDetails.getRappLegale().getProvinciaNascita() %>"></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_residenza"></dhv:label></td>

			<td><select class="todisable" name="addressLegaleCountryStab"
				id="addressLegaleCountryStab">
				<option
					value="<%=OrgDetails.getRappLegale().getIndirizzo().getIdProvincia()%>"><%=(OrgDetails.getRappLegale().getIndirizzo().getIdProvincia()>0)?OrgDetails.getRappLegale().getIndirizzo().getDescrizione_provincia() : "Inserire le prime 4 lettere".trim()%></option>

			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato
			le prime 5 lettere apparirà in automatico la lista delle province che
			iniziano con le lettere digitate) <font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
			<input type="hidden" name="addressLegaleCountryTestoStab"
				id="addressLegaleCountryTestoStab" /> <!--      <input type="text" size="30" maxlength="50" id = "" name="addressLegaleCountry" value="">-->
			</td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_residenza"></dhv:label></td>
			<td><select class="todisable" name="addressLegaleCityStab"
				id="addressLegaleCityStab">
				<option
					value="<%=OrgDetails.getRappLegale().getIndirizzo().getComune()%>"><%=(OrgDetails.getRappLegale().getIndirizzo().getComune()>0)? OrgDetails.getRappLegale().getIndirizzo().getDescrizioneComune() : "Inserire le prime 4 lettere".trim()%></option>

			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato
			le prime 5 lettere apparirà in automatico la lista dei comuni che
			iniziano con le lettere digitate) <font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%>
			<input type="hidden" name="addressLegaleCityTestoStab"
				id="addressLegaleCityTestoStab" /></td>
		</tr>


		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.indirizzo"></dhv:label></td>
			<td><select class="todisable" name="addressLegaleLine1Stab"
				id="addressLegaleLine1Stab">

				<option
					value="<%=OrgDetails.getRappLegale().getIndirizzo().getIdIndirizzo()%>"
					selected="selected"><%=OrgDetails.getRappLegale().getIndirizzo().getVia()%></option>


			</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(dopo aver digitato
			le prime 5 lettere apparirà in automatico la lista degli indirizzi
			che iniziano con le lettere digitate) <font color="red">(*)</font> <input
				type="hidden" name="addressLegaleLine1TestoStab"
				id="addressLegaleLine1TestoStab" /> <!--      <input type="text" size="30" maxlength="50" id = "addressLegaleLine1" name="addressLegaleLine1" value="">-->
			</td>
		</tr>


		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cf"></dhv:label></td>
			<td><input type="text" name="codFiscaleSoggettoStab"
				id="codFiscaleSoggettoStab"
				value="<%=OrgDetails.getRappLegale().getCodFiscale()%>" /> <font
				color="red">(*)</font> <%if(OrgDetails.isFlagFuoriRegione()){ %> <input
				type="checkbox" name="estero" id="estero"
				onclick="if(this.checked){document.getElementById('calcoloCF').style.visibility='hidden';} else {document.getElementById('calcoloCF').style.visibility='visible';}">Provenienza
			Estera <%} %> <input type="button" id="calcoloCF"
				value="Calcola Codice Fiscale" onclick="javascript:CalcolaCF(document.forms[0].sessoStab,document.forms[0].nomeStab,document.forms[0].cognomeStab,document.forms[0].comuneNascitaStab,document.forms[0].dataNascitaStab,'codFiscaleSoggettoStab')"></input></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.didentita"></dhv:label></td>
			<td><input class="todisable" type="text"
				name="documentoIdentitaStab" id="documentoIdentitaStab"
				value="<%=OrgDetails.getRappLegale().getDocumentoIdentita()%>" /></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.mail"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="emailStab" name="emailStab" value=""></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="telefono1Stab" name="telefono1Stab"
				value="<%=OrgDetails.getRappLegale().getTelefono1()%>"></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono2"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="telefono2Stab" name="telefono2Stab" value=""></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.fax"></dhv:label></td>
			<td><input class="todisable" type="text" size="30"
				maxlength="50" id="faxStab" name="faxStab" value=""></td>

		</tr>

	</table>
	<br>

	<%
  boolean noneSelected = false;
%>

	<br />
	<input type="hidden" name="close" value="">
	<input type="hidden" name="refresh" value="-1">
	<input type="hidden" name="rgPrecedente"
		value="<%=  Operatore.getRagioneSociale() %>" />
	<input type="hidden" name="dPrecedente"
		value="<%=  Operatore.getRagioneSociale() %>" />

	<input type="hidden" name="cfPrecedente"
		value="<%=  OrgDetails.getRappLegale().getCodFiscale() %>" />
	<input type="hidden" name="nomePrecedente"
		value="<%=  OrgDetails.getRappLegale().getNome() %>" />
	<input type="hidden" name="cognomePrecedente"
		value="<%=  OrgDetails.getRappLegale().getCognome() %>" />
	<input type="hidden" name="lnPrecedente"
		value="<%=  OrgDetails.getRappLegale().getComuneNascita()%>" />
	<input type="hidden" name="dnPrecedente"
		value="<%=  OrgDetails.getRappLegale().getDataNascitaString()%>" />
	<input type="hidden" name="ePrecedente"
		value="<%=  OrgDetails.getRappLegale().getEmail()%>" />
	<input type="hidden" name="tPrecedente"
		value="<%=  OrgDetails.getRappLegale().getTelefono1()%>" />
	<input type="hidden" name="faxPrecedente"
		value="<%=  OrgDetails.getRappLegale().getFax()%>" />
	<input type="hidden" name="modified"
		value="<%=  TicketDetails.getModified() %>" />
	<input type="hidden" name="currentDate"
		value="<%=  request.getAttribute("currentDate") %>" />
	<input type="hidden" name="statusId"
		value="<%=  TicketDetails.getStatusId() %>" />
	<input type="hidden" name="trashedDate"
		value="<%=  TicketDetails.getTrashedDate() %>" />
	<input type="button"
		value="Inserisci"
		name="Save" onClick="verificaSoggetto(document.getElementById('codFiscaleSoggetto'))">/ 
	
	</dhv:container></form>








<script>


$(document).ready(function() {	

	
	
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	
	
});

</script>


<div id="boxes"><%-- IL CAMPO SRC è DA AGGIUSTARE --%>
<div id="dialog4" class="window"><a href="#" class="close" /><b>Chiudi</b></a>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">

	<tr>
		<th colspan="2"><strong>
		<div id="intestazione"></div>
		</strong></th>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Nome</td>
		<td>
		<div id="nomeSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Cognome</td>
		<td>
		<div id="cognomeSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Sesso</td>
		<td>
		<div id="sessoSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Data Nascita</td>
		<td>
		<div id="dataNascitaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Comune Nascita</td>
		<td>
		<div id="comuneNascitaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia Nascita</td>
		<td>
		<div id="provinciaNascitaSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Comune Residenza</td>
		<td>
		<div id="comuneResidenzaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia Residenza</td>
		<td>
		<div id="provinciaResidenzaSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Indirizzo Residenza</td>
		<td>
		<div id="indirizzoResidenzaSoggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Documento</td>
		<td>
		<div id="documentoSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>e-mail</td>
		<td>
		<div id="mailSoggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>telefono principale</td>
		<td>
		<div id="telefono1Soggetto"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>cellulare/tel. secondario</td>
		<td>
		<div id="telefono2Soggetto"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Fax</td>
		<td>
		<div id="faxSoggetto"></div>
		</td>
	</tr>
	<tr id="azione">
		<td><input type="button" value="Sovrascrivi"
			onclick="document.getElementById('sovrascrivi').value='si';$('#mask').hide();$('#dialog4').hide();verificaSoggetto(document.getElementById('codFiscaleSoggettoStab'))" /></td>
		<td><input type="button" value="Non Sovrascrivere"
			onclick="document.getElementById('sovrascrivi').value='no';$('#mask').hide();$('#dialog4').hide();verificaSoggetto(document.getElementById('codFiscaleSoggettoStab'))" /></td>
	</tr>

</table>


</div>


<div id="dialog5" class="window"><a href="#" class="close" /><b>Chiudi</b></a>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">

	<tr>
		<th colspan="2"><strong>
		<div id="intestazioneStab"></div>
		</strong></th>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Nome</td>
		<td>
		<div id="nomeSoggettoStab"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Cognome</td>
		<td>
		<div id="cognomeSoggettoStab"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Sesso</td>
		<td>
		<div id="sessoSoggettoStab"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Data Nascita</td>
		<td>
		<div id="dataNascitaSoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Comune Nascita</td>
		<td>
		<div id="comuneNascitaSoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia Nascita</td>
		<td>
		<div id="provinciaNascitaSoggettoStab"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Comune Residenza</td>
		<td>
		<div id="comuneResidenzaSoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia Residenza</td>
		<td>
		<div id="provinciaResidenzaSoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Indirizzo Residenza</td>
		<td>
		<div id="indirizzoResidenzaSoggettoStab"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Documento</td>
		<td>
		<div id="documentoSoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>e-mail</td>
		<td>
		<div id="mailSoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>telefono principale</td>
		<td>
		<div id="telefono1SoggettoStab"></div>
		</td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>cellulare/tel. secondario</td>
		<td>
		<div id="telefono2SoggettoStab"></div>
		</td>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Fax</td>
		<td>
		<div id="faxSoggettoStab"></div>
		</td>
	</tr>
	<tr id="azioneStab">
		<td><input type="button" value="Sovrascrivi"
			onclick="document.getElementById('sovrascriviStab').value='si';checkForm(document.addticket);" /></td>
		<td><input type="button" value="Non Sovrascrivere"
			onclick="document.getElementById('sovrascriviStab').value='no';checkForm(document.addticket);" /></td>
	</tr>

</table>


</div>



<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>
