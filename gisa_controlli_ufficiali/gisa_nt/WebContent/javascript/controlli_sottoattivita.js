/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var ins ;
function trim(stringa){
	
	if(stringa!='')
	{
    while (stringa.substring(0,1) == ' '){
        stringa = stringa.substring(1, stringa.length);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == ' '){
        stringa = stringa.substring(0,stringa.length-1);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == '\n'){
        stringa = stringa.substring(0,stringa.length-1);
    }
	}
    return stringa;
}

function abilitaStatiNc(inseriti,tipoNc,idControllo,followup_formali_inseriti,followup_significativi_inseriti,followup_gravi_inseriti,lista_followup_gravi)
{
	
	
	ins = inseriti ;
	
	settaCampiGlobale(inseriti,tipoNc);	
	
	
	if(tipoNc=='1')
	{
		settaCampiTabFormali(followup_formali_inseriti);
		
		abilitaStatoFormali(idControllo);
		
		clonaFollowup();
		
	}
	
	if(tipoNc=='2')
	{
		settaCampiTabSignificativi(followup_significativi_inseriti);
		abilitaStatoSignificative(idControllo);
		clonaFollowupSignificative();
	}
	if(tipoNc=='3')
	{
	
		settaCampiTabGravi(followup_gravi_inseriti,lista_followup_gravi);
		
		abilitaStatoGravi(idControllo);
		clonaFollowupGravi();
		
	}
	
} 
			   
  
function settaCampiGlobale(inseriti,tipoNc)
{
if (inseriti !=null && inseriti != 'null')
	{
		
		document.getElementById('Inseriti').value = inseriti ;
		window.opener.document.getElementById('formali').value = inseriti ;
     }
     else
     {
    	 if (window.opener !=null)
      	document.getElementById('Inseriti').value = window.opener.document.getElementById('formali').value  ;
   	 }
}

/**
 * 	SETTA IGLI ID DEI FOLLOWUP INSERITI NELLA FINESTRA CHIAMANTE E IN QUELLA CHIAMATA
 * 
 * */

function settaCampiTabFormali(followup_formali_inseriti)
{
	
if (followup_formali_inseriti !=null && followup_formali_inseriti != 'null')
{
	
		window.opener.document.getElementById('followup_inseriti_formali').value = followup_formali_inseriti ;
		document.getElementById('followup_formali_inseriti').value = followup_formali_inseriti ;
}
else
{
 	document.getElementById('followup_formali_inseriti').value = window.opener.document.getElementById('followup_inseriti_formali').value  ;
}

}

function settaCampiTabSignificativi(followup_significativi_inseriti)
{
if (followup_significativi_inseriti !=null && followup_significativi_inseriti != 'null')
{
		window.opener.document.getElementById('followup_inseriti_significativi').value = followup_significativi_inseriti ;
		document.getElementById('followup_significativi_inseriti').value = followup_significativi_inseriti ;	
} 
else
{
 	document.getElementById('followup_significativi_inseriti').value = window.opener.document.getElementById('followup_inseriti_significativi').value  ;
}
}


function settaCampiTabFollowupGravii(followup_significativi_inseriti)
{
if (followup_significativi_inseriti !=null && followup_significativi_inseriti != 'null')
{
		window.opener.document.getElementById('followup_gravi_inseriti').value = followup_significativi_inseriti ;
		document.getElementById('followup_gravi_inseriti').value = followup_significativi_inseriti ;	
} 
else
{
 	document.getElementById('followup_gravi_inseriti').value = window.opener.document.getElementById('followup_gravi_inseriti').value  ;
}
}
  
function settaCampiTabGravi(attivita_gravi_inseriti,lista_followup_gravi)
{
	
if (attivita_gravi_inseriti !=null && attivita_gravi_inseriti != 'null')
{
		window.opener.document.getElementById('attivita_inseriti_gravi').value = attivita_gravi_inseriti ;
		document.getElementById('attivita_gravi_inseriti').value = attivita_gravi_inseriti ;	
} 
else
{
 	document.getElementById('attivita_gravi_inseriti').value = window.opener.document.getElementById('attivita_inseriti_gravi').value  ;
}

if (lista_followup_gravi !=null && lista_followup_gravi != 'null' && lista_followup_gravi != '')
{
		window.opener.document.getElementById('followup_gravi_inseriti').value = lista_followup_gravi ;
		document.getElementById('followup_gravi_inseriti').value = lista_followup_gravi ;	
} 
else
{
 	document.getElementById('followup_gravi_inseriti').value = window.opener.document.getElementById('followup_gravi_inseriti').value  ;
}
}


