<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>
	
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="operatoreToModify" class="org.aspcfs.modules.opu.base.Operatore"
	scope="request" />
<jsp:useBean id="nuovoIndirizzo" class="org.aspcfs.modules.opu.base.Indirizzo"
	scope="request" />
<jsp:useBean id="vecchioIndirizzo" class="org.aspcfs.modules.opu.base.Indirizzo"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="regioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="province" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="comuni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="regModifica" class="org.aspcfs.modules.opu.base.RegistrazioneModificaIndirizzoOperatore"
	scope="request" />	
	

<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script language="javascript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script language="JavaScript">

function doCheck(form){
	formTest = true;
	message = "";
//	alert($('#nuovaDimensione').val());
//	if ($('#nuovaDimensione').val() == null || $('#nuovaDimensione').val() == ''){
		//alert('ddd');
//		formTest = false;
//		message += "Specifica una nuova dimensione della colonia \r\n";
//	}
	 
	 if ($('#dataModificaResidenza').val() == '' ){
			//alert('ddd');
			formTest = false;
			message += "Specifica la data accettazione \r\n";
		}
	 
	 
    if (formTest == false) {
      alert(label("check.form", "Impossibile proseguire, per favore verifica i seguenti problemi:\r\n\r\n") + message);
      return false;
    }
    else
    {
    	
    	
    	return true;
    }
}


</script>

<%Stabilimento stab = (Stabilimento) operatoreToModify.getListaStabilimenti().get(0); 
  LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
  Indirizzo indOperativo = stab.getSedeOperativa();
%>

<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="OperatoreAction.do?command=details&opId=<%=lp.getId() %>"><dhv:label
				name="">Operatore</dhv:label></a> > <dhv:label
				name="">Accettazione modifica residenza</dhv:label>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>

<form name="modificaIndirizzo"
	action="OperatoreAction.do?command=AccettaModificaIndirizzoOperatore&auto-populate=true"
	method="post" onsubmit="javascript:return checkForm();">


<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Informazioni operatore</dhv:label></strong>
		</th>
	</tr>

	<tr>
		<td class="formLabel" nowrap> Asl di origine </td>
		<td><%=AslList.getSelectedValue(regModifica.getIdAslInserimentoRegistrazione()) %></td>
		<input type="hidden" name="idAslRiferimento" id="idAslRiferimento"
			value="<%=User.getSiteId() %>" />
	</tr>
	<tr>
		<td class="formLabel" nowrap>Provincia</td>
		<td><%=province.getSelectedValue(vecchioIndirizzo.getProvincia()) %></td>
	</tr>
	<input type="hidden" name="idIndirizzoOperativoOld" id="idIndirizzoOperativoOld"
		value=" <%=vecchioIndirizzo.getIdIndirizzo() %>" />
	<tr>
		<td class="formLabel" nowrap>Comune</td>
		<td><%=comuni.getSelectedValue(vecchioIndirizzo.getComune())%></td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Via</td>
		<td><%=(vecchioIndirizzo.getVia())%>
		<input type="hidden" name="idRelazioneStabilimentoLineaProduttiva" id="idRelazioneStabilimentoLineaProduttiva" value="<%=lp.getId() %>"/>
		
		</td>
	</tr>
	
		<tr>
		<td class="formLabel" nowrap>Dati Nuovo Indirizzo</td>
		<td><%=(nuovoIndirizzo.toString())%>
	
		
		</td>
	</tr>
	
	
</table>
</br>
</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Dati nuovo indirizzo</dhv:label></strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Data accettazione modifica indirizzo</td>
		<td><input readonly type="text" name="dataPresaModificaResidenza" size="10" id="dataModificaResidenza"
				value=""
				nomecampo="dataPresaModificaResidenza" tipocontrollo="T2" labelcampo="Data modifica indirizzo" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataPresaModificaResidenza,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">*</font></td>
	</tr>


<tr><td>&nbsp;</td><td>
	<input type="button" value="invia" id="invia"
		name="invia" onclick="if(doCheck(this.form)){this.form.submit()}" /></td></tr>
</table>

</br>
</br>






</form>