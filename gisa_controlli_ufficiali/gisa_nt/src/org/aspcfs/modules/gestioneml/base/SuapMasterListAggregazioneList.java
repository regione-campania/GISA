/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.abusivismi.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;
import org.aspcfs.utils.DatabaseUtils;

public class SuapMasterListAggregazioneList extends Vector  {

	private int id ; 
	private int idMacroarea;
	private String aggregazione;
	private int flussoOrig ;

	Boolean flagFisso;
	Boolean flagMobile ;
	Boolean flagApicoltura;
	Boolean flagRegistrabili;
	Boolean flagRiconoscibili;
	Boolean flagSintesis;
	Boolean flagBdu;
	Boolean flagVam;
	Boolean flagNoScia;


	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			SuapMasterListAggregazione thisAggregazione = this.getObject(rs);
			this.add(thisAggregazione);

		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		createFilter(db, sqlFilter);
		sqlSelect.append("SELECT DISTINCT "+MasterListImportUtil.TAB_AGGREGAZIONE+".* from "+MasterListImportUtil.TAB_MACROAREA
				+" join " +MasterListImportUtil.TAB_AGGREGAZIONE+" ON "+MasterListImportUtil.TAB_MACROAREA+".id = "+MasterListImportUtil.TAB_AGGREGAZIONE+".id_macroarea "
				+" join " +MasterListImportUtil.TAB_LINEA_ATTIVITA+" ON "+MasterListImportUtil.TAB_AGGREGAZIONE+".id = "+MasterListImportUtil.TAB_LINEA_ATTIVITA+".id_aggregazione "
				+" left join " +MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+" ON "+MasterListImportUtil.TAB_LINEA_ATTIVITA+".codice_univoco = "+MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+".codice_univoco "
				+ "WHERE 1=1 ");
		sqlOrder.append(" order by " + MasterListImportUtil.TAB_AGGREGAZIONE+ ".aggregazione ");

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		prepareFilter(pst);

		rs = DatabaseUtils.executeQuery(db, pst);

