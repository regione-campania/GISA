<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/DwrGiava.js"> </script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>

<%
	String riferimento_id_giava = request.getParameter("riferimentoIdGiava");
	String riferimento_id_nome_tab_giava = request.getParameter("riferimentoIdNomeTabGiava");
%>

<input style="width:250px" type="button" value="CREA UTENTE GIAVA" onclick="crea_utente_giava_popup()"/>
<div id="popupcreazioneutentegiava"></div>

<script>

$(function() {
	
	 $('#popupcreazioneutentegiava').dialog({
		title : 'CREAZIONE UTENTE PER SISTEMA GIAVA',
         autoOpen: false,
         resizable: false,
         closeOnEscape: false,
         width:1200,
         height:550,
         draggable: false,
         modal: true,
	     buttons: {
			 'AGGIUNGI': function() { 
				 			crea_utente_giava();
						 },
			'ANNULLA': function() {
			                loadModalWindowUnlock();
							$( this ).dialog('close');
					   }
      	 }
 	  });	 
});

function crea_utente_giava_popup(){
	
	var htmlText='<br>';
	htmlText += '<fieldset>'+
					'<legend><b>FORM INSERIMENTO</b></legend>'+
					'<table class="table details" style="border-collapse: collapse" width="100%" cellpadding="5"> '+
						'<tr>'+
							'<td style="width:10%" align="right">'+
								'<p>username</p>'+
							'</td>'+
							'<td style="width:60%" align="left">'+
								'<input type="text" id="usename_giava" placeholder="username" maxlength="30" size="40" autocomplete="off" value="">' +
								'<font color="red"> * massimo 30 caratteri</font>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:10%" align="right">'+
								'<p>password</p>'+
							'</td>'+
							'<td style="width:60%" align="left">'+
								'<input type="password" id="password_giava" placeholder="password" maxlength="20" size="30" autocomplete="off" value="">' +
								'<font color="red"> * massimo 20 caratteri</font>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:10%" align="right">'+
								'<p>conferma password</p>'+
							'</td>'+
							'<td style="width:60%" align="left">'+
								'<input type="password" id="conferma_password_giava" placeholder="password" maxlength="20" size="30" autocomplete="off" value="">' +
								'<font color="red"> * massimo 20 caratteri</font>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:10%" align="right">'+
								'<p>NOME</p>'+
							'</td>'+
							'<td style="width:60%" align="left">'+
								'<input type="text" id="nome_utente_giava" placeholder="nome"  maxlength="40" size="60" autocomplete="off" value="">' +
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:10%" align="right">'+
								'<p>COGNOME</p>'+
							'</td>'+
							'<td style="width:60%" align="left">'+
								'<input type="text" id="cognome_utente_giava" placeholder="cognome"  maxlength="40" size="60" autocomplete="off" value="">' +
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:10%" align="right">'+
								'<p>codice fiscale</p>'+
							'</td>'+
							'<td style="width:60%" align="left">'+
								'<input type="text" id="cf_utente_giava" placeholder="codice fiscale"  maxlength="20" size="30" autocomplete="off" value="">' +
							'</td>'+
						'</tr>'+
					'</table>'+
					'<input type="hidden" id="rif_id_stab_giava" value="<%=riferimento_id_giava%>"/>'+
					'<input type="hidden" id="rif_id_nome_tab_stab_giava" value="<%=riferimento_id_nome_tab_giava%>"/>'+
					'<input type="hidden" id="esistenza_stab_giava" value="0"/>'+
					'<input type="hidden" id="esito_creazione_utente_giava" value="0"/>'+
				'</fieldset>';
	$('#popupcreazioneutentegiava').html(htmlText);
	verificaEsistenzaStabGiava(<%=riferimento_id_giava%>, '<%=riferimento_id_nome_tab_giava%>');
	if(document.getElementById('esistenza_stab_giava').value != '0'){
		$('#popupcreazioneutentegiava').dialog('close');
		alert('Attenzione: esiste già un utente attivo per questo stabilimento!');		
	} else {
		$('#popupcreazioneutentegiava').dialog('open');
	}
	
}

function crea_utente_giava(){
	var usename_giava = document.getElementById('usename_giava').value;
	var password_giava = document.getElementById('password_giava').value;
	var conferma_password_giava = document.getElementById('conferma_password_giava').value;
	var nome_utente_giava = document.getElementById('nome_utente_giava').value;
	var cognome_utente_giava = document.getElementById('cognome_utente_giava').value;
	var cf_utente_giava = document.getElementById('cf_utente_giava').value;
	var rif_id_stab_giava = document.getElementById('rif_id_stab_giava').value;
	var rif_id_nome_tab_stab_giava = document.getElementById('rif_id_nome_tab_stab_giava').value;
	
	//controlli sui dati inseriti
	if(usename_giava.trim() == ''){alert('Attenzione: inserire username'); return false;}
	if(password_giava.trim() == ''){alert('Attenzione: inserire password'); return false;}
	if(nome_utente_giava.trim() == ''){alert('Attenzione: inserire nome utente'); return false;}
	if(cognome_utente_giava.trim() == ''){alert('Attenzione: inserire cognome utente'); return false;}
	if(cf_utente_giava.trim() == ''){alert('Attenzione: inserire codice fiscale utente'); return false;}
	if(password_giava.trim() != conferma_password_giava.trim()){
		alert('Attenzione: password e conferma password non coincidono!'); 
		return false;
	}
	
	//chiamata dwr per inserimento utente
	DwrGiava.inserisciUtenteGiava( nome_utente_giava, cognome_utente_giava, cf_utente_giava, 
								   usename_giava, password_giava, rif_id_stab_giava, 
								   rif_id_nome_tab_stab_giava, {callback:inserisciUtenteGiavaCallBack,async:false});
	var esito_creazione = document.getElementById('esito_creazione_utente_giava').value;
	if(esito_creazione.indexOf('ERRORE') !== -1){
		alert(document.getElementById('esito_creazione_utente_giava').value);
	} else {
		$('#popupcreazioneutentegiava').dialog('close');
		alert(document.getElementById('esito_creazione_utente_giava').value);		
	}
	
}

function inserisciUtenteGiavaCallBack(returnValue){
	document.getElementById('esito_creazione_utente_giava').value = returnValue;
}

function verificaEsistenzaStabGiava(rif_id_stab_giava, rif_id_nome_tab_stab_giava){
	
	DwrGiava.verificaEsistenzaStabGiava(rif_id_stab_giava, rif_id_nome_tab_stab_giava, 
			{callback:verificaEsistenzaStabGiavaCallBack,async:false});
}

function verificaEsistenzaStabGiavaCallBack(returnValue){
	
	if(returnValue != '0'){
		document.getElementById('esistenza_stab_giava').value = '1';
	}
}

</script>