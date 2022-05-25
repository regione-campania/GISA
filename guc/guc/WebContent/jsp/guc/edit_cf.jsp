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
  
  function validaCodiceFiscale(cf)
        {
            var validi, i, s, set1, set2, setpari, setdisp;
            if( cf == '' )  return '';
            cf = cf.toUpperCase();
            if( cf.length != 16 )
                return false;
            validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for( i = 0; i < 16; i++ ){
                if( validi.indexOf( cf.charAt(i) ) == -1 )
                    return false;
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
                return false;
            return true;
        }


 

  function checkForm(form) {
	  	var ids=[]; var j=0;
	    
		
	  
	    formTest = true;
	    message = "";
	
	    


	    if ((  trim(form.codiceFiscale.value) == "")) {
	        message += "- Campo codice fiscale obbligatorio.\r\n";
	        formTest = false;
	    }
	    else if ((  trim(form.codiceFiscale.value).length != 16  )&& (  trim(form.codiceFiscale.value).length != 11)) {
	    	message += "- Il codice fiscale deve essere di 16 caratteri o di 11 in caso di partita iva.\r\n";
	        formTest = false;
	    }
	
	    if ((  trim(form.codiceFiscale.value) == '${UserRecord.codiceFiscale}')) {
	        message += "- Campo codice fiscale non modificato.\r\n";
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
	    	
	    	
 	    	var controllo=controlloCF();
 	    	if (controllo.indexOf("ATTENZIONE")>-1)
 	    	{
 	    		alert(controllo);
 	    	    return false;
 	    	}
	    	
	    	var eles = [];
	    	var messExtOpt = '';
	    	var inputs = document.getElementsByTagName("input");
	    
	    	//if (!validaCodiceFiscale(form.codiceFiscale.value)){
	    		//alert('Codice fiscale non corretto.');
	    		//return false;
	    	//}
	    	
	    	if(confirm('Sei sicuro di voler aggiornare il codice fiscale in tutti gli End Point?')){
	    		loadModalWindow();
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
<form name="editUser" action="guc.EditCf.us" onSubmit="return checkForm(this);" method="post">
<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
		<a href="guc.Detail.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/detail.gif" height="18px" width="18px" />Dettaglio Utente</a>
	 	<a href="guc.ToEnable.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/enable.gif" height="18px" width="18px" />Attiva/Disattiva Credenziali</a>
		<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px; display: none"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
	</div>
	
	<h4 class="titolopagina">Modifica Codice Fiscale</h4>
	
	

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
  		<td class="formLabel">Codice fiscale</td>
  		<td><input type="text" name="codiceFiscale" id="codiceFiscale" value="${UserRecord.codiceFiscale}" maxlength="16" onKeyUp="this.value = this.value.toUpperCase();"/><font color=red>*</font></td>
	</tr>
	
		<input type="hidden" name="id" id="id" value="${UserRecord.id}" />
	
	
<tr> <td colspan="2">
<input type="button" value="Aggiorna" onclick="javascript: checkForm(this.form);"/>
</td>



</div>
</form>
</body>
 	

 <%@ include file="modalWindow.jsp"%>
