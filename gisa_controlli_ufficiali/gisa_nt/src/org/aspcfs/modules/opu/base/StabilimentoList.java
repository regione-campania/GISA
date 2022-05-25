/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.postgresql.util.PSQLException;

public class StabilimentoList extends Vector implements SyncableList {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.StabilimentoList.class);
	protected PagedListInfo pagedListInfo = null;
	private int idOperatore ;
	private int idAsl ;
	private Integer[] idLineaProduttiva ;
	private int idStabilimento ; 
	private Boolean flag_dia = false ;
	
	private boolean escludiInDomanda = false;
	private boolean escludiRespinti = false;
	private boolean inDomanda = false;
	
	private String ragioneSociale ;
	private String nomeSoggettoFisico;
	private String cognomeSoggettoFisico;
	private String codiceFiscaleSoggettoFisico;
	private String partitaIva;
	private String codiceFiscaleImpresa;
	private String comuneSedeProduttiva;
	private String indirizzoSedeProduttiva;
	private String numeroRegistrazione;
	private String comuneSedeLegale;
	private String attivita;
	private boolean flagSearch =false;
	private boolean flagClean =false;
	private String operazione;
	private int iTipoAttivita ;
	private int idLineaProduttivaMobile ;
	
	private String civicoSedeProduttiva;
	private int idSoggettoFisico ;
	
	
	
	public int getIdSoggettoFisico() {
		return idSoggettoFisico;
	}



	public void setIdSoggettoFisico(int idSoggettoFisico) {
		this.idSoggettoFisico = idSoggettoFisico;
	}



	public String getCivicoSedeProduttiva() {
		return civicoSedeProduttiva;
	}



	public void setCivicoSedeProduttiva(String civicoSedeProduttiva) {
		this.civicoSedeProduttiva = civicoSedeProduttiva;
	}



	public int getIdLineaProduttivaMobile() {
		return idLineaProduttivaMobile;
	}



	public void setIdLineaProduttivaMobile(int idLineaProduttivaMobile) {
		this.idLineaProduttivaMobile = idLineaProduttivaMobile;
	}



	public int getiTipoAttivita() {
		return iTipoAttivita;
	}



	public void setiTipoAttivita(int iTipoAttivita) {
		this.iTipoAttivita = iTipoAttivita;
	}



	public String getOperazione() {
	return operazione;
}

	
	
public String getCodiceFiscaleImpresa() {
		return codiceFiscaleImpresa;
	}



	public void setCodiceFiscaleImpresa(String codiceFiscaleImpresa) {
		this.codiceFiscaleImpresa = codiceFiscaleImpresa;
	}



