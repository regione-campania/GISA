/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/



function controlloDataFine(dataI, dataF){
	var arr1 = dataI.split("/");
	var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
    
	var arr2 = dataF.split("/");
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	
	var r1 = d1.getTime();
	var r2 = d2.getTime();
	
	if (r1<=r2) 
		return false;
				
	return true;
	
}

function controlloData(stringa){
	var now = new Date();
    var giorno = now.getDate();
    var mese = now.getMonth() + 1;
    var anno = now.getFullYear(); 


   
    
    
	var arr2 = stringa.split("/");
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	
	var r1 = now.getTime();
	var r2 = d2.getTime();

	if (r2>r1) 
		return false;
				
	return true;
	
}




function initprovaFunzione(){
	
	var valore= document.details.tipoCampione.value;

	if(valore=="3"){
		

		document.details.auditTipo.style.display="";	

if(document.details.auditTipo.value==1){
	
	 mostraMenu4();	 
}else{
	if(document.details.auditTipo.value==6){
		document.getElementById("notealtro").style.visibility="";
		}
	else{
		document.getElementById("notealtro").style.visibility="hidden";
		}

	document.details.bpi.style.display="none";		
	document.details.bpi.value="-1";

	document.details.haccp.style.display="none";		
	document.details.haccp.value="-1";
		
		document.details.tipoAudit.style.display="none";		
		document.details.tipoAudit.value="-1";
		document.details.tipoIspezione.style.display="none";
		document.details.tipoIspezione.value="-1";
}
		

document.details.tipoIspezione.value=-1;

document.details.tipoIspezione.style.display="none";

mostraMenuTipoIspezione();

				
mostraMenu2();	

	}
	else{
		if(valore=="4"){
			
			document.details.tipoAudit.style.display="none";
			document.details.auditTipo.style.display="none";	
				document.details.auditTipo.value="-1" ;
				document.details.tipoAudit.value="-1" ;
			 mostraMenu2();	 
			 mostraMenuTipoIspezione();
		 document.details.tipoIspezione.style.display="block";
		 
				}
		else{
			
			document.details.tipoAudit.style.display="none";
			document.details.tipoAudit.value="-1" ;

			document.details.auditTipo.style.display="none";
			document.details.auditTipo.value="-1" ;
			 
			 
			

		 	document.details.tipoIspezione.value=-1;
					
			 document.details.tipoIspezione.style.display="none";
			
 			mostraMenuTipoIspezione();
	
							
			 mostraMenu2();	
		}


		}

	
}


function abilitaCampoNote()
{
	element = document.details.comunicazioneRischio1[0];
	
	


	if(element.checked && element.value=="0")
	{
		document.details.noteRischio.style.visibility="visible";
	}
	else
	{
		element = document.details.comunicazioneRischio1[1];
		if(element.checked && element.value=="1")
		{
			document.details.noteRischio.style.visibility="hidden";
		}
	}
}
function mostraMenu4(){
	

	var valore= document.details.auditTipo.value;
	if(valore=="1"){
		
	 document.details.tipoAudit.style.display="";
	
		
				}else
				{
if(valore==6){
document.getElementById("notealtro").style.visibility="";
	
}else{

	document.getElementById("notealtro").style.visibility="hidden";
}
					
					document.details.bpi.style.display="none";		
					document.details.bpi.value="-1";

					document.details.haccp.style.display="none";		
					document.details.haccp.value="-1";
					 document.details.tipoAudit.style.display="none";
					}


}

function controllo_data(stringa){
	var espressione = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
	if (!espressione.test(stringa))
	{
	    return false;
	}
}




function mostraCampo(){
	valore = document.details.nucleoIspettivo.value;
	


if(valore==-1){
	document.getElementById("componenteNucleoSelectSian").style.display="none";
	document.getElementById("componenteNucleoSelect").style.display="none";
	document.getElementById("nucleo1").style.display="none";
	
}else{
	
	if(valore == 1 )
	{
	
		document.getElementById("componenteNucleoSelectSian").style.display="none";

		document.getElementById("nucleo1").style.display="none";
		
		document.getElementById("componenteNucleoSelect").style.display="block";
	
	}
	else{


		if(valore==2){

			document.getElementById("componenteNucleoSelect").style.display="none";
			document.getElementById("componenteNucleoSelectSian").style.display="block";
			document.getElementById("nucleo1").style.display="none";
		
		}
		else{

			document.getElementById("componenteNucleoSelectSian").style.display="none";
			document.getElementById("componenteNucleoSelect").style.display="none";
			document.getElementById("nucleo1").style.display="block";

			}



		}
		
}
}

function mostraCampo2(){

	

	valore= document.details.nucleoIspettivoDue.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect2").style.display="none";
		document.getElementById("componenteNucleoSelectSian2").style.display="none";
		document.getElementById("nucleo2").style.display="none";
		
	}

	if(valore==1 ){
document.getElementById("componenteNucleoSelect2").style.display="block";
document.getElementById("nucleo2").style.display="none";
document.getElementById("componenteNucleoSelectSian2").style.display="none";


		}else{

			if(valore==2){

				document.getElementById("componenteNucleoSelect2").style.display="none";
				document.getElementById("nucleo2").style.display="none";
				document.getElementById("componenteNucleoSelectSian2").style.display="block";

			
				
				}else{
					document.getElementById("componenteNucleoSelectSian2").style.display="none";
			document.getElementById("componenteNucleoSelect2").style.display="none";
			document.getElementById("nucleo2").style.display="block";
			}
			}
	

	
}



