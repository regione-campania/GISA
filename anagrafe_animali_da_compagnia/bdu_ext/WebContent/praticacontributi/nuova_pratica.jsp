<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,java.text.DateFormat"%>
<%@page import="org.aspcfs.modules.praticacontributi.base.Pratica"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%--<jsp:useBean id="comune" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="aaa" class="java.util.ArrayList" scope="request"/>--%>
<jsp:useBean id="pratica" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request"/>
<jsp:useBean id="veterinariPrivatiList" class="org.aspcfs.utils.web.LookupList" scope="request" />


<script language="JavaScript" TYPE="text/javascript" SRC="javascript/dateControl.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
 <script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
      

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<script>
function init() {
    <% if (request.getAttribute("UserProvincia")!=null) { 
  		UserBean user = (UserBean) request.getAttribute("UserProvincia"); 
  	%>
	<% } %>
  } 


var array=new Array();
var old=null;
 
//funzione per svuotare il contenuto della lista
function svuota(){	
	//recupero della lunghezza della lista
	num_option=document.getElementById('comune').options.length;
	for(a=num_option;a>=0;a--){		
		document.getElementById('comune').options[a]=null;
	}
}


function popolaComboComuni()
	{
	
		idTipologiaPratica = document.addRichiesta.idTipologiaPratica.value;
		idAsl = "-1";
		if(document.addRichiesta.aslRif!=null)
			idAsl = document.addRichiesta.aslRif.value;
		PopolaCombo.getValoriComboComuniAslId(idAsl, idTipologiaPratica, setComuniComboCallback) ;
		
	}

  function setComuniComboCallback(returnValue)
      {

        
    	  var select = document.forms[0].comune; //Recupero la SELECT
          

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
        	  if(indici[j]!="-1" && indici[j]!="")
        	  {
        	  		select.add(NewOpt, null); //Metodo Standard, non funziona con IE
        	  }
          }catch(e)
          {
        	  if(valori[j]!=null && valori[j]!="")
        	  {
        	  	select.add(NewOpt); // Funziona solo con IE
        	  }
          }
          }
        
      }

function onChangeState(){
	  index=document.addRichiesta.aslRif.value;
	
	  xx=array[index];
	alert('lista comuni '+xx);
	  if(old !=null){
		  		
				num_option=document.getElementById('comune').options.length;
				svuota();
	  }
	
	if(xx!=null){
		
	  for(i=0;i<xx.length;i++){
		 nouvel_element = new Option(xx[i],xx[i],false,false);
		 document.addRichiesta.comune.options[document.addRichiesta.comune.length] = nouvel_element;
		}
		old=xx;
		
		}
}

function doCheck(form) {
    if (form.dosubmit.value == "false") {     
      return true;
    } else {
      return(checkForm(form));
    }
   }

function checkPostData(data,dataReg){

	  try  {
		if (compareDates( data.value,"d/MM/y",dataReg.value,"d/MM/y")==1) { 
   		return false;
   	}
   	else{
   		return true;
   	}
	  }
	  catch (e) {
		  return true;
	  }
}	

function compareDates(date1,dateformat1,date2,dateformat2){
	var d1=getDateFromFormat(date1,dateformat1);
	var d2=getDateFromFormat(date2,dateformat2);
	if(d1==0 || d2==0){
		return -1;
	}
}

