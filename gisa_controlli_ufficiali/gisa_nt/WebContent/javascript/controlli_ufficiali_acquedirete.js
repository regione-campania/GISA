/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function eliminaCheckList (idAudit,idControllo,orgId)
{
	if (confirm("Sei Sicuro di voler Eliminare Questa CheckList ? ")) 
	{
	    document.location = 'CheckListAcqueRete.do?command=Delete&id='+idAudit+'&idControllo='+idControllo+'&orgId='+orgId;
	}
	
}

function compilaCheckList(messaggio,orgId,idControllo,idControlloUfficiale,isPrincipale,form)
{

	//if(confirm(messaggio))
	//{

		if(isPrincipale=='1')
		{
			if(confirm(messaggio))
			{
			document.forms[form].action='CheckListAcqueRete.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=true&idControllo='+idControlloUfficiale
			setTimestampStartRichiesta();
			document.forms[form].submit();
			}
			}
		else
		{
			
			checklist_inserite = document.getElementById('checklist_inserite').value.split(';');
			isInserita = false ;
			for(i=0;i<checklist_inserite.length;i++)
			{
				if (checklist_inserite[i]==document.getElementById('accountSize').value)
				{
					isInserita = true ;
				}
			}
			if(isInserita == true)
			{
			if (confirm('Attenzione! La CheckList selezionata e\' stata gia\' inserita. Sei sicuro di voler inserire la stessa checklist') == true)
			{
				document.forms[form].action='CheckListAcqueRete.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=false&idControllo='+idControlloUfficiale
				setTimestampStartRichiesta();
				document.forms[form].submit();
			}
			}
			else
			{
				document.forms[form].action='CheckListAcqueRete.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=false&idControllo='+idControlloUfficiale
				setTimestampStartRichiesta();
				document.forms[form].submit();
			}
		
			
			
		//}
			

	}
	
}
function allegaFile(form,gotoPage)
{

document.forms[form].encoding="multipart/form-data";

document.forms[form].action = "AcqueReteVigilanza.do?command=UploadListaDistribuzione&goto="+gotoPage;
formTest = true;
message = "";
if(document.getElementById("fileAllegare").value == "")
{
		message += label("check.vigilanza.richiedente.selezionato","- Richiesto il file da allegare \r\n");
      formTest = false;
	
}
if(document.forms[form].subject.value == "")
{
		message += label("check.vigilanza.richiedente.selezionato","- Richiesto il campo Oggetto \r\n");
      formTest = false;
	
}
if(formTest==true)
{

document.forms[form].submit;

}
else
{
	alert(message);
	return false;
}
}

function eliminaFileAllegato(fid,orgId,folderid,form,gotoPage)
{


	document.forms[form].encoding="multipart/form-data";
document.forms[form].action = "AcqueReteVigilanza.do?command=DeleteListaDistribuzione&goto="+gotoPage+"&fid="+fid+"&orgId="+orgId+"&folderId="+folderid;

document.forms[form].submit;
}

function aggiornaCategoria(idControllo,orgId)
{
	if (confirm("Attenzione! Aggiornando la categoria di rischio si procede contestualmente anche alla chiusura automatica del controllo ufficiale. Saranno impossibili ulteriori modifiche.\n\nAttenzione! Procedendo alla chiusura del CU e al conseguente aggiornamento della categoria di rischio, non sarà possibile inserire una seconda checklist. Si prega di verificare bene i dati inseriti prima di confermare l'operazione.\n\n  Proseguire?")) 
	{
		document.details.action='CheckListAcqueRete.do?command=UpdateCategoria&idC='+idControllo+'&orgId='+orgId ;
		document.details.submit();
	}
}

function showCampi(tipoIspezione)
{

	if(tipoIspezione == "7")
	{

		document.getElementById("tableHidden").style.display="";
	}
	else
	{	if (document.getElementById("tableHidden")!=null)
		{
			document.getElementById("tableHidden").style.display="none";
	
		}
	}
	
}

