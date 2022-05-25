/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

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
/*function abilitaTestoAlimentoComposto()
{
 //alimenti = document.getElementById("alimentiComposti");
 testo = document.getElementById("testoAlimentoComposto"); 
 if (alimenti.checked)
 {
  testo.style.visibility = "visible";
 }
 else
 {
  testo.style.visibility = "hidden";
 } 
}*/



function showRow1_tampone1(){



	if(document.getElementById("check1_1").checked==true){
	document.getElementById("row1tampone1").style.display="block";
	document.getElementById("check2_1").disabled=true;
	}else{
		document.getElementById("check2_1").disabled=false;
		document.getElementById("row1tampone1").style.display="none";
		if(document.getElementById("check2_1").checked==true){
			document.getElementById("check1_1").disabled=true;
			document.getElementById("row2tampone1").style.display="block";
		}
		else{
			document.getElementById("check1_1").disabled=false;
			document.getElementById("row2tampone1").style.display="none";
		}
		}
	
	
	


	
}

function showRow1_tampone2(){

	if(document.getElementById("check1_2").checked==true){
		document.getElementById("row1tampone2").style.display="block";
		document.getElementById("check2_2").disabled=true;
		}else{
			document.getElementById("check2_2").disabled=false;
			document.getElementById("row1tampone2").style.display="none";
			if(document.getElementById("check2_2").checked==true){
				document.getElementById("check1_2").disabled=true;
				document.getElementById("row2tampone2").style.display="block";
			}
			else{
				document.getElementById("check1_2").disabled=false;
				document.getElementById("row2tampone2").style.display="none";
			}
			}
	
}

function showRow1_tampone3(){

	if(document.getElementById("check1_3").checked==true){
		document.getElementById("row1tampone3").style.display="block";
		document.getElementById("check2_3").disabled=true;
		}else{
			document.getElementById("check2_3").disabled=false;
			document.getElementById("row1tampone3").style.display="none";
			if(document.getElementById("check2_3").checked==true){
				document.getElementById("check1_3").disabled=true;
				document.getElementById("row2tampone3").style.display="block";
			}
			else{
				document.getElementById("check1_3").disabled=false;
				document.getElementById("row2tampone3").style.display="none";
			}
			}
	
}


function showRow1_tampone4(){

	if(document.getElementById("check1_4").checked==true){
		document.getElementById("row1tampone4").style.display="block";
		document.getElementById("check2_4").disabled=true;
		}else{
			document.getElementById("check2_4").disabled=false;
			document.getElementById("row1tampone4").style.display="none";
			if(document.getElementById("check2_4").checked==true){
				document.getElementById("check1_4").disabled=true;
				document.getElementById("row2tampone4").style.display="block";
			}
			else{
				document.getElementById("check1_4").disabled=false;
				document.getElementById("row2tampone4").style.display="none";
			}
			}
	
}


function showRow1_tampone5(){

	if(document.getElementById("check1_5").checked==true){
		document.getElementById("row1tampone5").style.display="block";
		document.getElementById("check2_5").disabled=true;
		}else{
			document.getElementById("check2_5").disabled=false;
			document.getElementById("row1tampone5").style.display="none";
			if(document.getElementById("check2_5").checked==true){
				document.getElementById("check1_5").disabled=true;
				document.getElementById("row2tampone5").style.display="block";
			}
			else{
				document.getElementById("check1_5").disabled=false;
				document.getElementById("row2tampone5").style.display="none";
			}
			}
	
}


function showRow1_tampone6(){

	if(document.getElementById("check1_6").checked==true){
		document.getElementById("row1tampone6").style.display="block";
		document.getElementById("check2_6").disabled=true;
		}else{
			document.getElementById("check2_6").disabled=false;
			document.getElementById("row1tampone6").style.display="none";
			if(document.getElementById("check2_6").checked==true){
				document.getElementById("check1_6").disabled=true;
				document.getElementById("row2tampone6").style.display="block";
			}
			else{
				document.getElementById("check1_6").disabled=false;
				document.getElementById("row2tampone6").style.display="none";
			}
			}
	
}


