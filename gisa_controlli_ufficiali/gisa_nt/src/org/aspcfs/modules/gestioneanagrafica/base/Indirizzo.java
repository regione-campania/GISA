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
import java.util.ArrayList;
import java.util.Map;

import org.aspcfs.utils.Bean;



public class Indirizzo 
{
	private Integer id;
	private String via;
	private String cap;
	private Double latitudine;
	private Double longitudine;
	private Integer riferimento_org_id;
	private Integer riferimento_address_id;
	private Integer address_type;
	private String comune_testo;
	private String provincia_testo;
	private Integer idToponimo;
	private Toponimo toponimo = new Toponimo();
	private Provincia provincia = new Provincia();
	private Comune comune = new Comune();
	private Nazione nazione = new Nazione();
	
	
	

	public Integer getIdComune() {
		return idComune;
	}

	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Integer getIdNazione() {
		return idNazione;
	}

	public void setIdNazione(Integer idNazione) {
		this.idNazione = idNazione;
	}

	private Integer idComune;
	private Integer idProvincia;
	private Integer idNazione;
	
	private String civico;
	private String clean;
	private String topon;
	private String via_old;
	private String note_hd;
	private SoggettoFisico soggettoFisicoUpdatethis;
	private Integer code;

	public Indirizzo() 
	{
	}
	
	public Indirizzo(Map<String, String[]> parameterMap) 
    {
        Bean.populate(this, parameterMap);
    }
    
	
	public Indirizzo(String via, String cap, int idProvincia, int idNazione, String latitudine,
			String longitudine, int idComune, String comune_testo, int toponimo, String civico) {
		// TODO Auto-generated constructor stub
		this.via = via;
		this.cap = cap;
		this.idProvincia = idProvincia;
		this.idNazione = idNazione;
		try { this.latitudine = Double.parseDouble(latitudine);} catch (Exception e) {}
		try { this.longitudine = Double.parseDouble(longitudine);} catch (Exception e) {}		
		this.idComune = idComune;
		this.comune_testo = comune_testo;
		this.idToponimo = toponimo;
		this.civico = civico;
		
	}
	
