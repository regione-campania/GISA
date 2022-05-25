/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function abilitaMatriciCanili()
{
	checkMatriciCanili = document.getElementById("checkMatriciCanili");
    //sel2 = document.getElementById("lookupVegetale");
    if(checkMatriciCanili.checked==true)
    { 
    document.getElementById("matriciCaniliSelect").style.display="block";
    document.getElementById("noteMatriciCanili").style.display="block";

    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque();
   
 	disabilitaMaterialiAlimenti();
 	//disabilitaDolciumi();
 	//disabilitaGelati();
    disabilitaAdditivi();
    disabilitaAltriAlimenti();
    disabilitaBevande();
    disabilitaMangimi();
    }
    else
    { 
    	abilitaAltriAlimenti();
    	abilitaAcqueCheck();
    	abilitaCompostiVegetaleCheck();
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
		abilitaAdditivi();
    	abilitaMaterialiAlimenti();
    	abilitaBevandeCheck();
    	abilitaMangimiCheck();
    	//abilitaDolciumiCheck();
        //abilitaGelatiCheck();
        
    	document.getElementById("matriciCaniliSelect").style.display="none";
        document.getElementById("noteMatriciCanili").style.display="none";

        document.getElementById("matriciCaniliSelect").value="-1"
        document.getElementById("noteMatriciCanili").value="";
        
     //abilitaAnimaliCheck();
     //abilitaCompostiCheck();
          
    }  
}

function disabilitaMatriciCanili(){
	if (document.getElementById("checkMatriciCanili")!=null)
	document.getElementById("checkMatriciCanili").disabled=true;
	
}

function abilitaMatriciCaniliCheck(){
	if (document.getElementById("checkMatriciCanili")!=null)
	document.getElementById("checkMatriciCanili").disabled=false; 
	
}
function impostaResponsabilita(form){

	if (form!='addticket')
	{
	punteggio=document.forms[form].esito_punteggio.value;
	if(punteggio==0){
		document.forms[form].esito_responsabilita_positivita.value="1";
	}else
		document.forms[form].esito_responsabilita_positivita.value="-1";
	}

}


function mostraDescrizioneNonAccettato(){

	
	if(document.getElementById('esito_id').value=='4'){
		document.getElementById("campo_desc_non_accettato").style.display="";

	}else{
		document.getElementById("campo_desc_non_accettato").style.display="none";
	
	}

}

function mostraSottoCategoria(form){

	if(document.forms[form].tipoAlimento.value=="0")
		var righe=document.forms[form].alimentiOrigineVegetaleValoriNonTrasformati.value;
	else
		var righe=document.forms[form].alimentiOrigineVegetaleValoriTrasformati.value;




	if(righe=="1" ){
		document.forms[form].fruttaFresca.style.display="block";
		document.forms[form].fruttaSecca.style.display="none";
		document.forms[form].ortaggi.style.display="none";
		document.forms[form].funghi.style.display="none";
		document.forms[form].derivati.style.display="none";
		document.forms[form].conservati.style.display="none";
		document.forms[form].grassi.style.display="none";
		document.forms[form].vino.style.display="none";
		document.forms[form].zuppe.style.display="none";
		document.forms[form].pasta.style.display="none";

	}else{
		if(righe=="2" ){

			document.forms[form].fruttaFresca.style.display="none";
			document.forms[form].fruttaSecca.style.display="block";
			document.forms[form].ortaggi.style.display="none";
			document.forms[form].funghi.style.display="none";
			document.forms[form].derivati.style.display="none";
			document.forms[form].conservati.style.display="none";
			document.forms[form].grassi.style.display="none";
			document.forms[form].vino.style.display="none";
			document.forms[form].zuppe.style.display="none";
			document.forms[form].pasta.style.display="none";


		}else{
			if(righe=="4" ){
				document.forms[form].fruttaFresca.style.display="none";
				document.forms[form].fruttaSecca.style.display="none";
				document.forms[form].ortaggi.style.display="block";
				document.forms[form].funghi.style.display="none";
				document.forms[form].derivati.style.display="none";
				document.forms[form].conservati.style.display="none";
				document.forms[form].grassi.style.display="none";
				document.forms[form].vino.style.display="none";
				document.forms[form].zuppe.style.display="none";
				document.forms[form].pasta.style.display="none";

			}
			else{
				if(righe=="5" ){
					document.forms[form].fruttaFresca.style.display="none";
					document.forms[form].fruttaSecca.style.display="none";
					document.forms[form].ortaggi.style.display="none";
					document.forms[form].funghi.style.display="block";
					document.forms[form].derivati.style.display="none";
					document.forms[form].conservati.style.display="none";
					document.forms[form].grassi.style.display="none";
					document.forms[form].vino.style.display="none";
					document.forms[form].zuppe.style.display="none";
					document.forms[form].pasta.style.display="none";

				}else{
					if(righe=="6" ){
						document.forms[form].fruttaFresca.style.display="none";
						document.forms[form].fruttaSecca.style.display="none";
						document.forms[form].ortaggi.style.display="none";
						document.forms[form].funghi.style.display="none";
						document.forms[form].conservati.style.display="block";
						document.forms[form].derivati.style.display="none";
						document.forms[form].grassi.style.display="none";
						document.forms[form].vino.style.display="none";
						document.forms[form].zuppe.style.display="none";
						document.forms[form].pasta.style.display="none";

					}else{
						if(righe=="7" ){
							document.forms[form].fruttaFresca.style.display="none";
							document.forms[form].fruttaSecca.style.display="none";
							document.forms[form].ortaggi.style.display="none";
							document.forms[form].funghi.style.display="none";
							document.forms[form].derivati.style.display="block";
							document.forms[form].conservati.style.display="none";
							document.forms[form].grassi.style.display="none";
							document.forms[form].vino.style.display="none";
							document.forms[form].zuppe.style.display="none";
							document.forms[form].pasta.style.display="none";

						}else{

							if(righe=="8" ){
								document.forms[form].fruttaFresca.style.display="none";
								document.forms[form].fruttaSecca.style.display="none";
								document.forms[form].ortaggi.style.display="none";
								document.forms[form].funghi.style.display="none";
								document.forms[form].derivati.style.display="none";
								document.forms[form].conservati.style.display="none";
								document.forms[form].grassi.style.display="block";
								document.forms[form].vino.style.display="none";
								document.forms[form].zuppe.style.display="none";
								document.forms[form].pasta.style.display="none";

							}else{
								if(righe=="9" ){
									document.forms[form].fruttaFresca.style.display="none";
									document.forms[form].fruttaSecca.style.display="none";
									document.forms[form].ortaggi.style.display="none";
									document.forms[form].funghi.style.display="none";
									document.forms[form].derivati.style.display="none";
									document.forms[form].conservati.style.display="none";
									document.forms[form].grassi.style.display="none";
									document.forms[form].vino.style.display="block";
									document.forms[form].zuppe.style.display="none";
									document.forms[form].pasta.style.display="none";

								}else{

									if(righe=="11" ){
										document.forms[form].fruttaFresca.style.display="none";
										document.forms[form].fruttaSecca.style.display="none";
										document.forms[form].ortaggi.style.display="none";
										document.forms[form].funghi.style.display="none";
										document.forms[form].derivati.style.display="none";
										document.forms[form].conservati.style.display="none";
										document.forms[form].grassi.style.display="none";
										document.forms[form].vino.style.display="none";
										document.forms[form].zuppe.style.display="block";
										document.forms[form].pasta.style.display="none";

									}else{
										if(righe=="14" ){
												document.forms[form].grassi.style.display="none";
										document.forms[form].vino.style.display="none";
										document.forms[form].zuppe.style.display="none";
										document.forms[form].fruttaFresca.style.display="none";
										document.forms[form].fruttaSecca.style.display="none";
										document.forms[form].ortaggi.style.display="none";
										document.forms[form].funghi.style.display="none";
										document.forms[form].derivati.style.display="none";
										document.forms[form].conservati.style.display="none";
										document.forms[form].pasta.style.display="";
											
											
										}
										else
										{
											document.forms[form].grassi.style.display="none";
										document.forms[form].vino.style.display="none";
										document.forms[form].zuppe.style.display="none";
										document.forms[form].fruttaFresca.style.display="none";
										document.forms[form].fruttaSecca.style.display="none";
										document.forms[form].ortaggi.style.display="none";
										document.forms[form].funghi.style.display="none";
										document.forms[form].derivati.style.display="none";
										document.forms[form].conservati.style.display="none";
											document.forms[form].pasta.style.display="none";
										}
										

									}

								}

							}

						}

					}

				}

			}


		}

	}
}


