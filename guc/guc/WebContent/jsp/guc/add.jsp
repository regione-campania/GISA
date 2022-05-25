<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.util.json.JSONObject"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="java.util.*"%>
<%@page import="it.us.web.util.guc.*"%>
<%@page import="it.us.web.bean.guc.*"%>

<link href='css/cambiopassword/OpenSans.css' rel='stylesheet' type='text/css'>
<link href="css/cambiopassword/font-awesome.css" rel="stylesheet">
<script language="JavaScript" TYPE="text/javascript" SRC="js/cambioPassword.js"></script>

<script type="text/javascript" src="dwr/interface/DwrGUC.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<style>
input,label,a,.details,.pagedlist{text-transform:uppercase !important;}
.menutabs-td,.menutabs-th,.trails,.submenuItemUnselected,.sidetab-right,.sidetab-right-sel,.dettaglioTabella,
.odd,.even,.cg-DivItem,.submenuItemSelected{text-transform:uppercase !important;}

</style>

<script type="text/javascript">
  function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
  } 
  
  function validateEmail(email) {
	  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
	}

  
  function abilitaCampiBDU(form,endpoint) 
  {
	  if(endpoint=='bdu')
	  {
	  	if (form.roleIdbdu.value == '24' || form.roleIdbdu.value == '37')
	  	{
		  	form.id_provincia_iscrizione_albo_vet_privato.disabled = "";
		  	form.nr_iscrione_albo_vet_privato.disabled = "";
		  	form.id_provincia_iscrizione_albo_vet_privato.value = "-1";
		  	form.nr_iscrione_albo_vet_privato.value = "";
	  	} 
	  	else 
	  	{
		  	form.id_provincia_iscrizione_albo_vet_privato.value = "-1";
		  	form.nr_iscrione_albo_vet_privato.value = "";
		  	form.id_provincia_iscrizione_albo_vet_privato.disabled = "disabled";
		  	form.nr_iscrione_albo_vet_privato.disabled = "disabled";
	  	}
 	  	if (form.roleIdbdu.value == '24')
 	  	{
		  	form.numAutorizzazione.disabled="";
		  	form.numAutorizzazione.value="";
	  	} 
 	  	else 
 	  	{
		  	form.numAutorizzazione.value="";
		  	form.numAutorizzazione.disabled="disabled";
	  	}
	}
  }
  
  function abilitaCampiVAM(form,endpoint) 
  {
	  if(endpoint=='Vam')
	  {
	  		if (form.roleIdVam.value == '18' )
	  		{
		  		form.id_provincia_iscrizione_albo_vet_privato_vam.disabled = "";
		  		form.nr_iscrione_albo_vet_privato_vam.disabled = "";
		  		form.id_provincia_iscrizione_albo_vet_privato_vam.value = "-1";
		  		form.nr_iscrione_albo_vet_privato_vam.value = "";
	  		} 
	  		else 
	  		{
		  		form.id_provincia_iscrizione_albo_vet_privato_vam.value = "-1";
		  		form.nr_iscrione_albo_vet_privato_vam.value = "";
		  		form.id_provincia_iscrizione_albo_vet_privato_vam.disabled = "disabled";
		  		form.nr_iscrione_albo_vet_privato_vam.disabled = "disabled";
	  		}
      }
  }
  
  function abilitaCampoAccess(form,endpoint) 
  {
	  if(endpoint=='Gisa_ext')
	  {
	  		if (form.roleIdGisa_ext.value == '10000008' )
	  		{
		  		form.Gisa_ext_access.disabled = "true";
		  		form.hidden_Gisa_ext_access.value = "false";
	  		} 
	  		else 
	  		{
	  			form.Gisa_ext_access.disabled = false;
	  		}
      }
  }

  
 
  function checkForm(form) {
    formTest = true;
    message = "";

    if (form.roleIdGisa_ext.value != '-1' && form.roleIdGisa.value != '-1'){
    	message += "- Impossibile inserire lo stesso utente sia in GISA che in GISA EXT.\r\n";
	      formTest = false;
    }
    
    if (form.roleIdGisa_ext.value == '10000006')
    {
    	if (form.gestore.value=='-1' || form.comuneGestore.value=='-1'){
		 	message += "- Campo 'Gestore Acque' e 'Comune Gestore Acque' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    }
    
    if (form.roleIdGisa_ext.value == '10000001' || (form.roleIdGisa_ext.value == '10000002' && form.tipoAttivitaApicoltore.value=="C"))
    {
    	if (form.piva.value=='' ){
		 	message += "- Campo 'Partita iva associazione/attivita apicoltura' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    }
    
    if (form.roleIdGisa_ext.value == '10000002')
    {
    	if (form.tipoAttivitaApicoltore.value=='' ){
		 	message += "- Campo 'Tipo attivita apicoltore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    	if (form.comuneApicoltore.value=='-1' ){
		 	message += "- Campo 'Comune apicoltore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    	if (form.indirizzoApicoltore.value=='' ){
		 	message += "- Campo 'Indirizzo apicoltore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    	if (form.capIndirizzoApicoltore.value=='' ){
		 	message += "- Campo 'CAP Indirizzo apicoltore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    }
    
    if (form.roleIdGisa_ext.value == '10000004')
    {
    	if (form.comuneTrasportatore.value=='-1' ){
		 	message += "- Campo 'Comune trasportatore/distributore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    	if (form.indirizzoTrasportatore.value=='' ){
		 	message += "- Campo 'Indirizzo trasportatore/distributore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    	if (form.capIndirizzoTrasportatore.value=='' ){
		 	message += "- Campo 'CAP Indirizzo trasportatore/distributore' obbligatorio per il ruolo specificato.\r\n";
	        formTest = false;
		}	
    }
    	

    if (form.roleIdbdu.value == '24'){
		 if (form.numAutorizzazione.value==''){
		  message += "- Campo nr. autorizzazione obbligatorio per LP.\r\n";
	      formTest = false;
		} 
		if (form.luogo.value=='-1'){
		 	message += "- Campo luogo obbligatorio per LP.\r\n";
	        formTest = false;
		}
		
		if (form.id_provincia_iscrizione_albo_vet_privato.value=='-1'){
			message += "- Campo provincia iscrizione albo obbligatorio per LP.\r\n";
		     formTest = false;
		}
		
		if (form.nr_iscrione_albo_vet_privato.value==''){
			message += "- Campo nr. iscrizione albo veterinari obbligatorio per LP.\r\n";
		     formTest = false;
		}
	}
    
    if (form.roleIdVam.value == '18')
    {
		if (form.id_provincia_iscrizione_albo_vet_privato_vam.value=='-1'){
			message += "- Campo provincia iscrizione albo obbligatorio per LP su VAM.\r\n";
		     formTest = false;
		}
		
		if (form.nr_iscrione_albo_vet_privato_vam.value==''){
			message += "- Campo nr. iscrizione albo veterinari obbligatorio per LP su VAM.\r\n";
		     formTest = false;
		}
	}
    
    if (form.roleIdbdu.value == '37'){
    	if (form.id_provincia_iscrizione_albo_vet_privato.value=='-1'){
			message += "- Campo provincia iscrizione albo obbligatorio per Utente Unina.\r\n";
		     formTest = false;
		}
		
		if (form.nr_iscrione_albo_vet_privato.value==''){
			message += "- Campo nr. iscrizione albo veterinari obbligatorio per Utente Unina.\r\n";
		     formTest = false;
		}
    }
    
    
    
    if (form.roleIdVam.value!='-1' ){
    	valido=true;
    	var cont=0;
    	var lista_cliniche = document.getElementById('clinicaId');
    	for (var c=0;c<lista_cliniche.length;c++){
    		var el = lista_cliniche[c];
    		if (el.selected){
    			if (el.value=='-1')
    				valido=false;
    			cont=cont+1;
    		}
    	}    	
    	if (valido==false){
    		if (cont==1){
    			message += "- Selezionare almeno una clinica.\r\n"; 
    		} else {
    			message += "- Controllare le cliniche selezionate.\r\n"; 
    		}
	    	formTest = false;
    	}	
    }    
    

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
    else if ((  trim(form.codiceFiscale.value).length != 16  )&& (  trim(form.codiceFiscale.value).length != 11)) {
    	message += "- Il codice fiscale deve essere di 16 caratteri o di 11 in caso di partita iva.\r\n";
        formTest = false;
    }
    if ((  trim(form.email.value) == "")) {
        message += "- Campo email obbligatorio.\r\n";
        formTest = false;
    }
    
    var controllo=validateEmail(form.email.value);
 	if (!controllo)
 	{
 		alert("Il campo email non e\' stato inserito nel formato corretto");
 	    return false;
 	}
 	
 	
//     if ((  trim(form.username.value) == "")) {
//         message += "- Campo username obbligatorio.\r\n";
//         formTest = false;
//     }
//     if ((  trim(form.username.value) != "")) {
//     	var usr = trim(form.username.value);
//     	if (usr.indexOf(" ")>-1){
// 	        message += "- Campo username non deve contenere spazi vuoti.\r\n";
// 	        formTest = false;
//         }
//     }
//     if (( trim(form.password.value) == "") || (  trim(form.password2.value) == "") || (form.password.value != form.password2.value)) {
//         message += "- Controllare che entrambe le password siano inserite correttamente.\r\n";
//         formTest = false;
//     }
    if (form.idAsl.value == "0") {
        message += "- Campo asl obbligatorio.\r\n";
        formTest = false;
    }
    if (form.roleIdImportatori!= null && form.roleIdImportatori.value=='3'){
	    if (form.id_importatore!= null && form.id_importatore.value=='-1'){
	    	 message += "- Campo Importatore Obbligatorio.\r\n";
	         formTest = false;
	    }
    }    
    if (form.roleIdbdu!= null && form.roleIdbdu.value=='31'){
	    if (form.canilebduId!= null && form.canilebduId.value=='-1'){
	    	 message += "- Campo Canile Obbligatorio.\r\n";
	         formTest = false;
	    }
    }
    
    
    if (form.roleIdGisa_ext!= null && form.roleIdGisa_ext.value=='10000008'){
    	
	    if (form.numRegStab!= null && form.numRegStab.value==''){
	    	 message += "- Campo Numero Registrazione Stabilimento Obbligatorio.\r\n";
	         formTest = false;
	    } 

	    verificaEsistenzaStabGiava(form.numRegStab.value);
	    
	 }
    
    if ((form.roleIdGisa!= null && form.roleIdGisa.value<=0) &&  (form.roleIdGisa_ext!= null && form.roleIdGisa_ext.value<=0) && (form.roleIdbdu!= null && form.roleIdbdu.value<=0) &&  (form.roleIdVam!= null && form.roleIdVam.value<=0) &&  (form.roleIdDigemon!= null && form.roleIdDigemon.value<=0)){
	    if (form.canilebduId!= null && form.canilebduId.value=='-1'){
	    	 message += "- Inserire almeno un ruolo.\r\n";
	         formTest = false;
	    }
    }
	
    if (formTest == false) {
      alert("La form non può essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
      return false;
    }
    else {
    	var controllo=controlloCF();
    	if (controllo.indexOf("ATTENZIONE")>-1){
    		alert(controllo);
    		return false;
    	}else
    		return true;
    }
  }

  function settaRoleDescription(select, endpoint){
	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('roleDescription' + endpoint).value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('roleDescription' + endpoint).value = '';
	  }
	  
  }

  function settaClinicaDescription(select){
	  if(select.options[ select.selectedIndex ]!=null && select.options[ select.selectedIndex ].value != -1){
		  if(document.getElementById('clinicaDescription')){
		  	document.getElementById('clinicaDescription').value = select.options[ select.selectedIndex ].text;
		  }
	  }
	  else{
		  if(document.getElementById('clinicaDescription')){
		  	document.getElementById('clinicaDescription').value = '';
		  }
	  }
	  
  }

  function settaCliniche(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {

		  //nascondi tutte le cliniche
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCliniche');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCliniche");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  //mostra le cliniche dell'asl selezionata
		  if(document.getElementById('gruppoCliniche' + asl)){
		  	document.getElementById('gruppoCliniche' + asl).style.display='';
		  }
		  if(document.getElementById('clinicaId')){
		  	document.getElementById('clinicaId').value = -1;
		  }
		  
	  }
	  else{

		  //mostra tutte le cliniche
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCliniche');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCliniche");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
  
/*  GISA NON e\' ANCORA SUPPORTATO
  function settaStrutturaDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  if(document.getElementById('strutturaDescription')){
		  	document.getElementById('strutturaDescription').value = select.options[ select.selectedIndex ].text;
		  }
	  }
	  else{
		  if(document.getElementById('strutturaDescription')){
		  	document.getElementById('strutturaDescription').value = '';
		  }
	  }
	  
  }
  
  function settaStrutture(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {

		  //nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableStrutture');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableStrutture");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  document.getElementById('gruppoStrutture' + asl).style.display='';
		  document.getElementById('strutturaId').value = -1;
		  
	  }
	  else{

		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableStrutture');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableStrutture");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
*/

  function settaCanileDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('canileDescription').value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('canileDescription').value = '';
	  }

	  
  }


  function settaCanileDescriptionBdu(select){
	


	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('canilebduDescription').value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('canilebduDescription').value = '';
	  }
	  
  }
  

  function settaImportatoriDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('importatoriDescription').value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('importatoriDescription').value = '';
	  }
	  
  }

  function settaCanili(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {
		  //nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanili');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanili");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  //mostra i canili dell'asl selezionata
		  //document.getElementById('gruppoCanili' + asl).style.display='';
		  //document.getElementById('canileId').value = -1;

// per la bdu
		//nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanilibdu');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanilibdu");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }
			
		  //mostra i canili dell'asl selezionata
		  if(document.getElementById('gruppoCanilibdu' + asl))
		  	document.getElementById('gruppoCanilibdu' + asl).style.display='';
		  document.getElementById('canilebduId').value = -1;



		  
		  
	  }
	  else{

		  //mostra tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanili');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanili");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }



		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanilibdu');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }

		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanilibdu");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
 
  function settaImportatori(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {

		  //nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableImportatori');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableImportatori");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  //mostra i canili dell'asl selezionata
		  if (document.getElementById('gruppoImportatori' + asl))
			  document.getElementById('gruppoImportatori' + asl).style.display='';
		  document.getElementById('id_importatore').value = -1;
		  
	  }
	  else{

		  //mostra tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableImportatori');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableImportatori");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
  function gestisciInfoSuap(){
	  
	  if ( $("#roleIdGisa_ext").val() == 10000003  ){
		  $(".suapDisp").show();
		  $('.suapDisp').css('display','');
		  alert($("#livelloSuap").val());
		  if ($("#livelloSuap").val()=='1')
			  {
			  alert("11111");
		  		$('.inputSuap').attr('required','required');
		  		$('.inputSuapII').removeAttr('required');
		  
			  }
		  else
			  {
			  alert("2222");
			  $('.inputSuap').attr('required','required');
		  		$('.inputSuapII').attr('required','required');
			  }
	  }
	  else{
		  $(".suapDisp").hide();
		  $('.suapDisp').css('display','none');
		  $('.inputSuap').removeAttr("required");
		  $('.inputSuapII').removeAttr("required");
		  
		  
	  }
  }
  
  
  
  function gestisciInfoGestoreStabilimento(){
	  
	  if ( $("#roleIdGisa_ext").val() == 10000004 || $("#roleIdGisa_ext").val() == 1000005  || $("#roleIdGisa_ext").val() == 10000008 ){
		  $(".gestoreDisp").show();
		  $('.gestoreDisp').css('display','');
		  $('.inputGestore').attr('required','required');
	  }
	  else{
		  $(".gestoreDisp").hide();
		  $('.gestoreDisp').css('display','none');
		  $('.inputGestore').removeAttr("required");
	  }
	  
	  if ($("#roleIdGisa_ext").val() == 10000008 ){
		  $(".warningGiava").show();
		  $('.warningGiava').css('display','');
	  }
	  else{
		  $(".warningGiava").hide();
		  $('.warningGiava').css('display','none');
	  }
	  
  }
  function gestisciGestoreAcque()
  {
	  if ( $("#roleIdGisa_ext").val() == 10000006 ){
		  $(".rigaGestoreAcque").show();
		  $(".rigaComuneGestoreAcque").show();
	  }
	  else{
		  $(".rigaGestoreAcque").hide();
		  $(".rigaComuneGestoreAcque").hide();
		  document.getElementById('gestore').value='-1';
		  document.getElementById('comuneGestore').value='-1';
	
      }
  }
  
  function gestisciApicoltore()
  {
	 
	  if ( $("#roleIdGisa_ext").val() == 10000002)
		  $(".rigaTipoAttivitaApicoltore").show();
	  else
		  $(".rigaTipoAttivitaApicoltore").hide();

	  
	 if ( $("#roleIdGisa_ext").val() == 10000001 || ($("#roleIdGisa_ext").val() == 10000002 && $("#tipoAttivitaApicoltoreC").is(':checked'))){
		  $(".rigaDelegatoApicoltore").show();
	  }
	  else{
		  $(".rigaDelegatoApicoltore").hide();
		  document.getElementById('piva').value='';
	
      }
	 
	 if ( $("#roleIdGisa_ext").val() == 10000002){
		  $(".rigaComuneApicoltore").show();
		  $(".rigaIndirizzoApicoltore").show();
		  $(".rigaCapIndirizzoApicoltore").show();
	 }
	  else {
		  $(".rigaComuneApicoltore").hide();
		  $(".rigaIndirizzoApicoltore").hide();
		  $(".rigaCapIndirizzoApicoltore").hide();
		  document.getElementById('comuneApicoltore').value='-1';
		  document.getElementById('indirizzoApicoltore').value='';
		  document.getElementById('capIndirizzoApicoltore').value='';
	 }
	  
  }
  
  function gestisciTrasportatore()
  {
	 
	 if ( $("#roleIdGisa_ext").val() == 10000004){
		  $(".rigaComuneTrasportatore").show();
		  $(".rigaIndirizzoTrasportatore").show();
		  $(".rigaCapIndirizzoTrasportatore").show();
	 }
	  else {
		  $(".rigaComuneTrasportatore").hide();
		  $(".rigaIndirizzoTrasportatore").hide();
		  $(".rigaCapIndirizzoTrasportatore").hide();
		  document.getElementById('comuneTrasportatore').value='-1';
		  document.getElementById('indirizzoTrasportatore').value='';
		  document.getElementById('capIndirizzoTrasportatore').value='';
	 }
	  
  }

  function gestisciCanili(){

	  //se e\' selezionato il ruolo "Utente Canile" per l'endpoint Canina 
	  //mostra la sezione relativa ai canili, 
	  //altrimenti nascondila
	  
	  if ( $("#roleIdCanina").val() == 31 ){
		  $(".rigaCanili").show();
	  }
	  else{
		  $(".rigaCanili").hide();
		  $("#canileId").val(-1);
		  $("#canileDescription").val("");

		  
	  }


	//  alert($("#roleIdbdu").val());
	  if ( $("#roleIdbdu").val() == 31 ){
		  $(".rigaCanilibdu").show();
	  }
	  else{
		  $(".rigaCanilibdu").hide();
		  $("#canilebduId").val(-1);
		  $("#canilebduDescription").val("");

  }
	  
	  if ( $("#roleIdbdu").val() == 24 || $("#roleIdbdu").val() == 37  ){
		  $(".rigaVetPRivbdu").show();
	  }
	  else{
		  $(".rigaVetPRivbdu").hide();
	
      }
	  
	 
	  
	  if( $("#roleIdbdu").val() == 24)
	  {
		  $("#rigaAutorizzazione").show();
		  $("#rigaLuogo").show();
		  
	  }
	  else
	  {
		  $("#rigaAutorizzazione").hide();
		  $("#rigaLuogo").hide();
	  }
	  
	  if( $("#roleIdGisa_ext").val() == 10000006)
	  {
		  $("#rigaGestore").show();
		  $("#rigaComuneGestore").show();
		  
	  }
	  else
	  {
		  $("#rigaGestore").hide();
		  $("#rigaComuneGestore").hide();
	  }
	  
	  if( $("#roleIdGisa_ext").val() == 10000001)
	  {
		  $("#rigaDelegatoApicoltore").show();
		  
	  }
	  else
	  {
		  $("#rigaDelegatoApicoltore").hide();
	  }
	          
	  if( $("#roleIdGisa_ext").val() == 10000006)
	  {
		  $("#rigaGestori").show();
		  $("#rigaComuniGestori").show();
	  }
	  else
	  {
		  $("#rigaGestori").hide();
		  $("#rigaComuniGestori").hide();
	  }
	  
	  
	  
	  if ( $("#roleIdVam").val() == 18 ){
		  $(".rigaVetPRivVam").show();
	  }
	  else{
		  $(".rigaVetPRivVam").hide();
	  }
  }



  function gestisciImportatori(){

	  //se e\' selezionato il ruolo "Utente Canile" per l'endpoint Canina 
	  //mostra la sezione relativa ai canili, 
	  //altrimenti nascondila
	  
	  if ( $("#roleIdImportatori").val() == 3 ){
		  $(".rigaImportatori").show();
	  }
	  else{
		  $(".rigaImportatori").hide();
		  $("#id_importatore").val(-1);
		  $("#importatoriDescription").val("");
	  }

  }
  

  function svuotaData(input){
		input.value = '';
  }
	

  function verificaEsistenzaStabGiava(numRegistrazione)
  {
  	DwrGUC.verificaEsistenzaStabGiava(numRegistrazione,{callback:verificaEsistenzaStabGiavaCallback,async:false});
  }
  function verificaEsistenzaStabGiavaCallback(value)
  {
	  if (value){ 
	    	 message += "- Il valore inserito nel campo Numero Registrazione Stabilimento risulta associato ad un altro utente per lo stesso ruolo.\r\n";
	         formTest = false;
	    }  
	  }
  
</script>

<div id="content" align="center">

	<div align="center">
		<a href="<%=ApplicationProperties.getProperty("SITO_GISA")%>/modulistica/modulogestionecredenzialiguc.doc" target="_new" style="margin: 0px 0px 0px 50px"><img src="images/download.png" height="18px" width="18px" />Modulo Richiesta Credenziali</a>
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
	</div>

	<h4 class="titolopagina">Anagrafica Utente</h4>
 	

<%  TreeSet<String> listaEndpoint = new TreeSet<String>();
    for(GUCEndpoint endpoint : GUCEndpoint.values()){ 
    	listaEndpoint.add(endpoint.toString());
    }
%>

<form name="addUser" action="guc.Add.us" onSubmit="return checkForm(this);" method="post">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
	  <th colspan="2"><strong>Contatto</strong></th>
	</tr>
	<tr>
	  <td class="formLabel">Nome</td>
	  <td><input type="text" name="nome" value="${UserRecord.nome}"/><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Cognome</td>
	  <td><input type="text" name="cognome" value="${UserRecord.cognome}"/><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Codice fiscale</td>
	  <td><input pattern="[A-Za-z]{6}[0-9]{2}[A-Za-z]{1}[0-9]{2}[A-Za-z]{1}[0-9]{3}[A-Za-z]{1}" type="text" name="codiceFiscale" id="codiceFiscale" value="${UserRecord.codiceFiscale}" maxlength="16" onKeyUp="this.value = this.value.toUpperCase();"/><font color=red>*</font></td>
	</tr>
	
	<tr>
	  <td class="formLabel">E-mail</td>
	  <td>  
	  <input type="text" name="email" value="${UserRecord.email}" /><font color=red>*</font>
	</tr>
	
	<tr>
	  <td class="formLabel">Telefono</td>
	  <td>  
	  <input type="text" name="telefono" value="${UserRecord.telefono}"/>
	</tr>
	
	
	<tr>
	  <td class="formLabel">Note</td>
	  <td><textarea name="note" rows="5" cols="50" >${UserRecord.note}</textarea></td>
	</tr>
	
	
<!-- 	<tr> -->
<!-- 	  <th colspan="2"><strong>Credenziali</strong></th> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 	  <td class="formLabel">Username</td> -->
<!-- 	  <td> -->
<%-- 	    <input type="text" name="username" value="${UserRecord.username}"><font color=red>*</font> --%>
<!-- 	  </td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 	  <td class="formLabel">Password</td> -->
<!-- 	  <td> -->
	  
<!--  <div id="policyPassword"> -->
<!--  <input type="password" class="password" name="password" id="password" maxlength="15" onmouseover="show()" onmouseout="hide()"/> <font color=red>*</font> -->
<!--  <div align="right" id="policyForm" style="display:none">  -->
<!--  <font size="1px"> -->
<!--  <ul class="helper-text"> -->
<!--  <li class="length">Lunghezza 10-15 caratteri.</li> -->
<!--  <li class="lowercase">Contiene almeno una minuscola.</li> -->
<!--  <li class="uppercase">Contiene almeno una maiuscola.</li> -->
<!--  <li class="special">Contiene almeno un numero.</li> -->
<!--  </ul> -->
<!--  </font> -->
<!--  </div> -->
<!--  </div> -->
<!-- </td> -->
<!-- 	</tr> -->
	
<!-- 	<tr> -->
<!-- 	  <td class="formLabel">Password (di nuovo)</td> -->
<!-- 	  <td><input type="password" name="password2" id="password2"><font color=red>*</font></td> -->
<!-- 	</tr> -->

<!-- <tr><td></td><td> -->
<!--  <div class="gender"> -->
<!--     <input type="radio" value="None" id="male" name="gender" checked onClick="mostraNascondi(this)"/> -->
<!--     <label for="male" class="radio" chec>Nascondi</label> -->
<!--     <input type="radio" value="None" id="female" name="gender" onClick="mostraNascondi(this)"/> -->
<!--     <label for="female" class="radio">Mostra</label> -->
<!--    </div>  -->
<!-- </td></tr> -->

<!-- 	</table> -->
<%-- <%@ include file="../cambiopassword/policyPassword.jsp" %> --%>
	
<jsp:include page="./edit_profilo_include.jsp"/>
	
<br> 
<input type="checkbox" checked name="inviomail" value="si"/>Invio notifica mail<br/>
<input type="submit" value="Inserisci">
</form>


<script>
	function extOption(ckb){
		if(ckb.checked){
			ckb.value="true";
			document.getElementById('hidden_'+ckb.name).value="true";
		} else {
			ckb.value="false";
			document.getElementById('hidden_'+ckb.name).value="false";
		}		
	}
	
	function abilitaExtOpt(select,endpoint,opt){
		if (opt==-1){
			var elements = document.getElementsByClassName(endpoint);
			for (var i=0; i<elements.length;i++){
				var e = elements[i];
				e.value="false";
				e.disabled="disabled";
				e.checked="";
			}
			elements = document.getElementsByClassName('hidden_'+endpoint);
			for (var i=0; i<elements.length;i++){
				var e = elements[i];
				e.value="false";
			}
		} else {
			var s = opt.replace(/££/g, '"');
			var myjson = JSON.parse(s);
  			for (var property in myjson){
  				document.getElementById(property).disabled="";
  				if (myjson[property]=="false"){
  					document.getElementById(property).value="false";
  					document.getElementById(property).checked="";
  					document.getElementById('hidden_'+property).value="false";
  				} else {
  					document.getElementById(property).value="true";
  					document.getElementById(property).checked="checked";
  					document.getElementById('hidden_'+property).value="true";
  				}
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

<script>
gestisciInfoSuap();
</script>
</div>
