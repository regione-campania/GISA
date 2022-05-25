<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<script type="text/javascript">

var dataCu='' ;      

function setVeterinario (indice,val)
{
	alert(document.getElementById('Veterinari_'+indice).value );
	document.getElementById('id_utente_selezionato_'+indice).value = val;
}
function openProgressBarModal(){
	
	var result;

	res =	window.open('LookupSelector.do?command=popUpModale',null,
		'height=300px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
	return res ;
} 
function controlloEsistenzaCu(data,orgId)
{
	   dataCu = data ;
	   PopolaCombo.controlloEsistenzaCU(data ,orgId,gestisciRistpostaEsistenzaCuCallBack);
	   
}
function mostraUo(index)
{
	alert(index);
	document.getElementById('uo_'+index).style.display="";

}
function gestisciRistpostaEsistenzaCuCallBack(val)
{
	   if(val!=-1)
	   {
		   alert('Attenzione per la corrente impresa esiste già un controllo inserito in data '+dataCu+' con identificativo '+val);
		   dataCu='' ; 
	   }
}
var isPianomonitoraggio = false ; 
var isSorveglianza = false ;
function controlloCuSorveglianza()
{

	isPianomonitoraggio = false ;
	isSorveglianza = false ; 
if (document.addticket.assignedDate.value == '')
	return checkForm(document.addticket);
orgId = document.addticket.orgId.value;
assetId = -1 ;
if (document.addticket.assetId!=null)
{
	assetId = document.addticket.assetId.value;
}



tipoIspezione = document.addticket.tipoIspezione;
for (i=0 ; i<tipoIspezione.length;i++)
{
if(tipoIspezione.options[i].value == '2' && tipoIspezione.options[i].selected==true)
{
	isPianomonitoraggio = true ;
}

}

if (document.addticket.tipoCampione.value == '5')
{
	
	isSorveglianza = true ;
}


PopolaCombo.controlloInserimentoCuSorveglianza(orgId, isPianomonitoraggio,isSorveglianza,document.addticket.assignedDate.value,assetId, viewMessageCallback1);
}

function viewMessageCallback1(returnValue) {

messaggio1 = returnValue[0];
messaggio2 = returnValue[1];
messaggio3 = returnValue[2];
messaggio4 = returnValue[3];

flag = true ;

if (messaggio1 != null && messaggio1 != "") 
{
	if (document.addticket.tipoCampione.value == '5') 
	{
		alert('ATTENZIONE : non è possibile inserire un nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza ancora aperti.Chiudere prima i seguenti controlli: \n' + messaggio1);
		flag = false;
	}
}	/*else
	{
			alert('ATTENZIONE : non è possibile inserire un nuovo controllo ' + messaggio1);
			return false;
	}
}
else
{*/
	
	if (messaggio4!='')
	{
		alert(messaggio4);
		flag=false;
	}
	if(messaggio2!="" || messaggio3!="")
	{
		/**
		 * SE DATA INIZIO CONTROLLO è ANTECEDENTE ALLA DATA PROSSIMO CONTROLLO - 30 GG
		 * IL SISTEMA GENERERà UN MESSAGGIO NON BLOCCANTE.
		 * (VALE SOLO PER I CONTROLLI IN SORVEGLIANZA)
		 */
	if (messaggio2 != null && messaggio2 != "") 
	{
		
		if (flag == true)
			alert('ATTENZIONE! Hai effettuato una ispezione in sorveglianza in una data precedente a quella stabilita dai criteri di programmazione');
		flag= true;
	} 
	/**
	 * SE DATA INIZIO CONTROLLO è INFERIORE ALLA DATA ATTUALE -30 GIORNI IL SISTEMA SEGNALA UN
	 * MESSAGGIO NON BLOCCANTE (IL MESSAGGIO SARA BLOCCANTE A PARTIRE DA GENNAIO 2012)
	 * VALE PER TUTTI I TIPI DI CONTROLLO
	 */
	if (messaggio3 != null && messaggio3 != "" ) 
	{
		
		if (flag == true)
		{
			if (isPianomonitoraggio==false)
				alert('ATTENZIONE.Stai inserendo i dati di un controllo ufficiale effettuato oltre 30 gg fa. Il sistema impedisce questo tipo di operazioni per motivi di congruenza dei dati.' );
			else
			{
				alert('ATTENZIONE.Stai inserendo i dati di un controllo ufficiale effettuato oltre 15 gg fa. Il sistema impedisce questo tipo di operazioni per motivi di congruenza dei dati.' );
			}
		}

		
		if(document.getElementById("cu_pregresso")==null || (document.getElementById("cu_pregresso")!= null && document.getElementById("cu_pregresso").checked == false))
			flag = false;
		else
			if(document.getElementById("cu_pregresso").checked==true)
				flag = true;
	} 
	}
	else
		{
		
			if (flag == true)
				return checkForm(document.addticket);
		}
if (flag == true)
{
    
	
return checkForm(document.addticket);
}
}

</script>

<%
TipoIspezione.setMultiple(true);
TipoAudit.setMultiple(true);
TipoIspezione.setSelectSize(5);
TipoAudit.setSelectSize(5);
%>

<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>"/>

<dhv:include name="stabilimenti-sites" none="true">
	<%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
	<tr>
		<td nowrap class="formLabel"><dhv:label name="stabilimenti.site">Site</dhv:label>
		</td> 
		<td>
		<%if (OrgDetails.getSiteId()>0){ %>
		<%=SiteIdList.getSelectedValue(OrgDetails.getSiteId())%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=OrgDetails.getSiteId()%>">
		<%}
		else
		{
			UserBean utente = (UserBean)session.getAttribute("User");
			if(utente.getSiteId()>0)
			{
			%>
			<%=SiteIdList.getSelectedValue(utente.getSiteId()) %>
			<input type="hidden" name="siteId" id = "siteId" value="<%=utente.getSiteId()%>">
			<%
			}
			else
			{%>
				<%=SiteIdList.getHtmlSelect("siteId",-1)%>
			<%
			}
		}
		%>
		</td>
	</tr>
	<%--</dhv:evaluate>  --%>
	<dhv:evaluate if="<%=SiteIdList.size() <= 1%>">
		<input type="hidden" name="siteId" id="siteId" value="-1" />
	</dhv:evaluate>
</dhv:include>

 <tr class="containerBody">
      <td nowrap class="formLabel">
       Operatore Sottoposto a controllo
      </td>
      <td><%="<b>"+OrgDetails.getName()+"<b>" %> </h3></td>
    </tr>



<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Tipo di controllo</dhv:label>
	</td>
	<td>
	
	
	
	<table border="0" cellspacing="3" cellpadding="0" class="empty">
		<tr>
			<td>
			<%
			
				PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('addticket')");
				PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
				PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
				TipoAudit.setSelectStyle("display: none");
				AuditTipo.setSelectStyle("display: none");
				AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
				TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");

				Bpi.setSelectStyle("display: none");
				Bpi.setMultiple(true);
				Bpi.setSelectSize(10);
				TipoAudit.setMultiple(true);
				TipoAudit.setSelectSize(10);
				Haccp.setSelectStyle("display: none");
				Haccp.setMultiple(true);
				Haccp.setSelectSize(10);
				TipoIspezione.setSelectStyle("display: none");
				PianoMonitoraggio1.setSelectStyle("display: none");
				PianoMonitoraggio2.setSelectStyle("display: none");
				PianoMonitoraggio3.setSelectStyle("display: none");
				
				
				
			%> 
			<%=TipoCampione.getHtmlSelect("tipoCampione", TicketDetails.getTipoCampione())%>
			</td>
			<td><%=AuditTipo.getHtmlSelect("auditTipo", TicketDetails.getAuditTipo())%></td>

			<td>
			<table class="noborder">
				<tr>
					<td><label id = "audit_lab" style="visibility: hidden;">Oggetto dell'Audit</label> <br><%=TipoAudit.getHtmlSelect("tipoAudit",-1)%></td>
					<td>
					<table>
						<tr>
							<td><label id="lab1" style="display: none"><b>
							Motivo dell'Ispezione :</b></label></td>
						</tr>
						<tr>
							<td><%=TipoIspezione.getHtmlSelect("tipoIspezione",-1)%></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		<td>
			
			<%--NOTE --%>
			<table style="display: none" id="ispezione_altro">
			<tr>
			<td>Descrizione:</td>
			</tr>
			<tr>
			<td><textarea rows="6" cols="30" name="text_ispezione_altro"></textarea></td>
			</tr>
			</table>
			<%--FINE NOTE --%>
			
			<%--BPI --%>
			<label name="labelname1" id="label1" style="display: none"><b>BPI</b><br>(* In caso di selezione multipla tenere </br>
			premuto il tasto Ctrl durante la selezione)</label>
			<%=Bpi.getHtmlSelect("bpi", Integer.parseInt(TicketDetails.getBpi()))%> 
			<%--FINE BPI --%>
			<br>
			<%--HACCP --%>
			<label name="labelname2" id="label2" style="display: none">
			<b>HACCP</b><br>
			(*
			In caso di selezione multipla tenere </br>
			premuto il tasto Ctrl durante la selezione)</label> <font color="red">
			* </font>
			 <%=Haccp.getHtmlSelect("haccp", TicketDetails.getHaccp())%>
			
			<%--HACCP --%>
			</td>

			<td id="notealtro" style="visibility: hidden">Descrizione : <textarea name="notealtro" rows="6" cols="30"></textarea></td>
		</tr>


	</table>
	</td>
</tr>


<%

if (request.getAttribute("ViewLdA")!=null)
{
%>
<%@ include file="../controlliufficiali/linea_attivita_imprese.jsp" %>

<%	
}


if (request.getAttribute("ViewLdAStab")!=null)
{
%>
<%@ include file="../controlliufficiali/linea_attivita_stabilimenti.jsp" %>

<%	
}
if (request.getAttribute("ViewLdASoa")!=null)
{
%>
<%@ include file="../controlliufficiali/linea_attivita_soa.jsp" %>

<%	
}

%>




<tr id="nonconformitaprec" style="display: none" class="containerBody" >
		<td class = "formLabel" >
			Verifica Risoluzione N.C Precedenti 
		<td>
		<table class = "noborder">
				<tr>
				<td>Le azioni correttive <br> risultano adeguate ed efficaci ?</td>
					<td>SI <input type="radio" name="azione" value="1"
						onclick="javascript : document.getElementById('desc1').style.display='block' ">
					</td>
					<td>NO <input type="radio"
						onclick="javascript : document.getElementById('desc1').style.display='none'"
						value="0" name="azione">
					</td>
					<td id="desc1" style="display: none">
					Descrizione<br>
					<textarea name="azione_descrizione"></textarea>
					</td>
					<td >&nbsp;&nbsp;Contributi in
					Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008) </td>
					<td >&nbsp;&nbsp;
					<input type="text" name="contributi_risol_nc" value="<%=TicketDetails.getContributi_verifica_risoluzione_nc()%>">