function abilitaAdditivi(){

	document.getElementById("additivi").disabled=false;

}

function abilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=false;

}
function disabilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=true;

}

function abilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=false;

}

function disabilitaAdditivi(){

	document.getElementById("additivi").disabled=true;

}


function disabilitaBevande(){

	document.getElementById("bevande").disabled=true;

}


function disabilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=true;

}


function disabilitaAcque(){
	document.getElementById("acqua").disabled=true;

}


function mostraUlteririNote(form){


	value=document.forms[form].motivazione_campione.value;

	getCodiceInternoTipoIspezione(value);
	
	
	if(codiceInternoTipoIspezione=='22a'){//per altro

		document.getElementById("noteMotivazione").style.display="";
		document.getElementById("pianomonitoraggio").style.display="none";

		document.forms[form].motivazione_piano_campione.value='-1';
		mostraspecie();
		document.getElementById('specie_animali').value='-1';
		mostracategorieSpecie();
		
	}else{
		if(codiceInternoTipoIspezione=='2a'){//per piano di monitoraggio

			document.getElementById("pianomonitoraggio").style.display=""; 
			

		}
		else
		{
		document.getElementById("noteMotivazione").style.display="none";
		document.getElementById("pianomonitoraggio").style.display="none";
		document.forms[form].motivazione_piano_campione.value='-1';
		mostraspecie();
		document.getElementById('specie_animali').value='-1';
		mostracategorieSpecie();
		}
	
	}


}

function abilitaAcqueCheck(){
	document.getElementById("acqua").disabled=false; 

}

function abilitaBevandeCheck(){
	document.getElementById("bevande").disabled=false; 

}

function abilitaFlag(){
	if(document.getElementById("esito_allarme_rapido").checked==true){
		document.getElementById("esito_segnalazione_informazioni").disabled=true;
	}else{

		if(document.getElementById("esito_segnalazione_informazioni").checked==true){
			document.getElementById("esito_allarme_rapido").disabled=true;
		}else{
			document.getElementById("esito_segnalazione_informazioni").disabled=false;
			document.getElementById("esito_allarme_rapido").disabled=false;

		}




	}


}

function abilitatipoAdditivi(){
	var check=document.getElementById("additivi");

	if(check.checked){
		disabilitaCompostiVegetale();
		disabilitaComposti();

		disabilitaAcque();

		disabilitaBevande();

		disabilitaCompostiAnimale();

		disabilitaMaterialiAlimenti();
		//disabilitaDolciumi();
		//disabilitaGelati();
		document.getElementById("noteadditivi").style.display="block";

	}else{
		abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		abilitaCompostiCheck();
		abilitaAcqueCheck();
		abilitaBevandeCheck();	
		//abilitaDolciumiCheck();	
		//abilitaGelatiCheck();
		abilitaMaterialiAlimenti();

		document.getElementById("noteadditivi").style.display="none";	
		document.getElementById("noteadditivi").value="";
	}



}




