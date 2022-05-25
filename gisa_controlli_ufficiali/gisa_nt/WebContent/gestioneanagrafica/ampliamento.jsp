<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%
	boolean flagLineeScia = false;
	for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
		LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
		if(!l.getFlags().isNoScia())
		{
			flagLineeScia = true;
			break;
		}
	}
%>

<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<%if(flagLineeScia){ %>
	<script src="javascript/gestioneanagrafica/aggiungiLinea.js"></script>
<% } else { %>
	<script src="javascript/gestioneanagrafica/aggiungi_linea_noscia.js"></script>
<%} %>
<script src="javascript/gestioneanagrafica/add.js"></script>


<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}"> SCHEDA</a> > 
				<%if(flagLineeScia){ %>
					AGGIUNZIONE LINEA D'ATTIVITA'
					<% } else { %>
					AGGIUNGI LINEA
				<%} %>
		</td>
	</tr>
</table>

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=Ampliamento" onsubmit="return validateForm();">
<b>MODIFICA SCHEDA: <%if(flagLineeScia){ %>
					AGGIUNZIONE LINEA D'ATTIVITA'
					<% } else { %>
					AGGIUNGI LINEA
				<%} %> </b><br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>
<input type="hidden" id="numero_linee_attuali" name="numero_linee_attuali" value="<%=StabilimentoDettaglio.getListaLineeProduttive().size()%>"/>
<input type="hidden" id="tipo_linee_attivita" value="<%=StabilimentoDettaglio.getTipoAttivita()%>" />
<input type="hidden" id="tipo_gruppo_utente" value="<%=User.getId_tipo_gruppo_ruolo() %>"/>
<div id="dati_pratica" style="border: 1px solid black; background: #BDCFFF">
<br>
&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC / DATA SCIA: ${dataPratica} <br>
<br>
<input type="hidden" id="numeroPratica" name="numeroPratica" value="${numeroPratica}"/>
<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
<input type="hidden" id="idComunePratica" name="idComunePratica" value="${comunePratica}"/>
<input type="hidden" id="causaleRicevuta" value="${id_causale}">
</div>
<br/>

<%if(flagLineeScia){ %>

	<jsp:include page="include/sezione_gestione_causale.jsp"/>
	
	<div id="operazione_scheda" style="display: none">
<% } else { %>
	<input type="hidden" id="id_causale" name="_b_id_causale" value="5">
	<input type="hidden" id="data_pratica" name="_b_data_pratica" value="<%=(new SimpleDateFormat("dd-MM-yyyy")).format(Calendar.getInstance().getTime())%>">
	<input type="hidden" id="nota_pratica" name="_b_nota_pratica" value="aggiunzione linea di attivita">
	<div id="operazione_scheda">
<% } %>

<% if(StabilimentoDettaglio.getDataInizioAttivitaString() == null){ %>
	<script>
	alert('Attenzione: data inizio attività linea/stabilimento non presente, per procedere con questa operazione è necessario che sia presente una data inizio attività.  Compilare ERRATA CORRIGE per comunicare all\'Help Desk la data inizio attività.');
	window.location.href='GestioneAnagraficaAction.do?command=Details&altId=<%=StabilimentoDettaglio.getAltId() %>';
	</script>
<% } %>

