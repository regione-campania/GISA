/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function costruisci_obj_rel_ateco_linea_attivita_per_codice_istat_callback(returnValue) {
		  campo_combo_da_costruire = returnValue [2];
		  //alert('Combobox destinazione : ' + campo_combo_da_costruire);
		  var select = document.getElementById(campo_combo_da_costruire); //Recupero la SELECT
	  
	  //Azzero il contenuto della seconda select
	  for (var i = select.length - 1; i >= 0; i--)
		  select.remove(i);
	
	  indici = returnValue [0];
	  valori = returnValue [1];
	  //Popolo la seconda Select
	  for(j =0 ; j<indici.length; j++){
		      //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		      var NewOpt = document.createElement('option');
		      NewOpt.value = indici[j]; // Imposto il valore
		      NewOpt.text = valori[j]; // Imposto il testo
		
		      //Aggiungo l'elemento optionLatitudine
		      try
		      {
		    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		      } catch(e){
		    	  select.add(NewOpt); // Funziona solo con IE
		      }
	  }
	}
	
	function costruisci_rel_ateco_attivita( campo_codice_fiscale, campo_combo_da_costruire ) {
		  // "costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"
		  //alert('Sono in : costruisci_rel_ateco_attivita');
		  //cod_istat_principale = document.getElementById("codiceFiscaleCorrentista").value;
		  cod_istat_principale = document.getElementById(campo_codice_fiscale).value;
		  //alert('Valore selezionato : ' + cod_istat_principale);
		  PopolaCombo.costruisci_obj_rel_ateco_linea_attivita_per_codice_istat(cod_istat_principale , campo_combo_da_costruire, costruisci_obj_rel_ateco_linea_attivita_per_codice_istat_callback)
	}
	
	function costruisci_combo_linea_attivita_onLoad(){
		costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );
		costruisci_rel_ateco_attivita('codice1',  'id_rel_1' );
		costruisci_rel_ateco_attivita('codice2',  'id_rel_2' );
		costruisci_rel_ateco_attivita('codice3',  'id_rel_3' );
		costruisci_rel_ateco_attivita('codice4',  'id_rel_4' );
		costruisci_rel_ateco_attivita('codice5',  'id_rel_5' );
		costruisci_rel_ateco_attivita('codice6',  'id_rel_6' );
		costruisci_rel_ateco_attivita('codice7',  'id_rel_7' );
		costruisci_rel_ateco_attivita('codice8',  'id_rel_8' );
		costruisci_rel_ateco_attivita('codice9',  'id_rel_9' );
		costruisci_rel_ateco_attivita('codice10', 'id_rel_10');
	}

	function abilita_codici_ateco_vuoti() {

		// Blocco di codice che disabilita tutti i div relativi ai codici ateco
		document.getElementById("div_codice1").style.display="none";
		document.getElementById("div_codice2").style.display="none";	
		document.getElementById("div_codice3").style.display="none";
		document.getElementById("div_codice4").style.display="none";	
		document.getElementById("div_codice5").style.display="none";
		document.getElementById("div_codice6").style.display="none";	
	    document.getElementById("div_codice7").style.display="none";
	    document.getElementById("div_codice8").style.display="none";
	    document.getElementById("div_codice9").style.display="none";

	    // Blocco di codice che abilita tutti i div che presentano un valore ateco
		if ( (document.getElementById("codice1").value  != "") )			document.getElementById("div_codice1").style.display="";	
		if ( (document.getElementById("codice2").value  != "") )			document.getElementById("div_codice2").style.display="";
		if ( (document.getElementById("codice3").value  != "") )			document.getElementById("div_codice3").style.display="";
		if ( (document.getElementById("codice4").value  != "") )			document.getElementById("div_codice4").style.display="";
		if ( (document.getElementById("codice5").value  != "") )			document.getElementById("div_codice5").style.display="";
		if ( (document.getElementById("codice6").value  != "") )			document.getElementById("div_codice6").style.display="";
		if ( (document.getElementById("codice7").value  != "") )			document.getElementById("div_codice7").style.display="";
		if ( (document.getElementById("codice8").value  != "") )			document.getElementById("div_codice8").style.display="";
		if ( (document.getElementById("codice9").value  != "") )			document.getElementById("div_codice9").style.display="";

		// Blocco di codice che abilita un div vuoto dopo l"ultimo inserito
		if ( (document.getElementById("codice1").value  != "") && (document.getElementById("codice2").value  == "") )
			document.getElementById("div_codice1").style.display="";	

		if ( (document.getElementById("codice2").value  != "") && (document.getElementById("codice3").value  == "") )
			document.getElementById("div_codice2").style.display="";

		if ( (document.getElementById("codice3").value  != "") && (document.getElementById("codice4").value  == "") )
			document.getElementById("div_codice3").style.display="";

		if ( (document.getElementById("codice4").value  != "") && (document.getElementById("codice5").value  == "") )
			document.getElementById("div_codice4").style.display="";

		if ( (document.getElementById("codice5").value  != "") && (document.getElementById("codice6").value  == "") )
			document.getElementById("div_codice5").style.display="";

		if ( (document.getElementById("codice6").value  != "") && (document.getElementById("codice7").value  == "") )
			document.getElementById("div_codice6").style.display="";

		if ( (document.getElementById("codice7").value  != "") && (document.getElementById("codice8").value  == "") )
			document.getElementById("div_codice7").style.display="";

		if ( (document.getElementById("codice8").value  != "") && (document.getElementById("codice9").value  == "") )
			document.getElementById("div_codice8").style.display="";

		if ( (document.getElementById("codice9").value  != "") && (document.getElementById("codice10").value  == "") )
			document.getElementById("div_codice9").style.display="";
		
	}

	function resetCodiciIstatSecondari(){

		  if ( (document.getElementById("codice1").value  != "") ){
			  document.getElementById("codice1").value="";
			  document.getElementById("cod1").value="";
		  } 
		  if ( (document.getElementById("codice2").value  != "") ){
			  document.getElementById("codice2").value="";
			  document.getElementById("cod2").value="";
		  }
		  if ( (document.getElementById("codice3").value  != "") ){
			  document.getElementById("codice3").value="";
			  document.getElementById("cod3").value="";
		  }
		  if ( (document.getElementById("codice4").value  != "") ){
			  document.getElementById("codice4").value="";
			  document.getElementById("cod4").value="";
		  }
		  if ( (document.getElementById("codice5").value  != "") ){
			  document.getElementById("codice5").value="";
			  document.getElementById("cod5").value="";
		  }
		  if ( (document.getElementById("codice6").value  != "") ){
			  document.getElementById("codice6").value="";
			  document.getElementById("cod6").value="";
		  }
		  if ( (document.getElementById("codice7").value  != "") ){
			  document.getElementById("codice7").value="";
			  document.getElementById("cod7").value="";
		  }
		  if ( (document.getElementById("codice8").value  != "") ){
			  document.getElementById("codice8").value="";
			  document.getElementById("cod8").value="";
		  }
		  if ( (document.getElementById("codice9").value  != "") ){
			  document.getElementById("codice9").value="";
			  document.getElementById("cod9").value="";
		  }
		  if ( (document.getElementById("codice10").value  != "") ){
			  document.getElementById("codice10").value="";
			  document.getElementById("cod10").value="";
		  }
		  
		  document.getElementById("div_codice1").style.display="none";
		  document.getElementById("div_codice2").style.display="none";	
		  document.getElementById("div_codice3").style.display="none";
		  document.getElementById("div_codice4").style.display="none";	
		  document.getElementById("div_codice5").style.display="none";
		  document.getElementById("div_codice6").style.display="none";	
	      document.getElementById("div_codice7").style.display="none";
	      document.getElementById("div_codice8").style.display="none";
	      document.getElementById("div_codice9").style.display="none";
	      costruisci_combo_linea_attivita_onLoad();

	}



  indSelected = 0;
  orgSelected = 1;
  onLoad = 1;



  function mostraNextIndirizzo(ind){

  document.getElementById("indirizzo"+ind+"1").style.display="";
  document.getElementById("indirizzo"+ind+"2").style.display="";
  document.getElementById("indirizzo"+ind+"3").style.display="";
  document.getElementById("indirizzo"+ind+"4").style.display="";
  document.getElementById("indirizzo"+ind+"5").style.display="";
  document.getElementById("indirizzo"+ind+"6").style.display="";
  document.getElementById("indirizzo"+ind+"7").style.display="";
  document.getElementById("indirizzo"+ind+"8").style.display="";
  if(ind==2){
  	document.getElementById("indirizzo"+"2"+"button").style.display="none";
  	document.getElementById("indirizzo3button").style.display="";

  }else{

  	document.getElementById("indirizzo3button").style.display="none";
  	
  }

 }


  // MATTEO CARELLA - modifiche alla doCheck per il funzionamento della finestra modale di locking
  function doCheck(form){
	  if(form.dosubmit.value=="false") {
		  loadModalWindow();
		  return true;
	  }

	  else {
		  if(checkForm(form)) {
			  loadModalWindow();
			  return true;
		  }
		  else
			  return false;
		  
	  }
  }

