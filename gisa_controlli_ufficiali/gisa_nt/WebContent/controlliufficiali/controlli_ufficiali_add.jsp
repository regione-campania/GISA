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




<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script language="JavaScript">


var res;
	function load_linee_attivita_per_org_id_callback(returnValue) {
		  campo_combo_da_costruire = returnValue [2];
		  var select = document.getElementById(campo_combo_da_costruire); //Recupero la SELECT
	      
	      //Azzero il contenuto della seconda select
	      for (var i = select.length - 1; i >= 0; i--)
	    	  select.remove(i);

	      var NewOpt = document.createElement('option');
	      NewOpt.value = -1; // Imposto il valore
	      NewOpt.text = "-- SELEZIONARE UNA LINEA DI ATTIVITA --" // Imposto il testo

	     // if (returnValue [3]==indici[j])
	    	//  NewOpt.selected = true;
			
	      //Aggiungo l'elemento option
	      try
	      {
	    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	      } catch(e){
	    	  select.add(NewOpt); // Funziona solo con IE
	      }
	
	      indici = returnValue [0];
	      valori = returnValue [1];
	      //Popolo la seconda Select
	      for(j =0 ; j<indici.length; j++){
		      //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		      var NewOpt = document.createElement('option');
		      NewOpt.value = indici[j]; // Imposto il valore
		      NewOpt.text = valori[j]; // Imposto il testo

		     // if (returnValue [3]==indici[j])
		    	//  NewOpt.selected = true;
				
		      //Aggiungo l'elemento option
		      try
		      {
		    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		      } catch(e){
		    	  select.add(NewOpt); // Funziona solo con IE
		      }
		      
	      }

	   
	}
	
	  function costruisci_rel_ateco_attivita( org_id, campo_combo_da_costruire ) {
		  //alert(org_id);
		  //alert(campo_combo_da_costruire);
		  PopolaCombo.load_linee_attivita_per_org_id(org_id , campo_combo_da_costruire, null, load_linee_attivita_per_org_id_callback)
	  }

