<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="lookupTipologiaAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupSpecieAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript">
function mostraTrasformati(){

	if(document.details.tipoAlimento.value=="0"){
	document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="block";
	document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
	document.details.fruttaFresca.style.display="none";
	document.details.fruttaSecca.style.display="none";
	document.details.ortaggi.style.display="none";
	document.details.funghi.style.display="none";
	document.details.derivati.style.display="none";
	document.details.conservati.style.display="none";
	document.details.grassi.style.display="none";
	document.details.vino.style.display="none";
	document.details.zuppe.style.display="none";
	}else{
		if(document.details.tipoAlimento.value=="1"){
		document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
		document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="block";
		document.details.fruttaFresca.style.display="none";
		document.details.fruttaSecca.style.display="none";
		document.details.ortaggi.style.display="none";
		document.details.funghi.style.display="none";
		document.details.derivati.style.display="none";
		document.details.conservati.style.display="none";
		document.details.grassi.style.display="none";
		document.details.vino.style.display="none";
		document.details.zuppe.style.display="none";
		}
		else{

			document.details.alimentiOrigineVegetaleValoriNonTrasformati.style.display="none";
			document.details.alimentiOrigineVegetaleValoriTrasformati.style.display="none";
			document.details.fruttaFresca.style.display="none";
			document.details.fruttaSecca.style.display="none";
			document.details.ortaggi.style.display="none";
			document.details.funghi.style.display="none";
			document.details.derivati.style.display="none";
			document.details.conservati.style.display="none";
			document.details.grassi.style.display="none";
			document.details.vino.style.display="none";
			document.details.zuppe.style.display="none";
			}	
	}
		  }


function abilitaAnimalinonalimentarecheck(){

	document.getElementById("animalinonalimentari").disabled=false;
		
	}

function disabilitaAnimalinonalimentarecheck(){

	document.getElementById("animalinonalimentari").disabled=true;
		
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
		
		 document.details.altrialimenti.style.display = "block";
			
	  disabilitaCompostiAnimale();
		disabilitaBevande();
		disabilitaMangimi();
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
		 abilitaMangimiCheck()
	  abilitaCompostiVegetaleCheck();
	//abilitaDolciumiCheck();
	//abilitaGelatiCheck();
	   abilitaAnimaliCheck();
	 } 
	
	
}


function abilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=false;
		
	}
function disabilitaAltriAlimenti(){

	document.getElementById("alimentinonAnimali").disabled=true;
		
	}



