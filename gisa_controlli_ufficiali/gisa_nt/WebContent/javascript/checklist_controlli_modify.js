/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
domandePerCapitolo = new Array();
sottoDomandePerCapitolo = new Array();
capitolidadisabilitare = new Array();
progressiviCapitoli = new Array();

function inizializzaPunteggio(punteggio)
{
	document.getElementById("livelloRischio").value = punteggio;
}

function disabilitaCapitolo(idcapitolo,risposta,j,indice )
{
	
	
	codiciDomande =  domandePerCapitolo[idcapitolo];
	
	if(risposta == 'no')
	{
		
		if( document.getElementById('risposta'+idcapitolo+'2')!=null)
			document.getElementById('risposta'+idcapitolo+'2').checked = true;
		 
		if(document.getElementById('risposta'+idcapitolo+'1')!=null)
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
	
	//document.getElementById("valoreRange"+indice).readonly = true ;
	
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
		
		//document.getElementById("valoreRange"+indice).readonly = false ;
		
	}
	
}


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
document.onmousemove=positiontip



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

	
	
  operazioneList[index] = operazione;
  valoreRangeList[index] = valoreRange;
  notaList[index] = nota;
  document.getElementById('operazione' + index).value = operazione;
  document.getElementById('valoreRange' + index).value = valoreRange;

