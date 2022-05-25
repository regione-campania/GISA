<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script>
var initial_form="";
var final_form = "";

//COSTRIUSCI INFO INIZIALI (JSON)
 $( document ).ready(function(){
	    initial_form = getlFormInfo(document.forms[0]);
 });
 
 
function getlFormInfo(form){
	
//	 var array = jQuery(form).serializeArray();
	 var array = $('input[class!=exclude_from_json]', form).serializeArray();
	 var json = {};
	 jQuery.each(array, function() {
	      	json[this.name] = this.value || '';
	 });
	 
	 for (var prop in json){
	   	var el = document.getElementsByName(prop);
		if (el[0].type=='select-one'){
			json[prop]=[el[0].value,el[0].options[el[0].selectedIndex].text];
		}
	 }
	 return json;
}

function writeBeanModificationCallBack(returnValue){}
 
function checkFormChange(){
	final_form =  getlFormInfo(document.forms[0]);
	if  (JSON.stringify(initial_form) === JSON.stringify(final_form)) {
		console.log("DATI FORM INVARIATI");
	} else {
		var delta = {};
		var value_i;
		var value_f;
		for (var prop in initial_form){  
			if (final_form.hasOwnProperty(prop)){ //ESISTE la chiave
				value_i = initial_form[prop];
				value_f = final_form[prop];
				if (!Array.isArray(value_i) && !Array.isArray(value_f)){
					if (value_i!=value_f){   //VALORI DIVERSI
					    delta[prop] = [value_i,value_f];
					}
				} else {
					value_i = initial_form[prop][0];
					value_f = final_form[prop][0];
					if (value_i!=value_f) {
						delta[prop] = [initial_form[prop][0],initial_form[prop][1],final_form[prop][0],final_form[prop][1]];
					}
				}
			} else { //NON ESISTE la chiave
				value_i = initial_form[prop];
				value_f = final_form[prop];
				if (!Array.isArray(value_i) && !Array.isArray(value_f)){
			    	delta[prop] = [value_i,'rimosso'];
				} else {
					delta[prop] = [initial_form[prop][0],initial_form[prop][1],"rimosso"];
				}
			}
		}
		
		for (var prop in final_form){  
			if (!initial_form.hasOwnProperty(prop) && !delta.hasOwnProperty(prop)){
				value_f = final_form[prop];
				if (!Array.isArray(value_f)){
					delta[prop] = ['aggiunto',value_f];
				} else {
					delta[prop] = ['aggiunto',final_form[prop][0],final_form[prop][1]];
				}
			}
		}		
		PopolaCombo.writeBeanModification(JSON.stringify(initial_form),JSON.stringify(delta),<%=User.getUserRecord().getId()%>,'<%=User.getUserRecord().getUsername()%>',window.location.href,{callback:writeBeanModificationCallBack,async:false});
	}
}
</script>