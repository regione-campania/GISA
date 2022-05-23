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

<link href='css/cambiopassword/OpenSans.css' rel='stylesheet' type='text/css'>
<link href="css/cambiopassword/font-awesome.css" rel="stylesheet">
<script language="JavaScript" TYPE="text/javascript" SRC="js/cambioPassword.js"></script>

<script type="text/javascript">

  function trim(str){
	    return str.replace(/^\s+|\s+$/g,"");
  } 
    
 

  function checkForm(form) {
	  	var ids=[]; var j=0;
	    
		
	  
	    formTest = true;
	    message = "";
	
	    


	    if (checkFormPassword(form)==false)
	    	return false;
	    
	    
// 	    if ((  trim(form.nome.value) == "")) {
// 	        message += "- Campo nome obbligatorio.\r\n";
// 	        formTest = false;
// 	    }
// 	    if ((  trim(form.cognome.value) == "")) {
// 	        message += "- Campo cognome obbligatorio.\r\n";
// 	        formTest = false;
// 	    }
// 	    if ((  trim(form.codiceFiscale.value) == "")) {
// 	        message += "- Campo codice fiscale obbligatorio.\r\n";
// 	        formTest = false;
// 	    }
// 	    else if ((  trim(form.codiceFiscale.value).length != 16  )&& (  trim(form.codiceFiscale.value).length != 11)) {
// 	    	message += "- Il codice fiscale deve essere di 16 caratteri o di 11 in caso di partita iva.\r\n";
// 	        formTest = false;
// 	    }
	    if ((  trim(form.username.value) == "")) {
	        message += "- Campo username obbligatorio.\r\n";
	        formTest = false;
	    }
	    if ((  trim(form.username.value) != "")) {
	    	var usr = trim(form.username.value);
	    	if (usr.indexOf(" ")>-1){
		        message += "- Campo username non deve contenere spazi vuoti.\r\n";
		        formTest = false;
	        }
	    }
	    if ((form.password1.value != form.password2.value)) {
	        message += "- Controllare che entrambe le password siano inserite correttamente.\r\n";
	        formTest = false;
	    }
	    if ( ( trim(form.password1.value) != "") ) {
	    	form.newPassword.value = true;
	    }
	 
	    if (formTest == false) {
	      alert("La form non può essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
	      for (var k=0;k<ids.length;k++){
	    	  document.getElementById(ids[k]).disabled='disabled';
	      }
	      return false;
	    }
	    else {    	
	    	
	    	
// 	    	var controllo=controlloCF();
// 	    	if (controllo.indexOf("ATTENZIONE")>-1)
// 	    	{
// 	    		alert(controllo);
// 	    	    return false;
// 	    	}
	    	
	    	var eles = [];
	    	var messExtOpt = '';
	    	var inputs = document.getElementsByTagName("input");
	    
	    	
	    	
	    	if(confirm('SALVARE LE MODIFICHE ANAGRAFICHE APPORTATE ?')){
	      		return form.submit();
	    	}
	    	else {
	    		
	    		return false;
	    	}
	    }
  }
  
  
  function controlloCF(){
	    var cf="";  
	    if(document.getElementById('codiceFiscale')!=null)
	    	cf=trim(document.getElementById('codiceFiscale').value);  
		if(cf.length==11)
			return "";
		var validi, i, s, set1, set2, setpari, setdisp;
		    cf = cf.toUpperCase();
		    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		    for( i = 0; i < 16; i++ ){
		        if( validi.indexOf( cf.charAt(i) ) == -1 )
		            return "ATTENZIONE! Il codice fiscale contiene un carattere non valido '" +
		                cf.charAt(i) +
		                "'.\nI caratteri validi sono le lettere e le cifre.";
		    }
		    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
		    s = 0;
		    for( i = 1; i <= 13; i += 2 )
		        s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
		    for( i = 0; i <= 14; i += 2 )
		        s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
		    if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
		           return "ATTENZIONE! Il codice fiscale inserito non e' corretto.";
		    return "";

	}

 
</script>

<body onLoad="">
<form name="editUser" action="guc.EditAnagrafica.us" onSubmit="return checkForm(this);" method="post">
<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
		<a href="guc.Detail.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/detail.gif" height="18px" width="18px" />Dettaglio Utente</a>
	 	<a href="guc.ToEnable.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/enable.gif" height="18px" width="18px" />Attiva/Disattiva Credenziali</a>
		<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px; display: none"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
	</div>
	
	<h4 class="titolopagina">Modifica Credenziali</h4>
	
	

	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	
	<%--
	<tr>
		<th colspan="2"><strong>Contatto</strong></th>
	</tr>
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
  		<td><input type="text" name="codiceFiscale" id="codiceFiscale" value="${UserRecord.codiceFiscale}" maxlength="16" /><font color=red>*</font></td>
	</tr>
	
	<tr>
	  <td class="formLabel">E-mail</td>
	  <td>
	  
	  
	  <input type="text" name="email" value="${UserRecord.email}"/>
	  </td>
	</tr>
	<tr>
  		<td class="formLabel">Note</td>
  		<td><textarea name="note" rows="5" cols="50" >${UserRecord.note}</textarea></td>
	</tr> --%>
	
	<%-- <tr>
	  <td class="formLabel">Luogo</td>
	  <td>
	  <select id="luogo" name="luogo"  >
	  			<option value="-1">COMUNE</option>
	  				<%List<String> comuniList = (List<String>) request.getAttribute("comuniList");
	  		for(String comune : comuniList)
	  		{
	  		%>
	  			
	  				<option value="<%=comune %>" <%if(UserRecord.getLuogo()!=null && UserRecord.getLuogo().equalsIgnoreCase(comune)){ %>selected="selected"<% } %>><%=comune %></option>
			<%} %>	  
	  		</select>
	</td>	  
	</tr>
	<tr>
	  <td class="formLabel">Numero Autorizzazione (Solo per Veterinario Privato)</td>
	  <td>
	  <input class='bdu' type="text" id="numAutorizzazione" name="numAutorizzazione" value="${UserRecord.numAutorizzazione}"/>
	</tr>
	
	
	
	<tr>
	  <td class="formLabel">Provincia Iscrizione Albo (Solo per Unina e Veterinario Privato)</td>
	  <td>	  
	  <select class='bdu' id="id_provincia_iscrizione_albo_vet_privato" name="id_provincia_iscrizione_albo_vet_privato">
	  	<option value="-1">-- NESSUNA VOCE SELEZIONATA --</option>
	  	<% HashMap<String, Integer> HP = (HashMap<String,Integer>)request.getAttribute("HashProvince"); 
	  	   if (HP!=null && !HP.isEmpty()) {
	  		   Iterator it = HP.entrySet().iterator();
	  	   		while (it.hasNext()){ 
	  	   			Map.Entry entry = (Map.Entry)it.next();%>
	  	   			<option value="<%=entry.getValue()%>"
	  	   			<% if (UserRecord.getId_provincia_iscrizione_albo_vet_privato()==Integer.parseInt(entry.getValue().toString())){%>selected="selected"<%} %> ><%=entry.getKey()%></option>
	  	<% 		}
	  	   }%>
	  </select>
	</tr>
	
	<tr>
	  <td class="formLabel">Nr. Iscrizione Albo (Solo per Unina e Veterinario Privato)</td>
	  <td>	  
	  <input class='bdu' type="text" id="nr_iscrione_albo_vet_privato" name="nr_iscrione_albo_vet_privato" value="${UserRecord.nr_iscrione_albo_vet_privato}"/>
	</tr>
	 --%>
	
	
	<tr>
  		<th colspan="2"><strong>Credenziali</strong></th>
	</tr>
	<tr>
  		<td class="formLabel">Username</td>
  		<td>
    	<input type="text" name="username" style="background-color:#9c9c9c;" value="${UserRecord.username}" readonly="readonly" ><font color=red>*</font>
    	<input type="hidden" name="id" value="${UserRecord.id}" ></input>
    	<input type="hidden" name="oldUsername" value="${UserRecord.username}" ></input>
  		</td>
	</tr>
	<tr>
  		<td class="formLabel">Password</td>
  		<td>
  		<div id="policyPassword">
 <input type="password" class="password" name="password1" id="password1" maxlength="15" onmouseover="show()" onmouseout="hide()"/> <font color=red>*</font>
 <div align="right" id="policyForm" style="display:none"> 
 <font size="1px">
 <ul class="helper-text">
 <li class="length">Lunghezza 10-15 caratteri.</li>
 <li class="lowercase">Contiene almeno una minuscola.</li>
 <li class="uppercase">Contiene almeno una maiuscola.</li>
 <li class="special">Contiene almeno un numero.</li>
 </ul>
 </font>
 </div>
 </div>
  		<input type="hidden" name="password" value="${UserRecord.password}" ></input>
  		<input type="hidden" name="newPassword" value="false">
  		</td>
  		
	</tr>
	<tr>
  		<td class="formLabel">Password (di nuovo)</td>
  		<td><input type="password" name="password2" id="password2"></td>
	</tr>
	
	<tr><td></td><td>
 <div class="gender">
    <input type="radio" value="None" id="male" name="gender" checked onClick="mostraNascondi(this)"/>
    <label for="male" class="radio" chec>Nascondi</label>
    <input type="radio" value="None" id="female" name="gender" onClick="mostraNascondi(this)"/>
    <label for="female" class="radio">Mostra</label>
   </div> 
</td></tr>

	</table>
<%@ include file="../cambiopassword/policyPassword.jsp" %>
	

<input type="button" value="Aggiorna" onclick="javascript: checkForm(this.form);"/>




</div>
</form>
</body>
 	