function checkForm(form){
	  	
	  
  formTest = true;
  message = "";
 
  if (form.quantita!=null)
  {
  	if (trim(form.quantita.value)!='')
  	{
  		value = new String(form.quantita.value);
  		
  		 if (isNaN(value.replace(",", "."))){
  			 	message += label("","- Valore di Quantita Partita non Valido. Inserire Cifre\r\n");
  		    	formTest =  false;
  		 }
  		 else
  		 {
  			 form.quantita.value = trim(value.replace(",", ".")) ;
  		 }

  	}
  }
  
  if (form.quantitaBloccata!=null)
  {
  	if (trim(form.quantitaBloccata.value)!='')
  	{
  		value = new String(form.quantitaBloccata.value);
  		
  		 if (isNaN(value.replace(",", "."))){
  			 message += label("","- Valore di Quantita Partita Bloccata non Valido. Inserire Cifre\r\n");
  		    	formTest =  false;
  		 }
  		 else
  		 {
  			 form.quantitaBloccata.value = trim(value.replace(",", ".")) ;
  		 }

  	}
  }
  
  if (form.tipoIspezione.value != '3' )
  {
  if(form.id_linea_sottoposta_a_controllo!=null && form.id_linea_sottoposta_a_controllo.value =='-1')
	{
  	message += label("","- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
  	formTest =  false;
	}
  
  if(form.codici_selezionabili!=null && form.codici_selezionabili.value =='')
	{
  	message += label("","- Controllare di aver Selezionato almeno una linea di attivita.\r\n");
  	formTest =  false;
	}
  
  
  
  }
  if (form.nucleo_ispettivo_1.value == '-1')
  {
  	message += label("","- Controllare di aver Selezionato almeno un nucleo Ispettivo.\r\n");
  	formTest = false;
  }
  
  for (i = 1 ; i <=10 ; i++)
  {
  	if (document.getElementById('nucleo_ispettivo_'+i) != null)
  	{
  		if (document.getElementById('nucleo_ispettivo_'+i).value != '-1')
  		{
  		value = document.getElementById('nucleo_ispettivo_'+i).value ;
  	
  		if (value == '1' )
  		{
  			if (document.getElementById('Veterinari_'+i).value == '-1')
  			{
  				message += label("","- Controllare di aver Selezionato un Componente dalla Lista dei Veterinari.\r\n");
  			 	formTest = false;
  			}
  		}
  		else
  		if (value == '2')
  		{
  		
  			if (document.getElementById('Medici_'+i).value == '-1')
  			{
  				
  				message += label("","- Controllare di aver Selezionato un Componente dalla Lista dei Medici.\r\n");
  			 	formTest = false;
  			}
  		
  		}
  		else
  		if (value == '23')
  		{
  			if (document.getElementById('Tpal_'+i).value == '-1')
  			{
  				message += label("","- Controllare di aver Selezionato un Componente dalla Lista dei Tpal.\r\n");
  			 	formTest = false;
  			}
  			
  		}
  		else
  		if (value == '24')
  		{
  			if (document.getElementById('Ref_'+i).value == '-1')
  			{
  				message += label("","- Controllare di aver Selezionato un Componente dalla Lista dei Referenti allerta.\r\n");
  			 	formTest = false;
  			}
  			
  		}
  		else
  		if (value == '25')
  		{
  			if (document.getElementById('Amm_'+i).value == '-1')
  			{
  				message += label("","- Controllare di aver Selezionato un Componente dalla Lista Amministrativi Asl.\r\n");
  			 	formTest = false;
  			}
  			
  		}
  		else
  		if (document.getElementById('Utente_'+i).value == '')
  		{
  				
  				message += label("","- Controllare di aver Inserito un Componente.\r\n");
  			 	formTest = false;
  		}
  		}
  		
  	}
  }
  
  
		if(form.alertText!= null ){
			
  		if (form.alertText.value == "" ){
  			if (form.tipoIspezione.value != 3)
  			{
  				message += label("","- Controllare di aver inserito una linea di attivita.\r\n");
  			 	formTest = false;
  			}
  		}
  		}
  
  	if(!form.assignedDate.value == "" && !form.dataFineControllo.value == ""){
			
  		if (controlloDataFine(form.assignedDate.value, form.dataFineControllo.value)== true ){
  			
  			message += label("","- La Data di Inizio Controllo non puo\' essere successiva a quella di Fine Controllo.\r\n");
  			 formTest = false;
  			}
  		}


  	
  	if(form.contributi!=null)
  	{
  	var numero = form.contributi.value;
  	
  	var arr_num = numero.split(".");
  	
  	if (numero.indexOf(",") != (-1))
  	{
  	    
  		message += label("check.vigilanza.richiedente.selezionato","- Controllare che il sepratore delle cifre decimali per i contributi sia il punto anzich� la virgola \r\n");
	      formTest = false;
  	}
  	}

	if(form.tipoCampione.value!="-1"){

		if(form.tipoCampione.value=="3"){ // se ho selezionato audit

			if(form.auditTipo.value!=-1){
			if(form.auditTipo.value=="1"){
		if(form.tipoAudit.value=="-1"){
			 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
		      formTest = false;
		}
else{
			
			if(form.tipoAudit.value=="2"){// se ho selezionato bpi

				if(form.bpi!=null && (form.bpi.value=="-1" || form.bpi.value=="")){// se non ho slezionato niente da bp�i
					 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"BPI\" sia stato popolato\r\n");
				      formTest = false;
				}



				
				}else{
					if(form.tipoAudit.value=="3"){// se ho selezionato haccp

						if(form.haccp!=null && (form.haccp.value=="-1" || form.haccp.value == "")){// se non ho slezionato niente da haccp
							 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"HACCP\" sia stato popolato\r\n");
						      formTest = false;
						}

					}

					}

			


			}
		}}
			else
			{
				 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"tipo Audit\" sia stato popolato\r\n");
			      formTest = false;

				}

		}else{

			if(form.tipoCampione.value=="4"){// se ho selezionato ispezione

				if(form.tipoIspezione.value=="-1") // se non ho selezionato niente come tipo ispezione(in monitoraggio non , e sorveglianza)
			{
					 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Tipo di Ispezione\" sia stato popolato\r\n");
				      formTest = false;

					}

				else{

					if(form.tipoIspezione.value=="2") // se  ho selezionato tipo ispezione in monitoraggio 
					{
						if(form.piano_monitoraggio!=null && form.piano_monitoraggio.value=="-1" ){//se non ho selezionato nessun piano di monitoraggio

							 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il \"Piano di monitoraggio\" sia stato selezionato\r\n");
						      formTest = false;

							}
				}
					


					}

			if(form.ispezione.value=="-1" || form.ispezione.value==""){//se non ho selezionato nessun piano di monitoraggio

								 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Oggetto del controllo\" sia stato popolato\r\n");
							      formTest = false;

								}
			
			 value_isp = document.getElementById("ispezione");
			 for(i=0;i<value_isp.length;i++)
			 {
				 if (value_isp[i].selected)
				 {	 
					 if(value_isp[i].value == "69" || value_isp[i].value == "70" || value_isp[i].value == "71"  
							|| value_isp[i].value == "72" || value_isp[i].value == "73" || value_isp[i].value  == "74" 
								|| value_isp[i].value == "75" || value_isp[i].value == "76" ) {
						 
						 if(document.getElementById("animalitrasp").value == ""){
							 message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Specie Animali Trasportati\" sia stato popolato\r\n");
						     formTest = false; 
						 }
						 //else {
							 
						//	 value = document.getElementById("animalitrasp").value;
						//	 if(document.getElementById('num_specie'+value).value == ""){
						 //   		message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Numero\" relativo alla specie selezionata sia popolato\r\n");
						  //  		formTest = false;
						//	 } 
						 //}
						 
						 
					 }
				 }
			 }
				
			 
			 /*Controllare il numero relativo alla specie*/
			 specie = document.getElementById('animalitrasp');

				 for(i=0;i<specie.length;i++)
				 {
						 if(!specie[i].selected){
							 if(specie[i].value != -1){
								 var elem = document.getElementById('num_specie'+specie[i].value);
								 elem.value = -1;
							 }
							
							 //= '-1';
						 }
						 else {
							 
							 if(document.getElementById('num_specie'+specie[i].value) != null){
								 
								 if(document.getElementById('num_specie'+specie[i].value).value == "" || document.getElementById('num_specie'+specie[i].value).value == "-1"){
							    		message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Numero\" relativo alla specie selezionata sia popolato\r\n");
							    		formTest = false;
								 } 
								 
								 	
							 }
							 
						 }
				 }

			}

	}
	}

	if (document.getElementById("size_p") != null)
	{
		 numeroElementi = document.getElementById("size_p").value;
		
		 for(i=1;i<=numeroElementi ; i++)
		 {
			 
			 if (document.getElementById("mc_"+i).value == "" && document.getElementById("razza_"+i).value == "" && document.getElementById("sesso_"+i).value == ""
				 && document.getElementById("mantello_"+i).value == "" &&  document.getElementById("taglia_"+i).value == "" && document.getElementById("data_nascita_cane_"+i).value == "" )
				 
			 {
				  message += label("check.vigilanza.richiedente.selezionato","- Controllare di aver compilato qualche informazione sul cane controllato\r\n");
			      formTest = false;
			      break ;
			 }
			 
		 }
		 
		
	}
	
	 
		

	 if (form.nome_conducente != null && form.nome_conducente.value == "") {
	      message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nominativo Conduttore\" sia stato popolato\r\n");
	      formTest = false;
	    }
	 
	
	 
	 if (form.nominativo_proprietario != null && form.nominativo_proprietario.value == "") {
	      message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"Nominativo Proprietario\" sia stato popolato\r\n");
	      formTest = false;
	    }
	 
	 if (form.cf_proprietario != null && form.cf_proprietario.value == "") {
	      message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"CF Proprietario\" sia stato popolato\r\n");
	      formTest = false;
	    }
	
	
  if (form.siteId.value == "-1") {
    message += label("check.vigilanza.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
    formTest = false;
  }
  

  if (form.orgId.value == "-1") {
    message += label("check.vigilanza.richiedente.selezionato","- Controllare che \"Impresa\" sia stato selezionato\r\n");
    formTest = false;
  }
 
  //Controllo per il campo categoria trasportata
  if( document.getElementById("categoriaTrasportata") != null ){
  	if (document.getElementById("categoriaTrasportata").value == -1)
  	{
      	  message += label("check.vigilanza.richiedente.selezionato","- Controllare che \"Categoria Trasportata\" sia stato selezionato\r\n");
            formTest = false;
  	}
  } 
 
  if (form.tipoCampione.value == "-1") {
    message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Controllo\" sia stato popolato\r\n");
    formTest = false;
  }
  
  if (form.assignedDate.value == "") {
    message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato\r\n");
    formTest = false;
  }else{
  	flag= controllo_data(form.assignedDate.value)
  
		if(flag==false){
			formTest = false;
			  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Data Inizio Controllo\" sia stato popolato Correttamente\r\n");
			     

			}else{

				/*if(controlloData(form.assignedDate.value)==false){
				formTest = false;
				  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Data Inizio Controllo\" sia stato Precedente o uguale alla data attuale\r\n");
				}*/   
				}

      }
  messaggio = false;

  if(form.tipoIspezione.value == "7")
  {


  	 if(form.idAllerta.value == "")
  	 {
  		 formTest = false;
    		 message += label("check.vigilanza.data_richiesta.selezionato","- Controllare  di aver selezionato una  \"Allerta \" \r\n");
    	
		
      }

  if(form.esitoControllo.value==13 || form.esitoControllo.value==14 )
  {
		if(form.destinazioneDistribuzione.value==-1)
		{
			formTest = false;
			  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che i campi relativi a \"Destinazione Distribuzione partita\" siano stati popolati\r\n");
			
		}

		if(form.subject!=null && form.subject.value=="")
		{
			formTest = false;
			  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare che il campo \"Oggetto per lista distribuzione\" sia stato popolato\r\n");
			
		}
		

		if(document.getElementById("fileAllegare")!= null && document.getElementById("fileAllegare").value == "")
		{
			formTest = false;
			  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare di aver selezionato una \"Lista di distribuzione\" \r\n");
			
		}
		if(form.isAllegato.value =="false")
		{
			formTest = false;
			  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare di aver allegato il file cliccando sul pulsante  \"Allega\" \r\n");
			
		}
      
  }

  if(form.esitoControllo.value==-1)
  {
  	formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare di aver Selezionato un   \"Esito Controllo\" \r\n");
		
  }
  else
  {
  	 if(form.esitoControllo.value==7)
  	 {
      	 if(form.dataddt.value == "")
      	 {
      		 formTest = false;
     		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare  di aver inserito \"La Data Per DDT \" \r\n");
     	
           }

      	 if(form.numDdt.value == "")
      	 {
      		 formTest = false;
     		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare di aver inserito \"Numero DDT \" \r\n");
     	
           }
          	 
   	    
  	 }
  	 else
  	 {
           
  		 if(form.esitoControllo.value==8)
      	 {
          	
  			 if(form.quantita.value == "")
          	 {
          		 formTest = false;
         		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare  di aver inserito La \"Quantita \" \r\n");
         	
               }
       	    
      	 }
  		 else
  		 {
  			 if(form.esitoControllo.value==10 || form.esitoControllo.value==11)
  	    	 {
  				 if(form.quantitaBloccata.value == "")
              	 {
              		 formTest = false;
             		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare di aver inserito La \"Quantita Bloccata \" \r\n");
             	
                   }
  	     	    
  	    	 }
      	 }


      }

      
  }

	 
	value = form.azioniAdottate;
	settatoAzione1 = false;
	settatoAzione2 = false;
	for(i=0;i<value.length;i++)
	{
		if(value[i].selected==true)
		{

	if((value[i].value == "-1") || (value[i].value == ""))
	{
		 formTest = false;
		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare  di non aver selezionato la voce \"Seleziona Voce \" da Azioni Adottate \r\n");
	
	
	}
	else
	{
		if(value[i].value == "3")
		{
			if(form.articoliAzioni.value=="-1")
			{
				 formTest = false;
		  		  message += label("check.vigilanza.data_richiesta.selezionato","- Controllare  di aver Selezionato un \"Articolo \"  \r\n");
		  	

			}
			else
			{
				messaggio=true;
			}
		}
	}
		}

	}

  }
  
 
  if (form.closeNow){
    if (form.closeNow.checked && form.solution.value == "") {
      message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
      formTest = false;
    }
  }
 
  
 
   

  if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
   
  	if(form.tipoIspezione.value == 7)
  	{
  	
  		showMessaggioAllarme (form)	;
  		
  	}
  	
  	if (form != null&& form.tipoIspezione.value != 3) 
		{
			if (form.id_linea_sottoposta_a_controllo !=null)
			{
				
				alert('"ATTENZIONE! Qualora siano state controllate, nel corso dello stesso controllo, piu\' linee attivita\' occorre inserire piu\' controlli (uno per ogni linea attivita\' sottoposta a controllo)"'.toUpperCase());
			} 
		}
  	
  }

  
  
 form.submit();
 // win.document.location=link;
  loadModalWindow();
  return true;
  }

function abilitaNoteDescrizioni()
{
	
	value = document.getElementById("ispezione")
	att1 = false;
	att2 = false;
	att3 = false;
	att4 = false;
	att5 = false;
	att6 = false;
	att7 = false;
	att8 = false;
	for(i=0;i<value.length;i++)
	{
		if (value[i].selected)
		{
			if (value[i].value == "25")
			{
				att1 = true ;
				
			}
			
				if (value[i].value == "37")
			{
				att2=true;
			}
			
				if (value[i].value == "53")
			{
				att5=true;
			}
			
			
				if (value[i].value == "59")
			{
				att6=true;
			}
			
			
				if (value[i].value == "63")
			{
				att7=true;
			}
				
				if (value[i].value == "67")
			{
					att3=true;
			}
				
				
				if (value[i].value == "68")
			{
					att4=true;
			}
			
				if (value[i].value == "76")
			{
						att8=true;
			}
			
			
		}
			
	}
	
	if (att1 == true)
	{
		document.getElementById("desc_note1").style.display="block";
	}
	else
	{
		document.getElementById("desc_note1").style.display="none";
	}
	
	if (att2 == true)
	{
		document.getElementById("desc_note2").style.display="block";
	}
	else
	{
		document.getElementById("desc_note2").style.display="none";
	}
	
	if (att3 == true)
	{
		document.getElementById("desc_note3").style.display="block";
	}
	else
	{
		document.getElementById("desc_note3").style.display="none";
	}
	
	if (att4 == true)
	{
		document.getElementById("desc_note4").style.display="block";
	}
	else
	{
		document.getElementById("desc_note4").style.display="none";
	}
	
	if (att5 == true)
	{
		document.getElementById("desc_note5").style.display="block";
	}
	else
	{
		document.getElementById("desc_note5").style.display="none";
	}
	
	if (att6 == true)
	{
		document.getElementById("desc_note6").style.display="block";
	}
	else
	{
		document.getElementById("desc_note6").style.display="none";
	}
	
	if (att7 == true)
	{
		document.getElementById("desc_note7").style.display="block";
	}
	else
	{
		document.getElementById("desc_note7").style.display="none";
	}
	
	if (att8 == true)
	{
		document.getElementById("desc_note8").style.display="block";
	}
	else
	{
		document.getElementById("desc_note8").style.display="none";
	}
	
}

//Abilita la visualizzazione della lookup_specie_trasportata 
function abilitaSpecieTrasportata(){
	
	value = document.getElementById("ispezione")
	specie = false;
	
	for(i=0;i<value.length;i++)
	{
		if (value[i].selected)
		{
			if (value[i].value == "69" || value[i].value == "70" || value[i].value == "71"  
				|| value[i].value == "72" || value[i].value == "73" || value[i].value == "74" 
				|| value[i].value == "75" || value[i].value == "76")
			{
				specie = true ;
				
			}
		
		}
			
	}
	
	if (specie == true)
	{
		document.getElementById("specieT").style.display="";
	}
	else
	{
		document.getElementById("specieT").style.display="none";
	}
	
}


