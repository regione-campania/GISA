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



public class SoggettoFisico 
{
	private Integer id;
	private String titolo;
	private String nome;
	private String cognome;
	private String codice_fiscale;

	private String ipenteredby;
	private String ipmodifiedby;
	private String sesso;
	private String telefono;
	private String fax;
	private String email;
	private String telefono1;
	private Timestamp data_nascita;
	private String documento_identita;
	private Boolean provenienza_estera;
	private String provincia_nascita;
	private Timestamp data_cancellazione;
	private Timestamp data_inserimento;
	private Indirizzo indirizzo = new Indirizzo();
	private int idComuneNascita;
	private String nomeComuneNascita;
  //  private Comune comune_nascita = new Comune();
	
	public SoggettoFisico()
	{
	}
	
	public SoggettoFisico(Integer id)
    {
	    this.id=id;
	    
    }

	public SoggettoFisico(String cognome, String nome, String nomeComuneNascita, int idComuneNascita,
			String codice_fiscale, String sesso, String telefono, String documento_identita,
			//Boolean provenienza_estera, 
			String provincia_nascita, String data_nascita) {
		
		// TODO Auto-generated constructor stub
		this.cognome = cognome;
		this.nome = nome;
		this.setNomeComuneNascita(nomeComuneNascita);
		this.setIdComuneNascita(idComuneNascita);
		this.codice_fiscale = codice_fiscale;
		this.sesso = sesso;
		this.documento_identita = documento_identita;
		//this.provenienza_estera = provenienza_estera;
		this.provincia_nascita = provincia_nascita;
		setData_nascita(data_nascita);
		
	}

	public SoggettoFisico(ResultSet rs) throws SQLException {
	    
	    Bean.populate(this, rs);   
	    /*// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		this.cognome = rs.getString("cognome");
		this.nome = rs.getString("nome");
		this.nomeComuneNascita = rs.getString("comune_nascita");
		//this.idComuneNascita = idComuneNascita;
		this.codice_fiscale = rs.getString("codice_fiscale");
		this.sesso = rs.getString("sesso");
		this.documento_identita = rs.getString("documento_identita");
		//this.provenienza_estera = provenienza_estera;
		this.provincia_nascita = rs.getString("provincia_nascita");
		this.data_nascita = rs.getTimestamp("data_nascita");*/

	}
	
    
   public SoggettoFisico(Map<String, String[]> parameterMap) 
    {
         Bean.populate(this, parameterMap);
    }
 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public String getIpenteredby() {
		return ipenteredby;
	}

	public void setIpenteredby(String ipenteredby) {
		this.ipenteredby = ipenteredby;
	}

	public String getIpmodifiedby() {
		return ipmodifiedby;
	}

