<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="it.us.web.util.json.JSONObject"%>

<%@page import="it.us.web.bean.guc.*"%>
<%@page import="java.util.*"%>
<%@page import="it.us.web.util.guc.GUCEndpoint"%>

<jsp:useBean id="UserRecord" class="it.us.web.bean.guc.Utente" scope="request"/>

<script type="text/javascript">

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 



function validateEmail(email) {
	  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
	}

  function checkForm(form) {
	  	var ids=[]; var j=0;
	  
	    formTest = true;
	    message = "";
	
	    


	    if ((  trim(form.email.value) == "")) {
	        message += "- Campo Email obbligatorio.\r\n";
	        formTest = false;
	    }
	    
	
	    if ((  trim(form.email.value) == '${UserRecord.email}')) {
	        message += "- Campo email non modificato.\r\n";
	        formTest = false;
	    }
	    
	    if (formTest == false) {
	      alert("La form non può essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
	      for (var k=0;k<ids.length;k++){
	    	  document.getElementById(ids[k]).disabled='disabled';
	      }
	      return false;
	    }
	    else {    	
	    	
	    	var controllo=validateEmail(form.email.value);
 	    	if (!controllo)
 	    	{
 	    		alert("Il campo email non e\' stato inserito nel formato corretto");
 	    	    return false;
 	    	}
 	    	
	    	
	    	var eles = [];
	    	var messExtOpt = '';
	    	var inputs = document.getElementsByTagName("input");
	    
	    	//if (!validaCodiceFiscale(form.codiceFiscale.value)){
	    		//alert('Codice fiscale non corretto.');
	    		//return false;
	    	//}
	    	
	    	if(confirm('Sei sicuro di voler aggiornare la mail?')){
	    		loadModalWindow();
	      		return form.submit();
	    	}
	    	else {
	    		
	    		return false;
	    	}
	    }
  }
  
  
 
</script>

<body onLoad="">
<form name="editUser" action="guc.EditEmail.us" onSubmit="return checkForm(this);" method="post">
<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
		<a href="guc.Detail.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/detail.gif" height="18px" width="18px" />Dettaglio Utente</a>
	 	<a href="guc.ToEnable.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/enable.gif" height="18px" width="18px" />Attiva/Disattiva Credenziali</a>
		<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px; display: none"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
	</div>
	
	<h4 class="titolopagina">Modifica Email</h4>
	
	

	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	
	
	<tr>
		<th colspan="2"><strong>Contatto</strong></th>
	</tr>
	<tr>
		<td class="formLabel">Nome</td>
	  	<td>${UserRecord.nome}</td>
	</tr>
	<tr>
    	<td class="formLabel">Cognome</td>
  		<td>${UserRecord.cognome}</td>
	</tr>
	<tr>
  		<td class="formLabel">Email</td>
  		<td><input type="text" name="email" id="email" value="${UserRecord.email}"  /><font color=red>*</font></td>
	</tr>
	
		<input type="hidden" name="id" id="id" value="${UserRecord.id}" />
	
	
<tr> <td colspan="2">
<input type="button" value="Aggiorna" onclick="javascript: checkForm(this.form);"/>
</td>



</div>
</form>
</body>
 	

 <%@ include file="modalWindow.jsp"%>