function mostraCampo3(){


	valore= document.details.nucleoIspettivoTre.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect3").style.display="none";
		document.getElementById("componenteNucleoSelectSian3").style.display="none";
		document.getElementById("nucleo3").style.display="none";
		
	}

	if(valore==1){
document.getElementById("componenteNucleoSelect3").style.display="block";
document.getElementById("nucleo3").style.display="none";
document.getElementById("componenteNucleoSelectSian3").style.display="none";


		}else{
if(valore==2){
	document.getElementById("componenteNucleoSelect3").style.display="none";
	document.getElementById("nucleo3").style.display="none";
	document.getElementById("componenteNucleoSelectSian3").style.display="block";
	
}else{
			
			document.getElementById("componenteNucleoSelectSian3").style.display="none";
			document.getElementById("componenteNucleoSelect3").style.display="none";
			document.getElementById("nucleo3").style.display="block";
			}
	
		}
	
}


function mostraCampo4(){

	
	valore= document.details.nucleoIspettivoQuattro.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect4").style.display="none";
		document.getElementById("componenteNucleoSelectSian4").style.display="none";
		document.getElementById("nucleo4").style.display="none";
		
	}

	if(valore==1 ){
		document.getElementById("componenteNucleoSelectSian4").style.display="none";
document.getElementById("componenteNucleoSelect4").style.display="block";
document.getElementById("nucleo4").style.display="none";



		}else{
			if(valore==2){
				document.getElementById("componenteNucleoSelectSian4").style.display="block";
				document.getElementById("componenteNucleoSelect4").style.display="none";
				document.getElementById("nucleo4").style.display="none";
				}
			else{
				document.getElementById("componenteNucleoSelectSian4").style.display="none";
			document.getElementById("componenteNucleoSelect4").style.display="none";
			document.getElementById("nucleo4").style.display="block";
			}
	
		}
	
}



function mostraCampo5(){
	

	valore= document.details.nucleoIspettivoCinque.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect5").style.display="none";
		document.getElementById("componenteNucleoSelectSian5").style.display="none";
		document.getElementById("nucleo5").style.display="none";
		
	}

	if(valore==1 ){
document.getElementById("componenteNucleoSelect5").style.display="block";
document.getElementById("nucleo5").style.display="none";
document.getElementById("componenteNucleoSelectSian5").style.display="none";


		}else{
			if(valore==2){

				document.getElementById("componenteNucleoSelect5").style.display="none";
				document.getElementById("nucleo5").style.display="none";
				document.getElementById("componenteNucleoSelectSian5").style.display="block";


			}
			else{

				document.getElementById("componenteNucleoSelectSian5").style.display="none";
			document.getElementById("componenteNucleoSelect5").style.display="none";
			document.getElementById("nucleo5").style.display="block";
			}
		}

	
}


function mostraCampo6(){
	

	valore= document.details.nucleoIspettivoSei.value;

	if(valore==-1){
		document.getElementById("componenteNucleoSelectSian6").style.display="none";
		document.getElementById("componenteNucleoSelect6").style.display="none";
		document.getElementById("nucleo6").style.display="none";
		
	}
	if(valore==1){
document.getElementById("componenteNucleoSelect6").style.display="block";
document.getElementById("nucleo6").style.display="none";
document.getElementById("componenteNucleoSelectSian6").style.display="none";


		}else{

			if(valore==2){

				document.getElementById("componenteNucleoSelect6").style.display="none";
				document.getElementById("nucleo6").style.display="none";
				document.getElementById("componenteNucleoSelectSian6").style.display="block";

				}else{

			document.getElementById("componenteNucleoSelectSian6").style.display="none";
			document.getElementById("componenteNucleoSelect6").style.display="none";
			document.getElementById("nucleo6").style.display="block";
			}
		}

	
}


function mostraCampo7(){


	valore= document.details.nucleoIspettivoSette.value;

	if(valore==-1){
		document.getElementById("componenteNucleoSelect7").style.display="none";
		document.getElementById("componenteNucleoSelectSian7").style.display="none";
		document.getElementById("nucleo7").style.display="none";
		
	}
	if(valore==1){
		document.getElementById("componenteNucleoSelectSian7").style.display="none";
document.getElementById("componenteNucleoSelect7").style.display="block";
document.getElementById("nucleo7").style.display="none";



		}else{
			if(valore==2){

				document.getElementById("componenteNucleoSelectSian7").style.display="block";
				document.getElementById("componenteNucleoSelect7").style.display="none";
				document.getElementById("nucleo7").style.display="none";

			}else{
			document.getElementById("componenteNucleoSelectSian7").style.display="none";
			document.getElementById("componenteNucleoSelect7").style.display="none";
			document.getElementById("nucleo7").style.display="block";
			}}
	

	
}


function mostraCampo8(){

		valore= document.details.nucleoIspettivoOtto.value;
		if(valore==-1){
			document.getElementById("componenteNucleoSelect8").style.display="none";
			document.getElementById("componenteNucleoSelectSian8").style.display="none";
			document.getElementById("nucleo8").style.display="none";
			
		}

	if(valore==1 ){
		document.getElementById("componenteNucleoSelectSian8").style.display="none";
document.getElementById("componenteNucleoSelect8").style.display="block";
document.getElementById("nucleo8").style.display="none";



		}else{

if(valore==2){
	document.getElementById("componenteNucleoSelectSian8").style.display="block";
	document.getElementById("componenteNucleoSelect8").style.display="none";
	document.getElementById("nucleo8").style.display="none";

	
}else{
			document.getElementById("componenteNucleoSelectSian8").style.display="none";

			document.getElementById("componenteNucleoSelect8").style.display="none";
			document.getElementById("nucleo8").style.display="block";
			}
		}

	
}


