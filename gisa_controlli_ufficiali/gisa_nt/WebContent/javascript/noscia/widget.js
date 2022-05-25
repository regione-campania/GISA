/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function callback(top, topId, via, civico, comune, comuneId, cap, prov, provinciaId, addressObj) 
{ 
	
	if (addressObj.provincia!='')
	{
	    document.getElementById(top).value= addressObj.top;       
	    document.getElementById(via).value= addressObj.via;       
	    document.getElementById(civico).value= addressObj.civ;       
	    document.getElementById(comune).value= addressObj.comune;       
	    document.getElementById(cap).value= addressObj.cap;       
	    document.getElementById(prov).value= addressObj.provincia;    
	    document.getElementById(comuneId).value= addressObj.comuneId;     
	    document.getElementById(topId).value= addressObj.topId;     
	    document.getElementById(provinciaId).value= addressObj.provinciaId; 
	    
		document.getElementById(top).style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById(via).style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById(civico).style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById(comune).style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById(cap).style = 'display: ; background-color: #dddddd; text-align: center'; 
		document.getElementById(prov).style = 'display: ; background-color: #dddddd; text-align: center';
		$("._placeholder_"+prov).remove();
		$('<span class="_placeholder_'+prov+'">provincia </span>').insertBefore('#'+prov);
		$("._placeholder_"+comune).remove();
		$('<span class="_placeholder_'+comune+'">comune </span>').insertBefore('#'+comune);
		$("._placeholder_"+cap).remove();
		$('<span class="_placeholder_'+cap+'">cap </span>').insertBefore('#'+cap);
		$("._placeholder_"+top).remove();
		$('<span class="_placeholder_'+top+'">indirizzo </span>').insertBefore('#'+top);
		
	}

	loadModalWindowUnlock();

}


function openCapWidget(toponimo,topId, via,civico,comune,comuneId,cap,prov,provinciaId,flag_regione,flag_id_asl) {
  var w=400;
  var h=550;
  var left = (screen.width/2)-(w/2)-200;
  var top = (screen.height/2)-(h/2)-100;
  var stl='location=0,toolbar=0,status=0,menubar=0,scrollbars=0,resizable=0,width='+w+',height='+h+', top='+top+',left='+left;
  loadModalWindowCustom("Attendere Prego...");
  
  //Passo al widget gli id dell'elemento html da riempire
  sessionStorage.setItem("toponimo",toponimo);
  sessionStorage.setItem("via", via);
  sessionStorage.setItem("civico", civico);
  sessionStorage.setItem("comune", comune);
  sessionStorage.setItem("cap", cap);
  sessionStorage.setItem("prov",prov);
  sessionStorage.setItem("comuneId",comuneId);
  sessionStorage.setItem("topId",topId);
  sessionStorage.setItem("provinciaId",provinciaId);

  var win = window.open("javascript/cap_widget/capall.html?flag_regione=" + flag_regione + "&flag_id_asl=" + flag_id_asl, "", stl);

  }

function openCapWidgetRidotta(toponimo,topId, via,civico,comune,comuneId,cap,prov,provinciaId,flag_regione,flag_id_asl,idcomunein,idprovinciain){
	var w=400;
	var h=550;
	var left = (screen.width/2)-(w/2)-200;
	var top = (screen.height/2)-(h/2)-100;
	var stl='location=0,toolbar=0,status=0,menubar=0,scrollbars=0,resizable=0,width='+w+',height='+h+', top='+top+',left='+left;
	loadModalWindowCustom("Attendere Prego...");
  
	//Passo al widget gli id dell'elemento html da riempire
	sessionStorage.setItem("toponimo",toponimo);
	sessionStorage.setItem("via", via);
	sessionStorage.setItem("civico", civico);
	sessionStorage.setItem("comune", comune);
	sessionStorage.setItem("cap", cap);
	sessionStorage.setItem("prov",prov);
	sessionStorage.setItem("comuneId",comuneId);
	sessionStorage.setItem("topId",topId);
	sessionStorage.setItem("provinciaId",provinciaId);
	
	var win = window.open("javascript/cap_widget/capallridotta.html?flag_regione=" + flag_regione + "&flag_id_asl=" + flag_id_asl  + 
				"&idcomunein=" + idcomunein + "&idprovinciain=" + idprovinciain, "", stl);
  	
}
