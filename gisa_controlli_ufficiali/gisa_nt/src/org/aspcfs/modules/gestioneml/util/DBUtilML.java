/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;


public class DBUtilML {
	
	
	
	private static HashMap<String,Integer> fromDescrCodiceToId = new HashMap<String,Integer>();
	static
	{
		fromDescrCodiceToId.put("BDN", 3);
		fromDescrCodiceToId.put("API", 2);
		fromDescrCodiceToId.put("", 4);
		fromDescrCodiceToId.put(null, 4);
		fromDescrCodiceToId.put("SINTESI", 5); 
	}
	
	public static int getIdNorma(Connection db,String stringValue2)  {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		
		if(stringValue2 == null || stringValue2.equals(""))
			return -1;
		try
		{
			pst =  db.prepareStatement("select code from opu_lookup_norme_master_list where lower(description) = lower(?)"); //TODO CAMPI AGGIUNTIVI NON MESSI
			pst.setString(1,stringValue2);
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getInt(1);
					
			return toRet;
		}
		catch(Exception ex)
		{
//			ex.printStackTrace();
			return -1;
		}
		finally
		{
			try{rs.close();}catch(Exception ex2){}
			try{pst.close();}catch(Exception ex2){}
		}
	}

	public static int inserisciInMasterListSuap(Connection db, HashMap<String,String> campiAggiuntivi) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		
		try
		{
			pst =  db.prepareStatement("insert into master_list_suap6(descrizione,livello,id_padre,id_norma,cod_macroarea,cod_aggregazione,cod_attivita,id_nodo_radice) values(?,?,?,?,?,?,?,? )"); //TODO CAMPI AGGIUNTIVI NON MESSI
			
			String descrzConcat = campiAggiuntivi.get("concatDesc");
			int livello =  Integer.parseInt(campiAggiuntivi.get("lvl")); //il livello � quello della colonna excel
			int idPadre = Integer.parseInt(campiAggiuntivi.get("idPadre") == null ? "-1" : campiAggiuntivi.get("idPadre")); 
			
			
			String codMacro =campiAggiuntivi.get("codMacro"); 
			String codAggr = campiAggiuntivi.get("codAggr"); 
			String codAttivita = campiAggiuntivi.get("codAttivita"); 
			int idRadiceMasterListSuap = Integer.parseInt(campiAggiuntivi.get("idRadiceMasterListSuap") == null || campiAggiuntivi.get("idRadiceMasterListSuap").equals("") ? "-1" : campiAggiuntivi.get("idRadiceMasterListSuap"));
			int idNorma =Integer.parseInt(campiAggiuntivi.get("idNorma") == null ? "-1" : campiAggiuntivi.get("idNorma")); 
			
			pst.setString(1,descrzConcat);
			pst.setInt(2,livello);
			pst.setInt(3, idPadre);
			pst.setInt(4, idNorma);
			pst.setString(5, codMacro);
			pst.setString(6, codAggr);
			pst.setString(7, codAttivita);
			pst.setInt(8, idRadiceMasterListSuap);
			
			pst.executeUpdate();
			pst.close();
			
			pst = db.prepareStatement("select max(id) from master_list_suap6");
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getInt(1);
					
			return toRet;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex ;
		}
		finally
		{
			try{rs.close();}catch(Exception ex2){}
			try{pst.close();}catch(Exception ex2){}
		}
		
	}
	
	
	
	
	
