/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import it.izs.ws.WsPost;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.bloccocu.base.EventoBloccoCu;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.dpat2019.base.Dpat;
import org.aspcfs.modules.dpat2019.base.DpatAttivita;
import org.aspcfs.modules.dpat2019.base.DpatIndicatore;
import org.aspcfs.modules.dpat2019.base.DpatPiano;
import org.aspcfs.modules.dpat2019.base.DpatSezione;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo;
import org.aspcfs.modules.dpat2019.base.EsitoCall;
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBean;
import org.aspcfs.modules.dpat2019.base.DpatSezioneNewBean;
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBeanInterface;
import org.aspcfs.modules.dpat2019.base.DpatSezioneNewBeanInterface;
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBeanPreCong;
import org.aspcfs.modules.dpat2019.base.DpatSezioneNewBeanPreCong;
import org.aspcfs.modules.gestioneanagrafica.base.AnagraficaCodiceSINVSARecord;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.lineeattivita.base.RelAtecoLineeAttivita;
import org.aspcfs.modules.login.actions.Login;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.base.Tampone;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

import com.darkhorseventures.database.ConnectionElement;

import ext.aspcfs.modules.apicolture.actions.CfUtil;

public class PopolaCombo {

	private static final int GRUPPO_BOVINI_BUFANILI = 1;
	private static final int GRUPPO_OVINI_CAPRINI = 2;
	private static final int GRUPPO_SUINI = 3;
	private static final int GRUPPO_AVICOLI = 4;
	private static final int GRUPPO_EQUIDI = 5;
	private static final int GRUPPO_API = 7;
	private static final int GRUPPO_CONIGLI = 8;

	private static final int CAMPIONE = 2;
	private static final int TAMPONE = 7;
	private static final int NON_CONFORMITA = 8;

	static Logger logger = Logger.getLogger("MainLogger");


	public static void scriviStoricoConvergenza(Connection db,int id_cu,int id_anagrafica,int id_linea,int tipo_anagrafica,int eseguitoDa) throws SQLException
	{

		PreparedStatement pst = db.prepareStatement("INSERT INTO opu_storico_spostamenti_ccu(id_cu,id_anagrafica,id_linea,tipo_anagrafica,data_spostamento,eseguito_da) values ( ?,?,?,?,current_timestamp,?)");
		pst.setInt(1, id_cu);
		pst.setInt(2, id_anagrafica);
		pst.setInt(3, id_linea);
		pst.setInt(4, tipo_anagrafica);
		pst.setInt(5, eseguitoDa);
		pst.execute();


	}




	public static boolean verificaComuneAsl(int comune,int idAsl)
	{

		String sql = "select codiceistatasl::int from comuni1 where id = ?";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, comune);
			rs=pst.executeQuery();
			int idAslComune = -1 ;
			if(rs.next())
				idAslComune = rs.getInt(1);

			if(idAslComune==idAsl || comune<=0)
			{
				return true ;
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return false;



	}


	public static Object[] getValoriComboNorme(boolean flag) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {

				String select = "select code,description from opu_lookup_norme_master_list where enabled=true and flag_vecchia_gestione= "+flag;

				pst = db.prepareStatement(select);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}


