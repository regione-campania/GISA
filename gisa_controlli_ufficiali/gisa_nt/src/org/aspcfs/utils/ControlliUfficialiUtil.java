/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcf.modules.controlliufficiali.base.MotivoIspezione;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.acquedirete.actions.AcqueReteVigilanza;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.InformazioniStabilimento;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.RuoliUtentiParser;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class ControlliUfficialiUtil {

	public static final int TIPOLOGIA_IMPRESE					=	1;
	public static final int TIPOLOGIA_OPU					=	999;
	public static final int TIPOLOGIA_API					=	1000;
	public static final int TIPOLOGIA_RICHIESTE					=	1001;
	public static final int TIPOLOGIA_SINTESIS					=	2000;
	public static final int TIPOLOGIA_ANAGRAFICA					=	3000;

	public static final int TIPOLOGIA_STABILIMENTI				=	3;
	public static final int TIPOLOGIA_ALLEVAMENTI				=	2;
	public static final int TIPOLOGIA_ABUSIVI					=	4;
	public static final int TIPOLOGIA_CANILI					=	10;
	public static final int TIPOLOGIA_COLONIE				=	16;

	public static final int TIPOLOGIA_OPERATORI_COMMERCIALI		=	20;
	public static final int TIPOLOGIA_ZONECONTROLLO		=	15;

	public static final int TIPOLOGIA_PRIVATI					=	13;
	public static final int TIPOLOGIA_SOA1774					=	97;
	public static final int TIPOLOGIA_IMPRESEFUORIREGIONE		=	22;
	public static final int TIPOLOGIA_OSM_RICONOSCITI			=	800;
	public static final int TIPOLOGIA_OSM_REGISTRATI			=	801;
	public static final int TIPOLOGIA_FARMACIE					=	151;
	public static final int TIPOLOGIA_MOLLUSCHI					=	201;
	public static final int TIPOLOGIA_CANIPADRONALI				=	255;
	public static final int TIPOLOGIA_PUNTI_DI_SBARCO			=   5;
	public static final int TIPOLOGIA_AZIENDE_AGRICOLE			=   7;
	public static final int TIPOLOGIA_RIPRODUZIONE_ANIMALE		=   8;
	public static final int TIPOLOGIA_TRASPORTO_ANIMALI			=   9;
	public static final int TIPOLOGIA_TIPO_ACQUE				=   14;
	public static final int TIPOLOGIA_LABORATORIHACCP			=   152;
	public static final int TIPOLOGIA_OSA						=   850;
	public static final int TIPOLOGIA_PARAFARMACIE				=   802;
	public static final int TIPOLOGIA_ORGANIZZAZZIONE_INTERNA_ASL	=   6;
	public static final int TIPOLOGIA_OPERATORI_NON_ALTROVE			=   12;
	public static final int TIPOLOGIA_IMBARCAZIONI					=   17;

	public static int TIPOLOGIA = -1;
	public static int TIPO_TECNICA = -1;

	
	public static ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> getMotiviIspezione(Connection db) throws SQLException
	{
		/* String selezionaMotiviIspezione = "select * from (select distinct on (a.codice_interno_ind) a.codice_interno_ind as cod_interno_ind ,a.* from (select * from controlli_ufficiali_motivi_ispezione where "
				+ "data_scadenza > (now() + '1 day'::interval) OR data_scadenza IS NULL ) a order by cod_interno_ind,data_scadenza )bb " ;
		 */
		Calendar calCorrente = GregorianCalendar.getInstance();
		Date dataCorrente = new Date(System.currentTimeMillis());
		int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
		dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
		calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
		int anno_corrente = calCorrente.get(Calendar.YEAR);
		 
		String tipoAttivitaFiltro = "{}";
		if (TIPO_TECNICA>0){
			if (TIPO_TECNICA==3) {
				tipoAttivitaFiltro = "{ATTIVITA-AUDIT}";
			}
			else if (TIPO_TECNICA==4) {
				tipoAttivitaFiltro = "{PIANO, ATTIVITA-ISPEZIONE}";
			}
			else if (TIPO_TECNICA==5) {
				tipoAttivitaFiltro = "{ATTIVITA-SORVEGLIANZA}";
			}
		}
		 
		String selezionaMotiviIspezione = "select * from get_motivi_cu(?,?,?::text[])";
		PreparedStatement pst1 = db.prepareStatement(selezionaMotiviIspezione, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pst1.setInt(1,  TIPOLOGIA);
		pst1.setInt(2,  anno_corrente);
		pst1.setString(3,  tipoAttivitaFiltro);

		ResultSet rs1 = pst1.executeQuery();
		
		if(!rs1.next())
		{
//			//Aggiorna per effetto del passaggio al nuovo anno
//			pst1 = db.prepareStatement("select * from public.refresh_motivi_cu(?, ?)");
//			pst1.setInt(1,  anno_corrente);
//			pst1.setBoolean(2,  true);
//			pst1.executeQuery();
//			
//			//Vecchio anno
//			pst1.setInt(1,  anno_corrente-1);
//			pst1.executeQuery();
//			
//			//Rifai la query
//			pst1 = db.prepareStatement(selezionaMotiviIspezione);
//			pst1.setInt(1,  TIPOLOGIA);
//			pst1.setInt(2,  anno_corrente);
//			rs1 = pst1.executeQuery();
		}
		else
		{
			rs1.beforeFirst();
		}
		ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = new ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione>();
		while (rs1.next())
		{
			org.aspcfs.modules.vigilanza.base.MotivoIspezione motivo= new org.aspcfs.modules.vigilanza.base.MotivoIspezione();
			motivo.setIdMotivoIspezione(rs1.getInt("id_tipo_ispezione"));
			motivo.setIdPiano(rs1.getInt("id_piano"));
			motivo.setDescrizioneMotivoIspezione(rs1.getString("descrizione_tipo_ispezione"));
			motivo.setDescrizionePiano(rs1.getString("descrizione_piano"));
			motivo.setCodiceInternoMotivoIspezione(rs1.getString("codice_int_tipo_ispe"));
			motivo.setCodiceInternoPiano(rs1.getString("codice_int_piano"));
			motivo.setDescrizioneTecnicaControllo(rs1.getString("tipo_attivita"));
			listaMotiviIspezione.add(motivo); 
		
		}
		
		return listaMotiviIspezione ;
	}
	public static ArrayList<org.aspcfs.modules.vigilanza.base.MotivoAudit> getMotiviAudit(Connection db ,String filtroLivelloDaEscludere) throws SQLException
	{
		String selezionaMotiviAudit = "select * from controlli_ufficiali_motivi_audit " ;
		
		if (!selezionaMotiviAudit.equals(""))
			selezionaMotiviAudit+=" where livello_audit_tipo not in "+filtroLivelloDaEscludere ;
		
		PreparedStatement pst2 = db.prepareStatement(selezionaMotiviAudit);
		ResultSet rs2 = pst2.executeQuery();
		ArrayList<org.aspcfs.modules.vigilanza.base.MotivoAudit> listaMotiviAudit = new ArrayList<org.aspcfs.modules.vigilanza.base.MotivoAudit>();
		while (rs2.next())
		{
			org.aspcfs.modules.vigilanza.base.MotivoAudit motivo= new org.aspcfs.modules.vigilanza.base.MotivoAudit();
			
			motivo.setIdAuditTipo(rs2.getInt("id_audit_tipo"));
			motivo.setDescrizioneAuditTipo(rs2.getString("descrizione_audit_tipo"));
			
			motivo.setIdTipoAudit(rs2.getInt("id_tipo_audit"));
			motivo.setDescrizioneTipoAudit(rs2.getString("descrizione_tipo_audit"));
			
			motivo.setTipo_bpi_haccp(rs2.getString("tipo_bpi_o_haccp"));
			motivo.setIdBpi_o_haccp(rs2.getInt("id_bpi_haccp"));
			motivo.setDescrizioneBpi_o_haccp(rs2.getString("descrizione_bpi_o_haccp"));
			listaMotiviAudit.add(motivo);
			
			
		}
		return listaMotiviAudit;
	}
	
	public static ArrayList<org.aspcfs.modules.vigilanza.base.MotivoAudit> getMotiviAuditDpat(Connection db ) throws SQLException
	{
		String selezionaMotiviAudit = "select * from controlli_ufficiali_motivi_audit_dpat " ;
		
		PreparedStatement pst2 = db.prepareStatement(selezionaMotiviAudit);
		ResultSet rs2 = pst2.executeQuery();
		ArrayList<org.aspcfs.modules.vigilanza.base.MotivoAudit> listaMotiviAudit = new ArrayList<org.aspcfs.modules.vigilanza.base.MotivoAudit>();
		while (rs2.next())
		{
			org.aspcfs.modules.vigilanza.base.MotivoAudit motivo= new org.aspcfs.modules.vigilanza.base.MotivoAudit();
			
			motivo.setIdAuditTipo(rs2.getInt("id_audit_tipo"));
			motivo.setDescrizioneAuditTipo(rs2.getString("descrizione_audit_tipo"));
			
			motivo.setIdTipoAudit(rs2.getInt("id_tipo_audit"));
			motivo.setDescrizioneTipoAudit(rs2.getString("descrizione_tipo_audit"));
			
			motivo.setTipo_bpi_haccp(rs2.getString("tipo_bpi_o_haccp"));
			motivo.setIdBpi_o_haccp(rs2.getInt("id_bpi_haccp"));
			motivo.setDescrizioneBpi_o_haccp(rs2.getString("descrizione_bpi_o_haccp"));
			listaMotiviAudit.add(motivo);
			
			
		}
		return listaMotiviAudit;
	}
	
	public static ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> getMotiviDaCodiceInternoPianoOAttivita(Connection db ,String filtroLivelloDaEscludere,  String codiciInterniPiani, String codiciInterniTipoIspezione) throws SQLException
	{
		String selezionaMotiviIspezione = "select * from (select distinct on (a.codice_interno_ind) a.codice_interno_ind as cod_interno_ind ,a.* from (select * from controlli_ufficiali_motivi_ispezione where "
				+ "data_scadenza > (now() + '1 day'::interval) OR data_scadenza IS NULL ) a order by cod_interno_ind,data_scadenza )bb where 1=1 " ;
		if (!filtroLivelloDaEscludere.equals(""))
			selezionaMotiviIspezione+=" and livello_tipo_ispezione not in "+filtroLivelloDaEscludere ;
		
		String filtroAttivita = null;
		String filtroPiano =null;
		String filtroAttivitaPiano = null;
		
		if (codiciInterniTipoIspezione!=null && !codiciInterniTipoIspezione.equals(""))
			filtroAttivita = "codice_int_tipo_ispe in "+codiciInterniTipoIspezione;
		if (codiciInterniPiani!=null && !codiciInterniPiani.equals(""))
			filtroPiano = "codice_int_piano in "+codiciInterniPiani;
		
		if (filtroAttivita!=null && filtroPiano!=null)
			filtroAttivitaPiano = "and ("+filtroAttivita+ " or "+filtroPiano+")";
		else if (filtroAttivita!=null)
			filtroAttivitaPiano = "and ("+filtroAttivita+")";
		else if (filtroPiano!=null)
			filtroAttivitaPiano = "and ("+filtroPiano+")";

		if (filtroAttivitaPiano !=null)
			selezionaMotiviIspezione+=filtroAttivitaPiano;
		
		selezionaMotiviIspezione+=" order by ordinamento,ordinamento_figli";
		PreparedStatement pst1 = db.prepareStatement(selezionaMotiviIspezione);
		ResultSet rs1 = pst1.executeQuery();
		ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = new ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione>();
		while (rs1.next())
		{
			org.aspcfs.modules.vigilanza.base.MotivoIspezione motivo= new org.aspcfs.modules.vigilanza.base.MotivoIspezione();
			motivo.setIdMotivoIspezione(rs1.getInt("id_tipo_ispezione"));
			motivo.setIdPiano(rs1.getInt("id_piano"));
			motivo.setDescrizioneMotivoIspezione(rs1.getString("descrizione_tipo_ispezione"));
			motivo.setDescrizionePiano(rs1.getString("descrizione_piano"));
			motivo.setCodiceInternoMotivoIspezione(rs1.getString("codice_int_tipo_ispe"));
			motivo.setCodiceInternoPiano(rs1.getString("codice_int_piano"));
			listaMotiviIspezione.add(motivo);
		
		}
		
		return listaMotiviIspezione ;
	}
	
	public  static RispostaDwrCodicePiano getCodiceInternoPianoMonitoraggio(Connection db,String fromTable,int idpiano_attivita)
	{
		RispostaDwrCodicePiano ret = new RispostaDwrCodicePiano();
		String sql = "" ;
		if (fromTable.equalsIgnoreCase("lookup_piano_monitoraggio"))
			sql = "select distinct codice_interno::text,flag_condizionalita from "+fromTable+ " where code = "+idpiano_attivita + " ";
		else
			 sql = "select distinct codice_interno::text,null as flag_condizionalita from "+fromTable+ " where code = "+idpiano_attivita + " ";
		
		System.out.println("codiceInternoQuery:"+sql.toString());
		try { 
			ResultSet rs = db.prepareStatement(sql).executeQuery();
			if (rs.next())
			{
				String codiceInterno =rs.getString(1);
				boolean flagCondizionalita = rs.getBoolean(2);
				
				ret.setCodiceInterno(codiceInterno);
				ret.setFlagCondizionalita(flagCondizionalita+"");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	
	
	
	
	
	
	
	public  static RispostaDwrCodicePiano getCodicePianoMonitoraggioFromCodiceInterno(Connection db,String fromTable,int idpiano_attivita)
	{
		RispostaDwrCodicePiano ret = new RispostaDwrCodicePiano();
		String sql = "" ;
		
			 sql = "select distinct code,null as flag_condizionalita,description from "+fromTable+ " where codice_interno = "+idpiano_attivita + " and enabled";
		
		
		try { 
			ResultSet rs = db.prepareStatement(sql).executeQuery();
			if (rs.next())
			{
				int code =rs.getInt(1);
				boolean flagCondizionalita = rs.getBoolean(2);
				String description = rs.getString("description");
				ret.setCode(code+"");
				ret.setFlagCondizionalita(flagCondizionalita+"");
				ret.setDescrizione(description);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	public static String getCodiciInterniDaAlias(Connection db ,String alias) throws SQLException
	{
		String sql = "select m.codice_int_piano from controlli_ufficiali_motivi_ispezione m left join lookup_piano_monitoraggio p on m.id_piano = p.code left join lookup_tipo_ispezione i on m.id_tipo_ispezione = i.code where (m.data_scadenza > (now() + '1 day'::interval) OR m.data_scadenza IS NULL) and m.codice_int_piano>0  and p.alias ilike ?" ;
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, "%"+alias+"%");
		ResultSet rs = pst.executeQuery();
		ArrayList<String> codici = new ArrayList<String>();
		while (rs.next()){
			String codice = rs.getString(1);
			codici.add(codice);
		}
		String res = String.join(",", codici);
		return res;
	}
	
	
	public static void buildLookupTipoControlloUfficiale(Connection db ,ActionContext context,SystemStatus systemStatus,int idUser ,int siteId, int tipologia,String tipoDest) throws SQLException
	{

		TIPOLOGIA = tipologia; 
		try {TIPO_TECNICA = Integer.parseInt(context.getRequest().getParameter("tipoCampione")); } catch (Exception e) {}
		
		UserBean user = (UserBean )context.getRequest().getSession().getAttribute("User");
		
		buildElementControlliUfficialiComune(db,tipologia, context, tipoDest, systemStatus, idUser, siteId);

		String nameContext=context.getRequest().getServletContext().getServletContextName();

		buildElementControlliUfficialiPianoMonitoraggio(db,tipologia, context,user.getSiteId(),siteId);
		
		
		//AGGIORNAMENTO LOOKUP_TIPO_ISPEZIONE PER PASSAGGIO AL NUOVO ANNO
		Calendar calCorrente = GregorianCalendar.getInstance();
		Date dataCorrente = new Date(System.currentTimeMillis());
		int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
		dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
		calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
		int anno_corrente = calCorrente.get(Calendar.YEAR);
		 
		String tipoAttivitaFiltro = "{}";
		if (TIPO_TECNICA>0){
			if (TIPO_TECNICA==3) {
				tipoAttivitaFiltro = "{ATTIVITA-AUDIT}";
			}
			else if (TIPO_TECNICA==4) {
				tipoAttivitaFiltro = "{PIANO, ATTIVITA-ISPEZIONE}";
			}
			else if (TIPO_TECNICA==5) {
				tipoAttivitaFiltro = "{ATTIVITA-SORVEGLIANZA}";
			}
		}
		 
		String selezionaMotiviIspezione = "select count(*) as conta from get_motivi_cu(?,?, ?::text[]) ";
		PreparedStatement pst1 = db.prepareStatement(selezionaMotiviIspezione, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		pst1.setInt(1,  TIPOLOGIA);
		pst1.setInt(2,  anno_corrente);
		pst1.setString(3, tipoAttivitaFiltro);
		ResultSet rs1 = pst1.executeQuery();
		
		//Se si devono aggiornare i motivi
		rs1.next();
//		if(rs1.getInt("conta")==0)
//		{
//			//Aggiorna per effetto del passaggio al nuovo anno
//			pst1 = db.prepareStatement("select * from public.refresh_motivi_cu(?, ?)");
//			pst1.setInt(1,  anno_corrente);
//			pst1.setBoolean(2,  true);
//			pst1.executeQuery();
//			
//			//Aggiorna per effetto del passaggio al vecchio anno
//			pst1.setInt(1,  anno_corrente-1);
//			pst1.executeQuery();
//		}

		//FINE DELL'AGGIORNAMENTO LOOKUP_TIPO_ISPEZIONE PER PASSAGGIO AL NUOVO ANNO
	
		
		switch (tipologia)
		{
		

		case TIPOLOGIA_MOLLUSCHI :
		{


			LookupList verificaQuantitativo = new LookupList(db, "lookup_verifica_quantitativo");
			verificaQuantitativo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("VerificaQuantitativo", verificaQuantitativo);

			LookupList Declassamento = new LookupList(db, "lookup_classi_acque");
			Declassamento.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Declassamento", Declassamento);

			LookupList motivicunoneseguito = new LookupList(db, "lookup_motivo_controllo_non_eseguito");
			motivicunoneseguito.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("MotivoControlloNonEseguito", motivicunoneseguito);

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
				
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);

			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			
			
			TipoIspezione.removeElementByLevel(11);
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			 
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}

		case TIPOLOGIA_IMPRESE :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
			}
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoIspezione.removeElementByLevel(3);
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			else
			{
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}
		case TIPOLOGIA_OPU :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
			}
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "opu_stabilimento")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "opu_stabilimento")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoIspezione.removeElementByLevel(3);
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione =getMotiviIspezione(db);
				
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			else{
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			
			
			
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			LookupList TipoMobili = new LookupList(db,"lookup_tipo_mobili");
			context.getRequest().setAttribute("TipoMobili", TipoMobili);

			break;
		}
		case TIPOLOGIA_ANAGRAFICA :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
			}
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			String stabId = null;
			try { stabId = String.valueOf(Integer.parseInt(context.getRequest().getParameter("altId"))-20000000); } catch (Exception e) {}

			if (stabId != null) {
				if (!hasLineeCategorizzabili(db, stabId, "opu_stabilimento")){
					TipoCampione.removeElementByLevel(3);
				}
				if (hasOnlyLineeNoScia(db, stabId, "opu_stabilimento")){
					TipoCampione.removeElementByLevel(11);
				}
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoIspezione.removeElementByLevel(3);
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione =getMotiviIspezione(db);
				
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			else{
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			
			
			
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			LookupList TipoMobili = new LookupList(db,"lookup_tipo_mobili");
			context.getRequest().setAttribute("TipoMobili", TipoMobili);

			break;
		}
		case TIPOLOGIA_RICHIESTE :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);


			}

			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(1);
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(7);
			TipoCampione.removeElementByLevel(1000);
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
//			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
//			TipoIspezione.removeElementByLevel(20);
//			LookupElement piano = TipoIspezione.getElementfromCodiceInterno("2a");
//			TipoIspezione = new LookupList();
//			TipoIspezione.add(piano);
//			TipoIspezione.removeElementByLevel(11);
//			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
				
			String filtroCodiciAttivita = "(";
			for (int i = 0; i< org.aspcfs.modules.suap.actions.AccountVigilanza.CODICI_INTERNI_ATTIVITA_CONTROLLO.length; i++){
				filtroCodiciAttivita+="'"+org.aspcfs.modules.suap.actions.AccountVigilanza.CODICI_INTERNI_ATTIVITA_CONTROLLO[i]+"',";
			}
			filtroCodiciAttivita = filtroCodiciAttivita.substring(0, filtroCodiciAttivita.length()-1);
			filtroCodiciAttivita+=")";
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = ControlliUfficialiUtil.getMotiviDaCodiceInternoPianoOAttivita(db, "(20,11)", null, filtroCodiciAttivita);
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_SINTESIS :
		{
			System.out.println("tipoDest stampato in sintesis:"+tipoDest);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				//TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			//if(tipoDest!=null && tipoDest.equals("Autoveicolo")) -- modifica 11/01/2022 per sbloccare gli audit sui sintesis ibridi
			//{
			//	TipoCampione.removeElementByLevel(1);
			//}
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("altId"), "sintesis_stabilimento")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("altId"), "sintesis_stabilimento")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
				
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}
		
		case TIPOLOGIA_API :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoCampione.removeElementByLevel(1);
			}
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "apicoltura_apiari")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "apicoltura_apiari")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoIspezione.removeElementByLevel(3);
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione =getMotiviIspezione(db);
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			else{
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}

		case TIPOLOGIA_OPERATORI_NON_ALTROVE :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(7);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoCampione.removeElementByLevel(2);
				TipoCampione.removeElementByLevel(3);
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			else
			{
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}
		case TIPOLOGIA_IMBARCAZIONI :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);

			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}
		case TIPOLOGIA_ORGANIZZAZZIONE_INTERNA_ASL :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByCode(23);
				TipoCampione.removeElementByLevel(23);
				TipoCampione.removeElementByLevel(1); //Cancello vecchio audit
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(2);
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(4); // solo per molluschi
			TipoCampione.removeElementByCode(23);
			TipoCampione.removeElementByLevel(23);
			TipoCampione.removeElementByLevel(11);
			TipoCampione.removeElementByLevel(1); //Cancello vecchio audit
		
			LookupList SiteIdList = new LookupList();
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			//SiteIdList.addItem(-1,  "-- TUTTI --");
			SiteIdList.setTable("lookup_site_id");
			SiteIdList.buildListWithEnabled(db);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);



			LookupList oggettoAudit = new LookupList();
			oggettoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			oggettoAudit.setTable("lookup_oggetto_audit");
			oggettoAudit.buildList(db);
			context.getRequest().setAttribute("OggettoAudit", oggettoAudit);


		
			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo"); 
			
			context.getRequest().setAttribute("ListaMotiviAudit", getMotiviAudit(db, "(0,1,2,3,4,5)") );
			//context.getRequest().setAttribute("ListaMotiviAudit", getMotiviAuditDpat(db) );

			AuditTipo.removeElementByLevel(0);
			AuditTipo.removeElementByLevel(1);
			AuditTipo.removeElementByLevel(2);
			AuditTipo.removeElementByLevel(3);
			AuditTipo.removeElementByLevel(4);
			AuditTipo.removeElementByLevel(5);
			AuditTipo.addItem(-1, "Selezionare una voce");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);


			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			if(tipoDest!=null && tipoDest.equals("Autoveicolo"))
			{
				TipoIspezione.removeElementByLevel(3);
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione =getMotiviIspezione(db);
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			else
			{
				ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
				if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
				{
					//ESTRAGGO SOLO IL B7
					listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
				}
				context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			}
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
			
		}
		case TIPOLOGIA_CANIPADRONALI :
		{

			LookupList tipi_controlli_cani_padronali = new LookupList(db,"lookup_tipo_controllo_cani_padronali") ;
			
			context.getRequest().setAttribute("TipiControlliCani", tipi_controlli_cani_padronali);

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_STABILIMENTI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);

			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}


		case TIPOLOGIA_CANILI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);

			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_COLONIE :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);

			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}
		case TIPOLOGIA_OPERATORI_COMMERCIALI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_PUNTI_DI_SBARCO :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}
		case TIPOLOGIA_ZONECONTROLLO :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_AZIENDE_AGRICOLE :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
 
			break;
		}
		case TIPOLOGIA_TIPO_ACQUE :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(11);

			}

			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(1);
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(7);
			TipoCampione.removeElementByLevel(11);
			TipoCampione.removeElementByLevel(1000);
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
//			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
//			TipoIspezione.removeElementByLevel(20);
//			LookupElement piano = TipoIspezione.getElementfromCodiceInterno("2a");
//			TipoIspezione = new LookupList();
//			TipoIspezione.add(piano);
//			TipoIspezione.removeElementByLevel(11);
//			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
				
