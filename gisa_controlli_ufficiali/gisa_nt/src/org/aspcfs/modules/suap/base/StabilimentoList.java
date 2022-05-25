/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
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
	private String comuneSedeProduttiva;
	private String indirizzoSedeProduttiva;
	private String numeroRegistrazione;
	private String comuneSedeLegale;
	private String attivita;
	private boolean flagSearch =false;
	private boolean flagClean =false;
	private int tipoAttivita ;
	private int idComuneRichiesta ;
	private int stabIdAttivita ;
	
	



	public int getStabIdAttivita() {
		return stabIdAttivita;
	}

	public void setStabIdAttivita(int stabIdAttivita) {
		this.stabIdAttivita = stabIdAttivita;
	}

	public int getTipoAttivita() {
		return tipoAttivita;
	}

	public void setTipoAttivita(int tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	public int getIdComuneRichiesta() {
		return idComuneRichiesta;
	}

	public void setIdComuneRichiesta(int idComuneRichiesta) {
		this.idComuneRichiesta = idComuneRichiesta;
	}

	public void setTipoAttivita(String tipoAttivita) {
		if(tipoAttivita!=null && !"".equals(tipoAttivita))
			this.tipoAttivita = Integer.parseInt(tipoAttivita);
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



	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();


		sqlCount.append("select count( distinct(s.id_stabilimento)) as recordcount "
				+ "from suap_ric_scia_operatori_denormalizzati_view s where 1=1 ");
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


		sqlSelect.append("select distinct s.alt_id,s.stab_id_attivita,s.stab_id_carattere, s.data_inizio_attivita,s.data_fine_attivita,s.id_indirizzo, "
				+ "s.numero_registrazione, s.id_opu_operatore, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa, s.indirizzo_sede_legale, s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, "+
				"s.prov_sede_legale, s.note, s.entered, s.modified, s.enteredby, s.modifiedby,s.domicilio_digitale, s.comune, s.id_asl, s.id_stabilimento,"+ 
				"s.comune_stab, s.istat_operativo, s.indirizzo_stab, s.cap_stab, s.prov_stab,s.numero_registrazione_variazione,s.partita_iva_variazione, "+
				" s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.id_stato, s.stab_cessazione_stabilimento,s.sospensione_stabilimento,s.data_inizio_sospensione "
				+ " from suap_ric_scia_operatori_denormalizzati_view s  where 1=1 ");
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
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
		
		if(tipoAttivita>0)
		{
			sqlFilter.append(" and s.stab_id_attivita =?  ");
		}


		if(flagSearch==true)
		{
			sqlFilter.append(" and s.id_stato !=  "+Stabilimento.STATO_CESSATO);
		}

		if (idComuneRichiesta>0)
		{
			sqlFilter.append(" and s.id_comune_richiesta = ? ");
		}


		if (idOperatore>0)
		{
			sqlFilter.append(" and s.id_opu_operatore = ? ");
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

		if (nomeSoggettoFisico != null && !nomeSoggettoFisico.equals(""))
		{
			sqlFilter.append(" and s.nome_rapp_sede_legale ilike ? ");
		}
		if (cognomeSoggettoFisico != null && !cognomeSoggettoFisico.equals(""))
		{
			sqlFilter.append(" and s.cognome_rapp_sede_legale ilike ? ");
		}
		if (codiceFiscaleSoggettoFisico != null && !codiceFiscaleSoggettoFisico.equals(""))
		{
			sqlFilter.append(" and s.cf_rapp_sede_legale ilike ? ");
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

		if(tipoAttivita>0)
		{
			pst.setInt(++i, tipoAttivita) ;		

		}

		if (idComuneRichiesta>0)
		{
			pst.setInt(++i, idComuneRichiesta) ;
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

	public List<Stabilimento> getLista(List<Stabilimento> lista)
	{


		List<Stabilimento> listaToRet = new ArrayList<Stabilimento>();

		Iterator<Stabilimento> itStab =  this.iterator();

		while (itStab.hasNext())
		{
			Stabilimento st = itStab.next();
			if (listaToRet.size()>0)
			{
				for (Stabilimento operatore : lista)
				{

					if (st.compareTo(operatore,st)!=0)
					{
						listaToRet.add(st);
					}
				}
			}
			else
				listaToRet.add(st);

		}
		return listaToRet;
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
		st.setDataInizioAttivita(rs.getTimestamp("data_inizio_attivita"));
		st.setDataFineAttivita(rs.getTimestamp("data_fine_attivita"));
		st.setEnteredBy(rs.getInt("enteredby"));
		st.setModifiedBy(rs.getInt("modifiedby"));
		st.setIdOperatore(rs.getInt("id_opu_operatore"));
		st.setIdAsl(rs.getInt("id_asl"));
		st.setCessazioneStabilimento(rs.getBoolean("stab_cessazione_stabilimento"));
		try{
			st.setNumeroRegistrazioneVariazione(rs.getString("numero_registrazione_variazione"));
		} catch(Exception e)
		{}
		try{
			st.setAltId(rs.getInt("alt_id"));
		} catch(Exception e)
		{}
		try{
			st.setPartitaIvaVariazione(rs.getString("partita_iva_variazione"));
		} catch(Exception e)
		{}
		try{
			st.setStato(rs.getInt("id_stato"));
		} catch(Exception e)
		{}
		st.setNumeroRegistrazione(rs.getString("numero_registrazione"));
		st.setSospensioneStabilimento(rs.getBoolean("sospensione_stabilimento"));
		st.setDataInizioSospensione(rs.getTimestamp("data_inizio_sospensione"));


		return st;
	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	}




}