	public void setIpmodifiedby(String ipmodifiedby) {
		this.ipmodifiedby = ipmodifiedby;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public Timestamp getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(Timestamp data_nascita) {
		this.data_nascita = data_nascita;
	}
	public void setData_nascita(String data_nascita) {
		this.data_nascita = DatabaseUtils.parseDateToTimestamp(data_nascita);
	}

	public String getDocumento_identita() {
		return documento_identita;
	}

	public void setDocumento_identita(String documento_identita) {
		this.documento_identita = documento_identita;
	}

	public Boolean getProvenienza_estera() {
		return provenienza_estera;
	}

	public void setProvenienza_estera(Boolean provenienza_estera) {
		this.provenienza_estera = provenienza_estera;
	}

	public String getProvincia_nascita() {
		return provincia_nascita;
	}

	public void setProvincia_nascita(String provincia_nascita) {
		this.provincia_nascita = provincia_nascita;
	}

	public Timestamp getData_cancellazione() {
		return data_cancellazione;
	}

	public void setData_cancellazione(Timestamp data_cancellazione) {
		this.data_cancellazione = data_cancellazione;
	}

	public Timestamp getData_inserimento() {
		return data_inserimento;
	}

	public void setData_inserimento(Timestamp data_inserimento) {
		this.data_inserimento = data_inserimento;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public Integer insert( Connection conn, Integer utente ) throws SQLException 
	{
		ResultSet rs = null;
		ArrayList<SoggettoFisico> soggetto = this.getItems(conn);
		if (soggetto.size()>0)
			return soggetto.get(0).getId();	
		Integer id = null;
		
		String sql = " select * from anagrafica.anagrafica_inserisci_soggetto_fisico( ?, ?, ?, ?, ?,?,?, ?, ?, ?, ?,?, ?, ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, this.getTitolo()); 
		st.setString(2, this.getCognome());
		st.setString(3, this.getNome());
		st.setString(4, this.getNomeComuneNascita());  //comune nascita da capire
		st.setObject(5, this.getIdComuneNascita()); //id comune nascita da capire
		st.setString(6, this.getCodice_fiscale());
		st.setString(7, this.getSesso());
		st.setString(8, this.getTelefono());
		st.setString(9, this.getFax());
		st.setString(10, this.getEmail());
		st.setString(11, this.getTelefono1());
		st.setDate(12, (this.getData_nascita()!=null)?(new java.sql.Date(this.getData_nascita().getTime())):(null));
		st.setString(13, this.getDocumento_identita());
		st.setObject(14, this.getProvenienza_estera());
		st.setString(15, this.getProvincia_nascita());
		st.setObject(16, utente);
		rs = st.executeQuery();
		if(rs.next())
			id=rs.getInt(1);
		return id;
	}


	public ArrayList<SoggettoFisico> getItems(Connection conn) throws SQLException 
	{

	  	String sql = " select id,titolo,cognome,nome,codice_fiscale,sesso,telefono,email,data_nascita,documento_identita, id_comune_nascita as \"comune_nascita.id\" from anagrafica.anagrafica_cerca_soggetto_fisico(?,?,?,?,?,?,?,?,?,?,?,?) "  ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, this.getId());
		st.setString(2, this.getTitolo());
		st.setString(3, this.getCognome());
		st.setString(4, this.getNome());
		st.setObject(5, this.getNomeComuneNascita());//come si risolve lui vuole id comune?
		st.setString(6, this.getCodice_fiscale());
		st.setDate(7, (this.getData_nascita()!=null)?(new java.sql.Date(this.getData_nascita().getTime())):(null));
		st.setDate(8, (this.getData_nascita()!=null)?(new java.sql.Date(this.getData_nascita().getTime())):(null));
		st.setString(9, this.getDocumento_identita());
		st.setString(10, this.getProvincia_nascita());
		st.setDate(11, (this.getData_inserimento()!=null)?(new java.sql.Date(this.getData_inserimento().getTime())):(null));
		st.setDate(12, (this.getData_inserimento()!=null)?(new java.sql.Date(this.getData_inserimento().getTime())):(null));
		System.out.println("query:"+st.toString());
		
		ResultSet rs = st.executeQuery();
		ArrayList<SoggettoFisico> soggetti = new ArrayList<SoggettoFisico>();
		
		while(rs.next())
		{
			soggetti.add(new SoggettoFisico(rs));
		}
		
		return soggetti;
	}
	
	public void insertRelazioneImpresa(Connection conn,int id_impresa, int id_soggetto_fisico, int utente) throws SQLException 
	{
		
		boolean esiste = false;
		String sqlCerca = " select * from anagrafica.anagrafica_cerca_rel_impresa_soggetto_fisico( ?, ?, null, null)" ;
		ResultSet rs = null;
		PreparedStatement stCerca = conn.prepareStatement(sqlCerca);
		stCerca.setObject(1, id_impresa); 
		stCerca.setObject(2, id_soggetto_fisico);
		rs = stCerca.executeQuery();
		
		while (rs.next())
			esiste = true;
		
		if (!esiste){
			String sql = " select * from anagrafica.anagrafica_inserisci_rel_impresa_soggetto_fisico( ?, ?, ?, ?) " ;
			PreparedStatement st = conn.prepareStatement(sql);
			st.setObject(1, id_impresa); 
			st.setObject(2, id_soggetto_fisico);
			st.setObject(3, 1);
			st.setObject(4, utente);
			st.executeQuery();
		}
	}
	
	public String getNomeComuneNascita() {
		return nomeComuneNascita;
	}

	public void setNomeComuneNascita(String nomeComuneNascita) {
		this.nomeComuneNascita = nomeComuneNascita;
	}

	public int getIdComuneNascita() {
		return idComuneNascita;
	}

	public void setIdComuneNascita(int idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}

	public void setIndirizzo(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select ind.* from anagrafica.soggetti_fisici sogg left join anagrafica.rel_soggetti_fisici_indirizzi rel on rel.id_soggetto_fisico = sogg.id left join anagrafica.indirizzi ind on ind.id = rel.id_indirizzo where sogg.id = "+this.id+" and rel.data_scadenza is null and rel.data_cancellazione is null");
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			Indirizzo ind = new Indirizzo(rs);
			setIndirizzo(ind);
		}
	}

//    public Comune getComune_nascita() {
//        return comune_nascita;
//    }
//
//    public void setComune_nascita(Comune comune_nascita) {
//        this.comune_nascita = comune_nascita;
//    }
}
