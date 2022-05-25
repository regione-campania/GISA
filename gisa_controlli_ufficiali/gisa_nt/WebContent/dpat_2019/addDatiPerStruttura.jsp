<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatPiano"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatSezione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,java.text.DateFormat"%>
<%@ page import="org.aspcfs.modules.dpat2019.base.oia.OiaNodo"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="sezioni" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="dpat" class="org.aspcfs.modules.dpat2019.base.Dpat" scope="request"/>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<style>
.pulsante {
 outline: none;
 cursor: pointer;
 text-align: center;
 text-decoration: none;
 font: bold 12px Arial, Helvetica, sans-serif;
 color: black;
 padding: 0px 0px;
 border: solid 1px #0076a3;
 background: blue;
 background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#BDCFFF));
 background: -webkit-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: -moz-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: -ms-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: -o-linear-gradient(top,  #BDCFFF,  #BDCFFF);
 background: linear-gradient(top,  #BDCFFF,  #BDCFFF);
 -moz-border-radius: 8px;
 -webkit-border-radius: 8px;
 border-radius: 8px;
 -moz-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
 -webkit-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
 box-shadow: 0 1px 3px rgba(0,0,0,0.5);
}
.pulsante:hover {
 background: #0095cd;
 background: -webkit-gradient(linear, left top, left bottom, from(#0078a5), to(#00adee));
 background: -webkit-linear-gradient(top,  #0078a5,  #00adee);
 background: -moz-linear-gradient(top,  #0078a5,  #00adee);
 background: -ms-linear-gradient(top,  #0078a5,  #00adee);
 background: -o-linear-gradient(top,  #0078a5,  #00adee);
 background: linear-gradient(top,  #0078a5,  #00adee);
}
</style>
<%@ include file="../initPage.jsp"%>

<form action="dpat2019.do?command=InsertDatiPerStruttura&idDpat=<%=dpat.getId()%>" method="post">
<input type="hidden" id="idstruttura" name="idstruttura" value="<%=request.getAttribute("idStrutt")%>"/>
<input type="hidden" id="elencoIdRow" name="elencoIdRow" value=""/>
<script>
var idRow=2;
</script>

<%	
request.setAttribute("idstruttura", request.getAttribute("idStrutt"));
if (sezioni.size()>0){%>
	CARICO DI LAVORO EFFETTIVO ANNUALE MINIMO DI STRUTTURA IN U.I.<br>
	<input type="checkbox" id="ck_uiStruttura" name="ck_uiStruttura" onchange="abilitaText();"/>
	<input type="text" id="uiStruttura" name="uiStruttura" value="0" disabled/>
	<br><br><br>
	<input type="checkbox" id="ck_indicatori" name="ck_indicatori" onchange="abilitaSelect();"/>
	INDICATORI<br>
	<table id="t">
	<tr id="1">
		<th></th>
		<th></th>
		<th>Sezione</th>
		<th>Piano</th>
		<th>Attività</th>
		<th>Indicatore</th>
		<th>UI</th>
	</tr>
	<tr id="2">
		<input type="hidden" id="idRow_2" name="idRow_2" value="2"/>		
	 	<td><input type="button" id="add_2" name="add_2" value="+" onclick="cloneRow();" disabled/></td>  
	 	<td><input type="button" id="del_2" name="del_2" value="-" style="display :none" onclick="deleteRow(this)" disabled/></td>  
		<td><select id="elencoSezioni_2" name="elencoSezioni_2" onchange="loadPiani(this.value,document.getElementById('idRow_2').value);" disabled>
				<option value="-1">--SELEZIONA--</option>
				<%for (int i=0;i<sezioni.size();i++) {
					DpatSezione s = (DpatSezione)sezioni.get(i);%>
					<option value="<%=s.getId()%>"><%=s.getDescription()%></option>
				<%} %>
			</select></td>
		<td><select id="elencoPiani_2" name="elencoPiani_2" onchange="loadAttivita(this.value,document.getElementById('idRow_2').value);" disabled>
				<option value="-1">--SELEZIONA--</option>
			</select></td>
		<td><select id="elencoAttivita_2" name="elencoAttivita_2" onchange="loadIndicatori(this.value,document.getElementById('idRow_2').value);" disabled>
				<option value="-1">--SELEZIONA--</option>
			</select></td>
		<td><select id="elencoIndicatori_2" name="elencoIndicatori_2" disabled>
				<option value="-1">--SELEZIONA--</option>
			</select></td>
		<td><input type="text" id="ui_2" name="ui_2" disabled/></td>
	</tr>
	</table>
	<br><br>
	<p align="center">
	<input type="button" id="salva" value="salva" onclick="if(mySubmit()==0){submit();};"/>
	<input type="button" id="annulla" value="annulla" onclick="window.location='dpat2019.do?command=Lista'"/></p>
<%} else {%>
		Non esiste alcun DPAT (nell'anno corrente) specificato per l'asl selezionata.
		<p align="center">
		<input type="button" id="indietro" value="indietro" onclick="window.location='dpat2019.do?command=Lista'"/></p>
<%} %>
</form>

<script>
	var index = 0;

	function loadPiani(idSezione,idRow){
		index = idRow;
		document.getElementById("elencoPiani_"+index).style.display='block';
		PopolaCombo.getListaPiani(idSezione,getListaPianiCallback);	
	}
	function getListaPianiCallback(value){
		document.getElementById('elencoPiani_'+index).options.length = 0;
		document.getElementById('elencoAttivita_'+index).options.length = 0;
		document.getElementById('elencoIndicatori_'+index).options.length = 0;
		var select = document.getElementById("elencoPiani_"+index);
		select.options[select.options.length] = new Option('--SELEZIONA', '-1');
		for (var i=0;i<value.length;i++){
			select.options[select.options.length] = new Option(value[i].description, value[i].id);
		}
	}
	
	
	function loadAttivita(idPiano,idRow){
		index = idRow;
		document.getElementById("elencoAttivita_"+index).style.display='block';
		PopolaCombo.getListaAttivita(idPiano,getListaAttivitaCallback);	
	}
	function getListaAttivitaCallback(value){
		document.getElementById('elencoAttivita_'+index).options.length = 0;
		document.getElementById('elencoIndicatori_'+index).options.length = 0;
		
		var select = document.getElementById("elencoAttivita_"+index);
		select.options[select.options.length] = new Option('--SELEZIONA', '-1');
		for (var i=0;i<value.length;i++){
			select.options[select.options.length] = new Option(value[i].description, value[i].id);
		}
	}
	
	
	function loadIndicatori(idAttivita,idRow){
		index = idRow;
		document.getElementById("elencoIndicatori_"+index).style.display='block';
		document.getElementById("ui_"+index).style.display='block';
		PopolaCombo.getListaIndicatori(idAttivita,getListaIndicatoriCallback);	
	}
	function getListaIndicatoriCallback(value){
		document.getElementById('elencoIndicatori_'+index).options.length = 0;

		var select = document.getElementById("elencoIndicatori_"+index);
		select.options[select.options.length] = new Option('--SELEZIONA', '-1');
		for (var i=0;i<value.length;i++){
			select.options[select.options.length] = new Option(value[i].description, value[i].id);
		}
	}
	
	
	function cloneRow(){
		idRow=idRow+1;
		var row = document.getElementById("2");
		var table = document.getElementById("t");
		var clone = row.cloneNode(true); 
		clone.id = idRow;
		
		clone.getElementsByTagName('input')[0].id='idRow_'+idRow;  //HIDDEN
		clone.getElementsByTagName('input')[0].name='idRow_'+idRow;
		clone.getElementsByTagName('input')[0].value=idRow;
		
		clone.getElementsByTagName('input')[1].id='add_'+idRow;    //BOTTONI
		clone.getElementsByTagName('input')[1].name='add_'+idRow;
		clone.getElementsByTagName('input')[1].style='display : none';
		clone.getElementsByTagName('input')[2].id='del_'+idRow;
		clone.getElementsByTagName('input')[2].name='del_'+idRow;
		clone.getElementsByTagName('input')[2].style='display : block';
		
		clone.getElementsByTagName('select')[0].id='elencoSezioni_'+idRow;   //SELECT
		clone.getElementsByTagName('select')[1].id='elencoPiani_'+idRow;
		clone.getElementsByTagName('select')[2].id='elencoAttivita_'+idRow;
		clone.getElementsByTagName('select')[3].id='elencoIndicatori_'+idRow;
		clone.getElementsByTagName('select')[0].name='elencoSezioni_'+idRow;
		clone.getElementsByTagName('select')[1].name='elencoPiani_'+idRow;
		clone.getElementsByTagName('select')[2].name='elencoAttivita_'+idRow;
		clone.getElementsByTagName('select')[3].name='elencoIndicatori_'+idRow;
		clone.getElementsByTagName('select')[0].onchange=function (){loadPiani(clone.getElementsByTagName('select')[0].value,clone.getElementsByTagName('input')[0].value);};
		clone.getElementsByTagName('select')[1].onchange=function (){loadAttivita(clone.getElementsByTagName('select')[1].value,clone.getElementsByTagName('input')[0].value);};
		clone.getElementsByTagName('select')[2].onchange=function (){loadIndicatori(clone.getElementsByTagName('select')[2].value,clone.getElementsByTagName('input')[0].value);};
					
		clone.getElementsByTagName('input')[3].id='ui_'+idRow;   //TEXT
		clone.getElementsByTagName('input')[3].name='ui_'+idRow;
		
		table.appendChild(clone);
	}
	
	function deleteRow(r){
		var i = r.parentNode.parentNode.rowIndex;
		document.getElementById("t").deleteRow(i);
	}
	
	function concatenaIdRow(){
		var elencoId='';
		var numRow = document.getElementById("t").rows.length;
		for (var k=0; k<numRow; k++){
			elencoId=elencoId+document.getElementById("t").rows[k].id+";";
		}
		document.getElementById("elencoIdRow").value=elencoId;
	}
	
	function abilitaText(){
		if (document.getElementById("ck_uiStruttura").checked){
			document.getElementById("uiStruttura").disabled=false;
			document.getElementById("uiStruttura").value="0";
		}
		else{
			document.getElementById("uiStruttura").value="0";
			document.getElementById("uiStruttura").disabled=true;
		}
	}
	
	function abilitaSelect(){
		if (document.getElementById("ck_indicatori").checked){
			var select = document.getElementsByTagName("select");
			var inputs = document.getElementsByTagName("input");
			for(var i = 0; i < select.length; i++) {
				select[i].disabled=false;
				select[i].value='-1';
			}
			for(var i = 0; i < inputs.length; i++) {
				if(inputs[i].name.indexOf('ui_') == 0){
					inputs[i].disabled=false;
					inputs[i].value="";
				}
				if(inputs[i].name.indexOf('add_')==0){
					inputs[i].disabled=false;
				}
				if(inputs[i].name.indexOf('del_')==0){
					inputs[i].disabled=false;
				}
			}
		}
		else {
			var select = document.getElementsByTagName("select");
			var inputs = document.getElementsByTagName("input");
			for(var i = 0; i < select.length; i++) {
				select[i].disabled=true;
				select[i].value='-1';
			}
			for(var i = 0; i < inputs.length; i++) {
				if(inputs[i].name.indexOf('ui_') == 0){
					inputs[i].disabled=true;
					inputs[i].value="";
				}
				if(inputs[i].name.indexOf('add_') ==0){
					inputs[i].disabled=true;
				}
				if(inputs[i].name.indexOf('del_')==0){
					inputs[i].disabled=true;
				}
			}
		}
	}
	
	function controllaDati(){
		var flag=0;
		var ck_ind = document.getElementById("ck_indicatori");
		if (ck_ind.checked){
			var select = document.getElementsByTagName("select");
			for(var i = 0; i < select.length; i++) {
			    if(select[i].value == -1) {
			    	flag=1;
			    }
			}
			
			var inputs = document.getElementsByTagName("input");
			for(var i = 0; i < inputs.length; i++) {
			    if(inputs[i].name.indexOf('ui_') == 0) {
			    	if(inputs[i].value==""){
			    		flag=1;
			    	}
			    }
			}
		}
		return flag;
	}
	
	function mySubmit(){
		var flag = controllaDati();
		if (flag==0){
			concatenaIdRow();
		}
		else {
			alert("INSERIRE TUTTI I DATI");
		}
		return flag;
	}
</script>
	
	
	
	
	