function abilitaStatoFormali(idControllo)
{	
	formali = window.opener.document.getElementById('abilita_formali').value ;
	haveNC = window.opener.document.getElementById('descrizione_or_combo_sel_formali').value;
	haveFollowup = 'false';
	if(window.opener.document.getElementById('followup_inseriti_formali').value!='')
	{
		haveFollowup = 'true' ;
	}
	if ((formali == 'true' && haveFollowup == 'true' && haveNC =='false') || (formali == 'false' && haveFollowup == 'false' && haveNC =='false'))
	{
		stato = true ;
	}
	else
	{
		stato = false ;
	}
	abilitaStatoFormaliCallBack(stato);
	
}
	
function abilitaStatoFormaliCallBack(value)
{
	var msg_default = 'INSERIRE QUI LA DESCRIZIONE PER LA SINGOLA NON CONFORMITA'; 
	window.opener.document.getElementById('stato_formali').value = value;
	 if (ins != 'null' )
	 {
		 num_elementi_formali = window.opener.document.getElementById('elementi_nc_formali').value;
		 for (i=1 ; i<=num_elementi_formali; i++)
		 {
			 if  ( window.opener.document.getElementById('Provvedimenti1_'+i).selected == false || 
						( trim(window.opener.document.getElementById('Provvedimenti1_'+i).value) != ''		&&
						  trim(window.opener.document.getElementById('nonConformitaFormali_'+i).value) != msg_default
					 	)
					)
			 {
			 window.opener.document.getElementById('Provvedimenti1_'+i).disabled	=	'true';
			 window.opener.document.getElementById('nonConformitaFormali_'+i).readOnly	=	'true';
		 
			 }
		 }
		
		window.close();
		 abilitaAll();
			
	 }
	 abilitaInserimentoTotale();
}

function abilitaAll()
{
var arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('textarea');

var i;
for(i=0; i<arrayControlli.length; i++)  
	arrayControlli[i].disabled= false;

//arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('select');
//for(i=0; i<arrayControlli.length; i++)  
	//arrayControlli[i].disabled= false;
arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('a');
for(i=0; i<arrayControlli.length; i++)  
	arrayControlli[i].disabled= false;
arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('input');
for(i=0; i<arrayControlli.length; i++)  
	arrayControlli[i].disabled= false;

abilitaInserimentoTotale()
}
 

function disabilitaAll()
{
var arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('textarea');

var i;
for(i=0; i<arrayControlli.length; i++)  
	arrayControlli[i].disabled= true;

//arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('select');
//for(i=0; i<arrayControlli.length; i++)  
	//arrayControlli[i].disabled= true;
arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('a');
for(i=0; i<arrayControlli.length; i++)  
	arrayControlli[i].disabled= true;
arrayControlli  = window.opener.document.getElementById('nc').getElementsByTagName('input');
for(i=0; i<arrayControlli.length; i++)  
	arrayControlli[i].disabled= true;


}

function abilitaStatoSignificative(idControllo)
{	
	
	significative = window.opener.document.getElementById('abilita_significative').value ;
	haveNC = window.opener.document.getElementById('descrizione_or_combo_sel_significative').value;
	haveFollowup = 'false';
	if(window.opener.document.getElementById('followup_inseriti_significativi').value!='')
	{
		haveFollowup = 'true' ;
	}
	
	
	if ((significative == 'true' && haveFollowup == 'true' && haveNC =='false') || (significative == 'false' && haveFollowup == 'false' && haveNC =='false'))
	{
		stato = true ;
	}
	else
	{
		stato = false ;
	}
	
	abilitaStatoSignificativeCallBack(stato)
}
	
function abilitaStatoSignificativeCallBack(value)
{
	var msg_default = 'INSERIRE QUI LA DESCRIZIONE PER LA SINGOLA NON CONFORMITA'; 
		window.opener.document.getElementById('stato_significative').value = value;
		 if (ins != 'null' )
		 {
			 num_elementi_formali = window.opener.document.getElementById('elementi_nc_significative').value;
			 for (i=1 ; i<=num_elementi_formali; i++)
			 {
				 if  ( window.opener.document.getElementById('Provvedimenti2_'+i).selected == false || 
							( trim(window.opener.document.getElementById('Provvedimenti2_'+i).value) != ''		&&
							  trim(window.opener.document.getElementById('nonConformitaSignificative_'+i).value) != msg_default
						 	)
						)
				 {
				 window.opener.document.getElementById('Provvedimenti2_'+i).disabled	=	'true';
				 window.opener.document.getElementById('nonConformitaSignificative_'+i).readOnly	=	'true';
			 
				 }
			 }
			
			window.close();
			abilitaAll();
		 }
		 abilitaInserimentoTotale();
}


