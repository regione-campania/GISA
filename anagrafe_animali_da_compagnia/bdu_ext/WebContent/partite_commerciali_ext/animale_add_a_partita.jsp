<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*"%>


<%@page import="org.aspcfs.modules.anagrafe_animali_ext.base.Animale"%>

<%@page import="org.aspcfs.modules.system.base.SiteList"%>
<%@page import="org.aspcfs.modules.opu_ext.base.StabilimentoList"%>
<%@page import="org.aspcfs.modules.opu_ext.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu_ext.base.LineaProduttiva"%>

<%@page import="org.aspcfs.modules.system.base.SiteList"%>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<jsp:useBean id="Cane"
	class="org.aspcfs.modules.anagrafe_animali_ext.base.Cane" scope="request" />
<jsp:useBean id="Gatto"
	class="org.aspcfs.modules.anagrafe_animali_ext.base.Gatto" scope="request" />


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="esitoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="esitoControlloLaboratorioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="veterinariPrivatiList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="partita"
	class="org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale"
	scope="request" />
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/PraticaList.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/LineaProduttiva.js"> </script>

<script>
var campo ;
function verificaInserimentoAnimale (campoIn)
{
	campo = campoIn ;
	if(campo.value.length==15)
		DwrUtil.verificaInserimentoAnimaleByMC(campo.value, verificaInserimentoAnimaleCallBack);
}

function verificaInserimentoAnimaleCallBack(value)
{
	
	if (value.idEsito=='2' || value.idEsito=='4')
	{
		
		alert(value.descrizione);
		campo.value="";
	}
	if (value.idEsito=='3' )
	{
		if ( document.forms[0].ruolo.value =='24')
		{
			
			alert( value.descrizione);
			campo.value="";
		}
		
	}
	
}
function checkForm(form) {
	 
	//verificaContributo();
	
  	//checkMorsicatore();
   

formTest = true;
message = "";

  

    lanciaControlloDate();

	//controllo sulla taglia
	 if (form.idSpecie.value == '1' &&  form.idTaglia.value == '-1') { 
	     message += label("taglia.required", "- La taglia è richiesta\r\n");
	     formTest = false;
	  }
    if (form.idAslRiferimento.value == '-1' && form.flagSindacoFuoriRegione.checked==false) { 
	     message += label("taglia.required", "- Asl è richiesta\r\n");
	     formTest = false;
	  }
    
	 //controllo sulla razza
	if (form.idRazza.value == '-1') { 
	     message += label("razza.required", "- Razza è richiesta\r\n");
	     formTest = false;
	  }

	  //controllo sul mantello
	  if (form.idTipoMantello.value == '-1') { 
	     message += label("mantello.required", "- Mantello è richiesta\r\n");
	     formTest = false;
	  }

	    //controllo sul mc
	 if (form.microchip.value == "") {
	      message += label("serial.number.required", "- Il microchip è richiesto\r\n");
	      formTest = false;
	  }
	  else{
		    //controllo sulla lunghezza del microchip    
	        if( !( (form.microchip.value.length == 15) && ( /^([0-9]+)$/.test( form.microchip.value )) ) )
	        {
	       	  message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
	          formTest = false;
	        }
		
	  }



	 if (form.numeroPassaporto.value == '' || ( form.numeroPassaporto.value!="" && form.numeroPassaporto.value.length<13)){

		   	message += label("", "- Sono richieste 13 cifre o caratteri per il passaporto.\r\n");
  			formTest = false;
 	 }
 	 
	 if (form.numeroPassaporto && form.numeroPassaporto.value!=""){
		 	if (form.dataRilascioPassaporto.value==""){
	 	    	message += label("", "- Data Rilascio passaporto richiesta.\r\n");
	     		formTest = false;
	    	 }	
	}

	 if (form.dataRilascioPassaporto && form.dataRilascioPassaporto.value!=""){
		 	if (form.numeroPassaporto.value==""){
	 	    	message += label("", "- Campo passaporto richiesto.\r\n");
	     		formTest = false;
	    	 }	
		}

	 if (form.dataVaccino.value == ""){
			message += label("", "- La data di vaccinazione antirabbica è obbligatoria.\r\n");
	     		formTest = false;
		}
	 
	 if (form.numeroLottoVaccino.value == ""){
			message += label("", "- Il lotto vaccinazione antirabbica è obbligatorio.\r\n");
	     		formTest = false;
		}
	 
	 
	 if (form.nomeVaccino.value == ""){
			message += label("", "- Il nome della vaccinazione antirabbica è obbligatorio.\r\n");
	     		formTest = false;
		}
	 
	 if (form.dataScadenzaVaccino.value == ""){
			message += label("", "- La data scadenza della vaccinazione antirabbica è obbligatorio.\r\n");
	     		formTest = false;
		}
	 
	 if (form.produttoreVaccino.value == ""){
		 message += label("", "- Il produttore della vaccinazione antirabbica è obbligatorio.\r\n");
  		formTest = false;
	 }

	 
	 
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    else
    {
    	
    	
    	return true;
    }
	
  }