function abilitaDistributoriCampi(){

	document.getElementById("locali_button").disabled=true;
document.getElementById("codiceFiscaleCorrentista").value="47.99.20";
document.getElementById("alertText").value="Commercio effettuato per mezzo di distributori automatici";
document.getElementById("codiceFiscaleCorrentista").onchange();
  elm1 = document.getElementById("tipoVeicolo1"); //Nome
   elm2 = document.getElementById("targaVeicolo1"); //Cognome
  /* elm3 = document.getElementById("codiceCont1"); // Nome (Organization)*/
   elm4 = document.getElementById("tipoStruttura1");
   elm5 = document.getElementById("addressLine");
   elm6 = document.getElementById("prov1");
   elm7 = document.getElementById("labelCap");
   elm8 = document.getElementById("stateProv1");
   elm9 = document.getElementById("latitude1");
   elm10 = document.getElementById("longitude1");
 
   elm1.style.color="#cccccc";
    document.addAccount.tipoVeicolo.style.background = "#cccccc";
    document.addAccount.tipoVeicolo.value = "";
    document.addAccount.tipoVeicolo.disabled = true;
    
    elm2.style.color="#cccccc";
    document.addAccount.targaVeicolo.style.background = "#cccccc";
    document.addAccount.targaVeicolo.value = "";
    document.addAccount.targaVeicolo.disabled = true;

/*    elm3.style.color="#cccccc";
    document.addAccount.codiceCont.style.background = "#cccccc";
    document.addAccount.codiceCont.value = "";
    document.addAccount.codiceCont.disabled = true;*/
    
       elm4.style.color="#cccccc";
    document.getElementById("prov12").disabled = true;
            
    elm5.style.color="#cccccc";
    document.addAccount.addressline1.style.background = "#cccccc";
    document.addAccount.addressline1.value = "";
    document.addAccount.addressline1.disabled = true;
    
    elm6.style.color="#cccccc";
    document.getElementById("prov12").disabled = true;
    //document.getElementById("prov12").selectedIndex=0;
    
    elm7.style.color="#cccccc";
    document.addAccount.addresszip.style.background = "#cccccc";
    document.addAccount.addresszip.value = "";
    document.addAccount.addresszip.disabled = true;
    
    elm8.style.color="#cccccc";
    document.addAccount.address3state.style.background = "#cccccc";
    document.addAccount.address3state.value = "";
    document.addAccount.address3state.disabled = true;
    elm9.style.color="#cccccc";
    document.addAccount.address3latitude.style.background = "#cccccc";
    document.addAccount.address3latitude.value = "";
    document.addAccount.address3latitude.disabled = true;
    
    elm10.style.color="#cccccc";
    document.addAccount.address3longitude.style.background = "#cccccc";
    document.addAccount.address3longitude.value = "";
    document.addAccount.address3longitude.disabled = true;
    
    elm4.style.color="#cccccc";
    document.addAccount.TipoStruttura.style.background = "#cccccc";
    document.addAccount.TipoStruttura.value = "";
    document.addAccount.TipoStruttura.disabled = true;
            
    
   
    document.getElementById("prov12").disabled = true;
    document.addAccount.check.value = "es";
    document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
     
   elm5 = document.getElementById("indirizzo1");
   elm6 = document.getElementById("prov2");
   elm7 = document.getElementById("cap1");
   elm8 = document.getElementById("stateProv2");
   elm9 = document.getElementById("latitude2");
   elm10 = document.getElementById("longitude2");
   
  	 elm5.style.color="#cccccc";
    document.addAccount.indirizzo12.style.background = "#cccccc";
    document.addAccount.indirizzo12.value = "";
    document.addAccount.indirizzo12.disabled = true;
    
    elm6.style.color="#cccccc";
    document.getElementById("prov").disabled = true;
   //peppe document.getElementById("prov").selectedIndex=0;
    
    elm7.style.color="#cccccc";
    document.addAccount.cap.style.background = "#cccccc";
    document.addAccount.cap.value = "";
    document.addAccount.cap.disabled = true;
    
    elm8.style.color="#cccccc";
    
    elm9.style.color="#cccccc";
    document.addAccount.address2latitude.style.background = "#cccccc";
    document.addAccount.address2latitude.value = "";
    document.addAccount.address2latitude.disabled = true;
    
    elm10.style.color="#cccccc";
    document.addAccount.address2longitude.style.background = "#cccccc";
    document.addAccount.address2longitude.value = "";
    document.addAccount.address2longitude.disabled = true;
    
    
	/*elm3.style.color="#cccccc";
    document.addAccount.codiceCont.style.background = "#cccccc";
    document.addAccount.codiceCont.value = "";
    document.addAccount.codiceCont.disabled = true;*/
    
 	document.getElementById("prov").disabled = true;
    document.addAccount.check.value = "autoveicolo";
    document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO

    elm1=document.addAccount.TipoLocale;
    elm2=document.addAccount.address4city1;
    elm3=document.addAccount.address4line1;
    elm4=document.addAccount.address4latitude1;
    elm5=document.addAccount.address4longitude1;
    elm6=document.addAccount.address4zip1;
    elm7=document.addAccount.address4state1;
    if(elm1!=null){

    elm1.style.color="#cccccc";
    elm1.style.background = "#cccccc";

    elm1.value = "";
    elm1.disabled = true;
    }
    if(elm2!=null){
        
    elm2.style.color="#cccccc";
    elm2.style.background = "#cccccc";
    elm2.value = "";
    elm2.disabled = true;
    }

    if(elm3!=null){

    elm3.style.color="#cccccc";
    elm3.style.background = "#cccccc";
    elm3.value = "";
    elm3.disabled = true;
    }
    if(elm4!=null){
        
    elm4.style.color="#cccccc";
    elm4.style.background = "#cccccc";
    elm4.value = "";
    elm4.disabled = true;
    }
    if(elm5!=null){
        
    elm5.style.color="#cccccc";
    elm5.style.background = "#cccccc";
    elm5.value = "";
    elm5.disabled = true;
    }
    if(elm6!=null){
        
    elm6.style.color="#cccccc";
    elm6.style.background = "#cccccc";
    elm6.value = "";
    elm6.disabled = true;
    }
    if(elm7!=null){
        
    elm7.style.color="#cccccc";
    elm7.style.background = "#cccccc";
    elm7.value = "";
    elm7.disabled = true;
    }

    elm1=document.addAccount.TipoLocale2;
    elm2=document.addAccount.address4city2;
    elm3=document.addAccount.address4line12;
    elm4=document.addAccount.address4latitude2;
    elm5=document.addAccount.address4longitude2;
    elm6=document.addAccount.address4zip2;
    elm7=document.addAccount.address4state2;
    if(elm1!=null){

    elm1.style.color="#cccccc";
    elm1.style.background = "#cccccc";
    elm1.value = "";
    elm1.disabled = true;
    }
    if(elm2!=null){
        
    elm2.style.color="#cccccc";
    elm2.style.background = "#cccccc";
    elm2.value = "";
    elm2.disabled = true;
    }
    if(elm3!=null){
        
    elm3.style.color="#cccccc";
    elm3.style.background = "#cccccc";
    elm3.value = "";
    elm3.disabled = true;
    }
    if(elm4!=null){
        
    elm4.style.color="#cccccc";
    elm4.style.background = "#cccccc";
    elm4.value = "";
    elm4.disabled = true;
    }
    if(elm5!=null){
        
    elm5.style.color="#cccccc";
    elm5.style.background = "#cccccc";
    elm5.value = "";
    elm5.disabled = true;
    }
    if(elm6!=null){
        
    elm6.style.color="#cccccc";
    elm6.style.background = "#cccccc";
    elm6.value = "";
    elm6.disabled = true;
    }
    if(elm7!=null){
        
    elm7.style.color="#cccccc";
    elm7.style.background = "#cccccc";
    elm7.value = "";
    elm7.disabled = true;
    }

    elm1=document.addAccount.TipoLocale3;
    elm2=document.addAccount.address4city3;
    elm3=document.addAccount.address4line13;
    elm4=document.addAccount.address4latitude3;
    elm5=document.addAccount.address4longitude3;
    elm6=document.addAccount.address4zip3;
    elm7=document.addAccount.address4state3;

    if(elm1!=null){

     elm1.style.color="#cccccc";
    elm1.style.background = "#cccccc";
    elm1.value = "";
    elm1.disabled = true;
    }
    if(elm2!=null){
        
    elm2.style.color="#cccccc";
    elm2.style.background = "#cccccc";
    elm2.value = "";
    elm2.disabled = true;
    }
    if(elm3!=null){
        
    elm3.style.color="#cccccc";
    elm3.style.background = "#cccccc";
    elm3.value = "";
    elm3.disabled = true;
    }
    if(elm4!=null){
        
    elm4.style.color="#cccccc";
    elm4.style.background = "#cccccc";
    elm4.value = "";
    elm4.disabled = true;
    }
    if(elm5!=null){
        
    elm5.style.color="#cccccc";
    elm5.style.background = "#cccccc";
    elm5.value = "";
    elm5.disabled = true;
    }
    if(elm6!=null){
        
    elm6.style.color="#cccccc";
    elm6.style.background = "#cccccc";
    elm6.value = "";
    elm6.disabled = true;
    }
    if(elm7!=null){
        
    elm7.style.color="#cccccc";
    elm7.style.background = "#cccccc";
    elm7.value = "";
    elm7.disabled = true;
    }
    if( document.getElementById("aggiungialtrobutton")!=null)
    document.getElementById("aggiungialtrobutton").disabled="true";
    if( document.getElementById("aggiungialtrobutton2")!=null)
     document.getElementById("aggiungialtrobutton2").disabled="true";
}

