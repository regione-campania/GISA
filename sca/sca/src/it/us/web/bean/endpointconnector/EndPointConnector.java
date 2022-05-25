/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.endpointconnector;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class EndPointConnector {

	private static final long serialVersionUID = 2L;
	
	private int id;
	private int idOperazione;
	private int idEndPoint;
	private String sql;
	private String urlReloadUtenti;
	private EndPoint endPoint;
	private Operazione operazione;
	
	
	public EndPointConnector() {
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public int getIdOperazione() {
		return idOperazione;
	}


	public void setIdOperazione(int idOperazione) {
		this.idOperazione = idOperazione;
	}


	public int getIdEndPoint() {
		return idEndPoint;
	}


	public void setIdEndPoint(int idEndPoint) {
		this.idEndPoint = idEndPoint;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public String getUrlReloadUtenti() {
		return urlReloadUtenti;
	}


	public void setUrlReloadUtenti(String urlReloadUtenti) {
		this.urlReloadUtenti = urlReloadUtenti;
	}


	public EndPoint getEndPoint() {
		return endPoint;
	}


	public void setEndPoint(EndPoint endPoint) {
		this.endPoint = endPoint;
	}


	public Operazione getOperazione() {
		return operazione;
	}


	public void setOperazione(Operazione operazione) {
		this.operazione = operazione;
	}

	public EndPointConnector(Connection db, ResultSet rs) throws SQLException {
		buildRecord(db, rs);
	}
	
	
	private void buildRecord( Connection db, ResultSet rs) throws SQLException {

		this.id =  rs.getInt("id");
		this.idOperazione =  rs.getInt("id_operazione");
		this.idEndPoint =  rs.getInt("id_endpoint");
		this.sql = rs.getString("sql");
		this.urlReloadUtenti =  rs.getString("url_reload_utenti");
		buildEndPoint(db);
		buildOperazione(db);
	}
	
	private void buildEndPoint(Connection db){
		EndPoint ep = new EndPoint(db, this.getIdEndPoint());
		this.setEndPoint(ep);
	}
	private void buildOperazione(Connection db){
		Operazione op = new Operazione(db, this.getIdOperazione());
		this.setOperazione(op);
	}
	
public void getByEndPointOperazione(Connection db, int idEndPoint, int idOperazione){
	String sql = "select * from guc_endpoint_connector_config where id_endpoint = ? and id_operazione = ?";
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql);
	
	pst.setInt(1, idEndPoint);
	pst.setInt(2,  idOperazione);
	
	ResultSet rs = pst.executeQuery();
	
	if (rs.next()){
		buildRecord(db, rs);	
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}

