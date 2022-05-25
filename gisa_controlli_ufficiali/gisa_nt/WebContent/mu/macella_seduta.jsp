<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statiCapi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="specieBovine" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="razzeBovine" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="categorieBovine"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categorieBufaline"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="catRischio" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PianiRisanamento"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="seduta"
	class="org.aspcfs.modules.mu.base.SedutaUnivoca" scope="request" />
<jsp:useBean id="wkf" class="java.util.ArrayList" scope="request" />
<%@page
	import="org.aspcfs.modules.mu.base.CapoUnivoco, org.aspcfs.modules.mu_wkf.base.Path"%>
<%@ page import="java.util.*"%>
<%@page import="org.aspcfs.modules.mu.base.CapoUnivocoList"%>

<%@ include file="../../initPage.jsp"%>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>HTML Table Filter Generator script | Examples - jsFiddle
	demo by koalyptus</title>
<script type='text/javascript' src='/js/lib/dummy.js'></script>
<link rel="stylesheet" type="text/css" href="/css/result-light.css">
<script type='text/javascript'
	src="javascript/tablefilter/tablefilter_all_min.js"></script>
<link rel="stylesheet" type="text/css"
	href="javascript/tablefilter/filtergrid.css">
</head>

<script>
function macella(form, scelta){
	formTest = true;
	message = "";
	 
		message = label("", "" +  " Seleziona almeno un animale per la macellazione\r\n");
		formTest = false;
		
		$("input[id^=capo_]").each(
				
				function() {

				//alert($(this).attr("id"));	
			if ($(this).is(':checked')){
				//alert('ok');
				//alert($(this).val());
				formTest = true;
				message = "";
			}
				});
		
	if (formTest)	{
	 $("#idPath").val(scelta);
	 form.submit();
	}else { alert(message);}
}


function gestisciAggiuntaSingola(campo, specie, partita){
	 if (campo.checked){
		 campo.parentNode.parentNode.setAttribute('class', 'rowmusel');
	 }
	 else {
		 document.getElementById("specie_"+specie+"_"+partita).checked = false;
		 campo.parentNode.parentNode.setAttribute('class', 'rowmu'+specie);
	 }
	 }



function gestisciAggiuntaTotale(campo, specie, numero){
	//var x = document.getElementsByName("capo_"+specie);	
	var i = 0;	
	var id_checkboxes = '_'+specie;
	var id_text = 'specie_num_'+specie;
	//alert("input[id$="+id_checkboxes+"]");
	$("input:checkbox[id$="+id_checkboxes+"]").each(function(index){
		//	alert('aaaa');
		
			if (campo!=null){
			//	alert('fff');
					 if (campo.checked){							
						 $(this).prop('checked', true);
						 $(this).parent().parent().prop('class', 'rowmusel');
						 $("input[id="+id_text+"]").val(index+1);
						 }else{
							 $(this).prop('checked', false);
							 $(this).parent().parent().prop('class', 'rowmu'+specie);
							 $("input[id="+id_text+"]").val(0);
						 }
					
			}//
			else{
				
				
			//	alert("input:checkbox[id$="+id_checkboxes+"]");
				var num = numero.val();
				var numSpecie = $("input:checkbox[id$="+id_checkboxes+"]").size();
				//alert(numSpecie);
				if (num>numSpecie){
					num = numSpecie;
					numero.val(num);
				}
				
				if (index < num ){
					$(this).prop('checked', true);
					 $(this).parent().parent().prop('class', 'rowmusel');
				}
				else{
					 $(this).prop('checked', false);
					 $(this).parent().parent().prop('class', 'rowmu'+specie);
				}
				
				if (index = num){
					var all = 'specie_'+specie+'_all';
					$("input:checkbox[id$="+all+"]").prop('checked', true);
				}
				
			}
			
			})

 }
 
 
function selezionaNumCapi(specie,partita){
	 var numero = document.getElementById("specie_num_"+specie+"_"+partita);
	 gestisciAggiuntaTotale(null, specie, partita, numero);
}
 
function selezionaNumCapi(specie){
	
	var id_text = 'specie_num_'+specie;
	 var numero =  $("input[id="+id_text+"]");
	 gestisciAggiuntaTotale(null, specie, numero);
}
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>



<table class="trails" cellspacing="0">
	<tr>
		<td><a
			href="MacellazioneUnica.do?command=List&orgId=<%=seduta.getIdMacello()%>">Home
				macellazioni </a> > <a
			href="MacellazioneUnica.do?command=DettaglioSeduta&idSeduta=<%=seduta.getId()%>">
				Dettaglio seduta </a> > Procedi</td>
	</tr>
</table>