function gestioneSedeLegale(tipoDest){

	if(tipoDest == "Autoveicolo" || tipoDest == "Distributori"){

		//document.getElementById("address1city2").style.display="block";
		document.getElementById("address1city").disabled=false;
		//document.getElementById("address1city1").style.display="none";
		//document.getElementById("address1city1").value="";
		//document.getElementById("address1city1").disabled=true;
		document.addAccount.address2city.value=-1;
		document.addAccount.address2state.value = "";
		document.getElementById("coord1button").disabled = true;
		if(tipoDest == "Autoveicolo"){
			document.getElementById("coord2button").disabled = false;
			document.getElementById("sl").innerHTML="<font color = 'red'>Inserire comune,ndirizzo,provincia,coordinate per sede legale</font>";
			document.getElementById("so").innerHTML="";
			document.getElementById("mob").innerHTML="<font color = 'red'>Inserire comune,ndirizzo,provincia,coordinate per sede mobile</font>";
			document.getElementById("loc1").innerHTML="<font color = 'red'>Inserire comune,ndirizzo,provincia,coordinate per locale1</font>";
		}
		else{
			document.getElementById("coord2button").disabled = true;
			document.getElementById("sl").innerHTML="<font color = 'red'>Inserire comune,ndirizzo,provincia,coordinate per sede legale</font>";
			document.getElementById("so").innerHTML="";
			document.getElementById("mob").innerHTML="";
			document.getElementById("loc1").innerHTML="";
		}
		document.getElementById("address1latitude").disabled=false;
		document.getElementById("address1longitude").disabled=false;
		document.getElementById("coordbutton").disabled = false;
		
		document.addAccount.address1state.value = document.addAccount.address1state1.value;
		document.addAccount.address3state.value = document.addAccount.address1state1.value;
		document.addAccount.address2state.disabled = true;
		if(document.addAccount.address1state1.value!="")
			document.addAccount.address1state.disabled=true;
		
  		document.getElementById('provs').style.display = "" ; 
   		document.getElementById('provs').disabled = false ;

   		document.getElementById('address1city').style.display = "none" ; 
   		document.getElementById('address1city').disabled = true ;
				
	}
	else if(tipoDest == "Es. Commerciale"){
		document.getElementById('provs').style.display = "none" ; 
   		document.getElementById('provs').disabled = true ;

   		document.getElementById('address1city').style.display = "" ; 
   		document.getElementById('address1city').disabled = false ;
		//document.getElementById("address1city2").style.display="block";
		//document.getElementById("address1city2").style.display="none";
		document.getElementById("address1city").value="";
		//document.getElementById("address1city").disabled=false;
		document.getElementById("address1city").disabled=false;
		
		document.getElementById("address1latitude").value="";
		document.getElementById("address1latitude").disabled=false;
		document.getElementById("address1longitude").value="";
		document.getElementById("address1longitude").disabled=false;
		document.getElementById("coordbutton").disabled = false;
		
		document.addAccount.address1state.value = "";
		document.addAccount.address1state.disabled=false;
		document.addAccount.address2state.disabled = false;
		document.getElementById("coord1button").disabled = false;
		document.getElementById("coord2button").disabled = true;
		document.getElementById("sl").innerHTML="";
		document.getElementById("so").innerHTML="<font color = 'red'>Inserire comune,ndirizzo,provincia,coordinate per sede operativa</font>";
		document.getElementById("mob").innerHTML="";
		document.getElementById("loc1").innerHTML="";
		
			
	}
}


function verificaEsistenzaDIA()
{

	if (document.forms[0].cancel.value=='false'){
	tipoAttivita = document.addAccount.tipoDest ;
	if (tipoAttivita[0].checked)
		{
		tipoAttivita = 'Es. Commerciale';
		
		}
	
	if (tipoAttivita[1].checked)
	{
	tipoAttivita = 'Autoveicolo';
	
	}
	if (tipoAttivita[2].checked)
	{
	tipoAttivita = 'Distributori';
	
	}
	
	ragioneSociale = document.addAccount.name.value ;
	partitaIva = document.addAccount.partitaIva.value ;
	codFiscale = document.addAccount.codiceFiscale.value ;
	if (tipoAttivita = 'Es. Commerciale')
	{
		type = 5 ;
		citta		= document.addAccount.address2city.value ;
		indirizzo 	= document.addAccount.address2line1.value ;
	}
	else
	{
		if (tipoAttivita = 'Autoveicolo')
		{
			type = 7 ;
			citta 		= document.addAccount.address3city.value ;
			indirizzo 	= document.addAccount.address3line1.value ;
		}
		else
		{
			if (tipoAttivita = 'Distributori')
			{
				type = 1 ;
				citta 		= document.addAccount.address1city.value ;
				indirizzo 	= document.addAccount.address1line1.value ;
			}
		}

	}
	
	PopolaCombo.checkEsistenzaImpresa(ragioneSociale ,partitaIva ,citta,indirizzo,codFiscale,1,type,{callback:verificaEsistenzaDIACallback,async:false }) ;
	if( document.addAccount.dosubmit.value=='true')
		return true;
	return false;
	}
	return true;
	}

