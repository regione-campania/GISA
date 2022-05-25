/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
let inputParams = new URLSearchParams(window.location.search);
var flag_regione = inputParams.get('flag_regione');
var flag_id_asl = inputParams.get('flag_id_asl');

var selezioneProvincia = false;
var provinciaSelezionata = '';
var fileResourceProvince;

if(flag_regione == 'campania'){
	
	if(flag_id_asl == '-1'){
		fileResourceProvince = "resources/provincecampania.json";
	}else {
		fileResourceProvince = "GetProvinceByAsl.do?command=Search&idAsl=" + flag_id_asl;
	}
	
} else {
	fileResourceProvince = "resources/province.json";
}
var optionsProvincie = {
			url : function() {
						return fileResourceProvince;
						},

			getValue: "label",
			
			list: {
				onChooseEvent: function() {    
				      var value =  $("#provincia").getSelectedItemData().value;
				      var name = $("#provincia").getSelectedItemData().label;
				      $("#provinciaId").val(value).trigger("change");
				      $("#provincia").val(name).trigger("change");
				      provinciaSelezionata = name;
				      selezioneProvincia = true;
					},
				/*
			  	onSelectItemEvent: function() {    
			      var value =  $("#provincia").getSelectedItemData().value;
			      var name = $("#provincia").getSelectedItemData().label;
			      $("#provinciaId").val(value).trigger("change");
			      $("#provincia").val(name).trigger("change");
			      provinciaSelezionata = name;
			      selezioneProvincia = true;
				}*/
				
				match: {
					enabled: true
				}
			}
};
$("#provincia").easyAutocomplete(optionsProvincie);

$('#provincia').on('focus', function() {
      selezioneProvincia = false;
	  $(this).val('');
      $("#provinciaId").val(null);
      $("#comuni").val(null);
      $("#comuneId").val(null);
      $("#top").val(null);
      $("#topId").val(null);
      $("#via").val(null);
      $("#civ").val(null);
      $("#cap").val(null);
      $("#napoli").css({"visibility":"hidden"});
  }).on('blur', function() {
    if (!selezioneProvincia || ($("#provincia").val() != provinciaSelezionata)) {
      $(this).val(null);
      provinciaSelezionata = '';
    }
  });
  
