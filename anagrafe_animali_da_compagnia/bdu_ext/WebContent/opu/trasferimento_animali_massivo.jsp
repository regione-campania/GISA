<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,org.aspcfs.modules.anagrafe_animali.base.*"%>

<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%><jsp:useBean
	id="animaleList"
	class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList"
	scope="request" />
<jsp:useBean id="animaliListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="LookupSpecie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="operatore"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="newoperatore"
	class="org.aspcfs.modules.opu.base.Operatore" scope="session" />
<jsp:useBean id="idLp"
	class="java.lang.String" scope="session" />
<jsp:useBean id="dataTrasferimento"
	class="java.lang.String" scope="request" />
<jsp:useBean id="tutti"
	class="java.lang.String" scope="request" />



<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />


<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<%HashMap map = animaliListInfo.getSelectedItems(); %>

<%
	int idLinea = 0;
	if (operatore != null && operatore.getIdOperatore() > 0) {
		Stabilimento stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		idLinea = lp.getId();
	}
%>


<script type="text/javascript">

function submitSelecteds(link){
	//alert(link);
	$("#controlProperties").attr("action", link);
	document.form0.submit();
}


<%-- function clean (){
	if (!document.getElementById('tutti').checked){
		<%map.clear();%>
	}
} --%>

var selected = <%=(map.size()) %>;

function abilitaDisabilitaTutti()
{
	var nome = "no";
	var nomeOpposto = "si";
	if(document.getElementById('tutti').checked)
	{
		nome = "si";
		nomeOpposto = "no";
	}
	var i=1;
	while(document.getElementById("checkCane_"+nome + i)!=null)
	{
		document.getElementById("checkCane_"+nome + i).checked=true;
		document.getElementById("checkCane_"+nomeOpposto + i).checked=false;
		i++;
	}
}

function openPopup(url){
	//	alert('aaa');
	loadModalWindow();
		
		win = window.open(url,'','width=800,height=600');
		//win.onunload = function(){alert(win.closed); if (win.closed) loadModalWindowUnlock();}
	/* 	if (win.closed){
			alert('ddd');} */
		if (win != null)	{
			var timer = setInterval(function() {   
			    if(win.closed) {  
			    //	alert('ddd');
			        clearInterval(timer);  
			      //  alert('popup is closed');  
			        loadModalWindowUnlock();
			    }  
			}, 1000);  
		}
	//	return false;
	}





function checkForm(){
	formtest = true;
	message = '';
	//alert ('cxbb');
	
//	alert($("#dataMovimentazione").val());
	
	if ($("#dataTrasferimento").val() == null || $("#dataTrasferimento").val() == ''){
	//	alert('dfdsf');
		formtest = false;
		 message += label("","-Inserisci una data per il trasferimento \r\n");
       
	}
	
	if ($("#idOperatoreAdded").val() == null || $("#idOperatoreAdded").val() == ''){
		//	alert('dfdsf');
			formtest = false;
			 message += label("","-Inserisci un canile di destinazione per il trasferimento\r\n");
	       
		}
	


	if  ((!document.getElementById('tutti').checked) && (<%=(map.isEmpty()) %> && (selected == '0' || selected < 0))){

		formtest = false;
		 message += label("","-Seleziona almeno un animale da inserire nel trasferimento \r\n");
	}
	
	if (<%=(idLp != null && !("").equals(idLp) && animaleList.getId_detentore() == Integer.valueOf(idLp)) %> ){
		formtest = false;
		 message += label("","- Canile origine e destinazione coincidono \r\n");
	}
	
	
	
	if (formtest){
		$("#save").val(true);
		
		var link = 'LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivoSave&idLinea=<%=idLinea%>';
		$("#form0").attr("action", link);
		document.form0.submit();
		loadModalWindow();
	}
	else{
		alert(message);
	}
}

$(document).ready(function(){
$('input[type=radio]').change( function() {
	 
	   if (this.id.indexOf("si") > -1){
		   selected++;
	   }
	   
	   if (this.id.indexOf("no") > -1){
		   selected--;
	   }
	   //alert(selected);

	});
	
});
</script>




<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>

			<td>
			
				<a href="OperatoreAction.do?command=Search"><dhv:label name="">Risultati ricerca</dhv:label></a> > <a
					href="OperatoreAction.do?command=Details&opId=<%=idLinea%>"> <dhv:label
						name="">Dettaglio canile</dhv:label></a> >
						<dhv:label name="">Trasferimento massivo</dhv:label>
				</td>
			
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<%
	Stabilimento stab = null;
	LineaProduttiva lineaP = null;
	String param1="-1";
	if (operatore.getIdOperatore() > 0) {
		stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
		lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		 param1 = "idLinea=" + lineaP.getId();
	}
%>  


<form name="form0" id="form0" method="post" action="LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivo&idLinea=<%=idLinea%>">
<dhv:container name="anagrafica" selected="details"
		object="operatore" param="<%=param1%>"
		appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>


<br />


<link rel="stylesheet" type="text/css"
	href="extjs/resources/css/ext-all.css" />

<%
	if (operatore.getIdOperatore() > 0) {
		stab = (Stabilimento) operatore.getListaStabilimenti().get(0);
		lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		if (lineaP.getIdAttivita() == LineaProduttiva.idAttivitaColonia) {
%>


<%
	}
	}
