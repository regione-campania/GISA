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
<jsp:useBean id="popUp" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
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

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

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
  
  function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("data3");
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
  
 
  function IsNumber(Expression)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789.-";
    
    if (Expression.length < 1) 
        return (false);
    
    for (var i = 0; i < Expression.length; i++) 
    {
        var ch = Expression.substr(i, 1);
        var a = RefString.indexOf(ch, 0);
        if (a == -1)
            return (false);
    }
    return(true);
}
  
  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
    
    if (form.name){
      if ((checkNullString(form.name.value))){
        message += "- Ragione Sociale richiesta\r\n";
        formTest = false;
      }
    }
   	
//     if (form.address1city){
//         if ((checkNullString(form.address1city.value))){
//           message += "- Comune richiesto\r\n";
//           formTest = false;
//         }
//       }
//     if (form.address1state){
//         if ((checkNullString(form.address1state.value))){
//           message += "- Provincia richiesta\r\n";
//           formTest = false;
//         }
//       }
  
 
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else { 
      var test = document.addAccount.selectedList;
      if (test != null) {
        selectAllOptions(document.addAccount.selectedList);
      }
      if(alertMessage != "") {
        confirmAction(alertMessage);
      }
loadModalWindow();
		form.submit();
      
    }
  }
  function resetFormElements() {
    if (document.getElementById) {
      <dhv:include name="accounts-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
          elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
        elm3 = document.getElementById("nameLast1");
      </dhv:include>
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      <dhv:include name="accounts-firstname" none="true">
        elm1.style.color = "#000000";
        document.addAccount.nameFirst.style.background = "#ffffff";
        document.addAccount.nameFirst.disabled = false;
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2.style.color = "#000000";
        document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = false;
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
        elm3.style.color = "#000000";
        document.addAccount.nameLast.style.background = "#ffffff";
        document.addAccount.nameLast.disabled = false;
      </dhv:include>
      elm4.style.color = "#000000";
      document.addAccount.name.style.background = "#ffffff";
        document.addAccount.name.disabled = false;
      if (elm5) {
        elm5.style.color = "#000000";
        document.addAccount.ticker.style.background = "#ffffff";
        document.addAccount.ticker.disabled = false;
      }
      <dhv:include name="accounts-size" none="true">
        elm6.style.color = "#000000";
        document.addAccount.accountSize.style.background = "#ffffff";
        document.addAccount.accountSize.disabled = false;
      </dhv:include>
      /*
      <dhv:include name="accounts-title" none="true">
        elm7.style.color = "#000000";
        document.addAccount.listSalutation.style.background = "#ffffff";
        document.addAccount.listSalutation.disabled = false;
      </dhv:include>
      */
      
     
    }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      <dhv:include name="accounts-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
        elm3 = document.getElementById("nameLast1");
      </dhv:include>
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;
        resetFormElements();
        elm4.style.color="#cccccc";
        document.addAccount.name.style.background = "#cccccc";
        document.addAccount.name.value = "";
        document.addAccount.name.disabled = true;
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
          document.addAccount.ticker.disabled = true;
        }
        <dhv:include name="accounts-size" none="true">
          elm6.style.color = "#cccccc";
          document.addAccount.accountSize.style.background = "#cccccc";
          document.addAccount.accountSize.value = -1;
          document.addAccount.accountSize.disabled = true;
        </dhv:include>
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        <dhv:include name="accounts-firstname" none="true">
          elm1.style.color = "#cccccc";
          document.addAccount.nameFirst.style.background = "#cccccc";
          document.addAccount.nameFirst.value = "";
          document.addAccount.nameFirst.disabled = true;
        </dhv:include>
        <dhv:include name="accounts-middlename" none="true">
          elm2.style.color = "#cccccc";
          document.addAccount.nameMiddle.style.background = "#cccccc";
          document.addAccount.nameMiddle.value = "";
          document.addAccount.nameMiddle.disabled = true;
        </dhv:include>
        <dhv:include name="accounts-lastname" none="true">
          elm3.style.color = "#cccccc";
          document.addAccount.nameLast.style.background = "#cccccc";
          document.addAccount.nameLast.value = "";
          document.addAccount.nameLast.disabled = true;
        </dhv:include>
        /*
        <dhv:include name="accounts-title" none="true">
          elm7.style.color = "#cccccc";
          document.addAccount.listSalutation.style.background = "#cccccc";
          document.addAccount.listSalutation.value = -1;
          document.addAccount.listSalutation.disabled = true;
        </dhv:include>
        */
      }
    }
            
    onLoad = 0;
  }
  //-------------------------------------------------------------------
  // getElementIndex(input_object)
  //   Pass an input object, returns index in form.elements[] for the object
  //   Returns -1 if error
  //-------------------------------------------------------------------
  function getElementIndex(obj) {
    var theform = obj.form;
    for (var i=0; i<theform.elements.length; i++) {
      if (obj.name == theform.elements[i].name) {
        return i;
        }
      }
      return -1;
    }
  // -------------------------------------------------------------------
  // tabNext(input_object)
  //   Pass an form input object. Will focus() the next field in the form
  //   after the passed element.
  //   a) Will not focus to hidden or disabled fields
  //   b) If end of form is reached, it will loop to beginning
  //   c) If it loops through and reaches the original field again without
  //      finding a valid field to focus, it stops
  // -------------------------------------------------------------------
  function tabNext(obj) {
    if (navigator.platform.toUpperCase().indexOf("SUNOS") != -1) {
      obj.blur(); return; // Sun's onFocus() is messed up
      }
    var theform = obj.form;
    var i = getElementIndex(obj);
    var j=i+1;
    if (j >= theform.elements.length) { j=0; }
    if (i == -1) { return; }
    while (j != i) {
      if ((theform.elements[j].type!="hidden") &&
          (theform.elements[j].name != theform.elements[i].name) &&
        (!theform.elements[j].disabled)) {
        theform.elements[j].focus();
        break;
    }
    j++;
      if (j >= theform.elements.length) { j=0; }
    }
  }

  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addAccount&stateObj=address"+stateObj+"state";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    } else {
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }
  }

  var states = new Array();
  var initStates = false;
  function resetStateList(country, stateObj) {
    var stateSelect = document.forms['addAccount'].elements['address'+stateObj+'state'];
    var i = 0;
    if (initStates == false) {
      for(i = stateSelect.options.length -1; i > 0 ;i--) {
        var state = new Array(stateSelect.options[i].value, stateSelect.options[i].text);
        states[states.length] = state;
      }
    }
    if (initStates == false) {
      initStates = true;
    }
    stateSelect.options.length = 0;
    for(i = states.length -1; i > 0 ;i--) {
      var state = states[i];
      if (state[0].indexOf(country) != -1 || country == label('option.none','-- None --')) {
        stateSelect.options[stateSelect.options.length] = new Option(state[1], state[0]);
      }
    }
  }
  
  function updateCopyAddress(state){
    copyAddr = document.getElementById("copyAddress");
    if (state == 0){
     copyAddr.checked = false;
     copyAddr.disabled = true;
    } else {
     copyAddr.disabled = false;
    }
  }
