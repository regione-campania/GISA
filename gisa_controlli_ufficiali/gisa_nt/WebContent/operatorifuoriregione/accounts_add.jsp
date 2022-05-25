<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_add.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.operatorifuoriregione.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Conducente" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="Mittente" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.operatorifuoriregione.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatorifuoriregione.base.Organization" scope="request"/>
<jsp:useBean id="OrgDetailsAttori" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="indirizzoImpresa" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="OrgDetailsImpresa" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Site_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation(); 
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;

  function settaHidden()
  {
  document.addAccount.address1line3.value=document.addAccount.site2.value;
  }
  function clearForm() {
	  
	 document.forms['addAccount'].name.value="";
   	 document.forms['addAccount'].codiceImpresaInterno.value="";
     document.forms['addAccount'].partitaIva.value="";
  	 document.forms['addAccount'].codiceFiscale.value="";
  	 if(document.forms['addAccount'].contoCorrente!=null)
 	 document.forms['addAccount'].contoCorrente.value="";
 	 document.forms['addAccount'].nomeCorrentista.value="";
 	 document.forms['addAccount'].codiceFiscaleRappresentante.value="";
  	 document.forms['addAccount'].nomeRappresentante.value="";
     document.forms['addAccount'].cognomeRappresentante.value="";
 	 document.forms['addAccount'].dataNascitaRappresentante.value="";
 	 document.forms['addAccount'].luogoNascitaRappresentante.value="";
	 document.forms['addAccount'].emailRappresentante.value="";
	 document.forms['addAccount'].telefonoRappresentante.value="";
	 document.forms['addAccount'].fax.value="";
	 document.forms['addAccount'].address1city.value="";
	 document.forms['addAccount'].address1line1.value="";
	 document.forms['addAccount'].address1zip.value="";
	 document.forms['addAccount'].address1state.value="";
	 document.forms['addAccount'].address1line2.value="";
	 }

	 function cambiaLabel(id){
		 
		 if(id==0){
			 document.getElementById("label_in").style.display="block";
			 document.getElementById("label_fuori").style.display="none";
			 document.forms['addAccount'].address1line2.type="hidden";
			 document.addAccount.site2.style.display="block";}
		 else{
			 document.getElementById("label_in").style.display="none";
			 document.getElementById("label_fuori").style.display="block";
			 document.forms['addAccount'].address1line2.type="text";
			 document.addAccount.site2.style.display="none";}
	 }
  
  function changeDivContent(divName, divContents) {
	    if(document.layers){
	      // Netscape 4 or equiv.
	      divToChange = document.layers[divName];
	      divToChange.document.open();
	      divToChange.document.write(divContents);
	      divToChange.document.close();
	    } else if(document.all){
	      // MS IE or equiv.
	      divToChange = document.all[divName];
	      divToChange.innerHTML = divContents;
	    } else if(document.getElementById){
	      // Netscape 6 or equiv.
	      divToChange = document.getElementById(divName);
	      divToChange.innerHTML = divContents;
	    }
	    //when the content of any of the select items changes, do something here
	    //reset the sc and asset
	    if (divName == 'changeaccount') {
	      <dhv:include name="ticket.contact" none="false">
	      if(document.forms['addticket'].orgId.value != '-1'){
	        updateLists();
	      }
	      </dhv:include>
	      <dhv:include name="ticket.contractNumber" none="false">
	      changeDivContent('addServiceContract',label('none.selected','None Selected'));
	      resetNumericFieldValue('contractId');
	      </dhv:include>
	      <dhv:include name="ticket.contractNumber" none="false">
	      changeDivContent('addAsset',label('none.selected','None Selected'));
	      resetNumericFieldValue('assetId');
	      </dhv:include>
	      <%-- dhv:include name="ticket.laborCategory" none="false">
	      changeDivContent('addLaborCategory',label('none.selected','None Selected'));
	      resetNumericFieldValue('productId');
	      </dhv:include --%>
	    }
	  }

