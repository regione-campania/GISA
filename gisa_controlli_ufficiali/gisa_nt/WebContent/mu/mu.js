/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function gestisciNumCapi (id, operazione){
	var campo = document.getElementById(id);
	var numero =campo.value;

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
		var datiBovini = document.getElementById("datiBovini");
		var html = generaDatiBovini(num);
		//datiBovini.innerHTML = "<tr><td>"+html+"</td></tr>";
		
		var mydiv = document.getElementById("datiBovini");
        var newcontent = document.createElement('node');
        newcontent.innerHTML =  html;
        while (newcontent.firstChild) {
            mydiv.appendChild(newcontent.firstChild);
        }
		
		
	}
	
	if (id=='numBovini' && operazione == '-'){
		aggiornaIdBovini();
	}
	
	
}
function aggiornaIdBovini(){
	
	
}
function rimuoviCapo(id){
	//Rimuovo i div di piani che non sono più selezionati
	var tabella = "tabella_"+id;
	 var elemento = document.getElementById(tabella);
	 var mydiv = document.getElementById("datiBovini");
		mydiv.removeChild(elemento);
		gestisciNumCapi('numBovini', '-');
	  }

function generaDatiBovini(id){
	var matricola = "matricola_"+id;
	var specie = "specie_"+id;
	var categoriabovina = "categoriabovina_"+id;
	
	var campocategoriabovina = "CategorieBovine.getHtmlSelect(categoriabovina, -1 );"
	 
	
	var categoriabufalina = "categoriabufalina_"+id;
	var razza = "razza_"+id;
	var sesso = "sesso_"+id;
	var datanascita = "datanascita_"+id;
	
	var campodatanascita="<input readonly type=\"text\" name=\""+datanascita+"\" id=\""+datanascita+"\" size=\"10\" value=\"\" />&nbsp;"+  
    "<a href=\"#\" onClick=\"cal19.select(document.forms[0]."+datanascita+",'anchor19','dd/MM/yyyy'); return false;\" NAME=\"anchor19\" ID=\"anchor19\">"+
		"<img src=\"images/icons/stock_form-date-field-16.gif\" border=\"0\" align=\"absmiddle\"></a>"+
		"<a href=\"#\" style=\"cursor: pointer;\" onclick=\"svuotaData(document.forms[0]."+datanascita+");\"><img src=\"images/delete.gif\" align=\"absmiddle\"/></a>";
	var rischio ="rischio_"+id;
	var tabella = "tabella_"+id;
	var rimuovi ="<input type=\"button\" value= \"X\" onClick=\"rimuoviCapo('"+id+"')\"/>";
var html = "<table style=\"border-collapse: collapse;\" width=\"100%\" border=\"1px\" id=\""+tabella+"\">"+
"<tr><th bgcolor=\"red\">Capo bovino </th>" +
	" <td> Matricola </td> <td> <input type=\"text\" id=\""+matricola+"\" name=\""+matricola+"\"/> </td> "+
	" <td> Specie </td> <td> <select id=\""+specie+"\" name=\""+specie+"\"> <option value=\"Bufalino\">Bufalino</option> <option value=\"Bardotto\">Bardotto</option> </td> "+
	" <td> Categoria bovina </td> <td>"+campocategoriabovina +"</td> "+
	" <td> Categoria bufalina </td> <td> <select id=\""+categoriabufalina+"\" name=\""+categoriabufalina+"\"> <option value=\"Bufalino\">Bufalino</option> <option value=\"Bardotto\">Bardotto</option> </td> "+
	" <td> Razza </td> <td> <select id=\""+razza+"\" name=\""+razza+"\"> <option value=\"Bufalino\">Bufalino</option> <option value=\"Bardotto\">Bardotto</option> </td> "+
	" <td> Sesso </td> <td> <select id=\""+sesso+"\" name=\""+sesso+"\"> <option value=\"M\">M</option> <option value=\"F\">F</option> </td> "+
	" <td> Data di nascita </td> <td>"+campodatanascita+"</td>"+
	" <td> Categoria di rischio </td> <td> <select id=\""+rischio+"\" name=\""+rischio+"\"> <option value=\"0\">0 Default</option> <option value=\"1\">1 [bovini sani sopra i 72 mesi di età]</option> </td> "+
	"<td> "+rimuovi+" </td></tr>"+
	"<table>";
	return html; 
}
function svuotaData(input){
	input.value = '';
}