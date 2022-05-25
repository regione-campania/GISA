<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<%@ page import="java.text.SimpleDateFormat" %>

<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Relazione" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="NumBox" class="java.lang.String" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>
<%@ page import="org.aspcfs.modules.opu.base.Stabilimento" %>

<%@ page import="org.aspcfs.modules.gestioneml.base.*" %>

<%@ include file="../../initPage.jsp" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script>
function listaOperatori(relid){
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=ListaOperatoriMercatoLinea&idRelazione="+relid;
}

function checkForm(form){
	var ragioneSociale = form.ragioneSociale.value.trim();
	var partitaIva = form.partitaIva.value.trim();
	var numRegistrazione = form.numRegistrazione.value.trim();
	
	if (ragioneSociale == '' && partitaIva == '' && numRegistrazione == ''){
		alert('Inserire almeno un elemento di ricerca.');
		return false;
	}
	
	if (ragioneSociale != '' && ragioneSociale.length<5 ){
		alert('Inserire almeno 5 caratteri per la ricerca per Ragione Sociale.');
		return false;
	}
	if (partitaIva != '' && partitaIva.length<5 ){
		alert('Inserire almeno 5 caratteri per la ricerca per Partita IVA.');
		return false;
	}
	if (numRegistrazione != '' &numRegistrazione && partitaIva.length<10 ){
		alert('Inserire almeno 10 caratteri per la ricerca per Numero Registrazione.');
		return false;
	}	
	loadModalWindow();
	form.submit();
}

function insertForm(form){
	form.action="GestioneAnagraficaAction.do?command=Choose";
	loadModalWindow();
	form.submit();
}

</script>


<center>
<i>Operatori associati al Mercato:</i><br/>
<b><%=Relazione.getPathCompleto().replace("->", "-><br/>") %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b><br/>
<i>all'indirizzo:</i> <br/>
<b><%=Stabilimento.getIndirizzo().getDescrizioneToponimo() %> <%=Stabilimento.getIndirizzo().getVia() %> <%=(Stabilimento.getIndirizzo().getCivico()!=null) ? ", "+Stabilimento.getIndirizzo().getCivico() : "" %>, <%=Stabilimento.getIndirizzo().getDescrizioneComune() %>, <%=Stabilimento.getIndirizzo().getDescrizione_provincia() %></b>
</center>

<br/><br/>
<center>
<a href="#" onClick="listaOperatori('<%=Relazione.getIdRelazione()%>')">Torna alla lista operatori</a>
</center>
<br/>

<font color="red">Si ricorda che saranno ricercabili solo anagrafiche attive fisse con norma 852.</font><br/>


<form name="searchAccount" action="StabilimentoSintesisMercatoAction.do?command=SearchOperatori" method="post">


      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Rapida Operatore da aggiungere nel box <%=NumBox %></dhv:label></strong> 
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Ragione sociale</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="ragioneSociale" value=""/>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            Partita IVA
          </td>
          <td>
            <input type="text" maxlength="16" size="16" name="partitaIva" value=""/>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Num. registrazione</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="numRegistrazione" value=""/>
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">ASL</dhv:label>
          </td>
          <td>
                  <%= AslList.getHtmlSelect("idAsl",-1) %>
          </td>
        </tr>
         
        <tr>
          <td colspan="2">
            <input type="button" value="RICERCA" onClick="checkForm(this.form)"/>
            <input type="button" style="background:green" value="VOGLIO INSERIRE UN NUOVO OPERATORE" onClick="insertForm(this.form)"/>
            
          </td>
        </tr>
        
                     <input type="hidden" name="idRelazione" value="<%=Relazione.getIdRelazione()%>"/>
                     <input type="hidden" name="numBox" value="<%=NumBox%>"/>
                     <input type="hidden" name="urlRedirectOK" value="StabilimentoSintesisMercatoAction.do?command=InserisciOperatoreMercatoLinea&idRelazione=<%=Relazione.getIdRelazione() %>&numBox=<%=NumBox %>&riferimentoIdNomeTabOperatore=opu_stabilimento&riferimentoIdOperatore="/>
                     <input type="hidden" name="urlRedirectKO" value="StabilimentoSintesisMercatoAction.do?command=ListaOperatoriMercatoLinea&idRelazione=<%=Relazione.getIdRelazione() %>"/>
                     <input type="hidden" name="motivoInserimento" value="Inserimento operatore al mercato"/>
                     <input type="hidden" name="dataPratica" value="<%= new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date())%>"/>
                     
                     <input type="hidden" name="toponimo_stabilimento" value="<%=toHtml(Stabilimento.getIndirizzo().getDescrizioneToponimo())%>"/>
                     <input type="hidden" name="topIdStabilimento" value="<%=Stabilimento.getIndirizzo().getToponimo()%>"/>
                     <input type="hidden" name="via_stabilimento" value="<%=toHtml(Stabilimento.getIndirizzo().getVia())%>"/>
                     <input type="hidden" name="civico_stabilimento" value="<%=toHtml(Stabilimento.getIndirizzo().getCivico())%>"/>
                     <input type="hidden" name="comune_stabilimento" value="<%=toHtml(Stabilimento.getIndirizzo().getDescrizioneComune())%>"/>
                     <input type="hidden" name="comuneIdStabilimento" value="<%=Stabilimento.getIndirizzo().getComune()%>"/>
                     <input type="hidden" name="provincia_stabilimento" value="<%=toHtml(Stabilimento.getIndirizzo().getDescrizione_provincia())%>"/>
                     <input type="hidden" name="provinciaIdStabilimento" value="<%=Stabilimento.getIndirizzo().getProvincia()%>"/>
                     <input type="hidden" name="cap_stabilimento" value="<%=toHtml(Stabilimento.getIndirizzo().getCap())%>"/>
               
</form>



