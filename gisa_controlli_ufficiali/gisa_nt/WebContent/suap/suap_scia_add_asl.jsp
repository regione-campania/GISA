<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />


<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="UrlToSubmit" class="java.lang.String" scope="request" />

<script>function closeDialog(){
  $('#dialogSceltaComune').dialog('close');
	
}</script>


<script>

$(function () {
	    
	 
	
	 
	 $( "#dialogSceltaComune" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: true,
	       	title:"SCELTA DEL COMUNE PER CUI SI INTENDE INSERIRE LO STABILIMENTO",
	        width:850,
	        height:500,
	        draggable: false,
	        modal: true,
	      
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
  
});         

</script>
<div id = "dialogSceltaComune">

<table class="trails" cellspacing="0">
<tr>
<!-- <td width="100%">
INSERIMENTO NUOVA SCIA
</td>-->
</tr>
</table>

<br>
<form id="" name="scelta" action="<%=UrlToSubmit %>" onsubmit="return checkValiditaDataPec();" method="post">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>COMPILARE I DATI PER ANDARE AVANTI</strong>
    </th>
  </tr>
 
    <tr>
      <td nowrap class="formLabel">
        Data inizio attivita'
      </td>
      <td>
       <input type="text" size="15" name="dataRichiesta" readonly="readonly"
					id="dataRichiesta" required placeholder="dd/MM/YYYY">
					<a href="#" onClick="document.forms['scelta'].elements['dataRichiesta'].style = ''; cal19.select(document.forms[0].dataRichiesta,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
      
     
      </td>
    </tr>
    
     <tr>
      <td nowrap class="formLabel">
        Seleziona Comune
      </td>
      <td>
      <%
      ComuniList.setRequired(true);
      %>
       <%=ComuniList.getHtmlSelectText("comuneSuap", "-1") %>
      </td>
    </tr>
    
    </table>
    <br/><br/>
    <table width="100%">
    <tr>
    <td align="left">
    <input type="button"  class="redBigButton" onClick="closeDialog()" style="height:40px !important; width:250px !important;" value="ANNULLA" /> 
    </td>
    <td align="right">
    <input type="submit"  class="aniceBigButton" style="height:40px !important; width:250px !important;" value="AVANTI" /> 
    </td>
    </tr>
    </table>
        
</form>

	<script>
		function checkValiditaDataPec()
		{
			
			
			datapec = document.forms['scelta'].elements['dataRichiesta'].value;
			if(datapec.trim().length == 0)
			{
				//alert("Attenzione, inserire la data arrivo PEC");
				$("input[name='dataRichiesta']").css('border-color','rgba(255,0,0,0.9)');
				return false;
			}
			else
			{	
				var dataInserita = new Date(datapec);
				
				var dataOggi = new Date();
				var dateString = datapec; // Oct 23
				var dateParts = dateString.split("/");
				var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
				
				if (dateObject.getTime() > dataOggi.getTime()){
					alert("Attenzione, la data di inizio attivita' non puo' essere successiva a quella odierna".toUpperCase());
					return false;
				}
				
				
				var dataLimite = new Date('01/05/2015');
				if(dataInserita < dataLimite)
				{
					//solo se non si tratta di un riconosciuto...
					//questo ce lo dice l'url destinazione del form
					var urlDest = document.forms['scelta'].action;
					var t = urlDest.substring(urlDest.indexOf('tipoInserimento=')+'tipoInserimento='.length).charAt(0);
					if(+t != 2) //se non è riconosciuto
					{
						alert("Attenzione, la data di inizio attivita' non può essere precedente quella del 1° Maggio 2015".toUpperCase());
						$("input[name='dataRichiesta']").css('border-color','rgba(255,0,0,0.9)');
						return false;
					}
					else
					{
						$("input[name='dataRichiesta']").removeAttr("style");
						return true;
					}
				}
			}
			 
		}
	</script>

</div>