function showRow1_tampone7(){

	if(document.getElementById("check1_7").checked==true){
		document.getElementById("row1tampone7").style.display="block";
		document.getElementById("check2_7").disabled=true;
		}else{
			document.getElementById("check2_7").disabled=false;
			document.getElementById("row1tampone7").style.display="none";
			if(document.getElementById("check2_7").checked==true){
				document.getElementById("check1_7").disabled=true;
				document.getElementById("row2tampone7").style.display="block";
			}
			else{
				document.getElementById("check1_7").disabled=false;
				document.getElementById("row2tampone7").style.display="none";
			}
			}
	
}


function showRow1_tampone8(){

	if(document.getElementById("check1_8").checked==true){
		document.getElementById("row1tampone8").style.display="block";
		document.getElementById("check2_8").disabled=true;
		}else{
			document.getElementById("check2_8").disabled=false;
			document.getElementById("row1tampone8").style.display="none";
			if(document.getElementById("check2_8").checked==true){
				document.getElementById("check1_8").disabled=true;
				document.getElementById("row2tampone8").style.display="block";
			}
			else{
				document.getElementById("check1_8").disabled=false;
				document.getElementById("row2tampone8").style.display="none";
			}
			}
	
}


function showRow1_tampone9(){

	if(document.getElementById("check1_9").checked==true){
		document.getElementById("row1tampone9").style.display="block";
		document.getElementById("check2_9").disabled=true;
		}else{
			document.getElementById("check2_9").disabled=false;
			document.getElementById("row1tampone9").style.display="none";
			if(document.getElementById("check2_9").checked==true){
				document.getElementById("check1_9").disabled=true;
				document.getElementById("row2tampone9").style.display="block";
			}
			else{
				document.getElementById("check1_9").disabled=false;
				document.getElementById("row2tampone9").style.display="none";
			}
			}
	
}


function showRow1_tampone10(){

	if(document.getElementById("check1_10").checked==true){
		document.getElementById("row1tampone10").style.display="block";
		document.getElementById("check2_10").disabled=true;
		}else{
			document.getElementById("check2_10").disabled=false;
			document.getElementById("row1tampone10").style.display="none";
			if(document.getElementById("check2_10").checked==true){
				document.getElementById("check1_10").disabled=true;
				document.getElementById("row2tampone10").style.display="block";
			}
			else{
				document.getElementById("check1_10").disabled=false;
				document.getElementById("row2tampone10").style.display="none";
			}
			}
	
}




function abilitaCheckSegnalazione()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(nonConformita.checked)
    { allerta.checked = false;
    }
}


function abilitaCheckAllerta()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(allerta.checked)
    { nonConformita.checked = false;
    }
}


function showEsitiTampone1(form){
	
	var aa=document.forms[form].RicercaTamponi_1;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	document.getElementById("etichetta1").style.display="block";

if(aa[i].value!="-1"){
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone1_"+aa[i].value).style.display="block";
	}else{
			a=i+1;
			//document.getElementById("etichetta1").style.display="none";
			document.getElementById("esitoTampone1_"+aa[i].value).style.display="none";
			
			}
}
}
	mostraNextTampone2(form);
	
}


function showEsitiTampone2(form){
	
	var aa=document.forms[form].RicercaTamponi_2;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta2").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone2_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta2").style.display="none";
			document.getElementById("esitoTampone2_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone3(form);
}

function showEsitiTampone3(form){
	a=0;
	var aa=document.forms[form].RicercaTamponi_3;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta3").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone3_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta3").style.display="none";
			document.getElementById("esitoTampone3_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone4(form);
}

function showEsitiTampone4(form){
	
	var aa=document.forms[form].RicercaTamponi_4;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta4").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone4_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta4").style.display="none";
			document.getElementById("esitoTampone4_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone5(form);
}

function showEsitiTampone5(form){
	
	var aa=document.forms[form].RicercaTamponi_5;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta5").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone5_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta5").style.display="none";
			document.getElementById("esitoTampone5_"+aa[i].value).style.display="none";
			
			}
	}
}
	mostraNextTampone6(form);
}