function getDateFromFormat(val,format){
	val=val+"";
	format=format+"";
	var i_val=0;
	var i_format=0;
	var c="";
	var token="";
	var token2="";
	var x,y;
	var now=new Date();
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=1;
	var hh=now.getHours();
	var mm=now.getMinutes();
	var ss=now.getSeconds();
	var ampm="";
	while(i_format < format.length){
		c=format.charAt(i_format);
		token="";
		while((format.charAt(i_format)==c) &&(i_format < format.length)){
			token += format.charAt(i_format++);
			}
		if(token=="yyyy" || token=="yy" || token=="y"){
			if(token=="yyyy"){
				x=4;
				y=4;
				}
			if(token=="yy"){
				x=2;
				y=2;
				}
			if(token=="y"){
				x=2;
				y=4;
				}
			year=_getInt(val,i_val,x,y);
			if(year==null){
				return 0;
				}
			i_val += year.length;
			if(year.length==2){
				if(year > 70){
					year=1900+(year-0);
					}
				else{
					year=2000+(year-0);
					}
				}
			}else 
				if(token=="MMM"||token=="NNN"){
					month=0;
					for(var i=0;i<MONTH_NAMES.length;i++){
						var month_name=MONTH_NAMES[i];
						if(val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()){
							if(token=="MMM"||(token=="NNN"&&i>11)){
								month=i+1;
								if(month>12){
									month -= 12;
									}
								i_val += month_name.length;
								break;
								}
							}
						}if((month < 1)||(month>12)){
							return 0;
							}
						}else if(token=="EE"||token=="E"){for(var i=0;i<DAY_NAMES.length;i++){var day_name=DAY_NAMES[i];if(val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()){i_val += day_name.length;break;}}}else if(token=="MM"||token=="M"){month=_getInt(val,i_val,token.length,2);if(month==null||(month<1)||(month>12)){return 0;}i_val+=month.length;}else if(token=="dd"||token=="d"){date=_getInt(val,i_val,token.length,2);if(date==null||(date<1)||(date>31)){return 0;}i_val+=date.length;}else if(token=="hh"||token=="h"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<1)||(hh>12)){return 0;}i_val+=hh.length;}else if(token=="HH"||token=="H"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<0)||(hh>23)){return 0;}i_val+=hh.length;}else if(token=="KK"||token=="K"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<0)||(hh>11)){return 0;}i_val+=hh.length;}else if(token=="kk"||token=="k"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<1)||(hh>24)){return 0;}i_val+=hh.length;hh--;}else if(token=="mm"||token=="m"){mm=_getInt(val,i_val,token.length,2);if(mm==null||(mm<0)||(mm>59)){return 0;}i_val+=mm.length;}else if(token=="ss"||token=="s"){ss=_getInt(val,i_val,token.length,2);if(ss==null||(ss<0)||(ss>59)){return 0;}i_val+=ss.length;}else if(token=="a"){if(val.substring(i_val,i_val+2).toLowerCase()=="am"){ampm="AM";}else if(val.substring(i_val,i_val+2).toLowerCase()=="pm"){ampm="PM";}else{return 0;}i_val+=2;}else{if(val.substring(i_val,i_val+token.length)!=token){return 0;}else{i_val+=token.length;}}}if(i_val != val.length){return 0;}if(month==2){if( ((year%4==0)&&(year%100 != 0) ) ||(year%400==0) ){if(date > 29){return 0;}}else{if(date > 28){return 0;}}}if((month==4)||(month==6)||(month==9)||(month==11)){if(date > 30){return 0;}}if(hh<12 && ampm=="PM"){hh=hh-0+12;}else if(hh>11 && ampm=="AM"){hh-=12;}var newdate=new Date(year,month-1,date,hh,mm,ss);return newdate.getTime();}




function dateVerify(form){
/*	if((form.dataInizioSterilizzazione !="") && (form.dataInizioSterilizzazione!=null)){
		alert('fai i dovuti controlli');
		
	}	
	else {
		alert('selezionare prima la data di inizio');
		}
		
	*/
	if ( checkPostData(form.dataInizioSterilizzazione,form.dataDecreto.value) ){
		var days =  giorni_differenza(form.dataInizioSterilizzazione.value,form.dataDecreto.value);
		if ( days > 30 ){ 
			message += label("","- La data di inizio sterilizzazione non può essere precedente a 30 giorni a partire dalla data del decreto.\r\n"); 
			formTest = false; 
			}
		}
}  	