</td>
				</tr>
		</table>
</td>
</tr>



<tr id="hidden1" style="display: none" class="containerBody">
		<td class="formLabel">Sistema Allarme Rapido</td>
		<td>
		<table class= "noborder">
		<tr>
		<td>Codice Allerta</td>
		<td>
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="id_allerta" name="idAllerta" value="<%=TicketDetails.getCodiceAllerta()%>">
			<font color="red">*</font> &nbsp;[<a href="javascript:popLookupSelectorAllerta('id_allerta','name','ticket','');">
			<dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] 
			<input type="hidden" id="ticketid" <%if (request.getAttribute("ticketidd") != null) {%> value="<%=request.getAttribute("ticketidd")%>" <%} else {%> value="0" <%}%> name="ticketidd">
		</td>
		<td>&nbsp;&nbsp;
		Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008) 
		</td>
		<td >
		<input type="text" name="contributi_allarme_rapido" value="<%=TicketDetails.getContributi_allarme_rapido()%>">
		</td>
		
		</tr>
		
		</table>
		
		</td>
</tr>

<tr id="macellazione" style="display: none"  class="containerBody">
		<td  class="formLabel">
			Macellazione Privata
			</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br></td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_macellazione" value="<%=TicketDetails.getContributi_macellazione_urgenza()%>"></td>
				</tr>
		</table>
