/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function eliminaCheckList (idAudit,idControllo,orgId,assetId)
{
	if (confirm("Sei Sicuro di voler Eliminare Questa CheckList ? ")) 
	{
	    document.location = 'CheckListCaniPadronali.do?command=Delete&assetId='+assetId+'&id='+idAudit+'&idControllo='+idControllo+'&orgId='+orgId;
	}
	
}

function compilaCheckList(messaggio,orgId,assetId,idControllo,idControlloUfficiale,isPrincipale,form)
{

	//if(confirm(messaggio))
	//{

		if(isPrincipale=='1')
		{
			if(confirm(messaggio))
			{
			document.forms[form].action='CheckListCaniPadronali.do?command=Add&orgId='+orgId+'&assetId='+assetId+'&idC='+idControllo+'&isPrincipale=true&idControllo='+idControlloUfficiale
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
				document.forms[form].action='CheckListCaniPadronali.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=false&idControllo='+idControlloUfficiale
				setTimestampStartRichiesta();
				document.forms[form].submit();
			}
			}
			else
			{
				document.forms[form].action='CheckListCaniPadronali.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=false&idControllo='+idControlloUfficiale
				setTimestampStartRichiesta();
				document.forms[form].submit();
			}
			
			
			
		}
			

	//}
	
}

function allegaFile(form,gotoPage)
{

document.forms[form].encoding="multipart/form-data";

document.forms[form].action = "CaniPadronaliVigilanza.do?command=UploadListaDistribuzione&goto="+gotoPage;
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

function eliminaFileAllegato(fid,orgId,assetId,assetId,folderid,form,gotoPage)
{


	document.forms[form].encoding="multipart/form-data";
document.forms[form].action = "CaniPadronaliVigilanza.do?command=DeleteListaDistribuzione&goto="+gotoPage+"&fid="+fid+"&orgId="+orgId+"&assetId="+assetId+"&folderId="+folderid;

document.forms[form].submit;
}

function aggiornaCategoria(idControllo,orgId,assetId)
{
	if (confirm("Attenzione! Aggiornando la categoria di rischio si procede contestualmente anche alla chiusura automatica del controllo ufficiale. Saranno impossibili ulteriori modifiche. \n\nAttenzione! Procedendo alla chiusura del CU e al conseguente aggiornamento della categoria di rischio, non sarà possibile inserire una seconda checklist. Si prega di verificare bene i dati inseriti prima di confermare l'operazione.\n\n Proseguire?")) 
	{
		document.details.action='CheckListCaniPadronali.do?command=UpdateCategoria&idC='+idControllo+'&orgId='+orgId +'&assetId='+assetId;
		document.details.submit();
	}
}

function addCane(mc,razza,taglia,mantello,sesso,data_nascita){
  
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('elementi');
  	elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size');
  	size.value=parseInt(size.value)+1;
  	
  	var primo_elemento = document.main.lcso_patologia_1;
  	var indice = parseInt(elementi.value) - 1;
  	
  	var clonanbsp = document.getElementById('cane');
  	
  	/*clona riga vuota*/
  	clone=clonanbsp.cloneNode(true);
	 	
	clone.getElementsByTagName('INPUT')[0].name 	= "mc_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[0].id 		= "mc_"+elementi.value;
	clone.getElementsByTagName('INPUT')[0].value 	= mc ;
	
  	
  	clone.getElementsByTagName('INPUT')[1].name = "razza_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[1].id = "razza_"+elementi.value;
	clone.getElementsByTagName('INPUT')[1].value 	= razza ;

  	
  	clone.getElementsByTagName('INPUT')[2].name = "taglia_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[2].id = "taglia_"+elementi.value;
	clone.getElementsByTagName('INPUT')[2].value 	= taglia ;

	
  	clone.getElementsByTagName('INPUT')[3].name = "mantello_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[3].id = "mantello_"+elementi.value;
	clone.getElementsByTagName('INPUT')[3].value 	= mantello ;

	
  	clone.getElementsByTagName('INPUT')[4].name = "sesso_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[4].id = "sesso_"+elementi.value;
	clone.getElementsByTagName('INPUT')[4].value 	= sesso ;

  	clone.getElementsByTagName('INPUT')[5].name = "data_nascita_cane_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[5].id = "data_nascita_cane_"+elementi.value;
	clone.getElementsByTagName('INPUT')[5].value 	= data_nascita ;

	
  	/*Aggancio il nodo*/
  	clonanbsp.parentNode.appendChild(clone);

  	
  	clone.id = "row_" + elementi.value;
 
  	
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
