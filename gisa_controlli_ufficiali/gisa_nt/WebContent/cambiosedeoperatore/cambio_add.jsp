<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<link rel="stylesheet" type="text/css" href="opumodifica/css/styleModifica.css"></link>		


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script f src="dwr/interface/SuapDwr.js"> </script>

<script>

function PopolaListaComuni(idAsl)
{
	PopolaCombo.getValoriComboComuni1Asl(idAsl,{callback:PopolaListaComuniCallback,async:false}) ;
	
}

function PopolaListaComuniCallback(value)
{
	var valueCodici = value[0];
	var valueNomi = value[1];
	
	var select = "<select id=\"comune\" name=\"comune\" onChange=\"resetCampi()\">";
	for (i = 1; i<valueCodici.length;i++){
		select= select+ "<option value=\""+valueCodici[i]+"\">"+valueNomi[i]+"</option>";
	}
	var select = select + "</select>";
	document.getElementById("divComune").innerHTML = select;
	sortSelect(document.getElementById("comune"));
	
	resetCampi();
}

function calcolaCap(idComune, topon, indir, campo){	
	SuapDwr.getCap(idComune, topon, indir, campo,{callback:calcolaCapCallBack,async:false});
}

function showCoordinate(address,city,prov,cap,campo_lat,campo_long){
	campoLat = campo_lat;
	campoLong = campo_long;
	Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}

function setGeocodedLatLonCoordinate(value){
	campoLat.value = value[1];;
	campoLong.value =value[0];
}


function getSelectedText(elementId) {
    var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

function calcolaCapCallBack(val){
	var campo = val[1];
	var value = val[0];
	document.getElementById(campo).value = value;
}

function sortSelect(selElem) {
    var tmpAry = new Array();
    for (var i=0;i<selElem.options.length;i++) {
        tmpAry[i] = new Array();
        tmpAry[i][0] = selElem.options[i].text;
        tmpAry[i][1] = selElem.options[i].value;
    }
    tmpAry.sort();
    while (selElem.options.length > 0) {
        selElem.options[0] = null;
    }
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;
    }
    return;
}

function checkForm(form){
	var esito = true;
	
	var idAsl = document.getElementById("asl").value;
	var idComune = document.getElementById("comune").value;
	var idToponimo = document.getElementById("toponimo").value;
	var asl = getSelectedText("asl");
	var comune = getSelectedText("comune");
	var toponimo = getSelectedText("toponimo");
	var via = document.getElementById("via").value;
	var civico = document.getElementById("civico").value;
	var cap = document.getElementById("cap").value;
	var lat = document.getElementById("lat").value;
	var lon = document.getElementById("lon").value;
	
	var msg = 'Impossibile salvare.';
	if (idAsl==-1){
		msg+="\nASL non inserita.";
		esito=false;
	} 
	if (idComune==-1){
		msg+="\nCOMUNE non inserito.";
		esito=false;
	} 
	if (idToponimo==-1){
		msg+="\nTOPONIMO non inserito.";
		esito=false;
	} 
	if (via==''){
		msg+="\nVIA non inserita.";
		esito=false;
	}
	if (civico==''){
		msg+="\nCIVICO non inserito.";
		esito=false;
	}
	if (cap==''){
		msg+="\nCAP non inserito.";
		esito=false;
	}
	if (lat==''){
		msg+="\nLATITUDINE non inserita.";
		esito=false;
	}
	if (lon==''){
		msg+="\nLONGITUDINE non inserita.";
		esito=false;
	}
	
	if (esito==false){
		alert(msg);
		return false;
	}
	else {
		msg = "Dati inseriti per la nuova sede legale: \n\nASL: "+asl+"\t\tCOMUNE: "+comune+"\t\tINDIRIZZO: "+toponimo+" "+via+" "+civico+"\t\tCAP: "+cap+"\nLAT: "+lat+"\t\tLON: "+lon;
				
		if (idAsl!=<%=StabilimentoDettaglio.getIdAsl()%>)
			msg+="\n\t\t\tATTENZIONE: ASL MODIFICATA";
		msg+="\n\nConfermare?";
		
		if (confirm(msg)){
			loadModalWindow();
			form.submit();
			}
			
		else
			return false;
	}
}