function verificaEsistenzaDIACallback(value)	
{

	if (value == 'false')
	{
		 if (doCheck(document.addAccount)==true)
		 {
			 
			 document.addAccount.dosubmit.value='true';
			 return true ;
		}
		 document.addAccount.dosubmit.value='false';
		 return false ;
	}
	else
	{
		if (confirm('Impresa esistente , sicuro di voler salvare ? ')==true)
		{
			if ( doCheck(document.addAccount)==true)
			{
				document.addAccount.dosubmit.value='true';
				 return true ;
			}
			document.addAccount.dosubmit.value='false';
			return false;
		}
	}
	
}
function doCheck(form){
	 
	  if(checkForm(form)==true) {
		  loadModalWindow();
		  return true;
	  }
	  else
		  return false;
	  

}
//
//function verificaEsistenzaDIA()
//{
//
//	tipoAttivita = document.addAccount.tipoDest.value ;
//	ragioneSociale = document.addAccount.name.value ;
//	partitaIva = document.addAccount.partitaIva.value ;
//	codFiscale = document.addAccount.codiceFiscale.value ;
//	if (tipoAttivita = 'Es. Commerciale')
//	{
//		type = 5 ;
//		citta		= document.addAccount.address2city.value ;
//		indirizzo 	= document.addAccount.address2line1.value ;
//	}
//	else
//	{
//		if (tipoAttivita = 'Autoveicolo')
//		{
//			type = 7 ;
//			citta 		= document.addAccount.address3city.value ;
//			indirizzo 	= document.addAccount.address3line1.value ;
//		}
//		else
//		{
//			if (tipoAttivita = 'Distributori')
//			{
//				type = 1 ;
//				citta 		= document.addAccount.address1city.value ;
//				indirizzo 	= document.addAccount.address1line1.value ;
//			}
//		}
//
//	}
//	
//	PopolaCombo.checkEsistenzaImpresa(ragioneSociale ,partitaIva ,citta,indirizzo,codFiscale,0,type,verificaEsistenzaDIACallback ) ;
//	
//	}
//
//function verificaEsistenzaDIACallback(value)
//{
//
//	if (value == 'false')
//	{
//		 if (doCheck(document.addAccount)==true)
//		 {
//			 document.addAccount.submit();
//		}
//	}
//	else
//	{
//		if (confirm('DIA esistente, sicuro di voler salvare? ')==true)
//		{
//			if ( doCheck(document.addAccount)==true)
//			{
//				 document.addAccount.submit();
//			}
//		}
//	}
//	
//}

function disabilitaDistributoriCampi(){

   document.getElementById("locali_button").disabled=false;

   document.getElementById("codiceFiscaleCorrentista").value="";
   document.getElementById("alertText").value="";
   document.getElementById("codiceFiscaleCorrentista").onchange();
}

function checkForm(form) {
formTest = true;
message = "";
alertMessage = "";


if(form.siteId.value=="-1"){
	  	message += "- Controllare di aver selezionato il campo A.S.L.\r\n";
    formTest = false;
}   

if (form.name.value==""){
    
      message += "- Ragione Sociale richiesta\r\n";
      formTest = false;
   
  }

if (form.no_piva.checked==false){
if (checkNullString(form.partitaIva.value)){
	  if (checkNullString(form.codiceFiscale.value)){
  	message += "- Partita IVA richiesta\r\n";
 	 formTest = false;
	  }
}

if (! checkNullString(form.partitaIva.value) && (form.partitaIva.value.length<11 && form.provenienzaIT.checked)){
	  
 	message += "- Partita IVA non Valida \r\n";
	 formTest = false;
	  
}

if (form.partitaIva.value.length>11 && form.provenienzaIT.checked){
	 
   	message += "- Partita IVA non Valida per provenienza ITALIA \r\n";
  	 formTest = false;
	  
 }
} else {
	 if ( form.codiceFiscale.value=="" || form.codiceFiscale.value.length<16 ){
			message += "- Codice Fiscale richiesto \r\n";
		  	 formTest = false;
	 }
}

  if (form.provenienzaEST.checked && form.country.value==-1){
	 	 
	   	message += "- Selezionare un PAESE in caso di provenienza estera. \r\n";
	  	 formTest = false;
		  
	 }

if (form.no_piva.checked==false){
if (form.partitaIva && form.partitaIva.value!="" && form.provenienzaIT.checked){
  	 //alert(!isNaN(form.address2latitude.value));
  		if ((orgSelected == 1)  ){
  			if (isNaN(form.partitaIva.value)){
   			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
   				 formTest = false;
   			}		 
  		}
	 } 
}

if (checkNullString(form.codiceFiscaleCorrentista.value)){
	  
  	message += "- Codice ATECO principale richiesto\r\n";
	formTest = false;
	
}
if(document.getElementById("tipoD").checked || document.getElementById("tipoD3").checked){
  
    if(document.getElementById("codiceFiscaleCorrentista").value=="47.99.20"){
   	 message += "- Per il codice ISTAT selezionato il tipo di attivit� deve essere \'Distributori\'.\r\n";
		 formTest = false;
    }  	 
    }
if (document.getElementById("codiceFiscaleCorrentista").value=="00.00.00"){
		message += "- Codice ISTAT principale 00.00.00 non valido. Selezionare un Codice ISTAT principale valido.\r\n";
		formTest = false;
	}
	var codici_ateco_list = document.getElementsByClassName("codiciatecolista");
	for (var i = 0; i < codici_ateco_list.length; ++i) {
	    var item = codici_ateco_list[i]; 
	    if (item.value!=null && item.value=="00.00.00"){
	    	message += item.id+" - 00.00.00 non valido. Selezionare un Codice Ateco valido.\r\n";
	    	formTest = false;
	    }
	}


if(form.dataPresentazione.value==""){
	  message += "- Data Presentazione D.I.A./Inizio Attivita\' e\' richiesto\r\n";
    formTest = false;
}  
if(form.source.value == 1){
	
	if(checkNullString(form.dateI.value)){
		message += "- Data inizio carattere temporanea richiesta\r\n";
		formTest = false;
	}
   	if(checkNullString(form.dateF.value)){
		message += "- Data fine carattere temporanea richiesta\r\n";
		formTest = false;
	}
}

if(form.stageId.value=="-1")
    {
	  message += "- Il Campo Servizio Competente e\' richiesto\r\n";
      formTest = false;
}  

if (checkNullString(form.codiceFiscaleRappresentante.value)){
    message += "- Codice Fiscale del rappresentante richiesto\r\n";
    formTest = false;
  }


if (checkNullString(form.cognomeRappresentante.value)){
    message += "- Cognome del rappresentante richiesto\r\n";
    formTest = false;
  }
  
   if (checkNullString(form.nomeRappresentante.value)){
    message += "- Nome del rappresentante richiesto\r\n";
    formTest = false;
  }

   
   if(form.tipoD.checked){
       if (checkNullString(form.address1line1.value)){
         message += "- Indirizzo sede legale richiesto\r\n";
         formTest = false;
       }
       if (checkNullString(document.getElementById("address1city").value)){
           message += "- Comune sede legale richiesta\r\n";
           formTest = false;
         }
       if (checkNullString(form.address1state.value)){
           message += "- Provincia sede legale richiesta\r\n";
           formTest = false;
         }
       }

       if(form.tipoD2.checked || form.tipoD3.checked){
    	   if (checkNullString(form.address1line1.value)){
               message += "- Indirizzo sede legale richiesto\r\n";
               formTest = false;
             }
           
             if (document.getElementById("address1city").value=="-1"){
                 message += "- Comune sede legale richiesta\r\n";
                 formTest = false;
               }
             if (checkNullString(form.address1state.value)){
                 message += "- Provincia sede legale richiesta\r\n";
                 formTest = false;
               }
       }
 
       
       if(form.tipoDest[0].checked){

 	   	if (form.address2latitude && form.address2latitude.value!=""){
        	 //alert(!isNaN(form.address2latitude.value));
        		if ((orgSelected == 1)  ){
        			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
         			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
         				 formTest = false;
         			}		 
        		}
     	 }   


    	 
    	 if (form.address2longitude && form.address2longitude.value!=""){
       	 //alert(!isNaN(form.address2longitude.value));
       		if ((orgSelected == 1)  ){
       			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value > 15.8032837)){
        			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
        				 formTest = false;
        			}		 
       		}
    	 }   

   if (checkNullString(form.address2line1.value)){
         message += "- Indirizzo sede operativa richiesto\r\n";
         formTest = false;
       }

   if (checkNullString(form.address2city.value)){
         message += "- Comune sede operativa richiesta\r\n";
         formTest = false;
   }
   if (form.address2city.value == "-1"){
   	 
       message += "- Comune sede operativa richiesta\r\n";
       formTest = false;
     }
   if (checkNullString(form.address2latitude.value)){
       message += "- Latitudine sede operativa richiesta\r\n";
       formTest = false;
   }
   
   if (checkNullString(form.address2longitude.value)){
       message += "- Longitudine sede operativa richiesta\r\n";
       formTest = false;
   }
         
      
       }
       else if(form.tipoDest[1].checked==true)  {
     	
    	 
       if(form.tipoDest[1].checked){

    	   if(form.address3city.value=="" || form.address3city.value=="-1"){
    	   	  message += "- Comune di Sede Mobile e\' richiesto\r\n";
    	         formTest = false;
    	   }}
       
       if (form.nomeCorrentista){
       if ((orgSelected == 1) && (checkNullString(form.nomeCorrentista.value))){
         message += "- Targa Veicolo richiesta\r\n";
         formTest = false;
       	}
    		 }
 	       
       if (checkNullString(form.address3line1.value)){
         message += "- Indirizzo sede mobile richiesto\r\n";
         formTest = false;
       }
       
       if (checkNullString(form.address1latitude.value)){
           message += "- Latitudine sede legale richiesta\r\n";
           formTest = false;
       }
       
       if (checkNullString(form.address1longitude.value)){
           message += "- Longitudine sede legale richiesta\r\n";
           formTest = false;
       }
       
       
       
       if (form.address3latitude && form.address3latitude.value!=""){
         	 //alert(!isNaN(form.address3latitude.value));
         		if ((orgSelected == 1)  ){
         			if (isNaN(form.address3latitude.value) ||  (form.address3latitude.value < 39.988475) || (form.address3latitude.value > 41.503754)){
          			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Attivit� mobile)\r\n";
          				 formTest = false;
          			}		 
         		}
      	 }   

     	 if (form.address3longitude && form.address3longitude.value!=""){
        	 //alert(!isNaN(form.address2longitude.value));
        		if ((orgSelected == 1)  ){
        			if (isNaN(form.address3longitude.value) ||  (form.address3longitude.value < 13.7563172) || (form.address3longitude.value > 15.8032837)){
         			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Attivit� mobile)\r\n";
         				 formTest = false;
         			}		 
        		}
     	 }   
       

     }   