<table class="details" id="tabella_linee" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="2">Riepilogo</th>
	</tr>
	<tr>
		<td class="formLabel">Numero registrazione</td>
		<td><%=StabilimentoDettaglio.getNumero_registrazione()%></td>
	</tr>
	<tr>
		<td class="formLabel">Ragione Sociale Impresa</td>
		<td><%=StabilimentoDettaglio.getOperatore().getRagioneSociale()%></td>
	</tr>
	<tr>
		<td class="formLabel">Data inizio attivita'</td>
		<td><%=StabilimentoDettaglio.getDataInizioAttivitaString().replaceAll("/", "-")%>
		<input type="hidden" id="data_inizio_stab" name="data_inizio_stab" value="<%=StabilimentoDettaglio.getDataInizioAttivitaString().replaceAll("/", "-") %>">
		</td>
	</tr>
	<tr>
		<td class="formLabel">LINEE DI ATTIVITA'</td>
		<td>
			<table class="details" cellspacing="0" border="0" width="100%" cellpadding="4"> 
				<% 
				for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){ %>
			  		<%LineaProduttiva lp = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i); %>
			  		<tr>
			  			<td style="width:10%">
			  				<%=lp.getNumeroRegistrazione() %>
			  			</td>
			  			<td>
			  				<div id="div_linee_<%=i%>" ></div>
 						   	<%if(lp.getStato() == 0) { %>
  								<span style='background-color:lime;'>STATO: <b>ATTIVA</b></span>
  						   	<% } else if(lp.getStato() == 2) { %>
  						   		<span style='background-color:orange;'>STATO: <b>SOSPESA</b></span>
 						   	<% } else if(lp.getStato() == 4 && flagLineeScia) { %>
  						   		<span style='background-color:red;'>STATO: <b>CESSATA</b></span>  		
  						   	<% } else if(lp.getStato() == 4 && !flagLineeScia) { %>
  						   		<span style='background-color:red;'>STATO: <b>CESSATA/REVOCATA</b></span>
  						   	<% } %>			  				
			  				<input type="hidden" id="linea_<%=i%>" name="linea_<%=i%>" value="<%=lp.getDescrizione_linea_attivita() %>" >
			  				<input type="hidden" id="id_lp_<%=i%>" name="id_lp_<%=i%>" value="<%=lp.getId()%>">
			  				<input type="hidden" id="codice_lp_<%=i%>" name="codice_lp_<%=i%>" value="<%=lp.getCodice()%>">
			  				<input type="hidden" id="stato_lp_<%=i%>" name="stato_lp_<%=i%>" value="<%=lp.getStato()%>">
			  			</td>	
			  		</tr>
					<%} %> 
				
			</table>
		</td>
	</tr>
	
	<tr id="tr_attivita_id_sezione">
		<th colspan="2">
			LINEE DI ATTIVITA'
			<input id="attivita_id_sezione" type="button" value="aggiungi linea di attivita'" onclick="gestione_aggiunzione_linee();">
		</th>
	</tr>
	<input type="hidden" id="id_linee_selezionate" value="">
	<input type="hidden" id="numero_linee" name="numero_linee" value="1">
	<input type="hidden" id="numero_linee_effettivo" name="numero_linee_effettivo" value="0">
	<input type="hidden" id="current_day" value="<%=(new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()))%>" >
	
</tbody>
</table>
<br><br>
<center>

<button type="submit" class="yellowBigButton" style="width: 250px;">Salva</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<button type="button" id="torna" class="yellowBigButton" style="width: 250px;"
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti'">Annulla</button>

</center>
</div>
</form>
<br><br>

<div id='popuplineeattivita'/>
<input type="hidden" id="numero_linee_aggiungibili" value="${numero_linee_aggiungibili}"/>

<%if(flagLineeScia){ %>
	<body onload="controlla();">
	</body>
<% }  %>


<script>