function resetCampi(){
	document.getElementById("via").value='';
	document.getElementById("civico").value='';
	document.getElementById("lon").value='';
	document.getElementById("lat").value='';
	document.getElementById("cap").value='';
	document.getElementById("lat").value='';
	document.getElementById("toponimo").value='100';

}

function svuotaCoordinate(){
	document.getElementById("lon").value='';
	document.getElementById("lat").value='';
}
</script>



<%
String nomeContainer = StabilimentoDettaglio.getContainer();
nomeContainer = "suap";
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"&altId="+StabilimentoDettaglio.getAltId();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
%>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
Scheda Anagrafica Impresa
</td>
</tr>
</table>


<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">


<center>
<font size="3px">Operazione di cambio sede legale per lo stabilimento:<br/>
<b><%=StabilimentoDettaglio.getOperatore().getRagioneSociale() %></b><br/>
<i><%=StabilimentoDettaglio.getNumero_registrazione() %></i><br/>
<%-- Stato: <%=ListaStati.getSelectedValue(StabilimentoDettaglio.getStato()) %> -> <font color="red">CESSATO</font> --%>
</center>
<br/><br/>


<center>

<form id="cambioIndirizzo" name="cambioIndirizzo" action="CambioSedeOperatore.do?command=Insert&auto-populate=true" method="post">

<table class="indirizzo" cellpadding="10" cellspacing="10">

<tr>
<th></th>
<th>DATI ATTUALI</th>
<th>DATI NUOVA SEDE LEGALE</th>
</tr>

<tr>
<th>ASL</th>
<td><%=AslList.getSelectedValue(StabilimentoDettaglio.getIdAsl())%></td>
<td><% AslList.setJsEvent("onChange=\"PopolaListaComuni(this.value)\""); %><%=AslList.getHtmlSelect("asl", -1)%>
<% if (AslList.size()==1) { %><br/><font color="red">Per il cambio fuori ASL <br/>e' necessario rivolgersi all'Help Desk</font> <%} %>
</td>
</tr>

<tr>
<th>COMUNE</th>
<td><%=StabilimentoDettaglio.getOperatore().getSedeLegale().getDescrizioneComune() %></td>
<td><div id="divComune"></div></td>
</tr>

<tr><th>INDIRIZZO</th>
<td><%=ToponimiList.getSelectedValue(StabilimentoDettaglio.getOperatore().getSedeLegale().getToponimo()) %> <%=StabilimentoDettaglio.getOperatore().getSedeLegale().getVia() %> <%=StabilimentoDettaglio.getOperatore().getSedeLegale().getCivico() %> </td>
<td><%=ToponimiList.getHtmlSelect("toponimo", 100)%> <input type="text" id="via" name="via" onChange="svuotaCoordinate()"/> <input type="text" id="civico" name="civico" placeholder="CIVICO"/></td>
</tr>

<tr><th>CAP</th>
<td><%=StabilimentoDettaglio.getOperatore().getSedeLegale().getCap() %> </td>
<td><input type="text" readonly id="cap" name="cap" placeholder="CAP"/>  <input type="button" value="Calcola CAP" onclick="calcolaCap(document.getElementById('comune').value, document.getElementById('toponimo').value, document.getElementById('via').value, 'cap');" /></td>
</tr>

<tr><th>COORDINATE</th>
<td><%=StabilimentoDettaglio.getOperatore().getSedeLegale().getLatitudine() %> <%=StabilimentoDettaglio.getOperatore().getSedeLegale().getLongitudine() %> </td>
<td><input type="text" readonly id="lat" name="lat" placeholder="LAT"/> <input type="text" readonly id="lon" name="lon" placeholder="LON"/> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimo')+' '+document.getElementById('via').value+', '+document.getElementById('civico').value, getSelectedText('comune'), getSelectedText('comune'), document.getElementById('cap').value, document.getElementById('lat'), document.getElementById('lon'));" /></td>
</tr>

<tr><td colspan="3" align="center">
<input type="button" value="CONFERMA" onClick="checkForm(this.form)"/>
</td></tr>

</table>
<input type="hidden" id="idStabilimento" name="idStabilimento" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
</form>

</center>
</dhv:container>

<script>
document.getElementById("asl").onchange();
</script>