/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedaAdozioneCani.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.CampoModificato;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.beans.GenericBean;

public class SchedaAdozione extends GenericBean 
{

	private static Logger log = Logger.getLogger(SchedaAdozione.class);
	static 
	{
		if (System.getProperty("DEBUG") != null) 
		{
			log.setLevel(Level.DEBUG);
		}
	}
	
	private int id;
	private int idAnimale;
	private Animale animale;
	private int idCriterio;
	private Criterio criterio;
	private int idIndice;
	private Indice indice;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;
	private int enteredBy;
	private User userEnteredBy;
	private int modifiedBy;
	private User userModifiedBy;
	private java.sql.Timestamp trashedDate;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}
	
	public Animale getAnimale() {
		return animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
	}

	public void setIdAnimale(String idAnimale) {
		this.idAnimale = new Integer(idAnimale).intValue();
	}
	
	public int getIdCriterio() {
		return idCriterio;
	}

	public void setIdCriterio(int idCriterio) {
		this.idCriterio = idCriterio;
	}

	public void setIdCriterio(String idCriterio) {
		this.idCriterio = new Integer(idCriterio).intValue();
	}
	
	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	
	public int getIdIndice() {
		return idIndice;
	}

	public void setIdIndice(int idIndice) {
		this.idIndice = idIndice;
	}

	public void setIdIndice(String idIndice) {
		this.idIndice = new Integer(idIndice).intValue();
	}
	
	public Indice getIndice() {
		return indice;
	}

	public void setIndice(Indice indice) {
		this.indice = indice;
	}
	
	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) 
	{
		this.entered = entered;
	}
	
	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) 
	{
		this.modified = modified;
	}

	public void setEnteredBy(int enteredBy) 
	{
		this.enteredBy = enteredBy;
	};
	
	public int getEnteredBy() {
		return enteredBy;
	}
	
	public void setUserEnteredBy(User userEnteredBy) 
	{
		this.userEnteredBy = userEnteredBy;
	};
	
	public User getUserEnteredBy() {
		return userEnteredBy;
	}

	
	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) 
	{
		this.modifiedBy = modifiedBy;
	};
	
	public User getUserModifiedBy() {
		return userModifiedBy;
	}

	public void setUserModifiedBy(User userModifiedBy) 
	{
		this.userModifiedBy = userModifiedBy;
	};
	
	public java.sql.Timestamp getTrashedDate() {
		return trashedDate;
	}

	public void setTrashedDate(String trashedDate) 
	{
		this.trashedDate = DateUtils.parseDateStringNew(trashedDate, "dd/MM/yyyy");
	}

	

	

	public boolean insert(Connection db) throws SQLIntegrityConstraintViolationException, SQLException 
	{

		StringBuffer sql = new StringBuffer();

		
		id = DatabaseUtils.getNextSeqPostgres(db, "scheda_adozione_cani_id_seq");

		sql.append("INSERT INTO scheda_adozione_cani( ");

		if (id > -1) 
		{
			sql.append("id, ");
		}

		sql.append("id_animale,id_criterio,id_indice,entered,modified,entered_by,modified_by ) VALUES (");

		if (id > -1) 
		{
			sql.append("?, ");
		}

		sql.append("?,?,?,now(),now(),?,? )");
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		if (id > 0) 
		{
			pst.setInt(++i, id);
		}

		pst.setInt(++i, idAnimale);
		pst.setInt(++i, idCriterio);
		pst.setInt(++i, idIndice);
		pst.setInt(++i, enteredBy);
		pst.setInt(++i, modifiedBy);

		pst.execute();
		pst.close();

		this.id = DatabaseUtils.getCurrVal(db, "scheda_adozione_cani_id_seq", id);

		return true;

	}

	public SchedaAdozione() throws SQLException 
	{}
	
	public SchedaAdozione getById(Connection db, int id) throws SQLException 
	{
		SchedaAdozione scheda = null;
		if (id == -1) 
		{
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select * from scheda_adozione_cani where id = ?");
		pst.setInt(1, id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) 
		{
			scheda = buildRecord(db, rs);
		}

		rs.close();
		pst.close();
		
		return scheda;
	}
	
	public SchedaAdozione get(Connection db, int idCriterio, int idAnimale) throws SQLException 
	{
		SchedaAdozione scheda = null;
		if (id == -1) 
		{
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select * from scheda_adozione_cani where id_criterio = ? and id_animale = ? ");
		pst.setInt(1, idCriterio);
		pst.setInt(2, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) 
		{
			scheda = buildRecord(db, rs);
		}

		rs.close();
		pst.close();
		
		return scheda;
	}
	
	public ArrayList<SchedaAdozione> getByIdAnimale(Connection db, int idAnimale) throws SQLException 
	{
		ArrayList<SchedaAdozione> schede = new ArrayList<SchedaAdozione>();
		if (idAnimale == -1) 
		{
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement(" Select * " + 
													" from scheda_adozione_cani sc " + 
													" left join lookup_scheda_adozione_cani_criteri cr on cr.id = sc.id_criterio " + 
													" where sc.id_animale = ? and sc.trashed_date is null and cr.enabled " + 
													" order by cr.level ");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) 
		{
			SchedaAdozione scheda = buildRecord(db, rs);
			schede.add(scheda);
		}

		rs.close();
		pst.close();
		
		return schede;
	}
	
	public Valutazione getValutazione(Connection db, int idAnimale) throws SQLException 
	{
		Valutazione valutazione = new Valutazione();
		if (idAnimale == -1) 
		{
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement(" Select * from get_valutazione_scheda_adozione_cani(?) ");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) 
		{
			valutazione.setPunteggio(rs.getInt(1));
			valutazione.setValutazione(rs.getString(2));
		}

		rs.close();
		pst.close();
		
		return valutazione;
	}
	
	public boolean exist(Connection db, int idAnimale) throws SQLException 
	{
		ArrayList<SchedaAdozione> schede = this.getByIdAnimale(db, idAnimale);
		return schede.size()>0;
	}

	
	public SchedaAdozione(Connection db, ResultSet rs) throws SQLException 
	{
		this.buildRecord(db, rs);
	}

	private SchedaAdozione buildRecord(Connection db, ResultSet rs) throws SQLException 
	{
		SchedaAdozione scheda = new SchedaAdozione();
		if (rs.getInt("id_animale") > 0) 
		{
			scheda.animale = new Animale(db,rs.getInt("id_animale"));
		}
		if (rs.getInt("id_criterio") > 0) 
		{
			Criterio criterio = new Criterio();
			scheda.setCriterio(criterio.getById(db, rs.getInt("id_criterio")));
		}
		if (rs.getInt("id_indice") > 0) 
		{
			Indice indice = new Indice();
			scheda.setIndice(indice.getById(db, rs.getInt("id_indice")));
		}
		if (rs.getInt("entered_by") > 0) 
		{
			scheda.userEnteredBy = new User(db, rs.getInt("entered_by"));
		}
		if (rs.getInt("modified_by") > 0) 
		{
			scheda.userModifiedBy = new User(db, rs.getInt("modified_by"));
		}

		scheda.setAnimale(animale);
		scheda.setUserEnteredBy(userEnteredBy);
		scheda.setUserModifiedBy(userModifiedBy);
		scheda.setId(rs.getInt("id"));
		scheda.setIdAnimale(rs.getInt("id_animale"));
		scheda.setIdCriterio(rs.getInt("id_criterio"));
		scheda.setIdIndice(rs.getInt("id_indice"));
		scheda.setEntered(rs.getTimestamp("entered"));
		scheda.setModified(rs.getTimestamp("modified"));
		scheda.setEnteredBy(rs.getInt("entered_by"));
		scheda.setModifiedBy(rs.getInt("modified_by"));
		
		return scheda;
	}

	
	public int update(Connection conn) throws SQLException 
	{
		int result = -1;
		
		try 
		{
			
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE scheda_adozione_cani SET id_indice=?, modified=now(), modified_by=?  where id = ?");

			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setInt(++i, this.getIdIndice());
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			
			result = pst.executeUpdate();
			pst.close();
			
		} 
		catch (Exception e) 
		{
			throw new SQLException(e.getMessage());
		}

		return result;
	}
	
	public boolean insertStorico(Connection db) throws SQLIntegrityConstraintViolationException, SQLException 
	{

		StringBuffer sql = new StringBuffer();
		
		
		int nextval = DatabaseUtils.getNextSeqPostgres(db, "scheda_adozione_cani_storico_id_seq");
		
		
		sql.append("INSERT INTO scheda_adozione_cani_storico ( select ?, * from scheda_adozione_cani where id = ? )");

		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		pst.setInt(++i, nextval);
		
		if (id > 0) 
		{
			pst.setInt(++i, id);
		}

		pst.execute();
		pst.close();

		return true;

	}

}
