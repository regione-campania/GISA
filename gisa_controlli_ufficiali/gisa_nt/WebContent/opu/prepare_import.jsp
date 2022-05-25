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
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="normeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Error" class="java.lang.String" scope="request"/>
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
<%@ include file="../initPage.jsp" %>


<style type="text/css">
#dhtmltooltip{
position: absolute;
left: -300px;
width: 150px;
border: 1px solid black;
padding: 2px;
background-color: lightyellow;
visibility: hidden;
z-index: 100;
/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
}
#dhtmlpointer{
position:absolute;
left: -300px;
z-index: 101;
visibility: hidden;
}
</style>

<script type="text/javascript">

function inCostruzione(){
	alert('In Costruzione!');
	return false;
}
</script>

      <script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">
        var offsetfromcursorX=12 
        var offsetfromcursorY=10 
        var offsetdivfrompointerX=10 
        var offsetdivfrompointerY=14 
        document.write('<div id="dhtmltooltip"></div>') //write out tooltip DIV
        document.write('<img id="dhtmlpointer" src="images/arrow2.gif">') //write out pointer image
        var ie=document.all
        var ns6=document.getElementById && !document.all
        var enabletip=false
        if (ie||ns6)
        var tipobj=document.all? document.all["dhtmltooltip"] : document.getElementById? document.getElementById("dhtmltooltip") : ""
        var pointerobj=document.all? document.all["dhtmlpointer"] : document.getElementById? document.getElementById("dhtmlpointer") : ""
        function ietruebody(){
        return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
        }
        function ddrivetip(thetext, thewidth, thecolor){
        if (ns6||ie){
        if (typeof thewidth!="undefined") tipobj.style.width=thewidth+"px"
        if (typeof thecolor!="undefined" && thecolor!="") tipobj.style.backgroundColor=thecolor
        tipobj.innerHTML=thetext
        enabletip=true
        return false
        }
        }
        

        function hideddrivetip(){
        if (ns6||ie){
        enabletip=false
        tipobj.style.visibility="hidden"
        pointerobj.style.visibility="hidden"
        tipobj.style.left="-1000px"
        tipobj.style.backgroundColor=''
        tipobj.style.width=''
        }
        }
        function cambiaAddressType(i){
      	  document.forms['searchAccount'].searchcodeAddressType.value = i;
        }
      	  

      	function cambiaAttivitaImpresa(){
      		if(document.forms['searchAccount'].searchAccountCity.value != ""){
      			document.getElementById("attivita_impresa").style.display = "block";
      			document.getElementById("tipo_impresa_1").checked = "checked";
      			cambiaAddressType(5);
      		}else{
      			document.getElementById("attivita_impresa").style.display = "none";
      			if(document.getElementById("tipo_impresa_1").checked = "checked")
      				document.getElementById("tipo_impresa_1").checked = false;
      			if(document.getElementById("tipo_impresa_2").checked = "checked")
      				document.getElementById("tipo_impresa_2").checked = false;
      			if(document.getElementById("tipo_impresa_3").checked = "checked")
      				document.getElementById("tipo_impresa_3").checked = false;
      		}
      	}

        function setStatoCu(stato)
        {
            document.searchAccount.searchstatoCu.value = stato;
        }
       function abilitaRicercaAllerte()
       {
			if (document.searchAccount.flagAllerte.checked)
			{
				document.getElementById("bloccoAllerte").style.display="block";

				document.searchAccount.searchcodiceAllerta.value = "Tutte";
			}
			else
			{
				document.getElementById("bloccoAllerte").style.display="none";
				document.searchAccount.searchcodiceAllerta.value = "";
			}
           
       }
			function popolaComboComuni(idAsl)
			{
				idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				
					PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback) ;
				
			}

	          function setComuniComboCallback(returnValue)
	          {
	        	  var select = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
	              

	              //Azzero il contenuto della seconda select
	              for (var i = select.length - 1; i >= 0; i--)
	            	  select.remove(i);

	              indici = returnValue [0];
	              valori = returnValue [1];
	              //Popolo la seconda Select
	              for(j =0 ; j<indici.length; j++){
	              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
	              var NewOpt = document.createElement('option');
	              NewOpt.value = indici[j]; // Imposto il valore
	              NewOpt.text = valori[j]; // Imposto il testo

	              //Aggiungo l'elemento option
	              try
	              {
	            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	              }catch(e){
	            	  select.add(NewOpt); // Funziona solo con IE
	              }
	              }


	          }
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchAccountName.value="";
    document.forms['searchAccount'].searchcodeOrgSiteId.value="-1";
    document.forms['searchAccount'].searchAccountCity.value=""; 
    document.forms['searchAccount'].searchPartitaIva.value="";
    document.forms['searchAccount'].searchCodiceFiscale.value="";
   }
 
  function checkForm(form) {
	    <%-- Account Filters --%>
	    var nome = trim(document.forms['searchAccount'].searchAccountName.value);
	    var asl = document.forms['searchAccount'].searchcodeOrgSiteId.value;
	    var comune = trim(document.forms['searchAccount'].searchAccountCity.value); 
	    var iva = trim(document.forms['searchAccount'].searchPartitaIva.value);
	    var cf = trim(document.forms['searchAccount'].searchCodiceFiscale.value);
	    
	    var esito = true;
	    var message = '';
	    
	    if (asl!="-1" || comune!=""){
	    	esito = true;	
	    }
	    else if (asl=="-1" && comune=="" && nome=="" && cf=="" && iva==""){
	    	esito = false;
	    	message='Compilare almeno un campo.';
	    }
	    else {
	    	if (nome.length>0 && nome.length<3 ){
	    		esito = false;
	    		message+='Inserire almeno tre caratteri per la ragione sociale.\n';
	    		}
	    	if (iva.length>0 && iva.length<3 ){
	    		esito = false;
	    		message+='Inserire almeno tre caratteri per la partita iva.\n';
	    	}
	    	if (cf.length>0 && cf.length<3 ){
	    		esito = false;
	    		message+='Inserire almeno tre caratteri per il codice fiscale. \n';
	    	}
	   }
	    
	    if (esito == false){
	    	alert(message);
	    	return false;
	    	}
	    else{
	    	loadModalWindow();
	    	form.submit();
	  	  }
	    }
	