function showEsitiTampone6(form){
	
	var aa=document.forms[form].RicercaTamponi_6;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta6").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone6_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta6").style.display="none";
			document.getElementById("esitoTampone6_"+aa[i].value).style.display="none";
			
			}
	}
}
	mostraNextTampone7(form);
}

function showEsitiTampone7(form){
	
	var aa=document.forms[form].RicercaTamponi_7;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta7").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone7_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta7").style.display="none";
			document.getElementById("esitoTampone7_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone8(form);
}

function showEsitiTampone8(form){
	
	var aa=document.forms[form].RicercaTamponi_8;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta7").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone8_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta8").style.display="none";
			document.getElementById("esitoTampone8_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone9(form);
}

function showEsitiTampone9(form){
	
	var aa=document.forms[form].RicercaTamponi_9;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta9").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone9_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta9").style.display="none";
			document.getElementById("esitoTampone9_"+aa[i].value).style.display="none";
			
			}
		
}}
	mostraNextTampone10(form);
}

function showEsitiTampone10(form){
	
	var aa=document.forms[form].RicercaTamponi_10;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(aa[i].value!="-1"){

	document.getElementById("etichetta10").style.display="block";

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone10_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			//document.getElementById("etichetta10").style.display="none";
			document.getElementById("esitoTampone10_"+aa[i].value).style.display="none";
		
			}
		
}}
}


function showEsitiTipo2_tampone1(form){
	
	var bb=document.forms[form].RicercaTamponi2_1;
	
	for(c=0;c<bb.length;c++){
	document.getElementById("etichetta12").style.display="block";
	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone1").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone1").style.display="none";
			//document.getElementById("etichetta12").style.display="none";
			}
		
}}
	mostraNextTampone2(form);
}
function showEsitiTipo2_tampone2(form){
	
	var bb=document.forms[form].RicercaTamponi2_2;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){
	if (document.getElementById("etichetta21") != null)
	{
		document.getElementById("etichetta21").style.display="block";

	}
	
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone2").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta21").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone2").style.display="none";
			
			}
		
}}
	mostraNextTampone3(form);
}
function showEsitiTipo2_tampone3(form){
	
	var bb=document.forms[form].RicercaTamponi2_3;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta31").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone3").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta31").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone3").style.display="none";
			
			}
		
}}
	mostraNextTampone4();
}

function showEsitiTipo2_tampone4(form){
	
	var bb=document.forms[form].RicercaTamponi2_4;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta41").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone4").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta41").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone4").style.display="none";
			
			}
	}
}
	mostraNextTampone5(form);
}

function showEsitiTipo2_tampone5(form){
	
	var bb=document.forms[form].RicercaTamponi2_5;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta51").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone5").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta51").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone5").style.display="none";
			
			}
		
}}
	mostraNextTampone6();
}

function showEsitiTipo2_tampone6(form){
	
	var bb=document.forms[form].RicercaTamponi2_6;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta61").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone6").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta61").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone6").style.display="none";
			
			}
		
}}
	mostraNextTampone7(form);
}

function showEsitiTipo2_tampone7(form){
	
	var bb=document.forms[form].RicercaTamponi2_7;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta71").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone7").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta71").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone7").style.display="none";
			
			}
		
}}
	mostraNextTampone8(form);
}

function showEsitiTipo2_tampone8(form){
	
	var bb=document.forms[form].RicercaTamponi2_8;

	for(c=0;c<bb.length;c++){

		var selected1=	bb[c].selected;
		if(bb[c].value!="-1"){
	document.getElementById("etichetta81").style.display="block";
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone8").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta81").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone8").style.display="none";
			
			}
		}
}
	mostraNextTampone9(form);
}

function showEsitiTipo2_tampone9(form){
	
	var bb=document.forms[form].RicercaTamponi2_9;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta91").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone9").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta91").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone9").style.display="none";
			
			}
		
}}
	mostraNextTampone10(form);
}

