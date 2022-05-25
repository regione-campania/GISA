<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@page import="java.util.*"%>
<%@page import="it.us.web.util.guc.*"%>
<%@page import="it.us.web.bean.guc.*"%>

<script type="text/javascript">

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
  } 

function checkForm(form) {
    formTest = true;
    message = "";
    
  
    if ((  trim(form.nome.value) == "")) {
        message += "- Campo nome obbligatorio.\r\n";
        formTest = false;
    }
    if ((  trim(form.cognome.value) == "")) {
        message += "- Campo cognome obbligatorio.\r\n";
        formTest = false;
    }
    if ((  trim(form.codiceFiscale.value) == "")) {
        message += "- Campo codice fiscale obbligatorio.\r\n";
        formTest = false;
    }
    else if ((  trim(form.codiceFiscale.value).length != 16)) {
    	message += "- Il codice fiscale deve essere di 16 caratteri.\r\n";
        formTest = false;
    }
    if ((  trim(form.username.value) == "")) {
        message += "- Campo username obbligatorio.\r\n";
        formTest = false;
    }
    if (form.ruolo.value == "-1") {
        message += "- Campo ruolo obbligatorio.\r\n";
        formTest = false;
    } else if (form.ruolo.value == "SuperAmministratore") {
    	form.superAdmin.value=true;
    }
    
    if ((form.password1.value != form.password2.value)) {
        message += "- Controllare che entrambe le password siano inserite correttamente.\r\n";
        formTest = false;
    } else {
    	form.password.value=form.password1.value;
    }
    if ( ( trim(form.password1.value) != "" && form.addedit.value=="edit") ) {
    	form.newPassword.value = true;
    }
    
    if ((form.password1.value=="" || form.password2.value=="") && form.addedit.value=="add"){
        message += "- Campo password obbligatorio.\r\n";
        formTest = false;
    }
 	
    if (formTest == false) {
        alert("La form non può essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
        return false;
    }
    else {
        return true;
   	}
}

function svuotaData(input){
	input.value = '';
}
</script>

<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img
			src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
	</div>

	<c:if test="${idUtente==-1}"> 
	<h4 class="titolopagina">Aggiungi Utente Amministratore</h4></c:if>
	<c:if test="${idUtente!=-1}"> 
	<h4 class="titolopagina">Modifica Utente Amministratore</h4></c:if>



	<form name="addEditUser" action="guc.AddEditAdministrator.us?idUtente=${idUtente}&operation=${operation}" onSubmit="return checkForm(this);" method="post">
		<input type="hidden" name="addedit" value="${operation}" />
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<td class="formLabel">Nome</td>
				<td><input type="text" name="nome" value="${UserRecord.nome}" /><font color=red>*</font></td>
			</tr>
			<tr>
				<td class="formLabel">Cognome</td>
				<td><input type="text" name="cognome" value="${UserRecord.cognome}" /><font color=red>*</font></td>
			</tr>
			<tr>
				<td class="formLabel">Codice fiscale</td>
				<td><input type="text" name="codiceFiscale"
					value="${UserRecord.codiceFiscale}" maxlength="16" /><font color=red>*</font></td>
			</tr>
			<tr>
				<td class="formLabel">Data Nascita</td>
				<td><input type="text" id="dataNascita" name="dataNascita" maxlength="32" size="50" readonly="readonly" style="width:136px;"/>
	 				<img src="images/b_calendar.gif" alt="calendario" id="id_cal_1" />
					<script type="text/javascript">
	   					 Calendar.setup({
	     					inputField      :    "dataNascita",     // id of the input field
	     					ifFormat        :    "%d/%m/%Y",      // format of the input field
	    					button          :    "id_cal_1",  // trigger for the calendar (button ID)
	     					singleClick     :    true,
	     					timeFormat		:    "24",
	     					showsTime		:    false
						 });					    
					 </script>
					<a style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataNascita);"><img src="images/delete.gif" /></a>
				</td>
			</tr>
			<tr>
				<td class="formLabel">Comune Nascita</td>
				<td><input type="text" name="comuneNascita"
					value="${UserRecord.comuneNascita}"/></td>
			</tr>
			<tr><td></td></tr><tr><td></td></tr>
			<tr>
				<td class="formLabel">Indirizzo</td>
				<td><input type="text" name="indirizzo"
					value="${UserRecord.indirizzo}"/>
			</tr>
			<tr>
				<td class="formLabel">CAP</td>
				<td><input type="text" name="cap"
					value="${UserRecord.cap}" />
			</tr>
			<tr>
				<td class="formLabel">Comune</td>
				<td><input type="text" name="comune"
					value="${UserRecord.comune}" />
			</tr>
			<tr>
				<td class="formLabel">Provincia</td>
				<td><input type="text" name="provincia"
					value="${UserRecord.provincia}" />
			</tr>
			<tr>
				<td class="formLabel">Stato</td>
				<td><input type="text" name="stato"
					value="${UserRecord.stato}" />
			</tr>
			<tr><td></td></tr><tr><td></td></tr>			
			<tr>
				<td class="formLabel">E-mail</td>
				<td><input type="text" name="email" value="${UserRecord.email}" />
			</tr>
			<tr>
				<td class="formLabel">Fax</td>
				<td><input type="text" name="fax" value="${UserRecord.fax}" />
			</tr>
			<tr>
				<td class="formLabel">Telefono 1</td>
				<td><input type="text" name="telefono1" value="${UserRecord.telefono1}" />
			</tr>
			<tr>
				<td class="formLabel">Telefono 2</td>
				<td><input type="text" name="telefono2" value="${UserRecord.telefono2}" />
			</tr>
			<tr>
				<td class="formLabel">Note</td>
				<td><textarea name="note" rows="5" cols="50">${UserRecord.note}</textarea></td>
			</tr>
			<tr><td></td></tr><tr><td></td></tr>
			<tr>
				<td class="formLabel">Username</td>
				<td><input type="text" name="username"
					value="${UserRecord.username}"><font color=red>*</font></td>
			</tr>
			
			<c:if test="${operation=='edit'}"> 
				<tr>
					<td class="formLabel">Password</td>
					<td><input type="password" name="password1" value="">
					<input type="hidden" name="password" value="${UserRecord.password}" ></input>
	  				<input type="hidden" name="newPassword" value="false">
	  				</td>
				</tr>
				
				<tr>
					<td class="formLabel">Password (di nuovo)</td>
					<td><input type="password" name="password2" value=""></td>
				</tr>
			</c:if>
			<c:if test="${operation=='add'}">
				<tr>
					<td class="formLabel">Password</td>
					<td><input type="password" name="password1" value=""><font color=red>*</font>
					<input type="hidden" name="password" value="" ></input></td>
				</tr>
				
				<tr>
					<td class="formLabel">Password (di nuovo)</td>
					<td><input type="password" name="password2"><font color=red>*</font></td>
				</tr>
					
			</c:if>
			
			<tr>
				<td class="formLabel">Ruolo</td>
				<td>
				<input type="hidden" name="superAdmin" value="false"></input>				
				<select name="ruolo">
					<option value="-1">- Seleziona Ruolo -</option>
					<c:forEach items="${ruoli}" var="r">
						<option value="${r.ruolo}" <c:if test="${UserRecord.ruolo !=null  && r.ruolo==UserRecord.ruolo}">selected="selected"</c:if> >${r.ruolo}</option>
					</c:forEach>
				</select><font color=red>*</font>
				</td>
			</tr>			
			<tr>
				<td class="formLabel">Scadenza login</td>
				<td><input type="text" id="dataScadenza" name="dataScadenza" maxlength="32" size="50" readonly="readonly" style="width: 136px;" />
					<img src="images/b_calendar.gif" alt="calendario" id="id_cal_1" />
					<script type="text/javascript">
						Calendar.setup({
							inputField : "dataScadenza", // id of the input field
							ifFormat : "%d/%m/%Y", // format of the input field
							button : "id_cal_1", // trigger for the calendar (button ID)
							singleClick : true,
							timeFormat : "24",
							showsTime : false
						});
					</script> <a style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataScadenza);"> <img src="images/delete.gif" /></a>
				</td>
			</tr>
			<tr>
				<td class="formLabel">Abilitato</td>
				<td><select name="enabled">
					<option value="true" <c:if test="${UserRecord.enabled==true}">selected="selected"</c:if>>SI</option>
					<option value="false" <c:if test="${UserRecord.enabled==false}">selected="selected"</c:if>>NO</option>
				</select></td>
			</tr>	
		</table>
		<br> <c:if test="${idUtente==-1}"> 
				<input type="submit" value="Inserisci"> </c:if>
			<c:if test="${idUtente!=-1}">
				<input type="submit" value="Aggiorna"> </c:if>
	</form>
</div>
