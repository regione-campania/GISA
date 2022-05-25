/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.SysoLogger;

public class GestoreComunicazioniBdu {

	public GestoreComunicazioniBdu() {

	}

	public String recuperaOperatoreSuapGisa(Connection db, int idRelStabLp, int idTipoLinea) {
		
		PreparedStatement pst = null;
		String query_da_eseguire_su_bdu = null;
		String select =" select 'select * from public_functions.suap_inserisci_canile_bdu('''||o.partita_iva||''', '''||o.codice_fiscale_impresa||''', '''||o.ragione_sociale||''', '''||o.nome_rapp_sede_legale||''',"
					+ " '''||o.cognome_rapp_sede_legale||''', '''||o.cf_rapp_sede_legale||''','''||o.data_nascita_rapp_sede_legale||''','''||o.sesso||''',"
					+ " '''||regexp_replace(o.comune_nascita_rapp_sede_legale, '''', '''''')||''', '''||o.sigla_prov_soggfisico||''', '''||regexp_replace(o.comune_residenza, '''', '''''')||''', '''||regexp_replace(o.via_rapp_sede_legale, '''', '''''')||''','''||o.civico||''',"
					+ " '''||case when o.cap_residenza is not null then o.cap_residenza else '' end||''','''||o.nazione_residenza||''',"
					+ " '''||o.sigla_prov_legale||''', '''||regexp_replace(o.comune_sede_legale, '''', '''''')||''','''||regexp_replace(o.via_sede_legale, '''', '''''')||''','''||o.civico_sede_legale||''','''||o.cap_sede_legale||''', '''||o.nazione_sede_legale||''',"
					+ " '''||o.sigla_prov_operativa||''','''||regexp_replace(o.comune_stab, '''', '''''')||''','''||regexp_replace(o.via_stabilimento_calcolata, '''', '''''')||''','''||o.civico_sede_stab||''','''||o.cap_stab||''','''||o.nazione_stab||''',"
					+ idTipoLinea +",'||id_asl||',0,"
					+ " '''||case when toponimo_residenza is not null then toponimo_residenza else 'VIA' end||''','''||case when toponimo_sede_legale is not null then toponimo_sede_legale else 'VIA' end||''','''||case when toponimo_sede_stab is not null then toponimo_sede_stab else 'VIA' end ||''','''||id_linea_attivita||''')' as query , * "
					+ " from opu_operatori_denormalizzati_view o where id_linea_attivita = ? ";
		
		try {
			pst=db.prepareStatement(select);
			pst.setInt(1, idRelStabLp);
			System.out.println("query per sincronizzazione gisa-bdu:"+pst.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				query_da_eseguire_su_bdu = rs.getString("query");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query_da_eseguire_su_bdu;
			
	}
	
	
public boolean isPresenteRegistrazione(int idCu) throws SQLException {
		
	
	
	
	
	Connection db =null ; 

	
		PreparedStatement pst = null;
		boolean query_da_eseguire_su_bdu = false;
		String select =" select " + 
				"(select count(*) from evento_morsicatura m, evento e   where m.id_evento   = e.id_evento and e.data_cancellazione is null and e.trashed_date is null and m.id_cu = ?) + " + 
				"(select count(*) from evento_aggressione agg, evento e where agg.id_evento = e.id_evento and e.data_cancellazione is null and e.trashed_date is null and agg.id_cu = ?) ";
		
		try {
			db =GestoreConnessioni.getConnectionBdu();
			pst=db.prepareStatement(select);
			pst.setInt(1, idCu);
			pst.setInt(2, idCu);
			System.out.println("query per cercare registrazioni in bdu legate al cu:"+pst.toString());
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				query_da_eseguire_su_bdu = rs.getInt(1)>0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			db.close();
			
		}
		return query_da_eseguire_su_bdu;
			
	}
	
	
	
	
public void cessazioneOperatoreBduScia(int idStabGisa,Timestamp dataCessazione) throws SQLException {
		
	Connection connectionBdu =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.cessazione_operatore_bdu_da_scia(?,?);";
		
		try {
			connectionBdu =GestoreConnessioni.getConnectionBdu();
			pst=connectionBdu.prepareStatement(select);
			pst.setInt(1, idStabGisa);
			pst.setTimestamp(2, dataCessazione);
			
			System.out.println("VALIDAZIONE CESSAZIONE IN BDU -->"+pst.toString());
			pst.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionBdu.close();
		}
		
			
	}

public void variazioneOperatoreBduScia(int idStabGisaVecchio,int idStabGisaNuovo) throws SQLException {
	
	Connection connectionBdu =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.suap_variazione_titolarita(?,?);";
		
		try {
			connectionBdu =GestoreConnessioni.getConnectionBdu();
			pst=connectionBdu.prepareStatement(select);
			pst.setInt(1, idStabGisaVecchio);
			pst.setInt(2, idStabGisaNuovo);
			
			System.out.println("VALIDAZIONE VARIAZIONE IN BDU -->"+pst.toString());
			pst.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionBdu.close();
		}
		
			
	}




public void aggiornaIdStabilimentoGisaBdu(int idStabGisa,int idRelStablpBdu) throws SQLException {
		
	Connection connectionBdu =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.aggiorna_chiave_esterna_bdu(?,?);";
		
		try {
			connectionBdu =GestoreConnessioni.getConnectionBdu();
			pst=connectionBdu.prepareStatement(select);
			pst.setInt(1, idStabGisa);
			pst.setInt(2, idRelStablpBdu);
			
			System.out.println("VALIDAZIONE CESSAZIONE IN BDU -->"+pst.toString());
			pst.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionBdu.close();
		}
		
			
	}


public void aggiornaDatiEstesi(int idStabGisa) throws SQLException {
	
	Connection connectionBdu =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.aggiorna_info_canile(? );";
		
		try {
			connectionBdu =GestoreConnessioni.getConnectionBdu();
			pst=connectionBdu.prepareStatement(select);
			pst.setInt(1, idStabGisa);
			
			System.out.println("PROPAGAZIONE DATI ESTESI IN BDU -->"+pst.toString());
			pst.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionBdu.close();
		}
		
			
	}


public boolean verificaCessazioneInBdu(int idStabGisa) throws SQLException {
	
	Connection connectionBdu =null ; 
	boolean continuaValidazione  =true ;
		PreparedStatement pst = null;
		String select ="select * from public_functions.verifica_animali_cessazione_operatore_bdu(?);";
		
		try {
			connectionBdu =GestoreConnessioni.getConnectionBdu();
			pst=connectionBdu.prepareStatement(select);
			pst.setInt(1, idStabGisa);
			
			System.out.println("VERIFICA PRESENZAANIMALI IN BD U ->"+pst.toString());
			ResultSet rs= pst.executeQuery();
			if (rs.next())
			{
				continuaValidazione=rs.getBoolean(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionBdu.close();
		}
		System.out.println("VERIFICA PRESENZAANIMALI IN BD U continuaValidazione ->"+continuaValidazione);

		return continuaValidazione;
			
	}
	
public void inserisciNuovaSciaBdu(int idStabGisa) throws SQLException {
		
	Connection connectionBdu =null ; 

		PreparedStatement pst = null;
		String select ="select * from public_functions.suap_inserisci_canile_bdu(?);";
		
		try {
			connectionBdu =GestoreConnessioni.getConnectionBdu();
			pst=connectionBdu.prepareStatement(select);
			pst.setInt(1, idStabGisa);
			pst.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			connectionBdu.close();
		}
		
			
	}
	
public void inserisciOperatoreBdu(Connection db, String query) {
		
		PreparedStatement pst = null;	
		int id_stab_bdu=-1;
		try {
			pst=db.prepareStatement(query);
			System.out.println("query su bdu:"+pst.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				id_stab_bdu=rs.getInt("suap_inserisci_canile_bdu");
			}
			System.out.println("idstab:"+id_stab_bdu);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	private static void printInformazioniConnessioneGisa(String dbname, String host, String user, String pwd, String query){
		
		System.out.println("Aggiornamento GISA --- DBHOST: " +host + " DBNAME: " + dbname + " UserDB: " + user + " PWD: " + pwd);
		System.out.println("Aggiornamento GISA --- QUERY ESECUZIONE: " +query);
		
	}
	
	public static String cessazioneAutomaticaBdu(int orgIdC, Timestamp dataCessazione, String noteCessazione) throws SQLException {
		String output = "";
		Connection connectionBdu =null ; 

			PreparedStatement pst = null;
			String select ="select * from public_functions.bdu_cessazione_automatica(?,?,?);";
			
			try {
				connectionBdu =GestoreConnessioni.getConnectionBdu();
				pst=connectionBdu.prepareStatement(select);
				int i = 0;
				pst.setInt(++i, orgIdC);
				pst.setTimestamp(++i, dataCessazione);
				pst.setString(++i, noteCessazione);
				System.out.println("CESSAZIONE AUTOMATICA SU BDU ---> "+pst.toString());
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()){
					output = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				connectionBdu.close();
			}
			return output;	
		}

	public void variazioneOperatoreBduSciaNoCessazione(int idStabilimentoTrovato, Stabilimento richiesta, int idUtente) throws SQLException {
		String output = "";
		Connection connectionBdu =null ; 

			PreparedStatement pst = null;
			String select ="select * from public_functions.suap_variazione_titolarita(?,?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?, ?, ?, ?, ?,?, ?);";
			
			try {
				connectionBdu =GestoreConnessioni.getConnectionBdu();
				pst=connectionBdu.prepareStatement(select);
				int i = 0;
				pst.setString(++i, richiesta.getOperatore().getPartitaIva());
				pst.setString(++i, richiesta.getOperatore().getCodFiscale());
				pst.setString(++i, richiesta.getOperatore().getRagioneSociale());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getNome());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getCognome());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getCodFiscale());
				pst.setTimestamp(++i, richiesta.getOperatore().getRappLegale().getDataNascita());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getSesso());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getComuneNascita());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getProvinciaNascita());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getIndirizzo().getVia());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getIndirizzo().getCivico());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getIndirizzo().getCap());
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getIndirizzo().getNazione());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getDescrizione_provincia());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getDescrizioneComune());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getVia());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getCivico());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getCap());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getNazione());
				pst.setString(++i, richiesta.getSedeOperativa().getDescrizione_provincia());
				pst.setString(++i, richiesta.getSedeOperativa().getDescrizioneComune());
				pst.setString(++i, richiesta.getSedeOperativa().getVia());
				pst.setString(++i, richiesta.getSedeOperativa().getCivico());
				pst.setString(++i, richiesta.getSedeOperativa().getCap());
				pst.setString(++i, richiesta.getSedeOperativa().getNazione());
				pst.setInt(++i, richiesta.getIdAsl());
				pst.setInt(++i, idUtente);
				pst.setString(++i, richiesta.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getDescrizioneToponimo());
				pst.setString(++i, richiesta.getOperatore().getSedeLegale().getDescrizioneToponimo());
				pst.setInt(++i, idStabilimentoTrovato);
				System.out.println("VARIAZIONE SU BDU ---> "+pst.toString());
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()){
					output = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				connectionBdu.close();
			}
		}
	
	public void variazioneOperatoreBduGestioneAnagrafica(int idStabilimentoTrovato, org.aspcfs.modules.opu.base.Stabilimento stabin, int idUtente) throws SQLException {
		String output = "";
		Connection connectionBdu =null ; 

			PreparedStatement pst = null;
			String select ="select * from public_functions.suap_variazione_titolarita(?,?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?, ?, ?, ?, ?, ?, ?,?, ?);";
			
			try {
				connectionBdu =GestoreConnessioni.getConnectionBdu();
				pst=connectionBdu.prepareStatement(select);
				int i = 0;
				pst.setString(++i, stabin.getOperatore().getPartitaIva());
				pst.setString(++i, stabin.getOperatore().getCodFiscale());
				pst.setString(++i, stabin.getOperatore().getRagioneSociale());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getNome());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getCognome());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getCodFiscale());
				pst.setTimestamp(++i, stabin.getOperatore().getRappLegale().getDataNascita());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getSesso());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getComuneNascita());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getProvinciaNascita());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getIndirizzo().getVia());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getIndirizzo().getCivico());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getIndirizzo().getCap());
				pst.setString(++i, stabin.getOperatore().getRappLegale().getIndirizzo().getNazione());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getDescrizione_provincia());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getDescrizioneComune());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getVia());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getCivico());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getCap());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getNazione());
				pst.setString(++i, stabin.getSedeOperativa().getDescrizione_provincia());
				pst.setString(++i, stabin.getSedeOperativa().getDescrizioneComune());
				pst.setString(++i, stabin.getSedeOperativa().getVia());
				pst.setString(++i, stabin.getSedeOperativa().getCivico());
				pst.setString(++i, stabin.getSedeOperativa().getCap());
				pst.setString(++i, stabin.getSedeOperativa().getNazione());
				pst.setInt(++i, stabin.getIdAsl());
				pst.setInt(++i, idUtente);
				pst.setString(++i, stabin.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getDescrizioneToponimo());
				pst.setString(++i, stabin.getOperatore().getSedeLegale().getDescrizioneToponimo());
				pst.setInt(++i, idStabilimentoTrovato);
				System.out.println("VARIAZIONE SU BDU ---> "+pst.toString());
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()){
					output = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				connectionBdu.close();
			}
		}
	
	public void variazioneSuperficie(int idStabilimentoTrovato, Stabilimento richiesta, int idUtente) throws SQLException {
		String output = "";
		Connection connectionBdu =null ; 

			PreparedStatement pst = null;
			String select ="select * from public_functions.update_superficie_canile(?,?,?);";
			
			try {
				connectionBdu =GestoreConnessioni.getConnectionBdu();
				pst=connectionBdu.prepareStatement(select);
				int i = 0;
				pst.setInt(++i, idStabilimentoTrovato);
				pst.setInt(++i, richiesta.getSuperficie());
				pst.setInt(++i, idUtente);
				System.out.println("VARIAZIONE SU BDU ---> "+pst.toString());
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()){
					output = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				connectionBdu.close();
			}
		}
	
	public void variazioneSuperficie(int idStabilimentoTrovato, int superficie, int idUtente) throws SQLException {
		String output = "";
		Connection connectionBdu =null ; 

			PreparedStatement pst = null;
			String select ="select * from public_functions.update_superficie_canile(?,?,?);";
			
			try {
				connectionBdu =GestoreConnessioni.getConnectionBdu();
				pst=connectionBdu.prepareStatement(select);
				int i = 0;
				pst.setInt(++i, idStabilimentoTrovato);
				pst.setInt(++i, superficie);
				pst.setInt(++i, idUtente);
				System.out.println("VARIAZIONE SU BDU ---> "+pst.toString());
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()){
					output = rs.getString(1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				connectionBdu.close();
			}
		}
}
