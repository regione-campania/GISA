<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita" %>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva " %>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttivaList" %>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="org" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>

<%@ include file="../initPage.jsp"%>

<style>
.td {
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #fff;
	padding: 6px 6px 6px 12px;
	color: #6D929B;
}

#progress {
	position: relative;
	width: 400px;
	border: 1px solid #ddd;
	padding: 1px;
	border-radius: 3px;
}

#bar {
	background-color: #B4F5B4;
	width: 0%;
	height: 20px;
	border-radius: 3px;
}

#percent {
	position: absolute;
	display: inline-block;
	top: 3px;
	left: 48%;
}
input[readonly]
{
    background-color:grey;
}

 table.one {border-collapse:collapse;}
 td.b {
            border-style:solid;
            border-width:1px;
            border-color:#333333;
            padding:10px;
         }
         
 
</style>


	<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/SuapDwr.js"> </script>
<SCRIPT src="javascript/suapCittadinoUtil.js"></SCRIPT>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<link rel="stylesheet" href="css/opu.css" />
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script src="javascript/jquery.form.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal99 = new CalendarPopup();
	cal99.showYearNavigation();
	cal99.showYearNavigationInput();
	cal99.showNavigationDropdowns();
</SCRIPT>
<script src="javascript/aggiuntaCampiEstesiScia.js"> </script>
<script src="opu/rankbased_lineepregresse/candidatiRankUtils.js"></script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
	 
	<div style="display: none;"> 
    &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
	</div>
	 
<script> function checkForm(tot, form){
	/*var x = document.getElementsByName("idLineaProduttiva");
	var totModificati = x.length;*/
	
	console.log($("input#validatelp").val());
	
	if ($("input#validatelp").val() == 'false' /*totModificati!=parseInt(tot-1)*/)
		{
			alert('Selezionare la nuova linea per ogni vecchia linea di attività!');
			return false;
		}
	
	
	
	
	var i=0;
	while(document.getElementById('vecchiaLineaDataInizio' + i)!=null)
	{
		var dataInizio = document.getElementById('vecchiaLineaDataInizio' + i).value;
		var dataFine = document.getElementById('vecchiaLineaDataFine' + i).value;
		if(dataInizio!=null && dataInizio!='' && dataFine!=null && dataInizio!='' )
		{
			var arr1 = dataInizio.split("/");
			var arr2 = dataFine.split("/");
			var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
			var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
			var r1 = d1.getTime();
			var r2 = d2.getTime();
			if (r1 >= r2) 
			{
				  var riga = i+1; 
				  alert("La data fine attività non può essere antecedente alla data inizio attività per la linea numero " +  riga );
				  return false;
			}
		}
		i++;
	}
	
	
	
	
		loadModalWindow();
		//form.submit();
		return true;
	
}
</script>


	
	<dhv:evaluate if="<%=(StabilimentoDettaglio.getListaLineeProduttive().size() > 0)%>">
	
	<%@include file="/opu/rankbased_lineepregresse/creazione_candidati.jsp" %>
	
	
	<table width="100%" style="border:1px solid black">
	<tr><th><%=(StabilimentoDettaglio.getName()!=null)?(StabilimentoDettaglio.getName()):(org.getName()) %></th></tr>
	<tr><td>
	
<form name="searchAccount" id = "searchAccount" action="OpuStab.do?command=UpdateLineePregresse" method="post"
	onsubmit="return checkForm(window.totaleLineeDaAggiornare, this)"
