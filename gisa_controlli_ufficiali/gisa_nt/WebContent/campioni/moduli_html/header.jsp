<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

    <script>
function verificaStatoControllo(dataChiusura){
	
	if(dataChiusura != null && dataChiusura != '' && dataChiusura != 'null'){
		var f = document.forms['myform'];
		for(var i=0,fLen=f.length;i<fLen;i++){

			if (f.elements[i].type == 'radio')
		    { 
		          f.elements[i].disabled = true;
		    } 
			else if (f.elements[i].type == 'submit')
		    { 
		       f.elements[i].className = 'buttonClass';
			   
		    } 
		    else if (f.elements[i].type == 'text'){
			    
		  		f.elements[i].readOnly = true;
		  		//f.elements[i].className = 'layout'; MANDA IN ERRORE IL DOCUMENTALE
		  		f.elements[i].style.background = "#ffffff";
		    }
		}
		var g = document.forms['myform'].getElementsByTagName("textarea");
		for(var j=0; j < g.length; j++)
			 g.item(j).className = '';

		//document.getElementById('stampaId').style.display = 'none';
		if (document.getElementById('salvaId')!=null)
			document.getElementById('salvaId').style.display = 'none';

				
	}
}
</script>

    <script>
function verificaStatoControllo2(dataChiusura){
	
	if(dataChiusura != null && dataChiusura != '' && dataChiusura != 'null'){
	
//document.getElementById('stampaId').style.display = 'none';
		if (document.getElementById('bottoneModifica1')!=null)
			document.getElementById('bottoneModifica1').style.display = 'none';
		if (document.getElementById('bottoneModifica2')!=null)
			document.getElementById('bottoneModifica2').style.display = 'none';
		if (document.getElementById('bottoneModifica3')!=null)
			document.getElementById('bottoneModifica3').style.display = 'none';
				
	}
}
</script>


<body onload="javascript:verificaStatoControllo('<%=OrgCampione.getData_chiusura_campione()%>'); javascript:verificaStatoControllo2('<%=OrgCampione.getData_chiusura_campione()%>'); if (typeof catturaHtml =='function') {catturaHtml(this.gestionePdf);}; if (this.gestionePdf!=null) {this.gestionePdf.submit();}">

<!--  INIZIO HEADER -->
<div class="header">

<table cellpadding="10" cellspacing="10" style="border-collapse: collapse;table-layout:fixed;" width="100%">
<col width="20%"><col width="40%"><col width="20%">
<tr>

<td style="border:1px solid black;">
REGIONE <br/> CAMPANIA <br/> 
<img style="text-decoration: none;" width="100%" documentale_url="" src="gestione_documenti/schede/images/<%=(OrgOperatore.getAsl()!=null) ? OrgOperatore.getAsl().toLowerCase() : ""%>.jpg" />
</td>

<td style="border:1px solid black;">
DIPART. DI PREV. SERVIZIO <label class="layout"><%=OrgCampione.getServizio() != null ? OrgCampione.getServizio() : "" %></label><br>
U.O. <label class="layout"><%=OrgCampione.getUo() != null ? OrgCampione.getUo() : ""  %></label><br/>
PEC <label class="layout"><%=Modulo.getListaCampiModulo().get("mail_dipartimento").get("mail_dipartimento") %></label><br/>
COD UTENTE SIGLA <label class="layout"> <%if (Modulo.getListaCampiModulo().get("cod_utente_sigla")!=null) { %> <%=Modulo.getListaCampiModulo().get("cod_utente_sigla").get("cod_utente_sigla") %> <%} %></label>
</td>

<td style="border:1px solid black;">
<b><%=(Modulo.getTipoModulo()==1) ? "MOD. 1 MOLLUSCHI" : (Modulo.getTipoModulo()==2) ? "MOD. 2" : (Modulo.getTipoModulo()==3) ? "MOD. 3" : (Modulo.getTipoModulo()==4) ? "MOD. FISICO" : (Modulo.getTipoModulo()==14) ? "MOD. 14" : "" %></b><br/>
Rev. 8 <br/>
del<br/>
22/4/21
</td>

