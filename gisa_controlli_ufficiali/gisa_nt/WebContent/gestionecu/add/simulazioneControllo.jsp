<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>
function checkForm(form){
	loadModalWindow();
	form.submit();
}
</script>

<form name="aggiungiCU" action="GestioneCU.do?command=Simulazione&auto-populate=true" onSubmit="" method="post">

<center>
<b>Simulazione inserimento controllo ufficiale</b><br/>
Inserire il JSON (anche parziale). Il sistema tenterà di tradurlo in tentativo di inserimento.<br/>

<textarea rows="10" cols="200" id="jsonControllo" name="jsonControllo">
{"Tecnica":{"nome":"Ispezione con la Tecnica di Sorveglianza","id":5},"Linee":[{"codice":"MS.020-MS.020.500-852IT3A401","nome":"PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO -> PRODUZIONE DI CIBI PRONTI IN GENERE -> PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO->PRODUZIONE DI CIBI PRONTI IN GENERE->PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)","id":"192439"}],"Dati":{"note":"ks","dataInizio":"2021-08-31","dataFine":"2021-09-29"},"Anagrafica":{"partitaIva":"03280191218","riferimentoId":183564,"ragioneSociale":"A PIZZA DO CAFONE DI CERCIELLO CARMINE","riferimentoIdNomeTab":"opu_stabilimento"},"Nucleo":[{"nominativo":"SILVANA MALVA","id":1485,"Qualifica":{"nome":"Altro Funzionario Laureato","id":"96"},"Struttura":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO IGIENE DEGLI ALIMENTI E DELLA NUTRIZIONE->UOC SIAN","id":"8364"}}],"Utente":{"userId":<%=User.getUserId() %>},"Oggetti":[],"Asl":{"nome":"NAPOLI 3 SUD","id":206},"Motivi":[{"idPianoMonitoraggio":0,"CampiEstesi":[],"idAttivita":9295,"nome":"ISPEZIONI CON LA TECNICA DELLA SORVEGLIANZA IN TUTTI I TIPI DI STABILIMENTO  - ATT B5 >> EFFETTUAZIONI DI N. ISPEZIONI CON LA TECNICA DELLA SORVEGLIANZA          ","codiceEvento":"null","PerContoDi":{"nome":"UNITA OPERATIVA COMPLESSA SERVIZIO DI IGIENE E SANIT\u00c0 PUBBLICA->UOPC AMBITO 2","id":"8480"}}]}
</textarea>

<!-- BOTTONI -->
<table class="details" cellpadding="10" cellspacing="10" width="100%">
<tr>
<td colspan="2" align="center"><input type="button" style="font-size:40px" value="PROSEGUI" onclick="checkForm(this.form)"/>
</td>
</tr>
</table>
<!-- BOTTONI -->

</center>

</form>