function mostraCampo9(){

	
	valore= document.details.nucleoIspettivoNove.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect9").style.display="none";
		document.getElementById("componenteNucleoSelectSian9").style.display="none";
		document.getElementById("nucleo9").style.display="none";
		
	}

	if(valore==1){
document.getElementById("componenteNucleoSelect9").style.display="block";
document.getElementById("nucleo9").style.display="none";
document.getElementById("componenteNucleoSelectSian9").style.display="none";


		}else{

if(valore==2){

	document.getElementById("componenteNucleoSelect9").style.display="none";
	document.getElementById("nucleo9").style.display="none";
	document.getElementById("componenteNucleoSelectSian9").style.display="block";
	
}else{
			
			document.getElementById("componenteNucleoSelectSian9").style.display="none";
			document.getElementById("componenteNucleoSelect9").style.display="none";
			document.getElementById("nucleo9").style.display="block";
			}
		}

	
}



function mostraCampo10(){
	

	valore= document.details.nucleoIspettivoDieci.value;
	if(valore==-1){


		document.getElementById("componenteNucleoSelectSian10").style.display="none";
		
		document.getElementById("componenteNucleoSelect10").style.display="none";
		document.getElementById("nucleo10").style.display="none";
		
	}

	if(valore==1 || valore==2){
document.getElementById("componenteNucleoSelect10").style.display="block";
document.getElementById("nucleo10").style.display="none";
document.getElementById("componenteNucleoSelectSian10").style.display="none";



		}else{

if(valore==2){
	document.getElementById("componenteNucleoSelect10").style.display="none";
	document.getElementById("nucleo10").style.display="none";
	document.getElementById("componenteNucleoSelectSian10").style.display="block";


	
}else {
			
			document.getElementById("componenteNucleoSelectSian10").style.display="none";
			
			document.getElementById("componenteNucleoSelect10").style.display="none";
			document.getElementById("nucleo10").style.display="block";
		}}
	

	
}






function abilitaSel2(){
document.getElementById("nucleodue").style.display="";


}

function abilitaSel3(){
document.getElementById("nucleotre").style.display="";

}

function abilitaSel4(){
document.getElementById("nucleoquattro").style.display="";

}

function abilitaSel5(){
document.getElementById("nucleocinque").style.display="";

}

function abilitaSel6(){
document.getElementById("nucleosei").style.display="";

}

function abilitaSel7(){
document.getElementById("nucleosette").style.display="";

}

function abilitaSel8(){
document.getElementById("nucleootto").style.display="";

}

function abilitaSel9(){
document.getElementById("nucleonove").style.display="";

}

function abilitaSel10(){
document.getElementById("nucleodieci").style.display="";

}



function abilita2(){
document.getElementById("nucleodue").style.display="";

}

function abilita3(){
document.getElementById("nucleotre").style.display="";
}

function abilita4(){
document.getElementById("nucleoquattro").style.display="";
}

function abilita5(){
document.getElementById("nucleocinque").style.display="";
}

function abilita6(){
document.getElementById("nucleosei").style.display="";
}

function abilita7(){
document.getElementById("nucleosette").style.display="";
}

function abilita8(){
document.getElementById("nucleootto").style.display="";
}

function abilita9(){
document.getElementById("nucleonove").style.display="";
}

function abilita10(){
document.getElementById("nucleodieci").style.display="";
}

//aggiunto da d.dauria
function abilitaNucleoIspettivo(form)
{
 if(form.nucleoIspettivo.value != -1)
     {
        sel3 = document.getElementById("nucleouno").style.display="";
        mostraCampo();
     }
 if(form.nucleoIspettivoDue.value != -1)
     {
        sel4 = document.getElementById("nucleodue").style.display="";
        
        mostraCampo2();
     }  
  if(form.nucleoIspettivoTre.value != -1)
     {
        document.getElementById("nucleotre").style.display="";
        mostraCampo3();
     }        
  if(form.nucleoIspettivoQuattro.value != -1)
     {
        document.getElementById("nucleoquattro").style.display="";
        mostraCampo4();
     }         
   if(form.nucleoIspettivoCinque.value != -1)
     {
        document.getElementById("nucleocinque").style.display="";
        mostraCampo5();
     }           
   if(form.nucleoIspettivoSei.value != -1)
     {
        document.getElementById("nucleosei").style.display="";
        mostraCampo6();
     }           
   if(form.nucleoIspettivoSette.value != -1)
     {
        document.getElementById("nucleosette").style.display="";
        mostraCampo7();
     }           
    if(form.nucleoIspettivoOtto.value != -1)
     {
        document.getElementById("nucleootto").style.display="";
        mostraCampo8();
     }          
    if(form.nucleoIspettivoNove.value != -1)
     {
        document.getElementById("nucleonove").style.display="";
        mostraCampo9();
     }          
    if(form.nucleoIspettivoDieci.value != -1)
     {
        document.getElementById("nucleodieci").style.display="";
        mostraCampo10();
     }          

}


function calcolaTotale(){
	var f=0;
	var g=0;
	var h=0;

	
	 if(document.getElementById("puntiFormali").value=="")
		 f=0;
	 else{
		 f = parseInt(document.getElementById("puntiFormali").value);

	 }
	if(document.getElementById("puntiSignificativi").value=="")
		g=0;
	else{
		 g = parseInt(document.getElementById("puntiSignificativi").value);
			
	}
	if(document.getElementById("puntiGravi").value=="")
	h=0;
else{

	 h = parseInt(document.getElementById("puntiGravi").value);
		
}
	 totale = document.getElementById("totale");
	
	totale.value=f+g+h;




	
}