function mostraNextIndirizzo(ind){

document.getElementById("indirizzo"+ind+"1").style.display="";
document.getElementById("indirizzo"+ind+"2").style.display="";
document.getElementById("indirizzo"+ind+"3").style.display="";
document.getElementById("indirizzo"+ind+"4").style.display="";
document.getElementById("indirizzo"+ind+"5").style.display="";
document.getElementById("indirizzo"+ind+"6").style.display="";
document.getElementById("indirizzo"+ind+"7").style.display="";
document.getElementById("indirizzo"+ind+"8").style.display="";
if(ind==2){
	document.getElementById("indirizzo"+"2"+"button").style.display="none";
	document.getElementById("indirizzo3button").style.display="";

}else{

	document.getElementById("indirizzo3button").style.display="none";
	
}


	
	

	
}


function mostraSelezione(id){
	if(id==0)

	document.getElementById("fuoriasl").disabled="";
	
	 
	

	else 

		document.getElementById("fuoriasl").disabled="disabled";
		
		
	}
   
   function updateFormElementsNew(index) {
  	
  
  	
  	if(index==1){
  	if(document.getElementById("starMobil3")!= undefined){
  		document.getElementById("starMobil3").style.display="";
  		}
  		//document.getElementById("starMobil4").style.display="";
  		
  		if(document.getElementById("starMobil10")) document.getElementById("starMobil10").style.display="";
  	}
  	else if(index==0){
  	if(document.getElementById("starMobil3")!= undefined){
  		document.getElementById("starMobil3").style.display="none";
  		}
  		//document.getElementById("starMobil4").style.display="none";
  		
  		if(document.getElementById("starMobil10")) document.getElementById("starMobil10").style.display="none";
  	
  	
  	}
  	

  	
    if (document.getElementById) {
       elm2 = document.getElementById("targaVeicolo1"); //Cognome
      /* elm3 = document.getElementById("codiceCont1"); // Nome (Organization)*/
       elm4 = document.getElementById("tipoStruttura1");
       elm5 = document.getElementById("addressLine");
       elm6 = document.getElementById("prov1");
       elm7 = document.getElementById("labelCap");
       elm8 = document.getElementById("stateProv1");
       elm9 = document.getElementById("latitude1");
       elm10 = document.getElementById("longitude1");
       
      if (index == 0) {
        resetFormElementsNew();
        
      
        
        elm1.style.color="#cccccc";
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;
        
        elm2.style.color="#cccccc";
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
    
    /*    elm3.style.color="#cccccc";
        document.addAccount.codiceCont.style.background = "#cccccc";
        document.addAccount.codiceCont.value = "";
        document.addAccount.codiceCont.disabled = true;*/
        
           elm4.style.color="#cccccc";
        document.getElementById("prov12").disabled = true;
                
        elm5.style.color="#cccccc";
        document.addAccount.addressline1.style.background = "#cccccc";
        document.addAccount.addressline1.value = "";
        document.addAccount.addressline1.disabled = true;
        
        elm6.style.color="#cccccc";
        document.getElementById("prov12").disabled = true;
        document.getElementById("prov12").selectedIndex=0;
        
        elm7.style.color="#cccccc";
        document.addAccount.addresszip.style.background = "#cccccc";
        document.addAccount.addresszip.value = "";
        document.addAccount.addresszip.disabled = true;
        
        elm8.style.color="#cccccc";
        
        elm9.style.color="#cccccc";
        document.addAccount.address3latitude.style.background = "#cccccc";
        document.addAccount.address3latitude.value = "";
        document.addAccount.address3latitude.disabled = true;
        
        elm10.style.color="#cccccc";
        document.addAccount.address3longitude.style.background = "#cccccc";
        document.addAccount.address3longitude.value = "";
        document.addAccount.address3longitude.disabled = true;
        
        elm4.style.color="#cccccc";
        document.addAccount.TipoStruttura.style.background = "#cccccc";
        document.addAccount.TipoStruttura.value = "";
        document.addAccount.TipoStruttura.disabled = true;
                
        
       
        document.getElementById("prov12").disabled = false;
        document.addAccount.check.value = "es";
        document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
        
        tipo1 = document.getElementById("tipoD");
        tipo1.checked = true;
        
        /*document.getElementById("codice1").value = "";
        document.getElementById("codice2").value = "";
        document.getElementById("codice3").value = "";
        document.getElementById("codice4").value = "";
        document.getElementById("codice5").value = "";
        document.getElementById("codice6").value = "";
        document.getElementById("codice7").value = "";
        document.getElementById("codice8").value = "";
        document.getElementById("codice9").value = "";
        document.getElementById("codice10").value = "";*/
        
        
      } else if (index == 1){
      
        resetFormElementsNew();
        
        document.addAccount.address1type.style.background = "#000000";
       	document.addAccount.address1type.disabled = false;
        
       elm5 = document.getElementById("indirizzo1");
       elm6 = document.getElementById("prov2");
       elm7 = document.getElementById("cap1");
       elm8 = document.getElementById("stateProv2");
       elm9 = document.getElementById("latitude2");
       elm10 = document.getElementById("longitude2");
       
      	 elm5.style.color="#cccccc";
        document.addAccount.indirizzo12.style.background = "#cccccc";
        document.addAccount.indirizzo12.value = "";
        document.addAccount.indirizzo12.disabled = true;
        
        elm6.style.color="#cccccc";
        document.getElementById("prov").disabled = true;
        document.getElementById("prov").selectedIndex=0;
        
        elm7.style.color="#cccccc";
        document.addAccount.cap.style.background = "#cccccc";
        document.addAccount.cap.value = "";
        document.addAccount.cap.disabled = true;
        
        elm8.style.color="#cccccc";
        
        elm9.style.color="#cccccc";
        document.addAccount.address2latitude.style.background = "#cccccc";
        document.addAccount.address2latitude.value = "";
        document.addAccount.address2latitude.disabled = true;
        
        elm10.style.color="#cccccc";
        document.addAccount.address2longitude.style.background = "#cccccc";
        document.addAccount.address2longitude.value = "";
        document.addAccount.address2longitude.disabled = true;
        
        
    	/*elm3.style.color="#cccccc";
        document.addAccount.codiceCont.style.background = "#cccccc";
        document.addAccount.codiceCont.value = "";
        document.addAccount.codiceCont.disabled = true;*/
        
     	document.getElementById("prov").disabled = false;
        document.addAccount.check.value = "autoveicolo";
        document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO
        
      } else if (index==2) {
      	
      	resetFormElementsNew();
        
        elm1.style.color="#cccccc";
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;
    
        elm2.style.color="#cccccc";
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
        
        document.addAccount.check.value = "codiceCont";
        document.addAccount.orgType.value = "19"; //Valore per sindaco
        
      }
    }
   
    onLoad = 0;
  }
  
  function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("data4");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		document.addAccount.source.selectedIndex=0;
 		
  }
  
  function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("data4");
 		elm5 = document.getElementById("cessazione");
 		car = document.addAccount.source.value;
 	
 		if(car == 1){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			elm3.style.visibility = "visible";
 			elm4.style.visibility = "visible";
 			elm5.style.visibility = "visible";
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			elm3.style.visibility = "hidden";
 			elm4.style.visibility = "hidden";
 			elm5.style.visibility = "hidden";
 		}
 	
  }

  function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("data3");
 		elm4 = document.getElementById("data4");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		document.addAccount.source.selectedIndex=0;
 		
  }
  
  
  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
   
    if (document.getElementById('name').value.trim() == ''){
    	 message += "- Ragione Sociale richiesta\r\n";
         formTest = false;
    }
    if (document.getElementById('name').value.trim().length < 3){
        message += "- La lunghezza del campo Ragione Sociale non e\' corretta\r\n";
        formTest = false;
      
    }
   	
      if (document.getElementById('nomeCorrentista').value.trim()== ''){
          message += "- Identificativo Veicolo richiesto\r\n";
          formTest = false;
        }
      
      