</script>

<%@ include file="../initPage.jsp"%>

<%
	Animale animale = Cane;
	if (Gatto.getIdSpecie() == 1) {
		animale = (Animale) Cane;
	} else {
		animale = (Animale) Gatto;
	}
%>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<script language="javascript">


</script>

<script language="JavaScript">

function doCheck(form) {
    if (form.dosubmit.value == "false") {     
      return true;
    } else {
      return(checkForm(form));
    }
   }






	function checkSpecieAnimale(obj){
		document.forms[0].doContinue.value = 'false';
		document.forms[0].submit();

		
	}

		  

</script>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="PartiteCommerciali.do?command=DettagliPartita&amp;idPartita=<%=partita.getIdPartitaCommerciale() %>"><dhv:label
				name="anagrafica.animale">Dettagli prenotifica</dhv:label></a> > <dhv:label
				name="anagrafica.animale.aggiungi">Aggiungi Animale a Prenotifica</dhv:label></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body onLoad="">
<form name="addAnimale"
	action="PartiteCommerciali.do?command=InsertAnimale&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post"><input type="hidden" name="ruolo"
	value="<%=User.getRoleId()%>">


<table class="">
	<input type="button"
		value="<dhv:label name="global.button.save">Save</dhv:label>"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />
		
		<input type="button"
		value="Salva e Clona"
		onClick="this.form.action='PartiteCommerciali.do?command=InserteClonaAnimale&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>' ; this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />
		
		
	<input type="button"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="window.location.href='PartiteCommerciali.do?command=DettagliPartita&amp;idPartita=<%=partita.getIdPartitaCommerciale() %>';this.form.dosubmit.value='false';" />


	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="accounts.accountasset_include.SpecificInformation">Specific Information</dhv:label></strong>
			</th>
		</tr>

		<tr id="aslRif">
			<td class="formLabel" nowrap><dhv:label name="">Asl di Riferimento</dhv:label>
			</td>
			<td>
			<%
				if (User.getSiteId() == -1) {
			%> <%
 	// AslList.setJsEvent("onChange=\"javascript:checkSpecieAnimale();\"");
 %>
			<%=AslList.getHtmlSelect("idAslRiferimento", animale
								.getIdAslRiferimento())%>

			<%
				} else {
			%> <%=AslList.getSelectedValue(User.getSiteId())%> <input
				type="hidden" size="30" id="idAslRiferimento"
				name="idAslRiferimento" value="<%=User.getSiteId()%>"> <%
 	}
 %>


			</td>
		</tr>
		<input type="hidden" name="flagCircuitoCommerciale"
			id="flagCircuitoCommerciale" value="true" />

		<tr id="idSpecie">
			<td class="formLabel" nowrap><dhv:label name="">Nome partita</dhv:label>
			</td>
			<td><%=partita.getNrCertificato()%> <input type="hidden"
				value="<%=partita.getIdPartitaCommerciale()%>"
				name="idPartitaCircuitoCommerciale"
				id="idPartitaCircuitoCommerciale" /></td>
		</tr>


		<input type="hidden" name="idProprietario" id="idProprietario" value="<%=User.getUserRecord().getIdImportatore()%>"/>
		<input type="hidden" name="idDetentore" id="idDetentore" value="<%=User.getUserRecord().getIdImportatore()%>"/>


		<tr id="idSpecie">
			<td class="formLabel" nowrap><dhv:label name="">Specie animale</dhv:label>
			</td>
			<td><dhv:evaluate if="<%=(partita.getIdTipoPartita() != 3)%>">
				<!-- Diverso da partita mista -->
				<%
					specieList
								.setJsEvent("onChange=\"javascript:checkSpecieAnimale(this);\"");
				%>
				<%=specieList.getSelectedValue(partita
								.getIdTipoPartita())%>
				<input type="hidden" value="<%=partita.getIdTipoPartita()%>"
					name="idSpecie" id="idSpecie">
			</dhv:evaluate> <dhv:evaluate if="<%=(partita.getIdTipoPartita() == 3)%>">
				<!-- Uguale a partita mista -->
				<%
					specieList
								.setJsEvent("onChange=\"javascript:checkSpecieAnimale(this);\"");
				%>
				<%=specieList.getHtmlSelect("idSpecie", animale.getIdSpecie())%>
	
			</dhv:evaluate></td>
		</tr>


		



		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Razza</dhv:label></td>
			<td><%=razzaList.getHtmlSelect("idRazza", animale.getIdRazza())%>

			<%=showAttribute(request, "idRazza")%> <font color="red">*</font></td>
		</tr>
		<tr>
			<td class="formLabel"><dhv:label name="">Sesso</dhv:label></td>
			<td><input type="radio" name="sesso" value="M" id="sesso"
				<%=(!"F".equals(animale.getSesso())) ? " checked" : ""%>>
			<dhv:label name="cani.sesso.maschio">M</dhv:label> <input
				type="radio" name="sesso" value="F" id="sesso"
				<%=("F".equals(animale.getSesso())) ? " checked" : ""%>>
			<dhv:label name="cani.sesso.femmina">F</dhv:label></td>
		</tr>

		<dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>'>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
				<td><%=tagliaList.getHtmlSelect("idTaglia", Cane
								.getIdTaglia())%>

				<%=showAttribute(request, "idTaglia")%> <font color="red">*</font>
				</td>
			</tr>

		</dhv:evaluate>

		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Nato il</dhv:label></td>


			<td><input readonly type="text" name="dataNascita" size="10"
				value="<%=toDateasString(animale.getDataNascita())%>"
				nomecampo="nascita" tipocontrollo="T2" labelcampo="Data Nascita" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">*</font> <dhv:label
				name="">Data nascita presunta</dhv:label> <input type="checkbox"
				name="flagDataNascitaPresunta"
				<%=animale.isFlagDataNascitaPresunta() ? "DEFAULT CHECKED"
							: ""%> />


			</td>
		</tr>
		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Mantello</dhv:label></td>
			<td><%=mantelloList.getHtmlSelect("idTipoMantello", animale
							.getIdTipoMantello())%>

			<%=showAttribute(request, "idTipoMantello")%> <font color="red">*</font>
			</td>
		</tr>


		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Nome</dhv:label></td>
			<td><input type="text" size="30" id="nome" name="nome"
				maxlength="40" value="<%=toHtmlValue(animale.getNome())%>">
			<%=showAttribute(request, "nome")%></td>
		</tr>

		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
			<td><input type="text" size="16" id="microchip" name="microchip"
				maxlength="15" value=""
				onchange="verificaInserimentoAnimale(document.forms[0].microchip)">
			<font color="red">*</font> <%=showError(request, "ErroreMicrochip")%>&nbsp;&nbsp;

			</td>
		</tr>

		<!--  dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>' -->
		<tr class="containerBody" style="">
			<td class="formLabel"><dhv:label name="">Data chippatura</dhv:label>
			</td>
			<td><input readonly type="text" name="dataMicrochip" size="10"
				value="<%=toDateasString(Cane.getDataMicrochip())%>"
				nomecampo="chippatura" tipocontrollo="T2,T17"
				labelcampo="Data Chippatura" /> &nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataMicrochip,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <font color=red>&nbsp;*</font></td>
		</tr>


		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Passaporto</dhv:label>
			</td>
			<td><input type="text" name="numeroPassaporto" size="15"
				maxlength="13"
				value="<%=toHtmlValue(animale.getNumeroPassaporto())%>"><%=showAttribute(request, "numeroPassaporto")%>
				<font color=red>&nbsp;*</font>
			</td>
		</tr>
		<%-- <tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Data rilascio passaporto</dhv:label>
			</td>
			<td>if (update==false){ <input readonly type="text"
				name="dataRilascioPassaporto" size="10"
				value="<%=toDateasString(animale.getDataRilascioPassaporto())%>"
				nomecampo="dataRilascioPassaporto" tipocontrollo="T10,T9"
				labelcampo="Data rilascio passaporto" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataRilascioPassaporto,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color=red>&nbsp;*</font></td>
		</tr> --%>
		
		
		<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data rilascio passaporto - Data scadenza passaporto</dhv:label>
				</td>
				<td> <input readonly type="text"
					name="dataRilascioPassaporto" id="dataRilascioPassaporto" size="10"
					value="<%=toDateasString(animale.getDataRilascioPassaporto())%>"
					nomecampo="dataRilascioPassaporto" tipocontrollo=""
					labelcampo="Data rilascio passaporto" />&nbsp; <a href="#" 
					onClick="cal19.select2(document.forms[0].dataRilascioPassaporto, document.forms[0].dataScadenzaPassaporto, 'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a>&nbsp;&nbsp;
					--
					<input type="text" readonly
					name="dataScadenzaPassaporto" id="dataScadenzaPassaporto" size="10"
					value=""
					nomecampo="dataScadenzaPassaporto" tipocontrollo=""
					labelcampo="Data scadenza passaporto" />&nbsp; <a href="#" 
					onClick="cal19.select(document.forms[0].dataScadenzaPassaporto, 'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmidle"></a>&nbsp;&nbsp;</td>
			</tr>
		
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Vaccino antirabbico</dhv:label>
				</td>
				<td><input readonly type="text" name="dataVaccino" size="10"
					value="<%=toDateasString(animale.getDataVaccino())%>"
					nomecampo="dataVaccino" tipocontrollo="T10,T9"
					labelcampo="Data Vaccino antirabico" /> &nbsp; <a href="#"
					onClick="cal19.select(document.forms[0].dataVaccino,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a><font color=red>&nbsp;*</font></td>
			</tr>
	
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero Lotto Vaccino antirabbico</dhv:label>
				</td>
				<td> <input type="text" size="10"
					name="numeroLottoVaccino" maxlength="15"
					value="<%=toHtmlValue(animale.getNumeroLottoVaccino())%>"><font color=red>&nbsp;*</font></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Nome Vaccino antirabbico</dhv:label>
				</td>
				<td><input type="text" size="10"
					name="nomeVaccino" maxlength=""
					value="<%=toHtmlValue(animale.getNomeVaccino())%>"><font color=red>&nbsp;*</font></td>
			</tr>


	
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Produttore Vaccino antirabbico</dhv:label>
				</td>
				<td><input type="text" size="10"
					name="produttoreVaccino" maxlength=""
					value="<%=toHtmlValue(animale.getProduttoreVaccino())%>"><font color=red>&nbsp;*</font></td>
			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Scadenza Vaccino antirabbico</dhv:label>
				</td>
				<td><input readonly type="text" name="dataScadenzaVaccino" size="10"
					value="<%=toDateasString(animale.getDataScadenzaVaccino())%>"
					nomecampo="dataScadenzaVaccino" tipocontrollo="T10,T9"
					labelcampo="Data Scadenza Vaccino antirabico" /> &nbsp; <a href="#"
					onClick="cal19.select(document.forms[0].dataScadenzaVaccino,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a><font color=red>&nbsp;*</font></td>
			</tr>

		
	</table>



	


	<br>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Dati Aggiuntivi</strong></th>
		</tr>

		<tr class="containerBody">
			<td valign="top" class="formLabel"><dhv:label name="">Segni particolari</dhv:label>
			</td>
			<td><textarea name="segniParticolari" rows="3" cols="50"><%=toString(animale.getSegniParticolari())%></textarea>
			<%=showAttribute(request, "descriptionError")%></td>
		</tr>
		<tr class="containerBody">
			<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
			</td>
			<td><textarea name="nota2" rows="3" cols="50"><%=toString(animale.getNota2())%></textarea>
			</td>
		</tr>
	</table>
	<br>




	<input type="hidden" name="dosubmit" value="true" />
	<input type="hidden" name="saveandclone" value="" />

	<br />
	<input type="button"
		value="<dhv:label name="global.button.save">Save</dhv:label>"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />
		<input type="button"
		value="Salva e Clona"
		onClick="this.form.action='PartiteCommerciali.do?command=InserteClonaAnimale&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>' ; this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />
		


	<input type="hidden" name="doContinue" id="doContinue" value="">

	</form>