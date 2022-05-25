<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.praticacontributi.base.*"%>
<%--<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>--%>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%--<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>--%>
<jsp:useBean id="pratica" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request"/>
<%@ include file="../initPage.jsp"%>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>


<script language="JavaScript">
  function clearForm() {
	  if (document.forms['searchPratica'].dataFineSterilizzazione){
		  document.forms['searchPratica'].dataFineSterilizzazione.value = '';
		  }
	  if (document.forms['searchPratica'].dataInizioSterilizzazione){
		  document.forms['searchPratica'].dataInizioSterilizzazione.value = '';
		  }
	  if (document.forms['searchPratica'].dataDecreto){
		  document.forms['searchPratica'].dataDecreto.value = '';
		  }
	  if (document.forms['searchPratica'].searchcodenumeroDecretoPratica){
		  document.forms['searchPratica'].searchcodenumeroDecretoPratica.value = '';
		  }
	   if (document.forms['searchPratica'].aslRif){
		  document.forms['searchPratica'].aslRif.selectedIndex = 0;
		  }
	   if (document.forms['searchPratica'].comuneScelto){
			  document.forms['searchPratica'].comuneScelto.selectedIndex= -1;
			  }
	  
  }
 
  
  function checkForm(form) {
   	formTest = true;
    message = "";

  	if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
  }
  
  function init() {
		
	  } 


  function popolaCampi(){
	  var idAsl = "-1";
	//  alert($("#aslRif").is("input"));
	  
	  if ($("#aslRif").is("input")){
		  idAsl = $('#aslRif').val();
		//  alert('è hidden');

		  }else{ 
				idAsl = $("#aslRif").children("option:selected").val();
			//	alert('è select');
		  }
	//alert(idAsl);
	if (idAsl > 0){
		 $.ajax({
			  
		        type: "GET",
		        url: "./ServletComuni?combo=5&idAsl="+idAsl,
		        async: false,
		        dataType: "json",
		        error: function(XMLHttpRequest, status, errorThrown) {
		            alert("oh no!");
		            alert(status);
		        },
		        success: function (data, status) {
		        	//alert(data);
		            var options = '';
		            for (var i = 0; i < data.length; i++) {
		            	var row = data[i];
		              options += '<option value="' +row['codice'] + '">' + row['descrizione'] + '</option>';
		            }
		            $("#comuneScelto").html(options);

					
		         //   alert("success");
		           
		        	
		        },
		        complete: function() {
		        	
		        }
		    });
	}
	}



  $(document).ready(function() {

	   popolaCampi();
		
	$("#aslRif").change(function() {

		popolaCampi();

	});
	});
  
</script>
<body onload="javascript:init();clearForm();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="">
		<dhv:label name="">Pratica Contributi</dhv:label></a> > <dhv:label name="">Cerca Pratica Contributi </dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<form name="searchPratica" action="PraticaContributi.do?command=SearchList&auto-populate=true" onSubmit="return checkForm(this);" method="post">


<input type="hidden" name="onlyLP" id="onlyLP" value="true" />



<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<tr>
				<th colspan="2"><strong>
					<dhv:label  name="">Filtri informazioni Pratica Contributo</dhv:label></strong>
				</th>
			</tr>
			<tr>
       	  		<td nowrap class="formLabel">
           		   <dhv:label name="">Asl di Riferimento</dhv:label>
           		</td>
           		<td>	
        		<dhv:evaluate if="<%= (User.getSiteId() > 0) %>">
  		    		<%=aslRifList.getSelectedValue(User.getSiteId())  %>
  		    		 <input type="hidden" id="aslRif" name="aslRif" value="<%= User.getSiteId() %>" />
  		    	</dhv:evaluate>
 		  
 		    	<dhv:evaluate if="<%= (User.getSiteId() <= 0) %>" >
  					<%= aslRifList.getHtmlSelect("aslRif", -1 ) %>
  					<input type="hidden" name="aslRifHidd" value="-1" />
  				</dhv:evaluate>  
      		
  			</td>
        </tr>
        
        
        <tr>
        		<td class="formLabel"><dhv:label name="">Comune</dhv:label></td>
				<td>
				
				<select name="comuneScelto" id="comuneScelto" ></select>
					<!-- input type="text" name="comuneScelto" size="20" maxlength="30"/ -->
				</td>
        </tr>
       	<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="">Numero Decreto</dhv:label>
				</td>
				<td>
					<input type="text" name="searchcodenumeroDecretoPratica" size="10"/>
				</td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data Decreto</dhv:label></td>
				<td>
				<input readonly="readonly" type="text" name="dataDecreto" size="10"/>
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataDecreto,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
				</td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="">Data Inizio Piano Sterilizzazione</dhv:label>
				</td>
				<td>
						<input readonly="readonly" type="text" name="dataInizioSterilizzazione" size="10"/>
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataInizioSterilizzazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
				</td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="">Data Fine Piano Sterilizzazione</dhv:label>
				</td>
				<td>
						<input readonly="readonly" type="text" name="dataFineSterilizzazione" size="10"/>
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataFineSterilizzazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				
				</td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="">Stato Pratica</dhv:label>
				</td>
				<td>
				<input type="radio" name="searchcodeStatoP" value= "1"  > Chiuse
            	<input type="radio" name="searchcodeStatoP" value= "2" > Aperte
            	<input type="radio" name="searchcodeStatoP" value= "3" checked="checked" > Tutte
           		</td>
			</tr>
		
		</table>
		<br />		
        <input type="submit" value="<dhv:label name="button.search">Search</dhv:label>" > 
		<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
</form>

</body>