</td>
</tr>

<tr id="macellazione_urgenza" style="display: none"  class="containerBody">
		<td  class="formLabel">
			Macellazione D'Urgenza
			</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br></td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_macellazione_urgenza" value="<%=TicketDetails.getContributi_macellazione()%>"></td>
				</tr>
		</table>
</td>
</tr>

<tr id="importazionescambio" style="display: none" class="containerBody">
		<td class="formLabel">
			Controllo Importazione Scambio</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_importazione_scambio" value="<%=TicketDetails.getContributi_macellazione()%>"></td>
				</tr>
		</table>
</td>
</tr>
<tr  style="display: none" id="tossinfezione" class="containerBody"> 
		<td class="formLabel"> Motivi Tossinfezione</td>
		<td>
		<table class = "noborder">
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>Soggetti Coinvolti</td>
					<td><input type="text" name="soggettiCoinvolti"></td>
				</tr>
				<tr>
					<td>Di cui Ricoverati</td>
					<td><input type="text" name="ricoverati"></td>
				</tr>
				<tr>
					<td>Alimenti Sospetti</td>
					<td><textarea name="alimentiSospetti" rows="4" cols="30"></textarea></td>
				</tr>
				<tr>
					<td>Data insorgenza Sintomi</td>
					<td><zeroio:dateSelect form="addticket" field="dataSintomi" timestamp="<%=TicketDetails.getDataSintomi()%>" timeZone="<%=TicketDetails.getDataSintomiTimeZone()%>" showTimeZone="false" /></td>
				</tr>
				<tr>
					<td>Data ingestione pasto Sospetto</td>
					<td><zeroio:dateSelect form="addticket" field="dataPasto" timestamp="<%=TicketDetails.getDataPasto()%>" timeZone="<%=TicketDetails.getDataPastoTimeZone()%>" showTimeZone="false" /></td>
				</tr>
			</table>
