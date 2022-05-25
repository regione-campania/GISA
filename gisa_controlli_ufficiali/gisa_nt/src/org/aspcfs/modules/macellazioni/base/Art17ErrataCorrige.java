/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.aspcfs.modules.registrotrasgressori.base.Trasgressione;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;


public class Art17ErrataCorrige extends GenericBean{
	
	private Timestamp entered = null;
	private int id = -1;
	private int idMacello = -1;
	private int idCapo = -1;
	private int idUtente = -1;
	private String ipUtente = "";
	private String nomeUtente = "";
	private Timestamp dataMacellazione = null;
	private String sottoscritto = null;
	private String matricolaErrata = null;
	private String matricolaCorretta = null;
	private Timestamp dataNascitaErrata = null;
	private Timestamp dataNascitaCorretta = null;
	private int specieErrata = -1;
	private int specieCorretta = -1;
	private String sessoErrato = null;
	private String sessoCorretto = null;
	private String mod4Errato = null;
	private String mod4Corretto=null;
	private String altroErrato=null;
	private String altroCorretto=null;
	private String motivo = null;
	
	private boolean matricolaModificata = false;
	private boolean dataNascitaModificata = false;
	private boolean sessoModificato = false;
	private boolean specieModificata = false;
	private boolean mod4Modificato = false;
	private boolean altroModificato = false;
	
