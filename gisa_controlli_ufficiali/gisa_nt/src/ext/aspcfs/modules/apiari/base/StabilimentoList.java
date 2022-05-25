/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.ImReal;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.postgresql.util.PSQLException;

import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

public class StabilimentoList extends Vector implements SyncableList {

	public static final int TIPOLOGIA_APIARIO = 1 ;
	public static final int TIPOLOGIA_DESTINAZIONE = 3 ;
	
	
	
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.StabilimentoList.class);
	protected PagedListInfo pagedListInfo = null;
	private int idOperatore ;
	private int idStabilimento ; 
	private int stato ;
	private int idAsl ;
	private int idAslApiario ;
	private Boolean flag_notifica_variazione_ubicazione ;
	private Boolean flag_notifica_variazione_censimento ;
	private Boolean flag_notifica_variazione_detentore ;
	private String cun ;
	private String ragioneSociale ;
	private String cfProprietario;
	private int statoApiario ;
	private int statoPregresso ;
//	private int idComune;
	private String comune ;
	private int idApiarioScelto ;
	private int progressivoApiario ;
	private int progressivoApiarioFiltro ;
	private Boolean flagLaboratorio =null;
	private int inRegione ;
	
	private String isNuovaRicercaApicoltura;
	private String ricercaPer;
	private String idComune;
	private String idAslString;
	
	
	public String getIsNuovaRicercaApicoltura() {
		return isNuovaRicercaApicoltura;
	}

	public void setIsNuovaRicercaApicoltura(String isNuovaRicercaApicoltura) {
		this.isNuovaRicercaApicoltura = isNuovaRicercaApicoltura;
	}
	public String getIdAslString() {
		return idAslString;
	}

	public void setIdAslString(String idAslString) {
		this.idAslString = idAslString;
	}

	public String getIdComune()
	{return this.idComune;
	
	}
	
	public void setIdComune(String idComune)
	{this.idComune = idComune;
	
	}
	
	public String geRicercaPer() {
		return  ricercaPer;
	}

	public void setRicercaPer(String searchricercaPer) {
		this. ricercaPer = searchricercaPer;
	}

	public int getInRegione() {
		return inRegione;
	}

	public void setInRegione(int inRegione) {
		this.inRegione = inRegione;
	}
	
	public void setInRegione(String inRegione) {
		if (inRegione!=null && !"".equals(inRegione))
		this.inRegione = Integer.parseInt(inRegione);
	}

	public Stabilimento getLaboratorio()
	{
		if (this.size()>0)
		{
			Iterator<Stabilimento> itStab = this.iterator();
			while (itStab.hasNext())
			{
				Stabilimento stab = itStab.next();
				if (stab.isFlagLaboratorio())
					return stab;
			}
		}
		return null ;
	}
	
	public boolean isFlagLaboratorio() {
		return flagLaboratorio;
	}

	public void setFlagLaboratorio(boolean flagLaboratorio) {
		this.flagLaboratorio = flagLaboratorio;
	}

	public int getProgressivoApiario() {
		return progressivoApiario;
	}

	public void setProgressivoApiario(int progressivoApiario) {
		this.progressivoApiario = progressivoApiario;
	}
	
	public void setProgressivoApiario(String progressivoApiario) {
		
		if(progressivoApiario!=null && !"".equals(progressivoApiario))
			this.progressivoApiario = Integer.parseInt(progressivoApiario);
	}
	
	
	

	public int getProgressivoApiarioFiltro() {
		return progressivoApiarioFiltro;
	}

	public void setProgressivoApiarioFiltro(int progressivoApiarioFiltro) {
		this.progressivoApiarioFiltro = progressivoApiarioFiltro;
	}

	