	public Indirizzo(ResultSet rs) throws SQLException{
		// TODO Auto-generated constructor stub

	    try{ rs.findColumn("via"); this.via = rs.getString("via"); } catch (SQLException e) {} 
	    try{ rs.findColumn("cap"); this.cap = rs.getString("cap"); } catch (SQLException e) {} 
        try{ rs.findColumn("id");  this.id = rs.getInt("id"); } catch (SQLException e) {} 
        try{ rs.findColumn("provincia"); this.idProvincia = rs.getInt("provincia"); } catch (Exception e) {} 
        try{ rs.findColumn("nazione"); this.idNazione = rs.getInt("nazione"); } catch (Exception e) {} 
        try{ rs.findColumn("latitudine"); this.latitudine = rs.getDouble("latitudine"); } catch (SQLException e) {} 
        try{ rs.findColumn("longitudine"); this.longitudine = rs.getDouble("longitudine"); } catch (SQLException e) {} 
        try{ rs.findColumn("comune"); this.idComune = rs.getInt("comune"); } catch (SQLException e) {} 
        try{ rs.findColumn("toponimo"); this.idToponimo = rs.getInt("toponimo"); } catch (SQLException e) {} 
        try{ rs.findColumn("civico"); this.civico = rs.getString("civico"); } catch (SQLException e) {} 
		
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	
	
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public Double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(Double latitudine) {
		this.latitudine = latitudine;
	}
	public Double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(Double longitudine) {
		this.longitudine = longitudine;
	}


	public Integer getRiferimento_org_id() {
		return riferimento_org_id;
	}

	public void setRiferimento_org_id(Integer riferimento_org_id) {
		this.riferimento_org_id = riferimento_org_id;
	}

	public Integer getRiferimento_address_id() {
		return riferimento_address_id;
	}

	public void setRiferimento_address_id(Integer riferimento_address_id) {
		this.riferimento_address_id = riferimento_address_id;
	}

	public Integer getAddress_type() {
		return address_type;
	}

	public void setAddress_type(Integer address_type) {
		this.address_type = address_type;
	}

	public String getComune_testo() {
		return comune_testo;
	}

	public void setComune_testo(String comune_testo) {
		this.comune_testo = comune_testo;
	}

	public Integer getTiponimo() {
		return idToponimo;
	}

	public void setTiponimo(Integer tiponimo) {
		this.idToponimo = tiponimo;
	}

	public String getClean() {
		return clean;
	}

	public void setClean(String clean) {
		this.clean = clean;
	}

	public String getTopon() {
		return topon;
	}

	public void setTopon(String topon) {
		this.topon = topon;
	}

	public String getVia_old() {
		return via_old;
	}

	public void setVia_old(String via_old) {
		this.via_old = via_old;
	}

	public String getNote_hd() {
		return note_hd;
	}

	public void setNote_hd(String note_hd) {
		this.note_hd = note_hd;
	}

	public SoggettoFisico getSoggettoFisicoUpdatethis() {
		return soggettoFisicoUpdatethis;
	}

	public void setSoggettoFisicoUpdatethis(SoggettoFisico soggettoFisicoUpdatethis) {
		this.soggettoFisicoUpdatethis = soggettoFisicoUpdatethis;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	public Integer insert( Connection conn, Integer utente) throws SQLException 
	{
		Integer id = null;
		ResultSet rs = null;
		ArrayList<Indirizzo> indirizzi = this.getItems(conn);
		if (indirizzi.size()>0)
			return indirizzi.get(0).getId();		
		String sql = " select * from anagrafica.anagrafica_inserisci_indirizzo( ?, ?, ?, ?, ?,?, ?, ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, this.getVia()); 
		st.setString(2, this.getCap());
		st.setObject(3, this.getIdProvincia());
		st.setObject(4, this.getIdNazione());
		st.setObject(5, this.getLatitudine());
		st.setObject(6, this.getLongitudine());
		st.setObject(7, this.getIdComune());
		st.setObject(8, this.getIdToponimo());
		st.setString(9, this.getCivico());
		st.setObject(10, utente);
		rs = st.executeQuery();
		if(rs.next())
			id=rs.getInt(1);
		return id;
	}
	
	public void insertRelazioneSoggetto(Connection conn, int id_soggetto, int id_indirizzo, int utente) throws SQLException 
	{
		
		boolean esiste = false;
		String sqlCerca = " select * from anagrafica.anagrafica_cerca_rel_soggetto_fisico_indirizzo( ?, ?, null)" ;
		ResultSet rs = null;
		PreparedStatement stCerca = conn.prepareStatement(sqlCerca);
		stCerca.setObject(1, id_soggetto); 
		stCerca.setObject(2, id_indirizzo);
		rs = stCerca.executeQuery();
		
		while (rs.next())
			esiste = true;
		
		if (!esiste){
			String sql = " select * from anagrafica.anagrafica_inserisci_rel_soggetto_fisico_indirizzo( ?, ?, ?) " ;
			PreparedStatement st = conn.prepareStatement(sql);
			st.setObject(1, id_soggetto); 
			st.setObject(2, id_indirizzo);
			st.setObject(3, utente);
			st.executeQuery();
		}
	}
	
	public void insertRelazioneStabilimento( Connection conn, int id_stabilimento, int id_indirizzo, int utente) throws SQLException 
	{
		String sql = " select * from anagrafica.anagrafica_inserisci_rel_stabilimento_indirizzo( ?, ?, ?) " ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, id_stabilimento); 
		st.setObject(2, id_indirizzo);
		st.setObject(3, utente);
		st.executeQuery();
	}
	
	public void insertRelazioneImpresa( Connection conn, int id_impresa, int id_indirizzo, int utente) throws SQLException 
	{	
		boolean esiste = false;
		String sqlCerca = " select * from anagrafica.anagrafica_cerca_rel_impresa_indirizzo( ?, ?, null, null)" ;
		ResultSet rs = null;
		PreparedStatement stCerca = conn.prepareStatement(sqlCerca);
		stCerca.setObject(1, id_impresa); 
		stCerca.setObject(2, id_indirizzo);
		rs = stCerca.executeQuery();
		
		while (rs.next())
			esiste = true;
		
		if (!esiste){
			String sql = " select * from anagrafica.anagrafica_inserisci_rel_impresa_indirizzo( ?, ?, ?) " ;
			PreparedStatement st = conn.prepareStatement(sql);
			st.setObject(1, id_impresa); 
			st.setObject(2, id_indirizzo);
			st.setObject(3, utente);
			st.executeQuery();
		}
	}
	
	public ArrayList<Indirizzo> getItems(Connection conn) throws SQLException 
	{

	  	String sql = " select id,via,cap,provincia as \"provincia.code\", nazione as \"nazione.code\", latitudine, longitudine, comune as \"comune.id\", toponimo as \"toponimo.code\", civico "
	  			+ "from anagrafica.anagrafica_cerca_indirizzo(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, this.getId());
		st.setString(2, this.getVia());
		st.setString(3, this.getCap());
		st.setObject(4, this.getIdProvincia());
		st.setObject(5, this.getIdNazione());
		st.setObject(6, this.getLatitudine());
		st.setObject(7, this.getLongitudine());
		st.setObject(8, this.getIdComune());
		st.setObject(9, this.getIdToponimo());
		st.setString(10, this.getCivico());
		
		ResultSet rs = st.executeQuery();
		ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
		
		while(rs.next())
		{
			indirizzi.add(new Indirizzo(rs));
		}
		
		return indirizzi;
	}

    public String getProvincia_testo() {
        return provincia_testo;
    }

    public void setProvincia_testo(String provincia_testo) {
        this.provincia_testo = provincia_testo;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Comune getComune() {
        return comune;
    }

    public void setComune(Comune comune) {
        this.comune = comune;
    }


    public Nazione getNazione() {
        return nazione;
    }

    public void setNazione(Nazione nazione) {
        this.nazione = nazione;
    }

    public Integer getIdToponimo() {
        return idToponimo;
    }

    public void setIdToponimo(Integer idToponimo) {
        this.idToponimo = idToponimo;
    }

    public Toponimo getToponimo() {
        return toponimo;
    }

    public void setToponimo(Toponimo toponimo) {
        this.toponimo = toponimo;
    }
}
