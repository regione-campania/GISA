<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentaliStruttureList"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentaliStruttura"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStruttura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="DpatRS" class="org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentali" scope="request"/>
<jsp:useBean id="AttrezzatureCampionamenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaAsl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<jsp:useBean id="idSDC" type="java.lang.Integer" scope="request"/>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css" />

	

<table class="trails" cellspacing="0">
		<tr>	
		
			<td width="100%"><a href="Dpat.do">DPAT</a> &gt <a href="Dpat.do?&combo_area=<%=DpatRS.getStrutturaAmbito().getId() %>&idAsl=<%=DpatRS.getIdAsl()%>&anno=<%=DpatRS.getAnno()%>">DPAT</a> &gt All. 2 <%=DpatRS.getAnno() %> ASL  <%=ListaAsl.getSelectedValue(DpatRS.getIdAsl()) %></td>
		</tr>
	</table>
	
	

	
<br><br>

	



<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script>
dwr.engine.setErrorHandler(handelrError);
dwr.engine.setTextHtmlHandler(handelrError);
 
RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};

function callback()
{
	
	
	makeSomma(field);
}

function handelrError(errorString, exception) {
	  alert("errore");
	  if(exception && exception.javaClassName== 'java.lang.NumberFormatException'){
	  	alert('I dati immessi non sono corretti. Assicurarsi di aver inserito valori Numerici');
	  	makeSomma(field);
	  }
	  else
		 {
		  alert('Errore Generico Contattare il servizio di Help Desk');ù
		  makeSomma(field);
		  
		 }
}

var stato=true;
function aggiornaDati(id,nomeCampo,valoreCampo,campo,idDpatRS)
{
	var idSDC = "<%=idSDC%>";
				
	if (stato==true){
		field = campo ;
	 	PopolaCombo.aggiornaDatiRisorseStrumentali(id,nomeCampo,valoreCampo,idDpatRS,callback);
	} else {
 		alert('Attenzione!!! Impossibile procedere con l\'inserimento dei dati poichè è stato riaperto lo strumento di calcolo.');
 		window.location='Dpat.do?command=Default';		
 	}		
}

function checkStatoSDCCallback(value){
	stato=value;
}

function makeSomma(field)
{
	var somma = 0 ;
 	var name = field.name ;
 	var namePref = name.split('_')[0];
 
 
 	
 	for(i=0;i<document.getElementById('bodytable').getElementsByTagName('INPUT').length;i++){
 		if (document.getElementById('bodytable').getElementsByTagName('INPUT')[i].name.indexOf(namePref)>-1 && document.getElementById('bodytable').getElementsByTagName('INPUT')[i].name.indexOf('num')>-1 )
 		{
 			somma+=parseInt(document.getElementById('bodytable').getElementsByTagName('INPUT')[i].value);
 			
 		}
 		
 	}
 	if (document.getElementById('tot'+namePref)!=null)
 		document.getElementById('tot'+namePref).innerHTML="<b>"+somma+"</b>"
 	
 	
 
 
}


</script>


<%if (DpatRS.isCompleto()==false && (DpatRS.getStrutturaAmbito()!=null && DpatRS.getStrutturaAmbito().getStato_all2()!=2  )){%>
	<input type="button" value="Salva Definitivo" onclick="javascript : if (confirm('ATTENZIONE!!!Chiudere definitivamente l allegato 2? se clicchi OK non sarà MAI PIU possibile modificarlo.Se invece prevedi di apportare ancora modifiche clicca su ANNULLA')){window.location='DpatRS.do?command=SalvaDefinitivo&combo_area=<%=DpatRS.getStrutturaAmbito().getId() %>&id=<%=DpatRS.getId()%>&idAsl=<%=DpatRS.getIdAsl()%>'}">
<%} %>

 <input type="button" value="Esporta Modello 2" onClick="location.href='DpatRS.do?command=GeneraExcel&anno=<%=DpatRS.getAnno() %>&combo_area=<%=DpatRS.getStrutturaAmbito().getId()%>&idAsl=<%=DpatRS.getIdAsl()%>'" style="background-color:#FF4D00; font-weight: bold;"/>



<form method="post">