if(form.tipoD3.checked)
{


  
//Controlli javascript per la latitudine e la longitudine del locale collegato
	if (form.address4latitude1 && form.address4latitude1.value!=""){
  	 //alert(!isNaN(form.address3latitude.value));
  		if ((orgSelected == 1)  ){
  			if (isNaN(form.address4latitude1.value)){
   			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
   				 formTest = false;
   			}		 
  		}
	 }   

	 if (form.address4longitude1 && form.address4longitude1.value!=""){
 	 //alert(!isNaN(form.address2longitude.value));
 		if ((orgSelected == 1)  ){
 			if (isNaN(form.address4longitude1.value)){
  			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
  				 formTest = false;
  			}		 
 		}
	 }   

	if (form.address4latitude2 && form.address4latitude2.value!=""){
 	 //alert(!isNaN(form.address3latitude.value));
 		if ((orgSelected == 1)  ){
 			if (isNaN(form.address4latitude2.value) ||  (form.address4latitude2.value < 39.988475) || (form.address4latitude2.value > 41.503754)){
  			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
  				 formTest = false;
  			}		 
 		}
	 }   

	 if (form.address4longitude2 && form.address4longitude2.value!=""){
	 //alert(!isNaN(form.address2longitude.value));
		if ((orgSelected == 1)  ){
			if (isNaN(form.address4longitude2.value) ||  (form.address4longitude2.value < 13.7563172) || (form.address4longitude2.value > 15.8032837)){
 			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
 				 formTest = false;
 			}		 
		}
	 }   

	 if (form.address4latitude3 && form.address4latitude3.value!=""){
 	 //alert(!isNaN(form.address3latitude.value));
 		if ((orgSelected == 1)  ){
 			if (isNaN(form.address4latitude3.value) ||  (form.address4latitude3.value < 39.988475) || (form.address4latitude3.value > 41.503754)){
  			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
  				 formTest = false;
  			}		 
 		}
	 }   

	 if (form.address4longitude3 && form.address4longitude3.value!=""){
	 //alert(!isNaN(form.address2longitude.value));
		if ((orgSelected == 1)  ){
			if (isNaN(form.address4longitude3.value) ||  (form.address4longitude3.value < 13.7563172) || (form.address4longitude3.value > 15.8032837)){
 			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
 				 formTest = false;
 			}		 
		}
	 }   

}

if (formTest == false) {
  alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
  return false;
} else {
  var test = document.addAccount.selectedList;
  if (test != null) {
    selectAllOptions(document.addAccount.selectedList);
  }
  if(alertMessage != "") {
    if(form.saveandclone.value='true' )
    {
			
    }

    confirmAction(alertMessage);

    
  }
  return true;
}

}



function resetFormElementsNew() {


	   elm1 = document.getElementById("tipoVeicolo1"); //Nome

if(document.addAccount.tipoVeicolo!=null){
	   document.addAccount.tipoVeicolo.style.background = "#ffffff";
   document.addAccount.tipoVeicolo.disabled = false;
}
	   if(elm1!=null){
   elm1.style.color = "#000000";
	   }
   elm2 = document.getElementById("targaVeicolo1"); //Cognom
  if( document.addAccount.targaVeicolo!=null){
    document.addAccount.targaVeicolo.style.background = "#ffffff";
   document.addAccount.targaVeicolo.disabled = false;}
  if(elm2!=null){

  elm2.style.color = "#000000";
  }
 /*  elm3 = document.getElementById("codiceCont1"); // Nome (Organization)
   document.addAccount.codiceCont.style.background = "#ffffff";
   document.addAccount.codiceCont.disabled = false;
   elm3.style.color = "#000000";*/
   
  	elm5 = document.getElementById("addressLine"); // Nome (Organization)
if(document.addAccount.addressline1!=null){
   	document.addAccount.addressline1.style.background = "#ffffff";
   	document.addAccount.addressline1.disabled = false;}
   	if(elm5!=null){

    	elm5.style.color = "#000000";
   	}       	
   	elm6 = document.getElementById("prov1"); // Nome (Organization)

if(document.getElementById("prov")!=null){
   	document.getElementById("prov").disabled = true;}
   	if(elm6!=null){

     	elm6.style.color = "#000000";
   	}
   	elm7 = document.getElementById("labelCap"); // Nome (Organization)
if(document.addAccount.addresszip!=null){
   	document.addAccount.addresszip.style.background = "#ffffff";
   	document.addAccount.addresszip.disabled = false;}
   	if(elm7!=null){

       	elm7.style.color = "#000000";
   	}       

      elm8 = document.getElementById("stateProv1"); // Nome (Organization)
      if(elm8!=null){

     	elm8.style.color = "#000000";
     	document.addAccount.address3state.style.background = "#ffffff";
        document.addAccount.address3state.value = "";
        document.addAccount.address3state.disabled = false;
      }
   		elm9 = document.getElementById("latitude1"); // Nome (Organization)
if(document.addAccount.address3latitude!=null){
   	    document.addAccount.address3latitude.style.background = "#ffffff";
   		document.addAccount.address3latitude.disabled = false;}
   		if(elm9!=null){

   		elm9.style.color = "#000000";
   	
   		}
   		elm10 = document.getElementById("longitude1"); // Nome (Organization)
if(document.addAccount.address3longitude!=null){
   		document.addAccount.address3longitude.style.background = "#ffffff";
   		document.addAccount.address3longitude.disabled = false;}
   		if(elm10!=null){

       		elm10.style.color = "#000000";
   		}
   	
   	elm = document.getElementById("tipoStruttura1"); // Nome (Organization)
if(document.addAccount.TipoStruttura!=null){
   	document.addAccount.TipoStruttura.style.background = "#ffffff";
   	document.addAccount.TipoStruttura.disabled = false;}
   	if(elm!=null){

    	elm.style.color = "#000000";
   	}
   elm12 = document.getElementById("indirizzo1");
if(document.addAccount.indirizzo12!=null){
   document.addAccount.indirizzo12.style.background = "#ffffff";
   document.addAccount.indirizzo12.disabled = false;}
   if(elm12!=null){

     elm12.style.color = "#000000";
   }
   elm17 = document.getElementById("prov2");
   if(document.getElementById("prov12")!=null){
   document.getElementById("prov12").disabled = true;
   //document.getElementById("prov12").selectedIndex = 0;

   }
   if(elm17!=null){

     elm17.style.color = "#000000";
   }
   //peppedocument.getElementById("prov").selectedIndex = 0;
   
   elm13 = document.getElementById("cap1");
if(document.addAccount.cap!=null){
    document.addAccount.cap.style.background = "#ffffff";
   document.addAccount.cap.disabled = false;}
   if(elm13!=null){

     elm13.style.color = "#000000";
   }
   elm14 = document.getElementById("stateProv2");
   if(elm14!=null){

       elm14.style.color = "#000000";
   }
   elm15 = document.getElementById("latitude2");
if( document.addAccount.address2latitude!=null){
       document.addAccount.address2latitude.style.background = "#ffffff";
   document.addAccount.address2latitude.disabled = false;

}
    if(elm15!=null){

    elm15.style.color = "#000000";
   }
   elm16 = document.getElementById("longitude2");
if( document.addAccount.address2longitude!=null){
      document.addAccount.address2longitude.style.background = "#ffffff";
   document.addAccount.address2longitude.disabled = false;
}
   if(elm16!=null){

       elm16.style.color = "#000000";
   }   
   document.addAccount.address1type.style.background = "#ffffff";
   document.addAccount.address1type.disabled = false;
   document.addAccount.address1type.style.color="#000000"
   
}


