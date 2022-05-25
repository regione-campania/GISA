<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ page import="java.util.*,org.aspcfs.modules.vigilanza.base.*" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>
<%@page  import="org.aspcfs.utils.web.LookupList"%>

<%@ include file="../initPage.jsp" %>

<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js?n=2"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<jsp:useBean id="TipiControlliCani" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Bpi" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Haccp" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Ispezione" class="java.util.HashMap" scope="request" />
<jsp:useBean id="PianoMonitoraggio1" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio2" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio3" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails" class = "org.aspcfs.modules.canipadronali.base.Proprietario" scope="request" />
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request" />
<jsp:useBean id="DestinatarioCampione" 	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DestinazioneDistribuzione" 	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request" />
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request" />
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request" />
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request" />
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/confrontaDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js?n=2"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_canipadronali.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	//cal19.showNavigationDropdowns();
</SCRIPT>
        <script type="text/javascript">

        
        function removeRequired(indice,field)
        {
//         	alert(indice);
//         	alert(field.id);
        	
        	if (field.checked)
        		{
        	$( "#mc_" +indice).removeAttr("required");
        	$( "#mc_" +indice).css('background-color','lightGrey'); 
        		}
        	else
        		{
        		
        		$( "#mc_" +indice).attr("required","required");
        		$( "#mc_" +indice).css('background-color','white'); 
        		}
        	
        }
        function checkRigaCorrente(el){
        	var index = 'cane_1';
        	while( (el = el.parentNode) && el.nodeName.toLowerCase() !== 'tr' );
			  if( el ) 
               index = el.id;
        	return document.getElementById('data_nascita_'+index);
        	/* alert(campo.value);
        	var sizeCani =  document.getElementById('size_p').value;
        	return document.getElementById('data_nascita_cane_'+sizeCani); */
        	//return document.forms[0].data_nascita_cane_1;
        }
			function verificaAzienda()
			{
				PopolaCombo.verifica_esistenza_azienda(document.addticket.cod_azienda.value,verifica_esistenza_azienda_call) ;
			}
			

         

          function verifica_esistenza_azienda_call(returnValue)
          {
        	if (returnValue!='')
        	{
					document.getElementById('azienda').innerHTML="<font color='green'>Codice Azienda Trovato in Banca Dati</font>";

					
            }  
        	else
        	{
        		document.getElementById('azienda').innerHTML="<font color='red'>Codice Azienda non Trovato in Banca Dati";
        		document.getElementById('cod_azienda').value=""
            }
           }


          
          </script>

<body onload = "provaFunzione('addticket'); onloadAllerta('addticket'); resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>');"> 

 <%

	TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('addticket');");
	TitoloNucleoDue.setJsEvent("onChange=mostraCampo2('addticket')");
	PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
	AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
    TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");
    TipoCampione.setJsEvent("onChange=javascript:reloadAddCU('CaniPadronaliVigilanza.do?command=Add&tipoCampione='+this.value)"); 