function abilitaNumCapi()
{
	
	value = document.getElementById("animalitrasp")
	att1 = false;
	att2 = false;
	att3 = false;
	att4 = false;
	att5 = false;
	att6 = false;
	att7 = false;
	att8 = false;
	att9 = false;
	att10 = false;
	att11 = false;
	att12 = false;
	att13 = false;
	att14 = false;
	att15 = false;
	
	for(i=0;i<value.length;i++)
	{
		if (value[i].selected)
		{
			if (value[i].value == "1")
			{
				att1 = true ;
				
			}
			
			if (value[i].value == "2")
			{
				att2=true;
			}
			
			if (value[i].value == "4")
			{
				att3=true;
			}
			
			
			if (value[i].value == "6")
			{
				att4=true;
			}
			
			
			if (value[i].value == "10")
			{
				att5=true;
			}
				
			if (value[i].value == "11")
			{
				att6=true;
			}
				
			if (value[i].value == "12")
			{
				att7=true;
			}
			
			if (value[i].value == "13")
			{
				att8=true;
			}
			
			if (value[i].value == "14")
			{
				att9=true;
			}
			
			if (value[i].value == "15")
			{
				att10=true;
			}
			
			if (value[i].value == "16")
			{
				att11=true;
			}
		
			if (value[i].value == "18")
			{
				att12=true;
			}
			
			if (value[i].value == "19")
			{
				att13=true;
			}
			
			if (value[i].value == "20")
			{
				att14=true;
			}
			
			if (value[i].value == "21")
			{
				att15=true;
			}
			
		}
			
	}
	
	if (att1 == true)
	{
		document.getElementById("num_capo1").style.display="";
	}
	else
	{
		document.getElementById("num_capo1").style.display="none";
	}
	
	if (att2 == true)
	{
		document.getElementById("num_capo2").style.display="";
	}
	else
	{
		document.getElementById("num_capo2").style.display="none";
	}
	
	if (att3 == true)
	{
		document.getElementById("num_capo3").style.display="";
	}
	else
	{
		document.getElementById("num_capo3").style.display="none";
	}
	
	if (att4 == true)
	{
		document.getElementById("num_capo4").style.display="";
	}
	else
	{
		document.getElementById("num_capo4").style.display="none";
	}
	
	if (att5 == true)
	{
		document.getElementById("num_capo5").style.display="";
	}
	else
	{
		document.getElementById("num_capo5").style.display="none";
	}
	
	if (att6 == true)
	{
		document.getElementById("num_capo6").style.display="";
	}
	else
	{
		document.getElementById("num_capo6").style.display="none";
	}
	
	if (att7 == true)
	{
		document.getElementById("num_capo7").style.display="";
	}
	else
	{
		document.getElementById("num_capo7").style.display="none";
	}
	
	if (att8 == true)
	{
		document.getElementById("num_capo8").style.display="";
	}
	else
	{
		document.getElementById("num_capo8").style.display="none";
	}
	
	if (att9 == true)
	{
		document.getElementById("num_capo9").style.display="";
	}
	else
	{
		document.getElementById("num_capo9").style.display="none";
	}
	if (att10 == true)
	{
		document.getElementById("num_capo10").style.display="";
	}
	else
	{
		document.getElementById("num_capo10").style.display="none";
	}
	if (att11 == true)
	{
		document.getElementById("num_capo11").style.display="";
	}
	else
	{
		document.getElementById("num_capo11").style.display="none";
	}
	if (att12 == true)
	{
		document.getElementById("num_capo12").style.display="";
	}
	else
	{
		document.getElementById("num_capo12").style.display="none";
	}
	if (att13 == true)
	{
		document.getElementById("num_capo13").style.display="";
	}
	else
	{
		document.getElementById("num_capo13").style.display="none";
	}
	if (att14 == true)
	{
		document.getElementById("num_capo14").style.display="";
	}
	else
	{
		document.getElementById("num_capo14").style.display="none";
	}
	if (att15 == true)
	{
		document.getElementById("num_capo15").style.display="";
	}
	else
	{
		document.getElementById("num_capo15").style.display="none";
	}
	
	
	
}



function mostraCampo(form){
	valore = document.forms[form].nucleoIspettivo.value;
	


if(valore==-1){
	document.getElementById("componenteNucleoSelectSian").style.display="none";
	document.getElementById("componenteNucleoSelectTpal").style.display="none";
	document.getElementById("componenteNucleoSelect").style.display="none";
	document.getElementById("nucleo1").style.display="none";
	
}else{
	
	if(valore == 1 )
	{
		
		document.getElementById("componenteNucleoSelectSian").style.display="none";
		document.getElementById("componenteNucleoSelectTpal").style.display="none";
		document.getElementById("nucleo1").style.display="none";
		document.getElementById("componenteNucleoSelect").style.display="block";
	}
	else{


		if(valore==2){
			document.getElementById("componenteNucleoSelect").style.display="none";
			document.getElementById("componenteNucleoSelectTpal").style.display="none";
			document.getElementById("componenteNucleoSelectSian").style.display="block";
			document.getElementById("nucleo1").style.display="none";
		}
		else{

			if(valore == "23")
			{
				document.getElementById("componenteNucleoSelect").style.display="none";
				document.getElementById("componenteNucleoSelectTpal").style.display="block";
				document.getElementById("componenteNucleoSelectSian").style.display="none";
				document.getElementById("nucleo1").style.display="none";
				
			}
			else
			{
				if(document.getElementById("componenteNucleoSelectSian")!=null)
				{
					document.getElementById("componenteNucleoSelectSian").style.display="none";
				}
				if(document.getElementById("componenteNucleoSelectTpal")!=null)
				{
					document.getElementById("componenteNucleoSelectTpal").style.display="none";
				}
				if(document.getElementById("componenteNucleoSelect")!=null)
				{
					document.getElementById("componenteNucleoSelect").style.display="none";
				}
				if(document.getElementById("nucleo1")!=null)
				{
					document.getElementById("nucleo1").style.display="block";
				}
			}
			}



		}
		
}
}

function mostraCampo2(form){

	

	valore= document.forms[form].nucleoIspettivoDue.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect2").style.display="none";
		document.getElementById("componenteNucleoSelectSian2").style.display="none";
		document.getElementById("componenteNucleoSelectTpal2").style.display="none";
		document.getElementById("nucleo2").style.display="none";
		
	}

	if(valore==1 ){
document.getElementById("componenteNucleoSelect2").style.display="block";
document.getElementById("componenteNucleoSelectTpal2").style.display="none";
document.getElementById("nucleo2").style.display="none";
document.getElementById("componenteNucleoSelectSian2").style.display="none";


		}else{

			if(valore==2){
				document.getElementById("componenteNucleoSelectTpal2").style.display="none";
				document.getElementById("componenteNucleoSelect2").style.display="none";
				document.getElementById("nucleo2").style.display="none";
				document.getElementById("componenteNucleoSelectSian2").style.display="block";

			
				
				}else{
					if(valore == 23)
					{
						document.getElementById("componenteNucleoSelectTpal2").style.display="block";
						document.getElementById("componenteNucleoSelect2").style.display="none";
						document.getElementById("nucleo2").style.display="none";
						document.getElementById("componenteNucleoSelectSian2").style.display="none";
					}
					else
					{
						document.getElementById("componenteNucleoSelectSian2").style.display="none";
						document.getElementById("componenteNucleoSelectTpal2").style.display="none";
						document.getElementById("componenteNucleoSelect2").style.display="none";
						document.getElementById("nucleo2").style.display="block";
					}
			}
			}
	

	
}



function mostraCampo3(form){


	valore= document.forms[form].nucleoIspettivoTre.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelect3").style.display="none";
		document.getElementById("componenteNucleoSelectSian3").style.display="none";
		document.getElementById("nucleo3").style.display="none";
		
	}

	if(valore==1){
document.getElementById("componenteNucleoSelect3").style.display="block";
document.getElementById("nucleo3").style.display="none";
document.getElementById("componenteNucleoSelectSian3").style.display="none";
document.getElementById("componenteNucleoSelectTpal3").style.display="none";


		}else{
if(valore==2){
	document.getElementById("componenteNucleoSelect3").style.display="none";
	document.getElementById("nucleo3").style.display="none";
	document.getElementById("componenteNucleoSelectSian3").style.display="block";
	document.getElementById("componenteNucleoSelectTpal3").style.display="none";
	
}else{
	if(valore == 23)
	{
		document.getElementById("componenteNucleoSelectTpal3").style.display="block";
		document.getElementById("componenteNucleoSelect3").style.display="none";
		document.getElementById("nucleo3").style.display="none";
		document.getElementById("componenteNucleoSelectSian3").style.display="none";
	}
	else
	{
			document.getElementById("componenteNucleoSelectTpal3").style.display="none";
			document.getElementById("componenteNucleoSelectSian3").style.display="none";
			document.getElementById("componenteNucleoSelect3").style.display="none";
			document.getElementById("nucleo3").style.display="block";
	}
	
}
	
	}
	
}


function mostraCampo4(form){

	
	valore= document.forms[form].nucleoIspettivoQuattro.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelectTpal4").style.display="none";
		document.getElementById("componenteNucleoSelect4").style.display="none";
		document.getElementById("componenteNucleoSelectSian4").style.display="none";
		document.getElementById("nucleo4").style.display="none";
		
	}

	if(valore==1 ){
		document.getElementById("componenteNucleoSelectTpal4").style.display="none";
		document.getElementById("componenteNucleoSelectSian4").style.display="none";
		document.getElementById("componenteNucleoSelect4").style.display="block";
		document.getElementById("nucleo4").style.display="none";



		}else{
			if(valore==2){
				document.getElementById("componenteNucleoSelectTpal4").style.display="none";
				document.getElementById("componenteNucleoSelectSian4").style.display="block";
				document.getElementById("componenteNucleoSelect4").style.display="none";
				document.getElementById("nucleo4").style.display="none";
				}
			else{
				
				if(valore == "23")
				{
					document.getElementById("componenteNucleoSelectTpal4").style.display="block";
					document.getElementById("componenteNucleoSelectSian4").style.display="none";
					document.getElementById("componenteNucleoSelect4").style.display="none";
					document.getElementById("nucleo4").style.display="none";
					
					
					
				}
				else
				{
					document.getElementById("componenteNucleoSelectTpal4").style.display="none";
					document.getElementById("componenteNucleoSelectSian4").style.display="none";
					document.getElementById("componenteNucleoSelect4").style.display="none";
					document.getElementById("nucleo4").style.display="block";
					
					
				}
				
			}
	
		}
	
}