//-------------------------------------------------------------------
// getElementIndex(input_object)
//   Pass an input object, returns index in form.elements[] for the object
//   Returns -1 if error
//-------------------------------------------------------------------
function getElementIndex(obj) {
var theform = obj.form;
for (var i=0; i<theform.elements.length; i++) {
  if (obj.name == theform.elements[i].name) {
    return i;
    }
  }
  return -1;
}
// -------------------------------------------------------------------
// tabNext(input_object)
//   Pass an form input object. Will focus() the next field in the form
//   after the passed element.
//   a) Will not focus to hidden or disabled fields
//   b) If end of form is reached, it will loop to beginning
//   c) If it loops through and reaches the original field again without
//      finding a valid field to focus, it stops
// -------------------------------------------------------------------
function tabNext(obj) {
if (navigator.platform.toUpperCase().indexOf("SUNOS") != -1) {
  obj.blur(); return; // Sun's onFocus() is messed up
  }
var theform = obj.form;
var i = getElementIndex(obj);
var j=i+1;
if (j >= theform.elements.length) { j=0; }
if (i == -1) { return; }
while (j != i) {
  if ((theform.elements[j].type!="hidden") &&
      (theform.elements[j].name != theform.elements[i].name) &&
    (!theform.elements[j].disabled)) {
    theform.elements[j].focus();
    break;
}
j++;
  if (j >= theform.elements.length) { j=0; }
}
}

function update(countryObj, stateObj, selectedValue) {
var country = document.forms['addAccount'].elements[countryObj].value;
var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addAccount&stateObj=address"+stateObj+"state";
window.frames['server_commands'].location.href=url;
}

function continueUpdateState(stateObj, showText) {
if(showText == 'true'){
  hideSpan('state1' + stateObj);
  showSpan('state2' + stateObj);
} else {
  hideSpan('state2' + stateObj);
  showSpan('state1' + stateObj);
}
}

var states = new Array();
var initStates = false;
function resetStateList(country, stateObj) {
var stateSelect = document.forms['addAccount'].elements['address'+stateObj+'state'];
var i = 0;
if (initStates == false) {
  for(i = stateSelect.options.length -1; i > 0 ;i--) {
    var state = new Array(stateSelect.options[i].value, stateSelect.options[i].text);
    states[states.length] = state;
  }
}
if (initStates == false) {
  initStates = true;
}
stateSelect.options.length = 0;
for(i = states.length -1; i > 0 ;i--) {
  var state = states[i];
  if (state[0].indexOf(country) != -1 || country == label('option.none','-- None --')) {
    stateSelect.options[stateSelect.options.length] = new Option(state[1], state[0]);
  }
}
}

function updateCopyAddress(state){
copyAddr = document.getElementById("copyAddress");
if (state == 0){
 copyAddr.checked = false;
 copyAddr.disabled = true;
} else {
 copyAddr.disabled = false;
}
}

