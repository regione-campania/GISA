/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;

import it.us.web.util.properties.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "utenti_super_", schema = "public")
// @Where( clause = "trashed_date is null" )
public class SuperUtenteAll implements java.io.Serializable, Comparable<SuperUtenteAll>
{
	private static final long serialVersionUID = -2347427266119848332L;
	
	private int id;
	private String password;
	private String username;
	private Date entered;
	private Integer enteredBy;
	private Date modified;
	private Integer modifiedBy;
	private Date trashedDate;
	private boolean enabled;
	private Date enabledDate;
	private Date dataScadenza;
	private Date lastLogin;
	
	/**
	 * GESTIONE GEOLOCALIZZAZIONE
	 */
	private Double accessPositionLat;
	private Double accessPositionLon;
	private String accessPositionErr;
	
	private String note;
	
	private List<BUtenteAll> utenti = new ArrayList<BUtenteAll>(0);

	
	public SuperUtenteAll()
	{
		
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Column(name = "entered_by")
	public Integer getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@Override
	public int compareTo(SuperUtenteAll o)
	{
		return this.toString().compareTo( o.toString() );
	}
	
	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "enabled")
	@NotNull
	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "enabled_date", length = 29)
	public Date getEnabledDate() {
		return this.enabledDate;
	}

	public void setEnabledDate(Date enabledDate) {
		this.enabledDate = enabledDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_scadenza", length = 29)
	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	
	
	@Column(name = "access_position_lat")
	public Double getAccessPositionLat() {
		return accessPositionLat;
	}

	public void setAccessPositionLat(Double accessPositionLat) {
		this.accessPositionLat = accessPositionLat;
	}
	
	
	  public void setAccessPositionLat(String lat)
		{
			if (!lat.equals(""))
			{
				accessPositionLat = Double.parseDouble(lat);
			}
		}

	@Column(name = "access_position_lon")
	public Double getAccessPositionLon() {
		return accessPositionLon;
	}

	
	public void setAccessPositionLon(Double accessPositionLon) {
		this.accessPositionLon = accessPositionLon;
	}
	
	public void setAccessPositionLon(String lon)
	{
		if (!lon.equals(""))
		{
			accessPositionLon = Double.parseDouble(lon);
		}
	}

	
	@Column(name = "access_position_err")
	public String getAccessPositionErr() {
		return accessPositionErr;
	}

	public void setAccessPositionErr(String accessPositionErr) {
		this.accessPositionErr = accessPositionErr;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "superutente")
	@OrderBy(clause="clinica, id")
	@Where( clause = "enabled" )
	@Fetch (FetchMode.SELECT) 
	public List<BUtenteAll> getUtenti() {
		return this.utenti;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setUtenti(List<BUtenteAll> utenti) {
		this.utenti = utenti;
	}
	
	@Transient
	public String toString()
	{
		BUtenteAll utente = null;
		if(!getUtenti().isEmpty())
		{
			utente = getUtenti().get(0);
			return utente.toString();
		}
		else 
			return "";
	}

	/*public Object[] check_login_time(String username) {
		
		Connection conn = null;
		Context ctx;
		Object[] result = new Object [2];
		try {
			ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
			conn = ds.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			
			StringBuffer sql = new StringBuffer();
			String nome_tabella = Application.get("NOME_TABELLA");
			String nome_colonna = Application.get("NOME_COLONNA");
			String timeout = Application.get("TIMEOUT");
			sql.append("SELECT count(*) as tot, to_char("+nome_colonna+",'dd-mm-yyyy') as ultimo_accesso from " +nome_tabella +" a where a.username =  ? and a.trashed_date is null and enabled and "+nome_colonna+" >= (now() - "+timeout +") group by "+nome_colonna);
			pst = conn.prepareStatement(sql.toString());
			pst.setString(1,username);
			rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getInt("tot") > 0)
					result[0] = "true";
				 result[1] = rs.getString("ultimo_accesso");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}*/
}
