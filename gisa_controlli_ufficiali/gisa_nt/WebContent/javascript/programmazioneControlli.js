/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function disabilitaPeriodo(form)
{
	if(document.getElementById('durata')!=null	)
	{
		durata = document.getElementById('durata').value ;
		
	}
	else
	{
		durata = document.getElementById('searchcodedurata').value ;
		
	}

	//nome_form = form.name;
	
	if(durata!='20')
	{

		document.getElementById('data1').value = "" ;
		document.getElementById('data2').value = "" ;
		document.getElementById('data1').readOnly = 'true' ;
		document.getElementById('data2').readOnly = 'true' ;
		document.getElementById("validita").style.display="none";
		
		if (document.getElementById("obbligatorio")!=null)
		{
			document.getElementById("obbligatorio").style.display="none";
		}
		
	}
	else
	{
		if (document.getElementById("obbligatorio")!=null)
		{
			document.getElementById("obbligatorio").style.display="";
		}
		document.getElementById('data1').readOnly = '' ;
		document.getElementById('data2').readOnly = '' ;
		document.getElementById("validita").style.display="";
		
	}
}
function clearForm()
{
	document.getElementById('data1').value = "" ;
	document.getElementById('data2').value = "" ;
	 document.getElementById('searchcodedurata').value = "";
	 document.getElementById('piano_monitoraggio').value = "";
	 disabilitaPeriodo(document.searchProgrammazione);

}
function controlloNumerico(value)
{
	if (isNaN(value))
	{
		return false ;
	}
	return true ;
	
}
function checkForm(form){
	  
    formTest = true;
    message = "";

    var aslc = document.addProgrammazione.asl_coinvolte ;
    
  
    
    if (aslc.length!=null)
    {
    for(i=0;i<aslc.length;i++)
    {
    
    	if (document.getElementById('cu_'+aslc[i].value).value == 'Non Rilevante')
    	{
    		document.getElementById('cu_'+aslc[i].value).value = '0' ; 
    		
    	}
    	else
    	{
    	if(controlloNumerico(document.getElementById('cu_'+aslc[i].value).value)==false)
    	{
    		formTest = false;
    		message += "- Controllare di aver inserito valori numerici nella pianificazione dei controlli \n";
    		break;
    	}
    	} 
    	
    }
    }else
    {
    	if (document.getElementById('cu_'+aslc.value).value == 'Non Rilevante')
    	{
    		document.getElementById('cu_'+aslc.value).value = '0' ; 
    		
    	}
    	else
    	{
    	if(controlloNumerico(document.getElementById('cu_'+aslc[i].value).value)==false)
    	{
    		formTest = false;
    		message += "- Controllare di aver inserito valori numerici nella pianificazione dei controlli \n";
    	
    	}
    	} 
    	
    }
   
    if (aslc.length!=null)
    {
    for(i=0;i<aslc.length;i++)
    {
    	
    	 if (document.getElementById('campioni_'+aslc[i].value).value == 'Non Rilevante')
    		{
    			document.getElementById('campioni_'+aslc[i].value).value = '0' ; 
    			
    		}
    		else
    	{
    	if(controlloNumerico(document.getElementById('campioni_'+aslc[i].value).value)==false)
    	{
    		formTest = false;
    		message += "- Controllare di aver inserito valori numerici nella pianificazione dei campioni \n";
    		
    	}
    	
    }
	}
    }else{
    	 if (document.getElementById('campioni_'+aslc.value).value == 'Non Rilevante')
 		{
 			document.getElementById('campioni_'+aslc.value).value = '0' ; 
 			
 		}
 		else
 	{
 	if(controlloNumerico(document.getElementById('campioni_'+aslc[i].value).value)==false)
 	{
 		formTest = false;
 		message += "- Controllare di aver inserito valori numerici nella pianificazione dei campioni \n";
 		
 	}
 	
 }
    	
    }
    asl_scelte = false ;
    
    if(aslc.length!=null)
    {
    for(i=0;i<aslc.length;i++)
    {
    	if(trim(document.getElementById('cu_'+aslc[i].value).value)!='' && trim(document.getElementById('cu_'+aslc[i].value).value)!='0')
    	{
    		asl_scelte = true ;
    		break ;
    	}
    }
    }else
    {
    	if(trim(document.getElementById('cu_'+aslc.value).value)!='' && trim(document.getElementById('cu_'+aslc.value).value)!='0')
    	{
    		asl_scelte = true ;
    		
    	}
    	
    }
    
    
    if(aslc.length!=null)
    {
    for(i=0;i<aslc.length;i++)
    {
    	if(trim(document.getElementById('campioni_'+aslc[i].value).value)!='' && trim(document.getElementById('campioni_'+aslc[i].value).value)!='0')
    	{
    		asl_scelte = true ;
    		break ;
    	}
    }
    }else 
    {
    	if(trim(document.getElementById('campioni_'+aslc.value).value)!='' && trim(document.getElementById('campioni_'+aslc.value).value)!='0')
    	{
    		asl_scelte = true ;
    		
    	}
    	
    }
    
    
    
    if (asl_scelte == false)
    {
    	message += "- Controllare di aver pianificato un numero di controlli o campioni almeno per un\'asl \n";
		formTest = false;
    }
    
    
    if(form.durata.value =="20")
    {
    if( trim(form.data1.value)=='')
	{
    	message += "- Controllare di aver inserito la data dal \n";
		formTest = false;
	}
    
    if( trim(form.data2.value)=='')
	{
    	message += "- Controllare di aver inserito la data al \n";
		formTest = false;
	}
    }
    if(form.piano_monitoraggio.value =="-1")
    {
    	message += "- Controllare di aver selezionato un piano dalla lista dei piani di monitoraggio \n";
		formTest = false;
    }
    if (formTest == false) {
    	for(i=0;i<aslc.length;i++)
        {
    		if (document.getElementById('cu_'+aslc[i].value).value == '-1' )
        	{
    			document.getElementById('cu_'+aslc[i].value).value = 'Non Rilevante' ;
        	}
    		if (document.getElementById('campioni_'+aslc[i].value).value == '-1' )
        	{
    			document.getElementById('campioni_'+aslc[i].value).value = 'Non Rilevante' ;
        	}
        }
    		
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }

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

function calcotaTotaleCu()
{
	
indifce = 0 ;
totale = 0 ;

while (document.getElementById('padre_'+indifce)!=null)
{
	if(controlloNumerico(document.getElementById('cu_'+document.getElementById('padre_'+indifce).value).value)==true)
	{
		
		totale += parseInt(document.getElementById('cu_'+document.getElementById('padre_'+indifce).value).value);
	}
	indifce++;
}
document.getElementById('totaleCu').value = totale ;

}

 
function calcotaTotaleCuFigli(campoPadre,idPadre)
{
	
	indifce = 0 ;
	totale = 0 ;
	campoPadre.value =''
	while (document.getElementById('figli_'+indifce+'_'+idPadre)!=null)
	{
		
		if(controlloNumerico(document.getElementById('cu_'+document.getElementById('figli_'+indifce+'_'+idPadre).value).value)==true)
		{
		totale += parseInt(document.getElementById('cu_'+document.getElementById('figli_'+indifce+'_'+idPadre).value).value);
		
		}
		indifce ++ ;
	}
	campoPadre.value = totale;
	calcotaTotaleCu();

}

function calcotaTotaleCampioniFigli(campoPadre,idPadre)
{
	
	indifce = 0 ;
	totale = 0 ;
	campoPadre.value =''
	while (document.getElementById('figli_'+indifce+'_'+idPadre)!=null)
	{
		
		if(controlloNumerico(document.getElementById('campioni_'+document.getElementById('figli_'+indifce+'_'+idPadre).value).value)==true)
		{
		totale += parseInt(document.getElementById('campioni_'+document.getElementById('figli_'+indifce+'_'+idPadre).value).value);
		
		}
		indifce ++ ;
	}
	campoPadre.value = totale;
	calcotaTotaleCampioni();
}

function calcotaTotaleCampioni()
{
	
indifce = 0 ;
totale = 0 ;

while (document.getElementById('padre_'+indifce)!=null)
{
	if(controlloNumerico(document.getElementById('campioni_'+document.getElementById('padre_'+indifce).value).value)==true)
	{
		
		totale += parseInt(document.getElementById('campioni_'+document.getElementById('padre_'+indifce).value).value);
	}
	indifce++;
}
document.getElementById('totaleCampioni').value = totale ;

}