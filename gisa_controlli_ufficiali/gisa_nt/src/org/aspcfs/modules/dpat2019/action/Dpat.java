/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.dpat2019.base.DpatAttivita;
import org.aspcfs.modules.dpat2019.base.DpatCoefficiente;
import org.aspcfs.modules.dpat2019.base.DpatIndicatore;
import org.aspcfs.modules.dpat2019.base.DpatIndicatoreNewBean;
import org.aspcfs.modules.dpat2019.base.DpatIstanza;
import org.aspcfs.modules.dpat2019.base.DpatPiano;
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBean;
import org.aspcfs.modules.dpat2019.base.DpatSezione;
import org.aspcfs.modules.dpat2019.base.DpatSezioneNewBean;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo;
import org.aspcfs.modules.dpat2019.base.DpatStrutturaIndicatore;
import org.aspcfs.modules.dpat2019.base.PianoMonitoraggio;
import org.aspcfs.modules.dpat2019.base.Indicatore;
import org.aspcfs.modules.dpat2019.base.PianoAttivita;
import org.aspcfs.modules.dpat2019.base.PianoAttivitaInterface;
import org.aspcfs.modules.dpat2019.base.Sezione;
import org.aspcfs.modules.dpat2019.base.SezioneInterface;
import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniBean;
import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniNewBeanAbstract;
import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniBeanPreCong;
import org.aspcfs.modules.dpat2019.base.WrapperTuttiModelli;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUpload;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.programmazzionecu.base.PianoMonitoraggioList;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class Dpat extends CFSModule {

	public String executeCommandDefault(ActionContext context) { /*chiamato dal cavaliere */
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");

		Connection db = null;
		ArrayList<DpatIstanza> anniList = new ArrayList<DpatIstanza>();
		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Seleziona--");
			siteList.remove(siteList.size() - 1);

			// String sql = "select anno from dpat group by anno order by anno";
			String sql = "select *  from dpat_istanza where trashed_date is null   order by anno desc";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				DpatIstanza ist = new DpatIstanza();
				ist.buildRecord(rs);
				anniList.add(ist);
			}
			rs.close();
			pst.close();
			context.getRequest().setAttribute("anniList", anniList);
			context.getRequest().setAttribute("siteList", siteList);
			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			context.getRequest().setAttribute("annoCorrente", calCorrente.get(Calendar.YEAR)+"");
			
			LookupList lookupTipologia = new LookupList();
			lookupTipologia.addItem(-1,  "-- SELEZIONA VOCE --");
			lookupTipologia.addItem(13,  "STRUTTURA COMPLESSA");
			lookupTipologia.addItem(14,  "STRUTTURA SEMPLICE DIPARTIMENTALE");
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.freeConnection(context, db);
		}

		return "DpatHomeRegOK"; /*home.jsp */
	}
	
	
	
	
	public String executeCommandInitNuovoAnno(ActionContext context) 
	{ 
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");

		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			
			int annoCorrente = Integer.parseInt((String)context.getParameter("annoCorrente"));
			PreparedStatement pst = db.prepareStatement("select * from  dpat_duplica_modello(?,?,?)");
			pst.setInt(1, annoCorrente);
			pst.setInt(2, annoCorrente+1);
			pst.setInt(3, utente.getUserId());
			pst.execute();
			
			pst.close();
			
			/*devo prendere tutti i modelli non templates (quelli che non hanno nodi in stato 1, ma in 2 o 0) per gli anni
			 * e tutti quelli che invece hann otutti i nodi in stato 1, e sono caratterizzati dal nome template
			 */
			WrapperTuttiModelli wrapper = new WrapperTuttiModelli(db);
			
			context.getRequest().setAttribute("wrapperTuttiModelli", wrapper);
			context.getRequest().setAttribute("annoCorrente", annoCorrente+"");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			super.freeConnection(context, db);
		}

		return "DpatHomeConfiguraNEWDPATOK";
	}
	
	

	public String executeCommandHomeConfiguraDpat(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");

		Connection db = null;
		ArrayList<DpatIstanza> anniList = new ArrayList<DpatIstanza>();
		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Seleziona--");
			siteList.remove(siteList.size() - 1);

			// String sql = "select anno from dpat group by anno order by anno";
			String sql = "select *  from dpat_istanza where trashed_date is null order by anno desc";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				DpatIstanza ist = new DpatIstanza();
				ist.buildRecord(rs);
				anniList.add(ist);
			}
			rs.close();
			pst.close();
			context.getRequest().setAttribute("anniList", anniList);
			context.getRequest().setAttribute("siteList", siteList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.freeConnection(context, db);
		}

		return "DpatHomeConfiguraOK";
	}
	
	
	
	
	

	public String executeCommandHome(ActionContext context) { /*chiamata da home.jsp */
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		int anno = Integer.parseInt(context.getRequest().getParameter("anno"));
		Connection db = null;

		int idAsl = utente.getUserRecord().getSiteId();
		if (idAsl != -1) {
			context.getRequest().setAttribute("idAsl", String.valueOf(idAsl));
		} else {
			idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			context.getRequest().setAttribute("idAsl", String.valueOf(idAsl));
		}

		String idArea = "";
		idArea = context.getRequest().getParameter("combo_area");
		int idStrutturaAreaSelezionata = -1;
		if (idArea != null && !"".equals(idArea))
			idStrutturaAreaSelezionata = Integer.parseInt(idArea);

		context.getRequest().setAttribute("anno", String.valueOf(anno));
		try {
			db = this.getConnection(context);
			DpatStrumentoCalcolo dsc = new DpatStrumentoCalcolo(db, idAsl, anno, false, idStrutturaAreaSelezionata);

			context.getRequest().setAttribute("dsc", dsc);

			org.aspcfs.modules.dpat2019.base.Dpat dpatAtt = new org.aspcfs.modules.dpat2019.base.Dpat();
			dpatAtt.builRecordAslAnno(idAsl, anno, db);
			context.getRequest().setAttribute("dpatAtt", dpatAtt);

			DocumentaleAllegatoList listaAllegati = new DocumentaleAllegatoList();
			if (anno > 2015) {
				GestioneAllegatiUpload gestioneAllegati = new GestioneAllegatiUpload();
				listaAllegati = gestioneAllegati.getListaAllegatiDpat(context, db);

				OiaNodo[] listaStrutture = OiaNodo.getAreeStruttureComplesse(idAsl, anno, -1);

				LookupList listaStruttureDirezione = new LookupList();
				listaStruttureDirezione.setTable("");

				for (int i = 0; i < listaStrutture.length; i++) {
					listaStruttureDirezione.addItem(listaStrutture[i].getId(),
							listaStrutture[i].getDescrizione_lunga());
				}
				context.getRequest().setAttribute("ListaStruttureDirezione", listaStruttureDirezione);

			}
			context.getRequest().setAttribute("ListaAllegati", listaAllegati);

			LookupList listaAsl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.freeConnection(context, db);
		}
		return "DpatHomeOK"; /*home2.jsp*/
	}

	/****************************
	 * ACTION PER DPAT GENERALE *
	 *****************************/


	public String executeCommandDpatModifyGeneraleCompetenzeNEW(ActionContext context)
	{
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		String idAsl = (String) context.getRequest().getParameter("idAsl");
		String anno = (String) context.getRequest().getParameter("anno");

		if (anno.equals("corrente")) 
		{
			int a = Calendar.getInstance().get(Calendar.YEAR);
			anno = String.valueOf(a);
		}
		context.getRequest().setAttribute("anno", anno);

		String comboArea = context.getRequest().getParameter("combo_area");
		int idAreaSelezionata = -1;
		if (comboArea != null & !"".equals(comboArea))
			idAreaSelezionata = Integer.parseInt(comboArea);

		// DPAT PER L'ASL SELEZIONATA
		org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenzeNewBean d = null; /*sicuramente quello col modello dpat configurato congelato */
		try {
			db = this.getConnection(context);

			LookupList listaAsl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			sql = "select id,completo,id_asl,anno from dpat where id_asl=" + idAsl + " and anno=" + anno
					+ " and enabled";
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while (rs.next()) 
			{
				d = new org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenzeNewBean();
				d.setIdArea(idAreaSelezionata);
				d.setId(rs.getInt("id"));
				d.setCompleto(rs.getBoolean("completo"));
				d.setIdAsl(rs.getInt("id_asl"));
				d.setAnno(rs.getInt("anno"));
				d.buildRecord(d.getId(), db, this.getSystemStatus(context), idAreaSelezionata);
				d.buildlistSezioni(db, Integer.parseInt(anno),true);
			}

			if (d.getStrutturaAmbito().getStato_all6() == 2 || (d.isCompleto() == true && d.getAnno() < 2016))
				return executeCommandDpatDetailGeneraleCompetenze(context);

//			DpatIstanza istanza = new DpatIstanza();
//			int statoDpatConfig = istanza.getStatoFromAnno(db, Integer.parseInt(anno));
			if (d != null) {

				int num_indicatori_per_sezioni = Integer
						.parseInt(ApplicationProperties.getProperty("NR_INDICATORI_PER_SEZIONE"));
				int num_indicatori = 0;
				ArrayList<SezioneInterface> listaSezioniSplitted = new ArrayList<SezioneInterface>();
				// ELENCO SEZIONI, PIANI, ATTIVITA' ED INDICATORI DEL DPAT

				// PER OGNI SEZIONE COSTRUISCI LA LISTA DEI PIANI
				for (int i = 0; i < d.getElencoSezioni().getSezioni().size(); i++) {
					 

					SezioneInterface  newsz = (SezioneInterface) ((SezioneInterface) d.getElencoSezioni().getSezioni().get(i)).clone();
					ArrayList<PianoAttivitaInterface> listaPianiDaIncludere = new ArrayList<PianoAttivitaInterface>();
					int num_indicatori_this_piano = 0;
					num_indicatori = 0;
					ArrayList elencoPiani = ((SezioneInterface)d.getElencoSezioni().getSezioni().get(i)).getPianiAttivitaFigli();
					// PER OGNI PIANO COSTRUISCI LA LISTA DELLE ATTIVITA'
					for (int j = 0; j < elencoPiani.size();/*d.getElencoSezioni().get(i).getElencoPiani().size();*/ j++) {
						 
						
						PianoAttivitaInterface piano = (PianoAttivitaInterface)elencoPiani.get(j);
						ArrayList indicatoriPiano = piano.getIndicatoriFigli();
						num_indicatori_this_piano = indicatoriPiano.size();
						 

						if ((num_indicatori + num_indicatori_this_piano) <= num_indicatori_per_sezioni) {
							num_indicatori = num_indicatori + num_indicatori_this_piano;
							listaPianiDaIncludere.add(piano);
							newsz.setPianiAttivitaFigli(listaPianiDaIncludere); 
							if (j == elencoPiani.size()- 1) {
								listaSezioniSplitted.add(newsz);
							}
						} else if (num_indicatori_this_piano <= num_indicatori_per_sezioni) {
							listaSezioniSplitted.add(newsz);
							newsz = (SezioneInterface) ((SezioneInterface) (d.getElencoSezioni().getSezioni(). get(i))).clone();
							listaPianiDaIncludere = new ArrayList<PianoAttivitaInterface >();
							listaPianiDaIncludere.add((PianoAttivitaInterface )elencoPiani.get(j));
							newsz.setPianiAttivitaFigli(listaPianiDaIncludere); 
							num_indicatori = num_indicatori_this_piano;
							if (j == elencoPiani.size()- 1) {
								listaSezioniSplitted.add(newsz);
							}
						} else {

							int ii = num_indicatori_this_piano / num_indicatori_per_sezioni;
							if ((num_indicatori_this_piano % num_indicatori_per_sezioni) > 0) {
								ii++;
							}
							ArrayList<PianoAttivitaInterface> pp = new ArrayList<PianoAttivitaInterface>();
							for (int jj = 0; jj < ii; jj++) 
							{
								long oidP = ((PianoAttivitaInterface)elencoPiani.get(j)).getOid();
								PianoAttivitaInterface app_piano = new PianoAttivita().buildByOid(db, (int)oidP, true, true,Integer.parseInt(anno));// (DpatPiano)(d.getElencoSezioni().get(i).getElencoPiani().get(j));
								pp.add(jj, app_piano);
							}

							int index_indicatori = 0;
							for (int jj = 0; jj < pp.size(); jj++) 
							{
								PianoAttivitaInterface pp2 = pp.get(jj);
								
								ArrayList<Indicatore> app = new ArrayList<Indicatore>();
								for(int kk= 0; kk < num_indicatori_per_sezioni; kk++)
								{
									
									if(index_indicatori < pp2.getIndicatoriFigli().size())
									{
										Indicatore ind_app = (Indicatore)pp2.getIndicatoriFigli().get(index_indicatori);
										app.add(kk, ind_app);
										index_indicatori++;
									}
								}
								pp2.setIndicatoriFigli(app);
								pp.set(jj, pp2);
								
								
								listaSezioniSplitted.add(newsz);
								newsz = (SezioneInterface) ((SezioneInterface) (d.getElencoSezioni().getSezioni(). get(i))).clone();
								listaPianiDaIncludere = new ArrayList<PianoAttivitaInterface >();
								listaPianiDaIncludere.add((PianoAttivitaInterface )pp.get(jj));
								newsz.setPianiAttivitaFigli(listaPianiDaIncludere); 
								num_indicatori = num_indicatori_this_piano;
								if (j == elencoPiani.size()- 1) {
									listaSezioniSplitted.add(newsz);
								}
								
								 
								 
							}
						}

					}
				}

				d.getElencoSezioni().setSezioni(listaSezioniSplitted); 

				// RECORD DI DPAT_STRUTTURA_INDICATORE PER POPOLARE LE CELLE DEL
				// DPAT

				pst.close();
				rs.close();

				int idSDC = -1;
				PreparedStatement pst2 = db.prepareStatement("select id from dpat_strumento_calcolo where id_asl="
						+ d.getIdAsl() + " and anno=" + d.getAnno());
				ResultSet rs2 = pst2.executeQuery();
				while (rs2.next()) {
					idSDC = rs2.getInt(1);
				}
				context.getRequest().setAttribute("idSDC", new Integer(idSDC));
				pst2.close();
				rs2.close();

				// context.getRequest().setAttribute("elencoStruttureComplesse",
				// elencoStruttureComplesse);
				// context.getRequest().setAttribute("elencoStrutture",
				// elencoStrutture);
			}
			context.getRequest().setAttribute("dpat2", d);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "DpatModifyCompetenzeNEWOK";
	
		
	}
	
	

	public String executeCommandDpatDetailGeneraleCompetenze(ActionContext context) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		String idAsl = (String) context.getRequest().getParameter("idAsl");
		String anno = (String) context.getRequest().getParameter("anno");

		if (anno.equals("corrente")) {
			int a = Calendar.getInstance().get(Calendar.YEAR);
			anno = String.valueOf(a);
		}
		context.getRequest().setAttribute("anno", anno);

		String comboArea = context.getRequest().getParameter("combo_area");
		int idAreaSelezionata = -1;
		if (comboArea != null & !"".equals(comboArea))
			idAreaSelezionata = Integer.parseInt(comboArea);
		else if (context.getRequest().getAttribute("ComboArea") != null)
			idAreaSelezionata = (Integer) context.getRequest().getAttribute("ComboArea");

		// DPAT PER L'ASL SELEZIONATA
		org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenza d = null;
		try {
			db = this.getConnection(context);

			LookupList listaAsl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			sql = "select id from dpat where id_asl=" + idAsl + " and anno=" + anno + " and enabled";
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				d = new org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenza();
				d.setIdArea(idAreaSelezionata);
				d.setId(rs.getInt("id"));
				d.builRecord(d.getId(), db, this.getSystemStatus(context), idAreaSelezionata);
				d.buildlistSezioni(db, Integer.parseInt(anno));
			}
			DpatIstanza istanza = new DpatIstanza();
			int statoDpatConfig = istanza.getStatoFromAnno(db, Integer.parseInt(anno));
			if (d != null) {

				int num_indicatori_per_sezioni = Integer
						.parseInt(ApplicationProperties.getProperty("NR_INDICATORI_PER_SEZIONE"));
				int num_indicatori = 0;
				ArrayList<DpatSezione> listaSezioniSplitted = new ArrayList<DpatSezione>();
				// int part = 1;
				// ELENCO SEZIONI, PIANI, ATTIVITA' ED INDICATORI DEL DPAT

				// PER OGNI SEZIONE COSTRUISCI LA LISTA DEI PIANI
				for (int i = 0; i < d.getElencoSezioni().size(); i++) {
					d.getElencoSezioni().get(i).buildlistPiani(db, d.getElencoSezioni().get(i).getId(), statoDpatConfig,
							Integer.parseInt(anno));

					DpatSezione newsz = (DpatSezione) (d.getElencoSezioni().get(i)).clone();
					ArrayList<DpatPiano> listaPianiDaIncludere = new ArrayList<DpatPiano>();
					int num_indicatori_this_piano = 0;
					num_indicatori = 0;

					// PER OGNI PIANO COSTRUISCI LA LISTA DELLE ATTIVITA'
					for (int j = 0; j < d.getElencoSezioni().get(i).getElencoPiani().size(); j++) {
						d.getElencoSezioni().get(i).getElencoPiani().get(j).buildlistAttivita(db,
								d.getElencoSezioni().get(i).getElencoPiani().get(j).getId(), statoDpatConfig,
								Integer.parseInt(anno));

						num_indicatori_this_piano = 0;
						// PER OGNI ATTIVITA CALCOLA L'UI TOTALE E COSTRUISCI LA
						// LISTA DEGLI INDICATORI
						for (int k = 0; k < d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita()
								.size(); k++) {

							int idAtt = d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
									.getId();
							d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
									.buildlistIndicatori(db, d.getElencoSezioni().get(i).getElencoPiani().get(j)
											.getElencoAttivita().get(k).getId(), statoDpatConfig,
											Integer.parseInt(anno));

							num_indicatori_this_piano = num_indicatori_this_piano
									+ (d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k))
											.getElencoIndicatori().size();
							// SOMMA DELLE UI ATTIVITA' PER IL CALCOLO DEL SALDO
							// FINALE

							// PER OGNI INDICATORE CALCOLA L'UI TOTALE
							for (int ind = 0; ind < d.getElencoSezioni().get(i).getElencoPiani().get(j)
									.getElencoAttivita().get(k).getElencoIndicatori().size(); ind++) {
								if (d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
										.getElencoIndicatori().get(ind).getUiCalcolabile() == true) {
									int idInd = d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita()
											.get(k).getElencoIndicatori().get(ind).getId();
								} else {
									d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
											.getElencoIndicatori().get(ind).setCarico_in_ui(0);
								}
							}

						}

						if ((num_indicatori + num_indicatori_this_piano) <= num_indicatori_per_sezioni) {
							num_indicatori = num_indicatori + num_indicatori_this_piano;
							listaPianiDaIncludere.add(d.getElencoSezioni().get(i).getElencoPiani().get(j));
							newsz.setElencoPiani(listaPianiDaIncludere);
							if (j == d.getElencoSezioni().get(i).getElencoPiani().size() - 1) {
								listaSezioniSplitted.add(newsz);
							}
						} else if (num_indicatori_this_piano <= num_indicatori_per_sezioni) {
							listaSezioniSplitted.add(newsz);
							newsz = (DpatSezione) (d.getElencoSezioni().get(i)).clone();
							listaPianiDaIncludere = new ArrayList<DpatPiano>();
							listaPianiDaIncludere.add(d.getElencoSezioni().get(i).getElencoPiani().get(j));
							newsz.setElencoPiani(listaPianiDaIncludere);
							num_indicatori = num_indicatori_this_piano;
							if (j == d.getElencoSezioni().get(i).getElencoPiani().size() - 1) {
								listaSezioniSplitted.add(newsz);
							}
						} else {

							int ii = num_indicatori_this_piano / num_indicatori_per_sezioni;
							if ((num_indicatori_this_piano % num_indicatori_per_sezioni) > 0) {
								ii++;
							}
							ArrayList<DpatPiano> pp = new ArrayList<DpatPiano>();
							for (int jj = 0; jj < ii; jj++) {
								DpatPiano app_piano = new DpatPiano();// (DpatPiano)(d.getElencoSezioni().get(i).getElencoPiani().get(j));
								app_piano.setId(d.getElencoSezioni().get(i).getElencoPiani().get(j).getId());
								app_piano.setId_sezione(
										d.getElencoSezioni().get(i).getElencoPiani().get(j).getId_sezione());
								app_piano.setDescription(
										d.getElencoSezioni().get(i).getElencoPiani().get(j).getDescription());
								app_piano.setEnabled(d.getElencoSezioni().get(i).getElencoPiani().get(j).getEnabled());
								app_piano.setElencoAttivita(new ArrayList<DpatAttivita>());
								pp.add(jj, app_piano);
							}

							int index_indicatori = 0;
							for (int jj = 0; jj < pp.size(); jj++) {
								DpatPiano pp2 = pp.get(jj);
								pp2.buildlistAttivita(db, pp2.getId(), statoDpatConfig, Integer.parseInt(anno));
								for (int ll = 0; ll < pp2.getElencoAttivita().size(); ll++) {
									pp2.getElencoAttivita().get(ll).buildlistIndicatori(db,
											pp2.getElencoAttivita().get(ll).getId(), statoDpatConfig,
											Integer.parseInt(anno));
									ArrayList<DpatIndicatore> app = new ArrayList<DpatIndicatore>();
									for (int kk = 0; kk < num_indicatori_per_sezioni; kk++) {
										if (index_indicatori < pp2.getElencoAttivita().get(ll).getElencoIndicatori()
												.size()) {
											// app.add(kk,
											// pp2.getElencoAttivita().get(ll).getElencoIndicatori().get(index_indicatori));
											DpatIndicatore ind_app = pp2.getElencoAttivita().get(ll)
													.getElencoIndicatori().get(index_indicatori);
											ind_app.setCarico_in_ui(0);
											app.add(kk, ind_app);
											index_indicatori++;
										}
									}
									pp2.getElencoAttivita().get(ll).setUi(0);
									pp2.getElencoAttivita().get(ll).setElencoIndicatori(app);
									pp.set(jj, pp2);
								}

								listaSezioniSplitted.add(newsz);
								newsz = (DpatSezione) (d.getElencoSezioni().get(i)).clone();
								listaPianiDaIncludere = new ArrayList<DpatPiano>();
								listaPianiDaIncludere.add(pp.get(jj));
								newsz.setElencoPiani(listaPianiDaIncludere);
								num_indicatori = num_indicatori_this_piano;
								if (j == d.getElencoSezioni().get(i).getElencoPiani().size() - 1) {
									listaSezioniSplitted.add(newsz);
								}
							}
						}

					}
				}

				d.setElencoSezioni(listaSezioniSplitted);

				// RECORD DI DPAT_STRUTTURA_INDICATORE PER POPOLARE LE CELLE DEL
				// DPAT

				pst.close();
				rs.close();

				// context.getRequest().setAttribute("elencoStruttureComplesse",
				// elencoStruttureComplesse);
				// context.getRequest().setAttribute("elencoStrutture",
				// elencoStrutture);
			}
			context.getRequest().setAttribute("dpat", d);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		// SERVER DOCUMENTALE
		String layout = context.getRequest().getParameter("layout");
		if (layout != null && layout.equals("style"))
			return ("DpatDetailCompetenzeStyleOK");
		// SERVER DOCUMENTALE

		return "DpatDetailCompetenzeOK";
	}

	public String executeCommandDpatDetailGenerale(ActionContext context) {
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		String idAsl = (String) context.getRequest().getParameter("idAsl");
		String anno = (String) context.getRequest().getParameter("anno");

		context.getRequest().setAttribute("anno", anno);

		// DPAT PER L'ASL SELEZIONATA
		org.aspcfs.modules.dpat2019.base.Dpat d = null;
		try {
			db = this.getConnection(context);
			LookupList listaAsl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			sql = "select id from dpat where id_asl=" + idAsl + " and anno=" + anno + " and enabled ";
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();

			String comboArea = context.getRequest().getParameter("combo_area");
			int idAreaSelezionata = -1;
			if (comboArea != null & !"".equals(comboArea))
				idAreaSelezionata = Integer.parseInt(comboArea);

			while (rs.next()) {
				d = new org.aspcfs.modules.dpat2019.base.Dpat();
				d.setId(rs.getInt("id"));
				d.builRecord(d.getId(), db, this.getSystemStatus(context), idAreaSelezionata);
				d.buildlistSezioni(db, d.getAnno());
			}

			d.setIdAreaSelezionata(idAreaSelezionata);
			if (d != null) {

				DpatIstanza istanza = new DpatIstanza();
				int statoDpatConfig = istanza.getStatoFromAnno(db, Integer.parseInt(anno));

				int num_indicatori_per_sezioni = Integer
						.parseInt(ApplicationProperties.getProperty("NR_INDICATORI_PER_SEZIONE"));
				int num_indicatori = 0;
				ArrayList<DpatSezione> listaSezioniSplitted = new ArrayList<DpatSezione>();
				// ELENCO SEZIONI, PIANI, ATTIVITA' ED INDICATORI DEL DPAT

				// PER OGNI SEZIONE COSTRUISCI LA LISTA DEI PIANI
				for (int i = 0; i < d.getElencoSezioni().size(); i++) {
					d.getElencoSezioni().get(i).buildlistPiani(db, d.getElencoSezioni().get(i).getId(), statoDpatConfig,
							Integer.parseInt(anno));

					DpatSezione newsz = (DpatSezione) (d.getElencoSezioni().get(i)).clone();
					ArrayList<DpatPiano> listaPianiDaIncludere = new ArrayList<DpatPiano>();
					int num_indicatori_this_piano = 0;
					num_indicatori = 0;

					// PER OGNI PIANO COSTRUISCI LA LISTA DELLE ATTIVITA'
					for (int j = 0; j < d.getElencoSezioni().get(i).getElencoPiani().size(); j++) {
						d.getElencoSezioni().get(i).getElencoPiani().get(j).buildlistAttivita(db,
								d.getElencoSezioni().get(i).getElencoPiani().get(j).getId(), statoDpatConfig,
								Integer.parseInt(anno));

						num_indicatori_this_piano = 0;
						// PER OGNI ATTIVITA CALCOLA L'UI TOTALE E COSTRUISCI LA
						// LISTA DEGLI INDICATORI
						for (int k = 0; k < d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita()
								.size(); k++) {

							int idAtt = d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
									.getId();
							
								d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
										.setUi(0.0);

							d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
									.buildlistIndicatori(db, d.getElencoSezioni().get(i).getElencoPiani().get(j)
											.getElencoAttivita().get(k).getId(), statoDpatConfig,
											Integer.parseInt(anno));

							num_indicatori_this_piano = num_indicatori_this_piano
									+ (d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k))
											.getElencoIndicatori().size();
							// PER OGNI INDICATORE CALCOLA L'UI TOTALE
							for (int ind = 0; ind < d.getElencoSezioni().get(i).getElencoPiani().get(j)
									.getElencoAttivita().get(k).getElencoIndicatori().size(); ind++) {
								
									d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k)
											.getElencoIndicatori().get(ind).setCarico_in_ui(0);
							}

						}

						if ((num_indicatori + num_indicatori_this_piano) <= num_indicatori_per_sezioni) {
							num_indicatori = num_indicatori + num_indicatori_this_piano;
							listaPianiDaIncludere.add(d.getElencoSezioni().get(i).getElencoPiani().get(j));
							newsz.setElencoPiani(listaPianiDaIncludere);
							if (j == d.getElencoSezioni().get(i).getElencoPiani().size() - 1) {
								listaSezioniSplitted.add(newsz);
							}
						} else if (num_indicatori_this_piano <= num_indicatori_per_sezioni) {
							listaSezioniSplitted.add(newsz);
							newsz = (DpatSezione) (d.getElencoSezioni().get(i)).clone();
							listaPianiDaIncludere = new ArrayList<DpatPiano>();
							listaPianiDaIncludere.add(d.getElencoSezioni().get(i).getElencoPiani().get(j));
							newsz.setElencoPiani(listaPianiDaIncludere);
							num_indicatori = num_indicatori_this_piano;
							if (j == d.getElencoSezioni().get(i).getElencoPiani().size() - 1) {
								listaSezioniSplitted.add(newsz);
							}
						} else {

							int ii = num_indicatori_this_piano / num_indicatori_per_sezioni;
							if ((num_indicatori_this_piano % num_indicatori_per_sezioni) > 0) {
								ii++;
							}
							ArrayList<DpatPiano> pp = new ArrayList<DpatPiano>();
							for (int jj = 0; jj < ii; jj++) {
								DpatPiano app_piano = new DpatPiano();// (DpatPiano)(d.getElencoSezioni().get(i).getElencoPiani().get(j));
								app_piano.setId(d.getElencoSezioni().get(i).getElencoPiani().get(j).getId());
								app_piano.setId_sezione(
										d.getElencoSezioni().get(i).getElencoPiani().get(j).getId_sezione());
								app_piano.setDescription(
										d.getElencoSezioni().get(i).getElencoPiani().get(j).getDescription());
								app_piano.setEnabled(d.getElencoSezioni().get(i).getElencoPiani().get(j).getEnabled());
								app_piano.setElencoAttivita(new ArrayList<DpatAttivita>());
								pp.add(jj, app_piano);
							}

							int index_indicatori = 0;
							for (int jj = 0; jj < pp.size(); jj++) {
								DpatPiano pp2 = pp.get(jj);
								pp2.buildlistAttivita(db, pp2.getId(), statoDpatConfig, Integer.parseInt(anno));
								for (int ll = 0; ll < pp2.getElencoAttivita().size(); ll++) {
									pp2.getElencoAttivita().get(ll).buildlistIndicatori(db,
											pp2.getElencoAttivita().get(ll).getId(), statoDpatConfig,
											Integer.parseInt(anno));
									ArrayList<DpatIndicatore> app = new ArrayList<DpatIndicatore>();
									for (int kk = 0; kk < num_indicatori_per_sezioni; kk++) {
										if (index_indicatori < pp2.getElencoAttivita().get(ll).getElencoIndicatori()
												.size()) {
											// app.add(kk,
											// pp2.getElencoAttivita().get(ll).getElencoIndicatori().get(index_indicatori));
											// index_indicatori++;
											DpatIndicatore ind_app = pp2.getElencoAttivita().get(ll)
													.getElencoIndicatori().get(index_indicatori);
											ind_app.setCarico_in_ui(0);
											app.add(kk, ind_app);
											index_indicatori++;
										}
									}
									
									pp2.getElencoAttivita().get(ll).setUi(0);
									pp2.getElencoAttivita().get(ll).setElencoIndicatori(app);
									pp.set(jj, pp2);
								}

								listaSezioniSplitted.add(newsz);
								newsz = (DpatSezione) (d.getElencoSezioni().get(i)).clone();
								listaPianiDaIncludere = new ArrayList<DpatPiano>();
								listaPianiDaIncludere.add(pp.get(jj));
								newsz.setElencoPiani(listaPianiDaIncludere);
								num_indicatori = num_indicatori_this_piano;
								if (j == d.getElencoSezioni().get(i).getElencoPiani().size() - 1) {
									listaSezioniSplitted.add(newsz);
								}
							}
						}
					}
				}
				d.setElencoSezioniSplitted(listaSezioniSplitted);

				// CALCOLO SALDO PER OGNI STRUTTURA
				for (int i = 0; i < d.getElencoStrutture().size(); i++) {
					int idStrutt = d.getElencoStrutture().get(i).getId();
					int idDpat = d.getId();
					// double saldo = carico -
					// calcolaSaldoPerStruttura(idStrutt, idDpat, db); //DAVIDE
					double saldo = ottieniSaldoPerStrutturaDaDb(idStrutt, db); // DAVIDE
					d.getElencoStrutture().get(i).buildElenco(db, idStrutt, idDpat, idAreaSelezionata);
				}

				DpatStrutturaIndicatore dsi = new DpatStrutturaIndicatore();
				HashMap<Integer, HashMap<Integer, DpatStrutturaIndicatore>> dsiList = dsi.buildLista(db, d.getId(),
						idAreaSelezionata);

				context.getRequest().setAttribute("dsiList", dsiList);

				pst.close();
				rs.close();
			}
			context.getRequest().setAttribute("dpat", d);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		// SERVER DOCUMENTALE
		String layout = context.getRequest().getParameter("layout");
		if (layout != null && layout.equals("style"))
			return ("DpatDetailStyleOK");
		// SERVER DOCUMENTALE

		return "DpatDetailOK";
	}

	public String executeCommandSalvaDefinitivo(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		String idDpat = (String) context.getRequest().getParameter("id");
		String idAsl = (String) context.getRequest().getParameter("idAsl");
		String anno = (String) context.getRequest().getParameter("anno");
		Connection db = null;
		PreparedStatement pst = null;

		try {
			db = this.getConnection(context);
			DpatStrumentoCalcolo sdc = new DpatStrumentoCalcolo(db, Integer.parseInt(idAsl), Integer.parseInt(anno),
					this.getSystemStatus(context), -1);
			sdc.congelaStrumentoCalcolo(db,utente.getUserId());

			String sql = "update dpat set completo=true, modified=now(), modified_by=" + utente.getUserId()
					+ " where id=" + idDpat;

			pst = db.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();

			pst = db.prepareStatement("update dpat_risorse_strumentali set completo=true, modified=now(), modifiedby="
					+ utente.getUserId() + " where id_asl=" + idAsl + " and anno = " + anno);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idAsl", idAsl);
		context.getRequest().setAttribute("anno", anno);
		return executeCommandDpatDetailGenerale(context);
	}

	public String executeCommandCongelaDpat(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		String idDpat = (String) context.getRequest().getParameter("id");
		String idAsl = (String) context.getRequest().getParameter("idAsl");
		String anno = (String) context.getRequest().getParameter("anno");
		Connection db = null;
		PreparedStatement pst = null;
		String sql = "update dpat set congelato=true, dta_congelamento=now(), modified_by=" + utente.getUserId()
				+ " where id=" + idDpat;
		try {
			db = this.getConnection(context);
			pst = db.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		context.getRequest().setAttribute("idAsl", idAsl);
		context.getRequest().setAttribute("anno", anno);
		return executeCommandDpatDetailGenerale(context);
	}

	public String executeCommandSalvaDefinitivoCompetenze(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		String idDpat = (String) context.getRequest().getParameter("id");
		String idAsl = (String) context.getRequest().getParameter("idAsl");
		String anno = (String) context.getRequest().getParameter("anno");
		Connection db = null;
		PreparedStatement pst = null;

		String comboArea = context.getRequest().getParameter("combo_area");
		int idAreaSelezionata = -1;
		if (comboArea != null & !"".equals(comboArea))
			idAreaSelezionata = Integer.parseInt(comboArea);

		try {
			db = this.getConnection(context);

			if (idAreaSelezionata <= 0) {
				String sql = "update dpat set completo=true, modified=now(), modified_by=" + utente.getUserId()
						+ " where id=" + idDpat;
				pst = db.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
			} else {
				String sql = "update strutture_asl set stato_all6=2 where id=" + idAreaSelezionata;
				pst = db.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("ComboArea", idAreaSelezionata);
		context.getRequest().setAttribute("idAsl", idAsl);
		context.getRequest().setAttribute("anno", anno);
		return executeCommandDpatDetailGeneraleCompetenze(context);
	}

	/******************
	 * METODI UTILITY *
	 ********************/

	private int ottieniSaldoPerStrutturaDaDb(int idStruttura, Connection conn) {
		int saldo = 0;
		String sql = "select saldo from saldi_strutture where id_struttura = ?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, idStruttura);
			rs = pst.executeQuery();
			rs.next();
			if (rs.next())
				saldo = Integer.parseInt(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return saldo;

	}






	private double calcolaSaldoTotale(int idDpat, Connection db) {
		double tot = 0.0F;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		try {
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				tot = rs.getDouble("somma");
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tot;
	}

	private LookupList ritorna_lookup_tutti_comuni(ActionContext context, int idAsl, Connection db) {
		LookupList ret = new LookupList();
		int i = -1;

		PreparedStatement stat = null;
		ResultSet res = null;
		String sql = "SELECT codiceistatcomune, comune FROM comuni ";
		if (idAsl > 0) {
			sql += " where 1=1 and codiceistatasl='" + idAsl + "'";
		}

		try {
			stat = db.prepareStatement(sql);
			res = stat.executeQuery();

			while (res.next()) {
				LookupElement thisElement = new LookupElement();
				thisElement.setCode(Integer.parseInt(res.getString(1)));
				thisElement.setDescription(res.getString(2));
				thisElement.setEnabled(true);
				ret.add(thisElement);
			}
			return (ret);

		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		}
	}
	
	

	public String executeCommandSearchPianiMonitoraggio(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-view")) {
			return ("PermissionError");
		}
		PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListPianiInfo");

		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (context.getParameter("anno") != null)
			year = Integer.parseInt(context.getParameter("anno"));

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		PianoMonitoraggioList ticList = new PianoMonitoraggioList();
		String tipo = context.getRequest().getParameter("searchcodetipo_piano");
		if (tipo == null || tipo.equals("")) {
			tipo = (String) context.getRequest().getAttribute("searchcodetipo_piano");
		}

		String descrizione = context.getRequest().getParameter("searchdescrizione_piano");
		if (descrizione == null || descrizione.equals("")) {
			descrizione = (String) context.getRequest().getAttribute("searchdescrizione_piano");
		}
		String link = "dpat.do?command=SearchPianiMonitoraggio";
		if (tipo != null) {
			ticList.setTipo(Integer.parseInt(tipo));
			link += "&searchcodetipo_piano=" + tipo;
			context.getRequest().setAttribute("searchcodetipo_piano", Integer.parseInt(tipo));
		}
		if (descrizione != null) {
			ticList.setDescrizione(descrizione);
			link += "&searchdescrizione_piano=" + descrizione;
			context.getRequest().setAttribute("searchdescrizione_piano", descrizione);
		}
		ticListInfo.setLink(link);

		String includiAttivita = context.getParameter("includiAttivita");

		if (includiAttivita != null) {
			context.getRequest().setAttribute("includiAttivita", includiAttivita);
			if ("si".equalsIgnoreCase(includiAttivita)) {
				ticList.setIncludeAttivita(true);
			} else
				ticList.setIncludeAttivita(false);
		}

		ticListInfo.setSearchCriteria(ticList, context);
		ticList.setPagedListInfo(ticListInfo);

		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);

			DpatIstanza istanzaDpat = new DpatIstanza(db, year);
			//DpatWrapperSezioniBean dpatNew = new DpatWrapperSezioniBean(year, db,false);

			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);
			//context.getRequest().setAttribute("dpatNew", dpatNew);

			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
			
			LookupList lookup_gruppi_piani = new LookupList(db, "lookup_sezioni_piani_monitoraggio");
			lookup_gruppi_piani.addItem(-1, "-SELEZIONA GRUPPO DI MONITORAGGIO-");
			context.getRequest().setAttribute("lookup_sezioni_piani", lookup_gruppi_piani);
			ticList.buildListConfiguratore(db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicListPiani", ticList);

		if (context.getRequest().getAttribute("ErrorDpat") != null)
			context.getRequest().setAttribute("ErrorDpat", context.getRequest().getAttribute("ErrorDpat"));

		return ("ResultsOK");
	}
	
	
	
	
	
	
	public String executeCommandHomeConfiguraDpatNEW(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");

		Connection db = null;
		
		try {
			db = this.getConnection(context);
			/*devo prendere tutti i modelli non templates (quelli che non hanno nodi in stato 1, ma in 2 o 0) per gli anni
			 * e tutti quelli che invece hann otutti i nodi in stato 1, e sono caratterizzati dal nome template
			 */
			WrapperTuttiModelli wrapper = new WrapperTuttiModelli(db);
			context.getRequest().setAttribute("wrapperTuttiModelli", wrapper);
			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			context.getRequest().setAttribute("annoCorrente", calCorrente.get(Calendar.YEAR)+"");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.freeConnection(context, db);
		}

		return "DpatHomeConfiguraNEWDPATOK";
	}
	
	public String executeCommandCreaTemplateNewDpat(ActionContext context)
	{
		Connection db = null;
		ArrayList<DpatIstanza> anniList = new ArrayList<DpatIstanza>();
		String risultato = null;
		
		try {
			db = this.getConnection(context);
			/*devo prendere tutti i modelli non templates (quelli che non hanno nodi in stato 1, ma in 2 o 0) per gli anni
			 * e tutti quelli che invece hann otutti i nodi in stato 1, e sono caratterizzati dal nome template
			 */
			Integer anno = Integer.parseInt(context.getParameter("anno"));
			
			/*il nome scelto viene concatenato all'anno e viene messo un ID numerico per evitare duplicati alla fine*/
			
			WrapperTuttiModelli.creaNuovoTemplate(db,anno);
			risultato = "ok";
			
			
			
		} catch(Exception ex)
		{
			ex.printStackTrace();
			risultato = "ko";
			
		}finally {
			super.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("risultato", risultato);
		return "risultatoCreazioneTemplateDpatNew";
	}
	
	
	public String executeCommandSearchPianiMonitoraggioNewDpat(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-view")) {
			return ("PermissionError");
		}
		PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListPianiInfo");
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;

		PianoMonitoraggioList ticList = new PianoMonitoraggioList();
		String tipo = context.getRequest().getParameter("searchcodetipo_piano");
		if (tipo == null || tipo.equals("")) {
			tipo = (String) context.getRequest().getAttribute("searchcodetipo_piano");
		}

		String descrizione = context.getRequest().getParameter("searchdescrizione_piano");
		if (descrizione == null || descrizione.equals("")) {
			descrizione = (String) context.getRequest().getAttribute("searchdescrizione_piano");
		}
		String link = "dpat.do?command=SearchPianiMonitoraggioNewDpat"; /*deve mettere link della tabella di pagination verso il nuovo */
		if (tipo != null) {
			ticList.setTipo(Integer.parseInt(tipo));
			link += "&searchcodetipo_piano=" + tipo;
			context.getRequest().setAttribute("searchcodetipo_piano", Integer.parseInt(tipo));
		}
		if (descrizione != null) {
			ticList.setDescrizione(descrizione);
			link += "&searchdescrizione_piano=" + descrizione;
			context.getRequest().setAttribute("searchdescrizione_piano", descrizione);
		}
		
		/*due parametri : anno e congelato */
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if(context.getParameter("anno") != null)
		{
			year = Integer.parseInt(context.getParameter("anno"));
		}
		link += ("&anno="+year);
		context.getRequest().setAttribute("anno",new Integer(year));
		
		
		String cong = null;
		boolean congelatoBool;
		
		/*do priorita' di visibilita' all'attributo (poich' potrei averlo voluto settare esplicitamente */
		if(context.getRequest().getAttribute("congelato") != null)
		{
			cong = (String) context.getRequest().getAttribute("congelato");
		}
		else if(context.getParameter("congelato") != null)
		{
			
			cong = context.getParameter("congelato");
			context.getRequest().setAttribute("congelato", context.getParameter("congelato"));
		}
		else
		{
			cong = "false";
		}
		link += ("&congelato="+cong);
		congelatoBool = cong.equalsIgnoreCase("true");
		
		 
		
		
		
		
		ticListInfo.setLink(link);

		String includiAttivita = context.getParameter("includiAttivita");

		if (includiAttivita != null) {
			context.getRequest().setAttribute("includiAttivita", includiAttivita);
			if ("si".equalsIgnoreCase(includiAttivita)) {
				ticList.setIncludeAttivita(true);
			} else
				ticList.setIncludeAttivita(false);
		}

		ticListInfo.setSearchCriteria(ticList, context);
		ticList.setPagedListInfo(ticListInfo);

		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);

			//DpatIstanza istanzaDpat = new DpatIstanza(db, year);
			if(congelatoBool)
			{
				/*allora vogliamo aprire uno congelato */
				DpatWrapperSezioniBean dpatNew = new DpatWrapperSezioniBean(year, db,false, true);
				context.getRequest().setAttribute("dpatNew", dpatNew);
			}
			else
			{
				/*uno non congelato */
				DpatWrapperSezioniBeanPreCong dpatNew = new DpatWrapperSezioniBeanPreCong(year,db,false,true);
				context.getRequest().setAttribute("dpatNew", dpatNew);
			}
			

			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
			
			LookupList lookup_dpat_tipo_item = new LookupList(db, "lookup_dpat_tipo_item");
			context.getRequest().setAttribute("lookup_dpat_tipo_item", lookup_dpat_tipo_item);
			
			org.aspcfs.modules.dpat2019.base.Dpat dpat = new org.aspcfs.modules.dpat2019.base.Dpat();
			ArrayList<String> codiciInterniReadonly = dpat.getCodiciInterniReadonly(db,"I");
			context.getRequest().setAttribute("codiciInterniReadonlyIndicatori", codiciInterniReadonly);
			codiciInterniReadonly = dpat.getCodiciInterniReadonly(db,"P");
			context.getRequest().setAttribute("codiciInterniReadonlyPiani", codiciInterniReadonly);

			LookupList lookup_gruppi_piani = new LookupList(db, "lookup_sezioni_piani_monitoraggio");
			lookup_gruppi_piani.addItem(-1, "-SELEZIONA GRUPPO DI MONITORAGGIO-");
			context.getRequest().setAttribute("lookup_sezioni_piani", lookup_gruppi_piani);
			ticList.buildListConfiguratore(db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicListPiani", ticList);

		if (context.getRequest().getAttribute("ErrorDpat") != null)
			context.getRequest().setAttribute("ErrorDpat", context.getRequest().getAttribute("ErrorDpat"));

		return ("ResultsOKNEWDPAT");
	}
	
	
	
//	public String executeCommandSearchPianiMonitoraggioAttivi(ActionContext context) throws ParseException {
//		if (!hasPermission(context, "piani-piani-view")) {
//			return ("PermissionError");
//		}
//		PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListPianiInfo");
//
//		UserBean user = (UserBean) context.getSession().getAttribute("User");
//		Connection db = null;
//		PianoMonitoraggioList ticList = new PianoMonitoraggioList();
//		String tipo = context.getRequest().getParameter("searchcodetipo_piano");
//		if (tipo == null || tipo.equals("")) {
//			tipo = (String) context.getRequest().getAttribute("searchcodetipo_piano");
//		}
//
//		String descrizione = context.getRequest().getParameter("searchdescrizione_piano");
//		if (descrizione == null || descrizione.equals("")) {
//			descrizione = (String) context.getRequest().getAttribute("searchdescrizione_piano");
//		}
//		String link = "dpat.do?command=SearchPianiMonitoraggioAttivi";
//		if (tipo != null) {
//			ticList.setTipo(Integer.parseInt(tipo));
//			link += "&searchcodetipo_piano=" + tipo;
//			context.getRequest().setAttribute("searchcodetipo_piano", Integer.parseInt(tipo));
//		}
//		if (descrizione != null) {
//			ticList.setDescrizione(descrizione);
//			link += "&searchdescrizione_piano=" + descrizione;
//			context.getRequest().setAttribute("searchdescrizione_piano", descrizione);
//		}
//		ticListInfo.setLink(link);
//
//		String includiAttivita = context.getParameter("includiAttivita");
//
//		if (includiAttivita != null) {
//			context.getRequest().setAttribute("includiAttivita", includiAttivita);
//			if ("si".equalsIgnoreCase(includiAttivita)) {
//				ticList.setIncludeAttivita(true);
//			} else
//				ticList.setIncludeAttivita(false);
//		}
//
//		ticListInfo.setSearchCriteria(ticList, context);
//		ticList.setPagedListInfo(ticListInfo);
//
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//
//		SystemStatus systemStatus = this.getSystemStatus(context);
//		try {
//			db = this.getConnection(context);
//
//			DpatIstanza istanzaDpat = new DpatIstanza(db, year);
//			istanzadpat.setValoreFlagPianiImportati(db, year);
//			istanzadpat.setValoreFlagAttivitaImportate(db, year);
//			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);
//
//			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
//			context.getRequest().setAttribute("lookup_asl", lookup_asl);
//
//			LookupList lookup_gruppi_piani = new LookupList(db, "lookup_sezioni_piani_monitoraggio");
//			lookup_gruppi_piani.addItem(-1, "-SELEZIONA GRUPPO DI MONITORAGGIO-");
//			context.getRequest().setAttribute("lookup_sezioni_piani", lookup_gruppi_piani);
//			ticList.buildList(db);
//		} catch (Exception e) {
//			context.getRequest().setAttribute("Error", e);
//			return ("SystemError");
//		} finally {
//			this.freeConnection(context, db);
//		}
//		context.getRequest().setAttribute("TicListPiani", ticList);
//
//		if (context.getRequest().getAttribute("ErrorDpat") != null)
//			context.getRequest().setAttribute("ErrorDpat", context.getRequest().getAttribute("ErrorDpat"));
//
//		return ("ResultsAttiviOK");
//	}

	public String executeCommandToReplace(ActionContext context)
			throws ParseException { /*
									 * Chiamato sia per piano/attivita che
									 * indicatore, con cessato = si quando si
									 * cessa
									 */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {

			int anno = Integer.parseInt(context.getParameter("anno"));

			DpatIstanza istanzaDpat = new DpatIstanza();
			istanzaDpat.setAnno(anno);
			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);

			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);

			context.getRequest().setAttribute("Cessazione", context.getParameter("cessato"));

			String tipoInserimento = context.getParameter("tipoInserimento");

			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento"));

			if ("dpat_indicatore".equalsIgnoreCase(context.getParameter("tipoPianoAtt"))) {
				DpatIndicatore indicatore = new DpatIndicatore(db, idPianoRiferimento, anno);
				indicatore.setTipoInserimento(tipoInserimento);
				context.getRequest().setAttribute("PianoRiferimentoInd",
						indicatore); /* vecchia gestione */
			} else {
				DpatAttivita att = new DpatAttivita(db, idPianoRiferimento,
						anno);/* vecchia gestione */
				att.setTipoInserimento(tipoInserimento);

				context.getRequest().setAttribute("PianoRiferimentoAtt", att);
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		
		
		
		
		return ("ReplaceOK");
	}
	
	
 
	public String executeCommandDisabilitaEntry(ActionContext context) /*nuova gestione */
			throws ParseException {  
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {

			int anno = Integer.parseInt(context.getParameter("anno"));
			context.getRequest().setAttribute("anno", anno+"");
			
			boolean congelato = context.getParameter("congelato") != null && Boolean.parseBoolean(context.getRequest().getParameter("congelato"));
			context.getRequest().setAttribute("congelato", context.getParameter("congelato"));
//			DpatIstanza istanzaDpat = new DpatIstanza();
//			istanzadpat.setAnno(anno);
//			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);

			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);

			context.getRequest().setAttribute("Cessazione", context.getParameter("cessato"));

			String tipoInserimento = context.getParameter("tipoInserimento");

			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento"));

			Integer oidSezione = null;
			Integer oidPiano = null;
			
			if ("dpat_indicatore".equalsIgnoreCase(context.getParameter("tipoPianoAtt"))) {
				
				Indicatore indicatoreNewDpat = new Indicatore().buildByOid(db, idPianoRiferimento,false);
				indicatoreNewDpat.setTipoInserimento(tipoInserimento);
				context.getRequest().setAttribute("IndicatoreNewDPat", indicatoreNewDpat);
				
				oidPiano = indicatoreNewDpat.getOidPianoAttivita() != null ? indicatoreNewDpat.getOidPianoAttivita().intValue() : null;
				if(null != oidPiano)
				{
					PianoAttivitaInterface piano = null;
					if(congelato)
						piano = new PianoAttivita().buildByOid(db, oidPiano,false,true, anno);
					else
						piano = new PianoAttivita().buildByOid(db, oidPiano,false,true, anno);
					
					if(null != piano )
					{
						oidSezione = piano.getOidSezione() != null ? piano.getOidSezione().intValue() : null;
					}
					
				}
				
				
				
				
			} else {
				
				PianoAttivitaInterface pianoAttivitaNewDPat = new PianoAttivita().buildByOid(db, idPianoRiferimento,false,true,anno);
				
				pianoAttivitaNewDPat.setTipoInserimento(tipoInserimento);
				context.getRequest().setAttribute("PianoAttivitaNewDPat", pianoAttivitaNewDPat);
				
				oidSezione = pianoAttivitaNewDPat.getOidSezione() != null ? pianoAttivitaNewDPat.getOidSezione().intValue() : null;
				
			}
			
			if(null != oidSezione)
			{
				Sezione sez = new Sezione();
				sez =sez.buildByOid(db, oidSezione,false,true, congelato?sez.statiCongString:null, congelato?sez.statiCongString:sez.statiPreCongString,congelato?sez.statiCongString:null);
				context.getRequest().setAttribute("SezioneNewDpat", sez);
			}
			
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		
		
		return ("DisattivaEntryOK");
	}
	
	
	

	public String executeCommandToMoveAttivita(ActionContext context)
			throws ParseException {/* chiamato per update su attivita */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {

			int anno = Integer.parseInt(context.getParameter("anno"));

			DpatIstanza istanzaDpat = new DpatIstanza();
			istanzaDpat.setAnno(anno);
			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);

			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);

			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento"));

			DpatAttivita att = new DpatAttivita(db, idPianoRiferimento, anno);
			context.getRequest().setAttribute("PianoRiferimentoAtt", att);

			org.aspcfs.modules.dpat2019.base.Dpat dpat = new org.aspcfs.modules.dpat2019.base.Dpat();
			dpat.buildlistSezioni(db, anno);
			context.getRequest().setAttribute("ListaSezioniDpat", dpat);

			return ("MoveAttivitaOK");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	
	
	public String executeCommandUpdateEntryAttivita(ActionContext context)
	{ /* chiamato per update attivita */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {

			int anno = Integer.parseInt(context.getParameter("anno"));
			context.getRequest().setAttribute("anno", anno+"");
			
			boolean congelato = false;
			if(context.getRequest().getAttribute("congelato") != null)
			{
				congelato = Boolean.parseBoolean((String)context.getRequest().getAttribute("congelato"));
			}
			else if(context.getParameter("congelato") != null)
			{
				congelato = Boolean.parseBoolean(context.getParameter("congelato"));
			}
			context.getRequest().setAttribute("congelato", congelato+"");
			

			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
			
			int idAttivita = Integer.parseInt(context.getParameter("idPianoRiferimento"));
			//Indicatore indicatore = Indicatore.buildByOid(db, idPianoRiferimento,false);
			PianoAttivitaInterface toolPA = new PianoAttivita();
			PianoAttivitaInterface pianoAttivita = null;
			Sezione toolSez = new Sezione();
			SezioneInterface sezione = null;
			DpatWrapperSezioniNewBeanAbstract sez = new DpatWrapperSezioniBean(anno,db,true,false);
			
			pianoAttivita = toolPA.buildByOid(db,idAttivita,true,true,anno);
			sezione = toolSez.buildByOid(db,pianoAttivita.getOidSezione().intValue(),true,true, congelato?toolSez.statiCongString:null, congelato?toolSez.statiCongString:toolSez.statiPreCongString,congelato?toolSez.statiCongString:null);
			
			
//			context.getRequest().setAttribute("IndicatoreNewDPat", indicatore); 
			context.getRequest().setAttribute("PianoAttivitaNewDPat", pianoAttivita);
			context.getRequest().setAttribute("SezioneNewDPat", sezione);
			context.getRequest().setAttribute("ListaSezioniNewDPat", sez);
				

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("UpdateAttivitaOK");
	
		
	}
	
	
	public String executeCommandUpdateEntryIndicatore(ActionContext context)
			throws ParseException { /* chiamato per update indicatore */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {

			int anno = Integer.parseInt(context.getParameter("anno"));
			context.getRequest().setAttribute("anno", anno+"");
			
			boolean congelato = false;
			if(context.getRequest().getAttribute("congelato") != null)
			{
				congelato = Boolean.parseBoolean((String)context.getRequest().getAttribute("congelato"));
			}
			else if(context.getRequest().getParameter("congelato") != null)
			{
				congelato = Boolean.parseBoolean((String)context.getRequest().getParameter("congelato"));
			}
			context.getRequest().setAttribute("congelato", congelato+"");

			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
			
			LookupList TipoItemDpat = new LookupList(db, "lookup_dpat_tipo_item");
			TipoItemDpat.addItem(-1,  "<-- Selezionare una voce -->");
			context.getRequest().setAttribute("TipoItemDpat", TipoItemDpat);
			

			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento"));
			
			Indicatore indicatore = null;
			PianoAttivitaInterface pianoAttivita = null;
			Sezione sezione = new Sezione();
			DpatWrapperSezioniNewBeanAbstract sez = null;
			if(congelato)
			{
				 indicatore = new Indicatore().buildByOid(db, idPianoRiferimento,false);
				 pianoAttivita = new PianoAttivita().buildByOid(db,indicatore.getOidPianoAttivita().intValue(),true,true,anno);
				 sezione = sezione.buildByOid(db, pianoAttivita.getOidSezione().intValue(),true,true, congelato?sezione.statiCongString:null, congelato?sezione.statiCongString:sezione.statiPreCongString,congelato?sezione.statiCongString:null);
				 sez =  new DpatWrapperSezioniBean(anno,db,true,true);
			}
			else
			{ 
				indicatore = new Indicatore().buildByOid(db, idPianoRiferimento,false);
				 pianoAttivita = new PianoAttivita().buildByOid(db,indicatore.getOidPianoAttivita().intValue(),true,true,anno);
				 sezione = sezione.buildByOid(db, pianoAttivita.getOidSezione().intValue(),true,true, congelato?sezione.statiCongString:null, congelato?sezione.statiCongString:sezione.statiPreCongString,congelato?sezione.statiCongString:null);
				 sez =  new DpatWrapperSezioniBeanPreCong(anno,db,true,true);
				
			}
			
		
			
			context.getRequest().setAttribute("IndicatoreNewDPat", indicatore); 
			context.getRequest().setAttribute("PianoAttivitaNewDPat", pianoAttivita);
			context.getRequest().setAttribute("SezioneNewDPat", sezione);
			context.getRequest().setAttribute("ListaSezioniNewDPat", sez);
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("UpdateIndicatoreOK");
	}
	
	
	public String executeCommandToMoveIndicatore(ActionContext context)
			throws ParseException { /* chiamato per update indicatore */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {

			int anno = Integer.parseInt(context.getParameter("anno"));

			DpatIstanza istanzaDpat = new DpatIstanza();
			istanzaDpat.setAnno(anno);
			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);

			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);

			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento"));
			DpatIndicatore indicatore = new DpatIndicatore(db, idPianoRiferimento, anno);
			context.getRequest().setAttribute("PianoRiferimentoInd", indicatore);

			org.aspcfs.modules.dpat2019.base.Dpat dpat = new org.aspcfs.modules.dpat2019.base.Dpat();
			dpat.buildlistSezioni(db, anno);
			context.getRequest().setAttribute("ListaSezioniDpat", dpat);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("MoveIndicatoreOK");
	}

	public String executeCommandCongelaConfigurazioneDpat(ActionContext context) throws ParseException {

		int idDpatIstanza = Integer.parseInt(context.getParameter("idDpatIstanza"));
		Connection db = null;

		try {
			db = this.getConnection(context);
			DpatIstanza istanzaDpat = new DpatIstanza(db, idDpatIstanza);
			istanzaDpat.congelaConfigurazione(db);

		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandSearchPianiMonitoraggio(context);
	}
	
	
	

	public String executeCommandCongelaConfigurazioneDpatNEW(ActionContext context) throws ParseException {

		Connection db = null;
		try {
			db = this.getConnection(context);
			int anno = Integer.parseInt(context.getParameter("anno"));
			WrapperTuttiModelli.congela(db,anno);
			refreshMotiviCu(db, anno, true);
			/*setto come congelato poiche' lo sto congelando */
			
			context.getRequest().setAttribute("congelato", "true"); //---> questo fara' si che invocando executeCommandSearchPianiMonitoraggioNewDpat
			//venga ricercato modello per questo stesso anno, ma congelato, ovvero proprio questo che passa da non congelato a congelato
			
		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {
			this.freeConnection(context, db);
		}
		
		return executeCommandSearchPianiMonitoraggioNewDpat(context);
	}
	
	public String executeCommandNuovaEntry(ActionContext context) /*nuova gestione */
			throws ParseException { /*
									 * Chiamata quando si aggiunge
									 * piano/attivita o indicatore (discrimina
									 * tipoPianoAttivita)
									 */
		String viewName = null;
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			db = this.getConnection(context);

			int anno = Integer.parseInt(context.getParameter("anno"));
			context.getRequest().setAttribute("anno", anno+"");
			
			String tipoInserimento = context.getParameter("tipoInserimento");
			context.getRequest().setAttribute("congelato", context.getParameter("congelato"));
			
			LookupList TipoItemDpat = new LookupList(db, "lookup_dpat_tipo_item");
			TipoItemDpat.addItem(-1,  "<-- Selezionare una voce -->");
			context.getRequest().setAttribute("TipoItemDpat", TipoItemDpat);
			
			/*DpatIstanza istanzaDpat = new DpatIstanza();
			istanzadpat.setAnno(anno);
			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);
			*/
			
			int idPianoRiferimento = Integer.parseInt(context.getParameter(
					"idPianoRiferimento")); /*
											 * a seconda del tipoPianoAtt questo
											 * e' id piano_attivita o id
											 * indicatore
											 */
			 
			 
			
			
			if (tipoInserimento.equalsIgnoreCase("firstchild")) /*se tipo inserimento e' firstchild, sicuramente e' per un tipoPianoAttivita = indicatore */
			{ 
				PianoAttivita pianoAttivitaRiferimento = new PianoAttivita();
				pianoAttivitaRiferimento = pianoAttivitaRiferimento.buildByOid(db, idPianoRiferimento, false, true,anno);
				pianoAttivitaRiferimento.setTipoInserimento("firstchild");
				context.getRequest().setAttribute("PianoAttivitaNewDPat", pianoAttivitaRiferimento);
			} 
			else 
			{
				int idPianoAttivitaPadre = idPianoRiferimento;
				if ("dpat_indicatore".equalsIgnoreCase(context.getParameter("tipoPianoAtt"))) /*distinguo se e' template o no */
				{
					Indicatore indicatoreNewDpat = new Indicatore();
					indicatoreNewDpat = indicatoreNewDpat.buildByOid(db, idPianoRiferimento,false);  
					indicatoreNewDpat.setTipoInserimento(tipoInserimento);
					idPianoAttivitaPadre = indicatoreNewDpat.getIdPianoAttivita();
					context.getRequest().setAttribute("IndicatoreNewDPat", indicatoreNewDpat);
					
				} 
				PianoAttivita pianoAttivitaNewDPat = new PianoAttivita();
				pianoAttivitaNewDPat = pianoAttivitaNewDPat.buildByOid(db, idPianoAttivitaPadre, false, true,anno);
				pianoAttivitaNewDPat.setTipoInserimento(tipoInserimento);
				context.getRequest().setAttribute("PianoAttivitaNewDPat", pianoAttivitaNewDPat);
			}

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		
		return ("AggiungiEntryOK");
	}
	

	public String executeCommandAdd(ActionContext context)
			throws ParseException { /*
									 * Chiamata quando si aggiunge
									 * piano/attivita o indicatore (discrimina
									 * tipoPianoAttivita)
									 */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			db = this.getConnection(context);

			int anno = Integer.parseInt(context.getParameter("anno"));
			String tipoInserimento = context.getParameter("tipoInserimento");

			DpatIstanza istanzaDpat = new DpatIstanza();
			istanzaDpat.setAnno(anno);

			context.getRequest().setAttribute("IstanzaDpat", istanzaDpat);
			int idPianoRiferimento = Integer.parseInt(context.getParameter(
					"idPianoRiferimento")); /*
											 * a seconda del tipoPianoAtt questo
											 * e' id piano_attivita o id
											 * indicatore
											 */

			if (tipoInserimento.equalsIgnoreCase("firstchild")) {
				DpatAttivita att = new DpatAttivita(db, idPianoRiferimento, anno);
				att.setTipoInserimento(tipoInserimento);

				DpatIndicatore indicatore = new DpatIndicatore();
				indicatore.setTipoInserimento(tipoInserimento);
				indicatore.setDescrizioneAttivita(att.getDescription());
				indicatore.setDescrizioneAttivita(att.getDescription());
				indicatore.setCodiceAttivita(att.getCodiceInterno());
				indicatore.setId_attivita(att.getId());
				indicatore.setIdAttivita_(att.getId_());
				indicatore.setDescription(att.getDescription());
				indicatore.setId(att.getId());
				context.getRequest().setAttribute("PianoRiferimentoInd", indicatore);

			} else {

				if ("dpat_indicatore".equalsIgnoreCase(context.getParameter("tipoPianoAtt"))) {
					DpatIndicatore indicatore = new DpatIndicatore(db, idPianoRiferimento, anno);// vecchia
																									// gestione

					indicatore.setTipoInserimento(tipoInserimento);
					context.getRequest().setAttribute("PianoRiferimentoInd", indicatore);
				} else {
					DpatAttivita att = new DpatAttivita(db, idPianoRiferimento, anno);
					att.setTipoInserimento(tipoInserimento);
					context.getRequest().setAttribute("PianoRiferimentoAtt", att);
				}

			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("AddOK");
	}

	public String executeCommandElimina(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
			PianoMonitoraggio piano = new PianoMonitoraggio();
			piano.setCode(Integer.parseInt(context.getRequest().getParameter("code")));
			piano.setModifiedby(user.getUserId());
			piano.deleteConfiguratore(db);
			context.getRequest().setAttribute("searchdescrizione_piano",
					context.getRequest().getParameter("searchdescrizione_piano"));
			context.getRequest().setAttribute("searchcodetipo_piano",
					context.getRequest().getParameter("searchcodetipo_piano"));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandSearchPianiMonitoraggio(context);
	}

	public String executeCommandInsertEffettivo(ActionContext context)
	{  
		if (!hasPermission(context, "piani-piani-add"))
		{
			return ("PermissionError");
		}

		Connection db = null;
		boolean autocommit = false;
		try
		{
			db = this.getConnection(context);
			autocommit = db.getAutoCommit();
			db.setAutoCommit(false);
			
			UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
			
			System.out.println("### DPAT ### INSERIMENTO INDICATORE/PIANO");
			
			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento")); /*a seconda del tipo di inserimento e' id piano/attivita o id indicatore */
			int idIndicatore = Integer.parseInt(context.getParameter("idIndicatore"));
			int anno = Integer.parseInt(context.getParameter("anno"));
			String descrizione = context.getRequest().getParameter("descrizione");
			if(descrizione!=null)
			{
				descrizione = descrizione.replace("\r\n", " ");
				descrizione = descrizione.replace("\n", " ");
			}
			
			String codice_esame = context.getRequest().getParameter("codice_esame");
			String asl = context.getRequest().getParameter("asl");
			String tipoAttivita = context.getParameter("tipoAttivita");
			String alias = context.getParameter("alias");
			String tipoInserimento = context.getParameter("tipoInserimento");
			String codiceAlias = context.getParameter("cup");
			String tipoItemDpat = context.getParameter("tipoItemDpat");
			String rendicPerCampione = context.getParameter("rendicPerCampione");
			
			System.out.println("### DPAT ### INPUT: anno = " + anno + ", idIndicatore = " + idIndicatore + ", idPianoRiferimento = " + idPianoRiferimento
					+ ", descrizione = " + descrizione + ", codice_esame = " + codice_esame + ", asl = " + asl + ", tipoAttivita = " + tipoAttivita
					+ ", alias = " + alias + ", tipoInserimento = " + tipoInserimento + ", codiceAlias = " + codiceAlias );
			
			context.getRequest().setAttribute("anno", anno+"");
			context.getRequest().setAttribute("congelato", context.getRequest().getParameter("congelato"));
			
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);
		 
			 PreparedStatement pst = db.prepareStatement("select * from dpat_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			 pst.setInt(1, idIndicatore);
			 pst.setString(2, tipoInserimento);
			 pst.setInt(3, idPianoRiferimento);
			 pst.setString(4, descrizione);
			 pst.setString(5, codice_esame);
			 pst.setString(6, asl);
			 pst.setString(7, tipoAttivita);
			 pst.setString(8, alias);
			 pst.setString(9, codiceAlias);
			 pst.setString(10, context.getParameter("tipoPianoAttInd"));
			 pst.setInt(11, anno);
			 pst.setInt(12, utente.getUserId());
			 pst.setInt(13, Integer.parseInt(tipoItemDpat));
			 pst.setBoolean(14, Boolean.parseBoolean(rendicPerCampione));

			 System.out.println("### DPAT ### ESECUZIONE QUERY : " + pst.toString());
			 
			 pst.execute();
			 
			 db.commit();
			 
			 System.out.println("### DPAT ### DISATTIVAZIONE RIUSCITA ");
			
			 context.getRequest().setAttribute("inserito", "true");

		} 
		catch (Exception e) 
		{
			System.out.println("### DPAT ### DISATTIVAZIONE NON RIUSCITA, ERRORE: " + e.getMessage());
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			try
			{
				db.rollback();
				System.out.println("ROllback eseguito");
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace(); 
			}
			return ("SystemError");
		} 
		finally 
		{
			try{db.setAutoCommit(autocommit); } catch(Exception ex){}
			this.freeConnection(context, db);
		}

		return ("AggiungiEntryOK");
	}
	
	private void refreshMotiviCu(Connection db, int anno,boolean nuovaGestione) { /*se nuova gestione e' true, la tabella dei motivi viene popolata usando le nuove viste che si agganciano alle nuove tabelle */
		 PreparedStatement pst = null;
		 try
		 {
			 
			 pst = db.prepareStatement("select * from refresh_motivi_cu(?,?)");
			 pst.setInt(1, anno);
			 pst.setBoolean(2, nuovaGestione);
			 pst.executeQuery();
			 System.out.println(">>>>>>DPAT NUOVA GESTIONE: LANCIATO REFRESH_MOTIVI_CU PER ANNO "+anno);
			 System.out.println(">>>>>>DPAT NUOVA GESTIONE: IL REFRESH_MOTIVI_CU HA FATTO FINIRE LE ENTRIES DA "+(nuovaGestione ? "NUOVO FLUSSO " : "VECCHIO FLUSSO"));
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
		 finally
		 {
			 try{pst.close();} catch(Exception ex){}
		 }
	}

	public String executeCommandInsert(ActionContext context)
			throws ParseException { /*
									 * chiamato per effettivo inserimento
									 * (indipend per piano/attivita indicatore
									 */
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			int idPianoRiferimento = Integer.parseInt(context.getParameter("idPianoRiferimento"));
			int anno = Integer.parseInt(context.getParameter("anno"));
			LookupList lookup_asl = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("lookup_asl", lookup_asl);

			String descrizione = context.getRequest().getParameter("descrizione");
			String codice_esame = context.getRequest().getParameter("codice_esame");
			;
			String asl = context.getRequest().getParameter("asl");
			String tipoAttivita = context.getParameter("tipoAttivita");
			String codiceAlias = context.getParameter("alias");
			String tipoInserimento = context.getParameter("tipoInserimento");

			DpatAttivita attivitaRiferimento = new DpatAttivita(db, idPianoRiferimento, anno);
			DpatPiano piano = new DpatPiano();
			piano.setEnabled(true);

			String note = context.getRequest().getParameter("note");
			// String tipo_piano =
			// context.getRequest().getParameter("tipo_piano");
			String sezione = context.getRequest().getParameter("sezione");

			String descrizionePiano = "";
			if (context.getParameter("tipoAttivita").contains("ATTIVITA"))
				descrizionePiano = "ATTIVITA";
			else
				descrizionePiano = "PIANO";

			if (context.getParameter("tipoPianoAttInd").equalsIgnoreCase("dpat_attivita")) {

				attivitaRiferimento.setTipoInserimento(context.getParameter("tipoInserimento"));

				String selectStato = "select max(stato) from dpat_attivita where id_piano = ? and anno =" + anno;

				PreparedStatement pst = db.prepareStatement(selectStato);
				pst.setInt(1, attivitaRiferimento.getId_piano());
				ResultSet rs = pst.executeQuery();
				int stato = 1;
				if (rs.next())
					stato = rs.getInt(1) % 2;
				piano.setId_sezione(Integer.parseInt(context.getParameter("root")));
				piano.setDescription(descrizionePiano + " " + context.getParameter("alias"));
				piano.setAlias(context.getParameter("alias"));
				piano.setTipoAttivita(context.getParameter("tipoAttivita"));
				piano.setOrdinamento(attivitaRiferimento.getOrdinamento());

				piano.setStato(stato);
				piano.insert(db, attivitaRiferimento, anno);

				DpatAttivita att = new DpatAttivita();
				att.setId_piano(piano.getId());
				att.setDescription(descrizione);
				att.setCodiceEsame(codice_esame);
				att.setStato(stato);
				att.setCodiceAlias(context.getParameter("cup"));
				att.setCodicePiano(piano.getCodiceInterno());
				att.setAlias(context.getParameter("alias"));
				att.setTipoAttivita(context.getParameter("tipoAttivita"));
				att.insert(db, attivitaRiferimento, anno);

				DpatIndicatore ind = new DpatIndicatore();
				ind.setId_attivita(att.getId());
				ind.setDescription("INDICATORE DI DEFAULT DASOSTITUIRE");
				ind.setCodiceEsame("");
				ind.setCodiceAttivita(att.getCodiceInterno());
				ind.setAlias("");
				ind.setTipoAttivita(att.getTipoAttivita());
				ind.setStato(stato);

				ind.setUiCalcolabile(false);

				DpatCoefficiente coeff = new DpatCoefficiente();
				coeff.setCoefficiente(0);

				ind.setCoefficiente(coeff);
				ind.insert(db, anno);

				context.getRequest().setAttribute("inserito", "true");
			} else {

				DpatIndicatore indRif = null;
				if ((context.getParameter("tipoInserimento")).equalsIgnoreCase("firstchild")) {
					indRif = new DpatIndicatore();
					indRif.setId_attivita(idPianoRiferimento);
					indRif.setCodiceAttivita(idPianoRiferimento);
					indRif.setOrdinamento(0);
				} else
					indRif = new DpatIndicatore(db, idPianoRiferimento, anno);

				indRif.setTipoInserimento(context.getParameter("tipoInserimento"));

				String selectStato = "select max(stato) from dpat_indicatore where id_attivita = ?";

				PreparedStatement pst = db.prepareStatement(selectStato);
				pst.setInt(1, indRif.getId_attivita());
				ResultSet rs = pst.executeQuery();
				int stato = 1;
				if (rs.next())
					stato = rs.getInt(1) % 2;

				DpatIndicatore ind = new DpatIndicatore();

				ind.setId_attivita(Integer.parseInt(context.getParameter("root")));

				ind.setCodiceAttivita(indRif.getCodiceAttivita());
				ind.setDescription(descrizione);
				ind.setCodiceEsame(codice_esame);
				ind.setAlias(context.getParameter("alias"));
				ind.setTipoAttivita(context.getParameter("tipoAttivita"));
				ind.setStato(stato);
				ind.setOrdinamento(indRif.getOrdinamento());
				ind.setCodiceAlias(context.getParameter("cup"));
				ind.setUiCalcolabile(false);
				if (context.getParameter("uiCalcolabile") != null
						&& "si".equalsIgnoreCase(context.getParameter("uiCalcolabile")))
					ind.setUiCalcolabile(true);

				DpatCoefficiente coeff = new DpatCoefficiente();
				// coeff.setCoefficiente(Double.parseDouble(
				// context.getParameter("coefficiente")));
				coeff.setCodiceIndicatore(ind.getCodiceInterno());
				ind.setCoefficiente(coeff);
				ind.insert(db, indRif, anno);
				context.getRequest().setAttribute("inserito", "true");
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("AddOK");
	}

	public String executeCommandReplace(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			int anno = Integer.parseInt(context.getParameter("anno"));

			int id = Integer.parseInt(context.getParameter("id"));

			String descrizione = context.getRequest().getParameter("descrizione");
			String note = context.getRequest().getParameter("note");
			String codice_esame = context.getRequest().getParameter("codice_esame");
			String sezione = context.getRequest().getParameter("sezione");
			String asl = context.getRequest().getParameter("asl");

			String descrizionePiano = "";
			if (context.getParameter("tipoAttivita").contains("ATTIVITA"))
				descrizionePiano = "ATTIVITA";
			else
				descrizionePiano = "PIANO";

			String cessazione = context.getParameter("cessato");

			if ("dpat_attivita".equalsIgnoreCase(context.getParameter("tipoClasse"))) {

				DpatAttivita dpatAtt = new DpatAttivita(db, id, anno);
				if (dpatAtt.getDataScadenza() == null) {

					DpatPiano piano = new DpatPiano(db, dpatAtt.getId_piano());
					piano.setEnabled(true);

					String selectStato = "select max(stato) from dpat_attivita where id_piano = ? and anno = " + anno;

					PreparedStatement pst = db.prepareStatement(selectStato);
					pst.setInt(1, dpatAtt.getId_piano());
					ResultSet rs = pst.executeQuery();
					int stato = 1;
					if (rs.next())
						stato = rs.getInt(1) % 2;

					dpatAtt.disabilitaAttivita(db, context.getParameter("dataScadenza"), anno);

					if (cessazione != null && cessazione.equalsIgnoreCase("no")) {
						piano.setStato(stato);
						piano.setId_sezione(Integer.parseInt(context.getParameter("root")));
						piano.setDescription(descrizionePiano + " " + context.getParameter("alias"));
						piano.setAlias(context.getParameter("alias"));
						piano.setTipoAttivita(context.getParameter("tipoAttivita"));

						piano.insert(db, anno);

						dpatAtt.setStato(stato);
						dpatAtt.setId_piano(piano.getId());
						dpatAtt.setCodicePiano(piano.getCodiceInterno());
						dpatAtt.setDescription(descrizione);
						dpatAtt.setCodiceEsame(codice_esame);
						dpatAtt.setAlias(context.getParameter("alias"));
						dpatAtt.setTipoAttivita(context.getParameter("tipoAttivita"));
						dpatAtt.insert(db, anno);
					}
				} else {

					String selectStato = "select max(stato) from dpat_attivita where id_piano = ? and anno = " + anno;
					int stato = 1;
					PreparedStatement pst = db.prepareStatement(selectStato);
					pst.setInt(1, dpatAtt.getId_piano());
					ResultSet rs = pst.executeQuery();

					if (rs.next())
						stato = rs.getInt(1) % 2;

					if (cessazione != null && cessazione.equalsIgnoreCase("no")) {
						dpatAtt.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), false, anno);

						dpatAtt.setDescription(descrizione);
						dpatAtt.setCodiceEsame(codice_esame);
						dpatAtt.setAlias(context.getParameter("alias"));

						dpatAtt.setTipoAttivita(context.getParameter("tipoAttivita"));
						dpatAtt.setStato(stato);

						dpatAtt.updateRecordDaAttivate(db, anno);
					} else {
						dpatAtt.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), true, anno); // setto
																													// la
																													// data
																													// scadenza
																													// anche
																													// sul
																													// record
																													// che
																													// sidovraattualizzare
					}

				}

			} else {

				DpatIndicatore ind = new DpatIndicatore(db, id, anno);
				if (ind.getDataScadenza() == null) {

					String selectStato = "select max(stato) from dpat_indicatore where id_attivita = ? and anno = "
							+ anno;

					PreparedStatement pst = db.prepareStatement(selectStato);
					pst.setInt(1, ind.getId_attivita());
					ResultSet rs = pst.executeQuery();
					int stato = 1;
					if (rs.next())
						stato = rs.getInt(1) % 2;

					ind.disabilitaIndicatore(db, context.getParameter("dataScadenza"), anno);

					if (cessazione != null && cessazione.equalsIgnoreCase("no")) {
						ind.setStato(stato);
						ind.setId_attivita(Integer.parseInt(context.getParameter("root")));
						ind.setDescription(descrizione);
						ind.setCodiceEsame(codice_esame);
						ind.setAlias(context.getParameter("alias"));
						ind.setTipoAttivita(context.getParameter("tipoAttivita"));

						ind.setUiCalcolabile(false);
						if (context.getParameter("uiCalcolabile") != null
								&& "si".equalsIgnoreCase(context.getParameter("uiCalcolabile")))
							ind.setUiCalcolabile(true);

						DpatCoefficiente coeff = new DpatCoefficiente();
						coeff.setId_indicatore(ind.getCodiceInterno());
						coeff.setCoefficiente(Double.parseDouble(context.getParameter("coefficiente")));
						coeff.setCodiceIndicatore(ind.getCodiceInterno());

						ind.setCoefficiente(coeff);

						ind.insert(db, anno);
					}
				} else {

					if (cessazione != null && cessazione.equalsIgnoreCase("no")) {
						ind.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), false);

						ind.setDescription(descrizione);
						ind.setCodiceEsame(codice_esame);
						ind.setAlias(context.getParameter("alias"));
						ind.setTipoAttivita(context.getParameter("tipoAttivita"));

						ind.setUiCalcolabile(false);
						if (context.getParameter("uiCalcolabile") != null
								&& "si".equalsIgnoreCase(context.getParameter("uiCalcolabile")))
							ind.setUiCalcolabile(true);

						ind.updateRecordDaAttivate(db, anno); // OK

					} else {
						ind.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), true);
					}

				}
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("inserito", "true");

		return ("AddOK");
	}
	
	
	
	public String executeCommandDisattivaEffettivo(ActionContext context) throws ParseException 
	{ 
		if (!hasPermission(context, "piani-piani-add")) 
		{
			return ("PermissionError");
		}

		Connection db = null;
		boolean autocommit = false;
		try 
		{
			db = this.getConnection(context);
			autocommit = db.getAutoCommit();
			db.setAutoCommit(false);
			
			System.out.println("### DPAT ### DISATTIVAZIONE INDICATORE/PIANO");
			
			int id = Integer.parseInt(context.getParameter("id"));
			int anno = Integer.parseInt(context.getParameter("anno"));
			String tipoClasse = context.getParameter("tipoClasse");
			UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
			
			System.out.println("### DPAT ### INPUT: anno = " + anno + ", id = " + id + ", tipo_classe = " + tipoClasse);
			
			context.getRequest().setAttribute("anno", anno+"");
			context.getRequest().setAttribute("congelato", context.getParameter("congelato"));
			
			PreparedStatement pst = db.prepareStatement("select * from dpat_elimina(?,?,?,?)");
			pst.setString(1, tipoClasse);
			pst.setInt(2, anno);
			pst.setInt(3, id);
			pst.setInt(4, utente.getUserId());
			
			System.out.println("### DPAT ### ESECUZIONE QUERY : " + pst.toString());
			 
			pst.execute();
			
			pst = db.prepareStatement("select * from dpat_sposta_associazioni_indicatore(?, ?, ?,?)");
			pst.setInt(1, id);
			pst.setInt(2, -1);
			pst.setBoolean(3, false);
			pst.setInt(4, utente.getUserId());
			
			System.out.println("### DPAT ### ESECUZIONE QUERY : " + pst.toString());
			 
			pst.execute();
			 
			db.commit();
			
			System.out.println("### DPAT ### DISATTIVAZIONE RIUSCITA ");
			
			
		} 
		catch (Exception e) 
		{
			System.out.println("### DPAT ### DISATTIVAZIONE NON RIUSCITA, ERRORE: " + e.getMessage());
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			try
			{
				db.rollback();
				System.out.println("rollback eseguito");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();;
			}
			return ("SystemError");
		} 
		finally 
		{
			try
			{
				db.setAutoCommit(autocommit);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("inserito", "true");
		return ("DisattivaEntryOK");
	}

	
	
	public String executeCommandModificaAttivitaEffettivo(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		PreparedStatement pst = null;
		boolean autocommit = false;
		
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			autocommit = db.getAutoCommit();
			db.setAutoCommit(false);
			
			
			int operazione = Integer.parseInt(context.getParameter("operazione"));
			int id = Integer.parseInt(context.getParameter("id"));
			//String descrizione = context.getRequest().getParameter("descrizione");
			String descrizione;
			if(context.getRequest().getParameter("descrizione") != null){
				descrizione = context.getRequest().getParameter("descrizione").trim();
			} else {
				descrizione = context.getRequest().getParameter("descrizione");
			}
			if(descrizione!=null)
			{
				descrizione = descrizione.replace("\r\n", " ");
				descrizione = descrizione.replace("\n", " ");
			}
			
			String codice_esame = context.getRequest().getParameter("codice_esame");
			String alias = context.getRequest().getParameter("alias"); /*lo usiamo come alias piano */
			String codiceAliasAttivita = context.getParameter("cup");
			
			
			System.out.println("### DPAT MODIFICA ATTIVITA' ### INPUT: descrizione = " + descrizione + ", codice_esame = " + codice_esame + ", alias = " + alias  );
			
			
			HttpServletRequest request = context.getRequest();
			Integer anno = Integer.parseInt(context.getRequest().getParameter("anno"));
			request.setAttribute("anno", anno+"");
			
			boolean congelato = false;
			if(request.getAttribute("congelato")!= null)
			{
				congelato = Boolean.parseBoolean((String)request.getAttribute("congelato"));
			}
			else if(request.getParameter("congelato") != null)
			{
				congelato = Boolean.parseBoolean((String)request.getParameter("congelato"));
			}
			request.setAttribute("congelato", congelato+"");
			
			PianoAttivitaInterface toolPA = null;
			PianoAttivitaInterface attivitaDaModificare = null, toInsert = null, pianoAttivitaDaSpostare = null, pianoAttivitaNuovoFratello,lastChild = null;
			SezioneInterface	toolSez = null;
			Integer oidInserito = null;
			
			if(congelato)
			{
				toolPA = new PianoAttivita();
				toInsert = new PianoAttivita();
				toolSez = new Sezione();
			}
			else
			{
				toolPA = new PianoAttivita();
				toInsert = new PianoAttivita();
				toolSez = new Sezione();
			}
			
			switch(operazione)
			{
				case 1 : 
				{
					/*modifica  con mantenimento dei vecchi controlli sui vecchi motivi (quindi con dup)*/
					/*l'inserimento con duplicazione e' visto come :
					 * creo un nuovo bean, e lo inserisco dopo quello che sto modificando
					 * quindi prendo i campi che arrivano dalle maschere e li metto nel bean da inserire (l'ho gia' fatto prima di chiamare questo metodo
					 * ) e prendo i campi che non arrivano da maschere, che devono quindi provenire dal bean che sto duplicando (che e'  indicatore di riferimento)
					 * e li scarico in quello da inserire
					 */
					attivitaDaModificare = toolPA.buildByOid(db,id,false,true,anno);
					toInsert.setDescrizione(descrizione);
					toInsert.setCodiceEsame(codice_esame);
					toInsert.setAliasPiano(alias);
					toInsert.setAliasAttivita(alias);
					/*if (attivitaDaModificare.getTipoAttivita()!=null && attivitaDaModificare.getTipoAttivita().equalsIgnoreCase("piano"))							
						toInsert.setAliasPiano(alias);
					else
						toInsert.setAliasAttivita(alias);
					stessi alias, corretto ? */
					
					toInsert.setCodiceAliasAttivita(codiceAliasAttivita);
					toInsert.setCodiceRaggruppamento(attivitaDaModificare.getCodiceRaggruppamento()); /*questo fa si che nell'insertBeforeOrAfter venga mantenuto e non sia considerato un vero inserimento ex novo */
					toInsert.setOidSezione(attivitaDaModificare.getOidSezione());
					toInsert.setAnno(attivitaDaModificare.getAnno());
					toInsert.setOrdine(attivitaDaModificare.getOrdine()+1);
					toInsert.setTipoAttivita(attivitaDaModificare.getTipoAttivita());
					toInsert.setCodiceInternoPiano(attivitaDaModificare.getCodiceInternoPiano());
					toInsert.setCodiceInternoAttivita(attivitaDaModificare.getCodiceInternoAttivita());
					/*aggiungo i figli che mi voglio portare (il controllo sugli scaduti verra' fatto in fase di effettivo trasferimento) */
					toInsert.setIndicatoriFigli(attivitaDaModificare.getIndicatoriFigli());
					//DA VALUTARE COSA PASSARE-- IL METODO E'DA AGGIORNARE UNENDO LE FUNZIONI DI PRECONG E CONG
					
					oidInserito =toolPA.update(toInsert,attivitaDaModificare,db,null,null,null,null,null,congelato,user.getUserId());
					//Con refactoring del DPAT 2019 e' stato deciso che l'aggiornamento del codice alias per le precedenti versioni non va fatto
					//int aggiornati = toolPA.aggiornaCodiceAliasAttivitaPerTutteVersioni(db, toInsert,user.getUserId());
					
					//System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATI AGGIORNATI "+aggiornati+" CODICI ALIAS ATTIVITA PER LO STESSO CODICE RAGGRUPPAMENTO)");
					break;
				}
				case 2 : 
				{
					/* AGGIORNATO : ELIMINATA COME CASISTICA
					 * modifica di indicatore senza mantenimento dei vecchi controlli sui vecchi motivi (quindi senza dup)*/
					
					/*PianoAttivita pianoDaModificare = PianoAttivita.buildByOid(db,id,false);;
					pianoDaModificare.updateSenzaDup(descrizione, codiceAlias,alias,codice_esame,db);
					break*/
					
					throw new Exception("OPERAZIONE NON PIU' PREVISTA");
					
				}
				case 3 : 
				{
					/*spostamento indicatore */
					/*se come posizione indico 1 (prima di) allora devo fare inserimento come fratello dell'indicatore con oid in indicatoreRiferimento, prima */
					/*altrimenti inserisco come ultimo figlio del piano/attivita */
					Integer oidSezioneNuovoPadre = Integer.parseInt(context.getRequest().getParameter("sezione"));
					Integer posizione = Integer.parseInt(context.getRequest().getParameter("posizione"));
					Integer oidPianoAttivitaFratello = Integer.parseInt(context.getRequest().getParameter("pianoAttivitaRiferimento"));
					 pianoAttivitaDaSpostare = toolPA.buildByOid(db, id,false,true,anno);
					 
					if(posizione == 1) /* inserimento di indicatore prima (up) di indicatore scelto */
					{
						 pianoAttivitaNuovoFratello = toolPA.buildByOid(db,oidPianoAttivitaFratello,false,true,anno);
						//DA VALUTARE COSA PASSARE-- IL METODO E'DA AGGIORNARE UNENDO LE FUNZIONI DI PRECONG E CONG
						oidInserito = toolPA.insertBeforeOrAfter(db, pianoAttivitaDaSpostare, pianoAttivitaNuovoFratello, "up",null,null,user.getUserId());
						
						
					}
					else /*inserimento alla fine della sezione */
					{
						/*cerco ultimo figlio (attivo) del piano attivita specificato */
						//DA VALUTARE COSA PASSARE-- IL METODO E'DA AGGIORNARE UNENDO LE FUNZIONI DI PRECONG E CONG
						 lastChild = toolPA.searchLastChildOf(db,oidSezioneNuovoPadre,false,null,null);
						//DA VALUTARE COSA PASSARE-- IL METODO E'DA AGGIORNARE UNENDO LE FUNZIONI DI PRECONG E CONG
						 oidInserito = toolPA.insertBeforeOrAfter(db, pianoAttivitaDaSpostare, lastChild, "down",null,null,user.getUserId());
						
					}
					/*disabilito quello che ho spostato, cioe' la vecchia copia */
					
					toolPA.disabilitaByOid(db, pianoAttivitaDaSpostare.getOid().intValue(), pianoAttivitaDaSpostare.getAnno(),user.getUserId());
					System.out.println(">>>>>>DPAT NUOVA GESTIONE:LO SPOSTAMENTO HA FATTO SI CHE VENISSE DISABILITATA LA VECCHIA VERSIONE CON OID "+pianoAttivitaDaSpostare.getOid().intValue()+" CON FIGLI" );
					 
				}
				
				
			}
			
			
			if(congelato)
			{
				refreshMotiviCu(db,anno,true);
			}
//			ELSE //FORSE NUN ZERV
//			{
//				REFRESHMOTIVICU(DB,ANNO,FALSE);
//			}
			
			
			/*risetto i bean affinche' la pagina di rientro non schiatti */
			PianoAttivitaInterface toResendPA = toolPA.buildByOid(db, oidInserito, true,false,anno);
			Sezione toResendSez = new Sezione();
			toResendSez = toResendSez.buildByOid(db, toResendPA.getOidSezione().intValue(), true, false, congelato?toResendSez.statiCongString:null, congelato?toResendSez.statiCongString:toResendSez.statiPreCongString,congelato?toResendSez.statiCongString:null);
			context.getRequest().setAttribute("PianoAttivitaNewDPat", toResendPA);
			context.getRequest().setAttribute("SezioneNewDPat", toResendSez);
			
			if(congelato)
			{
				context.getRequest().setAttribute("ListaSezioniNewDPat", new DpatWrapperSezioniBean());
			}
			else
			{
				context.getRequest().setAttribute("ListaSezioniNewDPat", new DpatWrapperSezioniBeanPreCong());
			}
			
			db.commit();
			 
			

		} catch (Exception e) {
			
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			try{
				db.rollback();
				System.out.println("Rollback eseguito");
			} catch(Exception ex) {ex.printStackTrace(); }
			return ("SystemError");
		} finally {
			try{db.setAutoCommit(autocommit); } catch(Exception ex){}
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("inserito", "true");

		return ("UpdateAttivitaOK");
	
		
	}
	
	
	
	
	
	public String executeCommandModificaIndicatoreEffettivo(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		PreparedStatement pst = null;
		boolean autocommit = false;
		boolean readonly = false;
		
		try {
			
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			autocommit = db.getAutoCommit();
			db.setAutoCommit(false);
			int operazione = Integer.parseInt(context.getParameter("operazione"));
			int id = Integer.parseInt(context.getParameter("id"));
			String descrizione;
			if(context.getRequest().getParameter("descrizione") != null){
				descrizione = context.getRequest().getParameter("descrizione").trim();
			} else {
				descrizione = context.getRequest().getParameter("descrizione");
			}
			if(descrizione!=null)
			{
				descrizione = descrizione.replace("\r\n", " ");
				descrizione = descrizione.replace("\n", " ");
			}
			
			String codice_esame = context.getRequest().getParameter("codice_esame");
			String alias = context.getRequest().getParameter("alias");
			String codiceAliasIndicatore = context.getParameter("cup");
			String tipoItemDpat = context.getParameter("tipoItemDpat");
			String rendicPerCampione = context.getParameter("rendicPerCampione");
			
			System.out.println("### DPAT MODIFICA INDICATORE ### INPUT: descrizione = " + descrizione + ", codice_esame = " + codice_esame + ", alias = " + alias  );
			
			
			Integer anno = Integer.parseInt(context.getRequest().getParameter("anno"));
			context.getRequest().setAttribute("anno", anno+"");
			
			HttpServletRequest request = context.getRequest();
			
			boolean congelato = false;
			if(request.getAttribute("congelato") != null)
			{
				congelato = Boolean.parseBoolean((String)request.getAttribute("congelato"));
			}
			else if(request.getParameter("congelato") != null)
			{
				congelato = Boolean.parseBoolean(request.getParameter("congelato"));
				
			}
			request.setAttribute("congelato", congelato+"");
			
			boolean mantieniAssociazioni = false;
			if(request.getAttribute("mantieniAssociazioni") != null)
			{
				mantieniAssociazioni = Boolean.parseBoolean((String)request.getAttribute("mantieniAssociazioni"));
			}
			else if(request.getParameter("mantieniAssociazioni") != null)
			{
				mantieniAssociazioni = Boolean.parseBoolean(request.getParameter("mantieniAssociazioni")); 
				
			}
			request.setAttribute("mantieniAssociazioni", mantieniAssociazioni+"");
			
			Indicatore indicatoreDaModificare = null,  indicatoreNuovoFratello = null, lastChild = null, riferimentoPerInserimento = null;
			Indicatore toInsert = null;
			Indicatore tool = null;
			SezioneInterface toolSez = null; 
			
			PianoAttivitaInterface beanNuovoPadre = null,toolPA = null; 
			Integer oidNuovoIndicatore = null;
			
			if(congelato)
			{
				tool = new Indicatore();
				toInsert = new Indicatore();
				toolPA = new PianoAttivita();
				toolSez = new Sezione(); 
				
			}
			else
			{
				tool = new Indicatore();
				toInsert = new Indicatore();
				toolPA = new PianoAttivita();
				toolSez = new Sezione(); 
				
			}
			
			switch(operazione)
			{
				
				case 1 : 
				{
					/*modifica di indicatore con mantenimento dei vecchi controlli sui vecchi motivi (quindi con dup)*/
					
					
					
					

					/*l'inserimento con duplicazione e' visto come :
					 * creo un nuovo bean, e lo inserisco dopo quello che sto modificando
					 * quindi prendo i campi che arrivano dalle maschere e li metto nel bean da inserire (l'ho gia' fatto prima di chiamare questo metodo
					 * ) e prendo i campi che non arrivano da maschere, che devono quindi provenire dal bean che sto duplicando (che e'  indicatore di riferimento)
					 * e li scarico in quello da inserire
					 */
					indicatoreDaModificare = tool.buildByOid(db,id,false);
					
					toInsert.setDescrizione(descrizione);
					toInsert.setCodiceEsame(codice_esame);
					toInsert.setAliasIndicatore(alias);
					toInsert.setCodiceAliasIndicatore(codiceAliasIndicatore);
					toInsert.setCodiceRaggruppamento(indicatoreDaModificare.getCodiceRaggruppamento()); /*questo fa si che nell'insertBeforeOrAfter venga mantenuto */
					toInsert.setOidPianoAttivita(indicatoreDaModificare.getOidPianoAttivita());
					toInsert.setAnno(indicatoreDaModificare.getAnno());
					toInsert.setOrdine(indicatoreDaModificare.getOrdine()+1);
					toInsert.setTipoAttivita(indicatoreDaModificare.getTipoAttivita());
					toInsert.setCodiceInternoIndicatore(indicatoreDaModificare.getCodiceInternoIndicatore());
					toInsert.setCodiceInternoPianiGestioneCu(indicatoreDaModificare.getCodiceInternoPianiGestioneCu());
					toInsert.setCodiceInternoAttivitaGestioneCu(indicatoreDaModificare.getCodiceInternoAttivitaGestioneCu());
					toInsert.setCodiceInternoUnivocoTipoAttivitaGestioneCu(indicatoreDaModificare.getCodiceInternoUnivocoTipoAttivitaGestioneCu());
					toInsert.setTipoItemDpat(Integer.parseInt(tipoItemDpat));
					toInsert.setRendic_per_campioni(Boolean.parseBoolean(rendicPerCampione));
						
					/*SPOSTO COMPETENZE */
					//DA VALUTARE COSA PASSARE - IL METODO SI DEVE RISCRIVERE UNENDO LE LOGICHE DI CONG E PRECONG
					 oidNuovoIndicatore   = tool.update(toInsert,indicatoreDaModificare,db,null,null,null,null,null,congelato,user.getUserId());
					Integer competenzeSpostate = tool.spostaCompetenzeFromTo(indicatoreDaModificare.getOid(), oidNuovoIndicatore.longValue(),db);
					System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATE SPOSTATE "+competenzeSpostate+" COMPETENZE (MODELLO 5)");
					
					//Con refactoring del DPAT 2019 e' stato deciso che l'aggiornamento del codice alias per le precedenti versioni non va fatto
					//int aggiornati = tool.aggiornaCodiceAliasIndicatorePerTutteVersioni(db, toInsert,user.getUserId());
					//System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATI AGGIORNATI "+aggiornati+" CODICI ALIAS INDICATORI PER LO STESSO CODICE RAGGRUPPAMENTO)");
					
						int associazioniSpostate = tool.spostaAssociazioniFromTo(indicatoreDaModificare.getOid(), oidNuovoIndicatore.longValue(), mantieniAssociazioni, getUserId(context), db);
						System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATE SPOSTATE "+associazioniSpostate+" ASSOCIAZIONI MATRICI-ANALITI");
					
					break;
				}
				case 2 : 
				{
					/*AGGIORNAMENTO : CASO ELIMINATO
					 * 
					 * modifica di indicatore senza mantenimento dei vecchi controlli sui vecchi motivi (quindi senza dup)*/
					/*
					 * 
					Indicatore indicatoreDaModificare = Indicatore.buildByOid(db,id,false);;
					indicatoreDaModificare.updateSenzaDup(descrizione, codiceAlias,alias,codice_esame,db);
					break;
					*/
					
					throw new Exception("OPERAZIONE NON PIU' PREVISTA");
					
					 
				}
				case 3 : 
				{
					/*spostamento indicatore */
					/*se come posizione indico 1 (prima di) allora devo fare inserimento come fratello dell'indicatore con oid in indicatoreRiferimento, prima */
					/*altrimenti inserisco come ultimo figlio del piano/attivita */
					Integer oidPianoAttivitaNuovoPadre = Integer.parseInt(context.getRequest().getParameter("piano"));
					Integer posizione = Integer.parseInt(context.getRequest().getParameter("posizione"));
					indicatoreDaModificare = tool.buildByOid(db, id,false);
					
					//il cup puo' essere modificato perche' gia' esistente
					indicatoreDaModificare.setCodiceAliasIndicatore(codiceAliasIndicatore);	
					indicatoreDaModificare.setRendic_per_campioni(Boolean.parseBoolean(rendicPerCampione));
					indicatoreDaModificare.setTipoItemDpat(Integer.parseInt(tipoItemDpat));
					
					
					
										
					if(context.getRequest().getParameter("indicatoreRiferimento") != null && context.getRequest().getParameter("indicatoreRiferimento").trim().length() > 0)
					{
						Integer oidIndicatoreFratello = Integer.parseInt(context.getRequest().getParameter("indicatoreRiferimento"));
						
//						int idPadreVecchio = indicatoreDaSpostare.getOidPianoAttivita().intValue();
						org.aspcfs.modules.dpat2019.base.Dpat dpat = new org.aspcfs.modules.dpat2019.base.Dpat();
						
						if(posizione == 1) /* inserimento di indicatore prima (up) di indicatore scelto */
						{
							indicatoreNuovoFratello = tool.buildByOid(db,oidIndicatoreFratello,false);
							
							ArrayList<String> codiciInterniReadonly = dpat.getCodiciInterniReadonly(db,"I");
							if(((indicatoreNuovoFratello.getCodiceInternoPianiGestioneCu()!=null && codiciInterniReadonly.contains(indicatoreNuovoFratello.getCodiceInternoPianiGestioneCu().toString())) || (indicatoreNuovoFratello.getCodiceInternoAttivitaGestioneCu()!=null && codiciInterniReadonly.contains(indicatoreNuovoFratello.getCodiceInternoAttivitaGestioneCu().toLowerCase()))) && user.getRoleId()!=32 && user.getRoleId()!=1)
							{
								readonly  =true;
								System.out.println(">>>>>>DPAT NUOVA GESTIONE: NON SI PUO FARE LO SPOSTAMENTO PERCHE L'INDICATORE SELEZIONATO E' READONLY");
							}
							else
							{
								/*disabilito quello che ho spostato, cioe' la vecchia copia */ 
								tool.disabilitaByOid(db, indicatoreDaModificare.getOid().intValue(), indicatoreDaModificare.getAnno(),tool.statiCongString,tool.statiCongString,tool.statiCongString,user.getUserId());

									
								//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
								oidNuovoIndicatore = tool.insertBeforeOrAfter(db, indicatoreDaModificare, "up",null,null, indicatoreNuovoFratello.getOidPianoAttivita().intValue(), indicatoreNuovoFratello.getAnno(),indicatoreNuovoFratello.getOrdine(),user.getUserId());
								//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
								tool.deleteDummyBrother(db,indicatoreNuovoFratello,null);
							}
							
							
						}
						else /*inserimento alla fine del piano attivita, cioe' come ultimo figlio del piano attivita */
						{
							/*cerco ultimo figlio (attivo) del piano attivita specificato */
							 lastChild = tool.searchLastChildOf(db,oidPianoAttivitaNuovoPadre,false);
							 
							 ArrayList<String> codiciInterniReadonly = dpat.getCodiciInterniReadonly(db,"I");
						     if(((lastChild.getCodiceInternoPianiGestioneCu()!=null && codiciInterniReadonly.contains(lastChild.getCodiceInternoPianiGestioneCu().toString())) || (lastChild.getCodiceInternoAttivitaGestioneCu()!=null && codiciInterniReadonly.contains(lastChild.getCodiceInternoAttivitaGestioneCu().toLowerCase()))) && user.getRoleId()!=32 && user.getRoleId()!=1)
						     {
						    	 readonly  = true;
								 System.out.println(">>>>>>DPAT NUOVA GESTIONE: NON SI PUO FARE LO SPOSTAMENTO PERCHE L'INDICATORE SELEZIONATO E' READONLY");
						     }
						     else
							 {
						    	/*disabilito quello che ho spostato, cioe' la vecchia copia */ 
								tool.disabilitaByOid(db, indicatoreDaModificare.getOid().intValue(), indicatoreDaModificare.getAnno(),tool.statiCongString,tool.statiCongString,tool.statiCongString,user.getUserId());

								//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
								oidNuovoIndicatore = tool.insertBeforeOrAfter(db, indicatoreDaModificare, "down",null,null, lastChild.getOidPianoAttivita().intValue(), lastChild.getAnno(),lastChild.getOrdine(),user.getUserId());
								//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
								tool.deleteDummyBrother(db,lastChild,null);
							 }
							
						}
					}
					else /*allora stiamo facendo spostamento verso un nuovo piano attivita che non ha figli attivi o non ha proprio figli */
					{
						 beanNuovoPadre = toolPA.buildByOid(db, oidPianoAttivitaNuovoPadre, false,true,anno);
						/*controllo se ha almeno un figlio disattivato (i figli se esistono sono sicuramente disattivati per dove siamo arrivati col flusso  */
						ArrayList figliScaduti = beanNuovoPadre.getIndicatoriFigli();
						riferimentoPerInserimento = null;
						if(figliScaduti != null && figliScaduti.size() > 0)
						{
							riferimentoPerInserimento = (Indicatore) figliScaduti.get(figliScaduti.size() -1);
							System.out.println(">>>>>>DPAT NUOVA GESTIONE: IL PIANO ATTIVITA SCELTO VERSO CUI SPOSTARE("+oidPianoAttivitaNuovoPadre+") HA ALMENO UN FIGLIO SCADUTO ("+riferimentoPerInserimento.getOid()+") USO QUESTO COME RIFERIMENTO SPOSTAMENTO INDICATORE");
						}
						else
						{
							//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
							int oidDummy = tool.insertDummyChildPerPianoAttivitaScelto(db, anno, new Long((long)oidPianoAttivitaNuovoPadre),null,user.getUserId());
							riferimentoPerInserimento = tool.buildByOid(db, oidDummy, true);
							System.out.println(">>>>>>DPAT NUOVA GESTIONE: IL PIANO ATTIVITA SCELTO VERSO CUI SPOSTARE("+oidPianoAttivitaNuovoPadre+" NON HA  FIGLI, NE' ATTIVI NE' SCADUTI, QUINDI USO INSERISCO E USO DUMMY CHILD COME RIFERIMENTO PER SPOSTAMENTO ");
						}
						
						//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
						oidNuovoIndicatore = tool.insertBeforeOrAfter(db, indicatoreDaModificare, "down",null,null, riferimentoPerInserimento.getOidPianoAttivita().intValue(), riferimentoPerInserimento.getAnno(),riferimentoPerInserimento.getOrdine(),user.getUserId());
						/*nell'eventualita' che abbia dovuto inserire un dummy child */
						//DA VALUTARE COSA PASSARE - IL METODO DEVE ESSERE RISCRITTO UNENDO LE LOGICHE CONG E PRECONG
						tool.deleteDummyBrother(db, riferimentoPerInserimento,null);
						
						
					}
					
					 
					if(!readonly)
					{
					    Integer competenzeSpostate = tool.spostaCompetenzeFromTo(indicatoreDaModificare.getOid(), oidNuovoIndicatore.longValue(),db);
					    System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATE SPOSTATE "+competenzeSpostate+" COMPETENZE (MODELLO 5)");
					}
					
					int associazioniSpostate = tool.spostaAssociazioniFromTo(indicatoreDaModificare.getOid(), oidNuovoIndicatore.longValue(), mantieniAssociazioni, getUserId(context), db);
					System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATE SPOSTATE "+associazioniSpostate+" ASSOCIAZIONI MATRICI-ANALITI");
				
				}
				
				
			}
			
			if(!readonly)
			{
				refreshMotiviCu(db,anno,true);
			
			
				/*rimetto i bean nella request cosi' la pagina di rientro non schiatta */
				Indicatore toResend = tool.buildByOid(db, oidNuovoIndicatore, true);
				PianoAttivitaInterface toResendPA = toolPA.buildByOid(db, toResend.getOidPianoAttivita().intValue() , true,false,anno );
				Sezione toResendSez = new Sezione();
				toResendSez = toResendSez.buildByOid(db, toResendPA.getOidSezione().intValue(), true, false, congelato?toResendSez.statiCongString:null, congelato?toResendSez.statiCongString:toResendSez.statiPreCongString,congelato?toResendSez.statiCongString:null);
				context.getRequest().setAttribute("IndicatoreNewDPat",toResend); 
				context.getRequest().setAttribute("PianoAttivitaNewDPat", toResendPA);
				context.getRequest().setAttribute("SezioneNewDPat", toResendSez);
			}
			else
			{
				Indicatore toResend = tool.buildByOid(db,id,false);;
				PianoAttivitaInterface toResendPA = toolPA.buildByOid(db, toResend.getOidPianoAttivita().intValue() , true,false,anno );
				Sezione toResendSez = new Sezione();
				toResendSez = toResendSez.buildByOid(db, toResendPA.getOidSezione().intValue(), true, false, congelato?toResendSez.statiCongString:null, congelato?toResendSez.statiCongString:toResendSez.statiPreCongString,congelato?toResendSez.statiCongString:null);
				context.getRequest().setAttribute("IndicatoreNewDPat",toResend); 
				context.getRequest().setAttribute("PianoAttivitaNewDPat", toResendPA);
				context.getRequest().setAttribute("SezioneNewDPat", toResendSez);
			}
			if(congelato)
			{
				
				context.getRequest().setAttribute("ListaSezioniNewDPat", new DpatWrapperSezioniBean(anno,db,true,true));
			}
			else
			{
				 
				context.getRequest().setAttribute("ListaSezioniNewDPat", new DpatWrapperSezioniBeanPreCong(anno,db,true,true));
			}
			
			
			db.commit();
			 
			
 

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			try {db.rollback(); 
				System.out.println("ROllback eseguito");
			} catch(Exception ex) {ex.printStackTrace(); }
			return ("SystemError");
		} finally {
			try {db.setAutoCommit(autocommit); } catch(Exception ex){}
			this.freeConnection(context, db);
		}
		
		if(!readonly)
			context.getRequest().setAttribute("inserito", "true");
		else
			context.getRequest().setAttribute("errore", "Lo spostamento verso l'indicatore selezionato non è consentito. Rivolgersi all'Help Desk.");

		return ("UpdateIndicatoreOK");
	}
	
	
	
	

	public String executeCommandUpdateIndicatore(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			int anno = Integer.parseInt(context.getParameter("anno"));

			int id = Integer.parseInt(context.getParameter("id"));

			String descrizione = context.getRequest().getParameter("descrizione");
			String codice_esame = context.getRequest().getParameter("codice_esame");
			int operazione = Integer.parseInt(context.getParameter("operazione"));

			String cessazione = context.getParameter("cessato");

			DpatIndicatore ind = new DpatIndicatore(db, id, anno);
			if (ind.getDataScadenza() == null) {

				String selectStato = "select max(stato) from dpat_indicatore where id_attivita = ? and anno = " + anno;

				PreparedStatement pst = db.prepareStatement(selectStato);
				pst.setInt(1, ind.getId_attivita());
				ResultSet rs = pst.executeQuery();
				int stato = 1;
				if (rs.next())
					stato = rs.getInt(1) % 2;

				ind.disabilitaIndicatore(db, context.getParameter("dataScadenza"), anno);

				if (cessazione != null && cessazione.equalsIgnoreCase("no")) {

					ind.setStato(stato);
					ind.setId_attivita(Integer.parseInt(context.getParameter("root_")));
					ind.setDescription(descrizione);
					ind.setCodiceEsame(codice_esame);
					ind.setAlias(context.getParameter("alias"));
					ind.setTipoAttivita(context.getParameter("tipoAttivita"));
					ind.setUiCalcolabile(false);
					ind.setCodiceAlias(context.getParameter("cup"));

					switch (operazione) {

					case 1: {
						System.out.println("MODIFICA PER SOSTITUZIONE INDICATORE CON NUOVO CODICE");
						ind.insert(db, anno);
						break;
					}

					case 2: {
						System.out.println("MODIFICA PER SOSTITUZIONE INDICATORE CON NUOVO CODICE");
						ind.setCodiceInternoUnivoco(-1);
						ind.insert(db, anno);
						break;
					}
					case 3: {
						String indicatoreRiferimento = context.getParameter("indicatoreRiferimento");
						String posizionamento = context.getParameter("posizione");
						DpatIndicatore indRif = new DpatIndicatore(db, Integer.parseInt(indicatoreRiferimento), anno);
						if (posizionamento.equals("1")) {

							ind.setId_attivita(Integer.parseInt(context.getParameter("root_")));

							indRif.setTipoInserimento("up");
							ind.setTipoAttivita(indRif.getTipoAttivita());
							ind.disabilitaIndicatore(db, context.getParameter("dataScadenza"), anno);
							ind.setOrdinamento(indRif.getOrdinamento());
							ind.insert(db, indRif, anno);

						} else {
							indRif.setTipoInserimento("down");
							ind.setTipoAttivita(indRif.getTipoAttivita());
							ind.disabilitaIndicatore(db, context.getParameter("dataScadenza"), anno);
							ind.setOrdinamento(indRif.getOrdinamento());
							ind.insert(db, indRif, anno);
						}

						System.out.println("MODIFICA PER SPOSTAMENTO INDICATORE");
						break;
					}
					}

				}
			} else {

				if (cessazione != null && cessazione.equalsIgnoreCase("no")) {
					ind.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), false);

					ind.setDescription(descrizione);
					ind.setCodiceEsame(codice_esame);
					ind.setAlias(context.getParameter("alias"));
					ind.setTipoAttivita(context.getParameter("tipoAttivita"));

					ind.setUiCalcolabile(false);
					if (context.getParameter("uiCalcolabile") != null
							&& "si".equalsIgnoreCase(context.getParameter("uiCalcolabile")))
						ind.setUiCalcolabile(true);

					ind.updateRecordDaAttivate(db, anno); // OK

				} else {
					ind.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), true);
				}

			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("inserito", "true");

		return ("AddOK");
	}

	public String executeCommandUpdateAttivita(ActionContext context) throws ParseException {
		if (!hasPermission(context, "piani-piani-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			int anno = Integer.parseInt(context.getParameter("anno"));

			int id = Integer.parseInt(context.getParameter("id"));

			String descrizione = context.getRequest().getParameter("descrizione");
			String codice_esame = context.getRequest().getParameter("codice_esame");
			int operazione = Integer.parseInt(context.getParameter("operazione"));

			String descrizionePiano = "";
			if (context.getParameter("tipoAttivita").contains("ATTIVITA"))
				descrizionePiano = "ATTIVITA";
			else
				descrizionePiano = "PIANO";

			String cessazione = context.getParameter("cessato");

			DpatAttivita dpatAtt = new DpatAttivita(db, id, anno);
			if (dpatAtt.getDataScadenza() == null) {

				DpatPiano piano = new DpatPiano(db, dpatAtt.getId_piano(), anno);
				piano.setEnabled(true);

				String selectStato = "select max(stato) from dpat_attivita where id_piano = ? and anno = " + anno;

				PreparedStatement pst = db.prepareStatement(selectStato);
				pst.setInt(1, dpatAtt.getId_piano());
				ResultSet rs = pst.executeQuery();
				int stato = 1;
				if (rs.next())
					stato = rs.getInt(1) % 2;

				// dpatAtt.disabilitaAttivita(db,
				// context.getParameter("dataScadenza"),anno);

				if (cessazione != null && cessazione.equalsIgnoreCase("no")) {

					switch (operazione) {
					case 1: {
						// dpatAtt.disabilitaAttivita(db,
						// context.getParameter("dataScadenza"),anno);

						String insert_log = "insert into dpat_log_modifica_piano (data_operazione, user_id, id_piano, description_piano, alias_piano, id_att, description_att, codice_esame_att, alias_att, codice_alias_att)"
								+ " values (now(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement pst0 = db.prepareStatement(insert_log);
						pst0.setInt(1, user.getIdApiario());
						pst0.setInt(2, piano.getId());
						pst0.setString(3, piano.getDescription());
						pst0.setString(4, piano.getAlias());
						pst0.setInt(5, dpatAtt.getId());
						pst0.setString(6, dpatAtt.getDescription());
						pst0.setString(7, dpatAtt.getCodiceEsame());
						pst0.setString(8, dpatAtt.getAlias());
						pst0.setString(9, dpatAtt.getCodiceAlias());

						piano.setStato(2);
						// piano.setId_sezione(Integer.parseInt(context.getParameter("root_")));
						piano.setDescription(descrizionePiano + " " + context.getParameter("alias"));
						piano.setAlias(context.getParameter("alias"));
						// piano.setTipoAttivita(context.getParameter("tipoAttivita"));
						piano.update(db, anno, dpatAtt.getId_piano());

						dpatAtt.setStato(2);
						// dpatAtt.setId_piano(piano.getId());
						// dpatAtt.setCodicePiano(piano.getCodiceInterno());
						dpatAtt.setDescription(descrizione);
						dpatAtt.setCodiceEsame(codice_esame);
						dpatAtt.setAlias(context.getParameter("alias"));
						dpatAtt.setCodiceAlias(context.getParameter("cup"));
						// dpatAtt.setTipoAttivita(context.getParameter("tipoAttivita"));
						// dpatAtt.insert(db,anno);
						dpatAtt.update(db, dpatAtt.getId(), anno); // where id

						pst0.execute();
						pst0.close();

						break;
					}

					case 3: {
						dpatAtt.disabilitaAttivita(db, context.getParameter("dataScadenza"), anno);

						int idPianoRiferimento = Integer.parseInt(context.getParameter("root"));
						DpatAttivita attivitaRiferimento = new DpatAttivita(db, idPianoRiferimento, anno);
						DpatPiano pianoRiferimento = new DpatPiano(db, attivitaRiferimento.getId_piano(), anno);
						String posizionamento = context.getParameter("posizione");
						switch (posizionamento) {
						case "1": {
							attivitaRiferimento.setTipoInserimento("up");
							break;
						}
						case "2": {
							attivitaRiferimento.setTipoInserimento("down");
							break;
						}
						}

						piano.setStato(stato);
						piano.setId_sezione(Integer.parseInt(context.getParameter("sezione")));
						piano.setTipoAttivita(context.getParameter("tipoAttivita"));
						piano.setOrdinamento(pianoRiferimento.getOrdinamento());
						piano.insert(db, attivitaRiferimento, anno);

						dpatAtt.setCodiceAlias(context.getParameter("cup"));
						dpatAtt.setStato(stato);
						dpatAtt.setId_piano(piano.getId());
						dpatAtt.setOrdinamento(attivitaRiferimento.getOrdinamento());
						dpatAtt.setCodicePiano(piano.getCodiceInterno());
						dpatAtt.insert(db, attivitaRiferimento, anno);

						break;
					}
					default:
						dpatAtt.disabilitaAttivita(db, context.getParameter("dataScadenza"), anno);
					}

				}
			} else {

				dpatAtt.disabilitaAttivita(db, context.getParameter("dataScadenza"), anno);

				String selectStato = "select max(stato) from dpat_attivita where id_piano = ? and anno = " + anno;
				int stato = 1;
				PreparedStatement pst = db.prepareStatement(selectStato);
				pst.setInt(1, dpatAtt.getId_piano());
				ResultSet rs = pst.executeQuery();

				if (rs.next())
					stato = rs.getInt(1) % 2;

				if (cessazione != null && cessazione.equalsIgnoreCase("no")) {
					dpatAtt.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), false, anno);

					dpatAtt.setDescription(descrizione);
					dpatAtt.setCodiceEsame(codice_esame);
					dpatAtt.setAlias(context.getParameter("alias"));

					dpatAtt.setTipoAttivita(context.getParameter("tipoAttivita"));
					dpatAtt.setStato(stato);

					dpatAtt.updateRecordDaAttivate(db, anno);
				} else {
					dpatAtt.updateDataScadenzaRecordOld(db, context.getParameter("dataScadenza"), true, anno); // setto
																												// la
																												// data
																												// scadenza
																												// anche
																												// sul
																												// record
																												// che
																												// sidovraattualizzare
				}

			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("inserito", "true");

		return ("AddOK");
	}

	public String executeCommandEliminaDpat(ActionContext context) throws ParseException {

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			DpatIstanza ist = new DpatIstanza(db, year);
			ist.setValoreFlagPianiImportati(db, year);
			ist.setValoreFlagAttivitaImportate(db, year);
			if (ist.getStato().equalsIgnoreCase("temporaneo"))
				ist.deleteDpat(db, year);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandSearchPianiMonitoraggio(context);
	}

	public String executeCommandImportListaAttivitaDpat(ActionContext context) throws ParseException {

		Connection db = null;

		boolean autocommit = false;
		try {

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			autocommit = db.getAutoCommit();
			if (autocommit == true)
				db.setAutoCommit(false);

			int year = Calendar.getInstance().get(Calendar.YEAR);
			DpatIstanza ist = new DpatIstanza(db, year);
			ist.setFlagAttivitaImportate(db, year);
			ist.setValoreFlagAttivitaImportate(db, year);
			ist.setValoreFlagPianiImportati(db, year);
			String sql = "update lookup_tipo_ispezione set enabled = false ,anno = ? where enabled ;";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, (year - 1));
			pst.execute();

			sql = "insert into lookup_tipo_ispezione"
					+ "(description ,enabled,codice_configuratore,level,codice_interno )" +

					"( " +

					"SELECT  "
					+ "p.alias ||': '||substring(p.description from 0 for 290)  || case when f.alias is not null then '-->'||f.alias else '' end as tipo_ispezione , "
					+ "true,f.code,f.id_sezione,f.codice_interno " + "FROM lookup_piano_monitoraggio_configuratore p "
					+ "join lookup_piano_monitoraggio_configuratore f on p.code=f.id_padre and f.enabled "
					+ "where f.tipo_item_dpat = 3 and "
					+ "p.tipo_attivita ilike '%ispezione%' and p.enabled  and p.id_padre = -1 "
					+ "order by p.ordinamento,p.ordinamento_figli " + "); ";
			pst = db.prepareStatement(sql);
			pst.execute();

			db.commit();

			if (autocommit == false)
				try {

					db.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

		} catch (Exception e) {

			if (autocommit == false)
				try {
					db.rollback();
					db.setAutoCommit(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandSearchPianiMonitoraggio(context);
	}

public String executeCommandDpatGeneraXlsModifyGeneraleCompetenze(ActionContext context) throws IOException
{
	
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet();
	HSSFRow row = sheet.createRow(0);
	
	HSSFCellStyle styleIntest = wb.createCellStyle();
	styleIntest.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
	styleIntest.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	
	HSSFCell cell = row.createCell(0);
	cell.setCellValue("SEZIONE");
	cell.setCellStyle(styleIntest);
	
	HSSFCell cel1 = row.createCell(1);
	cel1.setCellValue("PIANO");
	cel1.setCellStyle(styleIntest);
//	HSSFCell cel2 = row.createCell(2);
//	cel2.setCellValue("ATTIVITA");
	
	HSSFCell cel2 = row.createCell(2);
	cel2.setCellValue("ALIAS");
	cel2.setCellStyle(styleIntest);
	
	HSSFCell cel3 = row.createCell(3);
	cel3.setCellValue("INDICATORE");
	cel3.setCellStyle(styleIntest);
	
	HSSFCell cel4 = row.createCell(4);
	cel4.setCellValue("STRUTTURA");
	cel4.setCellStyle(styleIntest);
	
	
	HSSFCell cel5 = row.createCell(5);
	cel5.setCellValue("ATTRIBUZIONE COMPETENZE");
	cel5.setCellStyle(styleIntest);
	
	int s =1 ;
	
	
	Connection db = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String sql = "";
	String idAsl = (String) context.getRequest().getParameter("idAsl");
	String anno = (String) context.getRequest().getParameter("anno");

	if (anno.equals("corrente")) {
		int a = Calendar.getInstance().get(Calendar.YEAR);
		anno = String.valueOf(a);
	}
	context.getRequest().setAttribute("anno", anno);


	String comboArea = context.getRequest().getParameter("combo_area");
	int idAreaSelezionata = -1 ;
	if (comboArea!=null & !"".equals(comboArea))
		idAreaSelezionata = Integer.parseInt(comboArea);

	// DPAT PER L'ASL SELEZIONATA
	org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenzeNewBean d = null;
	try {
		db = this.getConnection(context);
		
		LookupList listaAsl = new LookupList(db,"lookup_site_id");
		context.getRequest().setAttribute("ListaAsl", listaAsl);
		
		
		ArrayList <Integer> listaStruttureAsl = new ArrayList<Integer>();
		if (idAreaSelezionata==-999){
			PreparedStatement pstStrutture = db.prepareStatement("select id from dpat_strutture_asl where id_asl = ? and livello = 2 and enabled and anno = ?");
			pstStrutture.setInt(1, Integer.parseInt(idAsl));
			pstStrutture.setInt(2, Integer.parseInt(anno));
			ResultSet rsStrutture = pstStrutture.executeQuery();
			while (rsStrutture.next()){
				listaStruttureAsl.add(rsStrutture.getInt(1));
			}
			}
		else
			listaStruttureAsl.add(idAreaSelezionata);


		
		for (int b = 0; b<listaStruttureAsl.size(); b++){
			
			int idAreaDaEstrarre = listaStruttureAsl.get(b);
		
		sql = "select id,completo,id_asl,anno from dpat where id_asl=" + idAsl + " and anno="
				+ anno+" and enabled";
		pst = db.prepareStatement(sql);
		rs = pst.executeQuery();
		while (rs.next()) {
			d = new org.aspcfs.modules.dpat2019.base.DpatAttribuzioneCompetenzeNewBean();
			d.setIdArea(idAreaDaEstrarre);
			d.setId(rs.getInt("id"));
			d.setCompleto(rs.getBoolean("completo"));
			d.setIdAsl(rs.getInt("id_asl"));
			d.setAnno(rs.getInt("anno"));
			d.buildRecord(d.getId(), db,this.getSystemStatus(context),idAreaDaEstrarre);
			d.buildlistSezioni(db, Integer.parseInt(anno),true);
		} 


		//DpatIstanza istanza = new DpatIstanza();
//		int statoDpatConfig = istanza.getStatoFromAnno(db, Integer.parseInt(anno));
//		DpatWrapperSezioniNewBeanAbstract sezioni = new DpatWrapperSezioniNewBean;
		if (d != null) {

			// PER OGNI SEZIONE COSTRUISCI LA LISTA DEI PIANI
//			for (int i = 0; i < d.getElencoSezioni().size(); i++) {
//				d.getElencoSezioni().get(i).buildlistPiani(db,d.getElencoSezioni().get(i).getId(),statoDpatConfig,Integer.parseInt(anno));
//				// PER OGNI PIANO COSTRUISCI LA LISTA DELLE ATTIVITA'
//				for (int j = 0; j < d.getElencoSezioni().get(i).getElencoPiani().size(); j++) {
//					d.getElencoSezioni().get(i).getElencoPiani().get(j).buildlistAttivita(db,d.getElencoSezioni().get(i).getElencoPiani().get(j).getId(),statoDpatConfig,Integer.parseInt(anno));
//
//					// PER OGNI ATTIVITA CALCOLA L'UI TOTALE E COSTRUISCI LA LISTA DEGLI INDICATORI
//					for (int k = 0; k < d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().size(); k++) {
//
//						d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k).buildlistIndicatori(	db,d.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k).getId(),statoDpatConfig,Integer.parseInt(anno));
//
//					}
//
//				}
//			}   

			
			
			
			
		
			for(Object  seza : d.getElencoSezioni().getSezioni())
			{
				DpatSezioneNewBean sez = (DpatSezioneNewBean)seza;
				for(Object pa : sez.getPianiAttivitaFigli() )
				{
					DpatPianoAttivitaNewBean p =(DpatPianoAttivitaNewBean)pa;
					
					//for (DpatAttivita att : p.getElencoAttivita())
					//{
						for (Object inda : p.getIndicatoriFigli())
						{
							DpatIndicatoreNewBean ind = (DpatIndicatoreNewBean) inda;
							
							HSSFRow row_ = sheet.createRow(s);
							HSSFCell cell_0 = row_.createCell(0);
							cell_0.setCellValue(sez.getDescrizione());
							HSSFCell cel_1 = row_.createCell(1);
							cel_1.setCellValue(p.getDescrizione());
							
							HSSFCell cel_2 = row_.createCell(2);
							cel_2.setCellValue(ind.getAliasIndicatore());
							HSSFCell cel_3 = row_.createCell(3);
							cel_3.setCellValue(ind.getDescrizione());
//							HSSFCell cel_4 = row_.createCell(4);
//							cel_4.setCellValue(ind.getDescription());
						
							
							for (OiaNodo struttura : d.getElencoStrutture())
							{
								HSSFCell cel_4 = row_.createCell(4);
								cel_4.setCellValue(struttura.getDescrizione_lunga());
								
								
								HSSFCell cel_5 = row_.createCell(5);
								if (struttura.getCompetenzeIndicatori().get(ind.getOid().intValue())!= null && struttura.getCompetenzeIndicatori().get(ind.getOid().intValue())==Boolean.TRUE )
									cel_5.setCellValue("X");
								else
									cel_5.setCellValue("");
							
							}
							
							
							
							s++;
						}
					//}
				}
			}
		}
		}	
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			context.getResponse().setContentType("application/vnd.ms-excel");
			context.getResponse().setContentLength(outArray.length);
			context.getResponse().setHeader("Expires:", "0"); // eliminates browser caching
			
			String nomeStruttura = "";
			
			if (idAreaSelezionata==-999)
				nomeStruttura=listaAsl.getSelectedValue(d.getIdAsl()).replaceAll(" ",  "_");
			else
				nomeStruttura=d.getStrutturaAmbito().getDescrizione_lunga().replaceAll(" ",  "_");
			
			context.getResponse().setHeader("Content-Disposition", "attachment; filename=MODELLO_5_"+anno+"_"+nomeStruttura+".xls");
			
			java.io.OutputStream outStream = context.getResponse().getOutputStream();
			outStream.write(outArray);
			outStream.flush();
		
		context.getRequest().setAttribute("dpat", d);
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		this.freeConnection(context, db);
	}
	return "-none-";

	
}


	public String executeCommandListaAttivitaCodiceInterno_NewDpat(ActionContext context) throws Exception
	{


		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			String anno = context.getParameter("anno");
			boolean congelato = context.getParameter("congelato") != null && Boolean.parseBoolean((String)context.getParameter("congelato"));
			context.getRequest().setAttribute("congelato", context.getParameter("contelato"));

			
			int codRaggruppamento = Integer.parseInt(context.getParameter("codRaggruppamento"));
			
			ArrayList  piani = null;
			//DA VALUTARE COSA PASSARE - IL METODO SI DEVE RISCRIVERE UNENDO LE LOGICHE CONG E PRECONG
			piani = new PianoAttivita().searchVersioniPerCodiceRaggruppamento(db, codRaggruppamento,true,Integer.parseInt(anno),null,null);
			
			if(piani != null && piani.size() > 0)
			{
				int idSez = ((PianoAttivitaInterface)piani.get(0)).getOidSezione().intValue();
				
				Sezione  sez = new Sezione();
				sez = new Sezione().buildByOid(db, idSez, false,true, congelato?sez.statiCongString:null, congelato?sez.statiCongString:sez.statiPreCongString,congelato?sez.statiCongString:null);
				
				context.getRequest().setAttribute("sezione", sez);
			}
			 
			context.getRequest().setAttribute("alPianiAttivita", piani);

		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}

		String toRet = "ListaStoriaAttivitaOK_NEWDPAT";
		return toRet;
	
	}
	
	
	
	public String executeCommandListaIndicatoriCodiceInterno_NewDpat(ActionContext context) throws Exception {

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			String anno = context.getParameter("anno");
			boolean congelato = context.getParameter("congelato") != null && Boolean.parseBoolean((String)context.getParameter("congelato"));
			context.getRequest().setAttribute("congelato", context.getParameter("contelato"));

			
			int codRaggruppamento = Integer.parseInt(context.getParameter("codRaggruppamento"));
			
			ArrayList  indicatori = null;
			if(congelato)
			{
				indicatori = new Indicatore().searchVersioniPerCodiceRaggruppamento(db, codRaggruppamento,Integer.parseInt(anno));
			}
			else
			{
				indicatori = new Indicatore().searchVersioniPerCodiceRaggruppamento(db, codRaggruppamento,Integer.parseInt(anno));
			}
			
			
			context.getRequest().setAttribute("alIndicatori", indicatori);
			
			if(indicatori != null && indicatori.size() > 0)
			{
				int idPianoAttivita = ((Indicatore)indicatori.get(0)).getOidPianoAttivita().intValue();
				PianoAttivitaInterface piano = null;
				piano = new PianoAttivita().buildByOid(db, idPianoAttivita, false,true,Integer.parseInt(anno));
				
				context.getRequest().setAttribute("piano", piano);
				
				if(piano != null)
				{
					int idSez = piano.getOidSezione().intValue();
					Sezione sez = new Sezione();
					sez = sez.buildByOid(db, idSez, false,true, congelato?sez.statiCongString:null, congelato?sez.statiCongString:sez.statiPreCongString,congelato?sez.statiCongString:null);
					
					context.getRequest().setAttribute("sezione",sez);
				}
				
			}

		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}
		String toReturn = "ListaStoriaIndicatoriOK_NEWDPAT";

		return toReturn;
	}
	
	
	

	public String executeCommandListaAttivitaCodiceInterno(ActionContext context) throws ParseException {

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			String anno = context.getParameter("anno");

			int id = Integer.parseInt(context.getParameter("idAttivita"));
			DpatAttivita ind = new DpatAttivita(db, id, Integer.parseInt(anno));
			context.getRequest().setAttribute("DettaglioAttivita", ind);

		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}

		return "ListaStoriaAttivitaOK";
	}

	public String executeCommandListaIndicatoriCodiceInterno(ActionContext context) throws ParseException {

		Connection db = null;
		try {
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			String anno = context.getParameter("anno");

			int id = Integer.parseInt(context.getParameter("idIndicatore"));
			DpatIndicatore ind = new DpatIndicatore(db, id, Integer.parseInt(anno));
			context.getRequest().setAttribute("DettaglioIndicatore", ind);

		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}

		return "ListaStoriaIndicatoriOK";
	}
	
	
	public static void setNullableField(PreparedStatement pst,Object value, int index,int sqltype) throws Exception
	{
		if(value == null)
		{
			pst.setNull(index, sqltype);
		}
		else
		{
			switch(sqltype)
			{
			case  java.sql.Types.VARCHAR:
				pst.setString(index, (String)value );
				break;
				
			case  java.sql.Types.INTEGER:
				pst.setInt(index, (Integer)value );
				break;
			
			case  java.sql.Types.BOOLEAN:
				pst.setBoolean(index, (Boolean)value );
				break;
				
			case  java.sql.Types.DOUBLE:
				pst.setDouble(index, (Double)value );
				break;
			default:
				throw new Exception("tipo non supportato in setNullableFields");
			}
		}
		
	}
	
	 
	public static void main(String args[]) throws IOException
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		for(int k = 0; k< n; k++)
		{
			int g = Integer.parseInt(br.readLine());
			String[] tt = br.readLine().split(" ");
			int a[] = new int[g];
			for(int p = 0; p<g;p++)
			{
				 a[p]= Integer.parseInt(tt[p]);
			}
            getBribes(a);
		}
		
	}
	
	public static void getBribes(int t[])
	{
		 
		int[] cumdiff = new int[t.length];
		int bribes = 0;
		
		for(int i = 0; i< t.length; i++)
		{
			int diff = t[i]-(i+1 -cumdiff[i]);
			if(diff <= 0)
				continue;
			if(diff > 2)
			{
				System.out.println("Too chaotic");
				return;
			}
			
			for(int j = 1; j<= diff; j++)
			{
				if(t[i+j] < t[i])
					cumdiff[i+j]++;
			}
			bribes += diff;
		}
		
		System.out.println(bribes);
	}
	
	
	
}
