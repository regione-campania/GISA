/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function eliminaCheckList (idAudit,idControllo,orgId)
{
	if (confirm("Sei Sicuro di voler Eliminare Questa CheckList ? ")) 
	{
	    document.location = 'ChecklistOsmRegistrati.do?command=Delete&id='+idAudit+'&idControllo='+idControllo+'&orgId='+orgId;
	}
	
}

function aggiornaCategoria(idControllo,orgId)
{
	if (confirm("Attenzione! Aggiornando la categoria di rischio si procede contestualmente anche alla chiusura automatica del controllo ufficiale. Saranno impossibili ulteriori modifiche. Proseguire?")) 
	{
		document.details.action='ChecklistOsmRegistrati.do?command=UpdateCategoria&idC='+idControllo+'&orgId='+orgId ;
		document.details.submit();
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
			document.forms[form].action='ChecklistOsmRegistrati.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=true&idControllo='+idControlloUfficiale
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
				document.forms[form].action='ChecklistOsmRegistrati.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=false&idControllo='+idControlloUfficiale
				setTimestampStartRichiesta();
				document.forms[form].submit();
			}
			}
			else
			{
				document.forms[form].action='ChecklistOsmRegistrati.do?command=Add&orgId='+orgId+'&idC='+idControllo+'&isPrincipale=false&idControllo='+idControlloUfficiale
				setTimestampStartRichiesta();
				document.forms[form].submit();
			}
		
			
			
		//}
			

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
function allegaFile(form,gotoPage)
{
document.forms[form].encoding="multipart/form-data";
alert('document.forms[form].action')
document.forms[form].action = "OsmRegistratiVigilanza.do?command=UploadListaDistribuzione&goto="+gotoPage;
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
	alert('document.forms[form].action')
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
document.forms[form].action = "OsmRegistratiVigilanza.do?command=DeleteListaDistribuzione&goto="+gotoPage+"&fid="+fid+"&orgId="+orgId+"&folderId="+folderid;

document.forms[form].submit;
}