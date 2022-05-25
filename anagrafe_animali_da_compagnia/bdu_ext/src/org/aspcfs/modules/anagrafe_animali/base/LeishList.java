/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.PagedListInfo;


public class LeishList extends ArrayList {

	private PagedListInfo pagedListInfo = null;
	private int       idAnimale ;
	private String    identificativo ;
	private Timestamp dataInizioEsito = null;
	private Timestamp dataFineEsito = null;
	private int       idAsl = -1;
	private String    esito = "";
	private int idEsito = -1;
	private int idUtenteInserimento = -1;
	
	


	
	public int getIdAnimale() {
		return idAnimale;
	}
	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public void setIdAsl(String idAsl) {
		this.idAsl = Integer.parseInt(idAsl);
	}
	public Timestamp getDataInizioEsito() {
		return dataInizioEsito;
	}
	public void setDataInizioEsito(Timestamp dataInizioEsito) {
		this.dataInizioEsito = dataInizioEsito;
	}
	
	public void setDataInizioEsito(String eventoa) {
		this.dataInizioEsito = DateUtils.parseDateStringNew(eventoa, "dd/MM/yyyy");
	}
	
	public Timestamp getDataFineEsito() {
		return dataFineEsito;
	}
	public void setDataFineEsito(Timestamp dataFineEsito) {
		this.dataFineEsito = dataFineEsito;
	}
	
	public void setDataFineEsito(String eventoa) {
		this.dataFineEsito = DateUtils.parseDateStringNew(eventoa, "dd/MM/yyyy");
	}
	
	
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	
	
	public int getIdEsito() {
		return idEsito;
	}
	
	public void setIdEsito(int idEsito) {
		this.idEsito = idEsito;
		switch (this.idEsito) {
		case 1: //POSITIVO
			this.setEsito("P");
			break;
			
		case 2: //NEGATIVO
			this.setEsito("N");
			break;
			
		case 3: //DUBBIO
			this.setEsito("D");
			break;

		default:
			break;
		}
	}
	
	public void setIdEsito(String idEsito) {
		this.idEsito = Integer.parseInt(idEsito);
		
		switch (this.idEsito) {
		case 1: //POSITIVO
			this.setEsito("P");
			break;
			
		case 2: //NEGETIVO
			this.setEsito("N");
			break;
			
		case 3: //DUBBIO
			this.setEsito("D");
			break;
			
		case 4: //ALTRI ESITI
			this.setEsito("ALTRI");

		default:
			break;
		}
	}
	
	
	
	
	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}
	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}
	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);
		boolean add = true;
		while (rs.next()) {
			
		/*** ELIMINO QUESTO CONTROLLO COME RICHIESTO DA VERBALE 15/07/14 ***/
//	    if (idUtenteInserimento > 0){ /**
//	     SE HO UTENTE VALORIZZATO, SI TRATTA DI lp ED ESTRAGGO SOLO GLI ESITI SUCCESSIVI AL PRIMO PRELIEVO
//	     **/
//			java.sql.Timestamp leishPrimoUtente =  EventoPrelievoLeishmania.getDataPrimoPrelievoUtente(db, rs.getString("identificativo"), idUtenteInserimento);
//			if (rs.getTimestamp("data_esito").before(leishPrimoUtente)){
//				add = false;
//			}
//	    }
	//    if (add){
			Leish leish = this.getObject(rs);
			leish.setAnimale(new Animale(db, leish.getIdentificativo()));
			this.add(leish);
	//    }
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}

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
				"SELECT COUNT(*) AS recordcount " +
		"FROM esiti_leishmaniosi el  left join animale a on (a.id = el.id_animale) where 1=1 " );

		createFilter(sqlFilter, db);
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			pagedListInfo.setDefaultSort("el.data_esito","desc");
			pagedListInfo.appendSqlTail(db, sqlOrder);
		}
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {  
			sqlSelect.append("SELECT "); 
		}

		sqlSelect.append(
				" id_animale,identificativo,data_accertamento,data_prelievo,data_esito,esito_car,esito,data_aggiornamento" +
				" FROM esiti_leishmaniosi el left join animale a on (a.id = el.id_animale) " +
				" WHERE id_animale > -1 ");

		pst = db.prepareStatement( sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());

		items = prepareFilter(pst);
		if (pagedListInfo != null) 
		{
			pagedListInfo.doManualOffset(db, pst);
		}
		rs = pst.executeQuery();

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	public Leish getObject(ResultSet rs) throws SQLException {
		Leish thisAsset = new Leish(rs);
		return thisAsset;
	}

	private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}
		if ( idAnimale != 0 ) {
			sqlFilter.append(" AND id_animale = ? ");
		}
		if ( identificativo != null && ! identificativo.equals("") ) {
			sqlFilter.append(" AND identificativo = ? ");
		}
		
		if (dataInizioEsito != null && dataFineEsito != null) {
			sqlFilter
					.append("AND el.data_esito >= ? and el.data_esito <= ? ");
		}
		
		if (idAsl > 0){
			sqlFilter
			.append("AND a.id_asl_riferimento = ? ");
		}
		
		if (esito != null && !("").equals(esito) && !("ALTRI").equals(esito)){
			sqlFilter
			.append("AND el.esito = ? ");
		}else if (("ALTRI").equals(esito)){
			sqlFilter
			.append("AND (el.esito is null  || el.esito = '' ) ");
		}
		
		if (idUtenteInserimento > 0){
			sqlFilter.append("and identificativo IN ( select microchip from evento " +
					"where id_utente_inserimento = ? and  id_tipologia_evento = 54)");
		}
		
		
		
	}

	private int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;

		if ( idAnimale != 0 ) {
			pst.setInt(++i, idAnimale)	;
		}

		if ( identificativo != null && ! identificativo.equals("") ) {
			pst.setString(++i, "%"+identificativo.toLowerCase().trim()+"%");
		}
		
		if (dataInizioEsito != null && dataFineEsito != null) {
			pst.setTimestamp(++i, dataInizioEsito);
			pst.setTimestamp(++i, dataFineEsito);
		}
		
		if (idAsl > 0){
			pst.setInt(++i, idAsl);
		}
		if (esito != null && !("").equals(esito) && !("ALTRI").equals(esito)){
			pst.setString(++i, esito);
		}
		
		
		if (idUtenteInserimento > 0){
			pst.setInt(++i, idUtenteInserimento);
		}
		
		
		return i;
	}

}