function updateFormElementsNew(indexText) {


	index =0;
   if(indexText=='Es. Commerciale')
   {
	   index = 0;

		}
   if(indexText=='Autoveicolo')
   {
	   index = 1;

		}
   

	  //document.getElementById("codiceFiscaleCorrentista").value="";
	  //document.getElementById("alertText").value="";


	  elm1=document.addAccount.nameMiddle;
	    elm2=document.addAccount.cin;
	    elm3=document.addAccount.date3;


	 
	  
	  
	  elm1=document.addAccount.TipoLocale;

	    elm2=document.addAccount.address4city11;
	    elm3=document.addAccount.address4line11;
	    elm4=document.addAccount.address4latitude11;
	    elm5=document.addAccount.address4longitude11;
	    elm6=document.addAccount.address4zip11;
	    elm7=document.addAccount.address4state11;
if(elm1!=null){
	    elm1.style.color="";
		
	    elm1.style.background = "";
	    //elm1.value = "";
	    elm1.disabled = false;
}
if(elm2!=null){

//elm2.style.color="#cccccc";
	    elm2.style.background = "";
	   // elm2.value = "";
	    elm2.disabled = false;
}
if(elm3!=null){

	    elm3.style.color="";
	    elm3.style.background = "";
	    //elm3.value = "";
	    elm3.disabled = false;
}
if(elm4!=null){

	    elm4.style.color="";
	    elm4.style.background = "";
	    //elm4.value = "";
	    elm4.disabled = false;
}
if(elm5!=null){

	    elm5.style.color="";
	    elm5.style.background = "";
	    //elm5.value = "";
	    elm5.disabled = false;
}
if(elm6!=null){

	    elm6.style.color="";
	    elm6.style.background = "";
	    //elm6.value = "";
	    elm6.disabled = false;

}
if(elm7!=null){

	    elm7.style.color="";
	    elm7.style.background = "";
	    //elm7.value = "";
	    elm7.disabled = false;

}
	    elm1=document.addAccount.TipoLocale2;
	    elm2=document.addAccount.address4city2;
	    elm3=document.addAccount.address4line12;
	    elm4=document.addAccount.address4latitude2;
	    elm5=document.addAccount.address4longitude2;
	    elm6=document.addAccount.address4zip2;
	    elm7=document.addAccount.address4state2;

	    if(elm1!=null){

		 elm1.style.color="";
	    elm1.style.background = "";
	    //elm1.value = "";
	    elm1.disabled = false;
	    }
	    if(elm2!=null){
		    
	    //elm2.style.color="#cccccc";
	    elm2.style.background = "";
	    //elm2.value = "";
	    elm2.disabled = false;
	    }
	    if(elm3!=null){
		    
	    elm3.style.color="";
	    elm3.style.background = "";
	    //elm3.value = "";
	    elm3.disabled = false;
	    }
	    if(elm4!=null){
		    
	    elm4.style.color="";
	    elm4.style.background = "";
	    //elm4.value = "";
	    elm4.disabled = false;
	    }
	    if(elm5!=null){
		    
	    elm5.style.color="";
	    elm5.style.background = "";
	    //elm5.value = "";
	    elm5.disabled = false;
	    }
	    if(elm6!=null){
		    
	    elm6.style.color="";
	    elm6.style.background = "";
	    //elm6.value = "";
	    
	    elm6.disabled = false;
	    }
	    if(elm7!=null){
		    
	    elm7.style.color="";
	    elm7.style.background = "";
	    //elm7.value = "";
	    elm7.disabled = false;
	    }
	    elm1=document.addAccount.TipoLocale3;
	    elm2=document.addAccount.address4city3;
	    elm3=document.addAccount.address4line13;
	    elm4=document.addAccount.address4latitude3;
	    elm5=document.addAccount.address4longitude3;
	    elm6=document.addAccount.address4zip3;
	    elm7=document.addAccount.address4state3;
	    if(elm1!=null){

	    elm1.style.color="";
	    elm1.style.background = "";
	    //elm1.value = "";
	    elm1.disabled = false;
	    }
	    if(elm2!=null){
		    
	   // elm2.style.color="#cccccc";
	    elm2.style.background = "";
	    //elm2.value = "";
	    elm2.disabled = false;
	    }
	    if(elm3!=null){
		    
	    elm3.style.color="";
	    elm3.style.background = "";
	    //elm3.value = "";
	    elm3.disabled = false;
	    }
	    if(elm4!=null){
		    
	    elm4.style.color="";
	    elm4.style.background = "";
	    //elm4.value = "";
	    elm4.disabled = false;
	    }
	    if(elm5!=null){
		    
	    elm5.style.color="";
	    elm5.style.background = "";
	    //elm5.value = "";
	    elm5.disabled = false;
	    }
	    if(elm6!=null){
		    
	    elm6.style.color="";
	    elm6.style.background = "";
	    //elm6.value = "";
	    elm6.disabled = false;
	    }
	    if(elm7!=null){
		    
	    elm7.style.color="";
	    elm7.style.background = "";
	    //elm7.value = "";
	    elm7.disabled = false;
	    }
	    if(document.getElementById("aggiungialtrobutton")!=null)
	    document.getElementById("aggiungialtrobutton").disabled="";
	    if(document.getElementById("aggiungialtrobutton2")!=null)
	    document.getElementById("aggiungialtrobutton2").disabled="";
  	
  	if(index==1){
  	  	if(document.getElementById("starMobil3")!=null)
  		document.getElementById("starMobil3").style.display="";
  	  if(document.getElementById("starMobil4")!=null)
  		document.getElementById("starMobil4").style.display="";
  	if(document.getElementById("starMobil5")!=null)
		document.getElementById("starMobil5").style.display="";
  	if(document.getElementById("starMobil8")!=null)
  		document.getElementById("starMobil8").style.display="";
  	if(document.getElementById("starMobil9")!=null)
  		document.getElementById("starMobil9").style.display="";
  		if(document.getElementById("starMobil10")) document.getElementById("starMobil10").style.display="";

  		document.addAccount.address1latitude.style.background = "#ffffff";
  		document.addAccount.address1longitude.style.background = "#ffffff";
  		
  	}
  	else if(index==0){
  		if(document.getElementById("starMobil3")!=null)
  		document.getElementById("starMobil3").style.display="none";
  	  if(document.getElementById("starMobil4")!=null)
  		document.getElementById("starMobil4").style.display="none";
  	if(document.getElementById("starMobil5")!=null)
    		document.getElementById("starMobil5").style.display="none";
  	if(document.getElementById("starMobil8")!=null)
  		document.getElementById("starMobil8").style.display="none";
	if(document.getElementById("starMobil9")!=null)
  		document.getElementById("starMobil9").style.display="none";
  		if(document.getElementById("starMobil10")) document.getElementById("starMobil10").style.display="none";

  		document.addAccount.address1latitude.style.background = "#cccccc";
  		document.addAccount.address1longitude.style.background = "#cccccc";
  	
  	}
  	

  	
    if (document.getElementById) {
       elm1 = document.getElementById("tipoVeicolo1"); //Nome
       elm2 = document.getElementById("targaVeicolo1"); //Cognome
      /* elm3 = document.getElementById("codiceCont1"); // Nome (Organization)*/
       elm4 = document.getElementById("tipoStruttura1");
       elm5 = document.getElementById("addressLine");
       elm6 = document.getElementById("prov1");
       elm7 = document.getElementById("labelCap");
       elm8 = document.getElementById("stateProv1");
       elm9 = document.getElementById("latitude1");
       elm10 = document.getElementById("longitude1");
     
       
      if (index == 0) {
        resetFormElementsNew();
        document.addAccount.address3type.disabled = "true";
      
        if(elm1!=null){
        elm1.style.color="#cccccc";
        }
        if(document.addAccount.tipoVeicolo!=null){
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;}
        if(elm2!=null)
       // elm2.style.color="#cccccc";
if(document.addAccount.targaVeicolo!=null){
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
}
    
    /*    elm3.style.color="#cccccc";
        document.addAccount.codiceCont.style.background = "#cccccc";
        document.addAccount.codiceCont.value = "";
        document.addAccount.codiceCont.disabled = true;*/
        if(elm3!=null)
           elm4.style.color="#cccccc";
        if(  document.getElementById("prov12")!=null)
        document.getElementById("prov12").disabled = true;
                
if(elm5!=null)
        elm5.style.color="#cccccc";
if( document.addAccount.addressline1!=null){
        document.addAccount.addressline1.style.background = "#cccccc";
        document.addAccount.addressline1.value = "";
        document.addAccount.addressline1.disabled = true;}
if(elm6!=null)
        
        elm6.style.color="#cccccc";
if(document.getElementById("prov12")!=null){
        document.getElementById("prov12").disabled = true;
        //document.getElementById("prov12").selectedIndex=0;
}
if(elm7!=null)
        elm7.style.color="#cccccc";
if(document.addAccount.addresszip!=null){
        document.addAccount.addresszip.style.background = "#cccccc";
        document.addAccount.addresszip.value = "";
        document.addAccount.addresszip.disabled = true;
}
if(elm8!=null)
        elm8.style.color="#cccccc";
document.addAccount.address3state.style.background = "#cccccc";
document.addAccount.address3state.value = "";
document.addAccount.address3state.disabled = true;

        if(elm9!=null)
        elm9.style.color="#cccccc";
        if(document.addAccount.address3latitude!=null){
        document.addAccount.address3latitude.style.background = "#cccccc";
        document.addAccount.address3latitude.value = "";
        document.addAccount.address3latitude.disabled = true;
        }
        if(elm10!=null)
        elm10.style.color="#cccccc";
        if(document.addAccount.address3longitude!=null){
        document.addAccount.address3longitude.style.background = "#cccccc";
        document.addAccount.address3longitude.value = "";
        document.addAccount.address3longitude.disabled = true;
        }
        if(elm4!=null)
        elm4.style.color="#cccccc";
        if(document.addAccount.TipoStruttura!=null){
        document.addAccount.TipoStruttura.style.background = "#cccccc";
        document.addAccount.TipoStruttura.value = "";
        document.addAccount.TipoStruttura.disabled = true;
        }
        
       if(document.getElementById("prov12")!=null){
        document.getElementById("prov12").disabled = false;
        document.addAccount.check.value = "es";
        document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
       }
        tipo1 = document.getElementById("tipoD");
        tipo1.checked = true;
        
        /*document.getElementById("codice1").value = "";
        document.getElementById("codice2").value = "";
        document.getElementById("codice3").value = "";
        document.getElementById("codice4").value = "";
        document.getElementById("codice5").value = "";
        document.getElementById("codice6").value = "";
        document.getElementById("codice7").value = "";
        document.getElementById("codice8").value = "";
        document.getElementById("codice9").value = "";
        document.getElementById("codice10").value = "";*/
        
        
      } else if (index == 1){
    	  document.addAccount.address3type.disabled = "";
        resetFormElementsNew();
        document.addAccount.address1type.style.background = "#000000";
       	document.addAccount.address1type.disabled = false;
        
       elm5 = document.getElementById("indirizzo1");
       elm6 = document.getElementById("prov2");
       elm7 = document.getElementById("cap1");
       elm8 = document.getElementById("stateProv2");
       elm9 = document.getElementById("latitude2");
       elm10 = document.getElementById("longitude2");
       
      	 elm5.style.color="#cccccc";
        document.addAccount.indirizzo12.style.background = "#cccccc";
        document.addAccount.indirizzo12.value = "";
        document.addAccount.indirizzo12.disabled = true;
        
        elm6.style.color="#cccccc";
        document.getElementById("prov").disabled = true;
        //peppedocument.getElementById("prov").selectedIndex=0;
        
        elm7.style.color="#cccccc";
        document.addAccount.cap.style.background = "#cccccc";
        document.addAccount.cap.value = "";
        document.addAccount.cap.disabled = true;
        
        elm8.style.color="#cccccc";
        
        elm9.style.color="#cccccc";
        document.addAccount.address2latitude.style.background = "#cccccc";
        document.addAccount.address2latitude.value = "";
        document.addAccount.address2latitude.disabled = true;
        
        elm10.style.color="#cccccc";
        document.addAccount.address2longitude.style.background = "#cccccc";
        document.addAccount.address2longitude.value = "";
        document.addAccount.address2longitude.disabled = true;
        
        
    	/*elm3.style.color="#cccccc";
        document.addAccount.codiceCont.style.background = "#cccccc";
        document.addAccount.codiceCont.value = "";
        document.addAccount.codiceCont.disabled = true;*/
        
     	document.getElementById("prov").disabled = false;
        document.addAccount.check.value = "Autoveicolo";
        document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO
       } else if (index==2) {
      	
      	resetFormElementsNew();
        document.addAccount.address3type.disabled = "true";
        elm1.style.color="#cccccc";
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;
    
       // elm2.style.color="#cccccc";
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
        
        document.addAccount.check.value = "codiceCont";
        document.addAccount.orgType.value = "19"; //Valore per sindaco
        
      }
    }

   
   /* if (onLoad != 1){
      var url = "Accounts.do?command=RebuildFormElements&index=" + index;
      window.frames['server_commands'].location.href=url;
    }*/
    onLoad = 0;
  }
