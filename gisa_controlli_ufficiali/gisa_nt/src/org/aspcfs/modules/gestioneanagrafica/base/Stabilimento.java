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

import com.darkhorseventures.framework.beans.GenericBean;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;


public class Stabilimento extends GenericBean
{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
	private String denominazione;
	private Timestamp data_scadenza;
	private Timestamp data_cancellazione;
	private Integer id_stato;
	private String stato; /*desc di id stato da lookup_stati */
	private Timestamp data_inserimento;
	private Indirizzo indirizzo = new Indirizzo();
	private Impresa impresa = new Impresa();
	public Integer n_linea;
	public Integer categoria_rischio;
	public String note;
	private Timestamp data_prossimo_controllo;
	private int idAsl;
	private int alt_id;
	private String numero_registrazione;
	
	private ArrayList<Istanza> linee = new ArrayList<Istanza>();
	

	public Stabilimento() 
	{
	}
	
	
	public Stabilimento(Map<String, String[]> parameterMap )
	{
	    Bean.populate(this, parameterMap);
	}
	
	public Stabilimento(Integer id)
	{
		this.id=id;
	}


	public Stabilimento(String stato, String categoria_rischio2, String data_prossimo_controllo2,
			String data_inizio_attivita, String data_fine_attivita, String note, String idAsl) {
		// TODO Auto-generated constructor stub
		this.note = note;
		this.categoria_rischio = categoria_rischio;
		this.data_prossimo_controllo = data_prossimo_controllo;
		this.stato = stato;
		this.setIdAsl(Integer.parseInt(idAsl));
		
	}

	public Stabilimento(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	public void buildRecord(ResultSet rs) throws SQLException {
		// TODO Auto-generated constructor stub
		this.note=rs.getString("note");
		this.id_stato = rs.getInt("id_stato");
		this.idAsl = rs.getInt("id_asl");
		this.categoria_rischio = rs.getInt("categoria_rischio");
		this.data_prossimo_controllo = rs.getTimestamp("data_prossimo_controllo");
		this.id = rs.getInt("id");
		this.alt_id =rs.getInt("alt_id");
		this.numero_registrazione=rs.getString("numero_registrazione");
	}

	public Stabilimento(Connection db, int id2) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from anagrafica.stabilimenti where id = "+id2);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		setIndirizzo(db);
		setImpresa (db);
		setLinee(db);
		}

	private void setLinee(Connection db) throws SQLException {

		PreparedStatement pst = db.prepareStatement("select * from anagrafica.rel_stabilimenti_linee_attivita where id_stabilimento = ? and data_scadenza is null");
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Istanza linea = new Istanza();
			linea.buildRecord(rs);
			linea.setPathCompleto(db);
			linee.add(linea);
		}
		
			
	}

	private void setImpresa(Connection db) throws SQLException {
	
		PreparedStatement pst = db.prepareStatement("select i.* from anagrafica.stabilimenti s left join anagrafica.rel_imprese_stabilimenti rel on rel.id_stabilimento = s.id left join anagrafica.imprese i on i.id = rel.id_impresa where s.id = "+this.id+" and rel.data_scadenza is null and rel.data_cancellazione is null");
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			Impresa imp = new Impresa(rs);
			imp.setIndirizzo(db);
			imp.setSoggettoFisico(db);
			setImpresa(imp);
		}
	
		
	}
	
	
