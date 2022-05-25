<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*"%>


<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>

<%@page import="org.aspcfs.modules.system.base.SiteList"%>
<%@page import="org.aspcfs.modules.opu.base.StabilimentoList"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>

<%@page import="org.aspcfs.modules.system.base.SiteList"%>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<jsp:useBean id="Cane"
	class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto"
	class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />


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
<jsp:useBean id="partita"
	class="org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale"
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

function compareDates(date1,dateformat1,date2,dateformat2){var d1=getDateFromFormat(date1,dateformat1);var d2=getDateFromFormat(date2,dateformat2);if(d1==0 || d2==0){return -1;}else if(d1 > d2){return 1;}return 0;}

function checkPostData(data,dataReg){
  	if (compareDates( data.value,"d/MM/y",dataReg.value,"d/MM/y")==1) { 
     	return false;
     }else{
     	return true;
     }
  };

  
function verificaInserimentoAnimale (campoIn)
{
	campo = campoIn ;
	if(campo.value.length==15)
		DwrUtil.verificaInserimentoAnimale(campo.value,<%=User.getUserId()%>,verificaInserimentoAnimaleCallBack);
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
	     message += label("taglia.required", "- Taglia is required\r\n");
	     formTest = false;
	  }
    if (form.idAslRiferimento.value == '-1' && form.flagSindacoFuoriRegione.checked==false) { 
	     message += label("taglia.required", "- Asl is required\r\n");
	     formTest = false;
	  }
    
	 //controllo sulla razza
	if (form.idRazza.value == '-1') { 
	     message += label("razza.required", "- Razza is required\r\n");
	     formTest = false;
	  }

	  //controllo sul mantello
	  if (form.idTipoMantello.value == '-1') { 
	     message += label("mantello.required", "- Mantello is required\r\n");
	     formTest = false;
	  }

	    //controllo sul mc
	 if (form.microchip.value == "") {
	      message += label("serial.number.required", "- Microchip is required\r\n");
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


	 if (form.numeroPassaporto.value == "") {
	      message += label("", "- Nr passaporto richiesto\r\n");
	      formTest = false;
	  }


	 if (form.dataRilascioPassaporto.value == "") {
	      message += label("", "- Data di rilascio passaporto richiesta\r\n");
	      formTest = false;
	  }

	    if (form.dataVaccino.value == "") {
	        message += label("", "- Il campo Data Vaccino è richiesto\r\n");
	        formTest = false;
	      }

	     /*if (form.dataMicrocip.value == "") {
	          message += label("", "- Il campo Data Microchip è richiesto\r\n");
	          formTest = false;
	        }*/
	      if ( checkPostData( form.dataRilascioPassaporto, form.dataVaccino ) ) {
	        message += label("", "- La Data Vaccino deve essere antecedente alla Data Passaporto\r\n");
	        formTest = false;
	      }


	 

	 //controllo sul mc
    if (form.tatuaggio.value != "") {
		 if (form.idSpecie.value=='1' && form.dataTatuaggio.value == "") {
	      message += label("", "- Data Inserimento secondo mc richiesta\r\n");
	      formTest = false;
		 }  
	}
	if (form.idSpecie.value == '1' &&  form.dataTatuaggio.value != "") {
	 if (form.tatuaggio.value == "") {
		      message += label("", "- Tatuaggio Richiesto\r\n");
		      formTest = false;
		  }
	}
			 

//	 if (form.numeroPassaporto.value!="" && form.numeroPassaporto.value.length<13){
//
		 //  	message += label("", "- Sono richieste 13 cifre o caratteri per il passaporto.\r\n");
  //			formTest = false;
 	// }
 	 
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

	/*if (document.addAsset.contrIdentita){
		if (document.addAsset.contrIdentita.checked) {
			if (form.dataId.value==""){
					message += label("", "- Data Ehrlichiosi richiesta.\r\n");
		    		formTest = false;
		    
			}
			
			if (form.esito2.value=="-1"){
					message += label("", "- Esito Ehrlichiosi richiesto.\r\n");
		    		formTest = false;
		    
			}
		}
	}	

	if (document.addAsset.contrRik){
		if (document.addAsset.contrRik.checked) {
			if (form.dataRik.value==""){
					message += label("", "- Data Rickettiosi richiesta.\r\n");
		    		formTest = false;
		    
			}
			
			
			if (form.esitoRik.value=="-1"){
					message += label("", "- Esito Rickettiosi richiesto.\r\n");
		    		formTest = false;
		    
			}
		}
	}	

	if (document.addAsset.contrDoc){
		if (document.addAsset.contrDoc.checked) {
			if (form.dataDoc.value==""){
					message += label("", "- Data Leishmaniosi richiesta.\r\n");
		    		formTest = false;
		    
			}
			
		}
	}	*/

//inserire controlli ordinanze
	
	/*if ((form.ordSind.value!='') && (form.ordSind.value!= null)){
		  if ((form.dataOrdSind.value=='') || (form.dataOrdSind.value== null)){
			     message += label("", "- Selezionare una data di ordinanza sindacale\r\n");
			     formTest = false;
		  }

	   }

	  if ((form.dataOrdSind.value!='') && (form.dataOrdSind.value!= null)){
		  if ((form.ordSind.value=='') || (form.ordSind.value== null)){
			     message += label("", "- Ordinanza Sindacale obbligatoria\r\n");
			     formTest = false;
		  }

	   }*/
	//--------FINE CAMPI OBBLIGATORI
	 
	 
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
		<tr> <a href="PartiteCommerciali.do"><dhv:label name="circuito.commerciale">Circuito Commerciale</dhv:label></a> >
  <a href="PartiteCommerciali.do?command=DettagliPartita&idPartita=<%=partita.getIdPartitaCommerciale()%>"><dhv:label
						name="">Dettagli partita</dhv:label></a> >	
	<dhv:label name="anagrafica.animale.aggiungi">Aggiungi animale</dhv:label></td>
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
		value="<dhv:label name="global.button.save">SaveAAA</dhv:label>"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />
		
		<input type="button"
		value="Salva e Clona"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='1';if(doCheck(this.form)){this.form.submit()};" />
		
	<input type="button"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="window.location.href='AnimaleAction.do';this.form.dosubmit.value='false';" />


	<%if (request.getAttribute("SalvaeClona")!=null){
			%>
			<font color = "green"><strong> Animale con microchip <%=animale.getMicrochip()%> salvato correttamente.</strong> </font>
			<%
			
		} %>

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


		<%
			int idDetentore = -1;
			String nomeDetentore = "";
			int idProprietario = -1;
			String nomeProprietario = "";
			if (animale.getIdSpecie() == Cane.idSpecie) {
				if (Cane != null && Cane.getDetentore() != null) {
					nomeDetentore = toHtml(Cane.getDetentore()
							.getRagioneSociale());
					idDetentore = ((Cane.getDetentore() != null && Cane
							.getDetentore().getListaStabilimenti().size() > 0)) ? ((LineaProduttiva) (((Stabilimento) Cane
							.getDetentore().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId()
							: -1;

					nomeProprietario = toHtml(Cane.getProprietario()
							.getRagioneSociale());
					idProprietario = ((Cane.getProprietario() != null && Cane
							.getProprietario().getListaStabilimenti().size() > 0)) ? ((LineaProduttiva) (((Stabilimento) Cane
							.getProprietario().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId()
							: -1;
				}
			} else if (animale.getIdSpecie() == Gatto.idSpecie) {

				if (Gatto != null && Gatto.getDetentore() != null) {
					nomeDetentore = toHtml(Gatto.getDetentore()
							.getRagioneSociale());
					idDetentore = ((Gatto.getDetentore() != null && Gatto
							.getDetentore().getListaStabilimenti().size() > 0)) ? ((LineaProduttiva) (((Stabilimento) Gatto
							.getDetentore().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId()
							: -1;

					nomeProprietario = toHtml(Gatto.getProprietario()
							.getRagioneSociale());
					idProprietario = ((Gatto.getProprietario() != null && Gatto
							.getProprietario().getListaStabilimenti().size() > 0)) ? ((LineaProduttiva) (((Stabilimento) Gatto
							.getProprietario().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId()
							: -1;
				}
			}
		%>




		<tr class="containerBody" id="proprietario">
			<td class="formLabel" nowrap><dhv:label name="">Seleziona Proprietario</dhv:label>
			</td>
			<td>
			<table cellspacing="0" border="0" width="100%" class="details">


				<tr>

					<td><%=nomeProprietario%></td>
				</tr>
				<input type="hidden" name="idProprietario" id="idProprietario"
					value="<%=idProprietario%>">


			
			</table>

			<dhv:evaluate if="<%=(idProprietario == -1)%>">
				<input type="hidden" name="idProprietario" id="idProprietario"
					value="-1">
			</dhv:evaluate></td>

		</tr>




		<!-- dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>' -->
		<tr class="containerBody" id="detentore">
			<td class="formLabel" nowrap><dhv:label name="">Selezione Detentore</dhv:label>
			</td>
			<td>

			<table cellspacing="0" border="0" width="100%" class="details">
				<!-- dhv:evaluate if="<%=(Cane != null && Cane.getDetentore() != null)%>" -->

				<tr>

					<td><%=nomeDetentore%></td>
				</tr>

				<input type="hidden" name="idDetentore" id="idDetentore"
					value="<%=idDetentore%>">


				<!-- /dhv:evaluate -->
				
			</table>




			</td>

		</tr>


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
			<td class="formLabel"><dhv:label name="">Data Registrazione</dhv:label>
			</td>


			<td><input readonly type="text" name="dataRegistrazione"
				size="10" value="<%=toDateString(animale.getDataRegistrazione())%>"
				nomecampo="registrazione" tipocontrollo="T2"
				labelcampo="Data Registrazione" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataRegistrazione,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">* Data riportata
			dal documento.</font></td>
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
				value="<%=toDateString(animale.getDataNascita())%>"
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

		<tr class="containerBody" style="">
			<td class="formLabel"><dhv:label name="">Data chippatura</dhv:label>
			</td>
			<td><input readonly type="text" name="dataMicrochip" size="10"
				value="<%=toDateasString(animale.getDataMicrochip())%>"
				nomecampo="chippatura" tipocontrollo="T2,T17"
				labelcampo="Data Chippatura" /> &nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataMicrochip,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <font color=red>&nbsp;*</font></td>
		</tr>



		<tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="anagrafe_animali_tatuaggio">Tatuaggio/2°Microchip</dhv:label>
			</td>
			<td><input type="text" size="20" name="tatuaggio" maxlength="15"
				value="<%=toHtmlValue(animale.getTatuaggio())%>"
				onchange="verificaInserimentoAnimale(document.forms[0].tatuaggio)">
			<%=showError(request, "ErroreTatuaggio")%>&nbsp;&nbsp;</td>

		</tr>
	
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data rilascio secondo Microchip</dhv:label>
				</td>
				<td><input readonly type="text" name="dataTatuaggio"
					size="10"
					value="<%=toDateasString(animale.getDataTatuaggio())%>"
					nomecampo="dataTatuaggio" tipocontrollo="T10,T11,T9"
					labelcampo="Data Secondo MC" />&nbsp; <a href="#"
					onClick="cal19.select(document.forms[0].dataTatuaggio,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"> </a></td>

			</tr>
	
		<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Passaporto</dhv:label>
				</td>
				<td><input type="text" name="numeroPassaporto" size="15"
					maxlength="13"
					value="<%=toHtmlValue(animale.getNumeroPassaporto())%>"><font color=red>&nbsp;*</font></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data rilascio passaporto - Data scadenza passaporto</dhv:label>
				</td>
				<td><%-- if (update==false){ --%> <input readonly type="text"
					name="dataRilascioPassaporto" id="dataRilascioPassaporto" size="10"
					value="<%=toDateasString(animale.getDataRilascioPassaporto())%>"
					nomecampo="dataRilascioPassaporto" tipocontrollo="T10,T9"
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
					align="absmiddle"></a>&nbsp;&nbsp;<font color=red>&nbsp;*</font></td>
			</tr>
		
		<dhv:evaluate if='<%=(animale.getIdSpecie() == Cane.idSpecie)%>'>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Vaccino antirabbico</dhv:label>
				</td>
				<td><input readonly type="text" name="dataVaccino" size="10"
					value="<%=toDateasString(Cane.getDataVaccino())%>"
					nomecampo="dataVaccino" tipocontrollo=""
					labelcampo="Data Vaccino antirabico" /> &nbsp; <a href="#"
					onClick="cal19.select(document.forms[0].dataVaccino,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"><img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a><font color=red>&nbsp;*</font></td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if='<%=(animale.getIdSpecie() == Cane.idSpecie)%>'>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero Lotto Vaccino antirabbico</dhv:label>
				</td>
				<td><%--if (animale.getNumeroLottoVaccino() != null && animale.getNumeroLottoVaccino() != "") { %>
      <input type="text" size="10" name="numeroLottoVaccino" maxlength="15" value="<%= toHtmlValue(animale.getNumeroLottoVaccino()) %>">
      <%}else{ --%> <input type="text" size="10"
					name="numeroLottoVaccino" maxlength="15"
					value="<%=toHtmlValue(Cane.getNumeroLottoVaccino())%>"><font color=red>&nbsp;*</font></td>
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

		</dhv:evaluate>
		
				<dhv:evaluate if='<%=(animale.getIdSpecie() == Gatto.idSpecie)%>'>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Vaccino antirabbico</dhv:label>
				</td>
				<td><input readonly type="text" name="dataVaccino" size="10"
					value="<%=toDateasString(Gatto.getDataVaccino())%>"
					nomecampo="dataVaccino" tipocontrollo=""
					labelcampo="Data Vaccino antirabico" /> &nbsp; <a href="#"
					onClick="cal19.select(document.forms[0].dataVaccino,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"><img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a><font color=red>&nbsp;*</font></td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if='<%=(animale.getIdSpecie() == Gatto.idSpecie)%>'>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Numero Lotto Vaccino antirabbico</dhv:label>
				</td>
				<td><%--if (animale.getNumeroLottoVaccino() != null && animale.getNumeroLottoVaccino() != "") { %>
      <input type="text" size="10" name="numeroLottoVaccino" maxlength="15" value="<%= toHtmlValue(animale.getNumeroLottoVaccino()) %>">
      <%}else{ --%> <input type="text" size="10"
					name="numeroLottoVaccino" maxlength="15"
					value="<%=toHtmlValue(Gatto.getNumeroLottoVaccino())%>"><font color=red>&nbsp;*</font></td>
			</tr>

		</dhv:evaluate>
	</table>



	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="4"><strong><dhv:label name="">Controlli Richiesti</dhv:label></strong>
			</th>
		</tr>


		<tr class="containerBody">
			<td width="25%" valign="top" class="formLabel2"><dhv:label
				name="">Controllo documentale</dhv:label></td>

			<td valign="top" class="formLabel2"><input type="checkbox"
				name="flagPresenzaEsitoControlloDocumentale"
					<% if (animale.isFlagPresenzaEsitoControlloDocumentale()){%> checked="checked"<%} %>
				></td>

			<td><input  type="text" readonly="readonly" 
				id="dataEsitoControlloDocumentale"
				name="dataEsitoControlloDocumentale" size="10" value=""
				nomecampo="dataEsitoControlloDocumentale"
				labelcampo="Data esito controllo documentale" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataEsitoControlloDocumentale,'anchor19','dd/MM/yyyy'); return false;"
				value ="<%=toDateasString(animale.getDataEsitoControlloDocumentale()) %>"
				NAME="anchor19" ID="anchor19"/> 
				<img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></td>

			<td><%=esitoList
							.getHtmlSelect("idEsitoControlloDocumentale", animale.getIdEsitoControlloDocumentale())%>
			</td>
		</tr>

		<tr class="containerBody">
			<td width="25%" valign="top" class="formLabel2"><dhv:label
				name="">Controllo d'identità</dhv:label></td>

			<td valign="top" class="formLabel2"><input type="checkbox"
				name="flagPresenzaEsitoControlloIdentita" <% if (animale.isFlagPresenzaEsitoControlloIdentita()){%> checked="checked"<%} %> ></td>

			<td><input readonly type="text" id="dataEsitoControlloIdentita" value = "<%=toDateasString(animale.getDataEsitoControlloIdentita()) %>"
				name="dataEsitoControlloIdentita" size="10" value=""
				nomecampo="dataEsitoControlloIdentita"
				labelcampo="Data esito controllo identità" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataEsitoControlloIdentita,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></td>

			<td><%=esitoList.getHtmlSelect("idEsitoControlloIdentita", animale.getIdEsitoControlloIdentita())%>
			</td>
		</tr>

		<tr class="containerBody">
			<td width="25%" valign="top" class="formLabel2"><dhv:label
				name="">Controllo fisico</dhv:label></td>

			<td valign="top" class="formLabel2"><input type="checkbox"
				name="flagPresenzaEsitoControlloFisico" <% if (animale.isFlagPresenzaEsitoControlloFisico()){%> checked="checked"<%} %> ></td>

			<td><input readonly type="text" id="dataEsitoControlloFisico" value = "<%=toDateasString(animale.getDataEsitoControlloFisico()) %>"
				name="dataEsitoControlloFisico" size="10" value=""
				nomecampo="dataEsitoControlloFisico"
				labelcampo="Data esito controllo fisico" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataEsitoControlloFisico,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></td>

			<td><%=esitoList.getHtmlSelect("idEsitoControlloFisico", animale.getIdEsitoControlloFisico())%>
			</td>
		</tr>


		<tr class="containerBody">
			<td width="25%" valign="top" class="formLabel2"><dhv:label
				name=""> Controllo di laboratorio (anticorpale rabbia) </dhv:label>
			</td>

			<td valign="top" class="formLabel2"><input type="checkbox"
				name="flagPresenzaEsitoControlloLaboratorio" <% if (animale.isFlagPresenzaEsitoControlloLaboratorio()){%> checked="checked"<%} %> ></td>

			<td><input readonly type="text"
				id="dataEsitoControlloLaboratorio"
				name="dataEsitoControlloLaboratorio" size="10" value="<%=toDateasString(animale.getDataEsitoControlloLaboratorio()) %>"
				nomecampo="dataEsitoControlloLaboratorio"
				labelcampo="Data esito controllo laboratorio" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataEsitoControlloLaboratorio,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></td>

			<td><%=esitoControlloLaboratorioList
							.getHtmlSelect("idEsitoControlloLaboratorio", animale.getIdEsitoControlloLaboratorio())%>
			</td>
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
			<td><textarea name="note" rows="3" cols="50"><%=toString(animale.getNote())%></textarea>
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
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='1';if(doCheck(this.form)){this.form.submit()};" />
		


	<input type="hidden" name="doContinue" id="doContinue" value="">

	</form>