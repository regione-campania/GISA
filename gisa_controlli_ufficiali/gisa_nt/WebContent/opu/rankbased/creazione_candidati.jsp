<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<!-- salvo come oggetto js i candidati per tutte le linee -->
<script>
	
		 
		//candidatiPerIdLineeVecchie = [];
		candidatiPerIdLineeVecchie = {};
		<%for (int k=0;k<linee_attivita.size();k++)
		{
		
			int idLineaVecchia = linee_attivita.get(k).getId();
			//int idLineaVecchiaOriginale = linee_attivita.get(k).getIdLineaVecchiaOriginale();
			
			%>
			candidatiPerIdLineeVecchie[<%=idLineaVecchia%>] = [];
			<%
			ArrayList<LineeAttivita> candidati = linee_attivita.get(k).getCandidatiPerRanking();
			if(candidati == null)
				continue; //non sono arrivati candidati per quella linea vecchia
			for(int j=0;j<candidati.size(); j++)
			{
				
				int idLineaNuovaCandidata = candidati.get(j).getId();
				int idMacroareaNuovaCandidata = candidati.get(j).getIdMacroarea();
				int idAggregazioneNuovaCandidata = candidati.get(j).getIdAggregazione();
				int idAttivitaNuovaCandidata = candidati.get(j).getIdAttivita();
				String macroareaNuovaCandidata = candidati.get(j).getMacroarea();
				String aggregazioneNuovaCandidata = candidati.get(j).getAggregazione();
				String attivitaNuovaCandidata = candidati.get(j).getAttivita();
				
				//NB: idLineaVecchia è quella della vecchia ma in lista_linee_attivita_vecchia_anag
				//idLineaVecchiaOriginale è quella della vecchia ma secondo la gestione originale
			%>
			candidatiPerIdLineeVecchie[<%=idLineaVecchia%>].push( 
					{
						
						idLinea : <%=idLineaNuovaCandidata  %>
						,idMacroarea :<%=idMacroareaNuovaCandidata  %>
						,idAggregazione : <%=idAggregazioneNuovaCandidata  %>
						,idAttivita :  <%=idAttivitaNuovaCandidata   %>
						,macroarea : '<%=macroareaNuovaCandidata.replace("'","").replace("\"","") %>'
						,aggregazione :  '<%=aggregazioneNuovaCandidata.replace("'","").replace("\"","") %>'
						,attivita : '<%=attivitaNuovaCandidata.replace("'","").replace("\"","") %>'
					}
				);
			 
				
	
			<%}
		}%>
		 
</script>