function resetCarattere(){
	
		
		elm1 = document.getElementById("data1");
		elm2 = document.getElementById("data2");
		elm3 = document.getElementById("dat3");
		elm4 = document.getElementById("data4");
		
		elm1.style.visibility = "hidden";
		elm2.style.visibility = "hidden";
		elm3.style.visibility = "hidden";
		elm4.style.visibility = "hidden";
		document.addAccount.source.selectedIndex=0;
		
}

function selectCarattere(){

		elm1 = document.getElementById("data1");
		elm2 = document.getElementById("data2");
		elm3 = document.getElementById("dat3");
		elm4 = document.getElementById("data4");
		elm5 = document.getElementById("cessazione");
		elm6 = document.getElementById("data5");
		car = document.addAccount.source.value;
	
		if(car == 1){
			elm1.style.visibility = "visible";
			elm2.style.visibility = "visible";
			elm3.style.visibility = "visible";
			elm4.style.visibility = "visible";
			elm5.style.visibility = "visible";
			elm6.style.visibility = "visible";
		}
		else {
			elm1.style.visibility = "hidden";
			elm2.style.visibility = "hidden";
			elm3.style.visibility = "hidden";
			elm4.style.visibility = "hidden";
			elm5.style.visibility = "hidden";
			elm6.style.visibility = "hidden";
		}
	
}
function resetCodice(){

		document.getElementById("codice1").value = "";
    document.getElementById("codice2").value = "";
    document.getElementById("codice3").value = "";
    document.getElementById("codice4").value = "";
    document.getElementById("codice5").value = "";
    document.getElementById("codice6").value = "";
    document.getElementById("codice7").value = "";
    document.getElementById("codice8").value = "";
    document.getElementById("codice9").value = "";
    document.getElementById("codice10").value = "";
}

function clonaLocaleFunzionalmenteCollegato()
{
	var maxElementi = 3;
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('elementi');
  	
  	if (elementi.value<maxElementi)
  	{
  		
  	elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size');
  	size.value=parseInt(size.value)+1;
  	
  	var clonato = document.getElementById('locale_0');
  	
    	/*clona patologia*/	  	
  	clone=clonato.cloneNode(true);
  	clone.id="locale_"+elementi.value ;
  	clone.getElementsByTagName('FONT')[0].style.display = "none";
	clone.getElementsByTagName('FONT')[0].id = "city_loc"+ elementi.value;
	
	

  	clone.getElementsByTagName('FONT')[1].style.display = "none";
	clone.getElementsByTagName('FONT')[1].id = "address_loc"+ elementi.value;

  	clone.getElementsByTagName('FONT')[2].style.display = "none";
	clone.getElementsByTagName('FONT')[2].id = "state_loc"+ elementi.value;

  	clone.getElementsByTagName('FONT')[3].style.display = "none";
	clone.getElementsByTagName('FONT')[3].id = "zip_loc"+ elementi.value;
	
	clone.getElementsByTagName('INPUT')[0].name = "address" + elementi.value+"id";
	clone.getElementsByTagName('INPUT')[0].id = "address" + elementi.value+"id";
	clone.getElementsByTagName('INPUT')[0].value = "";
	
	clone.getElementsByTagName('INPUT')[1].name = "address4type" + elementi.value;
	clone.getElementsByTagName('INPUT')[1].id = "address4type" + elementi.value;
	
	
	
	
	clone.getElementsByTagName('SELECT')[0].name = "TipoLocale" + elementi.value;
	clone.getElementsByTagName('SELECT')[0].id = "TipoLocale" + elementi.value;
	clone.getElementsByTagName('SELECT')[0].value = "-1";
	clone.getElementsByTagName('SELECT')[0].required="required";
	  	
	clone.getElementsByTagName('INPUT')[2].name = "address4city" + elementi.value;
	clone.getElementsByTagName('INPUT')[2].id = "address4city" + elementi.value;
	clone.getElementsByTagName('INPUT')[2].value = "" ;
	
	
		clone.getElementsByTagName('INPUT')[2].required="required";
		
	
		clone.getElementsByTagName('INPUT')[3].name = "address4line1" + elementi.value;
	clone.getElementsByTagName('INPUT')[3].id = "address4line1" + elementi.value;
	clone.getElementsByTagName('INPUT')[3].value = "" ;
	
	clone.getElementsByTagName('INPUT')[3].required="required";
	
	
		clone.getElementsByTagName('INPUT')[4].name = "address4zip" + elementi.value;
	clone.getElementsByTagName('INPUT')[4].id = "address4zip" + elementi.value;
	clone.getElementsByTagName('INPUT')[4].value = "" ;
	clone.getElementsByTagName('INPUT')[4].required="required";
	
		clone.getElementsByTagName('INPUT')[5].name = "address4state" + elementi.value;
	clone.getElementsByTagName('INPUT')[5].id = "address4state" + elementi.value;
	clone.getElementsByTagName('INPUT')[5].value = "" ;
	clone.getElementsByTagName('INPUT')[5].required="required";
	
		clone.getElementsByTagName('INPUT')[6].name = "address4latitude" + elementi.value;
	clone.getElementsByTagName('INPUT')[6].id = "address4latitude" + elementi.value;
	clone.getElementsByTagName('INPUT')[6].value = "" ;
	
	clone.getElementsByTagName('INPUT')[7].name = "address4longitude" + elementi.value;
	clone.getElementsByTagName('INPUT')[7].id = "address4longitude" + elementi.value;
	clone.getElementsByTagName('INPUT')[7].value = "" ;
  	
	clone.getElementsByTagName('LABEL')[0].innerHTML ='Locale Funzionalmente collegato '+elementi.value ;
  	clone.getElementsByTagName('LABEL')[0].id = "intestazione" + elementi.value;
  	
  	
  
  	
  	//clone.id = "row_" + elementi.value;
  	
  	/*Aggancio il nodo prova*/
  	clone.style.display="";
  	clonato.parentNode.appendChild(clone);
  	
  	/*Lo rendo visibile*/
  	//clone.style.display="block";
  	
  	}else
  	{
  		
  	}

}



function removeLocale(indice)
{
	
	
	
	if(parseInt(indice)>0)
		{
	if (document.getElementById('locale_'+indice) != null)
		{
		document.getElementById('locale_'+indice).parentNode.removeChild(document.getElementById('locale_'+indice));
		document.getElementById('elementi').value=parseInt(document.getElementById('elementi').value)-1;
		document.getElementById('size').value=parseInt(document.getElementById('size').value)-1;
		}
		}
	else{
		alert('Locali Funzionamente Collegati Rimossi')
	}
	
	
	if (document.getElementById('elementi').value=='0' && document.getElementById('tipoD3').checked)
		{
		
		clonaLocaleFunzionalmenteCollegato();
		}
}

function removeAllLocali()
{
	indice = 1 ;
	while (document.getElementById('locale_'+indice) != null)
		{
		document.getElementById('locale_'+indice).parentNode.removeChild(document.getElementById('locale_'+indice)); 
		indice +=1 ;
		}
	document.getElementById('elementi').value="0";
	document.getElementById('size').value="0";
		}

	
