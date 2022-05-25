/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function recuperaLineaSottoposta(idTicket){
	PopolaCombo.recuperaLineaSottopostaCu(idTicket,{callback:recuperaLineaSottopostaCallBack,async:false});
}

function recuperaLineaSottopostaCallBack(val)
{
	controllaCampiAggiuntiviLineaMercato(val);
}

function controllaCampiAggiuntiviLinea(idLinea, idControllo) { 
	
	PopolaCombo.recuperaCampiAggiuntiviLineaMercato(idLinea, idControllo, {callback:controllaCampiAggiuntiviLineaMercatoCallBack,async:false});
	PopolaCombo.recuperaCampiAggiuntiviLineaTrasportoSOA(idLinea,{callback:controllaCampiAggiuntiviLineaTrasportoSOACallBack,async:false});

}
function controllaCampiAggiuntiviLineaMercatoCallBack(val)
{
	
	if (document.getElementById("OperatoreMercato")!=null){
		if (val==null){
			document.getElementById("OperatoreMercato").innerHTML="";
	
		}
		else {
			var html = "";
			html+="<td class=\"formLabel\">Operatore mercato sottoposto a controllo</td><td><select multiple size=\"10\" id=\"operatoreMercato\" name=\"operatoreMercato\">";
			var res = val.split(";;");
			for (k=0; k<res.length-1;k++){
				var elemento = res[k];
				var operatori = elemento.split("|");
				var ragioneSociale = operatori[1];
				var numBox = operatori[2];
				var selected = operatori[3];
				var id = operatori[0];
				html+="<option "+ selected +" value=\""+id +"\">[Num Box:"+numBox + "] "+ragioneSociale+"</option>";		
			}
//			html+="<option value=\"-1\">TUTTO IL MERCATO</option>";
			html+="</select></td>";
			document.getElementById("OperatoreMercato").innerHTML=html;
	
		}
	}
	}

function controllaCampiAggiuntiviLineaTrasportoSOACallBack(val)
{ 
	if (document.getElementById("AutomezzoSoa")!=null){

		if (val==null){
			document.getElementById("AutomezzoSoa").innerHTML="";
	
		}
		else {
			var html = "";
			html+="<td class=\"formLabel\">Automezzo sottoposto a controllo</td><td><select id=\"automezzoSoa\" name=\"automezzoSoa\">";
			var res = val.split(";;");
			for (k=0; k<res.length-1;k++){
				var elemento = res[k];
				var elementoSingolo = elemento.split("##");
				html+="<option value=\""+elementoSingolo[0] +"\">"+elementoSingolo[1]+"</option>";		
			}
	//		html+="<option value=\"MERCATO ALL'INGROSSO\">MERCATO ALL'INGROSSO</option>";
			html+="</select></td>";
			document.getElementById("AutomezzoSoa").innerHTML=html;
	
		}
	}
	}
	