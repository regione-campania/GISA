/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function checkLinea(indice,fieldCheck)
{
if(fieldCheck.checked)
	{
	document.getElementById("img"+indice).style.display="";
	}
else{
	document.getElementById("img"+indice).style.display="none";
}
	controllaSelezioneLineeProduttive();
	}
	
	function controllaSelezioneLineeProduttive()
	{
		var checkedVal = $('input[name=idLineaProduttiva]:checked');
		
		if(checkedVal.length>0)
			document.getElementById("validatelp").value="true";
			else
				document.getElementById("validatelp").value="false";
	}

function controlloDate(){
	
	var oggi = dataDiOggi();
	
    if(document.getElementById(this.id.replace("Fine","Inizio")).value==""){
            alert("ATTENZIONE! Inserire prima la data inizio linea.");        
            this.value="";
            return false;
    }else{
            if(!confrontoDate(document.getElementById(this.id.replace("Fine","Inizio")).value,document.getElementById(this.id).value)){
                    alert("ATTENZIONE! La data fine deve essere maggiore della data inizio.");
                    this.value="";
                    return false;
            }
    }
    
 
    if (this.id=='dataFine'){
    	
    }
    else if (this.id=='dataInizio'){
    	var giorniPrima = giorni_differenza(this.value, oggi);
    	var giorniDopo =  giorni_differenza(oggi, this.value); 
    	if (giorniPrima>30){
    		  alert("ATTENZIONE! La data inizio non può essere antecedente a 30 giorni dalla data odierna. (Differenza: "+giorniPrima+" giorni)");
              this.value="";
    	}
    	else if (giorniDopo>120){
    		  alert("ATTENZIONE! La data fine non può essere successiva a 120 giorni dalla data odierna. (Differenza: "+giorniDopo+" giorni)");
              this.value="";
    	}
    }
      
    if (this.id=='dataFine'){
    	if (document.getElementById("dataFineLinee")!=null)
    		document.getElementById("dataFineLinee").value = this.value;
    }
    else if (this.id=='dataInizio'){
    	if (document.getElementById("dataInizioLinee")!=null)
    		document.getElementById("dataInizioLinee").value = this.value;
    }
   
}


function controlloDataCessazione(){
	
	var oggi = dataDiOggi();
	
    
    if (this.id=='dataCessazione'){
    	var giorni = giorni_differenza(oggi, this.value); 
    	if (giorni>0){
    		  alert("ATTENZIONE! La data di cessazione non può essere superiore alla data odierna. (Differenza: "+giorni+" giorni)");
              this.value="";
    	}
    }
    
   
}

function confrontoDate(data_iniziale ,data_finale){
    var arr1 = data_iniziale.split("/");
    var arr2 = data_finale.split("/");
    var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
    var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
    var r1 = d1.getTime();
    var r2 = d2.getTime();
    if (r1<=r2) 
            return true;
    else 
            return false;
    }

//$(function() {
//        $('#dataInizioLinea').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'}); 
//        $('#dataFineLinea').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',  onClose: controlloDate         });
//});


function gestisciCessazione(campo){
	if (campo.checked){
		document.getElementById("divCessazione").style.display="block";
		document.getElementById("dataCessazione").className = "required";
		document.getElementById("tableQualeLinea").style.display="none";
		var i = 1;
		while (document.getElementById("attsecondarie"+i)!=null){
			document.getElementById("attsecondarie"+i).style.display="none";
			if (document.getElementById("attsecondarie"+i+"_ext")!=null)
				document.getElementById("attsecondarie"+i+"_ext").style.display="none";
			i++;
		}
		
//		$('#dataCessazione').datepick('destroy'); 
//		$(function() {	$('#dataCessazione').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0,  showOnFocus: false, showTrigger: '#calImg' }); });
	}
	else{
		document.getElementById("divCessazione").style.display="none";
		document.getElementById("dataCessazione").className = "";
		document.getElementById("tableQualeLinea").style.display="block";
		var i = 1;
		while (document.getElementById("attsecondarie"+i)!=null){
			document.getElementById("attsecondarie"+i).style.display="block";
			if (document.getElementById("attsecondarie"+i+"_ext")!=null)
				document.getElementById("attsecondarie"+i+"_ext").style.display="block";
			i++;
		}
	}
}


function gestisciSospensione(campo){
	if (campo.checked){
		document.getElementById("divSospensione").style.display="block";
		document.getElementById("dataInizioSospensione").className = "required";
		document.getElementById("tableQualeLinea").style.display="none";
		var i = 1;
		while (document.getElementById("attsecondarie"+i)!=null){
			document.getElementById("attsecondarie"+i).style.display="none";
			if (document.getElementById("attsecondarie"+i+"_ext")!=null)
				document.getElementById("attsecondarie"+i+"_ext").style.display="none";
			i++;
		}
		
//		$('#dataCessazione').datepick('destroy'); 
//		$(function() {	$('#dataCessazione').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0,  showOnFocus: false, showTrigger: '#calImg' }); });
	}
	else{
		document.getElementById("divSospensione").style.display="none";
		document.getElementById("dataInizioSospensione").className = "";
		document.getElementById("tableQualeLinea").style.display="block";
		var i = 1;
		while (document.getElementById("attsecondarie"+i)!=null){
			document.getElementById("attsecondarie"+i).style.display="block";
			if (document.getElementById("attsecondarie"+i+"_ext")!=null)
				document.getElementById("attsecondarie"+i+"_ext").style.display="block";
			i++;
		}
	}
}


function giorni_differenza(data1,data2){
	
	anno1 = parseInt(data1.substr(6),10);
	mese1 = parseInt(data1.substr(3, 2),10);
	giorno1 = parseInt(data1.substr(0, 2),10);
	anno2 = parseInt(data2.substr(6),10);
	mese2 = parseInt(data2.substr(3, 2),10);
	giorno2 = parseInt(data2.substr(0, 2),10);

	var dataok1=new Date(anno1, mese1-1, giorno1);
	var dataok2=new Date(anno2, mese2-1, giorno2);

	differenza = dataok2-dataok1;    
	giorni_diff = new String(Math.ceil(differenza/86400000));

	return giorni_diff;
}

function dataDiOggi(){
	var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!

    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd;
    } 
    if(mm<10){
        mm='0'+mm;
    } 
    var oggi = dd+'/'+mm+'/'+yyyy;
    return oggi;
}