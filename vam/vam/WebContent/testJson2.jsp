<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.JsonParser"%>
<%
		String prova = "{idAslRiferimento=206, note=v, commandOld=RegistrazioniAnimaledoAdd}"; //, nascita=04/01/2014, idProprietarioCorrente=4192562, idAnimale=6170087, idTipologiaEventoVam=, microchip= 380260002263877, dataAperturaCC=null, command=Insert, specieAnimaleId=1, TimeIni=1458200593291, ruolo=18, doContinue=false, idDetentoreCorrente=4192562, idTipologiaEvento=11, auto-populate=true, datatocheck=, origineInserimento=null, idStatoOriginale=2, registrazione=08/03/2014}";
	
	
		JsonParser parser = new JsonParser();
		JsonObject json1 = (JsonObject) parser.parse(prova);
		int idAnimale 		  = json1.get("idAnimale").getAsInt();
		int idTipologiaEvento = json1.get("idTipologiaEvento").getAsInt();
%>
		Registrazione inserita -  idAnimale: <%=idAnimale%>, idTipologiaEvento: <%=idTipologiaEvento%>
		
	