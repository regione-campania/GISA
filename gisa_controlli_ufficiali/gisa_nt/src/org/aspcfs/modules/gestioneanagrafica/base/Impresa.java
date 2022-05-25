/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.aspcfs.utils.Bean;
import org.aspcfs.utils.DatabaseUtils;


public class Impresa 
{
	private Integer id;
	private String ragione_sociale;
	public String codfisc;
	private Timestamp data_inserimento;
	private Timestamp data_scadenza;
	private Timestamp data_cancellazione;
	private String piva;
	private String pec;
	private String note;
	private Indirizzo indirizzo = new Indirizzo();
	private SoggettoFisico soggettofisico = new SoggettoFisico();
	private int tipo_impresa;
	private Integer id_asl = null;
	private int alt_id;

	private ArrayList<Istanza> linee = new ArrayList<Istanza>();
	
	public int getTipo_impresa() {
		return tipo_impresa;
	}

	public void setTipo_impresa(int tipo_impresa) {
		this.tipo_impresa = tipo_impresa;
	}

	public Impresa() 
	{
	}
	

	    
	  public Impresa(Map<String, String[]> parameterMap) 
	   {
	        Bean.populate(this, parameterMap);
	   }
	
	public Impresa(Integer id) 
	{
		this.id=id;
	}
	
	public Impresa(String ragione_sociale, String codfisc, String piva, int tipo_impresa, String pec,
			String note) {
		// TODO Auto-generated constructor stub
		this.ragione_sociale=ragione_sociale;
		this.codfisc = codfisc;
		this.piva = piva;
		this.tipo_impresa = tipo_impresa;
		this.pec = pec;
		this.note=note;
				
	}
	
	  
	public Impresa(ResultSet rs) throws SQLException {
		// TODO Auto-generated constructor stub
		//buildRecord(rs);
	    Bean.populate(this, rs);		
	}

	public void buildRecord(ResultSet rs) throws SQLException
	{
		this.ragione_sociale = rs.getString("ragione_sociale");
		this.id = rs.getInt("id");
		this.codfisc = rs.getString("codfisc");
		this.piva = rs.getString("piva");
		this.pec = rs.getString("pec");
		this.note = rs.getString("note");
		try{ rs.findColumn("alt_id"); this.alt_id = DatabaseUtils.getInt(rs, "alt_id");	} catch (SQLException e) {}	
		}
	
	
	public String getRagione_sociale() {
		return ragione_sociale;
	}

	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public Timestamp getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(Timestamp data_scadenza) {
		this.data_scadenza = data_scadenza;
	}
	
	public String getCodfisc() {
		return codfisc;
	}
	
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	public Timestamp getData_cancellazione() {
		return data_cancellazione;
	}
	public void setData_cancellazione(Timestamp data_cancellazione) {
		this.data_cancellazione = data_cancellazione;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Timestamp getData_inserimento() {
		return data_inserimento;
	}
	public void setData_inserimento(Timestamp data_inserimento) {
		this.data_inserimento = data_inserimento;
	}
	
	public void setIndirizzo(Indirizzo ind){this.indirizzo = ind;}
	public Indirizzo getIndirizzo() {return this.indirizzo;}
	
	public void setSoggettofisico(SoggettoFisico sogg){this.soggettofisico = sogg;}
	public SoggettoFisico getSoggettofisico() {return this.soggettofisico;}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	public Impresa(Connection db, int id2) throws SQLException
	{
		PreparedStatement pst = db.prepareStatement("select * from anagrafica.imprese where id = "+id2);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		setIndirizzo(db);
		setSoggettoFisico (db);
		setLinee(db);
		}

	
	public Impresa(Connection db, int altId, boolean b) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from anagrafica.imprese where alt_id = "+altId);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		setIndirizzo(db);
		setSoggettoFisico(db);
		setLinee(db);
		}
	
	public Integer insert( Connection conn, Integer utente ) throws SQLException 
	{
		Integer id = null;
		ResultSet rs = null;
		ArrayList<Impresa> imprese = this.getItems(conn);
//		if (imprese.size()>0)
//			return imprese.get(0).getId();		
		String sql = "select * from anagrafica.anagrafica_inserisci_impresa( ?, ?, ?, ?, ?, ?,?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, this.getRagione_sociale()); 
		st.setString(2, this.getCodfisc());
		st.setString(3, this.getPiva());
		st.setObject(4, this.getTipo_impresa());
		st.setObject(5, utente);
		st.setString(6, this.getPec());
		st.setString(7, this.getNote());
		st.setObject(8, null);
		rs = st.executeQuery(); 
		if(rs.next())
			id=rs.getInt(1);
		return id;
	}
	
	public ArrayList<Impresa> getItems(Connection conn) throws SQLException 
	{

	  	String sql = "select id, ragione_sociale ,codfisc ,piva, id_tipo_impresa_societa as \"tipo_impresa.code\", pec, note from anagrafica.anagrafica_cerca_impresa(?,?,?,?,?,?,?,?) "  ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, this.getId());
		st.setString(2, this.getRagione_sociale());
		st.setString(3, this.getCodfisc());
		st.setString(4, this.getPiva());
		st.setObject(5, this.getTipo_impresa());
		st.setTimestamp(6, null);
		st.setTimestamp(7, null);
		st.setString(8, this.getPec());
		
		ResultSet rs = st.executeQuery();
		ArrayList<Impresa> imprese = new ArrayList<Impresa>();
		
		while(rs.next())
		{
			imprese.add(new Impresa(rs));
		}
		
		return imprese;
	}

	public void setIndirizzo(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select ind.* from anagrafica.imprese i left join anagrafica.rel_imprese_indirizzi rel on rel.id_impresa = i.id left join anagrafica.indirizzi ind on ind.id = rel.id_indirizzo where i.id = "+this.id+" and rel.data_scadenza is null and rel.data_cancellazione is null");
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			Indirizzo ind = new Indirizzo(rs);
			setIndirizzo(ind);
		}
	}

	public void setSoggettoFisico(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select sogg.* from anagrafica.imprese i left join anagrafica.rel_imprese_soggetti_fisici rel on rel.id_impresa = i.id left join anagrafica.soggetti_fisici sogg on sogg.id = rel.id_soggetto_fisico where i.id = "+this.id+" and rel.data_scadenza is null and rel.data_cancellazione is null");
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			SoggettoFisico sogg = new SoggettoFisico(rs);
			sogg.setIndirizzo(db);
			setSoggettofisico(sogg);
		}
	}

	private void setLinee(Connection db) throws SQLException {

		PreparedStatement pst = db.prepareStatement("select * from anagrafica.rel_imprese_mobili_linee_attivita where id_impresa= ? and data_scadenza is null");
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Istanza linea = new Istanza();
			linea.buildRecord(rs);
			linea.setPathCompleto(db);
			linee.add(linea);
		}
		
	}
	public Integer getId_asl() {
		return id_asl;
	}

	public void setId_asl(Integer id_asl) {
		this.id_asl = id_asl;
	}

	public int getAlt_id() {
		return alt_id;
	}

	public void setAlt_id(int alt_id) {
		this.alt_id = alt_id;
	}
	
}