>	
	
  <input type="hidden" id="tipoAttivita" name ="tipoAttivita" value="<%=(StabilimentoDettaglio.getTipoAttivita()>0)?(StabilimentoDettaglio.getTipoAttivita()):(request.getAttribute("tipoAttivita"))%>"/>
  <input type="hidden" id="commit" name ="commit" value="<%=request.getAttribute("commit")%>"/>
	
	<%
		Iterator<LineaProduttiva> itLplist = StabilimentoDettaglio.getListaLineeProduttive().iterator();
				int indice = 1;
				while (itLplist.hasNext()) {
					LineaProduttiva lp = itLplist.next();
	%>
	<input type="hidden" id="vecchiaLineaId<%=indice-1 %>" name ="vecchiaLineaId<%=indice-1 %>" value="<%=lp.getId()%>"/>
	<input type="hidden" id="vecchiaLineaIdRel<%=indice-1 %>" name ="vecchiaLineaIdRel<%=indice-1 %>" value="<%=lp.getId_rel_stab_lp()%>"/>
	<input type="hidden" id="vecchiaLineaStato<%=indice-1 %>" name ="vecchiaLineaStato<%=indice-1 %>" value="<%=lp.getStato()%>"/>
	<input type="hidden" id="vecchiaLineaCodice<%=indice-1 %>" name ="vecchiaLineaCodice<%=indice-1 %>" value="<%=lp.getCodice_ufficiale_esistente()%>"/>
	<input type="hidden" id="vecchiaLineaNumero<%=indice-1 %>" name ="vecchiaLineaNumero<%=indice-1 %>" value="<%=lp.getNumeroRegistrazione()%>"/>
	
	<table width="100%">
	<col width="50%">
	<tr><td valign="top">
	
			<table width="100%" class="details">
			<tr><th>Vecchia Linea <%=indice %></th></tr>
			<%	
					String lineaCompleta = lp.getDescrizione_linea_attivita();
					String[] linea = lineaCompleta.split("->");
					for (int i = 0; i< linea.length; i++){%>
							<tr><td><%=linea[i] %> </td></tr>
					<%} %>
			</table>
	
	</td><td valign="top">

			 <!-- LINEA ATTIVITA -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">    
    <tr> <th colspan="2"><strong><dhv:label name="">Nuova Linea <%=indice %></dhv:label></strong> </th> </tr>
        
        <!-- PARTE CANDIDATI -->
       <%@include file="/opu/rankbased_lineepregresse/tr_conranktable_princ.jsp" %>
        
        <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Linea</dhv:label>
   			 </td> 
    		<td> 
   
		<input type ="hidden" value = "false" id = "validatelp" value = "false">
		<table id = "attprincipale<%=indice %>" style="width: 100%;"></table>


			</td>
  		</tr>
  		
  		<tr>
        	<td nowrap class="formLabel" >
     		 	Data inizio
   			 </td> 
    		<td> 
<%
  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String data1 = "";
	if(lp.getDataInizio()!=null)
		data1 = sdf.format(lp.getDataInizio());
%>
   				<input type="text" id="vecchiaLineaDataInizio<%=indice-1 %>" name ="vecchiaLineaDataInizio<%=indice-1 %>" value="<%=data1%>" required class="readonly"/>
   				<script> $(function() {	$('#vecchiaLineaDataInizio<%=indice-1%>').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg' }); }); </script>
			</td>
  		</tr>
  		
  		<tr>
        	<td nowrap class="formLabel" >
     		 	Data fine
   			 </td> 
    		<td> 
<%
	String data2 = "";
	if(lp.getDataFine()!=null)
		data2 = sdf.format(lp.getDataFine());
%>
   				<input type="text" id="vecchiaLineaDataFine<%=indice-1 %>" name ="vecchiaLineaDataFine<%=indice-1 %>" value="<%=data2%>"  class="readonly"/>
				<script> $(function() {	$('#vecchiaLineaDataFine<%=indice-1%>').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg' }); }); </script>
			</td>
  		</tr>
	
 </table>
 </td></tr>
			
			
	</table><br/>	
			
			
			<%
			indice++;
				}
				
				
				if(request.getAttribute("linee_attivita")!=null)
				{
				Iterator<LineeAttivita> itLplist2 = ((ArrayList<LineeAttivita>) request.getAttribute("linee_attivita")).iterator();
				indice = 1;
				while (itLplist2.hasNext()) {
					LineeAttivita lp = itLplist2.next();
	%>
	<input type="hidden" id="vecchiaLineaId<%=indice-1 %>" name ="vecchiaLineaId<%=indice-1 %>" value="<%=lp.getId()%>"/>
	<input type="hidden" id="vecchiaLineaIdRel<%=indice-1 %>" name ="vecchiaLineaIdRel<%=indice-1 %>" value="-1>"/>
	<input type="hidden" id="vecchiaLineaStato<%=indice-1 %>" name ="vecchiaLineaStato<%=indice-1 %>" value=""/>
	<input type="hidden" id="vecchiaLineaCodice<%=indice-1 %>" name ="vecchiaLineaCodice<%=indice-1 %>" value=""/>
	<input type="hidden" id="vecchiaLineaNumero<%=indice-1 %>" name ="vecchiaLineaNumero<%=indice-1 %>" value=""/>
	
	<table width="100%">
	<col width="50%">
	<tr><td valign="top">
	
			<table width="100%" class="details">
			<tr><th>Vecchia Linea <%=indice %></th></tr>
			<tr><td><%=lp.getMacroarea()%> </td></tr>
			<tr><td><%=lp.getAggregazione()%> </td></tr>
			<tr><td><%=lp.getAttivita()%> </td></tr>
			</table>
	
	</td><td valign="top">

			 <!-- LINEA ATTIVITA -->
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">    
    <tr> <th colspan="2"><strong><dhv:label name="">Nuova Linea <%=indice %></dhv:label></strong> </th> </tr>
        
        <!-- PARTE CANDIDATI -->
       <%@include file="/opu/rankbased_lineepregresse/tr_conranktable_princ.jsp" %>
        
        <tr>
        	<td nowrap class="formLabel" >
     		 <dhv:label name="">Linea</dhv:label>
   			 </td> 
    		<td> 
   
		<input type ="hidden" value = "false" id = "validatelp" value = "false">
		<table id = "attprincipale<%=indice %>" style="width: 100%;"></table>


			</td>
  		</tr>
  		<tr>
        	<td nowrap class="formLabel" >
     		 	Data inizio
   			 </td> 
    		<td> 
				<input type="text" id="vecchiaLineaDataInizio<%=indice-1 %>" name ="vecchiaLineaDataInizio<%=indice-1 %>" value="" required  class="readonly"/>
				<script> $(function() {	$('#vecchiaLineaDataInizio<%=indice-1%>').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: "+0m",  showOnFocus: false, showTrigger: '#calImg' }); }); </script>
			</td>
  		</tr>
  		<tr>
  			<td nowrap class="formLabel" >
     		 	Data fine
   			 </td> 
    		<td> 
				<input type="text" id="vecchiaLineaDataFine<%=indice-1 %>" name ="vecchiaLineaDataFine<%=indice-1 %>" value=""  class="readonly"/>
				<script> $(function() {	$('#vecchiaLineaDataFine<%=indice-1%>').datepick({dateFormat: 'dd/mm/yyyy',   showOnFocus: false, showTrigger: '#calImg' }); }); </script>
			</td>
  		</tr>
 </table>
 </td></tr>
			
			
	</table><br/>	
			
			
			<%
			indice++;
				}
				}
				
			%>
<input type="hidden" name = "tipo_impresa" id = "tipo_impresa" value = "<%=StabilimentoDettaglio.getOperatore().getTipo_impresa() %>">
<!-- qui salvo il totale linee da inserire -->			
<input type="hidden" name="numeroLinee" value="<%=indice-1 %>" />
			
<%for (int j = 1; j<=indice; j++){ %>

<script>mostraAttivitaProduttive('attprincipale<%=j %>',1,-1, false,-1);</script>

<%} %>			
			
<script>
	window.totaleLineeDaAggiornare = <%=indice %>;
</script>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>		

<center>
<input class="yellowBigButton" type="submit" value="AGGIORNA LINEE" />
<input class="yellowBigButton" type="button" value="Annulla" onclick="window.close()"/>
</center>	
			
</form>			
 
			
	</td></tr>		
	</dhv:evaluate>
	 
	
<script>
    $(".readonly").keydown(function(e){
        e.preventDefault();
    });
</script>












  	
   