function openUploadListaPopUp(orgId,folderId,tipoUpload){
	var res;
	var result;

	window.open('PrintReportVigilanza.do?command=UploadLista&tipo='+tipoUpload+'&orgId='+orgId+'&folderId='+folderId,null,
		'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		} 
		
function openUploadListaPopUpDocumentale(orgId,folderId,tipoUpload){
	var res;
	var result;

		window.open('GestioneAllegatiUpload.do?command=PrepareUploadLista&tipo='+tipoUpload+'&tipoAllegato='+tipoUpload+'&orgId='+orgId+'&folderId='+folderId,null,
		'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		} 

</script>

<script type="text/javascript">


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
function mostraUo(index)
{
	
	document.getElementById('uo_'+index).style.display="";

}

var isPianomonitoraggio = false ; 
var isSorveglianza = false ;
function controlloCuSorveglianza()
{

	loadModalWindow();
	
	entratoinpiano = false ;
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



tipoIspezione = document.getElementsByName('tipoIspezione');
if(tipoIspezione!=null)
{
for (i=0 ; i<tipoIspezione.length;i++)
{

	
	getCodiceInternoTipoIspezione(tipoIspezione[i].value.split(";")[0]);
	if (codiceInternoTipoIspezione == '2a' && entratoinpiano == false)
		{
			isPianomonitoraggio = true ;
			entratoinpiano=true ;
		
		}
}
}
if (document.addticket.tipoCampione.value == '5')
{
	
	isSorveglianza = true ;
}

PopolaCombo.controlloInserimentoCuSorveglianza(orgId, isPianomonitoraggio,isSorveglianza,document.addticket.assignedDate.value,assetId, {callback:viewMessageCallback1,async:false } );
return formTest;
}

function controlloCuSorveglianzaAcque()
{

	loadModalWindow();
	isPianomonitoraggio = false ;
	isSorveglianza = false ; 
if (document.addticket.assignedDate.value == '')
	return checkFormAcque(document.addticket);
orgId = document.addticket.orgId.value;
assetId = -1 ;
if (document.addticket.assetId!=null)
{
	assetId = document.addticket.assetId.value;
}

tipoIspezione = document.getElementsByName('tipoIspezione');
if(tipoIspezione!=null)
{
for (i=0 ; i<tipoIspezione.length;i++)
{
if(tipoIspezione.options[i].selected==true)
{
	
	getCodiceInternoTipoIspezione(tipoIspezione.options[i].value);
	if (codiceInternoTipoIspezione == '2a')
		isPianomonitoraggio = true ;
}

}
}

if (document.addticket.tipoCampione.value == '5')
{
	
	isSorveglianza = true ;
}

PopolaCombo.controlloInserimentoCuSorveglianza(orgId, isPianomonitoraggio,isSorveglianza,document.addticket.assignedDate.value,assetId, {callback:viewMessageCallback1Acque,async:false } );
return formTest;
}


function mostraStrutturaAsl()
{
tipoIspezione = document.getElementById('tipoCampione').value ;
select=document.getElementById('uo') ;


document.getElementById('nucleoIspettivo').style.display="";

if(document.getElementById('nucleoIspettivoSettato')!=null)
	document.getElementById('nucleoIspettivoSettato').style.display="none";


if (tipoIspezione == '4' || tipoIspezione == '3')
{
	document.getElementById('per_conto_di').style.display="none";
	
	if(document.getElementById('per_conto_di_settato')!=null)
	document.getElementById('per_conto_di_settato').style.display="none";
	
	
	

}
else
{
	if(tipoIspezione=='2')
		{
		
		document.getElementById('nucleoIspettivo').style.display="none";
		document.getElementById('per_conto_di').style.display="none";
		if(document.getElementById('per_conto_di_settato')!=null)
			document.getElementById('per_conto_di_settato').style.display="";
		if(document.getElementById('nucleoIspettivoSettato')!=null)
			document.getElementById('nucleoIspettivoSettato').style.display="";

		
		
		}
	else
		{
	document.getElementById('per_conto_di').style.display="";
	if(document.getElementById('per_conto_di_settato')!=null)
		document.getElementById('per_conto_di_settato').style.display="none";
		}

	
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

    if (messaggio1 != null && messaggio1 != "") 
	{
		alert('ATTENZIONE : non è possibile inserire un nuovo controllo in Sorveglianza. Esistono controlli ufficiali in sorveglianza aperti o inseriti lo stesso giorno. Controllare prima i seguenti controlli: \n' + messaggio1);
		flag=false;
	}
    	
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
    			if (isPianomonitoraggio==false)
    			{

    				alert(messaggio3 );

    			}
    			else
    			{
    				alert(messaggio3 );

    			}
    		}
    	}
    		
    	if ((messaggio5 != null && messaggio5 != "" ) ) 
    	{
    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    		if (flag == true)
    		{
    			if (isPianomonitoraggio==false)
    			{

    				alert(messaggio5 );

    			}
    			else
    			{
    				alert(messaggio5 );

    			}
    		}
    	}
    	
    	

    	if ((messaggio2 != null && messaggio2 != "" && isSorveglianza==true) ) 
    	    	{
    	    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    	    		if (flag == true)
    	    		{
    	    			

    	    				alert('Attenzione non è possibile inserire Controlli in Sorveglianza Ravvicinati.' );

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
    	loadModalWindowUnlock();
    	if(messaggio2 != null && messaggio2 != "" )
    	//CASO MESSAGGIO 2 valorizzato: la data del prossimo controllo è successiva alla data in cui tenti
    	//di inserire il controllo.
    		
    	return false;
    }
   
    }
    
    
    
function viewMessageCallback1Acque(returnValue) {

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
    			if (isPianomonitoraggio==false)
    			{

    				alert(messaggio3 );

    			}
    			else
    			{
    				alert(messaggio3 );

    			}
    		}
    	}
    		
    	if ((messaggio5 != null && messaggio5 != "" ) ) 
    	{
    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    		if (flag == true)
    		{
    			if (isPianomonitoraggio==false)
    			{

    				alert(messaggio5 );

    			}
    			else
    			{
    				alert(messaggio5 );

    			}
    		}
    	}
    	
    	

    	if ((messaggio2 != null && messaggio2 != "" && isSorveglianza==true) ) 
    	    	{
    	    		//mssg = "Se la richiesta per l'inserimento dei controlli è per l'anno corrente cliccando su OK, sarà possibile proseguire con l'operazione.\n Viceversa, non sarà possibile proseguire." ;
    	    		if (flag == true)
    	    		{
    	    			

    	    				alert('Attenzione non è possibile inserire Controlli in Sorveglianza Ravvicinati.' );

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
    				return checkFormAcque2(document.addticket);
    		}
    if (flag == true)
    {
        
    	
    return checkFormAcque2(document.addticket);
    }
    else{
    	loadModalWindowUnlock();
    	if(messaggio2 != null && messaggio2 != "" )
    	//CASO MESSAGGIO 2 valorizzato: la data del prossimo controllo è successiva alla data in cui tenti
    	//di inserire il controllo.
    		
    	return false;
    }
   
    }

</script>

<%

TipoAudit.setMultiple(true);

TipoAudit.setSelectSize(5);
%>

<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>"/>

<%
		UserBean utente1 = (UserBean)session.getAttribute("User");
%>
<input type="hidden" name="role_user" id="role_user" value="<%=utente1.getUserRecord().getRoleId() %>">

<div id="opendialog"></div>	

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
				if(utente.getSiteId()<0 && OrgDetails.getSiteId()>0 && OrgDetails.getSiteId()!=16 && OrgDetails.getTipologia() != 22)
				{
					%>
					<%=SiteIdList.getSelectedValue(OrgDetails.getSiteId())%> 
					<input type="hidden" name="siteId" id = "siteId" value="<%=OrgDetails.getSiteId()%>">
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

<%System.out.println("URL"+TicketDetails.getURlDettaglio()); %>

<%

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

else if (request.getAttribute("ViewLdA")!=null){
%>

<%@ include file="../controlliufficiali/linea_attivita_imprese.jsp" %>

<%	
}

%>
<%-- 
<%

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

else if (request.getAttribute("ViewLdA")!=null){
%>

<%@ include file="../controlliufficiali/linea_attivita_imprese.jsp" %>

<%	
}

%>
--%>

<%@ include file="../controlliufficiali/controlli_ufficiali_add_info.jsp" %>


<tr><td colspan="2"><div id="datiEstesi" name="datiEstesi"></div></td></tr>
<%@ include file="../controlliufficiali/controlli_ufficiali_info_extra2.jsp" %>


<%=showError(request, "unitaOperativaPerContoDiError")%>

<input type="hidden" id="tipoOperatore" name="tipoOperatore" value="<%=request.getAttribute("tipologia") %>"/>
