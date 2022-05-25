<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<table class="trails" cellspacing="0">
	<tr>
		<td>SUITE APP MOBILE GISA</td>
	</tr>
</table>

<center>

<dhv:permission name="campioni-campioni-preaccettazionesenzacampione-view"> 
<br>
<a href="#" onclick="linkAppPreaccettazioneSIGLA();"><h1 style="color:blue;text-decoration: underline;">App mobile Android Preaccettazione SIGLA</h1></a>
</dhv:permission>

<dhv:permission name="campioni-campioni-preaccettazionesenzacampione-view"> 
<br>
<a href="#" onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='DownloadAppMobile.do?command=AppMobileIosPreaccettazionesigla'"><h1 style="color:blue;text-decoration: underline;">App mobile iOS Preaccettazione SIGLA</h1></a>
</dhv:permission>

<dhv:permission name="campioni-campioni-preaccettazionesenzacampione-view"> 
<br>
<a href="#" onclick="linkAppWebGIS();"><h1 style="color:blue;text-decoration: underline;">App mobile Android GISA WebGIS</h1></a>
</dhv:permission>


<dhv:permission name="campioni-campioni-preaccettazionesenzacampione-view"> 
<br>
<a href="#" onclick="loadModalWindowCustom('Attendere Prego...'); window.location.href='DownloadAppMobile.do?command=AppMobileIosGisaWebGis'"><h1 style="color:blue;text-decoration: underline;">App mobile iOS GISA WebGIS</h1></a>
</dhv:permission>


</center>

<script>
function linkAppPreaccettazioneSIGLA()
{
	loadModalWindowUnlock();
	window.open('https://play.google.com/store/apps/details?id=com.preaccettazione&hl=it','_blank');
}

function linkAppWebGIS()
{
	loadModalWindowUnlock();
	window.open('https://play.google.com/store/apps/details?id=com.webgis&hl=it','_blank');
}
</script>