public void setProgressivoApiarioFiltro(String progressivoApiarioFiltro) {
		
		if(progressivoApiarioFiltro!=null && !"".equals(progressivoApiarioFiltro))
			this.progressivoApiarioFiltro = Integer.parseInt(progressivoApiarioFiltro);
	}

	public int getIdApiarioScelto() {
		return idApiarioScelto;
	}

	public void setIdApiarioScelto(int idApiarioScelto) {
		this.idApiarioScelto = idApiarioScelto;
	}
	
	public void setIdApiarioScelto(String idApiarioScelto) {
		if (idApiarioScelto!=null && !"".equals(idApiarioScelto))
			this.idApiarioScelto = Integer.parseInt(idApiarioScelto);
	}


	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public int getStatoPregresso() {
		return statoPregresso;
	}

	public void setStatoPregresso(int statoPregresso) {
		this.statoPregresso = statoPregresso;
	}

	public int getStatoApiario() {
		return statoApiario;
	}

	public void setStatoApiario(int statoApiario) {
		this.statoApiario = statoApiario;
	}

	public String getCun() {
		return cun;
	}

	public void setCun(String cun) {
		this.cun = cun;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCfProprietario() {
		return cfProprietario;
	}

	public void setCfProprietario(String cfProprietario) {
		this.cfProprietario = cfProprietario;
	}

	public boolean isFlag_notifica_variazione_ubicazione() {
		return flag_notifica_variazione_ubicazione;
	}

	public void setFlag_notifica_variazione_ubicazione(boolean flag_notifica_variazione_ubicazione) {
		this.flag_notifica_variazione_ubicazione = flag_notifica_variazione_ubicazione;
	}

	public boolean isFlag_notifica_variazione_censimento() {
		return flag_notifica_variazione_censimento;
	}

	public void setFlag_notifica_variazione_censimento(boolean flag_notifica_variazione_censimento) {
		this.flag_notifica_variazione_censimento = flag_notifica_variazione_censimento;
	}

	public boolean isFlag_notifica_variazione_detentore() {
		return flag_notifica_variazione_detentore;
	}

	public void setFlag_notifica_variazione_detentore(boolean flag_notifica_variazione_detentore) {
		this.flag_notifica_variazione_detentore = flag_notifica_variazione_detentore;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAslApiario(String idAslApiario) {
		if (idAslApiario!= null && !"".equals(idAslApiario))
		this.idAslApiario = Integer.parseInt(idAslApiario);
	}

	public int getIdAslApiario() {
		return idAslApiario;
	}

	public void setIdAslApiario(int idAslApiario) {
		this.idAslApiario = idAslApiario;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	
	
	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	
	

	

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub

	}
	
	

	
	
	
	
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		boolean ricercaPerApiario = ricercaPer != null && ricercaPer.contains("apiario");
		
		
		sqlCount.append("select count( s.id) as recordcount "
				+ " from apicoltura_imprese s  join apicoltura_apiari apiari on apiari.id_operatore = s.id  join opu_indirizzo ind on ind.id = apiari.id_indirizzo join comuni1 on comuni1.id=ind.comune  where 1=1  and s.trashed_date is null and apiari.trashed_date is null and s.stato !="+StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE);
		/*i filtri a seconda che sia ricerca per apiario o apicoltore (la query e' sempre uguale, quello che cambia e' su chi applicare restrizioni di parametri 
		 * che arrivano da client quali denominazione,comunque etc) vengon ocostruiti in create filter
		 */
		
		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);
			
			
			items = prepareFilter(pst);


			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(
						sqlCount.toString() +
						sqlFilter.toString() +
						"AND " + DatabaseUtils.toLowerCase(db) + "(org.name) < ? ");
				items = prepareFilter(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
		
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append(" order by progressivo ");
		}

		//Need to build a base SQL statement for returning records
		
		
	
		sqlSelect.append("select  apiari.* "
				+ " from apicoltura_imprese s  join apicoltura_apiari apiari on apiari.id_operatore = s.id  join opu_indirizzo ind on ind.id = apiari.id_indirizzo join comuni1 on comuni1.id=ind.comune   where 1=1 and s.trashed_date is null and apiari.trashed_date is null and s.stato !="+StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE);
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		
		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	public ResultSet queryStabilimento(Connection db, PreparedStatement pst,int idLineaProduttiva) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		
	
		sqlSelect.append("select s.* from apicoltura_apiari where 1=1 and id =? ");
		pst = db.prepareStatement(sqlSelect.toString());
		pst.setInt(1, idLineaProduttiva);

		rs = pst.executeQuery();
		
		return rs;
	}
	
	
	
	protected void createFilter(Connection db, StringBuffer sqlFilter)
	{
		/*ho splittato il flusso poiche' avendo modificato la logica clientside, ho necessita di modificare i bean mantenendo la retrocompatibilita visto che tali bean sono 
		 * condivisi e usati anche in altri punti
		 */
		if(isNuovaRicercaApicoltura != null && isNuovaRicercaApicoltura.contains("true"))
		{
			 createFilterNuovaRicercaApicoltura(db,sqlFilter);
		}
		else
		{
			createFilterVecchiaRicerca(db, sqlFilter);
		}
	}
	
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException
	{
		if(isNuovaRicercaApicoltura != null && isNuovaRicercaApicoltura.contains("true"))
		{
			return  prepareFilterNuovaRicerca(pst);
		}
		else
		{
			return prepareFilterVecchiaRicerca(pst);
		}
	}
	
	
	/*14/07 nuovo */
	protected void createFilterNuovaRicercaApicoltura(Connection db, StringBuffer sqlFilter) 
	{
		boolean ricercaPerApiario = ricercaPer != null && ricercaPer.contains("apiario");
		/*RICERCA Costants.TUTTE_ASL_CAMPANIA :  vuol dire che solo quelli in regione (quindi id != 16)
		 * Constants.INVALID_SITE : proprio tutti
		 * Altrimenti se idAsl > 0 setta il valore (puo' essere un'asl specifica o se e' 16  fuori regione)
		 */
		
		/*i parametri che utilizzo per la nuova ricerca, arrivano come searchNOMEPAR quindi come stringhe, 
		 * e in tal caso il framework gli mette %...% automaticamente
		 * quindi ne faccio stripping per i parametri che mi servono
		 */
		
		idAslString = idAslString != null ? idAslString.replace("%", "") : idAslString;
		idComune = idComune != null ? idComune.replace("%", "") : idComune;
		
		 
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}
		
		
		 
		if( idComune != null &&  idComune.trim().length( ) > 0 && Integer.parseInt(idComune) > 0)
		{
			sqlFilter.append(" and comuni1.id = ?" );
		}
		 
		
		 /*LA RICERCA NEL CAVALIERE APICOLTURA FA ARRIVARE IDASL VALORIZZATO SIA CHE SI CERCHI PER APICOLTORE CHE PER APIARIO, QUINDI IL CHECK PIU? AVANTI
		  * CHE LAVORA su idAslApiario e' fatto per retrocompatibilita'
		  */
		if(Integer.parseInt(idAslString) == Constants.INVALID_SITE) /*la costante indica di ricercare TUTTI */
		{
			/*quindi no restrizione su asl */
		}
		else if(Integer.parseInt(idAslString) == Constants.TUTTE_ASL_CAMPANIA) /*elimino solo quelli che sono fuori regione */
		{
			if(ricercaPerApiario) /*se e' per apiario */
			{
				sqlFilter.append(" and apiari.id_asl != 16");
			}
			else
			{
				sqlFilter.append(" and s.id_asl  != 16 ");

			}
		}
		else if(Integer.parseInt(idAslString) > 0) /*ho messo valore > 0 che non e' tutte_asl_campania quindi e' per asl specifica */
		{
			/*o e' valorizzato con id asl specifica, o con idAsl = 16 cioe' fuori regione */
			/*in entrambi i casi lo setto */
			if(ricercaPerApiario) /*se e' per apiario */
			{
				sqlFilter.append(" and apiari.id_asl = ?");
			}
			else
			{
				sqlFilter.append(" and s.id_asl = ? ");

			}
		}
		
	 
		
		if (ragioneSociale!=null && !"".equals(ragioneSociale))
		{
			sqlFilter.append(" and s.ragione_sociale ilike ? ");
		}
		
		if (cun!=null && !"".equals(cun))
		{
			sqlFilter.append(" and s.codice_azienda ilike ? ");
		}
		
		if (cfProprietario!=null && !"".equals(cfProprietario))
		{
			sqlFilter.append(" and s.codice_fiscale_impresa ilike ? ");
		}
		
	    
		
	}
	
	/*14/07 nuovo */
	protected int prepareFilterNuovaRicerca(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		  
		if (idComune!= null && !"".equals(comune) && Integer.parseInt(idComune) > 0)
		{
			
			pst.setInt(++i, Integer.parseInt(idComune));

		}
		if (stato>0 && statoPregresso>0)
		{
			pst.setInt(++i, stato) ;
			pst.setInt(++i, statoPregresso) ;	
		
		}
		else
		{
			if (stato>0)
			{
				pst.setInt(++i, stato) ;
			}
		}
		
 
		if (Integer.parseInt(idAslString)>0 && Integer.parseInt(idAslString) != Constants.TUTTE_ASL_CAMPANIA)
		{
			pst.setInt(++i, Integer.parseInt(idAslString)) ;
		}
		
		
		if (flag_notifica_variazione_ubicazione!=null)
		{
			pst.setBoolean(++i, flag_notifica_variazione_ubicazione);
		}
		if (flag_notifica_variazione_censimento!=null)
		{
			pst.setBoolean(++i, flag_notifica_variazione_censimento);
		}
		if (flag_notifica_variazione_detentore!=null)
		{
			pst.setBoolean(++i, flag_notifica_variazione_detentore);
		}
		
		
		if (idOperatore>0)
		{
			pst.setInt(++i, idOperatore) ;
		}
		
		if (ragioneSociale!=null && !"".equals(ragioneSociale))
		{
			pst.setString(++i, ragioneSociale);
		}
		
		if (cun!=null && !"".equals(cun))
		{
			pst.setString(++i, cun);
		}
		
		if (cfProprietario!=null && !"".equals(cfProprietario))
		{
			pst.setString(++i, cfProprietario);
		}
		    
		
		return i;
	}

	
	
	
	
	public void createFilterVecchiaRicerca(Connection conn, StringBuffer sqlFilter)
	{

		//andAudit( sqlFilter );
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}
		
		
		if (inRegione==1)
		{
			sqlFilter.append(" and s.id_asl !=16");

		}
		else
			if (inRegione==2)
			{
				sqlFilter.append(" and s.id_asl =16");

			}		
		
		if(flagLaboratorio!=null)
		{
			sqlFilter.append(" and apiari.flag_laboratorio ="+flagLaboratorio);

		}
		
		if (idApiarioScelto>0)
		{
			sqlFilter.append(" and apiari.id != "+idApiarioScelto);
		}
		if (progressivoApiario>0)
		{
			sqlFilter.append(" and apiari.progressivo != "+progressivoApiario);
		}
		if (progressivoApiarioFiltro>0)
		{
			sqlFilter.append(" and apiari.progressivo = "+progressivoApiarioFiltro);
		}
		
		if (comune!= null && !"".equals(comune))
		{
			
			sqlFilter.append(" and comuni1.nome ilike ? ");

		}
		if (stato>0 && statoPregresso>0)
		{
			sqlFilter.append(" and s.stato in (?,?) ");
		}
		else
		{
			if (stato>0)
			{
				sqlFilter.append(" and s.stato = ? ");
			}
		}
		if (statoApiario>0)
		{
			if (statoApiario==StabilimentoAction.API_STATO_VALIDATO)
				sqlFilter.append(" and apiari.sincronizzato_bdn = true ");
			else
				sqlFilter.append(" and apiari.sincronizzato_bdn = false ");
			
		}
		
		
		if (idAsl>0)
		{
			sqlFilter.append(" and s.id_asl = ? ");
		}
		
		if (idAslApiario>0)
		{
			sqlFilter.append(" and ( s.id_asl = ?  or apiari.id_asl=? ) ");
		}
		
		if (flag_notifica_variazione_ubicazione!=null)
		{
			sqlFilter.append(" and apiari.flag_notifica_variazione_ubicazione = ? ");
		}
		
		
		if (flag_notifica_variazione_censimento!=null)
		{
			sqlFilter.append(" and apiari.flag_notifica_variazione_censimento = ? ");
		}
		if (flag_notifica_variazione_detentore!=null)
		{
			sqlFilter.append(" and apiari.flag_notifica_variazione_detentore = ? ");
		}
		
		if (idOperatore>0)
		{
			sqlFilter.append(" and apiari.id_operatore = ? ");
		}
		
		
		
		if (idStabilimento>0)
		{
			sqlFilter.append(" and apiari.id ="+idStabilimento);
		}
		
		
		if (ragioneSociale!=null && !"".equals(ragioneSociale))
		{
			sqlFilter.append(" and s.ragione_sociale ilike ? ");
		}
		
		if (cun!=null && !"".equals(cun))
		{
			sqlFilter.append(" and s.codice_azienda ilike ? ");
		}
		
		if (cfProprietario!=null && !"".equals(cfProprietario))
		{
			sqlFilter.append(" and s.codice_fiscale_impresa ilike ? ");
		}
		
	    
	}
	
	
	public int prepareFilterVecchiaRicerca(PreparedStatement pst) throws SQLException
	{

		int i = 0;
		if (comune!= null && !"".equals(comune))
		{
			
			pst.setString(++i, "%" + comune + "%");

		}
		if (stato>0 && statoPregresso>0)
		{
			pst.setInt(++i, stato) ;
			pst.setInt(++i, statoPregresso) ;	
		
		}
		else
		{
			if (stato>0)
			{
				pst.setInt(++i, stato) ;
			}
		}
		
//		if (statoApiario>0)
//		{
//			pst.setInt(++i, statoApiario) ;
//		}
		if (idAsl>0)
		{
			pst.setInt(++i, idAsl) ;
		}
		
		if (idAslApiario>0)
		{
			pst.setInt(++i, idAslApiario) ;
			pst.setInt(++i, idAslApiario) ;	
		
		}
		
		
		if (flag_notifica_variazione_ubicazione!=null)
		{
			pst.setBoolean(++i, flag_notifica_variazione_ubicazione);
		}
		if (flag_notifica_variazione_censimento!=null)
		{
			pst.setBoolean(++i, flag_notifica_variazione_censimento);
		}
		if (flag_notifica_variazione_detentore!=null)
		{
			pst.setBoolean(++i, flag_notifica_variazione_detentore);
		}
		
		
		if (idOperatore>0)
		{
			pst.setInt(++i, idOperatore) ;
		}
		
		if (ragioneSociale!=null && !"".equals(ragioneSociale))
		{
			pst.setString(++i, ragioneSociale);
		}
		
		if (cun!=null && !"".equals(cun))
		{
			pst.setString(++i, cun);
		}
		
		if (cfProprietario!=null && !"".equals(cfProprietario))
		{
			pst.setString(++i, cfProprietario);
		}
		    
		
		return i;
	
	}
	
	
	public void buildList(Connection db) throws SQLException, IndirizzoNotFoundException {
		PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);
		while (rs.next()) {

			Stabilimento thisStab = this.getObject(rs);
		
			thisStab.setDetentore(new SoggettoFisico(db, rs.getInt("id_soggetto_fisico")));
			thisStab.setOperatore(new Operatore(db,thisStab.getIdOperatore()));
			Indirizzo ubicazione = new Indirizzo(db,rs.getInt("id_indirizzo"));
			ubicazione.setTipologiaSede(5);
			thisStab.setSedeOperativa(ubicazione);
		    this.add(thisStab);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	
	
	


	public Stabilimento getObject(ResultSet rs) throws SQLException {
		  
		Stabilimento st = new Stabilimento() ;
		
		
		
		try
		{
			st.setFlagLaboratorio(rs.getBoolean("flag_laboratorio"));
		}
		catch(PSQLException e)
		{

		}
		
		st.setIdBda(rs.getInt("id_bda"));
		st.setData_assegnazione_censimento(rs.getTimestamp("data_assegnazione_censimento"));
		st.setProgressivoBDA(rs.getString("progressivo"));
		st.setIdStabilimento(rs.getInt("id"));
		st.setStato(rs.getInt("stato"));
		st.setIdApicolturaClassificazione(rs.getInt("id_apicoltura_lookup_classificazione"));
		st.setIdApicolturaSottospecie(rs.getInt("id_apicoltura_lookup_sottospecie"));
		st.setIdApicolturaModalita(rs.getInt("id_apicoltura_lookup_modalita"));
		st.setNumAlveari(rs.getInt("num_alveari"));
		st.setNumSciami(rs.getInt("num_sciami"));
		st.setDataApertura(rs.getTimestamp("data_inizio"));
		st.setDataChiusura(rs.getTimestamp("data_chiusura"));
		st.setDataCessazione(rs.getTimestamp("data_cessazione"));
		
		st.setSincronizzatoBdn(rs.getBoolean("sincronizzato_bdn"));
		
		st.setEnteredBy(rs.getInt("entered_by"));
		st.setModifiedBy(rs.getInt("modified_by"));
		st.setIdOperatore(rs.getInt("id_operatore"));
		st.setAslRomaBdn(rs.getString("asl_roma_bdn"));
		try
		{
	    st.setIdAsl(rs.getInt("id_asl"));
		}catch(SQLException e)
		{
			
		}
		
		try
		{
	    st.setErroreValidazione(rs.getString("descrizione_errore"));
		}catch(SQLException e)
		{
			
		}
		
		return st;
	  }

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub
		
	}
}
