<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript">
function openModPnaa(orgId,idCampione,url, idC, mod){
	//var mes = document.getElementById('messaggio_pnaa').value;
	var mes = '';
	
	if( mes != '' && mes != 'null'){
		alert(mes);
	}else{
		
		  window.open('CampioniReport.do?command=ViewSchedaPNAA2&idCampione='+idCampione+'&idCU='+idC+'&orgId='+orgId+'&url='+url+'&tipo='+mod,'popupSelect',
				'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
   }				
}
</script>
 
 <script type="text/javascript">
 function openModificaPnaa(orgId,idCampione,url, idC, mod){
	//var mes = document.getElementById('messaggio_pnaa').value;
	var mes = '';
	
	if( mes != '' && mes != 'null'){
		alert(mes);
	}else{
		
		  window.open('CampioniReport.do?command=ModificaSchedaPNAA2&idCampione='+idCampione+'&idCU='+idC+'&orgId='+orgId+'&url='+url+'&tipo='+mod,'popupSelect',
				'height=600px,width=800px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
   }				
}
</script>

