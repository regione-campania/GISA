<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="operatorinonaltrove/opnonaltrove.js"></SCRIPT>


<script>

function showHide(name,namefs){
	  var elem = document.getElementById(name);
			if (elem.style.display=='none')
				{
			    	elem.style.display='inline-block';
			    	if(namefs != undefined)
						document.getElementById(namefs).style.display = 'inline-block';
				}
			    else
			    {
			    	elem.style.display='none';
			    	if(namefs != undefined)
			    		document.getElementById(namefs).style.display = 'none';
			    }
return false;
}

function hide(name,namefs){
	  var elem = document.getElementById(name);
		elem.style.display='none';
	
	 document.getElementById(namefs).style.display = 'none';
return false;
}

function goTo(link){
	
	if (link=='')
		alert('da implementare');
	else{
		loadModalWindow();
		window.location.href=link;
	}
	
	return false;
}

function scrollPage() {
	window.scrollBy(0,400); // horizontal and vertical scroll increments

}

 




</script>
 <center>
	 <input type="button" class="lightGreyBigButton" style="height:50px !important; width:175px !important;" value="Aggiungi" onClick="showHide('stabilimenti_nonscia_add','fs_add'); hide('stabilimenti_nonscia_search','fs_ric'); scrollPage();"/>
 
 <input type="button" class="lightGreyBigButton" style="height:50px !important; width:175px !important;" value="Ricerca" onClick="showHide('stabilimenti_nonscia_search','fs_ric'); hide('stabilimenti_nonscia_add','fs_add'); scrollPage();"/>
 
 
  <dhv:permission name = "gestione_noscia_gins-view">
 	<input type="button" class="lightGreyBigButton" style="height:50px !important; width:300px !important; text-align: center;" value="Gestione anagrafiche no-scia" onClick="location.href='GisaNoSciaGINS.do?command=Default'"/>
 </dhv:permission> 
 
 </center>
 
 </hr>
 
  
 <center>
 <fieldset id="fs_add" style="display : none">
 <legend><b>AGGIUNGI</b></legend>
<table id="stabilimenti_nonscia_add" style="display:none">
	
	
	<tr>
		<dhv:permission name="molluschi-molluschi-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Molluschi bivalvi" style="height:50px !important; width:350px !important;" onClick="goTo('MolluschiBivalvi.do?command=Add')">
		</td>
		</dhv:permission>
		
			<dhv:permission name="concessionari-concessionari-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Concessionari" style="height:50px !important; width:350px !important;" onClick="goTo('Concessionari.do?command=Add')">
		</td>
		</dhv:permission>

		
	</tr>
	
	<tr>
		<dhv:permission name="trasportoanimali-trasportoanimali-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Trasporto Animali" style="height:50px !important; width:350px !important;" onClick="goTo('TrasportoAnimali.do?command=ScegliRichiesta')">
		</td>
		</dhv:permission>
	   <%-- <dhv:permission name = "gestione_noscia-view"> --%>
	   <dhv:permission name="operatoriprivati-operatoriprivati-add">
			<td colspan="4"> 
	 		<input type="button" class="lightGreyBigButton" value="Privati" style="height:50px !important; width:350px !important;" 
	 			onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GisaNoScia.do?command=Choose&codice_univoco_ml=OPR-OPR-X';">
			</td> 
		</dhv:permission>