<%
String param1 = "orgId=" + seduta.getIdMacello();
%>
<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>" >
<table class="details" layout="fixed" width="50%">
	<col width="180px">
	<th colspan="2">Dati seduta</th>
	<tr>
		<td class="formLabel">Data della seduta</td>
		<td><%=toDateasString(seduta.getData())%></td>
	</tr>
	<tr>
		<td class="formLabel">Numero della seduta</td>
		<td><%=seduta.getNumeroSeduta()%></td>
	</tr>
	<tr>
		<td class="formLabel">Specie capi</td>
		<td>
			<table width="100%">
				<tr>
					<th>Specie</th>
					<th>Num. capi</th>
				</tr>
				<%
					HashMap <Integer, HashMap<Integer, Integer>> mapConteggi = CapoUnivocoList.calcolaStatisticheSpecie(seduta.getListaCapi()); 
					HashMap<Integer, Integer> grigliaSpecie= seduta.getListaCapiNumeri();
						Iterator<Integer> itSpecie = grigliaSpecie.keySet().iterator();
						while(itSpecie.hasNext())
						{
					int specie = itSpecie.next(); 
					int num = grigliaSpecie.get(specie);
					HashMap<Integer, Integer> mapThisConteggio = mapConteggi.get(specie);
				%>

				<tr>
					<td><%=specieList.getSelectedValue(specie)%></td>
					<td><%=num%> (di cui: <%
						Iterator it = mapThisConteggio.entrySet().iterator();
						int k = 0;
					    while (it.hasNext()) {
					        Map.Entry pair = (Map.Entry)it.next();
					       
					%> <%=(k > 0) ?  "," : ""%> <%=pair.getValue()%> <%=statiCapi.getSelectedValue((Integer)pair.getKey())%>
						) <%
 	k++;
 }
 %></td>
				</tr>

				<%
					}
				%>

			</table>
		</td>
	</tr>
</table>

<br />
<br />

<form name="macellaSeduta" id="macellaSeduta"
	action="MacellazioneUnica.do?command=OperazioniSeduta" method="post">
	<input type="hidden" id="idSeduta" name="idSeduta"
		value="<%=seduta.getId()%>" /> <input type="hidden" id="idPath"
		name="idPath" value="" />
	<table class="details"
		style="border-collapse: collapse; table-layout: fixed;" width="100%"
		id="table2">

		<tr>
			<th>Specie</th>
			<th>Matricola</th>
			<th>Numero partita</th>
			<th>Seleziona</th>
			<th>Selezione multipla</th>
		</tr>
		<%
			int idSpecieCorrente = -1;
			for (int i = 0; i< seduta.getListaCapi().size(); i++){
			CapoUnivoco capo = (CapoUnivoco) seduta.getListaCapi().get(i);
			if (capo.getIdStato() == CapoUnivoco.idStatoDocumentale){
		%>

		<tr class="rowmu<%=capo.getSpecieCapo()%>" id="<%=capo.getId()%>">
			<td><%=specieList.getSelectedValue(capo.getSpecieCapo())%></td>
			<td><%=capo.getMatricola()%></td>
			<td><%=capo.getNumeroPartita()%></td>
			<td><input type="checkbox"
				id="capo_<%=capo.getId()%>_<%=capo.getSpecieCapo()%>"
				name="capo_<%=capo.getId()%>_<%=capo.getSpecieCapo()%>" /></td>
			<td>
				<%
					if (idSpecieCorrente != capo.getSpecieCapo()){
				%> <input
				type="checkbox" id="specie_<%=capo.getSpecieCapo()%>_all"
				name="specie_<%=capo.getSpecieCapo()%>_all"
				onClick="gestisciAggiuntaTotale(this, '<%=capo.getSpecieCapo()%>', '-1')" />
				Seleziona tutti i capi di questa specie <br /> Seleziona <input
				type="text" id="specie_num_<%=capo.getSpecieCapo()%>" size="2"
				maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"
				name="specie_num_<%=capo.getSpecieCapo()%>" value="0" /> capi di
				questa specie <input type="button"
				onClick="selezionaNumCapi('<%=capo.getSpecieCapo()%>', '<%=capo.getIdPartita()%>')"
				value="ok" /> <%
 	}
 %> &nbsp;
			</td>
		</tr>

		<%
			idSpecieCorrente = capo.getSpecieCapo();
		}}
		%>
	</table>
	<br />
	<br />
	<div align="center">
		<table style="border: 1px; border-collapse: collapse">
			<tr>
				<%
					Iterator i = wkf.iterator();

				while (i.hasNext()){
				Path thisPath = (Path) i.next();
				%>
				<td style="border: 1px solid black">&nbsp; <input type="button"
					value="<%=thisPath.getDescrizione()%>"
					onClick="macella(this.form, <%=thisPath.getId()%>)" /> &nbsp;
				</td>
				<%
					}
				%>
				<!-- <td style="border:1px solid black">
&nbsp;
<input type="button" value="LIBERO CONSUMO" onClick="macella(this.form, 1)"/>
&nbsp;
</td>
<td style="border:1px solid black">
&nbsp;
<input type="button" value="MORTE ANTE MACELLAZIONE" onClick="macella(this.form, 2)"/>
&nbsp;
</td>
<td style="border:1px solid black">
&nbsp;
<input type="button" value="EVIDENZA VISITA AM/PM" onClick="macella(this.form, 3)" />
&nbsp;
</td> -->
			</tr>
		</table>
	</div>

</form>
</dhv:container>
<script type='text/javascript'>//<![CDATA[ 

var table2_Props = {
	col_0: "select",
	col_2: "select",
	col_3: "none",
	display_all_text: " [ Mostra tutte ] ",
    sort_select: true
};
var tf2 = setFilterGrid("table2", table2_Props);
//]]>  </script>