/*	public void GetLineeProd(Connection db) throws SQLException
	{
	    PreparedStatement pst = db.prepareStatement("select codice_attivita,path_descrizione from ml8_linee_attivita_nuove_materializzata");
        ResultSet rs = pst.executeQuery();
        while (rs.next()){
           // lineAttivita
            
        }
        
	}*/

	public Stabilimento(Connection db, int altId, boolean b) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from anagrafica.stabilimenti where alt_id = "+altId);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		setIndirizzo(db);
		setImpresa (db);
		setLinee(db);
		}

	private void setIndirizzo(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select ind.* from anagrafica.stabilimenti s left join anagrafica.rel_stabilimenti_indirizzi rel on rel.id_stabilimento = s.id left join anagrafica.indirizzi ind on ind.id = rel.id_indirizzo where s.id = "+this.id+" and rel.data_scadenza is null and rel.data_cancellazione is null");
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			Indirizzo ind = new Indirizzo(rs);
			setIndirizzo(ind);
		}
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDenominazione() 
	{
		return denominazione;
	}
	public void setDenominazione(String denominazione) 
	{
		this.denominazione = denominazione;
	}
	public Timestamp getData_scadenza() {
		return data_scadenza;
	}
	public void setDat_scadenza(Timestamp data_scadenza) 
	{
		this.data_scadenza = data_scadenza;
	}
	public Timestamp getData_cancellazione() {
		return data_cancellazione;
	}
	public void setData_cancellazione(Timestamp data_cancellazione) 
	{
		this.data_cancellazione = data_cancellazione;
	}
	
	public void setIndirizzo(Indirizzo ind)
	{
		this.indirizzo = ind;
	}
	
	public Indirizzo getIndirizzo() 
	{
		return this.indirizzo;
	}
	
	public Integer getId_stato() 
	{
		return id_stato;
	}
	public void setId_stato(Integer id_stato) 
	{
		this.id_stato = id_stato;
	}
	
	public String getStato() 
	{
		return stato;
	}
	public void setStato(String stato) 
	{
		this.stato = stato;
	}
	public Timestamp getData_inserimento() 
	{
		return data_inserimento;
	}
	public void setData_inserimento(Timestamp data_inserimento) 
	{
		this.data_inserimento = data_inserimento;
	}
	public Impresa getImpresa() {
		return impresa;
	}
	public void setImpresa(Impresa impresa) {
		this.impresa = impresa;
	}
	
	public Integer getCategoria_rischio() {
		return categoria_rischio;
	}

	public void setCategoria_rischio(Integer categoria_rischio) {
		this.categoria_rischio = categoria_rischio;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Timestamp getData_prossimo_controllo() {
		return data_prossimo_controllo;
	}

	public void setData_prossimo_controllo(Timestamp data_prossimo_controllo) {
		this.data_prossimo_controllo = data_prossimo_controllo;
	}
	
	public ArrayList<Stabilimento> getItems(Connection conn) throws SQLException 
	{

		String sql = " select * from public_functions.anagrafica_cerca_stabilimento(?,?,?,?,?,?) "  ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, this.getId());
		st.setString(2, this.getDenominazione());
		st.setObject(3, this.getIdAsl());                                                                            
		st.setObject(4, this.getStato());
		st.setTimestamp(5, null);//null?
		st.setTimestamp(6, null);//null?
		
		ResultSet rs = st.executeQuery();
		ArrayList<Stabilimento> stabilimenti = new ArrayList<Stabilimento>();
		
		while(rs.next())
		{
			stabilimenti.add(new Stabilimento(rs));
		}
		
		return stabilimenti;
	}
	public Integer insert( Connection conn, Integer idUtente ) throws SQLException 
	{
		Integer idStabilimento = null;
		ResultSet rs = null;
		String sql = " select * from anagrafica.anagrafica_inserisci_stabilimento( ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, this.getDenominazione());
		st.setInt(2, this.getIdAsl());
		st.setObject(3, this.getStato());
		st.setObject(4, null);
		st.setDate(5, null);
		st.setDate(6, null); 
		st.setDate(7, null);
		st.setString(8, this.getNote());
		st.setObject(9, idUtente);
		rs = st.executeQuery();
		if(rs.next())
			idStabilimento=rs.getInt(1);
		return idStabilimento;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	
	public void insertRelazioneImpresa( Connection conn, int id_impresa, int id_stabilimento, int utente ) throws SQLException 
	{
		boolean esiste = false;
		String sqlCerca = " select * from anagrafica.anagrafica_cerca_rel_impresa_stabilimento( ?, ?, null)" ;
		ResultSet rs = null;
		PreparedStatement stCerca = conn.prepareStatement(sqlCerca);
		stCerca.setObject(1, id_impresa); 
		stCerca.setObject(2, id_stabilimento);
		rs = stCerca.executeQuery();
		
		while (rs.next())
			esiste = true;
		
		if (!esiste){
			String sql = " select * from anagrafica.anagrafica_inserisci_rel_impresa_stabilimento( ?, ?, ?) " ;
			PreparedStatement st = conn.prepareStatement(sql);
			st.setObject(1, id_impresa); 
			st.setObject(2, id_stabilimento);
			st.setObject(3, utente);
			st.executeQuery();
		}
	}
	
	public void insertRelazioneSoggetto( Connection conn, int id_stabilimento, int id_soggetto, int utente) throws SQLException 
	{
		String sql = " select * from anagrafica.anagrafica_inserisci_rel_stabilimento_soggetto_fisico( ?, ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, id_stabilimento); 
		st.setObject(2, id_soggetto);
		st.setObject(3, 1);
		st.setObject(4, utente);
		st.executeQuery();
	}

	public int getAlt_id() {
		return alt_id;
	}

	public void setAlt_id(int alt_id) {
		this.alt_id = alt_id;
	}
	

	public ArrayList<Istanza> getLinee() {
		return linee;
	}

	public void setLinee(ArrayList<Istanza> linee) {
		this.linee = linee;
	}

	public String getNumero_registrazione() {
		return numero_registrazione;
	}

	public void setNumero_registrazione(String numero_registrazione) {
		this.numero_registrazione = numero_registrazione;
	}

	public void generaNumeroRegistrazione(Connection db, Integer idStabilimento) throws SQLException {
		String sql = " select * from anagrafica.anagrafica_inserisci_numero_registrazione_stabilimento(?)" ;
		PreparedStatement st = db.prepareStatement(sql);
		st.setObject(1, idStabilimento); 
		ResultSet rs = st.executeQuery();
		if (rs.next())
			this.numero_registrazione=rs.getString("anagrafica_inserisci_numero_registrazione_stabilimento");
	}
}