function abilitaStatoGravi(idControllo)
{	
	gravi = window.opener.document.getElementById('abilita_gravi').value ;
	haveNC = window.opener.document.getElementById('descrizione_or_combo_sel_gravi').value;
	haveFollowup = 'false';
	if(window.opener.document.getElementById('attivita_inseriti_gravi').value!='' || window.opener.document.getElementById('followup_gravi_inseriti').value!='')
	{
		haveFollowup = 'true' ;
	}
	
	if ((gravi == 'true' && haveFollowup == 'true' && haveNC =='false') || (gravi == 'false' && haveFollowup == 'false' && haveNC =='false'))
	{
		stato = true ;
	}
	else
	{
		stato = false ;
	}
	abilitaStatoGraviCallBack(stato);
	
} 
	
function abilitaStatoGraviCallBack(value)
{
	
		var msg_default = 'INSERIRE QUI LA DESCRIZIONE PER LA SINGOLA NON CONFORMITA'; 
		window.opener.document.getElementById('stato_gravi').value = value;
		 if (ins != 'null' )
		 {
			 num_elementi_formali = window.opener.document.getElementById('elementi_nc_gravi').value;
			 for (i=1 ; i<=num_elementi_formali; i++)
			 {
				 if  ( window.opener.document.getElementById('Provvedimenti3_'+i).selected == false || 
							( trim(window.opener.document.getElementById('Provvedimenti3_'+i).value) != ''		&&
							  trim(window.opener.document.getElementById('nonConformitaGravi_'+i).value) != msg_default
						 	)
						)
				 {
				 window.opener.document.getElementById('Provvedimenti3_'+i).disabled	=	'true';
				 window.opener.document.getElementById('nonConformitaGravi_'+i).readOnly	=	'true';
			 
				 }
			 }
			
				window.close();
				abilitaAll();
		 }
		 abilitaInserimentoTotale();
}


function abilitaInserimentoTotale()
{
	
if( window.opener.document.getElementById('stato_gravi').value=='true'  	&&
		window.opener.document.getElementById('stato_significative').value=='true'	&&
		window.opener.document.getElementById('stato_formali').value=='true'
)
{
	if (window.opener.document.getElementById('btn_salva')!=null)
		window.opener.document.getElementById('btn_salva').disabled='';
	if (window.opener.document.getElementById('btn_salva2')!=null)
		window.opener.document.getElementById('btn_salva2').disabled='';
}
else
{
	if (window.opener.document.getElementById('btn_salva')!=null)
		window.opener.document.getElementById('btn_salva').disabled='true';
	if (window.opener.document.getElementById('btn_salva2')!=null)
		window.opener.document.getElementById('btn_salva2').disabled='true';
}
}
  

function clonaFollowup()
{
	var clonato = window.opener.document.getElementById('lista_followup_formali');
  
  	
  	/*clona riga vuota*/
  
  	var array = window.opener.document.getElementById('followup_inseriti_formali').value.split(";");
	
  	for (i=0;i<array.length;i++)
  	{
  		if(window.opener.document.getElementById('lista_followup_formali_'+i)!=null)
  	  		window.opener.document.getElementById('lista_followup_formali_'+i).parentNode.removeChild(window.opener.document.getElementById('lista_followup_formali_'+i));

  	}
  
  	for (i=0;i<array.length;i++)
  	{
  		if(array[i]!="")
  		{
  		clone=clonato.cloneNode(true);
  		clone.getElementsByTagName('label')[0].innerHTML = "Followup "+(parseInt(i)+1);
  		clone.getElementsByTagName('label')[1].innerHTML = array[i];
  		clone.id="lista_followup_formali_"+i;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		}
  	}
}


function clonaFollowupSignificative()
{
	
  	var clonato = window.opener.document.getElementById('lista_followup_significativi');
  
  	
  	/*clona riga vuota*/
  
  	var array = window.opener.document.getElementById('followup_inseriti_significativi').value.split(";");
  	for (i=0;i<array.length;i++)
  	{
  		if(window.opener.document.getElementById('lista_followup_significativi_'+i)!=null)
  	  		window.opener.document.getElementById('lista_followup_significativi_'+i).parentNode.removeChild(window.opener.document.getElementById('lista_followup_significativi_'+i));

  	}
  	
  	for (i=0;i<array.length;i++)
  	{
  		
  		if(array[i]!='')
  		{
  		clone=clonato.cloneNode(true);
  		clone.getElementsByTagName('label')[0].innerHTML = "Followup "+(parseInt(i)+1);
  		clone.getElementsByTagName('label')[1].innerHTML = array[i];
  		clone.id="lista_followup_significativi_"+i;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		}
  	}
	

}