function showEsitiTipo2_tampone10(form){
	
	var bb=document.forms[form].RicercaTamponi2_10;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;

	if(bb[c].value!="-1"){

	document.getElementById("etichetta101").style.display="block";

	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone10").style.display="block";
		}else{
			b=c+1;
			//document.getElementById("etichetta101").style.display="none";
			document.getElementById("esito_"+bb[c].value+"_tampone10").style.display="none";
			
			}
		
}}
}



  function checkForm(form){
    formTest = true;
    message = "";


    if(form.check1_1.checked==false && form.check2_1.checked==false){

    	  message += label("check.tamponi.richiedente.selezionato","- Controllare che almeno un tampone sia stato inserito\r\n");
          formTest = false;
    	
    }else{
            	
    if(form.check1_1.checked==true){

   
    	if(form.Tamponi1.value=="-1"){
    		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver selezionato una voce per Carcassa per il Tampone 1\r\n");
    	      formTest = false;

    		}else{

    		array=form.RicercaTamponi_1;

    		var flag=0;
    		for( k=0 ; k<array.length ; k++){

    			

    			
    			if(array[k].value=="-1" && array[k].selected==true){
    				flag=1;
    				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 1\r\n");
    			      formTest = false;

    				}
    	
    			}
    			}
    	
    }else{
    	if(form.check2_1.checked==true){
    		if(form.superfice1.value==""){
    			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 1\r\n");
    		      formTest = false;

    			}else{

    			array=form.RicercaTamponi2_1;

    			var flag=0;
    			for( k=0 ; k<array.length ; k++){
    				
    				if(array[k].value=="-1" && array[k].selected==true){
    					flag=1;
    					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 1\r\n");
    				      formTest = false;

    					}
    		
    				}
    				}
    	}

    }
    }
    if(form.location.value==""){
    	message += label("check.tamponi.richiedente.selezionato","- Controllare cher il \"Numero Verbale\" sia stato riempito\r\n");
        formTest = false;
    	    }
 if(form.assignedDate.value==""){
    	message += label("check.tamponi.richiedente.selezionato","- Controllare che la \"Data Prelievo\" sia stata riempita\r\n");
        formTest = false;
    	    }
    if(form.DestinatarioTampone.value=="-1"){
    	message += label("check.tamponi.richiedente.selezionato","- Controllare cher il campo \"Laboratorio di Destinazione\" sia stato selezionato\r\n");
        formTest = false;
    	    }

if(form.check1_2.checked==true){


	if(form.Tamponi2.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_2;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
			      formTest = false;

				}
	
			}
			}
	
}else{
	if(form.check2_2.checked==true){


		if(form.superfice2.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_2;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 2\r\n");
				      formTest = false;

					}
		
				}
			
				}
	}
	
}
if(form.check1_3.checked==true){


	if(form.Tamponi3.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_3;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
			      formTest = false;

				}
	
			}

			}

}else{


	if(form.check2_3.checked==true){


		if(form.superfice3.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_3;

			var flag=0;
			for( k=0 ; k<array.length ; k++){

				

				
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 3\r\n");
				      formTest = false;

					}
		
				}
				

				}

	}
	
}

if(form.check1_4.checked==true){


	if(form.Tamponi4.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_4;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
			      formTest = false;

				}
	
			}
	
			}
}else{


	if(form.check2_4.checked==true){


		if(form.superfice4.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_4;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
			if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 4\r\n");
				      formTest = false;

					}
		
				}
				}
	
	}
}

if(form.check1_5.checked==true){


	if(form.Tamponi5.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_5;

		var flag=0;
		for( k=0 ; k<array.length ; k++){
			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
			      formTest = false;

				}
	
			}
				
		}

	
}else{


	if(form.check2_5.checked==true){


		if(form.superfice5.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_5;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
		
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 5\r\n");
				      formTest = false;

					}
		
				}
								
				}
	
	}
	
}

if(form.check1_6.checked==true){


	if(form.Tamponi6.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_6;

		var flag=0;
		for( k=0 ; k<array.length ; k++){
		if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
			      formTest = false;

				}
	
			}
		
			}

}else{


	if(form.check2_6.checked==true){


		if(form.superfice6.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_6;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
		if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 6\r\n");
				      formTest = false;

					}
		
				}
		
				}
	}

	
}


