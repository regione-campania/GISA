<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/find.js"></script>

<h4 class="titolopagina">
     		Ricerca Cartella Clinica
</h4>  


<table class="tabella">
	<tr>
   		<th colspan="2">
   			Ricerca per numero di Cartella Clinica
   		</th>
    </tr>
    
    <tr>
	    <td> 
	    	Numero Cartella Clinica
	    </td>
	    <td>
		    <form action="vam.cc.Find.us" method="post" onsubmit="javascript:return checkform(this);">			
			<input type="hidden" name="tipoFind" value="cc"/>	
			<input type="text" name="numeroCC" size="40" maxlength="64" value="CC-${utente.clinica.nomeBreve}-<%=new GregorianCalendar().get(Calendar.YEAR)%>-"/>
			<input type="submit" value="Cerca" />
			</form>
		</td>
    </tr>
    
    <tr>
   		<th colspan="3">
   			Ricerca per identificativo
   		</th>
    </tr>
    
    <tr>
	    <td> 
	    	Numero identificativo animale
	    </td>
	    <td>
		    <form action="vam.cc.Find.us" method="post" onsubmit="javascript:return checkform(this);">					
				<input type="hidden" name="tipoFind" value="mc"/>	
				<input type="text" name="numeroMC" size="40" maxlength="15" />
				Cerca in tutte le cliniche
				<select id="ricercaAllCliniche" name="ricercaAllCliniche">
					<option value="No">No</option>
					<option value="Si">Si</option>
				</select> 
				<input type="submit" value="Cerca" onclick=""/>
			</form>
		</td>
	</tr>
    
    <tr class="odd">
	    <td> 
	    	
	    </td>
	    <td>
		    <form action="vam.cc.Find.us" method="post">			
			<input type="hidden" name="tipoFind" value="all"/>				
			<input type="submit" onclick="attendere()"value="Lista di tutte le CC della clinica" />
			</form>
		</td>
	</tr>
    
		
    
    
</table>
    
