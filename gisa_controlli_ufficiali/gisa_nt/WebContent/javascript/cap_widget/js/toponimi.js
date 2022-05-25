/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var selezioneToponimo = false;
var toponimoSelezionato = '';
var optionsToponimo = {
	url: "resources/toponimi.json",

	getValue: "name",
	list: {
	  
	  onChooseEvent: function() {
	      var value =  $("#top").getSelectedItemData().value;
	      var name = $("#top").getSelectedItemData().name;
	      $("#topId").val(value).trigger("change");
	      $("#top").val(name).trigger("change");
	      toponimoSelezionato = name;
	      selezioneToponimo = true;
		},
	  /*
	  onSelectItemEvent: function() {
	      var value =  $("#top").getSelectedItemData().value;
	      var name = $("#top").getSelectedItemData().name;
	      $("#topId").val(value).trigger("change");
	      $("#top").val(name).trigger("change");
	      toponimoSelezionato = name;
	      selezioneToponimo = true;
		},
		*/
		match: {
			enabled: true
		}
	}
};

$("#top").easyAutocomplete(optionsToponimo);
$("#top").on('focus', function() {
	  selezioneToponimo = false;
      $(this).val('');
      $("#topId").val(null);
      $("#via").val(null);
      $("#civ").val(null);
      if($("#comuneId").val() == 5279){
    	  $("#cap").val(null);
      }      
  }).on('blur', function() {
    if (!selezioneToponimo || ($("#top").val() != toponimoSelezionato)) {
      $(this).val(null);
      toponimoSelezionato = '';
    }
  });