if(form.check1_7.checked==true){


	if(form.Tamponi7.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_7;

		var flag=0;
		for( k=0 ; k<array.length ; k++){

			

			
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
			      formTest = false;

				}
	
			}
			
			}

	

	
}else{


	if(form.check2_7.checked==true){


		if(form.superfice7.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_7;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
		
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 7\r\n");
				      formTest = false;

					}
		
				}
			}
		
	}

}


if(form.check1_8.checked==true){


	if(form.Tamponi8.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_8;

		var flag=0;
		for( k=0 ; k<array.length ; k++){
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
			      formTest = false;

				}
	
			}
	
			}

}else{

	if(form.check2_8.checked==true){
			if(form.superfice8.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_8;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 8\r\n");
				      formTest = false;

					}
		
				}
		
				}

	}

}



if(form.check1_9.checked==true){


	if(form.Tamponi9.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_9;

		var flag=0;
		for( k=0 ; k<array.length ; k++){
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
			      formTest = false;

				}
	
			}
				
			}

	

	
}else{

	if(form.check2_9.checked==true){

		if(form.superfice9.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_9;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
		
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 9\r\n");
				      formTest = false;

					}
		
				}
			
				}

	}

	
}


if(form.check1_10.checked==true){


	if(form.Tamponi10.value=="-1"){
		 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
	      formTest = false;

		}else{

		array=form.RicercaTamponi_10;

		var flag=0;
		for( k=0 ; k<array.length ; k++){
			if(array[k].value=="-1" && array[k].selected==true){
				flag=1;
				 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
			      formTest = false;

				}
	
			}
	
			}
	
}else{


	if(form.check2_10.checked==true){


		if(form.superfice10.value==""){
			 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
		      formTest = false;

			}else{

			array=form.RicercaTamponi2_10;

			var flag=0;
			for( k=0 ; k<array.length ; k++){
				if(array[k].value=="-1" && array[k].selected==true){
					flag=1;
					 message += label("check.tamponi.richiedente.selezionato","- Controllare di aver riempito tutti i campi per il Tampone 10\r\n");
				      formTest = false;

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



 function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }

 function setAssignedDate(form){
  resetAssignedDate();
  if (document.forms[form].assignedTo.value > 0){
    document.forms[form].assignedDate.value = document.forms[form].currentDate.value;
  }
 }

 function resetAssignedDate(form){
    document.forms[form].assignedDate.value = '';
 }
 



function mostraNextTampone2(){

document.getElementById("tampone2").style.display="";
}

function mostraNextTampone3(){

	document.getElementById("tampone3").style.display="";
	}


function mostraNextTampone4(){

	document.getElementById("tampone4").style.display="";
	}

function mostraNextTampone5(){

	document.getElementById("tampone5").style.display="";
	}


function mostraNextTampone6(){

	document.getElementById("tampone6").style.display="";
	}


function mostraNextTampone7(){

	document.getElementById("tampone7").style.display="";
	}


function mostraNextTampone8(){

	document.getElementById("tampone8").style.display="";
	}


function mostraNextTampone9(){

	document.getElementById("tampone9").style.display="";
	}


function mostraNextTampone10(){

	document.getElementById("tampone10").style.display="";
	}

function abilitaTamponi(form)
{
 if(form.check1_1.checked==true)
     {
  
	 showRow1_tampone1('details');

		if(document.details.RicercaTamponi_1.lenght!=0){

			showEsitiTampone1('details');
			//mostraNextTampone2();
			}

	 
     }else{ 
         if(form.check2_1.checked==true)
     {
        	
    	 showRow1_tampone1('details');

    		if(document.details.RicercaTamponi2_1.lenght!=0){

    			showEsitiTipo2_tampone1('details');
    			//mostraNextTampone2();
    			}

     }
     }




 if(form.check1_2.checked==true)
 {
	 showRow1_tampone2();
	if(document.details.RicercaTamponi_2.lenght!=0){
		
		showEsitiTampone2('details');
		mostraNextTampone3('details');
		}


 }
 else{ 
	
     if(form.check2_2.checked==true)
 	{
    	
	 showRow1_tampone2('details');
	
		if(document.details.RicercaTamponi2_2.lenght!=0){
		
			showEsitiTipo2_tampone2('details');
			mostraNextTampone3('details');
			
			}

 }
 }





 if(form.check1_3.checked==true)
 {
	
 showRow1_tampone3('details');

	if(document.details.RicercaTamponi_3.lenght!=0){

		showEsitiTampone3('details');
		mostraNextTampone4('details');
		}

 
 }else{ 
     if(form.check2_3.checked==true)
 {
    
	 showRow1_tampone3('details');

		if(document.details.RicercaTamponi2_3.lenght!=0){

			showEsitiTipo2_tampone3('details');
			mostraNextTampone4('details');
			}

 }
 }


 if(form.check1_4.checked==true)
 {
	
 showRow1_tampone4('details');

	if(document.details.RicercaTamponi_4.lenght!=0){

		showEsitiTampone4('details');
		mostraNextTampone5('details');
		}

 
 }else{ 
     if(form.check2_4.checked==true)
 {
    	
	 showRow1_tampone4('details');

		if(document.details.RicercaTamponi2_4.lenght!=0){

			showEsitiTipo2_tampone4('details');
			mostraNextTampone5('details');
			}

 }
 }





 if(form.check1_5.checked==true)
 {
	
 showRow1_tampone5('details');

	if(document.details.RicercaTamponi_5.lenght!=0){

		showEsitiTampone5('details');
		mostraNextTampone6('details');
		}

 
 }else{ 
     if(form.check2_5.checked==true)
 {
    	
	 showRow1_tampone5('details');

		if(document.details.RicercaTamponi2_5.lenght!=0){

			showEsitiTipo2_tampone5('details');
			mostraNextTampone6('details');
			}

 }
 }





 if(form.check1_6.checked==true)
 {
 showRow1_tampone6('details');

	if(document.details.RicercaTamponi_6.lenght!=0){

		showEsitiTampone6('details');
		mostraNextTampone7('details');
		}

 
 }else{ 
     if(form.check2_6.checked==true)
 {
	 showRow1_tampone6('details');

		if(document.details.RicercaTamponi2_6.lenght!=0){

			showEsitiTipo2_tampone6('details');
			mostraNextTampone7('details');
			}

 }
 }





 if(form.check1_7.checked==true)
 {
 showRow1_tampone7('details');

	if(document.details.RicercaTamponi_7.lenght!=0){

		showEsitiTampone7('details');
		mostraNextTampone8('details');
		}

 
 }else{ 
     if(form.check2_7.checked==true)
 {
	 showRow1_tampone7('details');

		if(document.details.RicercaTamponi2_7.lenght!=0){

			showEsitiTipo2_tampone7('details');
			mostraNextTampone8('details');
			}

 }
 }




 if(form.check1_8.checked==true)
 {
 showRow1_tampone8('details');

	if(document.details.RicercaTamponi_8.lenght!=0){

		showEsitiTampone8('details');
		mostraNextTampone9('details');
		}

 
 }else{ 
     if(form.check2_8.checked==true)
 {
	 showRow1_tampone8('details');

		if(document.details.RicercaTamponi2_8.lenght!=0){

			showEsitiTipo2_tampone8('details');
			}

 }
 }




 if(form.check1_9.checked==true)
 {
 showRow1_tampone9('details');

	if(document.details.RicercaTamponi_9.lenght!=0){

		showEsitiTampone9('details');
		mostraNextTampone10('details');
		}

 
 }else{ 
     if(form.check2_9.checked==true)
 {
	 showRow1_tampone9('details');

		if(document.details.RicercaTamponi2_9.lenght!=0){

			showEsitiTipo2_tampone9('details');
			mostraNextTampone10('details');
			}

 }
 }




 if(form.check1_10.checked==true)
 {
 showRow1_tampone10('details');

	if(document.details.RicercaTamponi_1.lenght!=0){

		showEsitiTampone10('details');
		
		}

 
 }else{ 
     if(form.check2_10.checked==true)
 {
	 showRow1_tampone10('details');

		if(document.details.RicercaTamponi2_10.lenght!=0){

			showEsitiTipo2_tampone10('details');
		
			}

 }
 }


}




  