function checkForm(form) {
    formTest = true;
    message = "";
    lanciaControlloDate();
    //aggiungere il controllo sui comuni
    if (form.aslRif!=null && form.aslRif.value == -1){
 	    message += label("","- E' obbligatorio selezionare un' asl\r\n");
        formTest = false;
    
 	}
  
    if (form.dataDecreto.value == ''){
 	    message += label("","- Data Decreto è un campo richiesto\r\n");
        formTest = false;
    
 	}
    if (form.dataInizioSterilizzazione.value == '' ){
 	    message += label("","- Data Inzio Intervallo di sterilizzazione richiesta\r\n");
        formTest = false;
    
 	}

    if (form.dataFineSterilizzazione.value == '' ){
 	    message += label("","- Data Fine Intervallo di sterilizzazione richiesta\r\n");
        formTest = false;
  	}
    
    //Flusso 251
    if (form.veterinari!=null && form.veterinari.value == '-1' ){
 	    message += label("","- Selezionare almeno un veterinario\r\n");
        formTest = false;
  	}

<% 
	//Flusso 251
    if (pratica.getIdTipologiaPratica()!=Pratica.idPraticaLP )
    {
%>

    if ((form.totaleCaniCatturati.value!= null) && (form.totaleCaniCatturati.value!= '')){
    	if (isNaN(form.totaleCaniCatturati.value)) {
	    message += label("","- Inserire un valore numerico per il totale dei cani catturati inclusi nella pratica\r\n");
        formTest = false;
    	}
    	else if(form.totaleCaniCatturati.value.indexOf("-")>-1 || form.totaleCaniCatturati.value.indexOf(".")>-1 || form.totaleCaniCatturati.value.indexOf(",")>-1)
    	{
    		message += label("","- Inserire un valore intero positivo per il numero cani catturati da sterilizzare inclusi nella pratica\r\n");
            formTest = false;
    	}
    	else if(parseInt(form.totaleCaniCatturati.value)>1000000)
    	{
    		message += label("","- Il numero cani catturati da sterilizzare inclusi nella pratica è troppo elevato\r\n");
            formTest = false;
    	}
    }
    else
    {
    	message += label("","- Numero cani catturati inclusi nella pratica è un campo richiesto\r\n");
    	formTest = false;
    }
    if ((form.totaleCaniPadronali.value!= null) && (form.totaleCaniPadronali.value!= '')){
    	if (isNaN(form.totaleCaniPadronali.value)) {
	    message += label("","- Inserire un valore numerico per il totale dei cani padronali inclusi nella pratica\r\n");
        formTest = false;
    	}
    	else if(form.totaleCaniPadronali.value.indexOf("-")>-1 || form.totaleCaniPadronali.value.indexOf(".")>-1 || form.totaleCaniPadronali.value.indexOf(",")>-1)
    	{
    		message += label("","- Inserire un valore intero positivo per il numero cani padronali da sterilizzare inclusi nella pratica\r\n");
            formTest = false;
    	}
    	else if(parseInt(form.totaleCaniPadronali.value)>1000000)
    	{
    		message += label("","- Il numero cani padronali da sterilizzare inclusi nella pratica è troppo elevato\r\n");
            formTest = false;
    	}
    }
    else
    {
    	message += label("","- Numero cani padronali inclusi nella pratica è un campo richiesto\r\n");
    	formTest = false;
    }
    if ((form.totaleGattiCatturati.value!= null) && (form.totaleGattiCatturati.value!= '')){
    	if (isNaN(form.totaleGattiCatturati.value)) {
	    message += label("","- Inserire un valore numerico per il totale dei gatti catturati inclusi nella pratica\r\n");
        formTest = false;
    	}
    	else if(form.totaleGattiCatturati.value.indexOf("-")>-1 || form.totaleGattiCatturati.value.indexOf(".")>-1 || form.totaleGattiCatturati.value.indexOf(",")>-1)
    	{
    		message += label("","- Inserire un valore intero positivo per il numero gatti catturati da sterilizzare inclusi nella pratica\r\n");
            formTest = false;
    	}
    	else if(parseInt(form.totaleGattiCatturati.value)>1000000)
    	{
    		message += label("","- Il numero gatti catturati da sterilizzare inclusi nella pratica è troppo elevato\r\n");
            formTest = false;
    	}
    }
    else
    {
    	message += label("","- Numero gatti catturati inclusi nella pratica è un campo richiesto\r\n");
    	formTest = false;
    }

    if ((form.totaleGattiPadronali.value!= null) && (form.totaleGattiPadronali.value!= '')){
    	if (isNaN(form.totaleGattiPadronali.value)) {
	    message += label("","- Inserire un valore numerico per il totale dei gatti padronali inclusi nella pratica\r\n");
        formTest = false;
    	}
    	else if(form.totaleGattiPadronali.value.indexOf("-")>-1 || form.totaleGattiPadronali.value.indexOf(".")>-1 || form.totaleGattiPadronali.value.indexOf(",")>-1)
    	{
    		message += label("","- Inserire un valore intero positivo per il numero gatti padronali da sterilizzare inclusi nella pratica\r\n");
            formTest = false;
    	}
    	else if(parseInt(form.totaleGattiPadronali.value)>1000000)
    	{
    		message += label("","- Il numero gatti padronali da sterilizzare inclusi nella pratica è troppo elevato\r\n");
            formTest = false;
    	}
    }
    else
    {
    	message += label("","- Numero gatti padronali inclusi nella pratica è un campo richiesto\r\n");
    	formTest = false;
    }
<%
    }
    else
    {

%>
    if ((form.totaleCaniMaschi.value!= null) && (form.totaleCaniMaschi.value!= '')){
    	if (isNaN(form.totaleCaniMaschi.value)) {
	    message += label("","- Inserire un valore numerico per il numero cani maschi da sterilizzare inclusi nella pratica\r\n");
        formTest = false;
    	}
    	else if(form.totaleCaniMaschi.value.indexOf("-")>-1 || form.totaleCaniMaschi.value.indexOf(".")>-1 || form.totaleCaniMaschi.value.indexOf(",")>-1)
    	{
    		message += label("","- Inserire un valore intero positivo per il numero cani maschi da sterilizzare inclusi nella pratica\r\n");
            formTest = false;
    	}
    	else if(parseInt(form.totaleCaniMaschi.value)>1000000)
    	{
    		message += label("","- Il numero cani maschi da sterilizzare inclusi nella pratica è troppo elevato\r\n");
            formTest = false;
    	}
    }
    else
    {
    	message += label("","- Numero cani maschi inclusi nella pratica è un campo richiesto\r\n");
    	formTest = false;
    }
    if ((form.totaleCaniFemmina.value!= null) && (form.totaleCaniFemmina.value!= '')){
    	if (isNaN(form.totaleCaniFemmina.value)) {
	    message += label("","- Inserire un valore numerico per il numero cani femmina da sterilizzare inclusi nella pratica\r\n");
        formTest = false;
    	}
    	else if(form.totaleCaniFemmina.value.indexOf("-")>-1 || form.totaleCaniFemmina.value.indexOf(".")>-1 || form.totaleCaniFemmina.value.indexOf(",")>-1)
    	{
    		message += label("","- Inserire un valore intero positivo per il numero cani femmina da sterilizzare inclusi nella pratica\r\n");
            formTest = false;
    	}
    	else if(parseInt(form.totaleCaniFemmina.value)>1000000)
    	{
    		message += label("","- Il numero cani femmina da sterilizzare inclusi nella pratica è troppo elevato\r\n");
            formTest = false;
    	}
    }
    else
    {
    	message += label("","- Numero cani femmina inclusi nella pratica è un campo richiesto\r\n");
    	formTest = false;
    }
<%
    }
%>
    if ((form.numeroDecretoPratica.value!= null) && (form.numeroDecretoPratica.value.trim()!= '')){
    	if (isNaN(form.numeroDecretoPratica.value)) {
	    message += label("","- Inserire un valore numerico per il decreto\r\n");
        formTest = false;
    	}
    }
    else
    {
    	message += label("", "- Numero decreto è un campo richiesto\r\n");
    	formTest = false;
    }

    if (giorni_differenza(form.dataInizioSterilizzazione.value,form.dataFineSterilizzazione.value)<0){
    	message += label("","- La data di fine sterilizzazione deve essere successiva a quella di inizio.\r\n");
    	formTest = false;
    }

	if (form.comune.value==""){
		 
		<% if (pratica.getIdTipologiaPratica()==Pratica.idPraticaComune || pratica.getIdTipologiaPratica()==Pratica.idPraticaLP ){%>
			message += label("","- Selezionare almeno un comune\r\n");
		<%} else{%>
			message += label("","- Selezionare almeno un canile\r\n");
			<%}%>	
    	formTest = false;

    }
 	 
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    else
    {
   	return true;
    }
	
  }



