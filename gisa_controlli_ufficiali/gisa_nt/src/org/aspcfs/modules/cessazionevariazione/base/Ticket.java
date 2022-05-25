/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.cessazionevariazione.base;

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
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;
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
	private String operazione ;
	private String inserito_da ;
	private int statoVoltura ;
	private String motivazione ;
	
	private SoggettoFisico rappresentateImpresa ; 
	private SoggettoFisico rappresentateStabilimento ; 
	
	private int idRappresentanteLegaleOperatore ;
	private int idRappresentanteLegaleStabilimento ;
	
	
	
	private HashMap<Integer, Boolean> volture_asl_coinvolte = new HashMap<Integer, Boolean>();
	public SoggettoFisico getRappresentateImpresa() {
		return rappresentateImpresa;
	}

	public void setRappresentateImpresa(SoggettoFisico rappresentateImpresa) {
		this.rappresentateImpresa = rappresentateImpresa;
	}

	public SoggettoFisico getRappresentateStabilimento() {
		return rappresentateStabilimento;
	}

	public void setRappresentateStabilimento(
			SoggettoFisico rappresentateStabilimento) {
		this.rappresentateStabilimento = rappresentateStabilimento;
	}

	public int getIdRappresentanteLegaleOperatore() {
		return idRappresentanteLegaleOperatore;
	}

	public void setIdRappresentanteLegaleOperatore(
			int idRappresentanteLegaleOperatore) {
		this.idRappresentanteLegaleOperatore = idRappresentanteLegaleOperatore;
	}

	public int getIdRappresentanteLegaleStabilimento() {
		return idRappresentanteLegaleStabilimento;
	}

	public void setIdRappresentanteLegaleStabilimento(
			int idRappresentanteLegaleStabilimento) {
		this.idRappresentanteLegaleStabilimento = idRappresentanteLegaleStabilimento;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public int getStatoVoltura() {
		return statoVoltura;
	}

	public void setStatoVoltura(int statoVoltura) {
		this.statoVoltura = statoVoltura;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
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
	  public void update_data_voltura_iniziale(Connection db)
	  {
		  try
		  {
			PreparedStatement pst =  db.prepareStatement("update ticket set assigned_date = ? where ticketid = ?");
			pst.setTimestamp(1,assignedDate);
			pst.setInt(2,this.id);
			pst.execute();
		  }
		  catch (Exception e) {
			// TODO: handle exception
		}
	  }

	  
	  public boolean approvaVolturaOpuAsl(Connection db,int idAsl)
	  {
		  boolean flag_approvata = true ;
		  try
		  {
			  
			  String update = "update volture_asl_coinvolte set approvata=true,approvata_da = ? ,approvata_data = current_Timestamp where id_voltura = ? and id_asl = ? ";
			  PreparedStatement pst = db.prepareStatement(update);
			  pst.setInt(1, modifiedBy);
			  pst.setInt(2, this.id);
			  pst.setInt(3, idAsl);
			  pst.execute();
			  
			  String sel = "select * from volture_asl_coinvolte where id_voltura = ? and (approvata=false or approvata is null)" ;
			  pst = db.prepareStatement(sel);
			  pst.setInt(1, this.id);
			  ResultSet rs = pst.executeQuery();
			  if (rs.next())
			  {
				  flag_approvata = false ;
			  }
			  
		  }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
		  }
		  return flag_approvata ;
	  }
	  
	  
	  public void popolaVoltureAslCoinvolte (Connection db)
	  {
		  try
		  {
			  PreparedStatement pst = null ;
			  String sel = "select * from volture_asl_coinvolte where id_voltura =?";
			  pst = db.prepareStatement(sel);
			  pst.setInt(1, this.getId());
			  ResultSet rs = pst.executeQuery();
			  while (rs.next())
			  {
				  volture_asl_coinvolte.put(rs.getInt("id_asl"), rs.getBoolean("approvata"));
			  }
		  }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
		  }
		  
	  }
	  
	  
	  
	  public HashMap<Integer, Boolean> getVolture_asl_coinvolte() {
		return volture_asl_coinvolte;
	}

	  public void updateStaoVoltura(Connection db)
	  {
		  try
		  {
			  	String update = "update ticket set operazione =? where ticketid = ? ";
			  	PreparedStatement pst = db.prepareStatement(update);
			  	pst.setString(1, operazione);
			  	pst.setInt(2, id);
			  	pst.execute();
		  }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
		  }
	  }
	public void setVolture_asl_coinvolte(
			HashMap<Integer, Boolean> volture_asl_coinvolte) {
		this.volture_asl_coinvolte = volture_asl_coinvolte;
	}

	public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		    PreparedStatement pst = db.prepareStatement(
		        "SELECT t.*,(cc.namefirst ||' '|| cc.namelast) as inserito_da , " +
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
		        "LEFT JOIN contact cc ON (t.enteredby = cc.user_id) " +
		       
		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
		      
		        "WHERE t.ticketid = ? AND t.tipologia = 4 ");
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		      SoggettoFisico rappImpresa =  new SoggettoFisico();
		      rappImpresa.queryRecordStorico(db, idRappresentanteLegaleOperatore);
		      
		      SoggettoFisico rapStab =  new SoggettoFisico();
		      rapStab.queryRecordStorico(db, idRappresentanteLegaleStabilimento);
		      
		  	this.setRappresentateImpresa(rappImpresa);
			this.setRappresentateStabilimento(rapStab);
		    }
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		  
		
		  }
	  
	  
	  public boolean rinsertRichiestaVoltura (Connection db,StabilimentoList listaStabilimenti,int idAsl)
	  {
		  try
		  {
			  String insert = "insert into volture_asl_coinvolte (id_voltura,id_asl) values (?,?)" ;
			  PreparedStatement pst = null ;
			  pst=db.prepareStatement(insert);
			  Iterator<Stabilimento> itStab = listaStabilimenti.iterator();
			  while (itStab.hasNext())
			  {
				  Stabilimento thisStab = itStab.next();
				  if (thisStab.getIdAsl()!=idAsl)
				  {
					  pst.setInt(1, this.id);
					  pst.setInt(2, thisStab.getIdAsl());
					  pst.execute();
				  }
			  }
			  
			  
		  }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
		  }
		  
		  return true ;
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
			id = DatabaseUtils.getNextInt( db, "ticket","ticketid",livello);		      
		      sql.append(
		          "INSERT INTO ticket (contact_id, pri_code, " +
		          "department_code, cat_code, scode, link_contract_id, " +
		          "link_asset_id, expectation, product_id, customer_product_id, " +
		          "key_count, status_id, trashed_date, user_group_id, cause_id, " +
		          "resolution_id, defect_id, escalation_level, resolvable, " +
		          "resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified ");
		      if (name != null && !"".equals( name ) ) {
			        sql.append(",ragionesocialevoltura ");
			      }
		      
		      
		      if (idStabilimento>0)
		      {
		    	  sql.append(",id_stabilimento ");
		      }
		      else
		      {
		    	  sql.append(",org_id ");
		      }
		      
		      if (idRappresentanteLegaleOperatore>0)
		      {
		    	  sql.append(",id_soggetto_fisico_operatore_storico ");
		      }
		      
		   
		      
		      
		      if (idRappresentanteLegaleStabilimento>0)
		      {
		    	  sql.append(",id_soggetto_fisico_stabilimento_storico ");
		      }
		      
		      if (operazione != null && !"".equals( operazione ) ) {
			        sql.append(",operazione ");
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
		      if ( ( fax !=null)&& !"".equals(fax)) {
		    	  sql.append(", fax ");
		      }
		      if (id > -1) {
		        sql.append(",ticketid ");
		      }
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
		      if(statoVoltura>=0)
		    	  sql.append(",stato_voltura ");
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?, ");
		      sql.append("?, ?, ? ");
		      if (name != null && !"".equals( name ) ) {
			        sql.append(",? ");
			      }
		      
		      if (idStabilimento>0)
		      {
		    	   sql.append(",? ");
		      }
		      else
		      {
		    	   sql.append(",? ");
		      }
		      if (idRappresentanteLegaleOperatore>0)
		      {
			        sql.append(",? ");

		      }
		      
		      if (idRappresentanteLegaleStabilimento>0)
		      {
			        sql.append(",? ");

		      }
		      
		      if (operazione != null && !"".equals( operazione ) ) {
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
		      if ( ( fax !=null && !"".equals(fax))  ) {
		          sql.append(", ? ");
		      }
		      if (id > -1) {
		        sql.append(",?");
		      }
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
			  if(statoVoltura>=0)
		    	  sql.append(",? ");
			  sql.append(")");
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      DatabaseUtils.setInt(pst, ++i, this.getContactId());
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
		      if ( ( name != null) && !"".equals( name ) ) {
		    	  pst.setString(++i, this.getName());
		      }
		      
		      if (idStabilimento>0)
		      {
			      DatabaseUtils.setInt(pst, ++i, idStabilimento);
		      }
		      else
		      {
			      DatabaseUtils.setInt(pst, ++i, orgId);
		      }
		      if (idRappresentanteLegaleOperatore>0)
		      {
			      DatabaseUtils.setInt(pst, ++i, idRappresentanteLegaleOperatore);


		    	 
		      }
		      
		      if (idRappresentanteLegaleStabilimento>0)
		      {
			      DatabaseUtils.setInt(pst, ++i, idRappresentanteLegaleStabilimento);


		    
		      }
		      
		      
		      if (operazione != null && !"".equals( operazione ) ) {
		    	  pst.setString(++i, this.getOperazione());
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
		      if ( ( fax !=null)&& !"".equals(fax)) {
		    	  pst.setString(++i, this.getFax());
		      }
		      if (id > -1) {
		        pst.setInt(++i, id);
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
		      if(statoVoltura>=0)
		    	  pst.setInt(++i, statoVoltura);
		      pst.execute();
		      
		      pst.close();
		      
//		      if (orgId>0)
//		      {
//		      Organization volgere = new Organization(db,orgId);
//	            volgere.setVoltura(true);
//	            volgere.update( db );
//		      }
		      
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
		    closed = rs.getTimestamp("closed");
		    idStabilimento = rs.getInt("id_stabilimento");
		    idRappresentanteLegaleOperatore = rs.getInt("id_soggetto_fisico_operatore_storico");
		    idRappresentanteLegaleStabilimento = rs.getInt("id_soggetto_fisico_stabilimento_storico");
		    super.setInserita_da(rs.getString("inserito_da"));
		    statoVoltura = rs.getInt("stato_voltura");
		    operazione = rs.getString("operazione");
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
		    orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");
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
		        "source_code = ?, ragionesocialevoltura=?, denonimazionevoltura=?, partitavoltura=?, id_allerta=?,contact_id = ?, problem = ?,ip_modified='"+super.getIpModified()+"', " +
		        "status_id = ?, trashed_date = ?, site_id = ? , ");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		    if(statoVoltura>0)
		    	sql.append("stato_voltura=? ,");
		
		    if (orgId != -1) {
		      sql.append(" org_id = ?, ");
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

    	    pst.setString(++i, this.getTelefonoRappresentante());
		    pst.setString(++i, this.getFax());
		    
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
		    if(statoVoltura>0)
		    	pst.setInt(++i, statoVoltura);
		   
		    if (orgId != -1) {
		      DatabaseUtils.setInt(pst, ++i, orgId);
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
		      //Update the ticket log
		    
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
		

		// delete all history data
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
		
		public void aggiornaStatoVoltura(Connection db)
		{
			try
			{
				PreparedStatement pst =  db.prepareStatement("update ticket set stato_voltura = ? , motivazione = ? ,operazione = ? where ticketid = ?");
				pst.setInt(1, statoVoltura);
				pst.setString(2, motivazione);
				pst.setString(3, operazione);
				pst.setInt(4, this.getId());
				pst.execute();
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
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

