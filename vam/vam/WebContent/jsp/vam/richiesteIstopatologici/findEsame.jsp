<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<h4 class="titolopagina">
     		Ricerca Esame Istopatologico
</h4>  


<form action="vam.richiesteIstopatologici.Find.us" method="post">			
		
	<table class="tabella">
		<tr class='even'>
    		<td>
    			Numero Esame
    		</td>
    		<td>
    			<input type="text" name="numeroEsame" maxlength="64" /> 
    		</td>    		
		 </tr>
		        
		 <tr class='odd'>
        	<td>
        	</td>
    		<td>    			
    			<input type="submit" value="Cerca"/>    			
    		</td>
		  </tr>
	</table>
		
	
	
</form>
 