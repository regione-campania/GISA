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


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script   src="js/jquery-ui.js"></script>




<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />


<jsp:useBean id="UserRecord" class="it.us.web.bean.guc.Utente" scope="request"/>
<style>
input,label,a,.details,.pagedlist{text-transform:uppercase !important;}
.menutabs-td,.menutabs-th,.trails,.submenuItemUnselected,.sidetab-right,.sidetab-right-sel,.dettaglioTabella,
.odd,.even,.cg-DivItem,.submenuItemSelected{text-transform:uppercase !important;}

</style>
<script type="text/javascript">

  function trim(str){
	    return str.replace(/^\s+|\s+$/g,"");
  } 
    
  function abilitaCampiBDU(form,endpoint) 
  {
	  if(endpoint=='bdu')
	  {
	  		if (form.roleIdbdu.value == '24' || form.roleIdbdu.value == '37')
	  		{
		  		if ( form.id_provincia_iscrizione_albo_vet_privato!=null)
			  	{
		  			form.id_provincia_iscrizione_albo_vet_privato.disabled = "";
			  	}
		  		if(form.nr_iscrione_albo_vet_privato!=null)
			  	{
			  		form.nr_iscrione_albo_vet_privato.disabled = "";
			  	}
		   		if(form.id_provincia_iscrizione_albo_vet_privato!=null)
			   	{
		 	 		form.id_provincia_iscrizione_albo_vet_privato.value = "-1";
			   	}
		  		if(form.nr_iscrione_albo_vet_privato!=null)
			  	{
		  			form.nr_iscrione_albo_vet_privato.value = ""; 
			  	}
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
		  		if ( form.id_provincia_iscrizione_albo_vet_privato_vam!=null)
			  	{
		  			form.id_provincia_iscrizione_albo_vet_privato_vam.disabled = "";
			  	}
		  		if(form.nr_iscrione_albo_vet_privato_vam!=null)
			  	{
			  		form.nr_iscrione_albo_vet_privato_vam.disabled = "";
			  	}
		   		if(form.id_provincia_iscrizione_albo_vet_privato_vam!=null)
			   	{
		 	 		form.id_provincia_iscrizione_albo_vet_privato_vam.value = "-1";
			   	}
		  		if(form.nr_iscrione_albo_vet_privato_vam!=null)
			  	{
		  			form.nr_iscrione_albo_vet_privato.value_vam = ""; 
			  	}
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
	  			if ( form.Gisa_ext_access!=null)
	  			{
		  			form.Gisa_ext_access.disabled = "true";
	  			}
	  			if ( form.hidden_Gisa_ext_access!=null)
  				{
		  			form.hidden_Gisa_ext_access.value = "false";
  				}
	  		} 
	  		else 
	  		{
	  			if ( form.Gisa_ext_access!=null)
  				{
	  				form.Gisa_ext_access.disabled = false;
  				}
	  		}
      }
  }

  function getDataOdierna(){
	  var today = new Date();
	  var dd = today.getDate();
	  var mm = today.getMonth()+1; //January is 0!
	  var yyyy = today.getFullYear();
	  if(dd<10){
	      dd='0'+dd
	  } 
	  if(mm<10){
	      mm='0'+mm
	  } 
	  var dataOdierna = dd+'/'+mm+'/'+yyyy;
	  return dataOdierna;
	  }
  
  function giorni_differenza(data1,data2){
		
		anno1 = parseInt(data1.substr(6),10);
		mese1 = parseInt(data1.substr(3, 2),10);
		giorno1 = parseInt(data1.substr(0, 2),10);
		anno2 = parseInt(data2.substr(6),10);
		mese2 = parseInt(data2.substr(3, 2),10);
		giorno2 = parseInt(data2.substr(0, 2),10);

		var dataok1=new Date(anno1, mese1-1, giorno1);
		var dataok2=new Date(anno2, mese2-1, giorno2);

		differenza = dataok2-dataok1;    
		giorni_diff = new String(Math.ceil(differenza/86400000));
		//alert('diff');
		//alert(giorni_diff);
		return giorni_diff;
	}
  
  function checkForm(form) {
	  	var ids=[]; var j=0;
	    var f = document.getElementsByTagName('input');
	  
	  
	    formTest = true;
	    message = "";
	    
	    
	    var element =  document.getElementById('dataScadenza');
	    if (typeof(element) != 'undefined' && element != null)
	    {
	    	var dataOdierna = getDataOdierna();
	    	if (element.value == '' || element.value == null){
	    	  message += "- Data inizio validità obbligatoria per variazione profilo.\r\n";
		      formTest = false;
	      }
	     else if ((giorni_differenza(dataOdierna, element.value)<=0)){
	   	  message += "- La Data inizio validità deve essere successiva alla data odierna.\r\n";
		      formTest = false;
	     }
	    }
	    /*
	    if (form.roleIdGisa_ext.value == '10000006')
	    {
			if (form.gestore.value=='-1' || form.comuneGestore.value=='-1')
			{
			 	message += "- Campo 'Gestore Acque' e 'Comune Gestore Acque' obbligatorio.\r\n";
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
			
			if (form.id_provincia_iscrizione_albo_vet_privato!= null && form.id_provincia_iscrizione_albo_vet_privato.value=='-1'){
				message += "- Campo provincia iscrizione albo obbligatorio per LP.\r\n";
			     formTest = false;
			}
			
			if (form.nr_iscrione_albo_vet_privato!= null &&  form.nr_iscrione_albo_vet_privato.value==''){
				message += "- Campo nr. iscrizione albo veterinari obbligatorio per LP.\r\n";
			     formTest = false;
			}
		}
	    
	    if (form.roleIdbdu.value == '37'){
	    	if (form.id_provincia_iscrizione_albo_vet_privato.value=='-1'){
				message += "- Campo provincia iscrizione albo obbligatorio per Utente Unina.\r\n";
			     formTest = false;
			}
			
			if (form.nr_iscrione_albo_vet_privato != null &&  form.nr_iscrione_albo_vet_privato.value==''){
				message += "- Campo nr. iscrizione albo veterinari obbligatorio per Utente Unina.\r\n";
			     formTest = false;
			}
	    }
	    
	    
	    if (form.roleIdVam!=null && form.roleIdVam.value!='-1' && 1==2){
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
	    	else if (cont==0)
	    	{
	    		message += "- Selezionare almeno una clinica.\r\n"; 
	    		formTest = false;
	    	}
	    }     
	    
	    
	    if (document.getElementById("numRegStab")!=null && document.getElementById("gestoreDispId").style.display=="" && document.getElementById("numRegStab").value=="" )
	    	{
	    	message += "- Campo Numero Registrazione Stabilimento obbligatorio.\r\n";
	        formTest = false;
	    	}
	    
	    
	   
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
	    */
	    if (formTest == false) {
	      alert("La form non può essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
	      for (var k=0;k<ids.length;k++){
	    	  document.getElementById(ids[k]).disabled='disabled';
	      }
	      return false;
	    }
	    else {    	
	    	var ruoloGisa = 'GISA : ' + form.roleIdGisa.options[form.roleIdGisa.selectedIndex].text;
	    	if (form.roleIdGisa_ext.options!=null)
	    		var ruoloGisa_ext = 'GISA_EXT : ' + form.roleIdGisa_ext.options[form.roleIdGisa_ext.selectedIndex].text;  
	    	if (form.roleIdVam!=null && form.roleIdVam.options!=null)
	    	var ruoloVam = 'VAM : '  + form.roleIdVam.options[form.roleIdVam.selectedIndex].text;
	    	
	    	if (form.roleIdbdu.options!=null)
	    	var ruoloBdu = 'BDU : ' + form.roleIdbdu.options[form.roleIdbdu.selectedIndex].text;
	    	if (form.roleIdImportatori!=null && form.roleIdImportatori.options!=null)
	    	var ruoloImportatori = 'IMPORTATORI : '  + form.roleIdImportatori.options[form.roleIdImportatori.selectedIndex].text;	
	    	if (form.roleIdDigemon!=null && form.roleIdDigemon.options!=null)
	    	var ruoloDigemon = 'DIGEMON : '  +  form.roleIdDigemon.options[ form.roleIdDigemon.selectedIndex].text;
	    	
	    	var eles = [];
	    	var messExtOpt = '';
	    	var inputs = document.getElementsByTagName("input");
	    	for(var i = 0; i < inputs.length; i++) {
	    		var e = inputs[i];
	    	    if(e.name.indexOf('hidden_') == 0) {
	    	        var k = e.name.replace(/hidden_/g, '');
	    	        k = k.replace(/_/g, ' ');
					var v = e.value;
					messExtOpt = messExtOpt+'\n'+k+' : '+v;
	    	    }
	    	}
	    	
	    	var messRuoli = ruoloGisa+"\n"+ruoloGisa_ext+"\n"+ruoloVam+"\n"+ruoloBdu+"\n"+ruoloImportatori+"\n"+ruoloDigemon+"\n";

	    	if(confirm(/*'Impostati i seguenti valori dei ruoli : \r\n\r\n'+messRuoli+' '+messExtOpt+*/'\r\n\r\nSicuro di voler procedere alle modifiche?')){
	      		return form.submit();
	    	}
	    	else {
	    		for (var k=0;k<ids.length;k++){
	  	    	  document.getElementById(ids[k]).disabled='disabled';
	  	      	}
	    		return false;
	    	}
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

  



  function settaCanileDescription(select){
	  if(select.options[ select.selectedIndex ]!=null && select.options[ select.selectedIndex ].value != -1){
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
		  if(document.getElementById('gruppoImportatori' + asl))
		  	document.getElementById('gruppoImportatori' + asl).style.display='';
		  //document.getElementById('id_importatore').value = -1;
		  
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
		  if (document.getElementById('gruppoCanilibdu' + asl))
		  	document.getElementById('gruppoCanilibdu' + asl).style.display='';
		  //document.getElementById('canilebduId').value = -1;
 
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

  
  function gestisciInfoGestoreStabilimento(){
	  
	  if ( $("#roleIdGisa_ext").val() == 10000004 || $("#roleIdGisa_ext").val() == 1000005  ){
		  $(".gestoreDisp").show();
		  $('.gestoreDisp').css('display','');
		  $('.inputGestore').attr('required','required');
	  }
	  else{
		  $(".gestoreDisp").hide();
		  $('.gestoreDisp').css('display','none');
		  $('.inputGestore').removeAttr("required");
	  }
  }
  
  function gestisciInfoSuap(){
	  
	  if ( $("#roleIdGisa_ext").val() == 10000003  ){
		  $(".suapDisp").show();
		  $('.suapDisp').css('display','');
		  alert('valore : '+$("#livelloSuap").val());
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


  function gestisciGestoreAcque()
  {
	  if ( $("#roleIdGisa_ext").val() == 10000006){
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
	  
	  if ( $("#roleIdbdu").val() == 24 ||  $("#roleIdbdu").val() == 37 ){
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
	  
	  if ( $("#roleIdVam").val() == 18 ){
		  $(".rigaVetPRivVam").show();
	  }
	  else{
		  $(".rigaVetPRivVam").hide();
	  }
  }
  

  function svuotaData(input){
		input.value = '';
  }
  
  
  function resetIdCanileImportatore(){
	  document.getElementById('canilebduId').value = -1;
	  document.getElementById('id_importatore').value = -1;
  }
</script>

<body onLoad=" settaImportatori();gestisciImportatori();settaCanili(true); gestisciCanili();gestisciGestoreAcque();gestisciApicoltore();gestisciTrasportatore();">



<%=(UserRecord.getError()!=null && !"".equals(UserRecord.getError())) ? "<font color='red'>"+UserRecord.getError()+"</font>" :"" %>
<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
		<a href="guc.Detail.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/detail.gif" height="18px" width="18px" />Dettaglio Utente</a>
	 	<a href="guc.ToEnable.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/enable.gif" height="18px" width="18px" />Attiva/Disattiva Credenziali</a>
		<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px; display: none"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
	</div>
	
	<h4 class="titolopagina">ANAGRAFICA Utente</h4>
	
	<form name="editUser" action="guc.EditProfilo.us" onSubmit="return checkForm(this);" method="post">
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
  		<td>${UserRecord.codiceFiscale}</td>
	</tr>
		
	<tr>
	  <td class="formLabel">E-mail</td>
	  <td>${UserRecord.email}</td>
	</tr>
	
	<tr>
	  <td class="formLabel">Telefono</td>
	  <td>${UserRecord.telefono}</td>
	</tr>
	
	<tr>
  		<td class="formLabel">Note</td>
  	<td>	${UserRecord.note}</td>
	</tr>
	<tr>
  		<th colspan="2"><strong>Credenziali</strong></th>
	</tr>
	<tr>
  		<td class="formLabel">Username</td>
  		<td>
    	${UserRecord.username}
    	<input type="hidden" name="id" value="${UserRecord.id}" ></input>
    	<input type="hidden" name="oldUsername" value="${UserRecord.username}" ></input>
  		</td>
	</tr>
	
	</table>
	
	

<jsp:include page="./edit_profilo_include.jsp"/>


<input type="button" value="Aggiorna" onclick="javascript: checkForm(this.form);"/>

<script type="text/javascript">
	if (document.getElementById("roleIdbdu").value != '24' && document.getElementById("roleIdbdu").value != '37'){
		document.getElementById("id_provincia_iscrizione_albo_vet_privato").value = "-1";
		document.getElementById("nr_iscrione_albo_vet_privato").value = "";
		document.getElementById("id_provincia_iscrizione_albo_vet_privato").disabled = "disabled";
		document.getElementById("nr_iscrione_albo_vet_privato").disabled = "disabled";
	}
	
 	 if (document.getElementById("roleIdbdu").value != '24'){
 		document.getElementById("numAutorizzazione").value="";
 		document.getElementById("numAutorizzazione").disabled="disabled";
 	} 
 	

 	
 	
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
 		document.getElementById('roleId'+endpoint).disabled="";
 	}	
 	
//  	abilitaCampiBDU(document.forms[0]);

abilitaCampoAccess(document.editUser,'Gisa_ext');
</script>


</form>
</div>
</body>
 	
