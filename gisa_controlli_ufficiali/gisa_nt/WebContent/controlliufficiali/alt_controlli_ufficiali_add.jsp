<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.utils.PopolaCombo"%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OggettoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Condizionalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="VerificaQuantitativo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />	

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


<script>

function openUploadListaPopUp(orgId,folderId,tipoUpload){
	var res;
	var result;

	
		window.open('PrintReportVigilanza.do?command=UploadLista&tipo='+tipoUpload+'&orgId='+orgId+'&folderId='+folderId,null,
		'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		
		} 

</script>

<script type="text/javascript">

var dataCu='' ;      
 
function setuo(campo){
	if(campo.id=='uo1')
	{
	i = 1 ;
	
	while (document.getElementById('uo'+i)!=null)
	{
		document.getElementById('uo'+i).value = document.getElementById('uo1').value ;
		i++;
	}
	}
		
}

function setVeterinario (indice,val)
{
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

	//loadModalWindow();
	
	entratoinpiano = false ;
	isPianomonitoraggio = false ;
	isSorveglianza = false ; 
if (document.addticket.assignedDate.value == '')
	return checkForm(document.addticket);
	
	if (document.addticket.altId!=null)
		altId = document.addticket.altId.value;

assetId = -1 ;
if (document.addticket.assetId!=null)
{
	assetId = document.addticket.assetId.value;
}



tipoIspezione = document.getElementsByName('tipoIspezione');
for (i=0 ; i<tipoIspezione.length;i++)
{

	
	getCodiceInternoTipoIspezione(tipoIspezione[i].value.split(";")[0]);
	if (codiceInternoTipoIspezione == '2a' && entratoinpiano == false)
		{
			isPianomonitoraggio = true ;
			entratoinpiano=true ;
		
		}


}

if (document.addticket.tipoCampione.value == '5')
{
	
	isSorveglianza = true ;
}

PopolaCombo.controlloInserimentoCuSorveglianzaAlt(altId, isPianomonitoraggio,isSorveglianza,document.addticket.assignedDate.value,assetId, {callback:viewMessageCallback1,async:false } );
return formTest;
}



function mostraStrutturaAsl()
{
tipoIspezione = document.getElementById('tipoCampione').value ;
select=document.getElementById('uo') ;

if (tipoIspezione == '4' || tipoIspezione == '3')
{
	document.getElementById('per_conto_di').style.display="none";
	/*document.getElementById('uo').multiple=false;
	document.getElementById('uo').size="1";

	for (var i = select.length - 1; i >= 0; i--)
	{
		
		if(select.options[i].value == '-1')
	   	  	select.remove(i);
	}
	var NewOpt = document.createElement('option');
    	NewOpt.value = -1; // Imposto il valore
	 	NewOpt.text = 'Selezionare una voce'; // Imposto il testo
	 	NewOpt.selected=true ;
    //Aggiungo l'elemento option
    try
    {
  	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }*/
	
	
	

}
else
{
	document.getElementById('per_conto_di').style.display="";

	/*for (var i = select.length - 1; i >= 0; i--)
	{
		
		if(select.options[i].value == '-1')
	   	  	select.remove(i);
	} 	
	document.getElementById('uo').multiple=true;
	document.getElementById('uo').size="7";
	var NewOpt = document.createElement('option');
	NewOpt.value = -1; // Imposto il valore
 	NewOpt.text = 'Selezionare una o piu voci'; // Imposto il testo
 	NewOpt.selected=true ;
//Aggiungo l'elemento option
try
{
	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
}catch(e){
	  select.add(NewOpt); // Funziona solo con IE
}*/

	
}



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
		alert('ATTENZIONE : non è possibile inserire un nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza aperti o inseriti lo stesso giorno. Controllare prima i seguenti controlli: \n' + messaggio1);
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

		if (flag!=false)
			flag= true;
	} 
	/**
	 * SE DATA INIZIO CONTROLLO è INFERIORE ALLA DATA ATTUALE -30 GIORNI IL SISTEMA SEGNALA UN
	 * MESSAGGIO NON BLOCCANTE (IL MESSAGGIO SARA BLOCCANTE A PARTIRE DA GENNAIO 2012)
	 * VALE PER TUTTI I TIPI DI CONTROLLO
	 */
	if (messaggio3 != null && messaggio3 != "" ) 
	{
		mssg = "La richiesta per l'inserimento di uno o più controlli ufficiali fuori limite di tempo deve essere inviata in Regione Campania agli indirizzi di posta elettronica flussi.veterinaria@pec.regione.campania.it e p.c a   agc20.sett02@pec.regione.campania.it compilando il file 'Inserimento Controlli Ufficiali Pregresso.xls' presente in bacheca.Il nodo regionale inviera risposta all'Help Desk che vi contatterà per comunicarVi l'esito della richiesta." ;
		
		if (flag == true)
		{
			if (isPianomonitoraggio==false)
				alert(mssg );
			else
			{
				alert(mssg );
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

<input type="hidden" name="altId" value="<%=OrgDetails.getAltId()%>"/>

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
				if(OrgDetails.getIdAsl()>0)
				{
				%>
				<%=SiteIdList.getSelectedValue(OrgDetails.getIdAsl())%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=OrgDetails.getIdAsl()%>">
		<%}
		else
		{%>
		REGIONE
		<input type="hidden" name="siteId" id = "siteId" value="14">
		<%}
				
				
			}else
			{
				if(utente.getSiteId()<0 && OrgDetails.getIdAsl()>0 && OrgDetails.getIdAsl()!=16 && !"Mobile".equalsIgnoreCase(OrgDetails.getMessage()))
				{
					%>
					<%=SiteIdList.getSelectedValue(OrgDetails.getIdAsl())%> 
					<input type="hidden" name="siteId" id = "siteId" value="<%=OrgDetails.getIdAsl()%>">
					<%
				}else
				{
			
			%>
			<% SiteIdList.setJsEvent("onchange='if(document.getElementById(\"tipoCampione\").value==-1) { alert(\"Attenzione. Selezionare prima la tecnica del controllo.\"); this.value=-1;} else {for(var i=document.getElementById(\"siteId\").options.length-1;i>=0;i--){if(!document.getElementById(\"siteId\").options[i].selected) document.getElementById(\"siteId\").remove(i);}   }' "); %>
			<%=SiteIdList.getHtmlSelect("siteId",-1)%>
			<%
		}}}
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


<%@ include file="../controlliufficiali/controlli_ufficiali_add_tipo_cu.jsp" %>

<%
if (request.getAttribute("tipologia")!=null && "201".equals(""+request.getAttribute("tipologia")))
{
%>
<tr class="containerBody" id = "molluschiquantitativo" style="display: none">
		<td class="formLabel">Verifica quantitativo prodotto raccolto</td>
		<td>
		<%=VerificaQuantitativo.getHtmlSelect("quantitativo",TicketDetails.getQuantitativo()) %>
		Quintali <input type = "text" name = "quintali" id = "quintali" value = "<%=TicketDetails.getQuintali()%>">
		</td>
	</tr>

<%	
}
%>
<% if(!OrgDetails.getContainer().equals("apiari")) { 
if (OrgDetails.getContainer().equals("sintesis")){%>
 <%@ include file="../controlliufficiali/sintesis_linea_attivita_imprese.jsp" %>
 <%} else if (OrgDetails.getContainer().equals("operatoriprivati")){%>
 <%@ include file="../controlliufficiali/anagrafica_linea_attivita_imprese.jsp" %>
<%} else { %>
 <%@ include file="../controlliufficiali/opu_linea_attivita_imprese.jsp" %>
<% } } %>

<%@ include file="../controlliufficiali/controlli_ufficiali_add_info.jsp" %>

<tr><td colspan="2"><div id="datiEstesi" name="datiEstesi"></div></td></tr>
<%@ include file="../controlliufficiali/controlli_ufficiali_info_extra2.jsp" %>

<input type="hidden" id="tipoOperatore" name="tipoOperatore" value="<%=request.getAttribute("tipologia") %>"/>
