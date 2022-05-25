/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;

import com.darkhorseventures.framework.beans.GenericBean;

public class SintesisIndirizzo extends GenericBean {

	public static final int TIPO_SEDE_LEGALE = 1 ;
	public static final int TIPO_SEDE_OPERATIVA = 5 ;
	public static final int TIPO_SEDE_MOBILE = 7 ;



	private int idIndirizzo = -1;
	private String cap;
	private int comune = -1;
	private String descrizioneComune ;
	private String provincia;
	private String via;
	private int nazione;
	private double latitudine;
	private double longitudine;
	private int idProvincia = -1 ;
	private int tipologiaSede = -1;
	private String descrizione_provincia;
	private Timestamp modified;
	private String comuneTesto ;
	private String descrizioneToponimo ;
	private int toponimo ;  
	private String civico;
	
	public SintesisIndirizzo(Connection db, int id) throws SQLException {
		String sqlSelect = "select * from sintesis_indirizzo where id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, id);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
			codificaDescrizioni(db);
			}
	}
	
	private void codificaDescrizioni(Connection db) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		pst = db.prepareStatement("select nome from comuni1 where id = ?");
		pst.setInt(1, comune);
		rs = pst.executeQuery();
		if (rs.next())
			descrizioneComune = rs.getString("nome");
		
		pst = db.prepareStatement("select description from lookup_province where code = ?");
		pst.setInt(1, idProvincia);
		rs = pst.executeQuery();
		if (rs.next())
			descrizione_provincia = rs.getString("description");
		
		pst = db.prepareStatement("select description from lookup_toponimi where code = ?");
		pst.setInt(1, toponimo);
		rs = pst.executeQuery();
		if (rs.next())
			descrizioneToponimo = rs.getString("description");
		
	}
	public SintesisIndirizzo() {
		// TODO Auto-generated constructor stub
	}
	private void buildRecord(ResultSet rs) throws SQLException {
		idIndirizzo = rs.getInt("id");
		cap = rs.getString("cap");
		comune = rs.getInt("comune");
		via = rs.getString("via");
		idProvincia =  rs.getInt("provincia");
		toponimo = rs.getInt("toponimo");
		civico = rs.getString("civico");
		latitudine = rs.getDouble("latitudine");
		longitudine = rs.getDouble("longitudine");
	}
	
	public int getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public int getComune() {
		return comune;
	}
	public void setComune(int comune) {
		this.comune = comune;
	}
	public void setComune(String comune) {
		try {this.comune = Integer.parseInt(comune);} catch (Exception e){};
	}
	public String getDescrizioneComune() {
		return descrizioneComune;
	}
	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public int getNazione() {
		return nazione;
	}
	public void setNazione(int nazione) {
		this.nazione = nazione;
	}
	public void setNazione(String nazione) {
		try {this.nazione = Integer.parseInt(nazione);} catch (Exception e){};
	}
	public double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}
	public double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	
	public void setLatitudine(String latitudine) {
		try {this.latitudine = Double.parseDouble(latitudine);} catch (Exception e) {}
	}
	public void setLongitudine(String longitudine) {
		try {this.longitudine = Double.parseDouble(longitudine);} catch (Exception e) {}
	}

	public int getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		try {this.idProvincia = Integer.parseInt(idProvincia);} catch (Exception e) {};
	}
	public int getTipologiaSede() {
		return tipologiaSede;
	}
	public void setTipologiaSede(int tipologiaSede) {
		this.tipologiaSede = tipologiaSede;
	}
	public String getDescrizione_provincia() {
		return descrizione_provincia;
	}
	public void setDescrizione_provincia(String descrizione_provincia) {
		this.descrizione_provincia = descrizione_provincia;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public void setIdAsl(String idAsl) {
		try {this.idAsl = Integer.parseInt(idAsl);} catch (Exception e) {} 
	}

	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public String getComuneTesto() {
		return comuneTesto;
	}
	public void setComuneTesto(String comuneTesto) {
		this.comuneTesto = comuneTesto;
	}
	public String getDescrizioneToponimo() {
		return descrizioneToponimo;
	}
	public void setDescrizioneToponimo(String descrizioneToponimo) {
		this.descrizioneToponimo = descrizioneToponimo;
	}
	public int getToponimo() {
		return toponimo;
	}
	public void setToponimo(int toponimo) {
		this.toponimo = toponimo;
	}
	
	public void setToponimo(String toponimo) {
		
		this.toponimo = 1;
		try {this.toponimo = Integer.parseInt(toponimo);} catch (Exception e) {};
	}
	
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}

	
	
	public void codificaCampi(Connection db) throws SQLException{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		//convertire descrizione comune in id
		
		pst = db.prepareStatement("select * from comuni1 where nome ilike trim(?)");
		pst.setString(1, descrizioneComune);
		rs = pst.executeQuery();
		while (rs.next()){
			this.comune = rs.getInt("id");
			//this.cap = rs.getString("cap");
			this.idProvincia =  rs.getInt("cod_provincia");
			this.idAsl =  rs.getInt("codiceistatasl");
		}
		
		//convertire via in toponimo + via + civico
		
		pst = db.prepareStatement("select * from normalizzazione_indirizzo(?)");
		pst.setString(1, via);
		rs = pst.executeQuery();
		while (rs.next()){
			this.via = rs.getString("viaout");
			setToponimo(rs.getString("toponimoout"));
			this.civico = rs.getString("civicoout");
		}
		
	}


	public void insertIndirizzo(Connection db) throws SQLException{
		int idInsertIndirizzo = -1;
		
		String sqlSelect = "select id from sintesis_indirizzo where toponimo = ? and via ilike ? and civico ilike ? and comune = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, toponimo);
		pstSelect.setString(2, via);
		pstSelect.setString(3, civico);
		pstSelect.setInt(4, comune);

		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next())
			idInsertIndirizzo = rsSelect.getInt("id");
		
		if (idInsertIndirizzo == -1){
			String sqlInsert = "insert into sintesis_indirizzo (toponimo, via, civico, comune, provincia, cap, latitudine, longitudine)  values (?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito";
			PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
			int i = 0;
			pstInsert.setInt(++i, toponimo);
			pstInsert.setString(++i, via);
			pstInsert.setString(++i, civico);
			pstInsert.setInt(++i, comune);
			pstInsert.setInt(++i, idProvincia);
			pstInsert.setString(++i, cap);
			pstInsert.setDouble(++i, latitudine);
			pstInsert.setDouble(++i, longitudine);

			ResultSet rsInsert = pstInsert.executeQuery();
			if (rsInsert.next())
				idInsertIndirizzo = rsInsert.getInt("id_inserito");
		}
			idIndirizzo = idInsertIndirizzo;
	}
	public boolean isDiverso(Connection db, SintesisIndirizzo indirizzo) {
		boolean idDiverso = false;
		
		if (this.getToponimo()!=indirizzo.getToponimo()){
			idDiverso = true;
		}
		
		if (this.getComune()!=indirizzo.getComune()){
			idDiverso = true;
		}
		
		String thisVia = this.getVia();
		if (thisVia == null)
			thisVia = "";
		
		String indVia = indirizzo.getVia();
		if (indVia == null)
			indVia = "";
		
		String thisCivico = this.getCivico();
		if (thisCivico == null)
			thisCivico = "";
		
		String indCivico = indirizzo.getCivico();
		if (indCivico == null)
			indCivico = "";
		
		if (!thisVia.equalsIgnoreCase(indVia)){
			idDiverso = true;
		}
		if (!thisCivico.equalsIgnoreCase(indCivico)){
			idDiverso = true;
		}
		
		return idDiverso;
	}
	public void aggiornaIndirizzo(Connection db) {
		// TODO Auto-generated method stub
		
	}

	public void aggiornaDati(Connection db, SintesisIndirizzo indResidenza) {
		// TODO Auto-generated method stub
		
	}
	
	public void aggiornaCoordinateCap(Connection db) throws SQLException{
		PreparedStatement pst = db.prepareStatement("update sintesis_indirizzo set latitudine = ?, longitudine = ?, cap = ? where id = ?");
		pst.setDouble(1, latitudine);
		pst.setDouble(2, longitudine);
		pst.setString(3, cap);
		pst.setInt(4, idIndirizzo);
		pst.executeUpdate();
	}
	
}



