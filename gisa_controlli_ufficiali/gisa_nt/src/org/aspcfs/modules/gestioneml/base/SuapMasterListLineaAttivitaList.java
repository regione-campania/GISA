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

public class SuapMasterListLineaAttivitaList extends Vector  {

	private int id ; 
	private int idAggregazione ; 
	private String codiceProdottoSpecie ; 
	private String lineaAttivita ;
	
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
			SuapMasterListLineaAttivita thisLineaAttivita = this.getObject(rs);
			this.add(thisLineaAttivita);

		}
		rs.close();
		
	}

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		createFilter(db, sqlFilter);
		sqlSelect.append("SELECT DISTINCT "+MasterListImportUtil.TAB_LINEA_ATTIVITA+".* from "+MasterListImportUtil.TAB_MACROAREA
				+" join " +MasterListImportUtil.TAB_AGGREGAZIONE+" ON "+MasterListImportUtil.TAB_MACROAREA+".id = "+MasterListImportUtil.TAB_AGGREGAZIONE+".id_macroarea "
				+" join " +MasterListImportUtil.TAB_LINEA_ATTIVITA+" ON "+MasterListImportUtil.TAB_AGGREGAZIONE+".id = "+MasterListImportUtil.TAB_LINEA_ATTIVITA+".id_aggregazione "
				+" left join " +MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+" ON "+MasterListImportUtil.TAB_LINEA_ATTIVITA+".codice_univoco = "+MasterListImportUtil.TAB_FLAG_LINEA_ATTIVITA+".codice_univoco "
				+ "WHERE 1=1 ");
		sqlOrder.append(" order by " + MasterListImportUtil.TAB_LINEA_ATTIVITA+ ".linea_attivita" );
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString() );
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
			sqlFilter.append(" and " + MasterListImportUtil.TAB_LINEA_ATTIVITA+ ".id= ?");
		}
		if(idAggregazione>0)
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_LINEA_ATTIVITA+ ".id_aggregazione= ?");
		}
		if(lineaAttivita != null && !"".equals(lineaAttivita))
		{
			sqlFilter.append(" and " + MasterListImportUtil.TAB_LINEA_ATTIVITA+ ".linea_attivita ilike ?");
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
		
		if(idAggregazione>0)
		{
			pst.setInt(++i, idAggregazione);
		}
		if(lineaAttivita != null && !"".equals(lineaAttivita))
		{
			pst.setString(++i, lineaAttivita);
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

	public SuapMasterListLineaAttivita getObject(ResultSet rs) throws SQLException {
		SuapMasterListLineaAttivita thisLineaAttivita = new SuapMasterListLineaAttivita(rs);
		return thisLineaAttivita;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAggregazione() {
		return idAggregazione;
	}

	public void setIdAggregazione(int idAggregazione) {
		this.idAggregazione = idAggregazione;
	}

	public String getCodiceProdottoSpecie() {
		return codiceProdottoSpecie;
	}

	public void setCodiceProdottoSpecie(String codiceProdottoSpecie) {
		this.codiceProdottoSpecie = codiceProdottoSpecie;
	}

	public String getLineaAttivita() {
		return lineaAttivita;
	}

	public void setLineaAttivita(String lineaAttivita) {
		this.lineaAttivita = lineaAttivita;
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
