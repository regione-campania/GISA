<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.actions.StabilimentoAction"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<jsp:useBean id="StabilimentoSoggettoAScia" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="SuapTipoScia" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />

	
	<script>
var esitoDocumentaleOnline;

function selezionaOperazione(operazione, form){
	
// 	verificaDocumentaleOnline();
// 	if (!esitoDocumentaleOnline)
// 		return false;
	
	document.getElementById("operazioneScelta").value = operazione;
	var isRiconosciuto = '<%=newStabilimento.getTipoInserimentoScia() %>' == '2' ? true : false;
	if(form.dataRichiesta.value=='')
		{
		alert('Inserire la data di Arrivo della pec');
		}
	else
	{
		var arr1 = form.dataRichiesta.value.split("/");
		var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
		var d2 = new Date();
		var r1 = d1.getTime();
		var r2 = d2.getTime();

		
		if (r1>r2) {
			alert("Attenzione, non è possibile inserire una data di arrivo PEC successiva alla data odierna.");
			return
		}
		
		
		
		if(!isRiconosciuto)
		{
			var d1 = new Date(form.dataRichiesta.value);
			var d2 = new Date('01/05/2015');
			if(d1 < d2)
			{
				alert("Attenzione, non è possibile inserire una data di arrivo PEC precedente al 1° Maggio 2015");
				return;
			}
		}
		
		form.submit();
	}
}


function selezionaOperazioneNew(operazione, form){

	
	document.getElementById("operazioneScelta").value = operazione;
	var isRiconosciuto = '<%=newStabilimento.getTipoInserimentoScia() %>' == '2' ? true : false;
	if(form.dataRichiesta.value=='')
		{
		alert('Inserire la data di Arrivo della pec');
		}
	else
	{
		var arr1 = form.dataRichiesta.value.split("/");
		var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
		var d2 = new Date();
		var r1 = d1.getTime();
		var r2 = d2.getTime();

		
		if (r1>r2) {
			alert("Attenzione, non è possibile inserire una data di arrivo PEC successiva alla data odierna.");
			return
		}
		
		
		
		if(!isRiconosciuto)
		{
			var d1 = new Date(form.dataRichiesta.value);
			var d2 = new Date('01/05/2015');
			if(d1 < d2)
			{
				alert("Attenzione, non è possibile inserire una data di arrivo PEC precedente al 1° Maggio 2015");
				return;
			}
		}
		
		
		if (operazione=='variazionemobile')
			form.action = 'CambioSoggettoFisico.do?command=Add';
		form.submit();
	}
}

</script>

<script>

function verificaDocumentaleOnline(){
              $.ajax({
                     type: 'POST',
                   dataType: "json",
                   cache: false,
                  url: 'DocumentaleUtil.do?command=VerificaDocumentaleOnline',
                  async: false,
                  success: function(msg) {
                	 
                 	  if (msg.Esito==true){
                 		 esitoDocumentaleOnline = true;
                 	  }
                 	  else{
                		 /*
                 		  if (!confirm("Attenzione! A causa di un problema momentaneo non sara' possibile allegare documenti a questa richiesta. Procedere comunque?".toUpperCase() ))
                			  esitoDocumentaleOnline = false; 
                		  else
                			  esitoDocumentaleOnline = true;
                		 */
                 		 esitoDocumentaleOnline = true;
                	  }
                      },
                 error: function (err) {
                           alert('ko '+err.responseText);
              }
             });
 }



</script>


<form  name="gestioneScia" action="SuapStab.do?command=Add" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  
<tr>
<th colspan="2">Riepilogo Stabilimento</th>
</tr>

 <tr class="containerBody">
    <td nowrap class="formLabel">
Ragione Sociale</td>
<td><%=StabilimentoSoggettoAScia.getOperatore().getRagioneSociale() %></td>
</tr>

 <tr class="containerBody">
    <td nowrap class="formLabel">Numero Registrazione</td>
<td><%=StabilimentoSoggettoAScia.getNumero_registrazione() %></td>
</tr>

 <tr class="containerBody">
    <td nowrap class="formLabel">Comune Sede Operativa</td>
<td><%=StabilimentoSoggettoAScia.getSedeOperativa().getDescrizioneComune() %></td>
</tr>



 
<tr class="containerBody">
   
   <td nowrap class="formLabel">
        Data Arrivo Pec da SUAP/Camera di Commercio
      </td>
      <td>
    
    <input type = "text" name="dataRichiesta" readonly="readonly" id = "dataRichiesta"  >
      	<a href="#" onClick="cal19.select(document.getElementById('dataRichiesta'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      
    
<!--        <input type="text" size="15" name="dataRichiesta" readonly="readonly" -->
<!-- 					id="dataRichiesta" required="required" placeholder="dd/MM/YYYY"> -->
					      	
      
     
      </td>
   
</tr>

</table>

        <input type="hidden" name="idStabilimentoOpu" id="idStabilimentoOpu" value="<%=StabilimentoSoggettoAScia.getIdStabilimento()%>"> 

        <input type="hidden" name="stato" id="stato" value="0"> 
        <input type="hidden" name="tipoInserimentoScia" id="tipoInserimentoScia" value="<%=newStabilimento.getTipoInserimentoScia()%>"> 
        <input type="hidden" name="operazioneScelta" id="operazioneScelta" value="">
        <input type="hidden" name="methodRequest" id="methodRequest" value="new">
        <input type="hidden" name="fissa" id="fissa" value="<%=StabilimentoSoggettoAScia.getTipoAttivita()==Stabilimento.ATTIVITA_MOBILE ? "false" : "true" %>">
       
        <div style="display: none;"> 
            &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
        </div>
        
        <fieldset>
        		
                <legend><h3></h3> </legend>
				<%if((StabilimentoSoggettoAScia.getTipoAttivita()==Stabilimento.ATTIVITA_FISSA)) { %> 
<%@ include file="bottoni_scia_fissa_gestione_breve.jsp"%>
				<% 
					} else 
					
						{
					%>
					
					
					<%@ include file="bottoni_scia_mobili_gestione_breve.jsp"%>
				<% } %>
				
				
              
       </fieldset>
         
</form>