//       if (document.getElementById('site2').value == '-1' && document.getElementById("inregione").checked){
//           message += "- Asl richiesta\r\n";
//           formTest = false;
//         }
      
      
//       if (document.getElementById('partitaIva').value == ''){
//           message += "- Partita Iva richiesta\r\n";
//           formTest = false;
//         }
      
      
     
     

    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
       
        
        return true;
      }
  }

</script>


<script> function gestisciTipoImpresa(val){
	if (val == 'fissa'){
		window.location.href = 'OperatoriFuoriRegione.do?command=Add&tipoD=Operatori';
	}
	else if (val == 'mobile'){
		window.location.href = 'OperatoriFuoriRegione.do?command=Add&tipoD=Autoveicolo';
	}
}


</script>

<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();">
</dhv:evaluate>
<form name="addAccount" action="OperatoriFuoriRegione.do?command=Insert&auto-populate=true"  onsubmit="javascript: return checkForm(this);" method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<% String tipoDi = (String)request.getParameter("tipoD");  %>
<td width="100%">

<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<a href="<%=((tipoDi.equals("Autoveicolo"))?("AltriOperatori.do?command=DashboardScelta"):("Distributori.do?command=ScegliD"))%>"><dhv:label name="">Attività Mobile Fuori Ambito ASL</dhv:label></a> >
<dhv:label name="">Aggiungi Attività Fuori Ambito ASL</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" >
<dhv:evaluate if="<%= !popUp %>">
<% String tipoDD = (String)request.getParameter("tipoD");  %>
  <input type="button" value="Annulla" onClick="window.location.href='OperatoriFuoriRegione.do?command=SearchForm'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="button" id="fuoriasl" disabled="disabled" title="Questo pulsante viene abilitato solo se si seleziona l'opzione 'Altre ASL della Campania e consente di selezionare un'impresa tra quelle registrate presso le altre ASL della Campania." value="<dhv:label name="">Seleziona Attività da Altre ASL della Campania</dhv:label>" onClick="javascript:window.location.href='OperatoriFuoriRegione.do?command=DefaultASL&tipoD=Autoveicolo';">
