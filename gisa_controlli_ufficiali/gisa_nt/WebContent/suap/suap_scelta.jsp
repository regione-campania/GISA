<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.actions.StabilimentoAction"%>
<%@page import="org.aspcfs.modules.suap.base.Stabilimento"%>
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />
<jsp:useBean id="fissa" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<jsp:useBean id="SuapTipoScia" class="org.aspcfs.utils.web.LookupList" scope="request" />

<link rel="stylesheet" href="css/jquery-ui.css" />


<%@ include file="../initPage.jsp" %>



<script>
var esitoDocumentaleOnline;

function selezionaOperazione(operazione, form){
	
	verificaDocumentaleOnline();
	if (!esitoDocumentaleOnline)
		return false;
	
	document.getElementById("operazioneScelta").value = operazione;
	form.submit();
	
}


</script>

<% 
String contesto = (String) application.getAttribute("SUFFISSO_TAB_ACCESSI");
	if (contesto!=null && !contesto.equals("") && contesto.equals("_ext")){
			
	}else{
%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
<a href="InterfValidazioneRichieste.do?command=MostraRichiesteDaValutare">PRATICHE APICOLTURA</a> >
Nuovo stabilimento
<!-- 
<a href="GisaSuapStab.do?command=SearchForm"><dhv:label name="">Pratiche SUAP</dhv:label></a> >
<a href="GisaSuapStab.do?command=Default"><dhv:label name=""> Pratiche SUAP > Nuovo stabilimento</dhv:label></a> 
-->
</td>
</tr>
</table>
<%}%>



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
                		 /* if (!confirm("Attenzione! A causa di un problema momentaneo non sara' possibile allegare documenti a questa richiesta. Procedere comunque?".toUpperCase()))
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


<%--
<div  class="highlight" style="font-weight: bold; font-size: 12px;">
 <center>
In questa sezione puoi selezionare la tipologia di <%=newStabilimento.getTipoInserimentoScia() == newStabilimento.TIPO_SCIA_RICONOSCIUTI ? "operazione " : "SCIA " %>da eseguire. Per ogni azione, il sistema ti propone un bottone richiamando il paragrafo della 
<a href="#" onClick="window.open('http://www.gisacampania.it/manualisuap/Allegato%20D.G.R.C.%20318.2015.pdf')"><img src="gestione_documenti/images/pdf_icon.png" width="25px"/> Delibera 318/15&nbsp;&nbsp;</a>che la descrive.
 </center>
</div>
<hr/>
--%>
<br>
<div><center><fieldset style="display: inline-block"><legend>Comune di:</legend><font size="3px" style="font-weight : bold"><%=User.getUserRecord().getSuap().getDescrizioneComune()%></font></fieldset></center></div>
<br>
<br>
 
<form id="example-advanced-form" name="addstabilimento" action="SuapStab.do?command=Add" method="post">
        <input type="hidden" name="stato" id="stato" value="<%=newStabilimento.getStato()%>"> 
        <input type="hidden" name="tipoInserimentoScia" id="tipoInserimentoScia" value="<%=newStabilimento.getTipoInserimentoScia()%>"> 
        <input type="hidden" name="operazioneScelta" id="operazioneScelta" value="">
        <input type="hidden" name="methodRequest" id="methodRequest" value="new">
        <input type="hidden" name="fissa" id="fissa" value="<%=fissa%>">
        <input type = "hidden" name="idLineaProduttiva" id = "idLineaPRoduttiva" value = "<%=request.getAttribute("IdLineaProduttiva") %>">
        <input type = "hidden" name="dataRichiesta" value = "<%= toDateasString(newStabilimento.getDataRichiestaSciaAsl()) %>">
        <div style="display: none;"> 
            &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
        </div>
      
       <!--  <fieldset> -->
                <!-- <legend>INDICARE IL TIPO DI OPERAZIONE CHE SI VUOLE ESEGUIRE</legend>-->
				<%if((fissa!= null && fissa.equals("true") || newStabilimento.getTipoInserimentoScia() == newStabilimento.TIPO_SCIA_RICONOSCIUTI)) { %> 
<%@ include file="bottoni_scia_fissa.jsp"%>
				<% 
					} else 
					
						if((fissa!= null  && fissa.equals("api") )) {
							
							%>
							
							<%@ include file="bottoni_scia_apicoltura.jsp"%>
							<%
						}
						else
						{
					%>
					
					
					<%@ include file="bottoni_scia_mobili.jsp"%>
				<% } %>
              
       <!-- </fieldset> -->
         
</form>