</script>

<body onload="javascript:init();popolaComboComuni()">
		<form method="post" name="addRichiesta" action="PraticaContributi.do?command=Save&auto-populate=true"  >
		
		
			<table class="trails" cellspacing="0">
			<tr>
				<td width="100%">
					  <a href="PraticaContributi.do"><dhv:label name="praticacontributi">Pratica contributi</dhv:label></a> >
					  <dhv:label name="praticacontributi.add">Aggiungi Pratica Contributi Sterilizzazione </dhv:label>
				</td>
			</tr>
			</table>
		
		
			
			<input type="button" value="Avvia la richiesta" onClick="this.form.dosubmit.value='true';if(doCheck(this.form)){this.form.submit()};"/>
			<input type="button" value="Annulla" onclick="location.href='PraticaContributi.do'" />
			<input type="hidden" id="dataCorrente" name="dataCorrente" nomecampo = "datacorrente" labelcampo="Data Corrente" value="<%= request.getAttribute("currentDate") %>"  >
			<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="<%=pratica.getIdTipologiaPratica() %>" />
			
			<br />
			<br />
			<br />
			<table class="details"  cellspacing="0" cellpadding="4" border="0" width="100%">
			<tr>
					<th colspan="2">
						<strong>
							<dhv:label name="praticacontributi.new">New</dhv:label>
						</strong>
					</th>
			</tr>
			<%
			//Flusso 251
		    if(pratica.getIdTipologiaPratica()!=Pratica.idPraticaLP)
		    {
			%>
			
				<tr>
       	  		<td nowrap class="formLabel">
           		   <dhv:label name="">Asl di Riferimento</dhv:label>
           		</td>
           		<td>	
           		<dhv:evaluate if="<%= (User.getSiteId() > 0) %>">
  				<%= aslRifList.getSelectedValue(User.getSiteId()) %>
  				<script>
  				<% 	ArrayList<String> listaComuni=(ArrayList<String>)request.getAttribute("lista");%> 
 					 array[<%=User.getSiteId() %>]=<%=listaComuni%>
  				</script>	
  				
  				 <input type="hidden" id="aslRif" name="aslRif" value="<%= User.getSiteId() %>" >
  				 <font color="red">*</font>
  				</dhv:evaluate>
           		
           		<dhv:evaluate if="<%= (User.getSiteId() <= 0) %>" >
           		<%
  					Hashtable<String,ArrayList<String>> t=(Hashtable<String,ArrayList<String>>)request.getAttribute("hashtable");
  				 %>
  					 <script>
<%Iterator<String> key=t.keySet().iterator();

while(key.hasNext()){
	String kiave=key.next();

	ArrayList<String> arr=t.get(kiave);

		%>
			
		 array[<%="'"+kiave+"'"%>]=<%=arr%>;

	<%} %>

</script>
<%
  					aslRifList.setJsEvent( "onchange=popolaComboComuni()");
  					%>
           		
           			<%=aslRifList.getHtmlSelect("aslRif", -1 ) %>
  					<input type="hidden" name="aslRifFReg" />
  					<font color="red">*</font>
           		</dhv:evaluate>
           		
  				</td>
            </tr>
            
            <% 
		    }
            %>
            
            
     <tr id="comuni">
     
    <dhv:evaluate if="<%= (pratica.getIdTipologiaPratica()==Pratica.idPraticaComune || pratica.getIdTipologiaPratica()==Pratica.idPraticaLP) %>">
	<td class="formLabel" >Comune</td>
	</dhv:evaluate>
	<dhv:evaluate if="<%= (pratica.getIdTipologiaPratica()==Pratica.idPraticaCanile) %>">
	<td class="formLabel" >Canile</td>
	</dhv:evaluate>
	
	
	
	<td>
	 	<select name="comune" id="comune" multiple="multiple" title="Per selezionare più comuni, digitare ctrl se i comuni non sono consecutivi nell'elenco, digitare shift nel caso di comuni consecutivi ">

