/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;


/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OperatoreList extends Vector implements SyncableList {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.OperatoreList.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		} 
	}


	private int stato = -1 ;
	

	private String partitaIva ;
	private String codiceFiscale ;
	private String nomeRappresentante = null;
	private String cognomeRappresentante = null;
	protected PagedListInfo pagedListInfo = null;
	private String ragioneSociale;
	private String partIva;
	private int idAsl = -1;
	private Integer[] idLineaProduttiva ;
	private String[] descrizioneLineaProduttiva ;
	private int idOperatore;
	private int siteId;
	private String telefono1;
	private String telefono2;
	private int owner;
	private int enteredBy;
	private String email;
	private String fax;
	private String note;
	private int modifiedBy;
	private int idComuneStabilimento ;
	private String comune ;
	private String codiceRegistrazione ;
	private int idTipoAttivitaApicoltura ;
	private String codiceFiscaleDelegato;;
	private String codiceAzienda ;
	
	private int statoApiario=-1 ;
	
	
	

	
	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public int getStatoApiario() {
		return statoApiario;
	}

	public void setStatoApiario(int statoApiario) {
		this.statoApiario = statoApiario;
	}

	public String getCodiceFiscaleDelegato() {
		return codiceFiscaleDelegato;
	}

	public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
		this.codiceFiscaleDelegato = codiceFiscaleDelegato;
	}

	public int getIdTipoAttivitaApicoltura() {
		return idTipoAttivitaApicoltura;
	}

	public void setIdTipoAttivitaApicoltura(int idTipoAttivitaApicoltura) {
		this.idTipoAttivitaApicoltura = idTipoAttivitaApicoltura;
	}
	
	public void setIdTipoAttivitaApicoltura(String idTipoAttivitaApicoltura) {
		
		if (!"".equals(idTipoAttivitaApicoltura))
		this.idTipoAttivitaApicoltura = Integer.parseInt(idTipoAttivitaApicoltura);
	}

	public int getStato() {
		return stato;
	}

	public void setStato(String stato)
	{
		if (!"".equals(stato) && stato != null)
			this.stato=Integer.parseInt(stato);
			
	}

	public void setStato(int stato) {
		this.stato = stato;
	}



	public String getCodiceRegistrazione() {
		return codiceRegistrazione;
	}



	public void setCodiceRegistrazione(String codiceRegistrazione) {
		this.codiceRegistrazione = codiceRegistrazione;
	}




	public String getComune() {
		return comune;
	}



	public void setComune(String comune) {
		this.comune = comune;
	}



	public OperatoreList() { }
	
	
	



	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}

	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}

	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}

	public String[] getDescrizioneLineaProduttiva() {
		return descrizioneLineaProduttiva;
	}

	public void setDescrizioneLineaProduttiva(String[] descrizioneLineaProduttiva) {
		this.descrizioneLineaProduttiva = descrizioneLineaProduttiva;
	}

	public Integer[] getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(Integer[] idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}

	public void setIdLineaProduttiva(String[] idLineaProduttiva) {

		this.idLineaProduttiva = new Integer[idLineaProduttiva.length];
		for(int i = 0 ; i<idLineaProduttiva.length; i++)
		{
			this.idLineaProduttiva[i] = Integer.parseInt(idLineaProduttiva[i]) ;
		}
	}


	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public void setIdAsl(String idAsl) {
		this.idAsl = new Integer(idAsl).intValue();
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}


	public void setIdOperatore(String idOperatore) {
		this.idOperatore = new Integer(idOperatore).intValue();
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}


	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public int getIdComuneStabilimento() {
		return idComuneStabilimento;
	}

	public void setIdComuneStabilimento(int idComuneStabilimento) {
		this.idComuneStabilimento = idComuneStabilimento;
	}

	public void setIdComuneStabilimento(String idComuneStabilimento) {
		if(idComuneStabilimento!=null && !"".equals(idComuneStabilimento))
			this.idComuneStabilimento = Integer.parseInt(idComuneStabilimento);
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		OperatoreList.log = log;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartIva() {
		return partIva;
	}

	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}


	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale (String codiceFiscale) {
		this.codiceFiscale=codiceFiscale;
	}





	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}



	public void buildList(Connection db) throws SQLException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			Operatore thisOperatore = this.getObject(rs);
			Indirizzo sl = new Indirizzo(db,rs.getInt("id_indirizzo"));
			sl.setTipologiaSede(1);
			thisOperatore.getListaSediOperatore().add( sl);
			thisOperatore.setRappLegale(new SoggettoFisico(db, rs.getInt("id_soggetto_fisico")));
			
			this.add(thisOperatore);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListApicoltori(Connection db) throws SQLException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException {
		PreparedStatement pst = null;

		ResultSet rs = queryListApicoltori(db, pst);
		while (rs.next()) {
			Operatore thisOperatore = this.getObject(rs);
			Indirizzo sl = new Indirizzo(db,rs.getInt("id_indirizzo"));
			sl.setTipologiaSede(1);
			thisOperatore.getListaSediOperatore().add( sl);
			thisOperatore.setRappLegale(new SoggettoFisico(db, rs.getInt("id_soggetto_fisico")));
			
			this.add(thisOperatore);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	
	
	
	public void buildListAttivitaApicoltura(Connection db) throws SQLException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException {
		PreparedStatement pst = null;

		ResultSet rs = queryListAttivita(db, pst);
		while (rs.next()) {
			Operatore thisOperatore = this.getObject(rs);
			Indirizzo sl = new Indirizzo(db,rs.getInt("id_indirizzo"));
			sl.setTipologiaSede(1);
			thisOperatore.getListaSediOperatore().add( sl);
			
			this.add(thisOperatore);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}

	/**
	 *  Gets the object attribute of the OrganizationList object
	 *
	 * @param  rs             Description of the Parameter
	 * @return                The object value
	 * @throws  SQLException  Description of the Exception
	 */
	public Operatore getObject(ResultSet rs) throws SQLException {
		Operatore thisOperatore = new Operatore(rs);
		
		return thisOperatore;
	}
 


	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
 
		//Need to build a base SQL statement for counting records

		sqlCount.append(
			
				"SELECT  count(*) as recordcount "+
						"from apicoltura_imprese o "+
						"join apicoltura_relazione_imprese_sede_legale  s on s.id_apicoltura_imprese =o.id and s.enabled " +
						"join apicoltura_apiari apiari on apiari.id_operatore = o.id and apiari.trashed_date is null  "+
						"left join opu_indirizzo ind on ind.id=s.id_indirizzo "+
						"left join comuni1 c on c.id = ind.comune where 1=1 and o.trashed_date is null "	
			 ); 
		
		// sqlCount.append("and oss.stato_ruolo = 1 ");

		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
		 			sqlFilter.toString());
		 
			items = prepareFilter(pst);
 

			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();
			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.ragione_sociale");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY o.ragione_sociale ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}
 
 

		sqlSelect.append(
				"distinct o.flag_scia,o.id_richiesta_suap,o.sincronizzato_bdn, o.data_fine,o.flag_laboratorio_annesso,o.codice_azienda_regionale,o.id_bda,rel.id_soggetto_fisico,o.stato,o.id_asl,ind.id as id_indirizzo,o.tipo_attivita_apicoltura , o.ragione_sociale,o.partita_iva,o.codice_fiscale_impresa,o.domicilio_digitale,o.faxt,o.telefono_fisso,o.telefono_cellulare,o.data_inizio,"+
				"ind.via as indirizzo_sede_legale,c.nome as comune_sede_legale,c.istat as istat_legale,ind.cap as cap_sede_legale,o.codice_azienda,"+
				"ind.provincia as prov_sede_legale,o.entered,o.modified,o.enteredby,o.modifiedby, o.id as idOperatore "+  
				"from apicoltura_imprese o " +
				"join apicoltura_apiari apiari on apiari.id_operatore = o.id and apiari.trashed_date is null   "+
				"left join apicoltura_rel_impresa_soggetto_fisico rel on rel.id_apicoltura_imprese=o.id "+
				"left join apicoltura_relazione_imprese_sede_legale  s on s.id_apicoltura_imprese =o.id and s.enabled "+
				"left join opu_indirizzo ind on ind.id=s.id_indirizzo "+
				"left join comuni1 c on c.id = ind.comune where 1=1 and o.trashed_date is null  "
				);
		//sqlSelect.append("and oss.stato_ruolo = 1 ");

 


		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);



		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = pst.executeQuery() ;
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	
	
	public ResultSet queryListApicoltori(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
 
		//Need to build a base SQL statement for counting records

		sqlCount.append(
			
				"SELECT  count(*) as recordcount "+
						"from apicoltura_imprese o "+
						"join apicoltura_relazione_imprese_sede_legale  s on s.id_apicoltura_imprese =o.id and s.enabled "
						+ "left join apicoltura_deleghe deleghe on deleghe.codice_fiscale_delegante = o.codice_fiscale_impresa "+
						"left join opu_indirizzo ind on ind.id=s.id_indirizzo "+
						"left join comuni1 c on c.id = ind.comune where 1=1 and o.trashed_date is null and  (deleghe.codice_fiscale_delegato ilike ? or o.codice_fiscale_impresa ilike ?) and o.stato != "+StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE+" and o.stato!="+StabilimentoAction.API_STATO_CESSATO	+" and o.stato!="+StabilimentoAction.API_STATO_CESSATO_SINCRONIZZATO	
			 ); 
		
		// sqlCount.append("and oss.stato_ruolo = 1 ");

		

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() );
		 
			pst.setString(1, codiceFiscaleDelegato);
			pst.setString(2, codiceFiscale);

			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();
			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.ragione_sociale");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY o.ragione_sociale ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}
 
 
		//v.stato_validazione is null è stato messo per contemplare i casi in cui la scia è assente (apicoltori inseriti prima della gesione scia in apicoltura)
		sqlSelect.append(
				"distinct o.flag_scia,o.id_richiesta_suap, o.sincronizzato_bdn ,o.data_fine, o.codice_azienda_regionale,o.flag_laboratorio_annesso, o.id_bda,rel.id_soggetto_fisico,o.stato,o.id_asl,ind.id as id_indirizzo,o.tipo_attivita_apicoltura , o.ragione_sociale,o.partita_iva,o.codice_fiscale_impresa,o.domicilio_digitale,o.faxt,o.telefono_fisso,o.telefono_cellulare,o.data_inizio,"+
				"ind.via as indirizzo_sede_legale,c.nome as comune_sede_legale,c.istat as istat_legale,ind.cap as cap_sede_legale,o.codice_azienda,"+
				"ind.provincia as prov_sede_legale,o.entered,o.modified,o.enteredby,o.modifiedby, o.id as idOperatore "+  
				"from apicoltura_imprese o "+
				"left join suap_ric_scia_operatori_denormalizzati_view v on v.id_opu_operatore = o.id_richiesta_suap  "+
				"left join apicoltura_deleghe deleghe on deleghe.codice_fiscale_delegante = o.codice_fiscale_impresa "+
				"left join apicoltura_rel_impresa_soggetto_fisico rel on rel.id_apicoltura_imprese=o.id "+
				"left join apicoltura_relazione_imprese_sede_legale  s on s.id_apicoltura_imprese =o.id and s.enabled "+
				"left join opu_indirizzo ind on ind.id=s.id_indirizzo "+
				"left join comuni1 c on c.id = ind.comune "
				+ " where 1=1 and ((o.tipo_attivita_apicoltura = 1 and  (v.stato_validazione <> 2 or v.stato_validazione is null)) or (o.tipo_attivita_apicoltura = 2)) AND "  
				+ "o.stato!=4  and o.trashed_date is null "
				+ "and ( o.codice_fiscale_impresa ilike ?) "
				+ "and o.stato != "+StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE+" and o.stato!="+StabilimentoAction.API_STATO_CESSATO +" and o.stato!="+StabilimentoAction.API_STATO_CESSATO_SINCRONIZZATO
				);
		//sqlSelect.append("and oss.stato_ruolo = 1 ");

 
		pst = db.prepareStatement(
				sqlSelect.toString() +  " " + sqlOrder.toString());
		
		pst.setString(1, codiceFiscale);


		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = pst.executeQuery() ;
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		
		System.out.println("pst query api:"+pst.toString());
		return rs;
	}


	
	
	
	
	
	
	
	
	
	
	public ResultSet queryListAttivita(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
			
				"SELECT  count(*) as recordcount "+
						"from apicoltura_imprese o "+
						" where 1=1 and o.trashed_date is null  and o.stato != "+StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE+" and o.stato!="+StabilimentoAction.API_STATO_CESSATO	
			 ); 
		
		// sqlCount.append("and oss.stato_ruolo = 1 ");
		createFilter(db, sqlFilter);
		

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString()+ sqlFilter.toString() );
		
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();
			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.ragione_sociale");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY o.ragione_sociale ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}
 
 

		sqlSelect.append(
				" *,o.id as idOperatore "+
						"from apicoltura_imprese o "+
						" where 1=1 and o.trashed_date is null  and o.stato != "+StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE+" and o.stato!="+StabilimentoAction.API_STATO_CESSATO	
			 ); 
		//sqlSelect.append("and oss.stato_ruolo = 1 ");

 


		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		
		prepareFilter(pst);

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = pst.executeQuery() ;
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}




	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 * @param  sqlFilter  Description of Parameter
	 * @param  db         Description of the Parameter
	 * @since             1.2
	 */

	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		//andAudit( sqlFilter );
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (codiceAzienda!=null && !"".equals(codiceAzienda))
		{
			sqlFilter.append(" and o.codice_azienda ilike ? ");
			
		}
		if (enteredBy>0)
		{
			sqlFilter.append(" and o.enteredby="+enteredBy+" ");
		}
		
		if (idTipoAttivitaApicoltura>0)
		{
			sqlFilter.append(" and tipo_attivita_apicoltura=? ");
		}
		
			if (stato >=0 )
			{
				sqlFilter.append(" and  o.stato="+stato+"  and ( apiari.flag_laboratorio = false or apiari.flag_laboratorio=false) ");
			}
			
			if (statoApiario >=0 )
			{
				sqlFilter.append(" and  apiari.stato="+statoApiario+" and ( apiari.flag_laboratorio = false or apiari.flag_laboratorio=false) ");
			}
		
		if (codiceRegistrazione!= null && ! "".equalsIgnoreCase(codiceRegistrazione))
		{
			sqlFilter.append("and o.codice_registrazione ilike ? " );
		}
		if (partitaIva != null && !"".equals(partitaIva)){
			sqlFilter.append("and o.partita_iva ilike ? " );
		}
		
		
		
		if (codiceFiscale != null && !"".equals(codiceFiscale)){
			sqlFilter.append("and o.codice_fiscale_impresa ilike ? " );
		}
		
		
		if ((ragioneSociale != null && ! "".equals(ragioneSociale)))
		{
			sqlFilter.append("and o.ragione_sociale ILIKE ? ");  		

		}
		