function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
 
    if (form.tipoCampione.value == "-1") 
    {
      message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Controllo\" sia stato popolato\r\n");
      formTest = false;
    }
  

  

	if(!form.assignedDate.value == "" && !form.dataFineControllo.value == ""){

		
		if (controlloDataFine(form.assignedDate.value, form.dataFineControllo.value) ){
			
			message += label("","- La Data di Inizio Controllo non pu� essere successiva a quella di Fine Controllo.\r\n");
			 formTest = false;
			}
		}
    
    
    var numero = form.contributi.value;
	
	var arr_num = numero.split(".");
	
	if (numero.indexOf(",") != (-1))
	{
	    
		message += label("check.vigilanza.richiedente.selezionato","- Controllare che il sepratore delle cifre decimali per i contributi sia il punto anzich� la virgola \r\n");
	      formTest = false;
	}
    
    if(form.tipoCampione.value!="-1"){

		if(form.tipoCampione.value=="3"){ // se ho selezionato audit
		if(form.auditTipo.value==-1){
			 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
		      formTest = false;
			}else{
				if(form.auditTipo.value==1){
		if(form.tipoAudit.value=="-1"){
			 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
		      formTest = false;
			
		}else{

			if(form.tipoAudit.value=="2"){// se ho selezionato bpi

				if(form.bpi.value=="-1"){// se non ho slezionato niente da bp�i
					 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"BPI\" sia stato popolato\r\n");
				      formTest = false;
				}



				
				}else{
					if(form.tipoAudit.value=="3"){// se ho selezionato haccp

						if(form.haccp.value=="-1"){// se non ho slezionato niente da haccp
							 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"HACCP\" sia stato popolato\r\n");
						      formTest = false;
						}

					}

					}

			}}}

		}else{

			if(form.tipoCampione.value=="4"){// se ho selezionato ispezione

				if(form.tipoIspezione.value=="-1") // se non ho selezionato niente come tipo ispezione(in monitoraggio non , e sorveglianza)
			{
					 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Tipo di Ispezione\" sia stato popolato\r\n");
				      formTest = false;

					}

				else{
					getCodiceInternoTipoIspezione(value);
					if(codiceInternoTipoIspezione=="2a") // se  ho selezionato tipo ispezione in monitoraggio 
					{
						if(form.pianoMonitoraggio1.value=="-1" && form.pianoMonitoraggio2.value=="-1" && form.pianoMonitoraggio3.value=="-1"){//se non ho selezionato nessun piano di monitoraggio

							 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il \"Piano di monitoraggio\" sia stato selezionato\r\n");
						      formTest = false;

							}

						

					}
					else{

						if(codiceInternoTipoIspezione=="3a" || codiceInternoTipoIspezione==="11a") // se  ho selezionato tipo ispezione non in monitoraggio 
						{
							
							if(form.ispezione.value=="-1"){//se non ho selezionato nessun piano di monitoraggio

								 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"dalla lista di ispezioni non in monitoraggio\" sia stato popolato\r\n");
							      formTest = false;

								}
						}

					}


					}




			}

		


	}
	
}



	



    if((form.componenteNucleot.value!="" && form.componenteNucleot.value!="null") || document.getElementById("componenteNucleoSelect").value!="" || form.componenteNucleoS.value!=""){

		if(form.nucleoIspettivo.value=="-1" ){
			 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 1\" sia stato popolato\r\n");
		      formTest = false;
		}
		}
		
		if(form.nucleoIspettivo.value!="-1"){
		
			if(form.componenteNucleoS.value=="" && (form.componenteNucleot.value=="" || form.componenteNucleot.value=="null" ) && (document.getElementById("componenteNucleoSelect").value=="" || document.getElementById("componenteNucleoSelect").value=="null")){
			 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 1\" sia stato popolato\r\n");
		      formTest = false;
		}
		}


	

		
			if(form.nucleoIspettivoDue.value!="-1"){
				if(form.componenteNucleoDueS.value=="" && ( form.componenteNucleoDuet.value=="" || form.componenteNucleoDuet.value=="null" ) && document.getElementById("componenteNucleoSelect2").value==""){
				 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 2\" sia stato popolato\r\n");
			      formTest = false;
			}
			}


			if(form.componenteNucleoTreS.value!="" || (form.componenteNucleoTret.value!="null" && form.componenteNucleoTret.value!="" ) || document.getElementById("componenteNucleoSelect3").value!=""){
 
				if(form.nucleoIspettivoTre.value=="-1"){
					 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 3\" sia stato popolato\r\n");
				      formTest = false;
				}
				}

			
				if(form.nucleoIspettivoTre.value!="-1"){
					if(form.componenteNucleoTreS.value=="" && ( form.componenteNucleoTret.value=="" || form.componenteNucleoTret.value=="null") && document.getElementById("componenteNucleoSelect3").value==""){
					 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 3\" sia stato popolato\r\n");
				      formTest = false;
				}
				}


				

				
					if(form.nucleoIspettivoQuattro.value!="-1" ){
						if(form.componenteNucleoQuattroS.value=="" && ( form.componenteNucleoQuattrot.value=="" || form.componenteNucleoQuattrot.value=="null") && document.getElementById("componenteNucleoSelect4").value==""){
							
						 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 4\" sia stato popolato\r\n");
					      formTest = false;
					}
					}


					

					
						if(form.nucleoIspettivoCinque.value!="-1"){
							if(form.componenteNucleoCinqueS.value=="" && (form.componenteNucleoCinquet.value=="" || form.componenteNucleoCinquet.value=="null") && document.getElementById("componenteNucleoSelect5").value==""){
							 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 5\" sia stato popolato\r\n");
						      formTest = false;
						}
						}


						if(form.componenteNucleoSeiS.value!="" || (form.componenteNucleoSeit.value!="null" && form.componenteNucleoSeit.value!="") || document.getElementById("componenteNucleoSelect6").value!=""){

							if(form.nucleoIspettivoSei.value=="-1"){
								 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 6\" sia stato popolato\r\n");
							      formTest = false;
							}
							}

						
							if(form.nucleoIspettivoSei.value!="-1"){
								if(form.componenteNucleoSeiS.value=="" && (form.componenteNucleoSeit.value==""  || form.componenteNucleoSeit.value=="null") && document.getElementById("componenteNucleoSelect6").value==""){
								 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 6\" sia stato popolato\r\n");
							      formTest = false;
							}
							}


							if(form.componenteNucleoSetteS.value!="" || (form.componenteNucleoSettet.value!="null" && form.componenteNucleoSettet.value!="") || document.getElementById("componenteNucleoSelect7").value!=""){

								if(form.nucleoIspettivoSette.value=="-1"){
									 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 7\" sia stato popolato\r\n");
								      formTest = false;
								}
								}

							
								if(form.nucleoIspettivoSette.value!="-1"){
									if(form.componenteNucleoSetteS.value=="" && (form.componenteNucleoSettet.value=="" || form.componenteNucleoSettet.value=="null") && document.getElementById("componenteNucleoSelect7").value==""){
									 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 7\" sia stato popolato\r\n");
								      formTest = false;
								}
								}


								if(form.componenteNucleoOttoS.value!="" || (form.componenteNucleoOttot.value!="null" && form.componenteNucleoOttot.value!="" ) || document.getElementById("componenteNucleoSelect8").value!=""){

									if(form.nucleoIspettivoOtto.value=="-1"){
										 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 8\" sia stato popolato\r\n");
									      formTest = false;
									}
									}

								
									if(form.nucleoIspettivoOtto.value!="-1"){
										if(form.componenteNucleoOttoS.value=="" && (form.componenteNucleoOttot.value=="" || form.componenteNucleoOttot.value=="null") && document.getElementById("componenteNucleoSelect8").value==""){
										 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 8\" sia stato popolato\r\n");
									      formTest = false;
									}
									}


									if(form.componenteNucleoNoveS.value!="" || (form.componenteNucleoNovet.value!="null" && form.componenteNucleoNovet.value!="") || document.getElementById("componenteNucleoSelect9").value!=""){

										if(form.nucleoIspettivoNove.value=="-1"){
											 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 9\" sia stato popolato\r\n");
										      formTest = false;
										}
										}

									
										if(form.nucleoIspettivoNove.value!="-1"){
											if(form.componenteNucleoNoveS.value=="" && (form.componenteNucleoNovet.value=="" || form.componenteNucleoNovet.value=="null") && document.getElementById("componenteNucleoSelect9").value==""){
											 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 9\" sia stato popolato\r\n");
										      formTest = false;
										}
										}


										if(form.componenteNucleoDieciS.value!="" || (form.componenteNucleoDiecit.value!="null" && form.componenteNucleoDiecit.value!="") || document.getElementById("componenteNucleoSelect10").value!=""){

											if(form.nucleoIspettivoDieci.value=="-1"){
												 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nucleo Ispettivo 10\" sia stato popolato\r\n");
											      formTest = false;
											}
											}

										
											if(form.nucleoIspettivoDieci.value!="-1"){
												if(form.componenteNucleoDieciS.value=="" && (form.componenteNucleoDiecit.value=="" || form.componenteNucleoDiecit.value=="null") && document.getElementById("componenteNucleoSelect10").value==""){
												 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Componente Nucleo Ispettivo 10\" sia stato popolato\r\n");
											      formTest = false;
											}
											}
		

    
