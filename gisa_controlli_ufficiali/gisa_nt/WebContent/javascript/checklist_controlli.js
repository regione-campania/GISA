/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function ietruebody(){
return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
}
function ddrivetip(thetext, thewidth, thecolor){
if (ns6||ie){
if (typeof thewidth!="undefined") tipobj.style.width=thewidth+"px"
if (typeof thecolor!="undefined" && thecolor!="") tipobj.style.backgroundColor=thecolor
tipobj.innerHTML=thetext
enabletip=true
return false
}
}
function positiontip(e){
if (enabletip){
var nondefaultpos=false
var curX=(ns6)?e.pageX : event.clientX+ietruebody().scrollLeft;
var curY=(ns6)?e.pageY : event.clientY+ietruebody().scrollTop;

var winwidth=ie&&!window.opera? ietruebody().clientWidth : window.innerWidth-20
var winheight=ie&&!window.opera? ietruebody().clientHeight : window.innerHeight-20
var rightedge=ie&&!window.opera? winwidth-event.clientX-offsetfromcursorX : winwidth-e.clientX-offsetfromcursorX
var bottomedge=ie&&!window.opera? winheight-event.clientY-offsetfromcursorY : winheight-e.clientY-offsetfromcursorY
var leftedge=(offsetfromcursorX<0)? offsetfromcursorX*(-1) : -1000

if (rightedge<tipobj.offsetWidth){

tipobj.style.left=curX-tipobj.offsetWidth+"px"
nondefaultpos=true
}
else if (curX<leftedge)
tipobj.style.left="5px"
else{

tipobj.style.left=curX+offsetfromcursorX-offsetdivfrompointerX+"px"
pointerobj.style.left=curX+offsetfromcursorX+"px"
}

if (bottomedge<tipobj.offsetHeight){
tipobj.style.top=curY-tipobj.offsetHeight-offsetfromcursorY+"px"
nondefaultpos=true
}
else{
tipobj.style.top=curY+offsetfromcursorY+offsetdivfrompointerY+"px"
pointerobj.style.top=curY+offsetfromcursorY+"px"
}
tipobj.style.visibility="visible"
if (!nondefaultpos)
pointerobj.style.visibility="visible"
else
pointerobj.style.visibility="hidden"
}
}
function hideddrivetip(){
if (ns6||ie){
enabletip=false
tipobj.style.visibility="hidden"
pointerobj.style.visibility="hidden"
tipobj.style.left="-1000px"
tipobj.style.backgroundColor=''
tipobj.style.width=''
}
}