<table width="100%" border="1">
<thead>
<tr>
<th colspan="12">ELENCO DELLE RISORSE STRUMENTALI IN POSSESSO DELLE STRUTTURE <%=(DpatRS.getFlagSianVet()!= null && ! "".equals(DpatRS.getFlagSianVet())) ? "("+DpatRS.getFlagSianVet()+")" : "" %></th>
</tr>
<tr >
	<th rowspan="2">&nbsp;</th>
	<th rowspan="2">
		N. AUTO DI SERVIZIO
		<br>(le auto utilizzate da più UU.OO. vanno conteggiate
		<br>una sola volta ed assegnate ad una sola U.O.)
	</th> 
	
	<th rowspan="2">ATTREZZATURE PER CAMPIONAMENTI
		<br>(lasciare visibile solo la risposta voluta)
	</th>
	<th colspan="2">N. PERSONAL COMPUTER</th>
	<th colspan="2">N. PERSONAL COMPUTER</th>
	
	<th rowspan="2">N. STAMPANTI</th>
	<th rowspan="2">QUANTITA' DI TELEFONI</th>
	<th rowspan="2">N. TERMOMETRI TARATI</th>
	<th colspan="2">ALTRO</th>
</tr>
<tr>
	
	<th>N. PERSONAL COMPUTER SENZA ADSL</th>
	<th>N. PERSONAL COMPUTER CON ADSL</th>
	<th>N. NOTEBOOK NON CONNESSI AD INTERNET</th>
	<th>N. NOTEBOOK CONNESSI AD INTERNET</th>
	<th>DESCRIZIONE</th>
	<th>QUANTITA'</th>
</tr>



</thead>
	<tbody id = "bodytable">
<%


