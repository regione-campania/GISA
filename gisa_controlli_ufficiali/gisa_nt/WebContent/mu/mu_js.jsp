<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<style>@media all{
.disabled
{
     background-color:grey !important;
}
</style>


<script>



function gestisciNumCapi (id, operazione){
	
	var campoIndice = document.getElementById("indiceCapiBovini");
	var numeroIndice =campoIndice.value;
	var campo = document.getElementById(id);
	var numero =campo.value;
	
	if (id=='numBovini' && operazione == '+'){
		if (!checkDatiBovini())
			return false;
	}
	
	if (operazione=='+'){
		var num = parseInt(numero)+1;
		campo.value = num;
	}
	else if (operazione=='-'){
		var num = parseInt(numero)-1;
		if (num>=0 )
		campo.value = num;
	} 
	
	//Aggiunta riga per campi dei bovini
	if (id=='numBovini' && operazione == '+'){
		var nuovoIndice = parseInt(numeroIndice)+1;
		campoIndice.value = nuovoIndice;
		numeroIndice = nuovoIndice;
		
		generaDatiBovini2(numeroIndice);
		aggiornaId();
	}
	
	if (id=='numBovini' && operazione == '-'){
		aggiornaIdBovini();
	}
	
	
}
function aggiornaIdBovini(){
	
	
}
function rimuoviRigaCapo(bottone){
	//Rimuovo i div di piani che non sono più selezionati
	var table = document.getElementById("tabellaDatiBovini");
	 var i = bottone.parentNode.parentNode.rowIndex;
	 table.deleteRow(i);
	gestisciNumCapi('numBovini', '-');
	  }

var specie;
var categoriabovina;
var categoriabufalina;
var razza;
var rischio;

// Aggiorna gli id degli elementi creati dinamicamente
function aggiornaId(){
	document.getElementById("specieTemp").name = specie;
	document.getElementById("specieTemp").id = specie;
	document.getElementById("categoriaBovinaTemp").name = categoriabovina;
	document.getElementById("categoriaBovinaTemp").id = categoriabovina;
	document.getElementById("categoriaBufalinaTemp").name = categoriabufalina;
	document.getElementById("categoriaBufalinaTemp").id = categoriabufalina;
	document.getElementById("razzaTemp").name = razza;
	document.getElementById("razzaTemp").id = razza;
	document.getElementById("rischioTemp").name = rischio;
	document.getElementById("rischioTemp").id = rischio;
	
}

//Cancella il campo data
function svuotaData(input){
	input.value = '';
}

//Mostra o nasconde le select delle specie/categorie bovine/bufaline
function gestisciSpecieBovine(campo){
	var idcampo = campo.id;
	var val = campo.value;
	var id = idcampo.split('_')[1];
	var categoriabovina = document.getElementById('categoriabovina_'+id);
	var categoriabufalina = document.getElementById('categoriabufalina_'+id);
	var razza = document.getElementById('razza_'+id);
	
	var mostracategoriabovina = false;
	var mostracategoriabufalina = false;
	var mostrarazza = false;
	
	if (val == 1){
		//Bovino: mostra categoria e razza
		mostracategoriabovina = true;
		mostrarazza=true;
	}
	else if (val ==5){
		//Bufalino: mostra categoria
		mostracategoriabufalina = true;
	}
	
	if (mostracategoriabovina)
		categoriabovina.style.display='block';
	else{
		categoriabovina.style.display='none';
		categoriabovina.value = -1;
	}
	
	if (mostracategoriabufalina)
		categoriabufalina.style.display='block';
	else{
		categoriabufalina.style.display='none';
		categoriabufalina.value = -1;
	}
	
	if (mostrarazza)
		razza.style.display='block';
	else{
		razza.style.display='none';
		razza.value = -1;
		}
} 

//Genera il numero partita automatico
function generaPartita()
{
	var asl =           '<%=(String)request.getAttribute("asl")%>';
	var codiceUnivoco = '<%=(String)request.getAttribute("codiceUnivoco")%>';
	var anno   =         '<%=(String)request.getAttribute("anno")%>';
	document.getElementById('numero_partita').value= asl + codiceUnivoco + anno;		
}

//Da sviluppare: recupero dei dati dalla BDN
function gestisciCapoEsistente(capo){

	var form = document.main;
	if( capo.esistente )
	{
			alert( "Matricola " + capo.matricola + " già esistente" );
	}
	else
	{
		Geocodifica.getCapo( capo.matricola, getDatiCapo );
	}
	
}
function getDatiCapo( data )
{
}

//Setta il valore dei campi veterinari
function set_vet( select, campo )
{
	var value_to_set = "";
	if( select.value != "-1" )
	{
		value_to_set = select.options[ select.selectedIndex ].text;
	}
	document.getElementById( campo ).value = value_to_set;
};



//Controlla che i dati dei bovini siano stati inseriti prima di poterne aggiungere un altro
function checkDatiBovini(){
	var max = document.getElementById("indiceCapiBovini").value;
	
	var esito = true;
	for (var i = 0; i<=max; i++){
		if (document.getElementById("matricola_"+i)!=null){
			if (document.getElementById("matricola_"+i).value=='')
				esito = false;
			if (document.getElementById("datanascita_"+i).value=='')
				esito = false;	
			
		}
		if (esito==false)
			break;
	}
	if (esito==false)
		alert ("Compilare tutti i dati dei capi bovini prima di procedere ad aggiungerne uno nuovo");
	return esito;
		
}

// Genera una riga con i dati del campo in aggiunta seduta
function generaDatiCapi(numero, idpartita, idcapo, specie, idspecie, matricola){
	
	if (document.getElementById("capo_"+idcapo)!=null)
		return false;
	
	var table = document.getElementById("table3");

	// Create an empty <tr> element and add it to the 1st position of the table:
	var row = table.insertRow(table.rows.length);
	row.id = idcapo;

	// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	//var cell4 = row.insertCell(4);

	// Add some text to the new cells:
	cell0.innerHTML = numero;
	cell1.innerHTML = specie;
	cell2.innerHTML = (matricola!='') ? matricola : "&nbsp;";
	var checkbox= "<input type=\"checkbox\" disabled checked=\"checked\" id=\"capo_"+idcapo+"\" name=\"capo_"+idpartita+"_"+idspecie+"\" onClick=\"gestisciAggiuntaSingola(this, '"+idspecie+"', '"+idpartita+"')\"/>";
	var bottonecancella = "<input type=\"button\" onClick=\"rimuoviDatiCapi('"+idcapo+"', '"+idspecie+"')\" value=\"X\"/>";
	cell3.innerHTML = checkbox+bottonecancella; 
	//var addtotale = "<input type=\"checkbox\" id=\"specie_"+idspecie+"_"+idpartita+"\" name=\"specie_"+idspecie+"\" onClick=\"gestisciAggiuntaTotale(this, '"+idspecie+"', '"+idpartita+"')\"/>";
	
	//if (document.getElementById("specie_"+idspecie+"_"+idpartita)!=null)
	//	addtotale = "&nbsp;";
	// cell4.innerHTML = addtotale;
	
	//Incremento numero totale
	var num = document.getElementById("num_"+idspecie).value;
	var nuovoNum = parseInt(num)+1;
	document.getElementById("num_"+idspecie).value = nuovoNum;
}

//Rimuove una riga dalla tabella dei dati dei capi in aggiunta seduta
function rimuoviDatiCapi(idcapo, idspecie){
	var table = document.getElementById("table3");
	var r = document.getElementById("capo_"+idcapo);
	if (r!=null){
	 var i = r.parentNode.parentNode.rowIndex;
	 table.deleteRow(i);
	//Decremento numero totale
		var num = document.getElementById("num_"+idspecie).value;
		var nuovoNum = parseInt(num)-1;
		if (nuovoNum<0)
			nuovoNum=0;
		document.getElementById("num_"+idspecie).value = nuovoNum;
	 
	}
}

// Genera una riga per i dati del capo in aggiunta partita
function generaDatiBovini2(id){
	
	var table = document.getElementById("tabellaDatiBovini");

	// Create an empty <tr> element and add it to the 1st position of the table:
	var row = table.insertRow(table.rows.length);

	// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	var cell5 = row.insertCell(5);
	var cell6 = row.insertCell(6);
	var cell7 = row.insertCell(7);
	var cell8 = row.insertCell(8);
	var cell9 = row.insertCell(9);
	var cell10 = row.insertCell(10);
	var cell11 = row.insertCell(11);
	var cell12 = row.insertCell(12);
	var cell13 = row.insertCell(13);
	

	// Add some text to the new cells:
	cell0.innerHTML = "Capo Bovino";
	cell0.className="formLabel";
	
	cell1.innerHTML = "Matricola";
	cell1.className="formLabel";
	
	var matricola = "matricola_"+id;
	var campomatricola = "<input type=\"text\" id=\""+matricola+"\" name=\""+matricola+"\" />";
	var importabdn = "<input type=\"button\" value=\"IMPORTA DA BDN\" onClick=\"importaCapoBdn('"+matricola+"')\"/>";
	cell2.innerHTML = campomatricola + importabdn;
	
	cell3.innerHTML = "Categoria";
	cell3.className="formLabel";
	
	specie = "specie_"+id;
	<%
	specieBovine.setJsEvent("onChange='gestisciSpecieBovine(this)'");%>
	var campospecie = "<%=specieBovine.getHtmlSelect("specieTemp", -1 )%>";
	categoriabovina = "categoriabovina_"+id;
	<%categorieBovine.setSelectStyle("display:none");%>
	var campocategoriabovina = "<%=categorieBovine.getHtmlSelect("categoriaBovinaTemp", -1 )%>";
	categoriabufalina = "categoriabufalina_"+id;
	<%categorieBufaline.setSelectStyle("display:none");%>
	var campocategoriabufalina = "<%=categorieBufaline.getHtmlSelect("categoriaBufalinaTemp", -1 )%>";
	razza = "razza_"+id;
	<%razzeBovine.setSelectStyle("display:none");%>
	var camporazza = "<%=razzeBovine.getHtmlSelect("razzaTemp", -1 )%>";
	cell4.innerHTML= campospecie + campocategoriabovina + campocategoriabufalina + camporazza;
	
	cell5.innerHTML ="Sesso";
	cell5.className="formLabel";
	
	var sesso = "sesso_"+id;
	cell6.innerHTML = "<select id=\""+sesso+"\" name=\""+sesso+"\"> <option value=\"M\">M</option> <option value=\"F\">F</option>";
	
	cell7.innerHTML ="Data di nascita";
	cell7.className="formLabel";
	
	var datanascita = "datanascita_"+id;
	var campodatanascita="<input readonly type=\"text\" name=\""+datanascita+"\" id=\""+datanascita+"\" size=\"10\" value=\"\" />&nbsp;"+  
    "<a href=\"#\" onClick=\"cal19.select(document.forms[0]."+datanascita+",'anchor19','dd/MM/yyyy'); return false;\" NAME=\"anchor19\" ID=\"anchor19\">"+
		"<img src=\"images/icons/stock_form-date-field-16.gif\" border=\"0\" align=\"absmiddle\"></a>"+
		"<a href=\"#\" style=\"cursor: pointer;\" onclick=\"svuotaData(document.forms[0]."+datanascita+");\"><img src=\"images/delete.gif\" align=\"absmiddle\"/></a>";
		
	cell8.innerHTML = campodatanascita;
	
	cell9.innerHTML = "Condizioni particolari del capo";
	cell9.className="formLabel";
	
	rischio ="rischio_"+id;
	var camporischio = "<%=catRischio.getHtmlSelect("rischioTemp", 0 )%>";
	cell10.innerHTML = camporischio;
	
	cell11.innerHTML = "Deceduto";
	cell11.className="formLabel";
	
	var idCheck = "flagDeceduto_"+id;
	var checkboxdeceduto = "<input type=\"checkbox\" name=\""+idCheck+"\"  id=\""+idCheck+"\"/> ";
	cell12.innerHTML = checkboxdeceduto;
	
	var rimuovi ="<input style=\"background:red\" type=\"button\" value= \"X\" onClick=\"rimuoviRigaCapo(this)\"/>";
	//var aggiungi = "<input type=\"button\" value=\"+\" onClick=\"gestisciNumCapi('numBovini', '+')\"/> ";
	cell13.innerHTML = rimuovi;
}

//Da sviluppare: importa i dati dalla BDN
function importaCapoBdn(campo){
	var matricola = document.getElementById(campo).value;
	alert(matricola + " importare da BDN");
	}
	
//Apre una popup con la lista dei capi in aggiunta seduta	
function openPopupCapiPartita(idPartita) {
	  var res;
      var result;
    	  window.open('MacellazioneUnica.do?command=ListCapiPartita&idPartita='+idPartita,'popupSelect',
            'height=400px,width=800px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
	
//Gestiscono l'aggiunta e la cancellazione delle righe dei capi in aggiunta seduta	
	function cancellaDatiCapi(capi){
		for (var i =0; i<capi.length; i++){
			rimuoviDatiCapi(capi[i][0], capi[i][1]);
		}
	}
	function aggiungiDatiCapi(capi){
		for (var i =0; i<capi.length; i++){
			generaDatiCapi(capi[i][0], capi[i][1], capi[i][2], capi[i][3], capi[i][4], capi[i][5]);
		}
		}
	
</script>

<input type="hidden" id="indiceCapiBovini" name ="indiceCapiBovini" value="0"/>