<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.campioni.actions.Campioni"%>
<%@page import="org.aspcfs.modules.campioni.actions.CampioniAssociazioneEsiti"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.CampioneAssociazioneEsiti"%>
<%@page import="org.aspcfs.modules.mycfs.actions.MatriciPiani"%>
<%@ include file="../initPage.jsp"%>
<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<%
	String data = (request.getAttribute("data")==null ? ("") : (String)request.getAttribute("data"));
	String matricola = (request.getAttribute("matricola")==null ? ("") : (String)request.getAttribute("matricola"));
%>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MyCFS.do?command=Home">
				<dhv:label name="My Home Page" mainMenuItem="true">La Mia Home Page</dhv:label></a> >
				<a href="CampioniAssociazioneEsiti.do?command=ToRicerca&amp;matricola=<%=matricola%>&amp;data=<%=data%>"><dhv:label name="">Associazione Esiti-Campioni</dhv:label></a> >
				<dhv:label name="">Lista Campioni</dhv:label>
		</td>
	</tr>
</table>

<%@page import="org.aspcfs.utils.DateUtils"%>
<jsp:useBean id="EsitiCampioni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"				scope="request" />



<form action="CampioniAssociazioneEsiti.do?command=Associa" name="main" method="post" onsubmit="return controllaForm();">

<input type="hidden" name="matricola" id="matricola" value="<%=matricola%>" />
<input type="hidden" name="data" id="data" value="<%=data%>" />

<%String errore = (String)request.getAttribute( "Error2" ); %>
<%=(errore == null) ? ("") : ("<font color=\"red\">" + errore+"</font><br/>") %>

<table class="details" width="100%" border="0" cellpadding="5" cellspacing="0">
	<tr>
    	<th colspan="11">Campioni</th>
    </tr>
    <tr>
   		<th>Tipo macellazione</th> 
		<th>Numero matricola/partita</th> 
		<th>Numero seduta</th> 
		<th>Data Macellazione</th> 
		<th>Impresa</th> 
		<th>Motivo campione</th> 
		<th>Matrice</th> 
		<th>Tipo analisi</th> 
		<th>Molecole/a agente etiologico</th> 
		<th>Esito</th> 
		<th>Data Esito</th> 
    </tr>
    			
<%	for( int index = 1; index <= Campioni.size(); index++ )
	{ 
		CampioneAssociazioneEsiti campione = (Campioni.size() >= index) ? ((CampioneAssociazioneEsiti)Campioni.get( index -1 )) : (new CampioneAssociazioneEsiti());
%>
    			
    	<tr class="containerBody">
    		<td>
    			<%=campione.getTipoMacellazione()%>&nbsp;
    			<input type="hidden" name="id_campione_<%=index%>" id="id_campione_<%=index%>" value="<%=campione.getId()%>" />
            </td>
                	
    		<td>
    			<%=campione.getMatricola()%>&nbsp;
    		</td>
            <td>
    			<%=toHtml(campione.getNumeroMacellazione())%>&nbsp;
    		</td>
    		<td>
    			<%=toHtml(toDateasString(campione.getDataMacellazione()))%>&nbsp;
    		</td>
    		<td>
    			<%=campione.getImpresa()%>&nbsp;
    		</td>
    		<td>
    			<%=toHtml((campione.getMotivo()==null)?(""):(campione.getMotivo()))%>&nbsp;
    		</td>
    		<td>
    			<%=toHtml((campione.getMatriceDesc()==null)?(""):(campione.getMatriceDesc()))%>&nbsp;
    		</td>
    		<td>
    			<%=toHtml((campione.getTipoAnalisi()==null)?(""):(campione.getTipoAnalisi()))%>&nbsp;
    		</td>
    		<td border="1">
    			<%=toHtml((campione.getMolecole()==null)?("" + campione.getNoteMolecole() ):(campione.getMolecole() + campione.getNoteMolecole() ))%>&nbsp;
    		</td>
           	<td>
           		<%=EsitiCampioni.getHtmlSelect( "cmp_id_esito_" + index, -1 )%>&nbsp;
			</td>
    		<td>
	   			<input readonly type="text" name="cmp_data_ricezione_esito_<%=index%>" id="cmp_data_ricezione_esito_<%=index%>" size="10" value="<%=DateUtils.timestamp2string(campione.getData_ricezione_esito())%>" />&nbsp;  
				<a href="#" onClick="cal19.select(document.forms[0].cmp_data_ricezione_esito_<%=index%>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>  
				<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cmp_data_ricezione_esito_<%=index%>);"><img src="images/delete.gif" align="absmiddle"/></a>
         	</td>
    	</tr>
    			
<%
} 
%>
    			
</table>

<input type="submit" name="associa" value="Associa" id="associa">
</form>

<script type="text/javascript">
function controllaForm( )
	{
		var form = document.main;
		
		var i=1;
		var selezionato = false;
		while( document.getElementById('cmp_id_esito_'+i)!=null)
		{
			if(document.getElementById('cmp_id_esito_'+i).value!='-1')
			{	
				selezionato=true;
			}
			if(document.getElementById('cmp_id_esito_'+i).value=="-1" && document.getElementById('cmp_data_ricezione_esito_'+i).value!='')
			{	
				alert("Impossibile valorizzare data senza esito" );
				return false;
			}
			i++;
		}
		if(!selezionato)
		{
			alert("Valorizzare almeno un esito campione" );
			return false;	
		}
		
		return true;
	}
	
function svuotaData(input){
	input.value = '';
	gestisciObbligatorietaComunicazioniEsterne();
	gestisciObbligatorietaMorteAnteMacellazione();
	gestisciObbligatorietaVisitaAnteMortem();
	gestisciObbligatorietaVisitaPostMortem();
}
</script>