function abilitatipomaterialiAlimenti(){

	var check=document.getElementById("materialialimenti");
	if(check.checked) {
		disabilitaCompostiVegetale();
		disabilitaComposti();

		disabilitaAcque();

		disabilitaBevande();
		//disabilitaDolciumi();
		//disabilitaGelati();
		disabilitaCompostiAnimale();
		disabilitaAltriAlimenti();
		disabilitaAdditivi();
		document.getElementById("notematerialialimenti").style.display="block";
	}else{

		abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		abilitaAltriAlimenti();
		abilitaCompostiCheck();
		abilitaAcqueCheck();
		abilitaBevandeCheck();	
		abilitaAdditivi();
		//abilitaGelatiCheck();	
		//abilitaDolciumiCheck();	

		document.getElementById("notematerialialimenti").style.display="none";
		document.getElementById("notematerialialimenti").value="";

	}

}

function abilitaTipoAlimentoAnimale(form){


	var check=document.getElementById("alimentiOrigineAnimale");

	if(check.checked==true){
		document.getElementById("tipoAlimentiAnimali").style.display="block";
		disabilitaCompostiVegetale();

		document.forms[form].alimentiOrigineAnimaleTrasformati.value="-1";

		//disabilitaDolciumi();
		//disabilitaGelati();
		disabilitaComposti();

		disabilitaAcque();
		disabilitaAltriAlimenti();
		disabilitaBevande();

		disabilitaAdditivi();

		disabilitaMaterialiAlimenti();

	}
	else{

		abilitaAltriAlimenti();
		document.getElementById("notealimenti").style.display="none";
		document.getElementById("tipoAlimentiAnimali").style.display="none";
		abilitaCompostiVegetaleCheck();
		abilitaCompostiCheck();
		abilitaAcqueCheck();
		abilitaBevandeCheck();	
		abilitaAdditivi();
		abilitaMaterialiAlimenti();
		//abilitaGelatiCheck();	
		//abilitaDolciumiCheck();	
		document.forms[form].TipoSpecie_uova.style.display="none";
		document.forms[form].TipoSpecie_latte.style.display="none";
		document.forms[form].alimentiOrigineAnimaleNonTrasformati.style.display="none";
		document.forms[form].alimentiOrigineAnimaleNonTrasformati.value="-1";
		document.forms[form].alimentiOrigineAnimaleTrasformati.style.display="none"
			document.forms[form].alimentiOrigineAnimaleTrasformati.value="-1";
		document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"; 

	}

}







function disabilitaTipochimico(form){

	//document.getElementById("nascosto2").style.display="none";
	document.forms[form].TipoCampione_sottochimico.style.display="none";
	document.forms[form].TipoCampione_sottochimico2.style.display="none";
	document.forms[form].TipoCampione_sottochimico3.style.display="none";
	document.forms[form].TipoCampione_sottochimico4.style.display="none";
	document.forms[form].TipoCampione_sottochimico5.style.display="none";
}

function abilitaLista_tipoAnalisi(form){

	var tipo=document.forms[form].TipoCampione.value;

	if(tipo==1){
		//document.getElementById("nascosto1").style.display="block";
		document.forms[form].TipoCampione_batteri.style.display="block";
		document.forms[form].TipoCampione_virus.style.display="none";
		document.forms[form].TipoCampione_parassiti.style.display="none";
		document.forms[form].TipoCampione_istologico.style.display="none";
		document.forms[form].TipoCampione_sierologico.style.display="none";
		document.forms[form].TipoCampione_chimico.style.display="none";

		disabilitaTipochimico(form);
		return;

	}
	if(tipo==2){
		//document.getElementById("nascosto1").style.display="block";
		document.forms[form].TipoCampione_batteri.style.display="none";
		document.forms[form].TipoCampione_virus.style.display="block";
		document.forms[form].TipoCampione_parassiti.style.display="none";
		document.forms[form].TipoCampione_chimico.style.display="none";
		document.forms[form].TipoCampione_istologico.style.display="none";
		document.forms[form].TipoCampione_sierologico.style.display="none";

		disabilitaTipochimico(form);
		return;


	}

	if(tipo==4){
		//	document.getElementById("nascosto1").style.display="block";
		document.forms[form].TipoCampione_batteri.style.display="none";
		document.forms[form].TipoCampione_virus.style.display="none";
		document.forms[form].TipoCampione_parassiti.style.display="block";
		document.forms[form].TipoCampione_chimico.style.display="none";
		document.forms[form].TipoCampione_istologico.style.display="none";
		document.forms[form].TipoCampione_sierologico.style.display="none";

		disabilitaTipochimico(form);
		return;

	}

	if(tipo==5){
		//	document.getElementById("nascosto1").style.display="block";
		document.forms[form].TipoCampione_batteri.style.display="none";
		document.forms[form].TipoCampione_virus.style.display="none";
		document.forms[form].TipoCampione_parassiti.style.display="none";
		document.forms[form].TipoCampione_istologico.style.display="none";
		document.forms[form].TipoCampione_sierologico.style.display="none";
		document.forms[form].TipoCampione_chimico.style.display="block";
		return;

	}
	
	if(tipo==26){
		//document.getElementById("nascosto1").style.display="block";
		document.forms[form].TipoCampione_batteri.style.display="none";
		document.forms[form].TipoCampione_virus.style.display="none";
		document.forms[form].TipoCampione_parassiti.style.display="none";
		document.forms[form].TipoCampione_istologico.style.display="block";
		document.forms[form].TipoCampione_sierologico.style.display="none";
		document.forms[form].TipoCampione_chimico.style.display="none";

		disabilitaTipochimico(form);
		return;

	}
	
	if(tipo==27){
		//document.getElementById("nascosto1").style.display="block";
		document.forms[form].TipoCampione_batteri.style.display="none";
		document.forms[form].TipoCampione_virus.style.display="none";
		document.forms[form].TipoCampione_parassiti.style.display="none";
		document.forms[form].TipoCampione_istologico.style.display="none";
		document.forms[form].TipoCampione_sierologico.style.display="block";
		document.forms[form].TipoCampione_chimico.style.display="none";

		disabilitaTipochimico(form);
		return;

	}

	//document.getElementById("nascosto1").style.display="none";
	disabilitaTipochimico(form);
	document.forms[form].TipoCampione_batteri.style.display="none";
	document.forms[form].TipoCampione_virus.style.display="none";
	document.forms[form].TipoCampione_parassiti.style.display="none";
	document.forms[form].TipoCampione_chimico.style.display="none";
	document.forms[form].TipoCampione_istologico.style.display="none";
	document.forms[form].TipoCampione_sierologico.style.display="none";

	return;



}

