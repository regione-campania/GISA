<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>

<jsp:useBean id="ListaDistribuzione" class="org.aspcfs.modules.allerte_new.base.ListaDistribuzione" scope="request"/>

<%
         		String modificabile =(String) request.getAttribute("Modificabile");
%>
  
  <script>

function view_uo_attivita(ind)
{
	document.getElementById('tipoIspezionePerContoDiDialog').value='attivita'
		document.getElementById("posizionePianoDialog").value=ind ;
	$('#dialogPerContoDi' ).dialog('open');
	
	}
	
function view_uo_attivita_multiplo()
{
	$('#dialogPerContoDiMultiplo' ).dialog('open');
	
	}
	
function checkApiariSelezionati(val){
	
	if (val=="A"){
		document.getElementById("apiariSelezionatiMotivodiv").style.display="none";
		document.getElementById("apiariSelezionatiMotivo").value="-1";
		document.getElementById("apiariSelezionatiMotivoAltrodiv").style.display="none";
		document.getElementById("apiariSelezionatiMotivoAltro").value = "";
		
		document.getElementById("apiariSelezionatiAlveariControllatidiv").style.display="";
		document.getElementById("apiariSelezionatiEsitodiv").style.display="";
		
	}
	else if (val=="B"){
		document.getElementById("apiariSelezionatiMotivodiv").style.display="";
		
		document.getElementById("apiariSelezionatiAlveariControllatidiv").style.display="";
		document.getElementById("apiariSelezionatiEsitodiv").style.display="";
	}
}	
function checkApiariSelezionatiMotivo(val){
	if (val=="4"){
		document.getElementById("apiariSelezionatiMotivoAltrodiv").style.display="";
	}
	else {
		document.getElementById("apiariSelezionatiMotivoAltrodiv").style.display="none";
		document.getElementById("apiariSelezionatiMotivoAltro").value="";
	}
}
function checkApiariSelezionatiEsito(val){
	if (val=="P"){
		document.getElementById("apiariSelezionatiEsitoNotediv").style.display="";
	}
	else{
		document.getElementById("apiariSelezionatiEsitoNotediv").style.display="none";
		document.getElementById("apiariSelezionatiEsitoNote").value="";
	}	
}
</script>

<%@page import="org.aspcfs.utils.web.LookupElement"%>

    	 <input type = "hidden" id = "modificabile" name  = "modificabile" value = "<%=modificabile %>">	
<input type= "hidden" name="context" value = "gisa">
<link rel="stylesheet" href="css/jquery-ui.css" />
<%
			
			TipoCampione.setJsEvent(TipoCampione.getJsEvent()+";mostraStrutturaAsl()");
			int valTipoCampioneDefault = -1;
			int valTipoIspezioneDefault = -1;
			if (TipoCampione.size()==1)
			{
				valTipoCampioneDefault = ((LookupElement)TipoCampione.get(0)).getCode();
			}
			if (TipoIspezione.size()==1 && valTipoCampioneDefault ==4)
			{
				valTipoIspezioneDefault = ((LookupElement)TipoIspezione.get(0)).getCode();
				
				
			}
				
			%> 
			
<tr class="containerBody">
	<td nowrap class="formLabel" width="50p;"><dhv:label name="">Tecnica di controllo</dhv:label>
	</td>
	<td>
	<%=showError(request, "tipoControlloError")%>
	<%=showError(request, "tipoControlloUOError")%>
	<%=showError(request, "data_preavvisoError")%>
    <%=showError(request, "flag_checklistError")%>
        <%=showError(request, "lineaAttivitaError")%>	
    
	<%out.print(TipoCampione.getSelectedValue(TicketDetails.getTipoCampione()));%>
				<br>
				<font color="red">IL TIPO DI CONTROLLO NON RISULTA MODIFICABILE</font>
				<input type="hidden" name = "tipoCampione" id="tipoCampione" value="<%=TicketDetails.getTipoCampione()%>">
				
			</td>
	
</tr>


