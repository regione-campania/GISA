<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*,org.aspcfs.modules.monitoringReport.base.* " %>


<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>


<script>

	function giorni_differenza(data1,data2){
    
	   	anno1 = parseInt(data1.substr(6),10);
	    mese1 = parseInt(data1.substr(3, 2),10);
	    giorno1 = parseInt(data1.substr(0, 2),10);
	    anno2 = parseInt(data2.substr(6),10);
	    mese2 = parseInt(data2.substr(3, 2),10);
	    giorno2 = parseInt(data2.substr(0, 2),10);
	
	    var dataok1=new Date(anno1, mese1-1, giorno1);
	    var dataok2=new Date(anno2, mese2-1, giorno2);
	
	    differenza = dataok2-dataok1;    
	    giorni_diff = new String(differenza/86400000);

    	return giorni_diff;
	}


	function checkData(){

		formTest = true;
		message = "";
		data1 = document.getElementById('data1').value;
		data2 = document.getElementById('data2').value; 
		
		if( data1 == "" && data2 == ""){
			message+='[Report per operatori mobili]Inserire una data\n';
			formTest = false;
		}
		else{
			if(data2 != ""){
				//data1 > data2
				if(giorni_differenza(data1,data2) < 0 ){
					message+='[Report per operatori mobili]Data di inizio periodo > Data di fine periodo\n';
					formTest = false;
				}			
			}
		}
		
		/*if(data2 == ""){
			message+='[Report per operatori mobili]Inserire la data di fine periodo\n';
			formTest = false;
		}*/
		
		if(message != ""){
			alert(message);
		}
		if(formTest){
	        document.forms['generateReport'].submit();    
				
		}

		return formTest;	
	}

	function checkDataMobili() {

		formTest = true;
		message = "";
		data1 = document.getElementById('opdate1').value;
		data2 = document.getElementById('opdate2').value; 

		if( data1 == "" && data2 == ""){
			message+='[Report per dettaglio operatori mobili]Inserire una data\n';
			formTest = false;
		}
		
		else{
			if(data2 != ""){
				//data1 > data2
				if(giorni_differenza(data1,data2) < 0 ){
					message+='[Report per dettaglio operatori mobili]Data di inizio periodo > Data di fine periodo\n';
					formTest = false;
				}			
			}
		}
		
		/*if(data2 == ""){
			message+='[Report per dettaglio operatori mobili]Inserire la data di fine periodo\n';
			formTest = false;
		}*/
		
		if(message != ""){
			alert(message);
		}
		if(formTest){
	        document.forms['generateReport'].submit();    
				
		}

		return formTest;	
	}

	function checkDataWorkLoadDetails(){

		formTest = true;
		message = "";
		data3 = document.getElementById('data3').value;
		data4 = document.getElementById('data4').value; 

		if( data3 == "" && data4 == ""){
			message+='[Report per dati di dettaglio]Inserire una data\n';
			formTest = false;
		}
		else{
			if(data3 != ""){
				//data1 > data2
				if(giorni_differenza(data3,data4) < 0 ){
					message+='[Report per dati di dettaglio]Data di inizio periodo > Data di fine periodo\n';
					formTest = false;
				}			
			}
		}
		
		/*if(data4 == ""){
			message+='[Report per dati di dettaglio]Inserire la data di fine periodo\n';
			formTest = false;
		}*/

		if(message != ""){
			alert(message);
		}
		
		if(formTest){
			//window.location.href = "MonitoringReport.do?command=GenerateReport";
	        document.forms['generateReport'].submit();    
				
		}

		return formTest;	
	}


	function checkDataWorkLoadRiepilogo(){

		formTest = true;
		message = "";
		data5 = document.getElementById('data5').value;
		data6 = document.getElementById('data6').value; 
		
		if(data5 == "" && data6 == ""){
			message+='[Report per dati di riepilogo]Inserire una data\n';
			formTest = false;
		}
		else{
			if(data5 != ""){
				//data1 > data2
				if(giorni_differenza(data5,data6) < 0 ){
					message+='[Report per dati di riepilogo]Data di inizio periodo > Data di fine periodo\n';
					formTest = false;
				}			
			}
		}
		
		/*if(data6 == ""){
			message+='[Report per dati di riepilogo]Inserire la data di fine periodo\n';
			formTest = false;
		}*/
		
		if(message != ""){
			alert(message);
		}

		if(formTest){
			//window.location.href = "MonitoringReport.do?command=GenerateReport";
	        document.forms['generateReport'].submit();    
				
		}

		return formTest;	
	}

	function clearForm() {

		document.getElementById('data1').value = "";
		document.getElementById('data2').value = "";
		document.getElementById('data3').value = ""; 
		document.getElementById('data4').value = ""; 
		document.getElementById('data5').value = "";
		document.getElementById('data6').value = ""; 
		document.getElementById('opdate1').value = "";
		document.getElementById('opdate2').value = "";
	}
	

