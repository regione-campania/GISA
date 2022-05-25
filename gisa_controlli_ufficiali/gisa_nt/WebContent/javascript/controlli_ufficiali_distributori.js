/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function allegaFile(form,gotoPage,idMacchinetta)
{
document.forms[form].encoding="multipart/form-data";

document.forms[form].action = "DistributoriVigilanza.do?command=UploadListaDistribuzione&goto="+gotoPage+"&idMacchinetta="+idMacchinetta;
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
document.forms[form].action = "DistributoriVigilanza.do?command=DeleteListaDistribuzione&goto="+gotoPage+"&fid="+fid+"&orgId="+orgId+"&folderId="+folderid;

document.forms[form].submit;
}