</script>



<%if (Error!=null && !Error.equals("") && !Error.equals("null")) {%>
<script>
alert('<%=Error%>');
</script>
<%} %>




<table width="100%">
  <colgroup><col width="50%">
  </colgroup><tbody>
  <tr>
    <td valign="top">  
    <!--  IMPRESA -->
      <table class="details" border="0" cellpadding="4" cellspacing="0" width="100%">
        <tbody>
        <tr>
          <th colspan="2"><strong>Ricerca per org_id</strong></th>
        </tr>
        <form name="searchAccount2" id = "searchAccount2" action="OpuStab.do?command=CaricaImport" method="post">
        <tr>
          <td class="formLabel">
            Org_id
          </td>
          <td>
            <input maxlength="70" size="50" name="orgId" id="orgId" value="" type="text">
          </td>
        </tr>  
      </tbody>
      </table>
      <input type="submit" value="CARICA"/>
      </form>
    </td>
    <td valign="top">  
    <form name="searchAccount" action="OpuStab.do?command=SearchImport" method="post">
            <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
              <tr>
                <th colspan="2"><strong><dhv:label name="">Ricerca Rapida Stabilimento</dhv:label></strong></th>
              </tr>
              <tr>
                <td class="formLabel">
                  <dhv:label name="">Impresa</dhv:label>
                </td>
                <td>
                  <input type="text" maxlength="70" size="50" name="searchAccountName" value="">
                </td>
              </tr>
              <tr>
                <td class="formLabel">
                  Codice Fiscale
                </td>
                <td>
                  <input type="text" size="23" name="searchCodiceFiscale" value="">
                </td>
              </tr>
              <tr>
                <td class="formLabel">
                  Partita IVA
                </td>
                <td>
                  <input type="text" size="23" name="searchPartitaIva" value="">
                </td>
              </tr> 
              
               <tr>
                <td class="formLabel">
                  <dhv:label name="">ASL</dhv:label>
                </td>
                <td>
                  <%
           
           SiteList.setJsEvent("onChange=popolaComboComuni()");
           %>
          	  <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", -1) %>
                </td>
              </tr> 
              
               <tr>
                <td class="formLabel">
                  <dhv:label name="">Comune</dhv:label>
                </td>
                <td>
                 	<%= ComuniList.getHtmlSelect("searchAccountCity", -1) %>
                </td>
              </tr> 
              
              
          

              
            </table>
      <dhv:include name="accounts-search-contacts" none="false">
      <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
      <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
      <br />
      </dhv:include>
      <input type="button" onclick='checkForm(this.form)' value="<dhv:label name="button.search">Search</dhv:label>">
      <input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="clearForm();">
      <input type="hidden" name="source" value="searchForm">
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

      <script type="text/javascript">
      </script>
      </form>
    </td>
  </tr>
  <tr> 	
  </tr>
</tbody>
</table>