DpatRisorseStrumentaliStruttureList listStrutture =  DpatRS.getListaStrutture();
int rowid = 0 ;
AttrezzatureCampionamenti.setSelectStyle("style=\"width: 100%;heigh:100%;\"");
int sommaAuto = 0 ;
int sommaPcNoInt = 0 ;
int sommaPcInt = 0  ;
int sommaNbNoConn = 0 ;
int sommaNbConn =  0 ;
int sommaStampanti = 0 ;
int sommaTelefoni = 0 ;
int sommaTermom = 0 ;
int sommaQuant = 0 ;
for (int i = 0 ; i < listStrutture.size(); i++)
{
	rowid = (rowid != 1 ? 1 : 2);
	DpatRisorseStrumentaliStruttura struttura = (DpatRisorseStrumentaliStruttura)listStrutture.get(i);
	String pathStruttura = struttura.getDescrizioneStrutturaLunga();
	AttrezzatureCampionamenti.setJsEvent("onchange=\"aggiornaDati("+struttura.getCodiceInternoFK()+",'id_attrezzature_campionamenti',this.value,this,"+DpatRS.getId()+")\"");
	sommaAuto += struttura.getNumAuto() ; 
	sommaPcNoInt += struttura.getNumComputerSenzaAdsl() ; 
	sommaPcInt += struttura.getNumComputerConAdsl() ; 
	sommaNbNoConn += struttura.getNumNotebookNonConnessi() ; 
	sommaNbConn += struttura.getNumNotebookConnessi() ; 
	sommaStampanti += struttura.getNumStampanti() ; 
	sommaTelefoni += struttura.getNumTelefoni() ; 
	sommaTermom += struttura.getNumTermometriTarati(); 
	sommaQuant += struttura.getQuantitaAltro() ; 

	
	%> 

	<tr class = "row<%=rowid%>">
		<td style="text-align: center;font-weight: bold;" class="tooltip" title="<%=pathStruttura %>"><span></span><%=struttura.getDescrizioneStruttura().toUpperCase() %></td>
		<td align="center"><input style="text-align: center;font-weight: bold;" type = "text"  value = "<%=struttura.getNumAuto() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_auto',this.value,this,<%=DpatRS.getId() %>)" name = "numAuto_<%=struttura.getId() %>" id = "numAuto_<%=struttura.getId() %>" width="10px;"></td>
		<td ><%= AttrezzatureCampionamenti.getHtmlSelect("id_attrezzature_campionamenti", struttura.getIdAttrezzatureCampionamenti()) %></td>
		<td align="center" ><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getNumComputerSenzaAdsl() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_computer_senza_adsl',this.value,this,<%=DpatRS.getId() %>)" name = "numComputerNoAdsl_<%=struttura.getId() %>" id = "numComputerNoAdsl_<%=struttura.getId() %>" width="10px;"></td>
		<td align="center"><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getNumComputerConAdsl() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_computer_con_adsl',this.value,this,<%=DpatRS.getId() %>)" name = "numComputerAdsl_"<%=struttura.getId() %> id = "numComputerAdsl_<%=struttura.getId() %>" width="10px;"></td>
		<td align="center"><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getNumNotebookNonConnessi() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_notebook_non_connessi',this.value,this,<%=DpatRS.getId() %>)" name = "numNotebookNoInternet_<%=struttura.getId() %>" id = "numNotebookNoInternet_<%=struttura.getId() %>" width="10px;"></td>
		<td align="center" ><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getNumNotebookConnessi() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_notebook_connessi',this.value,this,<%=DpatRS.getId() %>)" name = "numNotebookInternet_<%=struttura.getId() %>" id = "numNotebookInternet_<%=struttura.getId() %>" width="10px;"></td>
		<td align="center" ><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getNumStampanti() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_stampanti',this.value,this,<%=DpatRS.getId() %>)" name = "numStampanti_<%=struttura.getId() %>" id = "numStampanti_<%=struttura.getId() %>" width="10px;"></td>
		<td align="center"><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getNumTelefoni() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_telefoni',this.value,this,<%=DpatRS.getId() %>)" name = "numTelefoni_<%=struttura.getId() %>" id = "numTelefoni_<%=struttura.getId() %>" width="10px;"></td>
		<td align="center"><input style="text-align: center;font-weight: bold;" type = "text"  value = "<%=struttura.getNumTermometriTarati() %>" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'num_termometri_tarati',this.value,this,<%=DpatRS.getId() %>)" name = "numTermometri_<%=struttura.getId() %>" id = "numTermometri_<%=struttura.getId() %>" width="10px;"></td>
		<td ><textarea  value = "<%=toHtml(struttura.getAltro_descrizione()) %>" rows="5"  cols="32" onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'altro_descrizione',this.value,this,<%=DpatRS.getId() %>)" name = "descrizione_<%=struttura.getId() %>" id = "descrizione_<%=struttura.getId() %>" ><%=toHtml(struttura.getAltro_descrizione()) %></textarea></td>
		<td align="center"><input style="text-align: center;font-weight: bold;"  type = "text"  value = "<%=struttura.getQuantitaAltro() %>"  onchange="aggiornaDati(<%=struttura.getCodiceInternoFK() %>,'quantita_altro',this.value,this,<%=DpatRS.getId() %>)" name = "numQuantita_<%=struttura.getId() %>" id = "quantita_<%=struttura.getId() %>" width="10px;"></td>
	</tr>
		
		
		
	
		

	
	<%
}

%>
<tr bgcolor= "#ffdead">
		<td align="center"><b>TOT.</b></td>
		<td  align="center"><span id = "totnumAuto"><%="<b>"+sommaAuto +"</b>"%></span></td>
		<td  align="center">&nbsp;</td>	
		<td  align="center"><span id = "totnumComputerNoAdsl"><%="<b>"+sommaPcNoInt +"</b>"%></span></td>
		<td  align="center"><span id = "totnumComputerAdsl"><%="<b>"+sommaPcInt +"</b>"%></span></td>
		<td  align="center"><span id = "totnumNotebookNoInternet"><%="<b>"+sommaNbNoConn +"</b>"%></span></td>
		<td  align="center"><span id = "totnumNotebookInternet"><%="<b>"+sommaNbConn +"</b>"%></span></td>
		<td  align="center"><span id = "totnumStampanti"><%="<b>"+sommaStampanti +"</b>"%></span></td>
		<td  align="center"><span id = "totnumTelefoni"><%="<b>"+sommaTelefoni +"</b>"%></span></td>
		<td  align="center"><span id = "totnumTermometri"><%="<b>"+sommaTermom +"</b>"%></span></td>
		<td  align="center">&nbsp;</td>
		<td  align="center"><span id = "totnumQuantita"><%="<b>"+sommaQuant+"</b>"%></span></td>
	
		</tr>

</tbody>

</table>
</form>

      

