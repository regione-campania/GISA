<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.macellazioni.base.Condizione"%>
<%@page import="org.aspcfs.modules.acquedirete.actions.AcqueReteVigilanza"%>
<%@page import="org.aspcfs.modules.dpat2019.base.PianoMonitoraggio"%>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.utils.PopolaCombo"%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OggettoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Condizionalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="VerificaQuantitativo" class="org.aspcfs.utils.web.LookupList" scope="request" />	

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/NucleoIspettivo.js"> </script>
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

 


function controlloCuSorveglianza()
{

	loadModalWindow();
	
if (document.addticket.assignedDate.value == '')
	return checkForm(document.addticket);
orgId = document.addticket.orgId.value;
assetId = -1 ;
if (document.addticket.assetId!=null)
{
	assetId = document.addticket.assetId.value;
}




PopolaCombo.controlloInserimentoCuSorveglianza(orgId, false,false,document.addticket.assignedDate.value,assetId, {callback:viewMessageCallback1,async:false } );
return formTest;
}



function mostraStrutturaAsl()
{
tipoIspezione = document.getElementById('tipoCampione').value ;
select=document.getElementById('uo') ;

if (tipoIspezione == '4')
{
	document.getElementById('per_conto_di').style.display="none";
	
	
	

}
else
{
	document.getElementById('per_conto_di').style.display="";

	
}



}