function aggiornaChecklistType(index, operazione, valoreRange, nota,range){

	
	
	if(isNaN(valoreRange))
	{
		
		alert('Attenzione assicurarsi di aver inserito un numero come punteggio ');
		document.getElementById("valoreRange"+index).value = '';
	}
	else
	{

	if(valoreRange > range)
	{
		alert('I punti inseriti non fanno parte del range specificato ! ');
		document.getElementById(index).value = '';
		
	}
	else
	{
	  if (operazione == '+'){
	    document.getElementById('btnAggiungiPunti' + index).style.backgroundColor = '66ff00';
	    document.getElementById('btnSottraiPunti' + index).style.backgroundColor = 'white';
	  }
	  if (operazione == '-'){
	    document.getElementById('btnSottraiPunti' + index).style.backgroundColor = '66ff00';
	    document.getElementById('btnAggiungiPunti' + index).style.backgroundColor = 'white';
	  }

	  operazioneList[index] = operazione;
	  valoreRangeList[index] = valoreRange;
	  notaList[index] = nota;
	

	  if(document.getElementById('operazione'+index)!=null)
	  document.getElementById('operazione'+index).value = operazione;
	  document.getElementById('valoreRange' + index).value = valoreRange;
	  document.getElementById('nota' + index).value = nota;

	  aggiornaTotale();
	  
	}
	}
	}

	function aggiornaTot(index, operazione, valoreRange){
		  
		if (operazione == '+'){
		    document.getElementById('btnAggiungiPunti' + index).style.backgroundColor = '66ff00';
		  }
		  
		  var op = operazione;
		  var valoreR = valoreRange;
		  var totale = Number(document.getElementById('livelloRischio').value);
			//totale += Number(document.getElementById('punteggioUltimiAnni').value);
		  if (valoreRange != ''){
		      if (operazione == '+'){
		    	  totale += Number(valoreR);
		      }
		      if (operazione == '-'){
		    	  totale -= Number(valoreR);
		      }
		    }
		  
		  document.getElementById('livelloRischio').value = totale;
		    
		}

	function aggiornaTotale(){  //in questa funzione entra ogni qualvolta clicco su un radio oltre ad eseguirla all'inizio

		
		
	  var totRisposte = 0;
	  for (n=0;n<idList.length;n++) { 
	    if (rispostaList[idList[n]] != ''){
	        
	      totRisposte += puntiList[idList[n]];
	    } else {
	      document.getElementById('risposta' + idList[n] + '1').checked = false;
	      document.getElementById('risposta' + idList[n] + '2').checked = false;
	      document.getElementById('punti' + idList[n]).value = '';
	    }
	  }
	  
	  var totRange = 0;
	  for (n=1;n<idTypeList.length;n++) { //ho messo 0 piuttosto che 1
	    var valoreRange = valoreRangeList[n];
	    var operazione = operazioneList[n];
	    if (valoreRange != ''){
	      if (operazione == '+'){
	        totRange += Number(valoreRange);
	      }
	      if (operazione == '-'){
	        totRange -= Number(valoreRange);
	      }
	    }
	  } 
	  var punteggio = 0;
	  if((document.getElementById('TipoC').value)==4){
		  if(document.getElementById('punteggioUltimiAnni')!=null)
		 punteggio = Number(document.getElementById('punteggioUltimiAnni').value);
	  }
	  
	  
	  document.getElementById('livelloRischio').value = totRisposte + totRange + punteggio;

		
	  
	}


	function aggiornaCategoria(){
		 
		 
		 
		  var totale = Number(document.getElementById('livelloRischio').value);

		 
		  var categoria=0;
		  if((totale==null) || (totale<1 && totale<1) )
			   categoria=3;
		  else
		   if(totale >= 1 && totale <= 150)
		   categoria= 1 ;
		   else
		   if(totale <=251 && totale >= 151)
		   categoria= 2 ;
		   else
		   if(totale >= 251 && totale <= 350)
		   categoria= 3 ;
		   else
		   if(totale >= 351 && totale <= 450)
		   categoria= 4 ;
		   else
		   if(totale >= 451 )
		 	 categoria=5;
		   else
			   categoria="-";   
			   
		   	  
		 //document.getElementById('categoriaRischio').value = categoria;
		    
		}

	function aggiornaListElement(id, parentId,grandparentid, punti, risposta){


	aggiornaTotale2(id,punti,risposta);
		//alert(id + " "+ parentId+ " "+punti+" "+risposta);
	  rispostaList[id] = risposta; 
	  puntiList[id] = punti;
	  requiredList[id] = true;
	 
	 //alert("entrato in aggiorna list element");
	  if (parentId == null || parentId == '-1' || grandparentid == null || grandparentid == '-1'){
		 
	    for (n=0;n<idList.length;n++) {
	      
	      if (parentIdList[idList[n]] == id || grandParentIdList[idList[n]] == id) {
	    
	    	
	        if (rispostaList[id] == 'si'){
	        
	        	
	        	if ( ((parentIdList[idList[n]]=='-1') &&  grandParentIdList[idList[n]] != '-1') || ((parentIdList[idList[n]]!='-1') &&  grandParentIdList[idList[n]] == '-1') || ((parentIdList[idList[n]]!='-1') &&  grandParentIdList[idList[n]] != '-1')  )
	        	{
	        		 
	        		 abilita(idList[n]);
	        	}
	        	else
	        	{
	        		
	        			disabilita(idList[n]);
	        		
	        	}
	         
	        } else { 
	          disabilita(idList[n]);
	          	g = n+1;
	          
	  		  // document.getElementById(g).style.background = "#EDEDED";

	  		    
	  		   
	  		  }
	          
	        }
	      
	    }
	  } 

	  aggiornaCategoria();
	 

	}
	
	
	
	
	function aggiornaListElementModify(id, parentId,grandParendId, punti, risposta){
		
		  rispostaList[id] = risposta; 
		  puntiList[id] = punti;
		  requiredList[id] = true;
		  if (parentId == null || parentId == '-1' ||grandParendId == null || grandParendId =='-1' ){
		    for (n=0;n<idList.length;n++) {
		      if (parentIdList[idList[n]] == id ||grandParentIdList[id]==id ) {
		        if (rispostaList[id] == 'si'){
		          abilita(idList[n]);
		        } else {
		          disabilita(idList[n]);
		        }
		      }
		    }
		  } else {
		    parentIdList[id] = parentId;
		    grandParentIdList[id] = grandParendId;
		    if (risposta == ''){
		      disabilita(id);
		    }
		  }
		  //aggiornaTotale2(id,punti,risposta);
		  //aggiornaCategoria();
		}


	function aggiornaTotale2(id,punti,risposta)
	{

		
		puntiAttuali = document.getElementById('livelloRischio').value;
		
		puntiAttuali = puntiAttuali-puntiList[id];
		document.getElementById('livelloRischio').value = puntiAttuali +punti;
		
		
	}

	function disabilitaCapitolo(idcapitolo,risposta,j ,indice)
	{
		codiciDomande =  domandePerCapitolo[idcapitolo];
		
	
		if(risposta == 'no')
		{
			
			 document.getElementById('risposta'+idcapitolo+'2').checked = true;
			 document.getElementById('risposta'+idcapitolo+'1').checked = false;

			punteggio =document.getElementById('livelloRischio').value;
				for(i = 0; i <codiciDomande.length; i++)
		{
				id = domandePerCapitolo[idcapitolo] [i];
				
			 requiredList[id] = false;
			  rispostaList[id] = ''; 
			if(id != undefined)
			{

				punteggio = punteggio - puntiList[id];	
			  document.getElementById('risposta'+id+'1').checked = false;
			  document.getElementById('risposta'+id+'2').checked = false;
			  document.getElementById('risposta'+id+'1').disabled = true;
			  document.getElementById('risposta'+id+'2').disabled = true;
			  document.getElementById('punti'+id).value = '';
			  puntiList[id]='';
			  if(document.getElementById(j)!=null){
				  document.getElementById(j).style.background = "#EDEDED";
				  j++;
				  }
			}
		}
				

		codiciDomande =  sottoDomandePerCapitolo[idcapitolo];
		
		for(i = 0; i <codiciDomande.length; i++)
		{
				id = sottoDomandePerCapitolo[idcapitolo] [i];
				
			 requiredList[id] = false;
			 rispostaList[id] = ''; 
			if(id != undefined)
			{
				
				punteggio = punteggio - puntiList[id];	
			  document.getElementById('risposta'+id+'1').checked = false;
			  document.getElementById('risposta'+id+'2').checked = false;
			  document.getElementById('risposta'+id+'1').disabled = true;
			  document.getElementById('risposta'+id+'2').disabled = true;
			  document.getElementById('punti'+id).value = '';
			  puntiList[id]='';
			  if(document.getElementById(j)!=null){
				  
				  document.getElementById(j).style.background = "#EDEDED";
				  j++;
				  }
			 
			}
		}
		
		 if(document.getElementById(j)!=null){
			  
			  document.getElementById(j).style.background = "#EDEDED";
			  j++;
			  }

		document.getElementById('livelloRischio').value=punteggio;
		document.getElementById("valoreRange"+indice).disabled = true ;
		
		}else
		{
			
			document.getElementById('risposta'+idcapitolo+'1').checked = true;
			document.getElementById('risposta'+idcapitolo+'2').checked = false;
			for(i = 0; i <codiciDomande.length; i++)
			{
				id = domandePerCapitolo[idcapitolo] [i];

					if(id!=undefined){
				  requiredList[id] = true;
				  rispostaList[id] = ''; 
				  document.getElementById('risposta'+id+'1').disabled = false;
				  document.getElementById('risposta'+id+'2').disabled = false;
					}
			
			}
	
			document.getElementById("valoreRange"+indice).disabled = false ;
		}
		
	}


	function defaultValori(id, parentId, punti, risposta,grandParentId){

		if(parentId == '-1' || grandParentId == '-1')
		{
		 requiredList[id] = true;
		}
		else
		{
			requiredList[id] = false;
		}
		 parentIdList[id] = parentId;
		 grandParentIdList[id] = grandParentId ;
		 rispostaList[id] = risposta; 
		 puntiList[id] = punti;
	    
	  }
	  //aggiornaTotale();
	  //aggiornaCategoria();

	function abilitaPulsanti(id)
	{

	value = document.getElementById("valoreRange"+id).value;
	if(value != '')
	{
	document.getElementById("btnAggiungiPunti"+id).disabled = false;
	document.getElementById("btnSottraiPunti"+id).disabled = false
	}
	else
	{
	document.getElementById("btnAggiungiPunti"+id).disabled = true;
	document.getElementById("btnSottraiPunti"+id).disabled = true;
	}


	}

	function disabilita(id){
	  requiredList[id] = false;
	  rispostaList[id] = ''; 

	  document.getElementById('risposta'+id+'1').checked = false;
	  document.getElementById('risposta'+id+'2').checked = false;
	  document.getElementById('risposta'+id+'1').disabled = true;
	  document.getElementById('risposta'+id+'2').disabled = true;
	  document.getElementById('punti'+id).value = '';
	}

	function abilita(id){
	  requiredList[id] = true;
	  rispostaList[id] = ''; 

	  document.getElementById('risposta'+id+'1').disabled = false;
	  document.getElementById('risposta'+id+'2').disabled = false;
	  
	}
	
	
	function lastDomanda(e,row){
		
		
		if(curr>0){

			if(currow==1){
				document.getElementById(""+curr).style.background="#CAEEFF";
				}else{
					document.getElementById(""+curr).style.background="#CAEEFF"//="#E8C889";
				}
			
		}

		currow=row;


			curr=e;

				document.getElementById(""+e).style.background="#66CCFF";//="yellow";
				if(document.getElementById("last")!=null)
				document.getElementById("last").value=curr;
			
			}



	function setStato(btn){


		check = true;
	  for (n=0;n<idList.length;n++) {
	  
		  if(requiredList[idList[n]] == true){
			  if(document.getElementById("risposta"+idList[n]+"1").checked == false && document.getElementById("risposta"+idList[n]+"2").checked==false)
			  {
		
		  check=false;
			  }
	    	
	       
	    }
	    
	  }
	  if(check==false || btn == 0)
	  {
		  
		  document.addAccountAudit.stato.value='Temporanea';
	  }
	  else
	  {
		  document.addAccountAudit.stato.value='Definitiva';
	  }
	  return check;
	}

		function checkForm(){

			document.getElementById("btnSave1").value = "Salvataggio in Corso";
			  document.getElementById("btnSave2").value = "Salvataggio in Corso";
			  //document.getElementById("btnSave1").disabled=true;
			  //document.getElementById("btnSave2").disabled=true;
			check = true;
		  for (n=0;n<idList.length;n++) {
		    if(rispostaList[idList[n]] == '' && requiredList[idList[n]] == true){
		    	document.getElementById(n+1).style.background = "red";
		        
		    	
		      check=false; 
		    }
		   
		  }
		  if(check==false)
		  {
			  alert('Per poter salvare la check list e \' necessario rispondere a tutte le domande.');
			  document.getElementById("btnSave1").disabled=false;
			  document.getElementById("btnSave2").disabled=false;
			  //document.getElementById("btnSave1").value = "Salva";
			  //document.getElementById("btnSave2").value = "Salva";
		  }
		  return check;
		}
		
		function ultimaDomanda(idd){


			curr=idd;
			if(document.getElementById(""+idd)!=null)
			document.getElementById(""+idd).style.background="yellow";
			
		}


		function inizializzaPunteggio(punteggio)
		{
			document.getElementById("livelloRischio").value = punteggio;
		}
		

		function aggiornaListElement2(id, parentId, punti, risposta,puntivekki){

		  rispostaList[id] = risposta; 
		  
		  requiredList[id] = true;
		  if (parentId == null || parentId == '-1'){
		    for (n=0;n<idList.length;n++) {
		      if (parentIdList[idList[n]] == id) {
		        if (rispostaList[id] == 'si'){
		          abilita(idList[n]);
		        } else {
		          disabilita(idList[n]);
		      	document.getElementById(n+1).style.background = "#EDEDED";
		        }
		      }
		    }
		  } else {
		    parentIdList[id] = parentId;
		    if (risposta == ''){
		      disabilita(id);
		  	document.getElementById(n+1).style.background = "#EDEDED";
		    }
		  }
		  //aggiornaTotale2(id,punti,risposta);
		  aggiornaCategoria();
		}

domandePerCapitolo = new Array();
sottoDomandePerCapitolo = new Array();
capitolidadisabilitare = new Array();
var offsetfromcursorX=12 
var offsetfromcursorY=10 
var offsetdivfrompointerX=10 
var offsetdivfrompointerY=14 
document.write('<div id="dhtmltooltip"></div>') //write out tooltip DIV
document.write('<img id="dhtmlpointer" src="images/arrow2.gif">') //write out pointer image
var ie=document.all
var ns6=document.getElementById && !document.all
var enabletip=false
if (ie||ns6)
var tipobj=document.all? document.all["dhtmltooltip"] : document.getElementById? document.getElementById("dhtmltooltip") : ""
var pointerobj=document.all? document.all["dhtmlpointer"] : document.getElementById? document.getElementById("dhtmlpointer") : ""

document.onmousemove=positiontip