</script>



<dhv:evaluate if="<%= !"true".equals(popUp) %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">

<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<dhv:label name="">Aggiungi Attività Fuori Ambito ASL</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>



<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();">
</dhv:evaluate>
<form name="addAccount" action="OperatoriFuoriRegione.do?command=Insert&auto-populate=true" method="post">


<dhv:formMessage showSpace="false"/>
<input type="button" value="Inserisci" onClick="checkForm(this.form)">
<br /><br />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

  <br /><br />
  
  
    <div style="display:none">
   
      
  <input type="radio" id="tipo_mobile" name="tipo_impresa" value="mobile" onClick="gestisciTipoImpresa(this.value)"/> Mobile
  <input type="radio" id="tipo_fissa" name="tipo_impresa" value="fissa" checked onClick="gestisciTipoImpresa(this.value)"/> Fissa

 </div>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Impresa</dhv:label></strong>
    </th>
  </tr>  
  
  <input type="hidden" name="siteId" value="16">
  <input type="hidden" name="fuori_regione" value="true" checked>
  <input type="hidden" name="orgId" id="orgId" value="<%=  OrgDetailsImpresa.getOrgId() %>" />
  
  <dhv:include name="accounts-name" none="true">
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetailsImpresa.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  </dhv:include>
  
  <%--<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getBanca()) %>">
    </td>
  </tr>--%>
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
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetailsImpresa.getPartitaIva()) %>">
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
  
 
           
      <input type="hidden" name="orgType" value="" />

      <input type="hidden" name="check" />
 
  
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
        <zeroio:dateSelect form="addAccount" field="dataNascitaRappresentante" timestamp="<%= OrgDetailsImpresa.getDataNascitaRappresentante() %>"  showTimeZone="false" />
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
    <input type="text" name="address1city" id="address1city" value="<%= toHtmlValue(indirizzoImpresa.getCity()) %>"> 
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

	<input type="hidden" id="tipoD" name="tipoDest" value="Operatori">

<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<input type="button" value="Inserisci" onClick="checkForm(this.form)">

  <input type="button" value="Annulla" onClick="javascript:self.close();">
<input type="hidden" name="tipologia"  id="tipologia" value="Operatori" />
<input type="hidden" name="popUp"  id="popUp" value="<%=popUp %>" />
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>