function viewMessageCallback1(returnValue) {

    messaggio1 = returnValue[0];
    messaggio2 = returnValue[1];
    messaggio3 = returnValue[2];
    messaggio4 = returnValue[3];
    messaggio5 = returnValue[4];
    messaggio6 = returnValue[5];

    role = document.getElementById("role_user").value;
    flag = true ;

 
 
    
    
    	if (messaggio4!='')
    	{
    		alert(messaggio4);
    		flag=false;
    	}
    	if(messaggio2!="" || messaggio3!="" || messaggio5!="")
    	{
    		
    		/**
    		 * SE DATA INIZIO CONTROLLO è ANTECEDENTE ALLA DATA PROSSIMO CONTROLLO - 30 GG
    		 * IL SISTEMA GENERERà UN MESSAGGIO NON BLOCCANTE.
    		 * (VALE SOLO PER I CONTROLLI IN SORVEGLIANZA)
    		 */
    	
    	/**
    	 * SE DATA INIZIO CONTROLLO è INFERIORE ALLA DATA ATTUALE -30 GIORNI IL SISTEMA SEGNALA UN
    	 * MESSAGGIO NON BLOCCANTE (IL MESSAGGIO SARA BLOCCANTE A PARTIRE DA GENNAIO 2012)
    	 * VALE PER TUTTI I TIPI DI CONTROLLO
    	 */
    	 
    	//messaggio3 è legato all'inserimento dei controlli per l'anno 2014...
    	//messaggio5 è legato all'inserimento dei controlli per l'anno 2013...
    	
    	if ((messaggio3 != null && messaggio3 != "" ) ) 
    	{
    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    		if (flag == true)
    		{
    			
    				alert(messaggio3 );

    			
    		}
    	}
    		
    	if ((messaggio5 != null && messaggio5 != "" ) ) 
    	{
    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    		if (flag == true)
    		{
    			
    				alert(messaggio5 );

    			
    		}
    	}
    	
    	

    	if ((messaggio2 != null && messaggio2 != "" && isSorveglianza==true) ) 
    	    	{
    	    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    	    		if (flag == true)
    	    		{
    	    			

    	    				alert('Attenzione non è possibile inserire Controlli in Sorveglianza Ravvicinati. Controllo inseribile a partire dal'+messaggio2 );

    	    			    		}
    	    	}

    		
    		if(document.getElementById("cu_pregresso")==null || (document.getElementById("cu_pregresso")!= null && document.getElementById("cu_pregresso").checked == false))
    			flag = false;
    		else
    			if(document.getElementById("cu_pregresso").checked==true && messaggio5 =='')
    				flag = true;
    			else
    				{
    				if(document.getElementById("cu_pregresso").checked==true && messaggio5 !=''){
    					
    					flag = false;
    				}
    				//pregresso 2013 di nurecu non per autorità competenti
    				if(document.getElementById("cu_pregresso").checked==true && messaggio5 !='' ){
    					
    					flag = false;
    				}
    					
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
    else{
    	if(messaggio2 != null && messaggio2 != "" )
    	//CASO MESSAGGIO 2 valorizzato: la data del prossimo controllo è successiva alla data in cui tenti
    	//di inserire il controllo.
    		alert('ATTENZIONE! La data del prossimo controllo in sorveglianza che è possibile inserire in G.I.S.A. per questo operatore è '+messaggio2+'.');	
    	return false;
    }
   
    }

</script>



<%
		UserBean utente1 = (UserBean)session.getAttribute("User");
%>
<input type="hidden" name="role_user" id="role_user" value="<%=utente1.getUserRecord().getRoleId() %>">



<dhv:include name="stabilimenti-sites" none="true">
	<%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
	<tr>
		<td nowrap class="formLabel"><dhv:label name="stabilimenti.site">Site</dhv:label>
		</td> 
		<td>
		<%
		UserBean utente = (UserBean)session.getAttribute("User");
		if (utente.getSiteId()>0){ %>
		<%=SiteIdList.getSelectedValue(utente.getSiteId())%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=utente.getSiteId()%>">
		
		<%}
		else
		{
			if(utente.getSiteId()<0 && request.getAttribute("TipoOia")!=null)
			{
				if(OrgDetails.getSiteId()>0)
				{ 
				%>
				<%=SiteIdList.getSelectedValue(OrgDetails.getSiteId())%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=OrgDetails.getSiteId()%>">
		<%}
		else
		{%>
		REGIONE
		<input type="hidden" name="siteId" id = "siteId" value="14">
		<%}
				
				
			}else
			{
				if(User.getUserRecord().getGruppo_ruolo()==2 && OrgDetails.getSiteId()>0)
				{
					%>
					<%=SiteIdList.getSelectedValue(OrgDetails.getSiteId())%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=OrgDetails.getSiteId()%>">
					<%
					
				}
				else{
			
			%>
				<%=SiteIdList.getHtmlSelect("siteId",-1)%>
			<%}
		}}
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
	<td nowrap class="formLabel"><dhv:label name="">Tecnica di controllo</dhv:label>
	</td>
	<td><%=TipoCampione.getHtmlSelect("tipoCampione", 4)%></td>
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


<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label
		name="sanzionia.data_richiesta">Data Inizio Controllo</dhv:label></td>
	<td>
<font color="red">*</font>

<input readonly type="text" id="assignedDate" name="assignedDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
		</a>
<font color="red"><%= showError(request, "assignedDateError") %>


	<dhv:permission name="cu-pregresso-view" >
		Inserimento cu Pregresso <input type = "checkbox" name = "cu_pregresso" id = "cu_pregresso" value = "1">
		<!-- A seguito di una richiesta da parte dell'ORSA di poter inserire i CU degli anni precedenti
		verrà commentato quanto riportato tra parentesi e dato il permesso.
			(non e' possibile inserire controlli per gli anni precedenti)
		 -->
	</dhv:permission>
	
	</td>
</tr>


<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Data Fine Controllo</dhv:label>
	</td>
	<td>

	
		
		<input readonly type="text" id="dataFineControllo" name="dataFineControllo" size="10" />
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

<tr id="oggetto_controllo">
	<td valign="top" class="formLabel">Oggetto del Controllo</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
		
		<td rowspan="3" id = "sorveglianza" style="display: none">
		<%=IspezioneMacrocategorie.getHtmlSelect("ispezione_macro",-1) %>
		<font color="red">*</font>
		</td>
		
		
			<td rowspan="3" id = "non_sorveglianza" style="display: block">
			<select name="ispezione" <%if (TipoIspezione.size()>1){ %> multiple="multiple" size="10"<%} %> id="ispezione"
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
					<td><b>Num. capi Bovini</b><br>
				<input type="number" pattern="[0-9]"   min="1" id="num_specie1" name="num_specie1" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Bovini può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo2" style="display: none">
				<td><b>Num. capi Suini</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie2" name="num_specie2" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Suini può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo3" style="display: none">
					<td><b>Num. capi Equidi</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie4" name="num_specie3" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Equidi può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo4" style="display: none">
					<td><b>Num. capi Altre Specie </b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie6" name="num_specie4" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Altre Specie può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo5" style="display: none">
					<td><b>Num. capi Bufali </b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie10" name="num_specie5" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Bufali può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo6" style="display: none">
					<td><b>Num. Pesci acqua dolce</b><br>
					<input type="number" pattern="[0-9]"   min="1"  id="num_specie11" name="num_specie6" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pesci acqua dolce può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo7" style="display: none">
					<td><b>Num. Pesci Ornamentali</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie12" name="num_specie7" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pesci Ornamentali può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo8" style="display: none">
					<td><b>Num. Oche</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie13" name="num_specie8" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Oche può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
 			<tr id="num_capo9" style="display: none">
					<td><b>Num. Conigli</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie14" name="num_specie9" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Conigli può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo10" style="display: none">
					<td><b>Num. Ovaiole</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie15" name="num_specie10" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Ovaiole può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo11" style="display: none">
					<td><b>Num. Broiler</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie16" name="num_specie11" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Broiler può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo12" style="display: none">
					<td><b>Num. capi Vitelli</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie18" name="num_specie12" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Vitelli può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo13" style="display: none">
					<td><b>Num. Struzzi</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie19" name="num_specie13" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Struzzi può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo14" style="display: none">
					<td><b>Num. Cani</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie20" name="num_specie14" size="5"  onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Cani può contenere solo valori numerici');"/><font color="red">*</font></td>
			</tr>
			<tr id="num_capo15" style="display: none">
					<td><b>Num. capi Ovicaprini</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie21" name="num_specie15" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi Ovicaprini può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo22" style="display: none">
					<td><b>Num. capi di Pollame</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie22" name="num_specie22" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. capi di Pollame può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo23" style="display: none">
					<td><b>Num. Pesci</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie23" name="num_specie23" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pesci può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo24" style="display: none">
					<td><b>Num. Uccelli</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie24" name="num_specie24" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Uccelli può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo25" style="display: none">
					<td><b>Num. Rettili</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie25" name="num_specie25" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Rettili può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo26" style="display: none">
					<td><b>Num. capi Altre specie</b><br>
				<input type="number" pattern="[0-9]"   min="1"  id="num_specie26" name="num_specie26" size="5" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Altro può contenere solo valori numerici');" /><font color="red">*</font></td>
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