if(document.getElementById('nota' + index) != null)
    document.getElementById('nota' + index).value = nota;

  aggiornaTotale();
}
	}

}
	function aggiornaTotale(){

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
  for (n=1;n<idTypeList.length;n++) {
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
  
  if((document.getElementById('TipoC').value)==5){
	  if(document.getElementById('punteggioUltimiAnni')!=null)
	  {
		  if(document.getElementById('punteggioUltimiAnni').value != null){
	  var punteggio = Number(document.getElementById('punteggioUltimiAnni').value);
		  }
		  else
		  {
var punteggio = 0;
			  }
	  }else
	  {
		  var punteggio = 0;
		  }
	  
	  }
	  
  document.getElementById('livelloRischio').value = totRisposte + totRange + punteggio;
}


var curr=-1;
var currow=-1;



function lastDomanda(e,row){

	
if(curr>0){

	if(currow==1){
		document.getElementById(""+curr).style.background="#CAEEFF";
		}else{
			document.getElementById(""+curr).style.background="#CAEEFF";
		}
	
}

currow=row;
curr=e;
document.getElementById("last").value=curr;
if(document.getElementById("last")!=null)
document.getElementById(""+e).style.background="#66CCFF";
		
		
	}



function ultimaDomanda(idd){


	curr=idd;
	if(document.getElementById(""+idd)!=null)
	document.getElementById(""+idd).style.background="yellow";
	
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
		   
	document.getElementById('categoriaRischio').value = categoria;
	    
	}

function aggiornaTotale2(id,punti,risposta)
{

	
	puntiAttuali = document.getElementById('livelloRischio').value;
	
	puntiAttuali = puntiAttuali - puntiList[id];
		
	document.getElementById('livelloRischio').value = puntiAttuali +punti;
	//alert("parziale "+document.getElementById('livelloRischio').value+". Punti Attuali - puntidomanda = "+puntiAttuali+" - "+punti);
	puntiList[id] = punti;

	
}







function aggiornaListElementModify(id, parentId,grandparentid, punti, risposta,stato){

	
  rispostaList[id] = risposta; 
  puntiList[id] = punti;
  requiredList[id] = true;
 
  
  if (parentId == null || parentId == '-1' || grandparentid == null || grandparentid == '-1'){
	  
    for (n=0;n<idList.length;n++) {
    	 if (parentIdList[idList[n]] == id || grandParentIdList[idList[n]] == id ) {
    		
    		
        if (rispostaList[id] == 'si')
        {
        
          abilita(idList[n]);
        } else 
        {
        	if (risposta=='') 
        	{
        		abilita(idList[n]);
        	}
        	else
        	{
        	disabilita(idList[n]);
        	}
        } 
      }
    	 else
    	 {
    		 grandParentIdList[id] = grandparentid ;
    		 parentIdList[id] = parentId;
    		 //if (((parentIdList[id]!='-1' && rispostaList[parentIdList[id]] !='')  || ( grandParentIdList[id] !='-1' && rispostaList[grandParentIdList[id]] !=''))){
    		 if (((parentIdList[idList[n]]!='-1' && rispostaList[parentIdList[idList[n]]] =='si')  || ( grandParentIdList[idList[n]] !='-1' && rispostaList[grandParentIdList[idList[n]]] !='si')) ){
    		
    			abilita(idList[n]);    		
    			abilita(id); 
    		 }
    		 else
    		 {
    			 if (((parentIdList[idList[n]]!='-1' && rispostaList[parentIdList[idList[n]]] =='no')  || ( grandParentIdList[idList[n]] !='-1' && rispostaList[grandParentIdList[idList[n]]] =='no')) ){
    		    		
    				 disabilita(idList[n]);    		
    				 disabilita(id);
    			 }
    			 else
    			 if(((parentId == '-1' && grandparentid == '-1') || (parentId == '-1' && grandparentid != '-1') ))
    			
    				 abilita(id); 
    			 else
    				 if (((parentIdList[idList[n]]!='-1' && rispostaList[parentIdList[idList[n]]] =='si')  || ( grandParentIdList[idList[n]] !='-1' && rispostaList[grandParentIdList[idList[n]]] =='si')) ){
     		    		
        				 abilita(idList[n]);    		
        				 abilita(id);
        			 }else
    				 if(risposta=='' && rispostaList[parentId]=='si' || rispostaList[grandparentid]=='si')
    					 disabilita(id);
    				 else
    					 if(risposta=='')
    						 disabilita(id);

    		 }
    		
    		   
    	 }}
    
  } else {
		 
/*disabilita tutte le domande che hanno si a padre che nonno*/
	grandParentIdList[id] = grandparentid ;
    parentIdList[id] = parentId;
    
  
	 if (risposta == '' ){//&&  (rispostaList[parentIdList[id]] !=''  || rispostaList[grandParentIdList[id]] !='')){
		  disabilita(id);
	    }
   
  }
  aggiornaTotale2(id,punti,risposta);
  //aggiornaCategoria();
}



function aggiornaListElementModify2(id, parentId,grandparentid, punti, risposta,stato){

	
  rispostaList[id] = risposta; 
  puntiList[id] = punti;
  requiredList[id] = true;

 
  /**
   * se � una domanda che non ha padre
   * se � una super domanda
   */
 
  if (parentId == null || parentId == '-1' || grandparentid == null || grandparentid == '-1' || grandparentid ==''){
    for (n=0;n<idList.length;n++) {
    	
    
    	 if (parentIdList[idList[n]] == id && grandParentIdList[idList[n]] == '-1' ) // se sto ispezionando una domanda figlia 
    	 {
    		 
    		 
    		 if (rispostaList[parentIdList[idList[n]]] == 'si')
    		 {
    			 abilita(idList[n]);
    		 }
    		 else
    		 {
    			disabilita(idList[n]);
    		 }
    		 
    	 }
    	 else
    	 {
    		 if (grandParentIdList[idList[n]] == id && parentIdList[idList[n]] == '-1' ) // se sto ispezionando una domanda figlia di una super domanda
    		 {
    			 
    			 if (rispostaList[grandParentIdList[idList[n]]] == 'si')
        		 {
        			 abilita(idList[n]);
        		 }
        		 else
        		 {
        			disabilita(idList[n]);
        		 }
    		 }
    		 
    		 else						// se sto in una domanda figlia di un'altra domanda che � figlia della superdomanda
    		 {
    			 
    			 if (grandParentIdList[idList[n]] == id && parentIdList[idList[n]] == '-1' ) // se sto ispezionando una domanda figlia di una super domanda
        		 {
        			 
    				 
        			 if (rispostaList[grandParentIdList[idList[n]]] == 'si')
            		 {
            			 abilita(idList[n]);
            		 }
            		 else
            		 {
            			disabilita(idList[n]);
            		 }
        		 }
    			 else
    			 {
    				 
    				 if (id=='410500')
    				 {
      				   alert('idlist '+idList[n]+' id = '+id +' padre '+parentId+ 'granbd parent '+grandparentid + 'risposta padre '+ rispostaList[410500]);
    				 alert(grandParentIdList[idList[n]] + ' '+parentIdList[idList[n]])
    				 }
    				 grandParentIdList[id] = grandparentid ;
    				 parentIdList[id] = parentId;
        		 
        		 /*SE � UNA DOMANDA FIGLIA E SE LA DOMANDA PADRE HA RISPOSTA SI*/
        		 if (( parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]=='-1' && rispostaList[parentIdList[idList[n]]]=='si' ) ||(parentIdList[idList[n]]== '-1' && grandParentIdList[idList[n]]!='-1' && rispostaList[grandParentIdList[idList[n]]]=='si' ) )
        		 {
        			 abilita(idList[n]) ;
        		 }
        		 else
        		 {
        			 /*SE � UNA DOMANDA FIGLIA E SE LA DOMANDA PADRE HA RISPOSTA DIVERSO DA SI*/
        			 if ((parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]=='-1' && rispostaList[parentIdList[idList[n]]]!='si' ) ||(parentIdList[idList[n]]== '-1' && grandParentIdList[idList[n]]!='-1' &&  rispostaList[grandParentIdList[idList[n]]]!='si' ) )
            		 {
            			 disabilita(idList[n]) ;
            		 }
        			 else
        			 {
        				 if ((parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]!='-1' && rispostaList[parentIdList[idList[n]]]=='si' ) )
                		 {
                			abilita(idList[n]) ;
                		 }
        				 
        			 }
        			 
        		 }
        		 }
    			
    		
    			 
    		 }
    		
    	 }
    }
    }
  else
  {
	   //grandParentIdList[id] = grandparentid ;
	   //parentIdList[id] = parentId;
	   
	 
	   if (parentId != '-1' && (grandparentid=='-1' || grandparentid ==''))
	  {
		  if (rispostaList[parentIdList[id]] == 'si')
 		 {
 			 abilita(id);
 		 }
 		 else
 		 {
 			disabilita(id);
 		 }
	  }
	  else
	  {
		  if (parentId == '-1' && (grandparentid !='-1' &&  grandparentid !='' ) )
		  {
			  if (rispostaList[grandParentIdList[id]] == 'si')
     		 {
     			 abilita(id);
     		 }
     		 else
     		 {
     			disabilita(id);
     		 }
		  }
		  else
		  {
			
			  if (rispostaList[parentIdList[id]] == 'si'  )
		 	  {
				  
		 			 abilita(id);
		 	  }
		 		 else
		 		 {
		 			disabilita(id);
		 		 }
			  
		  }
	  }
	  
  }

      }


