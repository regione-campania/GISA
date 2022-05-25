/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.log.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.utils.DatabaseUtils;

public class LogBeanList extends Vector<LogBean>  {

	private static final long serialVersionUID = 1L;

	
	private int asl           = -1;
	private int userId        = -1;
	private int operazioneId  = -1;
	private Timestamp dataDa  = null;
	private Timestamp dataA   = null;

	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);

		while (rs.next()) {
			LogBean thisLog = this.getObject(rs);
			this.add(thisLog);
		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		sqlCount.append(
				"SELECT COUNT(*) AS recordcount " +
				"FROM log l " +
				"WHERE l.id >= 0 ");

		createFilter(db, sqlFilter);

		sqlOrder.append("ORDER BY l.data_operazione DESC ");

		sqlSelect.append
		(
				"SELECT " +
				"l.*, " +
				"lar.description as asl, " +
				"a.username as user, " +
				"lop.description as operazione " +
				"FROM log l " +
				"LEFT JOIN access a ON (l.user_id = a.user_id) " +
				"LEFT JOIN lookup_asl_rif lar ON (l.asl_id = lar.code) " +
				"LEFT JOIN lookup_log_op lop ON (l.operazione_id = lop.code) " +
				"WHERE l.id >= 0"
		);


		pst = db.prepareStatement( sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString() );

		prepareFilter(pst);

		rs = DatabaseUtils.executeQuery(db, pst);

		return rs;
	}

	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if ( asl > -1 ){
			sqlFilter.append("AND l.asl_id = ? ");
		}

		if ( userId > -1 ){
			sqlFilter.append("AND l.user_id = ? ");
		}

		if ( operazioneId > -1 ){
			sqlFilter.append("AND l.operazione_id = ? ");
		}

		if ( dataDa != null ){
			sqlFilter.append("AND l.data_operazione >= ? ");
		}

		if ( dataA != null ){
			sqlFilter.append("AND l.data_operazione <= ? ");
		}
	}

	protected void prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;

		if ( asl > -1 ){
			pst.setInt(++i, asl);
		}

		if ( userId > -1 ){
			pst.setInt(++i, userId);
		}

		if ( operazioneId > -1 ){
			pst.setInt(++i, operazioneId);
		}

		if ( dataDa != null ){
			pst.setTimestamp(++i, dataDa);
		}

		if ( dataA != null ){
			pst.setTimestamp(++i, dataA);
		}			

	}

	public LogBean getObject(ResultSet rs) throws SQLException {
		LogBean thisLog = new LogBean(rs);
		return thisLog;
	}

	public int getAsl() {
		return asl;
	}

	public void setAsl(int asl) {
		this.asl = asl;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOperazioneId() {
		return operazioneId;
	}

	public void setOperazioneId(int operazioneId) {
		this.operazioneId = operazioneId;
	}

	public Timestamp getDataDa() {
		return dataDa;
	}

	public void setDataDa(Timestamp dataDa) {
		this.dataDa = dataDa;
	}

	public void setDataDa(String dataDa) {
		this.dataDa = DatabaseUtils.parseDateToTimestamp(dataDa);
	}

	public Timestamp getDataA() {
		return dataA;
	}

	public void setDataA(Timestamp dataA) {
		this.dataA = dataA;
	}

	public void setDataA(String dataA) {
		this.dataA = DatabaseUtils.parseDateToTimestamp(dataA);
	}

}