	public static boolean propagazioneInBdu(int idStabilimento)
	{
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean bloccaErrataCorrige = false;
		try {
			db = GestoreConnessioni.getConnection();
			String sql = "select id_linea_bdu from opu_operatori_denormalizzati_view_bdu where   id_stabilimento =?";

			pst = db.prepareStatement(sql);
			pst.setInt(1, idStabilimento);
			rs = pst.executeQuery();
			while (rs.next())
			{
				if(rs.getInt(1)>0)
				{
					bloccaErrataCorrige=true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return bloccaErrataCorrige;

	}

	public static void convergiFrom852To852(RicercaOpu r1,RicercaOpu r2,Connection db) throws SQLException
	{


		String sqlAggiornaCu1 = "update linee_attivita_controlli_ufficiali set id_linea_attivita = ?,flag_spostamento=true where id_linea_attivita = ? and trashed_date is null;";

		/**
		 * spostamento della linea di attivita sull'anagrafica di
		 * destinazione se ci sono controlli
		 */

		String sqlAggiornaCu2 = "insert into la_imprese_linee_attivita (id,org_id,id_rel_ateco_attivita,primario,entered,entered_by,modified,modified_by,flag_spostamento) "
				+ " ( select ?,?,id_rel_ateco_attivita,primario,entered,entered_by,modified,modified_by,true from "
				+ " la_imprese_linee_attivita where id = ?) ";
		PreparedStatement pst = null ;

		boolean trovato = false;
		for (LineaProduttiva linea : r1.getListaControlliPerLinea()) {
			trovato = false;
			if (r2.getListaControlliPerLinea().size() > 0) {

				for (int i = 0; i < r2.getListaControlliPerLinea().size(); i++) {

					LineaProduttiva linea2 = r2.getListaControlliPerLinea().get(i);

					if (linea.getId() == linea2.getId()) { /*se la linea di attivita dell'anagrafica di partenza viene trovata in quella di destinazione aggiorno solo la linea di attivita sui controlli*/
						trovato = true;

						pst = db.prepareStatement(sqlAggiornaCu1);

						pst.setInt(1, linea2.getId_rel_stab_lp());
						pst.setInt(2, linea.getId_rel_stab_lp());
						pst.execute();

						break;
					}
				}

				if (trovato == false) { /*se non viene trovata nell'anagrafica di destinazione la aggiungo*/
					pst = db.prepareStatement(sqlAggiornaCu2);
					int id = DatabaseUtils.getNextSeq(db, "la_imprese_linee_attivita_id_seq");	


					pst.setInt(1, id);
					pst.setInt(2, r2.getRiferimentoId());
					pst.setInt(3, linea.getId_rel_stab_lp());
					pst.execute();

					pst = db.prepareStatement(sqlAggiornaCu1);
					pst.setInt(1, id);
					pst.setInt(2, linea.getId_rel_stab_lp());
					pst.execute();


				}

			}
		}


		String ss = "update organization set codice_ufficiale_esistente = coalesce(codice_ufficiale_esistente,'') || ';'|| ? where org_id = ?";
		pst = db.prepareStatement(ss);
		pst.setString(1, r1.getNumeroRegistrazione());
		pst.setInt(2, r2.getRiferimentoId());
		pst.execute();


	}


	public static void convergiFromAllToOpu(RicercaOpu r1,RicercaOpu r2,Connection db) throws SQLException
	{

		/**
		 * spostamento delle linee di attivita suicontrolli verso la nuova
		 * anagrafica
		 */
		String sqlAggiornaCu1 = "update linee_attivita_controlli_ufficiali set id_linea_attivita = ?,flag_spostamento=true where id_linea_attivita = ? and trashed_date is null;";

		/**
		 * spostamento della linea di attivita sull'anagrafica di
		 * destinazione se ci sono controlli
		 */

		String sqlAggiornaCu2 = "INSERT INTO opu_relazione_stabilimento_linee_produttive (id, id_stabilimento,id_linea_produttiva,data_inizio,data_fine,stato,tipo_attivita_produttiva,primario,modified,modifiedby,entered, enteredby, enabled,numero_registrazione,codice_ufficiale_esistente,id_vecchia_linea,num_protocollo,codice_nazionale,pregresso_o_import,note)"
				+ " ( select ?,?,id_linea_produttiva,data_inizio,data_fine,stato,tipo_attivita_produttiva,primario,modified,modifiedby,entered, enteredby, enabled,?,codice_ufficiale_esistente,id_vecchia_linea,num_protocollo,codice_nazionale,true,note"
				+ " from opu_relazione_stabilimento_linee_produttive where id =? )";

		PreparedStatement pst = null ;

		boolean trovato = false;
		for (LineaProduttiva linea : r1.getListaControlliPerLinea()) {
			trovato = false;
			if (r2.getListaControlliPerLinea().size() > 0) {

				for (int i = 0; i < r2.getListaControlliPerLinea().size(); i++) {

					LineaProduttiva linea2 = r2.getListaControlliPerLinea().get(i);

					if (linea.getId() == linea2.getId()) { /*se la linea di attivita dell'anagrafica di partenza viene trovata in quella di destinazione aggiorno solo la linea di attivita sui controlli*/
						trovato = true;

						if(r1.getTipologia()==999)
						{

							pst = db.prepareStatement(sqlAggiornaCu1);
							pst.setInt(1, linea2.getId_rel_stab_lp());
							pst.setInt(2, linea.getId_rel_stab_lp());
							pst.execute();




						}
						else
						{

							String insetLineaCu="insert into linee_attivita_controlli_ufficiali ( id_controllo_ufficiale,id_linea_attivita,flag_spostamento) "
									+ " (select id_controllo_ufficiale,?,true from linee_attivita_controlli_ufficiali where id_linea_attivita =? and trashed_date is null)";

							pst = db.prepareStatement(insetLineaCu);
							pst.setInt(1, linea2.getId_rel_stab_lp());
							pst.setInt(2, linea.getId_rel_stab_lp());
							pst.execute();

						}

						String ss = "update opu_relazione_stabilimento_linee_produttive set codice_ufficiale_esistente = codice_ufficiale_esistente || ';'|| ? where id = ?";
						pst = db.prepareStatement(ss);
						pst.setString(1, r1.getNumeroRegistrazione());
						pst.setInt(2, linea2.getId_rel_stab_lp());
						pst.execute();

						break;
					}
				}

				if (trovato == false) { /*se non viene trovata nell'anagrafica di destinazione la aggiungo*/


					int id = DatabaseUtils.getNextSeq(db, "opu_relazione_stabilimento_linee_produttive_id_seq");		


					if(r1.getTipologia()==999)
					{

						String numRegLinea = "" ;
						if (r2.getNumeroRegistrazione()!=null)
							numRegLinea= r2.getNumeroRegistrazione() + org.aspcfs.utils.StringUtils.zeroPad(linea.getProgressivoLineaAttivita(db, r2.getNumeroRegistrazione()) ,3);	 


						pst = db.prepareStatement(sqlAggiornaCu2);
						pst.setInt(1, id );
						pst.setInt(2, r2.getRiferimentoId());
						pst.setString(3, numRegLinea);

						pst.setInt(4, linea.getId_rel_stab_lp());
						pst.execute();
					}
					else
					{

						String sqlAggiornaCuOld = "" ;
//						sqlAggiornaCuOld="INSERT INTO opu_relazione_stabilimento_linee_produttive (id, id_stabilimento,id_linea_produttiva , stato ,modified,modifiedby,entered, enteredby,enabled,numero_registrazione,pregresso_o_import) "+ 
//								"( "+
//								"select ?,?,?, 0,entered, entered_by,entered, entered_by,true,?,true "+
//								"from la_imprese_linee_attivita  "+
//								"where id =?  "+
//								")";
						
						sqlAggiornaCuOld="INSERT INTO opu_relazione_stabilimento_linee_produttive (id, id_stabilimento,id_linea_produttiva , stato ,modified,modifiedby,entered, enteredby,enabled,numero_registrazione,pregresso_o_import) "+ 
						" values ( "+
						" ?,?,?, 0,now(), -1,now(), -1,true,?,true "+
						")";
						
						String numRegLinea = "" ;
						if (r2.getNumeroRegistrazione()!=null)
							numRegLinea= r2.getNumeroRegistrazione() + org.aspcfs.utils.StringUtils.zeroPad(linea.getProgressivoLineaAttivita(db, r2.getNumeroRegistrazione()) ,3);	 

						pst = db.prepareStatement(sqlAggiornaCuOld);

						pst.setInt(1, id );
						pst.setInt(2, r2.getRiferimentoId());
						pst.setInt(3, linea.getId());
						pst.setString(4, numRegLinea);
						pst.execute();

					}

					String ss = "update opu_relazione_stabilimento_linee_produttive set codice_ufficiale_esistente = coalesce(codice_ufficiale_esistente,'') || ';'|| ? where id = ?";
					pst = db.prepareStatement(ss);
					pst.setString(1, r1.getNumeroRegistrazione());
					pst.setInt(2, id);
					pst.execute();


					if(r1.getTipologia()==999)
					{
						pst = db.prepareStatement(sqlAggiornaCu1);
						pst.setInt(1, id);
						pst.setInt(2, linea.getId_rel_stab_lp());
						pst.execute();
					}
					else
					{
						String insetLineaCu="insert into linee_attivita_controlli_ufficiali ( id_controllo_ufficiale,id_linea_attivita,flag_spostamento) "
								+ " (select id_controllo_ufficiale,?,true from linee_attivita_controlli_ufficiali where id_linea_attivita =? and trashed_date is null)";

						pst = db.prepareStatement(insetLineaCu);
						pst.setInt(1, id);
						pst.setInt(2, linea.getId_rel_stab_lp());
						pst.execute();
					}

				}

			}
		}

		PreparedStatement pstDisabilitaVecchieIstanze = db.prepareStatement("update opu_relazione_stabilimento_linee_produttive set enabled = ?, note_internal_use_hd_only = ? where id_stabilimento = ?");
		pstDisabilitaVecchieIstanze.setBoolean(1, false);
		pstDisabilitaVecchieIstanze.setString(2, "Disabilitata a seguito di convergenza");
		pstDisabilitaVecchieIstanze.setInt(3, r1.getRiferimentoId());
		pstDisabilitaVecchieIstanze.executeUpdate();

	}



	public static void convergiCategoriaRischio(RicercaOpu r1,RicercaOpu r2,int idUtente , String note ,Connection db) throws SQLException
	{

		String sql = "";
		PreparedStatement pst = null;
		if (r1.getCategoriaRischio() > r2.getCategoriaRischio()) {
			if (r2.getRiferimentoIdNomeCol().equals("org_id"))
				sql = "update organization set categoria_rischio =?,prossimo_controllo=? where "
						+ r2.getRiferimentoIdNomeCol() + "=?";
			else
				sql = "update opu_stabilimento set categoria_rischio =?,data_prossimo_controllo=? where id=?;";

			pst = db.prepareStatement(sql);
			pst.setInt(1, r1.getCategoriaRischio());
			pst.setTimestamp(2, r1.getDataProssimoControllo());
			pst.setInt(3, r2.getRiferimentoId());
			pst.execute();

		}

		if (r1.getRiferimentoIdNomeCol().equals("org_id")) {
			sql = "update organization set trashed_date = current_date ,modifiedby=?,note_hd=? where org_id =?";
		} else {
			sql = "update opu_stabilimento set trashed_date = current_date ,modified_by=?,notes_hd=? where id =?";
		}

		pst = db.prepareStatement(sql);
		pst.setInt(1, idUtente);
		pst.setString(2, note);
		pst.setInt(3, r1.getRiferimentoId());
		System.out.println("trashed  anagrafica " + pst.toString());
		pst.execute();


	}

	public static void convergiControlli(RicercaOpu r1 , RicercaOpu r2,int idUtente,Connection db) throws SQLException
	{
		String sql = "update ticket set data_spostamento = current_timestamp,spostati_da = ? ,riferimento_id_anagrafica_old ="
				+ r1.getRiferimentoId()
				+ ",riferimenti_id_anagrafica_nome_old="
				+ r1.getRiferimentoIdNomeCol()
				+ ","
				+ r2.getRiferimentoIdNomeCol()
				+ "="
				+ r2.getRiferimentoId()
				+ " where "
				+ r1.getRiferimentoIdNomeCol()
				+ " ="
				+ r1.getRiferimentoId();

		PreparedStatement pst = null;
		pst = db.prepareStatement(sql);
		pst.setInt(1, idUtente);
		pst.execute();
	}
	public static boolean convergiAnagrafiche(RicercaOpu r1,RicercaOpu r2, int idUtente, String note) {

		/**
		 * quety di spostamento dei controlli
		 */


		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = null;
			String storico = "insert into  opu_storico_spostamenti_ccu (id_cu,id_stabilimento_precedente,id_rel_stab_lp_prec,id_master_list_prec,id_stabilimento_dopo,id_rel_stab_lp_dopo,id_master_list_dopo,data,esguito_da,tipologia_operatore_partenza,tipologia_operatore_destinazione) values (?,?,?,?,?,?,?,current_Timestamp,?,?,?)";
			PreparedStatement pstStorico = db.prepareStatement(storico);




			/**
			 * kivale = idCU Valore = Hashmap Kiave = nome Campo Valore = valore
			 * Campo
			 */
			HashMap<Integer, HashMap<String, Integer>> listaValoriCuOrigine = new HashMap<Integer, HashMap<String, Integer>>();

			String selCu = "";

			if(r1.getTipologia()==999)
				selCu ="select idcontrollo,id_stabilimento,id_rel_stab_lp_out,id_linea_master_list_out from dbi_get_controlli_ufficiali_su_linee_produttive(?)";
			else
				if(r1.getTipologia()==1)
					selCu ="select idcontrollo,id_stabilimento,id_rel_stab_lp_out,id_linea_master_list_out from dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(?)";


			PreparedStatement pstCu = db.prepareStatement(selCu);
			pstCu.setInt(1, r1.getRiferimentoId());
			ResultSet rsCu = pstCu.executeQuery();

			while (rsCu.next()) {
				int idCu = rsCu.getInt("idcontrollo");
				int idStab = rsCu.getInt("id_stabilimento");
				int idrelStabLp = rsCu.getInt("id_rel_stab_lp_out");

				scriviStoricoConvergenza(db, idCu, idStab, idrelStabLp, r1.getTipologia(),idUtente);
			}




			if (  r2.getTipologia()==999) {

				convergiFromAllToOpu(r1, r2, db);


			}
			else
				if (r1.getTipologia() == 1 && r2.getTipologia()==1) {

					convergiFrom852To852(r1, r2, db);
				}


			convergiControlli(r1, r2, idUtente, db);

			convergiCategoriaRischio(r1, r2, idUtente, note, db);
			if (  r1.getTipologia()==999) {

				RicercheAnagraficheTab.inserOpu(db, r1.getRiferimentoId());


			}


		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return true;

	}

	public EsitoCall salvaCoefficiente(int idIndicatore, String coefficiente) {

		String sql = "update lookup_piano_monitoraggio_configuratore set coefficiente = ? where code = ?";
		Connection db = null;
		EsitoCall json = new EsitoCall();
		try {
			db = GestoreConnessioni.getConnection();

			// double valore =Double.parseDouble(coefficiente);
			// if (valore<0 || valore>1)
			// {
			// json.setEsito("ko") ;
			// json.setDescrizione("Il valore deve essere compreso tr 0 e 1");
			// return json;
			// }
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setDouble(1, Double.parseDouble(coefficiente));
			pst.setInt(2, idIndicatore);
			pst.execute();

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			json.setEsito("ko");
			json.setDescrizione("Errore Generico");

			return json;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		json.setEsito("ok");
		json.setDescrizione("Salvataggio Avvenuto Con Successo");

		return json;

	}

	public boolean aggiornaCunMolluschi(int orgId, String cun) {

		String sql = "update organization set cun = ? where org_id = ?";
		Connection db = null;
		EsitoCall json = new EsitoCall();
		try {
			db = GestoreConnessioni.getConnection();

			// double valore =Double.parseDouble(coefficiente);
			// if (valore<0 || valore>1)
			// {
			// json.setEsito("ko") ;
			// json.setDescrizione("Il valore deve essere compreso tr 0 e 1");
			// return json;
			// }
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, cun);
			pst.setInt(2, orgId);
			pst.execute();
			return true;

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {

			return false;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

	}

	public boolean rifiutaSpecchioAcqueo(String dataIn, int idMotivazione, int orgId) {

		int CLASSE_RFIUTATA = 13;
		String sql = "update organization set molluschi_data_rifiuto = ?,molluschi_motivazione_rifiuto=?,provvedimenti_restrittivi=? where org_id = ?";
		Connection db = null;
		EsitoCall json = new EsitoCall();
		try {
			db = GestoreConnessioni.getConnection();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Timestamp data = new Timestamp(sdf.parse(dataIn).getTime());

			PreparedStatement pst = db.prepareStatement(sql);
			pst.setTimestamp(1, data);
			pst.setInt(2, idMotivazione);
			pst.setInt(3, CLASSE_RFIUTATA);
			pst.setInt(4, orgId);
			pst.execute();

			return true;

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {

			return false;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

	}

	public boolean verificaEsistenzaDiffida(int idNorma, String idCu, int org_id, int id_stab, int idApiario, int altId,
			int idDiffida) {
		boolean toRet = false;
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (id_stab == 0)
				id_stab = -1;
			/*
			 * String sql=
			 * "select distinct diffide.id_Controllo_ufficiale from norme_violate_sanzioni "
			 * +
			 * "join ticket diffide on diffide.ticketid = idticket and tipologia=11 "
			 * +
			 * "join ticket cu on cu.id_controllo_ufficiale=diffide.id_controllo_ufficiale and cu.tipologia=3 "
			 * +
			 * "left join ticket nc on nc.ticketid=diffide.id_nonconformita and nc.tipologia=8 "
			 * +
			 * "where diffide.id_Controllo_ufficiale =? and diffide.id_nonconformita>0 and  norme_violate_sanzioni.id_norma=? "
			 * +
			 * "and diffide.ticketid not in (?) and diffide.trashed_date is null and cu.trashed_date is null  and nc.trashed_date is null "
			 * ;
			 */
			String sql = "select * from norme_violate_sanzioni n"
					+ " join ticket t on n.idticket = t.ticketid and t.tipologia = 11"
					+ "	join ticket cu on cu.ticketid = t.id_controllo_ufficiale::int and cu.tipologia = 3 "
					+ " join ticket nc on nc.ticketid = t.id_nonconformita and nc.tipologia = 8 where 1=1 ";

			if (org_id > 0)
				sql += " and n.org_id = ? ";
			else if (id_stab > 0)
				sql += " and t.id_stabilimento = ? ";
			else if (idApiario > 0)
				sql += " and t.id_apiario = ? ";
			else if (altId > 0)
				sql += " and t.alt_id = ? ";

			// + "//n.stato_diffida = 0 and "
			sql += " and t.data_chiusura is null ";
			sql += " and current_timestamp<= (cu.assigned_date + interval '5 years') and n.id_norma = ? and t.id_nonconformita >0 and t.trashed_date is null and cu.trashed_date is null and nc.trashed_date is null and n.stato_diffida not in (1) ";

			if (idDiffida>0)
				sql += "and t.ticketid not in (?)";
			
			PreparedStatement pst = db.prepareStatement(sql);
			// pst.setString(1, idCu);
			int i = 0;
			if (org_id > 0)
				pst.setInt(++i, org_id);
			else if (id_stab > 0)
				pst.setInt(++i, id_stab);
			else if (idApiario > 0)
				pst.setInt(++i, idApiario);
			else if (altId > 0)
				pst.setInt(++i, altId);


			pst.setInt(++i, idNorma);
			
			if (idDiffida>0)
				pst.setInt(++i, idDiffida);
			
			// pst.setInt(3,idDiffida);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				toRet = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}

	public boolean verificaNormaInDiffidaOSA(int idNorma, int orgId, int idStab, int idApiario, int idDiffida) {
		boolean toRet = false;
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (idStab == 0)
				idStab = -1;
			String sql = "select distinct diffide.id_Controllo_ufficiale from norme_violate_sanzioni "
					+ "join ticket diffide on diffide.ticketid = idticket and tipologia=11 "
					+ "join ticket cu on cu.id_controllo_ufficiale=diffide.id_controllo_ufficiale and cu.tipologia=3 "
					+ "left join ticket nc on nc.ticketid=diffide.id_nonconformita and nc.tipologia=8 " + "where ";

			if (orgId > 0)
				sql += " diffide.org_id = ? ";
			else if (idStab > 0)
				sql += " diffide.id_stabilimento = ? ";
			else if (idApiario > 0)
				sql += " diffide.id_apiario = ? ";

			sql += " and diffide.data_chiusura is null and current_timestamp<= (cu.assigned_date + interval '5 years') and diffide.id_nonconformita>0 and  norme_violate_sanzioni.id_norma=? and norme_violate_sanzioni.stato_diffida = 0 "
					+ "and diffide.ticketid not in (?) and diffide.trashed_date is null and cu.trashed_date is null  and nc.trashed_date is null ";
			PreparedStatement pst = db.prepareStatement(sql);

			if (orgId > 0)
				pst.setInt(1, orgId);
			else if (idStab > 0)
				pst.setInt(1, idStab);
			else if (idApiario > 0)
				pst.setInt(1, idApiario);

			pst.setInt(2, idNorma);
			pst.setInt(3, idDiffida);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				toRet = true;

		} catch (LoginRequiredException e) {

			throw e;
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}

	public boolean verificaNormaInDiffidaAlt(int idNorma, int altId, int idDiffida) {
		boolean toRet = false;
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();

			String sql = "select distinct diffide.id_Controllo_ufficiale from norme_violate_sanzioni "
					+ "join ticket diffide on diffide.ticketid = idticket and tipologia=11 "
					+ "join ticket cu on cu.id_controllo_ufficiale=diffide.id_controllo_ufficiale and cu.tipologia=3 "
					+ "left join ticket nc on nc.ticketid=diffide.id_nonconformita and nc.tipologia=8 " + "where ";

			sql += " diffide.alt_id = ? ";

			sql += " and diffide.data_chiusura is null and current_timestamp<= (cu.assigned_date + interval '5 years') and diffide.id_nonconformita>0 and  norme_violate_sanzioni.id_norma=? and norme_violate_sanzioni.stato_diffida = 0 "
					+ "and diffide.ticketid not in (?) and diffide.trashed_date is null and cu.trashed_date is null  and nc.trashed_date is null ";
			PreparedStatement pst = db.prepareStatement(sql);

			pst.setInt(1, altId);

			pst.setInt(2, idNorma);
			pst.setInt(3, idDiffida);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				toRet = true;

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}

	public RispostaDwrCodicePiano getCodiceTipoPianoFromCodiceInterno(int value) {
		RispostaDwrCodicePiano toRet = new RispostaDwrCodicePiano();
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			toRet = ControlliUfficialiUtil.getCodicePianoMonitoraggioFromCodiceInterno(db, "lookup_piano_monitoraggio",
					value);
			System.out.println(toRet.getFlagCondizionalita());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}

	public RispostaDwrCodicePiano getCodiceInternoTipoPiano(int value) {
		RispostaDwrCodicePiano toRet = new RispostaDwrCodicePiano();
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			toRet = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", value);
			System.out.println(toRet.getFlagCondizionalita());
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}

	public RispostaDwrCodicePiano getCodiceInternoTipoIspezione(int value) {
		RispostaDwrCodicePiano toRet = new RispostaDwrCodicePiano();
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			toRet = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", value);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}

	public boolean verificaNormaInDiffida(String value) {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean viewdiffida = true;
		try {
			db = GestoreConnessioni.getConnection();

			pst = db.prepareStatement("select view_diffida from lookup_norme where code =" + value);
			rs = pst.executeQuery();
			if (rs.next()) {
				viewdiffida = rs.getBoolean("view_diffida");
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return viewdiffida;
	}

	public SoggettoFisico verificaSoggetto(String cf) throws SQLException {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		SoggettoFisico soggettoEsistente = null;
		try {
			db = GestoreConnessioni.getConnection();

			soggettoEsistente = new SoggettoFisico(cf, db);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		/* Metodo richiamato sul soggetto fisico proveniente dalla request */
		/**/

		return soggettoEsistente;

	}

	public Operatore verificaImpresa(String pIva) throws SQLException {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Operatore operatore = null;
		try {
			db = GestoreConnessioni.getConnection();

			operatore = new Operatore();
			operatore.queryRecordOperatorePartitaIva(db, pIva.trim());

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		/* Metodo richiamato sul soggetto fisico proveniente dalla request */
		/**/

		return operatore;

	}

	public static boolean verificaControlliSuStruttura(int idStruttura, String dataValidita) {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<OiaNodo> lista = new ArrayList<OiaNodo>();
		try {
			db = GestoreConnessioni.getConnection();

			Timestamp time = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			time = new Timestamp(sdf.parse(dataValidita).getTime());

			String sql = "select count(ticketid) from ticket cu left join tipocontrolloufficialeimprese tcu on cu.ticketid = tcu.idcontrollo left join unita_operative_controllo uo on cu.ticketid =uo.id_controllo"
					+ " left join strutture_controllate_autorita_competenti ac on cu.ticketid =ac.id_controllo where cu.assigned_date >=? and (ac.id_struttura =? or uo.id_unita_operativa=? or ( tcu.enabled and tcu.id_unita_operativa =?)) and cu.trashed_date is null ";
			pst = db.prepareStatement(sql);

			pst.setTimestamp(1, time);
			pst.setInt(2, idStruttura);
			pst.setInt(3, idStruttura);
			pst.setInt(4, idStruttura);

			rs = pst.executeQuery();
			if (rs.next())
				if (rs.getInt(1) > 0) {
					return true;
				}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return false;
	}

	public static OiaNodo[] getAreeStruttureComplesse(int idAsl, int anno, int idStruttura) throws SQLException {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<OiaNodo> lista = new ArrayList<OiaNodo>();
		try {
			db = GestoreConnessioni.getConnection();

			String sql = "select dpat_strutture_asl.*,tipooia.description as descrizione_tipologia_struttura "
					+ "from "
					+ "dpat_strutture_asl "
					+ "LEFT JOIN lookup_tipologia_nodo_oia tipooia ON dpat_strutture_asl.tipologia_struttura = tipooia.code "

					+ " where tipologia_struttura in( 13,14) and id_asl = ? "
					+ " and id_strumento_calcolo in (select id from  "
					+ "  dpat_strumento_calcolo where id_asl = ? and anno=? ) and disabilitato=false";

			if (idStruttura > 0)
				sql += " and dpat_strutture_asl.id = " + idStruttura;
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, idAsl);
			pst.setInt(3, anno);
			rs = pst.executeQuery();
			while (rs.next()) {
				OiaNodo n = new OiaNodo();
				n.loadResultSet(rs);
				n.setDescrizioneAreaStruttureComplesse(n.getDescrizione_lunga());
				lista.add(n);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		OiaNodo[] nodi = new OiaNodo[lista.size()];
		int ind = 0;
		for (OiaNodo n : lista) {
			nodi[ind] = n;
			ind++;
		}
		/* Metodo richiamato sul soggetto fisico proveniente dalla request */
		/**/

		return nodi;

	}

	public static OiaNodo[] getStruttureComplesse(int idAsl, int anno) throws SQLException {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<OiaNodo> lista = new ArrayList<OiaNodo>();
		try {
			db = GestoreConnessioni.getConnection();

			String sql = "select dpat_strutture_asl.*,o.org_id,o.tipologia,tipooia.description as descrizione_tipologia_struttura from "
					+ "dpat_strutture_asl left join organization o on o.site_id =dpat_strutture_asl.id_asl and o.tipologia = 6"
					+ "LEFT JOIN lookup_tipologia_nodo_oia tipooia ON dpat_strutture_asl.tipologia_struttura = tipooia.code "

					+ " where tipologia_struttura = 13 and id_asl = ? "
					+ " and id_strumento_calcolo in (select id from  "
					+ "  dpat_strumento_calcolo where id_asl = ? and anno=? ) and disabilitato=false";
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			pst.setInt(2, idAsl);
			pst.setInt(3, anno);
			rs = pst.executeQuery();
			while (rs.next()) {
				OiaNodo n = new OiaNodo();
				n.loadResultSet(rs);
				n.setDescrizioneAreaStruttureComplesse(n.getDescrizioneAreaStruttureComplesse() + " / "
						+ n.getDescrizione_lunga());
				lista.add(n);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		OiaNodo[] nodi = new OiaNodo[lista.size()];
		int ind = 0;
		for (OiaNodo n : lista) {
			nodi[ind] = n;
			ind++;
		}
		/* Metodo richiamato sul soggetto fisico proveniente dalla request */
		/**/

		return nodi;

	}

	public static ArrayList<String> getPartitaIva(String pIva) throws SQLException {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<String> listapartiteIva = new ArrayList<String>();

		try {
			db = GestoreConnessioni.getConnection();

			String sel = "select * from opu_operatore where trashed_Date is null and partita_iva ilike ?";
			pst = db.prepareStatement(sel);
			pst.setString(1, pIva + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				listapartiteIva.add(rs.getString(1));
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		/* Metodo richiamato sul soggetto fisico proveniente dalla request */
		/**/

		return listapartiteIva;

	}

	public static Object[] load_linee_attivita_per_id_stabilimento(String idStabilimento,
			String campo_combo_da_costruire, String default_value) {
		Object[] ret = new Object[4];

		Connection db = null;
		int i = -1;
		ArrayList<LineeAttivita> lista;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				lista = LineeAttivita.load_linee_attivita_per_id_stabilimento_opu(idStabilimento, db);

				Object[] ind = new Object[lista.size()];
				Object[] val = new Object[lista.size()];

				for (LineeAttivita linea : lista) {
					i++;
					ind[i] = linea.getId();

					if (!linea.getLinea_attivita().isEmpty())
						val[i] = linea.getMacroarea() + "- " + linea.getCategoria() + " - " + linea.getLinea_attivita();
					// val[i] = linea.getCategoria() + " - " +
					// linea.getLinea_attivita();
					else
						val[i] = linea.getCategoria();
					// val[i] = linea.getCategoria();
				}
				ret[0] = ind;
				ret[1] = val;
				ret[2] = campo_combo_da_costruire;
				if (default_value != null)
					ret[3] = default_value;
				else
					ret[3] = "-1";
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}
	
	public static Object[] load_linee_attivita_per_alt_id_stabilimento(String altId, 
			String campo_combo_da_costruire, String default_value) {
		Object[] ret = new Object[4];

		Connection db = null;
		int i = -1;
		ArrayList<LineeAttivita> lista;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				lista = LineeAttivita.load_linee_attivita_per_alt_id_stabilimento_opu(altId, db);

				Object[] ind = new Object[lista.size()];
				Object[] val = new Object[lista.size()];

				for (LineeAttivita linea : lista) {
					i++;
					ind[i] = linea.getId();

					if (!linea.getLinea_attivita().isEmpty())
						val[i] = linea.getMacroarea() + "- " + linea.getCategoria() + " - " + linea.getLinea_attivita();
					// val[i] = linea.getCategoria() + " - " +
					// linea.getLinea_attivita();
					else
						val[i] = linea.getCategoria();
					// val[i] = linea.getCategoria();
				}
				ret[0] = ind;
				ret[1] = val;
				ret[2] = campo_combo_da_costruire;
				if (default_value != null)
					ret[3] = default_value;
				else
					ret[3] = "-1";
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}

	public static Object[] load_linee_attivita_per_alt_id(String altId, String campo_combo_da_costruire,
			String default_value) {
		Object[] ret = new Object[4];

		Connection db = null;
		int i = -1;
		ArrayList<LineeAttivita> lista;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				lista = LineeAttivita.load_linee_attivita_per_alt_id(altId, db);

				Object[] ind = new Object[lista.size()];
				Object[] val = new Object[lista.size()];

				for (LineeAttivita linea : lista) {
					i++;
					ind[i] = linea.getId();

					if (!linea.getLinea_attivita().isEmpty())
						val[i] = linea.getMacroarea() + "- " + linea.getCategoria() + " - " + linea.getLinea_attivita();
					// val[i] = linea.getCategoria() + " - " +
					// linea.getLinea_attivita();
					else
						val[i] = linea.getCategoria();
					// val[i] = linea.getCategoria();
				}
				ret[0] = ind;
				ret[1] = val;
				ret[2] = campo_combo_da_costruire;
				if (default_value != null)
					ret[3] = default_value;
				else
					ret[3] = "-1";
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}
	
	
	public static Object[] load_linee_attivita_per_alt_id_sintesis(String altId, String campo_combo_da_costruire,
			String default_value) {
		Object[] ret = new Object[4];

		Connection db = null;
		int i = -1;
		ArrayList<LineeAttivita> lista;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				lista = LineeAttivita.load_linee_attivita_per_alt_id_sintesis(altId, db);

				Object[] ind = new Object[lista.size()];
				Object[] val = new Object[lista.size()];

				for (LineeAttivita linea : lista) {
					i++;
					ind[i] = linea.getId();

					if (!linea.getLinea_attivita().isEmpty())
						val[i] = linea.getMacroarea() + "- " + linea.getCategoria() + " - " + linea.getLinea_attivita();
					// val[i] = linea.getCategoria() + " - " +
					// linea.getLinea_attivita();
					else
						val[i] = linea.getCategoria();
					// val[i] = linea.getCategoria();
				}
				ret[0] = ind;
				ret[1] = val;
				ret[2] = campo_combo_da_costruire;
				if (default_value != null)
					ret[3] = default_value;
				else
					ret[3] = "-1";
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}
	
	public static Object[] load_linee_attivita_per_alt_id_anagrafica(String altId, String campo_combo_da_costruire,
			String default_value) {
		Object[] ret = new Object[4];

		Connection db = null;
		int i = -1;
		ArrayList<LineeAttivita> lista;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				lista = LineeAttivita.load_linee_attivita_per_alt_id_anagrafica(altId, db);

				Object[] ind = new Object[lista.size()];
				Object[] val = new Object[lista.size()];

				for (LineeAttivita linea : lista) {
					i++;
					ind[i] = linea.getId();

					if (!linea.getLinea_attivita().isEmpty())
						val[i] = linea.getMacroarea() + "- " + linea.getCategoria() + " - " + linea.getLinea_attivita();
					// val[i] = linea.getCategoria() + " - " +
					// linea.getLinea_attivita();
					else
						val[i] = linea.getCategoria();
					// val[i] = linea.getCategoria();
				}
				ret[0] = ind;
				ret[1] = val;
				ret[2] = campo_combo_da_costruire;
				if (default_value != null)
					ret[3] = default_value;
				else
					ret[3] = "-1";
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}
	
	
	
	

	public static boolean verificaStampaMod10(int idMacello, String dataMacellazione, int sedutaMacellazione)
			throws ParseException {

		int idTampone = 0;
		Connection db = null;
		Tampone t = new Tampone();
		try {

			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db
						.prepareStatement("select id from m_mod_10 where id_macello = ? and to_char(data_macellazione, 'dd/MM/yyyy') = ?  and sessione_macellazione = ?");
				pst.setInt(1, idMacello);
				pst.setString(2, dataMacellazione);
				pst.setInt(3, sedutaMacellazione);
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					return true;
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return false;
	}

	public static Tampone getTampone(int idMacello, String dataMacellazione, int sedutaMacellazione)
			throws ParseException {

		int idTampone = 0;
		Connection db = null;
		Tampone t = new Tampone();
		try {

			db = GestoreConnessioni.getConnection();
			if (db != null) {
				t = Tampone.load(idMacello, dataMacellazione, sedutaMacellazione, db);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return t;
	}

	public static String getComboPerContoDiSelSingola(ArrayList<OiaNodo> lista1, String name, String id, int defValue) {
		StringBuffer combo = new StringBuffer("");
		combo.append("<select onchange='' name = '" + name + "' id='" + id + "' >");
		if (defValue == -1) {
			combo.append("<option value = '-1'selected='selected'>Seleziona Unita Operativa</option>");
		} else {
			combo.append("<option value = '-1' >Seleziona Unita Operativa</option>");
		}

		for (OiaNodo nodoAsl : lista1) {
			if (nodoAsl.getLista_nodi().size() > 0) {
				combo.append("<optgroup label='" + nodoAsl.getDescrizione_lunga() + "'></optgroup>");

				for (OiaNodo nodoFiglio : nodoAsl.getLista_nodi()) {

					if (nodoFiglio.getId() == defValue) {
						combo.append(" <option value = '" + nodoFiglio.getId() + "' selected ='selected'>"
								+ nodoFiglio.getDescrizione_lunga() + "</option>");
					} else {
						combo.append(" <option value = '" + nodoFiglio.getId() + "' >"
								+ nodoFiglio.getDescrizione_lunga() + "</option>");

					}

				}

				for (OiaNodo nodoFiglio : nodoAsl.getLista_nodi()) {
					if (nodoFiglio.getLista_nodi().size() > 0) {

						combo.append("<optgroup label='" + nodoFiglio.getDescrizione_lunga() + "'></optgroup>");

						for (OiaNodo nipote : nodoFiglio.getLista_nodi()) {

							if (nipote.getId() == defValue) {
								combo.append(" <option value = '" + nipote.getId() + "' selected ='selected'>"
										+ nipote.getDescrizione_lunga() + "</option>");
							} else {
								combo.append(" <option value = '" + nipote.getId() + "' >"
										+ nipote.getDescrizione_lunga() + "</option>");

							}

						}
					}

				}

			} else {

				if (nodoAsl.getId() == defValue) {
					combo.append(" <option value = '" + nodoAsl.getId() + "' selected ='selected'>"
							+ nodoAsl.getDescrizione_lunga() + "</option>");
				} else {
					combo.append(" <option value = '" + nodoAsl.getId() + "' >" + nodoAsl.getDescrizione_lunga()
							+ "</option>");

				}
			}
		}
		combo.append("</select>");
		return combo.toString();
	}

	public static void insertSchedeSin() throws InterruptedException {

		// Client.importGisaToBdn("logSin");
	}

	public static Object[] getSchema(String tabella) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<String> nomi = new ArrayList<String>();
		ArrayList<String> tipi = new ArrayList<String>();
		Object[] toRet = new Object[2];
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = " SELECT  a.attname as Column,"
						+ " pg_catalog.format_type(a.atttypid, a.atttypmod) as Datatype "
						+ "FROM   pg_catalog.pg_attribute a "
						+ "WHERE a.attnum > 0 AND NOT a.attisdropped AND a.attrelid = ( "
						+ "SELECT c.oid FROM pg_catalog.pg_class c LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace WHERE c.relname =? "
						+ " AND pg_catalog.pg_table_is_visible(c.oid) " + " ) ";
				pst = db.prepareStatement(sel);
				pst.setString(1, tabella);
				rs = pst.executeQuery();
				while (rs.next()) {
					nomi.add(rs.getString(1));
					tipi.add(rs.getString(2));

				}

				Object[] nomiObj = new Object[nomi.size()];
				int i = 0;
				for (String nome : nomi) {
					nomiObj[i] = nome;
					i++;

				}

				Object[] tipiObj = new Object[nomi.size()];
				i = 0;
				for (String nome : tipi) {
					tipiObj[i] = nome;
					i++;

				}
				toRet[0] = nomiObj;
				toRet[1] = tipiObj;
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return toRet;

	}

	public static boolean controlloEsuistenzaAsl(String idAsl) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select * from oia_nodo where id_asl = ? and id_padre = -1";
				pst = db.prepareStatement(sel);
				pst.setInt(1, Integer.parseInt(idAsl));
				rs = pst.executeQuery();
				if (rs.next())
					esistente = true;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static boolean controlloEsuistenzaStabilimento(String approvalNumber, int idAsl) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "SELECT org_id FROM organization WHERE numAut = ?  AND enabled AND trashed_date IS NULL and stato_istruttoria in (0,9)";
				if (idAsl > 0)
					sel += " and site_id =? ";

				pst = db.prepareStatement(sel);
				pst.setString(1, approvalNumber);
				if (idAsl > 0)
					pst.setInt(2, idAsl);
				rs = pst.executeQuery();
				if (rs.next())
					esistente = true;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static String controlloNodo(int idNodo, String nomeTabella, String colonnaPadre) {
		String esistente = "no";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select * from " + nomeTabella + " where " + colonnaPadre + " = ? ";
				pst = db.prepareStatement(sel);
				pst.setInt(1, idNodo);
				rs = pst.executeQuery();
				if (rs.next())
					esistente = "si";
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static boolean controlloEsuistenzaApprovalNumber(String numAut) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select * from organization where numaut ilike ? and trashed_date is null";
				pst = db.prepareStatement(sel);
				pst.setString(1, numAut);
				rs = pst.executeQuery();
				if (rs.next())
					esistente = true;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static Object[] getNumeroCuCampioniPianificati(String idAsl) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Object[] toRet = new Object[2];
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String selCu = "select sum(cu_pianificati) "
						+ "from cu_programmazioni cup join cu_programmazioni_asl cupa on cup.id = cupa.id_programmazione and cup.trashed_date is null and cupa.enabled = true "
						+ "where cupa.id_asl = ? and anno = date_part ('years',current_date) and cu_pianificati >0";
				String selCampioni = "select sum(campioni_pianificati) "
						+ "from cu_programmazioni cup join cu_programmazioni_asl cupa on cup.id = cupa.id_programmazione and cup.trashed_date is null and cupa.enabled = true "
						+ "where cupa.id_asl = ? and anno = date_part ('years',current_date) and campioni_pianificati >0";

				pst = db.prepareStatement(selCu);
				pst.setInt(1, Integer.parseInt(idAsl));
				rs = pst.executeQuery();
				if (rs.next())
					toRet[0] = rs.getInt(1);

				pst = db.prepareStatement(selCampioni);
				pst.setInt(1, Integer.parseInt(idAsl));
				rs = pst.executeQuery();
				if (rs.next())
					toRet[1] = rs.getInt(1);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return toRet;

	}

	public static boolean controlloEsuistenzaAreaAsl(String idUtente) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select * from oia_nodo join oia_nodo_responsabili on oia_nodo.id =id_oia_nodo  where id_utente =?";
				pst = db.prepareStatement(sel);
				pst.setInt(1, Integer.parseInt(idUtente));
				rs = pst.executeQuery();
				if (rs.next())
					esistente = true;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static boolean controlloEsistenzaOpNonAltrove(String accountNumber, String partitaIva) {  
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "";
				
				if (accountNumber!=null && !accountNumber.equals(""))
					sel = "select org_id from organization where account_number = ? and tipologia = 12 and trashed_Date is null ";
				
				if (accountNumber!=null && !accountNumber.equals("") && partitaIva!=null && !partitaIva.equals(""))
					sel += " UNION ";
					
				if (partitaIva!=null && !partitaIva.equals(""))
					sel += "select riferimento_id from ricerche_anagrafiche_old_materializzata where partita_iva = ?";
				
				pst = db.prepareStatement(sel);
				int i = 0;
				
				if (accountNumber!=null && !accountNumber.equals(""))
					pst.setString(++i, accountNumber);
				
				if (partitaIva!=null && !partitaIva.equals(""))
					pst.setString(++i, partitaIva);
				
				rs = pst.executeQuery();
				if (rs.next())
					esistente = true;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}
	
	public static boolean importImbarcazione(int org_id, int id_utente) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		PreparedStatement pst_update = null;
		PreparedStatement pst_update_add = null;

		ResultSet rs = null;

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select * from organization where tipologia = 1 and trashed_date is null and org_id = ? ";
				pst = db.prepareStatement(sel);
				pst.setInt(1, org_id);
				rs = pst.executeQuery();
				if (rs.next()) {
					// si puo trasferire in imbarcazione
					String update = " update organization set tipologia = ?, taxid = ?, account_number = ?,  note_sviluppo = ?, fuori_regione = ?, numaut = ? where org_id = ?";
					pst_update = db.prepareStatement(update);
					pst_update.setInt(1, 17);
					pst_update.setString(2, rs.getString("account_number"));
					pst_update.setString(3, "N.D.");
					pst_update.setString(4, "IMPORT EFFETTUATO DA GISA DALL'UTENTE " + id_utente);
					pst_update.setBoolean(5, false);
					pst_update.setString(6, rs.getString("nome_correntista"));
					pst_update.setInt(7, org_id);
					int result = pst_update.executeUpdate();
					if (result > 0) {
						esistente = true;
						// Aggiornamento anche dell'indirizzo
						String update_add = " update organization_address set address_type = ? where org_id = ? and address_type = ? ";
						pst_update_add = db.prepareStatement(update_add);
						pst_update_add.setInt(1, 5);
						pst_update_add.setInt(2, org_id);
						pst_update_add.setInt(3, 7);
						pst_update_add.executeUpdate();
					}
				}

			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static boolean controlloEsuistenzaNumRegistrazioneImbarcazioni(String accountNumber) {
		boolean esistente = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select org_id from organization where account_number = ? and tipologia = 17 and trashed_date is null ";
				pst = db.prepareStatement(sel);
				pst.setString(1, accountNumber);
				rs = pst.executeQuery();
				if (rs.next())
					esistente = true;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esistente;

	}

	public static Object[] getValoriComboTipologiaStruttura(int codiceGruppo) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select code,description from lookup_tipologia_struttura where enabled = true ";
				String where = "";

				switch (codiceGruppo) {
				case GRUPPO_BOVINI_BUFANILI: {
					where = " and enabled_bovini_bufalini = true ";
					break;
				}

				case GRUPPO_OVINI_CAPRINI: {
					where = " and enabled_ovini_caprini = true ";
					break;
				}

				case GRUPPO_SUINI: {
					where = " and enabled_suini = true ";
					break;
				}

				case GRUPPO_AVICOLI: {
					where = " and enabled_avicoli = true ";
					break;
				}

				case GRUPPO_EQUIDI: {
					where = " and enabled_equini = true ";
					break;
				}

				case GRUPPO_API: {
					where = " and enabled_api = true ";
					break;
				}

				case GRUPPO_CONIGLI: {
					where = " and enabled_conigli = true ";
					break;
				}
				}

				if (codiceGruppo != -1) {
					select += where;
				}
				pst = db.prepareStatement(select);
				rs = pst.executeQuery();
				int i = 1;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = -1;
				val[0] = "TUTTE LE STRUTTURE";

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static int getCaricoLavoroAnnuale(int idQualifica) {
		int caricoLavoroDefault = 0;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			String sel = "select carico_default from lookup_qualifiche where code = " + idQualifica;
			pst = db.prepareStatement(sel);
			rs = pst.executeQuery();
			if (rs.next())
				caricoLavoroDefault = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		}

		finally {
			GestoreConnessioni.freeConnection(db);
		}
		return caricoLavoroDefault;

	}

	public static OiaNodo aggiornaDatiStruttura(int idStruttura, String fattori, int percentuale) {
		Connection db = null;
		OiaNodo struttura = null;

		try {
			db = GestoreConnessioni.getConnection();
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();

			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext()
					.getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			SystemStatus thisSystem = null;
			HashMap sessions = null;
			thisSystem = (SystemStatus) ((Hashtable) request.getServletContext().getAttribute("SystemStatus")).get(ce
					.getUrl());

			struttura = new OiaNodo(db, idStruttura, thisSystem);
			struttura.setFattoriIncidentiSuCarico(fattori);
			struttura.setPercentualeDaSottrarre(percentuale);

			DpatStrumentoCalcolo sc = new DpatStrumentoCalcolo();
			sc.queryRecord(db, struttura.getIdStrumentoCalcolo());
			sc.setCoefficienteUbaFromdb(db);
			Dpat d = new Dpat();

			struttura
			.aggiornaDatiStruttura(db, d.isCongelato(db, sc.getIdAsl(), sc.getAnno()), sc.getCoefficienteUba());

			OiaNodo nodoPadre = new OiaNodo(db, struttura.getId_padre());
			struttura.setSommaUiArea(nodoPadre.getSommaUiArea());
			struttura.setSommaUiAreaInizialeBloccata(nodoPadre.getSommaUiAreaInizialeBloccata());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return struttura;

	}

	public static Object[] getValoriComboImpiantiStabilimenti(int idcategoria) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();

			String select = "select code,description from lookup_impianto join categoria_impianti_stabilimenti on (code = id_impianto) where enabled = true and id_categoria = ?";

			pst = db.prepareStatement(select);
			pst.setInt(1, idcategoria);
			rs = pst.executeQuery();
			int i = 0;

			while (rs.next()) {
				int code = rs.getInt("code");
				String value = rs.getString("description");
				valori.put(code, value);

			}
			Object[] ind = new Object[valori.size()];
			Object[] val = new Object[valori.size()];

			for (Integer kiave : valori.keySet()) {
				ind[i] = kiave;
				val[i] = valori.get(kiave);
				i++;
			}
			ret[0] = ind;
			ret[1] = val;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getListaNominaitvi(int idAsl) {

		Object[] ret = null;

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			int size = 0;
			String select = "select nominativo from anagrafica_nominativo where id_asl =?";
			String conta = "select count(*) as num from anagrafica_nominativo where id_asl =?";
			pst = db.prepareStatement(conta);
			pst.setInt(1, idAsl);
			rs = pst.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
			ret = new Object[size];
			int i = 0;

			pst = db.prepareStatement(select);
			pst.setInt(1, idAsl);
			rs = pst.executeQuery();
			while (rs.next()) {
				ret[i] = rs.getString(1);
				i = i + 1;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboStruttureOia(int idNodo, int anno) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		Calendar calCorrente = GregorianCalendar.getInstance();
		Date dataCorrente = new Date(System.currentTimeMillis());
		calCorrente.setTime(new Timestamp(dataCorrente.getTime()));

		if (anno == calCorrente.get(Calendar.YEAR)) {
			int tolleranzaGiorni = Integer.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate() - tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			anno = calCorrente.get(Calendar.YEAR);

		}

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String filtro = "";
				if (idNodo == 1043661)
					filtro += " and oia_nodo.id_asl = -1";
				String t = "(select oia_nodo.id as code , descrizione_struttura  as description ,'' as short_description,false as default_item,1 as level,true as enabled "
						+ "from dpat_strutture_asl oia_nodo join organization o on o.site_id = oia_nodo.id_asl and o.tipologia=6 "
						+ " where n_livello !=3  and coalesce(oia_nodo.tipologia_struttura,0)!=39 and coalesce(oia_nodo.tipologia_struttura,0) !=14   and n_livello !=1 and (oia_nodo.disabilitato) = false and (anno ="
						+ anno + " or anno=-1) and o.org_id =" + idNodo + ")" + "";
				pst = db.prepareStatement(t);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static int controlloAssegnazioneIndirizzoImprese(int idIndirizzo) {
		int idOperatore = -1;
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sql = "select id_operatore from relazione_operatore_sede where id_indirizzo = ?";
				pst = db.prepareStatement(sql);
				pst.setInt(1, idIndirizzo);
				rs = pst.executeQuery();
				if (rs.next()) {
					idOperatore = rs.getInt(1);
				}
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return idOperatore;

	}

	public static int controlloAssegnazioneIndirizzoStabilimento(int idIndirizzo) {
		int idOperatore = -1;
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sql = "select id from stabilimento where id_indirizzo = ?";
				pst = db.prepareStatement(sql);
				pst.setInt(1, idIndirizzo);
				rs = pst.executeQuery();
				if (rs.next()) {
					idOperatore = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return idOperatore;

	}

	public static Object[] getValoriComboProdottiStabilimenti(int idcategoria) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {

				String select = "select lookup_prodotti_stabilimenti.code,lookup_prodotti_stabilimenti.description from lookup_prodotti_stabilimenti join prodotti_stabilimenti_categorie on "
						+ " lookup_prodotti_stabilimenti.code = prodotti_stabilimenti_categorie.id_prodotto where lookup_prodotti_stabilimenti.enabled=true and prodotti_stabilimenti_categorie.id_categoria = ?";

				pst = db.prepareStatement(select);
				pst.setInt(1, idcategoria);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboProdottiSoa(int idcategoria) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select lookup_prodotti_soa.code,lookup_prodotti_soa.description from lookup_prodotti_soa join prodotti_soa_categorie on "
						+ " lookup_prodotti_soa.code = prodotti_soa_categorie.id_prodotto where lookup_prodotti_soa.enabled=true and prodotti_soa_categorie.id_categoria = ?";

				pst = db.prepareStatement(select);
				pst.setInt(1, idcategoria);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboImpiantiSoa(int idcategoria) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select code,description "
						+ "from lookup_impianto_soa a1 join soa_categorie_impianti a2 on (a1.code = a2.id_impianto) "
						+ "where a2.id_categoria = ? and a1.enabled = true";

				pst = db.prepareStatement(select);
				pst.setInt(1, idcategoria);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboCategorieSoa(int tipoSoa) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select code,description " + "from lookup_categoria_soa where 1=1 ";
				if (tipoSoa > 0)
					select += " and tipo_soa = ?";

				pst = db.prepareStatement(select);
				if (tipoSoa > 0)
					pst.setInt(1, tipoSoa);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboComuni1Asl(int idAsl) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select id,nome from comuni1 c,lookup_site_id asl where c.notused is null and c.codiceistatasl=asl.codiceistat and asl.enabled=true ";

				if (idAsl != -1 && idAsl != -2) {
					select += " and asl.code = ? order by nome ";
				} else {
					select += " order by nome ";
				}

				pst = db.prepareStatement(select);
				if (idAsl != -1 && idAsl != -2) {
					pst.setInt(1, idAsl);
				}
				rs = pst.executeQuery();
				int i = 1;

				while (rs.next()) {
					String value = rs.getString("nome");
					int id = rs.getInt("id");
					valori.put(id, value);

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = "";
				val[0] = "                ";

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboComuni1Provincia(int idProvincia) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select id,nome from comuni1 c,lookup_province p where c.notused is null and c.cod_provincia::integer=p.code and p.enabled=true ";

				if (idProvincia != -1 && idProvincia != -2) {
					select += " and p.code = ? order by nome ";
				} else {
					select += " order by nome ";
				}

				pst = db.prepareStatement(select);
				if (idProvincia != -1 && idProvincia != -2) {
					pst.setInt(1, idProvincia);
				}
				rs = pst.executeQuery();
				int i = 1;

				while (rs.next()) {
					String value = rs.getString("nome");
					int id = rs.getInt("id");
					valori.put(id, value);

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = "";
				val[0] = "                ";

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}
	
	/**
	 * 
	 * Questa funzione seleziona i comuni dal DB per asl
	 * 
	 **/
	public static Object[] getValoriComboComuniAsl(int idAsl) {

		Object[] ret = new Object[2];

		// HashMap<String, String> valori =new HashMap<String,String>();
		ArrayList<String> valori = new ArrayList<String>();

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select comune from comuni c,lookup_site_id asl where notused is null and c.codiceistatasl=asl.codiceistat and asl.enabled=true ";

				if (idAsl != -1 && idAsl != -2) {
					select += " and asl.code = ? order by comune ";
				} else {
					select += " order by comune ";
				}
				pst = db.prepareStatement(select);
				if (idAsl != -1 && idAsl != -2) {
					pst.setInt(1, idAsl);
				}
				rs = pst.executeQuery();
				int i = 1;
				while (rs.next()) {
					String value = rs.getString("comune");
					valori.add(valori.size(), (value));

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = "";
				val[0] = "                ";

				for (String kiave : valori) {
					ind[i] = kiave;
					val[i] = kiave;
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboComuniProvinciaOSM(String provincia) {

		Object[] ret = new Object[2];

		// HashMap<String, String> valori =new HashMap<String,String>();
		ArrayList<String> valori = new ArrayList<String>();

		ArrayList<Integer> asl_id = new ArrayList<Integer>();
		if (provincia != null) {
			if (provincia.equals("AV")) {
				asl_id.add(201);

			} else if (provincia.equals("BN")) {
				asl_id.add(202);
			} else if (provincia.equals("CE")) {
				asl_id.add(203);

			} else if (provincia.equals("NA")) {
				asl_id.add(204);
				asl_id.add(205);
				asl_id.add(206);

			} else if (provincia.equals("SA")) {
				asl_id.add(207);

			}
		}

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			String select = null;
			if (db != null) {
				if (provincia.length() == 0) {
					select = "select comune from comuni order by comune;";
				} else {
					String array_valori = asl_id.toString();
					int i = array_valori.length();
					String solo_valori_array = array_valori.substring(1, i - 1);
					// pst.setInt(1, Integer.parseInt(solo_valori_array));
					select = "select comune from comuni where codiceistatasl IN (select codiceistat from lookup_site_id where code IN  ("
							+ solo_valori_array + "))";

				}

				pst = db.prepareStatement(select);
				rs = pst.executeQuery();
				int i = 1;

				while (rs.next()) {

					String value = rs.getString("comune");
					valori.add(valori.size(), (value.replaceAll("'", "-")));

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = "";
				val[0] = "                ";

				for (String kiave : valori) {
					ind[i] = kiave;
					val[i] = kiave;
					i++;
				}
				ret[0] = ind;
				ret[1] = val;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriAsl(int idcomune) {
		Object[] ret = new Object[2];
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select code, description from lookup_site_id where enabled = true ";

				if (idcomune > 0)
					select += " and codiceistat IN (select codiceistatasl from comuni1 where notused is null and id = ? ) ";

				pst = db.prepareStatement(select);
				if (idcomune > 0)
					pst.setInt(1, idcomune);
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComuniASL(String comune) {
		Object[] ret = new Object[2];
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();

			String select = "select code, description from lookup_site_id where enabled = true ";
			if (db != null) {
				if (comune != null && !comune.equals("")) {
					select += " and codiceistat IN (select codiceistatasl from comuni where comune = ? ) ";
				} else {
					select += " ; ";
				}

				pst = db.prepareStatement(select);
				if (comune != null && !comune.equals("")) {
					pst.setString(1, comune);
				}
				rs = pst.executeQuery();
				int i = 0;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size()];
				Object[] val = new Object[valori.size()];

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	// Metodo che a partire dalla provincia seleziona l'asl

	public static Object[] getValoriAslProvincia(String provincia) {

		Object[] ret = new Object[2];
		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		// HashMap<String, String> valori =new HashMap<String,String>();
		ArrayList<Integer> asl_id = new ArrayList<Integer>();

		if (provincia != null) {
			if (provincia.equals("AV")) {
				asl_id.add(201);

			} else if (provincia.equals("BN")) {
				asl_id.add(202);
			} else if (provincia.equals("CE")) {
				asl_id.add(203);

			} else if (provincia.equals("NA")) {
				asl_id.add(204);
				asl_id.add(205);
				asl_id.add(206);

			} else if (provincia.equals("SA")) {
				asl_id.add(207);

			}
		}

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			String select = null;
			String array_valori = asl_id.toString();
			if (db != null) {
				int i = array_valori.length();
				String solo_valori_array = array_valori.substring(1, i - 1);
				select = "select code,description from lookup_site_id where code IN  (" + solo_valori_array
						+ ") and enabled = true ";
				pst = db.prepareStatement(select);

				rs = pst.executeQuery();

				int j = 1;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}

				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = -1;
				val[0] = "--- SELEZIONA VOCE ---";

				for (Integer kiave : valori.keySet()) {
					ind[j] = kiave;
					val[j] = valori.get(kiave);
					j++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboOrientamentoProduttivo(int tipoStruttura, int codiceGruppo) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();

			if (db != null) {
				String select = "select code,description from lookup_orientamento_produttivo where enabled = true ";
				String where = "";
				if (tipoStruttura != -1) {
					where = " and tipo_struttura in (select description from lookup_tipologia_struttura where code = "
							+ tipoStruttura + ")";
				}
				switch (codiceGruppo) {
				case GRUPPO_BOVINI_BUFANILI: {
					where += " and enabled_bovini_bufalini = true ";
					break;
				}

				case GRUPPO_OVINI_CAPRINI: {
					where += " and enabled_ovini_caprini = true ";
					break;
				}

				case GRUPPO_SUINI: {
					where += " and enabled_suini = true ";
					break;
				}

				case GRUPPO_AVICOLI: {
					where += " and enabled_avicoli = true ";
					break;
				}

				case GRUPPO_EQUIDI: {
					where += " and enabled_equini = true ";
					break;
				}

				case GRUPPO_API: {
					where = " and enabled_api = true ";
					break;
				}

				case GRUPPO_CONIGLI: {
					where = " and enabled_conigli = true ";
					break;
				}
				}

				if (codiceGruppo != -1) {
					select += where;
				}
				pst = db.prepareStatement(select);
				rs = pst.executeQuery();
				int i = 1;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = -1;
				val[0] = "--- TUTTI ---";

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	public static Object[] getValoriComboSpecie(int codiceGrippo) {

		Object[] ret = new Object[2];

		HashMap<Integer, String> valori = new HashMap<Integer, String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String select = "select code,description from lookup_specie_allevata where enabled = true ";
				String where = "and codice_categoria = " + codiceGrippo;
				if (codiceGrippo != -1) {
					select += where;
				}
				pst = db.prepareStatement(select);
				rs = pst.executeQuery();
				int i = 1;

				while (rs.next()) {
					int code = rs.getInt("code");
					String value = rs.getString("description");
					valori.put(code, value);

				}
				Object[] ind = new Object[valori.size() + 1];
				Object[] val = new Object[valori.size() + 1];

				ind[0] = -1;
				val[0] = "TUTTE LE SPECI";

				for (Integer kiave : valori.keySet()) {
					ind[i] = kiave;
					val[i] = valori.get(kiave);
					i++;
				}
				ret[0] = ind;
				ret[1] = val;

			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;

	}

	/**
	 * AL MOMENTO DELL'APERTURA DELLA PRIMA CHECKLIST VIENE EFFETTUATO UN
	 * CONTROLLO . SE ESISTONO CONTROLLI UFFICIALI NON IN SORVEGLIANZA APERTI
	 * CONTENENTI ALMENO UN CAMPIONE TAMPONE O NON CONFORMITA APERTO , TALE
	 * CONTROLLO NON VERRa PRESO IN CONSIDERAZIONE PER IL CALCOLO DEL PUNTEGGIO.
	 * SE TUTTI I CAMPIONI TAMPONI E NON CONFORMITa SONO STATE CHIUSE MA IL
	 * CONTROLLO RESTA APERTO VIENE SEGNALATO DA UN MESSAGGIO BLOCCANTE
	 * 
	 * @param orgId
	 * @param idCuSorveglianza
	 * @return
	 */

	public String controlloAperturaChecklistApi(int orgId, int idCuSorveglianza, int userId) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;

		String messaggio = "";
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t.id_controllo_ufficiale, to_char(assigned_date, 'dd/MM/yyyy') as data_cu FROM ticket t "
				+ " WHERE t.ticketid > 0 "
				+ " AND t.tipologia = 3 "
				+ " and t.id_apiario = ? "
				+ " and (t.provvedimenti_prescrittivi != 5) "
				+ "  and  t.assigned_date <= ((select t.assigned_date  from ticket t where ticketid = ? )- interval '6 days')  "
				+ " and t.assigned_date >=((select t.assigned_date  from ticket t where ticketid = ? ) - interval '5 years') "
				+ " and t.trashed_date is null and t.closed is null ");
		StringBuffer sqlControllo = new StringBuffer("");

		sqlControllo.append(" select t.ticketid,t.closed " + " from ticket t where tipologia in (2,7,8) "
				+ " and id_controllo_ufficiale = ? and trashed_date is null ");

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, orgId);
				pst.setInt(2, idCuSorveglianza);
				pst.setInt(3, idCuSorveglianza);
				//				pst.setInt(4, userId);

				rs = pst.executeQuery();
				boolean attivita_chiuse = true;
				while (rs.next()) {
					attivita_chiuse = true;
					String idCu_ns = rs.getString(1);
					String dataCu = rs.getString(2);
					pst1 = db.prepareStatement(sqlControllo.toString());
					pst1.setString(1, idCu_ns);
					rs1 = pst1.executeQuery();
					while (rs1.next()) {
						Timestamp closed = rs1.getTimestamp("closed");
						if (closed == null) {
							attivita_chiuse = false;
						}
					}
					pst1.close();
					rs1.close();

					if (attivita_chiuse == true) {
						messaggio += "" + idCu_ns +" Del "+dataCu+ "\n";
					}
				}
				if(!messaggio.equals(""))
				{
					messaggio+="\nPer procedere Alla chiusura di questi controlli contattare l'HelpDesk";
				}
				rs.close();
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return messaggio;
	}

	public String controlloAperturaChecklistOpu(int orgId, int idCuSorveglianza, int userId) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;

		String messaggio = "";
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t.id_controllo_ufficiale ,to_char(assigned_date, 'dd/MM/yyyy') as data_cu FROM ticket t "
				+ " WHERE t.ticketid > 0  and  t.ticketid<10000000 "
				+ " AND t.tipologia = 3 "
				+ " and t.id_stabilimento = ? "
				+ " and (t.provvedimenti_prescrittivi != 5) "
				+ "  and  t.assigned_date <= ((select t.assigned_date  from ticket t where ticketid = ? )- interval '6 days')  "
				+ " and t.assigned_date >=((select t.assigned_date  from ticket t where ticketid = ? ) - interval '5 years') "
				+ " and t.trashed_date is null and t.closed is null ");
		StringBuffer sqlControllo = new StringBuffer("");

		sqlControllo.append(" select t.ticketid,t.closed " + " from ticket t where tipologia in (2,7,8) "
				+ " and id_controllo_ufficiale = ? and trashed_date is null ");

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, orgId);
				pst.setInt(2, idCuSorveglianza);
				pst.setInt(3, idCuSorveglianza);


				rs = pst.executeQuery();
				boolean attivita_chiuse = true;
				while (rs.next()) {
					attivita_chiuse = true;
					String idCu_ns = rs.getString(1);
					String dataCu = rs.getString(2);
					pst1 = db.prepareStatement(sqlControllo.toString());
					pst1.setString(1, idCu_ns);
					rs1 = pst1.executeQuery();
					while (rs1.next()) {
						Timestamp closed = rs1.getTimestamp("closed");
						if (closed == null) {
							attivita_chiuse = false;
						}
					}
					pst1.close();
					rs1.close();

					if (attivita_chiuse == true) {
						messaggio += "" + idCu_ns +" Del "+dataCu+ "\n";
					}
				}
				if(!messaggio.equals(""))
				{
					messaggio+="\nPer procedere Alla chiusura di questi controlli contattare l'HelpDesk";
				}
				rs.close();
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return messaggio;
	}

	public String controlloAperturaChecklistAlt(int altId, int idCuSorveglianza, int userId) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;

		String messaggio = "";
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t.id_controllo_ufficiale, to_char(assigned_date, 'dd/MM/yyyy') as data_cu FROM ticket t "
				+ " WHERE t.ticketid > 0 and  t.ticketid<10000000  "
				+ " AND t.tipologia = 3 "
				+ " and t.alt_id = ? "
				+ " and (t.provvedimenti_prescrittivi != 5) "
				+ "  and  t.assigned_date <= ((select t.assigned_date  from ticket t where ticketid = ? )- interval '6 days')  "
				+ " and t.assigned_date >=((select t.assigned_date  from ticket t where ticketid = ? ) - interval '5 years') "
				+ " and t.trashed_date is null and t.closed is null ");
		StringBuffer sqlControllo = new StringBuffer("");

		sqlControllo.append(" select t.ticketid,t.closed " + " from ticket t where tipologia in (2,7,8) "
				+ " and id_controllo_ufficiale = ? and trashed_date is null ");

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, altId);
				pst.setInt(2, idCuSorveglianza);
				pst.setInt(3, idCuSorveglianza);


				rs = pst.executeQuery();
				boolean attivita_chiuse = true;
				while (rs.next()) {
					attivita_chiuse = true;
					String idCu_ns = rs.getString(1);
					String dataCu = rs.getString(2);
					pst1 = db.prepareStatement(sqlControllo.toString());
					pst1.setString(1, idCu_ns);
					rs1 = pst1.executeQuery();
					while (rs1.next()) {
						Timestamp closed = rs1.getTimestamp("closed");
						if (closed == null) {
							attivita_chiuse = false;
						}
					}
					pst1.close();
					rs1.close();

					if (attivita_chiuse == true) {
						messaggio += "" + idCu_ns +" Del " +dataCu+ "\n";
					}
				}
				if(!messaggio.equals(""))
				{
					messaggio+="\nPer procedere Alla chiusura di questi controlli contattare l'HelpDesk";
				}
				rs.close();
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return messaggio;
	}

	public String controlloAperturaChecklist(int orgId, int idCuSorveglianza, int userId) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;

		String messaggio = "";
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t.id_controllo_ufficiale, to_char(assigned_date, 'dd/MM/yyyy') as data_cu FROM ticket t "
				+ " WHERE t.ticketid > 0 and  t.ticketid<10000000  "
				+ " AND t.tipologia = 3 "
				+ " and t.org_id = ? "
				+ " and (t.provvedimenti_prescrittivi != 5) "
				+ "  and  t.assigned_date <= ((select t.assigned_date  from ticket t where ticketid = ? )- interval '6 days')  "
				+ " and t.assigned_date >=((select t.assigned_date  from ticket t where ticketid = ? ) - interval '5 years') "
				+ " and t.trashed_date is null and t.closed is null ");
		StringBuffer sqlControllo = new StringBuffer("");

		sqlControllo.append(" select t.ticketid,t.closed " + " from ticket t where tipologia in (2,7,8) "
				+ " and id_controllo_ufficiale = ? and trashed_date is null ");

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, orgId);
				pst.setInt(2, idCuSorveglianza);
				pst.setInt(3, idCuSorveglianza);


				rs = pst.executeQuery();
				boolean attivita_chiuse = true;
				while (rs.next()) {
					attivita_chiuse = true;
					String idCu_ns = rs.getString(1);
					String dataCu = rs.getString(2);
					pst1 = db.prepareStatement(sqlControllo.toString());
					pst1.setString(1, idCu_ns);
					rs1 = pst1.executeQuery();
					while (rs1.next()) {
						Timestamp closed = rs1.getTimestamp("closed");
						if (closed == null) {
							attivita_chiuse = false;
						}
					}
					pst1.close();
					rs1.close();

					if (attivita_chiuse == true) {
						messaggio += "" + idCu_ns+" Del "+dataCu + "\n";
					}
				}
				if(!messaggio.equals(""))
				{
					messaggio+="\nPer procedere Alla chiusura di questi controlli contattare l'HelpDesk";
				}
				rs.close();
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return messaggio;
	}

	public String[] controlloInserimentoCuSorveglianza(int orgId, boolean isPianoMonitoraggio, boolean isSorveglianza,
			String data_nuovo_controllo, int assetId) throws ParseException {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String messaggio = "";
		String messaggio2 = "";
		String messaggio3 = "";
		String messaggio4 = "";
		String messaggio5 = "";
		
		String[] mess = new String[6];
		int tipologia = -1;
		try {

			WebContext ctx = WebContextFactory.get();
			String suffisso = (String) ctx.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");

			db = GestoreConnessioni.getConnection();

			/**
			 * BLOCCO 1. DATA CONTROLLO MAGGIORE DELLADATAATTUALE
			 *
			 */
			Timestamp dataControlloUfficiale = new Timestamp((new SimpleDateFormat("dd/MM/yyyy")).parse(
					data_nuovo_controllo).getTime());
			Timestamp time_attuale = new Timestamp(System.currentTimeMillis());
			if (dataControlloUfficiale.after(time_attuale))
				messaggio4 = "Attenzione la data inizio controllo e maggiore della data attuale";

			/**
			 * BLOCCO 2. DATA DEL CONTROLLO NON e DELL'ANNO CORRENTE CON LIMITE
			 * DI 15 GG PER L'ANNO SUCCESSIVO.
			 * 
			 */
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(new Date(dataControlloUfficiale.getTime()));
			int annoControllo = cal.get(Calendar.YEAR);

			Timestamp data_attuale_time = new Timestamp(System.currentTimeMillis());

			Calendar calCorrente2 = GregorianCalendar.getInstance();

			Date dataCorrente2 = new Date(System.currentTimeMillis());
			calCorrente2.setTime(new Timestamp(dataCorrente2.getTime()));
			int anno_corrente2 = calCorrente2.get(Calendar.YEAR);

			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties
					.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate() - tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno_corrente = calCorrente.get(Calendar.YEAR);

			// if (annoControllo != anno_corrente2)

			if (suffisso != null && !suffisso.equals("_ext"))
				if (annoControllo != anno_corrente ){//|| annoControllo < anno_corrente2) {
					if (annoControllo != anno_corrente ){// || annoControllo < anno_corrente2) {

						if (annoControllo > anno_corrente)
							messaggio3 = ApplicationProperties.getProperty("BLOCCO4_MESSAGGIO");
						else
							messaggio3 = ApplicationProperties.getProperty("BLOCCO1_MESSAGGIO");
					}

				}

			EventoBloccoCu blocco = new EventoBloccoCu();
			blocco.queryRecord(db);

			String tipoBlocco = blocco.getTipoBlocco();

			if (suffisso != null && !suffisso.equals("_ext")) {
				if (annoControllo == anno_corrente2
						&& blocco.getData_sblocco() != null
						&& ((tipoBlocco.equalsIgnoreCase("IN") && dataControlloUfficiale.compareTo(blocco
								.getData_sblocco()) <= 0))

						) {

					// messaggio3 =
					// "CONTROLLO NON ACCETTATO! PRESENTE BLOCCO AL "+blocco.getData_sblocco();
					messaggio5 = ApplicationProperties.getProperty("BLOCCO2_MESSAGGIO") + " dal "
							+ blocco.getData_bloccoString() + " al " + blocco.getData_sbloccoString() + " "
							+ ApplicationProperties.getProperty("BLOCCO2_1MESSAGGIO");
				} else {
					if (annoControllo == anno_corrente2
							&& blocco.getData_sblocco() != null
							&& ((tipoBlocco.equalsIgnoreCase("OUT") && dataControlloUfficiale.compareTo(blocco
									.getData_sblocco()) > 0))

							) {

						// messaggio3 =
						// "CONTROLLO NON ACCETTATO! PRESENTE BLOCCO AL "+blocco.getData_sblocco();
						messaggio5 = ApplicationProperties.getProperty("BLOCCO3_MESSAGGIO") + " dal "
								+ blocco.getData_bloccoString() + " al " + blocco.getData_sbloccoString() + " "
								+ ApplicationProperties.getProperty("BLOCCO2_1MESSAGGIO");
					}

				}

			}

			/* se in sorveglianza */
			if (isSorveglianza == true) {

				/**
				 * PRIMA DI INSERIRE UN NUOVO CONTROLLO UFFICIALE IN
				 * SORVEGLIANZA VERIFICO SE TUTTI GLI ALTRI CU IN SORVEGLIANZA
				 * SONO STATI CHIUSI O SE NE SONO PRESENTI INSERITI LO STESSO GIORNO
				 */

				StringBuffer sql = new StringBuffer("");
				sql.append(" SELECT t.closed,t.id_controllo_ufficiale,t.assigned_date,t.data_prossimo_controllo "
						+ " FROM ticket t  WHERE t.ticketid > 0  AND t.tipologia = 3  and t.org_id = ?  and "
						+ " (t.provvedimenti_prescrittivi =  5 ) "
						+ " and (t.assigned_date = ? or (t.closed is null and t.isaggiornata_categoria = false)) and trashed_date is null ");
				if (assetId != -1 && assetId != 0) {
					sql.append(" and link_asset_id = " + assetId);
				}
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, orgId);
				pst.setTimestamp(2, dataControlloUfficiale);

				rs = pst.executeQuery();
				// boolean cuAperto = false;
				while (rs.next()) {

					String idCu = rs.getString(2);
					// Timestamp data_prossimo_controllo = rs.getTimestamp(4);
						messaggio += "" + idCu + "\n";
						//break;
					

				}
				rs.close();
				pst.close();

				/**
				 * CONTROLLO SULLE DATE PER UN CONTROLLO UFFICIALE IN
				 * SORVEGLIANZA LA DATA DI INSERIMENTO DEL NUOVO CONTROLLO DEVE
				 * ESSERE MAGGIORE DELLA DATA DEL PROSSIMO CONTROLLO - 20 % E
				 * MINORE DELLA DATA DEL PROSSIMO CONTROLLO
				 */

				StringBuffer sql1 = new StringBuffer("");
				sql1.append("SELECT a.id, t.assigned_date,T.ID_CONTROLLO_UFFICIALE,T.DATA_PROSSIMO_CONTROLLO, t.categoria_rischio "
						+ " FROM ticket t left join audit a on (a.id_controllo=t.id_controllo_ufficiale and a.trashed_date is null)"
						+ " WHERE t.ticketid > 0  AND t.tipologia = 3  and t.org_id = ?  "
						+ " and  (t.provvedimenti_prescrittivi =  5) "
						+ " and t.trashed_date is null order by t.assigned_date desc ");
				pst = db.prepareStatement(sql1.toString());
				pst.setInt(1, orgId);

				rs = pst.executeQuery();
				if (rs.next()) {
					Timestamp fromTo = null;
					Integer id_audit = rs.getInt(1);
					Timestamp dataCu = rs.getTimestamp(2);
					Timestamp data_prossimoCu = rs.getTimestamp(4);
					Integer categoriaRischio = rs.getInt(5);
					int durataProssimoCu = 0;
					switch (categoriaRischio) {
					case 1: {
						durataProssimoCu = Integer.parseInt(ApplicationProperties
								.getProperty("SORVEGLIANZA_DURATA_CATEGORIA_1"));
						break;
					}
					case 2: {
						durataProssimoCu = Integer.parseInt(ApplicationProperties
								.getProperty("SORVEGLIANZA_DURATA_CATEGORIA_2"));
						break;
					}
					case 3: {
						durataProssimoCu = Integer.parseInt(ApplicationProperties
								.getProperty("SORVEGLIANZA_DURATA_CATEGORIA_3"));
						break;
					}
					case 4: {
						durataProssimoCu = Integer.parseInt(ApplicationProperties
								.getProperty("SORVEGLIANZA_DURATA_CATEGORIA_4"));
						break;
					}
					case 5: {
						durataProssimoCu = Integer.parseInt(ApplicationProperties
								.getProperty("SORVEGLIANZA_DURATA_CATEGORIA_5"));
						break;
					}
					default:
						break;
					}

					if (data_prossimoCu != null && !data_nuovo_controllo.equals("")) {

						fromTo = new Timestamp(data_prossimoCu.getTime());

						String idC = rs.getString(3);

						int tolleranza = (durataProssimoCu * Integer.parseInt(ApplicationProperties
								.getProperty("SORVEGLIANZA_TOLLERANZA"))) / 100;
						/**
						 * SE DATA INIZIO CONTROLLO e ANTECEDENTE ALLA DATA
						 * PROSSIMO CONTROLLO - 30 GG IL SISTEMA GENERERa UN
						 * MESSAGGIO NON BLOCCANTE. (VALE SOLO PER I CONTROLLI
						 * IN SORVEGLIANZA)
						 */

						Date data = new Date(fromTo.getTime());
						data.setMonth(data.getMonth() - tolleranza);
						if (dataControlloUfficiale.before(data)) {
							Date d = new Date(fromTo.getTime());
							d.setMonth(d.getMonth() - tolleranza);
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							messaggio2 = "" + sdf.format(data);
						}

					}

				}
				rs.close();
				pst.close();

			}

			mess[0] = messaggio;
			mess[1] = messaggio2;
			mess[2] = messaggio3;
			mess[3] = messaggio4;
			mess[4] = messaggio5;
			mess[5] = Integer.toString(tipologia);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return mess;
	}

	public String[] controlloInserimentoCuSorveglianzaOpu(int orgId, boolean isPianoMonitoraggio,
			boolean isSorveglianza, String data_nuovo_controllo, int assetId) throws ParseException {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String messaggio = "";
		String messaggio2 = "";
		String messaggio3 = "";
		String messaggio4 = "";
		String[] mess = new String[4];

		try {

			if (orgId > 0) {
				db = GestoreConnessioni.getConnection();
				if (db != null) {
					ResultSet rs2 = null;

					/**
					 * BLOCCO 1. DATA CONTROLLO MAGGIORE DELLADATAATTUALE
					 *
					 */
					Timestamp dataControlloUfficiale = new Timestamp((new SimpleDateFormat("dd/MM/yyyy")).parse(
							data_nuovo_controllo).getTime());
					Timestamp time_attuale = new Timestamp(System.currentTimeMillis());
					if (dataControlloUfficiale.after(time_attuale))
						messaggio4 = "Attenzione la data inizio controllo e maggiore della data attuale";

					/**
					 * BLOCCO 2. DATA DEL CONTROLLO NON e DELL'ANNO CORRENTE CON
					 * LIMITE DI 15 GG PER L'ANNO SUCCESSIVO.
					 * 
					 */
					Timestamp data_attuale_time = new Timestamp(System.currentTimeMillis());
					Calendar calCorrente = GregorianCalendar.getInstance();
					Date dataCorrente = new Date(System.currentTimeMillis());
					int tolleranzaGiorni = Integer
							.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
					dataCorrente.setDate(dataCorrente.getDate() - tolleranzaGiorni);
					calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
					int anno_corrente = calCorrente.get(Calendar.YEAR);

					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(new Date(dataControlloUfficiale.getTime()));
					int annoControllo = cal.get(Calendar.YEAR);

					if (annoControllo != anno_corrente) {

						if (annoControllo > anno_corrente)
							messaggio3 = ApplicationProperties.getProperty("BLOCCO4_MESSAGGIO");
						else
							messaggio3 = ApplicationProperties.getProperty("BLOCCO1_MESSAGGIO");
					}

					/* se in sorveglianza */
					if (isSorveglianza == true) {

						/**
						 * PRIMA DI INSERIRE UN NUOVO CONTROLLO UFFICIALE IN
						 * SORVEGLIANZA VERIFICO SE TUTTI GLI ALTRI CU IN
						 * SORVEGLIANZA SONO STATI CHIUSI
						 */

						StringBuffer sql = new StringBuffer("");
						sql.append(" SELECT t.closed,t.id_controllo_ufficiale,t.assigned_date,t.data_prossimo_controllo "
								+ " FROM ticket t  WHERE t.ticketid > 0  AND t.tipologia = 3  and t.id_stabilimento = ?  and "
								+ " (t.provvedimenti_prescrittivi =  5 ) "
								+ " and (t.assigned_date = ? or (t.closed is null and t.isaggiornata_categoria = false )) and trashed_date is null ");
						if (assetId != -1 && assetId != 0) {
							sql.append(" and link_asset_id = " + assetId);
						}
						pst = db.prepareStatement(sql.toString());
						pst.setInt(1, orgId);
						pst.setTimestamp(2, dataControlloUfficiale);

						rs = pst.executeQuery();
						boolean cuAperto = false;
						while (rs.next()) {

							String idCu = rs.getString(2);
							Timestamp data_prossimo_controllo = rs.getTimestamp(4);
								messaggio += "" + idCu + "\n";
								//break;
				}
						rs.close();
						pst.close();

						/**
						 * CONTROLLO SULLE DATE PER UN CONTROLLO UFFICIALE IN
						 * SORVEGLIANZA LA DATA DI INSERIMENTO DEL NUOVO
						 * CONTROLLO DEVE ESSERE MAGGIORE DELLA DATA DEL
						 * PROSSIMO CONTROLLO - 20 % E MINORE DELLA DATA DEL
						 * PROSSIMO CONTROLLO
						 */

						StringBuffer sql1 = new StringBuffer("");
						sql1.append("SELECT a.id, t.assigned_date,T.ID_CONTROLLO_UFFICIALE,T.DATA_PROSSIMO_CONTROLLO, t.categoria_rischio "
								+ " FROM ticket t left join audit a on (a.id_controllo=t.id_controllo_ufficiale and a.trashed_date is null)"
								+ " WHERE t.ticketid > 0  AND t.tipologia = 3  and t.id_stabilimento = ?  "
								+ " and  (t.provvedimenti_prescrittivi =  5) "
								+ " and t.trashed_date is null order by t.assigned_date desc ");
						pst = db.prepareStatement(sql1.toString());
						pst.setInt(1, orgId);

						rs = pst.executeQuery();

						if (rs.next()) {
							Timestamp fromTo = null;
							Integer id_audit = rs.getInt(1);
							Timestamp dataCu = rs.getTimestamp(2);
							Timestamp data_prossimoCu = rs.getTimestamp(4);
							Integer categoriaRischio = rs.getInt(5);
							int durataProssimoCu = 0;
							switch (categoriaRischio) {
							case 1: {
								durataProssimoCu = 48;
								break;
							}
							case 2: {
								durataProssimoCu = 36;
								break;
							}
							case 3: {
								durataProssimoCu = 24;
								break;
							}
							case 4: {
								durataProssimoCu = 12;
								break;
							}
							case 5: {
								durataProssimoCu = 6;
								break;
							}
							default:
								break;
							}

							if (data_prossimoCu != null && !data_nuovo_controllo.equals("")) {

								fromTo = new Timestamp(data_prossimoCu.getTime());

								String idC = rs.getString(3);

								/**
								 * SE DATA INIZIO CONTROLLO e ANTECEDENTE ALLA
								 * DATA PROSSIMO CONTROLLO - 30 GG IL SISTEMA
								 * GENERERa UN MESSAGGIO NON BLOCCANTE. (VALE
								 * SOLO PER I CONTROLLI IN SORVEGLIANZA)
								 */

								Date data = new Date(fromTo.getTime());
								data.setDate(data.getDate() - 30);
								if (dataControlloUfficiale.before(data)) {
									Date d = new Date(fromTo.getTime());
									d.setMonth(d.getMonth() - 1);
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									messaggio2 = "" + sdf.format(data);
								}

							}

						}
						rs.close();
						pst.close();

					}

				}
			}
			mess[0] = messaggio;
			mess[1] = messaggio2;
			mess[2] = messaggio3;
			mess[3] = messaggio4;
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return mess;
	}

	public String[] controlloInserimentoCuSorveglianzaAlt(int altId, boolean isPianoMonitoraggio,
			boolean isSorveglianza, String data_nuovo_controllo, int assetId) throws ParseException {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String messaggio = "";
		String messaggio2 = "";
		String messaggio3 = "";
		String messaggio4 = "";
		String[] mess = new String[4];

		try {

			if (altId > 0) {
				db = GestoreConnessioni.getConnection();
				if (db != null) {
					ResultSet rs2 = null;

					/**
					 * BLOCCO 1. DATA CONTROLLO MAGGIORE DELLADATAATTUALE
					 *
					 */
					Timestamp dataControlloUfficiale = new Timestamp((new SimpleDateFormat("dd/MM/yyyy")).parse(
							data_nuovo_controllo).getTime());
					Timestamp time_attuale = new Timestamp(System.currentTimeMillis());
					if (dataControlloUfficiale.after(time_attuale))
						messaggio4 = "Attenzione la data inizio controllo e maggiore della data attuale";

					/**
					 * BLOCCO 2. DATA DEL CONTROLLO NON e DELL'ANNO CORRENTE CON
					 * LIMITE DI 15 GG PER L'ANNO SUCCESSIVO.
					 * 
					 */
					Timestamp data_attuale_time = new Timestamp(System.currentTimeMillis());
					Calendar calCorrente = GregorianCalendar.getInstance();
					Date dataCorrente = new Date(System.currentTimeMillis());
					int tolleranzaGiorni = Integer
							.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
					dataCorrente.setDate(dataCorrente.getDate() - tolleranzaGiorni);
					calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
					int anno_corrente = calCorrente.get(Calendar.YEAR);

					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(new Date(dataControlloUfficiale.getTime()));
					int annoControllo = cal.get(Calendar.YEAR);

					if (annoControllo != anno_corrente) {

						if (annoControllo > anno_corrente)
							messaggio3 = ApplicationProperties.getProperty("BLOCCO4_MESSAGGIO");
						else
							messaggio3 = ApplicationProperties.getProperty("BLOCCO1_MESSAGGIO");
					}

					/* se in sorveglianza */
					if (isSorveglianza == true) {

						/**
						 * PRIMA DI INSERIRE UN NUOVO CONTROLLO UFFICIALE IN
						 * SORVEGLIANZA VERIFICO SE TUTTI GLI ALTRI CU IN
						 * SORVEGLIANZA SONO STATI CHIUSI
						 */

						StringBuffer sql = new StringBuffer("");
						sql.append(" SELECT t.closed,t.id_controllo_ufficiale,t.assigned_date,t.data_prossimo_controllo "
								+ " FROM ticket t  WHERE t.ticketid > 0  AND t.tipologia = 3  and t.alt_id = ?  and "
								+ " (t.provvedimenti_prescrittivi =  5 ) "
								+ " and (t.assigned_date = ? or (t.closed is null and t.isaggiornata_categoria = false )) and trashed_date is null ");
						if (assetId != -1 && assetId != 0) {
							sql.append(" and link_asset_id = " + assetId);
						}
						pst = db.prepareStatement(sql.toString());
						pst.setInt(1, altId);
						pst.setTimestamp(2, dataControlloUfficiale);

						rs = pst.executeQuery();
						boolean cuAperto = false;
						while (rs.next()) {

							
							String idCu = rs.getString(2);
							Timestamp data_prossimo_controllo = rs.getTimestamp(4);
								messaggio += "" + idCu + "\n";
							//	break;
					
						}
						rs.close();
						pst.close();

						/**
						 * CONTROLLO SULLE DATE PER UN CONTROLLO UFFICIALE IN
						 * SORVEGLIANZA LA DATA DI INSERIMENTO DEL NUOVO
						 * CONTROLLO DEVE ESSERE MAGGIORE DELLA DATA DEL
						 * PROSSIMO CONTROLLO - 20 % E MINORE DELLA DATA DEL
						 * PROSSIMO CONTROLLO
						 */

						StringBuffer sql1 = new StringBuffer("");
						sql1.append("SELECT a.id, t.assigned_date,T.ID_CONTROLLO_UFFICIALE,T.DATA_PROSSIMO_CONTROLLO, t.categoria_rischio "
								+ " FROM ticket t left join audit a on (a.id_controllo=t.id_controllo_ufficiale and a.trashed_date is null)"
								+ " WHERE t.ticketid > 0  AND t.tipologia = 3  and t.alt_id = ?  "
								+ " and  (t.provvedimenti_prescrittivi =  5) "
								+ " and t.trashed_date is null order by t.assigned_date desc ");
						pst = db.prepareStatement(sql1.toString());
						pst.setInt(1, altId);

						rs = pst.executeQuery();

						if (rs.next()) {
							Timestamp fromTo = null;
							Integer id_audit = rs.getInt(1);
							Timestamp dataCu = rs.getTimestamp(2);
							Timestamp data_prossimoCu = rs.getTimestamp(4);
							Integer categoriaRischio = rs.getInt(5);
							int durataProssimoCu = 0;
							switch (categoriaRischio) {
							case 1: {
								durataProssimoCu = 48;
								break;
							}
							case 2: {
								durataProssimoCu = 36;
								break;
							}
							case 3: {
								durataProssimoCu = 24;
								break;
							}
							case 4: {
								durataProssimoCu = 12;
								break;
							}
							case 5: {
								durataProssimoCu = 6;
								break;
							}
							default:
								break;
							}

							if (data_prossimoCu != null && !data_nuovo_controllo.equals("")) {

								fromTo = new Timestamp(data_prossimoCu.getTime());

								String idC = rs.getString(3);

								/**
								 * SE DATA INIZIO CONTROLLO e ANTECEDENTE ALLA
								 * DATA PROSSIMO CONTROLLO - 30 GG IL SISTEMA
								 * GENERERa UN MESSAGGIO NON BLOCCANTE. (VALE
								 * SOLO PER I CONTROLLI IN SORVEGLIANZA)
								 */

								Date data = new Date(fromTo.getTime());
								data.setDate(data.getDate() - 30);
								if (dataControlloUfficiale.before(data)) {
									Date d = new Date(fromTo.getTime());
									d.setMonth(d.getMonth() - 1);
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									messaggio2 = "" + sdf.format(data);
								}

							}

						}
						rs.close();
						pst.close();

					}

				}
			}
			mess[0] = messaggio;
			mess[1] = messaggio2;
			mess[2] = messaggio3;
			mess[3] = messaggio4;
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return mess;
	}

	public static boolean getStatoFormali(boolean nc_formali, String idControllo, String attivita, boolean haveNC) {
		boolean stato = false;
		boolean haveFollowup = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String[] idAttivita = attivita.split(";");
				String select = "select * from ticket where id_controllo_ufficiale ilike ? and tipologia = 15 and tipo_nc = ? ";
				if (!attivita.equals("")) {
					select += "and ticketid in (";
					for (int i = 0; i < idAttivita.length - 1; i++) {
						select += idAttivita[i] + ",";
					}
					select += idAttivita[idAttivita.length - 1] + ")";
				}
				pst = db.prepareStatement(select);
				pst.setString(1, "%" + idControllo);
				pst.setInt(2, 1);
				rs = pst.executeQuery();
				if (rs.next()) {
					haveFollowup = true;
				} else {
					haveFollowup = false;
				}

				if ((nc_formali == true && haveFollowup == true && haveNC == false)
						|| (nc_formali == false && haveFollowup == false && haveNC == false)) {
					stato = true;
				} else {
					stato = false;
				}

			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return stato;

	}

	public static boolean getStatoSignificative(boolean nc_formali, String idControllo, String attivita, boolean haveNC) {
		boolean stato = false;
		boolean haveFollowup = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String[] idAttivita = attivita.split(";");
				String select = "select * from ticket where id_controllo_ufficiale ilike ? and tipologia = 15 and tipo_nc = ? ";

				if (!attivita.equals("")) {
					select += "and ticketid in (";
					for (int i = 0; i < idAttivita.length - 1; i++) {
						select += idAttivita[i] + ",";
					}
					select += idAttivita[idAttivita.length - 1] + ")";
				}
				pst = db.prepareStatement(select);
				pst.setString(1, "%" + idControllo);
				pst.setInt(2, 2);
				rs = pst.executeQuery();
				if (rs.next()) {
					haveFollowup = true;
				} else {
					haveFollowup = false;
				}

				if ((nc_formali == true && haveFollowup == true && haveNC == false)
						|| (nc_formali == false && haveFollowup == false && haveNC == false)) {
					stato = true;
				} else {
					stato = false;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return stato;

	}

	public static boolean getStatoGravi(boolean nc_formali, String idControllo, String attivita, boolean haveNC) {
		boolean stato = false;
		boolean haveFollowup = false;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String[] idAttivita = attivita.split(";");
				String select = "select * from ticket where id_controllo_ufficiale ilike ? and tipologia in (1,9,6)  and tipo_nc = ? ";
				if (!attivita.equals("")) {
					select += "and ticketid in (";
					for (int i = 0; i < idAttivita.length - 1; i++) {
						select += idAttivita[i] + ",";
					}
					select += idAttivita[idAttivita.length - 1] + ")";
				}
				pst = db.prepareStatement(select);
				pst.setString(1, "%" + idControllo);
				pst.setInt(2, 3);
				rs = pst.executeQuery();
				if (rs.next()) {
					haveFollowup = true;
				} else {
					haveFollowup = false;
				}

				if ((nc_formali == true && haveFollowup == true && haveNC == false)
						|| (nc_formali == false && haveFollowup == false && haveNC == false)) {
					stato = true;
				} else {
					stato = false;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return stato;

	}

	public static boolean deleteAttivitaNc(String idNc) {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				if (!"".equals(idNc)) {
					String select = "update ticket set trashed_date = current_date where ticketid in (select ticketid from ticket where id_nonconformita = ? and trashed_date is null and tipo_nc not in (select tipologia from salvataggio_nc_note where idticket= ? ) )";
					pst = db.prepareStatement(select);
					pst.setInt(1, Integer.parseInt(idNc));
					pst.setInt(2, Integer.parseInt(idNc));
					pst.execute();
				}

			}
		} catch (SQLException e) {
			logger.warning("Errore in PopolaCombo metodo deleteAttivitaNc : " + e.getMessage());
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return true;
	}

	public static boolean deleteAttivitaNc(String nc, String idControllo, String tipo_nc, String doSubmit) {

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			if (!doSubmit.equals("1")) {
				if (!"".equalsIgnoreCase(nc)) {
					db = GestoreConnessioni.getConnection();
					if (db != null) {
						String[] idAttivita = nc.split(";");
						String select = "update ticket set trashed_date = current_date where id_controllo_ufficiale ilike ? and tipologia in(1,9,6,15) and "
								+ "(id_nonconformita is null or id_nonconformita =-1)  and ticketid in (";
						for (int i = 0; i < idAttivita.length - 1; i++) {
							select += idAttivita[i] + ",";
						}
						select += idAttivita[idAttivita.length - 1] + ")";

						if (!tipo_nc.equals("-1")) {
							select += " and tipo_nc = ? ";
						}

						pst = db.prepareStatement(select);
						pst.setString(1, "%" + idControllo);
						if (!tipo_nc.equals("-1")) {
							pst.setInt(2, Integer.parseInt(tipo_nc));
						}
						pst.execute();
					}
				}
			}

		} catch (SQLException e) {
			logger.warning("Errore in PopolaCombo metodo deleteAttivitaNc : " + e.getMessage());
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return true;
	}

	public String checkEsistenzaImpresa(String ragioneSociale, String partitaIva, String citta, String indirizzo,
			String codFiscale, int tipologia, int type) {
		String toReturn = "false";

		String sql = "select o.name , o.partita_iva , oa.city, oa.addrline1 , o.codice_fiscale as cf "
				+ "from organization o, organization_address oa "
				+ "where o.org_id = oa.org_id and o.trashed_date is null " + "and oa.address_type = " + type + " "
				+ "and o.tipologia =" + tipologia + " and o.name = ? ";
		if (!"".equals(codFiscale)) {
			sql = sql + " and (o.partita_iva = ? or codice_fiscale = ?)";
		} else {
			sql = sql + " and o.partita_iva = ?";
		}
		Connection db = null;

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, ragioneSociale);
				if (!"".equals(codFiscale)) {
					pst.setString(2, partitaIva);
					pst.setString(3, codFiscale);
				} else {
					pst.setString(2, partitaIva);
				}
				ResultSet rs = pst.executeQuery();
				int count = 0;
				while (rs.next()) {
					if (count < 3) {
						String ragioneS = rs.getString("name");
						String piva = rs.getString("partita_iva");
						String city = rs.getString("city");
						String ind = rs.getString("addrline1");
						String codfisc = rs.getString("cf");

						if (ragioneS != null && ragioneSociale.equalsIgnoreCase(ragioneS.trim())) {
							count = count + 1;

						}
						if (piva != null && partitaIva.equalsIgnoreCase(piva.trim())) {
							count = count + 1;
						}
						if (city != null && citta.equalsIgnoreCase(city.trim())) {
							count = count + 1;
						}
						if (ind != null && indirizzo.equalsIgnoreCase(ind.trim())) {
							count = count + 1;
						}
						if (codfisc != null && codFiscale.equalsIgnoreCase(codfisc.trim())) {
							count = count + 1;
						}
						if (count >= 3) {
							break;
						}

					}
					if (count >= 3) {
						break;
					} else {
						count = 0;
					}
				}
				pst.close();
				rs.close();
				if (count >= 3)
					return "true";
				return "false";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return toReturn;

	}

	public static LookupList costruisci_lookup_rel_ateco_linea_attivita_per_codice_istat(String codice_istat) {
		Connection db = null;
		ArrayList<RelAtecoLineeAttivita> lista;
		LookupList ret = new LookupList();

		if (codice_istat.equals("")) {
			ret.addItem(-1, "Selezionare prima il codice Ateco");
		} else {
			try {
				db = GestoreConnessioni.getConnection();
				if (db != null) {
					lista = RelAtecoLineeAttivita.load_all_rel_ateco_linee_attivita(db);

					for (RelAtecoLineeAttivita rel_ateco_linea : lista) {
						if (rel_ateco_linea.getCodice_istat().equals(codice_istat)) {
							if (!rel_ateco_linea.getLinea_attivita().isEmpty())
								ret.addItem(rel_ateco_linea.getId(), rel_ateco_linea.getCodice_istat() + " : "
										+ rel_ateco_linea.getCategoria() + " - " + rel_ateco_linea.getLinea_attivita());
							else
								ret.addItem(rel_ateco_linea.getId(), rel_ateco_linea.getCodice_istat() + " : "
										+ rel_ateco_linea.getCategoria());
						}
					}
				}
			} catch (LoginRequiredException e) {

				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				return (null);
			} finally {
				GestoreConnessioni.freeConnection(db);
			}
		}
		return ret;
	}

	public static Object[] costruisci_obj_rel_ateco_linea_attivita_per_codice_istat(String codice_istat,
			String campo_combo_da_costruire) {
		Object[] ret = new Object[3];

		Connection db = null;
		int i = -1;
		ArrayList<RelAtecoLineeAttivita> lista;
		if (codice_istat.equals("")) {
			Object[] ind = new Object[1];
			Object[] val = new Object[1];
			ind[0] = -1;
			val[0] = "-- Selezionare prima il codice Ateco --";
			ret[0] = ind;
			ret[1] = val;
			ret[2] = campo_combo_da_costruire;
		} else {
			try {
				db = GestoreConnessioni.getConnection();
				if (db != null) {
					lista = RelAtecoLineeAttivita.load_rel_ateco_linee_attivita_per_codice_istat(codice_istat, db);

					Object[] ind = new Object[lista.size()];
					Object[] val = new Object[lista.size()];

					for (RelAtecoLineeAttivita linea : lista) {
						i++;
						ind[i] = linea.getId();

						if (!linea.getLinea_attivita().isEmpty())
							// val[i] = linea.getCodice_istat() + " : " +
							// linea.getCategoria() + " - " +
							// linea.getLinea_attivita();
							val[i] = linea.getCategoria() + " - " + linea.getLinea_attivita();
						else
							// val[i] = linea.getCodice_istat() + " : " +
							// linea.getCategoria();
							val[i] = linea.getCategoria();
					}
					ret[0] = ind;
					ret[1] = val;
					ret[2] = campo_combo_da_costruire;
				}
			} catch (LoginRequiredException e) {

				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				return (null);
			} finally {
				GestoreConnessioni.freeConnection(db);
			}
		}
		return ret;

	}

	public static boolean controlla_linea_attivita_definita_per_orgId(String orgId_str) {
		int orgId = Integer.parseInt(orgId_str);
		Connection db = null;
		boolean ret = false;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				ret = RelAtecoLineeAttivita.controlla_linea_attivita_definita_per_orgId(orgId, db);
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}

	public static String verifica_esistenza_azienda(String cod_azienda) {

		Connection db = null;
		boolean ret = false;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement("select * from aziende where cod_azienda = ?");
				pst.setString(1, cod_azienda);
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					return cod_azienda;
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return "";
	}

	public static Object[] load_linee_attivita_per_org_id(String orgId, String campo_combo_da_costruire,
			String default_value) {
		Object[] ret = new Object[4];

		Connection db = null;
		int i = -1;
		ArrayList<LineeAttivita> lista;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				lista = LineeAttivita.load_linee_attivita_per_org_id(orgId, db);

				Object[] ind = new Object[lista.size()];
				Object[] val = new Object[lista.size()];

				for (LineeAttivita linea : lista) {
					i++;
					ind[i] = linea.getId();

					if (linea.getLinea_attivita()!=null && !linea.getLinea_attivita().isEmpty()){
						
						if (linea.isMappato())
							val[i] = linea.getAttivita();
						else	
							val[i] = linea.getCodice_istat() + " : " + linea.getCategoria() + " - "	+ linea.getLinea_attivita() + " - " + linea.getAttivita();
					}
					// val[i] = linea.getCategoria() + " - " +
					// linea.getLinea_attivita();
					else if (linea.getAttivita() != null & !"".equals(linea.getAttivita())){
						if (linea.isMappato())
							val[i] = linea.getAttivita();
						else	
						val[i] = linea.getCodice_istat() + " : " + linea.getCategoria() + " - " + linea.getAttivita();
					}
					else{
						
						if (linea.isMappato())
							val[i] = linea.getAttivita();
						else	
						val[i] = linea.getCodice_istat() + " : " + linea.getCategoria();
					// val[i] = linea.getCategoria();
					}
				}
				ret[0] = ind;
				ret[1] = val;
				ret[2] = campo_combo_da_costruire;
				if (default_value != null)
					ret[3] = default_value;
				else
					ret[3] = "-1";
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}

	public static int controlloEsistenzaCU(String data, int orgId) {
		Connection db = null;
		int idcontrollo = -1;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select ticketid from ticket where org_id =? and assigned_date = ? and org_id > 0";
				Timestamp time = null;
				if (data != null && !"".equals(data)) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					time = new Timestamp(sdf.parse(data).getTime());
					PreparedStatement pst = db.prepareStatement(sel);
					pst.setInt(1, orgId);
					pst.setTimestamp(2, time);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
						idcontrollo = rs.getInt(1);
				}
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return idcontrollo;
	}

	public static int controlloEsistenzaCUOpu(String data, int orgId) {
		Connection db = null;
		int idcontrollo = -1;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				String sel = "select ticketid from ticket where id_stabilimento =? and assigned_date = ? ";
				Timestamp time = null;
				if (data != null && !"".equals(data)) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					time = new Timestamp(sdf.parse(data).getTime());
					PreparedStatement pst = db.prepareStatement(sel);
					pst.setInt(1, orgId);
					pst.setTimestamp(2, time);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
						idcontrollo = rs.getInt(1);
				}
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return idcontrollo;
	}

	public static Object[] getContenutoCombo(String table) {
		Connection db = null;
		Object[] descrizioni;
		Object[] valori;
		Object[] ret = new Object[2];

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				LookupList combo = new LookupList(db, table);
				descrizioni = new Object[combo.size()];
				valori = new Object[combo.size()];

				for (int i = 0; i < combo.size(); i++) {
					LookupElement elm = (LookupElement) combo.get(i);
					descrizioni[i] = elm.getDescription();
					valori[i] = elm.getCode();
				}
				ret[0] = descrizioni;
				ret[1] = valori;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;
	}

	public boolean delete_campione(String ticketid) {
		boolean deleted = false;
		Connection db = null;
		int ticketId = Integer.parseInt(ticketid);
		
		
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
		
					String sql = "update ticket set trashed_date=now() where ticketid=?";
					PreparedStatement pst = db.prepareStatement(sql);
					pst.setInt(1, ticketId);
					int status = pst.executeUpdate();
					if (status > 0)
						deleted = true;
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return deleted;
	}

	public static String[] getUltimaPosizioneFromIp(String ip) {
		String[] coordinate = null;
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			org.aspcfs.modules.admin.base.User user = new User();
			user.setIp(ip);
			if (db != null) {
				ArrayList<String> s = user.getCoordinateUltimoAccesso(db);
				if (s.size() > 0)
					coordinate = new String[3];
				int i = 0;
				for (String c : s) {
					coordinate[i] = c;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return coordinate;

	}

	public static ArrayList<String> getCoefficienti(String id, int idAttivita, int idStruttura) {
		ArrayList<String> c = new ArrayList<String>();
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			/*
			 * String sql =
			 * "select si.id_struttura,si.descr_sezione,si.descr_piano,si.descr_attivita,si.id_indicatore,c.coefficiente "
			 * +
			 * "from dpat_coefficiente c join dpat_struttura_indicatore si on c.id_indicatore=si.id_indicatore "
			 * +
			 * "where si.descr_attivita='"+idAttivita+"' and si.id_struttura="+
			 * idStruttura; pst = db.prepareStatement(sql); rs =
			 * pst.executeQuery(); while (rs.next()){
			 * c.add("struttura_"+rs.getInt
			 * (1)+"_s_"+rs.getString(2)+"_p_"+rs.getString
			 * (3)+"_a_"+rs.getString
			 * (4)+"_i_"+rs.getString(5)+";"+rs.getDouble(6)); }
			 */

			// if (c.size()==0){
			String[] s = id.split("_");
			String sql = "select c.id_indicatore, c.coefficiente from dpat_coefficiente c "
					+ "join dpat_indicatore i on c.id_indicatore=i.id "
					+ "join dpat_attivita a on i.id_attivita=a.id where a.id=" + idAttivita
					+ " and a.enabled=true and i.enabled=true";
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				c.add(s[0] + "_" + s[1] + "_" + s[2] + "_" + s[3] + "_" + s[4] + "_" + s[5] + "_" + s[6] + "_" + s[7]
						+ "_" + s[8] + "_" + rs.getInt(1) + ";" + rs.getDouble(2));
			}
			// }
			pst.close();
			rs.close();
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return c;
	}

	public static void updateSommaUi(String id, int ui, double somma, int idDpat, int idUtente) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String s[] = id.split("_");
		int idSI = -1;
		try {
			db = GestoreConnessioni.getConnection();
			String sql = "select id from dpat_struttura_indicatore where id_struttura=" + s[1] + " and id_indicatore="
					+ s[9] + " and enabled=true";
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				idSI = rs.getInt("id");
			}

			if (idSI == -1) {
				pst = db.prepareStatement("insert into dpat_struttura_indicatore (id_struttura,id_indicatore,ui,somma_ui,descr_indicatore,descr_attivita,"
						+ "descr_piano,descr_sezione,entered,entered_by,modified,modified_by,enabled,id_dpat) values (?,?,?,?,?,?,?,?,now(),?,now(),?,?,?)");
				pst.setInt(1, Integer.parseInt(s[1]));
				pst.setInt(2, Integer.parseInt(s[9]));
				pst.setInt(3, ui);
				pst.setDouble(4, somma);
				pst.setString(5, s[9]);
				pst.setString(6, s[7]);
				pst.setString(7, s[5]);
				pst.setString(8, s[3]);
				pst.setInt(9, idUtente);
				pst.setInt(10, idUtente);
				pst.setBoolean(11, true);
				pst.setInt(12, idDpat);
				pst.executeUpdate();
			} else {
				pst = db.prepareStatement("update dpat_struttura_indicatore set ui=?,modified_by=?,modified=now() where id=?");
				pst.setInt(1, ui);
				pst.setInt(2, idUtente);
				pst.setInt(3, idSI);
				pst.executeUpdate();
			}
			pst = db.prepareStatement("update dpat_struttura_indicatore set somma_ui=?,modified_by=?,modified=now() where descr_attivita='"
					+ s[7] + "' and id_struttura=" + s[1]);
			pst.setDouble(1, somma);
			pst.setInt(2, idUtente);
			pst.executeUpdate();

			rs.close();
			pst.close();
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}

	public static void updateCaricoStruttura(int idDpat, String id, int carico) {
		Connection db = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		String s[] = id.split("_");
		try {
			db = GestoreConnessioni.getConnection();
			stat = db.prepareStatement("select id from dpat_ui_struttura where id_struttura=" + s[2] + " and id_dpat="
					+ idDpat);
			res = stat.executeQuery();
			int idSU = -1;
			while (res.next()) {
				idSU = res.getInt("id");
			}
			if (idSU == -1) {
				stat = db.prepareStatement("insert into dpat_ui_struttura (id_struttura,id_dpat,ui) values (?,?,?)");
				stat.setInt(1, Integer.parseInt(s[2]));
				stat.setInt(2, idDpat);
				stat.setInt(3, carico);
				stat.executeUpdate();
			} else {
				stat = db.prepareStatement("update dpat_ui_struttura set ui=? where id=?");
				stat.setInt(1, carico);
				stat.setInt(2, idSU);
				stat.executeUpdate();
			}
			res.close();
			stat.close();
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}

	public static void updateObiettivoTot(int idDpat, int val) {
		Connection db = null;
		PreparedStatement stat = null;
		try {
			db = GestoreConnessioni.getConnection();
			stat = db.prepareStatement("update dpat set obiettivo_in_ui=" + val + " where id=" + idDpat);
			stat.executeUpdate();
			stat.close();
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}

	public static void updateObiettivoParz(int idInd, int val) {
		Connection db = null;
		PreparedStatement stat = null;
		try {
			db = GestoreConnessioni.getConnection();
			stat = db.prepareStatement("update dpat_indicatore set obiettivo_in_cu=" + val + " where id=" + idInd);
			stat.executeUpdate();
			stat.close();
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}

	private static String checkValueString(String valore) {
		if ("".equals(valore))
			valore = "0";
		return valore;
	}
	
	public static void aggiornaDpatCompetenzeStruttura(int idStruttura, int idIndicatore, int idDpat, boolean flag,
			int userid) throws SQLException {

		Connection db = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			db = GestoreConnessioni.getConnection();

			String sql = "UPDATE dpat_competenze_struttura_indicatore set modified_by=?,modified=?, competenza_attribuita = ? where id_struttura = ? and id_indicatore = ? and id_dpat =? ";
			PreparedStatement pst = db.prepareStatement(sql);

			pst.setInt(1, userid);
			pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pst.setBoolean(3, flag);
			pst.setInt(4, idStruttura);
			pst.setInt(5, idIndicatore);
			pst.setInt(6, idDpat);

			System.out.println("AGGIORNA COMPETENZE STRUTTURA " + pst.toString());
			pst.execute();

			int recordAggiornati = pst.getUpdateCount();
			if (recordAggiornati == 0) {
				String insert = "insert into dpat_competenze_struttura_indicatore (modified_by,modified,competenza_attribuita,id_struttura,id_indicatore,id_dpat) values(?,current_timestamp,?,?,?,?);";
				pst = db.prepareStatement(insert);

				pst.setInt(1, userid);
				pst.setBoolean(2, flag);
				pst.setInt(3, idStruttura);
				pst.setInt(4, idIndicatore);
				pst.setInt(5, idDpat);

				pst.execute();

			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (NumberFormatException g) {
			throw g;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}
	
	public static void aggiornaDpatCompetenzeStrutturaNEW(int idStruttura, int idIndicatore, int idDpat, boolean flag,
			int userid) throws SQLException {

		Connection db = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			db = GestoreConnessioni.getConnection();

			String sql = "UPDATE dpat_competenze_struttura_indicatore set modified_by=?,modified=?, competenza_attribuita = ? where id_struttura = ? and id_indicatore = ? and id_dpat =? ";
			PreparedStatement pst = db.prepareStatement(sql);

			pst.setInt(1, userid);
			pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pst.setBoolean(3, flag);
			pst.setInt(4, idStruttura);
			pst.setInt(5, idIndicatore);
			pst.setInt(6, idDpat);

			System.out.println("AGGIORNA COMPETENZE STRUTTURA " + pst.toString());
			pst.execute();

			int recordAggiornati = pst.getUpdateCount();
			if (recordAggiornati == 0) {
				String insert = "insert into dpat_competenze_struttura_indicatore (modified_by,modified,competenza_attribuita,id_struttura,id_indicatore,id_dpat) values(?,current_timestamp,?,?,?,?);";
				pst = db.prepareStatement(insert);

				pst.setInt(1, userid);
				pst.setBoolean(2, flag);
				pst.setInt(3, idStruttura);
				pst.setInt(4, idIndicatore);
				pst.setInt(5, idDpat);

				pst.execute();

			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (NumberFormatException g) {
			throw g;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}

	public static void aggiornaDatiRisorseStrumentali(int idStruttura, String nomeCampo, String valoreCampo,
			int idDpatRisorseStrumentali) throws SQLException, NumberFormatException {
		Connection db = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			db = GestoreConnessioni.getConnection();

			String sql = "UPDATE dpat_risorse_strumentali_strutture set " + nomeCampo
					+ " = ? where   id_struttura = ? and id_risorse_strumentali = ?  ";
			PreparedStatement pst = db.prepareStatement(sql);
			if (nomeCampo.equalsIgnoreCase("altro_descrizione")) {
				pst.setString(1, valoreCampo);
			} else {
				pst.setInt(1, Integer.parseInt(valoreCampo));
			}
			pst.setInt(2, idStruttura);
			pst.setInt(3, idDpatRisorseStrumentali);
			pst.execute();

			int rowAggiornate = pst.getUpdateCount();
			if (rowAggiornate == 0) {
				sql = "insert into dpat_risorse_strumentali_strutture ( " + nomeCampo
						+ ",id_risorse_strumentali,id_struttura) values (?,?,?)";
				pst = db.prepareStatement(sql);

				if (nomeCampo.equalsIgnoreCase("altro_descrizione")) {
					pst.setString(1, valoreCampo);
				} else {
					pst.setInt(1, Integer.parseInt(valoreCampo));
				}
				pst.setInt(2, idDpatRisorseStrumentali);

				pst.setInt(3, idStruttura);
				pst.execute();
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (NumberFormatException g) {
			throw g;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
	}

	public static int getNumeroFigli(int idStruttura) {
		int c = 0;
		Connection db = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			db = GestoreConnessioni.getConnection();
			String sql = "select count(*) from oia_nodo where trashed_date is null and id_padre=? ";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStruttura);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				c = rs.getInt(1);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException g) {
			g.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return c;
	}

	public static int getNumeroFigliTemp(int idStruttura) {
		int c = 0;
		Connection db = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			db = GestoreConnessioni.getConnection();
			String sql = "select count(*) from oia_nodo_temp where trashed_date is null and id_padre=? ";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStruttura);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				c = rs.getInt(1);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException g) {
			g.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return c;
	}

	public String controlloEsistenzaNumVerbale(String numVerbale, int id_asl) {
		String toReturn = "false";

		numVerbale = numVerbale.replaceAll(" ", "");
		String sql = " select * from ticket where location = ? and trashed_date is null and site_id = ? ";
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, numVerbale);
				pst.setInt(2, id_asl);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					toReturn = "true";
					break;
				}

				pst.close();
				rs.close();
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return toReturn;

	}

	public Boolean checkStatoSDC(int id_sdc) {
		Boolean stato = false;
		Connection db = null;
		String sql = "select completo from dpat_strumento_calcolo where id=?";
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, id_sdc);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					stato = rs.getBoolean(1);
				}

				pst.close();
				rs.close();
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return stato;
	}

	public static org.aspcfs.modules.speditori.base.Organization buildSpeditore(int idSpeditore) {
		org.aspcfs.modules.speditori.base.Organization speditore = null;
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				speditore = new org.aspcfs.modules.speditori.base.Organization(db, idSpeditore);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return speditore;
	}

	public String getProvinciaDaComune(String comune) {
		String provincia = "";
		Connection db = null;
		String sql = "select prov.description from lookup_province prov left join comuni1 com on com.cod_provincia = prov.codistat where com.nome ilike ?";
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, comune);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					provincia = rs.getString(1);
				}

				pst.close();
				rs.close();
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return provincia;
	}



	public String[] getProvinciaeAslDaComune(String comune) {
		String provincia = "";
		Connection db = null;
		String sql = "select prov.description as provincia,asl.description as asl from lookup_province prov  join comuni1 com on com.cod_provincia = prov.codistat join lookup_site_id asl on asl.code = com.codiceistatasl::int where com.nome ilike ?";

		String[] listaValori = new String[2];
		listaValori[0] = "" ;
		listaValori[1] = "" ;

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, comune);
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					listaValori[0]  = rs.getString(1);
					listaValori[1]  = rs.getString(2);
				}

				pst.close();
				rs.close();
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return listaValori;
	}

	public boolean vreificaEsistenzaControlliPerPiani(String idPiano) {

		String toReturn = "";
		Connection con = null;
		String nomeTabellaLookup = "";
		int numCu = 0;

		try {
			String getLookupName = "select count(cu.ticketid) " + "from ticket cu "
					+ "join tipocontrolloufficialeimprese tipi on tipi.idcontrollo = cu.ticketid "
					+ "join lookup_piano_monitoraggio  p on p.code=tipi.pianomonitoraggio and tipi.enabled "
					+ "where cu.tipologia = 3 and cu.trashed_date is null and p.code=?";
			con = GestoreConnessioni.getConnection();
			PreparedStatement pst = con.prepareStatement(getLookupName);
			pst.setInt(1, Integer.parseInt(idPiano));
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				numCu = rs.getInt(1);
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(con);
		}

		return numCu > 0;

	}

	public boolean vreificaEsistenzaControlliPerPianiConfiguratore(String idPiano) {

		String toReturn = "";
		Connection con = null;
		String nomeTabellaLookup = "";
		int numCu = 0;

		try {
			String getLookupName = "select count(cu.ticketid) " + "from ticket cu "
					+ "join tipocontrolloufficialeimprese tipi on tipi.idcontrollo = cu.ticketid "
					+ "join lookup_piano_monitoraggio  p on p.code=tipi.pianomonitoraggio and tipi.enabled "
					+ "where cu.tipologia = 3 and cu.trashed_date is null and p.code_configuratore=?";
			con = GestoreConnessioni.getConnection();
			PreparedStatement pst = con.prepareStatement(getLookupName);
			pst.setInt(1, Integer.parseInt(idPiano));
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				numCu = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(con);
		}

		return numCu > 0;

	}

	public String getHtmlSelectByIdLookup(String idSelect, String tabellaDecodifica, String idToAppend) {

		String toReturn = "";
		Connection con = null;
		String nomeTabellaLookup = "";

		if (idSelect != null && !("").equals(idSelect)) {
			try {
				String getLookupName = "select tabella_lookup from lookup_associazione_classificazione_tabella_lookup where code = ?";
				con = GestoreConnessioni.getConnection();
				PreparedStatement pst = con.prepareStatement(getLookupName);

				pst.setInt(1, Integer.valueOf(idSelect));

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					nomeTabellaLookup = rs.getString("tabella_lookup");
				}

				if (nomeTabellaLookup != null && !("").equals(nomeTabellaLookup)) {
					LookupList list = new LookupList(con, nomeTabellaLookup);
					list.addItem(-1, "Seleziona");
					toReturn = "Specifica classificazione: "
							+ list.getHtmlSelect("idValoreClassificazione_" + idToAppend, -1);
				}

			} catch (LoginRequiredException e) {

				throw e;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				GestoreConnessioni.freeConnection(con);
			}

		}

		return toReturn;
	}

	public static Object[] writeBeanModification(String initial_form, String delta, int user_id, String username,
			String url) throws ParseException, SQLException {
		PGobject json = new PGobject();
		json.setType("json");
		json.setValue(initial_form);

		PGobject d = new PGobject();
		d.setType("json");
		d.setValue(delta);

		Object[] ret = new Object[2];
		Connection db = null;
		PreparedStatement pst = null;

		try {
			db = GestoreConnessioni.getConnectionStorico();
			pst = db.prepareStatement("insert into gisa_storico_bean (entered_by, entered,username,url,initial,delta) values (?,now(),?,?,?,?);");
			pst.setInt(1, user_id);
			pst.setString(2, username);
			pst.setString(3, url);
			pst.setObject(4, json);
			pst.setObject(5, d);
			pst.executeUpdate();
			pst.close();
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				GestoreConnessioni.freeConnectionStorico(db);
			return ret;
		}
	}

	public static void Logout() {
		Login loginAction = new Login();

		WebContext ctx = WebContextFactory.get();
		loginAction.executeCommandLogout(ctx);

	}

	public static boolean aggiornaDatiUtente(String cf, int userId) {

		Connection db = null;
		boolean ok = true;
		try {
			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// db = DbUtil.getConnection(dbName, username,
			// pwd, host);

			if (CfUtil.extractCodiceFiscale(cf).equals(""))
				return false;

			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();

			User user = new User();
			WebContext ctx = WebContextFactory.get();
			String suffisso = (String) ctx.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");

			user.setBuildContact(true);
			user.buildRecord(db, userId, suffisso);
			Contact contactToUpdate = user.getContact();
			contactToUpdate.setCodiceFiscale(cf);
			contactToUpdate.setUserId(userId);

			System.out.println("STO PER AGGIORNARE DATI");

			contactToUpdate.updateCodiceFiscale(db);

			System.out.println("DATI AGGIORNATI");

			user.setContact(contactToUpdate);

			UserBean uBean = (UserBean) ctx.getHttpServletRequest().getSession().getAttribute("User");
			uBean.setUserRecord(user);
			ctx.getHttpServletRequest().getSession().setAttribute("User", uBean);

			// INVIO EMAIL DI NOTIFICA A HD
			HttpServletRequest req = ctx.getHttpServletRequest();
			ApplicationPrefs prefs = (ApplicationPrefs) req.getSession().getServletContext()
					.getAttribute("applicationPrefs");

			// HttpServletRequest req = ctx.getHttpServletRequest();
			// ConnectionElement ce = null;
			// SystemStatus systemStatus = null;
			// ApplicationPrefs prefs = (ApplicationPrefs)
			// req.getSession().getServletContext().getAttribute("applicationPrefs");
			// String ceHost = prefs.get("GATEKEEPER.URL");
			// String ceUser = prefs.get("GATEKEEPER.USER");
			// String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
			//
			// ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			//
			// Object o = ((Hashtable)
			// req.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
			//
			// if(o != null){
			// systemStatus = (SystemStatus) o;
			//
			// systemStatus.updateHierarchy(db);
			// }

		} catch (LoginRequiredException e) {

			throw e;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return ok;
	}

	public static boolean verificaPNAA(int idCu) throws SQLException, ParseException, UnknownHostException {
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		try {

			Thread t = Thread.currentThread();
			db = GestoreConnessioni.getConnection();

			StringBuffer sqlSelect = new StringBuffer();
			PreparedStatement pst = null;
			boolean esitoOperatore = false;

			sqlSelect.append("select * from opu_can_pnaa(?)");
			try {
				pst = db.prepareStatement(sqlSelect.toString());
				pst.setInt(1, idCu);
				rs = pst.executeQuery();

				if (rs.next() && rs.getBoolean(1))
					esitoOperatore = true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return esitoOperatore;
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return true;
	}

	public static ArrayList<DpatPiano> getListaPiani(int idSezione, int anno) {
		Connection db = null;

		ArrayList<DpatPiano> lista = new ArrayList<DpatPiano>();

		try {
			db = GestoreConnessioni.getConnection();
			DpatSezione sez = new DpatSezione();
			sez.buildlistPianiConfiguratore(db, idSezione, anno);
			lista = sez.getElencoPiani();

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return lista;
	}
	
	
	public static ArrayList  getListaPianiAttivitaNewDpat(int idSezione, int anno,String congelato) throws Exception { /*solo i non scaduti */
		Connection db = null;
		
		ArrayList lista = new ArrayList();

		try {
			db = GestoreConnessioni.getConnection();
			DpatSezioneNewBeanInterface sez = null; 
			
			boolean congelatoBool = false;
			try
			{
				congelatoBool = Boolean.parseBoolean(congelato);
			}
			catch(Exception ex)
			{
				
			}
			
			if(congelatoBool)
				sez = new DpatSezioneNewBean().buildByOid(db, idSezione,true,true);
			else
				sez = new DpatSezioneNewBeanPreCong().buildByOid(db, idSezione,true,true);
			
			lista = sez.getPianiAttivitaFigli();

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return lista;
	}

	public static ArrayList<DpatAttivita> getListaAttivita(int idPiano, int anno) {
		Connection db = null;
		ArrayList<DpatAttivita> lista = new ArrayList<DpatAttivita>();
		try {
			db = GestoreConnessioni.getConnection();
			DpatPiano sez = new DpatPiano();
			sez.buildlistAttivitaConfiguratore(db, idPiano, anno);
			lista = sez.getElencoAttivita();

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return lista;
	}

	
	public static ArrayList getListaIndicatoriNewDpat(int idPianoAttivita, int anno, String congelato) { /*solo i non scaduti */
		ArrayList lista = new ArrayList ();
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			DpatPianoAttivitaNewBeanInterface piano = null;
			boolean congelatoBool = false;
			try
			{
				congelatoBool = Boolean.parseBoolean(congelato);
			}
			catch(Exception ex)
			{
				
			}
			if(congelatoBool)
				piano = new DpatPianoAttivitaNewBean().buildByOid(db,idPianoAttivita,true,true);
			else
				piano = new DpatPianoAttivitaNewBeanPreCong().buildByOid(db,idPianoAttivita,true,true);
			 
			lista = piano.getIndicatoriFigli();

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return lista;
	}
	
	
	
	
	public static ArrayList<DpatIndicatore> getListaIndicatori(int idAttivita, int anno) {
		ArrayList<DpatIndicatore> lista = new ArrayList<DpatIndicatore>();
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			DpatAttivita sez = new DpatAttivita();
			sez.buildlistIndicatoriConfiguratore(db, idAttivita, anno);
			lista = sez.getElencoIndicatori();

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return lista;
	}

	public static boolean controllaEsistenzaInStruttura(int idStruttua, int idUtente) {
		boolean ret = false;
		Connection db = null;
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db
					.prepareStatement("select *  from dpat_Strumento_calcolo_nominativi where id_anagrafica_nominativo =? and id_struttura = ?");
			pst.setInt(1, idUtente);
			pst.setInt(2, idStruttua);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				ret = true;

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;
	}

	
	 
	
	
	/*public static String[] verificaEsistenzaCodiceAttivita(String codice) {

		boolean ret = false;
		Connection db = null;
		String codiceDisponibile = "" ;
		String[] toRest = new String[2];
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from dpat_codici_attivita where codice ilike ?");
			pst.setString(1, codice);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				ret = true;
				ret = true;
				toRest[0]="true";
				
				pst = db.prepareStatement("select * from public_functions.dpat_get_prossimo_codice(?)");
				pst.setString(1, codice);
				rs = pst.executeQuery();
				if (rs.next())
				{
					codiceDisponibile=rs.getString(1);
					toRest[1]=codiceDisponibile;
				}
				else
				{
					toRest[1]="";
				}

			}
			if (ret == false)
				toRest = verificaEsistenzaCodiceIndicatore(codice);

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRest;

	}*/
	
	public static String[] verificaEsistenzaCodiceIndicatoreNEW(String codice, String anno, String id_piano_attivita, String id_indicatore)
	{

		boolean ret = false;
		String codiceDisponibile = "" ;
		Connection db = null;
		//String query = "select * from dpat_indicatore_new where lower(codice_alias_indicatore) = lower(?) and id = ? ";
	
		/*
		String query = "select * from codici_piani_attivita_indicatori where lower(codice_alias_indicatore) = lower(?) and codice_alias_attivita = "+
				"(select codice_alias_attivita from codici_piani_attivita_indicatori where id_indicatore  = ? )"; 
		*/
		
		String query = "select * from codici_piani_attivita_indicatori where lower(codice_alias_indicatore) = lower(?) and id_piano_attivita = ? and id_indicatore <> ?";
		
		
		
		String[] toRest = new String[2];
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(query);
			pst.setString(1, codice);
			pst.setInt(2,Integer.parseInt(id_piano_attivita));
			pst.setInt(3,Integer.parseInt(id_indicatore));
			
			logger.info(pst.toString());
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				 
				toRest[0]="true";
				
				int id_indicatore_duplicato = rs.getInt("id_indicatore");
				
				pst = db.prepareStatement("select * from  peekNextCodiceAlias('indicatore',?,?,?)");
				pst.setString(1, anno);
				pst.setString(2, codice);
				pst.setInt(3,id_indicatore_duplicato);
				
				logger.info(pst.toString());
				
				rs = pst.executeQuery();
				if (rs.next())
				{
					codiceDisponibile=rs.getString(1);
					toRest[1]=codiceDisponibile;
				}
				else
				{
					toRest[1]="";
				}
			}
			else
			{
				toRest[0]="false";
				toRest[1]="";
			}




		} catch (LoginRequiredException e) {

			logger.severe(e.getMessage());
			throw e;
		} catch (SQLException e) {
			
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
				
		return toRest;
	}
	
	public static String[] verificaEsistenzaCodiceAttivitaNEW(String codice, String anno, String id_indicatore )
	{


		String codiceDisponibile = "" ;
		Connection db = null;
		//String query = "select * from dpat_piano_attivita_new where lower(codice_alias_attivita) = lower(?) and id = ? ";
		String query = "select * from codici_piani_attivita_indicatori where lower(codice_alias_attivita) = lower(?) and anno = ?";
		
		String[] toRest = new String[2];
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(query);
			pst.setString(1, codice);
			pst.setInt(2,Integer.parseInt(anno));
			
			logger.info(pst.toString());
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				 
				toRest[0]="true";

				pst = db.prepareStatement("select * from  peekNextCodiceAlias('piano-attivita',?,?,?)");
				pst.setString(1, anno);
				pst.setString(2, codice);
				pst.setInt(3,Integer.parseInt(id_indicatore));
				
				logger.info(pst.toString());
				
				rs = pst.executeQuery();
				if (rs.next())
				{
					codiceDisponibile=rs.getString(1);
					toRest[1]=codiceDisponibile;
				}
				else
				{
					toRest[1]="";
				}
			}
			else
			{
				toRest[0]="false";
				toRest[1]="";
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRest;

	
	}
	
	public static String verificaEsistenzaMatriciAnaliti(String id_indicatore )
	{


		String esito = "" ;
		Connection db = null;
		//String query = "select * from dpat_piano_attivita_new where lower(codice_alias_attivita) = lower(?) and id = ? ";
		String query = "select * from dpat_indicatore_has_matrici_analiti_collegati(?)";
		
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(id_indicatore));
			
			logger.info(pst.toString());
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				esito = rs.getString(1);	
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;

	
	}
	
	/*public static String[] verificaEsistenzaCodiceIndicatore(String codice) {

		boolean ret = false;
		String codiceDisponibile = "" ;
		Connection db = null;

		String[] toRest = new String[2];
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from dpat_codici_indicatore where codice ilike ?");
			pst.setString(1, codice);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				ret = true;
				toRest[0]="true";

				pst = db.prepareStatement("select * from public_functions.dpat_get_prossimo_codice_indicatore(?)");
				pst.setString(1, codice);
				rs = pst.executeQuery();
				if (rs.next())
				{
					codiceDisponibile=rs.getString(1);
					toRest[1]=codiceDisponibile;
				}
				else
				{
					toRest[1]="";
				}
			}
			else
			{
				toRest[0]="false";
				toRest[1]="";
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRest;

	}*/

	public static String recuperaCodiceIdentificativoCu(int idLinea) {

		String ret = "";
		Connection db = null;
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from get_codice_identificativo_cu_per_campi_aggiuntivi(?)");
			pst.setInt(1, idLinea);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				ret = rs.getString(1);

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;

	}

	public static int recuperaLineaSottopostaCu(int idTicket) {

		int ret = -1;
		Connection db = null;
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select id_linea_attivita from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = ? and trashed_date is null");

			pst.setInt(1, idTicket);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				ret = rs.getInt(1);

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;

	}

	public static String recuperaCampiAggiuntiviLineaMercato(int idLinea, int idControllo) {

		String ret = null;
		Connection db = null;
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from dbi_get_operatori_mercato(?, ?)"); 
			pst.setInt(1, idLinea);
			pst.setInt(2, idControllo);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				if (ret==null)
					ret="";
				ret = ret+rs.getInt("id")+ "|"+rs.getString("ragione_sociale")+"|" + rs.getInt("num_box")+"|"+ rs.getString("selected")+";;"; 
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;

	}
	
	public String getAslDaComune(String idComune) {
		String idAsl = "";
		Connection db = null;
		String sql = "select codiceistatasl from comuni1 where id = ?";
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, Integer.parseInt(idComune));
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					idAsl = rs.getString(1);
				}

				pst.close();
				rs.close();
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return idAsl;
	}
		
	public String getCompatibilitaMotiviCU(String _tipoOperatore, String _idPianoAttivita, String _listaPianiAttivita) {
		
		String messaggio = "";
		
		int tipoOperatore = Integer.parseInt(_tipoOperatore);
		String[] idPianoAttivita = _idPianoAttivita.split(";");
		String[] listaPianiAttivita = _listaPianiAttivita.split(";;");
		
		if (idPianoAttivita.length!=2)
			return messaggio;
		
		int idAttivita = Integer.parseInt(idPianoAttivita[0]);
		int idPiano = Integer.parseInt(idPianoAttivita[1]);
			
		if (listaPianiAttivita.length<=1)
			return messaggio;
		
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				
				String codicePianoAttivita = getCodicePianoAttivita(db, idAttivita, idPiano);
				for (int i = 0; i< listaPianiAttivita.length; i++){
					String idPianoAttivitaTemp[] = listaPianiAttivita[i].split(";");
					
					int idAttivitaTemp = Integer.parseInt(idPianoAttivitaTemp[0]);
					int idPianoTemp = Integer.parseInt(idPianoAttivitaTemp[1]);
					
					String codicePianoAttivitaTemp = getCodicePianoAttivita(db, idAttivitaTemp, idPianoTemp);
					messaggio = getCompatibilita(db, tipoOperatore, codicePianoAttivita, codicePianoAttivitaTemp);
					if (messaggio!=null && !messaggio.equals(""))
						break;
					}
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return messaggio;
	}
	
	private String getCodicePianoAttivita(Connection db, int idAttivita, int idPiano) throws SQLException {
		String codice = null;
		String sql = "select codice from controlli_ufficiali_motivi_ispezione where 1=1 ";
		if (idPiano>0)
			sql+=" and id_piano = "+idPiano;
		else if (idAttivita>0)
			sql+=" and id_tipo_ispezione = "+idAttivita;
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			codice = rs.getString("codice");
		return codice;
	} 
	
	private String getCompatibilita(Connection db, int tipoOperatore, String codice1, String codice2) throws SQLException {
		
		String messaggio = null;
		PreparedStatement pst = db.prepareStatement("select get_cu_motivi_compatibilita from get_cu_motivi_compatibilita(?, ?, ?)");
		pst.setInt(1, tipoOperatore);
		pst.setString(2, codice1);
		pst.setString(3, codice2);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			messaggio = rs.getString("get_cu_motivi_compatibilita");
		return messaggio;
	}
	
	public String[] getEsistenzaTargaSintesis(String targa) {
		Connection db = null;
		String sql = "select * from get_esistenza_targa_sintesis(?)";

		String[] listaValori = new String[3];
		listaValori[0] = "" ;
		listaValori[1] = "" ;
		listaValori[2] = "" ;

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) {
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, targa);
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					listaValori[0]  = rs.getString(1);
					listaValori[1]  = rs.getString(2);
					listaValori[2]  = rs.getString(3);
				}

				pst.close();
				rs.close();
			}
		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return listaValori;
	}
	
	public static String recuperaCampiAggiuntiviLineaTrasportoSOA(int idLinea) { 

		String ret = null;
		Connection db = null;
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from public_functions.dbi_get_automezzi_soa(?)");
			pst.setInt(1, idLinea);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				if (ret==null)
					ret="";
				ret = ret+rs.getInt("id")+"##"+rs.getString("targa")+";;";
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;

	}
	
	public static int getNumeroControlliSuLinea(int idRelStabLp) throws SQLException 
	{

		String sql = "select count(*) from linee_attivita_controlli_ufficiali where id_linea_attivita = ? and trashed_date is null";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idRelStabLp);
			rs=pst.executeQuery();
			int numCu = 0 ;
			if(rs.next())
				numCu = rs.getInt(1);

			return numCu;

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return 0;
	}

	public static String[] getEsistenzaBduVam(String microchip, String campo)
	{
		String[] ret	= new String[2] ; 
		Connection dbBdu = null;
		Connection dbVam = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Logger logger = Logger.getLogger("MainLogger");
		
		try
		{
			
			logger.info("Controllo se esiste in BDU: "+microchip); 
			
			dbBdu = GestoreConnessioni.getConnectionBdu();
			String select 	= 	"select * from get_esistenza_identificativo(?)"	;

			pst = dbBdu.prepareStatement(select);
			pst.setString(1, microchip);
			rs = pst.executeQuery();

			if ( rs.next() )
			{
				ret[0] 		= rs.getString(1)				;
				ret[1] = campo;
			}else
			{
				ret[0] 		= "f";
				ret[1] = campo;
			}
			logger.info("Controllo se esiste in BDU: "+microchip+": "+ret[0]); 

			if (ret[0].equals("false")){
				
			logger.info("Controllo se esiste in VAM: "+microchip); 

			dbVam = GestoreConnessioni.getConnectionVam();
			select 	= 	"select * from get_esistenza_identificativo(?)"	;

			pst = dbVam.prepareStatement(select);
			pst.setString(1, microchip);
			rs = pst.executeQuery();

			if ( rs.next() )
			{
				ret[0] 		= rs.getString(1)				;
				ret[1] = campo;
			}else
			{
				ret[0] 		= "f";
				ret[1] = campo;
			}
			logger.info("Controllo se esiste in VAM: "+microchip+": "+ret[0]); 

			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(dbBdu);
			GestoreConnessioni.freeConnection(dbVam);
		}

		return ret;

	}
	
	public static boolean keepAlive(boolean keep)
	{

		String sql = "select 1";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			rs=pst.executeQuery();
			}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return true;



	}
	
	public static String recuperaCampiAggiuntiviLineaPrivati(int idLinea, int idControllo) {

		String ret = null;
		Connection db = null;
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from dbi_get_luogo_controllo_privati(?, ?)"); 
			pst.setInt(1, idLinea);
			pst.setInt(2, idControllo);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				if (ret==null)
					ret="";
				ret = ret+rs.getInt("id_indirizzo")
						+ "|"+rs.getInt("id_toponimo")
						+ "|"+rs.getString("desc_toponimo")
						+ "|"+rs.getString("via")
						+ "|"+rs.getString("civico")
						+ "|"+rs.getString("cap")
						+ "|"+rs.getInt("id_comune")
						+ "|"+rs.getString("desc_comune")
						+ "|"+rs.getInt("id_provincia")
						+ "|"+rs.getString("desc_provincia");
						
			}

		} catch (LoginRequiredException e) {

			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return ret;

	}
	
	public static String getCodiciEventoMotivoCU(int idIndicatore, int codRaggruppamento) throws SQLException 
	{
		String codici = "";
		String sql = "select * from get_codici_evento_motivo_cu(?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idIndicatore);
			pst.setInt(2, codRaggruppamento);
			rs=pst.executeQuery();
			if(rs.next())
				codici = rs.getString(1);

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return codici;
	}
	public static boolean hasEventoMotivoCU(String codiceEvento, int idIndicatore, int codRaggruppamento) throws SQLException 
	{
		boolean esito = false;
		String sql = "select * from has_evento_motivo_cu(?, ?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setString(1, codiceEvento);
			pst.setInt(2, idIndicatore);
			pst.setInt(3, codRaggruppamento);
			rs=pst.executeQuery();
			if(rs.next())
				esito = rs.getBoolean(1);

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static boolean hasChecklistBenessereMotivoCU(String codiceChecklist, int idIndicatore, int codRaggruppamento, int idControllo) throws SQLException 
	{
		boolean esito = false;
		String sql = "select * from has_checklist_benessere_motivo_cu(?, ?, ?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setString(1, codiceChecklist);
			pst.setInt(2, idIndicatore);
			pst.setInt(3, codRaggruppamento);
			pst.setInt(4, idControllo);
			rs=pst.executeQuery();
			if(rs.next())
				esito = rs.getBoolean(1);

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static String getInfoChecklistBenessereIstanza(int idControllo, int codiceSpecie, String output) throws SQLException 
	{
		String esito = null;
		String sql = "select * from get_info_chk_bns_istanza(?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idControllo);
			pst.setInt(2, codiceSpecie);
			rs=pst.executeQuery();
			if(rs.next()){
				try {esito = rs.getString(output);} catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static boolean verificaEsistenzaIstanzaBenessere(int idControllo, boolean isCondizionalita) throws SQLException 
	{
		boolean esito = false;
		String sql = "select * from get_esistenza_ba_sa_istanza(?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idControllo);
			pst.setBoolean(2, isCondizionalita);
			rs=pst.executeQuery();
			if(rs.next()){
				try {
					esito = rs.getBoolean(1);
				} catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static boolean verificaEsistenzaIstanzaFarmacosorveglianza(int idControllo) throws SQLException 
	{
		boolean esito = false;
		String sql = "select * from get_esistenza_farmacosorveglianza_istanza(?)"; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idControllo);
			rs=pst.executeQuery();
			if(rs.next()){
				try {
					esito = rs.getBoolean(1);
				} catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static boolean isPrevistoVerbaleCampione(int idCampione, String codiceModello) throws SQLException 
	{
		boolean esito = false;
		String sql = "select * from is_previsto_verbale_campione(?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idCampione);
			pst.setString(2, codiceModello);
			rs=pst.executeQuery();
			if(rs.next())
				esito = rs.getBoolean(1);

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static boolean isPrevistoPagoPA(int idSanzione) throws SQLException 
	{
		boolean esito = false;
		String sql = "select * from is_previsto_pagopa(?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idSanzione);
			rs=pst.executeQuery(); 
			if(rs.next())
				esito = rs.getBoolean(1);

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	/*
	public static String getCategoriaRischioQualitativa(int rifId, String rifNomeTab) throws SQLException 
	{
		String cat = "";
		String sql = "select * from get_categoria_rischio_qualitativa(?, ?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, rifId);
			pst.setString(2, rifNomeTab);
			rs=pst.executeQuery(); 
			if(rs.next())
				cat = rs.getString(1);

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return cat;
	}*/
	
	public static String getInfoChecklistBiosicurezzaIstanza(int idControllo, String output) throws SQLException 
	{
		String esito = null;
		String sql = "select * from get_info_chk_biosicurezza_istanza(?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idControllo);
			rs=pst.executeQuery();
			if(rs.next()){
				try {esito = rs.getString(output);} catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}




	public static boolean verificaEsistenzaIstanzaBiosicurezza(int idControllo) {
		boolean esito = false;
		String sql = "select * from get_esistenza_biosicurezza_istanza(?)"; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idControllo);
			rs=pst.executeQuery();
			if(rs.next()){
				try {
					esito = rs.getBoolean(1);
				} catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public static String getInfoChecklistFarmacosorveglianzaIstanza(int idControllo, String output) throws SQLException 
	{
		String esito = null;
		String sql = "select * from get_info_chk_farmacosorveglianza_istanza(?)";
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = GestoreConnessioni.getConnection();
			pst=db.prepareStatement(sql);
			pst.setInt(1, idControllo);
			rs=pst.executeQuery();
			if(rs.next()){
				try {esito = rs.getString(output);} catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	public boolean verificaCompetenzaUod(int idNorma) {
		boolean toRet = false;
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			String sql = "select COALESCE(competenza_uod, false) from lookup_norme where code = ?";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idNorma);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				toRet = rs.getBoolean(1);

		} catch (LoginRequiredException e) {

			throw e;
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;

	}
	
	public static AnagraficaCodiceSINVSARecord getCodiceSINVSA(int riferimentoId, String riferimentoId_nomeTab) throws SQLException {
		Connection connection = null;
		AnagraficaCodiceSINVSARecord recordCodice = null;
		
		try {
			String query = "select * from anagrafica_codice_sinvsa "
					 	 + "where riferimento_id = ? and riferimento_id_nome_tab = ? "
					 	 + "and trashed_date is null";
			connection = GestoreConnessioni.getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, riferimentoId);
			ps.setString(++i, riferimentoId_nomeTab);
			ResultSet res = ps.executeQuery();
			if(res.next()) {
				recordCodice = new AnagraficaCodiceSINVSARecord(
							res.getInt("id"),
							res.getInt("riferimento_id"),
							res.getString("riferimento_id_nome_tab"),
							res.getString("codice_sinvsa"),
							res.getDate("data_codice_sinvsa"),
							res.getDate("entered"),
							res.getInt("entered_by"),
							res.getDate("trashed_date"),
							res.getString("note_hd")
						);
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			GestoreConnessioni.freeConnection(connection);
		}
		
		return recordCodice;
	}
	
	public static void setCodiceSINVSA(String nuovoCodice, String dataNuovoCodice, int riferimentoId, String riferimentoId_nomeTab, int userId) throws SQLException {
		Connection connection = null;
		
		try {
			String query = "";
			PreparedStatement ps = null;
			connection = GestoreConnessioni.getConnection();
			
			int i = 0;
			query = "update anagrafica_codice_sinvsa set trashed_date = now() where riferimento_id = ? "
				  + "and riferimento_id_nome_tab = ? and trashed_date is null";
			ps = connection.prepareStatement(query);
			ps.setInt(++i, riferimentoId);
			ps.setString(++i, riferimentoId_nomeTab);
			ps.executeUpdate();
			
			query = "insert into anagrafica_codice_sinvsa(codice_sinvsa, data_codice_sinvsa, riferimento_id, riferimento_id_nome_tab, entered, entered_by) "
					 + "values(?,?,?,?,now(),?)";
			ps = connection.prepareStatement(query);
			i = 0;
			ps.setString(++i, nuovoCodice);
			ps.setDate(++i, Date.valueOf(dataNuovoCodice));
			ps.setInt(++i, riferimentoId);
			ps.setString(++i, riferimentoId_nomeTab);
			ps.setInt(++i, userId);
			ps.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			GestoreConnessioni.freeConnection(connection);
		}
		
	}
}
