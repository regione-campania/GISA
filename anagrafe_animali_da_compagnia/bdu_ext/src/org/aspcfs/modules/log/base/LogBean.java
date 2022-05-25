/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.log.base;

/* TABELLA LOG

CREATE TABLE log
(
  id serial NOT NULL,
  asl_id integer DEFAULT -1,
  user_id integer DEFAULT -1,
  operazione_id integer DEFAULT -1,
  note character varying,
  data_operazione timestamp without time zone DEFAULT now(),
  ip character varying
) 
WITHOUT OIDS;
ALTER TABLE log OWNER TO postgres;

*/

/* LOOKUP OPERAZIONI
 CREATE TABLE lookup_log_op
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_log_op_pkey PRIMARY KEY (code)
) 
WITHOUT OIDS;
ALTER TABLE lookup_log_op OWNER TO postgres;

*/

/* NUOVO PERMESSO
insert into permission( permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, level, description )
values( 339, 12, 'log-operazioni', true, false, false, false, 10, 'Operazioni Eseguite' );

*/

/*FILE DI CONFIGURAZIONE cfs-config.xml
 <action name="LogOperazioni" class="org.aspcfs.modules.log.actions.Log">
    <bean name="Log" class="org.aspcfs.modules.log.base.LogBean" scope="request"/>
    <forward name="SearchOp" resource="/log/log_search.jsp" layout="nav"/>
    <forward name="DetailOp" resource="/log/log_detail.jsp" layout="nav"/>
    <forward name="ListOp" resource="/log/log_list.jsp" layout="nav"/>
  </action>
*/

/*FILE DI CONFIGURAZIONE cfs-modules
    <action name="LogOperazioni"/>
    <submenu name="LogOp">
      <permission value="log-operazioni-view"/>
      <long_html value="Log Operazioni"/>
      <short_html value="Log Operazioni"/>
      <link value="LogOperazioni.do?command=SearchForm"/>
    </submenu>
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class LogBean {
	
	
	private int       id             = -1;
	private int       aslId          = -1;
	private int       userId         = -1;
	private int       operazioneId   = -1;
	private String    note            = null;
	private String    asl             = null;
	private Timestamp dataOperazione  = null;
	private Timestamp dataOperazioneA = null;
	private String    operazione      = null;
	private String    user            = null;
	private String    ip              = null; 

	
	public LogBean(){}
	
	public LogBean(ResultSet rs) throws SQLException {
	   buildRecord(rs);
	}
	
	public LogBean(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid id");
		} 
		
		PreparedStatement pst = db.prepareStatement
				(
				"SELECT " +
				"l.*, " +
				"lar.description as asl, " +
				"a.username as user, " +
				"lop.description as operazione " +
				"FROM log l " +
				"LEFT JOIN access a ON (l.user_id = a.user_id) " +
				"LEFT JOIN lookup_asl_rif lar ON (l.asl_id = lar.code) " +
				"LEFT JOIN lookup_log_op lop ON (l.operazione_id = lop.code) " +
				"WHERE l.id = ? "
				);
		
		pst.setInt(1, id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		
		if (rs.next()) {
			buildRecord(rs);
		}
		
		rs.close();
		pst.close();
	}
	
	protected void buildRecord(ResultSet rs) throws SQLException {
		operazione     = rs.getString("operazione");
		dataOperazione = rs.getTimestamp("data_operazione");
		operazioneId   = rs.getInt("operazione_id");
		asl            = rs.getString("asl");
		userId         = rs.getInt("user_id");
		aslId          = rs.getInt("asl_id");
	    note           = rs.getString("note");
	    user           = rs.getString("user");
	    id             = rs.getInt("id");
	    ip             = rs.getString("ip");
	}
	
	/**
	* Metodo utilizzato per la memorizzazione del Log ( il TipoOperazione può anche essere -1 )
	* Parametri passati:
	* -- Note 
	* -- Context
	* -- DB 
	*/
	public void store(int tipoOperazione, String notes, ActionContext context,Connection db) throws SQLException {
		UserBean user = (UserBean) context.getSession().getAttribute("User"); 
		if ( user != null ) {
			if ( user.getSiteId() > -1 ){
				aslId  = user.getSiteId();
				userId = user.getUserId();
			}
		}
		operazioneId = tipoOperazione;
		//aslId        = ( (((UserBean) context.getSession().getAttribute("User")) != null) ?  ((UserBean) context.getSession().getAttribute("User")).getAslRifId() : -1 ) ;
		//userId       = ( (((UserBean) context.getSession().getAttribute("User")) != null) ?  ((UserBean) context.getSession().getAttribute("User")).getUserId() : -1 ) ;
		note         = notes;
		ip           = context.getRequest().getRemoteAddr();
		
		store(db);
	}
	
	/**
	* Metodo utilizzato per la memorizzazione del Log ( il TipoOperazione può anche essere -1 )
	* Parametri passati:
	* -- Identificativo Asl
	* -- Identificativo Utente
	* -- Identificativo Operazione ( non necessario anche = -1 )
	* -- Note 
	*/
	public void store(int asl, int user_Id, int tipoOperazione, String notes, ActionContext context,Connection db) throws SQLException {
		operazioneId = tipoOperazione;
		aslId        = asl;
		userId       = user_Id;
		note         = notes;
		ip           = context.getRequest().getRemoteAddr();
		
		store(db);
	}
	
	private boolean store(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();

		try {

			
		
			sql.append
			(
			  "INSERT INTO log ("
			);
			
			if (operazioneId > -1) {
				sql.append(" operazione_id, ");
			}
			
			sql.append( " asl_id, user_id, note, ip ) VALUES ( ");
			
			if (operazioneId > -1) {
				sql.append("?,");
			}
			
			sql.append(" ?, ?, ?, ? ) ");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			if (operazioneId > -1) {
				pst.setInt(++i, operazioneId);
			}
			
			pst.setInt(++i, aslId);
			pst.setInt(++i, userId);
			pst.setString(++i, note);
			pst.setString(++i, ip);
			
			pst.execute();
			pst.close();


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;
	}

	  
	public Timestamp getDataOperazione() {
		return dataOperazione;
	}
	
	public void setDataOperazione(Timestamp dataOperazione) {
		this.dataOperazione = dataOperazione;
	}
	
	public void setDataOperazione(String dataOperazione) {
		this.dataOperazione = DatabaseUtils.parseTimestamp(dataOperazione);
	}
	
	public Timestamp getDataOperazioneA() {
		return dataOperazioneA;
	}
	
	public void setDataOperazioneA(Timestamp dataOperazioneA) {
		this.dataOperazioneA = dataOperazioneA;
	}
	
	public void setDataOperazioneA(String dataOperazioneA) {
		this.dataOperazioneA = DatabaseUtils.parseTimestamp(dataOperazioneA);
	}
	
	public int getAslId() {
		return aslId;
	}
	
	public void setAslId(int aslId) {
		this.aslId = aslId;
	}
	
	public void setAslId(String aslId) {
		this.aslId = Integer.parseInt(aslId);
	}
	
	public int getOperazioneId() {
		return operazioneId;
	}
	
	public void setOperazioneId(int operazioneId) {
		this.operazioneId = operazioneId;
	}
	
	public void setOperazioneId(String operazioneId) {
		this.operazioneId = Integer.parseInt(operazioneId);
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setUserId(String userId) {
		this.userId = Integer.parseInt(userId);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
	    this.note = note;
	}

	public String getAsl() {
		return asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String nomeOperazione) {
		this.operazione = nomeOperazione;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