function validateForm(){
	
	var num_linee = document.getElementById('numero_linee_effettivo').value;
	if(num_linee == '0'){
		alert('Nessuna linea di attivita inserita');
		return false;
	}
	
	for(var i=0; i< document.getElementById('numero_linee_attuali').value; i++ ){
		var id_linea_presente = document.getElementById('id_lp_'+i).value;
		var stato_linea_presente = document.getElementById('stato_lp_'+i).value;
		for(var j=1; j < document.getElementById('numero_linee').value; j++){
			var field_linea_inserita = document.getElementById('lineaattivita_'+j+'_id_linea_attivita_ml');
			if (typeof(field_linea_inserita) != 'undefined' && field_linea_inserita != null){
				var id_linea_inserita = field_linea_inserita.value;
				if(id_linea_presente == id_linea_inserita && stato_linea_presente == '0'){
					alert('Attenzione! una o piu linee di attivita\' gia presenti ed attive nello stabilimento');
					return false;
				} else if(id_linea_presente == id_linea_inserita && stato_linea_presente != '4'){
					alert('Attenzione! una o piu linee di attivita\' gia presenti nello stabilimento');
					return false;
				}
			}
			
		}
	}
	
	for(var j=1; j < document.getElementById('numero_linee').value; j++){
		var field_linea_inserita_data_inizio = document.getElementById('lineaattivita_'+j+'_data_inizio_attivita');
		if (typeof(field_linea_inserita_data_inizio) != 'undefined' && field_linea_inserita_data_inizio != null){
			var data_linea = field_linea_inserita_data_inizio.value.split('-');
			var data1 = new Date(data_linea[2] + '-' + data_linea[1] + '-' + data_linea[0]);
		    var data_stab = document.getElementById('data_inizio_stab').value.split('-');
		    var data2 = new Date(data_stab[2] + '-' + data_stab[1] + '-' + data_stab[0]);
		    var data_corrente = document.getElementById('current_day').value.split('-');
		    var data3 = new Date(data_corrente[2] + '-' + data_corrente[1] + '-' + data_corrente[0]);
		    
		    if (data2 > data1 && data3 > data2){
		    	alert('Attenzione! la data inizio attivita\' non puo\' essere antecedente alla data inizio stabilimento');
		    	return false;
		    }
		}
	}
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
}

if(document.getElementById('causaleRicevuta').value != '1'){
	document.getElementById('dati_pratica').style.display = 'none';
	document.getElementById('torna').onclick = function(){
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
	};
}else{
	document.getElementById('id_causale').value = "1";
	document.getElementById('specifica_causale').style='display: none';
	document.getElementById('operazione_scheda').style='display:';
}

for(var i=0; i< document.getElementById('numero_linee_attuali').value; i++){
	var desc = document.getElementById('linea_' + i).value;
	var res = desc.split("->");
	document.getElementById('div_linee_' + i).innerHTML = res[0] + '-><br>' + res[1] + '-><br>' + res[2];
}



function trim(str) {
    try {
        if (str && typeof(str) == 'string') {
            return str.replace(/^\s*|\s*$/g, "");
        } else {
            return '';
        }
    } catch (e) {
        return str;
    }
}


function gestione_aggiunzione_linee(){
	
	document.getElementById('id_linee_selezionate').value = '';
	var id_linee_selezionate = '';
	for(var i = 0; i <= document.getElementById('numero_linee_attuali').value; i++){	
	
		if(document.getElementById('id_lp_'+i)){
			if(id_linee_selezionate == ''){
				id_linee_selezionate = document.getElementById('id_lp_'+i).value;
			} else {
				id_linee_selezionate = id_linee_selezionate + ',' +  document.getElementById('id_lp_'+i).value;
			}
		}
	}
	if(document.getElementById('numero_linee').value != '0'){
		var numero_linee_aggiunte = document.getElementById('numero_linee').value;
		for(var i = 0; i <= numero_linee_aggiunte; i++){
			if(document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml')){
				id_linee_selezionate = id_linee_selezionate + ',' +  document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml').value;
			}
		}
	}
	document.getElementById('id_linee_selezionate').value = id_linee_selezionate;
	//function definita nel js javascript/gestioneanagrafica/aggiungiLinea 
	aggiungi_linea();
}

function controlla(){
	
	if(document.getElementById('tipo_linee_attivita').value == '2'){
		document.getElementById('dati_pratica').style.display = 'none';
		document.getElementById('specifica_causale').style='display: none';
		document.getElementById('operazione_scheda').style='display: none';
		alert('Attenzione: Operazione di aggiunzione linea non consentita per stabilimenti senza sede fissa!');
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
		return false;
	} 
	
	if(document.getElementById('numero_linee_aggiungibili').value == '0'){
		document.getElementById('dati_pratica').style.display = 'none';
		document.getElementById('specifica_causale').style='display: none';
		document.getElementById('operazione_scheda').style='display: none';
		alert('Attenzione: per questo stabilimento, in base alla linea esistente, non è possibile aggiungere altre linee!');
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
		return false;
	}
}

</script>