	public boolean isMatricolaModificata() {
		return matricolaModificata;
	}
	public void setMatricolaModificata(boolean matricolaModificata) {
		this.matricolaModificata = matricolaModificata;
	}
	public boolean isDataNascitaModificata() {
		return dataNascitaModificata;
	}
	public void setDataNascitaModificata(boolean dataNascitaModificata) {
		this.dataNascitaModificata = dataNascitaModificata;
	}
	public boolean isSessoModificato() {
		return sessoModificato;
	}
	public void setSessoModificato(boolean sessoModificato) {
		this.sessoModificato = sessoModificato;
	}
	public boolean isSpecieModificata() {
		return specieModificata;
	}
	public void setSpecieModificata(boolean specieModificata) {
		this.specieModificata = specieModificata;
	}
	public boolean isMod4Modificato() {
		return mod4Modificato;
	}
	public void setMod4Modificato(boolean mod4Modificato) {
		this.mod4Modificato = mod4Modificato;
	}
	public boolean isAltroModificato() {
		return altroModificato;
	}
	public void setAltroModificato(boolean altroModificato) {
		this.altroModificato = altroModificato;
	}
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public void setIdMacello(String idMacello) {
		if (idMacello!=null && !idMacello.equals(""))
			this.idMacello = Integer.parseInt(idMacello);
	}
	public int getIdCapo() {
		return idCapo;
	}
	public void setIdCapo(int idCapo) {
		this.idCapo = idCapo;
	}
	public void setIdCapo(String idCapo) {
		if (idCapo!=null && !idCapo.equals(""))
			this.idCapo = Integer.parseInt(idCapo);
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public String getIpUtente() {
		return ipUtente;
	}
	public void setIpUtente(String ipUtente) {
		this.ipUtente = ipUtente;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public Timestamp getDataMacellazione() {
		return dataMacellazione;
	}
	public void setDataMacellazione(Timestamp dataMacellazione) {
		this.dataMacellazione = dataMacellazione;
	}
	public void setDataMacellazione(String data) {
		this.dataMacellazione = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	public String getSottoscritto() {
		return sottoscritto;
	}
	public void setSottoscritto(String sottoscritto) {
		this.sottoscritto = sottoscritto;
	}
	public String getMatricolaErrata() {
		return matricolaErrata;
	}
	public void setMatricolaErrata(String matricolaErrata) {
		this.matricolaErrata = matricolaErrata;
	}
	public String getMatricolaCorretta() {
		return matricolaCorretta;
	}
	public void setMatricolaCorretta(String matricolaCorretta) {
		this.matricolaCorretta = matricolaCorretta;
	}
	public Timestamp getDataNascitaErrata() {
		return dataNascitaErrata;
	}
	public void setDataNascitaErrata(Timestamp dataNascitaErrata) {
		this.dataNascitaErrata = dataNascitaErrata;
	}
	public void setDataNascitaErrata(String data) {
		this.dataNascitaErrata = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	public Timestamp getDataNascitaCorretta() {
		return dataNascitaCorretta;
	}
	public void setDataNascitaCorretta(Timestamp dataNascitaCorretta) {
		this.dataNascitaCorretta = dataNascitaCorretta;
	}
	public void setDataNascitaCorretta(String data) {
		this.dataNascitaCorretta = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	public int getSpecieErrata() {
		return specieErrata;
	}
	public void setSpecieErrata(int specieErrata) {
		this.specieErrata = specieErrata;
	}
	public void setSpecieErrata(String specieErrata) {
		if (specieErrata!=null && !specieErrata.equals(""))
			this.specieErrata = Integer.parseInt(specieErrata);
	}
	public int getSpecieCorretta() {
		return specieCorretta;
	}
	public void setSpecieCorretta(int specieCorretta) {
		this.specieCorretta = specieCorretta;
	}
	public void setSpecieCorretta(String specieCorretta) {
		if (specieCorretta!=null && !specieCorretta.equals(""))
			this.specieCorretta = Integer.parseInt(specieCorretta);
	}
	public String getSessoErrato() {
		return sessoErrato;
	}
	public void setSessoErrato(String sessoErrato) {
		this.sessoErrato = sessoErrato;
	}
	public String getSessoCorretto() {
		return sessoCorretto;
	}
	public void setSessoCorretto(String sessoCorretto) {
		this.sessoCorretto = sessoCorretto;
	}
	public String getMod4Errato() {
		return mod4Errato;
	}
	public void setMod4Errato(String mod4Errato) {
		this.mod4Errato = mod4Errato;
	}
	public String getMod4Corretto() {
		return mod4Corretto;
	}
	public void setMod4Corretto(String mod4Corretto) {
		this.mod4Corretto = mod4Corretto;
	}
	public String getAltroErrato() {
		return altroErrato;
	}
	public void setAltroErrato(String altroErrato) {
		this.altroErrato = altroErrato;
	}
	public String getAltroCorretto() {
		return altroCorretto;
	}
	public void setAltroCorretto(String altroCorretto) {
		this.altroCorretto = altroCorretto;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public Art17ErrataCorrige (){ 
		
	}
	
public Art17ErrataCorrige (ResultSet rs) throws SQLException{ 
		buildRecord(rs);
	}
	

public Art17ErrataCorrige ( Connection db, int id){
	ResultSet rs = null;
	PreparedStatement pst;
	try {
		
		String query ="SELECT * FROM macelli_art17_errata_corrige where id = ?";
		pst = db.prepareStatement(query);
		int i = 0;
		pst.setInt(++i, id);
		
		rs = DatabaseUtils.executeQuery(db, pst); 
		 if (rs.next()){
				 buildRecord(rs);
				 
			 }
	rs.close();
	pst.close();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


	public Art17ErrataCorrige (ActionContext context){
		setIdMacello(context.getRequest().getParameter("idMacello"));
		setIdCapo(context.getRequest().getParameter("idCapo"));
		this.sottoscritto = context.getRequest().getParameter("sottoscritto");
		this.motivo = context.getRequest().getParameter("motivo");
		setDataMacellazione(context.getRequest().getParameter("dataMacellazione"));
		
		if (context.getRequest().getParameter("cb_matricola")!=null && context.getRequest().getParameter("cb_matricola").equals("on")){
			setMatricolaModificata(true);
			setMatricolaErrata(context.getRequest().getParameter("matricola_errata"));
			setMatricolaCorretta(context.getRequest().getParameter("matricola"));
		}
		if (context.getRequest().getParameter("cb_datanascita")!=null && context.getRequest().getParameter("cb_datanascita").equals("on")){
			setDataNascitaModificata(true);
			setDataNascitaErrata(context.getRequest().getParameter("datanascita_errata"));
			setDataNascitaCorretta(context.getRequest().getParameter("datanascita"));
		}
		if (context.getRequest().getParameter("cb_specie")!=null && context.getRequest().getParameter("cb_specie").equals("on")){
			setSpecieModificata(true);
			setSpecieErrata(context.getRequest().getParameter("specie_errata"));
			setSpecieCorretta(context.getRequest().getParameter("specie"));
		}
		if (context.getRequest().getParameter("cb_sesso")!=null && context.getRequest().getParameter("cb_sesso").equals("on")){
			setSessoModificato(true);
			setSessoErrato(context.getRequest().getParameter("sesso_errato"));
			setSessoCorretto(context.getRequest().getParameter("sesso"));
		}
		if (context.getRequest().getParameter("cb_mod4")!=null && context.getRequest().getParameter("cb_mod4").equals("on")){
			setMod4Modificato(true);
			setMod4Errato(context.getRequest().getParameter("mod4_errato"));
			setMod4Corretto(context.getRequest().getParameter("mod4"));
		}
		if (context.getRequest().getParameter("cb_altro")!=null && context.getRequest().getParameter("cb_altro").equals("on")){
			setAltroModificato(true);
			setAltroErrato(context.getRequest().getParameter("altro_errato"));
			setAltroCorretto(context.getRequest().getParameter("altro_corretto"));
		}
		
	}
	
	public int insert( Connection db ) throws SQLException{
		
		//int id = DatabaseUtils.getNextSeq(db, "macelli_art17_errata_corrige_id_seq");
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			
		sql.append("INSERT INTO macelli_art17_errata_corrige(id_macello, id_capo, id_utente, ip_utente, nome_utente, data_macellazione, sottoscritto, motivo, entered ");
		
		if (matricolaModificata)
			sql.append(", matricola_modificata, matricola_errata, matricola_corretta ");
		if (dataNascitaModificata)
			sql.append(", datanascita_modificata, datanascita_errata, datanascita_corretta ");
		if (specieModificata)
			sql.append(", specie_modificata, specie_errata, specie_corretta ");
		if (sessoModificato)
			sql.append(", sesso_modificato, sesso_errato, sesso_corretto ");
		if (mod4Modificato)
			sql.append(", mod4_modificato, mod4_errato, mod4_corretto ");
		if (altroModificato)
			sql.append(", altro_modificato, altro_errato, altro_corretto ");
		
		sql.append(") values ( ?, ?, ?, ?, ?, ?, ?, ?, now()");
		
		if (matricolaModificata)
			sql.append(",true, ?, ? ");
		if (dataNascitaModificata)
			sql.append(",true,  ?,  ? ");
		if (specieModificata)
			sql.append(",true,  ?,  ? ");
		if (sessoModificato)
			sql.append(",true,  ?,  ? ");
		if (mod4Modificato)
			sql.append(",true,  ?,  ? ");
		if (altroModificato)
			sql.append(",true,  ?,  ? ");

		sql.append(")");
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		//pst.setInt(++i, id);
		pst.setInt(++i, idMacello);
		pst.setInt(++i, idCapo);
		pst.setInt(++i, idUtente);
		pst.setString(++i, ipUtente);
		pst.setString(++i, nomeUtente);
		pst.setTimestamp(++i, dataMacellazione);
		pst.setString(++i, sottoscritto);
		pst.setString(++i, motivo);

		if (matricolaModificata)
			{
			pst.setString(++i, matricolaErrata);
			pst.setString(++i, matricolaCorretta);
			}
		if (dataNascitaModificata)
		{
			pst.setTimestamp(++i, dataNascitaErrata);
			pst.setTimestamp(++i, dataNascitaCorretta);
			}
		if (specieModificata)
		{
			pst.setInt(++i, specieErrata);
			pst.setInt(++i, specieCorretta);
			}
		if (sessoModificato)
		{
			pst.setString(++i, sessoErrato);
			pst.setString(++i, sessoCorretto);
			}
		if (mod4Modificato)
		{
			pst.setString(++i, mod4Errato);
			pst.setString(++i, mod4Corretto);
			}
		if (altroModificato)
		{
			pst.setString(++i, altroErrato);
			pst.setString(++i, altroCorretto);
			}
		
		pst.execute();
		pst.close();
		this.id = DatabaseUtils.getCurrVal(db, "macelli_art17_errata_corrige_id_seq", id);
		if (doCommit) {
			db.commit();
		}
		}catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}throw new SQLException(e.getMessage());
		} 
		return 1;
	}
	
	private void buildRecord(ResultSet rs) throws SQLException {
		  this.id = rs.getInt("id");
		  this.idMacello = rs.getInt("id_macello");
		  this.idCapo = rs.getInt("id_capo");
		  this.idUtente = rs.getInt("id_utente");
		  this.ipUtente = rs.getString("ip_utente");
		  this.dataMacellazione = rs.getTimestamp("data_macellazione");
		  this.sottoscritto = rs.getString("sottoscritto");
		  this.motivo = rs.getString("motivo");
		  this.matricolaErrata = rs.getString("matricola_errata");
		  this.matricolaCorretta = rs.getString("matricola_corretta");
		  this.dataNascitaErrata = rs.getTimestamp("datanascita_errata");
		  this.dataNascitaCorretta = rs.getTimestamp("datanascita_corretta");
		  this.specieErrata = rs.getInt("specie_errata");
		  this.specieCorretta = rs.getInt("specie_corretta");
		  this.sessoErrato = rs.getString("sesso_errato");
		  this.sessoCorretto = rs.getString("sesso_corretto");
		  this.mod4Errato = rs.getString("mod4_errato");
		  this.mod4Corretto = rs.getString("mod4_corretto");
		  this.altroErrato = rs.getString("altro_errato");
		  this.altroCorretto = rs.getString("altro_corretto");
		  this.matricolaModificata = rs.getBoolean("matricola_modificata");
		  this.dataNascitaModificata = rs.getBoolean("datanascita_modificata");
		  this.specieModificata = rs.getBoolean("specie_modificata");
		  this.sessoModificato = rs.getBoolean("sesso_modificato");
		  this.altroModificato = rs.getBoolean("altro_modificato");
		  this.mod4Modificato = rs.getBoolean("mod4_modificato");
		  this.entered = rs.getTimestamp("entered");
		  this.nomeUtente = rs.getString("nome_utente");
		
	}
	public Timestamp getEntered() {
		return entered;
	}
		
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
public int aggiornaCapo( Connection db ) throws SQLException{
		
		//int id = DatabaseUtils.getNextSeq(db, "macelli_art17_errata_corrige_id_seq");
		StringBuffer sql = new StringBuffer();
boolean doCommit = false;
		
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
				
		sql.append("UPDATE m_capi SET modified=now(), modified_by= ? ");
		
		if (matricolaModificata)
			sql.append(", cd_matricola = ?");
		if (dataNascitaModificata)
			sql.append(", cd_data_nascita = ? ");
		if (specieModificata){
			sql.append(", cd_specie = ?");
			sql.append(", cd_categoria_bovina = -1");
			sql.append(", cd_categoria_bufalina = -1");
		}
		if (sessoModificato)
			sql.append(", cd_maschio = ? ");
		if (mod4Modificato)
			sql.append(", cd_mod4 = ? ");
		sql.append(", errata_corrige_generati = errata_corrige_generati + 1  where id = ? ");
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		//pst.setInt(++i, id);
		pst.setInt(++i, idUtente);
	
		if (matricolaModificata)
			{
			pst.setString(++i, matricolaCorretta);
			}
		if (dataNascitaModificata)
		{
			pst.setTimestamp(++i, dataNascitaCorretta);
			}
		if (specieModificata)
		{
			pst.setInt(++i, specieCorretta);
			}
		if (sessoModificato)
		{
			if (sessoCorretto.equalsIgnoreCase("M"))
				pst.setBoolean(++i, true);
			else
				pst.setBoolean(++i, false);
			}
		if (mod4Modificato)
		{
			pst.setString(++i, mod4Corretto);
			}
		pst.setInt(++i, idCapo);
		
		pst.executeUpdate();
		pst.close();
		if (doCommit) {
			db.commit();
		}
		}catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}throw new SQLException(e.getMessage());
		} 
		return 2;
	}

public Vector load(Connection db){
	ResultSet rs = null;
	Vector ecList = new Vector();
	PreparedStatement pst;
	try {
		
		String query ="SELECT * FROM macelli_art17_errata_corrige where id_capo = ? and id_macello = ? and trashed_date is null";
		pst = db.prepareStatement(query);
		int i = 0;
		pst.setInt(++i, idCapo);
		pst.setInt(++i, idMacello);
		rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 Art17ErrataCorrige ec = new Art17ErrataCorrige(rs);
				 ecList.add(ec);
				 
			 }
	rs.close();
	pst.close();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return ecList;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}



}