<br /><br />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

  <br /><br />
  
  <INPUT TYPE="HIDDEN" id="tipo_impresa" name="tipo_impresa" value="mobile"/>
 <!--  <input type="radio" id="tipo_mobile" name="tipo_impresa" value="mobile" checked onClick="gestisciTipoImpresa(this.value)"/> Mobile
  <input type="radio" id="tipo_fissa" name="tipo_impresa" value="fissa" onClick="gestisciTipoImpresa(this.value)"/> Fissa
  -->
  <table>
    <% String fr = (String)request.getAttribute("fuori_regione");
if(fr!=null){%>
<tr >
    <td nowrap colspan="2">
        <input type="radio" name="fuori_regione" value="true" onclick="javascript: mostraSelezione(1);clearForm();cambiaLabel(1);">
      <dhv:label name="">Fuori Regione</dhv:label>
      <input type="radio" name="fuori_regione" id="inregione" value="false" checked onclick="javascript: mostraSelezione(0);cambiaLabel(0);">
      <dhv:label name="">Altre ASL della Campania</dhv:label>
    </td>
  </tr>
  <%}else{ %>
  <tr >
    <td nowrap colspan="2">
        <input type="radio" name="fuori_regione" value="true" checked onclick="javascript: mostraSelezione(1);cambiaLabel(1);">
      <dhv:label name="">Fuori Regione</dhv:label>
      <input type="radio" name="fuori_regione" value="false" onclick="javascript: mostraSelezione(0);cambiaLabel(0);">
      <dhv:label name="">Altre ASL della Campania</dhv:label>
    </td>
  </tr>
  <%} %>
  </table>
  
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">  
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Tipologiadi inserimento</dhv:label></strong>
    </th>
  </tr>  
          <tr>
          <td class="formLabel">
          Tipo
          </td>
          <td>
           <select id="cambio" name="cambio">
           <option value="1">Autoveicolo</option>
           <option value="0">Distributori</option>
           <!-- <option value="Operatori">Operatori</option>-->
           </select> &nbsp;&nbsp;<font color='red'><b>*PER L'AGGIUNTA DI UN DISTRIBUTORE VERRA' REINDIRIZZATI ALLA RICERCA DELL'IMPRESA PROPRIETARIA</b></font>
          </td>
        </tr>
