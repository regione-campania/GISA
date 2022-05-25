/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RiepilogoImport {

	private int id = -1;
	private java.sql.Timestamp dataOp = null;
	private String riepilogo = null;
	private String errori = null;
	private String okLog = null;
	private String koLog = null;
	private String recordInseriti = null;
	private int id_invio_massivo = -1;

	public int getId_invio_massivo() {
		return id_invio_massivo;
	}

	public void setId_invio_massivo(int id_invio_massivo) {
		this.id_invio_massivo = id_invio_massivo;
	}

	public String getCodice_azienda() {
		return codice_azienda;
	}

	public void setCodice_azienda(String codice_azienda) {
		this.codice_azienda = codice_azienda;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getTipo_invio() {
		return tipo_invio;
	}

	public void setTipo_invio(String tipo_invio) {
		this.tipo_invio = tipo_invio;
	}

	public int getId_controllo() {
		return id_controllo;
	}

	public void setId_controllo(int id_controllo) {
		this.id_controllo = id_controllo;
	}

	public java.sql.Timestamp getData_controllo() {
		return data_controllo;
	}

	public void setData_controllo(java.sql.Timestamp data_controllo) {
		this.data_controllo = data_controllo;
	}
	private String codice_azienda = null;
	private String specie = null;
	private String esito = null;
	private String tipo_invio = null;
	private int id_controllo = -1;
	private String inviato_da = "";
    private java.sql.Timestamp data_controllo = null;
    private java.sql.Timestamp data_invio = null;
    private java.sql.Timestamp data_esito = null;
	
	private ArrayList<RiepilogoImport> allRecord = null;

	public RiepilogoImport( String data, String path ) {
		setDataOp(data);
		this.errori = path+"_err.txt";
		this.riepilogo = path+"_rpg.txt";
		this.recordInseriti = path+"_ok.txt";
		
	}
	
	
	public RiepilogoImport( String data ) {
		setDataOp(data);
		
		
	}
	
	public RiepilogoImport( String data, String path,boolean soloErrori ) {
		setDataOp(data);
		this.errori = path+".txt";
		
		
	}
	
	public java.sql.Timestamp getData_invio() {
		return data_invio;
	}

	public void setData_invio(java.sql.Timestamp data_invio) {
		this.data_invio = data_invio;
	}

	public java.sql.Timestamp getData_esito() {
		return data_esito;
	}

	public void setData_esito(java.sql.Timestamp data_esito) {
		this.data_esito = data_esito;
	}

	public String getOkLog() {
		return okLog;
	}

	public void setOkLog(String okLog) {
		this.okLog = okLog;
	}

	public String getKoLog() {
		return koLog;
	}

	public void setKoLog(String koLog) {
		this.koLog = koLog;
	}

	public RiepilogoImport() {}
    

	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecord(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	public void buildListCapi(Connection db) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListCapi(db, pst);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecord(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListStabilimenti(Connection db) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListStabilimenti(db, pst);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordStabilimenti(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListBA(Connection db, String data) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListBA(db, pst, data);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordBA(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListB11(Connection db, String data) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListB11(db, pst, data);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordB11(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListSin(Connection db) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListSin(db, pst);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordBASin(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListOsm(Connection db) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListOsm(db, pst);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordOsm(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	/*LISTA SOA*/
	public void buildListSoa(Connection db) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListSoa(db, pst);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordSoa(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListDistributori(Connection db,int orgid) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListDistributori(db, pst,orgid);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordDistributori(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	
	public void buildListVeicoli(Connection db,int orgid) throws SQLException {
		PreparedStatement pst = null;
		this.allRecord = new ArrayList<RiepilogoImport>();
		ResultSet rs = queryListVeicoli(db, pst,orgid);
		while (rs.next()) {
			RiepilogoImport thisRiepilogo = buildRecordVeicoli(rs);
			this.allRecord.add(thisRiepilogo);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
	}
	public boolean insertVeicoli(Connection db,int org_id) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_trasporto " +
				"( data," +
				"  riepilogo," +
				"  errori, " +
				"  record_inseriti, enabled,orgid ) " +
				"  VALUES ( ?, ?, ?, ?, ?,? );" );

		int i = 0;
		DatabaseUtils.setTimestamp(pst, ++i, dataOp);
		pst.setString(++i, riepilogo);
		pst.setString(++i, errori);
		pst.setString(++i, recordInseriti);
		pst.setBoolean(++i, true);
		pst.setInt(++i, org_id);

		pst.execute();
		pst.close();

		return true;
	}

	public boolean insert(Connection db) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_capi " +
				"( data," +
				"  riepilogo," +
				"  errori, " +
				"  record_inseriti, enabled ) " +
				"  VALUES ( ?, ?, ?, ?, ? );" );

		int i = 0;
		DatabaseUtils.setTimestamp(pst, ++i, dataOp);
		pst.setString(++i, riepilogo);
		pst.setString(++i, errori);
		pst.setString(++i, recordInseriti);
		pst.setBoolean(++i, true);

		pst.execute();
		pst.close();

		return true;
	}

	public boolean insertStabilimento(Connection db) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_stabilimenti " +
				"( data," +
				"  riepilogo," +
				"  errori, " +
				"  record_inseriti, enabled ) " +
				"  VALUES ( ?, ?, ?, ?, ? );" );

		int i = 0;
		DatabaseUtils.setTimestamp(pst, ++i, dataOp);
		pst.setString(++i, riepilogo);
		pst.setString(++i, errori);
		pst.setString(++i, recordInseriti);
		pst.setBoolean(++i, true);

		pst.execute();
		pst.close();

		return true;
	}
	
	public boolean insertBenessereAnimale(Connection db) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_ba " +
				"( data_estrazione," +
				"  tipo_invio, " +
				"  inviato_da, id_controllo, codice_azienda, specie, data_controllo, data_invio, data_esito, id_invio_massivo ) " +
				"  VALUES ( ?, ?, ?, ?,?,?,?, ?, ?,?);" );

		int i = 0;
		
		pst.setTimestamp(++i, this.getDataOp());
		pst.setString(++i, tipo_invio);
		pst.setString(++i, inviato_da);
		pst.setInt(++i, id_controllo);
		pst.setString(++i, codice_azienda);
		pst.setString(++i, specie);
		pst.setTimestamp(++i, data_controllo);
		pst.setTimestamp(++i, data_invio);
		pst.setTimestamp(++i, data_esito);
		pst.setInt(++i, id_invio_massivo);
		
		pst.execute();
		pst.close();

		return true;
	}
	
	public boolean insertB11(Connection db) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_b11 " +
				"( data_estrazione," +
				"  tipo_invio, " +
				"  inviato_da, id_controllo, codice_azienda, specie, data_controllo, data_invio, data_esito, id_invio_massivo ) " +
				"  VALUES ( ?, ?, ?, ?,?,?,?, ?, ?,?) returning id as id_inserito;" );

		int i = 0;
		
		pst.setTimestamp(++i, this.getDataOp());
		pst.setString(++i, tipo_invio);
		pst.setString(++i, inviato_da);
		pst.setInt(++i, id_controllo);
		pst.setString(++i, codice_azienda);
		pst.setString(++i, specie);
		pst.setTimestamp(++i, data_controllo);
		pst.setTimestamp(++i, data_invio);
		pst.setTimestamp(++i, data_esito);
		pst.setInt(++i, id_invio_massivo);
		
		ResultSet rsInsert = pst.executeQuery();
		if (rsInsert.next())
			id = rsInsert.getInt("id_inserito");
		pst.close();
	
		return true;
	}
	
	
	public String getInviato_da() {
		return inviato_da;
	}

	public void setInviato_da(String inviato_da) {
		this.inviato_da = inviato_da;
	}

	public boolean insertSin(Connection db) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_sin " +
				"( data," +
				"  errori, " +
				"ok_log," +
				"ko_log," +
				"  enabled ) " +
				"  VALUES ( ?, ?, ?,?,? );" );

		int i = 0;
		DatabaseUtils.setTimestamp(pst, ++i, dataOp);
		pst.setString(++i, errori);
		pst.setString(++i, okLog);
		pst.setString(++i, koLog);

		pst.setBoolean(++i, true);

		pst.execute();
		pst.close();

		return true;
	}
	
	/*Insert per il modulo SOA*/
	public boolean insertSoa(Connection db) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_soa " +
				"( data," +
				"  riepilogo," +
				"  errori, " +
				"  record_inseriti, enabled ) " +
				"  VALUES ( ?, ?, ?, ?, ? );" );

		int i = 0;
		DatabaseUtils.setTimestamp(pst, ++i, dataOp);
		pst.setString(++i, riepilogo);
		pst.setString(++i, errori);
		pst.setString(++i, recordInseriti);
		pst.setBoolean(++i, true);

		pst.execute();
		pst.close();

		return true;
	}
	
	
	public boolean insertDistributore(Connection db,int org_id) throws SQLException {
		PreparedStatement pst = null;
	
		pst = db.prepareStatement(
				"INSERT INTO import_distributori " +
				"( data," +
				"  riepilogo," +
				"  errori, " +
				"  record_inseriti, enabled,orgid ) " +
				"  VALUES ( ?, ?, ?, ?, ?,? );" );

		int i = 0;
		DatabaseUtils.setTimestamp(pst, ++i, dataOp);
		pst.setString(++i, riepilogo);
		pst.setString(++i, errori);
		pst.setString(++i, recordInseriti);
		pst.setBoolean(++i, true);
		pst.setInt(++i, org_id);

		pst.execute();
		pst.close();

		return true;
	}
	
	
	
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_allevamenti WHERE id > -1 AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	public ResultSet queryListCapi(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_capi WHERE id > -1 AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	public ResultSet queryListVeicoli(Connection db, PreparedStatement pst,int orgid) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_trasporto WHERE id > -1 AND orgid="+orgid+" AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	public ResultSet queryListStabilimenti(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_stabilimenti WHERE id > -1 AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	public ResultSet queryListBA(Connection db, PreparedStatement pst, String data) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		//sqlSelect.append("SELECT * FROM import_ba WHERE id > -1 and to_char(data_estrazione,'dd-mm-yyyy') = '"+data+"'ORDER BY data_estrazione DESC");
		
		sqlSelect.append(
				"select * from invio_massivo_ba m join import_ba b on b.id_invio_massivo = m.id where enabled and  to_char(data_estrazione,'dd-mm-yyyy') =  '"+data+
				"' and b.id_invio_massivo in(select max(id) from invio_massivo_ba where enabled and to_char(data,'dd-mm-yyyy') =  '"+data+
				"')");
		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	public ResultSet queryListB11(Connection db, PreparedStatement pst, String data) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		//sqlSelect.append("SELECT * FROM import_ba WHERE id > -1 and to_char(data_estrazione,'dd-mm-yyyy') = '"+data+"'ORDER BY data_estrazione DESC");
		
		sqlSelect.append(
				"select * from invio_massivo_b11 m join import_b11 b on b.id_invio_massivo = m.id and  to_char(data_estrazione,'dd-mm-yyyy') =  '"+data+
				"' and b.id_invio_massivo in(select max(id) from invio_massivo_b11 where to_char(data,'dd-mm-yyyy') =  '"+data+
				"')");
		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	public ResultSet queryListSin(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_sin WHERE id > -1 AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	public ResultSet queryListOsm(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_osm WHERE id > -1 AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	public ResultSet queryListSoa(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_soa WHERE id > -1 AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	
	public ResultSet queryListDistributori(Connection db, PreparedStatement pst,int orgid) throws SQLException {
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		
		sqlSelect.append("SELECT * FROM import_distributori WHERE id > -1 AND orgid="+orgid+" AND enabled = true ORDER BY data DESC"); 

		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		
		return rs;
	}
	
	private RiepilogoImport buildRecord(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp( rs.getTimestamp("data") );
		ri.setRiepilogo( rs.getString("riepilogo") );
		ri.setErrori( rs.getString("errori") );
		ri.setRecordInseriti( rs.getString("record_inseriti") );
		ri.setId( rs.getInt("id") );
	
		return ri;
	}
	
	private RiepilogoImport buildRecordVeicoli(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp( rs.getTimestamp("data") );
		ri.setRiepilogo( rs.getString("riepilogo") );
		ri.setErrori( rs.getString("errori") );
		ri.setRecordInseriti( rs.getString("record_inseriti") );
		ri.setId( rs.getInt("id") );
		
		return ri;
	}
	
	private RiepilogoImport buildRecordStabilimenti(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp( rs.getTimestamp("data") );
		ri.setRiepilogo( rs.getString("riepilogo") );
		ri.setErrori( rs.getString("errori") );
		ri.setRecordInseriti( rs.getString("record_inseriti") );
		ri.setId( rs.getInt("id") );
		
		return ri;
	}
	
	private RiepilogoImport buildRecordBASin(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp(rs.getTimestamp("data_estrazione") );
		//ri.setErrori( rs.getString("errori") );
		ri.setId( rs.getInt("id"));
		ri.setKoLog(rs.getString("ko_log"));
		ri.setCodice_azienda(rs.getString("codice_azienda"));
		ri.setSpecie(rs.getString("specie"));
		ri.setEsito(rs.getString("esito"));
		ri.setId_controllo(rs.getInt("id_controllo"));
		ri.setTipo_invio(rs.getString("tipo_invio"));
		ri.setInviato_da(rs.getString("inviato_da"));
		ri.setData_controllo(rs.getTimestamp("data_controllo"));
		//ri.setKoLog(rs.getString("ko_log"));
		
		return ri;
	}
	
	private RiepilogoImport buildRecordBA(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp(rs.getTimestamp("data_estrazione") );
		//ri.setErrori( rs.getString("errori") );
		ri.setId( rs.getInt("id"));
		ri.setKoLog(rs.getString("ko_log"));
		ri.setCodice_azienda(rs.getString("codice_azienda"));
		ri.setSpecie(rs.getString("specie"));
		ri.setEsito(rs.getString("esito"));
		ri.setId_controllo(rs.getInt("id_controllo"));
		ri.setTipo_invio(rs.getString("tipo_invio"));
		ri.setInviato_da(rs.getString("inviato_da"));
		ri.setData_controllo(rs.getTimestamp("data_controllo"));
		//ri.setKoLog(rs.getString("ko_log"));
		
		return ri;
	}
	private RiepilogoImport buildRecordB11 (ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp(rs.getTimestamp("data_estrazione") );
		//ri.setErrori( rs.getString("errori") );
		ri.setId( rs.getInt("id"));
		ri.setKoLog(rs.getString("ko_log"));
		ri.setCodice_azienda(rs.getString("codice_azienda"));
		ri.setSpecie(rs.getString("specie"));
		ri.setEsito(rs.getString("esito"));
		ri.setId_controllo(rs.getInt("id_controllo"));
		ri.setTipo_invio(rs.getString("tipo_invio"));
		ri.setInviato_da(rs.getString("inviato_da"));
		ri.setData_controllo(rs.getTimestamp("data_controllo"));
		//ri.setKoLog(rs.getString("ko_log"));
		
		return ri;
	}
	
	private RiepilogoImport buildRecordOsm(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp( rs.getTimestamp("data") );
		ri.setRiepilogo( rs.getString("riepilogo") );
		ri.setErrori( rs.getString("errori") );
		ri.setRecordInseriti( rs.getString("record_inseriti") );
		ri.setId( rs.getInt("id") );
		
		return ri;
	}
	
	private RiepilogoImport buildRecordSoa(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp( rs.getTimestamp("data") );
		ri.setRiepilogo( rs.getString("riepilogo") );
		ri.setErrori( rs.getString("errori") );
		ri.setRecordInseriti( rs.getString("record_inseriti") );
		ri.setId( rs.getInt("id") );
		
		return ri;
	}
	private RiepilogoImport buildRecordDistributori(ResultSet rs) throws SQLException {
		RiepilogoImport ri = new RiepilogoImport();
	    ri.setDataOp( rs.getTimestamp("data") );
		ri.setRiepilogo( rs.getString("riepilogo") );
		ri.setErrori( rs.getString("errori") );
		ri.setRecordInseriti( rs.getString("record_inseriti") );
		ri.setId( rs.getInt("id") );
		
		return ri;
	}

	public int[] getTotaliBA(Connection db) throws SQLException {
		int[] totaliBA = {0,0};
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		PreparedStatement pst = null;
		sqlSelect.append("select count(aa.esito), aa.esito from (select count(esito), esito, id_controllo from import_ba where enabled and date_part('year', data_estrazione) =  date_part('year', now()) group by esito, id_controllo) aa  group by aa.esito"); 
		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		while (rs.next()){
			if (rs.getString("esito")!=null){
			if (rs.getString("esito").equals("OK"))
				totaliBA[0]= rs.getInt("count");
			else if (rs.getString("esito").equals("KO"))
				totaliBA[1]= rs.getInt("count");
			}
		}
		return totaliBA;
	}
	
	public int[] getTotaliB11(Connection db) throws SQLException {
		int[] totaliBA = {0,0};
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		PreparedStatement pst = null;
		sqlSelect.append("select count(aa.esito), aa.esito from (select count(esito), esito, id_controllo from import_b11 where date_part('year', data_estrazione) =  date_part('year', now()) group by esito, id_controllo) aa  group by aa.esito"); 
		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		while (rs.next()){
			if (rs.getString("esito")!=null){
			if (rs.getString("esito").equals("OK"))
				totaliBA[0]= rs.getInt("count");
			else if (rs.getString("esito").equals("KO"))
				totaliBA[1]= rs.getInt("count");
			}
		}
		return totaliBA;
	}
	
	public int[] getTotaliBA_2014(Connection db) throws SQLException {
		int[] totaliBA = {0,0};
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		PreparedStatement pst = null;
		sqlSelect.append("select count(aa.esito), aa.esito from (select count(esito), esito, id_controllo from import_ba where enabled and date_part('year', data_estrazione) = 2014 group by esito, id_controllo) aa  group by aa.esito"); 
		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		while (rs.next()){
			if (rs.getString("esito")!=null){
			if (rs.getString("esito").equals("OK"))
				totaliBA[0]= rs.getInt("count");
			else if (rs.getString("esito").equals("KO"))
				totaliBA[1]= rs.getInt("count");
			}
		}
		return totaliBA;
	}
	
	public int[] getTotaliB11_2014(Connection db) throws SQLException {
		int[] totaliBA = {0,0};
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		PreparedStatement pst = null;
		sqlSelect.append("select count(aa.esito), aa.esito from (select count(esito), esito, id_controllo from import_b11 where date_part('year', data_estrazione) =  2014 group by esito, id_controllo) aa  group by aa.esito"); 
		pst = db.prepareStatement( sqlSelect.toString() );
		rs = pst.executeQuery();
		while (rs.next()){
			if (rs.getString("esito")!=null){
			if (rs.getString("esito").equals("OK"))
				totaliBA[0]= rs.getInt("count");
			else if (rs.getString("esito").equals("KO"))
				totaliBA[1]= rs.getInt("count");
			}
		}
		return totaliBA;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.sql.Timestamp getDataOp() {
		return dataOp;
	}
	public void setDataOp(java.sql.Timestamp dataOp) {
		this.dataOp = dataOp;
	}
	public void setDataOp(String dataOp) {
		this.dataOp = DatabaseUtils.parseDateToTimestamp( dataOp );
	}
	public String getRiepilogo() {
		return riepilogo;
	}
	public void setRiepilogo(String riepilogo) {
		this.riepilogo = riepilogo;
	}
	public String getErrori() {
		return errori;
	}
	public void setErrori(String errori) {
		this.errori = errori;
	}
	public String getRecordInseriti() {
		return recordInseriti;
	}
	public void setRecordInseriti(String recordInseriti) {
		this.recordInseriti = recordInseriti;
	}
	public ArrayList<RiepilogoImport> getAllRecord(){
		return this.allRecord;
	}
	
}
