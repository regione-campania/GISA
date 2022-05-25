<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="lookupTipologiaAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupSpecieAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Pasta" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupmolluschi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AnimaliNonAlimentari" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script language="JavaScript">
function mostraTrasformati(){

	if(document.addticket.tipoAlimento.value=="0"){
	document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
	document.addticket.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	document.addticket.fruttaFresca.style.display="none";
	document.addticket.fruttaSecca.style.display="none";
	document.addticket.ortaggi.style.display="none";
	document.addticket.funghi.style.display="none";
	document.addticket.derivati.style.display="none";
	document.addticket.conservati.style.display="none";
	document.addticket.grassi.style.display="none";
	document.addticket.vino.style.display="none";
	document.addticket.zuppe.style.display="none";
	}else{
		if(document.addticket.tipoAlimento.value=="1"){
		document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.addticket.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
		document.addticket.fruttaFresca.style.display="none";
		document.addticket.fruttaSecca.style.display="none";
		document.addticket.ortaggi.style.display="none";
		document.addticket.funghi.style.display="none";
		document.addticket.derivati.style.display="none";
		document.addticket.conservati.style.display="none";
		document.addticket.grassi.style.display="none";
		document.addticket.vino.style.display="none";
		document.addticket.zuppe.style.display="none";
		}
		else{

			document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.addticket.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			document.addticket.fruttaFresca.style.display="none";
			document.addticket.fruttaSecca.style.display="none";
			document.addticket.ortaggi.style.display="none";
			document.addticket.funghi.style.display="none";
			document.addticket.derivati.style.display="none";
			document.addticket.conservati.style.display="none";
			document.addticket.grassi.style.display="none";
			document.addticket.vino.style.display="none";
			document.addticket.zuppe.style.display="none";
			}	
	}
		  }



function abilitaanimalinonalimentari(){


	 if (document.getElementById("animalinonalimentari").checked==true)
	 {

		 
		 document.getElementById("animalinonalimentaricombo").style.display = "block";
		
		 document.addticket.altrialimenti.style.display = "block";
		 disabilitaAltriAlimenti()
	  disabilitaCompostiAnimale();
		disabilitaBevande();
		disabilitaMangimi();
	  disabilitaCompostiVegetale();
	  disabilitaAcque(); disabilitaMatriciCanili();
	  disabilitaAdditivi();
	  disabilitaMaterialiAlimenti();
	    disabilitaComposti();
	 
	  
	 }
	 else
	 {

		 abilitaCompostiCheck();
		 abilitaBevandeCheck();
		 abilitaMangimiCheck();
		 abilitaMaterialiAlimenti();
		 abilitaAdditivi();
		 abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
		 document.getElementById("animalinonalimentaricombo").style.display = "none";
			  		abilitaCompostiVegetaleCheck();
		//abilitaDolciumiCheck();
		//abilitaGelatiCheck();
	   abilitaAnimaliCheck();
	 } 
	
	
}


function abilitaalimentinonAnimali(){


	 if (document.getElementById("alimentinonAnimali").checked==true)
	 {

		 
		 document.getElementById("alimentinonanimalicella").style.display = "block";
		
		 document.addticket.altrialimenti.style.display = "block";
		 disabilitaAnimalinonalimentarecheck();
	  disabilitaCompostiAnimale();
		disabilitaBevande();
		disabilitaMangimi();
	  disabilitaCompostiVegetale();
	  disabilitaAcque(); disabilitaMatriciCanili();
	  disabilitaAdditivi();
	  disabilitaMaterialiAlimenti();
	    disabilitaComposti();
	 
	  
	 }
	 else
	 {
		 abilitaAnimalinonalimentarecheck();
		 abilitaAltriAlimenti();
		 abilitaCompostiCheck();
		 abilitaBevandeCheck();
		 abilitaMangimiCheck();
		 abilitaMaterialiAlimenti();
		 abilitaAdditivi();
		 abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
		 document.getElementById("alimentinonanimalicella").style.display = "none";
  abilitaCompostiVegetaleCheck();
	//abilitaDolciumiCheck();
	//abilitaGelatiCheck();
	   abilitaAnimaliCheck();
	 } 
	
	
}


function abilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=false;
		
	}