</td>
</tr>


<tr id="seguitodicampionamento" style="display: none" class="containerBody">
		<td class="formLabel">
			A seguito di Campioni/tamponi nc
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi" value="<%=TicketDetails.getContributi_seguito_campioni_tamponi()%>"></td>
				</tr>
		</table>
</td>
</tr>

<tr id="svincolisanitari" style="display: none" class="containerBody">
		<td class = "formLabel" >
			Svincoli Sanitari</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>Data Preavviso</td>
					<td>
					<zeroio:dateSelect form="addticket" field="data_preavviso"
		timestamp="<%=TicketDetails.getData_preavviso()%>"
		showTimeZone="false" />
					</td>
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
					<zeroio:dateSelect form="addticket" field="data_comunicazione_svincolo"
		timestamp="<%=TicketDetails.getData_comunicazione_svincolo()%>"
		showTimeZone="false" />
					</td>
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
						<textarea rows="5" cols="33" name="tipologia_sottoprodotto"></textarea>
					</td>
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
<tr id="rilascio_certificazione" style="display: none" class="containerBody">
		<td class="formLabel">
			Rilascio Certificazione</td>
		<td>
		<table class = "noborder">
				<tr>
				<td>&nbsp;&nbsp;Contributi in Euro <br>(solo nei casi in cui è previsto <br> dal D.Lgs 194/2008)</td>
					<td>&nbsp;&nbsp;<input type="text" name="contributi_rilascio_certificazione" value="<%=TicketDetails.getContributi_rilascio_certificazione()%>"></td>
				</tr>
		</table>
</td>
</tr>