		return rs;
	}


	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}
		if(id>0)
		{
			sqlFilter.append(" and "+MasterListImportUtil.TAB_AGGREGAZIONE+".id= ?");
		}
		if(idMacroarea>0)
		{
			sqlFilter.append(" and "+MasterListImportUtil.TAB_AGGREGAZIONE+".id_macroarea= ?");
		}
		if(aggregazione != null && !"".equals(aggregazione))
		{
			sqlFilter.append(" and "+MasterListImportUtil.TAB_AGGREGAZIONE+".aggregazione ilike ?");
		}
		if(flussoOrig>0)
		{
			if (flussoOrig == MasterListImportUtil.FLUSSO_SINTESIS_RICONOSCIUTI)
				sqlFilter.append(" and "+MasterListImportUtil.TAB_AGGREGAZIONE+".id_flusso_originale in (?, ?) ");
			else
				sqlFilter.append(" and "+MasterListImportUtil.TAB_AGGREGAZIONE+".id_flusso_originale= ? ");
		}
		if(flagFisso != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".fisso = ?");
		}
		if(flagMobile != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".mobile = ?");
		}
		if(flagApicoltura != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".apicoltura = ?");
		}
		if(flagRegistrabili != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".registrabili = ?");
		}
		if(flagRiconoscibili != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".riconoscibili = ?");
		}
		if(flagSintesis != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".sintesis = ?");
		}
		if(flagBdu != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".bdu = ?");
		}
		if(flagVam != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".vam = ?");
		}
		if(flagNoScia != null)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+ ".no_scia = ?");
		}
	}


	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		if(id>0)
		{
			pst.setInt(++i, id);
		}
		
		if(idMacroarea>0)
		{
			pst.setInt(++i, idMacroarea);
		}
		if(aggregazione != null && !"".equals(aggregazione))
		{
			pst.setString(++i, aggregazione);
		}
		if(flussoOrig>0)
		{
			if (flussoOrig == MasterListImportUtil.FLUSSO_SINTESIS_RICONOSCIUTI){
					pst.setInt(++i, MasterListImportUtil.FLUSSO_SINTESIS_RICONOSCIUTI);
					pst.setInt(++i, MasterListImportUtil.FLUSSO_SINTESIS_REGISTRATI);
			}
			else
				pst.setInt(++i, flussoOrig);
		}
		
		if(flagFisso != null)
		{
			pst.setBoolean(++i, flagFisso);
		}
		if(flagMobile != null)
		{
			pst.setBoolean(++i, flagMobile);
		}
		if(flagApicoltura != null)
		{
			pst.setBoolean(++i, flagApicoltura);
		}
		if(flagRegistrabili != null)
		{
			pst.setBoolean(++i, flagRegistrabili);
		}
		if(flagRiconoscibili != null)
		{
			pst.setBoolean(++i, flagRiconoscibili);
		}
		if(flagSintesis != null)
		{
			pst.setBoolean(++i, flagSintesis);
		}
		if(flagBdu != null)
		{
			pst.setBoolean(++i, flagBdu);
		}
		if(flagVam != null)
		{
			pst.setBoolean(++i, flagVam);
		}
		if(flagNoScia != null)
		{
			pst.setBoolean(++i, flagNoScia);
		}
		
		return i ;
	}

	public SuapMasterListAggregazione getObject(ResultSet rs) throws SQLException {
		SuapMasterListAggregazione thisAggregazione = new SuapMasterListAggregazione(rs);
		return thisAggregazione;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMacroarea() {
		return idMacroarea;
	}

	public void setIdMacroarea(int idMacroarea) {
		this.idMacroarea = idMacroarea;
	}

	public String getAggregazione() {
		return aggregazione;
	}

	public void setAggregazione(String aggregazione) {
		this.aggregazione = aggregazione;
	}

	public int getFlussoOrig() {
		return flussoOrig;
	}

	public void setFlussoOrig(int flussoOrig) {
		this.flussoOrig = flussoOrig;
	}

	public Boolean getFlagFisso() {
		return flagFisso;
	}

	public void setFlagFisso(Boolean flagFisso) {
		this.flagFisso = flagFisso;
	}
	
	public void setFlagFisso(String flagFisso) {
		try {this.flagFisso = Boolean.parseBoolean(flagFisso); } catch(Exception e) {};
	}

	public Boolean getFlagMobile() {
		return flagMobile;
	}

	public void setFlagMobile(Boolean flagMobile) {
		this.flagMobile = flagMobile;
	}
	
	public void setFlagMobile(String flagMobile) {
		try {this.flagMobile = Boolean.parseBoolean(flagMobile); } catch(Exception e) {};
	}

	public Boolean getFlagApicoltura() {
		return flagApicoltura;
	}

	public void setFlagApicoltura(Boolean flagApicoltura) {
		this.flagApicoltura = flagApicoltura;
	}
	
	public void setFlagApicoltura(String flagApicoltura) {
		try {this.flagApicoltura = Boolean.parseBoolean(flagApicoltura); } catch(Exception e) {};
	}

	public Boolean getFlagRegistrabili() {
		return flagRegistrabili;
	}

	public void setFlagRegistrabili(Boolean flagRegistrabili) {
		this.flagRegistrabili = flagRegistrabili;
	}
	
	public void setFlagRegistrabili(String flagRegistrabili) {
		try {this.flagRegistrabili = Boolean.parseBoolean(flagRegistrabili); } catch(Exception e) {};
	}

	public Boolean getFlagRiconoscibili() {
		return flagRiconoscibili;
	}

	public void setFlagRiconoscibili(Boolean flagRiconoscibili) {
		this.flagRiconoscibili = flagRiconoscibili;
	}
	
	public void setFlagRiconoscibili(String flagRiconoscibili) {
		try {this.flagRiconoscibili = Boolean.parseBoolean(flagRiconoscibili); } catch(Exception e) {};
	}

	public Boolean getFlagSintesis() {
		return flagSintesis;
	}

	public void setFlagSintesis(Boolean flagSintesis) {
		this.flagSintesis = flagSintesis;
	}

	public void setFlagSintesis(String flagSintesis) {
		try {this.flagSintesis = Boolean.parseBoolean(flagSintesis); } catch(Exception e) {};
	}
	
	public Boolean getFlagBdu() {
		return flagBdu;
	}

	public void setFlagBdu(Boolean flagBdu) {
		this.flagBdu = flagBdu;
	}
	
	public void setFlagBdu(String flagBdu) {
		try {this.flagBdu = Boolean.parseBoolean(flagBdu); } catch(Exception e) {};
	}

	public Boolean getFlagVam() {
		return flagVam;
	}

	public void setFlagVam(Boolean flagVam) {
		this.flagVam = flagVam;
	}
	
	public void setFlagVam(String flagVam) {
		try {this.flagVam = Boolean.parseBoolean(flagVam); } catch(Exception e) {};
	}

	public Boolean getFlagNoScia() {
		return flagNoScia;
	}

	public void setFlagNoScia(Boolean flagNoScia) {
		this.flagNoScia = flagNoScia;
	}
	
	public void setFlagNoScia(String flagNoScia) {
		try {this.flagNoScia = Boolean.parseBoolean(flagNoScia); } catch(Exception e) {};
	}
	
	
}