function aggiornaListElementModify3(id, parentId,grandparentid, punti, risposta,stato){


	
	  rispostaList[id] = risposta; 
	  puntiList[id] = punti;
	  requiredList[id] = true;
	
	  /**
	   * se � una domanda che non ha padre
	   * se � una super domanda
	   */
	 
	 
	  
	  if (parentId == null || parentId == '-1' || grandparentid == null || grandparentid == '-1' || grandparentid ==''){
		
	    for (n=0;n<idList.length;n++) {
	    	
	    if(id!=idList[n])
	    {
	    	 if (parentIdList[idList[n]] == id && grandParentIdList[idList[n]] == '-1' ) // se sto ispezionando una domanda figlia 
	    	 {
	    		 
	    		
	    		 if (rispostaList[parentIdList[idList[n]]] == 'si')
	    		 {
	    			 abilita(idList[n]);
	    		 }
	    		 else
	    		 {
	    			
	    			disabilita(idList[n]);
	    			
	    		 }
	    		 
	    	 }
	    	 else
	    	 {
	    		 if (grandParentIdList[idList[n]] == id && parentIdList[idList[n]] == '-1' ) // se sto ispezionando una domanda figlia di una super domanda
	    		 {
	    			
	    			 
	    			 if (rispostaList[grandParentIdList[idList[n]]] == 'si')
	        		 {
	        			 abilita(idList[n]);
	        		 }
	        		 else
	        		 {
	        			disabilita(idList[n]);
	        			
	        		 }
	    		 }
	    		 
	    		 else						// se sto in una domanda figlia di un'altra domanda che � figlia della superdomanda
	    		 {
	    			 
	    			
	    			 if (grandParentIdList[idList[n]] == id && parentIdList[idList[n]] == '-1' ) // se sto ispezionando una domanda figlia di una super domanda
	        		 {
	        			 
	    				 
	        			 if (rispostaList[grandParentIdList[idList[n]]] == 'si')
	            		 {
	            			 abilita(idList[n]);
	            		 }
	            		 else
	            		 {
	            			disabilita(idList[n]);
	            			 
	            		 }
	        		 }
	    			 else
	    			 {
	    				 
	    				 
	    				// grandParentIdList[id] = grandparentid ;
	    				// parentIdList[id] = parentId;
	        		 
	        		 /*SE � UNA DOMANDA FIGLIA E SE LA DOMANDA PADRE HA RISPOSTA SI*/
	    				 
	    				
	    				 
	        		 if (( parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]=='-1' && (rispostaList[parentIdList[idList[n]]]=='si' ||rispostaList[parentIdList[idList[n]]]=='true' ) || rispostaList[parentIdList[idList[n]]]==true ) ||(parentIdList[idList[n]]== '-1' && grandParentIdList[idList[n]]!='-1' && rispostaList[grandParentIdList[idList[n]]]=='si' ) )
	        		 {
	        			 abilita(idList[n]) ;
	        		 }
	        		 else
	        		 {
	        			 /*SE � UNA DOMANDA FIGLIA E SE LA DOMANDA PADRE HA RISPOSTA DIVERSO DA SI*/
	        			
	        			 if ((parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]=='-1' && rispostaList[parentIdList[idList[n]]]!='si' && rispostaList[parentIdList[idList[n]]]!=null) ||(parentIdList[idList[n]]== '-1' && grandParentIdList[idList[n]]!='-1' &&  rispostaList[grandParentIdList[idList[n]]]!='si' && rispostaList[grandParentIdList[idList[n]]]!= null ) )
	            		 {
	        				 
	        			
	        					 disabilita(idList[n]) ;
	            			
	            			 
	            		 }
	        			 else
	        			 {
	        				 if ((parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]!='-1' && rispostaList[parentIdList[idList[n]]]=='si' ) )
	                		 {
	                			abilita(idList[n]) ;
	                		 }
	        				 
	        			 }
	        			  
	        		 }
	        		
	        		 }
	    		
	    			 
	    		 }
	    		
	    	 }
	    }
	    }
	    }
	  else
	  {
		   //grandParentIdList[id] = grandparentid ;
		   //parentIdList[id] = parentId;
		   
		 
		   if (parentId != '-1' && (grandparentid=='-1' || grandparentid ==''))
		  {
			  if (rispostaList[parentIdList[id]] == 'si')
	 		 {
	 			 abilita(id);
	 		 }
	 		 else
	 		 {
	 			disabilita(id);
	 			
	 		 }
		  }
		  else
		  {
			  if (parentId == '-1' && (grandparentid !='-1' &&  grandparentid !='' ) )
			  {
				  if (rispostaList[grandParentIdList[id]] == 'si')
	     		 {
	     			 abilita(id);
	     		 }
	     		 else
	     		 {
	     			disabilita(id);
	     			
	     		 }
			  }
			  else
			  {
				
				  if (rispostaList[parentIdList[id]] == 'si'  )
			 	  {
					  
			 			 abilita(id);
			 	  }
			 		 else
			 		 {
			 			disabilita(id);
			 			 
			 		 }
				  
			  }
		  }
		  
	  }
	 
	 
	      } 