//		if (idComuneStabilimento > 0)
//		{
//			sqlFilter.append("AND  o.comune = ? ");
//
//		}
		
		
		
		
		if (idAsl > 0){
			sqlFilter.append("AND ( o.id_asl = ? ) ");
		}
		
		if (nomeRappresentante!= null && ! "".equals(nomeRappresentante))
		{
			sqlFilter.append("AND o.nome_rapp_stab ilike ?");
		}
		
		if (cognomeRappresentante!= null && ! "".equals(cognomeRappresentante))
		{
			sqlFilter.append("AND o.cognome_rapp_stab ilike ?");
		}
		
	
		
	}


	



	/**
	 *  Sets the parameters for the preparedStatement - these items must
	 *  correspond with the createFilter statement
	 *
	 * @param  pst            Description of Parameter
	 * @return                Description of the Returned Value
	 * @throws  SQLException  Description of Exception
	 * @since                 1.2
	 */
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		
		if (codiceAzienda!=null && !"".equals(codiceAzienda))
		{
			pst.setString(++i, "%"+codiceAzienda+"%");	
			
		}
		
		if (idTipoAttivitaApicoltura>0)
		{
			pst.setInt(++i, idTipoAttivitaApicoltura);
		}
		if (codiceRegistrazione!= null && ! "".equalsIgnoreCase(codiceRegistrazione))
		{
			pst.setString(++i, codiceRegistrazione);	
		}
		if (partitaIva != null && !"".equals(partitaIva)){
			pst.setString(++i, partitaIva);	
		}
		if (codiceFiscale != null && !"".equals(codiceFiscale)){
			pst.setString(++i, codiceFiscale);	
		}
	

		if ((ragioneSociale != null && ! "".equals(ragioneSociale)))
		{
			pst.setString(++i, ragioneSociale);	

		}
		
		
		
		if (idAsl > 0){
			pst.setInt(++i, idAsl);
		}
		if (nomeRappresentante!= null && ! "".equals(nomeRappresentante))
		{
			pst.setString(++i, nomeRappresentante);
		}
		
		if (cognomeRappresentante!= null && ! "".equals(cognomeRappresentante))
		{
			pst.setString(++i, cognomeRappresentante);
		}

		return i;
	}




	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastAnchor(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNextAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNextAnchor(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSyncType(int arg0) {
		// TODO Auto-generated method stub

	}

	





}