function mostraFollowUP(form){

	
	if(document.forms[form].EsitoCampione.value=="1"){

		document.getElementById("followup").style.display="";

	}else{

		document.getElementById("followup").style.display="none";

	}


}


function abilitaLista_tipoChimico(form){

	var tipo=document.forms[form].TipoCampione_chimico.value;

	if(tipo==1){
		//	document.getElementById("nascosto2").style.display="block";
		document.forms[form].TipoCampione_sottochimico.style.display="block";
		document.forms[form].TipoCampione_sottochimico2.style.display="none";
		document.forms[form].TipoCampione_sottochimico3.style.display="none";
		document.forms[form].TipoCampione_sottochimico4.style.display="none";
		document.forms[form].TipoCampione_sottochimico5.style.display="none";

		return;

	}
	if(tipo==2){
		//document.getElementById("nascosto2").style.display="block";
		document.forms[form].TipoCampione_sottochimico.style.display="none";
		document.forms[form].TipoCampione_sottochimico2.style.display="block";
		document.forms[form].TipoCampione_sottochimico3.style.display="none";
		document.forms[form].TipoCampione_sottochimico4.style.display="none";
		document.forms[form].TipoCampione_sottochimico5.style.display="none";
		return;


	}

	if(tipo==3){
		//document.getElementById("nascosto2").style.display="block";
		document.forms[form].TipoCampione_sottochimico.style.display="none";
		document.forms[form].TipoCampione_sottochimico2.style.display="none";
		document.forms[form].TipoCampione_sottochimico3.style.display="block";
		document.forms[form].TipoCampione_sottochimico4.style.display="none";
		document.forms[form].TipoCampione_sottochimico5.style.display="none";
		return;


	}

	if(tipo==4){
		//	document.getElementById("nascosto2").style.display="block";
		document.forms[form].TipoCampione_sottochimico.style.display="none";
		document.forms[form].TipoCampione_sottochimico2.style.display="none";
		document.forms[form].TipoCampione_sottochimico3.style.display="none";
		document.forms[form].TipoCampione_sottochimico4.style.display="block";
		document.forms[form].TipoCampione_sottochimico5.style.display="none";
		return;

	}
	if(tipo==5){
		//document.getElementById("nascosto2").style.display="block";
		document.forms[form].TipoCampione_sottochimico.style.display="none";
		document.forms[form].TipoCampione_sottochimico2.style.display="none";
		document.forms[form].TipoCampione_sottochimico3.style.display="none";
		document.forms[form].TipoCampione_sottochimico4.style.display="none";
		document.forms[form].TipoCampione_sottochimico5.style.display="block";
		return;
	}


	if(tipo==-1){
		//	document.getElementById("nascosto2").style.display="none";
		document.forms[form].TipoCampione_sottochimico.style.display="none";
		document.forms[form].TipoCampione_sottochimico2.style.display="none";
		document.forms[form].TipoCampione_sottochimico3.style.display="none";
		document.forms[form].TipoCampione_sottochimico4.style.display="none";
		document.forms[form].TipoCampione_sottochimico5.style.display="none";
		return;
	}
	else
	{

		document.forms[form].TipoCampione_sottochimico.style.display="none";
		document.forms[form].TipoCampione_sottochimico2.style.display="none";
		document.forms[form].TipoCampione_sottochimico3.style.display="none";
		document.forms[form].TipoCampione_sottochimico4.style.display="none";
		document.forms[form].TipoCampione_sottochimico5.style.display="none";
	}

	return;

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

function abilitaalimentinonAnimali(form){


	if (document.getElementById("alimentinonAnimali").checked==true)
	{


		document.getElementById("alimentinonanimalicella").style.display = "block";

		document.forms[form].altrialimenti.style.display = "block";

		disabilitaCompostiAnimale();
		disabilitaBevande();
		disabilitaCompostiVegetale();
		disabilitaAcque();
		disabilitaAdditivi();
		disabilitaMaterialiAlimenti();

		disabilitaComposti();
		//disabilitaDolciumi();
		//disabilitaGelati();


	}
	else
	{

		abilitaCompostiCheck();
		abilitaBevandeCheck();
		abilitaMaterialiAlimenti();
		abilitaAdditivi();
		abilitaAcqueCheck()
		document.getElementById("alimentinonanimalicella").style.display = "none";

		abilitaCompostiVegetaleCheck();
		//abilitaDolciumiCheck();
		//abilitaGelatiCheck();
		abilitaAnimaliCheck();
	} 


}


//aggiunto da d.dauria
function abilitaTestoAlimentoComposto()
{


	if (document.getElementById("alimentiComposti").checked==true)
	{


		document.getElementById("testoAlimentoComposto").style.display = "block";

		disabilitaCompostiAnimale();
		disabilitaBevande();
		disabilitaCompostiVegetale();
		disabilitaAcque();
		disabilitaAdditivi();
		disabilitaMaterialiAlimenti();
		//disabilitaDolciumi();
		//disabilitaGelati();
		disabilitaAltriAlimenti();

	}
	else
	{

		abilitaAltriAlimenti();
		abilitaCompostiVegetaleCheck();
		abilitaBevandeCheck();
		abilitaMaterialiAlimenti();
		abilitaAdditivi();
		abilitaAcqueCheck()
		document.getElementById("testoAlimentoComposto").style.display = "none";
		abilitaCompostiVegetaleCheck();
//		abilitaDolciumiCheck();
//		abilitaGelatiCheck();
		abilitaAnimaliCheck();
	} 
}

function abilitaCheckSegnalazione()
{
	allerta = document.getElementById("allerta");
	nonConformita = document.getElementById("nonConformita");
//	if(nonConformita.checked)
//	{ allerta.checked = false;
//	}
}


function abilitaCheckAllerta()
{

//	if(allerta.checked)
//	{ nonConformita.checked = false;
//	}
}

function controlloLookup(form){
	//aggiunto per positivit�
	document.getElementById("note").style.visibility="hidden";

	document.getElementById("note_etichetta").style.visibility="hidden";

	

}
//aggiunto per positivit�
function abilitaNote(form)
{
	if(form.conseguenzePositivita.value == 4)
	{
		document.getElementById("note").style.visibility="visible";

		document.getElementById("note_etichetta").style.visibility="visible";

	} 
	else
	{
		document.getElementById("note").style.visibility="hidden";

		document.getElementById("note_etichetta").style.visibility="hidden";

	}

}

function abilitaLookupOrigineAnimale(form)
{

	alimentiOrigine = document.forms[form].tipoAlimentiAnimali.value;

	document.getElementById("lookupNonTrasformati").style.visibility="visible";
	//sel = document.getElementById("lookupNonTrasformati");

	//sel3 = document.getElementById("lookupTrasformati");
	if(alimentiOrigine==1)
	{ 


		document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"

			document.forms[form].alimentiOrigineAnimaleNonTrasformati.style.display="block";
		document.forms[form].alimentiOrigineAnimaleTrasformati.style.display="none"
			document.forms[form].alimentiOrigineAnimaleTrasformati.value="-1";


		disabilitaCompostiVegetale();

		disabilitaComposti();
	}

	else{
		if(alimentiOrigine=="2"){
			document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";

			document.forms[form].alimentiOrigineAnimaleNonTrasformati.style.display="none";
			document.forms[form].alimentiOrigineAnimaleNonTrasformati.value="-1";
			document.forms[form].alimentiOrigineAnimaleTrasformati.style.display="block";
			document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
			document.forms[form].TipoSpecie_latte.style.display="none";
			document.forms[form].TipoSpecie_uova.style.display="none";
			document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
			disabilitaCompostiVegetale();

			disabilitaComposti();

		}
		else
		{ 
			document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
			document.forms[form].TipoSpecie_latte.style.display="none";
			document.forms[form].TipoSpecie_uova.style.display="none";
			document.forms[form].TipoSpecie_latte.style.value="-1";
			document.forms[form].TipoSpecie_uova.value="-1";
			document.forms[form].alimentiOrigineAnimaleNonTrasformati.style.display="none";
			document.forms[form].alimentiOrigineAnimaleNonTrasformati.value="-1";
			document.forms[form].alimentiOrigineAnimaleTrasformati.value="-1";
			document.forms[form].alimentiOrigineAnimaleTrasformati.style.display="none"
				document.forms[form].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
			disabilitaCompostiVegetale();

			disabilitaComposti();

			document.getElementById("lookupNonTrasformati").style.visibility="hidden";


		} 

	}


}


//magic
function abilitaSpecie(form)
{
	if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
	{
		form.alimentiOrigineAnimaleTrasformati.value="-1";
		sel2 = document.form.alimentiOrigineAnimaleNonTrasformatiValori;//
		sel2.style.display="block"
			form.TipoSpecie_uova.style.display="none";
		form.TipoSpecie_uova.value="-1";
		form.TipoSpecie_latte.value="-1";
		form.TipoSpecie_latte.style.display="none";
		document.getElementById("notealimenti").style.display="block";
	} 
	else
	{

		if(form.alimentiOrigineAnimaleNonTrasformati.value==8){
			form.alimentiOrigineAnimaleTrasformati.value="-1";
			form.TipoSpecie_uova.value="-1";
			sel2 = document.form.alimentiOrigineAnimaleNonTrasformatiValori;//
			sel2.style.display="none"
				document.form.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
			form.TipoSpecie_latte.style.display="block";
			form.TipoSpecie_uova.style.display="none";
			document.getElementById("notealimenti").style.display="block";

		}else{
			if(form.alimentiOrigineAnimaleNonTrasformati.value==9){
				form.alimentiOrigineAnimaleTrasformati.value="-1";
				form.TipoSpecie_latte.value="-1";
				document.form.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
				document.form.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";

				form.TipoSpecie_uova.style.display="block";
				form.TipoSpecie_latte.style.display="none";
				document.getElementById("notealimenti").style.display="block";

			}else{
				document.form.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
				form.alimentiOrigineAnimaleTrasformati.value="-1";
				document.getElementById("notealimenti").style.display="block";
				form.TipoSpecie_uova.style.display="none";
				form.TipoSpecie_latte.style.display="none";
				form.TipoSpecie_uova.value="-1";
				form.TipoSpecie_latte.value="-1";
				document.form.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
				form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;

			} }}
	if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
	{
		document.form.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		document.getElementById("notealimenti").style.display="none";
		form.TipoSpecie_uova.style.display="none";
		form.TipoSpecie_latte.style.display="none";
		//sel3 = document.getElementById("lookupTrasformati");
		//sel3.style.visibility = "visible";
		sel2 = document.form.alimentiOrigineAnimaleNonTrasformatiValori;//getElementById("lookupNonTrasformatiValori");
		sel2.style.display="none"
			form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
	}




}

//aggiunto per magic
function disabilitaNonTrasformati(form)
{
	if(form.alimentiOrigineAnimaleTrasformati.value != -1)
	{

		//sel3 = document.getElementById("lookupNonTrasformati");
		//sel3.style.visibility = "visible";
		//}
		//else
		//{
		//sel3 = document.getElementById("lookupNonTrasformati");
		//sel3.style.visibility = "hidden";

		document.getElementById("notealimenti").style.display="block";
	}  

}

function disabilitaCompostiAnimale(){

	document.getElementById("alimentiOrigineAnimale").disabled=true;
}
function disabilitaComposti(){

	document.getElementById("alimentiComposti").disabled=true;
}

function abilitaAnimaliCheck(){

	document.getElementById("alimentiOrigineAnimale").disabled=false;
}
function abilitaCompostiCheck(){

	document.getElementById("alimentiComposti").disabled=false;

}
function disabilitaCompostiVegetale(){
	document.getElementById("alimentiOrigineVegetale").disabled=true;

}
function abilitaCompostiVegetaleCheck(){
	document.getElementById("alimentiOrigineVegetale").disabled=false;

}





function abilitaLookupOrigineVegetale(form)
{
	alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
	sel2 = document.getElementById("lookupVegetale");
	sel3 = document.getElementById("lookupVegetale1");
	if(alimentiOrigine.checked)
	{ sel2.style.visibility = "visible";
	sel3.style.visibility = "visible";
	disabilitaCompostiAnimale();
	disabilitaComposti();
	disabilitaAcque();
	disabilitaBevande();
	disabilitaAltriAlimenti();
	disabilitaAdditivi();

	//disabilitaDolciumi();
	//disabilitaGelati();
	disabilitaMaterialiAlimenti();
	document.getElementById("notealimenti2").style.display="block";
	}
	else
	{ 
		abilitaAltriAlimenti();
		document.forms[form].grassi.style.display="none";
		document.forms[form].vino.style.display="none";
		document.forms[form].zuppe.style.display="none";
		document.forms[form].fruttaFresca.style.display="none";
		document.forms[form].fruttaSecca.style.display="none";
		document.forms[form].ortaggi.style.display="none";
		document.forms[form].funghi.style.display="none";
		document.forms[form].derivati.style.display="none";
		document.forms[form].conservati.style.display="none";
		abilitaAnimaliCheck();
		abilitaCompostiCheck();
		// abilitaDolciumiCheck();
		//abilitaGelatiCheck();
		abilitaAcqueCheck();
		abilitaBevandeCheck();	
		abilitaAdditivi();
		abilitaMaterialiAlimenti();

		document.forms[form].fruttaFresca.style.display="none";
		document.forms[form].fruttaSecca.style.display="none";
		document.forms[form].ortaggi.style.display="none";
		document.forms[form].funghi.style.display="none";
		document.forms[form].derivati.style.display="none";
		document.forms[form].conservati.style.display="none";

		sel2.style.visibility = "hidden";
		sel3.style.visibility = "hidden";

		document.getElementById("notealimenti2").style.display="none";





	}  
}

function abilitaAcque(form)
{
	alimentiOrigine = document.getElementById("acqua");
	//sel2 = document.getElementById("lookupVegetale");
	if(alimentiOrigine.checked==true)
	{ 
		document.getElementById("acquaSelect").style.display="block";
		document.getElementById("noteacqua").style.display="block";

		disabilitaCompostiVegetale();
		disabilitaCompostiAnimale();
		disabilitaComposti();

		disabilitaMaterialiAlimenti();
		//disabilitaDolciumi();
		//disabilitaGelati();
		disabilitaAdditivi();
		disabilitaAltriAlimenti();
		disabilitaBevande();
	}
	else
	{ 
		abilitaAltriAlimenti();

		abilitaCompostiVegetaleCheck()
		abilitaAnimaliCheck();
		abilitaCompostiCheck();
		abilitaAdditivi();
		abilitaMaterialiAlimenti();
		abilitaBevandeCheck();
		//abilitaDolciumiCheck();
		//abilitaGelatiCheck();

		document.getElementById("acquaSelect").style.display="none";
		document.getElementById("noteacqua").style.display="none";

		document.getElementById("acquaSelect").value="-1"
			document.getElementById("noteacqua").value="";

		//abilitaAnimaliCheck();
		//abilitaCompostiCheck();

	}  
}

function abilitaBevande(form)
{
	alimentiOrigine = document.getElementById("bevande");
	//sel2 = document.getElementById("lookupVegetale");
	if(alimentiOrigine.checked)
	{ //sel2.style.visibility = "visible";
		//disabilitaCompostiAnimale();
		//disabilitaComposti();


		disabilitaCompostiVegetale();
		disabilitaCompostiAnimale();
		disabilitaComposti();
		disabilitaAcque();
		disabilitaMaterialiAlimenti();
		//disabilitaDolciumi();
		//disabilitaGelati();
		disabilitaAdditivi();

		disabilitaAltriAlimenti();

		document.getElementById("notebevande").style.display="block";
	}
	else
	{ 
		abilitaAltriAlimenti();
		abilitaCompostiVegetaleCheck()
		abilitaAnimaliCheck();
		abilitaCompostiCheck();
		//abilitaGelatiCheck();
		// abilitaDolciumiCheck();
		abilitaAcqueCheck();

		abilitaAdditivi();
		abilitaMaterialiAlimenti();
		//sel2.style.visibility = "hidden";

		document.getElementById("notebevande").style.display="none";
		document.getElementById("notebevande").value="";

		//abilitaAnimaliCheck();
		//abilitaCompostiCheck();

	}  
}

function controllo_data_esito(form)
{
	
	message = "";
	if(form.estimatedResolutionDate != null && form.estimatedResolutionDate.value!="")
	{
	var arr1 = form.estimatedResolutionDate.value.split("/");
	var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
	var d2 = new Date();
	var r1 = d1.getTime();
	var r2 = d2.getTime();

	
	if (r1>r2) 
	{
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che La data Esito non sia Successiva alla data Odierna\r\n");
		alert(message)
		return false;

	}
	}
	return true;
}

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 
function checkEsitoForm(form) {
	formTest = true;
	message = "";
	
	
	/*if (form.estimatedResolutionDate.value == "")
	{
		message += label("check.campioni.estimatedResolutionDate.selezionato","- Controllare che il campo \"Data \" sia stato popolato\r\n");
		formTest = false;
	}*/
	
	if (document.getElementById('esito_data').value == "")
	{
		message += label("check.campioni.estimatedResolutionDate.selezionato","- Controllare che il campo \"Data \" sia stato popolato\r\n");
		formTest = false;
	}
	

	if (document.getElementById('esito_id').value == "-1")
	{
		message += label("check.campioni.estimatedResolutionDate.selezionato","- Controllare che il campo \"Esito Campione \" sia stato popolato\r\n");
		formTest = false;
	}
	
	
	/*if (form.esito_id.value == "-1")
	{
		message += label("check.campioni.estimatedResolutionDate.selezionato","- Controllare che il campo \"Esito Campione \" sia stato popolato\r\n");
		formTest = false;
	}*/
	
	
	if (formTest == false) {
		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		return false;
	} else {
		document.getElementById('details').submit();
		//loadModalWindow();
		return true;
	}
}
function checkForm(form){
	formTest = true;
	message = "";
	
	//	BLOCCO PER CONTROLLARE CHE DATA CAMPIONE > DATA CONTROLLO
	if (form.dataC!=null && form.dataC.value!=''){
		var dataPrelievo = form.assignedDate.value;
	
		var dateParts = dataPrelievo.split("/");
		var dataPrelievo2 = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);
	
		dateParts = form.dataC.value.split("/");
		var dataCu2 = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);
		
		if (dataPrelievo2 < dataCu2){
			message+= label("", " - La data del prelievo risulta antecedente alla data del controllo ufficiale. \r\n");
			formTest = false;
			
		}
	}
	
	if(form.motivazione_campione.value=="-1" ){
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Motivazione\" sia stato popolato\r\n");
		formTest = false;

	}
	
	getCodiceInternoTipoIspezione(form.motivazione_campione.value);
	
	
	if(codiceInternoTipoIspezione =="2a" && form.motivazione_piano_campione.value=="-1" ){
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Motivazione . piano di monitoraggio\" sia stato popolato\r\n");
		formTest = false;

	}
	if((form.microchipMatriciCanili!=null && form.microchipMatriciCanili.value=='') ||( form.numBoxCanile!=null && form.numBoxCanile.value==''))
	{
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Microchip\" o \"Numero box \" sia stato popolato\r\n");
		formTest = false;
	}

	/**numero verbale non obbligatorio per macroarea */
	if (form.macroarea== null)
	if(form.location.value==""){
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Numero Verbale\" sia stato popolato\r\n");
		formTest = false;

	}
	if (form.assignedDate.value == "") {
		message += label("check.campioni.data_richiesta.selezionato","- Controllare che il campo \"Data Prelievo\" sia stato popolato\r\n");
		formTest = false;
	}

	if (form.size.value == "0") {
		message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
		formTest = false;
	}
	
	if(form.latitudine_campione1 !=null )
	{
		if(form.latitudine_campione1.value == '' )
		{
			message += label("check.campioni.richiedente.selezionato","- Controllare che \"Almeno la prima coppia di coordinate sia popolata\" \r\n");
			formTest = false;
			
		}
		
	}
	
	if (form.size_coordinate != null) {
	    var almenoUnaCoppiaSelezionata = false;
	    for (var i = 0; i < parseInt(form.size_coordinate.value); i++) {
	        if (document.getElementById("campionePuntoPrelievo_" + i) != null && document.getElementById("campionePuntoPrelievo_" + i).checked) {
	            almenoUnaCoppiaSelezionata = true;
	            break;
	        }
	    }
	    if (!almenoUnaCoppiaSelezionata) {
	        formTest = false;
	        message += label("check.campioni.richiedente.selezionato", "- Controllare che almeno una coppia di coordinate punti di campionamento sia stata selezionata.\r\n ");
	    }
	}
	/*if(form.idMatrice.value == "-1")
	{
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Matrice Alimenti\" sia stato popolato\r\n");
		formTest = false;
		
	}*/
	if(form.size1.value == "0")
	{
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Matrice Alimenti\" sia stato popolato\r\n");
		formTest = false;
		
	}
	if(form.DestinatarioCampione.value=="-1"){
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Laboratorio Destinazione\" sia stato popolato\r\n");
		formTest = false;

	}
	
	

	if (form.siteId.value == "-1") {
		message += label("check.campioni.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
		formTest = false;
	}


	







	if (form.closeNow){
		if (form.closeNow.checked && form.solution.value == "") {
			message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
			formTest = false;
		}
	}



	
		
			/*if (document.getElementById('motivazione_piano_campione').options[document.getElementById('motivazione_piano_campione').options.selectedIndex].text.indexOf('19 ')!=-1)
			{
				optionspecie = form.specie_animali.options;
				categoriabovina='';
				categoriabufalina='';
				settato=false;
				for(i=0;i<optionspecie.length;i++)
				{
					if (optionspecie[i].selected==true && optionspecie[i].value!='-1')
					{
						settato=true;
						if (optionspecie[i].text=='BOVINI')
						{
							categoriabovina=optionspecie[i].value;
						}
						if (optionspecie[i].text=='BUFALINI')
						{
							categoriabufalina=optionspecie[i].value;
						}
						
					}
					
				}
				if(settato==false)
				{
				message += "- controllare di aver selezionato almeno una specie di animali \n";
				formTest=false;
				}
				if (document.getElementById('specie_categoria_'+categoriabovina)!=null && document.getElementById('specie_categoria_'+categoriabovina).value=='-1')
				{
					message += "- controllare di aver selezionato una categoria per la specie Bovini \n";
					formTest=false;
				}
				if (document.getElementById('specie_categoria_'+categoriabufalina)!=null && document.getElementById('specie_categoria_'+categoriabufalina).value=='-1')
				{
					message += "- controllare di aver selezionato una categoria per la specie Bufalini \n";
					formTest=false;
				}
			}
				*/
				
	/* if  (document.getElementById('motivazione_piano_campione').options[document.getElementById('motivazione_piano_campione').options.selectedIndex].text.indexOf('19 ')!=-1 &&
				document.getElementById('motivazione_piano_campione').options[document.getElementById('motivazione_piano_campione').options.selectedIndex].text.indexOf('CARNI') == -1)
		{
		
		//Controllo obbligatoriet� specie
		 optionspecie = form.specie_animali.options;
		settato=false;
		for(i=0;i<optionspecie.length;i++)
		{
			if (optionspecie[i].selected==true && optionspecie[i].value!='-1')
			{
				settato=true;	
			}
			
		}
		if(settato==false)
		{
		message += "- controllare di aver selezionato almeno una specie di animali \n";
		formTest=false;
		}
		//Controllo obbligatoriet� Metodo di produzione
		if(document.getElementById("check_circuito_ogm_b").checked != true && document.getElementById("check_circuito_ogm_c").checked !=true && document.getElementById("check_circuito_ogm_s").checked !=true){
			message += "- controllare di aver selezionato almeno un metodo di produzione \n";
			formTest=false;
		}

		//Controllo obbligatoriet� Lista prodotti PNAA
		/*optionsprodotti = form.lista_prodotti.options;
		settatoProd=false;
		for(j=0;j<optionsprodotti.length;j++)
		{
			if (optionsprodotti[j].selected==true && optionsprodotti[j].value!='-1')
			{
				settatoProd=true;	
			}
			
		}
		if(settatoProd==false)
		{
		message += "- controllare di aver selezionato almeno uno stato del prodotto al momento del prelievo\n";
		formTest=false;
		
	
	}*/
			
			
			
		
	
	

	if (formTest == false) {
		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		return false;
	} else {
		loadModalWindow();
		//form.submit();
		return true;
	}
}