public static int inserisciInCampiEstesi(Connection db, HashMap<String,String> campiAggiuntivi) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		
		try
		{
			pst =  db.prepareStatement("insert into campi_estesi_masterlistsuap51(id_padre,livello,descrizione,attivita_specifica,caratterizzazione_specifica,dettaglio_specializzazione_produttiva,note_informative, id_radice_campi_estesi,id_attivita_master_list_suap) values(?,?,?,?,?,?,?,?,?)"); //TODO CAMPI AGGIUNTIVI NON MESSI
			
			String descrzConcat = campiAggiuntivi.get("concatDesc");
			int livello =  Integer.parseInt(campiAggiuntivi.get("lvl")); //il livello � quello della colonna excel
			int idPadre = Integer.parseInt(campiAggiuntivi.get("idPadre") == null ? "-1" : campiAggiuntivi.get("idPadre")); 
			String attivitaSpecifica = campiAggiuntivi.get("attivitaSpecifica");
			String caratterizzazioneSpecifica = campiAggiuntivi.get("caratterizzazioneSpecifica");
			String dettaglioSpecializzazioneProduttiva = campiAggiuntivi.get("dettaglioSpecializzazione");
			String note = campiAggiuntivi.get("note");
			int idNodoRadiceAlberoCampiEstesi = Integer.parseInt(campiAggiuntivi.get("radiceAlberoCampiEstesi") == null || campiAggiuntivi.get("radiceAlberoCampiEstesi").equals("") ? "-1" : campiAggiuntivi.get("radiceAlberoCampiEstesi") );
			int idFogliaMasterListLineaAttivita = Integer.parseInt( campiAggiuntivi.get("idLineaAttivitaFogliaMLDiProvenienza") == null ? "-1" : campiAggiuntivi.get("idLineaAttivitaFogliaMLDiProvenienza"));
			
			pst.setInt(1, idPadre);
			pst.setInt(2,livello);
			pst.setString(3,descrzConcat);
			pst.setString(4,attivitaSpecifica);
			pst.setString(5,caratterizzazioneSpecifica);
			pst.setString(6,dettaglioSpecializzazioneProduttiva);
			pst.setString(7,note);
			pst.setInt(8, idNodoRadiceAlberoCampiEstesi);
			pst.setInt(9, idFogliaMasterListLineaAttivita);
			
			pst.executeUpdate();
			pst.close();
			
			pst = db.prepareStatement("select max(id) from campi_estesi_masterlistsuap51");
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getInt(1);
					
			return toRet;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex ;
		}
		finally
		{
			try{rs.close();}catch(Exception ex2){}
			try{pst.close();}catch(Exception ex2){}
		}
		
	}

	
	 
	public static <T> void aggiornaCampoPer(Connection db,String nomeTabella,String campoDaAggiornare, String condizione, String valoreNuovo,Class<T> tipoCampo) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			if(tipoCampo.getName().equals(Integer.class.getName()))
			{
				 
				pst =  db.prepareStatement("update "+nomeTabella+" set "+campoDaAggiornare+" = ?" +" where "+ condizione);  
				pst.setInt(1, Integer.parseInt(valoreNuovo));
				
			}
			else if(tipoCampo.getName().equals(Double.class.getName()))
			{
				 
				pst =  db.prepareStatement("update "+nomeTabella+" set "+campoDaAggiornare+" = ?" +" where "+ condizione);  
				pst.setDouble(1, Double.parseDouble(valoreNuovo));
			}
			else if(tipoCampo.getName().equals(String.class.getName()))
			{
				valoreNuovo = valoreNuovo.replace("['\"]", "");
				pst =  db.prepareStatement("update "+nomeTabella+" set "+campoDaAggiornare+" = ?" +" where "+ condizione);  
				pst.setString(1, valoreNuovo);
			}
			
			
			 
			
			pst.executeUpdate();
					
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex ;
		}
	}

	public static void agganciaCampoEstesoToMasterListSuap(Connection db, int idLineaDiAttivitaMLDaAgganciare,
			int idAttivitaSpecifica) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			 
			
			pst = db.prepareStatement("insert into master_list_to_campi_estesi51 values(?,?)");
			pst.setInt(1, idLineaDiAttivitaMLDaAgganciare);
			pst.setInt(2, idAttivitaSpecifica);
			
			pst.executeUpdate();
					
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex ;
		}
		
	}

