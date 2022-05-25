/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.anagrafica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.anagrafica_noscia.prototype.base_beans.Comune;
import com.anagrafica_noscia.prototype.base_beans.Impresa;
import com.anagrafica_noscia.prototype.base_beans.Indirizzo;
import com.anagrafica_noscia.prototype.base_beans.LookupPair;
import com.anagrafica_noscia.prototype.base_beans.RelazioneStabilimentoLineaProduttiva;
import com.anagrafica_noscia.prototype.base_beans.SoggettoFisico;
import com.anagrafica_noscia.prototype.base_beans.Stabilimento;
import com.anagrafica_noscia.prototype.base_beans.Utilities;




/*classe che aggrega tutte le entita'
 * che corrispondono ad un inserimento
 */
public class AnagraficaInserimento extends AnagraficaBase 
{

	
	/*prende tutti i dati del mappazzone, e li rimanipola per creare i json da dare in pasto alla dbi del livello intermedio che fa inserimento */
	/*INDIRECT indica che usa il livello intermedio di dbi, e non le dbi di inserimento diretto sulle entita' */
	public static int  build_and_insert_indirect(JSONObject useThisToBuild, Connection conn,int userId) throws Exception
	{
		int toret = -1;
		boolean autocomm = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf0 = new SimpleDateFormat("dd/MM/yyyy");
		JSONObject jsonImpresa = null, jsonStabilimento = null, jsonLegaleRapp = null;
		JSONObject jsonIndImpresa = null, jsonIndLegaleRapp = null, jsonIndStab = null; 
		JSONArray jsonArrLinee = null;
		
		try
		{
			/*estraggo tutti i dati che arrivano dal form, tutto come string*/
			/*Impresa ****
			 */
			String dataArrivoPecS = Utilities.getNullableFieldAsString("dataarrivopec_scelto",useThisToBuild);  /*CHE FARCI ? */
			//String idComuneSceltoS = Utilities.getNullableFieldAsString("id_comune_scelto",useThisToBuild);  /*CHE FARCI ? */
			String idTipoImpresaS = Utilities.getNullableFieldAsString("id_tipo_impresa_scelto",useThisToBuild);
			String ragioneSocialeS = Utilities.getNullableFieldAsString("ditta_scelto",useThisToBuild);
			String pIvaImpresaS = Utilities.getNullableFieldAsString("piva_scelto",useThisToBuild);
			String cfImpresaS = Utilities.getNullableFieldAsString("cf_scelto",useThisToBuild);
			String cfUgualePivaImpresaS = Utilities.getNullableFieldAsString("cf_uguale_piva",useThisToBuild);
			boolean cfImpresaUgualePiva = Boolean.parseBoolean(cfUgualePivaImpresaS);
			if(cfImpresaUgualePiva)
			{
				cfImpresaS = pIvaImpresaS;
			}
			String pecImpresaS = Utilities.getNullableFieldAsString("pec_scelto",useThisToBuild);  /*CHE FARCI ? */
			String noteImpresaS = Utilities.getNullableFieldAsString("note_scelto",useThisToBuild);  /*CHE FARCI ? */
			
			/*Dati legale rapp
			 * 
			 */
			String nomeLegaleRappS = Utilities.getNullableFieldAsString("nomelegale_scelto",useThisToBuild);
			String cognomeLegaleRappS = Utilities.getNullableFieldAsString("cognomelegale_scelto",useThisToBuild);
			String idSexLegaleRappS = Utilities.getNullableFieldAsString("idsessolegale_scelto",useThisToBuild);
			String dataNascitaLegaleRappS = Utilities.getNullableFieldAsString("datanascitalegale_scelto",useThisToBuild);
			String nazioneNascitaLegaleRappS = Utilities.getNullableFieldAsString("nazionenascitalegale_scelto",useThisToBuild); /*CHE FARCI ? */
			String comuneNascitaLegaleRappS = Utilities.getNullableFieldAsString("desccomunenascitalegale_scelto",useThisToBuild);
			String cfLegaleRappS = Utilities.getNullableFieldAsString("cflegale_scelto",useThisToBuild);
			String nazioneResidenzaLegaleRappS = Utilities.getNullableFieldAsString("nazioneresidenzalegale_scelto",useThisToBuild);
			String provinciaResidenzaLegaleRappS = Utilities.getNullableFieldAsString("provinciaresidenzalegale_scelto",useThisToBuild);  /*CHE FARCI ? */
			String comuneResidenzaLegaleRappS = Utilities.getNullableFieldAsString("comuneresidenzalegale_scelto",useThisToBuild);
			Integer idComuneResidenzaLegaleRappS = null;
			
			try
			{
				idComuneResidenzaLegaleRappS = Comune.getByDesc(conn, comuneResidenzaLegaleRappS).getId();
			}
			catch(Exception ex)
			{
				idComuneResidenzaLegaleRappS = -1;
			}
			
			String idToponimoLegaleRappS = Utilities.getNullableFieldAsString("idtoponimoindirizzolegale_scelto",useThisToBuild);
			String viaLegaleRappLegaleS = Utilities.getNullableFieldAsString("vialegale_scelto",useThisToBuild);
			String civicoRappLegaleS = Utilities.getNullableFieldAsString("civicolegale_scelto",useThisToBuild);
			String capRappLegaleS = Utilities.getNullableFieldAsString("caplegale_scelto",useThisToBuild);
			String telRappLegaleS = Utilities.getNullableFieldAsString("telefonolegale_scelto",useThisToBuild);  /*CHE FARCI ? */
			String pecRappLegaleS = Utilities.getNullableFieldAsString("peclegale_scelto",useThisToBuild);  /*CHE FARCI ? */
			
			/*
			 * Dati sede Legale impresa (indirizzo impresa)
			 */
			String nazioneSedeLegaleS = Utilities.getNullableFieldAsString("nazionesedelegale_scelto",useThisToBuild);
			String provinciaSedeLegaleS = Utilities.getNullableFieldAsString("provinciasedelegale_scelto",useThisToBuild);  /*CHE FARCI ? */
			String descComuneSedeLegaleS = Utilities.getNullableFieldAsString("comunesedelegale_scelto",useThisToBuild);
			Integer idComuneSedeLegale = null;
			try
			{
				idComuneSedeLegale = Comune.getByDesc(conn, descComuneSedeLegaleS).getId();
			}
			catch(Exception ex)
			{
				idComuneSedeLegale = -1;
			}
			
			String idTOponimoSedeLegaleS = Utilities.getNullableFieldAsString("idtoponimosedelegale_scelto",useThisToBuild);
			String viaSedeLegaleS = Utilities.getNullableFieldAsString("viasedelegale_scelto",useThisToBuild);
			String civicoSedeLegaleS = Utilities.getNullableFieldAsString("civicosedelegale_scelto",useThisToBuild);
			String capSedeLegaleS = Utilities.getNullableFieldAsString("capsedelegale_scelto",useThisToBuild);
			
			/*
			 * Dati Stabilimento (indirizzo sede operativa)
			 */
			String denominazioneStabS = Utilities.getNullableFieldAsString("denominazionestab_scelto", useThisToBuild);
			String provinciaSedeStabS = Utilities.getNullableFieldAsString("provinciastabilimento_scelto",useThisToBuild);
			String idComuneSedeStabS = Utilities.getNullableFieldAsString("id_comune_stabilimento_scelto",useThisToBuild);
			String idTOponimoSedeStabS = Utilities.getNullableFieldAsString("idtoponimoindirizzostabilimento_scelto",useThisToBuild);
			String viaSedeStabS = Utilities.getNullableFieldAsString("viastabilimento_scelto",useThisToBuild);
			String civicoSedeStabS = Utilities.getNullableFieldAsString("civicostabilimento_scelto",useThisToBuild);
			String capSedeStabS = Utilities.getNullableFieldAsString("capstabilimento_scelto",useThisToBuild);
			String latStabS = Utilities.getNullableFieldAsString("latitudine_scelto",useThisToBuild);
			String lonStabS = Utilities.getNullableFieldAsString("longitudine_scelto",useThisToBuild);
			
			String sedeLegaleUgualeSedeOperativaS = Utilities.getNullableFieldAsString("sedelegale_corrisponde_stabilimento",useThisToBuild);
			boolean sedeLegaleUgualeSedeOperativa = Boolean.parseBoolean(sedeLegaleUgualeSedeOperativaS);
			/*se e' true, allora i dati della sede legale sono uguali a quelli dello stabilimento */
			if(sedeLegaleUgualeSedeOperativa)
			{
				 nazioneSedeLegaleS = "Italia";
				 provinciaSedeLegaleS = provinciaSedeStabS;
				 idComuneSedeLegale = Integer.parseInt(idComuneSedeStabS);
				 idTOponimoSedeLegaleS = idTOponimoSedeStabS;
				 viaSedeLegaleS = viaSedeStabS;
				 civicoSedeLegaleS = civicoSedeStabS;
				 capSedeLegaleS = capSedeStabS;
			}
			
			
			/*scorro sui paths arrivati e popolo quelle che sono le linee che andranno istanziate */
			JSONArray paths = useThisToBuild.getJSONArray("paths");
			ArrayList<RelazioneStabilimentoLineaProduttiva> rels = new ArrayList<RelazioneStabilimentoLineaProduttiva>();
			
			for(int i=0; i< paths.length(); i++)
			{
				JSONArray path = paths.getJSONArray(i); /*ciascuno di questi e' un livello della ML */
				
				JSONObject entryMLMacroarea = path.getJSONObject(0);
				JSONObject entryMLAggregazione = path.getJSONObject(1);
				JSONObject entryMLAttivita = path.getJSONObject(2);
				
				/*TODO MECCANISMO QUI GENERAZIONE NUM REG E CUN */
				/*TODO MECCANISMO CAMPI ESTESI QUI */
				RelazioneStabilimentoLineaProduttiva relstablp = new RelazioneStabilimentoLineaProduttiva();
				relstablp.setIdMacroarea(entryMLMacroarea.getInt("id"));
				relstablp.setIdAggregazione(entryMLAggregazione.getInt("id"));
				relstablp.setIdLineaAttivita(entryMLAttivita.getInt("id"));
				
				/*la data inizio TODO e data fine*/
				
				rels.add(relstablp);
			}
			
			
			
			
			/*ELABORO I DATI *************************************************************************/
			/*creo 4 rappresentazioni json (mappa per impresa, legalerapp e stabilimento e array di mappe per linee) da passare alla dbi*/
			
			/*impresa*/
			jsonImpresa = new JSONObject();
			jsonIndImpresa = new JSONObject();
			
			jsonImpresa.put("ragione_sociale", ragioneSocialeS);
			jsonImpresa.put("codice_fiscale", cfImpresaS );
			jsonImpresa.put("partita_iva", pIvaImpresaS );
			jsonImpresa.put("id_tipo_impresa_societa", idTipoImpresaS);
			jsonImpresa.put("data_arrivo_pec", dataArrivoPecS);
			jsonIndImpresa.put("toponimo", idTOponimoSedeLegaleS);
			jsonIndImpresa.put("descr_indirizzo", viaSedeLegaleS);
			jsonIndImpresa.put("civico", civicoSedeLegaleS);
			jsonIndImpresa.put("provincia", provinciaSedeLegaleS);
			jsonIndImpresa.put("cap", capSedeLegaleS);
			jsonIndImpresa.put("nazione", nazioneSedeLegaleS);
			jsonIndImpresa.put("comune", idComuneSedeLegale+"");
			jsonImpresa.put("indirizzo", jsonIndImpresa ); 
			
			
			/*legale rapp impresa */
			jsonLegaleRapp = new JSONObject();
			jsonLegaleRapp.put("cognome", cognomeLegaleRappS);
			jsonLegaleRapp.put("nome", nomeLegaleRappS);
			jsonLegaleRapp.put("comune_nascita", comuneNascitaLegaleRappS);
			jsonLegaleRapp.put("codice_fiscale", cfLegaleRappS);
			jsonLegaleRapp.put("sesso", idSexLegaleRappS.equals("0") ? "M" : "F");
			jsonLegaleRapp.put("telefono", telRappLegaleS);
			jsonLegaleRapp.put("email", pecRappLegaleS);
			jsonLegaleRapp.put("telefono1", telRappLegaleS);
			jsonLegaleRapp.put("data_nascita", dataNascitaLegaleRappS);
			jsonIndLegaleRapp = new JSONObject();
			jsonIndLegaleRapp.put("toponimo", idToponimoLegaleRappS);
			jsonIndLegaleRapp.put("descr_indirizzo", viaLegaleRappLegaleS);
			jsonIndLegaleRapp.put("civico", civicoRappLegaleS);
			jsonIndLegaleRapp.put("cap", capRappLegaleS);
			jsonIndLegaleRapp.put("comune", idComuneResidenzaLegaleRappS+"");
			jsonLegaleRapp.put("indirizzo", jsonIndLegaleRapp);
			
		 
			/*stab*/
			
			jsonStabilimento = new JSONObject();
			jsonIndStab = new JSONObject();
			
			jsonStabilimento.put("denominazione", denominazioneStabS);
			String idAsl = "16";
			try
			{
				idAsl = Comune.getIdDescAslFromIDComune(conn, Integer.parseInt(idComuneSedeStabS))[0]+"";
			} 
			catch(Exception ex)
			{
				System.out.println("Impossibile ottenere id asl da id comune stab. Metto fuori regione");
				
			}
			jsonStabilimento.put("id_asl",  idAsl);
			jsonIndStab.put("toponimo", idTOponimoSedeStabS);
			jsonIndStab.put("descr_indirizzo", viaSedeStabS);
			jsonIndStab.put("civico", civicoSedeStabS);
			jsonIndStab.put("cap", capSedeStabS);
			jsonIndStab.put("provincia", provinciaSedeStabS);
			jsonIndStab.put("comune", idComuneSedeStabS);
			jsonStabilimento.put("indirizzo",jsonIndStab);
			
			
			/*per le linee attivita */
			jsonArrLinee = new JSONArray();
			for(RelazioneStabilimentoLineaProduttiva rel : rels)
			{
				JSONObject ob = new JSONObject();
				ob.put("idmacroarea", rel.getIdMacroarea() );
				ob.put("idaggregazione", rel.getIdAggregazione());
				ob.put("idattivita", rel.getIdLineaAttivita());
				ob.put("numeroregistrazione", "");
				ob.put("cun", "");
				ob.put("telefono", "");
				ob.put("fax", "");
				jsonArrLinee.put(ob);
			}
	 
			System.out.println("Le rappresentazioni JSON da passare alla dbi intermedia di inserimento OSA nuova anagraficha, sono le seguenti :\n");
			System.out.println("Per Impresa ---------------------------------------------------:\n");
			System.out.println(jsonImpresa.toString()+"\n\n");
			System.out.println("Per Legale Rappresentante ---------------------------------------------------:\n");
			System.out.println(jsonLegaleRapp.toString()+"\n\n");
			System.out.println("Per Stabilimento ---------------------------------------------------:\n");
			System.out.println(jsonStabilimento.toString()+"\n\n");
			System.out.println("Per Le Linee ---------------------------------------------------:\n");
			System.out.println(jsonArrLinee.toString()+"\n\n");
			
			JSONObject wrapper0 = new JSONObject();
			JSONObject wrapper1 = new JSONObject();
			JSONObject wrapper2 = new JSONObject();
			JSONObject wrapper3 = new JSONObject();
			wrapper0.put("soggetto_fisico", jsonLegaleRapp);
			wrapper1.put("impresa", jsonImpresa);
			wrapper2.put("stabilimento", jsonStabilimento);
			wrapper3.put("linee_attivita", jsonArrLinee);
			toret = callIndirectInsert(conn, wrapper1, wrapper2, wrapper0, wrapper3,userId);
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{conn.setAutoCommit(autocomm);} catch(Exception ex){}
		}
		return toret ;
	}
	
	
	/*metodo che chiama la dbi di inserimento intermedio , e ritorna valore che indica cosa e' successo*/
	private static int callIndirectInsert(Connection conn, JSONObject jsonImpresa, JSONObject jsonStab, JSONObject jsonLegaleRapp, JSONObject arrLinee,Integer idUtente) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = conn.prepareStatement("select * from public_functions.anagrafica_inserisci_osa(?::json,?::json,?::json,?::json,?)");
			pst.setString(1,jsonLegaleRapp.toString());
			pst.setString(2,jsonImpresa.toString());
			pst.setString(3,jsonStab.toString());
			pst.setString(4,arrLinee.toString());
			pst.setInt(5, idUtente);
			System.out.println("CHIAMO LA DBI INTERMEDIA DI INSERIMENTO OSA (NUOVA ANAGRAFICA CON IL SEGUENTE STATEMENT :------------------------------------ \n");
			System.out.println(pst.toString());
			
			rs = pst.executeQuery();
			rs.next();
			return rs.getInt(1); /*ritorna codice che indica cosa e' avvenuto */
		}
		catch(Exception ex)
		{
			throw ex;
			
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	}
	
	
	
	
	
	
	
	
	

	
}