function mostraCampo5(form){
	

	valore= document.forms[form].nucleoIspettivoCinque.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelectTpal5").style.display="none";
		document.getElementById("componenteNucleoSelect5").style.display="none";
		document.getElementById("componenteNucleoSelectSian5").style.display="none";
		document.getElementById("nucleo5").style.display="none";
		
	}

	if(valore==1 ){
		document.getElementById("componenteNucleoSelectTpal5").style.display="none";
		document.getElementById("componenteNucleoSelect5").style.display="block";
		document.getElementById("nucleo5").style.display="none";
		document.getElementById("componenteNucleoSelectSian5").style.display="none";


		}else{
			if(valore==2){
				document.getElementById("componenteNucleoSelectTpal5").style.display="none";
				document.getElementById("componenteNucleoSelect5").style.display="none";
				document.getElementById("nucleo5").style.display="none";
				document.getElementById("componenteNucleoSelectSian5").style.display="block";


			}
			else{
				if(valore == "23")
				{
					document.getElementById("componenteNucleoSelectTpal5").style.display="block";
					document.getElementById("componenteNucleoSelect5").style.display="none";
					document.getElementById("nucleo5").style.display="none";
					document.getElementById("componenteNucleoSelectSian5").style.display="none";
					
				}
				else
				{
					document.getElementById("componenteNucleoSelectTpal5").style.display="none";
					document.getElementById("componenteNucleoSelectSian5").style.display="none";
					document.getElementById("componenteNucleoSelect5").style.display="none";
					document.getElementById("nucleo5").style.display="block";
					
				}

			
			}
		}

	
}


function mostraCampo6(form){
	

	valore= document.forms[form].nucleoIspettivoSei.value;

	if(valore==-1){
		document.getElementById("componenteNucleoSelectTpal6").style.display="none";
		document.getElementById("componenteNucleoSelectSian6").style.display="none";
		document.getElementById("componenteNucleoSelect6").style.display="none";
		document.getElementById("nucleo6").style.display="none";
		
	}
	if(valore==1){
		document.getElementById("componenteNucleoSelectTpal6").style.display="none";
		document.getElementById("componenteNucleoSelect6").style.display="block";
		document.getElementById("nucleo6").style.display="none";
		document.getElementById("componenteNucleoSelectSian6").style.display="none";


		}else{

			if(valore==2){
				document.getElementById("componenteNucleoSelectTpal6").style.display="none";
				document.getElementById("componenteNucleoSelect6").style.display="none";
				document.getElementById("nucleo6").style.display="none";
				document.getElementById("componenteNucleoSelectSian6").style.display="block";

				}else{
					if(valore == "23")
					{
						
						document.getElementById("componenteNucleoSelectTpal6").style.display="block";
						document.getElementById("componenteNucleoSelect6").style.display="none";
						document.getElementById("nucleo6").style.display="none";
						document.getElementById("componenteNucleoSelectSian6").style.display="none";
					}
					else
					{
						document.getElementById("componenteNucleoSelectTpal6").style.display="none";
						document.getElementById("componenteNucleoSelectSian6").style.display="none";
						document.getElementById("componenteNucleoSelect6").style.display="none";
						document.getElementById("nucleo6").style.display="block";	
					}
					

							
					}
		}

	
}


function mostraCampo7(form){


	valore= document.forms[form].nucleoIspettivoSette.value;

	if(valore==-1){
		document.getElementById("componenteNucleoSelectTpal7").style.display="none";
		document.getElementById("componenteNucleoSelect7").style.display="none";
		document.getElementById("componenteNucleoSelectSian7").style.display="none";
		document.getElementById("nucleo7").style.display="none";
		
	}
	if(valore==1){
		document.getElementById("componenteNucleoSelectTpal7").style.display="none";
		document.getElementById("componenteNucleoSelectSian7").style.display="none";
		document.getElementById("componenteNucleoSelect7").style.display="block";
		document.getElementById("nucleo7").style.display="none";

		}else{
			if(valore==2){
				document.getElementById("componenteNucleoSelectTpal7").style.display="none";
				document.getElementById("componenteNucleoSelectSian7").style.display="block";
				document.getElementById("componenteNucleoSelect7").style.display="none";
				document.getElementById("nucleo7").style.display="none";

			}
			else
			{
				if(valore == "23")
				{
					document.getElementById("componenteNucleoSelectTpal7").style.display="block";
					document.getElementById("componenteNucleoSelectSian7").style.display="none";
					document.getElementById("componenteNucleoSelect7").style.display="none";
					document.getElementById("nucleo7").style.display="none";
					
				}
				else
				{
					document.getElementById("componenteNucleoSelectTpal7").style.display="none";
					document.getElementById("componenteNucleoSelectSian7").style.display="none";
					document.getElementById("componenteNucleoSelect7").style.display="none";
					document.getElementById("nucleo7").style.display="block";
					
				}
			
			}}
	

	
}


function mostraCampo8(form){

		valore= document.forms[form].nucleoIspettivoOtto.value;
		if(valore==-1){
			document.getElementById("componenteNucleoSelectTpal8").style.display="none";
			document.getElementById("componenteNucleoSelect8").style.display="none";
			document.getElementById("componenteNucleoSelectSian8").style.display="none";
			document.getElementById("nucleo8").style.display="none";
			
		}

	if(valore==1 ){
		document.getElementById("componenteNucleoSelectTpal8").style.display="none";
		document.getElementById("componenteNucleoSelectSian8").style.display="none";
		document.getElementById("componenteNucleoSelect8").style.display="block";
		document.getElementById("nucleo8").style.display="none";



		}else{

if(valore==2){
	document.getElementById("componenteNucleoSelectTpal8").style.display="none";
	document.getElementById("componenteNucleoSelectSian8").style.display="block";
	document.getElementById("componenteNucleoSelect8").style.display="none";
	document.getElementById("nucleo8").style.display="none";

	
}else{
	if(valore == "23" )
	{
		document.getElementById("componenteNucleoSelectTpal8").style.display="block";
		document.getElementById("componenteNucleoSelectSian8").style.display="none";
		document.getElementById("componenteNucleoSelect8").style.display="none";
		document.getElementById("nucleo8").style.display="none";
		
		
	}
	else
	{
			document.getElementById("componenteNucleoSelectSian8").style.display="none";
			document.getElementById("componenteNucleoSelectTpal8").style.display="none";
			document.getElementById("componenteNucleoSelect8").style.display="none";
			document.getElementById("nucleo8").style.display="block";
	}
			}
		}

	
}


function mostraCampo9(form){

	
	valore= document.forms[form].nucleoIspettivoNove.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelectTpal9").style.display="none";
		document.getElementById("componenteNucleoSelect9").style.display="none";
		document.getElementById("componenteNucleoSelectSian9").style.display="none";
		document.getElementById("nucleo9").style.display="none";
		
	}

	if(valore==1){
		document.getElementById("componenteNucleoSelectTpal9").style.display="none";
		document.getElementById("componenteNucleoSelect9").style.display="block";
		document.getElementById("nucleo9").style.display="none";
		document.getElementById("componenteNucleoSelectSian9").style.display="none";


		}else{

if(valore==2){
	document.getElementById("componenteNucleoSelectTpal9").style.display="none";
	document.getElementById("componenteNucleoSelect9").style.display="none";
	document.getElementById("nucleo9").style.display="none";
	document.getElementById("componenteNucleoSelectSian9").style.display="block";
	
}else
{
	if(valore == "23")
	{
		document.getElementById("componenteNucleoSelectTpal9").style.display="block";
		document.getElementById("componenteNucleoSelect9").style.display="none";
		document.getElementById("nucleo9").style.display="none";
		document.getElementById("componenteNucleoSelectSian9").style.display="none"
	}
	else
	{

		document.getElementById("componenteNucleoSelectTpal9").style.display="none";
		document.getElementById("componenteNucleoSelectSian9").style.display="none";
		document.getElementById("componenteNucleoSelect9").style.display="none";
		document.getElementById("nucleo9").style.display="block";
		
		
	}
		
			}
		}

	
}



function mostraCampo10(form){
	

	valore= document.forms[form].nucleoIspettivoDieci.value;
	if(valore==-1){
		document.getElementById("componenteNucleoSelectTpal10").style.display="none";
		document.getElementById("componenteNucleoSelectSian10").style.display="none";
		document.getElementById("componenteNucleoSelect10").style.display="none";
		document.getElementById("nucleo10").style.display="none";
		
	}

	if(valore==1){
		document.getElementById("componenteNucleoSelectTpal10").style.display="none";
		document.getElementById("componenteNucleoSelect10").style.display="block";
		document.getElementById("nucleo10").style.display="none";
		document.getElementById("componenteNucleoSelectSian10").style.display="none";


		}else{

if(valore==2){
	document.getElementById("componenteNucleoSelectTpal10").style.display="none";
	document.getElementById("componenteNucleoSelect10").style.display="none";
	document.getElementById("nucleo10").style.display="none";
	document.getElementById("componenteNucleoSelectSian10").style.display="block";
	
}else
{
	if(valore == "23")
	{
		document.getElementById("componenteNucleoSelectTpal10").style.display="block";
		document.getElementById("componenteNucleoSelect10").style.display="none";
		document.getElementById("nucleo10").style.display="none";
		document.getElementById("componenteNucleoSelectSian10").style.display="none"
	}
	else
	{

		document.getElementById("componenteNucleoSelectTpal10").style.display="none";
		document.getElementById("componenteNucleoSelectSian10").style.display="none";
		document.getElementById("componenteNucleoSelect10").style.display="none";
		document.getElementById("nucleo10").style.display="block";
		
		
	}
		
			}
		}

	
}




function controlloDataFine(dataI, dataF){
	var arr1 = dataI.split("/");
	var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
    
	var arr2 = dataF.split("/");
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	
	var r1 = d1.getTime();
	var r2 = d2.getTime();
	
	if (r1<=r2) 
		return false;
				
	return true;
	
}

function checkPostData(data,dataReg){
  	if (compareDates( data.value,"d/MM/y",dataReg.value,"d/MM/y")==1) { 
     	return false;
     }else{
     	return true;
     }
  }	

function controlloData(stringa){
	var now = new Date();
    var giorno = now.getDate();
    var mese = now.getMonth() + 1;
    var anno = now.getFullYear(); 
	var arr2 = stringa.split("/");
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	var r1 = now.getTime();
	var r2 = d2.getTime();
	if (r2>r1) 
		return false;
				
	return true;
	
}


function controllo_data(stringa){
	var espressione = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
	if (!espressione.test(stringa))
	{
	    return false;
	}
}