</select>
		<font color="red">*</font>
	</td>
	</tr>
	

<%
			//Flusso 251
		    if(pratica.getIdTipologiaPratica()!=Pratica.idPraticaLP)
		    {
%>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Oggetto</dhv:label>
	           		</td>
    	       		<td>
    	       		<textarea rows="4" cols="35" name="oggettoPratica" value="<%=pratica.getOggettoPratica()%>"></textarea>
    				</td>
           		</tr>
<%
		    }
%>	
           		<tr>
           			<td  class="formLabel">
           				<dhv:label name="">Numero del Decreto</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="text" name="numeroDecretoPratica" size="10" />
    	       			<font color="red">*</font>
    				</td>
           		</tr>		
           		<tr>
           			<td  class="formLabel">
           				<dhv:label name="">Data del Decreto</dhv:label>
	           		</td>
    	       		<td>
    	       		<input type="text" name="dataDecreto" size="10" readonly="readonly" />
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataDecreto,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				<font color="red">*</font>
        	   		</td>
           		</tr>		
           		<tr>
           			<td  class="formLabel">
           				<dhv:label name="">Data Inizio</dhv:label>
	           		</td>
    	       		<td>
    	       		
    	       		<%-- 	<input type="text" name="dataInizioSterilizzazione" size="10" readonly="readonly" labelcampo="Data Inizio Sterilizzazione" nomecampo="iniziosterilizzazione" tipocontrollo="T19" />--%>
    	       		 	<input type="text" name="dataInizioSterilizzazione" size="10" readonly="readonly" labelcampo="Data Inizio Sterilizzazione" nomecampo="iniziosterilizzazione" />
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataInizioSterilizzazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				<font color="red">*</font>
	           	
        	   		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Data Fine</dhv:label>
	           		</td>
    	       		<td>
    	       		<input  type="text" name="dataFineSterilizzazione" size="10" readonly="readonly" />
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataFineSterilizzazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				<font color="red">*</font>
        	   		</td>
           		</tr>