<tr id ="row_piano" class="containerBody" style="display: none" >
		<td nowrap class="formLabel">Piano Di Monitoraggio</td>
		<td>
		<input type = "hidden" name = "num_piani" id = "num_piani" value = "0"/>
		
		<%
			UserBean user = (UserBean) session.getAttribute("User");
			%>
		&nbsp;[<a href="javascript:popLookupSelectorCustomPianiMonitoraggioCu('description','short_description','lookup_piano_monitoraggio','',<%=user.getSiteId() %>,document.addticket.piano_monitoraggio);"><label id = "link_selezona_piano" >Seleziona Piano Monitoraggio</label></a>] 
      		
		<br/><br/>
		<table class = "noborder">
		<tr id="clonepiano" style="display: none">
		<td>
		<input type = "hidden" name = "piano_monitoraggio" id = "piano_value" value = "-1">
		
		<label id = "piano"><b>[ Selezionare almeno un piano ]</b></label> <font color = "red">*</font></td></tr>
		</table>
		</td>
</tr>
<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label
		name="sanzionia.data_richiesta">Data Inizio Controllo</dhv:label></td>
	<td>

<input readonly type="text" id="assignedDate" name="assignedDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
		</a>
<font color="red">



	<dhv:permission name="cu-pregresso-view" >
		Inserimento cu Pregresso <input type = "checkbox" name = "cu_pregresso" id = "cu_pregresso" value = "1">
	</dhv:permission>
	</td>
</tr>


<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Data Fine Controllo</dhv:label>
	</td>
	<td>

	
		
		<input readonly type="text" id="dataFineControllo" name="dataFineControllo" size="10" 
		value="<%= (TicketDetails.getDataFineControllo()==null)?(""):(getLongDate(TicketDetails.getDataFineControllo()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFineControllo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
</td>
</tr>

<tr>
	<td valign="top" class="formLabel"><dhv:label name="sanzioni.note">Note</dhv:label>
	</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
			<td><textarea name="problem" cols="55" rows="8"><%=toString(TicketDetails.getProblem())%></textarea>
			</td>
			<td valign="top"><%=showAttribute(request, "problemError")%></td>
		</tr>
	</table>
	</td>
</tr>