function mostraCampo(form){
	
	
	elementi = 1 ;
	valore = document.getElementById("nucleo_ispettivo_"+elementi).value;
	
				if(valore==-1)
				{
					document.getElementById("Medici_"+elementi).style.display="none";
					document.getElementById("Tpal_"+elementi).style.display="none";
					document.getElementById("Veterinari_"+elementi).style.display="none";
					document.getElementById("Utente_"+elementi).style.display="none";
				}
				else
					{
						if(valore == 1 )
						{
							
							document.getElementById("Medici_"+elementi).style.display="none";
							document.getElementById("Tpal_"+elementi).style.display="none";
							document.getElementById("Veterinari_"+elementi).style.display="block";
							document.getElementById("Utente_"+elementi).style.display="none";
							
						}
						else
							{
								if(valore==2)
								{
										document.getElementById("Medici_"+elementi).style.display="block";
										document.getElementById("Tpal_"+elementi).style.display="none";
										document.getElementById("Veterinari_"+elementi).style.display="none";
										document.getElementById("Utente_"+elementi).style.display="none";
							}
							else
								{
									if(valore == "23") 
									{
										document.getElementById("Medici_"+elementi).style.display="none";
										document.getElementById("Tpal_"+elementi).style.display="block";
										document.getElementById("Veterinari_"+elementi).style.display="none";
										document.getElementById("Utente_"+elementi).style.display="none";
									}
									else
									{
											document.getElementById("Medici_"+elementi).style.display="none";
											document.getElementById("Tpal_"+elementi).style.display="none";
											document.getElementById("Veterinari_"+elementi).style.display="none";
											document.getElementById("Utente_"+elementi).style.display="block";
									}
						}

					}
		
				}
}






function abilitaSel2(){
	
document.getElementById("nucleodue").style.display="";


}

function abilitaSel3(){
document.getElementById("nucleotre").style.display="";

}

function abilitaSel4(){
document.getElementById("nucleoquattro").style.display="";

}

function abilitaSel5(){
document.getElementById("nucleocinque").style.display="";

}

function abilitaSel6(){
document.getElementById("nucleosei").style.display="";

}

function abilitaSel7(){
document.getElementById("nucleosette").style.display="";

}

function abilitaSel8(){
document.getElementById("nucleootto").style.display="";

}

function abilitaSel9(){
document.getElementById("nucleonove").style.display="";

}

function abilitaSel10(){
document.getElementById("nucleodieci").style.display="";

}



function abilita2(){
document.getElementById("nucleodue").style.display="";

}

function abilita3(){
document.getElementById("nucleotre").style.display="";
}

function abilita4(){
document.getElementById("nucleoquattro").style.display="";
}

function abilita5(){
document.getElementById("nucleocinque").style.display="";
}

function abilita6(){
document.getElementById("nucleosei").style.display="";
}

function abilita7(){
document.getElementById("nucleosette").style.display="";
}

function abilita8(){
document.getElementById("nucleootto").style.display="";
}

function abilita9(){
document.getElementById("nucleonove").style.display="";
}

function abilita10(){
document.getElementById("nucleodieci").style.display="";
}


function calcolaTotale(){
	var f=0;
	var g=0;
	var h=0;

	
	 if(document.getElementById("puntiFormali").value=="")
		 f=0;
	 else{
		 f = parseInt(document.getElementById("puntiFormali").value);

	 }
	if(document.getElementById("puntiSignificativi").value=="")
		g=0;
	else{
		 g = parseInt(document.getElementById("puntiSignificativi").value);
			
	}
	if(document.getElementById("puntiGravi").value=="")
	h=0;
else{

	 h = parseInt(document.getElementById("puntiGravi").value);
		
}
	 totale = document.getElementById("totale");
	
	totale.value=f+g+h;




	
}

function showMessaggioAllarme (form)
{
	orgId 		= form.orgId.value ;
	articolo	= form.articoliAzioni.value ;
	ControlliUfficiali.controlloSistemaAllarmeRapido(orgId , articolo,showMessageCallback );

}




        function showMessageCallback(returnValue)
        {
        	if(returnValue == 1)
        	{
        		alert('<< Attenzione. Nel caso in cui l OSA abbia gia subito una sanzione per gli stessi motivi, occorre procedere a sospendere l Impresa per il numero di giorni previsti. >>')
        		   
        	}

        }

        function trim(str){
            return str.replace(/^\s+|\s+$/g,"");
        }
        
        function controlloEsistenzaCu(data,orgId)
        {
        	   dataCu = data ;
        	   PopolaCombo.controlloEsistenzaCU(data ,orgId,gestisciRistpostaEsistenzaCuCallBack);
        	   
        }

        
        
  


  function popLookupSelectorCuSoaAllevaElimina(siteid,size)
  {	


  var clonato = document.getElementById('row'+'_'+size);
  	
  	clonato.parentNode.removeChild(clonato);
  	
  	size = document.getElementById('size');
  	size.value=parseInt(size.value)-1;
  }

  
  function piani(tipo){
	  
	 /*if(document.getElementById == null)
		 {
		 
		 if(document.forms[form].pianoMonitoraggio1.value!="-1"){
			  document.forms[form].pianoMonitoraggio1.disabled="";
			  document.forms[form].pianoMonitoraggio2.disabled="disabled";
			  document.forms[form].pianoMonitoraggio3.disabled="disabled";
			}else 
		if(document.forms[form].pianoMonitoraggio2.value!="-1"){
				document.forms[form].pianoMonitoraggio2.disabled="";
				document.forms[form].pianoMonitoraggio1.disabled="disabled";
				document.forms[form].pianoMonitoraggio3.disabled="disabled";
		}else if(document.forms[form].pianoMonitoraggio3.value!="-1"){
			document.forms[form].pianoMonitoraggio3.disabled="";
			document.forms[form].pianoMonitoraggio1.disabled="disabled";
			document.forms[form].pianoMonitoraggio2.disabled="disabled";
			}
		 
		 }
	 else
	 {
		 if(document.forms[form].pianoMonitoraggio1!=null && document.forms[form].pianoMonitoraggio1.value!="-1"){
			  document.forms[form].pianoMonitoraggio1.disabled="";
			  document.forms[form].pianoMonitoraggio2.disabled="disabled";
			  document.forms[form].pianoMonitoraggio3.disabled="disabled";
			}
		 else
		 if(document.forms[form].pianoMonitoraggio2.value!="-1"){
				document.forms[form].pianoMonitoraggio2.disabled="";
				document.forms[form].pianoMonitoraggio1.disabled="disabled";
				document.forms[form].pianoMonitoraggio3.disabled="disabled";
		}else if(document.forms[form].pianoMonitoraggio3.value!="-1"){
			document.forms[form].pianoMonitoraggio3.disabled="";
			document.forms[form].pianoMonitoraggio1.disabled="disabled";
			document.forms[form].pianoMonitoraggio2.disabled="disabled";
			}
		else
		{
			
			 document.forms[form].pianoMonitoraggio1.disabled="";
			  document.forms[form].pianoMonitoraggio2.disabled="";
			  document.forms[form].pianoMonitoraggio3.disabled="";
		}
		 
		 
	 }*/
	  
	  
	  if(tipo=='1')
	  {
		  
		  document.getElementById("link_regionali").disabled= true;
		  document.getElementById("link_territoriali").disabled= true;
		  
	  }else
		  if(tipo=='2')
		  {
			  document.getElementById("link_nazionali").disabled= true;
			  document.getElementById("link_territoriali").disabled= true;
		  }
		  else
			  if(tipo=='3')
			  {
				  document.getElementById("link_regionali").disabled= true;
				  document.getElementById("link_nazionali").disabled= true;
			  }
		  
		
}
 


 function setAssignedDate(form){
  resetAssignedDate();
  if (document.forms[form].assignedTo.value > 0){
    document.forms[form].assignedDate.value = document.forms[form].currentDate.value;
  }
 }

 function resetAssignedDate(form){
    document.forms[form].assignedDate.value = '';
 }
 

  
function provaFunzione(form){
	
	var valore= document.forms[form].tipoCampione.value;

	if(valore=="3"){


		document.forms[form].auditTipo.style.display="";	
		document.forms[form].tipoAudit.style.display="none";		
		document.forms[form].tipoAudit.value="-1";
		document.forms[form].tipoIspezione.style.display="none";
		if(document.getElementById("lab1")!=null)
		document.getElementById("lab1").style.display = "none";
		document.forms[form].tipoIspezione.value="-1";
		document.forms[form].ispezione.value="-1";
		document.forms[form].ispezione.style.display = "none";
		document.getElementById("oggetto_controllo").style.display = "none";
		
		mostraMenuTipoIspezione(form);

	

	}
	else{
		if(valore=="4"){
			document.getElementById("oggetto_controllo").style.display = "";
				document.forms[form].ispezione.style.display = "";
			document.forms[form].tipoAudit.style.display="none";
			document.forms[form].auditTipo.style.display="none";	
				document.forms[form].auditTipo.value="-1" ;
				document.forms[form].tipoAudit.value="-1" ;
			 mostraMenu2(form);	 
		 document.forms[form].tipoIspezione.style.display="block";
		 if(document.getElementById("lab1")!=null)
		 document.getElementById("lab1").style.display = "block";
		 
				}
		else{
			
			document.forms[form].tipoAudit.style.display="none";
			document.forms[form].tipoAudit.value="-1" ;

			document.forms[form].auditTipo.style.display="none";
			document.forms[form].auditTipo.value="-1" ;
			 
			 
			

		 	document.forms[form].tipoIspezione.value=-1;
					
			 document.forms[form].tipoIspezione.style.display="none";
			 if(document.getElementById("lab1")!=null)
			document.getElementById("lab1").style.display = "none";
 			mostraMenuTipoIspezione(form);
	
							
			 mostraMenu2(form);	
		}


		}

	
}

function abilitaDestinazione(form)
{
	if (document.forms[form].esitoControllo.value == -1)
	{
		 document.forms[form].destinazioneDistribuzione.style.display="none";
		 document.forms[form].destinazioneDistribuzione.value = "-1";
		 document.getElementById("hidden3").style.display="none";
		 document.forms[form].idFile.value="-1";
		 document.getElementById("partita").style.display="none";
		 
	}
	else
	{
		if (document.forms[form].esitoControllo.value == 13 || document.forms[form].esitoControllo.value == 14)
		{
		 	document.forms[form].destinazioneDistribuzione.style.display="block";
		 	document.getElementById("hidden3").style.display="";
		 	 document.getElementById("partita").style.display="";
		}
		else
		{
			 document.forms[form].destinazioneDistribuzione.style.display="none";
		 document.forms[form].destinazioneDistribuzione.value = "-1";
		 document.getElementById("hidden3").style.display="none";
		 document.forms[form].idFile.value="-1";
		 document.getElementById("partita").style.display="none";
			
		}
	}

}

function abilitaCampoNote(form)
{
	element = document.forms[form].comunicazioneRischio1[0];
	
	


	if(element.checked && element.value=="0")
	{
		document.forms[form].noteRischio.style.visibility="visible";
	}
	else
	{
		element = document.forms[form].comunicazioneRischio1[1];
		if(element.checked && element.value=="1")
		{
			document.forms[form].noteRischio.style.visibility="hidden";
		}
	}
}