%>
<script>
function showCodiceAzienda()
{
if(document.addticket.tipologia_cu.value=="5")
{
	document.getElementById("row_azienda").style.display="" ;
}
else
{
	document.getElementById("row_azienda").style.display="none" ;
}
}
function  copiaProprietario()
{
	
if (document.addticket.copia.checked == true )
{
	document.addticket.nome_conducente.value		=document.addticket.nominativo_proprietario.value ;
	document.addticket.cognome_conducente.value	=document.addticket.cf_proprietario.value ;
	document.addticket.documento_conducente.value	=document.addticket.documento_proprietario.value ;
	document.addticket.luogo_nascita_conducente.value =document.addticket.luogo_nascita_proprietario.value ;
	document.addticket.data_nascita_conducente.value =document.addticket.data_nascita_proprietario.value ;

	document.addticket.citta_conducente.value =document.addticket.citta_proprietario.value ;
	document.addticket.cap_conducente.value =document.addticket.cap_proprietario.value ;
	document.addticket.indirizzo_conducente.value =document.addticket.indirizzo_proprietario.value ;
	document.addticket.provincia_conducente.value =document.addticket.provincia_proprietario.value ;

	
}
else
{

	document.addticket.nome_conducente.value		="" ;
	document.addticket.cognome_conducente.value	="" ;
	document.addticket.documento_conducente.value	="" ;
	document.addticket.luogo_nascita_conducente.value ="" ;
	document.addticket.data_nascita_conducente.value ="";

	document.addticket.citta_conducente.value ="";
	document.addticket.cap_conducente.value ="" ;
	document.addticket.indirizzo_conducente.value ="" ;
	document.addticket.provincia_conducente.value ="" ;

}
	
}
function opensearchCaneBdr(){
	var res;
	var result;


		window.open('CaniPadronali.do?command=SearchFormCane',null,
		'height=600px,width=900px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
	
		}

function opensearchAzienda(codAzienda){
	var res;
	var result;

	if (codAzienda=='')
		alert('inserire il codice azienda')
		else
		{
	if (document.all) {
		window.open('CaniPadronali.do?command=SearchAzienda&codice_azienda='+codAzienda,null,
		'height=400px,width=780px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		} else {
		
			res = window.showModalDialog('CaniPadronali.do?command=SearchAzienda&codice_azienda='+codAzienda,null,
			'dialogWidth:780px;dialogHeight:400px;center: 1; scroll: 0; help: 1; status: 0');
		
		}
		}
		} 

function addCane(){
  
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('size_p');
  	//elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size_p');
  	size.value=parseInt(size.value)+1;
  	
  	var indice = parseInt(elementi.value) - 1;

  	
  	var clonanbsp = document.getElementById('cane_1');
  	/*clona riga vuota*/
  	clone=clonanbsp.cloneNode(true);

	clone.getElementsByTagName('TD')[0].innerHTML 	= '<b>'+size.value+'</b>' ;
	 	
	clone.getElementsByTagName('INPUT')[0].name 	= "mc_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[0].id 		= "mc_"+elementi.value;
	clone.getElementsByTagName('INPUT')[0].value 	= "" ;
	
	var ii = elementi.value;
	clone.getElementsByTagName('INPUT')[1].id="check_"+ii;
	clone.getElementsByTagName('INPUT')[1].onclick= function(){ var j = ii;  removeRequired(j,document.getElementById("check_"+j));};
	
	
	
  	
  	clone.getElementsByTagName('INPUT')[2].name = "razza_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[2].id = "razza_"+elementi.value;
	clone.getElementsByTagName('INPUT')[2].value 	= "" ;

  	
  	clone.getElementsByTagName('INPUT')[3].name = "taglia_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[3].id = "taglia_"+elementi.value;
	clone.getElementsByTagName('INPUT')[3].value 	= "" ;

	
  	clone.getElementsByTagName('INPUT')[4].name = "mantello_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[4].id = "mantello_"+elementi.value;
	clone.getElementsByTagName('INPUT')[4].value 	= "" ;

	
  	clone.getElementsByTagName('INPUT')[5].name = "sesso_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[5].id = "sesso_"+elementi.value;
	clone.getElementsByTagName('INPUT')[5].value 	= "" ;

  	clone.getElementsByTagName('INPUT')[6].name = "data_nascita_cane_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[6].id = "data_nascita_cane_"+elementi.value;
	clone.getElementsByTagName('INPUT')[6].value = 	"" ;
	
	
	
	

  	clone.getElementsByTagName('A')[0].href = "javascript:popCalendar('addticket','data_nascita_cane_"+elementi.value+"','it','IT','Europe/Berlin')";
	
	//clone.getElementsByTagName('A')[1].href = "javascript:rimuoviCane("+elementi.value+")";
	
  	/*Aggancio il nodo*/
  	clonanbsp.parentNode.appendChild(clone);
	clone.id = "cane_" + size.value;

  }


function rimuoviCane(index){

		
		size = document.getElementById("size_p").value ;
		if (size == "1" && index==1)
		{
			document.getElementById("mc_1").value = "" ;
			document.getElementById("mantello_1").value = "" ;
			document.getElementById("razza_1").value = "" ;
			document.getElementById("sesso_1").value = "" ;
			document.getElementById("taglia_1").value = "" ;
			document.getElementById("data_nascita_cane_1").value = "" ;
		}
		
		if (document.getElementById('cane_'+index)!=null && index!=1)
		{
	  		var clonato = document.getElementById('cane_'+index);
  	  		clonato.parentNode.removeChild(clonato);
  	  		sizeC = document.getElementById('size_p');
  	  		sizeC.value=parseInt(sizeC.value)-1;
		}
	
}
  
  function simulaSubmit()
  {
	  if(document.getElementById('cap_proprietario')!=null && document.getElementById('cap_proprietario').value=='80100')
	  {
		alert("Il cap del proprietario non può essere 80100");  
	  }
	  else if(document.getElementById('cap_conducente')!=null && document.getElementById('cap_conducente').value=='80100')
	  {
		alert("Il cap del conducente non può essere 80100");  
	  }
	  
	  else if (controlloCuSorveglianza()==true )
		  {
		  document.addticket.submit();
		  }
  }
</script>



<form method="post" name="addticket" action="CaniPadronaliVigilanza.do?command=Insert&auto-populate=true" >
<%-- Trails --%>
<input type = "hidden" name ="tipooperatorecanipadronali" id = "tipooperatorecanipadronali" value="si">
<input type = "hidden" id = "elementi_p" name = "elementi_p" value = "1">
<input type = "hidden" id = "size_p" name = "size_p" value = "1">

<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="CaniPadronali.do?command=SearchForm">Anagrafica Cani di proprieta</a> > 
  <%--<a href="CaniPadronali.do?command=Detail&orgId=<%=OrgDetails.getOrgId() %>&assetId=<%=OrgDetails.getId() %>">Scheda Cane Padronale</a> >--%>
   <dhv:label name="campioni.aggiungi">Aggiungi Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>

<input type="button" value="<dhv:label name="button.inserta">Inserisci</dhv:label>" name="Save" onclick="simulaSubmit()">
<input type="button" value="Annulla" onClick="location.href='CaniPadronali.do?command=SearchForm'">
<br>
<input type="hidden" name="assetId" value="<%=OrgDetails.getOrgId()%>">

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>

<%@ include file="../controlliufficiali/controlli_ufficiali_add.jsp" %>
<input type="hidden" name="idStabilimentoopu" value="-1"/>


<tr>
		<td nowrap class="formLabel"> Situazione Controllata 
		</td>
		<%
		
		TipiControlliCani.setJsEvent("onchange=showCodiceAzienda()");
		%>
		<td><%=TipiControlliCani.getHtmlSelect("tipologia_cu",-1) %>
		</td>
	</tr>
	<tr style="display: none" id = "row_azienda">
<td nowrap class="formLabel"> Codice Azienda
		</td>
		<td><input type = "text" name = "cod_azienda" id = "cod_azienda"> &nbsp; 
		<input type ="hidden" name = "id_allevamento" id = "id_allevamento">
		<input type ="hidden" name = "ragione_sociale_allevamento" id="ragione_sociale_allevamento">
		<div id ="azienda">
		</div>
		<br>
<!-- 		<input type = "button" onclick="opensearchAzienda(document.addticket.cod_azienda.value)" value="Verifica Esistenza in BDN"> -->
		</td>
		</tr>


</table>

<br> <br><br>
&nbsp;&nbsp;&nbsp;
<input type = "button" value="Cerca in BDR" onclick="javascript:opensearchCaneBdr()">

<br><br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Proprietario</dhv:label></strong>
    </th>
	</tr>
	<tr>
		<td nowrap class="formLabel"> Nominativo
		</td>
		<td><input type = "text" name = "nominativo_proprietario" size="50" width="50"><font color="red">*</font> 
		<input type = "hidden" name = "nome_proprietario">
		<input type = "hidden" name = "cognome_proprietario">
		<input type = "hidden" name = "asl_proprietario">
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel"> Tipo(i)
		</td>
		<td>
		<input type = "text" name = "tipo_proprietario" size="50" width="50">
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel"> Codice Fiscale
		</td>
		<td><input type = "text" name = "cf_proprietario"  size="30" maxlength="16"><font color="red">*</font> 
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"> Comune di nascita
		</td>
		<td><input type = "text" name = "luogo_nascita_proprietario">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"> Data Nascita
		</td>
		<td>
			<input readonly type="text" id="data_nascita_proprietario" name="data_nascita_proprietario" size="10" />
			<a href="#" onClick="cal19.select(document.forms[0].data_nascita_proprietario,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	
       
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"> Estremi Documento
		</td>
		<td><input type = "text" name = "documento_proprietario">
		</td>
	</tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzi Proprietario</dhv:label></strong>
    </th>
	</tr>
	<tr>
		<td nowrap class="formLabel">Citta
		</td>
		<td>
		<input type ="text" required="required" name = "citta_proprietario" ><font color="red">*</font> 
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Indirizzo
		</td>
		<td>
		<input type ="text" required="required" name = "indirizzo_proprietario" ><font color="red">*</font> 
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Provincia
		</td>
		<td>
		<input type ="text" required="required" name = "provincia_proprietario" ><font color="red">*</font> 
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Cap
		</td>
		<td>
		<input type ="text" required="required" id = "cap_proprietario" name = "cap_proprietario" maxlength="5"><font color="red">*</font> 
		</td> 
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Conduttore</dhv:label></strong>
    </th>
	</tr>
	
	<tr>	
		<td nowrap class="formLabel"> Spuntare se il conduttore<br> coincide con il proprietario
		</td>
		<td><input type = "checkbox" name="copia"  onclick="javascript: copiaProprietario()">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"> Nominativo
		</td>
		<td><input type = "text" name = "nome_conducente" size="50" width="50"><font color="red">*</font> 
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel"> Codice Fiscale
		</td>
		<td><input type = "text" name = "cognome_conducente"  size="30" maxlength="16">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">Comune di nascita
		</td>
		<td><input type = "text" name = "luogo_nascita_conducente">
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">Data Nascita
		</td>
		<td>
			<input readonly type="text" id="data_nascita_conducente" name="data_nascita_conducente" size="10" />
			<a href="#" onClick="cal19.select(document.forms[0].data_nascita_conducente,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
       
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel"> Estremi Documento
		</td>
		<td><input type = "text" name = "documento_conducente">
		</td>
	</tr>
	
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzi Conduttore</dhv:label></strong>
    </th>
	</tr>
	<tr>
		<td nowrap class="formLabel">Citta
		</td>
		<td>
		<input type ="text" name = "citta_conducente" >
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Indirizzo
		</td>
		<td>
		<input type ="text" name = "indirizzo_conducente" >
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Provincia
		</td>
		<td>
		<input type ="text" name = "provincia_conducente" >
		</td> 
	</tr>
	<tr>
		<td nowrap class="formLabel">Cap
		</td>
		<td>
		<input type ="text" id = "cap_conducente" name = "cap_conducente"  maxlength="5">
		</td> 
	</tr>
</table>

<br>

<a href="javascript: addCane()"> <img src="images/espandi.gif" /> Aggiungi Cane </a> &nbsp;&nbsp;
<a href="javascript: rimuoviCane(document.getElementById('size_p').value)"> <img src="images/tree.gif" /> Elimina Cane </a>

<br/><br/>
<font color="red"><b>Inserire almeno una informazione per ogni cane controllato</b></font>

<table id="canicontrollatitrCaniPadronali" cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="7">
      <strong><dhv:label name="">Lista Cani Controllati</dhv:label></strong>
    </th>
	</tr>
	<tr>
    <th >
     Num Cane
    </th>
	<th >
     Microchip
    </th>
    <th >
     Razza
    </th>
    <th >
     Taglia
    </th>
    <th >
     Mantello
    </th>
    <th >
    Sesso
    </th>
    <th >
     Data Nascita /Data Nascita Presunta
    </th>
   
	</tr>
	<tr id="cane_1">
	<td><b>1</b></td>
		<td><input type = "text" required="required"  id = "mc_1" name = "mc_1" size="30" maxlength="15"><font color="red">*</font> 
		<input type="checkbox" onclick="removeRequired(1,this)"/> Non Registrato
		</td>
		<td><input type = "text" required="required" id = "razza_1" name = "razza_1"><font color="red">*</font> 
		</td>
		
		<td><input type = "text" required="required" id = "taglia_1" name = "taglia_1"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "mantello_1" name = "mantello_1"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "sesso_1" name = "sesso_1"><font color="red">*</font> 
		</td>
		<td><input type = "text" required="required" id = "data_nascita_cane_1" name = "data_nascita_cane_1" readonly="readonly" value=""><font color="red">*</font> 
		<input type ="hidden" name ="data_decesso_1"  id = "data_decesso_1" value="">
		
		<a href="#" onClick="cal19.select(checkRigaCorrente(this),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			</a>
		</td>
		
	</tr>
	
</table>



<br> 

&nbsp;&nbsp;&nbsp;
<input type = "button" value="Cerca in BDR" onclick="javascript:opensearchCaneBdr()">

<br><br>


<br>





<%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_add.jsp" %>
	
<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp.jsp" %>

<br>
<br>
<br>

<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione.jsp" %>
	
 
 
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
 

<input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>">
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>

<input type="button" value="<dhv:label name="button.inserta">Inserisci</dhv:label>" name="Save" onclick="simulaSubmit()">
<input type="button" value="Annulla" onClick="location.href='CaniPadronali.do?command=SearchForm'">
</form>
</body>