</table>
  <table>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
  </table> 
  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">


  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Proprietario del Veicolo</dhv:label></strong>
    </th>
  </tr>  
  
  <input type="hidden" name="siteId" value="-1">
  
   <dhv:include name="accounts-classification" none="true">
  <tr style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) ? " checked" : "" %>>
      <dhv:label name="accounts.accounts_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= "individual".equals((String) request.getParameter("form_type")) ? " checked" : "" %>>
      <dhv:label name="accounts.accounts_add.Individual">Individual</dhv:label>
    </td>
  </tr>
  </dhv:include>

  <input type="hidden" name="orgId" id="orgId" value="<%=  OrgDetailsImpresa.getOrgId() %>" />
  <dhv:include name="accounts-name" none="true">
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" minlength="3" maxlength="80" id="name" name="name" value="<%= toHtmlValue(OrgDetailsImpresa.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  </dhv:include>
  
 
  <dhv:include name="organization.accountNumber" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Numero Registrazione</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="codiceImpresaInterno" maxlength="30" value="<%= toHtmlValue(OrgDetailsImpresa.getCodiceImpresaInterno()) %>">
        <!--font color="red">*</font -->
      </td>
    </tr>
  </dhv:include>


 
 

  <tr>
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" id="partitaIva" name="partitaIva" value="<%= toHtmlValue(OrgDetailsImpresa.getPartitaIva()) %>">
    </td>
  </tr>
  <!-- modificato da d.dauria -->
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetailsImpresa.getCodiceFiscale()) %>">    
    </td>
  </tr>
  <!-- fine modifica -->
  

  <% String tipoD = (String)request.getParameter("tipoD");
 if(tipoD.equals("Autoveicolo")){
 %>

  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Tipo Veicolo</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetailsImpresa.getContoCorrente()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Identificativo Veicolo</dhv:label>
    </td>
    <td>
      <input type="text" size="20" maxlength="10" title="Questo campo contiene la targa del veicolo o il numero di immatricolazione" id="nomeCorrentista" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetailsImpresa.getNomeCorrentista()) %>"><font color="red">*</font>
    </td>
  </tr>

<%} %>


	<input type="hidden" id="tipoD" name="tipoDest" value="<%=tipoD %>">
           
      <input type="hidden" name="orgType" value="" />

      <input type="hidden" name="check" />
 
  
    
   <tr style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
      
       <input readonly type="text" id="alertDate" name="alertDate" size="10" value = "" />
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>      
  
      
        <font color="red">*</font>
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
   </table>
  
  <table>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
  </table>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
      <%--input type="hidden" name="address1type" value="1"--%>
    </th>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetailsImpresa.getCodiceFiscaleRappresentante()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetailsImpresa.getNomeRappresentante()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetailsImpresa.getCognomeRappresentante()) %>">
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
      
      
             <input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" value = "" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>      
  
      
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetailsImpresa.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetailsImpresa.getEmailRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetailsImpresa.getTelefonoRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="fax" value="<%= toHtmlValue(OrgDetailsImpresa.getFax()) %>">
    </td>
    
  </tr>