function azioneSuAzioniAdottate(form)
{
	value = document.forms[form].azioniAdottate;
	settatoAzione1 = false;
	settatoAzione2 = false;
	for(i=0;i<value.length;i++)
	{
		if(value[i].selected==true)
		{

	if(value[i].value == "2" || value[i].value == "1")
	{
		

		if(document.forms[form].esitoControllo.value == "10" || document.forms[form].esitoControllo.value == "11" || document.forms[form].esitoControllo.value == "14"  )
		{
			document.getElementById("hiddenAzione1").style.display="";
			document.getElementById("rowAzione1").innerHTML="Quantita : "+document.forms[form].quantitaBloccata.value+"  "+document.forms[form].unitaMisura.value
		}
			//document.getElementById("hiddenAzione2").style.display="none";
		settatoAzione1 = true;
	
	}
	else
	{
		if(value[i].value == "3")
		{
			//document.getElementById("hiddenAzione1").style.display="none";
			document.getElementById("hiddenAzione2").style.display="";
			
			settatoAzione2=true;
		}
	}
	}
		
	}
	if(settatoAzione1==false && settatoAzione2==false)
	{
		document.getElementById("hiddenAzione1").style.display="none";
		document.getElementById("hiddenAzione2").style.display="none";
	}
	if(settatoAzione1==false)
	{	
		document.getElementById("hiddenAzione1").style.display="none";

	}
	if(settatoAzione2==false)
	{	
		document.getElementById("hiddenAzione2").style.display="none";

	}

}

function azioneSuEsitoControllo(form)
{
	value = document.forms[form].esitoControllo.value;

	if(value == "7")
	{
		document.getElementById("hiddenEsito1").style.display="";
		document.getElementById("hiddenEsito3").style.display="none";
		document.getElementById("rowAzione1").innerHTML="";
		document.getElementById("hiddenAzione1").style.display="none"
		document.getElementById("hiddenEsito2").style.display="";
		document.getElementById('misura').innerHTML=' '+document.forms[form].unitaMisura.value;
		document.getElementById("hiddenAzione1").style.display="none"
		
	}
	else
		if(value == "4" || value == "5" ||  value == "6" ||  value == "8")
		{
			document.getElementById("hiddenEsito1").style.display="none";
			document.getElementById("hiddenEsito2").style.display="";
			document.getElementById("hiddenEsito3").style.display="none";
			document.getElementById("rowAzione1").innerHTML="";
			document.getElementById('misura').innerHTML=' '+document.forms[form].unitaMisura.value;
			document.getElementById("hiddenAzione1").style.display="none"
		
		}
		else
		{
			if(value == "10" || value == "11" || value == "14")
			{
				document.getElementById("hiddenEsito1").style.display="none";
				document.getElementById("hiddenEsito2").style.display="none";
				document.getElementById("hiddenEsito3").style.display="";
				value = document.forms[form].azioniAdottate;
				document.getElementById('misura1').innerHTML=' '+document.forms[form].unitaMisura.value;
				for(i=0;i<value.length;i++)
				{
					if(value[i].selected==true)
					{

				if(value[i].value == "2")
				{
				document.getElementById("rowAzione1").innerHTML="Quantita : "+document.forms[form].quantitaBloccata.value+"  "+document.forms[form].unitaMisura.value
				
				document.getElementById("hiddenAzione1").style.display=""
				}}}
			
			}
			else
			{
				document.getElementById("hiddenEsito1").style.display="none";
				document.getElementById("hiddenEsito2").style.display="none";
				document.getElementById("hiddenEsito3").style.display="none";
				document.getElementById("rowAzione1").innerHTML="";
				document.getElementById("hiddenAzione1").style.display="none"
				
			}
			
		}


	
}

function onloadAllerta(form){
	
	var valore= document.forms[form].tipoIspezione.value;
	
	if (document.getElementById('tipoCampione').value=='4')
	{
		document.getElementById('oggetto_controllo').style.display = '';
	}
	else
	{
		document.getElementById('oggetto_controllo').style.display = 'none';
	}
	
	if(valore=="7")
	{


		document.forms[form].tipoIspezione.style.display="";
		if(document.getElementById("lab1")!=null)
		document.getElementById("lab1").style.display = "";
		if(document.getElementById("hidden1")!=null)
		document.getElementById("hidden1").style.visibility="visible";//display="block";
		if(document.getElementById("hidden2")!=null) 
			document.getElementById("hidden2").style.display="";
		if(document.getElementById("hidden3")!=null)
			document.getElementById("hidden3").style.display="";
		if(document.getElementById("tableHidden")!=null)
		{
		document.getElementById("tableHidden").style.display="";//display="block";
		document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
		document.forms[form].destinazioneDistribuzione.style.display="";
		if(document.getElementById("contributi")!=null)
		document.getElementById("contributi").style.visibility="visible";//display="block";
		
		document.getElementById('rowAzione1').innerHTML='Quantita : '+document.forms[form].quantitaBloccata.value+'  '+document.forms[form].unitaMisura.value;
		document.getElementById('misura').innerHTML=' '+document.forms[form].unitaMisura.value;
		}
	
	}
	
	
}

function mostraMenuTipoIspezione(form){

	var valore= document.forms[form].tipoIspezione.value;
	
	document.getElementById("row_piano").style.display = "none" ;
if(valore=="2"){
	document.getElementById("row_piano").style.display = "" ;
	if(document.getElementById('privati')==null)
	{
		 //document.forms[form].pianoMonitoraggio1.style.display="block";
		if( document.getElementById("monitoraggio")!=null)
		 document.getElementById("monitoraggio").style.display="block";
	}
	 //document.forms[form].pianoMonitoraggio2.style.display="block";
	 //document.forms[form].pianoMonitoraggio3.style.display="block";
	if( document.getElementById("monitoraggio")!=null)
	 document.getElementById("monitoraggio").style.display="block";
	if(document.getElementById("ispezione_altro")!=null) 
	document.getElementById("ispezione_altro").style.display="none";
	 
	 if(document.getElementById("label")!=null)
	 document.getElementById("label").style.display="none";
	 if(document.getElementById("hidden1")!=null)
	 document.getElementById("hidden1").style.visibility="hidden";//display="none";
	if( document.getElementById("hidden2") !=null)
	 document.getElementById("hidden2").style.display="none";
	if( document.getElementById("hidden3") !=null)
	 document.getElementById("hidden3").style.display="none";
	if (document.getElementById("tableHidden")!=null)
	{
	 
	 document.forms[form].destinazioneDistribuzione.style.display="none";
	 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
	 document.forms[form].destinazioneDistribuzione.value = "-1";
	 
	 document.getElementById("tableHidden").style.display="none";
	}
	if(document.getElementById("contributi")!=null)
	document.getElementById("contributi").style.visibility="hidden";//display="none";
	document.getElementById("tossinfezione").style.display="none";
	if(document.getElementById("nonconformitaprec")!=null)
	document.getElementById("nonconformitaprec").style.display="none";
	 

}
else
	if(valore=="4"){
		
		//document.forms[form].pianoMonitoraggio1.style.display="none";
		//document.forms[form].pianoMonitoraggio2.style.display="none";
		 
		if(document.getElementById("monitoraggio")!=null)

		 document.getElementById("monitoraggio").style.display="none";
		 if(document.getElementById("nonconformitaprec")!=null)
		document.getElementById("nonconformitaprec").style.display="none";
		//document.forms[form].pianoMonitoraggio3.style.display="none";
		 if(document.getElementById("ispezione_altro")!=null)
		 document.getElementById("ispezione_altro").style.display="";
		
		if(document.getElementById("label")!=null)
		document.getElementById("label").style.display="block";
		if(document.getElementById("hidden1")!=null)
		document.getElementById("hidden1").style.visibility="hidden";//display="none";
		if( document.getElementById("hidden2") !=null)
		 document.getElementById("hidden2").style.display="none";
		if( document.getElementById("hidden3") !=null)
		 document.getElementById("hidden3").style.display="none";
			if (document.getElementById("tableHidden")!=null)
		{
		
		 document.forms[form].destinazioneDistribuzione.style.display="none";
		 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
		 document.forms[form].destinazioneDistribuzione.value = "-1";
		  document.getElementById("tableHidden").style.display="none";
		}
			if(document.getElementById("contributi")!=null)
		 document.getElementById("contributi").style.visibility="hidden";//display="none";
		document.getElementById("tossinfezione").style.display="none";
	}else{
		if(valore=="6" || valore=="9" || valore == "11"){
			if(document.getElementById("nonconformitaprec")!=null)
			document.getElementById("nonconformitaprec").style.display="none";
			/*document.forms[form].pianoMonitoraggio1.style.display="none";
			document.forms[form].pianoMonitoraggio2.style.display="none";
			document.forms[form].pianoMonitoraggio3.style.display="none";
		*/
			 if(document.getElementById("monitoraggio")!=null)
			 document.getElementById("monitoraggio").style.display="none";
			 if(document.getElementById("ispezione_altro")!=null)
			 document.getElementById("ispezione_altro").style.display="none";
			if(document.getElementById("label")!=null)
			document.getElementById("label").style.display="block";
			if(document.getElementById("hidden1")!=null) 
			document.getElementById("hidden1").style.visibility="hidden";//display="none";
			if( document.getElementById("hidden2") !=null)
			 document.getElementById("hidden2").style.display="none";
			if( document.getElementById("hidden3") !=null)
			 document.getElementById("hidden3").style.display="none";
		
			 document.forms[form].destinazioneDistribuzione.style.display="none";
			 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
			 document.forms[form].destinazioneDistribuzione.value = "-1";
			 if(document.getElementById("contributi")!=null)
			 document.getElementById("contributi").style.visibility="hidden";//display="none";
			 document.getElementById("tableHidden").style.display="none";
			document.getElementById("tossinfezione").style.display="none";
			abilitaNoteDescrizioni();

		}

	else{
		if(valore=="7"){
			 if(document.getElementById("nonconformitaprec")!=null)
			document.getElementById("nonconformitaprec").style.display="none";
			 if(document.getElementById("contributi")!=null)

			 document.getElementById("contributi").style.visibility="hidden";//display="none";
			/*document.forms[form].pianoMonitoraggio1.style.display="none";
			document.forms[form].pianoMonitoraggio2.style.display="none";
			document.forms[form].pianoMonitoraggio3.style.display="none";
		*/
			 if(document.getElementById("monitoraggio")!=null)

			 document.getElementById("monitoraggio").style.display="none";
			 if(document.getElementById("ispezione_altro")!=null)

			 document.getElementById("ispezione_altro").style.display="none";
			if(document.getElementById("label")!=null)
			document.getElementById("label").style.display="none";
			if(document.getElementById("hidden1")!=null)
			document.getElementById("hidden1").style.visibility="visible";//display="block";
			if(document.getElementById("hidden2")!=null) 
			document.getElementById("hidden2").style.display="";
			if(document.getElementById("hidden3")!=null) 
			 document.getElementById("hidden3").style.display="none";
			 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
			 document.forms[form].destinazioneDistribuzione.style.display="none";
			
			 if(document.getElementById("contributi")!=null)
			document.getElementById("contributi").style.visibility="visible";//display="block";
			 document.getElementById("tableHidden").style.display="";
				document.getElementById("tossinfezione").style.display="none";


			
			}
		else{

			if(valore=="5"){
				 if(document.getElementById("nonconformitaprec")!=null)

				document.getElementById("nonconformitaprec").style.display="none";
				/*document.forms[form].pianoMonitoraggio1.style.display="none";
				document.forms[form].pianoMonitoraggio2.style.display="none";
				document.forms[form].pianoMonitoraggio3.style.display="none";
			*/
				 if(document.getElementById("monitoraggio")!=null)

				 document.getElementById("monitoraggio").style.display="none";
				 if(document.getElementById("ispezione_altro")!=null)

				 document.getElementById("ispezione_altro").style.display="none";
				if(document.getElementById("label")!=null)
				document.getElementById("label").style.display="none";
				if( document.getElementById("hidden1") !=null)
				document.getElementById("hidden1").style.visibility="hidden";//display="none";
				 if( document.getElementById("hidden2") !=null)
				 document.getElementById("hidden2").style.display="none";
				if( document.getElementById("hidden3") !=null)
				 document.getElementById("hidden3").style.display="none";
				
				 document.forms[form].destinazioneDistribuzione.style.display="none";
				 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
				 document.forms[form].destinazioneDistribuzione.value = "-1";
				 if(document.getElementById("contributi")!=null)
				document.getElementById("contributi").style.visibility="visible";//display="block";
				 document.getElementById("tableHidden").style.display="none";
					document.getElementById("tossinfezione").style.display="none";
				}else{

					if(valore=="8"){
						 if(document.getElementById("nonconformitaprec")!=null)

						document.getElementById("nonconformitaprec").style.display="block";
						/*document.forms[form].pianoMonitoraggio1.style.display="none";
						document.forms[form].pianoMonitoraggio2.style.display="none";
						document.forms[form].pianoMonitoraggio3.style.display="none";
					*/				 if(document.getElementById("monitoraggio")!=null)
						if( document.getElementById("monitoraggio")!=null)
						 document.getElementById("monitoraggio").style.display="none";
					 if(document.getElementById("ispezione_altro")!=null)

						 document.getElementById("ispezione_altro").style.display="none";
						if(document.getElementById("label")!=null)
							document.getElementById("label").style.display="none";
						if( document.getElementById("hidde1") !=null)
							document.getElementById("hidden1").style.visibility="hidden";//display="none";
						 if( document.getElementById("hidden2") !=null)
						 document.getElementById("hidden2").style.display="none";
						if( document.getElementById("hidden3") !=null)
						 document.getElementById("hidden3").style.display="none";
						
						 document.forms[form].destinazioneDistribuzione.style.display="none";
						 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
						 document.forms[form].destinazioneDistribuzione.value = "-1";
						 if(document.getElementById("contributi")!=null)
						document.getElementById("contributi").style.visibility="visible";//display="block";
						 document.getElementById("tableHidden").style.display="none";
						document.getElementById("tossinfezione").style.display="none";
						}
						else
						{
						if (valore == "16")
						{
							
							if(document.getElementById("nonconformitaprec")!=null)
						document.getElementById("nonconformitaprec").style.display="none";
						document.getElementById("tossinfezione").style.display="";
						/*document.forms[form].pianoMonitoraggio1.style.display="none";
						document.forms[form].pianoMonitoraggio2.style.display="none";
						document.forms[form].pianoMonitoraggio3.style.display="none";
					*/
						if( document.getElementById("monitoraggio")!=null)
						 document.getElementById("monitoraggio").style.display="none";
						if(document.getElementById("label")!=null)
						document.getElementById("label").style.display="none";
						 if( document.getElementById("hidden1") !=null)
						 document.getElementById("hidden1").style.visibility="hidden";//display="none";
						if( document.getElementById("hidden2") !=null)
						 document.getElementById("hidden2").style.display="none";
						if( document.getElementById("hidden3") !=null)
						 document.getElementById("hidden3").style.display="none";
							if (document.getElementById("tableHidden")!=null)
						{
						
						 document.forms[form].destinazioneDistribuzione.style.display="none";
						 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
						 document.forms[form].destinazioneDistribuzione.value = "-1";
						 if(document.getElementById("contributi")!=null)
						 document.getElementById("contributi").style.visibility="hidden";//display="none";
						 document.getElementById("tableHidden").style.display="none";
						}
						}


					else{
						if(document.getElementById("nonconformitaprec")!=null)
						document.getElementById("nonconformitaprec").style.display="none";
						if(document.getElementById("tossinfezione")!=null)
							document.getElementById("tossinfezione").style.display="none";
						/*document.forms[form].pianoMonitoraggio1.style.display="none";
						document.forms[form].pianoMonitoraggio2.style.display="none";
						document.forms[form].pianoMonitoraggio3.style.display="none";
					*/
							
						if(document.getElementById("monitoraggio")!=null)
							 document.getElementById("monitoraggio").style.display="none";
						if(document.getElementById("ispezione_altro")!=null)
							 document.getElementById("ispezione_altro").style.display="none";
						if(document.getElementById("label")!=null)
						document.getElementById("label").style.display="none";
						 if( document.getElementById("hidden1") !=null)
						 document.getElementById("hidden1").style.visibility="hidden";//display="none";
						if( document.getElementById("hidden2") !=null)
						 document.getElementById("hidden2").style.display="none";
						if( document.getElementById("hidden3") !=null)
						 document.getElementById("hidden3").style.display="none";
							if (document.getElementById("tableHidden")!=null)
						{
						
						 document.forms[form].destinazioneDistribuzione.style.display="none";
						 document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
						 document.forms[form].destinazioneDistribuzione.value = "-1";
						 if( document.getElementById("contributi")!=null)
						 document.getElementById("contributi").style.visibility="hidden";//display="none";
						 document.getElementById("tableHidden").style.display="none";
					}
					}
						}

					}
			
		
	
		}
	
	}
	}
	
}



