<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!--  VAL_VUOTO: Stringhe di spazi utilizzate per riempire le label nel caso in cui siano nulle 
		z: indice utilizzato per scorrere valoriScelti (array di valori inseribili non presenti nel sorgente html
		salvaForm: memorizza i campi di input in valoriScelti. Se nulli setta ______ per layout di stampa
-->

<%
//String VAL_VUOTO_SHORT = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"; 
//String VAL_VUOTO_LONG = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"; 
//String VAL_VUOTO_LONG_2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp";
%>
<script> function salva(formDoc){

var inputs, index;
var valori='';
inputs = document.getElementsByTagName('input');
for (index = 0; index < inputs.length; ++index) {

	if (inputs[index].type=='text' && inputs[index].className!='layout'){
	var val = inputs[index].value;
	var id = inputs[index].id;
	//if (val=="")
		//val= "&nbsp";
	//else
		//val=val.toUpperCase();		
     valori = valori.concat(val,';;;');
	}
}

formDoc.listavalori.value = valori;
formDoc.submit();

}

</script>