function mostraSottoCategoria(){

	if(document.details.tipoAlimento.value=="0")
		 var righe=document.details.alimentiOrigineVegetaleValoriNonTrasformati.value;
			else
				var righe=document.details.alimentiOrigineVegetaleValoriTrasformati.value;

 
	
	if(righe=="1" ){
		document.details.fruttaFresca.style.display="block";
		document.details.fruttaSecca.style.display="none";
		document.details.ortaggi.style.display="none";
		document.details.funghi.style.display="none";
		document.details.derivati.style.display="none";
		document.details.conservati.style.display="none";
		document.details.grassi.style.display="none";
		document.details.vino.style.display="none";
		document.details.zuppe.style.display="none";

		}else{
			if(righe=="2" ){

				document.details.fruttaFresca.style.display="none";
				document.details.fruttaSecca.style.display="block";
				document.details.ortaggi.style.display="none";
				document.details.funghi.style.display="none";
				document.details.derivati.style.display="none";
				document.details.conservati.style.display="none";
				document.details.grassi.style.display="none";
				document.details.vino.style.display="none";
				document.details.zuppe.style.display="none";


			}else{
				if(righe=="4" ){
					document.details.fruttaFresca.style.display="none";
					document.details.fruttaSecca.style.display="none";
					document.details.ortaggi.style.display="block";
					document.details.funghi.style.display="none";
					document.details.derivati.style.display="none";
					document.details.conservati.style.display="none";
					document.details.grassi.style.display="none";
					document.details.vino.style.display="none";
					document.details.zuppe.style.display="none";

				}
				else{
					if(righe=="5" ){
						document.details.fruttaFresca.style.display="none";
						document.details.fruttaSecca.style.display="none";
						document.details.ortaggi.style.display="none";
						document.details.funghi.style.display="block";
						document.details.derivati.style.display="none";
						document.details.conservati.style.display="none";
						document.details.grassi.style.display="none";
						document.details.vino.style.display="none";
						document.details.zuppe.style.display="none";

					}else{
						if(righe=="6" ){
							document.details.fruttaFresca.style.display="none";
							document.details.fruttaSecca.style.display="none";
							document.details.ortaggi.style.display="none";
							document.details.funghi.style.display="none";
							document.details.conservati.style.display="block";
							document.details.derivati.style.display="none";
							document.details.grassi.style.display="none";
							document.details.vino.style.display="none";
							document.details.zuppe.style.display="none";

						}else{
							if(righe=="7" ){
								document.details.fruttaFresca.style.display="none";
								document.details.fruttaSecca.style.display="none";
								document.details.ortaggi.style.display="none";
								document.details.funghi.style.display="none";
								document.details.derivati.style.display="block";
								document.details.conservati.style.display="none";
								document.details.grassi.style.display="none";
								document.details.vino.style.display="none";
								document.details.zuppe.style.display="none";

							}else{

								if(righe=="8" ){
									document.details.fruttaFresca.style.display="none";
									document.details.fruttaSecca.style.display="none";
									document.details.ortaggi.style.display="none";
									document.details.funghi.style.display="none";
									document.details.derivati.style.display="none";
									document.details.conservati.style.display="none";
									document.details.grassi.style.display="block";
									document.details.vino.style.display="none";
									document.details.zuppe.style.display="none";

								}else{
									if(righe=="9" ){
										document.details.fruttaFresca.style.display="none";
										document.details.fruttaSecca.style.display="none";
										document.details.ortaggi.style.display="none";
										document.details.funghi.style.display="none";
										document.details.derivati.style.display="none";
										document.details.conservati.style.display="none";
										document.details.grassi.style.display="none";
										document.details.vino.style.display="block";
										document.details.zuppe.style.display="none";

									}else{

										if(righe=="11" ){
											document.details.fruttaFresca.style.display="none";
											document.details.fruttaSecca.style.display="none";
											document.details.ortaggi.style.display="none";
											document.details.funghi.style.display="none";
											document.details.derivati.style.display="none";
											document.details.conservati.style.display="none";
											document.details.grassi.style.display="none";
											document.details.vino.style.display="none";
											document.details.zuppe.style.display="block";

										}else{

											document.details.grassi.style.display="none";
											document.details.vino.style.display="none";
											document.details.zuppe.style.display="none";
											document.details.fruttaFresca.style.display="none";
											document.details.fruttaSecca.style.display="none";
											document.details.ortaggi.style.display="none";
											document.details.funghi.style.display="none";
											document.details.derivati.style.display="none";
											document.details.conservati.style.display="none";

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
  
    disabilitaAcque();
   
     disabilitaBevande();
     disabilitaMangimi();
     disabilitaAltriAlimenti();
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
	abilitaAltriAlimentiCheck();
	abilitaMangimiCheck();
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
		  abilitaCompostiCheck();
		  abilitaAcqueCheck();
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
	
	document.details.alimentiOrigineAnimaleTrasformati.value="-1";

	//disabilitaDolciumi();
	//disabilitaGelati();
    disabilitaComposti();
   
    disabilitaAcque();
    
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
	  abilitaAcqueCheck();
	abilitaBevandeCheck();	
	abilitaMangimiCheck();
	abilitaAdditivi();
   abilitaMaterialiAlimenti();
   //abilitaGelatiCheck();	
	//abilitaDolciumiCheck();	
	  document.details.TipoSpecie_uova.style.display="none";
	  document.details.TipoSpecie_latte.style.display="none";
	  document.details.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	  document.details.alimentiOrigineAnimaleNonTrasformati.value="-1";
	  document.details.alimentiOrigineAnimaleTrasformati.style.display="none"
	  document.details.alimentiOrigineAnimaleTrasformati.value="-1";
	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"; 

}
	
}



//aggiunto da d.dauria
function abilitaTestoAlimentoComposto()
{
	 

if (document.getElementById("alimentiComposti").checked==true)
{

		
	 
		
disabilitaCompostiAnimale();
	disabilitaBevande();
	disabilitaMangimi();
disabilitaCompostiVegetale();
disabilitaAcque();
disabilitaAdditivi();
disabilitaAltriAlimenti();
disabilitaMaterialiAlimenti();
// disabilitaDolciumi();
//disabilitaGelati();
document.getElementById("testoAlimentoComposto").style.display = "";
document.getElementById("testoAlimentoComposto").style.visibility = "";

}
else
{
	 abilitaAltriAlimenti();
	 abilitaBevandeCheck();
	 abilitaMangimiCheck();
	 abilitaMaterialiAlimenti();
	 abilitaAdditivi();
	 abilitaAcqueCheck()
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
  
     document.details.alimentiOrigineAnimaleNonTrasformati.style.display="none";
     document.details.alimentiOrigineAnimaleNonTrasformati.value="-1";
     document.getElementById("tipoAlimentiAnimali").style.display="none";
     document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";

     //document.getElementById("lookupTrasformati").style.visibility = "hidden"; 
     
      document.details.alimentiOrigineAnimaleTrasformati.style.display="none";
     
      document.getElementById("lookupVegetale").style.visibility = "hidden";
      
  
     
     //aggiunto da d.dauria per far scomparire il testo degli alimenti composti
    document.getElementById("testoAlimentoComposto").style.display = "none";

   
}


function abilitaLookupOrigineAnimale()
{
	
    alimentiOrigine = document.details.tipoAlimentiAnimali.value;

document.getElementById("lookupNonTrasformati").style.visibility="visible";
  //sel = document.getElementById("lookupNonTrasformati");
  
    //sel3 = document.getElementById("lookupTrasformati");
    if(alimentiOrigine==1)
    { 
        

   	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none"
         
document.forms['details'].alimentiOrigineAnimaleNonTrasformati.style.display="block";
document.details.alimentiOrigineAnimaleTrasformati.style.display="none"
	document.details.alimentiOrigineAnimaleTrasformati.value="-1";

    
      disabilitaCompostiVegetale();
      
      disabilitaComposti();
    }

    else{
if(alimentiOrigine=="2"){
	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
     
	document.details.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.details.alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.details.alimentiOrigineAnimaleTrasformati.style.display="block";
	  document.forms['details'].alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      document.forms['details'].TipoSpecie_latte.style.display="none";
      document.forms['details'].TipoSpecie_uova.style.display="none";
      document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
		 disabilitaCompostiVegetale();
    
    disabilitaComposti();
	
}
else
{ 
	document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
	   document.forms['details'].TipoSpecie_latte.style.display="none";
	      document.forms['details'].TipoSpecie_uova.style.display="none";
		   document.forms['details'].TipoSpecie_latte.style.value="-1";
		      document.forms['details'].TipoSpecie_uova.value="-1";
	document.details.alimentiOrigineAnimaleNonTrasformati.style.display="none";
	document.details.alimentiOrigineAnimaleNonTrasformati.value="-1";
	document.details.alimentiOrigineAnimaleTrasformati.value="-1";
	document.details.alimentiOrigineAnimaleTrasformati.style.display="none"
		document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
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
      sel2 = document.details.alimentiOrigineAnimaleNonTrasformatiValori;//
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
    		sel2 = document.details.alimentiOrigineAnimaleNonTrasformatiValori;//
    	      sel2.style.display="none"
    	    	  document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    		form.TipoSpecie_latte.style.display="block";
    		form.TipoSpecie_uova.style.display="none";
    	 	document.getElementById("notealimenti").style.display="block";
    		
        	}else{
        		if(form.alimentiOrigineAnimaleNonTrasformati.value==9){
        			form.alimentiOrigineAnimaleTrasformati.value="-1";
            		form.TipoSpecie_latte.value="-1";
            		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
        			 document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
        		
            		form.TipoSpecie_uova.style.display="block";
            		form.TipoSpecie_latte.style.display="none";
            	 	document.getElementById("notealimenti").style.display="block";
            		
                	}else{
                		document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
                		form.alimentiOrigineAnimaleTrasformati.value="-1";
                	 	document.getElementById("notealimenti").style.display="block";
        		form.TipoSpecie_uova.style.display="none";
        	  	form.TipoSpecie_latte.style.display="none";
        	  	form.TipoSpecie_uova.value="-1";
        	  	form.TipoSpecie_latte.value="-1";
     document.details.alimentiOrigineAnimaleNonTrasformatiValori.style.display="none";
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
   
     } }}
     if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
     {
    	 document.details.alimentiOrigineAnimaleNonTrasformatiValori.value="-1";
    	 	document.getElementById("notealimenti").style.display="none";
    	 form.TipoSpecie_uova.style.display="none";
 	  	form.TipoSpecie_latte.style.display="none";
        //sel3 = document.getElementById("lookupTrasformati");
        //sel3.style.visibility = "visible";
        sel2 = document.details.alimentiOrigineAnimaleNonTrasformatiValori;//getElementById("lookupNonTrasformatiValori");
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
    disabilitaAcque();
    disabilitaBevande();
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
         abilitaAcqueCheck();
         abilitaBevandeCheck();	
         abilitaMangimiCheck();	
    	 abilitaAdditivi();
    	 abilitaMaterialiAlimenti();
    	 
   
    
    	 sel2.style.visibility = "hidden";
         sel3.style.visibility = "hidden";

     document.getElementById("notealimenti2").style.display="none";
    
 	document.details.fruttaFresca.style.display="none";
	document.details.fruttaSecca.style.display="none";
	document.details.ortaggi.style.display="none";
	document.details.funghi.style.display="none";
	document.details.derivati.style.display="none";
	document.details.conservati.style.display="none";
	document.details.grassi.style.display="none";
	document.details.vino.style.display="none";
	document.details.zuppe.style.display="none";
	   
		  
           
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

    disabilitaCompostiVegetale();
	disabilitaCompostiAnimale();
    disabilitaComposti();
   
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
    //sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { //sel2.style.visibility = "visible";
    //disabilitaCompostiAnimale();
    //disabilitaComposti();
    
    disabilitaMangimi();
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

			abilitaAcqueCheck();
			abilitaMangimiCheck();
			abilitaAdditivi();
			abilitaMaterialiAlimenti();

			document.getElementById("notebevande").style.display = "none";
			document.getElementById("notebevande").value = "";

			//abilitaAnimaliCheck();
			//abilitaCompostiCheck();

		}
	}

	function abilitaMangimi() {
		alimentiOrigine = document.getElementById("mangimi");
		//sel2 = document.getElementById("lookupVegetale");
		if (alimentiOrigine.checked) { //sel2.style.visibility = "visible";
			//disabilitaCompostiAnimale();
			//disabilitaComposti();

			disabilitaCompostiVegetale();
			disabilitaCompostiAnimale();
			disabilitaComposti();
			disabilitaAcque();
			disabilitaBevande();
			disabilitaMaterialiAlimenti();
			//disabilitaDolciumi();
			//disabilitaGelati();
			disabilitaAdditivi();
			disabilitaAltriAlimenti();

			document.getElementById("noteAlimentiMangimi").style.display = "";
			document.getElementById("lookupSpecieAlimento").style.display = "";
			document.getElementById("lookupTipologiaAlimento").style.display = "";
		} else {
			abilitaAltriAlimenti();
			abilitaCompostiVegetaleCheck()
			abilitaAnimaliCheck();
			abilitaCompostiCheck();
			abilitaBevandeCheck();
			//abilitaGelatiCheck();
			//  abilitaDolciumiCheck();
			abilitaAcqueCheck();

			abilitaAdditivi();
			abilitaMaterialiAlimenti();
			//sel2.style.visibility = "hidden";
			document.getElementById("lookupSpecieAlimento").style.display = "none";
			document.getElementById("lookupTipologiaAlimento").style.display = "none";

			document.getElementById("noteAlimentiMangimi").style.display = "none";
			document.getElementById("notealimenti").value = "";

			//abilitaAnimaliCheck();
			//abilitaCompostiCheck();

		}
	}
</script>

<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine animale</dhv:label>
    </td>
   
     <td>    
    <table class="noborder">
    <tr>
     <td>
     <%
     
     if( TicketDetails.getAlimentiOrigineAnimale() == true ){%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" checked="checked" id="alimentiOrigineAnimale" onclick="abilitaTipoAlimentoAnimale()"/>
       <%} else {%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" onclick="abilitaTipoAlimentoAnimale()"/>
       <%} %>
     </td>
     
     
     <td>
     <select name="tipoAlimentiAnimali" id="tipoAlimentiAnimali" onchange="abilitaLookupOrigineAnimale(this.form)" >
     <option value="-1" >--SELEZIONA UNA VOCE</option>
     <option value="1" <%if(TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()!=-1){ %>selected="selected" <%} %>>Alimenti Non Trasformati</option>
     <option value="2" <%if(TicketDetails.getAlimentiOrigineAnimaleTrasformati()!=-1){ %>selected="selected" <%} %>>Alimenti Trasformati</option>
     </select>
     
     
     </td>
     
       <td id="lookupNonTrasformati" style="padding: 5">
       <% AlimentiNonTrasformati.setJsEvent("onchange=\"javascript:abilitaSpecie(this.form);\"");%>
      <%= AlimentiNonTrasformati.getHtmlSelect("alimentiOrigineAnimaleNonTrasformati",TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()) %>
   
     <%  AlimentiTrasformati.setJsEvent("onchange=\"javascript:disabilitaNonTrasformati(this.form);\"");%>
     <%= AlimentiTrasformati.getHtmlSelect("alimentiOrigineAnimaleTrasformati",TicketDetails.getAlimentiOrigineAnimaleTrasformati()) %>
   
      </td > 
     <td >
       <%= TipoSpecie_latte.getHtmlSelect("TipoSpecie_latte",TicketDetails.getTipSpecie_latte()) %>
      <%= TipoSpecie_uova.getHtmlSelect("TipoSpecie_uova",TicketDetails.getTipSpecie_uova()) %>
     
      <%= AlimentiNonTrasformatiValori.getHtmlSelect("alimentiOrigineAnimaleNonTrasformatiValori",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     </td>
    <td style="display:none" id="notealimenti">
      Note :
     <textarea rows="8" cols="40" name="notealimenti" value=""<%=TicketDetails.getNoteAlimenti() %>><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
    </tr>
    </table>
    </td> 
  </tr><!-- chiusura tabella interna -->
  
  <!-- alimenti origine vegetale -->
   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine vegetale</dhv:label>
    </td>
     <td>    
    <table class="noborder">
    <tr>
     <td>
     <%
     if( TicketDetails.getAlimentiOrigineVegetale() == true ){%>
       <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" size="20" maxlength="256" checked="checked"  onclick="abilitaLookupOrigineVegetale()"/>
       <%} else {%>
              <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" onclick="abilitaLookupOrigineVegetale()" size="20" maxlength="256" />
       
       <%} %>
     </td>
      <td id="lookupVegetale">
      <select name="tipoAlimento" onchange="mostraTrasformati()">
       <option value="-1" <%if(TicketDetails.getIsvegetaletrasformato()==-1)  {%>selected="selected" <%} %>>--Seleziona Voce--</option>
      <option value="0" <%if(TicketDetails.getIsvegetaletrasformato()==-0)  {%>selected="selected" <%} %> >Non Trasformati</option>
      <option value="1"<%if(TicketDetails.getIsvegetaletrasformato()==1)  {%>selected="selected" <%} %> >Trasformati</option>
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
      
    
      while(iteraKiavi6.hasNext()){
			int kiave1=iteraKiavi6.next();
			String valore1=ListaBpi6.get(kiave1);
			multipleSelects6.addItem(kiave1,valore1);
		}
      
      if(TicketDetails.getAlimentiOrigineVegetaleValori()==1){
    	  FruttaFresca.setMultipleSelects(multipleSelects6);
    	  
      }else{
    	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==2){
        	  FruttaSecca.setMultipleSelects(multipleSelects6);
        	  
          } else{
        	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==4){
        		  Ortaggi.setMultipleSelects(multipleSelects6);
            	  
              }else{
            	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==5){
            		  Funghi.setMultipleSelects(multipleSelects6);
                	  
                  }else{
                	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==6){
                		  Derivati.setMultipleSelects(multipleSelects6);
                    	  
                      }else{
                    	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==7){
                        	  
                    		  Conservati.setMultipleSelects(multipleSelects6);
                          }else{
                        	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==8){
                            	  
                        		  Grassi.setMultipleSelects(multipleSelects6);
                              }else{
                            	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==9){
                                	  
                            		  Vino.setMultipleSelects(multipleSelects6);
                                  }else{
                                	  if(TicketDetails.getAlimentiOrigineVegetaleValori()==11){
                                    	  
                                		  Zuppe.setMultipleSelects(multipleSelects6);
                                      }
                                  }
                            	  
                              }
                        	  
                        	  
                        	  
                        	  
                          }
                      }
                	  
                  }
            	  
              }
        	  
          } 
    	  
      }
      
      
      
      
      Ortaggi.setSelectStyle("display:none");
      FruttaFresca.setSelectStyle("display:none");
      FruttaSecca.setSelectStyle("display:none");
      Funghi.setSelectStyle("display:none");
      Derivati.setSelectStyle("display:none");
      Conservati.setSelectStyle("display:none");
      Grassi.setSelectStyle("display:none");
      Vino.setSelectStyle("display:none");
      Zuppe.setSelectStyle("display:none");
      %>
      
     <%= FruttaFresca.getHtmlSelect("fruttaFresca",-1) %>
       <%= FruttaSecca.getHtmlSelect("fruttaSecca",-1) %>
         <%= Ortaggi.getHtmlSelect("ortaggi",-1) %>
         <%= Funghi.getHtmlSelect("funghi",-1) %>
           <%= Derivati.getHtmlSelect("derivati",-1) %>
           <%= Conservati.getHtmlSelect("conservati",-1) %>
      <%= Grassi.getHtmlSelect("grassi",-1) %>
       <%= Vino.getHtmlSelect("vino",-1) %>
        <%= Zuppe.getHtmlSelect("zuppe",-1) %>
   
   
      </td>
      
      <td style="display:none" id="notealimenti2">
      Note :
     <textarea rows="8" cols="40" name="notealimenti2" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
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
          
           <td>
             <% if( TicketDetails.isAltriAlimenti() == true ){%>
        <input type="checkbox" name="alimentinonAnomali"  id="alimentinonAnimali" onclick="abilitaalimentinonAnimali()"  size="20" maxlength="256" checked="checked" /></td>
       <%} else {%>
           <input type="checkbox" name="alimentinonAnomali"  id="alimentinonAnimali" onclick="abilitaalimentinonAnimali()"  size="20" maxlength="256" /></td>
     
       <%} %>
           
        
         <td style="display:none" id="alimentinonanimalicella">
         <%=AltriAlimenti.getHtmlSelect("altrialimenti",TicketDetails.getAltrialimenti()) %>
      <center>Descrizione</center>
     <textarea rows="8" cols="40" name="descrizionenonAnimali" value="<%=TicketDetails.getNoteAlimenti() %>" id="testoalimentinonanimali"></textarea>
     </td>

         </tr>
       </table>
       </td>
   </tr>
  
   <!-- alimenti composti -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Alimenti composti
    </td>
    <td>
      <% if( TicketDetails.getAlimentiComposti() == true ){%>
       <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onclick="abilitaTestoAlimentoComposto()" checked="checked"/>
       <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getNoteAlimenti()) %>" size="20" maxlength="256" />
       <%} else {%>
          <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onclick="abilitaTestoAlimentoComposto()" />    
          <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getNoteAlimenti()) %>" size="20" maxlength="256" style="display: none"/>
     
       <%} %>
     </td>
     </tr>
     
     
     
    <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Acqua</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
            <% if( TicketDetails.getAlimentiAcqua() == true ){%>
           <td>
           <input type="checkbox" name="alimentiAcqua"  id="acqua" onclick="abilitaAcque()" checked="checked"  size="20" maxlength="256" />
     </td>
     
     <td style="display:none" id="acquaSelect">
         <%= Acque.getHtmlSelect("acque",TicketDetails.getTipoAcque()) %>
     
     </td>
           <td style="display:none" id="noteacqua">
     <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteacqua"  value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
     
     
      <%}else{ %>
       <td><input type="checkbox" name="alimentiAcqua"  id="acqua" onclick="abilitaAcque()"  size="20" maxlength="256" />
      </td>
      <td style="display:none" id="acquaSelect">
         <%= Acque.getHtmlSelect("acque",TicketDetails.getTipoAcque()) %>
     
     </td>
           <td style="display:none" id="noteacqua">
     <center>Descrizione:</center>
     <textarea rows="8" cols="40" name="noteacqua"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
      <%} %>
               
   
     
   </tr>
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Bevande</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
             <% if( TicketDetails.getAlimentiBevande() == true ){%>
          <td>
           <input type="checkbox" name="alimentiBevande"  id="bevande" onclick="abilitaBevande()" checked="checked"  size="20" maxlength="256" />
            </td>
             <td style="display:none" id="notebevande">
      Descrizione: <br>
     <textarea rows="8" cols="40" name="notebevande" id="notealimentibevande" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
            
           <%}else{ %>
            <td>
            <input type="checkbox" name="alimentiBevande"  id="bevande" onclick="abilitaBevande()"  size="20" maxlength="256" />
           </td>
            <td style="display:none" id="notebevande">
      Descrizione:
     <textarea rows="8" cols="40" name="notebevande" id="notealimentibevande"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
           <%} %>
          
          
   
     
   </tr>
  
   
 <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Alimenti per uso Zootecnico</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
             <% if( TicketDetails.isMangimi() == true ){%>
          <td>
           <input type="checkbox" name="mangimi"  id="mangimi" onclick="abilitaMangimi()" checked="checked"  size="20" maxlength="256" />
            </td>
             <td id = "lookupSpecieAlimento"  >  <%= lookupSpecieAlimento.getHtmlSelect("lookupSpecieAlimento",TicketDetails.getSpecieAlimentoZootecnico()) %></td>
           <td id = "lookupTipologiaAlimento" >  <%= lookupTipologiaAlimento.getHtmlSelect("lookupTipologiaAlimento",TicketDetails.getTipologiaAlimentoZootecnico()) %></td>
             <td style="display:none" id="noteAlimentiMangimi">
      Descrizione: <br>
     <textarea rows="8" cols="40" name="notebevande" id="notealimenti" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
            
           <%}else{ %>
            <td>
            <input type="checkbox" name="mangimi"  id="mangimi" onclick="abilitaMangimi()"  size="20" maxlength="256" />
           </td>
                     <td id = "lookupSpecieAlimento" style="display:none" >  <%= lookupSpecieAlimento.getHtmlSelect("lookupSpecieAlimento",TicketDetails.getSpecieAlimentoZootecnico()) %></td>
           <td id = "lookupTipologiaAlimento" style="display:none">  <%= lookupTipologiaAlimento.getHtmlSelect("lookupTipologiaAlimento",TicketDetails.getTipologiaAlimentoZootecnico()) %></td>
  
            <td style="display:none" id="noteAlimentiMangimi">
      Descrizione:
     <textarea rows="8" cols="40" name="notealimenti" id="notealimenti"></textarea>
     
     </td>
         </tr>
       </table>
       </td>
           <%} %>
          
          
   
     
   </tr>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Additivi</dhv:label>
       </td>
       <td>
         <table class="noborder">
          <tr>
           
              <% if( TicketDetails.getAlimentiAdditivi() == true ){%>
           <td>
           <input type="checkbox" name="alimentiAdditivi" onclick="abilitatipoAdditivi()" checked="checked" id="additivi"  size="20" maxlength="256" />
          </td>
           
           <td style="display:none" id="noteadditivi">
      Descrizione:<br>
     <textarea rows="8" cols="40" name="noteadditivi" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
          
           <%}else{ %>
           <td> <input type="checkbox" name="alimentiAdditivi" onclick="abilitatipoAdditivi()" id="additivi"  size="20" maxlength="256" />
          </td>
           
           <td style="display:none" id="noteadditivi">
      Descrizione:
     <textarea rows="8" cols="40" name="noteadditivi" ></textarea>
     
     </td>
         </tr>
       </table>
       </td>
           <%} %>
           
          
   
     
   </tr>
   
   
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importo">Materiali a Contatto con Alimenti</dhv:label>
       </td>
       <td>
         <table  class="noborder">
          <tr>
           <td>
                <% if( TicketDetails.getMaterialiAlimenti() == true ){%>
           <input type="checkbox" name="materialiAlimenti" onclick="abilitatipomaterialiAlimenti()" id="materialialimenti" checked="checked" size="20" maxlength="256" />
         
         </td>
         
         <td style="display:none" id="notematerialialimenti">
      Descrizione:<br>
     <textarea rows="8" cols="40" name="notematerialialimenti" value="<%=TicketDetails.getNoteAlimenti() %>"><%=TicketDetails.getNoteAlimenti() %></textarea>
     
     </td>
         </tr>
       </table>
       </td>
         
           <%}else{ %>
         <td>   <input type="checkbox" name="materialiAlimenti" onclick="abilitatipomaterialiAlimenti()" id="materialialimenti"   size="20" maxlength="256" />
           
            </td>
            <td style="display:none" id="notematerialialimenti">
      Descrizione:
     <textarea rows="8" cols="40" name="notematerialialimenti" ></textarea>
     
     </td>
         </tr>
       </table>
       </td>
            
           <%} %>
           
          
           
   
     
   </tr>