//			String filtroCodiciPiani = "(";
//			for (int i = 0; i< AcqueReteVigilanza.CODICI_INTERNI_PIANI_CONTROLLO.length; i++){
//				filtroCodiciPiani+=AcqueReteVigilanza.CODICI_INTERNI_PIANI_CONTROLLO[i]+",";
//			}
//			filtroCodiciPiani = filtroCodiciPiani.substring(0, filtroCodiciPiani.length()-1);
//			filtroCodiciPiani+=")";
//			
//			String filtroCodiciAttivita = "(";
//			for (int i = 0; i< AcqueReteVigilanza.CODICI_INTERNI_ATTIVITA_CONTROLLO.length; i++){
//				filtroCodiciAttivita+="'"+AcqueReteVigilanza.CODICI_INTERNI_ATTIVITA_CONTROLLO[i]+"',";
//			}
//			filtroCodiciAttivita = filtroCodiciAttivita.substring(0, filtroCodiciAttivita.length()-1);
//			filtroCodiciAttivita+=")";
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = ControlliUfficialiUtil.getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}


		case TIPOLOGIA_RIPRODUZIONE_ANIMALE:
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			TipoCampione.removeElementByLevel(3); //disabilitata la sorveglianza per mancanza di checklist
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}
		case TIPOLOGIA_TRASPORTO_ANIMALI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
			CategoriaTrasportata.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);


			/*LookupList llist = new LookupList(db,"lookup_specie_trasportata");
			llist.addItem(-1, "-- SELEZIONA VOCE --");
			llist.removeElementByLevel(1);

			context.getRequest().setAttribute("SpecieA", llist);
			 */

			break;
		}

		case TIPOLOGIA_ALLEVAMENTI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}

		case TIPOLOGIA_ABUSIVI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			TipoCampione.removeElementByLevel(3);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_PRIVATI :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(7);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.removeElementByLevel(3);
			TipoIspezione.removeElementByLevel(20);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione =getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}
		case TIPOLOGIA_LABORATORIHACCP :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(3);
			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione =getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
			CategoriaTrasportata.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);


			/*LookupList llist = new LookupList(db,"lookup_specie_trasportata");
			llist.addItem(-1, "-- SELEZIONA VOCE --");
			llist.removeElementByLevel(1);

			context.getRequest().setAttribute("SpecieA", llist);
			 */

			break;
		}

		case TIPOLOGIA_OSA :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");

			//		    TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
			CategoriaTrasportata.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);


			/*LookupList llist = new LookupList(db,"lookup_specie_trasportata");
			llist.addItem(-1, "-- SELEZIONA VOCE --");
			llist.removeElementByLevel(1);

			context.getRequest().setAttribute("SpecieA", llist);
			 */

			break;
		}


		case TIPOLOGIA_SOA1774 :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);



			LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
			DestinatarioCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);


			LookupList categoriaR = new LookupList(db, "lookup_categoriarischio_soa");
			categoriaR.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CategoriaRischioSoa", categoriaR);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA UNA O PIU VOCI --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

		


			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);


			LookupList Bpi = new LookupList(db, "lookup_bpi");
			Bpi.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			context.getRequest().setAttribute("Bpi", Bpi);


			LookupList Haccp = new LookupList(db, "lookup_haccp");
			Haccp.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			context.getRequest().setAttribute("Haccp", Haccp);



			break;
		}

		case TIPOLOGIA_IMPRESEFUORIREGIONE :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(3);
			TipoCampione.removeElementByLevel(7);

			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		TipoIspezione.removeElementByLevel(2);
			//		TipoIspezione.removeElementByLevel(6);
			TipoIspezione.removeElementByLevel(3);
			TipoIspezione.removeElementByLevel(11);
			TipoIspezione.removeElementByLevel(20);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);


			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}

		case TIPOLOGIA_OSM_RICONOSCITI :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}

		case TIPOLOGIA_OSM_REGISTRATI :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			break;
		}
		case TIPOLOGIA_FARMACIE :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "opu_stabilimento")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "opu_stabilimento")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);


			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			//		TipoIspezione.removeElementByLevel(2);
			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.removeElementByLevel(11);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");

			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");

			context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList);

			break;
		}
		case TIPOLOGIA_PARAFARMACIE :
		{

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if (!hasLineeCategorizzabili(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(3);
			}
			if (hasOnlyLineeNoScia(db, context.getRequest().getParameter("orgId"), "organization")){
				TipoCampione.removeElementByLevel(11);
			}
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);


			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);		
			TipoIspezione.removeElementByLevel(20);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);

			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");

			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");

			context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList);

			break;
		}
		default :
		{
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
			{
				TipoCampione.removeElementByLevel(1);
				TipoCampione.removeElementByLevel(3);
				TipoCampione.removeElementByLevel(4);
				TipoCampione.removeElementByLevel(5);
				TipoCampione.removeElementByLevel(7);
				TipoCampione.removeElementByLevel(11);
			}
			if(!systemStatus.hasPermission(user.getRoleId(), "vigilanza-cupregressi-ncposticipate-view"))
			{
				TipoCampione.removeElementByLevel(1000);
			}
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoCampione.removeElementByLevel(4);
			TipoCampione.removeElementByLevel(5);
			TipoCampione.removeElementByLevel(7);
			
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_ispezionesemplice-view"))
			{
				//ELIMINA TUTTO CHE NON SIA ISPEZIONE SEMPLICE
				TipoCampione.removeAllElementsButLevel(2); TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			}
			
			context.getRequest().setAttribute("TipoCampione", TipoCampione);
			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			//		    TipoIspezione.removeElementByLevel(2);

			TipoIspezione.removeElementByLevel(20);
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.removeElementByLevel(11);
			
			ArrayList<org.aspcfs.modules.vigilanza.base.MotivoIspezione> listaMotiviIspezione = getMotiviIspezione(db);
			if(systemStatus.hasPermission(user.getRoleId(), "cu_solo_pianob7-view"))
			{
				//ESTRAGGO SOLO IL B7
				listaMotiviIspezione =	getMotiviDaCodiceInternoPianoOAttivita(db ,"",  "("+getCodiciInterniDaAlias(db, "B7")+")", "");
			}
			context.getRequest().setAttribute("ListaMotiviIspezione", listaMotiviIspezione);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			break;
		}

		
		
		
		}
	}

	public static void buildElementControlliUfficialiPianoMonitoraggio(Connection db ,int tipologia, ActionContext context,int siteIdUser,int siteId) throws SQLException
	{  
		    
		LookupList PianoMonitoraggio1= new LookupList(db, "lookup_piano_monitoraggio",true);
		
		context.getRequest().setAttribute("PianoMonitoraggio1", PianoMonitoraggio1);

	}
	


	public static void buildElementControlliUfficialiPianoMonitoraggio(Connection db , ActionContext context) throws SQLException
	{  
		//String sql = "(select * from lookup_piano_monitoraggio where level = 1 and code in (select id_asl from cu_programmazioni_asl where enabled = true))";
		LookupList PianoMonitoraggio1= new LookupList(db, "lookup_piano_monitoraggio",true);
		//String sql = "(select * from lookup_piano_monitoraggio where level = 1 and code in (select id_asl from cu_programmazioni_asl where enabled = true)";
		//PianoMonitoraggio1.removeItemfromLookup(db, "lookup_piano_monitoraggio", "site_id is not null");
		//PianoMonitoraggio1.removeElementByLevel(2);
		//PianoMonitoraggio1.removeElementByLevel(3);
		PianoMonitoraggio1.addItem(-1, "... Piani Monitoraggio...");
		context.getRequest().setAttribute("PianoMonitoraggio1", PianoMonitoraggio1);

		LookupList PianoMonitoraggio2= new LookupList(db, "lookup_piano_monitoraggio",true);
		//PianoMonitoraggio2.removeItemfromLookup(db, "lookup_piano_monitoraggio", "site_id is not null");
		//PianoMonitoraggio2.removeElementByLevel(1);
		//PianoMonitoraggio2.removeElementByLevel(3);
		PianoMonitoraggio2.addItem(-1, "... Piani Monitoraggio Regionali ...");
		context.getRequest().setAttribute("PianoMonitoraggio2", PianoMonitoraggio2);

		LookupList PianoMonitoraggio3= new LookupList(db, "lookup_piano_monitoraggio",true);
		//PianoMonitoraggio3.addItemPianiLocaliinMonitoraggio(db, siteId);
		//PianoMonitoraggio3.removeElementByLevel(1);
		//PianoMonitoraggio3.removeElementByLevel(2);
		//PianoMonitoraggio3.addItem(-1, "... Piani Monitoraggio Straordinari ...");
		//PianoMonitoraggio3.setSelectStyle("color:red");
		context.getRequest().setAttribute("PianoMonitoraggio3", PianoMonitoraggio3);	    

	}


	public static void buildElementControlliUfficialiComune(Connection db ,int tipologia, ActionContext context, String tipoDest, SystemStatus systemStatus , int idUser , int siteId) throws SQLException
	{


		//LookupList OrgCategoriaRischioList = new LookupList(db, " (select * from get_checklist_by_idcontrollo("+context.getRequest().getParameter("id")+")) "); //flusso 142
		LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
		OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");

		context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);

		
		
		LookupList Condizionalita = new LookupList(db, "lookup_condizionalita_new");
		//Condizionalita.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		Condizionalita.setMultiple(true);
		Condizionalita.setSelectSize(5);
		context.getRequest().setAttribute("Condizionalita", Condizionalita);
		
		LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		Provvedimenti.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		Provvedimenti.setMultiple(true);
		Provvedimenti.setSelectSize(9);
		context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

		LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
		DestinatarioCampione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);

		LookupList ArticoliAzioni = new LookupList(db, "lookup_articoli_azioni");
		ArticoliAzioni.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("ArticoliAzioni", ArticoliAzioni);

		LookupList AzioniAdottate = new LookupList(db, "lookup_azioni_adottate");
		AzioniAdottate.addItem(-1, "-- SELEZIONA VOCE --");
		AzioniAdottate.setMultiple(true);
		AzioniAdottate.setSelectSize(5);
		context.getRequest().setAttribute("AzioniAdottate", AzioniAdottate);

		//Gestione specie trasporti animali 
		LookupList listaAnimali = new LookupList(db, "lookup_specie_trasportata");
		listaAnimali.addItem(-1, "-- SELEZIONA VOCE --");
		listaAnimali.setMultiple(true);
		listaAnimali.setSelectSize(10);
		context.getRequest().setAttribute("SpecieA", listaAnimali);

		LookupList EsitoControllo = new LookupList(db, "lookup_esito_controllo");
		EsitoControllo.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("EsitoControllo", EsitoControllo);

		LookupList distribuzionePartita = new LookupList(db, "lookup_distribuzione_partita");
		distribuzionePartita.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("DistribuzionePartita", distribuzionePartita);

		LookupList destinazioneDistribuzione = new LookupList(db, "lookup_destinazione_distribuzione");
		destinazioneDistribuzione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("DestinazioneDistribuzione", destinazioneDistribuzione);

		LookupList titoloNucleo = new LookupList();
		titoloNucleo.setTable("lookup_qualifiche");
		titoloNucleo.buildListNucleoIspettivo(db);
		
		UserBean user = (UserBean )context.getRequest().getSession().getAttribute("User");
		String nameContext=context.getRequest().getServletContext().getServletContextName();
		if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
		{
			titoloNucleo.removeElementByLevel(1);
			titoloNucleo.removeElementByLevel(10);
		
		}
		
		//SE L'UTENTE APPARTIENE AL GRUPPO CRR, ELIMINARE DALLA LOOKUP TUTTI GLI ALTRI RUOLI NON CRR
		if (user.getUserRecord().getId_tipo_gruppo_ruolo() == Role.GRUPPO_CRR)
		{
			titoloNucleo.removeElementByLevel(10);
			titoloNucleo.removeElementByLevel(1);
			titoloNucleo.removeElementByLevel(10000);
			titoloNucleo.removeElementByLevel(10001);
		}
		
		titoloNucleo.addItem(-1,  "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("TitoloNucleo",titoloNucleo);
		
		LookupList titoloNucleoTest = new LookupList();
		titoloNucleoTest.setTable("lookup_qualifiche_nucleo_old_view");
		titoloNucleoTest.buildListWithEnabled(db);
		titoloNucleoTest.addItem(-1,  "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("titoloNucleoTest",titoloNucleoTest);
		
		LookupList titoloNucleoDue = new LookupList(db,"lookup_nucleo_ispettivo");
		titoloNucleoDue.addItem(-1,  "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("TitoloNucleoDue",titoloNucleoDue);

	
		LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
		EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

		LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
		TipoAudit.removeElementByLevel(3);
		TipoAudit.removeElementByLevel(5);
		TipoAudit.addItem(-1, "-- SELEZIONA UNA O PIU VOCI --");
		context.getRequest().setAttribute("TipoAudit", TipoAudit);

		LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		SiteIdList.removeElementByCode(16);
		SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		//SiteIdList.addItem(-1,  "-- TUTTI --");
		context.getRequest().setAttribute("SiteIdList", SiteIdList);

		LookupList Bpi = new LookupList(db, "lookup_bpi");
		Bpi.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		context.getRequest().setAttribute("Bpi", Bpi);

		LookupList Haccp = new LookupList(db, "lookup_haccp");
		Haccp.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		context.getRequest().setAttribute("Haccp", Haccp);

		
		context.getRequest().setAttribute("ListaMotiviAudit", getMotiviAudit(db, "(1,2,3,4,5,101,102,103)") );
		//context.getRequest().setAttribute("ListaMotiviAudit", getMotiviAuditDpat(db) );

		LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
		AuditTipo.addItem(-1, "Selezionare una voce");
		
		AuditTipo.removeElementByLevel(1);
		AuditTipo.removeElementByLevel(2);
		AuditTipo.removeElementByLevel(3);
		AuditTipo.removeElementByLevel(4);
		AuditTipo.removeElementByLevel(5);
		AuditTipo.removeElementByLevel(101);
		AuditTipo.removeElementByLevel(102);
		AuditTipo.removeElementByLevel(103);
		context.getRequest().setAttribute("AuditTipo", AuditTipo);

		HashMap<Integer, HashMap<Integer, String>> lista_ispezioni = new HashMap<Integer, HashMap<Integer,String>>();

		
		String sel = "select * from lookup_ispezione where enabled = true order by level , description";
		
		if (tipologia==TIPOLOGIA_TIPO_ACQUE)
		{
			sel =  "select * from lookup_ispezione where enabled = true and description ilike '%igiene degli alimenti%'order by level , description";
		}
		
		PreparedStatement pst = db.prepareStatement(sel);
		ResultSet rs = pst.executeQuery() ;
		while (rs.next())
		{
			int level 	= 	rs.getInt("level")	;
			int code 	=	rs.getInt("code")	;
			String desc = 	rs.getString("description")	;


			if (lista_ispezioni.get(level) == null)
			{
				if (tipologia == TIPOLOGIA_MOLLUSCHI)
				{
					lista_ispezioni.put(level,new HashMap<Integer, String>());
				}
				else
				{
					
					
					if (code != 90)
					{
						lista_ispezioni.put(level,new HashMap<Integer, String>());
					}


				}

			}
			HashMap<Integer, String> lista = lista_ispezioni.get(level);
			lista.put(code, desc);
			lista_ispezioni.put(level, lista);


		}
		context.getRequest().setAttribute("Ispezione", lista_ispezioni);



		//select * from lookup_specie_trasportata where enabled = true order by level , description
		HashMap<Integer, HashMap<Integer, String>> lista_specie_ispezione = new HashMap<Integer, HashMap<Integer,String>>();

		String sel_specie = "select * from lookup_specie_trasportata where enabled = true order by level , description";
		PreparedStatement pst_specie = db.prepareStatement(sel_specie);
		ResultSet rs_specie = pst_specie.executeQuery() ;
		while (rs_specie.next())
		{
			int level 	= 	rs_specie.getInt("level")	;
			int code 	=	rs_specie.getInt("code")	;
			String desc = 	rs_specie.getString("description")	;

			if (lista_specie_ispezione.get(level) == null)
			{
				if (tipologia == TIPOLOGIA_MOLLUSCHI)
				{
					lista_specie_ispezione.put(level,new HashMap<Integer, String>());
				}
				else
				{
					if (code != 90)
					{
						lista_specie_ispezione.put(level,new HashMap<Integer, String>());
					}


				}

			}

			HashMap<Integer, String> lista_s = lista_specie_ispezione.get(level);
			lista_s.put(code, desc);
			lista_specie_ispezione.put(level, lista_s);


		}
		context.getRequest().setAttribute("IspezioneSpecie", lista_specie_ispezione);


		LookupList IspezioneMacrocategorie = new LookupList(db, "lookup_ispezione_macrocategorie");
		IspezioneMacrocategorie.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		context.getRequest().setAttribute("IspezioneMacrocategorie", IspezioneMacrocategorie);


	}
	

	public static void buildLookupTipoControlloUfficialeOpu(Connection db ,ActionContext context,SystemStatus systemStatus,int idUser ,int siteId) throws SQLException
	{
		UserBean user = (UserBean )context.getRequest().getSession().getAttribute("User");
		
		buildElementControlliUfficialiComuneOpu(db, context,  systemStatus, idUser, siteId);
		buildElementControlliUfficialiPianoMonitoraggio(db, context);
		
		LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
		TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		
		TipoCampione.removeElementByLevel(4);
		TipoCampione.removeElementByLevel(5);
		
		

		context.getRequest().setAttribute("TipoCampione", TipoCampione);

		LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
		//TipoIspezione.removeElementByLevel(2);
		TipoIspezione.removeElementByLevel(20);
		TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
		
		context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
	}
	public static void buildElementControlliUfficialiComuneOpu(Connection db , ActionContext context,  SystemStatus systemStatus , int idUser , int siteId) throws SQLException
	{


		LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
		OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");

		context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);

		
		
		LookupList Condizionalita = new LookupList(db, "lookup_condizionalita");
		Condizionalita.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		Condizionalita.setMultiple(true);
		Condizionalita.setSelectSize(9);
		context.getRequest().setAttribute("Condizionalita", Condizionalita);
		
		LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		Provvedimenti.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		Provvedimenti.setMultiple(true);
		Provvedimenti.setSelectSize(9);
		context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

		LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
		DestinatarioCampione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);

		LookupList ArticoliAzioni = new LookupList(db, "lookup_articoli_azioni");
		ArticoliAzioni.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("ArticoliAzioni", ArticoliAzioni);

		LookupList AzioniAdottate = new LookupList(db, "lookup_azioni_adottate");
		AzioniAdottate.addItem(-1, "-- SELEZIONA VOCE --");
		AzioniAdottate.setMultiple(true);
		AzioniAdottate.setSelectSize(5);
		context.getRequest().setAttribute("AzioniAdottate", AzioniAdottate);

		//Gestione specie trasporti animali 
		LookupList listaAnimali = new LookupList(db, "lookup_specie_trasportata");
		listaAnimali.addItem(-1, "-- SELEZIONA VOCE --");
		listaAnimali.setMultiple(true);
		listaAnimali.setSelectSize(10);
		context.getRequest().setAttribute("SpecieA", listaAnimali);

		LookupList EsitoControllo = new LookupList(db, "lookup_esito_controllo");
		EsitoControllo.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("EsitoControllo", EsitoControllo);

		LookupList distribuzionePartita = new LookupList(db, "lookup_distribuzione_partita");
		distribuzionePartita.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("DistribuzionePartita", distribuzionePartita);

		LookupList destinazioneDistribuzione = new LookupList(db, "lookup_destinazione_distribuzione");
		destinazioneDistribuzione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("DestinazioneDistribuzione", destinazioneDistribuzione);

		LookupList titoloNucleo = new LookupList();
		titoloNucleo.setTable("lookup_qualifiche");
		titoloNucleo.buildListNucleoIspettivo(db);
		titoloNucleo.addItem(-1,  "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("TitoloNucleo",titoloNucleo);
		
				
		
		
		LookupList titoloNucleoTest = new LookupList();
		titoloNucleoTest.setTable("lookup_qualifiche_nucleo_old_view");
		titoloNucleoTest.buildListWithEnabled(db);
		titoloNucleoTest.addItem(-1,  "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("titoloNucleoTest",titoloNucleoTest);
		
		

		LookupList titoloNucleoDue = new LookupList(db,"lookup_nucleo_ispettivo");
		titoloNucleoDue.addItem(-1,  "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("TitoloNucleoDue",titoloNucleoDue);

	
		context.getRequest().setAttribute("TitoloNucleoTre",titoloNucleoDue);

		
		context.getRequest().setAttribute("TitoloNucleoQuattro",titoloNucleoDue);

	
		context.getRequest().setAttribute("TitoloNucleoCinque",titoloNucleoDue);     

		
		context.getRequest().setAttribute("TitoloNucleoSei",titoloNucleoDue);

	
		context.getRequest().setAttribute("TitoloNucleoSette",titoloNucleoDue);

		
		context.getRequest().setAttribute("TitoloNucleoOtto",titoloNucleoDue);

	
		context.getRequest().setAttribute("TitoloNucleoNove",titoloNucleoDue);

	
		context.getRequest().setAttribute("TitoloNucleoDieci",titoloNucleoDue);


		LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
		EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

		LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
		TipoAudit.removeElementByLevel(3);
		TipoAudit.removeElementByLevel(5);
		TipoAudit.addItem(-1, "-- SELEZIONA UNA O PIU VOCI --");
		context.getRequest().setAttribute("TipoAudit", TipoAudit);

		LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		//SiteIdList.addItem(-1,  "-- TUTTI --");
		context.getRequest().setAttribute("SiteIdList", SiteIdList);

		LookupList Bpi = new LookupList(db, "lookup_bpi");
		Bpi.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		context.getRequest().setAttribute("Bpi", Bpi);

		LookupList Haccp = new LookupList(db, "lookup_haccp");
		Haccp.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		context.getRequest().setAttribute("Haccp", Haccp);

		LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
		AuditTipo.addItem(-1, "Selezionare una voce");
		//	    if(	idUser== 22 || idUser== 18  ||  idUser==  35  || idUser== 15 	|| 
		//	    	idUser== 21 || idUser== 19  ||  idUser==  16  || idUser==  20 	|| 
		//	  		idUser== 34 || idUser== 33  ||  idUser==  25  || idUser== 26	|| 
		//	  		idUser== 23 || idUser== 24)
		//	    {
		AuditTipo.removeElementByLevel(1);
		AuditTipo.removeElementByLevel(2);
		AuditTipo.removeElementByLevel(3);
		AuditTipo.removeElementByLevel(4);
		AuditTipo.removeElementByLevel(5);
		AuditTipo.removeElementByLevel(101);
		AuditTipo.removeElementByLevel(102);
		AuditTipo.removeElementByLevel(103);
		//	    }
		context.getRequest().setAttribute("AuditTipo", AuditTipo);





		HashMap<Integer, HashMap<Integer, String>> lista_ispezioni = new HashMap<Integer, HashMap<Integer,String>>();

		
		String sel = "select * from lookup_ispezione where enabled = true order by level , description";
		
//		if (tipologia==TIPOLOGIA_TIPO_ACQUE)
//		{
//			sel =  "select * from lookup_ispezione where enabled = true and description ilike '%igiene degli alimenti%'order by level , description";
//		}
		
		PreparedStatement pst = db.prepareStatement(sel);
		ResultSet rs = pst.executeQuery() ;
		while (rs.next())
		{
			int level 	= 	rs.getInt("level")	;
			int code 	=	rs.getInt("code")	;
			String desc = 	rs.getString("description")	;


			if (lista_ispezioni.get(level) == null)
			{
//				if (tipologia == TIPOLOGIA_MOLLUSCHI)
//				{
//					lista_ispezioni.put(level,new HashMap<Integer, String>());
//				}
//				else
//				{
					
					
					if (code != 90)
					{
						lista_ispezioni.put(level,new HashMap<Integer, String>());
					}


//				}

			}
			HashMap<Integer, String> lista = lista_ispezioni.get(level);
			lista.put(code, desc);
			lista_ispezioni.put(level, lista);


		}
		context.getRequest().setAttribute("Ispezione", lista_ispezioni);



		//select * from lookup_specie_trasportata where enabled = true order by level , description
		HashMap<Integer, HashMap<Integer, String>> lista_specie_ispezione = new HashMap<Integer, HashMap<Integer,String>>();

		String sel_specie = "select * from lookup_specie_trasportata where enabled = true order by level , description";
		PreparedStatement pst_specie = db.prepareStatement(sel_specie);
		ResultSet rs_specie = pst_specie.executeQuery() ;
		while (rs_specie.next())
		{
			int level 	= 	rs_specie.getInt("level")	;
			int code 	=	rs_specie.getInt("code")	;
			String desc = 	rs_specie.getString("description")	;

			if (lista_specie_ispezione.get(level) == null)
			{
				//if (tipologia == TIPOLOGIA_MOLLUSCHI)
//				{
//					lista_specie_ispezione.put(level,new HashMap<Integer, String>());
//				}
//				else
//				{
					if (code != 90)
					{
						lista_specie_ispezione.put(level,new HashMap<Integer, String>());
					}


//				}

			}

			HashMap<Integer, String> lista_s = lista_specie_ispezione.get(level);
			lista_s.put(code, desc);
			lista_specie_ispezione.put(level, lista_s);


		}
		context.getRequest().setAttribute("IspezioneSpecie", lista_specie_ispezione);


		LookupList IspezioneMacrocategorie = new LookupList(db, "lookup_ispezione_macrocategorie");
		IspezioneMacrocategorie.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		context.getRequest().setAttribute("IspezioneMacrocategorie", IspezioneMacrocategorie);

	}

	public static void insertCUestesi(Connection db, org.aspcfs.modules.vigilanza.base.Ticket cu, String codice_interno, ActionContext context) throws SQLException {

		
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();
		
		boolean trovato = false ;
		try {
			//determino se mi e arrivato il primo campo
			boolean arrivati = false;
			sql.append("SELECT nome_campo FROM cu_html_fields where codice_interno = ? order by ordine_campo limit 1");
			pst = db.prepareStatement(sql.toString());
			pst.setString(1, codice_interno);
			rs = pst.executeQuery();
			if (rs.next()){
			String nomeCampo2 = rs.getString("nome_campo");
			if (context.getParameter(nomeCampo2)!=null)
				arrivati = true;
			}
		
			
			if (arrivati){
				disabilitaVecchiaSchedaCU(db, cu.getId(), codice_interno);
			// determino l'insieme delle colonne
			sql = new StringBuffer();
			rs = null;
			sql.append("SELECT id, nome_campo,multiple FROM cu_html_fields where codice_interno = ? order by ordine_campo");
			pst = db.prepareStatement(sql.toString());
			pst.setString(1, codice_interno);
			rs = pst.executeQuery();
			sql = new StringBuffer();
			sql.append("INSERT INTO cu_fields_value (id_controllo,id_cu_html_fields,id_utente_inserimento, id_codice_interno, valore) values (?,?,?,?, ?) ");
				
				PreparedStatement pst2 = db.prepareStatement(sql.toString());
				while (rs.next())
				{
					trovato = true ;
					int idCampo = rs.getInt("id");
					String nomeCampo = rs.getString("nome_campo");
					pst2.setInt(1, cu.getId());
					pst2.setInt(2, idCampo);
					pst2.setInt(3, user_id);
					pst2.setString(4, codice_interno);
					
						if (rs.getBoolean("multiple")==false)
						{
							pst2.setString(5, context.getParameter(nomeCampo));
							if (context.getParameter(nomeCampo)!=null)
								pst2.execute();
						}
						else
						{
							
							String[] valueSel = context.getRequest().getParameterValues(nomeCampo);
							if(valueSel!= null)
							for(int i = 0 ; i < valueSel.length; i++)
							{
								pst2.setString(5, valueSel[i]);
								pst2.execute();
							}
							
						}
				}
				} 
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
  			
		} finally {
		}
		
	}
	

	public static HashMap<String,ArrayList<Contact>> getUtentiAttiviperaslVeterinari(Connection db,int idasl)throws SQLException{

		HashMap<String,ArrayList<Contact>> listaUtenti = new HashMap<String, ArrayList<Contact>>();

		HashMap<String, Integer> listaVeterinari = RuoliUtentiParser.getListaServiziVeterinari();


		String sql="select distinct on (upper(replace(contact.namelast,' ', ''))||upper(replace(contact.namefirst, ' ', ''))) contact.user_id, upper(contact.namelast) as namelast,access.site_id,upper(contact.namefirst) as namefirst "
				+ " from contact,access, access_dati "
				+ "where contact.contact_id = access.contact_id  and access_dati.user_id = access.user_id and contact.enabled=true and role_id = ? ";

		if(idasl!=-1)
			sql=sql+" and access_dati.site_id="+idasl;

		sql=sql+" order by (upper(replace(contact.namelast,' ', ''))||upper(replace(contact.namefirst, ' ', ''))), " +
                   " upper(contact.namelast), "+
                   " access.site_id, "+
                   " upper(contact.namefirst) ";

		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=null;

		Iterator<String> itListaVeterinari = listaVeterinari.keySet().iterator();
		while (itListaVeterinari.hasNext())
		{
			ArrayList<Contact> listaUtentiattiviperAsl=new ArrayList<Contact>();
			String label = itListaVeterinari.next();
			int value = listaVeterinari.get(label);
			pst.setInt(1,value);
			rs = pst.executeQuery();
			Contact contact = null ;
			while(rs.next())
			{
				int user_id = rs.getInt(1);
				String value1=rs.getString(2);
				String namef=rs.getString(4);
				value1=value1+" "+namef+"";
				contact = new Contact();
				contact.setUserId(user_id);
				contact.setNameLast(value1);
				listaUtentiattiviperAsl.add(contact);

			}
			listaUtenti.put(label, listaUtentiattiviperAsl);
		}

		return listaUtenti;

	}
	
public static boolean disabilitaVecchiaSchedaCU(Connection db,int idTicket, String codice_interno) {
		
		PreparedStatement pst = null;
		int result = 0;
		String sql ="UPDATE cu_fields_value SET enabled = false where id_controllo = ? and id_codice_interno = ? and enabled = true";
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idTicket);
			pst.setString(2, codice_interno);
			result = pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result==0)
			return false;
		return true;
	}


public static void refreshVistaNucleo(Connection db,int idControllo) {
	ControlliUfficialiThread run = new ControlliUfficialiThread();
	run.refreshNucleo(idControllo, db);
	
}





private static boolean hasLineeCategorizzabili(Connection db, String riferimentoId, String riferimentoIdNomeTab) throws SQLException {

	int riferimentoIdInt = -1;
	try { riferimentoIdInt = Integer.parseInt(riferimentoId); } catch (Exception e) {}
	
	boolean esito = false;
	
	if (riferimentoIdInt == -1)
		return false;
	
	PreparedStatement pst = db.prepareStatement("select * from get_has_linee_categorizzabili(?, ?);");
	int i = 0;
	pst.setInt(++i, riferimentoIdInt);
	pst.setString(++i, riferimentoIdNomeTab);
	System.out.println("Has linee categorizzabili: "+pst.toString());
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		esito = rs.getBoolean(1);
	}
	return esito;
	
}

private static boolean hasOnlyLineeNoScia(Connection db, String riferimentoId, String riferimentoIdNomeTab) throws SQLException {

	int riferimentoIdInt = -1;
	try { riferimentoIdInt = Integer.parseInt(riferimentoId); } catch (Exception e) {}
	
	boolean esito = false;
	
	if (riferimentoIdInt == -1)
		return false;
	
	PreparedStatement pst = db.prepareStatement("select * from get_has_only_linee_noscia(?, ?);");
	int i = 0;
	pst.setInt(++i, riferimentoIdInt);
	pst.setString(++i, riferimentoIdNomeTab);
	System.out.println("Has only linee no scia: "+pst.toString());
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		esito = rs.getBoolean(1);
	}
	return esito;
	
}


//public static boolean aggiornaCategoriaQuantitativa(Connection db, int idControllo) throws SQLException { //flusso 142
//	boolean esito = false;
//	
//	PreparedStatement pst = db.prepareStatement("select * from aggiorna_categoria_rischio_quantitativa(?)");
//	pst.setInt(1, idControllo);
//	ResultSet rs = pst.executeQuery();
//	if (rs.next())
//		esito = rs.getBoolean(1);
//	return esito;
//	
//}
}
