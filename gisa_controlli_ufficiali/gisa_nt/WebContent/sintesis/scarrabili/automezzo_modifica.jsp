<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="Relazione" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Automezzo" class="org.aspcfs.modules.sintesis.base.SintesisAutomezzo" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneml.base.*" %> 

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="sintesis/scarrabili/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="sintesis/scarrabili/css/print.css" />

<%@ include file="../../initPage.jsp" %> 

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>

<script>

function annulla(){
	if (confirm('Le modifiche saranno annullate. Proseguire?')){
		loadModalWindow();
		window.location.href= 'StabilimentoSintesisAction.do?command=DettaglioAutomezzo&id=<%=Automezzo.getId()%>';
	}
}

function checkForm(form){
	if (confirm('Le modifiche saranno salvate. Proseguire?')){
		loadModalWindow();
		form.submit();
	}
}


var inputTemp = "";
function PopolaListaComuni(idProvincia, input)
{
	inputTemp = input;
	PopolaCombo.getValoriComboComuni1Provincia(idProvincia,{callback:PopolaListaComuniCallback,async:false}) ;
}

function PopolaListaComuniCallback(value)
{ 
	var valueCodici = value[0];
	var valueNomi = value[1];
	var select = "<select id=\""+inputTemp+"IdComune\" name=\""+inputTemp+"IdComune\" onChange=\"resetCampi()\">";
	for (i = 1; i<valueCodici.length;i++){
		select= select+ "<option value=\""+valueCodici[i]+"\">"+valueNomi[i]+"</option>";
	}
	var select = select + "</select>"; 
	document.getElementById("divComune"+inputTemp).innerHTML = select;
	sortSelect(document.getElementById(inputTemp+"IdComune"));
	resetCampi();
}

function getSelectedText(elementId) {
    var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
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

function resetCampi(){
	document.getElementById(inputTemp+"Via").value='';
	document.getElementById(inputTemp+"Civico").value='';
	document.getElementById(inputTemp+"IdToponimo").value='100';
}

</script>


<center>
<i>Modifica automezzo associato alla linea:</i><br/>
<b><%=Relazione.getPathCompleto() %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b><br/>
<a href="StabilimentoSintesisAction.do?command=ListaAutomezziLinea&idRelazione=<%=Relazione.getIdRelazione()%>">(Torna alla lista automezzi)</a>
</center>

<br/><br/>	

<form id = "editAutomezzo" name="editAutomezzo" action="StabilimentoSintesisAction.do?command=AggiornaAutomezzo&auto-populate=true" method="post">
	
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<col width="10%">

<tr><th colspan="2">Identificativo dell'automezzo/contenitore</th></tr>

<tr>
<td class="formLabel">Marca</td>
<td> <input type="text" readonly name="automezzoMarca" id="automezzoMarca" value="<%=toHtml(Automezzo.getAutomezzoMarca()) %>"/></td>
</tr>

<tr>
<td class="formLabel">Tipo</td>
<td><input type="text" readonly name="automezzoTipo" id="automezzoTipo" value="<%=toHtml(Automezzo.getAutomezzoTipo()) %>"/></td>
</tr>

<tr>
<td class="formLabel">Targa</td>
<td><input type="text" readonly name="automezzoTarga" id="automezzoTarga" value="<%=toHtml(Automezzo.getAutomezzoTarga()) %>"/></td>
</tr>

<tr><th colspan="2">Luogo di ricovero abituale dell'automezzo/contenitore</b> (se diverso dalla sede operativa dell'impresa)</th></tr>

<tr>
<td class="formLabel">Provincia</td>
<td>
<% ProvinceList.setJsEvent("onChange=\"PopolaListaComuni(this.value, 'ricovero')\""); %>
<%=ProvinceList.getHtmlSelect("ricoveroIdProvincia", Automezzo.getRicoveroIdProvincia())%>
</td>
</tr>

<tr>
<td class="formLabel">Comune</td>
<td><div id="divComunericovero"></div></td>
</tr>

<tr>
<td class="formLabel">Indirizzo</td>
<td><%=ToponimiList.getHtmlSelect("ricoveroIdToponimo", Automezzo.getRicoveroIdToponimo())%> <input type="text" id="ricoveroVia" name="ricoveroVia" value="<%=toHtml(Automezzo.getRicoveroVia())%>"/> <input type="text" id="ricoveroCivico" name="ricoveroCivico" placeholder="CIVICO" value="<%=toHtml(Automezzo.getRicoveroCivico())%>"/></td>
</tr>

<tr><th colspan="2">Luogo di detenzione del registro delle partite di cui all'art. 22 del Reg. CE/1069/2009</b> (se diverso dalla sede operativa dell'impresa)</th></tr>

<tr>
<td class="formLabel">Provincia</td>
<td>
<% ProvinceList.setJsEvent("onChange=\"PopolaListaComuni(this.value, 'detenzione')\""); %>
<%=ProvinceList.getHtmlSelect("detenzioneIdProvincia", Automezzo.getDetenzioneIdProvincia())%>
</td>
</tr>

<tr>
<td class="formLabel">Comune</td>
<td><div id="divComunedetenzione"></div></td>
</tr>

