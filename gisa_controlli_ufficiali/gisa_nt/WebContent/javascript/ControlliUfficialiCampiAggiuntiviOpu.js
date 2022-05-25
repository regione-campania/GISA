/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function recuperaLineaSottoposta(idTicket){
	PopolaCombo.recuperaLineaSottopostaCu(idTicket,{callback:recuperaLineaSottopostaCallBack,async:false});
}

function recuperaLineaSottopostaCallBack(val){
	controllaCampiAggiuntiviLinea(val);
}


function controllaCampiAggiuntiviLinea(idLinea, idControllo) { 
	PopolaCombo.recuperaCampiAggiuntiviLineaPrivati(idLinea, idControllo, {callback:controllaCampiAggiuntiviLineaPrivatiCallBack,async:false});
}
function controllaCampiAggiuntiviLineaPrivatiCallBack(val) {
	
	if (document.getElementById("LuogoControllo")!=null){
		if (val==null){
			document.getElementById("LuogoControllo").innerHTML="";
		}
		else {
			var html = "";
			var ret = val.split("|");
			var idIndirizzo = ret[0];
			var idToponimo = ret[1]; 
			var descToponimo = ret[2];
			var via = ret[3];
			var civico = ret[4];
			var cap = ret[5];
			var idComune = ret[6]; 
			var descComune = ret[7];
			var idProvincia = ret[8]; 
			var descProvincia = ret[9];
			
			if (idIndirizzo!= null) {
				
				
				//html+="<td class=\"formLabel\">Luogo del controllo</td><td><input type=\"hidden\" id=\"luogoDelControlloIdRel\" name = \"luogoDelControlloIdRel\" value = \"" + idRel + "\"/> <input type=\"text\" id=\"luogoDelControllo\" name=\"luogoDelControllo\" value =\"" + luogo + "\"/>";

				html+="<td class=\"formLabel\">Luogo del controllo</td><td>";
				html +="<input type=\"text\" id=\"toponimo_luogocontrollo\" name=\"toponimo_luogocontrollo\" placeholder=\"TOPONIMO\" value=\""+ (descToponimo!='null' ? descToponimo : "") +"\" size=\"10\" style=\"display: none\" readonly=\"\">"+
				"<input type=\"text\" id=\"via_luogocontrollo\" name=\"via_luogocontrollo\" placeholder=\"DENOMINAZIONE STRADA\" value=\""+(via!='null' ? via : "")+"\" size=\"38\" style=\"display: none\" readonly=\"\">"+
				"<input type=\"text\" id=\"civico_luogocontrollo\" name=\"civico_luogocontrollo\" placeholder=\"CIVICO\" value=\""+(civico!='null' ? civico : "")+"\" size=\"10\" style=\"display: none\" readonly=\"\">"+
				"<input type=\"text\" id=\"cap_luogocontrollo\" name=\"cap_luogocontrollo\" placeholder=\"CAP\" value=\""+(cap!='null' ? cap : "")+"\" size=\"5\" maxlength=\"5\" onkeydown=\"return false;\" style=\"display: none\" required=\"\" autocomplete=\"off\">"+
				"<input type=\"text\" id=\"comune_luogocontrollo\" name=\"comune_luogocontrollo\" placeholder=\"COMUNE\" value=\""+(descComune!='null' ? descComune : "")+"\" size=\"30\" style=\"display: none\" readonly=\"\">"+
				"<input type=\"text\" id=\"provincia_luogocontrollo\" name=\"provincia_luogocontrollo\" placeholder=\"PROVINCIA\" value=\""+(descProvincia!='null' ? descProvincia : "")+"\" size=\"18\" style=\"display: none\" readonly=\"\">"+
				"<input type=\"hidden\" id=\"provinciaIdLuogoControllo\" name=\"provinciaIdLuogoControllo\" value=\""+(idProvincia!='null' ? idProvincia : "")+"\">"+	
				"<input type=\"hidden\" id=\"comuneIdLuogoControllo\" name=\"comuneIdLuogoControllo\" value=\""+(idComune!='null' ? idComune : "")+"\">"+	
				"<input type=\"hidden\" id=\"topIdLuogoControllo\" name=\"topIdLuogoControllo\" value=\""+(idToponimo!='null' ? idToponimo : "")+"\">";
				html+="<input type=\"button\" value=\"Inserisci Luogo del Controllo\" onClick=\" if (document.getElementById('siteId').value==-1) {alert('Inserire prima ASL');} else { openCapWidget('toponimo_luogocontrollo','topIdLuogoControllo','via_luogocontrollo','civico_luogocontrollo','comune_luogocontrollo', 'comuneIdLuogoControllo','cap_luogocontrollo','provincia_luogocontrollo','provinciaIdLuogoControllo', 'campania', document.getElementById('siteId').value);}\"/>";
				html +="</td>";
				document.getElementById("LuogoControllo").innerHTML=html;
			}
	
		}
	}
	}