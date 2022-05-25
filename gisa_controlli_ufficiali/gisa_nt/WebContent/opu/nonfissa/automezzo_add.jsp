<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>

<%@page import="java.sql.Timestamp"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>

<%@ include file="../../initPage.jsp"%>






<form name="searchAccount" id = "searchAccount" action="OpuStab.do?command=InsertAutomezzo" method="post">


<!--  IMPRESA -->
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Dati automezzo</dhv:label></strong>
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="lab.denom">Identificativo veicolo / Targa</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="targa" id="targa" value="">
          </td>
        </tr>
        
         <tr>
        	<td nowrap class="formLabel">
     		 <dhv:label name="">Tipo veicolo</dhv:label>
   			 </td> 
    		<td> 
				   <input type="text" maxlength="70" size="50" name="tipo" id="tipo" value="">
			</td>
  		</tr>
  	
 </table> 		


<input type="button" id="search" name="search" value="Inserisci" onClick="checkForm(this.form);"/>
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="clearForm();">
<input type="hidden" name="idStabilimento" id="idStabilimento" value="<%=StabilimentoDettaglio.getIdStabilimento()%>">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

</form>

<br/><br/>
	

<table width="100%">
<col width="50%">
<tr><td valign="top">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
			<th colspan="2">
				<dhv:label name="<%="opu.operatore_gisa" %>"></dhv:label>
			</th>
	</tr>
	<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="">Ragione Sociale</dhv:label>
			</td>
			<td>
				<%= StabilimentoDettaglio.getOperatore().getRagioneSociale() %>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				Partita IVA
			</td>
			<td>
				<%= toHtml2( StabilimentoDettaglio.getOperatore().getPartitaIva()) %>
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				Codice Fiscale
			</td>
			<td>
				<%= toHtml2( StabilimentoDettaglio.getOperatore().getCodFiscale()) %>
			</td>
		</tr>
		
	
<dhv:evaluate if="<%= (StabilimentoDettaglio.getOperatore().getRappLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="<%="opu.soggetto_fisico_gisa"%>"></dhv:label>
			</td>
			<td>
				<%= StabilimentoDettaglio.getOperatore().getRappLegale().getNome()+ " "+StabilimentoDettaglio.getOperatore().getRappLegale().getCognome()+ " ("+StabilimentoDettaglio.getOperatore().getRappLegale().getCodFiscale()+")" %>
			<%= (!StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().toString().equals("")) ? "<br>Residente in : "+ StabilimentoDettaglio.getOperatore().getRappLegale().getIndirizzo().toString() :"" %>
			</td>
		</tr>
	</dhv:evaluate>
	<dhv:evaluate if="<%= (StabilimentoDettaglio.getOperatore().getSedeLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
			</td>
			<td>
				<%
					String comune =  ComuniList.getSelectedValue(StabilimentoDettaglio.getOperatore().getSedeLegale().getComune());
    				String via = (StabilimentoDettaglio.getOperatore().getSedeLegale().getVia()!= null)? StabilimentoDettaglio.getOperatore().getSedeLegale().getVia():"";  %>
				<%= StabilimentoDettaglio.getOperatore().getSedeLegale().toString() %>
				
				</td>
		</tr>
	</dhv:evaluate>
	</table>
	
</td><td valign="top">
	
<dhv:evaluate if="<%=(StabilimentoDettaglio.getSedeOperativa() != null &&   
StabilimentoDettaglio.getListaLineeProduttive().size()>0 && ((LineaProduttiva)StabilimentoDettaglio.getListaLineeProduttive().get(0)).getInfoStab().getTipoAttivita()!=3) %>">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    	
			<th colspan="2"><strong>Sede Produttiva</strong></th>
		
 </tr> 
 <dhv:evaluate if="<%=(AslList.size() > 0)%>">
	<tr>
			<td class="formLabel" nowrap><dhv:label name="opu.stabilimento.asl"></dhv:label>
			</td>
			<td><%=AslList.getSelectedValue(StabilimentoDettaglio.getIdAsl())%></td>
	</tr>
		</dhv:evaluate>
		
		 <dhv:evaluate if="<%=(StabilimentoDettaglio.getDenominazione()!= null && !"".equals(StabilimentoDettaglio.getDenominazione()) )%>">
	<tr>
			<td class="formLabel" nowrap>Denominazione
			</td>
			<td><%=StabilimentoDettaglio.getDenominazione()%></td>
	</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%=(StabilimentoDettaglio.getNumero_registrazione()!= null)%>">
											 
					<tr>
			<td class="formLabel" nowrap>Numero Registrazione
			</td>
			<td><%=StabilimentoDettaglio.getNumero_registrazione()%></td>
	</tr>						 
											 
	</dhv:evaluate>
	<dhv:evaluate if="<%=(StabilimentoDettaglio.getCodiceInterno()!= null)%>">
											 
					<tr>
			<td class="formLabel" nowrap>Codice Interno
			</td>
			<td><%=StabilimentoDettaglio.getCodiceInterno()%></td>
	</tr>						 
											 
	</dhv:evaluate>
	
		<dhv:evaluate if="<%=(StabilimentoDettaglio.getSedeOperativa()!= null)%>">
			<tr>
				<td class="formLabel" nowrap><dhv:label
					name="opu.sede_legale.indirizzo"></dhv:label></td>
				<td><%
				String comune = ComuniList.getSelectedValue(StabilimentoDettaglio.getSedeOperativa().getComune());
				String via = (StabilimentoDettaglio.getSedeOperativa().getVia() != null) ? StabilimentoDettaglio.getSedeOperativa().getVia(): "";
			%> 
			<%=StabilimentoDettaglio.getSedeOperativa().toString()%> </td>
			</tr>
			  <% if(Audit!=null){ %>
  
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= StabilimentoDettaglio.getCategoriaRischio()%>
      </td>
    </tr>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
      </td>
      <td>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
      Timestamp d = new Timestamp (datamio.getTime()); %>
  
         <%= (((StabilimentoDettaglio.getDataProssimoControllo()!=null))?(dataPC.format(StabilimentoDettaglio.getDataProssimoControllo())):(dataPC.format(d)))%>
      </td>
    </tr>
  <%}%>
</dhv:evaluate>
	<tr>
			<td class="formLabel" nowrap>Stato
			</td>
			<td><%=ListaStati.getSelectedValue(StabilimentoDettaglio.getStato())%></td>
	</tr>
 </table>

 </dhv:evaluate>
</td></tr></table>

<jsp:include page="../opu_linee_attivita_details.jsp" />
<jsp:include page="../opu_lista_distributori.jsp" />


 