if (form.assignedDate.value == "") { 
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data\" sia stata selezionata\r\n");
    formTest = false;
  }else{
  	flag= controllo_data(form.assignedDate.value)
    
	if(flag==false){
		formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato Correttamente\r\n");
		     

		}else{

			if(controlloData(form.assignedDate.value)==false){
			formTest = false;
			  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Data Inizio Controllo\" sia stato Precedente o uguale alla data attuale\r\n");
			}   
			}

    }

if(form.tipoIspezione.value == "7")
{
if(form.distribuzionePartita.value!=-1)
{
	if(form.destinazioneDistribuzione.value==-1)
	{
		formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che i campi relativi a \"Destribuzione partita\" siano stati popolati\r\n");
		
	}

	if(form.subject!=null && form.subject.value=="")
	{
		formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Oggetto per lista distribuzione\" sia stato popolato\r\n");
		
	}
	

	if(document.getElementById("fileAllegare")!= null && document.getElementById("fileAllegare").value == "")
	{
		formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver Allegato la  \"Lista di distribuzione\" \r\n");
		
	}

	if(document.details.isAllegato.value=="false")
	{
		formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");
		
	}
    
}

if(form.esitoControllo.value==-1)
{
	formTest = false;
	  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver Selezionato un   \"Esito Controllo\" \r\n");
	
}
else
{
	 if(form.esitoControllo.value==7)
	 {
    	 if(form.dataddt.value == "")
    	 {
    		 formTest = false;
   		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver inserito \"La Data Per DDT \" \r\n");
   	
         }

    	 if(form.numDdt.value == "")
    	 {
    		 formTest = false;
   		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver inserito \"Numero DDT \" \r\n");
   	
         }
        	 
 	    
	 }
	 else
	 {
		 if(form.esitoControllo.value==8)
    	 {
        	
			 if(form.quantita.value == "")
        	 {
        		 formTest = false;
       		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver inserito La \"Quantita \" \r\n");
       	
             }
     	    
    	 }
		 else
		 {
			 if(form.esitoControllo.value==10 || form.esitoControllo.value==11)
	    	 {
				 if(form.quantitaBloccata.value == "")
            	 {
            		 formTest = false;
           		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver inserito La \"Quantita Bloccata \" \r\n");
           	
                 }
	     	    
	    	 }
    	 }


    }

    
}

if(form.idAllerta.value == "")
{
	formTest = false;
	message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di aver inserito Il \"Codice Allerta \" \r\n");
	
}

 messaggio = false;
value = document.details.azioniAdottate;
settatoAzione1 = false;
settatoAzione2 = false;
for(i=0;i<value.length;i++)
{
	if(value[i].selected==true)
	{

if(value[i].value == "-1" || value [i].value == "")
{
	formTest = false;
	message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");
	

}
else
{
	if(value[i].value == "3")
	{
		if(form.articoliAzioni.value=="-1")
		{
			 formTest = false;
	  		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che di non aver Selezionato un \"Articolo \"  \r\n");
	  	

		}
		else
		{
			messaggio=true;
		}

		
	}
}

	}

}
}

  
  
  if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
    return true;
  }
}

