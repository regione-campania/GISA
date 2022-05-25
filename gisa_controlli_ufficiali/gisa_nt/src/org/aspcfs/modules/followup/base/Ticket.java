/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.followup.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemList;

public class Ticket extends org.aspcfs.modules.troubletickets.base.Ticket
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5904102118138677225L;
	public static final int NC_FORMALI = 1;
	public static final int NC_SIGNIFICATIVE = 2;
	public static final int NC_GRAVI = 3;
	public Ticket()
	{
		
	}
	private boolean farmacosorveglianza = false ;
	
	
	public boolean isFarmacosorveglianza() {
		return farmacosorveglianza;
	}
	public void setFarmacosorveglianza(boolean farmacosorveglianza) {
		this.farmacosorveglianza = farmacosorveglianza;
	}
	private String noteFollowup;
	public String getNoteFollowup() {
		return noteFollowup;
	}
	public void setNoteFollowup(String noteFollowup) {
		this.noteFollowup = noteFollowup;
	}
	private boolean followup=false;
	public boolean isFollowup() {
		return followup;
	}
	public void setFollowup(boolean followup) {
		this.followup = followup;
	}

	private int id_nonconformita=-1;
	public int getId_nonconformita() {
		return id_nonconformita;
	}
	public void setId_nonconformita(int id_nonconformita) {
		this.id_nonconformita = id_nonconformita;
	}
	
	private String valutazione ="";
	public String getValutazione() {
		return valutazione;
	}
	
	public void setValutazione(String val) {
		this.valutazione = val;
	}
	
	private String identificativonc="";
	public String getIdentificativonc() {
		return identificativonc;
	}
	public void setIdentificativonc(String identificativonc) {
		this.identificativonc = identificativonc;
	}
	
	protected HashMap<Integer, String> listaLimitazioniFollowup=new HashMap<Integer, String>();
	
	protected String tipo_richiesta = "";
	protected int tipologia = -1;
	//protected String dati_extra = "";
	
	protected int provvedimenti = -1;
	protected int followupAmministrative = -1;
	protected int followupPenali = -1;
	protected int sequestri = -1;
	protected String descrizione1 = "";
	protected String descrizione2 = "";
	protected String descrizione3 = "";
	
	//aggiunto da d.dauria per gestire i sequestri
    private boolean tipoSequestro = false;
    private boolean tipoSequestroDue = false;
    private boolean tipoSequestroTre = false;
    private boolean tipoSequestroQuattro = false; 
    private String testoAppoggio = "";
    private int punteggio = 0;
	private String idControlloUfficiale = null;
	private String identificativo = null;
    private String descrizioneProvvedimenti ;
    private int tipo_nc ;
   
    
	public int getTipo_nc() {
		return tipo_nc;
	}
	public void setTipo_nc(int tipo_nc) {
		this.tipo_nc = tipo_nc;
	}
	public String getDescrizioneProvvedimenti() {
		return descrizioneProvvedimenti;
	}
	public void setDescrizioneProvvedimenti(String descrizioneProvvedimenti) {
		this.descrizioneProvvedimenti = descrizioneProvvedimenti;
	}
	public void setPunteggio(String temp)
	{
		this.punteggio = Integer.parseInt(temp);
	}
	public void setPunteggio(int temp)
	{
		this.punteggio = temp;
	}
	public int getPunteggio() {
		return punteggio;
	}
	
	public String getIdentificativo() {
		return identificativo;
	}
	
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getIdControlloUfficiale() {
		return idControlloUfficiale;
	}

	public void setIdControlloUfficiale(String idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}
    public boolean getTipoSequestro() {
		return tipoSequestro;
	}

	public void setTipoSequestro(boolean tipoSequestro) {
		this.tipoSequestro = tipoSequestro;
	}
	
	public void setTipoSequestro(String temp) {
		this.tipoSequestro = DatabaseUtils.parseBoolean(temp);
	}

	public HashMap<Integer, String> getListaLimitazioniFollowup() {
		
		return listaLimitazioniFollowup;
	}
	public void setListaLimitazioniFollowup(
			Connection db) throws SQLException {
		
		String sql="select code,description from limitazioni_followup,lookup_followup where limitazioni_followup.idlimitazione=lookup_followup.code and  limitazioni_followup.idticket="+id;
		
		PreparedStatement pst=db.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			int kiave=rs.getInt("code");
			String value=rs.getString("description");
			listaLimitazioniFollowup.put(kiave, value);
		
		}
		
	}
	public boolean getTipoSequestroDue() {
		return tipoSequestroDue;
	}

	public void setTipoSequestroDue(boolean tipoSequestroDue) {
		this.tipoSequestroDue = tipoSequestroDue;
	}
	
	public void setTipoSequestroDue(String temp) {
		this.tipoSequestroDue = DatabaseUtils.parseBoolean(temp);
	}

	public boolean getTipoSequestroTre() {
		return tipoSequestroTre;
	}

	public void setTipoSequestroTre(boolean tipoSequestroTre) {
		this.tipoSequestroTre = tipoSequestroTre;
	}

	public void setTipoSequestroTre(String temp) {
		this.tipoSequestroTre = DatabaseUtils.parseBoolean(temp);
	}
	
	public boolean getTipoSequestroQuattro() {
		return tipoSequestroQuattro;
	}

	public void setTipoSequestroQuattro(boolean tipoSequestroQuattro) {
		this.tipoSequestroQuattro = tipoSequestroQuattro;
	}
	
	public void setTipoSequestroQuattro(String temp) {
		this.tipoSequestroQuattro = DatabaseUtils.parseBoolean(temp);
	}

    

	
	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public int getProvvedimenti() {
		return provvedimenti;
	}
	
	public void setProvvedimenti(String provvedimenti) {
	    try {
	      this.provvedimenti = Integer.parseInt(provvedimenti);
	    } catch (Exception e) {
	      this.provvedimenti = -1;
	    }
	  }

	public void setFollowupAmministrative(String followupAmministrative) {
	    try {
	      this.followupAmministrative = Integer.parseInt(followupAmministrative);
	    } catch (Exception e) {
	      this.followupAmministrative = -1;
	    }
	  }
	
	public void setFollowupPenali(String followupPenali) {
	    try {
	      this.followupPenali = Integer.parseInt(followupPenali);
	    } catch (Exception e) {
	      this.followupPenali = -1;
	    }
	  }
	
	public void setSequestri(String sequestri) {
	    try {
	      this.sequestri = Integer.parseInt(sequestri);
	    } catch (Exception e) {
	      this.sequestri = -1;
	    }
	  }

	public void setProvvedimenti(int provvedimenti) {
		this.provvedimenti = provvedimenti;
	}
	
	public int getFollowupAmministrative() {
		return followupAmministrative;
	}

	public void setFollowupAmministrative(int followupAmministrative) {
		this.followupAmministrative = followupAmministrative;
	}
	
	public int getFollowupPenali() {
		return followupPenali;
	}

	public void setFollowupPenali(int followupPenali) {
		this.followupPenali = followupPenali;
	}
	
	public int getSequestri() {
		return sequestri;
	}

	public void setSequestri(int sequestri) {
		this.sequestri = sequestri;
	}

	
	public String getTipo_richiesta() {
		return tipo_richiesta;
	}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	  public Ticket(Connection db,ResultSet rs) throws SQLException {
	    buildRecord(rs);
	    this.setListaLimitazioniFollowup(db);
	  }
	  
	  

	  public Ticket(Connection db, int id) throws SQLException {
	    queryRecord(db, id);
	  }

	  public Ticket(Connection db, int id,boolean farmacosorveglianza) throws SQLException {
			this.farmacosorveglianza=farmacosorveglianza;
		    queryRecord(db, id);
		  }

	  public String getDescrizione1() {
			return descrizione1;
		}

		public void setDescrizione1(String descrizione1) {
			this.descrizione1 = descrizione1;
		}
		
		  public String getDescrizione2() {
				return descrizione2;
			}

			public void setDescrizione2(String descrizione2) {
				this.descrizione2 = descrizione2;
			}
			  public String getDescrizione3() {
					return descrizione3;
				}

				public void setDescrizione3(String descrizione3) {
					this.descrizione3 = descrizione3;
				}
				
				
				
				private int idTicketNonConformita=-1;
				
				
				public int getIdTicketNonConformita() {
					return idTicketNonConformita;
				}

				private int idControlloUfficialeTicket=-1;
				
				/*private void setIdControlloUfficialeTicket(Connection db)throws SQLException{
					String sql="select ticketid from ticket where id_controllo_ufficiale in (select id_controllo_ufficiale from ticket where ticketid=?)";
					PreparedStatement pst=db.prepareStatement(sql);
					pst.setInt(1, idTicketNonConformita);
					ResultSet rs=pst.executeQuery();
					if(rs.next())
						idControlloUfficialeTicket=rs.getInt(1);
					
					
					
				}*/
				
				
				
				
				
				
				public int getIdControlloUfficialeTicket() {
					return idControlloUfficialeTicket;
				}


	  public void queryRecord(Connection db, int id) throws SQLException {
		    if (id == -1) {
		      throw new SQLException("Invalid Ticket Number");
		    }
		    
		     String sql = " SELECT t.*,cu.ticketid as id_cu,nonconformita.tipologia as tipologia_nonconformita, " +
		    		 	  " case when opu.asl_rif is not null then opu.asl_rif" + 
		    		 	  " when o.asl_rif is not null then o.asl_rif " + 
		    		 	  " when anag.asl_rif is not null then anag.asl_rif end AS orgsiteid," +
		    		 	  " case when opu.tipologia_operatore is not null then opu.tipologia_operatore" + 
		    		 	  " when o.tipologia_operatore is not null then o.tipologia_operatore " + 
		    		 	  " when anag.tipologia_operatore is not null then anag.tipologia_operatore end AS tipologia_operatore," + 
		    		 	  " cu.chiusura_attesa_esito " +
		    		 	  "FROM ticket t " ;
		    	 sql +=" left join ticket nonconformita on nonconformita.ticketid=t.id_nonconformita "
		    	 		+ "left join ricerche_anagrafiche_old_materializzata opu on t.id_stabilimento = opu.riferimento_id and opu.riferimento_id_nome_col='id_stabilimento' "  
						+ "left join ricerche_anagrafiche_old_materializzata o on t.org_id = o.riferimento_id and o.riferimento_id_nome_col='org_id' "	
						+ "left join ricerche_anagrafiche_old_materializzata anag on t.alt_id = anag.riferimento_id and anag.riferimento_id_nome_col='alt_id' " ;
		    	 sql +="LEFT JOIN ticket cu  ON (t.id_controllo_ufficiale = cu.id_controllo_ufficiale  and cu.tipologia = 3) " +
		        "WHERE t.ticketid = ? AND t.tipologia = 15";
		    	 
		        PreparedStatement pst = db.prepareStatement(sql);
		    pst.setInt(1, id);
		    ResultSet rs = pst.executeQuery();
		  
		    if (rs.next()) {
		      buildRecord(rs);
		      //this.setIdTicketNConformita(db);
		      //this.setIdControlloUfficialeTicket(db);
		      super.controlloBloccoCu(db, Integer.parseInt(this.idControlloUfficiale));
		    }
		    this.setListaLimitazioniFollowup(db);
		    rs.close();
		    pst.close();
		    if (this.id == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }
		  
		   
		  }
	  
	  
	  public void insertLimitazioniFollowup(Connection db,String[] s) throws SQLException{
		  
		  if(s!=null){
			
			  String sql="insert into limitazioni_followup (idticket,idlimitazione) values(?,?)";
			  PreparedStatement pst=db.prepareStatement(sql);
			  pst.setInt(1, id);
			  for(int i=0;i<s.length;i++){
			  pst.setInt(2, Integer.parseInt(s[i]));
			  pst.execute();
			  
			  }
		  }
		  
		  
	  }
	  
	  
	  public void updateLimitazioneFollowup(Connection db,String[] s)throws SQLException{
		  
		  PreparedStatement pst=db.prepareStatement("delete from limitazioni_followup where idticket="+id);
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
		          "resolvedby, resolvedby_department_code, state_id, site_id,ip_entered,ip_modified,  flag_posticipato,flag_campione_non_conforme, ");
		      
		     
		      
		      if (tipo_nc != -1)
		      {
		    	  sql.append("tipo_nc,");
		      }

		      
		      if (idControlloUfficiale != null)
		      {
		    	  sql.append("id_controllo_ufficiale,");
		      }
		      if (altId>0)
		      {
		    	  sql.append("alt_id,");
		      }
		      if (farmacosorveglianza == false)
			   {
			    	sql.append("org_id,id_stabilimento,id_apiario, ");
			   }
			     else
			     {
			    	 sql.append("id_farmacia,");
			     }
		      
		     
		      
		      
		        sql.append("ticketid, ");
		      
		      if (entered != null) {
		        sql.append("entered, ");
		      }
		      if (modified != null) {
		        sql.append("modified, ");
		      }
		      sql.append("followup,");
		      if(noteFollowup!=null){
		    	  sql.append("notefollowup,");
		    	  
		      }
		      
		      if (valutazione != null)
		      {
		    	  sql.append("nc_gravi_valutazione, ");
		      }
		      
		      sql.append("tipo_richiesta,  enteredBy, modifiedBy, " +
		      		"tipologia, provvedimenti_prescrittivi, sanzioni_amministrative, sanzioni_penali, sequestri, descrizione1, descrizione2, descrizione3  ");
		     
		      sql.append(" , tipo_sequestro");
		      sql.append(" , tipo_sequestro_due");
		      sql.append(" , tipo_sequestro_tre");
		      sql.append(" , tipo_sequestro_quattro");
		      sql.append(", id_nonconformita");
		      if (punteggio != -1) {
			        sql.append(", punteggio");
			      }
		      sql.append(",identificativonc");
		      sql.append(")");
		      sql.append("VALUES (?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?, ");
		      sql.append("?, ?, ?, ");
		      if (tipo_nc != -1)
		      {
		    	  sql.append("?,");
		      }
		      if (idControlloUfficiale != null)
		      {
		    	  sql.append("?,");
		      }
		      if (altId >0 )
		      {
		    	  sql.append("?,");
		      }
		      if (farmacosorveglianza == false)
			   {
			    	sql.append("?,?,?, ");
			   }
			     else
			     {
			    	 sql.append("?,");
			     }
		     
		      
		      
		     
		        sql.append(DatabaseUtils.getNextIntSql( "ticket","ticketid",livello)+",");
		     
		      if (entered != null) {
		        sql.append("?, ");
		      }
		      if (modified != null) {
		        sql.append("?, ");
		      }
		      

		      sql.append("?,");
		      if(noteFollowup!=null){
		    	  sql.append("?,");
		    	  
		      }
		      if (valutazione != null)
		      {
		    	  sql.append("?, ");
		      }
		      
		      sql.append("?,  ?, ?, " +
		      		     "15, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "); //ho aggiunto 2 punti interrogativi
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
		      sql.append(", ?");
		      if (punteggio != -1) {
			        sql.append(", ? ");
			      }
		      sql.append(", ? ");
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
		      pst.setBoolean(++i, super.isFlag_posticipato());
		      pst.setBoolean(++i, super.isFlag_campione_non_conforme());
		    
		      if (tipo_nc != -1)
		      {
		    	  DatabaseUtils.setInt(pst, ++i, tipo_nc);
		      }
		      if (idControlloUfficiale != null)
		      {
		    	  pst.setString(++i, idControlloUfficiale);
		      }
		      if (altId >0)
		      {
		    	  DatabaseUtils.setInt(pst, ++i, this.altId);
		      }
		      if (farmacosorveglianza == false)
			   {
		    	  DatabaseUtils.setInt(pst, ++i, this.orgId);
		    	  DatabaseUtils.setInt(pst, ++i, this.idStabilimento);
		    	  DatabaseUtils.setInt(pst, ++i, this.idApiario);


			   }
			     else
			     {
			    	 DatabaseUtils.setInt(pst, ++i, orgId);
			     }
		   
		     
		      if (entered != null) {
		        pst.setTimestamp(++i, entered);
		      }
		      if (modified != null) {
		        pst.setTimestamp(++i, modified);
		      } 
		      

		      pst.setBoolean(++i, followup);
		      if(noteFollowup!=null){
		    	  pst.setString(++i, noteFollowup);
		    	  
		      }
		      if (valutazione != null)
		      {
		    	  pst.setString(++i, valutazione);
		      }
		      
		      /**
		       * 
		       */
		      pst.setString( ++i, this.getTipo_richiesta() );
		     
		     
		      
		      pst.setInt(++i, this.getEnteredBy());
		      pst.setInt(++i, this.getModifiedBy());
		      DatabaseUtils.setInt(pst, ++i, provvedimenti);
		      DatabaseUtils.setInt(pst, ++i, followupAmministrative);
		      DatabaseUtils.setInt(pst, ++i, followupPenali);
		      DatabaseUtils.setInt(pst, ++i, sequestri);
		      pst.setString( ++i, this.getDescrizione1() );
		      pst.setString( ++i, this.getDescrizione2() );
		      pst.setString( ++i, this.getDescrizione3() );
		      pst.setBoolean(++i , this.getTipoSequestro());
		      pst.setBoolean(++i , this.getTipoSequestroDue());
		      pst.setBoolean(++i , this.getTipoSequestroTre());
		      pst.setBoolean(++i , this.getTipoSequestroQuattro());
		      pst.setInt(++i, id_nonconformita);
		        if (punteggio != -1) {
			        pst.setInt(++i, punteggio);
			      }
		        pst.setString(++i, identificativonc);
		        
		      /* pezzo aggiunto da d.dauria 
		      pst.setTimestamp(++i,new Timestamp( System.currentTimeMillis() ));
		      pst.setString(++i, "x");
		      this.setCloseIt(true);
		         */
		      ResultSet rs = pst.executeQuery();
		      if ( rs.next())
		    	  this.id = rs.getInt("ticketid");
		      pst.close();
		      
				db.prepareStatement("UPDATE TICKET set identificativo = '"+asl+idControlloUfficiale+"' || trim(to_char( "+id+", '"+DatabaseUtils.getPaddedFromId(id)+"' )) where ticketid ="+this.getId()).execute();
		      //OrganizationHistory orgHisory = new OrganizationHistory();
		      /*
		      TicketLog thisEntry = new TicketLog();
				thisEntry.setEnteredBy(this.getModifiedBy());
				thisEntry.setDepartmentCode(this.getDepartmentCode());
				thisEntry.setAssignedTo(this.getAssignedTo());
				thisEntry.setPriorityCode(this.getPriorityCode());
				thisEntry.setSeverityCode(this.getSeverityCode());
				thisEntry.setEscalationCode(this.getEscalationLevel());
				thisEntry.setEntryText(this.getComment());
				thisEntry.setTicketId(this.getId());
				thisEntry.setStateId(this.getStateId());
				thisEntry.process(db, this.getId(), this.getEnteredBy(), this
						.getModifiedBy());
				*/
		      //orgHisory.setOrgId( this.getOrgId() );
		      //orgHisory.setMessage( "Aperta richiesta n. " + this.getId() );
		      //orgHisory.insert( db );
		      
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
		  
		    try
		    {
			    super.setTipologiaNonConformita(rs.getInt("tipologia_nonconformita"));
		    }
		    catch
		    (SQLException e)
		    {
		    	
		    }
		    
		    
		    if (farmacosorveglianza == true)
		       {   
		    	orgId = DatabaseUtils.getInt(rs, "id_farmacia");
		       }
		       else
		       {
		    	   orgId = DatabaseUtils.getInt(rs, "org_id");
		       }
		    
		    try
		    {
		    	super.setIdDistributore(rs.getInt("id_distributore"));
		    }
		    catch
		    (SQLException e)
		    {
		    	
		    }
		    idStabilimento = rs.getInt("id_stabilimento");
		    idApiario = rs.getInt("id_apiario");
		    idControlloUfficialeTicket = rs.getInt("id_cu");
		    id_nonconformita = rs.getInt("id_nonconformita");
		    idTicketNonConformita=id_nonconformita;
		    contactId = DatabaseUtils.getInt(rs, "contact_id");
		    problem = rs.getString("problem");
		    entered = rs.getTimestamp("entered");
		    enteredBy = rs.getInt("enteredby");
		    modified = rs.getTimestamp("modified");
		    modifiedBy = rs.getInt("modifiedby");
		    closed = rs.getTimestamp("closed");
		 
		    chiusura_attesa_esito = rs.getBoolean("chiusura_attesa_esito");
		    tipologia_operatore = rs.getInt("tipologia_operatore");
		    if (idStabilimento>0)
				   tipologia_operatore = Ticket.TIPO_OPU;
		    
		    tipo_nc = rs.getInt("tipo_nc") ;
		    followup=rs.getBoolean("followup");
		    noteFollowup=rs.getString("notefollowup");
		    valutazione = rs.getString("nc_gravi_valutazione");
		    identificativonc=rs.getString("identificativonc");
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
		    tipo_richiesta = rs.getString( "tipo_richiesta" );
		
		    tipologia = rs.getInt( "tipologia" );
		    provvedimenti = DatabaseUtils.getInt(rs, "provvedimenti_prescrittivi");
		    followupAmministrative = DatabaseUtils.getInt(rs, "sanzioni_amministrative");
		    followupPenali = DatabaseUtils.getInt(rs, "sanzioni_penali");
		    sequestri = DatabaseUtils.getInt(rs, "sequestri");
		    
		    descrizione1 = rs.getString( "descrizione1" );
		    descrizione2 = rs.getString( "descrizione2" );
		    descrizione3 = rs.getString( "descrizione3" );
		    
			tipoSequestro = rs.getBoolean("tipo_sequestro");
			tipoSequestroDue = rs.getBoolean("tipo_sequestro_due");
			tipoSequestroTre = rs.getBoolean("tipo_sequestro_tre");
			tipoSequestroQuattro = rs.getBoolean("tipo_sequestro_quattro");
			idControlloUfficiale = rs.getString("id_controllo_ufficiale");		    
		    identificativo = rs.getString("identificativo");
		    punteggio = rs.getInt("punteggio");
		    
		    //organization table
		   
		    orgSiteId = DatabaseUtils.getInt(rs, "orgsiteid");


		    /*
		    //lookup_department table
		    departmentName = rs.getString("dept");
		    resolvedByDeptName = rs.getString("resolvedept");

		    //ticket_priority table
		    priorityName = rs.getString("ticpri");

		    //ticket_severity table
		    severityName = rs.getString("ticsev");

		    //ticket_category table
		    categoryName = rs.getString("catname");
		    //lookup_ticket_source table
		    sourceName = rs.getString("sourcename");

		    //service_contract table
		    serviceContractNumber = rs.getString("contractnumber");
		    totalHoursRemaining = rs.getFloat("hoursremaining");
		    contractStartDate = rs.getTimestamp("contractstartdate");
		    contractEndDate = rs.getTimestamp("contractenddate");
		    contractOnsiteResponseModel = DatabaseUtils.getInt(
		        rs, "contractonsiteservicemodel");

		    //asset table
		    assetSerialNumber = rs.getString("serialnumber");
		    assetManufacturerCode = DatabaseUtils.getInt(rs, "assetmanufacturercode");
		    assetVendorCode = DatabaseUtils.getInt(rs, "assetvendorcode");
		    assetModelVersion = rs.getString("modelversion");
		    assetLocation = rs.getString("assetlocation");
		    assetOnsiteResponseModel = DatabaseUtils.getInt(
		        rs, "assetonsiteservicemodel");

		    //product catalog
		    productSku = rs.getString("productsku");
		    productName = rs.getString("productname");

		    //ticketlink_project
		    projectId = DatabaseUtils.getInt(rs, "project_id");

		    //projects
		    projectName = rs.getString("projectname");

		    // user groups
		    userGroupName = rs.getString("usergroupname");

		    //from lookup_ticket_escalation table
		    escalationLevelName = rs.getString("escalationlevelname");
		    */
	
		    altId = DatabaseUtils.getInt(rs, "alt_id");
		    setPermission();
		  }
	  
	  public int update(Connection db, boolean override) throws SQLException {
		    int resultCount = 0;
		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer();
		    sql.append(
		        "UPDATE ticket " +
		        "SET link_contract_id = ?, link_asset_id = ?, department_code = ?, " +
		        "pri_code = ?, scode = ?, " +
		        "cat_code = ?, assigned_to = ?, " +
		        "subcat_code1 = ?, subcat_code2 = ?, subcat_code3 = ?, " +
		        "source_code = ?, contact_id = ?, problem = ?, " +
		        "status_id = ?, trashed_date = ?, site_id = ? , ");
		    if (!override) {
		      sql.append(
		          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedby = ?, ");
		    }
		  
		  
		  if(noteFollowup!=null) {
			  sql.append("notefollowup=?,");
		  }
		  
		  
		  if(valutazione !=null) {
			  sql.append("nc_gravi_valutazione =?,");
		  }
		  
		    
		    sql.append(
		        "solution = ?,  location = ?, assigned_date = ?, assigned_date_timezone = ?, " +
		        "est_resolution_date = ?, est_resolution_date_timezone = ?, resolution_date = ?, resolution_date_timezone = ?, " +
		        "cause = ?, expectation = ?, product_id = ?, customer_product_id = ?, " +
		        "user_group_id = ?, cause_id = ?, resolution_id = ?, defect_id = ?, state_id = ?, " +

		        //"escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, descrizione2 = ?, descrizione3 = ? , tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? " +

		        "escalation_level = ?, resolvable = ?, resolvedby = ?, resolvedby_department_code = ?, tipo_richiesta = ?, " +
		        "provvedimenti_prescrittivi = ?, sanzioni_amministrative = ?, sanzioni_penali = ?, sequestri = ?, descrizione1 = ?, " +
		        "descrizione2 = ?, descrizione3 = ?, tipo_sequestro = ?, tipo_sequestro_due = ?, tipo_sequestro_tre = ?, tipo_sequestro_quattro = ? " +

		        "WHERE ticketid = ? ");
		   /* if (!override) {
		      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
		    }*/
		    int i = 0;
		    pst = db.prepareStatement(sql.toString());
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);
	        pst.setNull(++i, java.sql.Types.INTEGER);

		    DatabaseUtils.setInt(pst, ++i, this.getContactId());
		    pst.setString(++i, this.getProblem());
		    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
		    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
		    DatabaseUtils.setInt(pst, ++i, this.getSiteId());
		    if (!override) {
		      pst.setInt(++i, this.getModifiedBy());
		    }
		  
		   
		    //pst.setBoolean(++i, followup);
			  if(noteFollowup!=null) {
				  pst.setString(++i, noteFollowup);
			  }
			  if(valutazione !=null) {
				  pst.setString(++i, valutazione);
			  }
		    pst.setString(++i, this.getSolution());
		    
		   
		    pst.setString(++i, location);
		    DatabaseUtils.setTimestamp(pst, ++i, assignedDate);
		    pst.setString(++i, this.assignedDateTimeZone);
		    DatabaseUtils.setTimestamp(pst, ++i, estimatedResolutionDate);
		    pst.setString(++i, estimatedResolutionDateTimeZone);
		    DatabaseUtils.setTimestamp(pst, ++i, resolutionDate);
		    pst.setString(++i, this.resolutionDateTimeZone);
		    pst.setString(++i, cause);
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

		    pst.setString( ++i, tipo_richiesta );
		    DatabaseUtils.setInt(pst, ++i, provvedimenti);
		    DatabaseUtils.setInt(pst, ++i, followupAmministrative);
		    DatabaseUtils.setInt(pst, ++i, followupPenali);
		    DatabaseUtils.setInt(pst, ++i, sequestri);
		    pst.setString(++i, descrizione1);
		    pst.setString(++i, descrizione2);
		    pst.setString(++i, descrizione3);
		    
		    pst.setBoolean(++i, this.getTipoSequestro());
			pst.setBoolean(++i, this.getTipoSequestroDue());
			pst.setBoolean(++i, this.getTipoSequestroTre());
			pst.setBoolean(++i, this.getTipoSequestroQuattro());
			
		    pst.setInt(++i, id);
		    /*if (!override && this.getModified() != null) {
		      pst.setTimestamp(++i, this.getModified());
		    }*/
		    resultCount = pst.executeUpdate();
		    pst.close();
		    
		    //controllo di
		   
		   
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
		PreparedStatement pst =null ;
				

		// Logic Delete the ticket
		/*pst = db.prepareStatement("DELETE FROM ticket WHERE ticketid = ?");
		pst.setInt(1, this.getId());*/
		pst = db.prepareStatement("UPDATE ticket SET trashed_date = ? WHERE ticketid = ?");
		pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
		pst.setInt(2, this.getId());
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
		
		  public int update(Connection db) throws SQLException {
			    int i = -1;
			    try {
			      db.setAutoCommit(false);
			      i = this.update(db, false);
			      
			      db.commit();
			    } catch (SQLException e) {
			      db.rollback();
			      throw new SQLException(e.getMessage());
			    } finally {
			      db.setAutoCommit(true);
			    }
			    return i;
			  }

		public String getTestoAppoggio() {
			return testoAppoggio;
		}

		public void setTestoAppoggio(String testoAppoggio) {
			this.testoAppoggio = testoAppoggio;
		}
		  
		  /*metodo di chiusura */
		  
		
	  
}