</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Conducente</dhv:label></strong>
      <input type="hidden" name="tipologia" value="1">
    </th>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognome" value="<%= toHtmlValue(Conducente.getCognome()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nome" value="<%= toHtmlValue(Conducente.getNome()) %>">
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
      
                   <input readonly type="text" id="data_nascita" name="data_nascita" size="10" value = "" />
		<a href="#" onClick="cal19.select(document.forms[0].data_nascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>      
  

        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogo_nascita" value="<%= toHtmlValue(Conducente.getLuogo_nascita()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Residenza</dhv:label>
    </td>
    <td>
      Comune: <input type="text" size="30" maxlength="50" name="comune_1" value="<%= toHtmlValue(Conducente.getComune()) %>">
      Indirizzo: <input type="text" size="30" maxlength="50" name="indirizzo_1" value="<%= toHtmlValue(Conducente.getIndirizzo()) %>">
      Provincia: <input type="text" size="30" maxlength="50" name="provincia_1" value="<%= toHtmlValue(Conducente.getProvincia()) %>">
    </td>
    
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Documento</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="documento" value="<%= toHtmlValue(Conducente.getDocumento()) %>">
    </td>
    
  </tr>

</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Mittente Merce</dhv:label></strong>
 	<input type="hidden" name="tipologia" value="2">
     </th>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Ragione Sociale</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="ragione_sociale" value="<%= toHtmlValue(Mittente.getRagione_sociale()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Sede Legale
    </td>
    <td>
      Comune: <input type="text" size="30" maxlength="50" name="comune_2" value="<%= toHtmlValue(Mittente.getComune()) %>">
      Indirizzo: <input type="text" size="30" maxlength="50" name="indirizzo_2" value="<%= toHtmlValue(Mittente.getIndirizzo()) %>">
      Provincia: <input type="text" size="30" maxlength="50" name="provincia_2" value="<%= toHtmlValue(Mittente.getProvincia()) %>">
    </td>
    
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Merce Trasportata</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="merce" value="<%= toHtmlValue(Mittente.getMerce()) %>">
    </td>
    
  </tr>
<tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Note</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="note" value="<%= toHtmlValue(Mittente.getNote()) %>">
    </td>
    
  </tr>
</table>
<br>
<%
  boolean noneSelected = false;
%>
<%--
if(OrgDetailsImpresa.getName()!=null){
	
int cont=0;
  Iterator iaddress = OrgDetailsImpresa.getAddressList().iterator();
  Object address[] = null;
  int i = 0;
  int locali=0;
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress)iaddress.next();
      if(thisAddress.getType() == 1 ){
--%>  
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
 
    <th colspan="2">
	    <strong>Sede Legale</strong>
	    <input type="hidden" name="address1type" value="1">
	  </th>
	  <% SiteList.setSelectStyle("display:none");%>
	  
  <tr >
    <td nowrap class="formLabel">
      <div id="label_fuori" style="display: block;"> <dhv:label name="">Nazione D'origine</dhv:label></div>
      <div id="label_in" style="display: none;"> <dhv:label name="">ASL Appartenenza</dhv:label></div>
    </td>
    <td>
    <%
    SiteList.setJsEvent("onChange =settaHidden()");
    %>
    
    <%= SiteList.getHtmlSelect("site2",OrgDetailsImpresa.getSiteId()) %>
    <input type="hidden" size="40" name="address1line3" maxlength="80" value="<%= (OrgDetailsImpresa.getSiteId()>0?(OrgDetailsImpresa.getSiteId()):(""))%>">
    <input type="text" size="40" name="address1line2" maxlength="80" value="">
    </td>
  </tr>  
  <tr>
	<td nowrap class="formLabel" name="province" id="province">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <input type="text" name="address1city" id="address1city" value="<%= toHtmlValue(indirizzoImpresa.getCity()) %>">&nbsp;
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80" value="<%= toHtmlValue(indirizzoImpresa.getStreetAddressLine1()) %>">
    </td>
  </tr>
   
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12" value="<%= toHtmlValue(indirizzoImpresa.getZip()) %>">
    </td>
  </tr>
 	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address1state" maxlength="80" value="<%= toHtmlValue(indirizzoImpresa.getCityState()) %>">
    </td>
  </tr>
  
  	
  
  
  
  
</table>
<br>
<%if(OrgDetailsImpresa.getName()!=null) {%>
<script type="text/javascript">
cambiaLabel(0);
</script>
<%} %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr>
    <td valign="middle" nowrap class="formLabel">
      <dhv:label name="">Luogo di controllo/Note</dhv:label>
    </td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA></td>
  </tr>
</table>

<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" onclick="javascript: return checkForm(this);" ><dhv:evaluate if="<%= !popUp %>">
<% String tipoDD = (String)request.getParameter("tipoD");  %>
  <input type="submit" value="Annulla" onClick="window.location.href='OperatoriFuoriRegione.do?command=SearchForm'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="window.location.href='OperatoriFuoriRegione.do?command=SearchForm'">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>

<script>
$('#cambio').change(function() {
    if($('#cambio').val()=='0')
    	goTo('OperatoriFuoriRegione.do?command=SearchForm&cambio_distr=1');
});
</script>