<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<link rel="stylesheet"	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<script>
function checkForm(){
	var formTest = true;
	var message = "";
	var form = document.forms['addBloccoSblocco'];
	
	var myselect = document.getElementById("motivo");
	var motivo=myselect.options[myselect.selectedIndex].value;

	if (motivo=="-1" ){
	    message += label("","- Attenzione, inserire il motivo.\r\n");
		formTest = false;	  
	}else{
		if (motivo=="Altro"){
			if(form.note.value.length<=0){
				message += label("","- Attenzione, selezionando 'Altro', è necessario inserire il campo 'Note'.\r\n");
				formTest = false;	  
				}
			}
		}
	if(form.dataEffettiva.value==""){
		message += label("","- Attenzione, inserire la data.\r\n");
		formTest = false;	  		
	}else{
		var data_eff=form.dataEffettiva.value.split("/");
		if(document.getElementById("data_hidden")!=null){
			 var data_min =document.getElementById("data_hidden").value.split("/");
			 var data_inf = new Date(data_min[2], data_min[1] - 1, data_min[0]);
			 var data_ins = new Date(data_eff[2], data_eff[1] - 1, data_eff[0]);
			 if (data_ins < data_inf){
					message += label("","- Attenzione, la data inserita deve essere maggiore del "+document.getElementById("data_hidden").value+".\r\n");
					formTest = false;	  	 
			 }	 
		}	
	}
    if (formTest == false) {
   		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      	return false;
    }else{
    	form.submit();
    }
}
</script>


<% if(request.getAttribute("esito_inserimento") != null && ((String)request.getAttribute("esito_inserimento")).equals("ok")) {%>
<script>
	alert("Operazione eseguita con successo!");
	window.opener.document.location.href="OperatoreAction.do?command=Details&opId=<%=(String)request.getAttribute("id_canile")%>&popup=false";
	window.close();
</script>
<% }else{ 
	if(request.getAttribute("ErrorBlocco") != null && !((String)request.getAttribute("ErrorBlocco")).equals("")) {%>
		<script>
			alert("<%=((String)request.getAttribute("ErrorBlocco"))%>");
		</script><% 
	} %>
	<form id="addBloccoSblocco" name="addBloccoSblocco"
		action="OperatoreAction.do?command=BloccaSbloccaCanile"
		method="post">
		<input type="hidden" id="inserimento" name="inserimento" value="true"/>
		<input type="hidden" id="idCanile" name="idCanile" value='<%=(request.getParameter("idCanile"))%>'/>
			
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<!-- IF BLOCCO O SBLOCCO CANILE -->
	<% 
	String tipo_op="";
	String tipo_op_min="";
	if(request.getParameter("operazione") != null && request.getParameter("operazione").equals("sblocca")){ 
		tipo_op="RIATTIVAZIONE"; tipo_op_min="riattivazione";
		%>
		<input type="hidden" id="tipo_operazione" name="tipo_operazione" value="sblocca"/>
	<% }else{ 
		tipo_op="SOSPENSIONE"; tipo_op_min="sospensione";%>
		<input type="hidden" id="tipo_operazione" name="tipo_operazione" value="blocca"/>
	<% } %>
		<tr >
			<th colspan="2"><strong><dhv:label name="">INFORMAZIONI <%=tipo_op%> INGRESSI CANILE</dhv:label></strong></th>
		</tr>
				<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data <%=tipo_op_min%> ingressi</dhv:label>
				</td>
				<td>	<input type="text" readonly
						name="dataEffettiva" id="dataEffettiva" size="10"
						value=""
						nomecampo="dataEffettiva" tipocontrollo=""
						labelcampo="Data effettiva" />&nbsp; <a href="#" 
						onClick="cal19.select(document.forms[0].dataEffettiva, 'anchor19','dd/MM/yyyy'); return false;"
						NAME="anchor19" ID="anchor19"> <img
						src="images/icons/stock_form-date-field-16.gif" border="0"
						align="absmidle"></a>
					
					
					
					<% if(tipo_op.equals("RIATTIVAZIONE")){ %>
						</a>&nbsp;<font color="red">* La data deve essere superiore al </font><b><%
						String data_=""+((String)request.getAttribute("data_minima_operazione")); 
						out.print(data_);
						%>
						<input type="hidden" id="data_hidden" value="<%=data_%>"/>
						</b><% 
					} else if((request.getAttribute("data_inizio_attivita")!=null && !(((String)request.getAttribute("data_inizio_attivita")).equals("")))){ %>
						</a>&nbsp;<font color="red">* La data deve essere superiore al </font><b>
						<%
						String data_=""+((String)request.getAttribute("data_inizio_attivita")); 
						out.print(data_);
						%>
						<input type="hidden" id="data_hidden" value="<%=data_%>"/>
						</b>
					<% } %>
					</td>
			</tr>
			
			<tr  class="containerBody">
					<td class="formLabel"><dhv:label name="">Motivo</dhv:label></td>
					<td>
							<select id="motivo" name="motivo">
								<option value="-1">--Seleziona---</option>
								<option value="Richiesta referenti Asl">Richiesta referenti Asl</option>
								<option value="Richiesta referenti regionali">Richiesta referenti regionali</option>
								<option value="Altro">Altro</option>
							</select>
							<font color="red">&nbsp;*</font> 
					</td>
				</tr>
				
			<tr  class="containerBody">
					<td class="formLabel"><dhv:label name="">Note</dhv:label></td>
					<td><textarea rows="4" cols="50" id="note" name="note"></textarea></td>
			</tr>	
	</table>	
	<input
		type="button"
		value="<dhv:label name="global.button.insert">Insert</dhv:label>"
		name="Save" onClick="checkForm()">
	
	</form>

<% } %>