<td style="border:1px solid black;">
VERBALE<br/>
PRELEVAMENTO<br/>
CAMPIONE <br/>
N.<br/>

<% if(OrgCampione.getCodPreaccettazione() != null && !OrgCampione.getCodPreaccettazione().equals("")){ %>
<% if(request.getParameter("tipo").equals("1")) { 
if (OrgCampione.getBarcodeMolluschiNew()!=null && !OrgCampione.getBarcodeMolluschiNew().equals("")) { %>
	<%=OrgCampione.getBarcodeMolluschiNew()%>
<% } else { %>
	<%=OrgCampione.getBarcodeMolluschi()%>
<%} } else { 
if (OrgCampione.getBarcodePrelievoNew()!=null && !OrgCampione.getBarcodePrelievoNew().equals("")) { %>
	<%=OrgCampione.getBarcodePrelievoNew()%>
	<%} else { %>
	<%=OrgCampione.getBarcodePrelievo()%>
<%} }  %>
<% } else {  %>
<% if(request.getParameter("tipo").equals("1")) { 
if (OrgCampione.getBarcodeMolluschiNew()!=null && !OrgCampione.getBarcodeMolluschiNew().equals("")) { %>
	<img src="<%=createBarcodeImage(OrgCampione.getBarcodeMolluschiNew())%>" />
<% } else { %>
	<img src="<%=createBarcodeImage(OrgCampione.getBarcodeMolluschi())%>" />
<%} } else { 
if (OrgCampione.getBarcodePrelievoNew()!=null && !OrgCampione.getBarcodePrelievoNew().equals("")) { %>
	<img src="<%=createBarcodeImage(OrgCampione.getBarcodePrelievoNew())%>" />
	<%} else { %>
	<img src="<%=createBarcodeImage(OrgCampione.getBarcodePrelievo())%>" />
<%} }  %>
<% } %>
</td>

</tr>
<tr>
<td colspan="4" style="border:1px solid black;">
<b>CAMPIONE PRELEVATO: </b>
[] per sospetto 
[<%=OrgCampione.getMotivazione_campione()!=null && OrgCampione.getMotivazione_campione().equals("89") ? "X" : "" %>] per piano di monitoraggio <label class="layout"><%=OrgCampione.getMotivazione_campione()!=null && OrgCampione.getMotivazione_campione().equals("89") ? toHtml(OrgCampione.getPiano().toUpperCase()) : "" %></label>
[<%=OrgCampione.getMotivazione_campione()!=null && !OrgCampione.getMotivazione_campione().equals("89") ? "X" : "" %>] per attivita' <label class="layout"><%=OrgCampione.getMotivazione_campione()!=null && !OrgCampione.getMotivazione_campione().equals("89") ? toHtml(OrgCampione.getPiano().toUpperCase()) : "" %></label>
</td>
</tr>
</table>
</div>

<div align="right">
<font size="1px">IdCU: <%=OrgCampione.getIdControllo()%> IdCampione:  <%=request.getParameter("ticketId") %></font><br/>
<table align="right" cellpadding="3" style="display:">
<tr><td style="align:center;"><font size="1px">Codice Preaccettazione: </font></td>
<td style="align:center;">
<font size="1px">
<% 	if(OrgCampione.getCodPreaccettazione() != null && !OrgCampione.getCodPreaccettazione().equals("")){ %>
<img align ="middle" src="<%=createBarcodeImage(OrgCampione.getCodPreaccettazione().toUpperCase() )%>" /> 
<% } else { %>
NON DISPONIBILE
<% } %>
</font>
</td>
</tr>
</table>
</div>
<!--  FINE HEADER -->