function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0){
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate(){
  document.forms['details'].assignedDate.value = '';
}  

function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
}



function provaFunzione(){
	
	var valore= document.details.tipoCampione.value;

	if(valore=="3"){


		document.details.auditTipo.style.display="";	
		document.details.tipoAudit.style.display="none";		
		document.details.tipoAudit.value="-1";
		document.details.tipoIspezione.style.display="none";
		document.details.tipoIspezione.value="-1";
		
		mostraMenuTipoIspezione();

	

	}
	else{
		if(valore=="4"){
			
			document.details.tipoAudit.style.display="none";
			document.details.auditTipo.style.display="none";	
				document.details.auditTipo.value="-1" ;
				document.details.tipoAudit.value="-1" ;
			 mostraMenu2();	 
		 document.details.tipoIspezione.style.display="block";
		 
				}
		else{
			
			document.details.tipoAudit.style.display="none";
			document.details.tipoAudit.value="-1" ;

			document.details.auditTipo.style.display="none";
			document.details.auditTipo.value="-1" ;
			 
			 
			

		 	document.details.tipoIspezione.value=-1;
					
			 document.details.tipoIspezione.style.display="none";
			
 			mostraMenuTipoIspezione();
	
							
			 mostraMenu2();	
		}


		}

	
}




function mostraMenuTipoIspezione(){

	var valore= document.details.tipoIspezione.value;
	
if(valore=="2"){

	 document.details.pianoMonitoraggio1.style.display="block";
	 document.details.pianoMonitoraggio2.style.display="block";
	 document.details.pianoMonitoraggio3.style.display="block";
	 document.details.ispezione.style.display="none";
	 //document.getElementById("label").style.display="none";
	 document.getElementById("hidden1").style.visibility="hidden";//display="none";
	 document.getElementById("hidden2").style.display="none";
	 document.getElementById("hidden3").style.display="none";
	 document.details.distribuzionePartita.value="-1";
	 document.details.destinazioneDistribuzione.style.display="none";
	 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
	 document.details.destinazioneDistribuzione.value = "-1";
	 
	 document.getElementById("tableHidden").style.display="none";
	document.getElementById("tossinfezione").style.display="none";
	document.getElementById("nonconformitaprec").style.display="none";
	
	document.getElementById("contributi").style.visibility="hidden";//display="none";
	 

}
else
	if(valore=="4"){
		document.getElementById("nonconformitaprec").style.display="none";
		document.details.pianoMonitoraggio1.style.display="none";
		document.details.pianoMonitoraggio2.style.display="none";
		document.details.pianoMonitoraggio3.style.display="none";
		document.details.ispezione.style.display="block";
		//document.getElementById("label").style.display="block";
		 document.getElementById("hidden1").style.visibility="hidden";//display="none";
		 document.getElementById("hidden2").style.display="none";
		 document.getElementById("hidden3").style.display="none";
		 document.details.distribuzionePartita.value="-1";
		 document.details.destinazioneDistribuzione.style.display="none";
		 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
		 document.details.destinazioneDistribuzione.value = "-1";
		 document.getElementById("contributi").style.visibility="hidden";//display="none";
		 document.getElementById("tableHidden").style.display="none";
		 document.getElementById("tossinfezione").style.display="none";
	}else{
		if(valore=="6" || valore=="9" || valore == "11"){
			document.getElementById("nonconformitaprec").style.display="none";
			document.details.pianoMonitoraggio1.style.display="none";
			document.details.pianoMonitoraggio2.style.display="none";
			document.details.pianoMonitoraggio3.style.display="none";
			document.details.ispezione.style.display="block";
			//document.getElementById("label").style.display="block";
			 document.getElementById("hidden1").style.visibility="hidden";//display="none";
			 document.getElementById("hidden2").style.display="none";
			 document.getElementById("hidden3").style.display="none";
			 document.details.distribuzionePartita.value="-1";
			 document.details.destinazioneDistribuzione.style.display="none";
			 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
			 document.details.destinazioneDistribuzione.value = "-1";
			 document.getElementById("contributi").style.visibility="hidden";//display="none";
			 document.getElementById("tableHidden").style.display="none";
				document.getElementById("tossinfezione").style.display="none";


		}

	else{
		if(valore=="7"){
			document.getElementById("nonconformitaprec").style.display="none";
			document.getElementById("contributi").style.visibility="hidden";//display="none";
			document.details.pianoMonitoraggio1.style.display="none";
			document.details.pianoMonitoraggio2.style.display="none";
			document.details.pianoMonitoraggio3.style.display="none";
			document.details.ispezione.style.display="none";
		//	document.getElementById("label").style.display="none";
			document.getElementById("hidden1").style.visibility="visible";//display="block";
			 document.getElementById("hidden2").style.display="";
			 document.getElementById("hidden3").style.display="none";
			 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
			 document.details.destinazioneDistribuzione.style.display="none";
			
			document.getElementById("tossinfezione").style.display="none";
			document.getElementById("contributi").style.visibility="visible";//display="block";
			 document.getElementById("tableHidden").style.display="";
			

			 
			
			}
		else{

			if(valore=="5"){
				document.getElementById("nonconformitaprec").style.display="none";
				document.details.pianoMonitoraggio1.style.display="none";
				document.details.pianoMonitoraggio2.style.display="none";
				document.details.pianoMonitoraggio3.style.display="none";
				document.details.ispezione.style.display="none";
			//	document.getElementById("label").style.display="none";
				 document.getElementById("hidden1").style.visibility="hidden";//display="none";
				 document.getElementById("hidden2").style.display="none";
				 document.getElementById("hidden3").style.display="none";
				 document.details.distribuzionePartita.value="-1";
				 document.details.destinazioneDistribuzione.style.display="none";
				 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
				 document.details.destinazioneDistribuzione.value = "-1";
				document.getElementById("contributi").style.visibility="visible";//display="block";
				 document.getElementById("tableHidden").style.display="none";
				document.getElementById("tossinfezione").style.display="none";
				}else{

					if(valore=="8"){
						document.getElementById("nonconformitaprec").style.display="";
						document.details.pianoMonitoraggio1.style.display="none";
						document.details.pianoMonitoraggio2.style.display="none";
						document.details.pianoMonitoraggio3.style.display="none";
						document.details.ispezione.style.display="none";
					//	document.getElementById("label").style.display="none";
						 document.getElementById("hidden1").style.visibility="hidden";//display="none";
						 document.getElementById("hidden2").style.display="none";
						 document.getElementById("hidden3").style.display="none";
						 document.details.distribuzionePartita.value="-1";
						 document.details.destinazioneDistribuzione.style.display="none";
						 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
						 document.details.destinazioneDistribuzione.value = "-1";
						document.getElementById("contributi").style.visibility="visible";//display="block";
						 document.getElementById("tableHidden").style.display="none";
						document.getElementById("tossinfezione").style.display="none";
						}


					else{
						
						if (valore == "16")
						{
						document.getElementById("nonconformitaprec").style.display="none";
						document.getElementById("tossinfezione").style.display="";
						
						document.details.pianoMonitoraggio1.style.display="none";
						document.details.pianoMonitoraggio2.style.display="none";
						document.details.pianoMonitoraggio3.style.display="none";
						document.details.ispezione.style.display="none";
					//	document.getElementById("label").style.display="none";
						 document.getElementById("hidden1").style.visibility="hidden";//display="none";
						 document.getElementById("hidden2").style.display="none";
						 document.getElementById("hidden3").style.display="none";
						 document.details.distribuzionePartita.value="-1";
						 document.details.destinazioneDistribuzione.style.display="none";
						 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
						 document.details.destinazioneDistribuzione.value = "-1";
						 document.getElementById("contributi").style.visibility="hidden";//display="none";
						 document.getElementById("tableHidden").style.display="none";
						}
						else
						{
							document.getElementById("nonconformitaprec").style.display="none";
						document.getElementById("tossinfezione").style.display="none";
						document.details.pianoMonitoraggio1.style.display="none";
						document.details.pianoMonitoraggio2.style.display="none";
						document.details.pianoMonitoraggio3.style.display="none";
						document.details.ispezione.style.display="none";
					//	document.getElementById("label").style.display="none";
						 document.getElementById("hidden1").style.visibility="hidden";//display="none";
						 document.getElementById("hidden2").style.display="none";
						 document.getElementById("hidden3").style.display="none";
						 document.details.distribuzionePartita.value="-1";
						 document.details.destinazioneDistribuzione.style.display="none";
						 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
						 document.details.destinazioneDistribuzione.value = "-1";
						 document.getElementById("contributi").style.visibility="hidden";//display="none";
						 document.getElementById("tableHidden").style.display="none";
						
						
						}
					}


					}
			
		
	
		}
	
	}
	}
	
}