function aggiornaListElementModify1(id, parentId,grandparentid, punti, risposta,stato){

	
  rispostaList[id] = risposta; 
  puntiList[id] = punti;
  requiredList[id] = true;
 
  /**
   * se � una domanda che non ha padre
   * se � una super domanda
   */
  if (parentId == null || parentId == '-1' || grandparentid == null || grandparentid == '-1'){
	  
    for (n=0;n<idList.length;n++) {
    	 if (parentIdList[idList[n]] == id || grandParentIdList[idList[n]] == id ) {
    		
    		
        if (rispostaList[id] == 'si')
        {
        
          abilita(idList[n]);
        } else 
        {
        	if (rispostaList[id]=='no') 
        	{
        		disabilita(idList[n]);
        	}
        	else
        	{
        	disabilita(idList[n]);
        	}
        } 
      }
    	 else
    	 {
    		 grandParentIdList[id] = grandparentid ;
    		 parentIdList[id] = parentId;
    		 
    		 /*SE � UNA DOMANDA FIGLIA E SE LA DOMANDA PADRE HA RISPOSTA SI*/
    		 if (( parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]=='-1' && rispostaList[parentIdList[idList[n]]]=='si' ) ||(parentIdList[idList[n]]== '-1' && grandParentIdList[idList[n]]!='-1' && rispostaList[grandParentIdList[idList[n]]]=='si' ) )
    		 {
    			 abilita(idList[n]) ;
    		 }
    		 else
    		 {
    			 /*SE � UNA DOMANDA FIGLIA E SE LA DOMANDA PADRE HA RISPOSTA DIVERSO DA SI*/
    			 if ((parentIdList[idList[n]]!= '-1' && grandParentIdList[idList[n]]=='-1' && rispostaList[parentIdList[idList[n]]]!='si' ) ||(parentIdList[idList[n]]== '-1' && grandParentIdList[idList[n]]!='-1' &&  rispostaList[grandParentIdList[idList[n]]]!='si' ) )
        		 {
        			 disabilita(idList[n]) ;
        		 }
    			 
    			 
    			 
    		 }
    		
    		 }
    		
    		   
    	 }
    
  } else {
		 
/*disabilita tutte le domande che hanno si a padre che nonno*/
	grandParentIdList[id] = grandparentid ;
    parentIdList[id] = parentId;
    //alert('id '+id + 'parent '+parentId + ' padre 2 '+parentIdList[id] + '  risposta padre '+rispostaList[grandParentIdList[id]])
	 if (risposta == '' &&  rispostaList[parentIdList[id]]=='si' ){//&&  (rispostaList[parentIdList[id]] !=''  || rispostaList[grandParentIdList[id]] !='')){
		 abilita(id);
	    }
	 else
	 {
		 disabilita(id);
	 }
		 
   
  }
  //aggiornaTotale2(id,punti,risposta);
  //aggiornaCategoria();
}