function controllaForm(form){
	formTest = true;
	message = "";
	
	if(form.problem.value=="" ){
		message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Note\" sia stato popolato\r\n");
		formTest = false;

	}
	var provvedimenti = form.provvedimenti.options;
	for (i=0 ; i<provvedimenti.length; i++)
	{
		if (provvedimenti[i].selected && provvedimenti[i].value =='-1')
		{
			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Provvedimenti\" sia stato popolato correttamente\r\n");
			formTest = false;
		}
		
	}
	


	if (formTest == false) {
		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		return false;
	} else {
		loadModalWindow();
		//form.submit();
		return true;
	}
}



function mostraTrasformati(form){

	if(document.form.tipoAlimento.value=="0"){
		document.form.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
		document.form.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
		document.form.fruttaFresca.style.display="none";
		document.form.fruttaSecca.style.display="none";
		document.form.ortaggi.style.display="none";
		document.form.funghi.style.display="none";
		document.form.derivati.style.display="none";
		document.form.conservati.style.display="none";
		document.form.grassi.style.display="none";
		document.form.vino.style.display="none";
		document.form.zuppe.style.display="none";
	}else{
		if(document.form.tipoAlimento.value=="1"){
			document.form.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.form.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
			document.form.fruttaFresca.style.display="none";
			document.form.fruttaSecca.style.display="none";
			document.form.ortaggi.style.display="none";
			document.form.funghi.style.display="none";
			document.form.derivati.style.display="none";
			document.form.conservati.style.display="none";
			document.form.grassi.style.display="none";
			document.form.vino.style.display="none";
			document.form.zuppe.style.display="none";
		}
		else{

			document.form.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.form.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			document.form.fruttaFresca.style.display="none";
			document.form.fruttaSecca.style.display="none";
			document.form.ortaggi.style.display="none";
			document.form.funghi.style.display="none";
			document.form.derivati.style.display="none";
			document.form.conservati.style.display="none";
			document.form.grassi.style.display="none";
			document.form.vino.style.display="none";
			document.form.zuppe.style.display="none";
		}	
	}
}



function resetNumericFieldValue(fieldId){
	document.getElementById(fieldId).value = -1;
}

function setAssignedDate(){
	resetAssignedDate();
	if (document.forms['form'].assignedTo.value > 0){
		document.forms['form'].assignedDate.value = document.forms['form'].currentDate.value;
	}
}

function resetAssignedDate(){
	document.forms['form'].assignedDate.value = '';
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





function noteAnalisiF(form){
	var tcamp = document.forms[form].TipoCampione.value;

	if(tcamp != -1){

		document.getElementById("noteAnalisi").style.display="block";

	}else if(tcamp == -1){

		document.getElementById("noteAnalisi").style.display="none";

	}
}

function scritta(form){
	var tcamp = document.forms[form].TipoCampione.value;

	if(tcamp == 1 || tcamp == 2 || tcamp == 4){

		document.getElementById("scritta").style.display="block";

	}else{

		document.getElementById("scritta").style.display="none";

	}
}

function nascondiResponsabilita(){
	
	if ((document.getElementById('EsitoCampione').value=="2")||(document.getElementById('EsitoCampione').value=="5")){
		document.getElementById('campo_responsabilita').style.display="none";
	}else{
		document.getElementById('campo_responsabilita').style.display="";
	}
}