<input type = "hidden" value="<%=modificabile %>">
<tr class="containerBody"  id="tr_select_motivo" style="display: none">
	<td nowrap class="formLabel" width="50p;">&nbsp;
	</td>
	<td>
	<table id = "tipo_cu" class="noborder">
	<tr><td>
	<%if(false){ %>
	<a href ="#" id = "link_select_motivo_href">
	<div id = "link_select_motivo">seleziona</div></a><%}else{
	
		out.print("<div id='link_select_motivo_href'><div id = 'link_select_motivo'></div></div>");
		
	%>
	
	<% } %>
				
				<%@ include file="../controlliufficiali/dialog_motivi_ispezione.jsp" %>
				<%@ include file="../controlliufficiali/dialog_motivi_audit.jsp" %>
				<%@ include file="../controlliufficiali/dialog_strutture_per_conto_di.jsp" %>
								<%@ include file="../controlliufficiali/dialog_strutture_per_conto_di_multiplo.jsp" %>
				
				<%@ include file="../controlliufficiali/dialog_lista_componenti_nucleo_ispettivo.jsp" %>
				<%@ include file="../controlliufficiali/dialog_strutture_controllate_autorita_competenti.jsp" %>
				
				
	</td><td></td></tr>
	
	<%
	
	int indAudit = 1 ;
	if (TicketDetails.getTipoCampione() == 3 && TicketDetails.getAssignedDate().before(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_MOTIVO_ISPEZIONE_AUDIT"))))
	{
	Iterator<Integer> itKeyMod =  TicketDetails.getTipoAudit().keySet().iterator();
	
	while (itKeyMod.hasNext())
	{
		int keyTA = itKeyMod.next();
		if (keyTA!=2 && keyTA!=3)
		{
		String descr = TicketDetails.getTipoAudit().get(keyTA);
		%>
				<tr name="tipo_audit"><td><b><%=indAudit %></b></td><td><%=AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+"->"+descr %></td></tr>
		
		<%
		indAudit++;
		}
		
	}
	

	 itKeyMod =  TicketDetails.getLisaElementibpi().keySet().iterator();
	while (itKeyMod.hasNext())
	{
		String descrizione= TicketDetails.getLisaElementibpi().get(itKeyMod.next());
		%>
		<tr name="tipo_audit"><td><b><%=indAudit %></b></td><td><%=AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+"->"+TicketDetails.getTipoAudit().get(2)+"->"+descrizione %></td></tr>
		<%
		indAudit++;
	}
	
	
	
	itKeyMod = TicketDetails.getLisaElementihaccp().keySet().iterator();
	while (itKeyMod.hasNext())
	{
		String descrizione= TicketDetails.getLisaElementihaccp().get(itKeyMod.next());
		%>
		<tr name="tipo_audit"><td><b><%=indAudit %></b></td><td><%=AuditTipo.getSelectedValue(TicketDetails.getAuditTipo())+"->"+TicketDetails.getTipoAudit().get(3)+"->"+descrizione %></td></tr>
		
		<%
		indAudit++;
	}
	
	}
	
	
	%>
	
	
	</table>
			
			
			</td>
	
</tr>
  
  
<tr id = "oggettoAudit" style="display: none" >
<td class = "formLabel" width="50p;">Oggetto Dell'Audit</td>
<td>
 <%
 LookupList valoriSel = new LookupList();
    HashMap<Integer,String > mapOggettoAudit = TicketDetails.getOggettoAudit();
    Iterator<Integer> itOggAudit = mapOggettoAudit.keySet().iterator();
   while (itOggAudit.hasNext())
    {
	   int key = itOggAudit.next() ;
	   LookupElement elmogg = new LookupElement();
	   elmogg.setCode(key);
	   elmogg.setDescription(mapOggettoAudit.get(key));
    	
    	
    	valoriSel.add(elmogg);
    	
    }
    %>

<%OggettoAudit.setSelectSize(9);
OggettoAudit.setMultiple(true);

%>

<%=OggettoAudit.getHtmlSelect("oggetto_audit",valoriSel) %>
</td>
</tr>

  
   <tr id = "auditFollowup" style="display: none" >
<td class = "formLabel" width="50p;">Audit di follow up?</td>
<td>
<input type="checkbox" id="auditFollowupCb" name="auditFollowupCb" <%if (TicketDetails.isAuditDiFollowUp()){ %>checked="checked"<%} %>/>
</td>
</tr>



<%

if ( "6".equalsIgnoreCase(""+OrgDetails.getTipologia()+"") )
{
%>
<tr>
<td class = "formLabel" width="50p;">Strutture Controllate</td>
<td>

<a href="#" onclick="$('#dialogAutoritaCompetenti' ).dialog('open');" style="">
		<font  color="#006699" style="font-weight: bold;">
		Seleziona Le Strutture Controllare</font></a>
		<div id = "struttureControllateDiv">
		
		 <%
    HashMap<Integer,OiaNodo > listaStruttureControllate = TicketDetails.getListaStruttureControllareAutoritaCompetenti();
    Iterator<Integer> itstrutt = listaStruttureControllate.keySet().iterator();
   while (itstrutt.hasNext())
    {
	   int kiaveStrAC= itstrutt.next() ;
	   %>
	   <input type= "hidden" name="strutturaControllata" value="<%=kiaveStrAC %>">
	   
	   <%
    	out.println(""+listaStruttureControllate.get(kiaveStrAC).getDescrizione_lunga()+ "<br>");
    }%>
		
		</div>
		
</td>
<td>
<%} %>
  
   <%
  		//simulazione supervisione
    	 if ( TicketDetails.getTipoCampione()==22 )
    	 {
    	 %>
    	 <input type="hidden" name="gotoPage" value="update" />
    	 
    	    <tr class="containerBody" id = "hidden3"  >
    <td nowrap class="formLabel" width="50p;">
      <dhv:label name="sanzionia.data_richiesta">Nuova Lista di Distribuzione Allegata</dhv:label>
    </td>
    <td>
    <table class = "noborder">
    <tr><td>
 		
  <a href="AccountsDocuments.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= fileItem.getId() %>&ver=<%= fileItem.getVersion() %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>">
       <%=fileItem.getSubject() %> </a> 
       
      <input type = "hidden" name = "isAllegatoSupervisione" id="isAllegatoSupervisione" value = "true"> 
      <input type = "hidden" name = "gotoPage" value = "update">   	
    </td>
    <td>
    </td>
    
    
    </tr></table></td>
  </tr>
    	 
<%} 
    	 
%>


<%

HashMap<String,String> listaCodiciInterniSettati = new HashMap<String,String>();
boolean isCondizionalita = false;
boolean isCondizionalitaB11 = false;
Iterator<LookupElement> itTipiIspez = TipoIspezione.iterator();

ArrayList<OiaNodo> lista1 = (ArrayList<OiaNodo>)request.getAttribute("StrutturaAsl");
int defValue = -1 ;
String descrizioneUO ="";

boolean isAllerta=false;
boolean entrato = false;
while(itTipiIspez.hasNext())
{
	LookupElement elTipIsp = itTipiIspez.next();
	int codeIsp =elTipIsp.getCode();
	String codeInternoIsp =elTipIsp.getCodiceInterno().toLowerCase();
	
	isAllerta = false ;
	if(codeInternoIsp!=null)
	{
	
		
		if(TicketDetails.getLista_uo_ispezione().containsKey(codeIsp))
		{
			
			listaCodiciInterniSettati.put(codeInternoIsp+"", "");
			%>
						<%
			defValue = TicketDetails.getLista_uo_ispezione().get(codeIsp) != null ? TicketDetails.getLista_uo_ispezione().get(codeIsp).getId() : -1;
			descrizioneUO = TicketDetails.getLista_uo_ispezione().get(codeIsp) != null ?  (TicketDetails.getLista_uo_ispezione().get(codeIsp).getId_padre()>0) ? TicketDetails.getLista_uo_ispezione().get(codeIsp).getDescrizionePadre()+"->"+ TicketDetails.getLista_uo_ispezione().get(codeIsp).getDescrizione_lunga() : TicketDetails.getLista_uo_ispezione().get(codeIsp).getDescrizione_lunga() : "";
		}
	
	if (codeInternoIsp.equalsIgnoreCase("2a") )
	{
		%>
				<input type = "hidden" name = "num_piani" id = "num_piani" value = "<%=TicketDetails.getPianoMonitoraggio().size() %>"/>
		
				<tr id ="clonepiano" class="containerBody" style="display: none">
		<td nowrap class="formLabel" width="50p;"><label id = "piano"><b></b></label></td>
		<td >
		<table class="noborder">
		<tr><td colspan="2"  >
		<input type = "hidden" name = "piano_monitoraggio" id = "piano_value" value = "-1">
		 <font color = "red">*</font>
		<input type = "hidden" name = "uo" id = "uo" value="-1">
		<div id ="uodescr" ></div>
		<a href="#" onclick="" style="">
		<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
		</td></tr>
		</table>
		
		
		</td></tr>
		
		<%
		int ind = 0 ;
	
		for(Piano p : TicketDetails.getPianoMonitoraggio())
		{
			
			
			listaCodiciInterniSettati.put(p.getCodice_interno()+"", ""+p.getDescrizione());
			
			ind ++ ;
			int idPiano = p.getId();
			String descrizionePiano = p.getDescrizione();
			String descuo = p.getDesc_uo() ;
			int iduo = p.getId_uo();
			if (!isCondizionalita)
				isCondizionalita = ((("982").equals(p.getCodice_interno())) || (("983").equals(p.getCodice_interno()))) && p.isFlagCondizionalita()==true;
			if (!isCondizionalitaB11)
				isCondizionalitaB11 = (("1483").equals(p.getCodice_interno())) && p.isFlagCondizionalita()==true;
			%>
			
			<tr id = "clonepiano<%=ind %>" class="containerBody">
		<input type = "hidden" name = "piano_monitoraggio<%=ind %>" id = "piano_monitoraggio<%=ind %>" value = "<%=idPiano %>">
		<td class="formLabel">
		<la	bel id = "piano<%=ind%>"><b><%=ind+") " %></b><%=descrizionePiano %></label></td>
		</td>
		<td>
		<input type = "hidden" name = "uo<%=ind %>" id = "uo<%=ind %>" value="<%=iduo%>">
		<div id ="uodescr<%=ind%>"><%=descuo %></div>
		<a href="#" id = "ancora_<%=ind %>" onclick="viewuo(<%=ind%>) "><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
		</td>
		
		</tr>
			
			<%
			
		}
		
		%>
		
		
	
<%
}
	else if (codeInternoIsp.equalsIgnoreCase("14a"))
	{
		%>
	
	
<tr id="macellazione" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("14a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
		<td nowrap class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br></td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_macellazione" value="<%=TicketDetails.getContributi_macellazione()%>"></td>
				
	
	<td>
	
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				
				</td>
				</tr>
		</table>
</td>
</tr>


		<%
	}else if (codeInternoIsp.equalsIgnoreCase("28a"))
	{
		%>
		
<tr id="macellazione_urgenza" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("28a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%>>
		<td nowrap class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br></td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_macellazione_urgenza" value="<%=TicketDetails.getContributi_macellazione_urgenza()%>"></td>
				<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				</tr>
		</table>
</td>
</tr>

		<%
	}
	
	
	else if (codeInternoIsp.equalsIgnoreCase("1a"))
	{
		%>
		
<tr id="seguitodicampionamento" <%if(TicketDetails.getTipoIspezioneCodiceInterno().contains("1a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> class="containerBody">
		<td class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi" value="<%=TicketDetails.getContributi_seguito_campioni_tamponi()%>"></td>
				<td>
			<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeInternoIsp.equalsIgnoreCase("20a"))
	{
	
		%>
		
<tr id="svincolisanitari" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("20a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
		<td class = "formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>
			<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td></tr>
				<tr>
				<td>Data Preavviso</td>
					<td>
					
						<input readonly type="text" id="data_preavviso" name="data_preavviso" size="10" value = "<%=toDateString(TicketDetails.getData_preavviso()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_preavviso,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					</td></tr><tr>
					<td>
					Protocollo Preavviso
					</td>
					<td >
					<input type = "text" name = "protocollo_preavviso" value = "<%=toHtml2(TicketDetails.getProtocollo_preavviso()) %>">
					</td>
					
				</tr>
				<tr>
				<td>Data Comunicazione Svincolo</td>
					<td>
					
											<input readonly type="text" id="data_comunicazione_svincolo" name="data_comunicazione_svincolo" size="10" value = "<%=toDateString(TicketDetails.getData_comunicazione_svincolo()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].data_comunicazione_svincolo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
					</td></tr><tr>
					<td>
					Protocollo Svincolo
					</td>
					<td >
					<input type = "text" name = "protocollo_svincolo" value = "<%=toHtml2(TicketDetails.getProtocollo_svincolo()) %>">
					</td>
					
				</tr>
					<tr>
				<td>Tipologia Sottoprodotto</td>
					<td>
						<textarea rows="5" cols="33" name="tipologia_sottoprodotto" value = "<%=TicketDetails.getTipologia_sottoprodotto() %>"><%=toHtml2(TicketDetails.getTipologia_sottoprodotto()) %></textarea>
					</td></tr><tr>
					<td>
					peso
					</td>
					<td >
					<input type = "text" name = "peso" value = "<%=TicketDetails.getPeso() %>">
					</td>
					
				</tr>
		</table>
</td>
</tr>

		<%
	}
	else if (codeInternoIsp.equalsIgnoreCase("10a"))
	{
		%>
		
<tr id="importazionescambio" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("10a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> class="containerBody">
		<td class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %></td>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui ï¿½ previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_importazione_scambio"  value="<%=TicketDetails.getContributi_importazione_scambio()%>"/></td>
				<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeInternoIsp.equalsIgnoreCase("7a"))
	{
		isAllerta=true ;
		
		if (entrato==false)
		{
			entrato=true;
		%>
		
<tr id="hidden1" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("7a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
		<td nowrap class="formLabel" width="50p;">Allarme Rapido</td>
		<td>
		
		<table class= "noborder">
		<tr>
		<td>Codice Allerta</td>
		<td>
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="id_allerta" value="<%=TicketDetails.getCodiceAllerta() %>" name="idAllerta" ><font color="red">*</font>
		</td>
		</tr>
		
		<input type="hidden" id="idLdd" name="idLdd" value="<%=TicketDetails.getIdListaDistribuzione()%>"/>
		
		<tr><td>
      <%if (modificabile!=null){ %>
      &nbsp;[<a href="javascript:popLookupSelectorAllerta('id_allerta','name','ticket','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
  	<%} %>
   </td> </tr>
   
   <tr>
   <td>&nbsp;
		Contributi in Euro <br>(solo nei casi in cui ï¿½ previsto <br> dal D.Lgs 194/2008) 
		</td>
		<td >&nbsp;&nbsp;
		<input type="text" name="contributi_allarme_rapido" value="<%=TicketDetails.getContributi_allarme_rapido()%>">
		</td>
		</tr>
		
<!-- 		<tr> -->
<%-- 		<td><a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;"> --%>
<!-- 		Seleziona Per Conto di</font></a> -->
				
<%-- 				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>"> --%>
<%-- 				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div> --%>
				
<!-- 				</td> -->
<!-- 		</tr> -->
		
		</table>
		
		</td>
</tr>
		<%
		}
		
		
	}
	else if (codeInternoIsp.equalsIgnoreCase("30a"))
	{
		%>
		

<tr id="rilascio_certificazione" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("30a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
		<td nowrap class="formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>Contributi in Euro <br>(solo nei casi in cui ï¿½ previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_rilascio_certificazione" value="<%=TicketDetails.getContributi_rilascio_certificazione()%>"></td>
				
<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				</tr>
		</table>
</td>
</tr>
		
		<%
	}	
	else if (codeInternoIsp.equalsIgnoreCase("9a"))
	{
		
		%>
		
	<tr id="tipoSospetto" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("9a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
		<td class="formLabel" width="50p;">
			Tipo di sospetto</td>
		<td>
		<table class = "noborder">
			<tr>
				<td><select id="tipoSosp" name="tipoSosp">
					<option value="-1" >Seleziona voce</option>
					<option value="1" onclick="document.getElementById('tipoBuffer').style.display='block';" <%if(TicketDetails.getTipoSospetto() != null && TicketDetails.getTipoSospetto().contains("ambient")){ %> selected="selected" <% }%> >Per emergenza ambientale</option>
  					<option value="2" onclick="document.getElementById('tipoBuffer').style.display='none';" <%if(TicketDetails.getTipoSospetto() != null && TicketDetails.getTipoSospetto().contains("altro")){ %> selected="selected" <% }%> >Per altro motivo</option>
				</select>
				</td>
				<td id="tipoBuffer" style="display:none">
					<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="idBuffer" value="<%=toHtmlValue(BufferDetails.getCodiceBuffer())%>" name="idBuffer" ><font color="red">*</font>
      				
      							&nbsp;[<a href="javascript:popLookupSelectorBuffer('idBuffer','name','buffer','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
  				   
   				</td>
				<input type="hidden" id="id_buffer" name="id_buffer" value="<%=BufferDetails.getId()%>"/>
			</tr>
		</table>
	</td>
   </tr>
    <tr class="containerBody" id ="ispezione_generica_<%=codeIsp %>"  <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("9a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
	<td nowrap class="formLabel"width="50p;"><%=TipoIspezione.getSelectedValue(codeIsp) %></td>
		<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
	</tr>
   
		
		<%
	}
	
	
	else if (codeInternoIsp.equalsIgnoreCase("58a"))
	{
		%>
		<tr id="nonconformitaprec" <%if( TicketDetails.containsIgnoreCase("58a", TicketDetails.getTipoIspezioneCodiceInterno()) &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> >
		<td class = "formLabel" width="50p;">
			<%=TipoIspezione.getSelectedValue(codeIsp) %>
		<td>
		<table class = "noborder">
				<tr>
				<td>Le azioni correttive <br> risultano adeguate ed efficaci ?</td>
				<td>
					SI <input type = "radio" name = "azione" value = "1" 
					<%if (TicketDetails.isAzione()){ %>checked="checked" <%} %> 
					<%if (modificabile!=null){ %>readonly="readonly"<%} %> onclick="javascript : document.getElementById('desc1').style.display='block'">
					</td>
					<td>
					NO <input type = "radio" <%if (modificabile!=null){ %>readonly="readonly"<%} %> 
					<%if (!TicketDetails.isAzione()){ %>checked="checked" <%} %> 
					onclick="javascript : document.getElementById('desc1').style.display='none'" value = "0" name = "azione">
					</td>
					<td id="desc1" <%if (!TicketDetails.isAzione()){ %>style = "display: none" <%} %>>
					Descrizione<br>
					<textarea name = "azione_descrizione" >
					<%=toHtml(TicketDetails.getAzione_descrizione()) %>
					</textarea>
					</td>
					<td >Contributi in Euro <br> </td>
					<td >&nbsp;&nbsp;
					<input type="text" name="contributi_risol_nc" value="<%=TicketDetails.getContributi_verifica_risoluzione_nc()%>">
					</td>
				
<td>
			<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				</tr>
		</table>
</td>
</tr>
		<%
	}
	else if (codeInternoIsp.equalsIgnoreCase("16a"))
	{
		%>
<tr  <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains("16a") &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> id="tossinfezione"> 
		<td nowrap class="formLabel" width="50p;"><%=TipoIspezione.getSelectedValue(codeIsp) %></td>
		<td>
		<table class = "noborder">
				<tr>
					<td></td>
				</tr>
				<tr>
					<td>Soggetti Coinvolti</td>
					<td>
					<input type="text" name="soggettiCoinvolti" value = "<%=toHtml(TicketDetails.getSoggettiCoinvolti()) %>" <%if(modificabile==null){%>readonly="readonly"<%} %> >
					</td>
				</tr>
				<tr>
					<td>Di cui Ricoverati</td>
					<td>
					<input type="text" name="ricoverati" value = "<%=toHtml(TicketDetails.getRicoverati()) %>"  <%if(modificabile==null){%>readonly="readonly"<%} %> > 
					</td>
				</tr>
				<tr>
					<td>Alimenti Sospetti</td>
					<td>
					<textarea name="alimentiSospetti" rows="4" cols="30" value = "<%=TicketDetails.getAlimentiSospetti() %>"  <%if(modificabile==null){%>readonly="readonly"<%} %>><%=TicketDetails.getAlimentiSospetti() %> </textarea>
					</td>
				</tr>
				<tr>
					<td>Data insorgenza Sintomi</td>
					<td>
					
					
						<input readonly type="text" id="dataSintomi" name="dataSintomi" size="10" value = "<%=toDateString(TicketDetails.getDataSintomi()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataSintomi,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
			
					
					</td>
				</tr>
				<tr>
					<td>Data Ingestione pasto Sospetto</td>
					<td>
					<input readonly type="text" id="dataPasto" name="dataPasto" size="10" value = "<%=toDateString(TicketDetails.getDataSintomi()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataPasto,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					
				
					</td>
				</tr>

				<tr><td>Per condo Di</td>
				<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				
				</tr>
			</table>
</td>
</tr>
		<%
	}
	else 
	{
		%>
		<%
					
				
				%>
		
<tr class="containerBody" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains(codeInternoIsp) &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%> id ="ispezione_generica_<%=codeIsp %>">
	<td nowrap class="formLabel" width="50p;"><%=TipoIspezione.getSelectedValue(codeIsp) %></td>
		<td>
				<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)"><font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font></a>
				
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value = "<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div>
				
				</td>
				</tr>
		<%
		

	}
	
	if (isAllerta==true)
	{
		%>
		
			<tr class="containerBody" id ="ispezione_generica_<%=elTipIsp.getCode() %>" <%if( TicketDetails.getTipoIspezioneCodiceInterno().contains(codeInternoIsp) &&  TicketDetails.getLista_uo_ispezione().containsKey(codeIsp)){ %><%}else{%>style="display: none"<%}%>>
			<td nowrap class="formLabel" width="50p;"><%=TipoIspezione.getSelectedValue(elTipIsp.getCode()) %></td>
				<td>
					<a href="#" onclick="view_uo_attivita(<%=elTipIsp.getCode()%>)">
				<font  color="#006699" style="font-weight: bold;">
		Seleziona Per Conto di</font>
				</a>
				<input type = "hidden" name = "per_condo_di<%=elTipIsp.getCode() %>" id = "per_condo_di<%=elTipIsp.getCode() %>" value="<%=defValue%>">
				<div id ="descrizione_<%=elTipIsp.getCode() %>"><%=descrizioneUO %></div> 
						</td>
						
	</tr>
		
		<%
	}
}
}
%>

<%

		LookupList ls = new LookupList();
	    HashMap<Integer,String > cond = TicketDetails.getTipo_ispezione_condizionalita();
	    Iterator<Integer> itcond = cond.keySet().iterator();
	   while (itcond.hasNext())
	    {
		   int key = itcond.next() ;
		   LookupElement eeee = new LookupElement();
		   eeee.setCode(key);
		   eeee.setDescription(cond.get(key));
	    	
	    	
	    	ls.add(eeee); 
	    	
	    	
	    }
		%>
	
	<script>
function gestisciCondizionalita(cb){
	var select = document.getElementById("condizionalita");
	var div = document.getElementById("cond");
	if (cb.checked){
		div.style="display:none";
		select.value="38";
	}
	else {
		div.style="display:block";
	}
}
</script>
	
<%boolean check = false;
if (ls.size()==0)
	check = true;
else if (ls.size()==1 && ls.exists(5))
	check = true;
else if (ls.size()<=2 && ls.exists(38))
	check = true;
%>	
	
<tr id="condizionalitarow" class="containerBody" <% if (!isCondizionalita){ %> style="display:none"<% } %>>
			<td class="formLabel" width="50p;">
				CONDIZIONALITA
			<td>
			<table class = "noborder">
					<tr>
					<td>
					<input type="checkbox" id="cond_cb" name="cond_cb" onClick="gestisciCondizionalita(this)" <%=(check) ? "checked='checked'" : "" %>/>Non necessita di condizionalità
					
					<div id="cond" <%=(check) ? "style='display:none'" : "" %>>
					<%=Condizionalita.getHtmlSelect("condizionalita", ls) %>
					</div>
					
					</td>
						
					<td>
					
					&nbsp;
					</td>
			</table>
	</td>
	</tr>
	
	
<tr id="condizionalitab11row" class="containerBody" <% if (!isCondizionalitaB11){ %> style="display:none"<% } %>>
			<td class="formLabel" width="50p;">
				CONDIZIONALITA
			<td>
			<table class = "noborder">
					<tr>
					<td>
					<input type="checkbox" id="cond_b11_cb" name="cond_b11_cb" onClick="return false;" <%=(ls.exists(5)) ? "checked='checked'" : "" %> />ATTO B11 - rintracciabilità e sicurezza alimentare
					</td>
					<td>
					&nbsp;
					</td>
			</table>
	</td>
	</tr>

<tr id="checklist_ba_tr" class="containerBody" style="display: none">
		<td  class="formLabel" width="50p;">
			E' stata consegnata una copia della<br>
			presente checklist all'allevatore?
			</td>
		<td>
		<select id= "flag_checklist" name = "flag_checklist" >
			<option value = "-1" selected="selected" >Seleziona Voce</option>
			<option value = "N" <%if ("N".equalsIgnoreCase(TicketDetails.getFlag_checklist()) ){%>selected="selected"<%} %>>NO</option>
			<option value = "S" <%if ("S".equalsIgnoreCase(TicketDetails.getFlag_checklist()) ){%>selected="selected"<%} %>>SI</option>
		</select>
				
		</td>
		</tr>
<%

//commento al 214
if(true)
{
%>		
	
	<tr id="misureFormativetr" class="containerBody" 
<%
if(TicketDetails.getMisureFormative()==null || TicketDetails.getMisureFormative().equals(""))
{	
%>	
style="display:none;"
<%
}
%>
	>
		            <td  class="formLabel" width="50p;">
					Misure formative
					</td>
					<td>
						<input type="text" value="<%=(TicketDetails.getMisureFormative()!=null)?(TicketDetails.getMisureFormative()):("")%>" name="misureFormative" id="misureFormative"/>
						<font color="red">*</font>			
				    </td>
	</tr>
	<tr id="misureRestrittivetr" class="containerBody"  
<%
if(TicketDetails.getMisureRestrittive()==null || TicketDetails.getMisureRestrittive().equals(""))
{	
%>	
style="display:none;"
<%
}
%>
	>
		            <td  class="formLabel" width="50p;">
					Misure restrittive
					</td>
					<td>
						<input type="text" value="<%=(TicketDetails.getMisureRestrittive()!=null)?(TicketDetails.getMisureRestrittive()):("")%>" name="misureRestrittive" id="misureRestrittive"/>
						<font color="red">*</font>	
						<font color="red">Le misure prescrittive sono di competenza delle attività AO9_B, AO9_C e B52 che vanno inserite da macroarea iuv e sono valide solo per cani padronali e canili </font>		
				    </td>
	</tr>
	<tr id="misureRiabilitativetr" class="containerBody" 
<%
if(TicketDetails.getMisureRiabilitative()==null || TicketDetails.getMisureRiabilitative().equals(""))
{	
%>	
style="display:none;"
<%
}
%>
	>
		            <td  class="formLabel" width="50p;">
					Misure riabilitative
					</td>
					<td>
						<input type="text" value="<%=(TicketDetails.getMisureRiabilitative()!=null)?(TicketDetails.getMisureRiabilitative()):("")%>" name="misureRiabilitative" id="misureRiabilitative"/>
						<font color="red">*</font>			
				    </td>
	</tr>
	
	<%
}
%>

<!-- commento al 247-->

<tr id="apiariSelezionatitr" class="containerBody" <%= TicketDetails.getApiariSelezionati()==null ? "style=\"display:none\"" : "" %>>
		            <td  class="formLabel" width="50p;">
					Apiari Selezionati
					</td>
					<td>
						<input type="radio" id="apiariSelezionatiA" name="apiariSelezionati" value="A" onClick="checkApiariSelezionati(this.value)" <%="A".equals(TicketDetails.getApiariSelezionati()) ? "checked" : "" %>>Random	
						<input type="radio" id="apiariSelezionatiB" name="apiariSelezionati" value="B" onClick="checkApiariSelezionati(this.value)" <%="B".equals(TicketDetails.getApiariSelezionati()) ? "checked" : "" %>>Con criteri di rischio		
				    	<font color="red">*</font><br/>
				    	
				    	<div id="apiariSelezionatiMotivodiv" <%="A".equals(TicketDetails.getApiariSelezionati()) ? "style=\"display:none\"" : "" %>>
				    	<b>Motivo: </b>
				    	<select id="apiariSelezionatiMotivo" name="apiariSelezionatiMotivo" onChange="checkApiariSelezionatiMotivo(this.value)">
						<option value="1" <%="1".equals(TicketDetails.getApiariSelezionatiMotivo()) ? "selected" : "" %>>nomadismo</option>
						<option value="2" <%="2".equals(TicketDetails.getApiariSelezionatiMotivo()) ? "selected" : "" %>>acquisto materiale biologico </option>
						<option value="3" <%="3".equals(TicketDetails.getApiariSelezionatiMotivo()) ? "selected" : "" %>>apicoltori in possesso di diversi apiari stanziali in più regioni o province autonome</option>
						<option value="4" <%="4".equals(TicketDetails.getApiariSelezionatiMotivo()) ? "selected" : "" %>>altro</option>
						</select>			
						</div>
						
						<div id="apiariSelezionatiMotivoAltrodiv" <%="B".equals(TicketDetails.getApiariSelezionati()) && "4".equals(TicketDetails.getApiariSelezionatiMotivo()) ? "" : "style=\"display:none\"" %>>
						<b>Altro:</b> <input type="text" id="apiariSelezionatiMotivoAltro" name="apiariSelezionatiMotivoAltro" placeholder="Altro" value="<%=TicketDetails.getApiariSelezionatiMotivoAltro()%>"/> <br/>
						</div>
						
						<div id="apiariSelezionatiAlveariControllatidiv">
						<b>Numero alveari controllati:</b> <input type="number" min="0" step="1" onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="apiariSelezionatiAlveariControllati" name="apiariSelezionatiAlveariControllati" value="<%=TicketDetails.getApiariSelezionatiAlveariControllati()%>"/> <br/>
				    	</div>
				    	
						<div id="apiariSelezionatiEsitodiv">
				    	<b>Esito:</b> 
				    	<input type="radio" id="apiariSelezionatiEsitoP" name="apiariSelezionatiEsito" value="P" onClick="checkApiariSelezionatiEsito(this.value)" <%="P".equals(TicketDetails.getApiariSelezionatiEsito()) ? "checked" : "" %>>Positivo	
						<input type="radio" id="apiariSelezionatiEsitoN" name="apiariSelezionatiEsito" value="N" onClick="checkApiariSelezionatiEsito(this.value)" <%="N".equals(TicketDetails.getApiariSelezionatiEsito()) ? "checked" : "" %>>Negativo	
						</div>
						
						<div id="apiariSelezionatiEsitoNotediv" <%=("P".equals(TicketDetails.getApiariSelezionatiEsito())) ? "" : "style=\"display:none\"" %>>
						<b>Note:</b> <input type="text" id="apiariSelezionatiEsitoNote" name="apiariSelezionatiEsitoNote" placeholder="Note" value="<%=TicketDetails.getApiariSelezionatiEsitoNote()%>"/> <br/>
						</div>
						
				    </td>
	</tr>


		<%
	



if (request.getAttribute("tipologia")!=null && "201".equals(""+request.getAttribute("tipologia")))
{
%>
<tr class="containerBody" id="molluschiquantitativo" style="display: none">
		<td class="formLabel" width="50p;">Verifica quantitativo prodotto raccolto</td>
		<td>
		<%=VerificaQuantitativo.getHtmlSelect("quantitativo",TicketDetails.getQuantitativo()) %>
		Quintali <input type = "text" name = "quintali" id = "quintali" value = "<%=TicketDetails.getQuintali()%>">
		</td>
	</tr>
	


<%} %>

