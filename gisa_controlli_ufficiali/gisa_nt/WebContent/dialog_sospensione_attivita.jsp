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
<script>

function nascondiBottoni(form){
	form.salvaSospensione.disabled = true;
	form.esciSospensione.disabled = true;
	form.salvaSospensione.value = "ATTENDERE";
}



function openPopUpSospensioneAttivita()
{
	
	$( "#dialogSospensioneAttivita" ).dialog("open");
	}
$(function () {
	    
	 
	 $.fx.speeds._default = 1000;
	 
	 $( "#dialogSospensioneAttivita" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"SOSPENSIONE ATTIVITA'",
	        width:850,
	        height:370,
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
	       
	    }).prev(".ui-dialog-titlebar");
	 
  
});         
</script>


<div id="dialogSospensioneAttivita">

<form method="post" action="<%=request.getParameter("urlSubmitSospensione")%>" onsubmit="return checkDataSospensione(cambiaFormatoData(document.forms[0].elements['dataSospensioneAttivita'].value));nascondiBottoni(this);">

<input type = "hidden" name="idAnagrafica" value="<%=request.getParameter("idAnagrafica") %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>INDICARE LA DATA DI SOSPENSIONE ATTIVITA' </strong>
    </th>
  </tr>
   
    <tr>
      <td nowrap class="formLabel">
        Data
      </td>
      <td>
      
       <input type = "text" pattern="\d{1,2}/\d{1,2}/\d{4}" name="dataSospensioneAttivita"  id = "dataSospensioneAttivita" required="required" >
      	<a href="#" onClick="cal19.select(document.getElementById('dataSospensioneAttivita'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			
      </td>
    </tr>
    
      <tr>
      <td nowrap class="formLabel">
        Note
      </td>
      <td>
      <textarea rows="8" cols="50" name="noteSospensione"></textarea>		
      </td>
    </tr>
    
     <tr>
      <td colspan="2">
     
       
         <!-- <p align="left" style="margin-left: 2">
         <b>
         (ATTENZIONE) : L'operazione di Sospensione di Ufficio va utilizzata solo nel caso in cui la data di sospensione
         				di attività sia antecedente alla delibera 318/15.
       					Si  rende noto che un utilizzo scorretto di questa funzione potrebbe comportare incongruenze in
						fase di estrazione e rendicontazione dei dati.
       	</b>			
       		</p>		 -->	 
		      					 		
      </td>
    </tr>
   
    
</table>
 <input type="submit" value="SALVA" id="salvaSospensione">
 <input type="button" value="ESCI" id="esciSospensione" onclick="javasript:$( '#dialogSospensioneAttivita' ).dialog('close');">
</form>

</div>



<script>
	
	/*da dd/MM/yyyy che e' il formato atteso dal server a yyyy-MM-dd che e' il formato per il date di js */
	function cambiaFormatoData(originalStrDate)
	{
		if(originalStrDate.trim().length == 0)
			return '';
		
		var t = originalStrDate.split("/");
		var finalStrDate =  t[2]+"-"+t[1]+"-"+t[0];
		return finalStrDate;
	}
	
	function checkDataSospensione(strDataSospensione)
	{
		
		try
		{
			if(strDataSospensione == undefined || strDataSospensione.trim().length == 0)
			{

				//alert("DATA INVALIDA");
				return false;
			}
			var todayDate = new Date();
			var sospensioneDate = new Date(strDataSospensione);
			if(todayDate < sospensioneDate)
			{
				alert("Attenzione, la data di sospensione deve essere nel passato.")
				
				//alert("DATA INVALIDA");
				return false;
			}
			else
			{
				var strDataInizio = '<%= request.getParameter("data_inizio") != null ? request.getParameter("data_inizio") : ""%>';
				if(strDataInizio.trim().length > 0)
				{
					var dataInizioDate = new Date(strDataInizio);
					if(sospensioneDate < dataInizioDate)
					{
						alert("Attenzione, la data di sospensione non puo' essere precedente alla data inizio attivita' ("+strDataInizio+")");

						//alert("DATA INVALIDA");
						return false;
					}
				}
				
				
			}
			
			//alert("DATA VALIDA");
			return true;
		}
		catch(E)
		{
			//alert("DATA INVALIDA");
			return true;
		}
		
	}
</script>