%>
<table><tr><td>Data trasferimento</td><td>
<input readonly type="text" name="dataTrasferimento" id="dataTrasferimento"
				size="10" value="<%=dataTrasferimento%>"
				nomecampo="dataTrasferimento" tipocontrollo="T2"
				labelcampo="Data Trasferimento" />&nbsp; <a href="#"
				onClick="cal19.select(document.getElementById('dataTrasferimento'),'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">*</font></td></tr>
<tr><td>Canile</td><td>
<a
							onclick="javascript: loadModalWindow();openPopup('OperatoreAction.do?command=SearchForm&operazioneTrasferimentoMassivo=true&popup=true&tipoRegistrazione=31');"
							href="#">Ricerca</a><font color="red">*</font></td></tr>
<input type="hidden" name="idOperatoreAdded" id="idOperatoreAdded" value="<%=idLp %>" />
<input type="hidden" name="doContinue" id="doContinue"/>
<input type="hidden" name="save" id="save"/>
<tr><td><%=(newoperatore.getIdOperatore() > 0) ? newoperatore.getRagioneSociale() : "" %></td></tr>
<tr>
    <td>Includi tutti gli animali</td>
    <td><input type="checkbox" onclick="abilitaDisabilitaTutti()" value="tutti" name="tutti" id="tutti" <%=(tutti != null && !("").equals(tutti)) ? "checked" : "" %> /></td>
</tr>
<%
if(animaleList.size()>0)
{
%>
	<tr>
		<td><input type="button" onclick="javascript:checkForm();" value="Invia Dati"/></td>
	</tr>
<%
}
%>
</table>

<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<th width="16%" nowrap><strong>Tipologia</strong></th>
			<th width="16%" nowrap><strong><dhv:label name="">Microchip/Tatuaggio</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Stato</dhv:label></strong>
			</th>
			<th width="16%" nowrap><strong><dhv:label name="">Includi Mc nel trasferimento</dhv:label></strong>
			</th>
		</tr>
	</thead>
	<tbody>
		<%
			Iterator itr = animaleList.iterator();
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					Animale thisAnimale = (Animale) itr.next();
		%>
		<input type="hidden" id="<%=thisAnimale.getIdAnimale()%>_microchip"        name="<%=thisAnimale.getIdAnimale()%>_microchip"        value="<%=thisAnimale.getMicrochip() %>" >
		<input type="hidden" id="<%=thisAnimale.getIdAnimale()%>_idSpecie"         name="<%=thisAnimale.getIdAnimale()%>_idSpecie"         value="<%=thisAnimale.getIdSpecie()  %>" >
		<input type="hidden" id="<%=thisAnimale.getIdAnimale()%>_idAslRiferimento" name="<%=thisAnimale.getIdAnimale()%>_idAslRiferimento" value="<%=thisAnimale.getIdAslRiferimento() %>" >
		<input type="hidden" id="<%=thisAnimale.getIdAnimale()%>_idStato"          name="<%=thisAnimale.getIdAnimale()%>_idStato"          value="<%=thisAnimale.getStato() %>" >
		<input type="hidden" id="<%=thisAnimale.getIdAnimale()%>_idProprietario"   name="<%=thisAnimale.getIdAnimale()%>_idProprietario"   value="<%=thisAnimale.getIdProprietario() %>" >
		
		<tr class="row<%=rowid%>">
			<td width="15%" nowrap><%=LookupSpecie.getSelectedValue(thisAnimale.getIdSpecie())%></td>
			<td width="15%" nowrap><dhv:evaluate if="<%=!isPopup(request)%>">
					<a orderInfo="<%=toHtml(thisAnimale.getMicrochip())%>"
						href="AnimaleAction.do?command=Details&animaleId=<%=thisAnimale.getIdAnimale()%>&idSpecie=<%=thisAnimale.getIdSpecie()%><%=addLinkParams(request, "popup|popupType|actionId")%>"><%=toHtml(thisAnimale.getMicrochip())%></a>
				</dhv:evaluate> <dhv:evaluate if="<%=isPopup(request)%>">
					<%=toHtml((thisAnimale.getMicrochip() != null && !("").equals(thisAnimale.getMicrochip())) ? thisAnimale.getMicrochip() : thisAnimale.getMicrochip())%>
				</dhv:evaluate></td>
			<td width="15%" nowrap><%=toHtml(statoList.getSelectedValue(thisAnimale.getStato()))%>&nbsp;</td>
					<td>
					
						<input type="radio" id="checkCane_si<%=i%>" name="checkCane<%=thisAnimale.getIdAnimale()%>" value="true" > Si
						<input type="radio" id="checkCane_no<%=i%>" name="checkCane<%=thisAnimale.getIdAnimale()%>" value="false" checked="checked"> No<br>
	
											
					</td>	
		</tr>
		<%
			}
			} else {
		%>


		<tr class="containerBody">
			<td colspan="9"><dhv:label name="">Non sono stati trovati animali. Operazione non consentita 
				</dhv:label></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
</dhv:container>

</form>


<script type="text/javascript">
	abilitaDisabilitaTutti();
</script>