function abilitaAnimalinonalimentarecheck(){

	document.getElementById("animalinonalimentari").disabled=false;
		
	}

function disabilitaAnimalinonalimentarecheck(){

	document.getElementById("animalinonalimentari").disabled=true;
		
	}

function disabilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=true;
		
	}



function mostraSottoCategoria(){

	if(document.addticket.tipoAlimento.value=="0")
		 var righe=document.addticket.alimentiOrigineVegetaleValoriNonTrasformati.value;
			else
				var righe=document.addticket.alimentiOrigineVegetaleValoriTrasformati.value;

 
	
	if(righe=="1" ){
		document.addticket.fruttaFresca.style.display="block";
		document.addticket.pasta.style.display="none";
		document.addticket.fruttaSecca.style.display="none";
		document.addticket.ortaggi.style.display="none";
		document.addticket.funghi.style.display="none";
		document.addticket.derivati.style.display="none";
		document.addticket.conservati.style.display="none";
		document.addticket.grassi.style.display="none";
		document.addticket.vino.style.display="none";
		document.addticket.zuppe.style.display="none";

		}else{
			if(righe=="2" ){
				document.addticket.pasta.style.display="none";
				document.addticket.fruttaFresca.style.display="none";
				document.addticket.fruttaSecca.style.display="block";
				document.addticket.ortaggi.style.display="none";
				document.addticket.funghi.style.display="none";
				document.addticket.derivati.style.display="none";
				document.addticket.conservati.style.display="none";
				document.addticket.grassi.style.display="none";
				document.addticket.vino.style.display="none";
				document.addticket.zuppe.style.display="none";


			}else{
				if(righe=="4" ){
					document.addticket.pasta.style.display="none";
					document.addticket.fruttaFresca.style.display="none";
					document.addticket.fruttaSecca.style.display="none";
					document.addticket.ortaggi.style.display="block";
					document.addticket.funghi.style.display="none";
					document.addticket.derivati.style.display="none";
					document.addticket.conservati.style.display="none";
					document.addticket.grassi.style.display="none";
					document.addticket.vino.style.display="none";
					document.addticket.zuppe.style.display="none";

				}
				else{
					if(righe=="5" ){
						document.addticket.pasta.style.display="none";
						document.addticket.fruttaFresca.style.display="none";
						document.addticket.fruttaSecca.style.display="none";
						document.addticket.ortaggi.style.display="none";
						document.addticket.funghi.style.display="block";
						document.addticket.derivati.style.display="none";
						document.addticket.conservati.style.display="none";
						document.addticket.grassi.style.display="none";
						document.addticket.vino.style.display="none";
						document.addticket.zuppe.style.display="none";

					}else{
						if(righe=="6" ){
							document.addticket.pasta.style.display="none";
							document.addticket.fruttaFresca.style.display="none";
							document.addticket.fruttaSecca.style.display="none";
							document.addticket.ortaggi.style.display="none";
							document.addticket.funghi.style.display="none";
							document.addticket.conservati.style.display="block";
							document.addticket.derivati.style.display="none";
							document.addticket.grassi.style.display="none";
							document.addticket.vino.style.display="none";
							document.addticket.zuppe.style.display="none";

						}else{
							if(righe=="7" ){
								document.addticket.pasta.style.display="none";
								document.addticket.fruttaFresca.style.display="none";
								document.addticket.fruttaSecca.style.display="none";
								document.addticket.ortaggi.style.display="none";
								document.addticket.funghi.style.display="none";
								document.addticket.derivati.style.display="block";
								document.addticket.conservati.style.display="none";
								document.addticket.grassi.style.display="none";
								document.addticket.vino.style.display="none";
								document.addticket.zuppe.style.display="none";

							}else{

								if(righe=="8" ){
									document.addticket.pasta.style.display="none";
									document.addticket.fruttaFresca.style.display="none";
									document.addticket.fruttaSecca.style.display="none";
									document.addticket.ortaggi.style.display="none";
									document.addticket.funghi.style.display="none";
									document.addticket.derivati.style.display="none";
									document.addticket.conservati.style.display="none";
									document.addticket.grassi.style.display="block";
									document.addticket.vino.style.display="none";
									document.addticket.zuppe.style.display="none";

								}else{
									if(righe=="9" ){
										document.addticket.pasta.style.display="none";
										document.addticket.fruttaFresca.style.display="none";
										document.addticket.fruttaSecca.style.display="none";
										document.addticket.ortaggi.style.display="none";
										document.addticket.funghi.style.display="none";
										document.addticket.derivati.style.display="none";
										document.addticket.conservati.style.display="none";
										document.addticket.grassi.style.display="none";
										document.addticket.vino.style.display="block";
										document.addticket.zuppe.style.display="none";

									}else{

										if(righe=="11" ){
											document.addticket.pasta.style.display="none";
											document.addticket.fruttaFresca.style.display="none";
											document.addticket.fruttaSecca.style.display="none";
											document.addticket.ortaggi.style.display="none";
											document.addticket.funghi.style.display="none";
											document.addticket.derivati.style.display="none";
											document.addticket.conservati.style.display="none";
											document.addticket.grassi.style.display="none";
											document.addticket.vino.style.display="none";
											document.addticket.zuppe.style.display="block";

										}else{
											if(righe=="14" ){
												document.addticket.pasta.style.display="block";
												document.addticket.fruttaFresca.style.display="none";
												document.addticket.fruttaSecca.style.display="none";
												document.addticket.ortaggi.style.display="none";
												document.addticket.funghi.style.display="none";
												document.addticket.derivati.style.display="none";
												document.addticket.conservati.style.display="none";
												document.addticket.grassi.style.display="none";
												document.addticket.vino.style.display="none";
												document.addticket.zuppe.style.display="none";

											}
											else
											{
												document.addticket.pasta.style.display="none";
												document.addticket.grassi.style.display="none";
												document.addticket.vino.style.display="none";
												document.addticket.zuppe.style.display="none";
												document.addticket.fruttaFresca.style.display="none";
												document.addticket.fruttaSecca.style.display="none";
												document.addticket.ortaggi.style.display="none";
												document.addticket.funghi.style.display="none";
												document.addticket.derivati.style.display="none";
												document.addticket.conservati.style.display="none";

												
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


function abilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=false;
		
	}

function disabilitaAdditivi(){

	document.getElementById("additivi").disabled=true;
		
	}


function disabilitaBevande(){

	document.getElementById("bevande").disabled=true;
		
	}
function disabilitaMangimi(){

	document.getElementById("mangimi").disabled=true;
		
	}


function disabilitaMaterialiAlimenti(){

	document.getElementById("materialialimenti").disabled=true;
		
	}


function disabilitaAcque(){
	document.getElementById("acqua").disabled=true;
	
}



function abilitaAcqueCheck(){
	document.getElementById("acqua").disabled=false; 
	
}

function abilitaBevandeCheck(){
	document.getElementById("bevande").disabled=false; 
	
}
function abilitaMangimiCheck(){
	document.getElementById("mangimi").disabled=false; 
	
}

function abilitatipoAdditivi(){
	var check=document.getElementById("additivi");

if(check.checked){
	disabilitaCompostiVegetale();
    disabilitaComposti();
    disabilitaAltriAlimenti();
    disabilitaAnimalinonalimentarecheck();
    disabilitaAcque(); disabilitaMatriciCanili();
    disabilitaBevande();
    disabilitaMangimi();
    disabilitaCompostiAnimale();
    disabilitaMaterialiAlimenti();
   
document.getElementById("noteadditivi").style.display="block";
	
}else{
	abilitaAnimaliCheck();
	abilitaCompostiVegetaleCheck();
	abilitaCompostiCheck();
	abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
	abilitaBevandeCheck();	
	abilitaAnimalinonalimentarecheck();
	abilitaMangimiCheck();
	abilitaAltriAlimenti();
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
  
    disabilitaAcque(); disabilitaMatriciCanili();
    disabilitaAnimalinonalimentarecheck();
     disabilitaBevande();
     disabilitaMangimi();
     //disabilitaDolciumi();
     //disabilitaGelati();
     disabilitaCompostiAnimale();
     disabilitaAltriAlimenti();
   disabilitaAdditivi();
   document.getElementById("notematerialialimenti").style.display="block";
}else{
	abilitaAltriAlimenti();
	 abilitaAnimaliCheck();
		abilitaCompostiVegetaleCheck();
		abilitaAnimalinonalimentarecheck();
		  abilitaCompostiCheck();
		  abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
		abilitaBevandeCheck();	
		abilitaMangimiCheck();	
		abilitaAdditivi();
	//	abilitaGelatiCheck();	
		//abilitaDolciumiCheck();	

document.getElementById("notematerialialimenti").style.display="none";
document.getElementById("notematerialialimenti").value="";
	
}
	
}

function abilitaTipoAlimentoAnimale(){


var check=document.getElementById("alimentiOrigineAnimale");

if(check.checked==true){
	document.getElementById("tipoAlimentiAnimali").style.display="block";
	disabilitaCompostiVegetale();
	disabilitaAnimalinonalimentarecheck();
	document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";

	//disabilitaDolciumi();
	//disabilitaGelati();
    disabilitaComposti();
   
    disabilitaAcque(); disabilitaMatriciCanili();
    
     disabilitaBevande();
     disabilitaMangimi();
    disabilitaAdditivi();
    disabilitaAltriAlimenti();
    disabilitaMaterialiAlimenti();
    
}
else{
	abilitaAltriAlimenti();
	document.getElementById("notealimenti").style.display="none";
	document.getElementById("tipoAlimentiAnimali").style.display="none";
	abilitaCompostiVegetaleCheck();
	  abilitaCompostiCheck();
	  abilitaAnimalinonalimentarecheck();
	  abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
	abilitaBevandeCheck();	
	abilitaMangimiCheck();
	abilitaAdditivi();
   abilitaMaterialiAlimenti();
   //abilitaGelatiCheck();	
	//abilitaDolciumiCheck();	
	  document.addticket.TipoSpecie_uova.style.display="none";
	  document.addticket.TipoSpecie_latte.style.display="none";
	  document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	  document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
	  document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none"
	  document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";
	  document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"; 

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
	disabilitaMangimi();
disabilitaCompostiVegetale();
disabilitaAcque(); disabilitaMatriciCanili();
disabilitaAdditivi();
disabilitaAltriAlimenti();
disabilitaMaterialiAlimenti();
disabilitaAnimalinonalimentarecheck();
// disabilitaDolciumi();
//disabilitaGelati();


}
else
{
	 abilitaAltriAlimenti();
	 abilitaBevandeCheck();
	 abilitaMangimiCheck();
	 abilitaMaterialiAlimenti();
	 abilitaAnimalinonalimentarecheck();
	 abilitaAdditivi();
	 abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
	 document.getElementById("testoAlimentoComposto").style.display = "none";
abilitaCompostiVegetaleCheck();
//abilitaDolciumiCheck();
//abilitaGelatiCheck();
 abilitaAnimaliCheck();
} 
}


function controlloLookup(){
     //aggiunto per positività
    //document.getElementById("note").style.visibility="hidden";
  
  // document.getElementById("note_etichetta").style.visibility="hidden";
  
    //aggiunto da d.dauria
     document.getElementById("lookupNonTrasformati").style.visibility = "hidden";
  
     document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
     document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
     document.getElementById("tipoAlimentiAnimali").style.display="none";
     document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";

     //document.getElementById("lookupTrasformati").style.visibility = "hidden"; 
     
      document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none";
     
      document.getElementById("lookupVegetale").style.visibility = "hidden";
      
  
     
     //aggiunto da d.dauria per far scomparire il testo degli alimenti composti
    document.getElementById("testoAlimentoComposto").style.display = "none";

   
}


function abilitaLookupOrigineAnimale()
{
	
    alimentiOrigine = document.addticket.tipoAlimentiAnimali.value;

document.getElementById("lookupNonTrasformati").style.visibility="visible";
  //sel = document.getElementById("lookupNonTrasformati");
  
    //sel3 = document.getElementById("lookupTrasformati");
    if(alimentiOrigine==1)
    { 
        

   	 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"
         
document.forms['addticket'].alimentiOrigineAnimaleNonTrasformati.style.display="block";
document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none"
	document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";

    
      disabilitaCompostiVegetale();
      
      disabilitaComposti();
    }

    else{
if(alimentiOrigine=="2"){
	 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
     
	document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.addticket.alimentiOrigineAnimaleTrasformati.style.display="block";
	  document.forms['addticket'].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      document.forms['addticket'].TipoSpecie_latte.style.display="none";
      document.forms['addticket'].TipoSpecie_uova.style.display="none";
      document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		 disabilitaCompostiVegetale();
		 disabilitaAnimalinonalimentarecheck();
    disabilitaComposti();
	
}
else
{ 
	document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	   document.forms['addticket'].TipoSpecie_latte.style.display="none";
	      document.forms['addticket'].TipoSpecie_uova.style.display="none";
		   document.forms['addticket'].TipoSpecie_latte.style.value="-1";
		      document.forms['addticket'].TipoSpecie_uova.value="-1";
	document.addticket.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.addticket.alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.addticket.alimentiOrigineAnimaleTrasformati.value="-1";
	document.addticket.alimentiOrigineAnimaleTrasformati.style.display="none"
		document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
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
      sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//
      sel2.style.display="block"
  	form.TipoSpecie_uova.style.display="none";
      form.TipoSpecie_uova.value="-1";
      form.TipoSpecie_latte.value="-1";
  	form.TipoSpecie_latte.style.display="none";
  	document.getElementById("notealimenti").style.display="block";
  	document.getElementById("molluschi").style.display="none";
     } 
    else
     {

    	if(form.alimentiOrigineAnimaleNonTrasformati.value==8){
    		form.alimentiOrigineAnimaleTrasformati.value="-1";
    		form.TipoSpecie_uova.value="-1";
    		sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//
    	      sel2.style.display="none"
    	    	  document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    		form.TipoSpecie_latte.style.display="block";
    		form.TipoSpecie_uova.style.display="none";
    	 	document.getElementById("notealimenti").style.display="block";
    		document.getElementById("molluschi").style.display="none";
        	}else{
        		if(form.alimentiOrigineAnimaleNonTrasformati.value==6){

        			form.alimentiOrigineAnimaleTrasformati.value="-1";
            		form.TipoSpecie_uova.value="-1";
            		sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//
            	      sel2.style.display="none"
            	    	  document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
            		form.TipoSpecie_latte.style.display="none";
            		form.TipoSpecie_uova.style.display="none";
            	 	document.getElementById("notealimenti").style.display="none";
            	 	document.getElementById("molluschi").style.display="block";
            		
        		}
        		else
        		{
        		if(form.alimentiOrigineAnimaleNonTrasformati.value==9){
        			document.getElementById("molluschi").style.display="none";
        			form.alimentiOrigineAnimaleTrasformati.value="-1";
            		form.TipoSpecie_latte.value="-1";
            		document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
        			 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
        		
            		form.TipoSpecie_uova.style.display="block";
            		form.TipoSpecie_latte.style.display="none";
            	 	document.getElementById("notealimenti").style.display="block";
            		
                	}else{
                		document.getElementById("molluschi").style.display="none";
                		document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
                		form.alimentiOrigineAnimaleTrasformati.value="-1";
                	 	document.getElementById("notealimenti").style.display="block";
        		form.TipoSpecie_uova.style.display="none";
        	  	form.TipoSpecie_latte.style.display="none";
        	  	form.TipoSpecie_uova.value="-1";
        	  	form.TipoSpecie_latte.value="-1";
     document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
   
     } }}}
     if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
     {
    	 document.addticket.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    	 	document.getElementById("notealimenti").style.display="none";
    	 form.TipoSpecie_uova.style.display="none";
 	  	form.TipoSpecie_latte.style.display="none";
        //sel3 = document.getElementById("lookupTrasformati");
        //sel3.style.visibility = "visible";
        sel2 = document.addticket.alimentiOrigineAnimaleNonTrasformatiValori;//getElementById("lookupNonTrasformatiValori");
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





function abilitaLookupOrigineVegetale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    sel2 = document.getElementById("lookupVegetale");
    sel3 = document.getElementById("lookupVegetale1");
    if(alimentiOrigine.checked)
    { sel2.style.visibility = "visible";
    sel3.style.visibility = "visible";
    disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque(); disabilitaMatriciCanili();
    disabilitaBevande();
    disabilitaAnimalinonalimentarecheck();
    disabilitaMangimi();
    disabilitaAdditivi();
    disabilitaAltriAlimenti();
   // disabilitaDolciumi();
    //disabilitaGelati();
	   disabilitaMaterialiAlimenti();
    document.getElementById("notealimenti2").style.display="block";
    }
    else
    { 

    	abilitaAltriAlimenti();
    	 abilitaAnimaliCheck();
         abilitaCompostiCheck();
        // abilitaDolciumiCheck();
         //abilitaGelatiCheck();
         abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
         abilitaBevandeCheck();	
         abilitaMangimiCheck();	
    	 abilitaAdditivi();
    	 abilitaMaterialiAlimenti();
    	 
    	 abilitaAnimalinonalimentarecheck();
    
    	 sel2.style.visibility = "hidden";
         sel3.style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";
    
 	document.addticket.fruttaFresca.style.display="none";
	document.addticket.fruttaSecca.style.display="none";
	document.addticket.ortaggi.style.display="none";
	document.addticket.funghi.style.display="none";
	document.addticket.derivati.style.display="none";
	document.addticket.conservati.style.display="none";
	document.addticket.grassi.style.display="none";
	document.addticket.vino.style.display="none";
	document.addticket.zuppe.style.display="none";
	   
		  
           
    }  
}

function abilitaAcque()
{
    alimentiOrigine = document.getElementById("acqua");
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked==true)
    { 
    document.getElementById("acquaSelect").style.display="block";
    document.getElementById("noteacqua").style.display="block";
    disabilitaAnimalinonalimentarecheck();
    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaMatriciCanili();
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

    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
		abilitaAdditivi();
    	abilitaMaterialiAlimenti();
    	abilitaBevandeCheck();
    	abilitaMangimiCheck();
    	abilitaAnimalinonalimentarecheck();
    	abilitaMatriciCaniliCheck();
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

function abilitaBevande()
{
    alimentiOrigine = document.getElementById("bevande");
   
    if(alimentiOrigine.checked)
    {
    disabilitaMangimi();
    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque(); disabilitaMatriciCanili();
 	disabilitaMaterialiAlimenti();
 	disabilitaAdditivi();
 	disabilitaAnimalinonalimentarecheck();
    disabilitaAltriAlimenti();
    document.getElementById("notebevande").style.display="block";
    }
    else
    { 
    	abilitaAltriAlimenti();
    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
        abilitaAnimalinonalimentarecheck();
      	abilitaMangimiCheck();
        abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
        abilitaAdditivi();
    	abilitaMaterialiAlimenti();
        document.getElementById("notebevande").style.display="none";
     	document.getElementById("notebevande").value="";
          
    }  
}


function abilitaMangimi()
{
    alimentiOrigine = document.getElementById("mangimi");
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { 
    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
    disabilitaAcque(); disabilitaMatriciCanili();
    disabilitaBevande();
 	disabilitaMaterialiAlimenti();
    disabilitaAdditivi();
    disabilitaAltriAlimenti();
    
    disabilitaAnimalinonalimentarecheck();
    
    document.getElementById("noteAlimentiMangimi").style.display="";
    document.getElementById("lookupSpecieAlimento").style.display="";
    document.getElementById("lookupTipologiaAlimento").style.display="";
    }
    else
    { 
    	abilitaAltriAlimenti();
    	abilitaCompostiVegetaleCheck()
    	abilitaAnimaliCheck();
        abilitaCompostiCheck();
        abilitaAnimalinonalimentarecheck();
        abilitaBevande();
        abilitaAcqueCheck(); abilitaMatriciCaniliCheck();
     	abilitaAdditivi();
       	abilitaMaterialiAlimenti();
    	document.getElementById("lookupSpecieAlimento").style.display="none";
    	document.getElementById("lookupTipologiaAlimento").style.display="none";
     	document.getElementById("noteAlimentiMangimi").style.display="none";
     	document.getElementById("notealimenti").value="";

     //abilitaAnimaliCheck();
     //abilitaCompostiCheck();
          
    }  
}




</script>



<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di Origine Animale</dhv:label>
    </td>
    <td>    
    <table class="noborder">
    <tr>
     <td>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" onclick="abilitaTipoAlimentoAnimale()"/>
     </td> 
     <td>
     <select name="tipoAlimentiAnimali" id="tipoAlimentiAnimali" onchange="abilitaLookupOrigineAnimale()">
     <option value="-1">--SELEZIONA UNA VOCE</option>
     <option value="1">Alimenti Non Trasformati</option>
     <option value="2">Alimenti Trasformati</option>
     </select>
     
     
     </td>
     
      <td id="lookupNonTrasformati" style="padding: 5">
       <% AlimentiNonTrasformati.setJsEvent("onchange=\"javascript:abilitaSpecie(this.form);\"");%>
      <%= AlimentiNonTrasformati.getHtmlSelect("alimentiOrigineAnimaleNonTrasformati",TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()) %>
   
     <%  AlimentiTrasformati.setJsEvent("onchange=\"javascript:disabilitaNonTrasformati(this.form);\"");%>
     <%= AlimentiTrasformati.getHtmlSelect("alimentiOrigineAnimaleTrasformati",TicketDetails.getAlimentiOrigineAnimaleTrasformati()) %>
   
      </td > 
     <td id="lookupNonTrasformatiValori" >
     <%lookupmolluschi.setSelectStyle("display:none"); %>
      <%= AlimentiNonTrasformatiValori.getHtmlSelect("alimentiOrigineAnimaleNonTrasformatiValori",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     <%= lookupmolluschi.getHtmlSelect("molluschi",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
    
     <%
     TipoSpecie_latte.setSelectStyle("display:none");
     TipoSpecie_uova.setSelectStyle("display:none");
     
     
     %>
     <%= TipoSpecie_latte.getHtmlSelect("TipoSpecie_latte",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
      <%= TipoSpecie_uova.getHtmlSelect("TipoSpecie_uova",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     
     
    
     </td>
     <td style="display:none" id="notealimenti">
      Note :
     <textarea rows="8" cols="40" name="notealimenti"></textarea>
     
     </td>
  
    </tr>
    </table>
    </td> 
  </tr><!-- chiusura tabella interna -->
  
  <!-- alimenti origine vegetale -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di Origine Vegetale</dhv:label>
    </td>
     <td>    
    <table class="noborder" id="padre">
    <tr>
     <td>
       <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" size="20" maxlength="256"  onclick="abilitaLookupOrigineVegetale()" />
     <td id="lookupVegetale">
      <select name="tipoAlimento" onchange="mostraTrasformati()">
       <option value="-1">--Seleziona Voce--</option>
      <option value="0">Non Trasformati</option>
      <option value="1">Trasformati</option>
      </select>
      </td>
      <td id="lookupVegetale1">
      <%
      AlimentiVegetaliNonTraformati.setSelectStyle("display:none");
      AlimentiVegetaliNonTraformati .setJsEvent("onchange=\"javascript: mostraSottoCategoria();\""); %>
      
      <%= AlimentiVegetaliNonTraformati.getHtmlSelect("alimentiOrigineVegetaleValoriNonTrasformati",TicketDetails.getAlimentiOrigineVegetaleValori()) %>
      
       <%
       AlimentiVegetaliTraformati.setSelectStyle("display:none");
       AlimentiVegetaliTraformati .setJsEvent("onchange=\"javascript: mostraSottoCategoria();\""); %>
      
      <%= AlimentiVegetaliTraformati.getHtmlSelect("alimentiOrigineVegetaleValoriTrasformati",TicketDetails.getAlimentiOrigineVegetaleValori()) %>
      
      </td> 
      <td>
      <%
      Ortaggi.setSelectStyle("display:none");
      FruttaFresca.setSelectStyle("display:none");
      FruttaSecca.setSelectStyle("display:none");
      Funghi.setSelectStyle("display:none");
      Derivati.setSelectStyle("display:none");
      Pasta.setSelectStyle("display:none");
      Conservati.setSelectStyle("display:none");
      Zuppe.setSelectStyle("display:none");
      Vino.setSelectStyle("display:none");
      Grassi.setSelectStyle("display:none");
      
      %>
      
     <%= FruttaFresca.getHtmlSelect("fruttaFresca",-1) %>
       <%= FruttaSecca.getHtmlSelect("fruttaSecca",-1) %>
       <%= Pasta.getHtmlSelect("pasta",-1) %>
         <%= Ortaggi.getHtmlSelect("ortaggi",-1) %>
         <%= Funghi.getHtmlSelect("funghi",-1) %>
           <%= Derivati.getHtmlSelect("derivati",-1) %>
           <%= Conservati.getHtmlSelect("conservati",-1) %>
                 <%= Zuppe.getHtmlSelect("zuppe",-1) %>
                       <%= Vino.getHtmlSelect("vino",-1) %>
                             <%= Grassi.getHtmlSelect("grassi",-1) %>
     
   
   
   
   
      </td>
     
      
      
      
       <td style="display:none" id="notealimenti2">
     <center>Descrizione</center> 
     <textarea rows="8" cols="40" name="notealimenti2"></textarea>
     
     </td>
      </tr>
      </table> <!--  chiusura tabella alimenti vegetali -->
     </td>
     </tr>
  
  
  
        <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Altri Alimenti di origine non animale</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentinonAnomali"  id="alimentinonAnimali" onclick="abilitaalimentinonAnimali()"  size="20" maxlength="256" /></td>
   
         <td style="display:none" id="alimentinonanimalicella">
         <%=AltriAlimenti.getHtmlSelect("altrialimenti",TicketDetails.getAltrialimenti()) %>
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="descrizionenonAnimali" id="testoalimentinonanimali"></textarea>
     </td>
         </tr>
       </table>
       </td>
   </tr>
    
   <!-- alimenti composti -->
  <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti Composti</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiComposti"  id="alimentiComposti" onclick="abilitaTestoAlimentoComposto()" size="20" maxlength="256" /></td>
           <td id="testoAlimentoComposto"><input type="text" name="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getTestoAlimentoComposto()) %>" size="60" maxlength="256" /></td>
         </tr>
       </table>
       </td>
   
     
   </tr>
   
   
   
  
    <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Acqua</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiAcqua"  id="acqua" onclick="abilitaAcque()"  size="20" maxlength="256" /></td>
      
               <td style="display:none" id="acquaSelect">
         <%= Acque.getHtmlSelect("acque",-1) %>
     
     </td>
           <td style="display:none" id="noteacqua">
     <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteacqua"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Bevande</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiBevande"  id="bevande" onclick="abilitaBevande()"  size="20" maxlength="256" /></td>
           <td style="display:none" id="notebevande">
      Descrizione:
     <textarea rows="8" cols="40" name="notebevande" id="notealimentibevande"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
  
   
    <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti per uso Zootecnico</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="mangimi"  id="mangimi" onclick="abilitaMangimi()" size="20" maxlength="256" /></td>
           
           <td id = "lookupSpecieAlimento" style="display:none" >  <%= lookupSpecieAlimento.getHtmlSelect("lookupSpecieAlimento",-1) %></td>
           <td id = "lookupTipologiaAlimento" style="display:none">  <%= lookupTipologiaAlimento.getHtmlSelect("lookupTipologiaAlimento",-1) %></td>
           <td style="display:none" id="noteAlimentiMangimi">
      Note :
     <textarea rows="8" cols="40" name="notealimenti"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr> 
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Additivi</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           <td><input type="checkbox" name="alimentiAdditivi" onclick="abilitatipoAdditivi()" id="additivi"  size="20" maxlength="256" /></td>
           <td style="display:none" id="noteadditivi">
      Descrizione:
     <textarea rows="8" cols="40" name="noteadditivi"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Materiali a Contatto con Alimenti</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          <tr>
           <td><input type="checkbox" name="materialiAlimenti" onclick="abilitatipomaterialiAlimenti()" id="materialialimenti"  size="20" maxlength="256" /></td>
           <td style="display:none" id="notematerialialimenti">
      Descrizione:
     <textarea rows="8" cols="40" name="notematerialialimenti"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
   
   <%
  
   
   %>
   <tr class="containerBody"<%if(request.getAttribute("Allerte")!=null){ %> style="display: none"<%} %>>
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo"> Matrici animali non alimentari</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          <tr>
           <td><input type="checkbox" name="animalinonalimentari" onclick="abilitaanimalinonalimentari()" id="animalinonalimentari"  size="20" maxlength="256" /></td>
           <td style="display:none" id="animalinonalimentaricombo">
      <%= AnimaliNonAlimentari.getHtmlSelect("animalinonalimentarivalue",-1) %>
     
     </td>
         </tr>
       </table>
       </td>
   
     
   </tr>
 