function piani(){
	  
	  if(document.details.pianoMonitoraggio1.value!="-1"){
		  document.details.pianoMonitoraggio1.disabled="";
		  document.details.pianoMonitoraggio2.disabled="disabled";
		  document.details.pianoMonitoraggio3.disabled="disabled";
		}else {
		  document.details.pianoMonitoraggio1.disabled="";
		  document.details.pianoMonitoraggio2.disabled="";
		  document.details.pianoMonitoraggio3.disabled="";
		}
	if(document.details.pianoMonitoraggio2.value!="-1"){
			document.details.pianoMonitoraggio2.disabled="";
			document.details.pianoMonitoraggio1.disabled="disabled";
			document.details.pianoMonitoraggio3.disabled="disabled";
	}else if(document.details.pianoMonitoraggio3.value!="-1"){
		document.details.pianoMonitoraggio3.disabled="";
		document.details.pianoMonitoraggio1.disabled="disabled";
		document.details.pianoMonitoraggio2.disabled="disabled";
		}
		
}

function onloadAllerta(){
	var valore= document.details.tipoIspezione.value;
	if(valore=="7")
	{


		document.details.tipoIspezione.style.display="";
		document.getElementById("hidden1").style.visibility="visible";//display="block";
		 document.getElementById("hidden2").style.display="";
		 document.getElementById("hidden3").style.display="";
		 document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
		 document.details.destinazioneDistribuzione.style.display="";
		document.getElementById("contributi").style.visibility="visible";//display="block";
		document.getElementById("tableHidden").style.display="";//display="block";
		abilitaNucleoIspettivo();
		document.getElementById('rowAzione1').innerHTML='Quantita : '+document.details.quantitaBloccata.value+' '+document.details.unitaMisura.value;
		document.getElementById('misura').innerHTML=' '+document.details.unitaMisura.value;


	}
	


	
}