<tr>
<td class="formLabel">Indirizzo</td>
<td><%=ToponimiList.getHtmlSelect("detenzioneIdToponimo", Automezzo.getDetenzioneIdToponimo())%> <input type="text" id="detenzioneVia" name="detenzioneVia" value="<%=toHtml(Automezzo.getDetenzioneVia())%>"/> <input type="text" id="detenzioneCivico" name="detenzioneCivico" placeholder="CIVICO" value="<%=toHtml(Automezzo.getDetenzioneCivico())%>"/></td>
</tr>

<tr><th colspan="2">Caratteristiche dell'automezzo o del contenitore e materiali trasportati</th></tr>

<tr>
<td class="formLabel">Caratteristiche</td>
<td>
<input type="checkbox" name="automezzoCisternaTrasporto" id="automezzoCisternaTrasporto" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoCisternaTrasporto())) ? "checked=\"checked\"" : "" %> value="S"> cisterna adibita al trasporto<br/>
<input type="checkbox" name="automezzoVeicoloFreschi" id="automezzoVeicoloFreschi"  <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschi())) ? "checked=\"checked\"" : "" %> value="S"> veicolo adibito al trasporto di sottoprodotti freschi <input type="checkbox" name="automezzoVeicoloFreschiCat1" id="automezzoVeicoloFreschiCat1" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat1())) ? "checked=\"checked\"" : "" %> value="S"> categoria 1 <input type="checkbox" name="automezzoVeicoloFreschiCat2" id="automezzoVeicoloFreschiCat2" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat2())) ? "checked=\"checked\"" : "" %> value="S"> categoria 2 <input type="checkbox" name="automezzoVeicoloFreschiCat3" id="automezzoVeicoloFreschiCat3" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat3())) ? "checked=\"checked\"" : "" %> value="S"> categoria 3<br/>
<input type="checkbox" name="automezzoVeicoloDerivati" id="automezzoVeicoloDerivati" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivati())) ? "checked=\"checked\"" : "" %> value="S"> veicolo adibito al trasporto di prodotti derivati <input type="checkbox" name="automezzoVeicoloDerivatiCat1" id="automezzoVeicoloDerivatiCat1" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat1())) ? "checked=\"checked\"" : "" %> value="S"> categoria 1 <input type="checkbox" name="automezzoVeicoloDerivatiCat2" id="automezzoVeicoloDerivatiCat2" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat2())) ? "checked=\"checked\"" : "" %> value="S"> categoria 2 <input type="checkbox" name="automezzoVeicoloDerivatiCat3" id="automezzoVeicoloDerivatiCat3" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat3())) ? "checked=\"checked\"" : "" %> value="S"> categoria 3<br/>
<input type="checkbox" name="automezzoContenitore" id="automezzoContenitore" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoContenitore())) ? "checked=\"checked\"" : "" %> value="S"> contenitore: dimensioni e caratteristiche <input type="text" name="automezzoContenitoreDimensioni" id="automezzoContenitoreDimensioni" value="<%=toHtml(Automezzo.getAutomezzoContenitoreDimensioni()) %>"/>
</td>
</tr>

<tr><th colspan="2">Caratteristiche del trasporto</th></tr>

<tr>
<td class="formLabel">Caratteristiche</td>
<td>
<input type="checkbox" name="trasportoTemperaturaControllata" id="trasportoTemperaturaControllata" <%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoTemperaturaControllata())) ? "checked=\"checked\"" : "" %> value="S"> a temperatura controllata<br/>
<input type="checkbox" name="trasportoIsotermico" id="trasportoIsotermico" <%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoIsotermico())) ? "checked=\"checked\"" : "" %> value="S"> isotermico <input type="checkbox" name="trasportoTemperaturaAmbiente" id="trasportoTemperaturaAmbiente" <%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoTemperaturaAmbiente())) ? "checked=\"checked\"" : "" %> value="S"> a temperatura ambiente<br/>
</td>
</tr>

<tr>
<th colspan="2">NUMERO IDENTIFICATIVO ATTRIBUITO</th>
</tr>
<tr>
<td class="formLabel">Numero</td>
<td><%=toHtml(Automezzo.getNumeroIdentificativo()) %></td></tr>

<tr>
<td><input type="button" value="annulla" onClick="annulla()"/></td>
<td><input type="button" value="conferma" onClick="checkForm(this.form)"/></td>
</tr>

</table>

<input type="hidden" name="id" id="id" value="<%=Automezzo.getId()%>">
<input type="hidden" name="idRel" id="idRel" value="<%=Automezzo.getIdRelazione()%>">

</form>

<script>
document.getElementById("ricoveroIdProvincia").onchange();
document.getElementById("ricoveroIdComune").value = <%=Automezzo.getRicoveroIdComune()%>;
document.getElementById("ricoveroIdToponimo").value = <%=Automezzo.getRicoveroIdToponimo()%>;
document.getElementById("ricoveroVia").value = '<%=toHtml(Automezzo.getRicoveroVia())%>';
document.getElementById("ricoveroCivico").value = '<%=toHtml(Automezzo.getRicoveroCivico())%>';

document.getElementById("detenzioneIdProvincia").onchange();
document.getElementById("detenzioneIdComune").value = <%=Automezzo.getDetenzioneIdComune()%>;
document.getElementById("detenzioneIdToponimo").value = <%=Automezzo.getDetenzioneIdToponimo()%>;
document.getElementById("detenzioneVia").value = '<%=toHtml(Automezzo.getDetenzioneVia())%>';
document.getElementById("detenzioneCivico").value = '<%=toHtml(Automezzo.getDetenzioneCivico())%>';
</script>


<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

