<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="listaEventi" class="org.aspcfs.modules.registrazioniAnimali.base.EventoList" scope="request"/>
<jsp:useBean id="registrazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
<jsp:useBean id="cane" class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request"/>
<jsp:useBean id="gatto" class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request"/>
<jsp:useBean id="wkf" class="org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF" scope="request" />
<jsp:useBean id="praticacontributi"	class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script type="text/javascript">
function openDelete(registrazioneId, tipologiaRegistrazioneId, animaleId){
	var res;
	var result;
	if ((tipologiaRegistrazioneId == <%=EventoSterilizzazione.idTipologiaDB%>) && <%=animaleDettaglio.getIdPraticaContributi()>0%>){
		if(confirm('Attenzione. Stai cancellando un evento sterilizzazione con una richiesta di contributo. Proseguendo verranno incrementati gli animali restanti nella pratica associata (nr decreto: '+<%=praticacontributi.getNumeroDecretoPratica()%>+')\n Sicuro di continuare?'))
			window.open('RegistrazioniAnimale.do?command=PrepareDeleteRegistrazione&registrazioneId='+registrazioneId+'&animaleId='+animaleId+'&tipologiaRegistrazioneId='+tipologiaRegistrazioneId,'popupSelect',
			'height=410px,width=410px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
	else
		window.open('RegistrazioniAnimale.do?command=PrepareDeleteRegistrazione&registrazioneId='+registrazioneId+'&animaleId='+animaleId+'&tipologiaRegistrazioneId='+tipologiaRegistrazioneId,'popupSelect',
		'height=410px,width=410px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
</script> 


<!--  CERCO L'ULTIMO EVENTO INSERITO NELLA LISTA (E' L'UNICO A POTER ESSERE CANCELLATO) -->
  <%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int ultimaRegistrazione =-1;
    long dataUltimaRegistrazione =-1;
    long enteredUltimaRegistrazione =-1;
    Iterator k = listaEventi.iterator();
    if ( k.hasNext() ) {
     // int rowid = 0;
      int i =0;
      while (k.hasNext()) {
        i++;
      //  rowid = (rowid != 1?1:2);
        Evento thisEvento = (Evento)k.next();
        long dataThisRegistrazione = (thisEvento.getDataRegistrazione()!=null)?(thisEvento.getDataRegistrazione().getTime()):(thisEvento.getEntered().getTime());
        Date dataThisRegistrazioneDate = new Date(dataThisRegistrazione);
        Date dataThisRegistrazioneDate2 = sdf.parse(sdf.format(dataThisRegistrazioneDate));
        dataThisRegistrazione = dataThisRegistrazioneDate2.getTime();
        
        long enteredThisRegistrazione = thisEvento.getEntered().getTime();
        //System.out.println(thisEvento.getIdEvento() + ". dataThisRegistrazione: " + dataThisRegistrazione);
        //System.out.println(thisEvento.getIdEvento() + ". enteredThisRegistrazione: " + enteredThisRegistrazione);
        //System.out.println(thisEvento.getIdEvento() + ". dataUltimaRegistrazione: " + dataUltimaRegistrazione);
        //System.out.println(thisEvento.getIdEvento() + ". enteredUltimaRegistrazione: " + enteredUltimaRegistrazione);
        if (dataThisRegistrazione>dataUltimaRegistrazione || (dataThisRegistrazione==dataUltimaRegistrazione && enteredThisRegistrazione>enteredUltimaRegistrazione))
        {
        	dataUltimaRegistrazione=dataThisRegistrazione;
        	enteredUltimaRegistrazione=enteredThisRegistrazione;
        	ultimaRegistrazione=thisEvento.getIdEvento();
        }
      }}
  %>


<% String param1 = "idAnimale=" + animaleDettaglio.getIdAnimale() +"&idSpecie=" + animaleDettaglio.getIdSpecie();
   %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="AnimaleAction.do?command=Search"><dhv:label name="">Ricerca animali</dhv:label></a> > 
<a href="AnimaleAction.do?command=Details&animaleId=<%=animaleDettaglio.getIdAnimale()%>&idSpecie=<%=animaleDettaglio.getIdSpecie()%>"><dhv:label name="">Dettagli animale</dhv:label></a> >
<dhv:label name="">Registrazioni</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="animale" selected="Registrazioni" object="animaleDettaglio" param="<%=param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>

    <br>
    
    <dhv:permission name="anagrafe_canina_registrazioni-add">
    <dhv:evaluate
			if="<%=!(animaleDettaglio.getProprietario().checkModificaResidenzaStabilimento())%>">
	<dhv:evaluate if="<%=animaleDettaglio.checkAslAnimaleUtenteOrRoleHd(User) %>">
		<dhv:evaluate if="<%=wkf.isFlagPossibilitaRegistazioni() %>">
		<a href="RegistrazioniAnimale.do?command=Add&idAnimale=<%=animaleDettaglio.getIdAnimale() %>">Aggiungi registrazione</a>
		</dhv:evaluate>
	</dhv:evaluate>
	</dhv:evaluate>
</dhv:permission>
    

  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <!--  th>
        &nbsp;
      </th-->
      <th width="30%" valign="center" align="left">
        <strong>Numero</strong>
      </th>
      <th><b><dhv:label name="">Categoria</dhv:label></b></th>
     <th><b><dhv:label name="">Data inserimento in BDU</dhv:label></b></th>
     <th><b><dhv:label name="">Data della registrazione</dhv:label></b></th>
      <th width="15%">
        <b><dhv:label name="">Proprietario/Detentore</dhv:label></b>
      </th>
      <th width="20%">
        <b><dhv:label name="">Animale</dhv:label></b>
      </th>
      <th width="30%">
       <b><dhv:label name="">Destinazione (in caso di cessione, trasferimento, etc) <br> Provenienza (in caso di rientro da fuori regione/fuori stato, etc) </dhv:label></b>
      </th>
      <th><b><dhv:label name="">Asl di Riferimento</dhv:label></b></th>
      
       <dhv:evaluate if="<%=User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))%>">
 	<th><b><dhv:label name="">HelpDesk</dhv:label></b></th></dhv:evaluate>
        
    </tr>
  <%
    Iterator j = listaEventi.iterator();
    if ( j.hasNext() ) {
     // int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
      //  rowid = (rowid != 1?1:2);
        Evento thisEvento = (Evento)j.next();
  %>
    <tr class="">
      <!-- td  nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <a href="javascript:displayMenu('select<%= i %>','menuTic','<%= animaleDettaglio.getIdAnimale() %>','<%= thisEvento.getIdEvento() %>', '<%= false %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td-->
      <td nowrap>
        <a href="RegistrazioniAnimale.do?command=Details&id=<%= thisEvento.getIdEvento() %>&tipologiaEvento=<%= thisEvento.getIdTipologiaEvento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisEvento.getIdEvento() %></a>
      </td>
      <td  nowrap>
	   	<%= toHtml(registrazioniList.getSelectedValue(thisEvento.getIdTipologiaEvento())) + ((thisEvento.isFlagRegistrazioneForzata()) ? "<font color=\"red\"> (INSERIMENTO A POSTERIORI)</color>" : "") %>
	  </td>
	  <td >
      	<%=toDateasString(thisEvento.getEntered()) %>&nbsp;
      </td>
      	  <td >
      	<%=toDateasString(thisEvento.getDataRegistrazione()) %>&nbsp;
      </td>
 	  <td>
 	  <% 
 	  
 	  Operatore proprietario = thisEvento.getIdProprietarioOriginarioRegistrazione();
 	  if (proprietario != null) { 
        Stabilimento stab = (Stabilimento) (proprietario.getListaStabilimenti().get(0));
        LineaProduttiva linea = (LineaProduttiva)( stab.getListaLineeProduttive().get(0));
 	  %>
 	  
 	  <a href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=linea.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(proprietario.getRagioneSociale()) %></a>
 	  
 	  
			<!-- a href="LineaProduttivaAction.do?command=Details&lineaId=<%= linea.getId() %>"><%= toHtml(proprietario.getRagioneSociale()) %></a-->
	<%}else{ %>
	--
	<%} %>/
	  <% 
	  Operatore detentore = thisEvento.getIdDetentoreOriginarioRegistrazione();
	  if (detentore != null) { 
	        Stabilimento stab1 = (Stabilimento) (detentore.getListaStabilimenti().get(0));
	        LineaProduttiva linea1 = (LineaProduttiva)( stab1.getListaLineeProduttive().get(0));
	  %>
	  
	   <a href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=linea1.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(detentore.getRagioneSociale()) %>&nbsp;</a>
		<!-- a href="LineaProduttivaAction.do?command=Details&lineaId=<%= linea1.getId() %>"><%= toHtml(detentore.getRagioneSociale()) %>&nbsp;</a-->
	 <%}
	  else{ %>  
	--
	 <%} %>
	  </td>
	  <td>
	    
         <a href="AnimaleAction.do?command=Details&animaleId=<%=animaleDettaglio.getIdAnimale() %>&idSpecie=<%=animaleDettaglio.getIdSpecie() %>"> <%= thisEvento.getMicrochip() %></a>

       
	   </td>
  	<td>
  	 <% 
 	  
 	  Operatore proprietarioDest = thisEvento.getIdProprietarioDestinatarioRegistrazione();
 	  if (proprietarioDest != null) { 
        Stabilimento stabprpDest = (Stabilimento) (proprietarioDest.getListaStabilimenti().get(0));
         LineaProduttiva lineaDeststa = (LineaProduttiva)( stabprpDest.getListaLineeProduttive().get(0));
 	  %><a href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=lineaDeststa.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(proprietarioDest.getRagioneSociale()) %>&nbsp;</a>
		
	 <%}
	  else{ %>  
	<%=thisEvento.getDestinazione() %>
	 <%} %>
	  
  	
  	</td>
  	<td>
  	<%=AslList.getSelectedValue(thisEvento.getIdAslRiferimento()) %>
  	</td>
  	
  	<!-- La condizione  || thisEvento.getIdAnimale()==6707506 è stata aggiunta per permettere di far uscire il tasto cancella quando le precedenti condizioni non lo consentono
  	     E' stato pensato per l'hd di II livello per risolvere i tt in maniera veloce -->
  	<dhv:evaluate if="<%=User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))%>"> 
  		<dhv:evaluate if="<%=thisEvento.getIdStatoOriginale()>0%>">
  			<dhv:evaluate if="<%=(thisEvento.getIdTipologiaEvento() != EventoRegistrazioneBDU.idTipologiaDB 
  									&& thisEvento.getIdTipologiaEvento() != EventoInserimentoMicrochip.idTipologiaDB  
  									&& thisEvento.getIdTipologiaEvento() != EventoCattura.idTipologiaDB
  									&& !(thisEvento.getIdTipologiaEvento()== EventoSterilizzazione.idTipologiaDB && DwrUtil.checkContributo(animaleDettaglio.getMicrochip())==true)
  									&& thisEvento.getIdEvento()==ultimaRegistrazione ) || thisEvento.getIdAnimale()==6707506
  									//|| thisEvento.getIdTipologiaEvento()== EventoDecesso.idTipologiaDB
  									%>"> 
  									<!--  SE E' UN UTENTE HD E LA REGISTRAZIONE E' ELIMINABILE ED E' L'ULTIMA INSERITA (O E' UN DECESSO) ED HA UNO STATO PRECEDENTE-->
	<td>
	
	<a href="#" onclick="openDelete('<%=thisEvento.getIdEvento()%>','<%=thisEvento.getIdTipologiaEvento()%>','<%=animaleDettaglio.getIdAnimale()%>');"
				id="" target="_self">Cancella</a>
	
  	 <!-- <a href="AnimaleAction.do?command=PrepareDeleteRegistrazione&registrazioneId=<%= thisEvento.getIdEvento() %>&animaleId=<%=animaleDettaglio.getIdAnimale() %>" target="_blank">Cancella</a> -->
	</td>
  	</dhv:evaluate></dhv:evaluate></dhv:evaluate>
  	
     </tr>

  <%}%>
  <%} else {%>
    <tr class="containerBody">
      <td colspan="9">
        <dhv:label name="">Nessuna registrazione individuata.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
	<br>
  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->
</dhv:container>