function mostraMenu2(){
	
	
	var valore= document.forms['details'].tipoAudit.value;
	if(valore=="2"){
		
	 document.forms['details'].haccp.style.display="none";
	 document.forms['details'].bpi.style.display="block";
	 document.details.ispezione.style.display="none";
	 //document.getElementById("esitoControllo").style.display="block";
	 mostraMenuTipoIspezione();
		document.details.tipoIspezione.style.display="none";
				}else
			if(valore=="3"){
				
				 document.forms['details'].bpi.style.display="none";
				 document.forms['details'].haccp.style.display="block";
				// document.getElementById("esitoControllo").style.display="block";
				 mostraMenuTipoIspezione();
					document.details.tipoIspezione.style.display="none";
			}else{
		
				 document.details.bpi.style.display="none";
				 document.details.haccp.style.display="none";
				// document.getElementById("label1").style.display="none";
				// document.getElementById("label2").style.display="none";
				 //document.getElementById("esitoControllo").style.display="none";
				 document.getElementById("hidden1").style.visibility="hidden";
				 document.getElementById("hidden2").style.display="none";
				 document.getElementById("hidden3").style.display="none";
				 document.getElementById("tableHidden").style.display="none";
				 
				 document.details.destinazioneDistribuzione.style.display="none";
				
				 document.getElementById("contributi").style.visibility="hidden";
				 //document.getElementById("esitoControllo").style.display="none";
				

			}
}

function abilitaCodiceAllerta(){

	if(document.forms['details'].tipoIspezione.value=="7"){
		document.getElementById("hidden1").style.visibility="visible";

		}
	
	
}

function abilitaDestinazione()
{
	if (document.details.distribuzionePartita.value == -1)
	{
		 document.details.destinazioneDistribuzione.style.display="none";
		 document.details.destinazioneDistribuzione.value = "-1";
		 document.getElementById("hidden3").style.display="none";
		 document.details.idFile.value = "-1";
	}
	else
	{
		 document.details.destinazioneDistribuzione.style.display="block";
		 document.getElementById("hidden3").style.display="";
	}

}


function azioneSuEsitoControllo()
{
	value = document.details.esitoControllo.value;

	if(value == "7")
	{
		document.getElementById("hiddenEsito1").style.display="";
		document.getElementById("hiddenEsito2").style.display="none";
		document.getElementById("hiddenEsito3").style.display="none";
		document.getElementById("rowAzione1").innerHTML="";
		document.getElementById("hiddenAzione1").style.display="none"
		
	}
	else
	{
		if(value == "8")
		{
			
			document.getElementById("hiddenEsito1").style.display="none";
			document.getElementById("hiddenEsito2").style.display="";
			document.getElementById("hiddenEsito3").style.display="none";
			document.getElementById("rowAzione1").innerHTML="";
			document.getElementById('misura').innerHTML=""+document.details.unitaMisura.value;
			document.getElementById("hiddenAzione1").style.display="none"
		
		}
		else
		{
			if(value == "10" || value == "11")
			{
				document.getElementById("hiddenEsito1").style.display="none";
				document.getElementById("hiddenEsito2").style.display="none";
				document.getElementById("hiddenEsito3").style.display="";
				value = document.details.azioniAdottate;
				document.getElementById('misura1').innerHTML=' '+document.details.unitaMisura.value;
				for(i=0;i<value.length;i++)
				{
					if(value[i].selected==true)
					{

				if(value[i].value == "2")
				{
				document.getElementById("rowAzione1").innerHTML="Quantita : "+document.details.quantitaBloccata.value+" "+document.details.unitaMisura.value
				
				document.getElementById("hiddenAzione1").style.display=""
				}}}
			
			}
			else
			{
				document.getElementById("hiddenEsito1").style.display="none";
				document.getElementById("hiddenEsito2").style.display="none";
				document.getElementById("hiddenEsito3").style.display="none";
				document.getElementById("rowAzione1").innerHTML="";
				document.getElementById("hiddenAzione1").style.display="none"
				
			}
			
		}


	}
}



function azioneSuAzioniAdottate()
{
	value = document.details.azioniAdottate;
	settatoAzione1 = false;
	settatoAzione2 = false;
	for(i=0;i<value.length;i++)
	{
		if(value[i].selected==true)
		{

	if(value[i].value == "2")
	{
		

		if(document.details.esitoControllo.value == "10" || document.details.esitoControllo.value == "11" )
		{
			document.getElementById("hiddenAzione1").style.display="";
			document.getElementById("rowAzione1").innerHTML="Quantita : "+document.details.quantitaBloccata.value+" "+document.details.unitaMisura.value
		}
			//document.getElementById("hiddenAzione2").style.display="none";
		settatoAzione1 = true;
	
	}
	else
	{
		if(value[i].value == "3")
		{
			//document.getElementById("hiddenAzione1").style.display="none";
			document.getElementById("hiddenAzione2").style.display="";
			
			settatoAzione2=true;
		}
	}
	}
		
	}
	if(settatoAzione1==false && settatoAzione2==false)
	{
		document.getElementById("hiddenAzione1").style.display="none";
		document.getElementById("hiddenAzione2").style.display="none";
	}
	if(settatoAzione1==false)
	{	
		document.getElementById("hiddenAzione1").style.display="none";

	}
	if(settatoAzione2==false)
	{	
		document.getElementById("hiddenAzione2").style.display="none";

	}

}

function abilitaSistemaAllarmeRabido()

{
	var valore= document.details.tipoIspezione.value;
	if(valore=="7")
	{
		

		document.details.tipoIspezione.style.display="";
		document.getElementById("tableHidden").style.display="";
		document.getElementById("hidden1").style.visibility="visible";//display="block";
		 document.getElementById("hidden2").style.display="";

		 if(document.details.distribuzionePartita != -1 )
		 {
		 	document.getElementById("hidden3").style.display="";
		 	document.details.destinazioneDistribuzione.style.background = "#FFFFFF";
		 	document.details.destinazioneDistribuzione.style.display="";
		 }
		 abilitaDestinazione();

		document.getElementById("contributi").style.visibility="visible";//display="block";
		//display="block";
		abilitaCampoNote();
		
		document.getElementById('rowAzione1').innerHTML='Quantita : '+document.details.quantitaBloccata.value+' '+document.details.unitaMisura.value;
		document.getElementById('misura').innerHTML=' '+document.details.unitaMisura.value;
		azioneSuEsitoControllo();
		azioneSuAzioniAdottate();

		
	}
	

}