function mostraMenu4(form){
	

	var valore= document.forms[form].auditTipo.value;
	if(valore=="1"){
		
	 document.forms[form].tipoAudit.style.display="";
	
		
				}else
				{
					if(valore==6){
						
						if(document.getElementById("notealtro")!=null)
							document.getElementById("notealtro").style.visibility="";
					}
					else{
						if(document.getElementById("notealtro")!=null)
							document.getElementById("notealtro").style.visibility="hidden";
						}
					 document.forms[form].tipoAudit.style.display="none";
					 if( document.forms[form].haccp!=null)
					 document.forms[form].haccp.style.display="none";
					 if(document.forms[form].bpi!=null)
					 document.forms[form].bpi.style.display="none";
					 if(document.getElementById("label1")!=null)
						 document.getElementById("label1").style.display="none";
					 if(document.getElementById("label2")!=null)
					 document.getElementById("label2").style.display="none";
					
					}


}

function mostraMenu2(form){
	

	var valore= document.forms[form].tipoAudit.value;
	if(valore=="2"){
		
	if(document.forms[form].haccp!=null)
	 document.forms[form].haccp.style.display="none";
	if(document.forms[form].bpi!=null)
	 document.forms[form].bpi.style.display="block";
	 if(document.getElementById("label2")!=null)
	 document.getElementById("label2").style.display="none";
	 if(document.getElementById("label1")!=null)
		 document.getElementById("label1").style.display="block";
	//document.getElementById("esitoControllo").style.display="block";
		
				}else
			if(valore=="3"){			
					
				if(document.forms[form].bpi!=null)
				 document.forms[form].bpi.style.display="none";
				if(document.forms[form].haccp!=null)
				 document.forms[form].haccp.style.display="block";
				 //if(document.getElementById("label1")!=null)
				if( document.getElementById("label1")!=null)
				 document.getElementById("label1").style.display="none";
				 //if(document.getElementById("label2")!=null)
				if( document.getElementById("label2")!=null)
				 document.getElementById("label2").style.display="block";
				// document.getElementById("esitoControllo").style.display="block";
				
				
			}else{
			
				if(document.forms[form].bpi!=null)
				 document.forms[form].bpi.style.display="none";
				if(document.forms[form].haccp!=null)
				document.forms[form].haccp.style.display="none";
				 if(document.getElementById("label2")!=null)
				 {
					 document.getElementById("label2").style.display="none";
					 
					 document.getElementById("label1").style.display="none";
					if ( document.getElementById("hidden2") != null)
					 document.getElementById("hidden2").style.display="none";
					 if ( document.getElementById("hidden3") != null)
					 document.getElementById("hidden3").style.display="none";
					if (document.getElementById("tableHidden")!=null)
					 document.getElementById("tableHidden").style.display="none";
				 }
				 
				 //document.getElementById("esitoControllo").style.display="none";
				 if( document.getElementById("hidden1")!=null)
				 document.getElementById("hidden1").style.visibility="hidden";
				
				
				 
				 //document.forms[form].distribuzionePartita.value="-1";
				 //document.forms[form].destinazioneDistribuzione.style.display="none";
				// document.forms[form].destinazioneDistribuzione.value = "-1";
				 if(document.getElementById("contributi")!=null)
				 document.getElementById("contributi").style.visibility="hidden";

			}

	mostraMenuTipoIspezione(form);


}

function mostraMenu3(form){
	

	var valore = document.forms[form].ispezione;
	var a = 0;

	for( var i = 0; i < valore.length; i++){
		
		if(valore[i].value=="13" && valore[i].selected==true){
		
					 document.getElementById("fup").style.display="block";
					 a = 1;
		
				}
	}
	if(a == 0){
	
			 document.getElementById("fup").style.display="none";
		
	}

}

function abilitaCodiceAllerta(){

	if(document.forms['details'].tipoIspezione!=null && document.forms['details'].tipoIspezione.value=="7"){
		if(document.getElementById("hidden1")!=null)
		document.getElementById("hidden1").style.visibility="visible";

		}
	
}

function initprovaFunzione(form){
	
	if (document.forms[form].tipoCampione!=null )
	{
	var valore= document.forms[form].tipoCampione.value;

	if(valore=="3"){
		
		document.getElementById("oggetto_controllo").style.display="none";
		document.forms[form].auditTipo.style.display="";	

if(document.forms[form].auditTipo.value==1){
	
	 mostraMenu4(form);	 
}else{
	if(document.forms[form].auditTipo.value==6){
		if(document.getElementById("notealtro")!=null)
			document.getElementById("notealtro").style.visibility="";
		}
	else{
		if(document.getElementById("notealtro")!=null)
			document.getElementById("notealtro").style.visibility="hidden";
		}

	if( document.forms[form].bpi!=null)
	{
	document.forms[form].bpi.style.display="none";		
	document.forms[form].bpi.value="-1";
	}
	if( document.forms[form].haccp!=null)
	{
	document.forms[form].haccp.style.display="none";		
	document.forms[form].haccp.value="-1";
	}
	
		document.forms[form].tipoAudit.style.display="none";		
		document.forms[form].tipoAudit.value="-1";
		document.forms[form].tipoIspezione.style.display="none";
		if(document.getElementById("lab1")!=null)
		document.getElementById("lab1").style.display = "none";
		document.forms[form].tipoIspezione.value="-1";
}
		

document.forms[form].tipoIspezione.value=-1;

document.forms[form].tipoIspezione.style.display="none";
if(document.getElementById("lab1")!=null)
document.getElementById("lab1").style.display = "none";

mostraMenuTipoIspezione(form);

				
mostraMenu2(form);	

	}
	else{
		if(valore=="4"){
			document.getElementById("oggetto_controllo").style.display="";
			document.forms[form].tipoAudit.style.display="none";
			document.forms[form].auditTipo.style.display="none";	
				document.forms[form].auditTipo.value="-1" ;
				document.forms[form].tipoAudit.value="-1" ;
			 mostraMenu2(form);	 
			 mostraMenuTipoIspezione(form);
		 document.forms[form].tipoIspezione.style.display="block";
		 if(document.getElementById("lab1")!=null)
		 document.getElementById("lab1").style.display = "block";
		 
				}
		else{
			
			document.forms[form].tipoAudit.style.display="none";
			document.forms[form].tipoAudit.value="-1" ;

			document.forms[form].auditTipo.style.display="none";
			document.forms[form].auditTipo.value="-1" ;
			 
			 
			

		 	document.forms[form].tipoIspezione.value=-1;
					
			 document.forms[form].tipoIspezione.style.display="none";
			 if(document.getElementById("lab1")!=null)
			 document.getElementById("lab1").style.display = "none";
			
 			mostraMenuTipoIspezione(form);
	
							
			 mostraMenu2(form);	
		}


		}
	}
	
}

