/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.rappresentantestabilimento.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;

import org.aspcfs.modules.contacts.base.ContactHistory;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1444892015507779325L;
	public Ticket()
	{
		
		
	}
	
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	protected String pippo = "";
	protected int tipoCampione = -1;
	protected int esitoCampione = -1;
	protected int destinatarioCampione = -1;
	protected Timestamp dataAccettazione = null;
	protected String dataAccettazioneTimeZone = null;
	private String codiceFiscaleRappresentante = null;
	private String nomeRappresentante = null;
	private String cognomeRappresentante = null;
	private String emailRappresentante = null;
	private int titoloRappresentante = 0;
	private String telefonoRappresentante = null;
	private java.sql.Timestamp dataNascitaRappresentante = null;
	private String luogoNascitaRappresentante = null;
	private String fax = null;
	private String name = null;
	private String banca = null;
	private String partitaIva = null;
	private String city_legale_rapp ;
	private String address_legale_rapp ;
	private String prov_legale_rapp;
		public String getCity_legale_rapp() {
			return city_legale_rapp;
		}

		public void setCity_legale_rapp(String city_legale_rapp) {
			this.city_legale_rapp = city_legale_rapp;
		}

		public String getAddress_legale_rapp() {
			return address_legale_rapp;
		}

		public void setAddress_legale_rapp(String address_legale_rapp) {
			this.address_legale_rapp = address_legale_rapp;
		}

		public String getProv_legale_rapp() {
			return prov_legale_rapp;
		}

		public void setProv_legale_rapp(String prov_legale_rapp) {
			this.prov_legale_rapp = prov_legale_rapp;
		}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefonoRappresentante() {
		return telefonoRappresentante;
	}
	public void setTelefonoRappresentante(String tmp) {
	    this.telefonoRappresentante = tmp;
	  }

	
	public String getLuogoNascitaRappresentante() {
		return luogoNascitaRappresentante;
	}

	public void setLuogoNascitaRappresentante(String luogoRappresentante) {
		this.luogoNascitaRappresentante = luogoRappresentante;
	}

	public void setTitoloRappresentante(String tmp) {
	    this.titoloRappresentante = Integer.parseInt(tmp);
	  }

	public int getTitoloRappresentante() {
		return titoloRappresentante;
	}

	public void setTitoloRappresentante(int titoloRappresentante) {
		this.titoloRappresentante = titoloRappresentante;
	}

	public String getCodiceFiscaleRappresentante() {
		return codiceFiscaleRappresentante;
	}

	public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
		this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBanca() {
		return banca;
	}

	public void setBanca(String banca) {
		this.banca = banca;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partita) {
		this.partitaIva = partita;
	}

	public String getNomeRappresentante() {
		return nomeRappresentante;
	}
	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}
	public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}

	public String getEmailRappresentante() {
		return emailRappresentante;
	}

	public void setEmailRappresentante(String emailRappresentante) {
		this.emailRappresentante = emailRappresentante;
	}
	public java.sql.Timestamp getDataNascitaRappresentante() {
		return dataNascitaRappresentante;
	}
	public void setDataNascitaRappresentante(String tmp) {
	    this.dataNascitaRappresentante = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }

	public void setDataNascitaRappresentante(java.sql.Timestamp tmp) {
		this.dataNascitaRappresentante = tmp;
	}
	

	public String getPippo() {
		return pippo;
	}

	public void setPippo(String pippo) {
		this.pippo = pippo;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setDataAccettazioneTimeZone(String tmp) {
	    this.dataAccettazioneTimeZone = tmp;
	  }
	
	 public String getDataAccettazioneTimeZone() {
		    return dataAccettazioneTimeZone;
		  }
	
	 public void setDataAccettazione(java.sql.Timestamp tmp) {
		    this.dataAccettazione = tmp;
		  }


		  /**
		   *  Sets the assignedDate attribute of the Ticket object
		   *
		   * @param  tmp  The new assignedDate value
		   */
		  public void setDataAccettazione(String tmp) {
		    this.dataAccettazione = DatabaseUtils.parseDateToTimestamp(tmp);
		  }
	 
	public java.sql.Timestamp getDataAccettazione() {
			    return dataAccettazione;
			  }

		  
	public int getTipoCampione() {
		return tipoCampione;
	}
	
	public void setTipoCampione(String tipoCampione) {
	    try {
	      this.tipoCampione = Integer.parseInt(tipoCampione);
	    } catch (Exception e) {
	      this.tipoCampione = -1;
	    }
	  }
	
	public int getDestinatarioCampione() {
		return destinatarioCampione;
	}
	
	public void setDestinatarioCampione(String destinatarioCampione) {
	    try {
	      this.destinatarioCampione = Integer.parseInt(destinatarioCampione);
	    } catch (Exception e) {
	      this.destinatarioCampione = -1;
	    }
	  }

	public void setEsitoCampione(String esitoCampione) {
	    try {
	      this.esitoCampione = Integer.parseInt(esitoCampione);
	    } catch (Exception e) {
	      this.esitoCampione = -1;
	    }
	  }

	public void setTipoCampione(int tipoCampione) {
		this.tipoCampione = tipoCampione;
	}
	
	public void setDestinatarioCampione(int destinatarioCampione) {
		this.destinatarioCampione = destinatarioCampione;
	}
	
	public int getEsitoCampione() {
		return esitoCampione;
	}

	public void setEsitoCampione(int esitoCampione) {
		this.esitoCampione = esitoCampione;
	}

	
	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	  public Ticket(ResultSet rs) throws SQLException {
		
	    buildRecord(rs);
	  }

	  public Ticket(Connection db, int id) throws SQLException {
	    queryRecord(db, id);
	  }

	  public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		    PreparedStatement pst = db.prepareStatement(
		        "SELECT t.*, " +
		        "o.name AS orgname, " +
		        "o.enabled AS orgenabled, " +
		        "o.site_id AS orgsiteid, " +
		        
		        "a.serial_number AS serialnumber, " +
		        "a.manufacturer_code AS assetmanufacturercode, " +
		        "a.vendor_code AS assetvendorcode, " +
		        "a.model_version AS modelversion, " +
		        "a.location AS assetlocation, " +
		        "a.onsite_service_model AS assetonsiteservicemodel " +
		      
		        "FROM ticket t " +
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		     
		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
		       
		        "WHERE t.ticketid = ? AND t.tipologia = 4 ");
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		    }
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		  
		   
		  }
	  
	  public boolean insert(Connection db,ActionContext context) throws SQLException {
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
		          "INSERT INTO ticket (contact_id, pri_code, " +
		          "department_code, cat_code, scode, org_id, link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id ");
		      if (name != null && !"".equals( name ) ) {
			        sql.append(",ragionesocialevoltura ");
			      }
		      if (banca != null && !"".equals( banca ) ) {
			        sql.append(",denonimazionevoltura ");
			      }
		      if (partitaIva != null && !"".equals( partitaIva ) ) {
			        sql.append(",partitavoltura ");
			      }
		      if (titoloRappresentante > -1) {
			        sql.append(",id_allerta ");
			      }
		      if ( ( codiceFiscaleRappresentante != null) && !"".equals( codiceFiscaleRappresentante ) ) {
		    	  sql.append(", problem ");
		      }
		      if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
		    	  sql.append(", solution ");
		      }
		      if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
		    	  sql.append(", comment ");
		      }
		      if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
		    	  sql.append(", cause ");
		      }
		      if ( ( telefonoRappresentante != null)&& !"".equals(telefonoRappresentante)) {
		    	  sql.append(", telefono_rappresentante ");
		      }
		      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
		    	  sql.append(", critical ");
		      }
		      if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante )) {
		    	  sql.append(", location ");
		      }
		      if ( ( city_legale_rapp != null) && !"".equals( city_legale_rapp )) {
		    	  sql.append(", city_legale_rapp ");
		      }
		      if ( ( prov_legale_rapp != null) && !"".equals( prov_legale_rapp )) {
		    	  sql.append(", prov_legale_rapp ");
		      }
		      if ( ( address_legale_rapp != null) && !"".equals( address_legale_rapp )) {
		    	  sql.append(", address_legale_rapp ");
		      }
		      
		      
		      
		      if ( ( fax !=null)&& !"".equals(fax)) {
		    	  sql.append(", fax ");
		      }
		      
		        sql.append(",ticketid ");
		      
		      if (entered != null) {
		        sql.append(",entered ");
		      }
		      if (modified != null) {
		        sql.append(",modified ");
		      }
		      sql.append(",tipo_richiesta, custom_data, enteredBy, modifiedBy, " +
		      		"tipologia, sanzioni_penali ");
		      if (dataAccettazione != null) {
			        sql.append(", data_accettazione ");
			      }
		      if (dataAccettazioneTimeZone != null) {
			        sql.append(",data_accettazione_timezone ");
			      }
		      
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		      sql.append("?, ?, ? ");
		      if (name != null && !"".equals( name ) ) {
			        sql.append(",? ");
			      }
		      if (banca != null && !"".equals( banca ) ) {
			        sql.append(",? ");
			      }
		      if (partitaIva != null && !"".equals( partitaIva ) ) {
			        sql.append(",? ");
			      }
		      if (titoloRappresentante > -1) {
		          sql.append(", ? ");
		        }
		      if ( ( codiceFiscaleRappresentante != null) && !"".equals( codiceFiscaleRappresentante) ) {
		          sql.append(", ? ");
		      }
		      if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
		          sql.append(", ? ");
		      }
		      if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
		          sql.append(", ? ");
		      }
		      if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
		          sql.append(", ? ");
		      }
		      if ( ( telefonoRappresentante !=null && !"".equals(telefonoRappresentante))  ) {
		          sql.append(", ? ");
		      }
		      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
		    	  sql.append(", ? ");
		      }
		      if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
		    	  sql.append(", ? ");
		      }
		      if ( ( city_legale_rapp != null) && !"".equals( city_legale_rapp )) {
		    	  sql.append(", ? ");
		      }
		      if ( ( prov_legale_rapp != null) && !"".equals( prov_legale_rapp )) {
		    	  sql.append(", ? ");
		      }
		      if ( ( address_legale_rapp != null) && !"".equals( address_legale_rapp )) {
		    	  	sql.append(", ? ");
		      }
		      
		      if ( ( fax !=null && !"".equals(fax))  ) {
		          sql.append(", ? ");
		      }
		      
				sql.append( DatabaseUtils.getNextIntSql("ticket", "ticketid", livello)+",");

		      
		      if (entered != null) {
		        sql.append(",? ");
		      }
		      if (modified != null) {
		        sql.append(",? ");
		      }
		      sql.append(",?, ?, ?, ?" +
		      		     ",4, ? ");
		      if (dataAccettazione != null) {
			        sql.append(", ? ");
			      }
			  if (dataAccettazioneTimeZone != null) {
			        sql.append(", ? ");
			      }
			  
			  sql.append(") RETURNING ticketid ");
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      DatabaseUtils.setInt(pst, ++i, this.getContactId());
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);
		        pst.setNull(++i, java.sql.Types.INTEGER);

		      DatabaseUtils.setInt(pst, ++i, orgId);
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
		      if ( ( name != null) && !"".equals( name ) ) {
		    	  pst.setString(++i, this.getName());
		      }
		      if ( ( banca != null) && !"".equals( banca ) ) {
		    	  pst.setString(++i, this.getBanca());
		      }
		      if ( ( partitaIva != null) && !"".equals( partitaIva ) ) {
		    	  pst.setString(++i, this.getPartitaIva());
		      }
		      if (titoloRappresentante > -1) {
		          pst.setInt(++i, this.getTitoloRappresentante());
		        }
		      if ( ( codiceFiscaleRappresentante != null) && !"".equals( codiceFiscaleRappresentante ) ) {
		    	  pst.setString(++i, this.getCodiceFiscaleRappresentante());
		      }
		      if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
		    	  pst.setString(++i, this.getNomeRappresentante());
		      }
		      if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
		    	  pst.setString(++i, this.getCognomeRappresentante());
		      }
		      if ( ( emailRappresentante != null) && !"".equals( emailRappresentante ) ) {
		    	  pst.setString(++i, this.getEmailRappresentante());
		      }
		      if ( ( telefonoRappresentante !=null)&& !"".equals(telefonoRappresentante)) {
		    	  pst.setString(++i, this.getTelefonoRappresentante());
		      }
		      if ((dataNascitaRappresentante != null)&&(!dataNascitaRappresentante.equals(""))) {
		    	  pst.setTimestamp(++i, this.getDataNascitaRappresentante());
		      }
		      if ( ( luogoNascitaRappresentante != null) && !"".equals( luogoNascitaRappresentante ) ) {
		    	  pst.setString(++i, this.getLuogoNascitaRappresentante());
		      }
		      if ( ( city_legale_rapp != null) && !"".equals( city_legale_rapp )) {
		    	  pst.setString(++i, this.getCity_legale_rapp());
		      }
		      if ( ( prov_legale_rapp != null) && !"".equals( prov_legale_rapp )) {
		    	  pst.setString(++i, this.getProv_legale_rapp());
		      }
		      if ( ( address_legale_rapp != null) && !"".equals( address_legale_rapp )) {
		    	  pst.setString(++i, this.getAddress_legale_rapp());
		      }
		      if ( ( fax !=null)&& !"".equals(fax)) {
		    	  pst.setString(++i, this.getFax());
		      }
		      
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } 
		      
		      /**
		       * 
		       */
		      pst.setString( ++i, this.getTipo_richiesta() );
		     
		      pst.setString( ++i, this.getPippo() );
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		       DatabaseUtils.setInt(pst, ++i, destinatarioCampione);
		      if (dataAccettazione != null) {
			        pst.setTimestamp(++i, dataAccettazione);
			      }
		      if (dataAccettazioneTimeZone != null) {
			        pst.setString(++i, dataAccettazioneTimeZone);
			      }
		      
		      ResultSet rs = pst.executeQuery();
		      if (rs.next())
		    	  this.id = rs.getInt("ticketid");
		      
		      pst.close();
		      
		      Organization volgere = new Organization(db,orgId);
	            volgere.setVoltura(true);
	            volgere.update( db,context );
		      
		      
		      //Update the rest of the fields
		      this.update(db, true);
		     
		      if (commit) {
		        db.commit();
		      }
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
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    city_legale_rapp = rs.getString("city_legale_rapp");
		    prov_legale_rapp = rs.getString("prov_legale_rapp");
		    address_legale_rapp = rs.getString("address_legale_rapp");
		    closed = rs.getTimestamp("closed");
		   
		    location = rs.getString("location");
		    assignedDate = rs.getTimestamp("assigned_date");
		    estimatedResolutionDate = rs.getTimestamp("est_resolution_date");
		    resolutionDate = rs.getTimestamp("resolution_date");
		    assetId = DatabaseUtils.getInt(rs, "link_asset_id");
		   
		    estimatedResolutionDateTimeZone = rs.getString("est_resolution_date_timezone");
		    assignedDateTimeZone = rs.getString("assigned_date_timezone");
		    resolutionDateTimeZone = rs.getString("resolution_date_timezone");
		    statusId = DatabaseUtils.getInt(rs, "status_id");
		    trashedDate = rs.getTimestamp("trashed_date");
	
		    causeId = DatabaseUtils.getInt(rs, "cause_id");
		    resolutionId = DatabaseUtils.getInt(rs, "resolution_id");
		   
		    stateId = DatabaseUtils.getInt(rs, "state_id");
		    siteId = DatabaseUtils.getInt(rs, "site_id");
		    tipo_richiesta = rs.getString( "tipo_richiesta" );
		    pippo = rs.getString( "custom_data" );
		    tipologia = rs.getInt( "tipologia" );
		    destinatarioCampione = DatabaseUtils.getInt(rs, "sanzioni_penali");
		    dataAccettazione = rs.getTimestamp( "data_accettazione" );
		    dataAccettazioneTimeZone = rs.getString("data_accettazione_timezone");
		    titoloRappresentante = rs.getInt("id_allerta");
		    name = rs.getString("ragionesocialevoltura");
		    banca = rs.getString("denonimazionevoltura");
		    partitaIva = rs.getString("partitavoltura");
			codiceFiscaleRappresentante = rs.getString("problem");
			nomeRappresentante = rs.getString("comment");
			cognomeRappresentante = rs.getString("solution");
			emailRappresentante = rs.getString("cause");
			telefonoRappresentante = rs.getString("telefono_rappresentante");
			dataNascitaRappresentante = rs.getTimestamp("critical");
			luogoNascitaRappresentante = rs.getString("location");
		    fax = rs.getString("fax");
		    //organization table
		    companyName = rs.getString("orgname");
		   
		    contractStartDate = rs.getTimestamp("contractstartdate");
		    contractEndDate = rs.getTimestamp("contractenddate");
		   

		    //asset table
		    assetSerialNumber = rs.getString("serialnumber");
		    assetManufacturerCode = DatabaseUtils.getInt(rs, "assetmanufacturercode");
		    assetVendorCode = DatabaseUtils.getInt(rs, "assetvendorcode");
		    assetModelVersion = rs.getString("modelversion");
		    assetLocation = rs.getString("assetlocation");
		    assetOnsiteResponseModel = DatabaseUtils.getInt(
		        rs, "assetonsiteservicemodel");

		 
		  }
	  
	  public int update(Connection db, boolean override) throws SQLException {
		    int resultCount = 0;
		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    sql.append(
		        "UPDATE ticket " +
		        "SET link_asset_id = ?, department_code = ?, " +
		        "pri_code = ?, scode = ?, " +
		        "cat_code = ?, assigned_to = ?, " +
		        "subcat_code1 = ?, telefono_rappresentante = ?, fax = ?, " +
		        "source_code = ?, ragionesocialevoltura=?, denonimazionevoltura=?, partitavoltura=?, id_allerta=?,contact_id = ?, problem = ?, " +
		        "status_id = ?, trashed_date = ?, site_id = ? , ");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		  
		    if (orgId != -1) {
		      sql.append(" org_id = ?, ");
		    }
		    if ( ( city_legale_rapp != null) && !"".equals( city_legale_rapp )) {
		    	  sql.append("city_legale_rapp  = ? ,");
		      }
		      if ( ( prov_legale_rapp != null) && !"".equals( prov_legale_rapp )) {
		    	  sql.append(" prov_legale_rapp = ? , ");
		      }
		      if ( ( address_legale_rapp != null) && !"".equals( address_legale_rapp )) {
		    	  sql.append(" address_legale_rapp = ? , ");
		      }
		    
		    
		    sql.append(
		        "solution = ?, location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
		        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
		        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
		        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +
		        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, sanzioni_penali = ?, data_accettazione = ?, data_accettazione_timezone = ?, comment = ?, critical = ? " +
		        "WHERE ticketid = ? AND tipologia = 4");
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
		    
		    DatabaseUtils.setInt(pst, ++i, this.getAssetId());
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);

		   
		  pst.setString(++i, this.getName());
		    pst.setString(++i, this.getBanca());
		    pst.setString(++i, this.getPartitaIva());
		    DatabaseUtils.setInt(pst, ++i, this.getTitoloRappresentante());
		    DatabaseUtils.setInt(pst, ++i, this.getContactId());
		    pst.setString(++i, this.getCodiceFiscaleRappresentante());
		    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
		    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		    if (!override) {
		      pst.setInt(++i, this.getModifiedBy());
		    }
		  
		    if (orgId != -1) {
		      DatabaseUtils.setInt(pst, ++i, orgId);
		    }
		    if ( ( city_legale_rapp != null) && !"".equals( city_legale_rapp )) {
		    	  pst.setString(++i, this.getCity_legale_rapp());
		      }
		      if ( ( prov_legale_rapp != null) && !"".equals( prov_legale_rapp )) {
		    	  pst.setString(++i, this.getProv_legale_rapp());
		      }
		      if ( ( address_legale_rapp != null) && !"".equals( address_legale_rapp )) {
		    	  pst.setString(++i, this.getAddress_legale_rapp());
		      }
		    pst.setString(++i, this.getCognomeRappresentante());
		    pst.setString(++i, this.getLuogoNascitaRappresentante());
		    DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
		    pst.setString(++i, this.assignedDateTimeZone);
		    DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
		    pst.setString(++i, estimatedResolutionDateTimeZone);
		    DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
		    pst.setString(++i, this.resolutionDateTimeZone);
		    pst.setString(++i, this.getEmailRappresentante());
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);

		    DatabaseUtils.setInt(pst, ++i, causeId);
		    DatabaseUtils.setInt(pst, ++i, resolutionId);
		      pst.setNull(++i, java.sql.Types.INTEGER);
		    DatabaseUtils.setInt(pst, ++i, this.getStateId());
		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.BOOLEAN);

		      pst.setNull(++i, java.sql.Types.INTEGER);
		      pst.setNull(++i, java.sql.Types.INTEGER);

		    DatabaseUtils.setInt(pst, ++i, destinatarioCampione);
		    pst.setTimestamp(++i, dataAccettazione );
		    pst.setString(++i, dataAccettazioneTimeZone );
		    pst.setString(++i, this.getNomeRappresentante());
		    pst.setTimestamp(++i, this.getDataNascitaRappresentante() );
		    pst.setInt(++i, id);
		   
		    resultCount = pst.executeUpdate();
		    pst.close();
		   
		   
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
		      this.setClosed((java.sql.Timestamp) null);
		     
		      db.commit();
		    } catch (SQLException e) {
		      db.rollback();
		      throw new SQLException(e.getMessage());
		    } finally {
		      db.setAutoCommit(true);
		    }
		    return resultCount;
		  }


		public boolean delete(Connection db, String baseFilePath)
		throws SQLException {
	if (this.getId() == -1) {
		throw new SQLException("Ticket ID not specified.");
	}
	boolean commit = db.getAutoCommit();
	try {
		if (commit) {
			db.setAutoCommit(false);
		}
		
		PreparedStatement pst = null ;

		// Delete the ticket
		pst = db.prepareStatement("DELETE FROM ticket WHERE ticketid = ?");
		pst.setInt(1, this.getId());
		pst.execute();
		pst.close();
		if (commit) {
			db.commit();
		}
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
				// Update the ticket log
				
				db.commit();
			} catch (SQLException e) {
				db.rollback();
				throw new SQLException(e.getMessage());
			} finally {
				db.setAutoCommit(true);
			}
			return resultCount;
		}
		
		
	  
}