function aggiornaListElement2(id, parentId,grandparentid, punti, risposta,puntivekki){
	
  rispostaList[id] = risposta; 
  
  requiredList[id] = true;
  if (parentId == null || parentId == '-1' || grandparentid == null || grandparentid == '-1'){
    for (n=0;n<idList.length;n++) {
    	 if (parentIdList[idList[n]] == id || grandParentIdList[idList[n]] == id) {
        if (rispostaList[id] == 'si'){
          abilita(idList[n]);
        } else {
          disabilita(idList[n]);
      	//document.getElementById(n+1).style.background = "#EDEDED";
        }
      }
    }
  } else {
    parentIdList[id] = parentId;
    grandParentIdList[id] = grandparentid ;
    if (risposta == ''){
      disabilita(id);
  	//document.getElementById(n+1).style.background = "#EDEDED";
    }
  }
  aggiornaTotale2(id,punti,risposta);
  aggiornaCategoria();
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

function abilita(id){
	
  requiredList[id] = true;
  //rispostaList[id] = ''; 
  document.getElementById('risposta'+id+'1').disabled = false;
  document.getElementById('risposta'+id+'2').disabled = false;
  
}

function defaultValori(id, parentId, punti, risposta,grandParentId){

	
	 parentIdList[id] = parentId;
	 grandParentIdList[id] = grandParentId ;
	
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


	check = true;
  for (n=0;n<idList.length;n++) {
  
	  if(requiredList[idList[n]] == true){
		  if(document.getElementById("risposta"+idList[n]+"1").checked == false && document.getElementById("risposta"+idList[n]+"2").checked==false)
		  {
	  //alert('id domanda '+idList[n]+' risposta '+rispostaList[idList[n]]+ ' richiesto '+requiredList[idList[n]] )
    	document.getElementById(n+1).style.background = "red";
	  check=false;
		  }
    	
       
    }
   
  }
  if(check==false)
  {
	  alert('Per poter salvare la check list e \' necessario rispondere a tutte le domande.');
	 
  }
  else
  {
	  /*if(document.forms[0].data1.value == '' || document.forms[0].componentiGruppo.value == '')
	  {
		  alert('Assicurarsi di aver inserito la data e i componenti del gruppo');
		  
	  }
	  else
	  {*/
	  //document.forms[0].submit();
	  //}
  }
 
  return check;
}