function abilitaSistemaAllarmeRabido(form)

{
	if(document.forms[form].tipoIspezione!=null)
	{
	var valore= document.forms[form].tipoIspezione.value;
	if(valore=="7")
	{
		document.forms[form].tipoIspezione.style.display="";
		if(document.getElementById("lab1")!=null)
		document.getElementById("lab1").style.display = "";
		document.getElementById("tableHidden").style.display="";
		if ( document.getElementById("hidden1") != null)
		document.getElementById("hidden1").style.visibility="visible";//display="block";
		if ( document.getElementById("hidden2") != null)
		document.getElementById("hidden2").style.display="";

		 if(document.forms[form].esitoControllo == 13 || document.forms[form].esitoControllo == 14 )
		 {
		 	document.getElementById("hidden3").style.display="";
		 	document.forms[form].destinazioneDistribuzione.style.background = "#FFFFFF";
		 	document.forms[form].destinazioneDistribuzione.style.display="";
		 }
		 abilitaDestinazione(form);
		 if(document.getElementById("contributi")!=null)
		document.getElementById("contributi").style.visibility="visible";//display="block";
		//display="block";
		abilitaCampoNote(form);
		
		document.getElementById('rowAzione1').innerHTML='Quantita : '+document.forms[form].quantitaBloccata.value+' '+document.forms[form].unitaMisura.value;
		document.getElementById('misura').innerHTML=' '+document.forms[form].unitaMisura.value;
		azioneSuEsitoControllo(form);
		azioneSuAzioniAdottate(form);
	
	}
	}

}

function prova(val)
	  	{
	
	
	
	valore=document.getElementById('nucleo_ispettivo_'+val).value;
	elementi = val;
	
	
				if(valore=="-1")
				{
					document.getElementById("Medici_"+elementi).style.display="none";
					document.getElementById("Tpal_"+elementi).style.display="none";
					document.getElementById("Veterinari_"+elementi).style.display="none";
					document.getElementById("Utente_"+elementi).style.display="none";
					document.getElementById("Ref_"+elementi).style.display="none";
					document.getElementById("Amm_"+elementi).style.display="none";
				}
				else
					{
						if(valore =="1" )
						{
							
							document.getElementById("Medici_"+elementi).style.display="none";
							document.getElementById("Tpal_"+elementi).style.display="none";
							document.getElementById("Veterinari_"+elementi).style.display="block";
							document.getElementById("Utente_"+elementi).style.display="none";
							document.getElementById("Ref_"+elementi).style.display="none";
							document.getElementById("Amm_"+elementi).style.display="none";
						}
						else
							{
								if(valore=="2")
								{
										document.getElementById("Medici_"+elementi).style.display="block";
										document.getElementById("Tpal_"+elementi).style.display="none";
										document.getElementById("Veterinari_"+elementi).style.display="none";
										document.getElementById("Utente_"+elementi).style.display="none";
										document.getElementById("Ref_"+elementi).style.display="none";
										document.getElementById("Amm_"+elementi).style.display="none";
							}
							else
								{
									if(valore == "23") 
									{
										document.getElementById("Medici_"+elementi).style.display="none";
										document.getElementById("Tpal_"+elementi).style.display="block";
										document.getElementById("Veterinari_"+elementi).style.display="none";
										document.getElementById("Utente_"+elementi).style.display="none";
										document.getElementById("Ref_"+elementi).style.display="none";
										document.getElementById("Amm_"+elementi).style.display="none";
									}
									else
									{
										if(valore == "24") 
									{
										document.getElementById("Medici_"+elementi).style.display="none";
										document.getElementById("Tpal_"+elementi).style.display="none";
										document.getElementById("Veterinari_"+elementi).style.display="none";
										document.getElementById("Utente_"+elementi).style.display="none";
										document.getElementById("Ref_"+elementi).style.display="block";
										document.getElementById("Amm_"+elementi).style.display="none";
									}
									else
									{
										if(valore == "25") 
									{
										document.getElementById("Medici_"+elementi).style.display="none";
										document.getElementById("Tpal_"+elementi).style.display="none";
										document.getElementById("Veterinari_"+elementi).style.display="none";
										document.getElementById("Utente_"+elementi).style.display="none";
										document.getElementById("Ref_"+elementi).style.display="none";
										document.getElementById("Amm_"+elementi).style.display="block";
									}
									else
									{
											document.getElementById("Medici_"+elementi).style.display="none";
											document.getElementById("Tpal_"+elementi).style.display="none";
											document.getElementById("Veterinari_"+elementi).style.display="none";
											document.getElementById("Utente_"+elementi).style.display="block";
											document.getElementById("Ref_"+elementi).style.display="none";
											document.getElementById("Amm_"+elementi).style.display="none";
									}
									}
											
									}
						}

					}
		
				}
	  	}
  	 
	  

  
  function clona(){
	  	var maxElementi = 10;
	  	var elementi;
	  	var elementoClone;
	  	var tableClonata;
	  	var tabella;
	  	var selezionato;
	  	var x;
	  	elementi = document.getElementById('elementi');
	  	if (elementi.value < maxElementi)
	  	{
		
	  	if (document.getElementById('nucleo_ispettivo_'+elementi.value).value != '-1')
	  	{
	  	
	  		
	  	elementi.value=parseInt(elementi.value)+1;
	  	size = document.getElementById('size');
	  	size.value=parseInt(size.value)+1;
	  	var primo_elemento = document.getElementById('nucleo_ispettivo_1');
	  	var indice = parseInt(elementi.value) - 1;
	  	
	  	x = document.getElementById('lcso_patologia_'+String(indice));
	  	if(primo_elemento!=null && x==null){
	  		selezionato = document.getElementById('nucleo_ispettivo_1').selectedIndex;
	  	}else if(primo_elemento==null && x!=null){
	  		selezionato = x.selectedIndex;
	  	}
	  	var clonato = document.getElementById('nucleo_ispettivo');
	  	
	  	
	  	/*clona riga vuota*/
	  	clone=clonato.cloneNode(true);
		clone.getElementsByTagName('SELECT')[0].name = "nucleo_ispettivo_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[0].id = "nucleo_ispettivo_"+String(indice+1);
	  	clone.getElementsByTagName('SELECT')[0].value = '-1';
	 
		clone.getElementsByTagName('SELECT')[0].onchange= function () 
		{
				prova(parseInt(indice)+1);
		}
	  	//clone.getElementsByTagName('LABEL')[0].innerHtml = "Nucleo Ispettivo "+elementi.value;
		
		/**
	  	 * LISTA VETERINARI
	  	 */
	  	clone.getElementsByTagName('SELECT')[1].id = "Veterinari_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[1].name = "Veterinari_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[1].style.display="none";
		clone.getElementsByTagName('SELECT')[1].value = '-1';
	  	/**
	  	 * LISTA MEDICI
	  	 */
	  	clone.getElementsByTagName('SELECT')[2].id = "Medici_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[2].name = "Medici_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[2].style.display="none";
		clone.getElementsByTagName('SELECT')[2].value = '-1';
	  	/**
	  	 * LISTA TPAL
	  	 */
	  	clone.getElementsByTagName('SELECT')[3].id = "Tpal_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[3].name = "Tpal_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[3].style.display="none";
	  	clone.getElementsByTagName('SELECT')[3].value = '-1';
	  	
	  	/**
	  	 * LISTA REFERENTE ALLERTE
	  	 */
	  	clone.getElementsByTagName('SELECT')[4].id = "Ref_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[4].name = "Ref_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[4].style.display="none";
	  	clone.getElementsByTagName('SELECT')[4].value = '-1';
	  	
	  	/**
	  	 * LISTA AMMINISTRATIVI
	  	 */
	  	clone.getElementsByTagName('SELECT')[5].id = "Amm_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[5].name = "Amm_"+elementi.value;
	  	clone.getElementsByTagName('SELECT')[5].style.display="none";
	  	clone.getElementsByTagName('SELECT')[5].value = '-1';
		
	  	clone.getElementsByTagName('INPUT')[0].name = "Utente_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[0].id = "Utente_"+elementi.value;
	  	clone.getElementsByTagName('INPUT')[0].style.display="none";
	  	clone.getElementsByTagName('INPUT')[0].value = '';
	  	
	  	/*Lo rendo visibile*/
	
	  	clone.id = "nucleoispettivo_"+elementi.value;
	  	/*Aggancio il nodo*/
	  	clonato.parentNode.appendChild(clone);

	  	/*Lo rendo visibile*/
	  
	  	clone.style.visibility="visible";
	  	
	  	}
	  	
	  	}
	  }
	  
	  function resetElementiNucleoIspettivo(numElementi)
	  {
	  	
	  	document.getElementById('size').value = (parseInt(numElementi)+1);
	  	document.getElementById('elementi').value = (parseInt(numElementi)+1);
	  }
	 
	  function verificaChiusuraCu(flagChiusura){
			if(flagChiusura!=null){
				if(flagChiusura=="1"){
					alert("Questo Controllo Ufficiale non puo essere chiuso. Ci sono attivita\' che non sono state ancora chiuse.");
					//alert("Controllo Ufficiale e sottosezioni chiuse.");
				}else if(flagChiusura=="2"){
					alert("Questo Controllo Ufficiale non puo essere chiuso a causa di checklist mancante o categoria di rischio non aggiornata. Le altre sottoattività sono state chiuse.");
				}else if(flagChiusura=="3"){
					alert("Chiusura del controllo ufficiale effettuata correttamente.");
				}else if(flagChiusura=="4"){
					alert("Controllo Ufficiale chiuso in attesa di esito (sottosezione Tamponi o Campioni).");
				}
			}
		}
	  		
