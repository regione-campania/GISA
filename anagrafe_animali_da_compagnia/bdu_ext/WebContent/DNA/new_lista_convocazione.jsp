<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>

<%@ include file="../initPage.jsp" %>

<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>


<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>

<script language="JavaScript">
function checkFile(form){
	var fileCaricato = form.id;
	if (!fileCaricato.value.endsWith(".csv")){
		alert('Errore! Sono accettati solo file di tipo CSV.');
		form.id.value='';
	}
}


  function doCheck(form) {
	 
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
	  formtest=true;
	  message = "";
	  
	  if (form.denominazione.value==''){
	        message+="- Denominazione richiesta.\n";
			formtest= false;}
	 if (form.idComune.selectedIndex==0){
			 message+="- Comune richiesto.\n";
			formtest= false;}
	  
	  lanciaControlloDate();
	 // formTest = false;
     
	  if (form.id.value==''){
	        message+="- File richiesto.\n";
			formtest= false;}
   
   if (message!=''){
		  alert(message);
		  formtest=false;}

   return formtest;
  }


  function visualizzaCircoscrizioni()
  {


  						


  			DwrUtil.getListaCircoscrizioni(document.inputForm.idComune.value, valorizzaLista);
  			}
  			
  			


  function valorizzaLista(listaCircoscrizioni)
  {

    var select = document.forms['inputForm'].idCircoscrizione; //Recupero la SELECT

    i = 0;
    //indice utilizzato per i canili
    k = 0;

    //Azzero il contenuto della seconda select
    for (var j = select.length - 1; j >= 0; j--)
    	  	select.remove(j);

  /*	 var NewOpttmp = document.createElement('option');
  	 NewOpttmp.value=-1;
  	 NewOpttmp.text=" -- Nessuna Circoscrizione --";
  	 try{
  	 select.add(NewOpttmp, null); //Metodo Standard, non funziona con IE
  	 }
  	 catch(e){
  		 select.add(NewOpttmp); 
  	 }	*/
    while(i < listaCircoscrizioni.length){
  		
  			

  			 var NewOpt = document.createElement('option');
  			 NewOpt.value = listaCircoscrizioni[i].id;
  			 //alert(NewOpt.value);
  		 	 NewOpt.text =  listaCircoscrizioni[i].nomeCircoscrizione;
  			
  			 
  			  //Aggiungo l'elemento option
  			    try
  			    {
  			  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
  			    }catch(e){
  			  	  select.add(NewOpt); // Funziona solo con IE
  			    }
  			    i++;						   
  	}
    if (select.length>0){
        document.getElementById("idCircoscrizione").style.display="inline";
        document.getElementById("circoscrizioneAssente").style.display="none";
		}else{
			document.getElementById("idCircoscrizione").style.display="none";
			document.getElementById("circoscrizioneAssente").style.display="inline";
		}
			
      
      }
    
</script>
<body>
<form method="post" name="inputForm" action="ListaConvocazioneAction.do?command=SalvaListaConvocazione&auto-populate=true" enctype="multipart/form-data" onSubmit="return doCheck(this);">
<%-- Trails --%>
</br>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <dhv:label name="">Caricamento nuovo file</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>

<table width="50%" cellpadding="2" cellspacing="2" style="background-color:#E6E8EA" border="1">
<col width="10%">
  <col width="10%">
  <col width="10%">
  <col width="50%">
  
<tr>
<td nowrap><p align="right">Denominazione</p></td>
<td nowrap><input type="text" name="denominazione" id="denominazione" value=""></td>
<td nowrap>Escludi proprietari con pi&ugrave; di 
<select name="numeroCaniEsclusione" id="numeroCaniEsclusione">
<option value="-1">--</option>
<option value="10">1</option>
<option value="20">20</option>
<option value="10">30</option></select> cani</td>

</tr>

<tr>
<td nowrap><p align="right">Comune</p></td>
<td nowrap><%comuniList.setJsEvent("onChange=\"javascript:visualizzaCircoscrizioni(this);\"");	%>
	<%=comuniList.getHtmlSelect("idComune", -1) %></td>
<td nowrap><p align="right">Circoscrizione</p></td>
<td nowrap>&nbsp; <select id="idCircoscrizione" name="idCircoscrizione" style="display:none;"></select>
<label id="circoscrizioneAssente" name ="circoscrizioneAssente" style="display:none;">NESSUNA CIRCOSCRIZIONE</label></td>
</tr>

<tr>
<td nowrap><p align="right">Data Inizio</p></td>
<td nowrap><input readonly type="text" name="dataInizio"
				size="10" value=""
				nomecampo="dataInizio" tipocontrollo="T2, T20"
				labelcampo="Data Inizio" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataInizio,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
<td nowrap><p align="right">Data Fine</p></td>
<td nowrap><input readonly type="text" name="dataFine"
				size="10" value=""
				nomecampo="dataFine" tipocontrollo="T2, T20"
				labelcampo="Data Fine" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataFine,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
</tr>

<tr>
<td nowrap><p align="right">File</p></td>
<td nowrap><input type="file" name="id" size="45" onChange="checkFile(this.form)";  />
            <%= showAttribute(request, "fileError") %>
</td>
<td nowrap></td>
<td nowrap></td>
</tr>
</table></br>
<input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="upload" onClick="this.form.dosubmit.value='true';">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MicrochipImports.do?command=View';this.form.dosubmit.value='false';">
<input type="hidden" name="dosubmit" value="true">
</br></br><font color="red">*</font> I file devono essere in formtato CSV.<br>
File con un elevato numero di proprietari possono richiedere del tempo per essere caricati. <br>
Il caricamento potr&agrave; considerarsi concluso quando lo stato della lista risulter&agrave; "Terminato", con o senza errori.<br><br>
* Il Formato dei file previsto è: <br> I e II riga di intestazione, dalla III riga in poi dati.<br>
Es. : <br>
Numero Totale cani : 1.914 <br>						
codice_fiscale	    nome	cognome	specie	Num_Cani	data_nascita	indirizzo -  quartiere <br>
dfgghnfhnchnchhh	Rossi	Antonio	Cane	1	        10/02/1992	     VIA TEST TEST Nr.332 Isol.10 Pi.4Int.12- VOMERO 

</form>


</body>
