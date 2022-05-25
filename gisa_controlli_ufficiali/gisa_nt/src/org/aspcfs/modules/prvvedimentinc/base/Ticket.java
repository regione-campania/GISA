/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.prvvedimentinc.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{


	private int idAsl ; 
	public int getIdAsl() {
		return idAsl;
	}


	private String id_controllo_ufficiale ;
	
	
	public String getId_controllo_ufficiale() {
		return id_controllo_ufficiale;
	}


	public void setId_controllo_ufficiale(String id_controllo_ufficiale) {
		this.id_controllo_ufficiale = id_controllo_ufficiale;
	}


	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}


	public String getRagioneSociale() {
		return ragioneSociale;
	}


	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public HashMap<Integer, String> getProvvedimentiAdottati() {
		return provvedimentiAdottati;
	}


	public void setProvvedimentiAdottati(
			HashMap<Integer, String> provvedimentiAdottati) {
		this.provvedimentiAdottati = provvedimentiAdottati;
	}


	public String getIdControlloUfficiale() {
		return idControlloUfficiale;
	}


	public void setIdControlloUfficiale(String idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}

	private String ragioneSociale ; 
	private String note ;
	private HashMap<Integer, String> provvedimentiAdottati = new HashMap<Integer, String>();
	private String idControlloUfficiale ;
	private String identificativo ;
	
	
	public Ticket(ResultSet rs) throws SQLException {
		
		buildRecord(rs);
	}
	public String getIdentificativo() {
		return identificativo;
	}


	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5904102118138677225L;

	public Ticket()
	{

	}


	public HashMap<Integer, String> getListaLimitazioniFollowup() {

		return provvedimentiAdottati;
	}
	public void setListaProvvedimentiAdottati(
			Connection db) throws SQLException {

		String sql="select code,description from prvvodimenti_adottati_no_nc,lookup_provvedimenti_no_nc where prvvodimenti_adottati_no_nc.id_provvedimento=lookup_provvedimenti_no_nc.code and  prvvodimenti_adottati_no_nc.idticket="+id;

		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			int kiave=rs.getInt("code");
			String value=rs.getString("description");
			provvedimentiAdottati.put(kiave, value);

		}

	}


	public Ticket(Connection db,ResultSet rs) throws SQLException {

		buildRecord(rs);
		this.setListaProvvedimentiAdottati(db);
	}



	public Ticket(Connection db, int id) throws SQLException {

		queryRecord(db, id);
	}

	public Ticket(Connection db, int id,boolean farmacosorveglianza) throws SQLException {

		queryRecord(db, id);
	}




	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}

		String sql =    "SELECT t.*,cu.ticketid as id_cu, " +

		"o.site_id AS orgsiteid,o.tipologia as tipologia_operatore,cu.chiusura_attesa_esito " +
		"FROM ticket t " ;
		sql +="LEFT JOIN organization o ON (t.org_id = o.org_id) " ;
		sql +="LEFT JOIN ticket cu  ON (t.id_controllo_ufficiale = cu.id_controllo_ufficiale  and cu.tipologia = 3) " +
		"WHERE t.ticketid = ? AND t.tipologia = 16";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			buildRecord(rs);
			//this.setIdTicketNConformita(db);
			//this.setIdControlloUfficialeTicket(db);
		}
		this.setListaProvvedimentiAdottati(db);
		rs.close();
		pst.close();
		
		
		
	}


	public void insertLimitazioniFollowup(Connection db,String[] s) throws SQLException{

		if(s!=null){

			String sql="insert into prvvodimenti_adottati_no_nc (idticket,id_provvedimento) values(?,?)";
			PreparedStatement pst=db.prepareStatement(sql);
			pst.setInt(1, id);
			for(int i=0;i<s.length;i++){
				pst.setInt(2, Integer.parseInt(s[i]));
				pst.execute();

			}
		}


	}


	public void updateLimitazioneFollowup(Connection db,String[] s)throws SQLException{

		PreparedStatement pst=db.prepareStatement("delete from prvvodimenti_adottati_no_nc where idticket="+id);
		pst.execute();
		this.insertLimitazioniFollowup(db, s);


	}

	public synchronized boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean commit = db.getAutoCommit();
		try {
			if (commit) {
				db.setAutoCommit(false);
			}
			
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
			
			sql.append(
					"INSERT INTO ticket (contact_id, problem, pri_code, " +
					"department_code, cat_code, scode,  link_contract_id, " +
					"link_asset_id, expectation, product_id, customer_product_id, " +
					"key_count, status_id, trashed_date, user_group_id, cause_id, " +
					"resolution_id, defect_id, escalation_level, resolvable, " +
			"resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified, ");

			


			if (idControlloUfficiale != null)
			{
				sql.append("id_controllo_ufficiale,");
			}
			
			
			
				sql.append("org_id,id_stabilimento,");
			


			
				sql.append("ticketid, ");
			if (entered != null) {
				sql.append("entered, ");
			}
			if (modified != null) {
				sql.append("modified, ");
			}
			
			

			sql.append(" enteredBy, modifiedBy, " +
			"tipologia");
			
			sql.append(",identificativo");
			sql.append(")");
			sql.append("VALUES (?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ");
			sql.append("?, ?, ?, ");
			
			if (idControlloUfficiale != null)
			{
				sql.append("?,");
			}
			
				sql.append("?,?,");
			


				sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");

			if (entered != null) {
				sql.append("?, ");
			}
			if (modified != null) {
				sql.append("?, ");
			}


			
		

			sql.append("?, ?, 16, "); //ho aggiunto 2 punti interrogativi
			String asl = null;
			if(siteId==201){
				asl = "AV";	
			}else if(siteId == 202){
				asl = "BN";
			}else if(siteId ==203){
				asl = "CE";
			}else if(siteId ==204){
				asl = "NA1";
			}else if(siteId == 205){
				asl = "NA2Nord";
			}else if(siteId == 206){
				asl = "NA3Sud";
			}else if(siteId ==207){
				asl = "SA";
			}
			else{
				if(siteId ==16){
					asl = "FuoriRegione";
				}


			}
			
			sql.append(" ");
			sql.append(") RETURNING ticketid");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			DatabaseUtils.setInt(pst, ++i, this.getContactId());
			pst.setString(++i, this.getProblem());
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);


			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, assetId);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, statusId);
			DatabaseUtils.setTimestamp(pst, ++i, trashedDate);
			pst.setNull(++i, java.sql.Types.INTEGER);
			DatabaseUtils.setInt(pst, ++i, causeId);
			DatabaseUtils.setInt(pst, ++i, resolutionId);
			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			pst.setNull(++i, java.sql.Types.BOOLEAN);

			pst.setNull(++i, java.sql.Types.INTEGER);
			pst.setNull(++i, java.sql.Types.INTEGER);

			DatabaseUtils.setInt(pst, ++i, this.getStateId());
			DatabaseUtils.setInt(pst, ++i, this.getSiteId());
			pst.setString(++i, super.getIpEntered());
			pst.setString(++i, super.getIpModified());
			
			if (idControlloUfficiale != null)
			{
				pst.setString(++i, idControlloUfficiale);
			}
			
				DatabaseUtils.setInt(pst, ++i, orgId);
				DatabaseUtils.setInt(pst, ++i, idStabilimento);

			

			
			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}
			if (modified != null) {
				pst.setTimestamp(++i, modified);
			} 


		
			

			/**
			 * 
			 */
			



			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			
			ResultSet rs = pst.executeQuery();
			if ( rs.next())
				this.id = rs.getInt("ticketid");
			//Update the rest of the field
			if (commit) {
				db.commit();
			}
			db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();
		} catch (SQLException e) {
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		//ticket table
		this.setId(rs.getInt("ticketid"));

		orgId = DatabaseUtils.getInt(rs, "org_id");
		idStabilimento = DatabaseUtils.getInt(rs, "id_stabilimento");

		idControlloUfficiale = rs.getString("id_controllo_ufficiale");
		contactId = DatabaseUtils.getInt(rs, "contact_id");
		note = rs.getString("problem");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		closed = rs.getTimestamp("closed");
		
		chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");
		tipologia_operatore = rs.getInt("tipologia_operatore");
		if (idStabilimento>0)
			   tipologia_operatore = Ticket.TIPO_OPU;
		location = rs.getString("location");
		assignedDate = rs.getTimestamp("assigned_date");
		estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		resolutionDate = rs.getTimestamp("resolution_date");
		cause = rs.getString("cause");

		assetId = DatabaseUtils.getInt(rs, "link_asset_id");
	
		trashedDate = rs.getTimestamp("trashed_date");
		causeId = DatabaseUtils.getInt(rs, "cause_id");
		resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
		identificativo = rs.getString("identificativo");

		orgSiteId = DatabaseUtils.getInt(rs, "site_id");

	

		setPermission();
	}

	public int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
				"UPDATE ticket " +
				"SET problem = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
						db) + ", modifiedby = ? " +
						"WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setString(++i, problem);
		
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		
		return resultCount;
	}

	public int chiudi(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql =
				"UPDATE ticket " +
				"SET closed = ?, modified = " + DatabaseUtils.getCurrentTimestamp(
						db) + ", modifiedby = ? " +
						"WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, new Timestamp( System.currentTimeMillis() ) );
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}


	

	public int reopen(Connection db) throws SQLException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			String sql = "UPDATE ticket " + "SET closed = ?, modified = "
			+ DatabaseUtils.getCurrentTimestamp(db)
			+ ", modifiedby = ? " + "WHERE ticketid = ? ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			this.setClosed((java.sql.Timestamp) null);
			
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}

	public int update(Connection db) throws SQLException {
		int i = -1;
		try {
			db.setAutoCommit(false);
			i = this.update(db, false);
			db.commit();
			db.setAutoCommit(true);
			//db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return i;
	}





}

