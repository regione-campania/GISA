<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>

 <link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
  
 <script src='javascript/modalWindow.js'></script>
  <script src='javascript/jquerymini.js'></script>	
  
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">
  
        function checkData(){

    		formTest = true;
    		message = "";
    		data1 = document.getElementById('data1').value;
    		data2 = document.getElementById('data2').value; 
    		
    		if( data1 == "" && data2 == ""){
    			message+='Inserire un range di date\n';
    			formTest = false;
    		}
    		if( data1 == "" && data2 != ""){
    			message+='Inserire una data di inizio periodo\n';
    			formTest = false;
    		}
    		if( data2 == "" && data1 !=""){
    			message+='Inserire una data di fine periodo\n';
    			formTest = false;
    		}
    		
    		if( data1 != ""){
    			var anno = data1.substring(6,10);
    			var annoCorrente = new Date().getFullYear();
    			
    			if (anno < (annoCorrente-2)){
    			message+='Inserire una data di inizio periodo non antecedente a due anni dalla data attuale.\n';
    			formTest = false;
    			}
    		}
    		
    		if(data1 != "" && data2 != ""){
    				//data1 > data2
    				if(giorni_differenza(data1,data2) < 0 ){
    					message+='Data di inizio periodo > Data di fine periodo\n';
    					formTest = false;
    				}			
    			}
    		
    	
    		if(message != ""){
    			alert(message);
    		}
    		if(formTest){
    			loadModalWindow();
    	        document.forms['generateReport'].submit();    
    				
    		}

    		return formTest;	
    	}
        
    	function checkDataB11(){

    		formTest = true;
    		message = "";
    		data1 = document.getElementById('data1_b11').value;
    		data2 = document.getElementById('data2_b11').value; 
    		
    		if( data1 == "" && data2 == ""){
    			message+='Inserire un range di date\n';
    			formTest = false;
    		}
    		if( data1 == "" && data2 != ""){
    			message+='Inserire una data di inizio periodo\n';
    			formTest = false;
    		}
    		if( data2 == "" && data1 !=""){
    			message+='Inserire una data di fine periodo\n';
    			formTest = false;
    		}
    		if( data1 != ""){
    			var anno = data1.substring(6,10);
    			var annoCorrente = new Date().getFullYear();
    			
    			if (anno < (annoCorrente-2)){
    			message+='Inserire una data di inizio periodo non antecedente a due anni dalla data attuale.\n';
    			formTest = false;
    			}
    		}
    		
    		if(data1 != "" && data2 != ""){
    				//data1 > data2
    				if(giorni_differenza(data1,data2) < 0 ){
    					message+='Data di inizio periodo > Data di fine periodo\n';
    					formTest = false;
    				}			
    			}
    	
    		if(message != ""){
    			alert(message);
    		}
    		if(formTest){
    			loadModalWindow();
    	        document.forms['generateReportB11'].submit();    
    				
    		}

    		return formTest;	
    	}
        
    	
</script>

 <dhv:container name="inviocuba" selected="Invia" object="">
    
 <body>
 
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="Stabilimenti.do"><dhv:label
			name="">Benessere Animale</dhv:label></a> > <dhv:label
			name="">Invia Schede Benessere Animale</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>

<br/><br/>

<form  name="generateReport" method="post" action = "GestioneInvioChecklist.do?command=InvioMassivo">

	 <table cellpadding="4" cellspacing="0" border="0" width="70%" class="details">
	 <col width="10%"/> <col width="40%"/>
	 
        <tr>
          <th colspan="3">
            <strong>Invia Checklist per il Benessere animale</strong>
          </th>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name=""></dhv:label>
          </td>
          <td>
           Dal <input readonly type="text" name="searchtimestampInizio" id="data1" size="10" />
			<a href="#" onClick="cal19.select(document.generateReport.searchtimestampInizio,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			Al <input readonly type="text" name="searchtimestampFine" id="data2" size="10" />&nbsp;
			<a href="#" onClick="cal19.select(document.generateReport.searchtimestampFine,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>&nbsp;				  	
         </td>
         
         <td>
        <input type = "button" value = "INVIA" onclick="javascript:checkData();">
        </td>
        </tr>
	</table>
	</form>
	
<br/><br/><br/>

<form  name="generateReportB11" method="post" action = "GestioneInvioChecklist.do?command=InvioMassivo">

	 <table cellpadding="4" cellspacing="0" border="0" width="70%" class="details">
	 	 <col width="10%"/> <col width="40%"/>
	         <tr>
          <th colspan="3">
            <strong>Invia Checklist per la sicurezza alimentare - Atto B26</strong>
          </th>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name=""></dhv:label>
          </td>
          <td>
           Dal <input readonly type="text" name="searchtimestampInizio_b11" id="data1_b11" size="10" />
			<a href="#" onClick="cal19.select(document.generateReportB11.searchtimestampInizio_b11,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>
			Al <input readonly type="text" name="searchtimestampFine_b11" id="data2_b11" size="10" />&nbsp;
			<a href="#" onClick="cal19.select(document.generateReportB11.searchtimestampFine_b11,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a><font color="red">*</font>&nbsp;				  	
         </td>
         <td>
         	<input type = "button" value = "INVIA" onclick="javascript:checkDataB11();">
         </td>
        </tr>
	</table>	
</form>
</body>
</dhv:container>


	