<tr id="oggetto_controllo" style="display: none">
	<td valign="top" class="formLabel">Oggetto del Controllo</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
		
		<td rowspan="3" id = "sorveglianza" style="display: none">
		<%=IspezioneMacrocategorie.getHtmlSelect("ispezione_macro",-1) %>
		<font color="red">*</font>
		</td>
		
		
			<td rowspan="3" id = "non_sorveglianza" style="display: block">
			<select name="ispezione"  multiple="multiple" size="10" id="ispezione"
				onmouseout="abilitaNoteDescrizioni();abilitaSpecieTrasportata();">
				<%
					Iterator<Integer> itLista = Ispezione.keySet().iterator();
					while (itLista.hasNext()) {
						int key = itLista.next();
				%>
				<optgroup label="<%=IspezioneMacrocategorie.getValueFromId(key)%>" style="color: blue"></optgroup>

				<%
					HashMap<Integer, String> l = (HashMap<Integer, String>) Ispezione.get(key);
						Iterator<Integer> itL = l.keySet().iterator();
						while (itL.hasNext()) {
							int code = itL.next();
							String desc = l.get(code);
							boolean sel = false;

							if (request.getAttribute("listaControlliIspezione")!=null)
							{
								ArrayList<Integer> lista = (ArrayList<Integer>) request.getAttribute("listaControlliIspezione");
								if (lista != null) {
									for (int code2 : lista) {

										if (code2 == code) {
											sel = true;
										}
									}
								}
							}
								
				%>


				<option value="<%=code%>" <%if (sel == true) {%> selected="selected"
					<%}%>><%=desc%></option>
				<%
					}

					}
				%>
			</select><font color="red">*</font></td>
			<td>&nbsp;</td>
			<td>
			<table>
				<tr id="desc_note1" style="display: none">
					<td><b>Settore Alimenti per il consumo Umano (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc1"></textarea></td>
				</tr>
				<tr id="desc_note2" style="display: none">
					<td><b>Settore alimenti Zootecnici (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc2"></textarea></td>
				</tr>
				<tr id="desc_note3" style="display: none">

					<td><b>Settore Benessere Animale non durante il trasporto</b><br>

					<td><b>Settore Benessere Animale (Descrizione)</b><br>

					<textarea rows="3" cols="20" name="ispezioni_desc3"></textarea></td>
				</tr>
				<tr id="desc_note4" style="display: none">
					<td><b>Settore Sanita animale (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc4"></textarea></td>
				</tr>
				<tr id="desc_note5" style="display: none">
					<td><b>Settore S.O.A. negli Impianti di trasformazione (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc5"></textarea></td>
				</tr>
				<tr id="desc_note6" style="display: none">
					<td><b>Settore Rifiuti S.O.A. nelle altre imprese (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc6"></textarea></td>
				</tr>
				<tr id="desc_note7" style="display: none">
					<td><b>Altro (Descrizione)</b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc7"></textarea></td>
				</tr>
				<tr id="desc_note8" style="display: none">
					<td><b>Settore benessere animale durante il trasporto </b><br>
					<textarea rows="3" cols="20" name="ispezioni_desc8"></textarea></td>
				</tr>
				
			</table>
			</td>

		</tr>
	</table>
	</td>
</tr>

<tr id="specieT" style="display: none">
	<td nowrap class="formLabel">
		<dhv:label name="">Specie Animali Trasportati</dhv:label>
	</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
			<td rowspan="3">
			
			</td>
			
			
				<%SpecieA.setJsEvent("onmouseout= abilitaNumCapi();"); %>
				<td><%=SpecieA.getHtmlSelect("animalitrasp",-1) %>
				&nbsp; <font color="red">*</font></td>
			
			
			<td>&nbsp;</td>
			<td>
			<table>
			<tr id="num_capo1" style="display: none">
					<td><b>Num. Bovini</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie1" name="num_specie1" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Bovini può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo2" style="display: none">
				<td><b>Num. Suini</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie2" name="num_specie2" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Suini può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo3" style="display: none">
					<td><b>Num. Equidi</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie4" name="num_specie3" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Equidi può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo4" style="display: none">
					<td><b>Num. Altre Specie </b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie6" name="num_specie4" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Altre Specie può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo5" style="display: none">
					<td><b>Num. Bufali </b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie10" name="num_specie5" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Bufali può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo6" style="display: none">
					<td><b>Num. Pesci acqua dolce</b><br>
					<input type="number" pattern="[0-9]" min="1"  id="num_specie11" name="num_specie6" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pesci acqua dolce può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo7" style="display: none">
					<td><b>Num. Pesci Ornamentali</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie12" name="num_specie7" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pesci Ornamentali può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo8" style="display: none">
					<td><b>Num. Oche</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie13" name="num_specie8" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Oche può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
 			<tr id="num_capo9" style="display: none">
					<td><b>Num. Conigli</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie14" name="num_specie9" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Conigli può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo10" style="display: none">
					<td><b>Num. Ovaiole</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie15" name="num_specie10" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Ovaiole può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo11" style="display: none">
					<td><b>Num. Broiler</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie16" name="num_specie11" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Broiler può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo12" style="display: none">
					<td><b>Num. Vitelli</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie18" name="num_specie12" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Vitelli può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo13" style="display: none">
					<td><b>Num. Struzzi</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie19" name="num_specie13" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Struzzi può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo14" style="display: none">
					<td><b>Num. Cani</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie20" name="num_specie14" size="5"  onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Cani può contenere solo valori numerici');"/><font color="red">*</font></td>
			</tr>
			<tr id="num_capo15" style="display: none">
					<td><b>Num. Ovicaprini</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie21" name="num_specie15" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Ovicaprini può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
				
			</table>
			</td>

		</tr>
	</table>
	</td>
</tr>
 <input type = "hidden" id = "modificabile" name  = "modificabile" value = "yes">
<input type="hidden" name="ncrilevate" value="2" />
<!-- nucleo ispettivo -->
<%@ include file="nucleo_ispettivo_modify.jsp"%>



