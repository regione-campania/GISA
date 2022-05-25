/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var idTab;
var livelloIn;
var tipoRegistrazione ;
var numeroLinee = 0 ;
function mostraMasterList(tipologiaDaVisualizzare , idRiferimento, nomeIdRiferimento , idFlussoOriginale)
  {
	
	var dataInput ="tipologia="+tipologiaDaVisualizzare+"&"+nomeIdRiferimento+"="+idRiferimento+"&flussoOrig="+idFlussoOriginale ;
	
	$.ajax({
		  // definisco il tipo della chiamata
		  type: "POST",
		  // specifico la URL della risorsa da contattare
		  url: "GestioneMasterList.do?command=CostruisciMasterList",
		  // passo dei dati alla risorsa remota
		  data: data,
		  // definisco il formato della risposta
		  dataType: "json",
		  // imposto un'azione per il caso di successo
		  success: function(risposta){
			  mostraMasterListCallback(risposta)
		  },
		  // ed una per il caso di fallimento
		  error: function(){
		    alert("Chiamata fallita!!!");
		  }
		});
	
  }
  

  function mostraMasterListCallback(lista)
  {
	 
	 
  	alert(lista[i].aggregazione);
  
  }
  
  