</script>

<form name="generateReport" action="MonitoringReport.do?command=GenerateReport" method="post">        			
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="Report Monitoraggio Utenti">Report Monitoraggio Utenti</dhv:label> >
<dhv:label name="Report Regione">Report Regione</dhv:label>
</td>
</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">
      <table cellpadding="4" cellspacing="0" border="0" width="70%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="Report Monitoraggio Utenti">Report Monitoraggio Portatili</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Operatori Mobili</dhv:label>
          </td>
          <td>
           Dal <input readonly type="text" name="searchtimestampInizio" id="data1" size="10" />
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampInizio,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			
			Al <input readonly type="text" name="searchtimestampFine" id="data2" size="10" />&nbsp;
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampFine,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>&nbsp;
			
			<input type="checkbox" name="ipgtw" value="ipgtw" checked="checked" > Aggiungi IP GTW &nbsp;
			<input type="button" id="mobili" name="mobili" value="Genera Report" onClick="this.form.tipoReport.value='mobili';javascript:checkData();"/>			  	
         </td>
        </tr>
         <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Dettaglio Operatori Mobili</dhv:label>
          </td>
          <td>
           Dal <input readonly type="text" name="searchtimestampMobiliDal" id="opdate1" size="10" />
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampMobiliDal,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			
			Al <input readonly type="text" name="searchtimestampMobiliAl" id="opdate2" size="10" />&nbsp;
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampMobiliAl,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>&nbsp;
			
			<input type="checkbox" name="ipgtw" value="ipgtw" checked="checked" > Aggiungi IP GTW &nbsp;
			<input type="button" id="mobili" name="mobili" value="Genera Report" onClick="this.form.tipoReport.value='dettaglio_mobili';javascript:checkDataMobili();"/>			  	
         </td>
        </tr>
        <tr>
        	<th colspan="2">
            	<strong><dhv:label name="Report Monitoraggio Carico di lavoro Utenti">Report Monitoraggio Carico di Lavoro Utenti</dhv:label></strong>
          	</th>
        </tr>
        <tr>
        <td nowrap class="formLabel">
            <dhv:label name="">Dati di dettaglio</dhv:label>
        </td>
        <td>
         <!--  <a href="MonitoringReport.do?command=GenerateReportWorkLoad">Report Monitoraggio Carico di Lavori</a>-->
		Dal	<input readonly type="text" name="searchtimestampDal" id="data3" size="10" />
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampDal,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			
		Al <input readonly type="text" name="searchtimestampAl" id="data4" size="10" />&nbsp;
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampAl,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			
			<input type="button" id="lavoro" name="lavoro" value="Genera Report" onClick="this.form.tipoReport.value='lavoro';javascript:checkDataWorkLoadDetails();"/>			  	
			
		</td>			
        </tr>
		<tr>
        <td nowrap class="formLabel">
            <dhv:label name="">Dati di riepilogo</dhv:label>
        </td>
        <td>
         <!--  <a href="MonitoringReport.do?command=GenerateReportWorkLoad">Report Monitoraggio Carico di Lavori</a>-->
		Dal	<input readonly type="text" name="searchtimestampInizioPeriodo" id="data5" size="10" />
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampInizioPeriodo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			
		Al <input readonly type="text" name="searchtimestampFinePeriodo" id="data6" size="10" />&nbsp;
			<a href="#" onClick="cal19.select(document.forms[0].searchtimestampFinePeriodo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			
			<input type="button" id="riepilogo" name="riepilogo" value="Genera Report" onClick="this.form.tipoReport.value='riepilogo';javascript:checkDataWorkLoadRiepilogo();"/>			  	
			
		</td>
		
		  	<input type="hidden" id="tipoReport" name="tipoReport" value="" />
			
        </tr>
	</table>
			<br/>
			<input type ="button" name="Pulisci" id ="Pulisci" value ="Pulisci" onclick="javascript:clearForm();"/>
	</td>
</tr>
</table>
 
</form>  