public void setOperazione(String operazione) {
	this.operazione = operazione;
}

	public boolean isFlagSearch() {
		return flagSearch;
	}

	public void setFlagSearch(boolean flagSearch) {
		this.flagSearch = flagSearch;
	}

	public Boolean getFlag_dia() {
		return flag_dia;
	}

	public void setFlag_dia(Boolean flag_dia) {
		this.flag_dia = flag_dia;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getNomeSoggettoFisico() {
		return nomeSoggettoFisico;
	}

	public void setNomeSoggettoFisico(String nomeSoggettoFisico) {
		this.nomeSoggettoFisico = nomeSoggettoFisico;
	}

	public String getCognomeSoggettoFisico() {
		return cognomeSoggettoFisico;
	}

	public void setCognomeSoggettoFisico(String cognomeSoggettoFisico) {
		this.cognomeSoggettoFisico = cognomeSoggettoFisico;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getComuneSedeProduttiva() {
		return comuneSedeProduttiva;
	}

	public void setComuneSedeProduttiva(String comuneSedeProduttiva) {
		this.comuneSedeProduttiva = comuneSedeProduttiva;
	}

	public boolean isFlag_dia() {
		return flag_dia;
	}

	public void setFlag_dia(boolean flag_dia) {
		this.flag_dia = flag_dia;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	private int idRelazioneStabilimentoLineaProduttiva = -1;
	
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	
	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAsl(String idAsl) {
		if (idAsl!=null && !"".equals(idAsl))
			this.idAsl = Integer.parseInt(idAsl);
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	
	

	public Integer[] getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(Integer[] idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
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
	
	

	public int getIdRelazioneStabilimentoLineaProduttiva() {
		return idRelazioneStabilimentoLineaProduttiva;
	}

	public void setIdRelazioneStabilimentoLineaProduttiva(
			int idRelazioneStabilimentoLineaProduttiva) {
		this.idRelazioneStabilimentoLineaProduttiva = idRelazioneStabilimentoLineaProduttiva;
	}

//	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
//		ResultSet rs = null;
//		int items = -1;
//
//		StringBuffer sqlSelect = new StringBuffer();
//		StringBuffer sqlCount = new StringBuffer();
//		StringBuffer sqlFilter = new StringBuffer();
//		StringBuffer sqlOrder = new StringBuffer();
//		
//		
//		sqlCount.append("select count(*) as recordcount from opu_stabilimento where 1=1 ");
//		createFilter(db, sqlFilter);
//
//		if (pagedListInfo != null) {
//			//Get the total number of records matching filter
//			pst = db.prepareStatement(
//					sqlCount.toString() +
//					sqlFilter.toString());
//			// UnionAudit(sqlFilter,db);
//			items = prepareFilter(pst);
//
//
//			rs = pst.executeQuery();
//			if (rs.next()) {
//				int maxRecords = rs.getInt("recordcount");
//				pagedListInfo.setMaxRecords(maxRecords);
//			}
//			rs.close();
//			pst.close();
//
//			//Determine the offset, based on the filter, for the first record to show
//			if (!pagedListInfo.getCurrentLetter().equals("")) {
//				pst = db.prepareStatement(
//						sqlCount.toString() +
//						sqlFilter.toString() +
//						"AND " + DatabaseUtils.toLowerCase(db) + "(org.name) < ? ");
//				items = prepareFilter(pst);
//				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
//				rs = pst.executeQuery();
//				if (rs.next()) {
//					int offsetCount = rs.getInt("recordcount");
//					pagedListInfo.setCurrentOffset(offsetCount);
//				}
//				rs.close();
//				pst.close();
//			}
//
//			//Determine column to sort by
//		
//			pagedListInfo.appendSqlTail(db, sqlOrder);
//
//			//Optimize SQL Server Paging
//			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
//		} else {
//			sqlOrder.append("");
//		}
//
//		//Need to build a base SQL statement for returning records
//		if (pagedListInfo != null) {
//			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
//		} else {
//			sqlSelect.append("SELECT distinct");
//		}
//		sqlSelect.append(" s.* from opu_stabilimento as s join opu_relazione_stabilimento_linee_produttive r on s.id=r.id_stabilimento where 1=1 ");
//		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
//		items = prepareFilter(pst);
//		
//		rs = pst.executeQuery();
//		if (pagedListInfo != null) {
//			pagedListInfo.doManualOffset(db, rs);
//		}
//		return rs;
//	}
	
	
	
	
	
	
	public ResultSet queryListSearchFisse(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		
	

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select distinct on (id_stabilimento) id_stabilimento as id_stab_dist,s.stab_id_attivita,s.stab_id_carattere, s.data_inizio_attivita,s.data_fine_attivita,s.id_indirizzo, "
				+ "flag_dia, s.numero_registrazione, s.id_opu_operatore, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa, s.indirizzo_sede_legale, s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, "+
"s.prov_sede_legale, s.note, s.entered, s.modified, s.enteredby, s.modifiedby,s.domicilio_digitale, s.flag_ric_ce, s.num_ric_ce, s.comune, s.id_asl, s.id_stabilimento, s.esito_import, s.data_import, s.cun, s.id_sinvsa, s.descrizione_errore,"+ 
"s.comune_stab, s.istat_operativo, s.indirizzo_stab, s.cap_stab, s.prov_stab, "+
"s.data_fine_dia, s.categoria_rischio, s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.id_stato "
				+ " from opu_operatori_denormalizzati_view s  where 1=1  "
				+ " and s.id_stato!=  4   ");
		
		if(partitaIva!=null && !"".equals(partitaIva))
		{
			sqlSelect.append(" and ( partita_iva = ?) ");
		}
		else
		{
			if(codiceFiscaleImpresa!=null && !"".equals(codiceFiscaleImpresa))
			{
				sqlSelect.append(" and ( codice_fiscale_impresa = ?) ");
			}
			
			else
				sqlSelect.append(" and ( partita_iva = ?) ");
			
		}
		
		
		
		if(iTipoAttivita==2)
		{
			
			if(idLineaProduttivaMobile > 0)
			{
				sqlSelect.append(" and stab_id_attivita=2 and id_linea_attivita_stab = ?") ;
			}
		}
		else
		{
			sqlSelect.append(" and stab_id_attivita=1 and levenshtein(lower(coalesce(s.indirizzo_stab,'')),lower(?))<=5 and s.comune_stab ilike ? and civico_sede_stab ilike ? ") ;
		}
				
		
		pst = db.prepareStatement(sqlSelect.toString());
		
		
		
		
		if(iTipoAttivita==2)
		{
			
			if(partitaIva!=null && !"".equals(partitaIva))
			{
				pst.setString(1, partitaIva);
			}
			else
			{
				if(codiceFiscaleImpresa!=null && !"".equals(codiceFiscaleImpresa))
				{
					pst.setString(1, codiceFiscaleImpresa);			
					
				}
				
			}
			
			if(idLineaProduttivaMobile > 0)
			{
				
				pst.setInt(2, idLineaProduttivaMobile);
			}
		}
		else
		{
			
			if(partitaIva!=null && !"".equals(partitaIva))
			{
				pst.setString(1, partitaIva);
			}
			else
			{
				if(codiceFiscaleImpresa!=null && !"".equals(codiceFiscaleImpresa))
				{
					pst.setString(1, codiceFiscaleImpresa);			
					
				}
				else
					pst.setString(1, "");	
					
				
			}
			pst.setString(2, indirizzoSedeProduttiva);
			pst.setString(3, comuneSedeProduttiva);
			pst.setString(4, civicoSedeProduttiva);
			}
		
		
		
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
		
		
	
		sqlSelect.append("select s.* from opu_stabilimento s join opu_relazione_stabilimento_linee_produttive r on s.id=r.id_stabilimento where 1=1 and r.id =? ");
		pst = db.prepareStatement(sqlSelect.toString());
		pst.setInt(1, idLineaProduttiva);

		rs = pst.executeQuery();
		
		return rs;
	}
	
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		
		sqlCount.append("select count( distinct(s.id_stabilimento)) as recordcount "
				+ "from opu_operatori_denormalizzati_view s where 1=1 ");
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
			sqlOrder.append("");
		}	

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select distinct on (id_stabilimento) id_stabilimento as id_stab_dist,s.stab_id_attivita,s.stab_id_carattere, s.data_inizio_attivita,s.data_fine_attivita,s.id_indirizzo, "
				+ "flag_dia, s.numero_registrazione, s.id_opu_operatore, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa, s.indirizzo_sede_legale, s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, "+
"s.prov_sede_legale, s.note, s.entered, s.modified, s.enteredby, s.modifiedby,s.domicilio_digitale, s.flag_ric_ce, s.num_ric_ce, s.comune, s.id_asl, s.id_stabilimento, s.esito_import, s.data_import, s.cun, s.id_sinvsa, s.descrizione_errore,"+ 
"s.comune_stab, s.istat_operativo, s.indirizzo_stab, s.cap_stab, s.prov_stab, "+
"s.data_fine_dia, s.categoria_rischio, s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.id_stato "
				+ " from opu_operatori_denormalizzati_view s  where 1=1 ");
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		
		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	
	public ResultSet queryListErrataCorrigeSoggettoFisico(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
	

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select distinct on (id_stabilimento) id_stabilimento as id_stab_dist,s.stab_id_attivita,s.stab_id_carattere, s.data_inizio_attivita,s.data_fine_attivita,s.id_indirizzo, "
				+ "flag_dia, s.numero_registrazione, s.id_opu_operatore, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa, s.indirizzo_sede_legale, s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, "+
"s.prov_sede_legale, s.note, s.entered, s.modified, s.enteredby, s.modifiedby,s.domicilio_digitale, s.flag_ric_ce, s.num_ric_ce, s.comune, s.id_asl, s.id_stabilimento, s.esito_import, s.data_import, s.cun, s.id_sinvsa, s.descrizione_errore,"+ 
"s.comune_stab, s.istat_operativo, s.indirizzo_stab, s.cap_stab, s.prov_stab, "+
"s.data_fine_dia, s.categoria_rischio, s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.id_stato "
				+ " from opu_operatori_denormalizzati_view s  where 1=1 ");
		
		if(!codiceFiscaleSoggettoFisico.equalsIgnoreCase("") && idOperatore>0)
		{
			sqlSelect.append(" and (cf_rapp_sede_legale ilike ? or id_opu_operatore =? )");
		}
		
		
		int i = 0 ;
		pst = db.prepareStatement(sqlSelect.toString()  );
		if(!codiceFiscaleSoggettoFisico.equalsIgnoreCase("") && idOperatore>0)
		{
			pst.setString(++i, codiceFiscaleSoggettoFisico);
			pst.setInt(++i, idOperatore);
		}
		
		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	
	
	
	
	public ResultSet queryTrasportatoriDistributori(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		
	
		sqlSelect.append("select  * "+ 
					" from public_functions.suap_stabilimenti_trasportatori_distributori_per_proprietario(?)");
		pst = db.prepareStatement(sqlSelect.toString());
		pst.setString(1, numeroRegistrazione);

		rs = pst.executeQuery();
		
		return rs;
	}
	
	protected void createFilter(Connection db, StringBuffer sqlFilter) 
	{
		//andAudit( sqlFilter );
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}
		
		if(flagClean==true)
		{
			sqlFilter.append(" and s.flag_clean =true ");
		}
		
		
		if(flagSearch==true)
		{
			sqlFilter.append(" and s.id_stato !=  "+Stabilimento.STATO_CESSATO);
		}
		
		if (idOperatore>0)
		{
			sqlFilter.append(" and s.id_opu_operatore = ? ");
		}
		
		if (flag_dia != null)
		{
			sqlFilter.append(" and s.flag_dia =  "+flag_dia + " ");
		}
		
		if (idStabilimento>0)
		{
			sqlFilter.append(" and s.id_stabilimento ="+idStabilimento);
		}
		if (idAsl>0)
		{
			sqlFilter.append(" and (s.id_asl = ? or id_asl is null) ");
		}
		
		if (idRelazioneStabilimentoLineaProduttiva > -1){
			sqlFilter.append(" and s.id_linea_attivita = ? ");
		}
		
		
	    if (idLineaProduttiva != null && idLineaProduttiva.length>0)
	    {
	    	sqlFilter.append("AND  s.id_linea_attivita_stab in ( ");
	    	for (int i = 0 ; i<idLineaProduttiva.length-1 ; i++)
	    	{
	    		if(! idLineaProduttiva[i].equals("-1"))
	    			sqlFilter.append(idLineaProduttiva[i]+",");
	    	}
	    	if(! idLineaProduttiva[idLineaProduttiva.length-1].equals("-1"))
	    		sqlFilter.append(idLineaProduttiva[idLineaProduttiva.length-1]+") ");
	    	else
	    		sqlFilter.append(") ");
	    	
	    	
	    }
	    
	    if (ragioneSociale != null && !ragioneSociale.equals(""))
	    {
	    	sqlFilter.append(" and s.ragione_sociale ilike ? ");
	    }
	    if (comuneSedeProduttiva != null && !comuneSedeProduttiva.equals(""))
	    {
	    	sqlFilter.append(" and s.comune_stab ilike ? ");
	    }
	    if (indirizzoSedeProduttiva != null && !indirizzoSedeProduttiva.equals(""))
	    {
	    	sqlFilter.append(" and s.indirizzo_stab ilike ? ");
	    }
	    if (partitaIva != null && !partitaIva.equals(""))
	    {
	    	sqlFilter.append(" and s.partita_iva  ilike ? ");
	    }
	    
	    
	    
	    
	    
	    if (codiceFiscaleImpresa != null && !codiceFiscaleImpresa.equals("") && (partitaIva==null || (partitaIva!=null && "".equals(partitaIva))))

	    {
	    	sqlFilter.append(" and s.codice_fiscale_impresa  ilike ? ");
	    }
	    
	    if (nomeSoggettoFisico != null && !nomeSoggettoFisico.equals(""))
	    {
	    	sqlFilter.append(" and s.nome_rapp_sede_legale ilike ? ");
	    }
	    if (cognomeSoggettoFisico != null && !cognomeSoggettoFisico.equals(""))
	    {
	    	sqlFilter.append(" and s.cognome_rapp_sede_legale ilike ? ");
	    }
	    if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals("") && codiceFiscaleSoggettoFisico.length()>=10)
	    {
	    	sqlFilter.append(" and s.cf_rapp_sede_legale ilike ? ");
	    }
	    if (idSoggettoFisico>0 && (codiceFiscaleSoggettoFisico == null || codiceFiscaleSoggettoFisico.equals("") || codiceFiscaleSoggettoFisico.length()<10))
	    {
	    	sqlFilter.append(" and s.id_soggetto_fisico ilike ? ");
	    }
	    
	    if (numeroRegistrazione != null && !numeroRegistrazione.equals(""))
	    {
	    	sqlFilter.append(" and s.numero_registrazione ilike ? ");
	    }
	    if (comuneSedeLegale != null && !comuneSedeLegale.equals(""))
	    {
	    	sqlFilter.append(" and s.comune_sede_legale ilike ? ");
	    }
	    if (attivita != null && !attivita.equals(""))
	    {
	    	sqlFilter.append(" and s.path_attivita_completo ilike ? ");
	    }
	    if (escludiInDomanda)
	    {
	    	sqlFilter.append(" and s.id_stato not in (?) ");
	    }
	   
	    if (inDomanda)
	    {
	    	sqlFilter.append(" and s.id_stato in (?) ");
	    }
	   
	}
	protected int prepareFilter(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		if (idOperatore>0)
		{
			pst.setInt(++i, idOperatore) ;
		}
		if (idAsl>0)
		{
			pst.setInt(++i, idAsl) ;
		}
		
		if (idRelazioneStabilimentoLineaProduttiva > -1){
			pst.setInt(++i, idRelazioneStabilimentoLineaProduttiva);
		}
		
		  if (ragioneSociale != null && !ragioneSociale.equals(""))
		    {
		    	pst.setString(++i, ragioneSociale);
		    }
		  if (comuneSedeProduttiva != null && !comuneSedeProduttiva.equals(""))
		    {
			  pst.setString(++i, comuneSedeProduttiva);
		    }
		  if (indirizzoSedeProduttiva != null && !indirizzoSedeProduttiva.equals(""))
		    {
			  pst.setString(++i, indirizzoSedeProduttiva);
		    } 
		    if (partitaIva != null && !partitaIva.equals(""))
		    {
		    	pst.setString(++i, partitaIva);
		    }
		    if (codiceFiscaleImpresa != null && !codiceFiscaleImpresa.equals("") && (partitaIva==null || (partitaIva!=null && "".equals(partitaIva))))
		    {
		    	pst.setString(++i, codiceFiscaleImpresa);
		    	}
		    
		    if (nomeSoggettoFisico != null && !nomeSoggettoFisico.equals(""))
		    {
		    	pst.setString(++i, nomeSoggettoFisico);
		    }
		    if (cognomeSoggettoFisico != null && !cognomeSoggettoFisico.equals(""))
		    {
		    	pst.setString(++i, cognomeSoggettoFisico);
		    }
		    if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals(""))
		    {
		    	pst.setString(++i, codiceFiscaleSoggettoFisico);
		    }
		    if (idSoggettoFisico>0 && (codiceFiscaleSoggettoFisico == null || codiceFiscaleSoggettoFisico.equals("") || codiceFiscaleSoggettoFisico.length()<10))
		    {
		    	pst.setInt(++i, idSoggettoFisico);
		    }
		    
		    if (numeroRegistrazione != null && !numeroRegistrazione.equals(""))
		    {
		    	pst.setString(++i, numeroRegistrazione);
		    }
		    if (comuneSedeLegale != null && !comuneSedeLegale.equals(""))
		    {
		    	pst.setString(++i, comuneSedeLegale);
		    }
		    if (attivita != null && !attivita.equals(""))
		    {
		    	pst.setString(++i, attivita);
		    }
		    if (escludiInDomanda)
		    {
		    	pst.setInt(++i, Stabilimento.STATO_IN_DOMANDA);
		    }   
		   
		    if (inDomanda)
		    {
		    	pst.setInt(++i, Stabilimento.STATO_IN_DOMANDA);
		    }   
		return i;
	}


	
	public void buildListSearch(Connection db) throws SQLException, IndirizzoNotFoundException {
		PreparedStatement pst = null;

		ResultSet rs = queryListSearchFisse(db, pst);
		while (rs.next()) {

			Stabilimento thisStab = this.getObject(rs);
			
			thisStab.setSedeOperativa(new Indirizzo(db,rs.getInt("id_indirizzo")));
			Operatore op =new Operatore();
			op.queryRecordOperatoreEsclusaSedeProduttiva(db, thisStab.getIdOperatore());
			thisStab.setOperatore(op);
//			Indirizzo sedeOp = new Indirizzo(db,thisStab.getSedeOperativa().getIdIndirizzo());
//		    thisStab.setSedeOperativa(sedeOp);
		   
		    LineaProduttivaList lpList = new LineaProduttivaList();
		    lpList.setIdStabilimento(thisStab.getIdStabilimento());
		    lpList.setIdLineaProduttiva(this.getIdLineaProduttiva());
		    lpList.buildList(db);
		    thisStab.setListaLineeProduttive(lpList);
			  
		    this.add(thisStab);

		    
		    
		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildList(Connection db) throws SQLException, IndirizzoNotFoundException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {

			Stabilimento thisStab = this.getObject(rs);
			
			thisStab.setSedeOperativa(new Indirizzo(db,rs.getInt("id_indirizzo")));
			Operatore op =new Operatore();
			op.queryRecordOperatoreEsclusaSedeProduttiva(db, thisStab.getIdOperatore());
			thisStab.setOperatore(op);
//			Indirizzo sedeOp = new Indirizzo(db,thisStab.getSedeOperativa().getIdIndirizzo());
//		    thisStab.setSedeOperativa(sedeOp);
		   
		    LineaProduttivaList lpList = new LineaProduttivaList();
		    lpList.setIdStabilimento(thisStab.getIdStabilimento());
		    lpList.setIdLineaProduttiva(this.getIdLineaProduttiva());
		    lpList.buildList(db);
		    thisStab.setListaLineeProduttive(lpList);
			
		     
		    this.add(thisStab);

		    
		    
		}
	}
		
		public void buildListErrataCorrigeSoggettoFisico(Connection db) throws SQLException, IndirizzoNotFoundException {
			PreparedStatement pst = null;

			ResultSet rs = queryListErrataCorrigeSoggettoFisico(db, pst);
			while (rs.next()) {

				Stabilimento thisStab = this.getObject(rs);
				
				thisStab.setSedeOperativa(new Indirizzo(db,rs.getInt("id_indirizzo")));
				Operatore op =new Operatore();
				op.queryRecordOperatoreEsclusaSedeProduttiva(db, thisStab.getIdOperatore());
				thisStab.setOperatore(op);
//				Indirizzo sedeOp = new Indirizzo(db,thisStab.getSedeOperativa().getIdIndirizzo());
//			    thisStab.setSedeOperativa(sedeOp);
			   
			    LineaProduttivaList lpList = new LineaProduttivaList();
			    lpList.setIdStabilimento(thisStab.getIdStabilimento());
			    lpList.setIdLineaProduttiva(this.getIdLineaProduttiva());
			    lpList.buildList(db);
			    thisStab.setListaLineeProduttive(lpList);
				
			     
			    this.add(thisStab);

			    
			    
			}
			

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	
	
	public void buildStabilimento(Connection db,int idLineaProduttiva) throws SQLException, IllegalAccessException, InstantiationException {
		PreparedStatement pst = null;

		ResultSet rs = queryStabilimento(db, pst,idLineaProduttiva);
		while (rs.next()) {

			Stabilimento thisStab = this.getObject(rs);
			Indirizzo sedeOp = new Indirizzo(db,rs.getInt("id_indirizzo"));
		    thisStab.setSedeOperativa(sedeOp);
		    
		    LineaProduttivaList lpList = new LineaProduttivaList();
		    lpList.setId(idLineaProduttiva);
		    lpList.buildList(db);
		    

		    thisStab.setListaLineeProduttive(lpList);
		     
		    
		    SoggettoFisico soggettoRappLegale = new SoggettoFisico(db, thisStab.getRappLegale().getIdSoggetto());
		    thisStab.setRappLegale(soggettoRappLegale);
			
		    this.add(thisStab);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	
	
	
	public void buildTrasportatoriDistributori(Connection db) throws SQLException, IllegalAccessException, InstantiationException {
		PreparedStatement pst = null;

		ResultSet rs = queryTrasportatoriDistributori(db, pst);
		while (rs.next()) {

			Stabilimento thisStab = new Stabilimento();
			
			Indirizzo sedeOp = new Indirizzo();
			sedeOp.setVia(rs.getString("sede_operativa"));
		    thisStab.setSedeOperativa(sedeOp);
		    
		    
		    SoggettoFisico soggettoRappLegale = new SoggettoFisico();
		    soggettoRappLegale.setCodFiscale(rs.getString("codice_fiscale_proprietario"));
		    soggettoRappLegale.setNome(rs.getString("nome_proprietario"));
		    soggettoRappLegale.setCognome(rs.getString("cognome_proprietario"));
		    
		    Operatore operatore = new Operatore();
		    operatore.setRappLegale(soggettoRappLegale);
		    
		    Indirizzo sedeLegale = new Indirizzo();
		    sedeLegale.setVia(rs.getString("sede_legale"));
		    operatore.setSedeLegaleImpresa(sedeLegale);
		    operatore.setRagioneSociale(rs.getString("ragione_sociale"));
		    operatore.setIdOperatore(rs.getInt("id_operatore"));
		    operatore.setPartitaIva(rs.getString("partita_iva"));
		    thisStab.setNumero_registrazione(rs.getString("numero_registrazione"));
		    thisStab.setCodice_ufficiale_esistente(rs.getString("codice_ufficiale_esistente"));
		    thisStab.setIdStabilimento(rs.getInt("id_stabilimento"));
		    
		    thisStab.setOperatore(operatore);
		    
		    
		    thisStab.setRappLegale(soggettoRappLegale);
			
		    this.add(thisStab);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public Stabilimento getObject(ResultSet rs) throws SQLException {
		  
		Stabilimento st = new Stabilimento() ;
		
		st.setTipoAttivita(rs.getInt("stab_id_attivita"));
		
		st.setTipoCarattere(rs.getInt("stab_id_carattere"));
		st.setName(rs.getString("ragione_sociale"));
		st.getSedeOperativa().setDescrizioneComune(rs.getString("comune_stab"));
		st.getSedeOperativa().setVia(rs.getString("indirizzo_stab"));
		st.getSedeOperativa().setProvincia(rs.getString("prov_stab"));
		st.setIdStabilimento(rs.getInt("id_stabilimento"));
		st.setOrgId(this.getIdStabilimento());
		st.setDataInizioAttivita(rs.getTimestamp("data_inizio_attivita"));
		st.setDataFineAttivita(rs.getTimestamp("data_fine_attivita"));
		st.setEnteredBy(rs.getInt("enteredby"));
		st.setModifiedBy(rs.getInt("modifiedby"));
		st.setIdOperatore(rs.getInt("id_opu_operatore"));
		st.setIdAsl(rs.getInt("id_asl"));
		
		try{
		st.setStato(rs.getInt("id_stato"));
		} catch(Exception e)
		{}
		
		st.setNumero_registrazione(rs.getString("numero_registrazione")); 
		
	    
		return st;
	  }
	
	
	

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub
		
	}

	public String getCodiceFiscaleSoggettoFisico() {
		return codiceFiscaleSoggettoFisico;
	}

	public void setCodiceFiscaleSoggettoFisico(String codiceFiscaleSoggettoFisico) {
		this.codiceFiscaleSoggettoFisico = codiceFiscaleSoggettoFisico;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}

	public void setComuneSedeLegale(String comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}

	public boolean isEscludiInDomanda() {
		return escludiInDomanda;
	}

	public void setEscludiInDomanda(boolean escludiInDomanda) {
		this.escludiInDomanda = escludiInDomanda;
	}

	public boolean isInDomanda() {
		return inDomanda;
	}

	public void setInDomanda(boolean inDomanda) {
		this.inDomanda = inDomanda;
	}
	public boolean isEscludiRespinti() {
		return escludiRespinti;
	}

	public void setEscludiRespinti(boolean escludiRespinti) {
		this.escludiRespinti = escludiRespinti;
	}

	public String getIndirizzoSedeProduttiva() {
		return indirizzoSedeProduttiva;
	}

	public void setIndirizzoSedeProduttiva(String indirizzoSedeProduttiva) {
		this.indirizzoSedeProduttiva = indirizzoSedeProduttiva;
	}

	public boolean isFlagClean() {
		return flagClean;
	}

	public void setFlagClean(boolean flagClean) {
		this.flagClean = flagClean;
	}
	
	
	
}
