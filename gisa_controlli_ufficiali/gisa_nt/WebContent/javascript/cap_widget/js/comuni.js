/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var selezioneComune = false;
var comuneSelezionato = '';
var capDaInserire = null;

var fileresourcecomuni = "";

var optionsComuni = {
		
	  url: function() {
		    if(flag_id_asl == '204' || flag_id_asl == '205' || flag_id_asl == '206'){
				fileresourcecomuni = "GetComuneByAsl.do?command=Search&idAsl=" + flag_id_asl;
			}else{
				fileresourcecomuni = "GetComuneByProvincia.do?command=Search&provincia.code=" + $("#provinciaId").val();
			}
		  	return fileresourcecomuni;
	  },

	  getValue: "nome",
	  list: {
		  maxNumberOfElements: 20,
		  
		  	onChooseEvent: function() {
			      var value =  $("#comuni").getSelectedItemData().id;
			      var name = $("#comuni").getSelectedItemData().nome;
				  $("#comuneId").val(value).trigger("change");
				  $("#comuni").val(name).trigger("change");
				  comuneSelezionato = name;
				  selezioneComune = true;
				  if(value != 5279){
					  capDaInserire = $("#comuni").getSelectedItemData().cap;
					  $("#napoli").css({"visibility":"hidden"});
					  $("#top").prop('disabled', false);    	  
					  $("#via").removeAttr('readonly');
					  $("#cap").val(capDaInserire);
				  } else {
					  $("#napoli").css({"visibility":"visible"});
					  $("#top").prop('disabled', true);
					  $("#via").attr('readonly','readonly');
					  $("#cap").css({"visibility":"hidden"});
				  	  $("#top").css({"visibility":"hidden"});
				  	  $("#via").css({"visibility":"hidden"});
				  }				  
			},
			
			/*
		  	onSelectItemEvent: function() {
			      var value =  $("#comuni").getSelectedItemData().id;
			      var name = $("#comuni").getSelectedItemData().nome;
				  $("#comuneId").val(value).trigger("change");
				  $("#comuni").val(name).trigger("change");
				  comuneSelezionato = name;
				  selezioneComune = true;
				  if(value != 5279){
					  capDaInserire = $("#comuni").getSelectedItemData().cap;
					  $("#napoli").css({"visibility":"hidden"});
					  $("#top").prop('disabled', false);    	  
					  $("#via").removeAttr('readonly');
				  } else {
					  $("#napoli").css({"visibility":"visible"});
					  $("#top").prop('disabled', true);
					  $("#via").attr('readonly','readonly');
				  }				  
			},*/
			match: {
				enabled: true,
				method:  function(element, phrase) {
		              if(element.indexOf(phrase) === 0) {
		                return true;
		              } else {
		                return false;
		              }
		            }
			}
	  }
};

$("#comuni").easyAutocomplete(optionsComuni);
$("#comuni").on('focus', function() {
  	  selezioneComune = false;
      $(this).val('');
      $("#comuneId").val(null);
      $("#top").val(null);
      $("#topId").val(null);
      $("#via").val(null);
      $("#civ").val(null);
      $("#cap").val(null);
      $("#napoli").css({"visibility":"hidden"});
      $("#via").css({"visibility":"visible"});
      $("#top").css({"visibility":"visible"});
      $("#civ").css({"visibility":"visible"});
      $("#cap").css({"visibility":"visible"});
  }).on('blur', function() {
	    if (!selezioneComune || ($("#comuni").val() != comuneSelezionato)) {
	      $(this).val(null);
	      comuneSelezionato = '';
	    }else  if($("#comuneId").val() != '5279'){
	    	$("#cap").val(capDaInserire);
	    }
  });