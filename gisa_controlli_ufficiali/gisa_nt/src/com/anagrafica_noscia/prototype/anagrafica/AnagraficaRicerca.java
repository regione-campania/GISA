/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.anagrafica;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.anagrafica_noscia.prototype.base_beans.Impresa;
import com.anagrafica_noscia.prototype.base_beans.LookupPair;
import com.anagrafica_noscia.prototype.base_beans.RelazioneStabilimentoLineaProduttiva;
import com.anagrafica_noscia.prototype.base_beans.SoggettoFisico;
import com.anagrafica_noscia.prototype.base_beans.Stabilimento;
import com.anagrafica_noscia.prototype.base_beans.Utilities;

/*classe che aggrega tutte le entita'
 * relative ad un'entry di ricerca
 */
public class AnagraficaRicerca  extends AnagraficaBase{

	
	/*prende il mappazzone json in arrivo dalla search form del client e ottiene la lista 
	 * di anagrafiche 
	 * che incapsulano le entita' coinvolte 
	 */
	public static ArrayList<AnagraficaBase> build_and_search(JSONObject useThisToSearch, Connection conn) throws Exception
	{
		ArrayList<AnagraficaBase> toRet = new ArrayList<AnagraficaBase>();
		HashMap<Integer, AnagraficaBase> utilHash = new HashMap<Integer,AnagraficaBase>(); /*usata come appoggio, la chiave e' id impresa */
		
		/*estraggo tutti i dati dal mappazzone (se i dati arrivano "" li setto a null) */
		/*parte impresa*/
		boolean searchByImpresa = useThisToSearch.getBoolean("ricercaper_datiimpresa");
		String ragioneSociale = Utilities.getAsNullIfEmpty("ragione_sociale",useThisToSearch); 
		String codiceFiscale = Utilities.getAsNullIfEmpty("codice_fiscale",useThisToSearch); 
		String partitaIva = Utilities.getAsNullIfEmpty("partitaIva",useThisToSearch); 
		
		/*parte legale */

		boolean searchByLegale = useThisToSearch.getBoolean("ricercaper_datilegale");
		String nomeLegale = Utilities.getAsNullIfEmpty("nome_rappresentante",useThisToSearch);
		String cognomeLegale = Utilities.getAsNullIfEmpty("cognome_rappresentante",useThisToSearch);
		String codFiscaleRapp = Utilities.getAsNullIfEmpty("codice_fiscale_rappresentante", useThisToSearch);
		
		/*parte stabilimento */
		boolean searchByStabilimento = useThisToSearch.getBoolean("ricercaper_datistab");
		String idtipoAttivita =   Utilities.getAsNullIfEmpty("idtipoattivita_scelto",useThisToSearch);
		Integer idTipoAttivitaI = idtipoAttivita != null ? Integer.parseInt(idtipoAttivita) : null;
		String descComunestab = Utilities.getAsNullIfEmpty("comune_stab",useThisToSearch);
		
		
		/*la ricerca deve essere fatta in AND tra le sezioni scelte tra le 3 (eventualmente tutte e 3) dall'utente
		 * cioe' impresa, legale e stabilimento
		 */
		
		
		/*inizio mettendo tutte le imprese che soddisfano i requisiti (SE E' RICHIESTA RICERCA PER IMPRESA )*/
		ArrayList<Impresa> impreseFiltrate = null;
		
		if(searchByImpresa) /*e' richiesto filtraggio con i dati impresa*/
		{
			 impreseFiltrate = Impresa.getByDatiAnag(codiceFiscale, ragioneSociale, partitaIva, conn);
		}
		else /*le prendo tutte */
		{
			impreseFiltrate = Impresa.getAllImprese(conn);
		}
		
		
		if(searchByLegale
			&&  (nomeLegale != null || cognomeLegale != null || codFiscaleRapp != null)/*anche se e' flaggato "search by legale" client side, deve essere passata almeno una restrizione (sul nome o altro) altrimenti
			la ricerca in toto e' troppo pesante (e superflua) rispetto a tutti i soggetti fisici */
			)
		{
			/*e' richiesto filtraggio anche tramite rapp legale, quindi prendo tutti i rapp legali per quei filtri 
			 * e tiro via le imprese che non matchano*/
			ArrayList<SoggettoFisico> rappsFiltrati = SoggettoFisico.getByDatiAnag(nomeLegale,  cognomeLegale, codFiscaleRapp, conn);
			/*li metto in un hash map cosi' evito di scorrerli ogni volta */
			HashMap<Integer, Boolean> rappsFiltratiHash = new HashMap<Integer,Boolean>();
			for(SoggettoFisico sf : rappsFiltrati)
			{
				rappsFiltratiHash.put(sf.getId(), true);
			}
			/*scorro ora su tutte le imprese precedentemente selezionate, ed elimino tutte quelle che non hanno 
			 * almeno un rapp legale nel set dei rappsFiltrati
			 */
			ArrayList<Impresa> temp = new ArrayList<Impresa>(); 
			
			for(Impresa imp : impreseFiltrate)
			{
				 for(SoggettoFisico sogg : imp.getLegaliRappresentanti())
				 {
					 if(rappsFiltratiHash.containsKey(sogg.getId()))
					 {
						 /*trovato almeno un rapp legale tra quelli filtrati che fa match con uno di quelli dell'impresa
						  */
						 temp.add(imp);
						 break;
					 }
				 }
			}
			/*in temp ho le imprese che hanno tra i legali rappresentanti almeno uno dei filtrati */
			impreseFiltrate = temp;
		}
		
		
		
		 
		if(searchByStabilimento
				&& (idTipoAttivitaI != -1 ||descComunestab != null)) /*anche se il client flagga search by stabilimento, deve specificare almeno un campo di filtering altrimenti 
				e' superflua e costosa la ricerca per tutti stabs */
		{
			/* e' richiesto filtraggio degli stabilimenti */
			ArrayList<Stabilimento> stabsFiltrati = Stabilimento.getByDatiAnag(idTipoAttivitaI, descComunestab , conn);
			/*li metto in un hash map cosi' evito di scorrerli ogni volta */
			HashMap<Integer, Boolean> stabsFiltratiH = new HashMap<Integer,Boolean>();
			for(Stabilimento stab : stabsFiltrati)
			{
				stabsFiltratiH.put(stab.getId(), true);
			}
			
			/*scorro sulle imprese filtrate */
			ArrayList<Impresa> temp = new ArrayList<Impresa>();
			
			for(Impresa imp : impreseFiltrate)
			{
				/*scorro sugli stabilimenti */
				for(Stabilimento stab : imp.getStabilimenti())
				{
					if(stabsFiltratiH.containsKey(stab.getId()))
					{

						 /*trovato almeno uno stab tra quelli filtrati che fa match con uno di quelli dell'impresa
						  */
						temp.add(imp);
						break;
					}
				}
			}
			
			/*in temp ho le imprese che hanno tra i legali rappresentanti almeno uno dei filtrati */
			impreseFiltrate = temp;
			
			
		}
		
		/*come ultimo filtraggio tiro via tutte le imprese che tra le linee degli stabilimenti associati non hanno
		 * almeno una di flusso origine no-scia
		 */
		ArrayList<Impresa> temp = new ArrayList<Impresa>();
		for(Impresa imp : impreseFiltrate)
		{
			imp_loop: 
			{
				List<Stabilimento> stabs = imp.getStabilimenti();
				for(Stabilimento stab : stabs)
				{
					List<RelazioneStabilimentoLineaProduttiva> rels = stab.getLineeProds();
					for(RelazioneStabilimentoLineaProduttiva rel : rels)
					{
						int idFlussoOriginale = rel.getAggregazione().getIdFlussoOriginaleAggregazione();
						/*questa e' la desc del flusso */
						String descFlussoOriginale = LookupPair.buildByCode(conn, "lookup_flusso_originale_ml", "code", "description", idFlussoOriginale, "enabled = true").getDesc();
						if(descFlussoOriginale.equalsIgnoreCase("NO-SCIA"))
						{
							temp.add(imp);
							break imp_loop;
						}
						
					}
				}
			}
		}
		
		/*ora in temp ho solo imprese che soddisfano tutti i criteri di scelta */
		/*metto il primo stabilimento ed il primo legale nell'anagrafica della ricerca */
		for(Impresa imp : impreseFiltrate)
		{
			AnagraficaRicerca anagToInsert = new AnagraficaRicerca();
			anagToInsert.setImpresa(imp);
			anagToInsert.setLegaliRappresentanti(imp.getLegaliRappresentanti());
			anagToInsert.setStabilimenti(imp.getStabilimenti());
			toRet.add(anagToInsert);
		}
		
		return toRet;
	}
}