<%
			//Flusso 251
		    if(pratica.getIdTipologiaPratica()==Pratica.idPraticaLP)
		    {
	%>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Veterinari</dhv:label>
	           		</td>
    	       		<td>
    	       		<%=veterinariPrivatiList.getHtmlSelect("veterinari",-1)%>
    	       		<font color="red">*</font>
        	   		</td>
           		</tr>
   <%
		    }
   %>        		
           		
           		
           		
<!--           		<td>	-->
<!--				-->
<!--           					<dhv:label name="">Nel caso in cui non vi siano gatti o cani padronali o catturati inserire il valore 0-->
<!--							</dhv:label>-->
<!--				-->
<!--			</td>-->
			

<%
			//Flusso 251
		    if(pratica.getIdTipologiaPratica()!=Pratica.idPraticaLP)
		    {
	%>
			<tr>
					<th colspan="2">
						<strong>
							<dhv:label name="">Se non ci sono gatti/cani padronali o catturati inserire 0</dhv:label>
						</strong>
					</th>
			</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Cani Padronali</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="number" name="totaleCaniPadronali"  size="10" title="Nel caso in cui non ci siano cani padronali inserire il valore 0"/>
    	       			<font color="red">*</font>
    	       		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Cani Catturati</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="number" name="totaleCaniCatturati" size="10" title="Nel caso in cui non ci siano cani catturati inserire il valore 0"/>
    	       			<font color="red">*</font>
        	   		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Gatti Padronali</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="number" name="totaleGattiPadronali" size="10" title="Nel caso in cui non ci siano gatti padronali inserire il valore 0"/>
    	       			<font color="red">*</font>
        	   		</td>
           		</tr>	
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Gatti Catturati</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="number" name="totaleGattiCatturati" size="10" title="Nel caso in cui non ci siano gatti catturati inserire il valore 0"/>
    	       			<font color="red">*</font>
        	   		</td>
           		</tr>
<%
		    }
		    else
		    {
%>	

			<tr>
					<th colspan="2">
						<strong>
							<dhv:label name="">Se non ci sono cani maschi o femmina inserire 0</dhv:label>
						</strong>
					</th>
			</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Cani Maschi da sterilizzare</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="number" name="totaleCaniMaschi"  size="10" title="Nel caso in cui non ci siano cani maschi inserire il valore 0"/>
    	       			<font color="red">*</font>
    	       		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Cani Femmina da sterilizzare</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="number" name="totaleCaniFemmina"  size="10" title="Nel caso in cui non ci siano cani femmina inserire il valore 0"/>
    	       			<font color="red">*</font>
    	       		</td>
           		</tr>
<%
		    }
%>		
           </table>
			<input type="hidden" name="dosubmit" value="true" />
	    </form>
</body>    