function clonaFollowupGravi()
{
	
  	var clonato = window.opener.document.getElementById('lista_followup_gravi');
  	/*clona riga vuota*/
  	var array = window.opener.document.getElementById('attivita_inseriti_gravi').value.split(";");
  	array_followup = window.opener.document.getElementById('followup_gravi_inseriti').value.split(";");
  	/*RIMUOVE SANZIONI SEQUESTRI E NOTIZIE DI REATO*/
  	num = 0;
  	for (i=0;i<array.length;i++)
  	{ 
  		if(window.opener.document.getElementById('lista_followup_gravi_'+num)!=null)
  	  		window.opener.document.getElementById('lista_followup_gravi_'+num).parentNode.removeChild(window.opener.document.getElementById('lista_followup_gravi_'+num));
  			num ++ ;
  	}
  	/*RIMUOVE FOLLOWUP */
  	for (i=0;i<array_followup.length;i++)
	{ 
		if(window.opener.document.getElementById('lista_followup_gravi_'+num)!=null)
			window.opener.document.getElementById('lista_followup_gravi_'+num).parentNode.removeChild(window.opener.document.getElementById('lista_followup_gravi_'+num));
			num++ ;
	}
  
  	num = 0;
  	for (i=0;i<array.length;i++)
  	{
  		
  		var array2 = array[i].split("-");
  		
  		if (array2[1]!='' && array2[1]!=undefined)
  		{
  		clone=clonato.cloneNode(true);
  		
  		if(array2[1]=='san')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+(parseInt(num)+1)+ " (Sanzione)";
  		}
  		if(array2[1]=='diff')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+(parseInt(num)+1)+ " (Diffida)";
  		}
  		if(array2[1]=='seq')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+(parseInt(num)+1)+ " (Sequestro)";
  		}
  		if(array2[1]=='rea')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+(parseInt(num)+1)+ " (Nota di Reato)";
  		}
  		if(array2[1]=='fol')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+(parseInt(num)+1)+ " (Followup)";
  		}
  		
  		
  		clone.getElementsByTagName('label')[1].innerHTML = array2[0];
  		clone.id="lista_followup_gravi_"+num;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		num = num+1;
  		}
  	}
  	
  	for (i=0;i<array_followup.length;i++)
  	{
  		if (array_followup[i]!='')
  		{
  			
		clone=clonato.cloneNode(true);
  		clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+(parseInt(num)+1)+ " (Followup)";
  		clone.getElementsByTagName('label')[1].innerHTML = array_followup[i];
  		clone.id="lista_followup_gravi_"+num;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		num++;
  		}
  	}

  
}

/**
 * SETTA IL CONTENUTO DELLA COMBO AZIONI NON CONFORME PER (PRESENTE NELLE SANZIONI) , 
 * CON LE VOCI INSERITE PER LE NON CONFORMIT� GRAVI
 * @return
 */
function carica_contenuto_combo_sanzioni()
{
	
	if (window.opener!=null)
{
	var select = document.forms['addticket'].Provvedimenti; //Recupero la SELECT
	
	var NewOpt = document.createElement('option');
    NewOpt.value = -1; // Imposto il valore
    NewOpt.text = '-- SELEZIONARE UNA VOCE --'// Imposto il testo
    NewOpt.selected=true;
    try
    {
  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }
	var num_elementi_gravi = window.opener.document.getElementById('elementi_nc_gravi').value;
	
	for (i=1;i<=num_elementi_gravi;i++)
	{
		var combo = window.opener.document.getElementById('Provvedimenti3_'+i);
		
		for(j=0;j<combo.length;j++)
		{
			if(combo[j].selected==true && combo[j].value!=-1)
			{
				esistente = false ;
				for (z=0;z<select.length;z++)
				{
					if(select[z]!=null && select[z].value==combo[j].value)
					{
						esistente = true ;
					}
				}
				if(esistente ==false)
				{
				var NewOpt = document.createElement('option');
			    NewOpt.value = combo[j].value; // Imposto il valore
			    NewOpt.text = combo[j].text; // Imposto il testo
			  
			    try
			    {
			  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			    }catch(e){
			  	  select.add(NewOpt); // Funziona solo con IE
			    }
				}
			   
			}
		}}
		
	
		
	}
       
    }






			
		 