<%-- 		<dhv:permission name="operatoriprivati-operatoriprivati-add"> --%>
<!-- 		<td colspan="4"> -->
<!-- 		<input type="button" class="lightGreyBigButton" value="Privati" style="height:50px !important; width:350px !important;" onClick="goTo('Operatoriprivati.do?command=Add')"> -->
<!-- 		</td> -->
<%-- 		</dhv:permission> --%>
	</tr>


	<tr>
		<dhv:permission name="abusivismi-abusivismi-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Operatori abusivi" style="height:50px !important; width:350px !important;" onClick="goTo('Abusivismi.do?command=Add')">
		</td>
		</dhv:permission>
		<dhv:permission name="operatoriregione-operatoriregione-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Att. e distributori fuori ambito asl" style="height:50px !important; width:350px !important;" onClick="goTo('OperatoriFuoriRegione.do?command=Add&tipoD=Autoveicolo')">
		</td>
		</dhv:permission>
	</tr>

	<tr>
		<dhv:permission name="punti_di_sbarco-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Punti di sbarco" style="height:50px !important; width:350px !important;" onClick="goTo('PuntiSbarco.do?command=Add')">
		</td>
		</dhv:permission>
		<% boolean hasPermissionAdd = false; %>
    	<dhv:permission name="operatorinonaltrove-operatorinonaltrove-add">
  		<% hasPermissionAdd = true;%>
		</dhv:permission>
		<td colspan="4">
		<% if (hasPermissionAdd){ %>
			<input type="button" class="lightGreyBigButton" value="Altri oper. non presenti altrove" style="height:50px !important; width:350px !important;" onClick="goTo('OpnonAltrove.do?command=Add')">
		<% }else{ %>
			<input type="button" class="lightGreyBigButton" value="Altri oper. non presenti altrove" style="height:50px !important; width:350px !important;" onClick="popupAggiunta(); return false;">
		<% } %>
		</td>
	</tr>
	
	<tr>
	    <dhv:permission name="imbarcazioni-imbarcazioni-add">
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Imbarcazioni" style="height:50px !important; width:350px !important;" onClick="goTo('Imbarcazioni.do?command=Add')">
		</td>
		</dhv:permission>
		
	</tr>
	<tr>
		<td><dhv:permission name = "gestione_noscia-view">
 		<input type="button" class="GreenBigButton" style="height:50px !important; width:190px !important; text-align: center;" value="Gestione no-scia" onClick="location.href='GisaNoScia.do?command=Default'"/>
    	</dhv:permission>
    	</td>
    </tr>	 
	
</table>
</fieldset>
<fieldset id="fs_ric" style="display : none">
<legend><b>RICERCA</b></legend>
<table id="stabilimenti_nonscia_search" style="display:none"> 
	 
	<tr>
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Molluschi bivalvi" style="height:50px !important; width:350px !important;" onClick="goTo('MolluschiBivalvi.do')">
		</td>
		<td colspan="4">
				<input type="button" class="lightGreyBigButton" value="Concessionari" style="height:50px !important; width:350px !important;" onClick="goTo('Concessionari.do?command=SearchForm')">
		
		</td>
	</tr>
	
	
	

	<tr>
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Trasporto Animali" style="height:50px !important; width:350px !important;" onClick="goTo('TrasportoAnimali.do')">
		</td>
		<td colspan="4">
<!-- 		<input type="button" class="lightGreyBigButton" value="Privati" style="height:50px !important; width:350px !important;" onClick="goTo('Operatoriprivati.do?command=SearchForm')"> -->
		
		<input type="button" class="lightGreyBigButton" value="Privati" style="height:50px !important; width:350px !important;" onClick="alert('Per ricercare i privati utilizzare il cavaliere Anagrafica Stabilimenti.');"> 		
		</td>
	</tr>

	<tr>
		<!--td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Imbarcazioni" style="height:50px !important; width:350px !important;" onClick="goTo('Imbarcazioni.do')">
		</td-->
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Operatori abusivi" style="height:50px !important; width:350px !important;" onClick="goTo('Abusivismi.do')">
		</td>
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Att. e distributori fuori ambito asl" style="height:50px !important; width:350px !important;" onClick="goTo('OperatoriFuoriRegione.do?command=SearchForm')">
		</td>
	</tr>

	<tr>
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Punti di sbarco" style="height:50px !important; width:350px !important;" onClick="goTo('PuntiSbarco.do?command=SearchForm')">
		</td>
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Altri oper. non presenti altrove" style="height:50px !important; width:350px !important;" onClick="goTo('OpnonAltrove.do')">
		</td>
	</tr>
	<tr>
		<td>
		 	<dhv:permission name = "gestione_noscia-view">
 				<input type="button" class="GreenBigButton" style="height:50px !important; width:190px !important; text-align: center;" value="Gestione no-scia" onClick="location.href='GisaNoScia.do?command=SearchForm'"/>
    		</dhv:permission>
    	</td>		
     </tr>	
	
	
	<!-- tr>
		<td colspan="4">
		<input type="button" class="lightGreyBigButton" value="Operatori abusivi" style="height:50px !important; width:350px !important;" onClick="goTo('Abusivismi.do')">
		</td>
		<td colspan="4">
		&nbsp;
		</td>
	</tr-->
</table>
</fieldset>
 </center>