//	public static Integer[] ottieniIdGruppoPerCodiciGruppi(Connection db, HashMap<String, String> campiDiMioPadreDaCuiDipendo) throws Exception 
//	{
//		 
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		ArrayList<Integer> idsToRet = null;
//	 
//		String codiciGruppi = campiDiMioPadreDaCuiDipendo.get("codiciDocumenti");
//		if(codiciGruppi == null || codiciGruppi.equals(""))
//		{
//			return null;
//		}
//		String temp[] = codiciGruppi.split(",");
//		idsToRet = new ArrayList<Integer>();
//		
//		for(String codiceGruppo : temp)
//		{
//
//			
//			try
//			{
//				
//				codiceGruppo = codiceGruppo.trim();
//				if(codiceGruppo.equals(""))
//					continue;
//				
//				pst = db.prepareStatement("select id from master_list_suap_allegati_procedure where lower(codice_gruppo) = lower(?) ");
//				pst.setString(1,codiceGruppo);
//				rs = pst.executeQuery();
//				while(rs.next())
//				{
//					idsToRet.add(rs.getInt(1));
//				}
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//				 
//			}
//			finally
//			{
//				try{rs.close();}catch(Exception ex){}
//				try{pst.close();}catch(Exception ex){}
//			}
//		}
//		
//		if(idsToRet.size() == 0)
//		{
//			return null;
//		}
//		
//		return idsToRet.toArray(new Integer[idsToRet.size()]);
//		
//		 
//	 
// 
//			
//		
//	}

//	public static void inserisciInCampiPerDocumentiAllegati(Connection db, Integer idUltimoInserimentoDettaglio,
//			Integer[] idsCodiciDocumento) throws Exception
//	{
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		
//		
//		for(Integer idAllegato : idsCodiciDocumento)
//		{
//			
//			try
//			{
//				//NB in realt� idmasterlistsuap non punta alla masterlistsuap ma alla tabella campi estesi 
//				pst = db.prepareStatement("insert into master_list_suap_allegati_procedure_relazione51(id_master_list_suap,id_master_list_suap_gruppo_allegati) values(?,?)");
//				pst.setInt(1, idUltimoInserimentoDettaglio.intValue());
//				pst.setInt(2, idAllegato.intValue());
//				
//				pst.executeUpdate();
//						
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//				throw ex; 
//			}
//			finally
//			{
//				try{pst.close();}catch(Exception ex){}
//				try{rs.close();}catch(Exception ex){}
//			}
//		}
//		
//		
//		
//	}

	public static void inserisciInformazioniFlusso(Connection db, HashMap<String, String> campiDiMioPadreDaCuiDipendo, boolean riconoscibili) throws Exception {
		
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			int idConfigFlusso = -1;
			
			
			//devo fare il mapping tra questa descrizione, ed il codice ottenuto da lookup_configurazione_validazione_master_list
			//a meno che non si tratti di foglio excel dei riconoscibili (in quel caso ci va sempre 1)
			if(riconoscibili)
			{
				idConfigFlusso = 1;
			}
			else
			{
				//mapping
				String descr = campiDiMioPadreDaCuiDipendo.get("descrCodiceNazionale");
				if(descr != null && !descr.equals("") && fromDescrCodiceToId.get(descr) == null) //cio� � un valore presente ma che non e' stato gestito nelle descrizioni
				{
					//allora gli diamo stesso valore di bdn
					descr = "BDN";
				}
				idConfigFlusso = fromDescrCodiceToId.get(descr);
				 
			}
			 
			//ottengo id della foglia della master list su cui fare update
			int idLineaAttivita = Integer.parseInt(campiDiMioPadreDaCuiDipendo.get("idFogliaLineaAttivita"));
			pst = db.prepareStatement("update master_list_suap6 set id_lookup_configurazione_validazione = ? where id = ?");
			pst.setInt(1,idConfigFlusso);
			pst.setInt(2,idLineaAttivita);
			pst.executeUpdate();
			
			 
					
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex ;
		}
		
		
	}
}
