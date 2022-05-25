<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.utils.web.LookupElement"%><jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="categoria" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoAutorizzazzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script>
var campoLat;
	var campoLong;
	var field_id_impianto ;
	function setComboImpianti(idCategoria,idField)
	{
			field_id_impianto = idField;
			
		   	PopolaCombo.getValoriComboImpiantiStabilimenti(idCategoria,setValoriComboCallBack);
		   
	}

	function checkStabilimento(idAsl)
	{
			appeovalnumber = document.addAccount.account_number.value ;
		   	PopolaCombo.controlloEsuistenzaStabilimento(appeovalnumber,idAsl,checkStabilimentoCallBack);
		   
	}


	 function checkStabilimentoCallBack(returnValue)
	 {
		if (returnValue==false)
		{
			alert('Stabilimento non Esistente');
		}
		else
		{
			document.addAccount.submit();
			}
	 }
	 function setValoriComboCallBack(returnValue)
     {
	    	var select = document.getElementById(field_id_impianto); //Recupero la SELECT
         

         //Azzero il contenuto della seconda select
         for (var i = select.length - 1; i >= 0; i--)
       	  select.remove(i);

         indici = returnValue [0];
         valori = returnValue [1];
         //Popolo la seconda Select
         if (indici.length==0)
         {
        	 var NewOpt = document.createElement('option');
             NewOpt.value = -1; // Imposto il valore
        	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
             	NewOpt.title = valori[j];
             //Aggiungo l'elemento option
             try
             {
           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
             }catch(e){
           	  select.add(NewOpt); // Funziona solo con IE
             }
          }
         else
         {
         
         for(j =0 ; j<indici.length; j++){
         //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
         var NewOpt = document.createElement('option');
         NewOpt.value = indici[j]; // Imposto il valore
         if(valori[j] != null)
         	NewOpt.text = valori[j]; // Imposto il testo
         	NewOpt.title = valori[j];
         //Aggiungo l'elemento option
         try
         {
       	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
         }catch(e){
       	  select.add(NewOpt); // Funziona solo con IE
         }
         }
         }


     }
		var field_id_prodotti ;

	 function setComboProdotti(idCategoria,idField)
		{
		 	field_id_prodotti = idField;
				
			   	PopolaCombo.getValoriComboProdottiStabilimenti(idCategoria,setComboProdottiCallBack);
			   
		}

		 function setComboProdottiCallBack(returnValue)
	     {
		    	var select = document.getElementById(field_id_prodotti); //Recupero la SELECT
	         

	         //Azzero il contenuto della seconda select
	         for (var i = select.length - 1; i >= 0; i--)
	       	  select.remove(i);

	         indici = returnValue [0];
	         valori = returnValue [1];
	         //Popolo la seconda Select
	         if (indici.length==0)
	         {
	        	 var NewOpt = document.createElement('option');
	             NewOpt.value = -1; // Imposto il valore
	        	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
	             	NewOpt.title = valori[j];
	             //Aggiungo l'elemento option
	             try
	             {
	           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	             }catch(e){
	           	  select.add(NewOpt); // Funziona solo con IE
	             }
	          }
	         else
	         {
	        	 var NewOpt = document.createElement('option');
	             NewOpt.value = -1; // Imposto il valore
	        	 	NewOpt.text = 'Seleziona uno o piu prodotti'; // Imposto il testo
	             	NewOpt.title = valori['Seleziona uno o piu prodotti'];
	             	NewOpt.selected = true ;
	             //Aggiungo l'elemento option
	             try
	             {
	           	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	             }catch(e){
	           	  select.add(NewOpt); // Funziona solo con IE
	             }
	         for(j =0 ; j<indici.length; j++){
	         //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
	         var NewOpt = document.createElement('option');
	         NewOpt.value = indici[j]; // Imposto il valore
	         if(valori[j] != null)
	         	NewOpt.text = valori[j]; // Imposto il testo
	         	NewOpt.title = valori[j];
	         //Aggiungo l'elemento option
	         try
	         {
	       	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	         }catch(e){
	       	  select.add(NewOpt); // Funziona solo con IE
	         }
	         }
	         }


	     }

		 function setComboImpiantiProdotti(idCategoria,idField1,idField2)
		 {
			setComboImpianti(idCategoria,idField1);
			setComboProdotti(idCategoria,idField2);
		 }
	     

	
  	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
  	{
	   campoLat = campo_lat;
	   campoLong = campo_long;
	   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
	   
	   
	}
	function setGeocodedLatLonCoordinate(value)
	{
		campoLat.value = value[1];;
		campoLong.value =value[0];
		
	}

	 function doCheck(form){
		 
			  if(checkForm(form)) {
				  form.submit();
				  return true;
			  }
			  else
				  return false;
			  
		  }
	  
	function checkForm(form)
	{
		formTest=true ;
		msg='';
		if(form.name.value=='')
		{
			msg+=' - Controllare di aver inserito la ragione sociale\n';
			formTest = false ;
		}

		if(form.piva.value=='')
		{
			msg+=' - Controllare di aver inserito la partita iva\n';
			formTest = false ;
		}

		if(form.dateI.value=='')
		{
			msg+=' - Controllare di aver inserito la data presentazione istanza\n';
			formTest = false ;
		}

		if(form.address2city.value=='-1')
		{
			msg+=' - Controllare di aver inserito il Comune per la sede operativa\n';
			formTest = false ;
		}
		if(form.address1city.value=='')
		{
			msg+=' - Controllare di aver inserito il Comune per la sede Legale\n';
			formTest = false ;
		}
		if(form.address1line1.value=='')
		{
			msg +=' - Controllare di aver inserito un indirizzo per la sede Legale\n';
			formTest = false ;
		}

		if(form.address2line1.value=='')
		{
			msg +=' - Controllare di aver inserito un indirizzo per la sede operativa\n';
			formTest = false ;
		}

		if(form.address2latitude.value=='')
		{
			msg+=' - Controllare di aver inserito la latitudine per la sede operativa\n';
			formTest = false ;
		}
		if(form.address2longitude.value=='')
		{
			msg +=' - Controllare di aver inserito la longitudine per la sede operativa\n';
			formTest = false ;
		}

		if(form.address2zip.value=='')
		{
			msg +=' - Controllare di aver inserito il cap per la sede operativa\n';
			formTest = false ;
		}
		
		numSottoAttivita = form.size.value ;


		if (parseInt(numSottoAttivita)==0)
		{
			msg +=' - Controllare di aver Inserito almeno una sottoattivita\n';
			formTest = false ;
		}
		else
		{
		for(i=1;i<=parseInt(numSottoAttivita);i++)
		{
			if(document.getElementById('impianto_'+i).value=='-1')
			{
				msg +=' - Controllare di aver Selezionato un impianto per la sottoattivita '+i+'\n';
				formTest = false ;
			}
			if(document.getElementById('categoria_'+i).value=='-1')
			{
				msg +=' - Controllare di aver Selezionato una categoria per la sottoattivita '+i+'\n';
				formTest = false ;
			}
	
		}
	}	

		if(formTest==false)
		{
			alert(msg);
		}
		return formTest;
		
	
	}
	function azzeraElementi()
	{
		document.getElementById("elementi").value = "0" ;
		document.getElementById("size").value = "0" ;
	}
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>

<body onload="azzeraElementi()">

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Stabilimenti.do">Stabilimenti</a> >Richiesta di Istruttoria Per Stabilimento Esistente 
</td>
</tr>
</table>
<br/>

<form name = "addAccount" method="post" action="Stabilimenti.do?command=ModifyStabilimento">

<br/>


<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Approval Number</strong> </th>
		</tr>

				<tr class="containerBody">
				<td>
					<input type="text" name="account_number" value="">
					</td>
				</tr>
			
		
			
  
  </table>
  
  <input type = "button" value="Modifica Pratica" onclick="javascript:checkStabilimento(<%=User.getSiteId() %>) " >
  
</form>