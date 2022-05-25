/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedaMorsicatura.base;

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
import org.aspcfs.modules.schedaAdozioneCani.base.SchedaAdozione;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.beans.GenericBean;

public class SchedaMorsicaturaRecords extends GenericBean 
{

	private static Logger log = Logger.getLogger(SchedaMorsicaturaRecords.class);
	static 
	{
		if (System.getProperty("DEBUG") != null) 
		{
			log.setLevel(Level.DEBUG);
		}
	}
	
	private int id;
	private int idScheda;
	private SchedaMorsicatura scheda;
	private int idIndice;
	private Indice indice;
	private String valoreManuale;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;
	private int enteredBy;
	private User userEnteredBy;
	private int modifiedBy;
	private User userModifiedBy;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdScheda() {
		return idScheda;
	}

	public void setIdScheda(int idScheda) {
		this.idScheda = idScheda;
	}
	
	public SchedaMorsicatura getScheda() {
		return scheda;
	}

	public void setScheda(SchedaMorsicatura scheda) {
		this.scheda = scheda;
	}

	public void setIdScheda(String idScheda) {
		this.idScheda = new Integer(idScheda).intValue();
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
	
	public String getValoreManuale() {
		return valoreManuale;
	}

	public void setValoreManuale(String valoreManuale) {
		this.valoreManuale = valoreManuale;
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
	}
	
	public User getUserModifiedBy() {
		return userModifiedBy;
	}

	public void setUserModifiedBy(User userModifiedBy) 
	{
		this.userModifiedBy = userModifiedBy;
	}
	

	public boolean insert(Connection db) throws SQLIntegrityConstraintViolationException, SQLException 
	{

		StringBuffer sql = new StringBuffer();

		
		id = DatabaseUtils.getNextSeqPostgres(db, "scheda_morsicatura_records_id_seq");

		sql.append("INSERT INTO scheda_morsicatura_records( ");

		if (id > -1) 
		{
			sql.append("id, ");
		}

		sql.append("id_scheda,id_indice,entered,modified, entered_by,modified_by, valore_manuale ) VALUES (");

		if (id > -1) 
		{
			sql.append("?, ");
		}

		sql.append("?,?,now(),now(), ?,?,?)");
		
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		if (id > 0) 
		{
			pst.setInt(++i, id);
		}

		pst.setInt(++i, idScheda);
		pst.setInt(++i, idIndice);
		pst.setInt(++i, enteredBy);
		pst.setInt(++i, modifiedBy);
		pst.setString(++i, valoreManuale);

		pst.execute();
		pst.close();

		this.id = DatabaseUtils.getCurrVal(db, "scheda_morsicatura_records_id_seq", id);

		return true;

	}

	public SchedaMorsicaturaRecords() throws SQLException 
	{}
	
	public SchedaMorsicaturaRecords getById(Connection db, int id) throws SQLException 
	{
		SchedaMorsicaturaRecords scheda = null;
		if (id == -1) 
		{
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select * from scheda_morsicatura_records where id = ?");
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
	
	public SchedaMorsicaturaRecords(Connection db, ResultSet rs) throws SQLException 
	{
		this.buildRecord(db, rs);
	}

	private SchedaMorsicaturaRecords buildRecord(Connection db, ResultSet rs) throws SQLException 
	{
		SchedaMorsicaturaRecords record = new SchedaMorsicaturaRecords();
		if (rs.getInt("id_scheda") > 0) 
		{
			SchedaMorsicatura scheda = new SchedaMorsicatura();
			record.setScheda(scheda.getById(db,rs.getInt("id_scheda")));
		}
		if (rs.getInt("id_indice") > 0) 
		{
			Indice indice = new Indice();
			record.setIndice(indice.getById(db, rs.getInt("id_indice")));
		}

		record.setId(rs.getInt("id"));
		record.setIdIndice(rs.getInt("id_indice"));
		record.setIdScheda(rs.getInt("id_scheda"));
		record.setValoreManuale(rs.getString("valore_manuale"));
		
		return record;
	}

	
	public int update(Connection conn) throws SQLException 
	{
		int result = -1;
		
		try 
		{
			
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE scheda_morsicatura_records SET id_indice=? where id = ?");

			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setInt(++i, this.getIdIndice());
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
		
		
		int nextval = DatabaseUtils.getNextSeqPostgres(db, "scheda_morsicatura_storico_id_seq");
		
		
		sql.append("INSERT INTO scheda_morsicatura_records_storico ( select ?, * from scheda_morsicatura_records_ where id = ? )");

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
	
	
	
	public ArrayList<SchedaMorsicaturaRecords> getByIdScheda(Connection db, int idScheda) throws SQLException 
	{
		ArrayList<SchedaMorsicaturaRecords> records = new ArrayList<SchedaMorsicaturaRecords>();

		PreparedStatement pst = db.prepareStatement(" Select r.* " + 
													" from scheda_morsicatura sc " + 
													" left join scheda_morsicatura_records r on r.id_scheda = sc.id " + 
													" left join lookup_scheda_morsicatura_indici i on i.id = r.id_indice " + 
													" left join lookup_scheda_morsicatura_criteri cr on cr.id = i.id_criterio " + 
													" where sc.id = ? and sc.trashed_date is null " + 
													" order by cr.level ");
		pst.setInt(1, idScheda);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) 
		{
			SchedaMorsicaturaRecords r = buildRecord(db, rs);
			records.add(r);
		}

		rs.close();
		pst.close();
		
		return records;
	}

}
